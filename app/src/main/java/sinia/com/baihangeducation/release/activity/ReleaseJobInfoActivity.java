package sinia.com.baihangeducation.release.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.example.framwork.utils.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.release.info.bean.SelectedDay;
import sinia.com.baihangeducation.supplement.alertview.AlertViewContorller;
import sinia.com.baihangeducation.supplement.alertview.OnDismissListener;
import sinia.com.baihangeducation.supplement.alertview.OnItemClickListener;
import sinia.com.baihangeducation.release.info.ReleaseJobInfoListInfo;
import sinia.com.baihangeducation.release.info.bean.JobEducationListInfo;
import sinia.com.baihangeducation.release.info.bean.JobExpListInfo;
import sinia.com.baihangeducation.release.info.bean.JobIndustryListInfo;
import sinia.com.baihangeducation.release.info.bean.JobSalaryListInfo;
import sinia.com.baihangeducation.release.info.bean.JobSexListInfo;
import sinia.com.baihangeducation.release.info.bean.JobTagListInfo;
import sinia.com.baihangeducation.release.info.bean.JobTypeListInfo;
import sinia.com.baihangeducation.release.presenter.ReleaseJobInfoPresenter;
import sinia.com.baihangeducation.release.presenter.ReleaseJobPresenter;
import sinia.com.baihangeducation.release.view.IReleaseJobInfoView;
import sinia.com.baihangeducation.release.view.ISendReleaseJobView;
import sinia.com.baihangeducation.supplement.base.BaseActivity;
import sinia.com.baihangeducation.supplement.base.Goto;
import sinia.com.baihangeducation.supplement.tool.PickerUtils;

import com.mcxtzhang.swipemenulib.customview.ChangeStyleNumberPicker;
import com.mcxtzhang.swipemenulib.customview.listdialog.ChangeISNOPicker;
import com.mcxtzhang.swipemenulib.utils.ACache;

/**
 * 企业发布职位
 */
public class ReleaseJobInfoActivity extends BaseActivity implements IReleaseJobInfoView, OnItemClickListener, ISendReleaseJobView {
    private ReleaseJobInfoPresenter presenter;
    private ReleaseJobPresenter releaseJobPresenter;
    private ACache cache;
    private RelativeLayout mJobChoiceLayout;        //职位选择layout
    private TextView mJobChoiceTextView;            //职位选择TextView
    private EditText mCompanyNameEditText;          //招聘名称EditText
    private RelativeLayout mWorkAdressLayout;       //工作地点layout
    private TextView mWorkAdressTextView;           //工作地点TextView
    private EditText mWorkAdressDetailEditText;     //工作地点详情EditText
    //    private EditText mNeedPeopleMinEditText;        //招聘人数最小EditText
    private RelativeLayout mEducationLayout;        //学历要求Layout
    private TextView mEducationTextView;            //学历要求TextView
    private RelativeLayout mExpLayout;              //经验要求Layout
    private TextView mExpTextView;                  //经验要求TextView
    private RelativeLayout mSalaryTypeLayout;       //薪资类型Layout
    private TextView mSalaryTypeTextView;           //薪资类型TextView
    private RelativeLayout mSalaryLayoutPartTime;   //薪资待遇兼职Layout
    private EditText mSalaryEditTextPartTime;       //兼职薪资待遇EditText
    private RelativeLayout mSalaryLayoutAllTime;    //全职薪资待遇Layout
    private EditText mSalaryMinEditTextAllTime;     //全职最小薪资待遇EditText
    private EditText mSalaryMaxEditTextAllTime;     //全职最大薪资待遇EditText
    private RelativeLayout mJobTypeLayout;          //岗位类型Layout
    private TextView mJobTypeTextView;              //岗位类型TextView
    private RelativeLayout mBelongIndutryLayout;    //所属行业Layout
    private TextView mBelongIndutryTextView;        //所属行业TextView
    private RelativeLayout mGenderLayout;           //性别选项Layout            仅兼职
    private TextView mGenderTextView;               //性别选项TextView
    //    private RelativeLayout mLanguageLayout;         //语言要求Layout
//    private EditText mLanguageEditText;             //语言要求EditText
    private RelativeLayout mJobTagLayout;           //职位标签Layout
    private TextView mJobTagTextView;               //职位标签TextView
    //    private RelativeLayout mWorkDateLayout;         //上班日期Layout            仅兼职
//    private TextView mWorkDateMinTextView;          //上班日期最小值TextView
//    private TextView mWorkDateMaxTextView;          //上班日期最大值TextView
//    private TextView mWorkTimeMinTextView;          //上班时间最小值TextView
    private TextView mWorkTimeMaxTextView;          //上班时间最大值TextView
    private RelativeLayout mLinkerLayout;           //联系人名字Layout
    private EditText mLinkerEditText;               //联系人名字EditText
    //    private EditText mLinkTelEditText;              //联系人电话EditText
    private EditText mContantEditText;              //联系人电话EditText
    private TextView mReleaseTextView;              //发布

