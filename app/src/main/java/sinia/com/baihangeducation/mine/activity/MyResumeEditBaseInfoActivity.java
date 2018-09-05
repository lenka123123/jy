package sinia.com.baihangeducation.mine.activity;

import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;

import java.util.List;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.supplement.alertview.AlertViewContorller;
import sinia.com.baihangeducation.supplement.alertview.OnItemClickListener;
import sinia.com.baihangeducation.supplement.base.BaseActivity;
import sinia.com.baihangeducation.MyApplication;
import sinia.com.baihangeducation.mine.model.AccountManger;

import com.mcxtzhang.swipemenulib.info.bean.MyResumInfo;

import sinia.com.baihangeducation.supplement.tool.AddressInfo;
import sinia.com.baihangeducation.supplement.tool.PickerUtils;

/**
 * 简历里的编辑基本资料页面
 */

public class MyResumeEditBaseInfoActivity extends BaseActivity implements OnItemClickListener {
    private EditText mName;
    private TextView mGender;
    private TextView mBrithday;
    private EditText mTel;
    private TextView mAdress;
    private EditText mAdressDetail;
    private TextView mPersentType;
    private TextView mConfirm;

    private AlertViewContorller mAlertViewContorller;//避免创建重复View，先创建View，然后需要的时候show出来，推荐这个做法
    String[] type = {"学生", "社会人群"};
    String[] gender = {"男", "女"};
    private OptionsPickerView cityPicker;
    private TimePickerView timePicker;
    private AddressInfo addressInfo;

    private MyApplication application;

    @Override
    public int initLayoutResID() {
        return R.layout.myresume_editbaseinfo;
    }

    @Override
    protected void initData() {
        application = (MyApplication) context.getApplication();

        addressInfo = new AddressInfo();
        mCommonTitle.setCenterText(R.string.ucentre_baseinfo);
        mCommonTitle.setBackground(getResources().getDrawable(R.color.white));

        mName.setText(MyResumInfo.name);
        mGender.setText(MyResumInfo.gender_name);
        mBrithday.setText(MyResumInfo.birthday);
        mTel.setText(MyResumInfo.tel);
        mAdress.setText(MyResumInfo.area_name);
        mAdressDetail.setText(MyResumInfo.address);
        mPersentType.setText(MyResumInfo.graduated_name);
        Log.i("查看人员类型ID",
                MyResumInfo.graduated + "");
    }


    @Override
    protected void initView() {
        mName = $(R.id.myresume_editbaseinfo_name);
        mGender = $(R.id.myresume_editbaseinfo_gender);
        mBrithday = $(R.id.myresume_editbaseinfo_brithday);
        mTel = $(R.id.myresume_editbaseinfo_tel);
        mAdress = $(R.id.myresume_editbaseinfo_adress);
        mAdressDetail = $(R.id.myresume_editbaseinfo_adressdetail);
        mPersentType = $(R.id.myresume_editbaseinfo_persenttype);
        mConfirm = $(R.id.myresume_editbaseinfo_confirm);

        mName.setOnClickListener(this);
        mGender.setOnClickListener(this);
        mBrithday.setOnClickListener(this);
        mTel.setOnClickListener(this);
        mAdress.setOnClickListener(this);
        mAdressDetail.setOnClickListener(this);
        mPersentType.setOnClickListener(this);
        mConfirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        mName.setCursorVisible(true);
        switch (v.getId()) {
            case R.id.myresume_editbaseinfo_name:
                //输入名字
                mName.setCursorVisible(true);
                showSoftInput(mName);
                break;
            case R.id.myresume_editbaseinfo_tel:
                //输入电话
                mTel.setCursorVisible(true);
                hideSoftInput(mName);
                hideSoftInput(mName);
                hideSoftInput(mAdressDetail);
                showSoftInput(mTel);
                break;
            case R.id.myresume_editbaseinfo_adressdetail:
                //输入地址详情
                mAdressDetail.setCursorVisible(true);
                hideSoftInput(mTel);
                hideSoftInput(mName);
                showSoftInput(mAdressDetail);
                break;
            case R.id.myresume_editbaseinfo_adress:
                //地址选择器
                mAdressDetail.setCursorVisible(false);
                mName.setCursorVisible(false);
                mTel.setCursorVisible(false);
                hideSoftInput(mName);
                hideSoftInput(mTel);
                hideSoftInput(mAdressDetail);
                getAddress(mAdress);
                cityPicker.show();
                break;
            case R.id.myresume_editbaseinfo_gender:
                //性别选择
                mAdressDetail.setCursorVisible(false);
                mName.setCursorVisible(false);
                mTel.setCursorVisible(false);

                hideSoftInput(mAdressDetail);
                mAlertViewContorller = new AlertViewContorller(mGender, "选择性别", null, "取消", null, gender,
                        context, AlertViewContorller.Style.ActionSheet, this);
                mAlertViewContorller.setCancelable(true).show();
                break;
            case R.id.myresume_editbaseinfo_brithday:
                //性别生日
                mAdressDetail.setCursorVisible(false);
                mName.setCursorVisible(false);
                mTel.setCursorVisible(false);
                hideSoftInput(mName);
                hideSoftInput(mTel);
                hideSoftInput(mAdressDetail);
                timePicker = PickerUtils.initTimePicker(this, mBrithday);
                timePicker.show();
                break;
            case R.id.myresume_editbaseinfo_persenttype:
                //人员类别选择
                mAdressDetail.setCursorVisible(false);
                mName.setCursorVisible(false);
                mTel.setCursorVisible(false);
                hideSoftInput(mName);
                hideSoftInput(mTel);
                hideSoftInput(mAdressDetail);
                mAlertViewContorller = new AlertViewContorller(mPersentType, "选择人员类别", null, "取消", null, type,
                        context, AlertViewContorller.Style.ActionSheet, this);
                mAlertViewContorller.setCancelable(true).show();
                break;
            case R.id.myresume_editbaseinfo_confirm:
                mAdressDetail.setCursorVisible(false);
                mName.setCursorVisible(false);
                mTel.setCursorVisible(false);

                hideSoftInput(mName);
                hideSoftInput(mTel);
                hideSoftInput(mAdressDetail);
                saveData();
//                ContrastResumeInfo contrastResumeInfo = new ContrastResumeInfo(myResumInfoCopy);
//                contrastResumeInfo.contras();
                break;
        }
    }

