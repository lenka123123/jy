package sinia.com.baihangeducation.home.activity;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.supplement.base.BaseActivity;
import com.mcxtzhang.swipemenulib.info.SimpleResumeInfo;
import sinia.com.baihangeducation.home.present.GetSimpleResumePresenter;
import sinia.com.baihangeducation.home.view.IGetSimpleResumeView;
import sinia.com.baihangeducation.supplement.base.Goto;
import com.mcxtzhang.swipemenulib.utils.ACache;

public class CompleteInfoActivity extends BaseActivity implements IGetSimpleResumeView {
    private TextView mCompleteName;
    private TextView mCompleteSchool;
    private TextView mCompleteMajor;
    private TextView mCompleteSave;
    private ACache cache;
    private GetSimpleResumePresenter presenter;
    private String schoolName;
    private String schoolID;
    private String majorName;
    private String majorID;

    @Override
    public int initLayoutResID() {
        return R.layout.completeinfo;
    }

    @Override
    protected void initData() {
        mCommonTitle.setCenterText(R.string.completeinfo);
        mCommonTitle.setBackground(getResources().getDrawable(R.color.white));

        presenter = new GetSimpleResumePresenter(context, this);
        presenter.getSimpleInfo();
    }

    @Override
    protected void onResume() {
        super.onResume();
        cache = ACache.get(context);
        schoolName = cache.getAsString("HOMESCHOOLNAME");
        schoolID = cache.getAsString("HOMESCHOOLID");
        majorName = cache.getAsString("COMPLETEMAJORNAME");
        majorID = cache.getAsString("COMPLETEMAJORID");

    }

    @Override
    protected void initView() {
        cache = ACache.get(context);
        mCompleteName = $(R.id.completeinfo_name);
        mCompleteSchool = $(R.id.completeinfo_schoolname);
        mCompleteMajor = $(R.id.completeinfo_major);
        mCompleteSave = $(R.id.completeinfo_confirm);


        mCompleteSchool.setOnClickListener(this);
        mCompleteMajor.setOnClickListener(this);
        mCompleteSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.completeinfo_schoolname:

                //获取学校名字
                Goto.toEditMyResumeEducationExpChoiceSchool(context);
                break;
            case R.id.completeinfo_major:
                //获取专业
                Goto.toEditMyResumeEducationExpChoice_1Major(context);
                break;
            case R.id.completeinfo_confirm:
                presenter.sendSimpleInfo();
                break;
        }
    }

    @Override
    public String getName() {
        return mCompleteName.getText().toString().trim();
    }

    @Override
    public String getSchoolName() {
        return schoolName;
    }

    @Override
    public String getSchoolId() {
        return schoolID;
    }

    @Override
    public String getMajorName() {
        return majorName;
    }

    @Override
    public String getMajorId() {
        return majorID;
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        Log.i("TextUtils.isEmpty", "onRestart: "+ AppConfig.SCHOOLNAME);
        Log.i("TextUtils.isEmpty", "onRestart: "+AppConfig.SCHOOLNAMEID);
        Log.i("TextUtils.isEmpty", "onRestart: "+AppConfig.SCHOOLMAGOR);
        Log.i("TextUtils.isEmpty", "onRestart: "+AppConfig.SCHOOLMAGORID);

        if (!TextUtils.isEmpty(AppConfig.SCHOOLNAME)) {
            mCompleteSchool.setText(AppConfig.SCHOOLNAME);
        }
        if (!TextUtils.isEmpty(AppConfig.SCHOOLMAGOR)) {
            mCompleteMajor.setText(AppConfig.SCHOOLMAGOR);
        }
    }

    @Override
    public void getSimpleResumeSuccess(SimpleResumeInfo simpleResumeInfo) {
        if (simpleResumeInfo != null) {
            if (!TextUtils.isEmpty(simpleResumeInfo.name)) {
                mCompleteName.setText(simpleResumeInfo.name);
            }
            if (!TextUtils.isEmpty(AppConfig.SCHOOLNAME)) {
                mCompleteSchool.setText(AppConfig.SCHOOLNAME);
            }
            if (!TextUtils.isEmpty(simpleResumeInfo.major_name)) {
                mCompleteMajor.setText(simpleResumeInfo.major_name);
            }
            schoolName = simpleResumeInfo.school_name;
            schoolID = simpleResumeInfo.school_id + "";
            majorName = simpleResumeInfo.major_name;
            majorID = simpleResumeInfo.major_id + "";
        }

    }

    @Override
    public void sendSimpleResumeSuccess() {

        finish();
    }

    @Override
    public void showLoading() {
        showProgress();
    }

    @Override
    public void hideLoading() {
        hideProgress();
    }
}
