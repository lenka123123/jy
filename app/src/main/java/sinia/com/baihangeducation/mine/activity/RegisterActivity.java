package sinia.com.baihangeducation.mine.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
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

import com.fm.openinstall.OpenInstall;
import com.fm.openinstall.listener.AppInstallAdapter;
import com.fm.openinstall.listener.AppInstallListener;
import com.fm.openinstall.model.AppData;
import com.fm.openinstall.model.Error;
import com.mcxtzhang.swipemenulib.info.bean.AuthCodeInfo;

import sinia.com.baihangeducation.mine.presenter.AuthCodePresenter;
import sinia.com.baihangeducation.mine.presenter.RegisterPresenter;
import sinia.com.baihangeducation.mine.view.IAuthCodeView;
import sinia.com.baihangeducation.mine.view.IRegisterView;
import sinia.com.baihangeducation.supplement.base.Goto;

import com.mcxtzhang.swipemenulib.utils.Constants;
import com.mcxtzhang.swipemenulib.utils.TimeUtils;

import org.json.JSONException;
import org.json.JSONObject;

import static sinia.com.baihangeducation.R.drawable.new_login_showpwd;

/**
 * 注册页面
 */

public class RegisterActivity extends AppCompatActivity implements IRegisterView, IAuthCodeView, View.OnClickListener {


    private EditText mPhoneNum;         //手机号码
    private EditText mPassword;         //密码
    private EditText mPasswordAgain;    //第二次密码
    private EditText mAuthCode;         //验证码
    private TimeButton mTimeButton;     //获取验证码
    private TextView mRegister;         //注册
    private TextView mUserAgreement;    //用户协议

    private boolean mIsShowPwdFlag1 = false;
    private boolean mIsShowPwdFlag2 = false;

    private AuthCodePresenter mAuthCodePresenter;
    private RegisterPresenter mRegisterPresenter;

    private Activity context;
    private String channelCode = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);
        context = this;

        final SharedPreferences sp = getSharedPreferences("filename", MODE_PRIVATE);
        boolean isFirst = sp.getBoolean("isFirst", true);
        if(isFirst){
            OpenInstall.getInstall(new AppInstallListener() {
                @Override
                public void onInstallFinish(AppData appData, Error error) {
                    //获取渠道数据
                    System.out.println("=======channelCode==qq" + appData.getData().toString());
                    try {
                        JSONObject   object = new JSONObject(appData.getData().toString());
                        channelCode = object.getString("channel");

                        System.out.println("=======channelCode==qq" + channelCode);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

//                @Override
//                public void onInstallFinish(AppData appData, Error error) {
//                    //使用数据后，不想再调用，将isFirst设置为false
//                    sp.edit().putBoolean("isFirst", false).apply();
//                }
            });
        }




//        OpenInstall.getInstall(new AppInstallAdapter() {
//            @Override
//            public void onInstall(AppData appData) {
//                //获取渠道数据
//                String channelCode = appData.getChannel();
//                //获取自定义数据
//                String bindData = appData.getData();
//                Log.d("OpenInstallww", "getInstall : installData = " + appData.toString());
//            }
//        });


        initView();
        initData();
    }



    protected void initData() {
//        if (decorViewGroup != null && statusBarView != null)
//        decorViewGroup.removeView(statusBarView);
        mAuthCodePresenter = new AuthCodePresenter(context, this);
        mRegisterPresenter = new RegisterPresenter(context, this);


    }

    protected void initView() {
//          View decorView = getWindow().getDecorView();//获取屏幕的decorView
//        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);//设置全屏
//        mCommonTitle.setCenterText(R.string.register);
        mPhoneNum = findViewById(R.id.edt_register_phone);
        mPassword = findViewById(R.id.edt_register_password1);
        mPasswordAgain = findViewById(R.id.edt_register_password2);
        mAuthCode = findViewById(R.id.edt_register_verification_code);

        mTimeButton = findViewById(R.id.tb_get_verification_code);
        mRegister = findViewById(R.id.tv_register_register);
        mUserAgreement = findViewById(R.id.tv_register_protocol);


        mUserAgreement.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);

        mTimeButton.setOnClickListener(this);
        mRegister.setOnClickListener(this);
        mUserAgreement.setOnClickListener(this);

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterActivity.this.finish();
            }
        });

//        mIsShowPwd1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    //如果选中，显示密码
//                    mPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
//                } else {
//                    //否则隐藏密码
//                    mPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
//                }
//            }
//        });
//        mIsShowPwd2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    //如果选中，显示密码
//                    mPasswordAgain.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
//                } else {
//                    //否则隐藏密码
//                    mPasswordAgain.setTransformationMethod(PasswordTransformationMethod.getInstance());
//                }
//            }
//        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tb_get_verification_code:
                //获取验证码
                if (getPhone().length() != 11) {
                    mTimeButton.setLenght(0);
                    Toast.getInstance().showErrorToast(context, "请输入手机号");

                    return;
                }

                mAuthCodePresenter.getAuthCode();
                break;
            case R.id.tv_register_register:

                if (TimeUtils.isLetterDigit(getPassword())) {
                    //注册
                    mRegisterPresenter.register(channelCode);
                } else {
                    Toast.getInstance().showErrorToast(context, "请输入6-12位数字或字母");
                }


                break;
            case R.id.tv_register_protocol:
                //用户协议
                Goto.toUserRuleActivity(context);
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
//            if (et == mPassword) {
//                mIsShowPwd1.setBackgroundResource(new_login_showpwd);
//                mPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
//            } else {
//                mIsShowPwd2.setBackgroundResource(new_login_showpwd);
//                mPasswordAgain.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
//            }
//        } else {
//            if (et == mPassword) {
//                mIsShowPwd1.setBackgroundResource(R.drawable.new_login_unshowpwd);
//                mPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
//            } else {
//                mIsShowPwd2.setBackgroundResource(R.drawable.new_login_unshowpwd);
//                mPasswordAgain.setTransformationMethod(PasswordTransformationMethod.getInstance());
//            }
//        }
//    }

    @Override
    public void succress() {
        //用户注册成功后调用
        OpenInstall.reportRegister();
        Goto.toMainActivity(context);
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
        return true;
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
        mTimeButton.setLenght(60 * 1000);
    }

    @Override
    public void showGetAuthCodeError(String error) {
        mTimeButton.setLenght(0);
        Toast.getInstance().showErrorToast(context, error);
    }

    @Override
    public void showLoading() {
//        showProgress();
    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
