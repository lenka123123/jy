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
import com.yanzhenjie.nohttp.Logger;

import java.util.HashSet;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.supplement.base.BaseActivity;

import com.mcxtzhang.swipemenulib.info.bean.AuthCodeInfo;

import sinia.com.baihangeducation.mine.presenter.AuthCodePresenter;
import sinia.com.baihangeducation.mine.presenter.BindingPhonePresenter;
import sinia.com.baihangeducation.mine.view.IAuthCodeView;
import sinia.com.baihangeducation.mine.view.IBindingPhoneView;
import sinia.com.baihangeducation.supplement.base.Goto;

import com.mcxtzhang.swipemenulib.utils.Constants;

/**
 * 绑定手机号码页面
 */

public class BindingPhoneActivity extends AppCompatActivity implements IBindingPhoneView, IAuthCodeView, View.OnClickListener {

    private Intent intent;
    private String uid;
    private String type;

    private EditText mBindePhoneNum;        //手机号码
    private EditText mBindeAuthCode;        //验证码
    private TimeButton mTimeButton;         //获取验证啊按钮
    private TextView mBindSubmit;           //提交
//    private TextView mBindUnGetAuthCode;    //无法获取验证码


    private AuthCodePresenter mAuthCodePresenter;
    private BindingPhonePresenter mBindingPhonePresenter;

    //极光推送，绑定用户时候的参数.
    private String userType;
    private Set<String> set;
    private Activity context;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_bindingphone);
        context = this;
        initView();
        initData();
    }


    protected void initData() {
        intent = getIntent();
        uid = intent.getStringExtra("UID");
        type = intent.getStringExtra("TYPE");

        mAuthCodePresenter = new AuthCodePresenter(context, this);
        mBindingPhonePresenter = new BindingPhonePresenter(context, this);
    }

    protected void initView() {
        mBindePhoneNum = findViewById(R.id.bindthired_phone);
        mBindeAuthCode = findViewById(R.id.bindthired_code);
        mTimeButton = findViewById(R.id.bindthired_phone_get_verification_code);
        mBindSubmit = findViewById(R.id.bindthired_submit);
        mTimeButton.setOnClickListener(this);
        mBindSubmit.setOnClickListener(this);
        findViewById(R.id.back).setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bindthired_phone_get_verification_code:
                mAuthCodePresenter.getAuthCode();
                break;
            case R.id.bindthired_submit:
                mBindingPhonePresenter.binding();
                break;
//            case R.id.bindthired_unget_verification_code:
//                Goto.toUnGetAuthCode(context);
//                break;

            case R.id.back:
                BindingPhoneActivity.this.finish();
                break;
        }
    }

    /**
     * 设置推送别名和tag
     */

    private void initJPushTag() {
        userType = CommonUtil.getAndroidId(this);
        Log.i("设备ID", userType + "        MainActivity");
        set = new HashSet<>();
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
                            pushTag();
                        Logger.d("JPushInterface.setTags--------" + userType + "-------------result=" + result);
                    }
                });
    }

    @Override
    public String getPhoneNum() {
        return mBindePhoneNum.getText().toString().trim();
    }

    @Override
    public String getAuthCode() {
        return mBindeAuthCode.getText().toString().trim();
    }

    @Override
    public String getUid() {
        return uid;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public void showBindingSucess() {
        //绑定成功
        initJPushTag();
        finish();
    }

    @Override
    public String getPhone() {
        return mBindePhoneNum.getText().toString().trim();
    }

    @Override
    public String getAuthCodeType() {
        return Constants.CODE_NUM_BIND;
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

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
