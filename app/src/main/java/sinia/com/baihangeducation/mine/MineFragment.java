package sinia.com.baihangeducation.mine;


import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.framwork.glide.ImageLoaderUtils;
import com.example.framwork.utils.SpCommonUtils;
import com.example.framwork.utils.UserInfo;

import cn.jpush.im.android.api.JMessageClient;
import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.MainActivity;
import sinia.com.baihangeducation.R;

import com.mcxtzhang.swipemenulib.base.BaseFragment;

import java.io.File;

import sinia.com.baihangeducation.MyApplication;
import sinia.com.baihangeducation.mine.presenter.GetBaseInfoPresenter;
import sinia.com.baihangeducation.mine.presenter.UpdateVersionPresenter;
import sinia.com.baihangeducation.mine.view.GetBaseInfoView;
import sinia.com.baihangeducation.supplement.base.Goto;
import sinia.com.baihangeducation.supplement.tool.BaseUtil;

/**
 * Created by Administrator on 2018.02.24
 */

public class MineFragment extends BaseFragment implements View.OnClickListener, GetBaseInfoView {

    private MyApplication application;

    private LinearLayout mMyResumeBaseInfo;     //我的简历基本信息
    private TextView mLogin;                    //登录
    private TextView mIsMessage;                //是否有消息红点提示
    private RelativeLayout chat_layout;         //消息
    private TextView mSingleNum;                //签到加分
    private TextView mPartTimeNum;              //兼职加分
    private TextView mTraingNum;                //培训加分
    private TextView mInvitionNum;              //邀请加分
    private TextView mHelpOtherNum;             //互助加分
    //    private TextView mGrowth;             //我的成长
    private LinearLayout mSingle;               //签到
    private LinearLayout mPatrTime;             //兼职
    private LinearLayout mTraing;               //培训
    private LinearLayout mInvition;             //邀请
    private LinearLayout mHelpEachOher;         //互助
    //    private LinearLayout mBaseInfo;       //个人基本资料
    private TextView mTask;                     //任务
    private TextView mRealName;                 //实名认证
    private TextView mVIP;                      //vip
    private ImageView mGender;                  //性别
    //    private TextView mNickName;                         //昵称
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


    private TextView no_read_num;
    private ImageView logoImg;
    private ImageView logo_man;
    private TextView textViewName;
    private boolean isCreated = false;
    private ImageView logo;
    private GetBaseInfoPresenter getBaseInfoPresenter;
    private UpdateVersionPresenter updateVersionPresenter;


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        AppConfig.HOMT = false;
        AppConfig.PART = false;
        AppConfig.CHAT = false;
        AppConfig.CLUB = false;
        AppConfig.ME = true;

