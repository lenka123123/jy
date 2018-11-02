package sinia.com.baihangeducation.mine.activity;

import android.app.Dialog;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.framwork.utils.DialogUtils;

import cn.jpush.im.android.api.JMessageClient;
import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.supplement.base.BaseActivity;
import sinia.com.baihangeducation.MyApplication;
import sinia.com.baihangeducation.mine.model.AccountManger;

import com.example.framwork.utils.SpCommonUtils;
import com.mcxtzhang.swipemenulib.info.bean.CommonInfo;

import sinia.com.baihangeducation.mine.presenter.MySettingPresenter;
import sinia.com.baihangeducation.mine.view.IMySettingView;
import sinia.com.baihangeducation.supplement.base.Goto;

/**
 * Created by Administrator on 2018/4/9.
 */

public class MySettingActivity extends BaseActivity implements IMySettingView {

    private MySettingPresenter presenter;

    private LinearLayout mNewGuide;     //新手指南
    private LinearLayout mAboutUs;     //关于就业邦
    private LinearLayout mChangePD;     //修改密码
    private LinearLayout mCleanCache;     //清除缓存
    private LinearLayout mComplaintsAndSuggestions;     //投诉与建议
    private LinearLayout mLinkUs;     //联系客服
    private LinearLayout mSharaUs;     //分享就业邦
    private LinearLayout mCheckUpdata;     //检查更新
    private TextView mLogout;           //退出登录
    private Dialog exitDialog;

    private TextView version;


    @Override
    public int initLayoutResID() {
        return R.layout.mysetting;
    }

    @Override
    protected void initData() {
        mCommonTitle.setCenterText(R.string.mine_setting);
        mCommonTitle.setBackgroundColor(Color.WHITE);
        presenter = new MySettingPresenter(context, this);
        AppConfig.IS_MANUAL_lOGINED = false;
    }

    @Override
    protected void initView() {
        mNewGuide = $(R.id.setting_newguide);
        mAboutUs = $(R.id.setting_aboutus);
        mChangePD = $(R.id.setting_changepd);
        mCleanCache = $(R.id.setting_cleancache);
        mComplaintsAndSuggestions = $(R.id.setting_complaintsandsuggestions);
        mLinkUs = $(R.id.setting_linkus);
        mSharaUs = $(R.id.setting_sharaus);
        mCheckUpdata = $(R.id.setting_checkupdata);
        mLogout = $(R.id.setting_logout);
        version = $(R.id.version);
        getVerson(version);

        mNewGuide.setOnClickListener(this);
        mAboutUs.setOnClickListener(this);
        mChangePD.setOnClickListener(this);
        mCleanCache.setOnClickListener(this);
        mComplaintsAndSuggestions.setOnClickListener(this);
        mLinkUs.setOnClickListener(this);
        mSharaUs.setOnClickListener(this);
        mCheckUpdata.setOnClickListener(this);
        mLogout.setOnClickListener(this);
    }

    private void getVerson(TextView version) {
        String localVersion = "";
        try {
            PackageInfo packageInfo = context.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            localVersion = packageInfo.versionName;

            version.setText("Jiuyebang " + localVersion);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.setting_newguide:
                //新手指南
                Goto.toNewUserGuideActivity(context);
                break;
            case R.id.setting_aboutus:
                //关于就业邦
                Goto.toAboutJobBangActivity(context);
                break;
            case R.id.setting_changepd:
                //修改密码
                Goto.toUpdataPwdActivity(context);
                break;
            case R.id.setting_cleancache:
                //清除缓存
                break;
            case R.id.setting_complaintsandsuggestions:
                //投诉与建议
                break;
            case R.id.setting_linkus:
                //联系客服
                Goto.toLinkUsActivity(context);
                break;
            case R.id.setting_sharaus:
                //分享就业邦
                break;
            case R.id.setting_checkupdata:
                //检查更新
//                Log.i("检查更新", commonInfo.android_audit_version + "版本");
                break;
            case R.id.setting_logout:
                //退出登录
                showExitDialog();
                break;
            case R.id.btn_left:
                if (exitDialog != null)
                    exitDialog.dismiss();
                AppConfig.IS_MANUAL_lOGINED = false;
                break;
            case R.id.btn_right:
                JMessageClient.logout();
                if (exitDialog != null)
                    exitDialog.dismiss();
                System.out.println("logoutSuccess  rrrr");
                presenter.logout();
                AppConfig.ISlOGINED = false;
                AppConfig.IS_MANUAL_lOGINED = true;

                SpCommonUtils.put(context, AppConfig.IS_LOGIN_APP, false);
                System.out.println("tokentoken  false" + false + "==" + false);
                finish();
                break;
        }
    }

    private void showExitDialog() {
        exitDialog = DialogUtils.getInstance().getCenterDialog(context, R.layout.dialog_common_two_button);
        TextView txtContent = (TextView) exitDialog.findViewById(R.id.txt_content);
        Button btnLeft = (Button) exitDialog.findViewById(R.id.btn_left);
        Button btnRight = (Button) exitDialog.findViewById(R.id.btn_right);
        txtContent.setText("是否确定退出登录?");
        btnLeft.setOnClickListener(this);
        btnRight.setOnClickListener(this);
        exitDialog.show();
    }

    @Override
    public void logoutSuccess() {


//        AccountManger.clearUserInfo(context);
//        if (exitDialog != null)
//            exitDialog.dismiss();
//        finish();
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
