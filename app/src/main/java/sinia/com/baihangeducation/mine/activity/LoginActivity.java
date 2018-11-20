package sinia.com.baihangeducation.mine.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.example.framwork.baseapp.BaseAppConfig;
import com.example.framwork.utils.CommonUtil;
import com.example.framwork.utils.SpCommonUtils;
import com.example.framwork.utils.Toast;
import com.githang.statusbar.StatusBarCompat;
import com.mcxtzhang.swipemenulib.utils.Constants;
import com.mcxtzhang.swipemenulib.utils.TimeUtils;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.yanzhenjie.nohttp.Logger;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import cn.jpush.im.android.api.event.MessageEvent;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.MainActivity;
import sinia.com.baihangeducation.R;

import com.mcxtzhang.swipemenulib.info.IsCompleteInfo;
import com.mcxtzhang.swipemenulib.info.bean.ThirdLoginInfo;

import org.greenrobot.eventbus.EventBus;

import sinia.com.baihangeducation.club.club.interfaces.GetRequestListener;
import sinia.com.baihangeducation.mine.presenter.LoginPresenter;
import sinia.com.baihangeducation.mine.presenter.ThirdLoginPresenter;
import sinia.com.baihangeducation.mine.view.ILoginView;
import sinia.com.baihangeducation.mine.view.IThirdLoginView;
import sinia.com.baihangeducation.receiver.JPushDialogActivity;
import sinia.com.baihangeducation.supplement.base.Goto;
import sinia.com.baihangeducation.supplement.tool.OkHttpUtils;
import sinia.com.baihangeducation.supplement.tool.WxShareAndLoginUtils;


public class LoginActivity extends AppCompatActivity implements ILoginView, IThirdLoginView, View.OnClickListener, GetRequestListener, IWXAPIEventHandler {
    private LoginPresenter mLoginPresenter;
    private UMShareAPI umShareAPI = null;
    public boolean isCheck = true;
    private EditText mPhoneNum;         //用户名
    private EditText mPassword;         //密码
    private TextView mLogin;            //登录
    private TextView mRegister;         //注册
    private TextView mForgetPassword;   //忘记密码
    private TextView mLogin4WeChat;     //微信登录
    private TextView mLogin4QQ;         //QQ登录
    private Button mDeletePhoneName;    //清空电话号码
    private Button mIsShowPassword;     //是否显示密码

    private String accessToken = "";
    private String uuid = "";
    private int third_type = 0;
    private boolean mIsShowPwdFlag = false;

    private ThirdLoginInfo thirdLoginInfo = new ThirdLoginInfo();
    private ThirdLoginPresenter thirdLoginPresenter;

    //极光推送，绑定用户时候的参数
    private String userType;
    private Set<String> set;
    private Activity context;
    private TextView mUserAgreement;
    private Button testweixin;
    private IWXAPI api;
    private String weichatCode = "";
    private String unionid = "";
    private TextView phone_logo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_login);

        Intent intent = getIntent();
        unionid = intent.getStringExtra("type");


        AppConfig.WEICHATCODE = "";
        context = this;
        initView();
        initData();
        AppConfig.USERID = "USERID";
        AppConfig.TOKEN = "USERID";
        SpCommonUtils.put(LoginActivity.this, AppConfig.USERTOKEN, "USERID");
        SpCommonUtils.put(LoginActivity.this, AppConfig.FINALUSERID, "USERID");
        weichatCode = (String) SpCommonUtils.get(LoginActivity.this, AppConfig.WEICHAT_CODE, "");
        api = WXAPIFactory.createWXAPI(this, BaseAppConfig.WX_APP_ID);
        /*注册微信广播*/
