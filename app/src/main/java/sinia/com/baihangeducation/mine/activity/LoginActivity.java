package sinia.com.baihangeducation.mine.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.framwork.utils.CommonUtil;
import com.example.framwork.utils.SpCommonUtils;
import com.example.framwork.utils.Toast;
import com.githang.statusbar.StatusBarCompat;
import com.mcxtzhang.swipemenulib.utils.TimeUtils;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.yanzhenjie.nohttp.Logger;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.supplement.base.BaseActivity;

import com.mcxtzhang.swipemenulib.info.IsCompleteInfo;
import com.mcxtzhang.swipemenulib.info.bean.ThirdLoginInfo;

import sinia.com.baihangeducation.mine.presenter.LoginPresenter;
import sinia.com.baihangeducation.mine.presenter.ThirdLoginPresenter;
import sinia.com.baihangeducation.mine.view.ILoginView;
import sinia.com.baihangeducation.mine.view.IThirdLoginView;
import sinia.com.baihangeducation.supplement.base.Goto;

/**
 * 登录页面 sinia.com.baihangeducation.mine.activity.LoginActivity
 */

public class LoginActivity extends AppCompatActivity implements ILoginView, IThirdLoginView, View.OnClickListener {
    private LoginPresenter mLoginPresenter;
    private UMShareAPI umShareAPI = null;

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

    //极光推送，绑定用户时候的参数.
    private String userType;
    private Set<String> set;
    private Activity context;
    private TextView mUserAgreement;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        context = this;
        initView();
        initData();
    }


    protected void initData() {

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
    }


    /**
     * 初始化控件
     */

    protected void initView() {
        mPhoneNum = (EditText) findViewById(R.id.edt_phone_num);
        mPassword = (EditText) findViewById(R.id.edt_password);
        mLogin = (TextView) findViewById(R.id.tv_login);
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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                LoginActivity.this.finish();
                break;
            case R.id.tv_login:
                if (TimeUtils.isLetterDigit(getPassword())) {
                    mLoginPresenter.login(this);
                } else {
                    Toast.getInstance().showErrorToast(context, "请输入6-12位数字或字母");
                }

                break;
            case R.id.tv_login_weixin:
                QQorWeChatLogin(SHARE_MEDIA.WEIXIN);
                break;
            case R.id.tv_login_qq:
                QQorWeChatLogin(SHARE_MEDIA.QQ);
                break;
            case R.id.tv_register:
                Goto.toRegister(context);
                break;
            case R.id.tv_forget_password:
                //忘记密码
                Goto.toForgetPassword(context);
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
    protected void onResume() {
        super.onResume();
        Log.i("生命周期", "onResume");
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


}