    private String[] jobtype = {"全职", "兼职"};                  //1全职  2兼职
    private String[] jobLine = {"是", "否"};                  //1全职  2兼职
    private String[] linkerway = {"手机号", "QQ", "微信"};                  //1全职  2兼职
    private OptionsPickerView cityPicker;                       //城市选择器
    private TimePickerView timePicker;                          //日期选择器

    private List<JobEducationListInfo> job_education_list;       //学历要求
    private List<JobExpListInfo> job_experience_list;            //经验要求
    private List<JobIndustryListInfo> job_industry_list;         //岗位类型
    private List<JobSalaryListInfo> job_money_type_list;         //薪资类型
    private List<JobSexListInfo> job_sex_list;                   //性别要求
    private List<JobTagListInfo> job_tag_list;                   //职位标签
    private List<JobTypeListInfo> job_type_list;                 //所属行业

    private String mParmJobType = "1";              //职位type
    private String mProvinceId;                     //省份ID
    private String mCityId;                         //城市ID
    private String mDistrictId;                     //区ID
    private String indutryText;                     //所属行业名字
    private String indutryIndex = "1";                    //所属行业id
    private String tagText;                         //职位标签名字
    private String tagIndex;                        //职位标签id
    private String educationID = "1";                     //学历要求id
    private String expID;                           //经验要求id
    private String genderID;                        //性别要求id
    private String jobTypeID;                       //岗位类型要求id
    private String moneyID = "1";                         //薪资类型要求id
    private String highMoney;                       //薪资最高值

    //仿ios弹窗
    private RelativeLayout mAgeLimitRelative;
    private TextView mAgeLimitTextView;
    private RelativeLayout mInterviewLimitRelative;
    private TextView mInterviewTextView;
    private RelativeLayout mLinkerWayRelative;
    private TextView mLinkerWayTextView;
    private RelativeLayout mWorkTimeMaxLayout;
    private EditText mNeedPeopleMaxEditText;
    private EditText mContant;
    private int mPosition;
    private AlertViewContorller mAlertViewContorller;
    private AlertViewContorller mEducationAlertViewContorller;
    private AlertViewContorller mExpTextAlertViewContorller;
    private AlertViewContorller mSalaryTypeAlertViewContorller;
    private AlertViewContorller mJobTypeAlertViewContorller;
    private AlertViewContorller mGenderAlertViewContorller;
    private AlertViewContorller mBelongAlertViewContorller;
    private AlertViewContorller mInterviewAlertViewContorller;
    private ChangeISNOPicker changeISNOPicker;
    private StringBuffer stringBuffer;

    @Override
    public int initLayoutResID() {
        return R.layout.releasejob;
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (cache != null) {
            indutryText = cache.getAsString("belongindutry");
            mBelongIndutryTextView.setText(indutryText);
            indutryIndex = cache.getAsString("belongindutryindex");
            tagText = cache.getAsString("tagtext");
            mJobTagTextView.setText(tagText);
            tagIndex = cache.getAsString("tagindex");
        }
    }

