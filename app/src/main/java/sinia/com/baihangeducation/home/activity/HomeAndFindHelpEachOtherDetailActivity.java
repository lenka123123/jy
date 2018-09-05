package sinia.com.baihangeducation.home.activity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.framwork.glide.ImageLoaderUtils;
import com.example.framwork.utils.ProgressActivityUtils;
import com.example.framwork.utils.Toast;
import com.example.framwork.widget.ProgressActivity;
import com.example.framwork.widget.superrecyclerview.recycleview.SuperRecyclerView;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMWeb;
import com.zzhoujay.richtext.RichText;

import java.util.ArrayList;
import java.util.List;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.supplement.base.BaseActivity;
import sinia.com.baihangeducation.MyApplication;
import sinia.com.baihangeducation.home.adapter.HomeAndFindHelpEachOtherDetailAdapter;
import com.mcxtzhang.swipemenulib.info.HomeAndFindHelpEachOtherDetailAllInfo;
import com.mcxtzhang.swipemenulib.info.bean.CommentInfo;
import sinia.com.baihangeducation.home.present.HomeAndFindHelpEachOtherDetailPresenter;
import sinia.com.baihangeducation.home.view.HomeAndFindHelpEachOtherDetailView;
import sinia.com.baihangeducation.supplement.tool.BaseUtil;

/**
 * Created by Administrator on 2018/4/27.
 */

public class HomeAndFindHelpEachOtherDetailActivity extends BaseActivity implements HomeAndFindHelpEachOtherDetailView, SuperRecyclerView.LoadingListener, SwipeRefreshLayout.OnRefreshListener {
    private Intent intent;
    private String cooperation_id;
    private int countpage = 1;
    private int itemnum = 20;
    private String type = "5";
    private String parentCommentId = "0";
    private HomeAndFindHelpEachOtherDetailAllInfo mData;
    private View contentView;
    private PopupWindow popupWindow;
    private MyApplication application;

    private SuperRecyclerView rvContainer;
    private ProgressActivity progressActivity;
    private SwipeRefreshLayout swipeContainer;
    private ProgressActivityUtils progressActivityUtils;
    private HomeAndFindHelpEachOtherDetailAdapter mHomeAndFindHelpEachOtherDetailAdapter;
    private List<CommentInfo> mList;
    private boolean isLoadMore = false;

    private TextView mTitle;                        //标题
    private ImageView mImg;                         //头像
    private TextView mName;                         //姓名
    private TextView mSolve;                       //是否解决
    private TextView mAdressAndTimeAndSee;                       //地址 时间 浏览了多少次
    private TextView mIsPay;                       //是否付款
    private TextView mNeedNum;                       //需要多少人
    private TextView mSex;                       //性别
    private TextView mSolveState;                       //是否解决状态
    private TextView mContent;                       //内容
    private TextView mPayNum;                       //付款金额
    private TextView mApply;                       //申请按钮
    private TextView mMessageNum;                       //留言数量
    private EditText mWriteMessage;                       //留言
    private RelativeLayout mMessageLayout;                       //留言布局
    private ImageView mShare;                       //分享

    private HomeAndFindHelpEachOtherDetailPresenter mHomeAndFindHelpEachOtherDetailPresenter;

    @Override
    public int initLayoutResID() {
        return R.layout.homeandfindhelpeachotherdetailtitle;
    }

    @Override
    protected void initData() {
        application = (MyApplication) context.getApplication();
        mList = new ArrayList<>();
        intent = getIntent();
        mCommonTitle.setCenterText(R.string.helpdetail);
        mCommonTitle.setBackground(getResources().getDrawable(R.color.white));
        cooperation_id = intent.getStringExtra("cooperation_id");

        mHomeAndFindHelpEachOtherDetailPresenter = new HomeAndFindHelpEachOtherDetailPresenter(context, this);
        mHomeAndFindHelpEachOtherDetailPresenter.getDetailInfo();

        mHomeAndFindHelpEachOtherDetailAdapter = new HomeAndFindHelpEachOtherDetailAdapter(context, mList);
        addHeaderView();
        initSwipeLayout(swipeContainer, this);
        showSwipeRefreshLayout(swipeContainer);
        progressActivityUtils = new ProgressActivityUtils(context, progressActivity);
        initRecyclerView(rvContainer, mHomeAndFindHelpEachOtherDetailAdapter, this);
    }

