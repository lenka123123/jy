package sinia.com.baihangeducation.home.view;

import com.example.framwork.base.BaseView;

import com.mcxtzhang.swipemenulib.info.HomeAndFindHelpEachOtherListInfo;

/**
 * Created by Administrator on 2018/4/21.
 */

public interface HomeAndFindHelpEachOtherView extends BaseView {

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
     * 纬度
     *
     * @return
     */
    String getLocationLat();

    /**
     * 经度
     *
     * @return
     */
    String getLocationLng();

    /**
     * 类型 1转让 2互助
     *
     * @return
     */
    String getType();

    void getHomeAndFindHelpEachOtherDataSuccess(HomeAndFindHelpEachOtherListInfo datas , int maxpage);
}