    @Override
    protected void initData() {
        cache = ACache.get(context);
        job_education_list = new ArrayList<>();
        job_experience_list = new ArrayList<>();
        job_industry_list = new ArrayList<>();
        job_money_type_list = new ArrayList<>();
        job_sex_list = new ArrayList<>();
        job_tag_list = new ArrayList<>();
        job_type_list = new ArrayList<>();
        mCommonTitle.setCenterText(R.string.raleasejob);
        mCommonTitle.setBackgroundColor(Color.WHITE);
        releaseJobPresenter = new ReleaseJobPresenter(context, this);
        presenter = new ReleaseJobInfoPresenter(context, this);
        presenter.getJobOptionList();
//
//        if ((mJobChoiceTextView.getText().toString()).equals("全职")) {
//            mParmJobType = "1";
//            mWorkDateLayout.setVisibility(View.GONE);
//            mGenderLayout.setVisibility(View.GONE);
//            mSalaryLayoutPartTime.setVisibility(View.GONE);
//            mSalaryLayoutAllTime.setVisibility(View.VISIBLE);
//        } else {
//            mParmJobType = "2";
//            mWorkDateLayout.setVisibility(View.VISIBLE);
//            mGenderLayout.setVisibility(View.VISIBLE);
//            mSalaryLayoutPartTime.setVisibility(View.VISIBLE);
//            mSalaryLayoutAllTime.setVisibility(View.GONE);
//        }
    }

