package sinia.com.baihangeducation.find.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.framwork.utils.ProgressActivityUtils;
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

import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.supplement.base.BaseActivity;
import sinia.com.baihangeducation.MyApplication;

import com.mcxtzhang.swipemenulib.info.JobBangDetailListInfo;
import com.mcxtzhang.swipemenulib.info.ShareEveryDayDetailListInfo;
import com.mcxtzhang.swipemenulib.info.bean.AddCollectionSuccessInfo;
import com.mcxtzhang.swipemenulib.info.bean.ShareEveryDayDetailCommentInfo;

import sinia.com.baihangeducation.find.presenter.ShareEveryDayDetailPresenter;
import sinia.com.baihangeducation.find.view.ShareEveryDayDetailView;
import sinia.com.baihangeducation.home.adapter.MessageAdapter;

import com.mcxtzhang.swipemenulib.info.bean.CommentInfo;
import com.mcxtzhang.swipemenulib.info.bean.HomeAndFindHelpEachOtherDetailCommentInfo;

import sinia.com.baihangeducation.home.present.AddOrDetelCollctionPresenter;
import sinia.com.baihangeducation.home.present.MessagePresenter;
import sinia.com.baihangeducation.home.view.AddCollctionView;
import sinia.com.baihangeducation.home.view.MessageView;
import sinia.com.baihangeducation.supplement.base.Goto;
import sinia.com.baihangeducation.supplement.tool.BaseUtil;
import sinia.com.baihangeducation.find.campus.adapter.MessageDialog;

/**
 * 每日分享详情
 */

public class ShareEveryDayDetailActivity extends BaseActivity implements ShareEveryDayDetailView, MessageView, AddCollctionView, SuperRecyclerView.LoadingListener, SwipeRefreshLayout.OnRefreshListener {

    private MyApplication application;
    private String newId;
    private ShareEveryDayDetailPresenter mShareEveryDayDetailPresenter;//获取详情数据
    private AddOrDetelCollctionPresenter mAddCollctionPresenter;//添加收藏
    private MessagePresenter mMessagePresenter;             //留言
    private int collectionId;
    private String addCollectionType = "7";
    private ShareEveryDayDetailListInfo mData;
    private Intent intent;
    private View contentView;
    private PopupWindow popupWindow;
    private List<ShareEveryDayDetailCommentInfo> mCommentList;
    private int countpage = 1;
    private int itemnum = 20;
    private String type = "1";
    private String parentCommentId = "0";

    private MessageDialog dialog;

    private TextView mTitle;
    private TextView mShortInfo;
    private TextView mContent;
    private ImageView mIsCollection;
    private RelativeLayout mMessageLayout;
    private ImageView mMessage;
    private TextView mMessageNum;
    private ImageView mShare;
    private EditText mWriteMessage;

    private SuperRecyclerView rvContainer;
    private ProgressActivity progressActivity;
    private SwipeRefreshLayout swipeContainer;
    private ProgressActivityUtils progressActivityUtils;
    private MessageAdapter mMessageAdapter;
    private List<CommentInfo> mList;


    private boolean isSuccess = true;
    private WebView mWebView;

    @Override
    public int initLayoutResID() {
        return R.layout.shareeverydaydetailhead;
    }

    @Override
    protected void initData() {
        mCommentList = new ArrayList<>();
        dialog = new MessageDialog(context);

        mList = new ArrayList<>();

        application = (MyApplication) context.getApplication();
        mCommonTitle.setCenterText(R.string.shareeveryday);
        mCommonTitle.setBackground(getResources().getDrawable(R.color.white));
        intent = getIntent();
        //获取新闻ID
        newId = intent.getStringExtra("NEWSID");
        //获取详情presenter
        mShareEveryDayDetailPresenter = new ShareEveryDayDetailPresenter(context, this);
        //获取详情
        mShareEveryDayDetailPresenter.getShareEveryDayDetailData();
        //删除或者添加收藏presenter
        mAddCollctionPresenter = new AddOrDetelCollctionPresenter(context, this);
        //留言presenter
        mMessagePresenter = new MessagePresenter(context, this);

        mMessageAdapter = new MessageAdapter(context, mList);

        addHeaderView();

        initSwipeLayout(swipeContainer, this);
        showSwipeRefreshLayout(swipeContainer);
        progressActivityUtils = new ProgressActivityUtils(context, progressActivity);
        rvContainer.setRefreshEnabled(false);
        initRecyclerView(rvContainer, mMessageAdapter, this);
    }

