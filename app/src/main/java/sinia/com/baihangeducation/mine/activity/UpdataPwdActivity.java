package sinia.com.baihangeducation.mine.activity;

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

public class UpdataPwdActivity extends BaseActivity implements IUpdataPwdView, IAuthCodeView {
    private TextView mPhone;                //手机号码
    private EditText mInputPwd_1;           //第一次输入新密码
    private EditText mInputPwd_2;           //第二次输入新密码
    private EditText mAuthCode;             //验证码
    private TimeButton mGetAuthCode;        //获取验证码按钮
    private ToggleButton mIsShowPwd_1Bt;            //第二次输入新密码  是否显示密码按钮
    private ToggleButton mIsShowPwd_2Bt;            //第二次输入新密码  是否显示密码按钮
    private TextView mSubmit;               //提交按钮

    private boolean mIsShowPwdFlag1 = false;
    private boolean mIsShowPwdFlag2 = false;

    private AuthCodePresenter mAuthCodePresenter;
    private UpdataPwdPresenter mUpdataPwdPresenter;
    private String phone;


    @Override
    public int initLayoutResID() {
        return R.layout.updatapwd_activity;
    }

    @Override
    protected void initData() {

        mCommonTitle.setCenterText(R.string.updataloginpwd);
        phone = (String) SpCommonUtils.get(context, AppConfig.USERPHOTO, "");
        //手机号码中间四位加密
        String maskNumber = phone.substring(0, 3) + "****" + phone.substring(7, phone.length());
        mPhone.setText(maskNumber);

        mAuthCodePresenter = new AuthCodePresenter(context, this);
        mUpdataPwdPresenter = new UpdataPwdPresenter(context, this);
    }

    @Override
    protected void initView() {
        mPhone = $(R.id.updatapwd_phone);
        mInputPwd_1 = $(R.id.updatapwd_pwd_1);
        mInputPwd_2 = $(R.id.updatapwd_pwd_2);
        mAuthCode = $(R.id.updatapwd_verification_code);
        mGetAuthCode = $(R.id.updatapwd_get_verification_code);
        mIsShowPwd_1Bt = $(R.id.updatapwd_pwd_1_isshowpwd);
        mIsShowPwd_2Bt = $(R.id.updatapwd_pwd_2_isshowpwd);
        mSubmit = $(R.id.updatapwd_submit);

        mGetAuthCode.setOnClickListener(this);
        mAuthCode.setOnClickListener(this);
        mIsShowPwd_1Bt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //如果选中，显示密码
                    mInputPwd_1.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //否则隐藏密码
                    mInputPwd_1.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
        mIsShowPwd_2Bt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //如果选中，显示密码
                    mInputPwd_2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //否则隐藏密码
                    mInputPwd_2.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        mSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.updatapwd_get_verification_code:
                //获取验证码按钮
                mAuthCodePresenter.getAuthCode();
                break;
            case R.id.updatapwd_submit:
                //提交
                mUpdataPwdPresenter.updataPwd();
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
            if (et == mInputPwd_1) {

                mIsShowPwd_1Bt.setBackgroundResource(R.drawable.new_login_showpwd);
                mInputPwd_1.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {

                mIsShowPwd_2Bt.setBackgroundResource(R.drawable.new_login_showpwd);
                mInputPwd_2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
        } else {
            if (et == mInputPwd_2) {

                mIsShowPwd_2Bt.setBackgroundResource(R.drawable.new_login_unshowpwd);
                mInputPwd_2.setTransformationMethod(PasswordTransformationMethod.getInstance());
            } else {

                mIsShowPwd_2Bt.setBackgroundResource(R.drawable.new_login_unshowpwd);
                mInputPwd_2.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        }
    }

    @Override
    public void showLoading() {
        showProgress();
    }

    @Override
    public void hideLoading() {
        hideProgress();
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
}
