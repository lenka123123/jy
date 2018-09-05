package sinia.com.baihangeducation.mine.activity;

import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.supplement.base.BaseActivity;

/**
 * 实名认证成功三秒跳转页面
 */

public class RealNameSuccessActivity extends BaseActivity {
    private int senends = 3;
    private TextView mSencends;
    private Timer timer = new Timer();

    @Override
    public int initLayoutResID() {
        return R.layout.realname_success;
    }

    @Override
    protected void initData() {
        timer.schedule(task, 1000, 1000);
    }

    @Override
    protected void initView() {
        mSencends = $(R.id.realname_success_sencends);
    }


    TimerTask task = new TimerTask() {
        @Override
        public void run() {

            runOnUiThread(new Runnable() {      // UI thread
                @Override
                public void run() {
                    senends--;
                    mSencends.setText("" + senends);
                    if (senends < 0) {
                        timer.cancel();
                        finish();
                    }
                }
            });
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }
}
