package sinia.com.baihangeducation.mine;


import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.framwork.glide.ImageLoaderUtils;
import com.example.framwork.utils.UserInfo;

import sinia.com.baihangeducation.R;

import com.mcxtzhang.swipemenulib.base.BaseFragment;

import sinia.com.baihangeducation.MyApplication;
import sinia.com.baihangeducation.mine.presenter.GetBaseInfoPresenter;
import sinia.com.baihangeducation.mine.view.GetBaseInfoView;
import sinia.com.baihangeducation.supplement.base.Goto;
import sinia.com.baihangeducation.supplement.tool.BaseUtil;

/**
 * Created by Administrator on 2018.02.24.
 */

public class MineFragment extends BaseFragment implements View.OnClickListener, GetBaseInfoView {

    private UserInfo userInfo;
    private MyApplication application;

    private LinearLayout mMyResumeBaseInfo;     //我的简历基本信息
    private TextView mLogin;                    //登录
    private TextView mIsMessage;                    //是否有消息红点提示
    private TextView mMessage;                    //消息
    private TextView mSingleNum;                //签到加分
    private TextView mPartTimeNum;                //兼职加分
    private TextView mTraingNum;                //培训加分
    private TextView mInvitionNum;                //邀请加分
    private TextView mHelpOtherNum;                //互助加分
    private TextView mGrowth;                //我的成长
    private LinearLayout mSingle;                //签到
    private LinearLayout mPatrTime;                //兼职
    private LinearLayout mTraing;                //培训
    private LinearLayout mInvition;                //邀请
    private LinearLayout mHelpEachOher;                //互助
    private LinearLayout mBaseInfo;                //个人基本资料
    private TextView mTask;                         //任务
    private TextView mRealName;                         //实名认证
    private TextView mVIP;                         //vip
    private ImageView mGender;                         //性别
    private TextView mNickName;                         //昵称
    private TextView mMyInfoPercent;                         //个人信息百分之
    private LinearLayout mMyInfoSend;                 //个人信息我的投递
    private LinearLayout mMyInfoRealName;                 //个人信息 实名认证
    private LinearLayout mMyInfoWantJob;                 //个人信息 求职意向
    private LinearLayout mCoupons;                 //我的优惠券
    private LinearLayout mCollection;                 //我的收藏
    private LinearLayout mSend;                 //我的发布
    private LinearLayout mBuy;                 //我的购买
    private LinearLayout mSetting;                 //我的设置
    private TextView mGeXingSingle;                 //个性签名
    private ImageView mImg;                 //头像

    private GetBaseInfoPresenter presenter;

    @Override
    public int initLayoutResID() {
        return R.layout.fragment_mine;
    }

    /**
     * 初始化数据
     */
    @Override
    protected void initData() {
//        setView( );
    }

    private void setView(UserInfo userInfo) {


            mLogin.setVisibility(View.VISIBLE);
            mIsMessage.setVisibility(View.GONE);
            mMessage.setVisibility(View.GONE);
            mVIP.setVisibility(View.GONE);
            mGender.setVisibility(View.GONE);
            mSingleNum.setText("");
            mPartTimeNum.setText("");
            mTraingNum.setText("");
            mInvitionNum.setText("");
            mHelpOtherNum.setText("");
            mNickName.setText("用户名");
            mGeXingSingle.setText("个性签名");
            mRealName.setText("未实名");
            ImageLoaderUtils.displayRound(context, mImg, "", R.drawable.new_eorrlogo);

            mLogin.setVisibility(View.GONE);
            mIsMessage.setVisibility(View.GONE);
            mMessage.setVisibility(View.VISIBLE);
            mVIP.setVisibility(View.VISIBLE);
            mGender.setVisibility(View.VISIBLE);

            mSingleNum.setText(userInfo.growth_sign + "");
            mPartTimeNum.setText(userInfo.growth_job + "");
            mTraingNum.setText(userInfo.growth_train + "");
            mInvitionNum.setText(userInfo.growth_invite + "");
            mHelpOtherNum.setText(userInfo.growth_help + "");
            mNickName.setText(userInfo.nickname);
            mGeXingSingle.setText(userInfo.slogan);
            mRealName.setText(userInfo.real_status_name);
            mVIP.setText(userInfo.vip_level + "");
            if (userInfo.gender == 1) {
                mGender.setBackground(getResources().getDrawable(R.drawable.new_male));
            } else {
                mGender.setBackground(getResources().getDrawable(R.drawable.new_female));
            }
            ImageLoaderUtils.displayRound(context, mImg, userInfo.avatar, R.drawable.new_eorrlogo);
            if (userInfo.is_seed == 1) {
                mTask.setVisibility(View.VISIBLE);
            } else {
                mTask.setVisibility(View.GONE);
            }

        }

    @Override
    public void onResume() {
        super.onResume();
        application = (MyApplication) context.getApplication();


    }

