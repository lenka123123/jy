package sinia.com.baihangeducation.home.view;

import com.example.framwork.base.BaseView;

/**
 * Created by Administrator on 2018/4/24.
 */

public interface CompanyJoinInView extends BaseView {
    /**
     * 获取LOGO
     *
     * @return
     */
    String getLogo();

    /**
     * 获取法人名字
     *
     * @return
     */
    String getLegalName();

    /**
     * 获取企业名字
     *
     * @return
     */
    String getCompanyName();

    /**
     * 获取机构类型
     *
     * @return
     */
    String getOrganizationType();

    /**
     * 获取省份ID
     *
     * @return
     */
    String getProvinceId();

    /**
     * 获取城市ID
     *
     * @return
     */
    String getCityId();

    /**
     * 获取区ID
     *
     * @return
     */
    String getDistId();

    /**
     * 获取地址详情
     *
     * @return
     */
    String getAdressDetail();

    /**
     * 获取公司电话
     *
     * @return
     */
    String getCompanyTel();

    /**
     * 获取负责人姓名
     *
     * @return
     */
    String getHeadName();

    /**
     * 获取负责人电话
     *
     * @return
     */
    String getHeadTel();

    /**
     * 获取负责人电话
     *
     * @return
     */
    String getLicensePhoto();

    void submitCompanyInfoSuccess();
}
