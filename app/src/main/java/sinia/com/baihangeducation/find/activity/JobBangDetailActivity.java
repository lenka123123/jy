package sinia.com.baihangeducation.find.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.net.http.SslError;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.example.framwork.utils.DensityUtil;
import com.example.framwork.utils.ProgressActivityUtils;
import com.example.framwork.widget.ProgressActivity;
import com.example.framwork.widget.superrecyclerview.recycleview.SuperRecyclerView;
import com.lzy.ninegrid.ImageInfo;
import com.mcxtzhang.swipemenulib.customview.BitmapUtil;
import com.mcxtzhang.swipemenulib.customview.GlideLoadUtils;
import com.mcxtzhang.swipemenulib.customview.ZZoomImageView;
import com.mcxtzhang.swipemenulib.utils.BitmapBigSave;
import com.mcxtzhang.swipemenulib.utils.BitmapSave;
import com.mcxtzhang.swipemenulib.utils.GlobalHandler;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMWeb;
import com.zzhoujay.richtext.RichText;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.reconsitution.tabs.pay.PayActivity;
import sinia.com.baihangeducation.release.adapter.PhotoShowDialog;
import sinia.com.baihangeducation.supplement.base.BaseActivity;
import sinia.com.baihangeducation.MyApplication;

import com.mcxtzhang.swipemenulib.info.JobBangDetailListInfo;
import com.mcxtzhang.swipemenulib.info.ShareEveryDayDetailListInfo;
import com.mcxtzhang.swipemenulib.info.bean.AddCollectionSuccessInfo;
import com.mcxtzhang.swipemenulib.info.bean.ShareEveryDayDetailCommentInfo;

import sinia.com.baihangeducation.find.presenter.JobBangDetailPresenter;
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

public class JobBangDetailActivity extends BaseActivity
        implements ShareEveryDayDetailView, AddCollctionView, MessageView,
        SuperRecyclerView.LoadingListener, SwipeRefreshLayout.OnRefreshListener {


    private String raderId;
    private JobBangDetailPresenter mJobBangDetailPresenter;//获取详情数据
    private AddOrDetelCollctionPresenter mAddCollctionPresenter;//添加收藏
    private int collectionId;
    private String addCollectionType;
    private JobBangDetailListInfo mData;
    private Intent intent;
    private View contentView;
    private PopupWindow popupWindow;
    private List<ShareEveryDayDetailCommentInfo> mCommentList;
    private int countpage = 1;
    private int itemnum = 20;
    private String type;
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
    private WebView webview;

    private MessagePresenter mMessagePresenter;             //留言
    private SuperRecyclerView rvContainer;
    private ProgressActivity progressActivity;
    private SwipeRefreshLayout swipeContainer;
    private ProgressActivityUtils progressActivityUtils;
    private MessageAdapter mMessageAdapter;
    private List<CommentInfo> mList;
    private boolean isLoadMore = false;
    private LinearLayout pay_linearLayout;
    private Button pay_button;
    private View pay_view;
    private String typename;

    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 200;
    private SubsamplingScaleImageView img;
    private Bitmap picture;

    private String bitmapBigSavePath;


    /**
     * 当系统版本大于5.0时 开启enableSlowWholeDocumentDraw 获取整个html文档内容
     */
    private void checkSdkVersion() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            WebView.enableSlowWholeDocumentDraw();
        }
    }

    /**
     * 当build target为23时，需要动态申请权限
     */
    private void requestPermission() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                //申请WRITE_EXTERNAL_STORAGE权限
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
            }

        }
    }

    @Override
    public int initLayoutResID() {
        //检查版本
        checkSdkVersion();
        return R.layout.shareeverydaydetailhead;
    }


    @Override
    protected void initView() {
        //申请权限
        requestPermission();
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

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);

