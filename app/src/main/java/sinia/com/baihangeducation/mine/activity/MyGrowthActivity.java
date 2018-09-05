package sinia.com.baihangeducation.mine.activity;

import android.view.View;
import android.widget.TextView;

import com.example.framwork.widget.customtoolbar.CommonTitle;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.supplement.base.BaseActivity;
import sinia.com.baihangeducation.supplement.base.Goto;

/**
 * 我的成长值
 */

public class MyGrowthActivity extends BaseActivity {

    protected static CommonTitle mCommonTitle;   //头部布局
    private View line;

    private TextView mSingleDailyMore;          //签到查看
    private TextView mDoPartTime;               //兼职查看
    private TextView mJoinTraing;               //培训查看
    private TextView mInviteFriends;            //邀请好友查看
    private TextView mSchoolHelp;               //校园互助查看

    @Override
    public int initLayoutResID() {
        return R.layout.mygrowth;
    }

    @Override
    protected void initData() {
        mCommonTitle.setCenterText(R.string.mygroeth);
        mCommonTitle.setBackgroundColor(getResources().getColor(R.color.color_00ffffff));
    }

    @Override
    protected void initView() {
        mCommonTitle = findViewById(R.id.title_bar);
        line = $(R.id.title_line);
        line.setVisibility(View.GONE);

        mSingleDailyMore = $(R.id.mygrowth_getgrowthsingledailymore);
        mDoPartTime = $(R.id.mygrowth_getgrowthdoparttimemore);
        mJoinTraing = $(R.id.mygrowth_getgrowthjointraingmore);
        mInviteFriends = $(R.id.mygrowth_getgrowthinvitefriendmore);
        mSchoolHelp = $(R.id.mygrowth_getgrowthinschoolhelpmore);

        mSingleDailyMore.setOnClickListener(this);
        mDoPartTime.setOnClickListener(this);
        mJoinTraing.setOnClickListener(this);
        mInviteFriends.setOnClickListener(this);
        mSchoolHelp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.mygrowth_getgrowthsingledailymore:
                //签到
                Goto.toSingleActivity(context);
                break;
            case R.id.mygrowth_getgrowthdoparttimemore:
                //兼职
                Goto.toPartTimeActivity(context);
                break;
            case R.id.mygrowth_getgrowthjointraingmore:
                //培训
                Goto.toTraingActivity(context);
                break;
            case R.id.mygrowth_getgrowthinvitefriendmore:
                //邀请好友
//                Goto.toInvitationActivity(context);
                break;
            case R.id.mygrowth_getgrowthinschoolhelpmore:
                //校园互助
                Goto.toHelpEachOtherActivity(context);
                break;
        }
    }
}