    @Override
    protected void initView() {


        mJobChoiceLayout = $(R.id.releasejob_jobchoice_layout);
        mJobChoiceTextView = $(R.id.releasejob_jobchoice_textview);

        mCompanyNameEditText = $(R.id.releasejob_companyname_edittext);
        mCompanyNameEditText.setCursorVisible(false);

        mWorkAdressLayout = $(R.id.releasejob_workadress_layout);
        mWorkAdressTextView = $(R.id.releasejob_workadress_textview);
        mWorkAdressDetailEditText = $(R.id.releasejob_workadressdetail_edittext);
        mNeedPeopleMaxEditText = $(R.id.releasejob_needpeoplemax_textview);
        mEducationLayout = $(R.id.releasejob_education_layout);
        mEducationTextView = $(R.id.releasejob_education_TextView);
        mExpLayout = $(R.id.releasejob_exp_layout);
        mExpTextView = $(R.id.releasejob_exp_textview);
        mSalaryTypeLayout = $(R.id.releasejob_salarytype_layout);
        mSalaryTypeTextView = $(R.id.releasejob_salarytype_textview);
        mSalaryLayoutPartTime = $(R.id.releasejob_salary_layout_parttime);
        mSalaryEditTextPartTime = $(R.id.releasejob_salary_edittext_parttime);
        mSalaryLayoutAllTime = $(R.id.releasejob_salarymin_layout_alltime);
        mSalaryMinEditTextAllTime = $(R.id.releasejob_salarymin_edittext_alltime);
        mSalaryMaxEditTextAllTime = $(R.id.releasejob_salarymax_edittext_alltime);
        mJobTypeLayout = $(R.id.releasejob_jobtype_layout);
        mJobTypeTextView = $(R.id.releasejob_jobtype_textview);
        mBelongIndutryLayout = $(R.id.releasejob_belongindutry_layout);
        mBelongIndutryTextView = $(R.id.releasejob_belongindutry_textview);
        mGenderLayout = $(R.id.releasejob_gender_layout);
        mGenderTextView = $(R.id.releasejob_gender_textview);
        mJobTagLayout = $(R.id.releasejob_jobtag_layout);
        mJobTagTextView = $(R.id.releasejob_jobtag_textview);
//        mWorkTimeMinTextView = $(R.id.releasejob_worktimemin_textview);
        mWorkTimeMaxTextView = $(R.id.releasejob_worktimemax_textview);
        mWorkTimeMaxLayout = $(R.id.releasejob_worktimemax_layout);
        mLinkerLayout = $(R.id.releasejob_linker_layout);
        mLinkerEditText = $(R.id.releasejob_linker_edittext);
        mContantEditText = $(R.id.releasejob_contant_edittext);
        mReleaseTextView = $(R.id.releasejob_release_textview);

        mAgeLimitRelative = $(R.id.releasejob_age_layout);
        mAgeLimitTextView = $(R.id.releasejob_age_textview);
        mInterviewLimitRelative = $(R.id.releasejob_need_interview_layout);
        mInterviewTextView = $(R.id.releasejob_need_interview_textview);
        mLinkerWayRelative = $(R.id.releasejob_linker_way_layout);
        mLinkerWayTextView = $(R.id.releasejob_linker_way_text);
        mContant = $(R.id.releasejob_memo_edittext);

        mJobChoiceLayout.setOnClickListener(this);
        mCompanyNameEditText.setOnClickListener(this);
        mWorkAdressLayout.setOnClickListener(this);
        mEducationLayout.setOnClickListener(this);
        mExpLayout.setOnClickListener(this);
        mSalaryTypeLayout.setOnClickListener(this);
        mJobTypeLayout.setOnClickListener(this);
        mBelongIndutryLayout.setOnClickListener(this);
        mJobTagLayout.setOnClickListener(this);
        mGenderLayout.setOnClickListener(this);

        mReleaseTextView.setOnClickListener(this);
        mAgeLimitRelative.setOnClickListener(this);
        mInterviewLimitRelative.setOnClickListener(this);
        mLinkerWayRelative.setOnClickListener(this);
        mWorkTimeMaxLayout.setOnClickListener(this);
        changeISNOPicker = new ChangeISNOPicker(ReleaseJobInfoActivity.this);
        changeISNOPicker.setAlertOnClickListener(new ChangeISNOPicker.AlertOnClickListener() {
            @Override
            public void alertClick(String age) {
//                android.widget.Toast.makeText(ReleaseJobInfoActivity.this, age, android.widget.Toast.LENGTH_LONG).show();
            }
        });
    }
    //    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
//            if (mAlertViewContorller != null && mAlertViewContorller.isShowing()) {
//                mAlertViewContorller.dismiss();
//                return false;
//            }
//        }
//        return super.onKeyDown(keyCode, event);
////    }
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
//            if (mAlertViewContorller != null && mAlertViewContorller.isShowing())
//                mAlertViewContorller.dismiss();
//            if (mAlertViewContorller != null && mAlertViewContorller.isShowing())
//                mAlertViewContorller.dismiss();
//            if (mAlertViewContorller != null && mAlertViewContorller.isShowing())
//                mAlertViewContorller.dismiss();
//            if (mAlertViewContorller != null && mAlertViewContorller.isShowing())
//                mAlertViewContorller.dismiss();
//            if (mAlertViewContorller != null && mAlertViewContorller.isShowing())
//                mAlertViewContorller.dismiss();
//
//            return false;
//
//        }
//        return super.onKeyDown(keyCode, event);
//    }


    @Override

    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.releasejob_age_layout:
                ChangeStyleNumberPicker changeStyleNumberPicker = new ChangeStyleNumberPicker(ReleaseJobInfoActivity.this);
                changeStyleNumberPicker.showAlertDialog();
                changeStyleNumberPicker.setAlertOnClickListener(new ChangeStyleNumberPicker.AlertOnClickListener() {
                    @Override
                    public void alertClick(String age) {
                        mAgeLimitTextView.setText(age);
                    }
                });
                break;

