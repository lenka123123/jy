package sinia.com.baihangeducation.mine.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.framwork.utils.SpCommonUtils;
import com.example.framwork.utils.Toast;
import com.example.framwork.utils.UserInfo;
import com.example.framwork.widget.TimeButton;

import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.supplement.base.BaseActivity;
import sinia.com.baihangeducation.MyApplication;
import sinia.com.baihangeducation.mine.model.AccountManger;

import com.mcxtzhang.swipemenulib.info.bean.AuthCodeInfo;

import sinia.com.baihangeducation.mine.presenter.AuthCodePresenter;
import sinia.com.baihangeducation.mine.presenter.UpdataPwdPresenter;
import sinia.com.baihangeducation.mine.view.IAuthCodeView;
import sinia.com.baihangeducation.mine.view.IUpdataPwdView;
import sinia.com.baihangeducation.supplement.base.Goto;

import com.mcxtzhang.swipemenulib.utils.Constants;

/**
 * 修改密码页面
 */

public class UpdataPwdActivity extends AppCompatActivity implements IUpdataPwdView, IAuthCodeView, View.OnClickListener {

    private EditText mInputPwd_1;           //第一次输入新密码
    private EditText mInputPwd_2;           //第二次输入新密码
    private EditText mAuthCode;             //验证码
    private TimeButton mGetAuthCode;        //获取验证码按钮
    private TextView mSubmit;               //提交按钮

    private boolean mIsShowPwdFlag1 = false;
    private boolean mIsShowPwdFlag2 = false;

    private AuthCodePresenter mAuthCodePresenter;
    private UpdataPwdPresenter mUpdataPwdPresenter;
    private String phone;
    private Activity context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.updatapwd_activity);
        context = this;
        initView();
        initData();
    }

    protected void initData() {
        phone = (String) SpCommonUtils.get(context, AppConfig.USERPHOTO, "");
        //手机号码中间四位加密
        String maskNumber = phone.substring(0, 3) + "****" + phone.substring(7, phone.length());


        mAuthCodePresenter = new AuthCodePresenter(context, this);
        mUpdataPwdPresenter = new UpdataPwdPresenter(context, this);
    }

    protected void initView() {
        mInputPwd_1 = findViewById(R.id.updatapwd_pwd_1);
        mInputPwd_2 = findViewById(R.id.updatapwd_pwd_2);
        mAuthCode = findViewById(R.id.updatapwd_verification_code);
        mGetAuthCode = findViewById(R.id.updatapwd_get_verification_code);
        mSubmit = findViewById(R.id.updatapwd_submit);

        findViewById(R.id.back).setOnClickListener(this);
        mGetAuthCode.setOnClickListener(this);
        mAuthCode.setOnClickListener(this);
//        mIsShowPwd_1Bt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    //如果选中，显示密码
//                    mInputPwd_1.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
//                } else {
//                    //否则隐藏密码
//                    mInputPwd_1.setTransformationMethod(PasswordTransformationMethod.getInstance());
//                }
//            }
//        });
//        mIsShowPwd_2Bt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    //如果选中，显示密码
//                    mInputPwd_2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
//                } else {
//                    //否则隐藏密码
//                    mInputPwd_2.setTransformationMethod(PasswordTransformationMethod.getInstance());
//                }
//            }
//        });

        mSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.updatapwd_get_verification_code:
                //获取验证码按钮
                mAuthCodePresenter.getAuthCode();
                break;
            case R.id.updatapwd_submit:
                //提交
                mUpdataPwdPresenter.updataPwd();
                break;
            case R.id.back:
                //提交
                UpdataPwdActivity.this.finish();
                break;
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
//            if (et == mInputPwd_1) {
//
//                mIsShowPwd_1Bt.setBackgroundResource(R.drawable.new_login_showpwd);
//                mInputPwd_1.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
//            } else {
//
//                mIsShowPwd_2Bt.setBackgroundResource(R.drawable.new_login_showpwd);
//                mInputPwd_2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
//            }
//        } else {
//            if (et == mInputPwd_2) {
//
//                mIsShowPwd_2Bt.setBackgroundResource(R.drawable.new_login_unshowpwd);
//                mInputPwd_2.setTransformationMethod(PasswordTransformationMethod.getInstance());
//            } else {
//
//                mIsShowPwd_2Bt.setBackgroundResource(R.drawable.new_login_unshowpwd);
//                mInputPwd_2.setTransformationMethod(PasswordTransformationMethod.getInstance());
//            }
//        }
//    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public String getNewPwd() {
        return mInputPwd_1.getText().toString().trim();
    }

    @Override
    public String getNewPwdAgain() {
        return mInputPwd_2.getText().toString().trim();
    }

    @Override
    public String getAuthCode() {
        return mAuthCode.getText().toString().trim();
    }

    @Override
    public void showUpdataPwdSuccess() {
        Toast.getInstance().showSuccessToast(context, "修改密码成功");
        AccountManger.clearUserInfo(context);
        Goto.toLogin(context);
        finish();
    }

    @Override
    public String getPhone() {
        return phone;
    }

    @Override
    public String getAuthCodeType() {
        return Constants.CODE_NUM_MODIFY_PSD;
    }

    @Override
    public void showAuthCodeSuccess(AuthCodeInfo authCodeInfo) {

    }

    @Override
    public void showGetAuthCodeError(String error) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
