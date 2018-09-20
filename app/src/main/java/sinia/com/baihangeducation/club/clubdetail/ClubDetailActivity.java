package sinia.com.baihangeducation.club.clubdetail;

import android.content.Context;
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
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.framwork.utils.ProgressActivityUtils;
import com.example.framwork.utils.Toast;
import com.example.framwork.widget.ProgressActivity;
import com.example.framwork.widget.superrecyclerview.recycleview.SuperRecyclerView;
import com.mcxtzhang.swipemenulib.customview.GlideLoadUtils;

import java.util.ArrayList;
import java.util.List;

import sinia.com.baihangeducation.MainActivity;
import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.club.clubdetail.interfaces.ClubDetailContract;
import sinia.com.baihangeducation.club.clubdetail.model.ClubDetailBean;
import sinia.com.baihangeducation.club.clubdetail.model.ClubDetailModel;
import sinia.com.baihangeducation.club.clubdetail.presenter.ClubDetailPresenter;
import sinia.com.baihangeducation.club.clubdetail.view.ClubDetailListAdapter;
import sinia.com.baihangeducation.newcampus.tabs.friend.Utils;
import sinia.com.baihangeducation.supplement.alertview.AlertViewContorller;
import sinia.com.baihangeducation.supplement.alertview.OnItemClickListener;
import sinia.com.baihangeducation.supplement.base.BaseActivity;
import sinia.com.baihangeducation.supplement.base.Goto;

