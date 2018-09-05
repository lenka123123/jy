package sinia.com.baihangeducation.release.activity;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.example.framwork.utils.FileUtil;
import com.example.framwork.utils.Toast;
import com.imnjh.imagepicker.SImagePicker;
import com.imnjh.imagepicker.activity.PhotoPickerActivity;
import com.yanzhenjie.nohttp.Logger;

import java.util.ArrayList;
import java.util.List;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.supplement.alertview.AlertViewContorller;
import sinia.com.baihangeducation.supplement.alertview.OnItemClickListener;
import sinia.com.baihangeducation.release.info.ReleaseTrainingListInfo;
import sinia.com.baihangeducation.release.info.bean.CycleListBean;
import sinia.com.baihangeducation.release.info.bean.IndustryListBean;
import sinia.com.baihangeducation.release.info.bean.LevelListBean;
import sinia.com.baihangeducation.release.presenter.ReleaseTrainingPresenter;
import sinia.com.baihangeducation.release.view.IGetReleaseTrainingInfoView;
import sinia.com.baihangeducation.supplement.base.BaseActivity;
import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.supplement.tool.PickerUtils;

public class ReleaseTrainingAcgtivity extends BaseActivity implements IGetReleaseTrainingInfoView, OnItemClickListener {
    private ReleaseTrainingPresenter getTrainingInfoPresenter;
    private EditText mTitleEditText;                        //标题EditText
    private EditText mCoTitleEditText;                      //副标题EditText
    private RelativeLayout mCityLayout;                     //城市选择Layout
    private TextView mCityTextView;                         //城市选择TextView
    private EditText mAdressDetailEditText;                 //详细地址EditText
    private RelativeLayout mBelongIndutryLayout;            //所属行业Layout
    private TextView mBelongIndutryTextView;                //所属行业TextView
    private RelativeLayout mBelongLevelLayout;              //所属级别Layout
    private TextView mBelongLevelTextView;                  //所属级别TextView
    private RelativeLayout mBelongCycleLayout;              //所属周期Layout
    private TextView mBelongCycleTextView;                  //所属周期TextVie
    private EditText mClassTimeNumEditText;                 //课时数EditText
    private EditText mClassTimeLongEditText;                //课时长EditText
    private EditText mClassPriceEditText;                   //课程价格EditText
    private TextView mClassTimeMinTextView;                 //上课时间最小值TextView
    private TextView mClassTimeMaxTextView;                 //上课时间最大值TextView
    private RelativeLayout mIsUseCertificateLayout;         //是否可用优惠券Layout
    private TextView mIsUseCertificateTextView;             //是否可用优惠券TextView
    private RelativeLayout mUpCoverLayout;                  //上传封面图Layout
    private TextView mUpCoverTextView;                      //上传封面图Textview
    private EditText mLinkTelEditText;                      //联系电话
    private EditText mClassIntroduceEditText;               //课程介绍
    private TextView mReleaseTrainingTextView;              //发布并审核

    public List<CycleListBean> mCycleList;                  //周期列表
    public List<IndustryListBean> mIndustryList;            //行业类型列表
    public List<LevelListBean> mLevelList;                  //级别列表

    private String[] iscoupon = {"可使用优惠券", "不可使用优惠券"};                //1是  2否
    private static final int RELEASETRAININGCOVER = 114;

    private AlertViewContorller mAlertViewContorller;                           //仿ios弹窗
    private OptionsPickerView cityPicker;                   //城市选择器
    private TimePickerView timePicker;                      //日期选择器
    private String mProvinceId;                             //省份ID
    private String mCityId;                                 //城市ID
    private String mDistrictId;                             //区ID
    private String mCycleId;                                //周期ID
    private String mIndustryId;                             //行业ID
    private String mLevelId;                                //等级ID
    private String mIsCoupon = "0";                               //是否可以使用优惠券
    private String mCoverImg;                               //封面图片

    @Override
    public int initLayoutResID() {
        return R.layout.releasetraining;
    }

    @Override
    protected void initData() {

        mCommonTitle.setCenterText(R.string.releasetraining);
        mCommonTitle.setBackground(getResources().getDrawable(R.color.white));
        getTrainingInfoPresenter = new ReleaseTrainingPresenter(context, this);
        getTrainingInfoPresenter.getReleaseTrainingInfo();

    }