    /**
     * 将用户资本资料储存
     */
    private void saveData() {
        if (AccountManger.checkEditMyResumeBaseInfo(context, mName.getText().toString().trim(), mTel.getText().toString().trim())) {
            //名字
            MyResumInfo.name = mName.getText().toString();
            //性别
            if (mGender.getText().toString().trim() != null) {
                if (mGender.getText().toString().trim() == "男") {
                    MyResumInfo.gender = String.valueOf(1);
                } else if (mGender.getText().toString().trim() == "女") {
                    MyResumInfo.gender = String.valueOf(2);
                } else MyResumInfo.gender = String.valueOf(3);
                MyResumInfo.gender_name = mGender.getText().toString().trim();
            }
            //生日
            if (mBrithday.getText().toString().trim() != null) {
                MyResumInfo.birthday = mBrithday.getText().toString().trim();
            }
            //电话
            MyResumInfo.tel = mTel.getText().toString().trim();
            //省市区地址
            if (mAdress.getText().toString().trim() != null) {
                MyResumInfo.area_name = mAdress.getText().toString().trim();
            }
            //详细地址
            if (mAdressDetail.getText().toString().trim() != null && mAdressDetail.getText().toString().trim() != "") {
                MyResumInfo.address = mAdressDetail.getText().toString().trim();
            }
            //人员类别
            if (mPersentType.getText().toString().trim() != null) {
                if (mPersentType.getText().toString().trim() == "学生") {
                    MyResumInfo.graduated = String.valueOf(2);
                } else {
                    MyResumInfo.graduated = String.valueOf(1);
                }
                MyResumInfo.graduated_name = mPersentType.getText().toString().trim();
            }
            finish();
        }
    }

    private void getAddress(TextView view) {
        cityPicker = PickerUtils.initCityPicker(this, view, new PickerUtils.OnCityClickListener() {
            @Override
            public void onCityClick(String provinceId, String cityId, String districtId) {
                addressInfo.province_id = provinceId;
                addressInfo.city_id = cityId;
                addressInfo.district_id = districtId;
                Log.i("位置ID", provinceId + "省");
                Log.i("位置ID", cityId + "市");
                Log.i("位置ID", districtId + "区");
                MyResumInfo.province_id = provinceId;
                MyResumInfo.city_id = cityId;
                MyResumInfo.area_id = districtId;
            }
        });
    }

    @Override
    public void onItemClick(View view, List<String> mOthers, Object o, int position) {
        if (position == -1) {
            mAlertViewContorller.dismiss();
        } else {
            TextView view1 = (TextView) view;
            view1.setText(mOthers.get(position));
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
}
