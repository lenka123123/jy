package sinia.com.baihangeducation.mine.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.supplement.base.BaseActivity;

import sinia.com.baihangeducation.mine.HelpEachOtherFragment;
import sinia.com.baihangeducation.mine.InterestingFragment;
import sinia.com.baihangeducation.supplement.base.Goto;

/**
 * Created by Administrator on 2018/4/10.
 */

public class MyReleaseActivity extends BaseActivity {

    private FrameLayout ll;
    private RadioButton mHelp;
    private RadioButton mInteresting;
    private LinearLayout mBack;
    private ImageView mAdd;

    private FragmentManager mFragmentManager;
    private HelpEachOtherFragment mHELPFragment;
    private InterestingFragment mFunFragment;

    @Override
    public int initLayoutResID() {
        return R.layout.fragment_mine_myrelease;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragmentManager = getSupportFragmentManager();

        FragmentTransaction ft = mFragmentManager.beginTransaction();
        mHELPFragment = new HelpEachOtherFragment();
        ft.add(R.id.fragment_mine_myrelease_frame, mHELPFragment);
        ft.commit();
    }

    @Override
    protected void initData() {

//        mCommonTitle.setCenterText(R.string.myrelease);
//        mCommonTitle.setBackground(getResources().getDrawable(R.drawable.new_realname_title_bg));
//        TextView rightTxt = mCommonTitle.getRightTxt();
//        rightTxt.setBackground(getResources().getDrawable(R.drawable.new_add));
//        rightTxt.setVisibility(View.VISIBLE);
//        rightTxt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Goto.toMyRelease_ReleaseActivity(context);
//            }
//        });
    }

    @Override
    protected void initView() {
        ll = $(R.id.fragment_mine_myrelease_frame);
        mBack = $(R.id.fragment_mine_myrelease_back);
        mAdd = $(R.id.fragment_mine_myrelease_add);

        mHelp = $(R.id.fragment_mine_myrelease_help);
        mInteresting = $(R.id.fragment_mine_myrelease_interesting);
        mHelp.setOnClickListener(this);
        mInteresting.setOnClickListener(this);
        mBack.setOnClickListener(this);
        mAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        FragmentTransaction ft = mFragmentManager.beginTransaction(); //开启一个事务
        switch (v.getId()) {
            case R.id.fragment_mine_myrelease_help:
                if (null == mHELPFragment) {
                    mHELPFragment = new HelpEachOtherFragment();
                    ft.add(R.id.fragment_mine_myrelease_frame, mHELPFragment);
                }
                if (null != mFunFragment) {
                    ft.hide(mFunFragment);
                }
                ft.show(mHELPFragment);
                break;
            case R.id.fragment_mine_myrelease_interesting:
                if (null == mFunFragment) {
                    mFunFragment = new InterestingFragment();
                    ft.add(R.id.fragment_mine_myrelease_frame, mFunFragment);
                }
                if (null != mHELPFragment) {
                    ft.hide(mHELPFragment);
                }
                ft.show(mFunFragment);
                break;
            case R.id.fragment_mine_myrelease_back:
                finish();
                break;
                case R.id.fragment_mine_myrelease_add:
                    Goto.toMyRelease_ReleaseActivity(context);
                break;
        }
        ft.commit();   //提交事务
    }
}