//        mHandler = GlobalHandler.getInstance();
//        mHandler.setHandleMsgListener(this);
    }

    @Override
    protected void initData() {
        mCommentList = new ArrayList<>();
        dialog = new MessageDialog(context);

        mList = new ArrayList<>();


        intent = getIntent();
        //获取raiderid 用于获取详情
        raderId = intent.getStringExtra("RAIDERID");
        //获取收藏ID
        addCollectionType = intent.getStringExtra("collectionID");
        //获取留言ID
        type = intent.getStringExtra("messageID");
        typename = intent.getStringExtra("typename");

        mCommonTitle.setBackgroundColor(Color.WHITE);
        if (typename.equals("")) {
            mCommonTitle.setCenterText(R.string.jobbangclassdetail);
        } else {
            mCommonTitle.setCenterText(typename + "详情");
        }


        //获取详情presenter
        mJobBangDetailPresenter = new JobBangDetailPresenter(context, this);
        //获取详情
        mJobBangDetailPresenter.getJobBangDetailData();
        //添加或删除收藏presenter
        mAddCollctionPresenter = new AddOrDetelCollctionPresenter(context, this);
        //留言presenter
        mMessagePresenter = new MessagePresenter(context, this);

        mMessageAdapter = new MessageAdapter(context, mList);
        addHeaderView();

        initSwipeLayout(swipeContainer, this);
        showSwipeRefreshLayout(swipeContainer);
        progressActivityUtils = new ProgressActivityUtils(context, progressActivity);
        initRecyclerView(rvContainer, mMessageAdapter, this);


    }

    @Override
    protected void onResume() {
        super.onResume();
        mJobBangDetailPresenter = new JobBangDetailPresenter(context, this);
        mJobBangDetailPresenter.getJobBangDetailData();

    }

    @Override
    protected void onRestart() {
        super.onRestart();

        isLoadMore = false;
        countpage = 1;
        getServerData();

    }

    private void addHeaderView() {
        View header = LayoutInflater.from(context).inflate(R.layout.shareeverydayrefresh, null);
        mTitle = header.findViewById(R.id.shareeverydaydetail_title);
        mShortInfo = header.findViewById(R.id.shareeverydaydetail_companyanddate);
        mContent = header.findViewById(R.id.shareeverydaydetail_caontent);
        webview = header.findViewById(R.id.webview);
        img = header.findViewById(R.id.img);
        img.setVisibility(View.GONE);

        WebSettings mWebSettings = webview.getSettings();
        // 设置是否可缩放 仅支持双击缩放，不支持触摸缩放
        mWebSettings.setSupportZoom(true);
        //设置自适应屏幕，两者合用
        mWebSettings.setUseWideViewPort(true);//将图片调整到适合webview的大小
        mWebSettings.setLoadWithOverviewMode(true);// 缩放至屏幕的大小
        //设置编码格式
        mWebSettings.setDefaultTextEncodingName("utf-8");
        //支持自动加载图片
        mWebSettings.setLoadsImagesAutomatically(true);
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口

//        int fontSize = (int) getResources().getDimension(R.dimen.y60);
//        mWebSettings.setDefaultFontSize(fontSize);//修改webvew字体大小

        mWebSettings.setTextSize(WebSettings.TextSize.LARGEST);

        //调用JS方法.安卓版本大于17,加上注解 @JavascriptInterface
        mWebSettings.setJavaScriptEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mWebSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webview.setBackgroundColor(0); // 设置背景色
        webview.setWebViewClient(new myWebviewClient());

        webview.addJavascriptInterface(new JavascriptInterface(), "imageListener");


        pay_linearLayout = header.findViewById(R.id.pay_linearLayout);
        pay_button = header.findViewById(R.id.goto_pay);
        pay_view = header.findViewById(R.id.pay_view);

        pay_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(JobBangDetailActivity.this, PayActivity.class);
                if (typename != null && !typename.equals(""))
                    intent.putExtra("type", typename);
                intent.putExtra("title", mData.raiders_title);
                intent.putExtra("price", mData.raiders_price);
                intent.putExtra("raiders_id", mData.raiders_id + "");
                startActivity(intent);
            }
        });
        mMessageAdapter.addHeaderView(header);
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (!AppConfig.ISlOGINED) {
            return;
        }
        switch (v.getId()) {
            case R.id.shareeverydaydetail_messagelayout:
                //查看留言
                Goto.toMessageActivity(context, mData.raiders_id, type);
                break;
            case R.id.search_writemessage:
                //留言
                //解决点击发送两次问题
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
                                        mJobBangDetailPresenter.sendCommen(v.getText().toString().trim());
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
//                Toast.makeText(context, "QQ空间", Toast.LENGTH_SHORT).show();
                break;
            case R.id.sharemeun_wechatfriend:
                //微信好友
                doShare(SHARE_MEDIA.WEIXIN);
//                Toast.makeText(context, "微信好友", Toast.LENGTH_SHORT).show();
                break;
            case R.id.sharemeun_moment:
                //朋友圈
                doShare(SHARE_MEDIA.WEIXIN_CIRCLE);
//                Toast.makeText(context, "朋友圈", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    /**
     * 分享
     *
     * @param
     */
    private void doShare(SHARE_MEDIA media) {
        UMWeb web = new UMWeb(mData.raiders_share_url);
        web.setTitle(mData.raiders_title);//标题
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
    public String getNewsId() {
        return raderId;
    }

    @Override
    public String getMessageType() {
        return type;
    }

    @Override
    public String getMessageTypeID() {
        return mData.raiders_id + "";
    }

    @Override
    public String getMessageParentId() {
        return "0";
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
            CommentInfo info = new CommentInfo();
            mList.add(info);
            mMessageAdapter.notifyDataSetChanged();
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
            mList.addAll(mInfo.list);
            mMessageAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void sendMessageSuccess() {

    }

    @Override
    public void sendMessageSuccess(CommentInfo info) {

    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getParentCommentId() {
        return parentCommentId;
    }

    @Override
    public void getShareEveryDayDetailSuccess(ShareEveryDayDetailListInfo info, int maxpage) {


    }

    private boolean isSuccess = true;

    @Override
    public void getJobBangDetailSuccess(JobBangDetailListInfo info, int maxpage) {
        //  raiders_is_buy   0：未购买 1：已购买 )
        mData = info;
        collectionId = info.collect_id;
        mTitle.setText(info.raiders_title);
        mShortInfo.setText(info.raiders_add_date);


        if (!info.raiders_price.equals("免费")) {// 未购买

            if (info.raiders_is_buy == 1) {
                pay_linearLayout.setVisibility(View.GONE);
                pay_button.setVisibility(View.GONE);
                pay_view.setVisibility(View.GONE);
            } else {
                pay_linearLayout.setVisibility(View.VISIBLE);
                pay_button.setVisibility(View.VISIBLE);
                pay_view.setVisibility(View.VISIBLE);
            }
        } else {
            pay_linearLayout.setVisibility(View.GONE);
            pay_button.setVisibility(View.GONE);
            pay_view.setVisibility(View.GONE);
        }


        if (!info.raiders_content.isEmpty()) {
            webview.loadDataWithBaseURL(null, getFormatHtml(info.raiders_content), "text/html", "utf-8", null);
        }
//            webview.loadDataWithBaseURL(null, info.raiders_content, "text/html", "utf-8", null);
//            webview.loadData(info.raiders_content, "text/html; charset=UTF-8", null);//这种写法可以正确解码


//        webview.setDrawingCacheEnabled(true);
//        webview.buildDrawingCache();  //启用DrawingCache并创建位图
////        Bitmap bitmap = Bitmap.createBitmap(webview.getDrawingCache()); //创建一个DrawingCache的拷贝，因为DrawingCache得到的位图在禁用后会被回收
//        webview.setDrawingCacheEnabled(false);  //禁用DrawingCahce否则会影响性能
//        webview.destroyDrawingCache();


        // RichText.from(info.raiders_content).into(mContent);
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
    public void getShareEveryDaySendCommenSuccess() {
        com.example.framwork.utils.Toast.getInstance().showSuccessToast(context, "留言成功");
        mWriteMessage.setText("");
        mWriteMessage.setHint("写留言...");

        //刷新
        onRefresh();
    }


    private void clickImage() {
        // 这段js函数的功能就是，遍历所有的img几点，并添加onclick函数，函数的功能是在图片点击的时候调用本地java接口并传递url过去
        //if(isShow && objs[i].width>80)是将小图片过滤点,不显示小图片,如果没有限制,可去掉
        webview.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName(\"img\"); " +
                "var imgUrl = \"\";" +
                "var filter = [\"img//EventHead.png\",\"img//kong.png\",\"hdtz//button.png\"];" +
                "var isShow = true;" +
                "for(var i=0;i<objs.length;i++){" +
                "for(var j=0;j<filter.length;j++){" +
                "if(objs[i].src.indexOf(filter[j])>=0) {" +
                "isShow = false; break;}}" +
                "if(isShow && objs[i].width>80){" +
                "imgUrl += objs[i].src + ',';isShow = true;" +
                "    objs[i].onclick=function()  " +
                "    {  "
                + "        window.imageListener.openImage(imgUrl,this.src);" +
                "    }" +
                "}" +
                "}" +
                "})()"
        );
    }

    private class myWebviewClient extends WebViewClient {

        @SuppressLint("SetJavaScriptEnabled")
        @Override
        public void onPageFinished(WebView view, String url) {
            //网页加载完成 走JS代码
            clickImage();
            super.onPageFinished(view, url);
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();
            super.onReceivedSslError(view, handler, error);
        }

//        @Override
//        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            //该方法在Build.VERSION_CODES.LOLLIPOP以前有效，从Build.VERSION_CODES.LOLLIPOP起，建议使用shouldOverrideUrlLoading(WebView, WebResourceRequest)} instead
//            //返回false，意味着请求过程里，不管有多少次的跳转请求（即新的请求地址），均交给webView自己处理，这也是此方法的默认处理
//            //返回true，说明你自己想根据url，做新的跳转，比如在判断url符合条件的情况下，我想让webView加载http://ask.csdn.net/questions/178242
//
//            return true;
//        }
//
//        @Override
//        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//            //返回false，意味着请求过程里，不管有多少次的跳转请求（即新的请求地址），均交给webView自己处理，这也是此方法的默认处理
//            //返回true，说明你自己想根据url，做新的跳转，比如在判断url符合条件的情况下，我想让webView加载http://ask.csdn.net/questions/178242
//
//            return true;
//        }

    }

    int position = 0;

    public class JavascriptInterface {
        @android.webkit.JavascriptInterface
        public void openImage(String imageUrl, String img) {

            String[] imgs = imageUrl.split(",");
            ArrayList<String> imgUrlList = new ArrayList<>();
            for (String s : imgs) {
                System.out.println("===========img==" + s);
                imgUrlList.add(s);
            }
            for (int i = 0; i < imgUrlList.size(); i++) {
                if (img.equals(imgUrlList.get(i))) {
                    position = i;
                }
            }

            final ArrayList<ImageInfo> imageInfo = new ArrayList<>();

            for (int i = 0; i < imgUrlList.size(); i++) {
                ImageInfo info = new ImageInfo();
                info.setThumbnailUrl(imgUrlList.get(i));
                info.setBigImageUrl(imgUrlList.get(i));
                imageInfo.add(info);
            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    PhotoShowDialog photoShowDialog = new PhotoShowDialog(JobBangDetailActivity.this, imageInfo, position);
                    photoShowDialog.show();
                }
            });

//            Intent intent = new Intent(JobBangDetailActivity.this, ImageActivity.class);
//            intent.putExtra("number", position);
//            intent.putExtra("list", (Serializable) imgUrlList);
//            JobBangDetailActivity.this.startActivity(intent);
        }
    }


    /**
     * 获得快照
     */
    private void getSnapshot() {

        float scale = webview.getScale();
        int webViewHeight = (int) (webview.getContentHeight() * scale + 0.5);

        int itemADWidth = DensityUtil.getScreenWidth(context);

        Bitmap bitmap = Bitmap.createBitmap(itemADWidth, webViewHeight, Bitmap.Config.ALPHA_8);

        Canvas canvas = new Canvas(bitmap);
        webview.draw(canvas);

        webview.setVisibility(View.GONE);
        img.setVisibility(View.VISIBLE);
//        img.setImageBitmap(bitmap);

    }


    public void getFullWebViewSnapshot() {
        //重新调用WebView的measure方法测量实际View的大小（将测量模式设置为UNSPECIFIED模式也就是需要多大就可以获得多大的空间）
        webview.measure(View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        //调用layout方法设置布局（使用新测量的大小）
        webview.layout(0, 0, webview.getMeasuredWidth(), webview.getMeasuredHeight());
        //开启WebView的缓存(当开启这个开关后下次调用getDrawingCache()方法的时候会把view绘制到一个bitmap上)
        webview.setDrawingCacheEnabled(true);
        //强制绘制缓存（必须在setDrawingCacheEnabled(true)之后才能调用，否者需要手动调用destroyDrawingCache()清楚缓存）
        webview.buildDrawingCache();
        //根据测量结果创建一个大小一样的bitmap

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;


        picture = Bitmap.createBitmap(webview.getMeasuredWidth(),
                webview.getMeasuredHeight(), Bitmap.Config.RGB_565);

        picture = resizeBitmap(picture, webview.getMeasuredWidth() * 3 / 4, webview.getMeasuredHeight() * 3 / 4);


        //已picture为背景创建一个画布
        Canvas canvas = new Canvas(picture);  // 画布的宽高和 WebView 的网页保持一致
        Paint paint = new Paint();
        //设置画笔的定点位置，也就是左上角
        canvas.drawBitmap(picture, 0, webview.getMeasuredHeight(), paint);
        //将webview绘制在刚才创建的画板上
        webview.draw(canvas);

        //将bitmap保存到SD卡
        //FileTools.saveBitmap(picture, savePath);

        bitmapBigSavePath = BitmapBigSave.saveBitmap(context, picture);

//        mHandler.sendEmptyMessageDelayed(1, 3000);


//        img.setImageBitmap(picture);
//        picture.recycle();
//        picture = null;

    }


    public void showBitmap() {
        BitmapFactory.Options bfOptions = new BitmapFactory.Options();
        bfOptions.inDither = false;//使图片不抖动。不是很懂
        bfOptions.inPurgeable = true;//使得内存可以被回收
        bfOptions.inTempStorage = new byte[12 * 1024]; //临时存储

        File file = new File(bitmapBigSavePath);//path:图片的绝对地址
        FileInputStream fs = null;
        try {
            fs = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Bitmap bmp = null;
        if (fs != null) {
            try {
                bmp = BitmapFactory.decodeFileDescriptor(fs.getFD(), null, bfOptions); //这样莫非就让bmp到了临时存储的位置？

                webview.setVisibility(View.GONE);
                img.setVisibility(View.VISIBLE);
                img.setImage(ImageSource.bitmap(bmp));

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100) {
            baos.reset();
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);
            options -= 10;
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);
        return bitmap;
    }

    public Bitmap resizeBitmap(Bitmap bitmap, int w, int h) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        float scaleWidth = ((float) w) / width;
        float scaleHeight = ((float) h) / height;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);

        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width,
                height, matrix, true);
        return resizedBitmap;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (popupWindow != null) {
            popupWindow.dismiss();
        }
        if (picture != null) {
            picture.recycle();
            picture = null;
        }

    }

    @Override
    public String getAddCollctionType() {
        return addCollectionType;
    }

    @Override
    public String getAddClloectionTpyeId() {
        return mData.raiders_id + "";
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
//        mShareEveryDayDetailPresenter = new ShareEveryDayDetailPresenter(context, this);
//        mShareEveryDayDetailPresenter.getShareEveryDayDetailData();
        mData.is_collect = 2;
        mIsCollection.setBackground(getResources().getDrawable(R.drawable.new_find_uncollection));
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
        mJobBangDetailPresenter.getJobBangDetailData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, intent);
    }


    public void handleMsg(Message msg) {
        Toast.makeText(context, msg.what + " 88", Toast.LENGTH_SHORT).show();
        switch (msg.what) {
            case 0:
                getFullWebViewSnapshot();
                Toast.makeText(context, "0", Toast.LENGTH_SHORT).show();
                break;
            case 1:
                showBitmap();
                Toast.makeText(context, "1", Toast.LENGTH_SHORT).show();
                break;
        }
    }


    private String getFormatHtml(String content) {
        if (!content.contains("<img")) { //如果没有img图像标签，可以不做任何处理
            return content;
        }
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append("<html>");
        strBuilder.append("<head>");
        strBuilder.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />");
        strBuilder.append("<title>无标题文档</title>");
        strBuilder.append("<script type=\"text/javascript\">");
        strBuilder.append("function aaa() {");
        strBuilder.append("var imgTags = document.getElementsByTagName(\"img\");");

        strBuilder.append("var len = imgTags.length;");
        strBuilder.append("for(var i=0;i<len;i++) {");
        strBuilder.append("imgTags.item(i).onclick = function() {");
        strBuilder.append("	window.open(this.src,null,null,null);");
        strBuilder.append("};");
        strBuilder.append("}");

        strBuilder.append("}");
        strBuilder.append("</script>");
        strBuilder.append("<style type=\"text/css\">");
        strBuilder.append("img{ width:100%; height:auto}");
        strBuilder.append("div{ width:auto; height:auto;}");
        strBuilder.append("</style>");
        strBuilder.append("</head>");

        strBuilder.append("<body onload=\"aaa();\">");

        strBuilder.append("<div>");
        strBuilder.append(content.replaceAll("style=", ""));  //此处为去掉原始属性。如果想去掉指定标签的style属性，此处需要特殊处理。
        strBuilder.append("</div>");
        strBuilder.append("</body>");
        strBuilder.append("</html>");
        return strBuilder.toString();
    }

}
