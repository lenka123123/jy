package sinia.com.baihangeducation.mine.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RadioButton;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.supplement.base.BaseActivity;

import sinia.com.baihangeducation.mine.MySendAllTimeFragment;
import sinia.com.baihangeducation.mine.MySendPartTimeFragment;

/**
 * 我的页面 我的投递
 */

public class MySendActivity extends BaseActivity {

    private RadioButton mPartTime;
    private RadioButton mAllTime;

    private FragmentManager mFragmentManager;
    private MySendPartTimeFragment mMySendPartTimeFragment;
    private MySendAllTimeFragment mMySendAllTimeFragment;

    private String full = "";

    @Override
    public int initLayoutResID() {
        return R.layout.fragment_mine_mysend;
    }


    @Override
    protected void initData() {
        Intent intent = getIntent();
        full = intent.getStringExtra("full");

        mCommonTitle.setCenterText(R.string.mine_mysend);
        mCommonTitle.setBackgroundColor(Color.WHITE);

        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = mFragmentManager.beginTransaction();

        if (full == null || full.equals("")) {
            mMySendPartTimeFragment = new MySendPartTimeFragment();
            ft.add(R.id.fragment_mine_mysend_framlayout, mMySendPartTimeFragment);
            ft.commit();
            mPartTime.setChecked(true);
        } else {
            mAllTime.setChecked(true);
            mMySendAllTimeFragment = new MySendAllTimeFragment();
            ft.add(R.id.fragment_mine_mysend_framlayout, mMySendAllTimeFragment);
            ft.commit();
        }
    }

    @Override
    protected void initView() {
        mPartTime = $(R.id.fragment_mine_mysend_parttime);
        mAllTime = $(R.id.fragment_mine_mysend_alltime);

        mPartTime.setOnClickListener(this);
        mAllTime.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        FragmentTransaction ft = mFragmentManager.beginTransaction(); //开启一个事务
        switch (v.getId()) {
            case R.id.fragment_mine_mysend_parttime:
                //兼职
                if (null == mMySendPartTimeFragment) {
                    mMySendPartTimeFragment = new MySendPartTimeFragment();
                    ft.add(R.id.fragment_mine_mysend_framlayout, mMySendPartTimeFragment);
                }
                if (null != mMySendAllTimeFragment) {
                    ft.hide(mMySendAllTimeFragment);
                }
                ft.show(mMySendPartTimeFragment);
                break;
            case R.id.fragment_mine_mysend_alltime:
                //全职
                if (null == mMySendAllTimeFragment) {
                    mMySendAllTimeFragment = new MySendAllTimeFragment();
                    ft.add(R.id.fragment_mine_mysend_framlayout, mMySendAllTimeFragment);
                }
                if (null != mMySendPartTimeFragment) {
                    ft.hide(mMySendPartTimeFragment);
                }
                ft.show(mMySendAllTimeFragment);
                break;
        }
        ft.commit();   //提交事务
    }
}
