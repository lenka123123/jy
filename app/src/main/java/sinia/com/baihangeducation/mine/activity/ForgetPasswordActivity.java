package sinia.com.baihangeducation.mine.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.framwork.widget.TimeButton;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.supplement.base.BaseActivity;
import sinia.com.baihangeducation.mine.model.AccountManger;

import com.mcxtzhang.swipemenulib.info.bean.AuthCodeInfo;

import sinia.com.baihangeducation.mine.presenter.AuthCodePresenter;
import sinia.com.baihangeducation.mine.view.IAuthCodeView;
import sinia.com.baihangeducation.supplement.base.Goto;

import com.mcxtzhang.swipemenulib.utils.Constants;

/**
 * 忘记密码第一个页面
 */

public class ForgetPasswordActivity extends BaseActivity implements IAuthCodeView {

    private TimeButton mTimeButton;     //获取验证码按钮
    private EditText phoneNum;          //手机号码
    private EditText mAuthCode;         //验证码
    private TextView mNext;             //下一步
    private TextView unGetAuthCode;     //无法获取验证码
    private Button mDeletePhone;        //清空手机号按钮

    private AuthCodePresenter mAuthCodePresenter;

    @Override
    public int initLayoutResID() {
        return R.layout.forgetpassword;
    }

    /**
     * 初始化数据
     */
    @Override
    protected void initData() {
//         decorViewGroup.removeView(statusBarView);
//        mCommonTitle.setCenterText(R.string.findloginpassword);
        mAuthCodePresenter = new AuthCodePresenter(context, this);
    }

    /**
     * 初始化控件
     */
    @Override
    protected void initView() {

        mTimeButton = $(R.id.forget_get_verification_code);
        $(R.id.back).setOnClickListener(this);

        phoneNum = $(R.id.fogetpwd_phone);
        mAuthCode = $(R.id.forget_verification_code);
        mNext = $(R.id.forget_next);
        unGetAuthCode = $(R.id.forget_unget_verification_code);
        mDeletePhone = $(R.id.fogetpwd_deltephone);

        mTimeButton.setOnClickListener(this);
        mNext.setOnClickListener(this);
        unGetAuthCode.setOnClickListener(this);
        mDeletePhone.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

        switch (v.getId()) {
            case R.id.back:
                ForgetPasswordActivity.this.finish();
                break;
            case R.id.forget_get_verification_code:
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
            case R.id.fogetpwd_deltephone:
                phoneNum.setText("");
                phoneNum.setHint(R.string.input_phone_num);
                break;
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

    }

    @Override
    public void showGetAuthCodeError(String error) {
        com.example.framwork.utils.Toast.getInstance().showErrorToast(context, error);
    }
}
