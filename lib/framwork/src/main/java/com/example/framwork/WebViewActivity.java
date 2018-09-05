package com.example.framwork;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.widget.Toast;

import com.example.framwork.baseapp.AppManager;
import com.example.framwork.widget.CustomWebView;
import com.example.framwork.widget.customtoolbar.CommonTitle;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by gaoy on 2017/1/21.
 * webview
 */

public class WebViewActivity extends Activity {
    private CommonTitle commonTitle;
    private CustomWebView webview;
    private String url;
    private String title;
    private int titleBg;
    private ValueCallback<Uri> mUploadMessage;
    public ValueCallback<Uri[]> uploadMessage;
    public static final int REQUEST_SELECT_FILE = 100;
    private final static int FILECHOOSER_RESULTCODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.web_view_ui);
        AppManager.getAppManager().addActivity(this);
        initView();
        initData();
    }


    protected void initView() {
        webview = (CustomWebView) findViewById(R.id.webview);
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
        commonTitle = (CommonTitle) findViewById(R.id.commonTitle);
        commonTitle.getLeftRes().setImageDrawable(getResources().getDrawable(R.drawable.new_back));
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
            Log.i("网址为",url);
            title = intent.getStringExtra("title");
            titleBg = intent.getIntExtra("title_bg", 0);
        }
        if (titleBg != 0)
            commonTitle.setBackgroundResource(titleBg);
        commonTitle.setCenterText(title);
        webview.addJavascriptInterface(new JsAction(),"action");
        webview.loadUrl(url);
        registerForContextMenu(webview);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

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


    private class JsAction {

        @JavascriptInterface //H5调用Android的该方法
        public void toast(final String jsonString) {
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(WebViewActivity.this);
                    builder.setTitle("H5调用Android显示对话框");
                    if (object != null) {
                        builder.setMessage(object.optString("func", ""));
                    }
                    builder.setNegativeButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            //在dialog消息的时候，将修改H5的内容
//                            reloadJs("alert", "iOS change javascript to message for H5");
                        }
                    });
                    dialog.show();
                }
            });
        }
    }

    /**
     * APP通过该方法来调用H5的方法
     * @param method 方法名
     * @param param 方法参数
     */

    private void reloadJs(String method, String param) {
        String url = String.format("javascript:%s(\"%s\")", method, param);
        webview.loadUrl(url);
    }
}
