package sinia.com.baihangeducation.mine.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.framwork.utils.CommonUtil;
import com.example.framwork.utils.Toast;
import com.example.framwork.widget.TimeButton;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.club.club.interfaces.GetRequestListener;
import sinia.com.baihangeducation.mine.presenter.LoginPresenter;
import sinia.com.baihangeducation.mine.presenter.ThirdLoginPresenter;
import sinia.com.baihangeducation.supplement.base.BaseActivity;
import sinia.com.baihangeducation.mine.model.AccountManger;

import com.mcxtzhang.swipemenulib.info.bean.AuthCodeInfo;

import sinia.com.baihangeducation.mine.presenter.AuthCodePresenter;
import sinia.com.baihangeducation.mine.view.IAuthCodeView;
import sinia.com.baihangeducation.supplement.base.Goto;

import com.mcxtzhang.swipemenulib.utils.Constants;
import com.yanzhenjie.nohttp.Logger;

import java.util.HashSet;
import java.util.Set;

/**
 * 忘记密码第一个页面
 */

public class ForgetPasswordActivity extends AppCompatActivity implements IAuthCodeView, View.OnClickListener, GetRequestListener {

    private TimeButton mTimeButton;     //获取验证码按钮
    private EditText phoneNum;          //手机号码
    private EditText mAuthCode;         //验证码
    private TextView mNext;             //下一步
    private TextView unGetAuthCode;     //无法获取验证码


    private AuthCodePresenter mAuthCodePresenter;
    private Activity context;
    private String type;
    private TextView tv_login;
    private LoginPresenter mLoginPresenter;
    private HashSet set;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.forgetpassword);
        context = this;
        initView();
        initData();
    }


    protected void initData() {
//         decorViewGroup.removeView(statusBarView);
//        mCommonTitle.setCenterText(R.string.findloginpassword);
        mAuthCodePresenter = new AuthCodePresenter(context, this);
        mLoginPresenter = new LoginPresenter(context);

    }

    protected void initView() {
        Intent intent = getIntent();
        type = intent.getStringExtra("type");

        mTimeButton = findViewById(R.id.forget_get_verification_code);
        findViewById(R.id.back).setOnClickListener(this);

        phoneNum = findViewById(R.id.fogetpwd_phone);
        mAuthCode = findViewById(R.id.forget_verification_code);
        mNext = findViewById(R.id.forget_next);
        tv_login = findViewById(R.id.tv_login);
        unGetAuthCode = findViewById(R.id.forget_unget_verification_code);

        mTimeButton.setOnClickListener(this);
        mNext.setOnClickListener(this);
        tv_login.setOnClickListener(this);
        unGetAuthCode.setOnClickListener(this);

        if (type.equals("")) {
            mNext.setVisibility(View.VISIBLE);
            tv_login.setVisibility(View.INVISIBLE);
        } else {
            mNext.setVisibility(View.INVISIBLE);
            tv_login.setVisibility(View.VISIBLE);
        }
        tv_login.setClickable(true);
    }

    /**
     * 登录成功
     */
    public void showLoginSuccress() {
        Goto.toMainActivity(context);
        initJPushTag();

        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_login:
                tv_login.setClickable(false);
                if (getPhone().length() != 11) {
                    mTimeButton.setLenght(0);
                    Toast.getInstance().showErrorToast(context, "请输入手机号");
                    return;
                }

                if (mAuthCode.getText().length() < 1) {
                    Toast.getInstance().showErrorToast(context, "请输入验证码");
                    return;
                }

                mLoginPresenter.loginForVerification(getPhone(), mAuthCode.getText().toString(), this);

                break;
            case R.id.back:
                ForgetPasswordActivity.this.finish();
                break;
            case R.id.forget_get_verification_code:

                System.out.println("=====getPhone=========" + getPhone().length());
                if (getPhone().length() != 11) {
                    mTimeButton.setLenght(0);
                    Toast.getInstance().showErrorToast(context, "请输入手机号");
                    return;
                }

                mAuthCodePresenter.getAuthCode();
                break;
            case R.id.forget_next:
                if (AccountManger.checkThridBind(context, phoneNum.getText().toString().trim(), mAuthCode.getText().toString().trim())) {
                    Goto.toForgetPassword2(context, phoneNum.getText().toString().trim(), mAuthCode.getText().toString().trim());
                    finish();
                }
                break;
            case R.id.forget_unget_verification_code:
                Goto.toUnGetAuthCode(context);
                break;
//            case R.id.fogetpwd_deltephone:
//                phoneNum.setText("");
//                phoneNum.setHint(R.string.input_phone_num);
//                break;
        }
    }

    @Override
    public String getPhone() {
        return phoneNum.getText().toString().trim();
    }

    @Override
    public String getAuthCodeType() {
        return Constants.CODE_NUM_FORGET_PSD;
    }

    @Override
    public void showAuthCodeSuccess(AuthCodeInfo authCodeInfo) {
        mTimeButton.setLenght(60 * 1000);
    }

    @Override
    public void showGetAuthCodeError(String error) {
        mTimeButton.setLenght(0);
        com.example.framwork.utils.Toast.getInstance().showErrorToast(context, error);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void setRequestSuccess(String msg) {
        tv_login.setClickable(false);
        Goto.toMainActivity(context);
        initJPushTag();
        finish();
    }

    @Override
    public void setRequestFail() {
        tv_login.setClickable(true);
    }

    /**
     * 设置推送别名和tag
     */
    //极光推送，绑定用户时候的参数
    private String userType;

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
}
