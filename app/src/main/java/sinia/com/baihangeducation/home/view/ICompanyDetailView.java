package sinia.com.baihangeducation.home.view;

import com.example.framwork.base.BaseView;

import com.mcxtzhang.swipemenulib.info.CompanyDetailInfo;

public interface ICompanyDetailView extends BaseView {

    /**
     * 获取页码
     * @return
     */
    String getPage();

    /**
     * 获取每页数
     * @return
     */
    String getPerpage();

    /**
     * 获取经度
     * @return
     */
    String getLocationLng();

    /**
     * 获取纬度
     * @return
     */
    String getLocationLat();

    /**
     * 获取公司ID
     * @return
     */
    String getCompanyId();

    /**
     * 获取公司详情成功
     */
    void getCompanyDetailSuccess(CompanyDetailInfo info , int maxpage);

}
