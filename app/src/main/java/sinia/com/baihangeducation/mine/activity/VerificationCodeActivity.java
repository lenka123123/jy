package sinia.com.baihangeducation.mine.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.framwork.utils.Toast;
import com.example.framwork.widget.TimeButton;
import com.mcxtzhang.swipemenulib.info.bean.AuthCodeInfo;
import com.mcxtzhang.swipemenulib.utils.Constants;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.mine.model.AccountManger;
import sinia.com.baihangeducation.mine.presenter.AuthCodePresenter;
import sinia.com.baihangeducation.mine.view.IAuthCodeView;
import sinia.com.baihangeducation.supplement.base.Goto;

/**
 * 验证码登录
 */

public class VerificationCodeActivity extends AppCompatActivity implements IAuthCodeView, View.OnClickListener {

    private TimeButton mTimeButton;     //获取验证码按钮
    private EditText phoneNum;          //手机号码
    private EditText mAuthCode;         //验证码
    private TextView mNext;             //下一步
    private TextView unGetAuthCode;     //无法获取验证码


    private AuthCodePresenter mAuthCodePresenter;
    private Activity context;


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
    }

    protected void initView() {

        mTimeButton = findViewById(R.id.forget_get_verification_code);
        findViewById(R.id.back).setOnClickListener(this);

        phoneNum = findViewById(R.id.fogetpwd_phone);
        mAuthCode = findViewById(R.id.forget_verification_code);
        mNext = findViewById(R.id.forget_next);
        unGetAuthCode = findViewById(R.id.forget_unget_verification_code);

        mTimeButton.setOnClickListener(this);
        mNext.setOnClickListener(this);
        unGetAuthCode.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                VerificationCodeActivity.this.finish();
                break;
            case R.id.forget_get_verification_code:

             //   System.out.println("=====getPhone=========" + getPhone().length());
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
        Toast.getInstance().showErrorToast(context, error);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
