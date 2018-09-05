package sinia.com.baihangeducation.home.view;

import com.example.framwork.base.BaseView;

import com.mcxtzhang.swipemenulib.info.HomeTraingDataListInfo;
import com.mcxtzhang.swipemenulib.info.HomeTraingSeachListInfo;

/**
 * Created by Administrator on 2018/4/24.
 */

public interface HomeTraingView extends BaseView {

    /**
     * 获取页码
     *
     * @return
     */
    String getPage();

    /**
     * 获取每页数
     */
    String getPerpage();

    /**
     * 行业ID    ( 非必传 )
     *
     * @return
     */
    String getIndustryId();

    /**
     * 级别ID    ( 非必传 )
     *
     * @return
     */
    String getLevelId();

    /**
     * 周期ID    ( 非必传 )
     *
     * @return
     */
    String getWeekId();

    /**
     * 排序ID    ( 非必传 )
     *
     * @return
     */
    String getOrderId();

    void getTraingInfoSuccess(HomeTraingDataListInfo mHomeTraingDataListInfo, int maxpage);

    void getSeachListSuccess(HomeTraingSeachListInfo mHomeTraingSeachListInfo);
}