    @Override
    protected void initView() {
        mTitleEditText = $(R.id.releasetraining_title_edittext);
        mTitleEditText.setCursorVisible(true);
        mCoTitleEditText = $(R.id.releasetraining_cotitle_edittext);
        mCityLayout = $(R.id.releasetraining_city_layout);
        mCityTextView = $(R.id.releasetraining_city_textview);
        mAdressDetailEditText = $(R.id.releasetraining_adressdetail_edittext);
        mAdressDetailEditText.setCursorVisible(false);
        mBelongIndutryLayout = $(R.id.releasetraining_belongindutry_layout);
        mBelongIndutryTextView = $(R.id.releasetraining_belongindutry_textview);
        mBelongLevelLayout = $(R.id.releasetraining_belonglevel_layout);
        mBelongLevelTextView = $(R.id.releasetraining_belonglevel_textview);
        mBelongCycleLayout = $(R.id.releasetraining_belongcycle_layout);
        mBelongCycleTextView = $(R.id.releasetraining_belongcycle_textview);
        mClassTimeNumEditText = $(R.id.releasetraining_classtimenum_edittext);
        mClassTimeLongEditText = $(R.id.releasetraining_classtimelong_edittext);
        mClassPriceEditText = $(R.id.releasetraining_classprice_edittext);
        mClassTimeMinTextView = $(R.id.releasetraining_classtimemin_textview);
        mClassTimeMaxTextView = $(R.id.releasetraining_classtimemax_textview);
        mIsUseCertificateLayout = $(R.id.releasetraining_isusecertificate_layout);
        mIsUseCertificateTextView = $(R.id.releasetraining_isusecertificate_textview);
        mUpCoverLayout = $(R.id.releasetraining_upcover_layout);
        mUpCoverTextView = $(R.id.releasetraining_upcover_textview);
        mLinkTelEditText = $(R.id.releasetraining_linktel_edittext);
        mClassIntroduceEditText = $(R.id.releasetraining_classintroduce_edittext);
        mReleaseTrainingTextView = $(R.id.releasetraining_releasetraining_textview);

        mCityLayout.setOnClickListener(this);
        mBelongIndutryLayout.setOnClickListener(this);
        mBelongLevelLayout.setOnClickListener(this);
        mBelongCycleLayout.setOnClickListener(this);
        mClassTimeMinTextView.setOnClickListener(this);
        mClassTimeMaxTextView.setOnClickListener(this);
        mIsUseCertificateLayout.setOnClickListener(this);
        mUpCoverLayout.setOnClickListener(this);
        mReleaseTrainingTextView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.releasetraining_city_layout:
                hideEditTextInput();
                getAddress(mCityTextView);
                cityPicker.show();
                break;
            case R.id.releasetraining_belongindutry_layout:
                hideEditTextInput();
                String[] belongIndutry = new String[mIndustryList.size()];
                for (int i = 0; i < mIndustryList.size(); i++) {
                    belongIndutry[i] = mIndustryList.get(i).industry_name;
                }
                addAlerView(mBelongIndutryTextView, "所属行业", belongIndutry);
                break;

            case R.id.releasetraining_belonglevel_layout:
                hideEditTextInput();
                String[] belongLevel = new String[mLevelList.size()];
                for (int i = 0; i < mLevelList.size(); i++) {
                    belongLevel[i] = mLevelList.get(i).level_name;
                }
                addAlerView(mBelongLevelTextView, "等级要求", belongLevel);
                break;

            case R.id.releasetraining_belongcycle_layout:
                hideEditTextInput();
                String[] belongCycle = new String[mCycleList.size()];
                for (int i = 0; i < mCycleList.size(); i++) {
                    belongCycle[i] = mCycleList.get(i).cycle_name;
                }
                addAlerView(mBelongCycleTextView, "周期要求", belongCycle);
                break;

            case R.id.releasetraining_classtimemin_textview:
                hideEditTextInput();
                timePicker = PickerUtils.initMinTimePicker(this, mClassTimeMinTextView);
                timePicker.show();
                break;

            case R.id.releasetraining_classtimemax_textview:
                hideEditTextInput();
                timePicker = PickerUtils.initMaxTimePicker(this, mClassTimeMaxTextView, mClassTimeMinTextView.getText().toString());
                timePicker.show();
                break;

            case R.id.releasetraining_isusecertificate_layout:
                hideEditTextInput();
                addAlerView(mIsUseCertificateTextView, "是否可以使用优惠券", iscoupon);
                break;

            case R.id.releasetraining_upcover_layout:
                hideEditTextInput();
                takePhoto(RELEASETRAININGCOVER);
                break;

            case R.id.releasetraining_releasetraining_textview:
                getTrainingInfoPresenter.releaseTrainingInfo();
                break;
        }
    }

    private void hideEditTextInput() {
        hideSoftInput(mTitleEditText);
        hideSoftInput(mCoTitleEditText);
        hideSoftInput(mAdressDetailEditText);
        hideSoftInput(mClassTimeNumEditText);
        hideSoftInput(mClassTimeLongEditText);
        hideSoftInput(mClassPriceEditText);
        hideSoftInput(mLinkTelEditText);
        hideSoftInput(mClassIntroduceEditText);
    }

    private void takePhoto(int type) {
        SImagePicker
                .from(context)
                .pickMode(SImagePicker.MODE_AVATAR)
                .showCamera(true).rowCount(3)
                .cropFilePath(
                        AppConfig.IMAGE_PATH + "/releasecover.png")
                .forResult(type);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == RELEASETRAININGCOVER) {
            ArrayList<String> pathList = data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT_SELECTION);
            if (pathList != null && pathList.size() != 0) {
                Logger.d(FileUtil.getInstance().getFileSize(context, pathList.get(0)));
                mCoverImg = pathList.get(0);
                Log.i("图片地址", pathList.get(0));
                mUpCoverTextView.setText("已经选择");
            }
        }
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
    public String getReleaseTrainingIndustryId() {
        return mIndustryId;
    }

    @Override
    public String getReleaseTrainingLevelId() {
        return mLevelId;
    }

    @Override
    public String getReleaseTrainingCycleId() {
        return mCycleId;
    }

    @Override
    public String getReleaseTrainingTitle() {
        return mTitleEditText.getText().toString().trim();
    }

    @Override
    public String getReleaseTrainingSubTitle() {
        return mCoTitleEditText.getText().toString().trim();
    }

    @Override
    public String getReleaseTrainingPrice() {
        return mClassPriceEditText.getText().toString().trim();
    }

    @Override
    public String getReleaseTrainingProvinceId() {
        return mProvinceId;
    }

    @Override
    public String getReleaseTrainingCityId() {
        return mCityId;
    }

    @Override
    public String getReleaseTrainingAreaId() {
        return mDistrictId;
    }

    @Override
    public String getReleaseTrainingAdress() {
        return mAdressDetailEditText.getText().toString().trim();
    }

    @Override
    public String getReleaseTrainingTel() {
        return mLinkTelEditText.getText().toString().trim();
    }

    @Override
    public String getReleaseTrainingClassNum() {
        return mClassTimeNumEditText.getText().toString().trim();
    }

    @Override
    public String getReleaseTrainingClassDuration() {
        return mClassTimeLongEditText.getText().toString().trim();
    }

    @Override
    public String getReleaseTrainingClassDate() {
        if (!TextUtils.isEmpty(mClassTimeMinTextView.getText().toString().trim()) && !TextUtils.isEmpty(mClassTimeMaxTextView.getText().toString().trim()))
            return mClassTimeMinTextView.getText().toString().trim() + "至" + mClassTimeMaxTextView.getText().toString().trim();
        else
            return null;
    }

    @Override
    public String getReleaseTrainingClassIntro() {
        return mClassIntroduceEditText.getText().toString().trim();
    }

    @Override
    public String getReleaseTrainingIsCoupon() {
        return "2";
    }

    @Override
    public String getReleaseTrainingTrainId() {
        return null;
    }

    @Override
    public String getReleaseTrainingCover() {
        return mCoverImg;
    }

    @Override
    public void getReleaseTrainingInfoSuccess(ReleaseTrainingListInfo info) {
        if (info != null) {
            if (info.cycle_list != null && info.cycle_list.size() > 0) {
                mCycleList = info.cycle_list;
            }
            if (info.industry_list != null && info.industry_list.size() > 0) {
                mIndustryList = info.industry_list;
            }
            if (info.level_list != null && info.level_list.size() > 0) {
                mLevelList = info.level_list;
            }
        }

    }

    @Override
    public void releaseSuccess() {
        Toast.getInstance().showSuccessToast(context, "发布成功！");
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

    @Override
    public void onItemClick(View view, List<String> mOthers, Object o, int position) {
        if (position == -1) {
            mAlertViewContorller.dismiss();
        } else {
            TextView view1 = (TextView) view;
            view1.setText(mOthers.get(position));
            for (int i = 0; i < mCycleList.size(); i++) {
                if (mOthers.get(position).equals(mCycleList.get(i).cycle_name)) {
                    mCycleId = mCycleList.get(i).cycle_id + "";
                }
            }
            for (int i = 0; i < mIndustryList.size(); i++) {
                if (mOthers.get(position).equals(mIndustryList.get(i).industry_name)) {
                    mIndustryId = mIndustryList.get(i).industry_id + "";
                }
            }
            for (int i = 0; i < mLevelList.size(); i++) {
                if (mOthers.get(position).equals(mLevelList.get(i).level_name)) {
                    mLevelId = mLevelList.get(i).level_id + "";
                }
            }
            if ((mOthers.get(position)).equals("可使用优惠券")) {
                mIsCoupon = "1";
            } else if ((mOthers.get(position)).equals("不可使用优惠券")) {
                mIsCoupon = "2";
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
}