    private void addHeaderView() {
        View header = LayoutInflater.from(context).inflate(R.layout.homeandfindhelpeachotherdetailhead, null);
        mTitle = header.findViewById(R.id.homeandfindhelpeachotherdetailhead_title);
        mImg = header.findViewById(R.id.homeandfindhelpeachotherdetailhead_img);
        mName = header.findViewById(R.id.homeandfindhelpeachotherdetailhead_name);
        mSolve = header.findViewById(R.id.homeandfindhelpeachotherdetailhead_issolve);
        mAdressAndTimeAndSee = header.findViewById(R.id.homeandfindhelpeachotherdetailhead_adressandtimeandesee);
        mIsPay = header.findViewById(R.id.homeandfindhelpeachotherdetailhead_ispay);
        mNeedNum = header.findViewById(R.id.homeandfindhelpeachotherdetailhead_neednum);
        mSex = header.findViewById(R.id.homeandfindhelpeachotherdetailhead_sex);
        mSolveState = header.findViewById(R.id.homeandfindhelpeachotherdetailhead_solvestate);
        mContent = header.findViewById(R.id.homeandfindhelpeachotherdetailhead_content);
        mPayNum = header.findViewById(R.id.homeandfindhelpeachotherdetailhead_pricenum);
        mApply = header.findViewById(R.id.homeandfindhelpeachotherdetailhead_apply);

        mApply.setOnClickListener(this);
        mHomeAndFindHelpEachOtherDetailAdapter.addHeaderView(header);
    }

