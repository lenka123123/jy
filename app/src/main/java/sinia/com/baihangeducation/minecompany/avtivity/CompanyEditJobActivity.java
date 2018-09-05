package sinia.com.baihangeducation.minecompany.avtivity;

import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.example.framwork.utils.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.release.presenter.CompanyPresenter;
import sinia.com.baihangeducation.release.view.ISendCompanyJobView;
import sinia.com.baihangeducation.supplement.alertview.AlertViewContorller;
import sinia.com.baihangeducation.supplement.alertview.OnItemClickListener;
import sinia.com.baihangeducation.supplement.tool.PickerUtils;
import sinia.com.baihangeducation.release.info.ReleaseJobInfoListInfo;
import sinia.com.baihangeducation.release.info.bean.JobEducationListInfo;
import sinia.com.baihangeducation.release.info.bean.JobExpListInfo;
import sinia.com.baihangeducation.release.info.bean.JobIndustryListInfo;
import sinia.com.baihangeducation.release.info.bean.JobSalaryListInfo;
import sinia.com.baihangeducation.release.info.bean.JobSexListInfo;
import sinia.com.baihangeducation.release.info.bean.JobTagListInfo;
import sinia.com.baihangeducation.release.info.bean.JobTypeListInfo;
import sinia.com.baihangeducation.release.presenter.ReleaseJobInfoPresenter;
import sinia.com.baihangeducation.release.view.IReleaseJobInfoView;
import sinia.com.baihangeducation.supplement.base.BaseActivity;
import sinia.com.baihangeducation.home.HomeFragment;
import com.mcxtzhang.swipemenulib.info.bean.MyReleaseJobDetaiInfo;
import sinia.com.baihangeducation.mine.presenter.GetMyReleaseJobDetaiInfoPresenter;
import sinia.com.baihangeducation.mine.view.IGetMyReleaseJobDetaiInfoView;
import sinia.com.baihangeducation.supplement.base.Goto;
import com.mcxtzhang.swipemenulib.utils.ACache;

/**
 * 企业发布职位
 */
public class CompanyEditJobActivity extends BaseActivity implements IGetMyReleaseJobDetaiInfoView, IReleaseJobInfoView, OnItemClickListener, ISendCompanyJobView {
    private ReleaseJobInfoPresenter presenter;
    private CompanyPresenter releaseJobPresenter;
    private GetMyReleaseJobDetaiInfoPresenter getMyReleaseJobDetaiInfoPresenter;
    private ACache cache;
    private String myReleaseJobId;

    private RelativeLayout mJobChoiceLayout;        //职位选择layout
    private TextView mJobChoiceTextView;            //职位选择TextView
    private EditText mCompanyNameEditText;          //招聘名称EditText
    private RelativeLayout mWorkAdressLayout;       //工作地点layout
    private TextView mWorkAdressTextView;           //工作地点TextView
    private EditText mWorkAdressDetailEditText;     //工作地点详情EditText
    private EditText mNeedPeopleMinEditText;        //招聘人数最小EditText
    private EditText mNeedPeopleMaxEditText;        //招聘人数最大EditText
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
    private TextView mWorkTimeMinTextView;          //上班时间最小值TextView
    private TextView mWorkTimeMaxTextView;          //上班时间最大值TextView
    private RelativeLayout mLinkerLayout;           //联系人名字Layout
    private EditText mLinkerEditText;               //联系人名字EditText
//    private EditText mLinkTelEditText;              //联系人电话EditText
    private EditText mContantEditText;              //联系人电话EditText
    private TextView mReleaseTextView;              //发布

    private String[] jobtype = {"全职", "兼职"};                  //1全职  2兼职
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
    private String indutryIndex;                    //所属行业id
    private String tagText;                         //职位标签名字
    private String tagIndex;                        //职位标签id
    private String educationID;                     //学历要求id
    private String expID;                           //经验要求id
    private String genderID;                        //性别要求id
    private String jobTypeID;                       //岗位类型要求id
    private String moneyID;                         //薪资类型要求id
    private String highMoney;                       //薪资最高值


    private AlertViewContorller mAlertViewContorller;                               //仿ios弹窗

