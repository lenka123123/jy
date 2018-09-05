package sinia.com.baihangeducation.home.activity;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.framwork.utils.Toast;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.supplement.base.BaseActivity;
import com.mcxtzhang.swipemenulib.info.TraingDetailInfo;
import sinia.com.baihangeducation.home.present.TraingJoinInPresenter;
import sinia.com.baihangeducation.home.view.IMarkTrainingView;

/**
 * Created by Administrator on 2018/4/28.
 */

public class TraingJoinInActivity extends BaseActivity implements IMarkTrainingView {
    private TraingJoinInPresenter presenter;

    private Intent intent;
    private TraingDetailInfo info;

    private TextView title;
    private TextView content;
    private TextView price;
    private TextView classAdress;
    private TextView tel;
    private TextView classNum;
    private TextView classTime;
    private TextView classDate;
    private EditText inputName;
    private EditText inputTel;
    private EditText inputEmail;
    private EditText inputMessage;
    private TextView joinIn;

    private String trainId;

    @Override
    public int initLayoutResID() {
        return R.layout.traingjoinin;
    }

    @Override
    protected void initData() {
        mCommonTitle.setCenterText(R.string.mark);
        mCommonTitle.setBackground(getResources().getDrawable(R.color.white));
        presenter  = new TraingJoinInPresenter(context,this);
        intent = getIntent();
        info = (TraingDetailInfo) intent.getSerializableExtra("TRAININGINFO");
        if (info != null) {
            trainId = info.train_id + "";
            title.setText(info.train_title);
            content.setText(info.train_sub_title);
            price.setText(info.train_price);
            classAdress.setText(info.train_address);
            tel.setText(info.train_tel);
            classNum.setText(info.train_class_num);
            classTime.setText(info.train_class_duration);
            classDate.setText(info.train_class_date);
        }

    }

    @Override
    protected void initView() {
        title = $(R.id.traingjoinin_title);
        content = $(R.id.traingjoinin_content);
        price = $(R.id.traingjoinin_price);
        classAdress = $(R.id.traingjoinin_classadress);
        tel = $(R.id.traingjoinin_tel);
        classNum = $(R.id.traingjoinin_classnum);
        classTime = $(R.id.traingjoinin_classtime);
        classDate = $(R.id.traingjoinin_classdate);


        inputName = $(R.id.traingjoinin_inputname);
        inputTel = $(R.id.traingjoinin_inputtel);
        inputEmail = $(R.id.traingjoinin_inputemail);
        inputMessage = $(R.id.traingjoinin_inputmessage);
        joinIn = $(R.id.traingjoinin_joinin);

        joinIn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.traingjoinin_joinin:
                presenter.markTraining();
                break;
        }
    }

    @Override
    public String getTrainingId() {
        return trainId;
    }

    @Override
    public String getRealName() {
        return inputName.getText().toString().trim();
    }

    @Override
    public String getContactTel() {
        return inputTel.getText().toString().trim();
    }

    @Override
    public String getEmail() {
        return inputEmail.getText().toString().trim();
    }

    @Override
    public String getMarkTraingMessage() {
        return inputMessage.getText().toString().trim();
    }

    @Override
    public String getUserCouponId() {
        return "0";
    }

    @Override
    public void joinSuccess() {
        Toast.getInstance().showSuccessToast(context,"报名成功");
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