    @Override
    protected void initView() {
        mWriteMessage = $(R.id.homeandfindhelpeachotherdetailhead_writemessage);
        mMessageLayout = $(R.id.homeandfindhelpeachotherdetailhead_message);
        mShare = $(R.id.homeandfindhelpeachotherdetailhead_share);
        mMessageNum = $(R.id.homeandfindhelpeachotherdetailhead_messagenum);

        rvContainer = $(R.id.rv_container);
        progressActivity = $(R.id.progressActivity);
        swipeContainer = $(R.id.swipe_container);

        mWriteMessage.setOnClickListener(this);
        mMessageLayout.setOnClickListener(this);
        mShare.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mHomeAndFindHelpEachOtherDetailPresenter != null) {
            onRefresh();
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (!BaseUtil.isLogin(context, application)) {
            return;
        }
        switch (v.getId()) {
            case R.id.homeandfindhelpeachotherdetailhead_writemessage:
                //写留言
                mWriteMessage.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        //是否是回车键
                        if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                            //隐藏键盘
                            ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                                    .hideSoftInputFromWindow(context.getCurrentFocus()
                                            .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                            //发送
                            mWriteMessage.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                                @Override
                                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                                    if (!v.getText().toString().trim().isEmpty()) {

                                        mHomeAndFindHelpEachOtherDetailPresenter.sendCommen(v.getText().toString().trim());
                                    }
                                    return false;
                                }
                            });
                        }
                        return false;
                    }
                });
                break;
            case R.id.homeandfindhelpeachotherdetailhead_message:
                //看留言
                break;
            case R.id.homeandfindhelpeachotherdetailhead_share:
                //分享
                if (mData != null)
                    addShareMeun();
                break;
            case R.id.homeandfindhelpeachotherdetailhead_apply:
                //申请
                mHomeAndFindHelpEachOtherDetailPresenter.apply();
                break;
            case R.id.sharemeun_qqfriend:
                //QQ好友
                doShare(SHARE_MEDIA.QQ);
                break;
            case R.id.sharemeun_qqzone:
                //QQ空间
                doShare(SHARE_MEDIA.QZONE);
                android.widget.Toast.makeText(context, "QQ空间", android.widget.Toast.LENGTH_SHORT).show();
                break;
            case R.id.sharemeun_wechatfriend:
                //微信好友
                doShare(SHARE_MEDIA.WEIXIN);
                android.widget.Toast.makeText(context, "微信好友", android.widget.Toast.LENGTH_SHORT).show();
                break;
            case R.id.sharemeun_moment:
                //朋友圈
                doShare(SHARE_MEDIA.WEIXIN_CIRCLE);
                android.widget.Toast.makeText(context, "朋友圈", android.widget.Toast.LENGTH_SHORT).show();
                break;
        }
    }

    /**
     * 分享
     *
     * @param
     */
    private void doShare(SHARE_MEDIA media) {
        UMWeb web = new UMWeb(mData.cooperation_share_url);
        web.setTitle(mData.cooperation_title);//标题
        new ShareAction(context)
                .setPlatform(media)
                .withMedia(web)
                .setCallback(shareListener)
                .share();
    }

    //分享回调监听
    private UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {
        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
//            android.widget.Toast.makeText(context, "分享成功", android.widget.Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            android.widget.Toast.makeText(context, "分享失败" + t.getMessage(), android.widget.Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            android.widget.Toast.makeText(context, "分享取消了", android.widget.Toast.LENGTH_LONG).show();
        }
    };

    /**
     * 分享popwindow
     */
    private void addShareMeun() {
        contentView = LayoutInflater.from(this).inflate(
                R.layout.sharemenu, null);
        // 创建PopupWindow对象
        popupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, false);
        // 需要设置一下此参数，点击外边可消失
        TextView qq = contentView.findViewById(R.id.sharemeun_qqfriend);
        TextView qqZone = contentView.findViewById(R.id.sharemeun_qqzone);
        TextView wechat = contentView.findViewById(R.id.sharemeun_wechatfriend);
        TextView moment = contentView.findViewById(R.id.sharemeun_moment);
        qq.setOnClickListener(this);
        qqZone.setOnClickListener(this);
        wechat.setOnClickListener(this);
        moment.setOnClickListener(this);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        // 设置点击窗口外边窗口消失
        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(R.style.pop_anim);
        // 设置此参数获得焦点，否则无法点击
        popupWindow.setFocusable(true);
        popupWindow.showAtLocation(mShare.getRootView(), Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void showLoading() {
        showProgress();
    }

    @Override
    public void hideLoading() {
        hideProgress();
        hideSwipeRefreshLayout(swipeContainer);
        rvContainer.completeLoadMore();
    }

    @Override
    public String getPage() {
        return countpage + "";
    }

    @Override
    public String getPerpage() {
        return itemnum + "";
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getTypeId() {
        return cooperation_id;
    }

    @Override
    public String getParentId() {
        return parentCommentId;
    }

    @Override
    public String getLocationLat() {
        return getLat();
    }

    @Override
    public String getLocationLng() {
        return getLng();
    }

    @Override
    public String getCooperationId() {
        return cooperation_id;
    }

    @Override
    public void getDetailInfoSuccess(HomeAndFindHelpEachOtherDetailAllInfo mInfo, int maxpage) {
        if (mInfo != null) {
            mData = mInfo;
            mTitle.setText(mInfo.cooperation_title);
            ImageLoaderUtils.displayRound(context, mImg, mInfo.cooperation_user_avatar, R.drawable.new_eorrlogo);
            mName.setText(mInfo.cooperation_user_nickname);
            if (mInfo.cooperation_status == 1) {
                mSolve.setVisibility(View.VISIBLE);
            } else {
                mSolve.setVisibility(View.GONE);
            }
            mAdressAndTimeAndSee.setText(mInfo.cooperation_city_name + " " + mInfo.cooperation_distance + "   " + mInfo.cooperation_add_date + "   " + "浏览了" + mInfo.cooperation_look_num + "次");
            if (mInfo.cooperation_is_paid == 1) {
                mIsPay.setText("付费");
            } else {
                mIsPay.setText("免费");
            }

            if (mInfo.is_apply == 2) {
                mApply.setEnabled(true);
                mApply.setText("申请");
                mApply.setBackground(getResources().getDrawable(R.drawable.textview_round));
            } else {
                mApply.setEnabled(false);
                mApply.setText("已申请");
                mApply.setBackground(getResources().getDrawable(R.drawable.textview_unchoicw_round));

            }

            mNeedNum.setText(mInfo.cooperation_need_num + "");
            mSex.setText(mInfo.cooperation_sex_req);
            mSolveState.setText(mInfo.cooperation_status_name);
            RichText.from(mInfo.cooperation_content).into(mContent);
            mPayNum.setText(mInfo.cooperation_price + "");
            if (mInfo.comment_list.count == 0) {
                mMessageNum.setVisibility(View.GONE);
            } else {
                mMessageNum.setVisibility(View.VISIBLE);
                mMessageNum.setText(mInfo.comment_list.count + "");
            }
        }

        if (mInfo.comment_list.list.size() == 0) {
//            progressActivityUtils.showEmptry("暂无数据");
        } else {
            progressActivityUtils.showContent();
            countpage++;
            if (maxpage == 1 || countpage > maxpage) {
                rvContainer.setLoadMoreEnabled(false);
            } else {
                rvContainer.setLoadMoreEnabled(true);
            }
            if (!isLoadMore) {
                mList.clear();
            }
            mList.addAll(mInfo.comment_list.list);
            mHomeAndFindHelpEachOtherDetailAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void getSendMessageSuccess() {
        Toast.getInstance().showSuccessToast(context, "留言成功");
        mWriteMessage.setText("");
        mWriteMessage.setHint("写留言...");
        onRefresh();
    }

    @Override
    public void getApplySuccess() {
        onRefresh();
    }

    @Override
    public void onRefresh() {
        isLoadMore = false;
        countpage = 1;
        getServerData();
    }

    @Override
    public void onLoadMore() {
        isLoadMore = true;
        getServerData();
    }


    /**
     * 获取数据
     */
    private void getServerData() {
        mHomeAndFindHelpEachOtherDetailPresenter.getDetailInfo();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, intent);
    }
}
