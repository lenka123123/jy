package sinia.com.baihangeducation.mine.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.supplement.base.BaseActivity;
import sinia.com.baihangeducation.MyApplication;
import sinia.com.baihangeducation.mine.model.ContrastResumeInfo;

import com.example.framwork.utils.SPUtils;
import com.mcxtzhang.swipemenulib.info.bean.College;
import com.mcxtzhang.swipemenulib.info.bean.EducationInfo;
import com.mcxtzhang.swipemenulib.info.bean.Marjor;
import com.mcxtzhang.swipemenulib.info.bean.MyResumInfo;

import sinia.com.baihangeducation.mine.presenter.GetEditEducationInfoPresenter;
import sinia.com.baihangeducation.mine.view.IGetEducationView;
import sinia.com.baihangeducation.supplement.base.Goto;

import com.mcxtzhang.swipemenulib.customview.PickerView;
import com.mcxtzhang.swipemenulib.info.bean.MyResumeEducation_expInfo;

/**
 * 编辑教育经历页面
 */

public class MyResumeEditEducationExpActivity extends BaseActivity implements IGetEducationView {
    private TextView mSchoolName;       //学校名称
    private TextView mMajor;            //专业
    private TextView mEducation;       //学历
    private TextView mConfirm;          //提交

    private GetEditEducationInfoPresenter presenter;
    List<EducationInfo> educationlist;
    private String educationtext = "";
    private String educationtextID = "";
    private MyApplication application;
    private MyResumInfo myResumInfos;
    private MyResumInfo myResumInfosCopy;
    private PickerView pickerView;
    private TextView cancel;
    private TextView confirm;
    private Dialog dialog;
    private View v;

    @Override
    public int initLayoutResID() {
        return R.layout.educationexperience;
    }

    @Override
    protected void initData() {
        mCommonTitle.setCenterText(R.string.myreumeeducationexp);
        mCommonTitle.setBackground(getResources().getDrawable(R.color.white));
        myResumInfosCopy = ContrastResumeInfo.myResumInfoCopy;
        presenter = new GetEditEducationInfoPresenter(context, this);
        presenter.getEducation();
    }

    @Override
    protected void initView() {
        mSchoolName = $(R.id.educationexp_schoolname);
        mMajor = $(R.id.educationexp_major);
        mEducation = $(R.id.educationexp_education);
        mConfirm = $(R.id.educationexp_confirm);

        mSchoolName.setOnClickListener(this);
        mMajor.setOnClickListener(this);
        mEducation.setOnClickListener(this);
        mConfirm.setOnClickListener(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogStyle);
        LayoutInflater inflater = LayoutInflater.from(context);
        v = inflater.inflate(R.layout.popwindowchoicelist, null);
        pickerView = v.findViewById(R.id.picker);
        cancel = v.findViewById(R.id.dialog_cancel);
        confirm = v.findViewById(R.id.dialog_confirm);
        dialog = builder.create();


        mSchoolName.setText(MyResumInfo.education_exp.school_name);
        mMajor.setText(MyResumInfo.education_exp.major_name);
        mEducation.setText(MyResumInfo.education_exp.education_name);

    }


    @Override
    protected void onRestart() {
        super.onRestart();
        setmEducationData();
    }

    private void setmEducationData() {
        if (mSchoolName != null) {
            mSchoolName.setText(AppConfig.SCHOOLNAME);
        }
        if (mMajor != null) {
            mMajor.setText(AppConfig.SCHOOLMAGOR);
        }
        if (mEducation != null) {
            mEducation.setText(AppConfig.SCHOOLEDUCTION);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setData();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.educationexp_schoolname:
                //获取学校名字
                Goto.toEditMyResumeEducationExpChoiceSchool(context);
                break;
            case R.id.educationexp_major:
                //获取专业
                Goto.toEditMyResumeEducationExpChoice_1Major(context);
                break;
            case R.id.educationexp_education:
                //选择学历
                educationtext = "";

                if (educationlist != null && educationlist.size() > 0) {
                    addPopwindow();
                }

                break;
            case R.id.educationexp_confirm:
                AppConfig.educationtextID = educationtextID;
                AppConfig.educationtext = educationtext;

                finish();
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
    public String getPage() {
        return null;
    }

    @Override
    public String getItenmNum() {
        return null;
    }

    @Override
    public String getFatherId() {
        return null;
    }

    @Override
    public void getEducationSchoolSuccess(List<College> list, int maxpage) {

    }

    @Override
    public void getEducationMajor_1_Success(List<Marjor> list, int maxpage) {

    }

    @Override
    public void getEducationMajor_2_Success(List<Marjor> list, int maxpage) {

    }


    @Override
    public void getEducationSuccess(List<EducationInfo> list) {
        this.educationlist = list;

    }


    private void addPopwindow() {
        List<String> data = new ArrayList<String>();
        for (int i = 0; i < educationlist.size(); i++) {
            data.add(educationlist.get(i).edu_name);

        }

        pickerView.setData(data);
        dialog.show();
        dialog.getWindow().setContentView(v);//自定义布局应该在这里添加，要在dialog.show()的后面
//        dialog.getWindow().setLayout(width1/2, LinearLayout.LayoutParams.WRAP_CONTENT);
        //dialog.getWindow().setGravity(Gravity.CENTER);//可以设置显示的位置

        pickerView.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                for (int i = 0; i < educationlist.size(); i++) {
                    if (text.equals(educationlist.get(i).edu_name)) {
                        educationtextID = String.valueOf(educationlist.get(i).edu_id);

                    }
                }
                educationtext = text;

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.i("文本内容",educationtext);
                if (educationtext.equals("")) {
                    educationtext = "本科";
                }
                AppConfig.SCHOOLEDUCTION = educationtext;
                SPUtils.getInstance().put(AppConfig.getContext(), "SCHOOLEDUCTION", educationtext);
                mEducation.setText(educationtext);

                dialog.dismiss();
            }
        });
    }

    private void setData() {

        if (myResumInfosCopy != null) {
            if (myResumInfosCopy.education_exp.school_name != null && !TextUtils.isEmpty(myResumInfosCopy.education_exp.school_name))
                mSchoolName.setText(myResumInfosCopy.education_exp.school_name);
            if (myResumInfosCopy.education_exp.major_name != null && !TextUtils.isEmpty(myResumInfosCopy.education_exp.major_name))
                mMajor.setText(myResumInfosCopy.education_exp.major_name);
            if (myResumInfosCopy.education_exp.education_name != null && !TextUtils.isEmpty(myResumInfosCopy.education_exp.education_name))
                mEducation.setText(myResumInfosCopy.education_exp.education_name);
        }
    }
}
