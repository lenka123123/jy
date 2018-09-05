package sinia.com.baihangeducation.home.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.example.framwork.glide.ImageLoaderUtils;
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
import sinia.com.baihangeducation.supplement.base.BaseActivity;
import sinia.com.baihangeducation.MyApplication;
import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.home.present.CompanyJoinInPresenter;
import sinia.com.baihangeducation.home.view.CompanyJoinInView;
import sinia.com.baihangeducation.supplement.tool.PickerUtils;
import sinia.com.baihangeducation.supplement.tool.BaseUtil;

/**
 * Created by Administrator on 2018/4/23.
 */

public class CompanyJoinInActivity extends BaseActivity implements CompanyJoinInView, OnItemClickListener {

    private static final int COMPANY_LOGO = 110;
    private static final int LICENSE_IMG = 111;
    private AlertViewContorller mAlertViewContorller;//避免创建重复View，先创建View，然后需要的时候show出来，推荐这个做法
    private String[] organization = {"普通企业", "培训机构"};  //2普通企业  3培训机构
    private String compangLogo;                 //企业logo
    private String licenseImg;                  //营业执照
    private String mProvinceId;                  //省份ID
    private String mCityId;                      //城市ID
    private String mDistrictId;                  //区ID

    private LinearLayout mHeadLayout;           //头像layout 点击放入头像
    private ImageView mHeadImg;                 //头像
    private EditText mLegalName;                //法人姓名
    private EditText mLicenseName;              //营业执照全称
    private TextView mAdress;                   //地址
    private TextView mIsTraning;                //是否为培训机构
    private EditText mAdressDetail;             //地址详情
    private EditText mCompanyTel;               //企业电话
    private EditText mHeadName;                 //负责人姓名
    private EditText mHeadTel;                  //负责人电话
    private RelativeLayout mOutLayout;          //上传图片最外层layout 用于点击事件
    private ImageView mOutImage;                //上传图片最外层ImageView 用于点最后显示图片
    private ImageView mInImage;                //上传图片最里层ImageView 用于显示提示图片
    private TextView mSubmit;                   //提交

    private CompanyJoinInPresenter mCompanyJoinInPresenter;
    private OptionsPickerView cityPicker;

    private MyApplication application;

    @Override
    public int initLayoutResID() {
        return R.layout.fragment_home_commpanyjoinin;
    }

    @Override
    protected void initData() {
        application = (MyApplication) context.getApplication();

        mCommonTitle.setCenterText(R.string.company_joinin);
        mCommonTitle.setBackgroundColor(Color.WHITE);

        mCompanyJoinInPresenter = new CompanyJoinInPresenter(context, this);
    }

    @Override
    protected void initView() {
        mHeadLayout = $(R.id.fragment_home_commpanyjoinin_headlayout);
        mHeadImg = $(R.id.fragment_home_commpanyjoinin_img);
        mLegalName = $(R.id.fragment_home_commpanyjoinin_legalname);
        mLicenseName = $(R.id.fragment_home_commpanyjoinin_licensename);
        mAdress = $(R.id.fragment_home_commpanyjoinin_adress);
        mAdressDetail = $(R.id.fragment_home_commpanyjoinin_adressdetail);
        mCompanyTel = $(R.id.fragment_home_commpanyjoinin_companytel);
        mHeadName = $(R.id.fragment_home_commpanyjoinin_headname);
        mHeadTel = $(R.id.fragment_home_commpanyjoinin_headtel);
        mOutLayout = $(R.id.fragment_home_commpanyjoinin_outimglayout);
        mOutImage = $(R.id.fragment_home_commpanyjoinin_outimg);
        mInImage = $(R.id.fragment_home_commpanyjoinin_intimg);
        mSubmit = $(R.id.fragment_home_commpanyjoinin_submit);
        mIsTraning = $(R.id.fragment_home_commpanyjoinin_istraing);

        mHeadLayout.setOnClickListener(this);
        mHeadImg.setOnClickListener(this);
        mLegalName.setOnClickListener(this);
        mLicenseName.setOnClickListener(this);
        mAdress.setOnClickListener(this);
        mAdressDetail.setOnClickListener(this);
        mCompanyTel.setOnClickListener(this);
        mHeadName.setOnClickListener(this);
        mHeadTel.setOnClickListener(this);
        mOutLayout.setOnClickListener(this);
        mOutImage.setOnClickListener(this);
        mInImage.setOnClickListener(this);
        mSubmit.setOnClickListener(this);
        mIsTraning.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.fragment_home_commpanyjoinin_headlayout:
                //选择LOGO
                hideSoftInput(mLegalName);
                hideSoftInput(mLicenseName);
                hideSoftInput(mAdressDetail);
                hideSoftInput(mCompanyTel);
                hideSoftInput(mHeadName);
                hideSoftInput(mHeadTel);
                takePhoto(COMPANY_LOGO);
                break;
            case R.id.fragment_home_commpanyjoinin_legalname:
                //输入法人姓名
                break;
            case R.id.fragment_home_commpanyjoinin_licensename:
                //营业执照全称
                hideSoftInput(mLegalName);
                showSoftInput(mLicenseName);
                hideSoftInput(mAdressDetail);
                hideSoftInput(mCompanyTel);
                hideSoftInput(mHeadName);
                hideSoftInput(mHeadTel);
                break;
            case R.id.fragment_home_commpanyjoinin_adress:
                //选择地址
                hideSoftInput(mLegalName);
                hideSoftInput(mLicenseName);
                hideSoftInput(mAdressDetail);
                hideSoftInput(mCompanyTel);
                hideSoftInput(mHeadName);
                hideSoftInput(mHeadTel);
                getAddress(mAdress);
                cityPicker.show();
                break;
            case R.id.fragment_home_commpanyjoinin_adressdetail:
                //输入地址详情
                hideSoftInput(mLegalName);
                hideSoftInput(mLicenseName);
                showSoftInput(mAdressDetail);
                hideSoftInput(mCompanyTel);
                hideSoftInput(mHeadName);
                hideSoftInput(mHeadTel);
                break;
            case R.id.fragment_home_commpanyjoinin_companytel:
                //输入企业电话
                hideSoftInput(mLegalName);
                hideSoftInput(mLicenseName);
                hideSoftInput(mAdressDetail);
                showSoftInput(mCompanyTel);
                hideSoftInput(mHeadName);
                hideSoftInput(mHeadTel);
                break;
            case R.id.fragment_home_commpanyjoinin_headname:
                //输入负责人姓名
                hideSoftInput(mLegalName);
                hideSoftInput(mLicenseName);
                hideSoftInput(mAdressDetail);
                hideSoftInput(mCompanyTel);
                showSoftInput(mHeadName);
                hideSoftInput(mHeadTel);
                break;
            case R.id.fragment_home_commpanyjoinin_headtel:
                //输入负责人电话
                hideSoftInput(mLegalName);
                hideSoftInput(mLicenseName);
                hideSoftInput(mAdressDetail);
                hideSoftInput(mCompanyTel);
                hideSoftInput(mHeadName);
                showSoftInput(mHeadTel);
                break;
            case R.id.fragment_home_commpanyjoinin_istraing:
                hideSoftInput(mLegalName);
                hideSoftInput(mLicenseName);
                hideSoftInput(mAdressDetail);
                hideSoftInput(mCompanyTel);
                hideSoftInput(mHeadName);
                hideSoftInput(mHeadTel);
                mAlertViewContorller = new AlertViewContorller(mIsTraning, "选择你的机构类别", null, "取消", null, organization,
                        context, AlertViewContorller.Style.ActionSheet, this);
                mAlertViewContorller.setCancelable(true).show();
                break;
            case R.id.fragment_home_commpanyjoinin_outimg:
                //点击添加营业执照
                takePhoto(LICENSE_IMG);
                break;
            case R.id.fragment_home_commpanyjoinin_submit:
                //提交
                if (!BaseUtil.isLogin(context, application)) {
                    return;
                }
                mCompanyJoinInPresenter.submitCompanyInfo();
                break;
        }
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