            case R.id.releasejob_jobchoice_layout:
                changeISNOPicker.showAlertDialog(Arrays.asList(jobtype), "职位选择", mJobChoiceTextView);

//                addAlerView(mJobChoiceTextView, "职位选择", jobtype);
                break;
            case R.id.releasejob_companyname_edittext:
                mCompanyNameEditText.setCursorVisible(true);
                break;
            case R.id.releasejob_workadress_layout:
                hideEditTextInput();
                getAddress(mWorkAdressTextView);
                cityPicker.show();
                break;
            case R.id.releasejob_education_layout:
                hideEditTextInput();
                String[] educationdata = new String[job_education_list.size()];
                for (int i = 0; i < job_education_list.size(); i++) {
                    educationdata[i] = job_education_list.get(i).education_name;
                }
                changeISNOPicker.showAlertDialog(Arrays.asList(educationdata), "学历要求", mEducationTextView);

//                addAlerView(mEducationTextView, "学历要求", educationdata);
                break;
            case R.id.releasejob_exp_layout:
                hideEditTextInput();
                String[] expdata = new String[job_experience_list.size()];
                for (int i = 0; i < job_experience_list.size(); i++) {
                    expdata[i] = job_experience_list.get(i).experience_name;
                }
                changeISNOPicker.showAlertDialog(Arrays.asList(expdata), "经验要求", mExpTextView);
//                addAlerView(mExpTextView, "经验要求", expdata);
                break;

            case R.id.releasejob_salarytype_layout:
                hideEditTextInput();
                String[] salarydata;
//                if ((mJobChoiceTextView.getText().toString()).equals("全职"))
//                    salarydata = new String[]{"月薪", "年薪"};
//                else
                salarydata = new String[]{"日结", "次日结", "周结", "月结"};
                changeISNOPicker.showAlertDialog(Arrays.asList(salarydata), "薪资待遇", mSalaryTypeTextView);
//                addAlerView(mSalaryTypeTextView, "薪资待遇", salarydata);
                break;

            case R.id.releasejob_jobtype_layout:
                hideEditTextInput();
                String[] jobtypedata = new String[job_industry_list.size()];
                for (int i = 0; i < job_industry_list.size(); i++) {
                    jobtypedata[i] = job_industry_list.get(i).industry_name;

                }
                changeISNOPicker.showAlertDialog(Arrays.asList(jobtypedata), "岗位类型", mJobTypeTextView);
//                addAlerView(mJobTypeTextView, "岗位类型", jobtypedata);

                break;

            case R.id.releasejob_belongindutry_layout:
                selectType = "是否连做";
                changeISNOPicker.showAlertDialog(Arrays.asList(jobLine), "是否连做", mBelongIndutryTextView);
//                addAlerView(mBelongIndutryTextView, "是否连做", jobLine);
                break;
            case R.id.releasejob_linker_way_layout: //联系方式
                changeISNOPicker.showAlertDialog(Arrays.asList(linkerway), "联系方式", mLinkerWayTextView);
//                addAlerView(mLinkerWayTextView, "联系方式", linkerway);

//                Goto.toBelongIndutryActivity(context); 所属行业 ==>  是否连做
                break;

            case R.id.releasejob_need_interview_layout:
                hideEditTextInput();
                changeISNOPicker.showAlertDialog(Arrays.asList(jobLine), "是否需要面试", mInterviewTextView);
//                addAlerView(mInterviewTextView, "是否需要面试", jobLine);

                break;
            case R.id.releasejob_jobtag_layout:
                hideEditTextInput();
                Goto.toTagActivity(context);
                break;
            case R.id.releasejob_gender_layout:
                hideEditTextInput();
                String[] genderdata = new String[job_sex_list.size()];
                for (int i = 0; i < job_sex_list.size(); i++) {
                    genderdata[i] = job_sex_list.get(i).sex_name;
                }
                changeISNOPicker.showAlertDialog(Arrays.asList(genderdata), "性别选择", mGenderTextView);
//                addAlerView(mGenderTextView, "性别选择", genderdata);
                break;

