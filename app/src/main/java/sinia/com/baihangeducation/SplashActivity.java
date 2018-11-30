package sinia.com.baihangeducation;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageView;

import com.baidu.mobstat.StatService;
import com.example.framwork.glide.ImageLoaderUtils;
import com.example.framwork.utils.SPUtils;

import java.util.Timer;
import java.util.TimerTask;

import sinia.com.baihangeducation.mine.activity.LoginActivity;
import sinia.com.baihangeducation.supplement.base.BaseActivity;
import sinia.com.baihangeducation.mine.model.AccountManger;
import sinia.com.baihangeducation.home.present.GetCommonInfoPresenter;
import sinia.com.baihangeducation.home.view.IGetCommonInfoView;
import sinia.com.baihangeducation.supplement.base.Goto;

import com.example.framwork.utils.SpCommonUtils;
import com.mcxtzhang.swipemenulib.info.bean.CommonInfo;
import com.mcxtzhang.swipemenulib.utils.ACache;
import com.mcxtzhang.swipemenulib.utils.Constants;
import com.mcxtzhang.swipemenulib.utils.PermissUtil;

/**
 * 启动页
 */
public class SplashActivity extends BaseActivity implements IGetCommonInfoView {

    ACache mCache;
    ImageView welcomeImg;
    Button btnJump;
    private int recLen = 3;
    Timer timer = new Timer();
    private GetCommonInfoPresenter presenter;

    @Override
    protected void forSplashActivity() {
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }
    }

    @Override
    public int initLayoutResID() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(this);
    }

    @Override
    protected void initView() {
        welcomeImg = $(R.id.welcome_img);
        btnJump = $(R.id.btn_jump);
        AppConfig.ISlOGINED = false;

        String token = (String) SpCommonUtils.get(SplashActivity.this, AppConfig.USERTOKEN, "");
        String userId = (String) SpCommonUtils.get(SplashActivity.this, AppConfig.FINALUSERID, "");
        int type = (int) SpCommonUtils.get(SplashActivity.this, AppConfig.FINAL_NUM_FULL_TYPE, 1);
        AppConfig.LOGINPHOTOTPATH = (String) SpCommonUtils.get(SplashActivity.this, AppConfig.FINAL_SAVE_PHOTO_PATH, "");

        AppConfig.USERIDTYPE = type;
        if (!token.equals("")) {
            AppConfig.TOKEN = token;
            AppConfig.USERID = userId;
            AppConfig.ISlOGINED = true;
        }
        //    System.out.println("tokentoken  " + token + "==" + userId);
        if (PermissUtil.checkPermissions(this, PermissUtil.appNeedPermissions)) {
            initSplash();
        }
    }

    private void initSplash() {
        AccountManger.getUserInfo(context);
        AlphaAnimation anima = new AlphaAnimation(0.3f, 1.0f);
        anima.setDuration(3000);// 设置动画显示时间
        welcomeImg.startAnimation(anima);
        presenter = new GetCommonInfoPresenter(this, this);
        presenter.getCommonInfo();
        btnJump.setOnClickListener(this);
    }

    /**
     * 权限申请回调
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PermissUtil.PERMISSON_REQUESTCODE) {
            initSplash();
        }
    }


    @Override
    protected void initData() {
        mCache = ACache.get(context);


    }

    public void onClickListener(View v) {
        task.cancel();
        gotoActivity();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        onClickListener(v);
    }

    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {      // UI thread
                @Override
                public void run() {
                    recLen--;
                    btnJump.setText(recLen + " 跳过");
                    if (recLen < 0) {
                        Log.i("倒计时", recLen + "dao");
                        timer.cancel();
                        btnJump.setVisibility(View.GONE);
                        gotoActivity();
                    }
                }
            });
        }
    };

    private void gotoActivity() {

        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
        SplashActivity.this.finish();
//
//        mCache.put("LAT",getLat());
//        mCache.put("LNG",getLng());
//        boolean isFromBannerHome = (boolean) SPUtils.getInstance().get(this, Constants.IS_FIRST_START, true);
//        if (!isFromBannerHome){
//            Log.i("跳转主页","跳转");
//            Goto.toMainActivity(context);}
//        else Goto.toGuide(context);
//        finish();
    }


    private void startJump() {
        btnJump.setVisibility(View.VISIBLE);
        timer.schedule(task, 1000, 1000);
    }


    @Override
    public String getLocationAdcode() {
        return getAdCode();
    }

    @Override
    public void showSuccess(CommonInfo commonInfo) {
        startJump();
        if (commonInfo != null && !commonInfo.open_img.equals("")) {
            ImageLoaderUtils.displayBigPhoto(context, welcomeImg, commonInfo.open_img);
        }
    }

    @Override
    public void showError() {
        startJump();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null)
            timer.cancel();
    }
}