    private void takePhoto(int type) {
        switch (type) {
            case COMPANY_LOGO:
                SImagePicker
                        .from(CompanyJoinInActivity.this)
                        .pickMode(SImagePicker.MODE_IMAGE)
                        .showCamera(true).rowCount(3)
                        .cropFilePath(
                                AppConfig.IMAGE_PATH + "/company_logo.png")
                        .forResult(type);


                break;
            case LICENSE_IMG:
                SImagePicker
                        .from(context)
                        .pickMode(SImagePicker.MODE_AVATAR)
                        .showCamera(true).rowCount(3)
                        .cropFilePath(
                                AppConfig.IMAGE_PATH + "/license_img.png")
                        .forResult(type);
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == COMPANY_LOGO) {
            ArrayList<String> pathList = data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT_SELECTION);
            if (pathList != null && pathList.size() != 0) {
                Logger.d(FileUtil.getInstance().getFileSize(context, pathList.get(0)));
                compangLogo = pathList.get(0);
                Log.i("图片地址", pathList.get(0));
                ImageLoaderUtils.display(context, mHeadImg, pathList.get(0), R.drawable.logo, true);
            }
        } else if (resultCode == Activity.RESULT_OK && requestCode == LICENSE_IMG) {
            ArrayList<String> pathList = data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT_SELECTION);
            if (pathList != null && pathList.size() != 0) {
                Logger.d(FileUtil.getInstance().getFileSize(context, pathList.get(0)));
                licenseImg = pathList.get(0);
                ImageLoaderUtils.display(context, mOutImage, pathList.get(0), R.drawable.logo, true);
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
    public String getLogo() {
        return compangLogo;
    }

    @Override
    public String getLegalName() {
        return mLegalName.getText().toString().trim();
    }

    @Override
    public String getCompanyName() {
        return mLicenseName.getText().toString().trim();
    }

    /**
     * 1是普通企业 2是培训机构
     *
     * @return
     */
    @Override
    public String getOrganizationType() {
        return (mIsTraning.getText().toString().trim()).equals("培训机构") ? "3" : "2";
    }

    @Override
    public String getProvinceId() {
        return mProvinceId;
    }

    @Override
    public String getCityId() {
        return mCityId;
    }

    @Override
    public String getDistId() {
        return mDistrictId;
    }

    @Override
    public String getAdressDetail() {
        return mAdressDetail.getText().toString().trim();
    }

    @Override
    public String getCompanyTel() {
        return mCompanyTel.getText().toString().trim();
    }

    @Override
    public String getHeadName() {
        return mHeadName.getText().toString().trim();
    }

    @Override
    public String getHeadTel() {
        return mHeadTel.getText().toString().trim();
    }

    @Override
    public String getLicensePhoto() {
        return licenseImg;
    }

    @Override
    public void submitCompanyInfoSuccess() {
        Toast.getInstance().showSuccessToast(context, "恭喜你，提交成功！");
        finish();
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
