package sinia.com.baihangeducation.mine.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.supplement.base.BaseActivity;
import sinia.com.baihangeducation.MyApplication;
import sinia.com.baihangeducation.mine.model.AccountManger;
import sinia.com.baihangeducation.mine.model.ContrastResumeInfo;

import com.mcxtzhang.swipemenulib.info.bean.AddJobTimeInfoList;
import com.mcxtzhang.swipemenulib.info.bean.AddJobTypeInfoList;
import com.mcxtzhang.swipemenulib.info.bean.MyResumInfo;
import com.mcxtzhang.swipemenulib.info.bean.MyResumeJobInfo;

import sinia.com.baihangeducation.mine.presenter.GetJobTypePresenter;
import sinia.com.baihangeducation.mine.view.IAddJobTypeView;

import com.mcxtzhang.swipemenulib.customview.PickerView;

/**
 * 添加工作经历页面
 */

public class AddJobExperienceActivity extends BaseActivity implements IAddJobTypeView {
    private LinearLayout ll;
    private TextView mChoiceJobType;            //选择工作类型
    private EditText mInputeType;               //输入工作类型
    private TextView mWorkTime;                 //选择工作时间
    private EditText mInputContant;             //职位描述输入
    private TextView mWordNum;                  //职位描述字数
    private TextView mClean;                    //清除职位描述
    private TextView mConfirm;                  //确认

    private GetJobTypePresenter mGetJobPresentere;
    List<AddJobTypeInfoList> typelist;
    List<AddJobTimeInfoList> timelist;
    private String tpyecontant;
    private String tpyecontantid;
    private String timecontant;
    private String timecontantid;

    @Override
    public int initLayoutResID() {
        return R.layout.myresume_addjobinfo;
    }

    @Override
    protected void initData() {

        mCommonTitle.setCenterText(R.string.addjobexperience);
        mCommonTitle.setBackground(getResources().getDrawable(R.color.white));


        mGetJobPresentere = new GetJobTypePresenter(context, this);

        //监听EditText输入
        mInputContant.addTextChangedListener(mTextWatcher);
    }

    TextWatcher mTextWatcher = new TextWatcher() {
        private CharSequence temp;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            temp = s;
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (temp.length() <= 200) {
                mWordNum.setText((200 - temp.length()) + "");
            }
        }
    };

    @Override
    protected void initView() {

        ll = $(R.id.ll);
        mChoiceJobType = $(R.id.addjobinfo_choicejobtype);
        mInputeType = $(R.id.addjobinfo_inputjobtype);
        mWorkTime = $(R.id.addjobinfo_worktime);
        mInputContant = $(R.id.addjobinfo_contant);
        mWordNum = $(R.id.addjobinfo_wordnum);
        mClean = $(R.id.addjobinfo_clean);
        mConfirm = $(R.id.addjobinfo_confirm);

        mChoiceJobType.setOnClickListener(this);
        mWorkTime.setOnClickListener(this);
        mClean.setOnClickListener(this);
        mConfirm.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.addjobinfo_choicejobtype:
                //获取工作类型信息
                mGetJobPresentere.getJobType();
                break;
            case R.id.addjobinfo_worktime:
                //获取工作时间信息
                mGetJobPresentere.getJobTime();
                break;
            case R.id.addjobinfo_clean:
                mInputContant.setText("");
                mInputContant.setHint(R.string.pleaseinput);
                break;
            case R.id.addjobinfo_confirm:
                //确认

                if (AccountManger.checkMyResumeAddJobExp(context, mChoiceJobType.getText().toString().trim(), mInputeType.getText().toString().trim(), mWorkTime.getText().toString().trim(), mInputContant.getText().toString().trim())) {
                    addJobInfo();

                    finish();
                }
                break;
        }
    }

    private void addJobInfo() {
        List<MyResumeJobInfo> list = MyResumInfo.job_exp;

        MyResumeJobInfo myResumeJobInfo = new MyResumeJobInfo();
        myResumeJobInfo.job_time_id = timecontantid;
        myResumeJobInfo.job_time_name = timecontant;
        myResumeJobInfo.type_id = tpyecontantid;
        myResumeJobInfo.type_name = tpyecontant;
        myResumeJobInfo.name = mInputeType.getText().toString().trim();
        myResumeJobInfo.note = mInputContant.getText().toString().trim();
        list.add(myResumeJobInfo);
        // TODO: 2018/8/18 0018     myResumInfoCopy.job_exp.add(myResumeJobInfo);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void getJobTypeSuccess(List<AddJobTypeInfoList> list) {
        this.typelist = list;
        addPopwindow(1);
    }

    @Override
    public void getJobTimeSuccess(List<AddJobTimeInfoList> timelist) {
        this.timelist = timelist;
        addPopwindow(2);
    }

    private void addPopwindow(final int type) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogStyle);
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.popwindowchoicelist, null);
        final PickerView pickerView = v.findViewById(R.id.picker);
        TextView cancel = v.findViewById(R.id.dialog_cancel);
        TextView confirm = v.findViewById(R.id.dialog_confirm);
        final Dialog dialog = builder.create();
        List<String> data = new ArrayList<String>();
        switch (type) {
            case 1:
                for (int i = 0; i < typelist.size(); i++) {
                    data.add(typelist.get(i).job_type_name);
                }
                break;
            case 2:
                for (int i = 0; i < timelist.size(); i++) {
                    data.add(timelist.get(i).job_time_name);
                }
                break;
        }

        pickerView.setData(data);
        dialog.show();
        dialog.getWindow().setContentView(v);//自定义布局应该在这里添加，要在dialog.show()的后面
//        dialog.getWindow().setLayout(width1/2, LinearLayout.LayoutParams.WRAP_CONTENT);
        //dialog.getWindow().setGravity(Gravity.CENTER);//可以设置显示的位置

        pickerView.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                switch (type) {
                    case 1:
                        tpyecontant = text;
                        break;
                    case 2:
                        timecontant = text;
                        break;
                }
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
                switch (type) {
                    case 1:
                        if (tpyecontant == null) {
                            tpyecontant = typelist.get(typelist.size() / 2).job_type_name;
                            tpyecontantid = typelist.get(typelist.size() / 2).job_type_id + "";
                            mChoiceJobType.setText(typelist.get(typelist.size() / 2).job_type_name + "");
                        }
                        mChoiceJobType.setText(tpyecontant);
                        break;
                    case 2:
                        if (timecontant == null) {
                            timecontant = timelist.get(timelist.size() / 2).job_time_name;
                            timecontantid = timelist.get(timelist.size() / 2).job_time_id + "";
                            mChoiceJobType.setText(typelist.get(typelist.size() / 2).job_type_name + "");
                        }
                        mWorkTime.setText(timecontant);
                        break;
                }

                dialog.dismiss();
            }
        });
    }

    @Override
    public void getJobTypeFail() {

    }


}