//        IntentFilter filter = new IntentFilter(WXEntryActivity.ACTION_GETWX);
//        LocalBroadcastManager.getInstance(this).registerReceiver(wxReceiver, filter);
    }

    @Override
    protected void onResume() {
        super.onResume();

//        thirdLoginPresenter.bindWeixinLogin("oZ7F3wyMgU-5COwsUTqbzR_7Nzkg");
        if (thirdLoginPresenter != null && !AppConfig.WEICHATCODE.equals(""))
            thirdLoginPresenter.bindWeixinLogin(AppConfig.WEICHATCODE);
        AppConfig.WEICHATCODE = "";
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        if (thirdLoginPresenter != null && !AppConfig.WEICHATCODE.equals(""))
            thirdLoginPresenter.bindWeixinLogin(AppConfig.WEICHATCODE);
        AppConfig.WEICHATCODE = "";
        EventBus.getDefault().post("Main");
    }

    protected void initData() {
        Intent intent = new Intent();
        //设置广播的名字（设置Action）
        intent.setAction("myBroadCastAction");
        sendBroadcast(intent);

        String name = (String) SpCommonUtils.get(context, AppConfig.USERPHOTO, "");
        String pwd = (String) SpCommonUtils.get(context, AppConfig.USERPWD, "");
        mLoginPresenter = new LoginPresenter(context, this);
        thirdLoginPresenter = new ThirdLoginPresenter(this, this);

        if (name.equals("") || pwd.equals("")) {

        } else {
            mPhoneNum.setText(name);
//            mPassword.setText(pwd);
            //   mLoginPresenter.login(this);
        }

        System.out.println("微信返回==todo 用户同意22" + AppConfig.WEICHATCODE);
        if (unionid != null && !unionid.equals("")) {
            System.out.println("微信返回==todo 用户同意223332323" + unionid);
            thirdLoginPresenter.bindWeixinLogin(unionid);
        }

    }


    /**
     * 初始化控件
     */

    protected void initView() {
        isCheck = true;
        testweixin = (Button) findViewById(R.id.testweixin);
        mPhoneNum = (EditText) findViewById(R.id.edt_phone_num);
        mPassword = (EditText) findViewById(R.id.edt_password);
        mLogin = (TextView) findViewById(R.id.tv_login);
        phone_logo = (TextView) findViewById(R.id.phone_logo);
        mRegister = (TextView) findViewById(R.id.tv_register);
        mForgetPassword = (TextView) findViewById(R.id.tv_forget_password);
        mLogin4WeChat = (TextView) findViewById(R.id.tv_login_weixin);
        mLogin4QQ = (TextView) findViewById(R.id.tv_login_qq);
        mDeletePhoneName = findViewById(R.id.bt_login_delete);
        mIsShowPassword = findViewById(R.id.bt_login_isshowpwd);

        mUserAgreement = findViewById(R.id.tv_register_protocol);


        mUserAgreement.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);

        findViewById(R.id.back).setOnClickListener(this);
        mPhoneNum.setOnClickListener(this);
        mPassword.setOnClickListener(this);
        mLogin.setOnClickListener(this);
        mRegister.setOnClickListener(this);
        mForgetPassword.setOnClickListener(this);
        mLogin4WeChat.setOnClickListener(this);
        mLogin4QQ.setOnClickListener(this);
        mDeletePhoneName.setOnClickListener(this);
        mIsShowPassword.setOnClickListener(this);
        mUserAgreement.setOnClickListener(this);
        testweixin.setOnClickListener(this);
        phone_logo.setOnClickListener(this);

    }

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.testweixin:
                //验证码登录
//                Goto.toVerificationCodeLogin(LoginActivity.this);
                System.out.println("weichatCode===" + weichatCode);
                WxShareAndLoginUtils.WxLogin(LoginActivity.this);

                break;

            case R.id.phone_logo:
                Goto.toForgetPassword(context,"login");
                break;
            case R.id.back:
                Goto.toMainActivity(context);
                LoginActivity.this.finish();
                break;
            case R.id.tv_login:
                if (TimeUtils.isLetterDigit(getPassword())) {
                    showLoading();
                    mLogin.setClickable(false);
                    if (isCheck) {
                        mLoginPresenter.login(this);
                    }
                    isCheck = false;
                } else {
                    isCheck = true;
                    mLogin.setClickable(true);
                    Toast.getInstance().showErrorToast(context, "请输入6-12位数字或字母");
                }

                break;
            case R.id.tv_login_weixin:
                QQorWeChatLogin(SHARE_MEDIA.WEIXIN);
                break;
            case R.id.tv_login_qq:
