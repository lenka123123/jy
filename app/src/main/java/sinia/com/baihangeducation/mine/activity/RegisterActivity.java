package sinia.com.baihangeducation.mine.activity;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.framwork.utils.Toast;
import com.example.framwork.widget.TimeButton;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.release.campus.ReleaseFunActivity;
import sinia.com.baihangeducation.supplement.base.BaseActivity;

import com.mcxtzhang.swipemenulib.info.bean.AuthCodeInfo;

import sinia.com.baihangeducation.mine.presenter.AuthCodePresenter;
import sinia.com.baihangeducation.mine.presenter.RegisterPresenter;
import sinia.com.baihangeducation.mine.view.IAuthCodeView;
import sinia.com.baihangeducation.mine.view.IRegisterView;
import sinia.com.baihangeducation.supplement.base.Goto;

import com.mcxtzhang.swipemenulib.utils.Constants;

import static sinia.com.baihangeducation.R.drawable.new_login_showpwd;

/**
 * 注册页面
 */

public class RegisterActivity extends BaseActivity implements IRegisterView, IAuthCodeView {


    private EditText mPhoneNum;         //手机号码
    private EditText mPassword;         //密码
    private EditText mPasswordAgain;    //第二次密码
    private EditText mAuthCode;         //验证码
    private TimeButton mTimeButton;     //获取验证码
    private TextView mRegister;         //注册
    private CheckBox mIsAgree;          //是否同意
    private TextView mUserAgreement;    //用户协议
    private Button mDeletePhone;        //清空手机号码
    private ToggleButton mIsShowPwd1;         //第一个显示密码
    private ToggleButton mIsShowPwd2;         //第二个显示密码

    private boolean mIsShowPwdFlag1 = false;
    private boolean mIsShowPwdFlag2 = false;

    private AuthCodePresenter mAuthCodePresenter;
    private RegisterPresenter mRegisterPresenter;

    @Override
    public int initLayoutResID() {
        return R.layout.activity_register;
    }

    /**
     * 初始化数据
     */
    @Override
    protected void initData() {
//        if (decorViewGroup != null && statusBarView != null)
//        decorViewGroup.removeView(statusBarView);
        mAuthCodePresenter = new AuthCodePresenter(context, this);
        mRegisterPresenter = new RegisterPresenter(context, this);
    }

    /**
     * 初始化控件
     */
    @Override
    protected void initView() {
//          View decorView = getWindow().getDecorView();//获取屏幕的decorView
//        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);//设置全屏
//        mCommonTitle.setCenterText(R.string.register);
        mPhoneNum = $(R.id.edt_register_phone);
        mPassword = $(R.id.edt_register_password1);
        mPasswordAgain = $(R.id.edt_register_password2);
        mAuthCode = $(R.id.edt_register_verification_code);

        mTimeButton = $(R.id.tb_get_verification_code);
        mRegister = $(R.id.tv_register_register);
        mIsAgree = $(R.id.cb_isagree);
        mUserAgreement = $(R.id.tv_register_protocol);

        mDeletePhone = $(R.id.edt_register_phone_delete);
        mIsShowPwd1 = $(R.id.edt_register_password1_isshow);
        mIsShowPwd2 = $(R.id.edt_register_password2_isshow);

        mTimeButton.setOnClickListener(this);
        mRegister.setOnClickListener(this);
        mUserAgreement.setOnClickListener(this);
        mDeletePhone.setOnClickListener(this);

        $(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterActivity.this.finish();
            }
        });

        mIsShowPwd1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //如果选中，显示密码
                    mPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //否则隐藏密码
                    mPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
        mIsShowPwd2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //如果选中，显示密码
                    mPasswordAgain.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //否则隐藏密码
                    mPasswordAgain.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tb_get_verification_code:
                //获取验证码
                mAuthCodePresenter.getAuthCode();
                break;
            case R.id.tv_register_register:
                //注册
                mRegisterPresenter.register();
                break;
            case R.id.tv_register_protocol:
                //用户协议
                Goto.toUserRuleActivity(context);
                break;
            case R.id.edt_register_phone_delete:
                //清空手机号码
                mPhoneNum.setText("");
                mPhoneNum.setHint(R.string.input_phone_num);
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
            if (et == mPassword) {
                mIsShowPwd1.setBackgroundResource(new_login_showpwd);
                mPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                mIsShowPwd2.setBackgroundResource(new_login_showpwd);
                mPasswordAgain.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
        } else {
            if (et == mPassword) {
                mIsShowPwd1.setBackgroundResource(R.drawable.new_login_unshowpwd);
                mPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            } else {
                mIsShowPwd2.setBackgroundResource(R.drawable.new_login_unshowpwd);
                mPasswordAgain.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        }
    }

    @Override
    public void succress() {
        Toast.getInstance().showSuccessToast(context, "注册成功");
//        Goto.toLogin(mContext);
        finish();
    }

    /**
     * 获取手机号码
     *
     * @return
     */
    @Override
    public String getphoneNum() {
        return mPhoneNum.getText().toString().trim();
    }

    /**
     * 获取验证码
     *
     * @return
     */
    @Override
    public String getAuthCode() {
        return mAuthCode.getText().toString().trim();
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

    /**
     * 获取第二次密码
     *
     * @return
     */
    @Override
    public String getPasswordAgain() {
        return mPasswordAgain.getText().toString().trim();
    }

    /**
     * 检查是否阅读协议
     *
     * @return
     */
    @Override
    public Boolean getIsRead() {
        return mIsAgree.isChecked();
    }

    @Override
    public String getPhone() {
        return mPhoneNum.getText().toString().trim();
    }

    @Override
    public String getAuthCodeType() {
        return Constants.CODE_NUM_REGISTER;
    }

    @Override
    public void showAuthCodeSuccess(AuthCodeInfo authCodeInfo) {

    }

    @Override
    public void showGetAuthCodeError(String error) {
        Toast.getInstance().showErrorToast(context, error);
    }

    @Override
    public void showLoading() {
//        showProgress();
    }

    @Override
    public void hideLoading() {
        hideProgress();
    }
}