public class ClubDetailActivity extends BaseActivity implements
        ClubDetailContract.View, SuperRecyclerView.LoadingListener, SwipeRefreshLayout.OnRefreshListener, OnItemClickListener {


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
    private int perpage = 20;
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
    private String power;
    private String introduce;
    private String club_id;
    private ProgressActivity progressActivity;
    private SwipeRefreshLayout swipeContainer;
    private ProgressActivityUtils progressActivityUtils;
    private String logoUrl;
    private TextView transparent;
    private TextView exit;
    private ImageView back;

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

        progressActivityUtils = new ProgressActivityUtils(ClubDetailActivity.this, progressActivity);
        initSwipeLayout(swipeContainer, this);
        clubDetailPresenter = new ClubDetailPresenter(new ClubDetailModel(ClubDetailActivity.this), this);
        getServerData();

    }

    @Override
    protected void initView() {

        exit = findViewById(R.id.exit);
        back = findViewById(R.id.back);
        transparent = findViewById(R.id.transparent);
        progressActivity = findViewById(R.id.progressActivity);
        swipeContainer = findViewById(R.id.swipe_container);
        rvContainer = findViewById(R.id.rv_container);
        send_ad = findViewById(R.id.send_ad);
        send_ad.setOnClickListener(this);
        exit.setOnClickListener(this);
        back.setOnClickListener(this);
        mClubListAdapter = new ClubDetailListAdapter(ClubDetailActivity.this, list);
        initRecyclerView(rvContainer, mClubListAdapter, this);
        addHeaderView();

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

        notice_tv.setOnClickListener(this);
        join_club.setOnClickListener(this);
        club_person_list.setOnClickListener(this);
        visitor = header.findViewById(R.id.visitor);
        administrator = header.findViewById(R.id.administrator);
        mClubListAdapter.addHeaderView(header);
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

        GlideLoadUtils.getInstance().glideLoad(ClubDetailActivity.this, successMessage.info.logo, logo_img, R.drawable.logo);
//         GlideLoadUtils.getInstance().glideLoad(getApplicationClubDetailActivity.this(), successMessage.info.logo, logo, R.drawable.logo);
//        Glide.with(getApplicationClubDetailActivity.this()).load(url).into(imageview)


        school_name.setText(successMessage.info.name);
        clubId = successMessage.info.id;
        power = successMessage.info.power;
        logoUrl = successMessage.info.logo;
        introduce = "  " + successMessage.info.introduce.trim();

        school_person.setText(successMessage.info.member_num + "人");
        school_number.setText("No." + successMessage.info.new_order);
        school_total_money.setText("俱乐部总收入: " + successMessage.info.income);

//        public String page;
//        public String perpage;
//        public String count;

        if (currentPage == 1) {
            list.clear();
        }
        mClubListAdapter.setData(successMessage.info.power, successMessage.info.id);
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

        if (power.equals("2")) {
            setNotice(notice_tv, introduce);
        } else {
            notice_tv.setText(introduce);
        }

        if (successMessage.info.power.equals("0")) {
            join_club.setClickable(true);
            join_club.setText("申请加入");
            if (successMessage.info.is_apply.equals("0")) {
                join_club.setClickable(false);
                join_club.setText("已申请");
            } else {
                join_club.setClickable(true);
                join_club.setText("申请加入");
            }
        } else {
            join_club.setClickable(false);
            join_club.setText("已加入");
        }
        // role  角色    ( 1：成员 2：财务 3：副会长 4：会长 )
        // power   ( 0：游客 1：普通成员 2：管理员 )
        if (power.equals("2")) {
            visitor.setVisibility(View.GONE);
            administrator.setVisibility(View.VISIBLE);//任务
            send_ad.setVisibility(View.VISIBLE); //发布公告
            join_club.setClickable(true);
            join_club.setText("申请列表");
        } else {
            if (power.equals("1")) {
                exit.setText("退出");
            } else {
                exit.setVisibility(View.INVISIBLE);
            }
            if (power.equals("1")) {//普通成员
                visitor.setVisibility(View.GONE);
                administrator.setVisibility(View.VISIBLE);//任务
            } else {
                visitor.setVisibility(View.VISIBLE);
                administrator.setVisibility(View.GONE);
            }

            send_ad.setVisibility(View.GONE);

//            if (successMessage.admin_info != null && successMessage.admin_info.id != null) {   //没有管理员
            System.out.println("=======null===");
            if (successMessage.admin_info.nickname != null)
                administrator_name.setText(successMessage.admin_info.nickname);
            if (successMessage.admin_info.avatar != null) {

                Glide.with(ClubDetailActivity.this).load(successMessage.admin_info.avatar).asBitmap().error(R.drawable.new_eorrlogo).centerCrop().into(new BitmapImageViewTarget(administrator_logo) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(ClubDetailActivity.this.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        administrator_logo.setImageDrawable(circularBitmapDrawable);
                    }
                });
            } else {
                Glide.with(ClubDetailActivity.this).load(R.drawable.new_eorrlogo).asBitmap().error(R.drawable.new_eorrlogo).centerCrop().into(new BitmapImageViewTarget(administrator_logo) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(ClubDetailActivity.this.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        administrator_logo.setImageDrawable(circularBitmapDrawable);
                    }
                });
            }
        }

    }


    private void setNotice(TextView notice_tv, String showText) {
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
        if (v.getId() == R.id.back) {
            ClubDetailActivity.this.finish();
        }
        if (v.getId() == R.id.exit) {
            AlertViewContorller mAlertViewContorller = new AlertViewContorller(transparent, "是否退出该俱乐部", null, "取消", null, gender,
                    ClubDetailActivity.this, AlertViewContorller.Style.ActionSheet, this);
            mAlertViewContorller.setCancelable(true).show();
        }

        if (v.getId() == R.id.join_club) {
            if (join_club.getText().equals("申请加入"))
                clubDetailPresenter.joinClub(clubId);

            if (join_club.getText().equals("申请列表"))
                Goto.toApplyClubListActivity(ClubDetailActivity.this, clubId);
        }
        if (v.getId() == R.id.club_person_list) {
            Goto.toClubPersonClubListActivity(ClubDetailActivity.this, clubId);
        }

        if (v.getId() == R.id.send_ad) {
            //发布公告
            Goto.toClubSendAnnounceActivity(ClubDetailActivity.this, clubId, "", "", "");
        }
        if (v.getId() == R.id.notice_tv) {//
            //编辑简介
            if (power.equals("2"))
                Goto.toClubNoticeActivity(ClubDetailActivity.this, clubId, introduce);
        }
    }

    @Override
    public void showError(String errorMessage) {
    }


    @Override
    public void onRefresh() {
        isLoadMore = false;
        showLoading();
        currentPage = 1;
        getServerData();

    }

    @Override
    public void onLoadMore() {
        isLoadMore = true;
        currentPage = 1;
        hideLoading();

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
        if (clubDetailPresenter != null)
            clubDetailPresenter.getSearchSchoolList(String.valueOf(currentPage), "20", club_id);
    }

    @Override
    public void onItemClick(View view, List<String> mOthers, Object o, int position) {
        if (position >= 0) {
            clubDetailPresenter.dropClub(club_id);
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


}