//                QQorWeChatLogin(SHARE_MEDIA.QQ);
                break;
            case R.id.tv_register:
                Goto.toRegister(context);
                break;
            case R.id.tv_forget_password:
                //忘记密码
                Goto.toForgetPassword(context,"");
                break;
            case R.id.bt_login_delete:
                mPhoneNum.setText("");
                mPhoneNum.setHint(R.string.input_phone_num);
                break;
            case R.id.bt_login_isshowpwd:
                if (mIsShowPwdFlag)
                    mIsShowPwdFlag = false;
                else
                    mIsShowPwdFlag = true;
                showPwdOrNot();
                break;
            case R.id.tv_register_protocol:
                //用户协议
                Goto.toUserRuleActivity(context);
                break;
        }
    }

    /**
     * 判断是否显示密码
     */
    private void showPwdOrNot() {
        if (mIsShowPwdFlag) {
            mIsShowPassword.setBackgroundResource(R.drawable.new_login_showpwd);
            mPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

        } else {
            mIsShowPassword.setBackgroundResource(R.drawable.new_login_unshowpwd);
            mPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }

    }

    /**
     * 微信或QQ登录
     */
    private void QQorWeChatLogin(SHARE_MEDIA type) {
        umShareAPI = UMShareAPI.get(this);
        umShareAPI.doOauthVerify(this, type, umAuthListener);
        //  umShareAPI.getPlatformInfo(mContext, type, umAuthListener);
    }

    UMAuthListener umAuthListener = new UMAuthListener() {
        /**
         * @desc 授权开始的回调
         * @param platform 平台名称
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @desc 授权成功的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param data 用户资料返回
         */
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            Log.i("第三方登录", data.toString());
//            {ret=0, unionid=, pay_token=ED7669916FA04A53E41F60B5363771F5, page_type=, openid=208C17991B9446A6FA32C1371401191C, accessToken=5FF087981C134D3B7F9A0072EEC1469E, access_token=5FF087981C134D3B7F9A0072EEC1469E, uid=208C17991B9446A6FA32C1371401191C, sendinstall=, as=LTTiT2YjagGdp0bl, pfkey=352d67886f9ad449c9ada9140908205d, pf=desktop_m_qq-10000144-android-2002-, appid=, auth_time=, expires_in=7776000, aid=1105546121}

            accessToken = data.get("access_token").toString();
            if (platform.equals(SHARE_MEDIA.WEIXIN)) {
                third_type = 2;
                uuid = data.get("openid").toString();
            } else if (platform.equals(SHARE_MEDIA.QQ)) {
                third_type = 1;
                uuid = data.get("uid").toString();
            }

            thirdLoginInfo.third_type = third_type;
            thirdLoginInfo.third_token = accessToken;
            thirdLoginInfo.uuid = uuid;
            thirdLoginPresenter.bindLogin(thirdLoginInfo, platform, umShareAPI);

        }

        /**
         * @desc 授权失败的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
        }

        /**
         * @desc 授权取消的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         */
        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
        }
    };

    /**
     * 登录成功
     */
    @Override
    public void showLoginSuccress() {
        Goto.toMainActivity(context);
        initJPushTag();

        finish();

    }

    /**
     * 设置推送别名和tag
     */

    private void initJPushTag() {
        userType = CommonUtil.getAndroidId(this);
        Log.i("设备ID", userType + "        MainActivity");
        set = new HashSet<>();
        set.clear();
        set.add(userType);
        pushTag();
    }

    private void pushTag() {
        JPushInterface.setAliasAndTags(this, userType, set,
                new TagAliasCallback() {

                    @Override
                    public void gotResult(int result, String arg1,
                                          Set<String> arg2) {
                        if (result == 6002)
                            //   pushTag();
                            Logger.d("JPushInterface.setTags--------" + userType + "-------------result=" + result);
                    }
                });
    }

    @Override
    public String getUserType() {
        return null;
    }

    @Override
    public String getVcode() {
        return null;
    }

    /**
     * 获取手机号码
     *
     * @return
     */
    @Override
    public String getPhoneNum() {
        return mPhoneNum.getText().toString().trim();
    }

    /**
     * 获取密码
     *
     * @return
     */
    @Override
    public String getPassword() {
        return mPassword.getText().toString().trim();
    }

    @Override
    public String getLocationLng() {
        return "";
    }

    @Override
    public String getLocationLat() {
        return "";
    }

    @Override
    public void getCompleteSucess(IsCompleteInfo mIsCompleteInfo) {
//        if (mIsCompleteInfo.is_complete==1){
//            finish();
//        }else {
//
//        }
    }

    @Override
    public void showLoading() {
//        showProgress();
    }

    @Override
    public void hideLoading() {
//        hideProgress();
    }

    @Override
    public void showSuccess() {

    }

    UMAuthListener authListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
            Log.i("第三方登录", map.toString());
        }

        @Override
        public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {

        }

        @Override
        public void onCancel(SHARE_MEDIA share_media, int i) {

        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("生命周期", "onStart");
    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.i("生命周期", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("生命周期", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        hideProgress();
        Log.i("生命周期", "onDestroy");
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            Goto.toMainActivity(context);
            LoginActivity.this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void setRequestSuccess(String msg) {
        hideLoading();
        mLogin.setClickable(false);
        isCheck = false;
    }

    @Override
    public void setRequestFail() {
        hideLoading();
        isCheck = true;
        mLogin.setClickable(true);
    }

    @Override
    public void onReq(BaseReq baseReq) {
        System.out.println("微信返回1==" + baseReq.getType());
        System.out.println("微信返回1==" + baseReq.openId);


    }

    @Override
    public void onResp(BaseResp baseResp) {
        System.out.println("微信返回==" + baseResp.errStr);
        System.out.println("微信返回==" + baseResp.errCode);
        System.out.println("微信返回==" + baseResp.getType());
        System.out.println("微信返回==" + baseResp.getType());
    }


    public void getAccessTokenaaa(String token) {
        String url = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=" + Constants.APP_ID + "&grant_type=refresh_token&refresh_token=" + token;
//        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + Constants.APP_ID + "&secret=" + Constants.APP_SECRET + "&code=" + code + "&grant_type=authorization_code";
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder().url(url).get()
                //默认就是GET请求，可以不写
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("微信返回== getAccessToken " + call.toString());
                Toast.getInstance().showErrorToast(context, e.getMessage().toString());
//                Log.d(TAG, "onFailure: ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println("微信返回== 刷新token getAccessTokenResponse" + response.body().string());
                System.out.println("微信返回== 刷新tokengetAccessTokenResponsed" + response.message());

            }
        });

    }


    /*创建用于接收数据的广播*/
    private BroadcastReceiver wxReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
