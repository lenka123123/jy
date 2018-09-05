package sinia.com.baihangeducation.minecompany.view;

import com.example.framwork.base.BaseView;

import sinia.com.baihangeducation.minecompany.info.CompanyDataInfo;

public interface IUpdateCompanyUCenterInfoView extends BaseView {

    String getprov_id();

    String getcity_id();

    String getdist_id();

    String getaddress();

    String gettel();

    String getlink_person();

    String getlink_phone();

    void getCompanyInfoSuccess(CompanyDataInfo companyDataInfo);

    void updateCompanyInfoSucccess();
}
