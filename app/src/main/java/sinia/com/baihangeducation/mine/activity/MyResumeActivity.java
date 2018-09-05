package sinia.com.baihangeducation.mine.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.framwork.utils.SpCommonUtils;

import java.util.ArrayList;
import java.util.List;

import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.supplement.base.BaseActivity;
import sinia.com.baihangeducation.mine.adapter.JobExpAdapter;

import com.mcxtzhang.swipemenulib.info.bean.MyResumInfo;
import com.mcxtzhang.swipemenulib.info.bean.MyResumInfoforResume;
import com.mcxtzhang.swipemenulib.info.bean.MyResumeJobInfo;

import sinia.com.baihangeducation.mine.presenter.GetMyResumePresenter;
import sinia.com.baihangeducation.mine.presenter.SubmitMyResumePresent;
import sinia.com.baihangeducation.mine.view.IGetMyResumeView;
import sinia.com.baihangeducation.supplement.base.Goto;

import com.mcxtzhang.swipemenulib.customview.MyScrollView;
import com.mcxtzhang.swipemenulib.customview.NoScrollListView;

/**
 * 我的简历页面
 */

public class MyResumeActivity extends BaseActivity implements IGetMyResumeView, MyScrollView.OnScrollListener {

    private Intent intent;
    private String fromFlag;

    private GetMyResumePresenter mGetMyResumePresenter;
    private View line;
    private MyScrollView mMyScrollView;


    private ImageView mMyResumeImg;         //用户头像
    private TextView mName;                 //用户姓名
    private TextView mCompeleWorkCount;                 //完工次数
    private TextView mUnCompeleWorkCount;                 //放鸽子次数
    private TextView mHelpEachOtherCount;                 //互助次数
    private TextView mCompletePercent;                 //完成度百分比
    private LinearLayout mEditBaseInfo;                 //编辑基本资料
    private TextView mBaseInfoName;                 //基本资料 名字
    private TextView mBaseInfoGender;                 //基本资料 性别
    private TextView mBaseInfoJob;                 //基本资料 职业
    private TextView mBaseInfoTel;                 //基本资料 电话
    private TextView mBaseInfoBrithday;                 //基本资料 生日
    private TextView mBaseInfoAdress;                 //基本资料 地址
    private LinearLayout mEditEducationExp;             //编辑教育经历
    private TextView mEducationExpEducation;            //教育经历 学历
    private TextView mEducationExpMajor;            //教育经历 专业
    private TextView mEducationExpSchoolName;            //教育经历 学校名称
    private LinearLayout mEditJobExp;                   //编辑工作经历
    private NoScrollListView mJobExpListView;                   //工作经历listview
    private LinearLayout mEditSelfAssessment;           //编辑自我评价
    private TextView mSelfAssesment;           //自我评价
    private LinearLayout mUpStudentCard;            //上传学生证
    private TextView mIsUpStudentCard;                  //是否已上传学生证
    private LinearLayout mUpHealthCard;             //上传健康证
    private TextView mIsUpHealthCard;           //是否已上传健康证
    private List<MyResumeJobInfo> job_exp = new ArrayList<>();
    private JobExpAdapter jobExpAdapter;

    private SubmitMyResumePresent submitMyResumePresent;


    @Override
    public int initLayoutResID() {
        return R.layout.myresume;
    }

    @Override
    protected void initData() {
        intent = getIntent();
        AppConfig.SCHOOLNAME = "";
        AppConfig.SCHOOLMAGOR = "";
        AppConfig.SCHOOLEDUCTION = "";
        fromFlag = intent.getStringExtra("FROM");
        mGetMyResumePresenter = new GetMyResumePresenter(context, this, MyResumeActivity.this);
        mGetMyResumePresenter.getMyResume();

        setDefaultData();
    }


    @Override
    protected void onResume() {
        super.onResume();
        // TODO: 2018/7/10 0010    setData();
    }

