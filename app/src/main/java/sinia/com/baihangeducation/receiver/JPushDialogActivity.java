package sinia.com.baihangeducation.receiver;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.mine.model.AccountManger;
import sinia.com.baihangeducation.mine.presenter.MySettingPresenter;
import sinia.com.baihangeducation.mine.view.IMySettingView;
import sinia.com.baihangeducation.supplement.base.Goto;


/**
 * Created by apple on 2017/6/14.
 * 推送的弹窗
 */

public class JPushDialogActivity extends AppCompatActivity implements View.OnClickListener, IMySettingView {

    TextView dialogMessage;
    TextView dialogCancel;
    TextView dialogYes;
    //数据库
    String message;
    private MySettingPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_jpush_message);
        presenter = new MySettingPresenter(JPushDialogActivity.this, this);
        presenter.logout();
        AccountManger.clearUserInfo(JPushDialogActivity.this);
        initView();
    }

    private void initView() {
        dialogMessage = (TextView) findViewById(R.id.dialog_message);
        dialogCancel = (TextView) findViewById(R.id.dialog_cancel);
        dialogYes = (TextView) findViewById(R.id.dialog_yes);
        dialogMessage.setText("该帐号已在其他设备登录，本地已下线，如果非本人操作，请立即修改您的登录密码！");


        dialogCancel.setOnClickListener(this);
        dialogYes.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_cancel:
                AppConfig.ISlOGINED = false;
                AppConfig.TOKEN = "USERID";
                AppConfig.USERID = "USERID";
                Goto.toLogin(this);
                finish();
                break;
            case R.id.dialog_yes:
                AppConfig.ISlOGINED = false;
                AppConfig.TOKEN = "USERID";
                AppConfig.USERID = "USERID";
                Goto.toLogin(this);
                finish();
                break;
        }
    }

    @Override
    public void logoutSuccess() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
