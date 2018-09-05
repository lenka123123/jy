package sinia.com.baihangeducation.minecompany.avtivity;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.example.framwork.glide.ImageLoaderUtils;
import com.example.framwork.utils.Toast;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.supplement.base.BaseActivity;
import sinia.com.baihangeducation.minecompany.info.CompanyDataInfo;
import sinia.com.baihangeducation.minecompany.presenter.CompanyUCenterInfoPresenter;
import sinia.com.baihangeducation.minecompany.view.IUpdateCompanyUCenterInfoView;
import sinia.com.baihangeducation.supplement.tool.PickerUtils;

public class CompanyUCenterInfoActivity extends BaseActivity implements IUpdateCompanyUCenterInfoView {
    private CompanyUCenterInfoPresenter presenter;

    private ImageView mCompanyIcon;                         //头像
    private TextView mCompanyName;                          //企业名字
    private TextView mCompanyLegalName;                     //法人名字
    private RelativeLayout mCompanyAdressLayout;            //地址选择Layout
    private TextView mCompanyAdressContant;                 //省市区
    private EditText mCompanyAdressDetail;                  //地址详情
    private EditText mCompanyTel;                           //公司电话
    private EditText mCompanyLinkName;                      //负责人姓名
    private EditText mCompanyLinkTel;                       //负责人电话
    private TextView mCompanySave;                          //保存

    private OptionsPickerView cityPicker;                   //城市选择器
    private String mProvinceId;                             //省份ID
    private String mCityId;                                 //城市ID
    private String mDistrictId;                             //区ID

    @Override
    public int initLayoutResID() {
        return R.layout.companyucenterinfo;
    }

    @Override
    protected void initData() {

        mCommonTitle.setCenterText(R.string.companydetail);
        mCommonTitle.setBackground(getResources().getDrawable(R.color.white));

        presenter = new CompanyUCenterInfoPresenter(context, this);
        presenter.getCompanyUCenterInfo();
    }

    @Override
    protected void initView() {

        mCompanyIcon = $(R.id.companyucenterinfo_icon);
        mCompanyName = $(R.id.companyucenterinfo_companyname);
        mCompanyLegalName = $(R.id.companyucenterinfo_companylegalname);
        mCompanyAdressLayout = $(R.id.companyucenterinfo_adresslayout);
        mCompanyAdressContant = $(R.id.companyucenterinfo_adresscontant);
        mCompanyAdressDetail = $(R.id.companyucenterinfo_adressdetail);
        mCompanyTel = $(R.id.companyucenterinfo_compangtel);
        mCompanyLinkName = $(R.id.companyucenterinfo_linkname);
        mCompanyLinkTel = $(R.id.companyucenterinfo_linktel);
        mCompanySave = $(R.id.companyucenterinfo_save);

        mCompanyAdressLayout.setOnClickListener(this);
        mCompanySave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.companyucenterinfo_adresslayout:
                hideEditTextInput();
                getAddress(mCompanyAdressContant);
                cityPicker.show();
                break;
            case R.id.companyucenterinfo_adressdetail:
                hideSoftInput(mCompanyTel);
                hideSoftInput(mCompanyLinkName);
                hideSoftInput(mCompanyLinkTel);
                break;
            case R.id.companyucenterinfo_compangtel:
                hideSoftInput(mCompanyAdressDetail);
                hideSoftInput(mCompanyLinkName);
                hideSoftInput(mCompanyLinkTel);
                break;
            case R.id.companyucenterinfo_linkname:
                hideSoftInput(mCompanyAdressDetail);
                hideSoftInput(mCompanyTel);
                hideSoftInput(mCompanyLinkTel);
                break;
            case R.id.companyucenterinfo_linktel:
                hideSoftInput(mCompanyAdressDetail);
                hideSoftInput(mCompanyTel);
                hideSoftInput(mCompanyLinkName);
                break;
            case R.id.companyucenterinfo_save:
                presenter.updateCompanyInfo();
                break;
        }
    }

    private void hideEditTextInput() {
        hideSoftInput(mCompanyAdressDetail);
        hideSoftInput(mCompanyTel);
        hideSoftInput(mCompanyLinkName);
        hideSoftInput(mCompanyLinkTel);
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
    public String getprov_id() {
        return mProvinceId;
    }

    @Override
    public String getcity_id() {
        return mCityId;
    }

    @Override
    public String getdist_id() {
        return mDistrictId;
    }

    @Override
    public String getaddress() {
        return mCompanyAdressDetail.getText().toString().trim();
    }

    @Override
    public String gettel() {
        return mCompanyTel.getText().toString().trim();
    }

    @Override
    public String getlink_person() {
        return mCompanyLinkName.getText().toString().trim();
    }

    @Override
    public String getlink_phone() {
        return mCompanyLinkTel.getText().toString().trim();
    }

    @Override
    public void getCompanyInfoSuccess(CompanyDataInfo companyDataInfo) {
        if (companyDataInfo != null) {
            ImageLoaderUtils.displayRound(context, mCompanyIcon, companyDataInfo.company_logo, R.drawable.new_eorrlogo);
            mCompanyName.setText(companyDataInfo.company_name);
            mCompanyLegalName.setText(companyDataInfo.company_legal_person);

            mCompanyAdressContant.setText(PickerUtils.getLocationNameByID(context, companyDataInfo.company_prov_id,
                    companyDataInfo.company_city_id, companyDataInfo.company_dist_id));
            mProvinceId = companyDataInfo.company_prov_id;
            mCityId = companyDataInfo.company_city_id;
            mDistrictId = companyDataInfo.company_dist_id;

            mCompanyAdressDetail.setText(companyDataInfo.company_address);
            mCompanyTel.setText(companyDataInfo.company_tel);
            mCompanyLinkName.setText(companyDataInfo.company_link_person);
            mCompanyLinkTel.setText(companyDataInfo.company_link_phone);
        }
    }

    @Override
    public void updateCompanyInfoSucccess() {
        Toast.getInstance().showSuccessToast(context,"保存成功！");
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
