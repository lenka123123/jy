package sinia.com.baihangeducation.release.view;

import com.example.framwork.base.BaseView;

public interface ISendCompanyJobView extends BaseView {
    /**
     * 职位类别：1、全职，2、兼职
     * @return
     */
    String getReleaseType();

    /**
     * 职位类型编号 多个英文逗号分隔
     * @return
     */
    String getJobTypeIds();

    /**
     * 职位标签编号 多个英文逗号分隔
     * @return
     */
    String getTagIds();

    /**
     * 所属行业
     * @return
     */
    String getIndustryId();

    /**
     * 职位名称
     * @return
     */
    String getReleaseTitle();

    /**
     * 职位内容
     * @return
     */
    String getReleaseContent();

    /**
     * 最低招聘人数
     * @return
     */
    String getNumLower();

    /**
     * 最高招聘人数
     * @return
     */
    String getNumUpper();

    /**
     * 薪资类型编号
     * @return
     */
    String getMoneyType();

    /**
     * 最低工资( 仅全职传递 )
     * @return
     */
    String getMoneyLower();

    /**
     * 最高工资( 仅全职传递 )
     * @return
     */
    String getMoneyUpper();

    /**
     * 语言要求
     * @return
     */
    String getLanguage();

    /**
     *  学历编号
     * @return
     */
    String getEducationId();

    /**
     *  工作经验编号
     * @return
     */
    String getExperienceId();

    /**
     *  性别编号
     * @return
     */
    String getSexId();

    /**
     *  开始日期    ( 仅兼职传递 )
     * @return
     */
    String getDateStart();

    /**
     *  结束日期    ( 仅兼职传递 )
     * @return
     */
    String getDateEnd();

    /**
     *  工作开始时间
     * @return
     */
    String getTimeStart();

    /**
     *  工作结束时间
     * @return
     */
    String getTimeEnd();

    /**
     *  省份编号
     * @return
     */
    String getProvId();

    /**
     *  市编号
     * @return
     */
    String getReleaseCityId();

    /**
     *  区县编号
     * @return
     */
    String getDistId();

    /**
     *  详细地址
     * @return
     */
    String getAddress();

    /**
     *  联系人
     * @return
     */
    String getLinkPerson();

    /**
     *  联系电话
     * @return
     */
    String getLinkPhone();

    /**
     * 编辑专有键值
     * @return
     */
    String getEditID();

    /**
     * 发布成功
     */
    void getReleaseSuccess();


    String getAgeLower();
}
