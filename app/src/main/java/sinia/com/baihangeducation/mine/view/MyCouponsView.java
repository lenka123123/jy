package sinia.com.baihangeducation.mine.view;

import com.example.framwork.base.BaseView;

import com.mcxtzhang.swipemenulib.info.bean.MyCouponsInfo;

/**
 * Created by Administrator on 2018/4/9.
 */

public interface MyCouponsView extends BaseView{
    String getPage();

    String getItenmNum();

    /**
     * 获取优惠券类型      1：已用；2：未用；3过期
     * @return
     */
    String getCouponsType();

    /**
     * 获取成功
     */
    void getCouponsSuccess(MyCouponsInfo myCouponsInfo,int maxpag);
}