        if (isCreated) {
//            setDrawableOPpen();
            if (getBaseInfoPresenter != null)
                getBaseInfoPresenter.getBaseInfoLoginAfter();
        }
    }

    public void setReSatart() {
        if (!AppConfig.ME) return;
        if (isCreated) {
//            setDrawableOPpen();
            System.out.println("====切换账号=====");
            if (getBaseInfoPresenter != null) {
                getBaseInfoPresenter.getBaseInfoLoginAfter();
                System.out.println("====切换账号=====");
            }

            if (updateVersionPresenter != null) {
                updateVersionPresenter.getBaseInfo(this);
                System.out.println("====切换账号=====");
            }

        }
    }

    public void setDrawableOPpen() {
        String no_read_num_es = (String) SpCommonUtils.get(getActivity(), AppConfig.FINAL_NO_READ_NUM, "");
        String train_num = (String) SpCommonUtils.get(getActivity(), AppConfig.FINAL_NUM_TRAIN_NUM, "");
        String full_job_num = (String) SpCommonUtils.get(getActivity(), AppConfig.FINAL_NUM_FULL_JOB_NUM, "");
        String part_job_num = (String) SpCommonUtils.get(getActivity(), AppConfig.FINAL_NUM_FULL_PARK_NUM, "");
        String help_num = (String) SpCommonUtils.get(getActivity(), AppConfig.FINAL_NUM_FULL_HULP_NUM, "");
        String nickname = (String) SpCommonUtils.get(getActivity(), AppConfig.FINAL_NUM_FULL_HULP_NICKNAME, "");
        int type = (int) SpCommonUtils.get(getActivity(), AppConfig.FINAL_NUM_FULL_TYPE, 1);
        int auth_status = (int) SpCommonUtils.get(getActivity(), AppConfig.FINAL_NUM_FULL_AUTH_STATUS, 1);
        String avatar = (String) SpCommonUtils.get(getActivity(), AppConfig.FINALUAVATAR, "");
        String gender = (String) SpCommonUtils.get(getActivity(), AppConfig.FINALGENDEREEE, "1");

        int vip_level = (int) SpCommonUtils.get(getActivity(), AppConfig.FINAL_NUM_FULL_VIP_LEVEL, 1);

        if (no_read_num_es.equals("0")) {
            no_read_num.setVisibility(View.GONE);
        } else {
            no_read_num.setText(" " + no_read_num_es + " ");
            no_read_num.setVisibility(View.VISIBLE);
        }

        if (!AppConfig.LOGINPHOTOTPATH.equals("")) {
            avatar = AppConfig.LOGINPHOTOTPATH;
        }

        Glide.with(getActivity()).load(avatar).asBitmap().error(R.drawable.new_eorrlogo).centerCrop().into(new BitmapImageViewTarget(logoImg) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(getActivity().getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                logoImg.setImageDrawable(circularBitmapDrawable);
            }
        });
        if (Integer.valueOf(gender) == 1) {
            Glide.with(getActivity()).load("").error(R.drawable.new_male).into(logo_man);
        } else {
            Glide.with(getActivity()).load("").error(R.drawable.moman).into(logo_man);
        }
        textViewName.setText(nickname);
        mRealName.setText(auth_status == 1 ? "未认证" : "已认证");
        mVIP.setText("Lv." + vip_level);
    }


    @Override
    public int initLayoutResID() {
        return R.layout.fragment_mine;
    }

    /**
     * 初始化数据
     */
    @Override
    protected void initData() {
        isCreated = true;
        updateVersionPresenter = new UpdateVersionPresenter(getActivity(), null);
        updateVersionPresenter.getBaseInfo(this);

        getBaseInfoPresenter = new GetBaseInfoPresenter(getActivity(), this);

        getBaseInfoPresenter.getBaseInfoLoginAfter();


//        setView( );
    }

    public void showUserInfoSuccess(UserInfo myinfo) {
        //签到数
        mSingleNum.setText(myinfo.my_num.sign_num + "");
        //兼职数
        mPartTimeNum.setText(myinfo.my_num.part_job_num + "");
        //培训数
        mTraingNum.setText(myinfo.my_num.train_num + "");
        //互助数
//        mHelpOtherNum.setText(myinfo.my_num.help_num + "");
        mHelpOtherNum.setText("0");

        SpCommonUtils.put(context, AppConfig.FINALUAVATAR, myinfo.avatar);
        SpCommonUtils.put(context, AppConfig.FINALNICKNAME, myinfo.nickname);
        SpCommonUtils.put(context, AppConfig.FINALSLOGAN, myinfo.slogan);
        SpCommonUtils.put(context, AppConfig.FINALGENDEREEE, String.valueOf(myinfo.gender));
        SpCommonUtils.put(context, AppConfig.FINALEMEMAIL, myinfo.email);

        if (!AppConfig.LOGINPHOTOTPATH.equals("")) {
            AppConfig.LOGINPHOTOTPATH = myinfo.avatar;
        }

        Glide.with(getActivity()).load(myinfo.avatar).asBitmap().error(R.drawable.new_eorrlogo).centerCrop().into(new BitmapImageViewTarget(logoImg) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(getActivity().getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                logoImg.setImageDrawable(circularBitmapDrawable);
            }
        });


        if (Integer.valueOf(myinfo.gender) == 1) {
            Glide.with(getActivity()).load("").error(R.drawable.new_male).into(logo_man);
        } else {
            Glide.with(getActivity()).load("").error(R.drawable.moman).into(logo_man);
        }
        textViewName.setText(myinfo.nickname);
        mRealName.setText(myinfo.auth_status == 1 ? "未认证" : "已认证");
        mVIP.setText("Lv." + myinfo.vip_level);

    }

    public void gsetBaseInfoSuccess(UserInfo userInfo) {
        String no_read_num_es = "0";
        int num = Integer.valueOf(userInfo.no_read_num);
        if (num < 1) {
            num = 0;
        }
        no_read_num_es = num + "";
        if (no_read_num != null) {
            if (no_read_num_es.equals("0")) {
                no_read_num.setVisibility(View.GONE);
            } else {
                no_read_num.setText(" " + no_read_num_es + " ");
                no_read_num.setVisibility(View.VISIBLE);
            }
        }
    }


    private void setView(UserInfo userInfo) {
        ImageLoaderUtils.displayRound(getActivity(), mImg, "", R.drawable.new_eorrlogo);

        mLogin.setVisibility(View.GONE);
        mIsMessage.setVisibility(View.GONE);
        mVIP.setVisibility(View.VISIBLE);
        mGender.setVisibility(View.VISIBLE);

        mSingleNum.setText(userInfo.growth_sign + "");
        mPartTimeNum.setText(userInfo.growth_job + "");
        mTraingNum.setText(userInfo.growth_train + "");
        mInvitionNum.setText(userInfo.growth_invite + "");
        mHelpOtherNum.setText(userInfo.growth_help + "");
        textViewName.setText(userInfo.nickname);
        mGeXingSingle.setText(userInfo.slogan);


        mVIP.setText(userInfo.vip_level + "");
        if (userInfo.gender == 1) {
            mGender.setBackground(getResources().getDrawable(R.drawable.new_male));
        } else {
            mGender.setBackground(getResources().getDrawable(R.drawable.new_female));
        }
        ImageLoaderUtils.displayRound(getActivity(), mImg, userInfo.avatar, R.drawable.new_eorrlogo);
        if (userInfo.is_seed == 1) {
            mTask.setVisibility(View.VISIBLE);
        } else {
            mTask.setVisibility(View.GONE);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        application = (MyApplication) getActivity().getApplication();
    }

    /**
     * 初始化控件
     */
    @Override
    protected void initView() {
        logoImg = $(R.id.logo);
        logo_man = $(R.id.logo_man);
        textViewName = $(R.id.name);
        logoImg.setOnClickListener(this);
        no_read_num = $(R.id.news_home);
        mMyResumeBaseInfo = $(R.id.fragment_mine_myresumebaseinfo);
        mLogin = $(R.id.fragment_mine_login);
        mIsMessage = $(R.id.fragment_mine_ismessage);
        chat_layout = $(R.id.fragment_mine_message);
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
        logo = $(R.id.logo);
        mTask = $(R.id.fragment_mine_task);
        mRealName = $(R.id.fragment_mine_reallname);
        mVIP = $(R.id.fragment_mine_vip);
        mGender = $(R.id.fragment_mine_gender);
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


        logo.setOnClickListener(this);
        mMyResumeBaseInfo.setOnClickListener(this);
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Goto.toLogin(getActivity());
            }
        });
        chat_layout.setOnClickListener(this);
        mSingle.setOnClickListener(this);
        mPatrTime.setOnClickListener(this);
        mTraing.setOnClickListener(this);
        mInvition.setOnClickListener(this);
        mHelpEachOher.setOnClickListener(this);
        mTask.setOnClickListener(this);
        mRealName.setOnClickListener(this);
        mGender.setOnClickListener(this);
        mMyInfoPercent.setOnClickListener(this);
        mMyInfoSend.setOnClickListener(this);
        mMyInfoRealName.setOnClickListener(this);
        mMyInfoWantJob.setOnClickListener(this);
        mCoupons.setOnClickListener(this);
        mCollection.setOnClickListener(this);
        mSend.setOnClickListener(this);
        mBuy.setOnClickListener(this);
        mSetting.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (!BaseUtil.isLogin(getActivity(), application)) {
            return;
        }
        switch (v.getId()) {
            case R.id.fragment_mine_myresumebaseinfo:
                Goto.toMyResumeActivity(getActivity());
                break;
            case R.id.fragment_mine_message:
                //消息
                Goto.toFragmentMessageActivity(getActivity());
                break;
            case R.id.fragment_mine_baseinfo:
                //个人中心资料
                Goto.toUCentreBaseInfoActivity(getActivity());
                break;
            case R.id.logo:
                //个人中心资料
                Goto.toUCentreBaseInfoActivity(getActivity());
//                Goto.toMyCouponsActivity(getActivity());
                break;
            case R.id.fragment_mine_mysetting:
                //设置
                Goto.toMySettingActivity(getActivity());
                break;
            case R.id.fragment_mine_myinforealname:
                //实名认证
                Goto.toRealNameActivity(getActivity());
                break;
            case R.id.fragment_mine_mysend:
                // 购买
                Goto.toMyPayActivity(getActivity());
                break;
            case R.id.fragment_mine_mycollection:
                //我的收藏
                Goto.toMyCollectionActivity(getActivity());
                break;
//            case R.id.fragment_mine_mygrowth:
//                //我的c成长
//                Goto.toMyGrowthActivity(getActivity());
//                break;
            case R.id.fragment_mine_single:
                //签到
                Goto.toSingleActivity(getActivity());
                break;
            case R.id.fragment_mine_parttime:
                //my 兼职
                Goto.toMySendCommentActivity(getActivity());
                break;
            case R.id.fragment_mine_traing:
                //培训
                Goto.toTraingActivity(getActivity());
                break;
            case R.id.fragment_mine_invitation:
                //邀请
//                Goto.toInvitationActivity(getActivity());
                break;
            case R.id.fragment_mine_helpeachother:
                //互助
                Goto.toHelpEachOtherActivity(getActivity());
                break;
            case R.id.fragment_mine_send:
                //我的投递
                Goto.toMySendActivity(getActivity());
                break;
            case R.id.fragment_mine_myinfowantjob:
                //我的金库
                Goto.toMyCoffersActivity(getActivity());
                break;
            case R.id.fragment_mine_task:
                //任务
                Goto.toTaskActivity(getActivity());
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