    private void addHeaderView() {
        View header = LayoutInflater.from(context).inflate(R.layout.shareeverydayrefresh, null);
        mTitle = header.findViewById(R.id.shareeverydaydetail_title);
        mShortInfo = header.findViewById(R.id.shareeverydaydetail_companyanddate);
        mContent = header.findViewById(R.id.shareeverydaydetail_caontent);
        mWebView = header.findViewById(R.id.webview);


        mMessageAdapter.addHeaderView(header);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mShareEveryDayDetailPresenter = new ShareEveryDayDetailPresenter(context, this);
        mShareEveryDayDetailPresenter.getShareEveryDayDetailData();
    }

    @Override
    protected void initView() {
        mIsCollection = $(R.id.shareeverydaydetail_iscollection);
        mMessageLayout = $(R.id.shareeverydaydetail_messagelayout);
        mMessage = $(R.id.shareeverydaydetail_message);
        mMessageNum = $(R.id.shareeverydaydetail_messagenum);
        mShare = $(R.id.shareeverydaydetail_share);
        mWriteMessage = $(R.id.search_writemessage);

        rvContainer = $(R.id.rv_container);
        progressActivity = $(R.id.progressActivity);
        swipeContainer = $(R.id.swipe_container);


        mWriteMessage.setOnClickListener(this);
        mMessageLayout.setOnClickListener(this);
        mIsCollection.setOnClickListener(this);
        mShare.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (!BaseUtil.isLogin(context, application)) {
            return;
        }
        switch (v.getId()) {
            case R.id.shareeverydaydetail_messagelayout:
                //查看留言
                Goto.toMessageActivity(context, mData.news_id, "1");
                break;
            case R.id.search_writemessage:
                //留言
                //解决点击发送两次问题
                mWriteMessage.setEnabled(true);
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
                                        //发送留言
                                        if (AppConfig.ISlOGINED) {
                                            mShareEveryDayDetailPresenter.sendCommen(v.getText().toString().trim());
                                        } else {
                                            new AlertDialog.Builder(context).setTitle("提示！").setMessage("您尚未登录，请先登录。").setPositiveButton("登录", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Goto.toLogin(context);
                                                }
                                            }).setNegativeButton("取消", null).show();
                                        }

                                    }
                                    return false;
                                }
                            });
                        }
                        return false;
                    }
                });

                break;
            case R.id.shareeverydaydetail_share:
                //分享页面
                if (mData != null)
                    addShareMeun();
                break;
            case R.id.shareeverydaydetail_iscollection:
                //收藏
                Log.i("是否收藏", mData.is_collect + "");
                Log.i("参数ID", collectionId + "");
                if (mData.is_collect != 1) {
                    mAddCollctionPresenter.addCollection();
                } else {
                    mAddCollctionPresenter.detelCollection();
                }
                break;
            case R.id.sharemeun_qqfriend:
                //QQ好友
                doShare(SHARE_MEDIA.QQ);
                break;
            case R.id.sharemeun_qqzone:
                //QQ空间
                doShare(SHARE_MEDIA.QZONE);
                Toast.makeText(context, "QQ空间", Toast.LENGTH_SHORT).show();
                break;
            case R.id.sharemeun_wechatfriend:
                //微信好友
                doShare(SHARE_MEDIA.WEIXIN);
                Toast.makeText(context, "微信好友", Toast.LENGTH_SHORT).show();
                break;
            case R.id.sharemeun_moment:
                //朋友圈
                doShare(SHARE_MEDIA.WEIXIN_CIRCLE);
                Toast.makeText(context, "朋友圈", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    /**
     * 分享
     *
     * @param
     */
    private void doShare(SHARE_MEDIA media) {
        UMWeb web = new UMWeb(mData.news_share_url);
        web.setTitle(mData.news_title);//标题
        web.setDescription(mData.news_short_info);//描述
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
//            Toast.makeText(context, "分享成功", Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(context, "分享失败" + t.getMessage(), Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(context, "分享取消了", Toast.LENGTH_LONG).show();
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
    protected void onDestroy() {
        super.onDestroy();
        if (popupWindow != null) {
            popupWindow.dismiss();
        }
    }

    @Override
    public void showLoading() {
        hideProgress();
        hideSwipeRefreshLayout(swipeContainer);
        rvContainer.completeLoadMore();
    }

    @Override
    public void hideLoading() {
        hideProgress();
        hideSwipeRefreshLayout(swipeContainer);
        rvContainer.completeLoadMore();
    }

    @Override
    public String getNewsId() {
        return newId;
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
    public void getMessageSuccess(HomeAndFindHelpEachOtherDetailCommentInfo mInfo, int maxpage) {
        if (mInfo.list.size() == 0) {
            mList.clear();

        } else {
            progressActivityUtils.showContent();
            countpage++;
            if (maxpage == 1 || countpage > maxpage) {
                rvContainer.setLoadMoreEnabled(false);
            } else {
                rvContainer.setLoadMoreEnabled(true);
            }
            System.out.println("=========");
            mList.addAll(mInfo.list);
            mMessageAdapter.setData(mInfo.list);
        }
    }

    @Override
    public void sendMessageSuccess() {
//        onRefresh();
    }

    @Override
    public void sendMessageSuccess(CommentInfo info) {

    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getMessageType() {
        return "1";
    }

    @Override
    public String getMessageTypeID() {
        return mData.news_id + "";
    }

    @Override
    public String getMessageParentId() {
        return "0";
    }

    @Override
    public String getParentCommentId() {
        return parentCommentId;
    }

    @Override
    public void getShareEveryDayDetailSuccess(ShareEveryDayDetailListInfo info, int maxpage) {
        mData = info;
        collectionId = info.collect_id;
        mTitle.setText(info.news_title);
        mShortInfo.setText(info.news_add_date);

        mWebView.loadData(info.news_content, "text/html; charset=UTF-8", null);//这种写法可以正确解码
//        RichText.fromHtml(info.news_content).into(mContent);
        //判断是否收藏

        if (info.is_collect == 1) {
            isSuccess = true;
            mIsCollection.setBackground(getResources().getDrawable(R.drawable.new_find_collection));
        } else {
            isSuccess = false;
            mIsCollection.setBackground(getResources().getDrawable(R.drawable.new_find_uncollection));
        }


        //判断留言是否为0

        if ((info.comment_list.count) == 0) {
            mMessageNum.setVisibility(View.GONE);
        } else {
            mMessageNum.setVisibility(View.VISIBLE);
            mMessageNum.setText(info.comment_list.count + "");
        }
        mMessagePresenter.getMessageInfo();
    }

    @Override
    public void getJobBangDetailSuccess(JobBangDetailListInfo info, int maxpage) {
        //    public int raiders_is_buy ;
//
//        if (!info.raiders_price.equals("免费")) {// 未购买
//            pay_linearLayout.setVisibility(View.VISIBLE);
//            pay_button.setVisibility(View.VISIBLE);
//            pay_view.setVisibility(View.VISIBLE);
//        } else {
//            pay_linearLayout.setVisibility(View.GONE);
//            pay_button.setVisibility(View.GONE);
//            pay_view.setVisibility(View.GONE);
//        }
//        if (info.raiders_is_buy == 1) {
//            pay_linearLayout.setVisibility(View.GONE);
//            pay_button.setVisibility(View.GONE);
//            pay_view.setVisibility(View.GONE);
//        }


    }

    @Override
    public void getShareEveryDaySendCommenSuccess() {
        //提示留言成功
        com.example.framwork.utils.Toast.getInstance().showSuccessToast(context, "留言成功");
        //留言输入框清除内容
        mWriteMessage.setText("");
        mWriteMessage.setHint("写留言...");

    }

    @Override
    public String getAddCollctionType() {
        return addCollectionType;
    }

    @Override
    public String getAddClloectionTpyeId() {
        return mData.news_id + "";
    }

    @Override
    public String getAddClloectionId() {
        return collectionId + "";
    }

    @Override
    public void addCollectionSuccess(AddCollectionSuccessInfo mAddCollectionSuccessInfo) {
        mData.is_collect = 1;
        collectionId = mAddCollectionSuccessInfo.collect_id;

        if (!isSuccess) {
            mIsCollection.setBackground(getResources().getDrawable(R.drawable.new_find_collection));
            isSuccess = true;
        } else {
            mIsCollection.setBackground(getResources().getDrawable(R.drawable.new_find_uncollection));
            isSuccess = false;
        }
    }

    @Override
    public void detelCollectionSuccess() {
        mData.is_collect = 2;
        mIsCollection.setBackground(getResources().getDrawable(R.drawable.new_find_uncollection));
    }

    @Override
    public void onRefresh() {
        hideProgress();
        hideSwipeRefreshLayout(swipeContainer);
        rvContainer.completeLoadMore();
    }

    @Override
    public void onLoadMore() {
        hideProgress();
        hideSwipeRefreshLayout(swipeContainer);
        rvContainer.completeLoadMore();
    }

    /**
     * 获取数据
     */
    private void getServerData() {
        mShareEveryDayDetailPresenter.getShareEveryDayDetailData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, intent);
    }
}
