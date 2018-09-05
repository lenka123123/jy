package sinia.com.baihangeducation.release.view;

import com.example.framwork.base.BaseView;

import sinia.com.baihangeducation.release.info.ReleaseTrainingListInfo;

public interface IGetReleaseTrainingInfoView extends BaseView {
    /**
     * 行业类型编号
     * @return
     */
    String getReleaseTrainingIndustryId();

    /**
     * 级别编号
     * @return
     */
    String getReleaseTrainingLevelId();

    /**
     * 周期编号
     * @return
     */
    String getReleaseTrainingCycleId();

    /**
     * 标题
     * @return
     */
    String getReleaseTrainingTitle();

    /**
     * 副标题
     * @return
     */
    String getReleaseTrainingSubTitle();

    /**
     * 价格
     * @return
     */
    String getReleaseTrainingPrice();

    /**
     * 省份编号
     * @return
     */
    String getReleaseTrainingProvinceId();

    /**
     * 市编号
     * @return
     */
    String getReleaseTrainingCityId();

    /**
     * 区县编号
     * @return
     */
    String getReleaseTrainingAreaId();

    /**
     * 详细地址
     * @return
     */
    String getReleaseTrainingAdress();

    /**
     *联系电话
     * @return
     */
    String getReleaseTrainingTel();

    /**
     * 课时数
     * @return
     */
    String getReleaseTrainingClassNum();

    /**
     * 课时长
     * @return
     */
    String getReleaseTrainingClassDuration();

    /**
     * 上课时间
     * @return
     */
    String getReleaseTrainingClassDate();

    /**
     * 课程内容
     * @return
     */
    String getReleaseTrainingClassIntro();

    /**
     * 是否可使用优惠券 1可以 2不可以
     * @return
     */
    String getReleaseTrainingIsCoupon();

    /**
     * 培训ID  仅仅编辑培训重新提交用
     * @return
     */
    String getReleaseTrainingTrainId();

    /**
     * 封面图
     * @return
     */
    String getReleaseTrainingCover();

    void getReleaseTrainingInfoSuccess(ReleaseTrainingListInfo info);

    void releaseSuccess();
}
