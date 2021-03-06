package sinia.com.baihangeducation.club.clubdetail;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.framwork.utils.DialogUtils;
import com.example.framwork.utils.ProgressActivityUtils;
import com.example.framwork.utils.SpCommonUtils;
import com.example.framwork.utils.Toast;
import com.example.framwork.widget.ProgressActivity;
import com.example.framwork.widget.superrecyclerview.recycleview.SuperRecyclerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mcxtzhang.swipemenulib.customview.GlideLoadUtils;
import com.umeng.commonsdk.debug.I;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetGroupInfoCallback;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.enums.ConversationType;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.GroupInfo;
import cn.jpush.im.android.eventbus.EventBus;
import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.MainActivity;
import sinia.com.baihangeducation.MyApplication;
import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.club.addclub.AddCLubActivity;
import sinia.com.baihangeducation.club.club.interfaces.ClubGetRequestListener;
import sinia.com.baihangeducation.club.club.interfaces.GetRequestListener;
import sinia.com.baihangeducation.club.club.presenter.ClubHomePresenter;
import sinia.com.baihangeducation.club.clubactive.adapter.ReCommendAdapter;
import sinia.com.baihangeducation.club.clubactive.adapter.ReCommendForTestAdapter;
import sinia.com.baihangeducation.club.clubactive.model.ActiveListData;
import sinia.com.baihangeducation.club.clubdetail.interfaces.ClubDetailContract;
import sinia.com.baihangeducation.club.clubdetail.model.ClubDetailBean;
import sinia.com.baihangeducation.club.clubdetail.model.ClubDetailModel;
import sinia.com.baihangeducation.club.clubdetail.presenter.ClubDetailPresenter;
import sinia.com.baihangeducation.club.clubdetail.view.ClubDetailListAdapter;
import sinia.com.baihangeducation.club.im.ChatActivity;
import sinia.com.baihangeducation.club.im.entity.Event;
import sinia.com.baihangeducation.club.im.entity.EventType;
import sinia.com.baihangeducation.mine.activity.LoginActivity;
import sinia.com.baihangeducation.newcampus.tabs.friend.Utils;
import sinia.com.baihangeducation.supplement.alertview.AlertViewContorller;
import sinia.com.baihangeducation.supplement.alertview.OnItemClickListener;
import sinia.com.baihangeducation.supplement.base.BaseActivity;
import sinia.com.baihangeducation.supplement.base.Goto;
import sinia.com.baihangeducation.supplement.tool.ClubDialogUtils;