    @Override
    public int initLayoutResID() {
        return R.layout.releasejob;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (cache != null) {
            indutryText = cache.getAsString("belongindutry");
            Log.i("所属行业内容", indutryText + "表单");
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
        myReleaseJobId = getIntent().getStringExtra("MYRELEASEJOBID");
        job_education_list = new ArrayList<>();
        job_experience_list = new ArrayList<>();
        job_industry_list = new ArrayList<>();
        job_money_type_list = new ArrayList<>();
        job_sex_list = new ArrayList<>();
        job_tag_list = new ArrayList<>();
        job_type_list = new ArrayList<>();

        mCommonTitle.setCenterText(R.string.raleasejob);
        mCommonTitle.setBackground(getResources().getDrawable(R.color.white));

        releaseJobPresenter = new CompanyPresenter(context, this);
        presenter = new ReleaseJobInfoPresenter(context, this);
        getMyReleaseJobDetaiInfoPresenter = new GetMyReleaseJobDetaiInfoPresenter(context, this);

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
        mWorkAdressLayout = $(R.id.releasejob_workadress_layout);
        mWorkAdressTextView = $(R.id.releasejob_workadress_textview);
        mWorkAdressDetailEditText = $(R.id.releasejob_workadressdetail_edittext);
        mNeedPeopleMinEditText = $(R.id.releasejob_needpeoplemin_textview);
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
        mWorkTimeMinTextView = $(R.id.releasejob_worktimemin_textview);
        mWorkTimeMaxTextView = $(R.id.releasejob_worktimemax_textview);
        mLinkerLayout = $(R.id.releasejob_linker_layout);
        mLinkerEditText = $(R.id.releasejob_linker_edittext);
        mContantEditText = $(R.id.releasejob_contant_edittext);
        mReleaseTextView = $(R.id.releasejob_release_textview);

        mJobChoiceLayout.setOnClickListener(this);
        mWorkAdressLayout.setOnClickListener(this);
        mEducationLayout.setOnClickListener(this);
        mExpLayout.setOnClickListener(this);
        mSalaryTypeLayout.setOnClickListener(this);
        mJobTypeLayout.setOnClickListener(this);
        mBelongIndutryLayout.setOnClickListener(this);
        mJobTagLayout.setOnClickListener(this);
        mGenderLayout.setOnClickListener(this);
        mWorkTimeMinTextView.setOnClickListener(this);
        mWorkTimeMaxTextView.setOnClickListener(this);
        mReleaseTextView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.releasejob_jobchoice_layout:
                hideEditTextInput();
                addAlerView(mJobChoiceTextView, "职位选择", jobtype);
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

                addAlerView(mEducationTextView, "学历要求", educationdata);
                break;
            case R.id.releasejob_exp_layout:
                hideEditTextInput();
                String[] expdata = new String[job_experience_list.size()];
                for (int i = 0; i < job_experience_list.size(); i++) {
                    expdata[i] = job_experience_list.get(i).experience_name;
                }
                addAlerView(mExpTextView, "经验要求", expdata);
                break;

            case R.id.releasejob_salarytype_layout:
                hideEditTextInput();
                String[] salarydata;
                if ((mJobChoiceTextView.getText().toString()).equals("全职"))
                    salarydata = new String[]{"月薪", "年薪"};
                else
                    salarydata = new String[]{"日薪", "周薪"};
                addAlerView(mSalaryTypeTextView, "薪资待遇", salarydata);
                break;

            case R.id.releasejob_jobtype_layout:
                hideEditTextInput();
                String[] jobtypedata = new String[job_industry_list.size()];
                for (int i = 0; i < job_industry_list.size(); i++) {
                    jobtypedata[i] = job_industry_list.get(i).industry_name;
                }
                addAlerView(mJobTypeTextView, "岗位类型", jobtypedata);
                break;

            case R.id.releasejob_belongindutry_layout:
                hideEditTextInput();
                Goto.toBelongIndutryActivity(context);
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

                addAlerView(mGenderTextView, "性别选择", genderdata);
                break;


            case R.id.releasejob_worktimemin_textview:
                hideEditTextInput();
                timePicker = PickerUtils.initTimeHoursPicker(this, mWorkTimeMinTextView);
                timePicker.show();
                break;
            case R.id.releasejob_worktimemax_textview:
                hideEditTextInput();
                timePicker = PickerUtils.initTimeHoursPicker(this, mWorkTimeMaxTextView);
                timePicker.show();
                break;
            case R.id.releasejob_release_textview:
                hideEditTextInput();
                if (!TextUtils.isEmpty(mSalaryTypeTextView.getText().toString().trim())) {
                    for (int i = 0; i < job_money_type_list.size(); i++) {
                        if (mSalaryTypeTextView.getText().toString().trim().equals(job_money_type_list.get(i).money_type_name)) {
                            moneyID = job_money_type_list.get(i).money_type_id + "";
                        }
                    }
                }
                releaseJobPresenter.editJob();
                break;
        }
    }

