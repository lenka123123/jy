package sinia.com.baihangeducation.mine.activity;

import android.content.Intent;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.supplement.base.BaseActivity;
import sinia.com.baihangeducation.mine.presenter.ForgetPasswordSubmitPresenter;
import sinia.com.baihangeducation.mine.view.IForgetPasswordView;

/**
 * 忘记密码第二个页面
 */

public class ForgetPassword2Activity extends BaseActivity implements IForgetPasswordView {

    private EditText mPwd1;             //输入第一次密码
    private EditText mPwd2;             //输入第二次密码
    private TextView mSubmit;           //提交
    private Button mIsShowPwd1;         //输入第一次密码框是否显示密码按钮
    private Button mIsShowPwd2;         //输入第二次密码框是否显示密码按钮
    private Intent intent;

    private boolean mIsShowPwdFlag1 = false;
    private boolean mIsShowPwdFlag2 = false;

    private ForgetPasswordSubmitPresenter mForgetPasswordSubmitPresenter;

    @Override
    public int initLayoutResID() {
        return R.layout.forgetpassword2;
    }

    /**
     * 初始化数据
     */
    @Override
    protected void initData() {
//        if (decorViewGroup != null && statusBarView != null)
//        decorViewGroup.removeView(statusBarView);
//        mCommonTitle.setCenterText(R.string.findloginpassword);
        intent = getIntent();

        mForgetPasswordSubmitPresenter = new ForgetPasswordSubmitPresenter(context, this);
    }

    /**
     * 初始化控件
     */
    @Override
    protected void initView() {
        $(R.id.back).setOnClickListener(this);
        mPwd1 = $(R.id.fogetpwd_pswd_page2);
        mPwd2 = $(R.id.fogetpwd_pswd_again_page2);
        mSubmit = $(R.id.forget_submit);
        mIsShowPwd1 = $(R.id.fogetpwd_isshowpwd);
        mIsShowPwd2 = $(R.id.fogetpwd_isshowpwd_again);

        mSubmit.setOnClickListener(this);
        mIsShowPwd1.setOnClickListener(this);
        mIsShowPwd2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
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
                //提交
                mForgetPasswordSubmitPresenter.forgetPasswordSubmit();
                break;
            case R.id.fogetpwd_isshowpwd:
                if (mIsShowPwdFlag1)
                    mIsShowPwdFlag1 = false;
                else
                    mIsShowPwdFlag1 = true;

                showPwdOrNot(mPwd1, mIsShowPwdFlag1);
                break;
            case R.id.fogetpwd_isshowpwd_again:
                if (mIsShowPwdFlag2)
                    mIsShowPwdFlag2 = false;
                else
                    mIsShowPwdFlag2 = true;

                showPwdOrNot(mPwd2, mIsShowPwdFlag2);
                break;
        }
    }

    /**
     * 判断密码框是否显示密码
     *
     * @param et   密码框控件
     * @param flag
     */
    private void showPwdOrNot(EditText et, boolean flag) {
        if (flag) {
            if (et == mPwd1) {
                mIsShowPwd1.setBackgroundResource(R.drawable.new_login_showpwd);
                mPwd1.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                mIsShowPwd2.setBackgroundResource(R.drawable.new_login_showpwd);
                mPwd2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
        } else {
            if (et == mPwd1) {
                mIsShowPwd1.setBackgroundResource(R.drawable.new_login_unshowpwd);
                mPwd1.setTransformationMethod(PasswordTransformationMethod.getInstance());
            } else {
                mIsShowPwd2.setBackgroundResource(R.drawable.new_login_unshowpwd);
                mPwd2.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        }
    }


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
        showProgress();
    }

    @Override
    public void hideLoading() {
        hideProgress();
    }
}
