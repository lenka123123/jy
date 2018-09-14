package sinia.com.baihangeducation.mine.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.framwork.utils.Toast;
import com.mcxtzhang.swipemenulib.utils.TimeUtils;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.supplement.base.BaseActivity;
import sinia.com.baihangeducation.mine.presenter.ForgetPasswordSubmitPresenter;
import sinia.com.baihangeducation.mine.view.IForgetPasswordView;

/**
 * 忘记密码第二个页面
 */

public class ForgetPassword2Activity extends AppCompatActivity implements IForgetPasswordView, View.OnClickListener {

    private EditText mPwd1;             //输入第一次密码
    private EditText mPwd2;             //输入第二次密码
    private TextView mSubmit;           //提交

    private Intent intent;

    private boolean mIsShowPwdFlag1 = false;
    private boolean mIsShowPwdFlag2 = false;

    private ForgetPasswordSubmitPresenter mForgetPasswordSubmitPresenter;
    private Activity context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.forgetpassword2);
        context = this;
        initView();
        initData();
    }


    protected void initData() {
//        if (decorViewGroup != null && statusBarView != null)
//        decorViewGroup.removeView(statusBarView);
//        mCommonTitle.setCenterText(R.string.findloginpassword);
        intent = getIntent();

        mForgetPasswordSubmitPresenter = new ForgetPasswordSubmitPresenter(context, this);
    }

    protected void initView() {
        findViewById(R.id.back).setOnClickListener(this);
        mPwd1 = findViewById(R.id.fogetpwd_pswd_page2);
        mPwd2 = findViewById(R.id.fogetpwd_pswd_again_page2);
        mSubmit = findViewById(R.id.forget_submit);


        mSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.fogetpwd_isshowpwd:
//                //如果现实密码点开
//                if (mIsShowPwd.isChecked()) {
//                    mPwd1.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
//                } else {
//                    mPwd1.setTransformationMethod(PasswordTransformationMethod.getInstance());
//                }
//                break;
            case R.id.back:
                ForgetPassword2Activity.this.finish();
                break;
            case R.id.forget_submit:

                if (TimeUtils.isLetterDigit(getPassword())) {
                    //提交
                    mForgetPasswordSubmitPresenter.forgetPasswordSubmit();
                } else {
                    Toast.getInstance().showErrorToast(context, "请输入6-12位数字字母登录组合");
                }

                break;
//            case R.id.fogetpwd_isshowpwd:
//                if (mIsShowPwdFlag1)
//                    mIsShowPwdFlag1 = false;
//                else
//                    mIsShowPwdFlag1 = true;
//
//                showPwdOrNot(mPwd1, mIsShowPwdFlag1);
//                break;
//            case R.id.fogetpwd_isshowpwd_again:
//                if (mIsShowPwdFlag2)
//                    mIsShowPwdFlag2 = false;
//                else
//                    mIsShowPwdFlag2 = true;
//
//                showPwdOrNot(mPwd2, mIsShowPwdFlag2);
//                break;
        }
    }

//    /**
//     * 判断密码框是否显示密码
//     *
//     * @param et   密码框控件
//     * @param flag
//     */
//    private void showPwdOrNot(EditText et, boolean flag) {
//        if (flag) {
//            if (et == mPwd1) {
//                mIsShowPwd1.setBackgroundResource(R.drawable.new_login_showpwd);
//                mPwd1.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
//            } else {
//                mIsShowPwd2.setBackgroundResource(R.drawable.new_login_showpwd);
//                mPwd2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
//            }
//        } else {
//            if (et == mPwd1) {
//                mIsShowPwd1.setBackgroundResource(R.drawable.new_login_unshowpwd);
//                mPwd1.setTransformationMethod(PasswordTransformationMethod.getInstance());
//            } else {
//                mIsShowPwd2.setBackgroundResource(R.drawable.new_login_unshowpwd);
//                mPwd2.setTransformationMethod(PasswordTransformationMethod.getInstance());
//            }
//        }
//    }


    @Override
    public String getPhoneNum() {
        return intent.getStringExtra("PhoneName");
    }

    @Override
    public String getAuthCode() {
        return intent.getStringExtra("AuthCode");
    }

    @Override
    public String getPassword() {
        return mPwd1.getText().toString().trim();
    }

    @Override
    public String getPasswordAgain() {
        return mPwd2.getText().toString().trim();
    }

    @Override
    public void showAuthCodeSuccess() {
        finish();
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