    /**
     * 初始化控件
     */
    @Override
    protected void initView() {
        mMyResumeBaseInfo = $(R.id.fragment_mine_myresumebaseinfo);
        mLogin = $(R.id.fragment_mine_login);
        mIsMessage = $(R.id.fragment_mine_ismessage);
        mMessage = $(R.id.fragment_mine_message);
        mSingleNum = $(R.id.fragment_mine_singlenum);
        mPartTimeNum = $(R.id.fragment_mine_parttimenum);
        mTraingNum = $(R.id.fragment_mine_traingnum);
        mInvitionNum = $(R.id.fragment_mine_invitationnum);
        mHelpOtherNum = $(R.id.fragment_mine_helpeachothernum);
        mSingle = $(R.id.fragment_mine_single);
        mPatrTime = $(R.id.fragment_mine_parttime);
        mTraing = $(R.id.fragment_mine_traing);
        mInvition = $(R.id.fragment_mine_invitation);
        mHelpEachOher = $(R.id.fragment_mine_helpeachother);
        mBaseInfo = $(R.id.fragment_mine_baseinfo);
        mTask = $(R.id.fragment_mine_task);
        mRealName = $(R.id.fragment_mine_reallname);
        mVIP = $(R.id.fragment_mine_vip);
        mGender = $(R.id.fragment_mine_gender);
        mNickName = $(R.id.fragment_mine_nickname);
        mMyInfoPercent = $(R.id.fragment_mine_myinfopercent);
        mMyInfoSend = $(R.id.fragment_mine_send);
        mMyInfoRealName = $(R.id.fragment_mine_myinforealname);
        mMyInfoWantJob = $(R.id.fragment_mine_myinfowantjob);
        mCoupons = $(R.id.fragment_mine_mycoupons);
        mCollection = $(R.id.fragment_mine_mycollection);
        mSend = $(R.id.fragment_mine_mysend);
        mBuy = $(R.id.fragment_mine_buy);
        mSetting = $(R.id.fragment_mine_mysetting);
        mGeXingSingle = $(R.id.fragment_mine_gexingqianming);
        mImg = $(R.id.fragment_mine_img);
        mGrowth = $(R.id.fragment_mine_mygrowth);


        mBaseInfo.setOnClickListener(this);
        mMyResumeBaseInfo.setOnClickListener(this);
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Goto.toLogin(context);
            }
        });
        mMessage.setOnClickListener(this);
        mSingle.setOnClickListener(this);
        mPatrTime.setOnClickListener(this);
        mTraing.setOnClickListener(this);
        mInvition.setOnClickListener(this);
        mHelpEachOher.setOnClickListener(this);
        mTask.setOnClickListener(this);
        mRealName.setOnClickListener(this);
        mVIP.setOnClickListener(this);
        mGender.setOnClickListener(this);
        mNickName.setOnClickListener(this);
        mMyInfoPercent.setOnClickListener(this);
        mMyInfoSend.setOnClickListener(this);
        mMyInfoRealName.setOnClickListener(this);
        mMyInfoWantJob.setOnClickListener(this);
        mCoupons.setOnClickListener(this);
        mCollection.setOnClickListener(this);
        mSend.setOnClickListener(this);
        mBuy.setOnClickListener(this);
        mSetting.setOnClickListener(this);
        mGrowth.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (!BaseUtil.isLogin(context, application)) {
            return;
        }
        switch (v.getId()) {
            case R.id.fragment_mine_myresumebaseinfo:
                Goto.toMyResumeActivity(context);
                break;
            case R.id.fragment_mine_message:
                //消息
                Goto.toFragmentMessageActivity(context);
                break;
            case R.id.fragment_mine_baseinfo:
                //个人中心资料
                Goto.toUCentreBaseInfoActivity(context);
                break;
            case R.id.fragment_mine_mycoupons:
                //个人中心资料
                Goto.toMyCouponsActivity(context);
                break;
            case R.id.fragment_mine_mysetting:
                //设置
                Goto.toMySettingActivity(context);
                break;
            case R.id.fragment_mine_myinforealname:
                //实名认证
                Goto.toRealNameActivity(context);
                break;
            case R.id.fragment_mine_mysend:
                //我的发布
                Goto.toMyReleaseActivity(context);
                break;
            case R.id.fragment_mine_mycollection:
                //我的收藏
                Goto.toMyCollectionActivity(context);
                break;
            case R.id.fragment_mine_mygrowth:
                //我的c成长
                Goto.toMyGrowthActivity(context);
                break;
            case R.id.fragment_mine_single:
                //签到
                Goto.toSingleActivity(context);
                break;
            case R.id.fragment_mine_parttime:
                //兼职
                Goto.toPartTimeActivity(context);
                break;
            case R.id.fragment_mine_traing:
                //培训
                Goto.toTraingActivity(context);
                break;
            case R.id.fragment_mine_invitation:
                //邀请
//                Goto.toInvitationActivity(context);
                break;
            case R.id.fragment_mine_helpeachother:
                //互助
                Goto.toHelpEachOtherActivity(context);
                break;
            case R.id.fragment_mine_send:
                //我的投递
                Goto.toMySendActivity(context);
                break;
            case R.id.fragment_mine_myinfowantjob:
                //意向求职
                Goto.toWantJobActivity(context);
                break;
            case R.id.fragment_mine_task:
                //任务
                Goto.toTaskActivity(context);
                break;
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }


    @Override
    public void getGetBaseInfoSuccess(UserInfo userInfo) {
        setView(userInfo);
    }
}