    private void hideEditTextInput() {
        hideSoftInput(mCompanyNameEditText);
        hideSoftInput(mWorkAdressDetailEditText);
        hideSoftInput(mNeedPeopleMinEditText);
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
        mAlertViewContorller = new AlertViewContorller(textView, title, null, "取消", null, data,
                context, AlertViewContorller.Style.ActionSheet, this);
        mAlertViewContorller.setCancelable(true).show();
    }

    private void getAddress(TextView view) {
        cityPicker = PickerUtils.initCityPicker(this, view, new PickerUtils.OnCityClickListener() {
            @Override
            public void onCityClick(String provinceId, String cityId, String districtId) {
                mProvinceId = provinceId;
                mCityId = cityId;
                mDistrictId = districtId;
                Log.i("位置ID", provinceId + "省");
                Log.i("位置ID", cityId + "市");
                Log.i("位置ID", districtId + "区");
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
                mExpTextView.setText(job_experience_list.get(0).experience_name);
                expID = job_experience_list.get(0).experience_id + "";
            }
            //岗位类型
            if (info.job_industry_list != null && info.job_industry_list.size() > 0) {
                job_industry_list = info.job_industry_list;
                mJobTypeTextView.setText(job_industry_list.get(0).industry_name);
                jobTypeID = job_industry_list.get(0).industry_id + "";
            }
            //薪资类型
            if (info.job_money_type_list != null && info.job_money_type_list.size() > 0) {
                job_money_type_list = info.job_money_type_list;
            }
            //性别选择
            if (info.job_sex_list != null && info.job_sex_list.size() > 0) {
                job_sex_list = info.job_sex_list;
                mGenderTextView.setText(job_sex_list.get(0).sex_name);
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
            getMyReleaseJobDetaiInfoPresenter.getMyReleaseJobDetaiData();
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
            mAlertViewContorller.dismiss();
        } else {
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if (mAlertViewContorller != null && mAlertViewContorller.isShowing()) {
                mAlertViewContorller.dismiss();
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mAlertViewContorller != null) {
            mAlertViewContorller.dismiss();
        }
        //所属行业缓存清除
        cache.put("belongindutry", "");
        cache.put("belongindutryindex", "");
        //职位标签缓存清除
        cache.put("tagtext", "");
        cache.put("tagindex", "");
    }


    @Override
    public String getReleaseType() {
        return mParmJobType;
    }

    @Override
    public String getJobTypeIds() {
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
        return mNeedPeopleMinEditText.getText().toString().trim();
    }

    @Override
    public String getNumUpper() {
        return mNeedPeopleMaxEditText.getText().toString().trim();
    }

    @Override
    public String getMoneyType() {
        return moneyID;
    }

    @Override
    public String getMoneyLower() {
        return mSalaryMinEditTextAllTime.getText().toString().trim();
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
        return "";
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
        return "";
    }

    @Override
    public String getDateEnd() {
        return "";
    }

    @Override
    public String getTimeStart() {
        return mWorkTimeMinTextView.getText().toString().trim();
    }

    @Override
    public String getTimeEnd() {
        return mWorkTimeMaxTextView.getText().toString().trim();
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
        return "";
    }

    @Override
    public String getEditID() {
        return myReleaseJobId;
    }

    @Override
    public void getReleaseSuccess() {
        Toast.getInstance().showSuccessToast(context, "修改成功");
        finish();
    }

    @Override
    public String getAgeLower() {
        return "";
    }

    @Override
    public String getMyReleaseJobDetailJobId() {
        return myReleaseJobId;
    }

    @Override
    public String getMyReleaseJobDetailCityId() {
        return HomeFragment.cityID;
    }

    @Override
    public void getMyReleaseJobDetailInfoSuccess(MyReleaseJobDetaiInfo jobDetaiInfo) {
        //判断是否为空
        if (jobDetaiInfo != null) {
            //判断兼职还是全职更改页面显示
            if (jobDetaiInfo.job_type == 1) {
                mJobChoiceTextView.setText("全职");
                mSalaryMinEditTextAllTime.setText(jobDetaiInfo.job_money_lower_ex);
                mSalaryMaxEditTextAllTime.setText(jobDetaiInfo.job_money_upper_ex);
                mParmJobType = "1";
                mGenderLayout.setVisibility(View.GONE);
                mSalaryLayoutPartTime.setVisibility(View.GONE);
                mSalaryLayoutAllTime.setVisibility(View.VISIBLE);
            } else if (jobDetaiInfo.job_type == 2) {
                mJobChoiceTextView.setText("兼职");
                mSalaryEditTextPartTime.setText(jobDetaiInfo.job_money);
                mParmJobType = "2";
                mGenderLayout.setVisibility(View.VISIBLE);
                mSalaryLayoutPartTime.setVisibility(View.VISIBLE);
                mSalaryLayoutAllTime.setVisibility(View.GONE);
            }
            mCompanyNameEditText.setText(jobDetaiInfo.job_title);
            mWorkAdressTextView.setText(PickerUtils.getLocationNameByID(context, jobDetaiInfo.job_prov_id + "",
                    jobDetaiInfo.job_city_id + "", jobDetaiInfo.job_dist_id + ""));
            mProvinceId = jobDetaiInfo.job_prov_id + "";
            mCityId = jobDetaiInfo.job_city_id + "";
            mDistrictId = jobDetaiInfo.job_dist_id + "";
            mWorkAdressDetailEditText.setText(jobDetaiInfo.job_address_only);
            mNeedPeopleMinEditText.setText(jobDetaiInfo.job_num_lower_ex);
            mNeedPeopleMaxEditText.setText(jobDetaiInfo.job_num_upper_ex);
            //根据学历要求ID遍历学历集合
            for (int i = 0; i < job_education_list.size(); i++) {
                if (job_education_list.get(i).education_id == jobDetaiInfo.job_education_ex) {
                    mEducationTextView.setText(job_education_list.get(i).education_name);
                    //学历要求ID
                    educationID = jobDetaiInfo.job_education_ex + "";
                }
            }
            mExpTextView.setText(jobDetaiInfo.job_experience);
            //经验要求ID
            expID = jobDetaiInfo.job_experience_ex + "";
            //根据薪水类型要求ID遍历薪水类型集合
            for (int i = 0; i < job_money_type_list.size(); i++) {
                if (job_money_type_list.get(i).money_type_id == jobDetaiInfo.job_money_type_ex) {
                    mSalaryTypeTextView.setText(job_money_type_list.get(i).money_type_name);
                    //薪资类型ID
                    moneyID = jobDetaiInfo.job_money_type_ex + "";
                }
            }
            //根据岗位类型ID遍历岗位集合
            for (int i = 0; i < job_industry_list.size(); i++) {
                if (job_industry_list.get(i).industry_id == jobDetaiInfo.job_industry_id_ex) {
                    mJobTypeTextView.setText(job_industry_list.get(i).industry_name);
                    //岗位类型ID
                    jobTypeID = jobDetaiInfo.job_industry_id_ex + "";
                }
            }

            //去除所属行业分割符号,生成集合
            List<String> belongIndutryResult = Arrays.asList(jobDetaiInfo.job_type_ids_ex.split(","));
            //遍历
            for (int i = 0; i < belongIndutryResult.size(); i++) {
                indutryText = indutryText + job_type_list.get(Integer.parseInt(belongIndutryResult.get(i))).type_name + ",";
                indutryIndex = indutryIndex + job_type_list.get(Integer.parseInt(belongIndutryResult.get(i))).type_id + ",";
            }
            //所属行业id去除最后一个逗号
            indutryIndex = indutryIndex.substring(0, indutryIndex.length() - 1);
            //所属行业名字去除最后一个逗号
            mBelongIndutryTextView.setText(indutryText.substring(0, indutryText.length() - 1));

            mGenderTextView.setText(jobDetaiInfo.job_sex_name);
            //性别ID
            genderID = jobDetaiInfo.job_sex_ex + "";

            //去除职位标签分割符号,生成集合
            List<String> jobTagResult = Arrays.asList(jobDetaiInfo.job_tag_ids_ex.split(","));
            //遍历
            for (int i = 0; i < jobTagResult.size(); i++) {
                tagText = tagText + job_tag_list.get(Integer.parseInt(jobTagResult.get(i))-1).tag_name + ",";
                tagIndex = tagIndex + job_tag_list.get(Integer.parseInt(jobTagResult.get(i))-1).tag_id + ",";
            }
            //职位标签id去除最后一个逗号
            tagIndex = tagIndex.substring(0, tagIndex.length() - 1);
            //职位标签名字去除最后一个逗号
            mJobTagTextView.setText(tagText.substring(0, tagText.length() - 1));

            mWorkTimeMinTextView.setText(jobDetaiInfo.job_time_start_ex);
            mWorkTimeMaxTextView.setText(jobDetaiInfo.job_time_end_ex);
            mLinkerEditText.setText(jobDetaiInfo.job_link_person);
            // TODO: 2018/7/13 0013       mLinkTelEditText.setText(jobDetaiInfo.job_link_phone);
            String contant1 = (jobDetaiInfo.job_content).replace("<p>", "");
            String contant2 = contant1.replace("</p>", "");
            mContantEditText.setText(contant2);

        }

    }
}
