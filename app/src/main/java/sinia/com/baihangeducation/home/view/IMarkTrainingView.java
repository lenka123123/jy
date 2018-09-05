package sinia.com.baihangeducation.home.view;

import com.example.framwork.base.BaseView;

public interface IMarkTrainingView extends BaseView{

    /**
     * 培训ID    ( 必传 )
     * @return
     */
    String getTrainingId();

    /**
     * 真实姓名    ( 必传 )
     * @return
     */
    String getRealName();

    /**
     * 联系电话（手机)    ( 必传 )
     * @return
     */
    String getContactTel();

    /**
     * 联系邮箱    ( 必传 )
     * @return
     */
    String getEmail();

    /**
     * 留言    ( 非必传 )
     * @return
     */
    String getMarkTraingMessage();

    /**
     * 用户优惠券ID    ( 非必传 )
     * @return
     */
    String getUserCouponId();

    void joinSuccess();
}
