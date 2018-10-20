package sinia.com.baihangeducation.mine.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RadioButton;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.mine.MyCollectionMessageFragment;
import sinia.com.baihangeducation.supplement.base.BaseActivity;
import sinia.com.baihangeducation.mine.MyCollectionAllTimeFragment;
import sinia.com.baihangeducation.mine.MyCollectionFunnyFragment;
import sinia.com.baihangeducation.mine.MyCollectionInformationFragment;
import sinia.com.baihangeducation.mine.MyCollectionPartTimeFragment;
import sinia.com.baihangeducation.mine.MyCollectionSecretFragment;
import sinia.com.baihangeducation.mine.MyCollectionStrategyFragment;
import sinia.com.baihangeducation.mine.MyCollectionTestFragment;
import sinia.com.baihangeducation.mine.presenter.MyCollectionPresenter;

/**
 * 我的收藏
 */

public class MyCollectionActivity extends BaseActivity {
    private int countpage = 1;
    private int itemnum = 20;
    private String type = 1 + "";       //收藏类型（1：兼职 2：全职；3：趣事；4:攻略 5试卷 6秘籍 7资讯）
    private MyCollectionPresenter presenter;

    private RadioButton mPartTime;     //兼职
    private RadioButton mAllTime;      //全职
    private RadioButton mFunny;        //趣事
    private RadioButton mStrategy;     //攻略
    private RadioButton mTest;         //试卷
    private RadioButton mSecret;       //秘籍
    private RadioButton mInformation;  //资讯
    private RadioButton mMessage;  //资讯

    private FragmentManager mFragmentManager;

    private MyCollectionMessageFragment mMyCollectionMessageFragment;         //资讯Fragment
    private MyCollectionPartTimeFragment mMyCollectionPartTimeFragment;         //兼职Fragment
    private MyCollectionAllTimeFragment mMyCollectionAllTimeFragment;           //全职Fragment
    private MyCollectionFunnyFragment mMyCollectionFunnyFragment;               //趣事Fragment
    private MyCollectionStrategyFragment mMyCollectionStrategyFragment;         //攻略Fragment
    private MyCollectionTestFragment mMyCollectionTestFragment;                 //试卷Fragment
    private MyCollectionSecretFragment mMyCollectionSecretFragment;             //秘籍Fragment
    private MyCollectionInformationFragment mMyCollectionInformationFragment;   //资讯Fragment

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragmentManager = getSupportFragmentManager();

