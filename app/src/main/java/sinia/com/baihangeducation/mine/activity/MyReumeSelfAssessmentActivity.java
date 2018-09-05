package sinia.com.baihangeducation.mine.activity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.supplement.base.BaseActivity;
import sinia.com.baihangeducation.MyApplication;
import sinia.com.baihangeducation.mine.model.ContrastResumeInfo;

import com.mcxtzhang.swipemenulib.info.bean.MyResumInfo;

/**
 * 简历里添加自我评价页面
 */

public class MyReumeSelfAssessmentActivity extends BaseActivity {
    private EditText mSelfAssessmentContant;
    private TextView mWordsNum;
    private TextView mClean;
    private TextView mSubmit;


    @Override
    public int initLayoutResID() {
        return R.layout.myreume_selfassessment;
    }

    @Override
    protected void initData() {
        mCommonTitle.setCenterText(R.string.myreumeselfassessment);
        mCommonTitle.setBackground(getResources().getDrawable(R.color.white));

        mSelfAssessmentContant.addTextChangedListener(mTextWatcher);

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
                mWordsNum.setText((200 - temp.length()) + "");
            }
        }
    };

    @Override
    protected void initView() {

        mSelfAssessmentContant = $(R.id.myreumeselfassessment_contant);
        mWordsNum = $(R.id.myreumeselfassessment_wordnum);
        mClean = $(R.id.myreumeselfassessment_clean);
        mSubmit = $(R.id.myreumeselfassessment_confirm);

        mSelfAssessmentContant.setText(MyResumInfo.evaluation);
        mSelfAssessmentContant.setSelection(MyResumInfo.evaluation.length());
        mClean.setOnClickListener(this);
        mSubmit.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.myreumeselfassessment_clean:
                mSelfAssessmentContant.setText("");
                mSelfAssessmentContant.setHint(R.string.pleaseinput);
                break;
            case R.id.myreumeselfassessment_confirm:
                //提交
                setData();
                ContrastResumeInfo contrastResumeInfo = new ContrastResumeInfo(context);
                contrastResumeInfo.contras();
                finish();
                break;
        }
    }

    private void setData() {
        MyResumInfo.evaluation = mSelfAssessmentContant.getText().toString().trim();
    }
}