public class ClubDetailActivity extends BaseActivity implements
        ClubDetailContract.View, SuperRecyclerView.LoadingListener,
        SwipeRefreshLayout.OnRefreshListener, OnItemClickListener, GetRequestListener {

    private TextView notice_tv;
    private ImageView logo_img;
    private TextView school_name;
    private TextView school_person;
    private TextView school_number;
    private TextView school_total_money;

    private boolean isLoadMore = false;
    private boolean onRestart = false;
    private int onTime = 1;

    private boolean addData = false;
    private int currentPage = 1;
    private int perpage = 10;
    private boolean isCreated = false;

    private List<ClubDetailBean.NoticeList.Notice> list = new ArrayList<ClubDetailBean.NoticeList.Notice>();
    private SuperRecyclerView rvContainer;
    private ClubDetailListAdapter mClubListAdapter;
    private TextView join_club;
    private LinearLayout visitor;
    private LinearLayout administrator;
    private ClubDetailPresenter clubDetailPresenter;
    private String clubId = "";
    private TextView administrator_name;
    private ImageView administrator_logo;
    private TextView club_person_list;
    private TextView send_ad;

    private String introduce = "";
    private String club_id = "";
    private ProgressActivity progressActivity;
    private SwipeRefreshLayout swipeContainer;
    private ProgressActivityUtils progressActivityUtils;
    private String logoUrl = "";
    private TextView transparent;
    private TextView exit;
    private ImageView back;
    private TextView im_chat;
    private String phone = "";
    private String jmessage_group_id = "0";
    private List<String> mPermissionList = new ArrayList<>();
    private TextView club_active;
    private TextView club_part_list;
    private String is_apply_permiss = "";
    private String updatLogUrl = "";
    private String type;
    private String is_chairman;
    private EditText editText;
    private RecyclerView foot_listview;
    private ReCommendForTestAdapter mMemberManageAdapter;

    private List<ActiveListData.ActiveList> mRows = new ArrayList<>();

    @Override
    public int initLayoutResID() {
        return R.layout.activity_club_detail_list;
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        onRestart = true;
        if (clubDetailPresenter != null) {
            onTime = 1;
            currentPage = 1;
            getServerData();
        }
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        club_id = intent.getStringExtra("club_id");

        phone = (String) SpCommonUtils.get(ClubDetailActivity.this, AppConfig.USERPHOTO, "");

        progressActivityUtils = new ProgressActivityUtils(ClubDetailActivity.this, progressActivity);
        initSwipeLayout(swipeContainer, this);
        clubDetailPresenter = new ClubDetailPresenter(new ClubDetailModel(ClubDetailActivity.this), this);
        getServerData();
    }

    @Override
    protected void initView() {

        editText = findViewById(R.id.edittext);
        exit = findViewById(R.id.exit);
        back = findViewById(R.id.back);
        transparent = findViewById(R.id.transparent);
        progressActivity = findViewById(R.id.progressActivity);
        swipeContainer = findViewById(R.id.swipe_container);
        rvContainer = findViewById(R.id.rv_container);
        rvContainer.setNoMore(false);

        send_ad = findViewById(R.id.send_ad);
        send_ad.setOnClickListener(this);
        exit.setOnClickListener(this);
        back.setOnClickListener(this);
        mClubListAdapter = new ClubDetailListAdapter(ClubDetailActivity.this, list);
        initRecyclerView(rvContainer, mClubListAdapter, this);
        addHeaderView();
        addFeetView();
    }

    @Override
    public void onItemClick(View view, List<String> mOthers, Object o, int position) {
        if (position >= 0) {
            clubDetailPresenter.dropClub(club_id);
        }

    }

    private void addHeaderView() {
        View header = LayoutInflater.from(ClubDetailActivity.this).inflate(R.layout.activity_club_detail, null);

        logo_img = header.findViewById(R.id.logo);
        school_name = header.findViewById(R.id.school_name);
        school_person = header.findViewById(R.id.school_person);
        school_number = header.findViewById(R.id.school_number);
        school_total_money = header.findViewById(R.id.school_total_money);
        notice_tv = header.findViewById(R.id.notice_tv);
        join_club = header.findViewById(R.id.join_club);
        administrator_name = header.findViewById(R.id.administrator_name);
        administrator_logo = header.findViewById(R.id.administrator_logo);
        club_person_list = header.findViewById(R.id.club_person_list);
        im_chat = header.findViewById(R.id.im_chat);
        club_active = header.findViewById(R.id.club_active);
        club_part_list = header.findViewById(R.id.club_part_list);

        im_chat.setOnClickListener(this);
        notice_tv.setOnClickListener(this);
        join_club.setOnClickListener(this);
        club_person_list.setOnClickListener(this);
        club_active.setOnClickListener(this);
        club_part_list.setOnClickListener(this);
        visitor = header.findViewById(R.id.visitor);
        administrator = header.findViewById(R.id.administrator);
        mClubListAdapter.addHeaderView(header);
    }

    private void addFeetView() {
        View foot = LayoutInflater.from(ClubDetailActivity.this).inflate(R.layout.foot_club_detail_list, null);
        foot_listview = foot.findViewById(R.id.rv_container);
        mMemberManageAdapter = new ReCommendForTestAdapter(ClubDetailActivity.this);
        foot_listview.setAdapter(mMemberManageAdapter);


        // 设置布局管理器
        final GridLayoutManager manager = new GridLayoutManager(this, 1);
        foot_listview.setLayoutManager(manager);
        // 设置adapter
        // 设置Item添加和移除的动画
        foot_listview.setItemAnimator(new DefaultItemAnimator());
        // 设置Item之间间隔样式

//        mMemberManageAdapter.setData(mRows, currentPage);
        mClubListAdapter.addFooterView(foot);
    }

    @Override
    public void showSearchSchoolList(ClubDetailBean successMessage) {
        hideLoading();
        if (successMessage.info != null) {
            progressActivityUtils.showContent();
        } else {
            progressActivityUtils.showContent();
            rvContainer.setLoadMoreEnabled(true);
        }
// ActiveListData.ActiveList


        updatLogUrl = successMessage.info.logo;
        GlideLoadUtils.getInstance().glideLoad(ClubDetailActivity.this, successMessage.info.logo, logo_img, R.drawable.logo);
//         GlideLoadUtils.getInstance().glideLoad(getApplicationClubDetailActivity.this(), successMessage.info.logo, logo, R.drawable.logo);
//        Glide.with(getApplicationClubDetailActivity.this()).load(url).into(imageview)
        type = successMessage.info.type;
        is_chairman = successMessage.info.is_chairman;

        jmessage_group_id = successMessage.info.jmessage_group_id;
        school_name.setText(successMessage.info.name);
        clubId = successMessage.info.id;
        logoUrl = successMessage.info.logo;
        introduce = "  " + successMessage.info.introduce.trim();

        school_person.setText(successMessage.info.member_num + "人   " + successMessage.info.nature);
        school_number.setText(successMessage.info.school_name);
        school_total_money.setText("俱乐部总收入: " + successMessage.info.income);

        if (currentPage == 1) {
            list.clear();
        }

        mClubListAdapter.setData(mPermissionList, successMessage.info.id);
        if (perpage * currentPage > Integer.valueOf(successMessage.notice_list.count)) { //没
            isLoadMore = false;
//            mClubListAdapter.setData(successMessage.notice_list.list, false, successMessage.info.power, successMessage.info.id);

            list.addAll(successMessage.notice_list.list);
            mClubListAdapter.notifyDataSetChanged();

        } else {
            isLoadMore = true;
            currentPage++;
            list.addAll(successMessage.notice_list.list);
            mClubListAdapter.notifyDataSetChanged();
//            mClubListAdapter.setData(successMessage.notice_list.list, true, successMessage.info.power, successMessage.info.id);
        }

        /**
         * 删除学校公告
         dropSchoolNotice
         学校公告编辑
         editSchoolNotice
         学校公告发布
         pushSchoolNotice
         */

        if (mPermissionList != null) {
            send_ad.setVisibility(mPermissionList.contains("pushNotice") ? View.VISIBLE : View.GONE); //发布公告
        }
        is_apply_permiss = successMessage.info.is_apply;

        //   ( 0：已申请 1：未申请 2：社员  3：禁止申请 )
        if (successMessage.info.is_apply.equals("3")) {
            join_club.setClickable(false);
            join_club.setVisibility(View.INVISIBLE);
            exit.setClickable(false);
            join_club.setText("");
            exit.setText("");
            exit.setVisibility(View.VISIBLE);
        } else if (successMessage.info.is_apply.equals("2")) {
            join_club.setClickable(true);
            join_club.setText("申请列表");
            if (!mPermissionList.contains("setClubApply")) {
                join_club.setText("已加入");
            }
            exit.setText("退出");
            exit.setVisibility(View.VISIBLE);
        } else if (successMessage.info.is_apply.equals("1")) {
            join_club.setClickable(true);
            join_club.setText("申请加入");
            exit.setVisibility(View.INVISIBLE);
        } else {
            join_club.setClickable(false);
            join_club.setText("审核中");
            exit.setVisibility(View.INVISIBLE);
        }
        setNotice(notice_tv, successMessage.info.introduce);

        visitor.setVisibility(View.GONE);
        administrator.setVisibility(View.VISIBLE);//任务

        mRows.clear();
        mRows.addAll(successMessage.activity_list);
        mMemberManageAdapter.setData(mRows, currentPage);
    }


    private void setNotice(TextView notice_tv, String showText) {
        if (!mPermissionList.contains("editIntroduce")) {
            notice_tv.setText(showText);
            return;
        }
//        String showText = "我是多行文ddddddddffffffff 33333333333333333333333333333333333333333尾需要添加一张图片";
        //注意此处showText后+ " "主要是为了占位
        SpannableString ss = new SpannableString(showText + " ");
        int len = ss.length();
        //图片
        Drawable d = ContextCompat.getDrawable(ClubDetailActivity.this, (R.drawable.edit_announce));
        // dp转换成px
//        d.setBounds(Utils.dp2px(0), Utils.dp2px(0), Utils.dp2px(20), Utils.dp2px(20)); //绘画的区域
        d.setBounds(10, Utils.dp2px(0), Utils.dp2px(20), Utils.dp2px(10));//第一0是距左右边距离，第二0是距上下边距离，第三69长度,第四宽度

        //构建ImageSpan
        ImageSpan span = new ImageSpan(d, ImageSpan.ALIGN_BASELINE);
        ss.setSpan(span, len - 1, len, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

        notice_tv.setText(ss);
    }

    static Bitmap drawableToBitmap(Drawable drawable) // drawable 转换成bitmap
    {
        int width = drawable.getIntrinsicWidth();// 取drawable的长宽
        int height = drawable.getIntrinsicHeight();
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;// 取drawable的颜色格式
        Bitmap bitmap = Bitmap.createBitmap(width, height, config);// 建立对应bitmap
        Canvas canvas = new Canvas(bitmap);// 建立对应bitmap的画布
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);// 把drawable内容画到画布中
        return bitmap;
    }

    String[] gender = {"是"};

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.im_chat:
//                ( 0：已申请 1：未申请 2：已通过 3：禁止申请 )
                if (is_apply_permiss.equals("0")) {
                    getCenterCancelDialogShow();
                    return;
                }
                if (is_apply_permiss.equals("1")) {
                    getCenterCancelDialog();
                    return;
                }

                if (is_apply_permiss.equals("3")) {
                    getCenterCancelDialogShowCancel();
                    return;
                }

                JMessageClient.enterGroupConversation(Long.valueOf(jmessage_group_id));
                JMessageClient.getGroupInfo(Long.valueOf(jmessage_group_id), new GetGroupInfoCallback() {
                    @Override
                    public void gotResult(int i, String s, GroupInfo groupInfo) {
                        if (groupInfo.getGroupMembers() == null || groupInfo.getGroupMembers().size() <= 0) {
                            Toast.getInstance().showErrorToast(ClubDetailActivity.this, "聊天功能异常，请重新登录");
                            return;
                        }

                        final Intent intent = new Intent(ClubDetailActivity.this, ChatActivity.class);
                        intent.putExtra(MyApplication.GROUP_ID, Long.valueOf(jmessage_group_id));
                        intent.putExtra("fromGroup", false);

                        intent.putExtra(MyApplication.MEMBERS_COUNT, groupInfo.getGroupMembers().size());
                        intent.putExtra(MyApplication.CONV_TITLE, groupInfo.getGroupName());
                        intent.putExtra("logo", updatLogUrl);
                        startActivity(intent);
                    }
                });
                break;
            case R.id.back:
                ClubDetailActivity.this.finish();
                break;
            case R.id.club_part_list:
                if (is_apply_permiss.equals("0")) {
                    getCenterCancelDialogShow();
                    return;
                }
                if (is_apply_permiss.equals("1")) {
                    getCenterCancelDialog();
                    return;
                }
                if (is_apply_permiss.equals("3")) {
                    getCenterCancelDialogShowCancel();
                    return;
                }
                Goto.toClubPart(ClubDetailActivity.this, mPermissionList.contains("pushJob"));
                break;
            case R.id.exit:
                if (isNeetLogin()) return;
                AlertViewContorller mAlertViewContorller = new AlertViewContorller(transparent, "是否退出该俱乐部", null, "取消", null, gender,
                        ClubDetailActivity.this, AlertViewContorller.Style.ActionSheet, this);
                mAlertViewContorller.setCancelable(true).show();
                break;
            case R.id.join_club:
                if (join_club.getText().equals("申请加入")) {
                    if (isNeetLogin()) return;
                    clubDetailPresenter.joinClub(clubId);
                }
                if (join_club.getText().equals("申请列表")) {
                    if (mPermissionList.contains("setClubApply")) {
                        join_club.setVisibility(View.VISIBLE);
                        if (isNeetLogin()) return;
                        Goto.toApplyClubListActivity(ClubDetailActivity.this, clubId);
                    } else {
//                        Toast.getInstance().showErrorToast(ClubDetailActivity.this, "权限限制");
                        //已经加入   但是没有权限看申请列表   join_club.setVisibility(View.GONE);
                        getCenterCancelDialogApply();
                    }
                } else {

                }
                break;
            case R.id.club_person_list:
                if (is_apply_permiss.equals("0")) {
                    getCenterCancelDialogShow();
                    return;
                }
                if (is_apply_permiss.equals("1")) {
                    getCenterCancelDialog();
                    return;
                }
                if (is_apply_permiss.equals("3")) {
                    getCenterCancelDialogShowCancel();
                    return;
                }
                if (isNeetLogin()) return;
                Goto.toClubPersonClubListActivity(ClubDetailActivity.this, clubId,
                        mPermissionList.contains("dropCrew"), mPermissionList.contains("setClubApply"), is_chairman);

                break;
            case R.id.send_ad:
                //发布公告
                if (isNeetLogin()) return;
                Goto.toClubSendAnnounceActivity(ClubDetailActivity.this, "发布公告", clubId, "", "", "",
                        mPermissionList.contains("dropSchoolNotice"),
                        mPermissionList.contains("editSchoolNotice"),
                        mPermissionList.contains("pushSchoolNotice")
                );
                break;
            case R.id.club_active:
                if (is_apply_permiss.equals("0")) {
                    getCenterCancelDialogShow();
                    return;
                }
                if (is_apply_permiss.equals("1")) {
                    getCenterCancelDialog();
                    return;
                }
                if (is_apply_permiss.equals("3")) {
                    getCenterCancelDialogShowCancel();
                    return;
                }
                //社团活动
                if (isNeetLogin()) return;
                Goto.toHotActive(ClubDetailActivity.this, "club", club_id);
                break;
            case R.id.notice_tv:
                //编辑简介
                if (isNeetLogin()) return;
                if (mPermissionList.contains("editIntroduce"))
                    Goto.toClubNoticeActivity(ClubDetailActivity.this, clubId, introduce);
                break;
        }


    }


    @Override
    public void showError(String errorMessage) {
    }

    public boolean isNeetLogin() {
        if (!AppConfig.ISlOGINED) {
            new AlertDialog.Builder(ClubDetailActivity.this).setTitle("提示！").setMessage("您尚未登录，请先登录。").setPositiveButton("登录", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Goto.toLogin(ClubDetailActivity.this);
                }
            }).setNegativeButton("取消", null).show();
            return true;
        }
        return false;
    }


    @Override
    public void onRefresh() {

        showLoading();
        currentPage = 1;
        getServerData();

    }

    @Override
    public void onLoadMore() {
        if (isLoadMore) {
            showLoading();
            getServerData();
        } else {
            hideLoading();
        }


    }


    public void showLoading() { //调用接口时候
        showProgress();
    }


    public void hideLoading() {//得到数据的时候
        hideProgress();
        hideSwipeRefreshLayout(swipeContainer);
        rvContainer.completeLoadMore();
    }


    /**
     * 获取数据
     */
    private void getServerData() {
        hideEditTextInput(editText);
        if (clubDetailPresenter != null) {

            clubDetailPresenter.getClubPermission(club_id, this);
        }
    }

    @Override
    public void showdropClub(String errorMessage) {
        currentPage = 1;
        getServerData();
    }

    @Override
    public void showjoinClub(String errorMessage) {
        currentPage = 1;
        getServerData();
    }


    // 权限返回
    @Override
    public void setRequestSuccess(String msg) {
        mPermissionList = new Gson().fromJson(msg, new TypeToken<List<String>>() {
        }.getType());
        clubDetailPresenter.getSearchSchoolList(String.valueOf(currentPage), String.valueOf(perpage), club_id);
    }

    @Override
    public void setRequestFail() {
        clubDetailPresenter.getSearchSchoolList(String.valueOf(currentPage), String.valueOf(perpage), club_id);
    }

    public void getCenterCancelDialog() {
        final Dialog dialog = new Dialog(ClubDetailActivity.this, com.example.framwork.R.style.custom_cancel_dialog);
        dialog.setContentView(R.layout.clcub_join_dialog);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.findViewById(R.id.club_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialogWindow.findViewById(R.id.club_join).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNeetLogin()) return;
                if (clubDetailPresenter != null)
                    clubDetailPresenter.joinClub(clubId);
                dialog.cancel();
            }
        });
        //dialogWindow.setWindowAnimations(R.style.mystyle);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = ClubDetailActivity.this.getResources().getDisplayMetrics().widthPixels;
        lp.alpha = 1.0f;
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialogWindow.setAttributes(lp);
        dialogWindow.setGravity(Gravity.CENTER);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();

    }


    public void getCenterCancelDialogShow() {
        final Dialog dialog = new Dialog(ClubDetailActivity.this, com.example.framwork.R.style.custom_cancel_dialog);
        dialog.setContentView(R.layout.clcub_join_dialog_apply);
        Window dialogWindow = dialog.getWindow();

        dialogWindow.findViewById(R.id.club_join).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        //dialogWindow.setWindowAnimations(R.style.mystyle);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = ClubDetailActivity.this.getResources().getDisplayMetrics().widthPixels;
        lp.alpha = 1.0f;
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialogWindow.setAttributes(lp);
        dialogWindow.setGravity(Gravity.CENTER);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();

    }

    public void getCenterCancelDialogShowCancel() {
        final Dialog dialog = new Dialog(ClubDetailActivity.this, com.example.framwork.R.style.custom_cancel_dialog);
        dialog.setContentView(R.layout.clcub_join_dialog_apply_cancel);
        Window dialogWindow = dialog.getWindow();

        dialogWindow.findViewById(R.id.club_join).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        //dialogWindow.setWindowAnimations(R.style.mystyle);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = ClubDetailActivity.this.getResources().getDisplayMetrics().widthPixels;
        lp.alpha = 1.0f;
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialogWindow.setAttributes(lp);
        dialogWindow.setGravity(Gravity.CENTER);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();

    }


    public void getCenterCancelDialogApply() {
        final Dialog dialog = new Dialog(ClubDetailActivity.this, com.example.framwork.R.style.custom_cancel_dialog);
        dialog.setContentView(R.layout.clcub_join_dialog_apply_join_club);
        Window dialogWindow = dialog.getWindow();

        dialogWindow.findViewById(R.id.club_join).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        //dialogWindow.setWindowAnimations(R.style.mystyle);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = ClubDetailActivity.this.getResources().getDisplayMetrics().widthPixels;
        lp.alpha = 1.0f;
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialogWindow.setAttributes(lp);
        dialogWindow.setGravity(Gravity.CENTER);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();

    }

    /**
     * 隐藏软键盘
     *
     * @param editText
     */
    protected void hideEditTextInput(EditText editText) {
        //隐藏键盘
        ((InputMethodManager) ClubDetailActivity.this.getSystemService(INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(editText.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

    }


}