    @Override
    protected void initView() {

        mMyResumeImg = $(R.id.my_resume_img);
        line = $(R.id.title_line);
        line.setVisibility(View.GONE);
        mMyScrollView = $(R.id.myresume_myscrollview);
        submitMyResumePresent = new SubmitMyResumePresent(context);

        //因为涉及到动态改变头部布局的透明度，所以这里从新实例化头部


//        mCommonTitle.setBackgroundColor(Color.WHITE);
//        mCommonTitle.getBackground().setAlpha(0);
//        mCommonTitle.setCenterText(R.string.myresume);
//        mCommonTitle.getRightTxt().setTextSize(18);
////        mCommonTitle.getRightTxt().setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
//
        if (mCommonTitle != null) {
            mCommonTitle.setBackgroundColor(Color.WHITE);
            mCommonTitle.setCenterText("简历中心");
            mCommonTitle.setRightText("提交");
            mCommonTitle.getRightTxt().setTextColor(Color.BLACK);

            TextView rightTxt = mCommonTitle.getRightTxt();
            rightTxt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("点击提交========");

                    submitMyResumePresent.submutresume(3);

//
//                    if (!TextUtils.isEmpty(fromFlag) && fromFlag.equals("ADD")) {
//                        submitMyResumePresent.submutresume(fromFlag);
//                    }


                }
            });

        }

        mName = $(R.id.myresume_name);              //用户姓名
//        mCompeleWorkCount = $(R.id.myresume_completeworkcount);//完工次数
//        mUnCompeleWorkCount = $(R.id.myresume_uncompleteworkcount);//放鸽子次数
//        mHelpEachOtherCount = $(R.id.myresume_helpeachothercount);//互助次数
//        mCompletePercent = $(R.id.myresume_completepercent);//完成度百分比
        mEditBaseInfo = $(R.id.myresume_editbaseinfo);//编辑基本资料
        mBaseInfoName = $(R.id.myresume_baseinfo_name);// 基本资料 名字
        mBaseInfoGender = $(R.id.myresume_baseinfo_gender);//基本资料 性别
        mBaseInfoJob = $(R.id.myresume_baseinfo_job);//基本资料 职业
        mBaseInfoTel = $(R.id.myresume_baseinfo_telnum);//基本资料 电话
        mBaseInfoBrithday = $(R.id.myresume_baseinfo_brithday);//基本资料 生日
        mBaseInfoAdress = $(R.id.myresume_baseinfo_adress);//基本资料 地址
        mEditEducationExp = $(R.id.myresume_editeducation_exp);//编辑教育经历;
        mEducationExpEducation = $(R.id.myresumen_education_exp_education);//教育经历 学历
        mEducationExpMajor = $(R.id.myresumen_education_exp_major);//教育经历 专业
        mEducationExpSchoolName = $(R.id.myresume_editeducation_exp_schoolname);//教育经历 学校名称
        mEditJobExp = $(R.id.myresume_editjob_exp);//编辑工作经历
        mJobExpListView = $(R.id.myresume_jobexp_listview);//工作经历listview
        mEditSelfAssessment = $(R.id.myresume_editselfassessment);//编辑自我评价
        mSelfAssesment = $(R.id.myresume_evaluation);
        mUpStudentCard = $(R.id.myresume_upstudentcard);//上传学生证
        mIsUpStudentCard = $(R.id.myresume_idupstudentcard);//是否已上传学生证
        mUpHealthCard = $(R.id.myresume_uphealthcard);//上传健康证
        mIsUpHealthCard = $(R.id.myresume_isuphealthcard);//是否已上传健康证


        mMyScrollView.setScrolListener(this);
        mEditBaseInfo.setOnClickListener(this);
        mEditEducationExp.setOnClickListener(this);
        mEditSelfAssessment.setOnClickListener(this);
        mUpStudentCard.setOnClickListener(this);
        mUpHealthCard.setOnClickListener(this);
        mEditJobExp.setOnClickListener(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        if (!MyResumInfo.name.equals("")) {
            mBaseInfoName.setText(MyResumInfo.name);
        }
        if (!MyResumInfo.gender_name.equals("0")) {
            mBaseInfoGender.setText(MyResumInfo.gender_name);
        }
        if (!MyResumInfo.graduated_name.equals("0")) {
            mBaseInfoJob.setText(MyResumInfo.graduated_name);
        }
        if (!MyResumInfo.tel.equals("0")) {
            mBaseInfoTel.setText(MyResumInfo.tel);
        }
        if (!MyResumInfo.birthday.equals("0")) {
            mBaseInfoBrithday.setText(MyResumInfo.birthday);
        }
        if (!MyResumInfo.address.equals("0")) {
            mBaseInfoAdress.setText(MyResumInfo.address);
        }


        if (!AppConfig.educationtext.equals("")) {
            mEducationExpEducation.setText(AppConfig.educationtext);
        }
        if (!AppConfig.SCHOOLMAGOR.equals("")) {
            mEducationExpMajor.setText(AppConfig.SCHOOLMAGOR);
        }
        if (!AppConfig.SCHOOLNAME.equals("")) {
            mEducationExpSchoolName.setText(AppConfig.SCHOOLNAME);
        }
        if (!MyResumInfo.evaluation.equals("0")) {
            mSelfAssesment.setText(MyResumInfo.evaluation);
        }


        //工作经历未写


    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.myresume_editbaseinfo:
                Goto.toEditMyResumeBaseInfo(context);
                break;
            case R.id.myresume_editeducation_exp:
                Goto.toEditMyResumeEducationExp(context);
                break;
            case R.id.myresume_upstudentcard:
                Goto.toEditMyResumeStudentCard(context);
                break;
            case R.id.myresume_uphealthcard:
                Goto.toEditMyResumeHealthCard(context);
                break;
            case R.id.myresume_editselfassessment: //编辑自我评价
                Goto.toMyReumeSelfAssessmentActivity(context);
                break;
            case R.id.myresume_editjob_exp: //工作经历
                Goto.toMyResumeJobInfoActivity(context);
                break;
        }
    }

    @Override
    public void onScroll(int scrollY) {
        Log.i("检测滑动距离", scrollY + "");
        if (scrollY < 100) {

            mCommonTitle.getBackground().setAlpha(0);
        } else if (scrollY >= 100 && scrollY < 860) {
            mCommonTitle.getBackground().setAlpha((scrollY - 100) / 3);
        } else {
            mCommonTitle.getBackground().setAlpha(255);
        }
    }

    @Override
    public void showLoading() {
        showProgress();
    }

    @Override
    public void hideLoading() {
        hideProgress();
    }

    @Override
    public void getMyResumeSuccess(MyResumInfoforResume myResumInfos) {
        setData(myResumInfos);
    }

    @Override
    public void getMyResumeFail() {

    }

    /**
     * @param myResumInfos
     */
    private void setData(MyResumInfoforResume myResumInfos) {
        if (myResumInfos != null) {
            String avatar = (String) SpCommonUtils.get(context, AppConfig.FINALUAVATAR, "");

            if (!AppConfig.LOGINPHOTOTPATH.equals("")) {
                avatar = AppConfig.LOGINPHOTOTPATH;
            }

            Glide.with(context).load(avatar).asBitmap().centerCrop().error( R.drawable.new_eorrlogo).into(new BitmapImageViewTarget(mMyResumeImg) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable =
                            RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    mMyResumeImg.setImageDrawable(circularBitmapDrawable);
                }
            });

