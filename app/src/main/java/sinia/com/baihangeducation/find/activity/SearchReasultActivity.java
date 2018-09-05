package sinia.com.baihangeducation.find.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RadioButton;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.supplement.base.BaseActivity;

import sinia.com.baihangeducation.find.SearchReasultInfomationFragment;
import sinia.com.baihangeducation.find.SearchReasultSecretFragment;
import sinia.com.baihangeducation.find.SearchReasultStrategyFragment;
import sinia.com.baihangeducation.find.SearchReasultTestFragment;

/**
 * 搜索结果
 */

public class SearchReasultActivity extends BaseActivity {

    private RadioButton mFunny;
    private RadioButton mStrategy;
    private RadioButton mTest;
    private RadioButton mSecret;
    private RadioButton mInfomation;

    private Intent intent;
    public static String keyword;

    private FragmentManager mFragmentManager;

//    private SearchReasultFunnyFragment mSearchReasultFunnyFragment;                 //趣事
    private SearchReasultStrategyFragment mSearchReasultStrategyFragment;           //攻略
    private SearchReasultTestFragment mSearchReasultTestFragment;                   //试卷
    private SearchReasultSecretFragment mSearchReasultSecretFragment;                //秘籍
    private SearchReasultInfomationFragment mSearchReasultInfomationFragment;       //资讯

    @Override
    public int initLayoutResID() {
        return R.layout.searchresult;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragmentManager = getSupportFragmentManager();

        FragmentTransaction ft = mFragmentManager.beginTransaction();
        mSearchReasultInfomationFragment = new SearchReasultInfomationFragment();
        ft.add(R.id.searchresult_framlayout, mSearchReasultInfomationFragment);
        ft.commit();
    }

    @Override
    protected void initData() {
        mCommonTitle.setCenterText(R.string.searchreasult);
        mCommonTitle.setBackground(getResources().getDrawable(R.color.white));

        intent = getIntent();
        keyword = intent.getStringExtra("keyword");
//        Log.i("穿过来的keyword",keyword);
    }

    @Override
    protected void initView() {
        mFunny = $(R.id.search_funny);
        mStrategy = $(R.id.searchresult_strategy);
        mTest = $(R.id.searchresult_test);
        mSecret = $(R.id.searchresult_secret);
        mInfomation = $(R.id.searchresult_infomation);

        mFunny.setOnClickListener(this);
        mStrategy.setOnClickListener(this);
        mTest.setOnClickListener(this);
        mSecret.setOnClickListener(this);
        mInfomation.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        FragmentTransaction ft = mFragmentManager.beginTransaction(); //开启一个事务
        switch (v.getId()) {
//            case R.id.search_funny:
//                //趣事
//                if (null == mSearchReasultFunnyFragment) {
//                    mSearchReasultFunnyFragment = new SearchReasultFunnyFragment();
//                    ft.add_friend(R.id.searchresult_framlayout, mSearchReasultFunnyFragment);
//                }
//                if (null != mSearchReasultStrategyFragment) {
//                    ft.hide(mSearchReasultStrategyFragment);
//                }
//                if (null != mSearchReasultTestFragment) {
//                    ft.hide(mSearchReasultTestFragment);
//                }
//                if (null != mSearchReasultSecretFragment) {
//                    ft.hide(mSearchReasultSecretFragment);
//                }
//                if (null != mSearchReasultInfomationFragment) {
//                    ft.hide(mSearchReasultInfomationFragment);
//                }
//                ft.show(mSearchReasultFunnyFragment);
//                break;
            case R.id.searchresult_strategy:
                //攻略

                if (null == mSearchReasultStrategyFragment) {
                    mSearchReasultStrategyFragment = new SearchReasultStrategyFragment();
                    ft.add(R.id.searchresult_framlayout, mSearchReasultStrategyFragment);
                }
//                if (null != mSearchReasultFunnyFragment) {
//                    ft.hide(mSearchReasultFunnyFragment);
//                }
                if (null != mSearchReasultTestFragment) {
                    ft.hide(mSearchReasultTestFragment);
                }
                if (null != mSearchReasultSecretFragment) {
                    ft.hide(mSearchReasultSecretFragment);
                }
                if (null != mSearchReasultInfomationFragment) {
                    ft.hide(mSearchReasultInfomationFragment);
                }
                ft.show(mSearchReasultStrategyFragment);
                break;
            case R.id.searchresult_test:
                //试卷
                if (null == mSearchReasultTestFragment) {
                    mSearchReasultTestFragment = new SearchReasultTestFragment();
                    ft.add(R.id.searchresult_framlayout, mSearchReasultTestFragment);
                }
//                if (null != mSearchReasultFunnyFragment) {
//                    ft.hide(mSearchReasultFunnyFragment);
//                }
                if (null != mSearchReasultStrategyFragment) {
                    ft.hide(mSearchReasultStrategyFragment);
                }
                if (null != mSearchReasultSecretFragment) {
                    ft.hide(mSearchReasultSecretFragment);
                }
                if (null != mSearchReasultInfomationFragment) {
                    ft.hide(mSearchReasultInfomationFragment);
                }
                ft.show(mSearchReasultTestFragment);
                break;
            case R.id.searchresult_secret:
                //秘籍

                if (null == mSearchReasultSecretFragment) {
                    mSearchReasultSecretFragment = new SearchReasultSecretFragment();
                    ft.add(R.id.searchresult_framlayout, mSearchReasultSecretFragment);
                }
//                if (null != mSearchReasultFunnyFragment) {
//                    ft.hide(mSearchReasultFunnyFragment);
//                }
                if (null != mSearchReasultStrategyFragment) {
                    ft.hide(mSearchReasultStrategyFragment);
                }
                if (null != mSearchReasultTestFragment) {
                    ft.hide(mSearchReasultTestFragment);
                }
                if (null != mSearchReasultInfomationFragment) {
                    ft.hide(mSearchReasultInfomationFragment);
                }
                ft.show(mSearchReasultSecretFragment);
                break;
            case R.id.searchresult_infomation:
                //资讯
                if (null == mSearchReasultInfomationFragment) {
                    mSearchReasultInfomationFragment = new SearchReasultInfomationFragment();
                    ft.add(R.id.searchresult_framlayout, mSearchReasultInfomationFragment);
                }
//                if (null != mSearchReasultFunnyFragment) {
//                    ft.hide(mSearchReasultFunnyFragment);
//                }
                if (null != mSearchReasultStrategyFragment) {
                    ft.hide(mSearchReasultStrategyFragment);
                }
                if (null != mSearchReasultTestFragment) {
                    ft.hide(mSearchReasultTestFragment);
                }
                if (null != mSearchReasultInfomationFragment) {
                    ft.hide(mSearchReasultInfomationFragment);
                }
                ft.show(mSearchReasultInfomationFragment);
                break;
        }
        ft.commit();
    }
}