            case R.id.releasejob_worktimemax_layout:
                if ((mJobChoiceTextView.getText().toString()).equals("全职")) { //全职
                    hideEditTextInput();
                    timePicker = PickerUtils.initTimeHoursPicker(this, mWorkTimeMaxTextView);
                    timePicker.show();
                } else { //兼职
                    Intent intent = new Intent(ReleaseJobInfoActivity.this, DataSelectActivity.class);
                    ReleaseJobInfoActivity.this.startActivityForResult(intent, 0);
                }

                break;
            case R.id.releasejob_release_textview:
                mReleaseTextView.setVisibility(View.VISIBLE);
                hideEditTextInput();
                if (!TextUtils.isEmpty(mSalaryTypeTextView.getText().toString().trim())) {
                    for (int i = 0; i < job_money_type_list.size(); i++) {
                        if (mSalaryTypeTextView.getText().toString().trim().equals(job_money_type_list.get(i).money_type_name)) {
                            //     moneyID = job_money_type_list.get(i).money_type_id + "";

                        }
                    }
                }
                releaseJobPresenter.releaseJob("");
                break;
        }
    }

    private String selectType = "";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        //当requestCode,resultCode同时为0时,也就是处理特定的结果

        stringBuffer = new StringBuffer();
        System.out.println("上班时间====" + requestCode + "resultCode" + resultCode);
        if (requestCode == 0 && resultCode == 200) {

            ArrayList<SelectedDay> selectedDayList = (ArrayList<SelectedDay>) intent.getSerializableExtra("daylist");
            if (selectedDayList == null || selectedDayList.size() < 0) {
                return;
            }
            for (int i = 0; i < selectedDayList.size(); i++) {
                stringBuffer.append(selectedDayList.get(i).year);
                stringBuffer.append("-");
                stringBuffer.append(Integer.valueOf(selectedDayList.get(i).month) + 1 + "");
                stringBuffer.append("-");
                stringBuffer.append(selectedDayList.get(i).day);
                stringBuffer.append(",");
            }
            stringBuffer.deleteCharAt(stringBuffer.length() - 1);
            System.out.println("上班时间" + stringBuffer.toString());
            mWorkTimeMaxTextView.setText("已设置");
        }
    }

    private void hideEditTextInput() {
        hideSoftInput(mCompanyNameEditText);
        hideSoftInput(mWorkAdressDetailEditText);
        hideSoftInput(mNeedPeopleMaxEditText);
        hideSoftInput(mSalaryEditTextPartTime);
        hideSoftInput(mSalaryMinEditTextAllTime);
        hideSoftInput(mSalaryMaxEditTextAllTime);
        hideSoftInput(mLinkerEditText);
        hideSoftInput(mContantEditText);
    }

    /**
     * pop弹框
     *
     * @param textView
     * @param title
     * @param data
     */
    private void addAlerView(TextView textView, String title, String[] data) {

        System.out.println("是否连做11new");
        AlertViewContorller alertViewContorller = new AlertViewContorller(textView, title, null, "取消", null, data, context, AlertViewContorller.Style.ActionSheet, this);
        alertViewContorller.setCancelable(true).show();
    }

    private void getAddress(TextView view) {
        cityPicker = PickerUtils.initCityPicker(this, view, new PickerUtils.OnCityClickListener() {
            @Override
            public void onCityClick(String provinceId, String cityId, String districtId) {
                mProvinceId = provinceId;
                mCityId = cityId;
                mDistrictId = districtId;
            }
        });
    }

    @Override
    public void getReleaseJobInfoSuccess(ReleaseJobInfoListInfo info) {

        if (info != null) {
            //学历
            if (info.job_education_list != null && info.job_education_list.size() > 0) {
                job_education_list = info.job_education_list;
                mEducationTextView.setText(job_education_list.get(0).education_name);
                educationID = job_education_list.get(0).education_id + "";
            }
            //经验
            if (info.job_experience_list != null && info.job_experience_list.size() > 0) {
                job_experience_list = info.job_experience_list;
//                mExpTextView.setText(job_experience_list.get(0).experience_name);
                expID = job_experience_list.get(0).experience_id + "";
            }
            //岗位类型
            if (info.job_industry_list != null && info.job_industry_list.size() > 0) {
                job_industry_list = info.job_industry_list;
                //  mJobTypeTextiew.setText(job_industry_list.get(0).industry_name);
                jobTypeID = job_industry_list.get(0).industry_id + "";
            }
            //薪资类型
            if (info.job_money_type_list != null && info.job_money_type_list.size() > 0) {
                job_money_type_list = info.job_money_type_list;
            }
            //性别选择
            if (info.job_sex_list != null && info.job_sex_list.size() > 0) {
                job_sex_list = info.job_sex_list;
//                mGenderTextView.setText(job_sex_list.get(0).sex_name);
                genderID = job_sex_list.get(0).sex_id + "";
            }
            //职位标签
            if (info.job_tag_list != null && info.job_tag_list.size() > 0) {
                job_tag_list = info.job_tag_list;
            }
            //所属行业
            if (info.job_type_list != null && info.job_type_list.size() > 0) {
                job_type_list = info.job_type_list;

            }
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
    public void onItemClick(View view, List<String> mOthers, Object o, int position) {
        if (position == -1) {
//            mAlertViewContorller.dismiss();
        } else {

            mPosition = position;
            TextView view1 = (TextView) view;
            view1.setText(mOthers.get(position));
            if ((mOthers.get(position)).equals("全职")) {
                mParmJobType = "1";

                mGenderLayout.setVisibility(View.GONE);
                mSalaryLayoutPartTime.setVisibility(View.GONE);
                mSalaryLayoutAllTime.setVisibility(View.VISIBLE);
            } else if ((mOthers.get(position)).equals("兼职")) {
                mParmJobType = "2";
                mGenderLayout.setVisibility(View.VISIBLE);
                mSalaryLayoutPartTime.setVisibility(View.VISIBLE);
                mSalaryLayoutAllTime.setVisibility(View.GONE);
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        //所属行业缓存清除
        cache.put("belongindutry", "");
        cache.put("belongindutryindex", "");
        //职位标签缓存清除
        cache.put("tagtext", "");
        cache.put("tagindex", "");
    }


    @Override
    public String getReleaseType() {
        return "2";
//        if ((mJobChoiceTextView.getText().toString()).equals("全职")) {
//            return "1";
//        } else {
//            return "2";
//        }
    }

    @Override
    public String getJobTypeIds() {
        if (job_industry_list != null)
            indutryIndex = String.valueOf(job_industry_list.get(mPosition).industry_id);
        return indutryIndex;
    }

    @Override
    public String getTagIds() {
        return tagIndex;
    }

    @Override
    public String getIndustryId() {
        return jobTypeID;
    }

    @Override
    public String getReleaseTitle() {
        return mCompanyNameEditText.getText().toString().trim();
    }

    @Override
    public String getReleaseContent() {
        return mContantEditText.getText().toString().trim();
    }

    @Override
    public String getNumLower() {
        return "";
    }

    @Override
    public String getNumUpper() {
        return mNeedPeopleMaxEditText.getText().toString().trim(); //不要
    }

    @Override
    public String getMoneyType() {
        // {"money_type_id":"1 "月结"},{"money_type_id":"2 "日结"},{"money_type_id":"4", 周结"},{"money_type_id":"5","money_type_name":"次日结"}
        if ((mJobChoiceTextView.getText().toString()).equals("全职")) { //全职
            String type = mSalaryTypeTextView.getText().toString().trim();
            if (type.equals("年薪"))
                return "7";
            if (type.equals("月薪"))
                return "6";

        } else {
            String type = mSalaryTypeTextView.getText().toString().trim();
            if (type.equals("月结")) {
                moneyID = "1";
            }
            if (type.equals("日结")) {
                moneyID = "2";
            }
            if (type.equals("周结")) {
                moneyID = "4";
            }
            if (type.equals("次日结")) {
                moneyID = "5";
            }
            return moneyID;
        }
        return moneyID;
    }


    @Override
    public String getMoneyLower() {
        return mSalaryMaxEditTextAllTime.getText().toString().trim();
    }

    @Override
    public String getMoneyUpper() {
        if (mParmJobType == "1") {
            highMoney = mSalaryMaxEditTextAllTime.getText().toString().trim();
        } else {
            highMoney = mSalaryEditTextPartTime.getText().toString().trim();
        }
        return highMoney;
    }

    @Override
    public String getLanguage() {
        return "1";
    }

    @Override
    public String getEducationId() {
        return educationID;
    }

    @Override
    public String getExperienceId() {
        return expID;
    }

    @Override
    public String getSexId() {
        return genderID;
    }

    @Override
    public String getDateStart() {
        return "1";
    }

    @Override
    public String getDateEnd() {
        return "";
    }

    @Override
    public String getTimeStart() {
        return "1";
    }

    @Override
    public String getTimeEnd() {
        return "2";
    }

    @Override
    public String getProvId() {
        return mProvinceId;
    }

    @Override
    public String getReleaseCityId() {
        return mCityId;
    }


    @Override
    public String getDistId() {
        return mDistrictId;
    }

    @Override
    public String getAddress() {
        return mWorkAdressDetailEditText.getText().toString().trim();
    }

    @Override
    public String getLinkPerson() {
        return mLinkerEditText.getText().toString().trim();
    }

    @Override
    public String getLinkPhone() {
        return mContantEditText.getText().toString().trim();
    }

    @Override
    public String getEditID() {
        return null;
    }

    public String getAgeLower() {
        if (!mAgeLimitTextView.getText().toString().equals("")) {
            if (mAgeLimitTextView.getText().toString().equals("不限")) {
                return "16";
            }
            String[] age = mAgeLimitTextView.getText().toString().split("-");
            return Integer.valueOf(age[0]) < Integer.valueOf(age[1]) ? age[0] : age[1];
        }
        return "16";
    }

    public String getAgeUpper() {
        if (!mAgeLimitTextView.getText().toString().equals("")) {
            if (mAgeLimitTextView.getText().toString().equals("不限")) {
                return "16";
            }
            String[] age = mAgeLimitTextView.getText().toString().split("-");
            return Integer.valueOf(age[0]) < Integer.valueOf(age[1]) ? age[1] : age[0];
        }
        return "16";
    }

    public String getIsContinue() {
        return mBelongIndutryTextView.getText().toString();
    }

    @Override
    public String getTimeGroup() {
        return "";
    }

    public String getIsInterview() {
        return mInterviewTextView.getText().toString().equals("是") ? "1" : "2";
    }

    public String getLinkType() {
        return mLinkerWayTextView.getText().toString();
    }

    public String getGender() {
        return mGenderTextView.getText().toString();
    }

    public String getExp() {
        return mExpTextView.getText().toString();
    }

    public String getSalaryType() {
        return mSalaryTypeTextView.getText().toString();
    }

    public String getJobType() {
        return mJobTypeTextView.getText().toString();
    }

    public String getJobTag() {
        return mJobTagTextView.getText().toString();
    }

    public String getContant() {
        return mContant.getText().toString();
    }

    public String getJobTime() {
        if (stringBuffer == null)
            return "";
        return stringBuffer.toString().trim();
    }

    @Override
    public void getReleaseSuccess() {
        mReleaseTextView.setVisibility(View.VISIBLE);
        Toast.getInstance().showSuccessToast(context, "发布成功");
        finish();
    }

    @Override
    public void getReleaseFail() {
        mReleaseTextView.setVisibility(View.VISIBLE);
    }
}