//            System.out.println(" myResumInfos.user_avatar");
//            if (myResumInfos.user_avatar == null && myResumInfos.user_avatar.equals("")) {
//                if (!Config.avatar.equals(""))
//                    ImageLoaderUtils.displayRound(context, mMyResumeImg, Config.avatar, R.drawable.new_eorrlogo);
//            } else {
//                ImageLoaderUtils.displayRound(context, mMyResumeImg, myResumInfos.user_avatar, R.drawable.new_eorrlogo);
//            }


//            info.put("name", MyResumInfo.name);
//            info.put("gender", MyResumInfo.gender);
//            info.put("graduated", MyResumInfo.graduated);
//            info.put("birthday", MyResumInfo.birthday);
//            info.put("province_id", MyResumInfo.province_id);
//            info.put("city_id", MyResumInfo.city_id);
//            info.put("area_id", MyResumInfo.area_id);
//            info.put("address", MyResumInfo.address);
//            info.put("tel", MyResumInfo.tel);

//            public static List<MyResumeJobInfo> job_exp;                     //工作经历


            mName.setText(myResumInfos.user_nickname);
            MyResumInfo.user_nickname = myResumInfos.user_nickname;
            mBaseInfoName.setText(myResumInfos.name);
            MyResumInfo.name = myResumInfos.name;
            mBaseInfoGender.setText(myResumInfos.gender_name);
            MyResumInfo.gender_name = myResumInfos.gender_name;
            MyResumInfo.gender = myResumInfos.gender;

            mBaseInfoJob.setText(myResumInfos.graduated_name);
            MyResumInfo.graduated_name = myResumInfos.graduated_name;
            mBaseInfoTel.setText(myResumInfos.tel);
            MyResumInfo.tel = myResumInfos.tel;
            mBaseInfoBrithday.setText(myResumInfos.birthday);
            MyResumInfo.birthday = myResumInfos.birthday;
            mBaseInfoAdress.setText(myResumInfos.address);
            MyResumInfo.address = myResumInfos.address;
            MyResumInfo.graduated = myResumInfos.graduated;
            MyResumInfo.area_id = myResumInfos.area_id;
            MyResumInfo.province_id = myResumInfos.province_id;
            MyResumInfo.city_id = myResumInfos.city_id;
            MyResumInfo.area_name = myResumInfos.area_name;
            MyResumInfo.area_name = myResumInfos.area_name;
            MyResumInfo.job_exp = myResumInfos.job_exp;


            mEducationExpEducation.setText(myResumInfos.education_exp.education_name);
            MyResumInfo.education_exp = myResumInfos.education_exp;
            mEducationExpMajor.setText(myResumInfos.education_exp.major_name);
            mEducationExpSchoolName.setText(myResumInfos.education_exp.school_name);
            AppConfig.SCHOOLNAME = myResumInfos.education_exp.school_name;
            AppConfig.SCHOOLMAGOR = myResumInfos.education_exp.major_name;
            AppConfig.educationtext = myResumInfos.education_exp.education_name;

            mSelfAssesment.setText(myResumInfos.evaluation);
            MyResumInfo.evaluation = myResumInfos.evaluation;

            MyResumInfo.student_photo = myResumInfos.student_photo;
            if (myResumInfos.student_photo != null && !myResumInfos.student_photo.isEmpty() && !myResumInfos.student_photo.equals("")) {
                if (myResumInfos.student_photo.substring(0, 4).equals("http")) {
                    mIsUpStudentCard.setVisibility(View.VISIBLE);
                    mIsUpStudentCard.setText("已上传");
                } else {
                    mIsUpStudentCard.setVisibility(View.VISIBLE);
                    mIsUpStudentCard.setText("已选择");
                }
            } else {
                mIsUpStudentCard.setVisibility(View.GONE);
            }
            MyResumInfo.health_photo = myResumInfos.health_photo;
            if (myResumInfos.health_photo != null && !myResumInfos.health_photo.isEmpty() && !myResumInfos.health_photo.equals("")) {
                if (myResumInfos.health_photo.substring(0, 4).equals("http")) {
                    mIsUpHealthCard.setVisibility(View.VISIBLE);
                    mIsUpHealthCard.setText("已上传");
                } else {
                    mIsUpHealthCard.setVisibility(View.VISIBLE);
                    mIsUpHealthCard.setText("已选择");
                }
            } else {
                mIsUpHealthCard.setVisibility(View.GONE);

            }

            // TODO: 2018/7/10 0010    job_exp = myResumInfos.job_exp;
            jobExpAdapter = new JobExpAdapter(context, job_exp);
            mJobExpListView.setAdapter(jobExpAdapter);

        }

    }

    private void setDefaultData() {


        if (!MyResumInfo.name.equals("")) {
            mBaseInfoName.setText(MyResumInfo.name);
        }
        if (!MyResumInfo.gender_name.equals("")) {
            mBaseInfoGender.setText(MyResumInfo.gender_name);
        }
        if (!MyResumInfo.graduated_name.equals("")) {
            mBaseInfoJob.setText(MyResumInfo.graduated_name);
        }
        if (!MyResumInfo.tel.equals("")) {
            mBaseInfoTel.setText(MyResumInfo.tel);
        }
        if (!MyResumInfo.birthday.equals("")) {
            mBaseInfoBrithday.setText(MyResumInfo.birthday);
        }
        if (!MyResumInfo.address.equals("")) {
            mBaseInfoAdress.setText(MyResumInfo.address);
        }
        if (!AppConfig.SCHOOLEDUCTION.equals("")) {
            mEducationExpEducation.setText(AppConfig.SCHOOLEDUCTION);
        }
        if (!AppConfig.SCHOOLMAGOR.equals("")) {
            mEducationExpMajor.setText(AppConfig.SCHOOLMAGOR);
        }
        if (!AppConfig.SCHOOLNAME.equals("")) {
            mEducationExpSchoolName.setText(AppConfig.SCHOOLNAME);
        }
    }
}