//            if (intent.getAction().equals(WXEntryActivity.ACTION_GETWX)) {
//                String wx_access_token = intent.getStringExtra(WXEntryActivity.access_token);
//                String wx_openid = intent.getStringExtra(WXEntryActivity.openid);
//                thirdLoginPresenter.bindWeixinLogin(wx_openid);
//            }
        }
    };


    //        https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE&connect_redirect=1#wechat_redirect

//
//    private void getAccessTokenFirst(   ) {
//        String http = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" +
//                 Constants.APP_ID + "&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE&connect_redirect=1";
//
//        String http = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
//                + Constants.APP_ID + "&secret=" + Constants.APP_SECRET + "&code=" +
//                code + "&grant_type=authorization_code";
//        OkHttpUtils.ResultCallback<String> resultCallback = new OkHttpUtils.ResultCallback<String>() {
//            @Override
//            public void onSuccess(String response) {
//                System.out.println("===response==" + response);
//                JSONObject job = JSONObject.parseObject(response);
//                if (job.get("unionid") != null) {
//                    System.out.println(job.get("unionid"));
//                    SpCommonUtils.put(LoginActivity.this, AppConfig.WEICHAT_CODE, job.get("unionid").toString());
//                    thirdLoginPresenter.bindWeixinLogin(job.get("unionid").toString());
//                    getAccessTokenaaa(job.get("refresh_token").toString());
//                } else {
////                        thirdLoginPresenter.bindWeixinLogin("");
//                    SpCommonUtils.put(LoginActivity.this, AppConfig.WEICHAT_CODE, "");
//                }
//
//
//            }
//
//            @Override
//            public void onFailure(Exception e) {
//                android.widget.Toast.makeText(LoginActivity.this, "登录失败", android.widget.Toast.LENGTH_SHORT).show();
//            }
//        };
//        OkHttpUtils.get(http, resultCallback);
//    }


    private void getAccessToken(String code) {
        //获取授权
        String http = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="
                + Constants.APP_ID + "&secret=" + Constants.APP_SECRET + "&code=" +
                code + "&grant_type=authorization_code";
        OkHttpUtils.ResultCallback<String> resultCallback = new OkHttpUtils.ResultCallback<String>() {
            @Override
            public void onSuccess(String response) {
                String access = null;
                String openId = null;

//                     ===response=={
// "access_token":"15_fSq7RGugDQLBBxHAT1IqK8odApi-QUBfvwgWz1nHT2EXrp4_FclFqNuAthFPcuxU55VUQQLKUoH3AnzX1CO5dAJIsVNTKiGWlMEMC6GP37w",
// "expires_in":7200,
// "refresh_token":"15_eVsG_NRRuunPgbp_1B_CD6-QvYUle6lMVcHAV3pzkQAB9Uvqe96R8U0P-Vgv3zgc12tft5RQBNZmkPt1eXicMyPq6urOaq49csLB7PplP7Q",
// "openid":"oa9icwBIXUWSsKwUHyPOI0pNtXQU",
// "scope":"snsapi_userinfo",
// "unionid":"oZ7F3wyMgU-5COwsUTqbzR_7Nzkg"}


// {"errcode":40163,"errmsg":"code been used, hints: [ req_id: CWu0.A05242026 ]"}
                System.out.println("===response== you" + response);
//                JSONObject job = JSONObject.parseObject(response);
//                if (job.get("unionid") != null) {
//                    System.out.println(job.get("unionid"));
//                    SpCommonUtils.put(LoginActivity.this, AppConfig.WEICHAT_CODE, job.get("unionid").toString());
//                    thirdLoginPresenter.bindWeixinLogin(job.get("unionid").toString());
//                    getAccessTokenaaa(job.get("refresh_token").toString());
//                } else {
////                        thirdLoginPresenter.bindWeixinLogin("");
//                    SpCommonUtils.put(LoginActivity.this, AppConfig.WEICHAT_CODE, "");
//                }


                //获取个人信息
//                String getUserInfo = "https://api.weixin.qq.com/sns/userinfo?access_token=" + access + "&openid=" + openId + "";
//                OkHttpUtils.ResultCallback<WeChatInfo> resultCallback = new OkHttpUtils.ResultCallback<WeChatInfo>() {
//                    @Override
//                    public void onSuccess(WeChatInfo response) {
//                        response.setErrCode(resp.errCode);
//                        Log.i("TAG获取个人信息", new Gson().toJson(response));
////                        Toast.makeText(WXEntryActivity.this, new Gson().toJson(response), Toast.LENGTH_LONG).show();
//                        finish();
//                    }
//
//                    @Override
//                    public void onFailure(Exception e) {
//                        Toast.makeText(WXEntryActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
//                    }
//                };
//                OkHttpUtils.get(getUserInfo, resultCallback);
            }

            @Override
            public void onFailure(Exception e) {
                android.widget.Toast.makeText(LoginActivity.this, "登录失败", android.widget.Toast.LENGTH_SHORT).show();
            }
        };
        OkHttpUtils.get(http, resultCallback);
    }


}
