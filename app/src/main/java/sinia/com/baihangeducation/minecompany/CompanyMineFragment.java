package sinia.com.baihangeducation.minecompany;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.framwork.glide.ImageLoaderUtils;
import com.example.framwork.utils.UserInfo;

import sinia.com.baihangeducation.R;

import com.mcxtzhang.swipemenulib.base.BaseFragment;

import sinia.com.baihangeducation.MyApplication;
import sinia.com.baihangeducation.supplement.base.Goto;

public class CompanyMineFragment extends BaseFragment {
    private RelativeLayout mMessage;            //消息
    private ImageView mHeadIcon;                //头像
    private TextView mName;                     //名字
    private TextView mCompanyName;              //公司名字
    private LinearLayout mMyReleasePartTime;    //我发布的兼职
    private LinearLayout mMyReleaseAllTime;     //我发布的全职
    private LinearLayout mMyReleaseTraining;    //我发布的培训
    private LinearLayout mMyCollection;         //我的收藏
    private LinearLayout mMyCompanyInfo;        //我的企业信息
    private LinearLayout mMySetting;            //我的设置


    @Override
    public int initLayoutResID() {
        return R.layout.fragment_mine_company;
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onResume() {
        super.onResume();


    }

    @Override
    protected void initView() {
        mMessage = $(R.id.fragment_mine_company_message);
        mHeadIcon = $(R.id.fragment_mine_company_headicon);
        mName = $(R.id.fragment_mine_company_name);
        mCompanyName = $(R.id.fragment_mine_company_compangname);
        mMyReleasePartTime = $(R.id.fragment_mine_company_myreleaseparttime);
        mMyReleaseAllTime = $(R.id.fragment_mine_company_myreleasealltime);
        mMyReleaseTraining = $(R.id.fragment_mine_company_myreleasetraining);
        mMyCollection = $(R.id.fragment_mine_company_mycollection);
        mMyCompanyInfo = $(R.id.fragment_mine_company_mycompanyinfo);
        mMySetting = $(R.id.fragment_mine_company_mysetting);

        mMessage.setOnClickListener(this);
        mMyReleasePartTime.setOnClickListener(this);
        mMyReleaseAllTime.setOnClickListener(this);
        mMyReleaseTraining.setOnClickListener(this);
        mMyCollection.setOnClickListener(this);
        mMyCompanyInfo.setOnClickListener(this);
        mMySetting.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.fragment_mine_company_message:
                Goto.toFragmentMessageActivity(context);
                break;
            case R.id.fragment_mine_company_myreleaseparttime:
                Goto.toCompanyUCenterReleasePartTimeActivity(context);
                break;
            case R.id.fragment_mine_company_myreleasealltime:
                Goto.toCompanyUCenterReleaseAllTimeActivity(context);
                break;
            case R.id.fragment_mine_company_myreleasetraining:
                Goto.toCompanyUCenterReleaseTrainingActivity(context);
                break;
            case R.id.fragment_mine_company_mycollection:
                //我的收藏
                Goto.toMyCollectionActivity(context);
                break;
            case R.id.fragment_mine_company_mycompanyinfo:
                //企业信息
                Goto.toCompanyUCenterInfoActivity(context);
                break;
            case R.id.fragment_mine_company_mysetting:
                //设置
                Goto.toMySettingActivity(context);
                break;
        }

    }
}