        FragmentTransaction ft = mFragmentManager.beginTransaction();
//        mMyCollectionPartTimeFragment = new MyCollectionPartTimeFragment();
        mMyCollectionAllTimeFragment = new MyCollectionAllTimeFragment();
        ft.add(R.id.fragment_mine_collection_framlayout, mMyCollectionAllTimeFragment);
        ft.commit();
    }

    @Override
    public int initLayoutResID() {
        return R.layout.fragment_mine_collection;
    }

    @Override
    protected void initData() {
        mCommonTitle.setCenterText(R.string.mine_mycollection);
        mCommonTitle.setBackground(getResources().getDrawable(R.color.white));
    }

    @Override
    protected void initView() {
        mPartTime = $(R.id.fragment_mine_collection_parttime);
        mAllTime = $(R.id.fragment_mine_collection_alltime);
        mFunny = $(R.id.fragment_mine_collection_funny);
        mStrategy = $(R.id.fragment_mine_collection_strategy);
        mTest = $(R.id.fragment_mine_collection_test);
        mSecret = $(R.id.fragment_mine_collection_secret);
        mInformation = $(R.id.fragment_mine_collection_information);
        mMessage = $(R.id.fragment_mine_message_information);

        mAllTime.setChecked(true);
        mPartTime.setOnClickListener(this);
        mAllTime.setOnClickListener(this);
        mFunny.setOnClickListener(this);
        mStrategy.setOnClickListener(this);
        mTest.setOnClickListener(this);
        mSecret.setOnClickListener(this);
        mInformation.setOnClickListener(this);
        mMessage.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        FragmentTransaction ft = mFragmentManager.beginTransaction(); //开启一个事务
        switch (v.getId()) {
            case R.id.fragment_mine_collection_parttime:
                //兼职
                if (null == mMyCollectionPartTimeFragment) {
                    mMyCollectionPartTimeFragment = new MyCollectionPartTimeFragment();
                    ft.add(R.id.fragment_mine_collection_framlayout, mMyCollectionPartTimeFragment);
                }
                if (null != mMyCollectionAllTimeFragment) {
                    ft.hide(mMyCollectionAllTimeFragment);
                }
                if (null != mMyCollectionFunnyFragment) {
                    ft.hide(mMyCollectionFunnyFragment);
                }
                if (null != mMyCollectionStrategyFragment) {
                    ft.hide(mMyCollectionStrategyFragment);
                }
                if (null != mMyCollectionTestFragment) {
                    ft.hide(mMyCollectionTestFragment);
                }
                if (null != mMyCollectionSecretFragment) {
                    ft.hide(mMyCollectionSecretFragment);
                }
                if (null != mMyCollectionInformationFragment) {
                    ft.hide(mMyCollectionInformationFragment);
                }
                if (null != mMyCollectionMessageFragment) {
                    ft.hide(mMyCollectionMessageFragment);
                }
                ft.show(mMyCollectionPartTimeFragment);
                break;
            case R.id.fragment_mine_collection_alltime:
                //全职
                if (null == mMyCollectionAllTimeFragment) {
                    mMyCollectionAllTimeFragment = new MyCollectionAllTimeFragment();
                    ft.add(R.id.fragment_mine_collection_framlayout, mMyCollectionAllTimeFragment);
                }

                if (null != mMyCollectionPartTimeFragment) {
                    ft.hide(mMyCollectionPartTimeFragment);
                }
                if (null != mMyCollectionFunnyFragment) {
                    ft.hide(mMyCollectionFunnyFragment);
                }
                if (null != mMyCollectionStrategyFragment) {
                    ft.hide(mMyCollectionStrategyFragment);
                }
                if (null != mMyCollectionTestFragment) {
                    ft.hide(mMyCollectionTestFragment);
                }
                if (null != mMyCollectionSecretFragment) {
                    ft.hide(mMyCollectionSecretFragment);
                }
                if (null != mMyCollectionInformationFragment) {
                    ft.hide(mMyCollectionInformationFragment);
                }
                if (null != mMyCollectionMessageFragment) {
                    ft.hide(mMyCollectionMessageFragment);
                }
                ft.show(mMyCollectionAllTimeFragment);
                break;
            case R.id.fragment_mine_collection_funny:
                //趣事
                if (null == mMyCollectionFunnyFragment) {
                    mMyCollectionFunnyFragment = new MyCollectionFunnyFragment();
                    ft.add(R.id.fragment_mine_collection_framlayout, mMyCollectionFunnyFragment);
                }

                if (null != mMyCollectionPartTimeFragment) {
                    ft.hide(mMyCollectionPartTimeFragment);
                }
                if (null != mMyCollectionAllTimeFragment) {
                    ft.hide(mMyCollectionAllTimeFragment);
                }
                if (null != mMyCollectionStrategyFragment) {
                    ft.hide(mMyCollectionStrategyFragment);
                }
                if (null != mMyCollectionTestFragment) {
                    ft.hide(mMyCollectionTestFragment);
                }
                if (null != mMyCollectionSecretFragment) {
                    ft.hide(mMyCollectionSecretFragment);
                }
                if (null != mMyCollectionInformationFragment) {
                    ft.hide(mMyCollectionInformationFragment);
                }
                if (null != mMyCollectionMessageFragment) {
                    ft.hide(mMyCollectionMessageFragment);
                }
                ft.show(mMyCollectionFunnyFragment);
                break;
            case R.id.fragment_mine_collection_strategy:
                //攻略
                if (null == mMyCollectionStrategyFragment) {
                    mMyCollectionStrategyFragment = new MyCollectionStrategyFragment();
                    ft.add(R.id.fragment_mine_collection_framlayout, mMyCollectionStrategyFragment);
                }
                if (null != mMyCollectionPartTimeFragment) {
                    ft.hide(mMyCollectionPartTimeFragment);
                }
                if (null != mMyCollectionAllTimeFragment) {
                    ft.hide(mMyCollectionAllTimeFragment);
                }
                if (null != mMyCollectionFunnyFragment) {
                    ft.hide(mMyCollectionFunnyFragment);
                }
                if (null != mMyCollectionTestFragment) {
                    ft.hide(mMyCollectionTestFragment);
                }
                if (null != mMyCollectionSecretFragment) {
                    ft.hide(mMyCollectionSecretFragment);
                }
                if (null != mMyCollectionInformationFragment) {
                    ft.hide(mMyCollectionInformationFragment);
                }
                if (null != mMyCollectionMessageFragment) {
                    ft.hide(mMyCollectionMessageFragment);
                }
                ft.show(mMyCollectionStrategyFragment);
                break;
            case R.id.fragment_mine_collection_test:
                //试卷
                if (null == mMyCollectionTestFragment) {
                    mMyCollectionTestFragment = new MyCollectionTestFragment();
                    ft.add(R.id.fragment_mine_collection_framlayout, mMyCollectionTestFragment);
                }
                if (null != mMyCollectionPartTimeFragment) {
                    ft.hide(mMyCollectionPartTimeFragment);
                }
                if (null != mMyCollectionAllTimeFragment) {
                    ft.hide(mMyCollectionAllTimeFragment);
                }
                if (null != mMyCollectionFunnyFragment) {
                    ft.hide(mMyCollectionFunnyFragment);
                }
                if (null != mMyCollectionStrategyFragment) {
                    ft.hide(mMyCollectionStrategyFragment);
                }
                if (null != mMyCollectionSecretFragment) {
                    ft.hide(mMyCollectionSecretFragment);
                }
                if (null != mMyCollectionInformationFragment) {
                    ft.hide(mMyCollectionInformationFragment);
                }
                if (null != mMyCollectionMessageFragment) {
                    ft.hide(mMyCollectionMessageFragment);
                }
                ft.show(mMyCollectionTestFragment);
                break;
            case R.id.fragment_mine_collection_secret:
                //秘籍
                if (null == mMyCollectionSecretFragment) {
                    mMyCollectionSecretFragment = new MyCollectionSecretFragment();
                    ft.add(R.id.fragment_mine_collection_framlayout, mMyCollectionSecretFragment);
                }

                if (null != mMyCollectionPartTimeFragment) {
                    ft.hide(mMyCollectionPartTimeFragment);
                }
                if (null != mMyCollectionAllTimeFragment) {
                    ft.hide(mMyCollectionAllTimeFragment);
                }
                if (null != mMyCollectionFunnyFragment) {
                    ft.hide(mMyCollectionFunnyFragment);
                }
                if (null != mMyCollectionStrategyFragment) {
                    ft.hide(mMyCollectionStrategyFragment);
                }
                if (null != mMyCollectionTestFragment) {
                    ft.hide(mMyCollectionTestFragment);
                }
                if (null != mMyCollectionInformationFragment) {
                    ft.hide(mMyCollectionInformationFragment);
                }
                if (null != mMyCollectionMessageFragment) {
                    ft.hide(mMyCollectionMessageFragment);
                }
                ft.show(mMyCollectionSecretFragment);
                break;
            case R.id.fragment_mine_collection_information:
                //资讯
                if (null == mMyCollectionInformationFragment) {
                    mMyCollectionInformationFragment = new MyCollectionInformationFragment();
                    ft.add(R.id.fragment_mine_collection_framlayout, mMyCollectionInformationFragment);
                }

                if (null != mMyCollectionPartTimeFragment) {
                    ft.hide(mMyCollectionPartTimeFragment);
                }
                if (null != mMyCollectionAllTimeFragment) {
                    ft.hide(mMyCollectionAllTimeFragment);
                }
                if (null != mMyCollectionFunnyFragment) {
                    ft.hide(mMyCollectionFunnyFragment);
                }
                if (null != mMyCollectionStrategyFragment) {
                    ft.hide(mMyCollectionStrategyFragment);
                }
                if (null != mMyCollectionTestFragment) {
                    ft.hide(mMyCollectionTestFragment);
                }
                if (null != mMyCollectionSecretFragment) {
                    ft.hide(mMyCollectionSecretFragment);
                }

                if (null != mMyCollectionMessageFragment) {
                    ft.hide(mMyCollectionMessageFragment);
                }
                ft.show(mMyCollectionInformationFragment);
                break;


            case R.id.fragment_mine_message_information:
                //培训
                if (null == mMyCollectionMessageFragment) {
                    mMyCollectionMessageFragment = new MyCollectionMessageFragment();
                    ft.add(R.id.fragment_mine_collection_framlayout, mMyCollectionMessageFragment);
                }

                if (null != mMyCollectionPartTimeFragment) {
                    ft.hide(mMyCollectionPartTimeFragment);
                }
                if (null != mMyCollectionAllTimeFragment) {
                    ft.hide(mMyCollectionAllTimeFragment);
                }
                if (null != mMyCollectionFunnyFragment) {
                    ft.hide(mMyCollectionFunnyFragment);
                }
                if (null != mMyCollectionStrategyFragment) {
                    ft.hide(mMyCollectionStrategyFragment);
                }
                if (null != mMyCollectionTestFragment) {
                    ft.hide(mMyCollectionTestFragment);
                }
                if (null != mMyCollectionSecretFragment) {
                    ft.hide(mMyCollectionSecretFragment);
                }
                if (null != mMyCollectionMessageFragment) {
                    ft.hide(mMyCollectionMessageFragment);
                }
                ft.show(mMyCollectionMessageFragment);
                break;
        }
        ft.commit();   //提交事务
    }
}
