package sinia.com.baihangeducation.supplement.web;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;


import com.example.framwork.baseapp.AppManager;

import com.example.framwork.widget.CustomWebView;
import com.example.framwork.widget.customtoolbar.CommonTitle;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import org.json.JSONException;
import org.json.JSONObject;

import sinia.com.baihangeducation.R;


/**
 * Created by gaoy on 2017/1/21.
 * webview
 */

public class ShareWebViewActivity extends Activity implements View.OnClickListener {
    private LinearLayout mLayout;
    private CommonTitle commonTitle;
    private CustomWebView webview;
    private String url;
    private String title;
    private int titleBg;
    private ValueCallback<Uri> mUploadMessage;
    public ValueCallback<Uri[]> uploadMessage;
    public static final int REQUEST_SELECT_FILE = 100;
    private final static int FILECHOOSER_RESULTCODE = 2;
    private View contentView;
    private PopupWindow popupWindow;

    private String shareUrl;
    private String shareTitle;
    private String shareImg;
    private String shareDescribe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(com.example.framwork.R.layout.web_view_ui);
        AppManager.getAppManager().addActivity(this);
        initView();
        initData();
    }


    protected void initView() {
        webview = (CustomWebView) findViewById(com.example.framwork.R.id.webview);
        mLayout = findViewById(R.id.web_view_ui);
        WebSettings settings = webview.getSettings();
        settings = webview.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setPluginState(WebSettings.PluginState.ON);
        settings.setUseWideViewPort(true);
        settings.setAllowFileAccess(true);
        settings.setSupportZoom(true);
        settings.setLoadWithOverviewMode(true);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        settings.setUseWideViewPort(true);
        commonTitle = (CommonTitle) findViewById(com.example.framwork.R.id.commonTitle);
        commonTitle.getLeftRes().setImageDrawable(getResources().getDrawable(com.example.framwork.R.drawable.new_back));
        commonTitle.getLeftRes().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            url = intent.getStringExtra("url");
            Log.i("网址为", url);
            title = intent.getStringExtra("title");
            titleBg = intent.getIntExtra("title_bg", 0);
        }
        if (titleBg != 0)
            commonTitle.setBackgroundResource(titleBg);
        commonTitle.setCenterText(title);
        webview.addJavascriptInterface(new JsAction(), "action");
        webview.loadUrl(url);
        registerForContextMenu(webview);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, intent);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (requestCode == REQUEST_SELECT_FILE) {
                if (uploadMessage == null)
                    return;
                uploadMessage.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(resultCode, intent));
                uploadMessage = null;
            }
        } else if (requestCode == FILECHOOSER_RESULTCODE) {
            if (null == mUploadMessage)
                return;
            // Use MainActivity.RESULT_OK if you're implementing WebView inside Fragment
            // Use RESULT_OK only if you're implementing WebView inside an Activity
            Uri result = intent == null || resultCode != RESULT_OK ? null : intent.getData();
            mUploadMessage.onReceiveValue(result);
            mUploadMessage = null;
        } else
            Toast.makeText(getBaseContext(), "Failed to Upload Image", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sharemeun_qqfriend:
                //QQ好友
                doShare(SHARE_MEDIA.QQ);
                break;
            case R.id.sharemeun_qqzone:
                //QQ空间
                doShare(SHARE_MEDIA.QZONE);
                break;
            case R.id.sharemeun_wechatfriend:
                //微信好友
                doShare(SHARE_MEDIA.WEIXIN);
                break;
            case R.id.sharemeun_moment:
                //朋友圈
                doShare(SHARE_MEDIA.WEIXIN_CIRCLE);
                break;
        }
    }

    /**
     * 分享
     *
     * @param
     */
    private void doShare(SHARE_MEDIA media) {
        UMWeb web = new UMWeb(shareUrl);
        web.setTitle(shareTitle);//标题
        web.setDescription(shareDescribe);
        web.setThumb(new UMImage(ShareWebViewActivity.this, shareImg));
        new ShareAction(ShareWebViewActivity.this)
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
            if (popupWindow != null) {
                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
            }

//            com.example.framwork.utils.Toast.getInstance().showSuccessToast(ShareWebViewActivity.this, "分享成功");
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            if (popupWindow != null) {
                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
            }
            com.example.framwork.utils.Toast.getInstance().showErrorToast(ShareWebViewActivity.this,"分享失败:"+t.getMessage());
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            if (popupWindow != null) {
                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
            }
            com.example.framwork.utils.Toast.getInstance().showErrorToast(ShareWebViewActivity.this,"取消了分享");
        }
    };

    /**
     * JS调用的方法
     */
    private class JsAction {
        @JavascriptInterface //H5调用Android的该方法
        public void share(final String jsonString) {
            //注意@JavascriptInterface 下的该方法和当前activity并不是同一个线程，所以增加要将H5的调用和回调到H5的代码都写到runOnUiThread。否则会抛出以下异常
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject object = null;
                    try {
                        object = new JSONObject(jsonString);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    if (object != null) {
                        String action = object.optString("func", "");
                        if (!TextUtils.isEmpty(action) && action.equals("share")) {
                            shareUrl = object.optString("share_url", "");
                            shareTitle = object.optString("title", "");
                            shareImg = object.optString("img_url", "");
                            shareDescribe = object.optString("describe", "");
                            addShareMeun();
                        }
                    }
                }
            });
        }
    }

    /**
     * APP通过该方法来调用H5的方法
     *
     * @param method 方法名
     * @param param  方法参数
     */

    private void reloadJs(String method, String param) {
        String url = String.format("javascript:%s(\"%s\")", method, param);
        webview.loadUrl(url);
    }

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
        popupWindow.showAtLocation(mLayout, Gravity.BOTTOM, 0, 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();
    }
}
