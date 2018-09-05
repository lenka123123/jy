package sinia.com.baihangeducation.home.view;

import com.example.framwork.base.BaseView;

import java.util.List;

import com.mcxtzhang.swipemenulib.info.HomePartTimeListInfo;
import com.mcxtzhang.swipemenulib.info.HomePartTimeSearchListInfo;
import com.mcxtzhang.swipemenulib.info.bean.HomePartTimeInfo;

/**
 * Created by Administrator on 2018/4/24.
 */

public interface HomePartTimeView extends BaseView {

    String getCityId();

    String getType();

    String getLocationLat();

    String getLocationLng();

    String getIndustryId();

    String getMoneyId();

    String getDistId();

    String getOrderId();

    String getPager();

    String getPerpage();

    void getPartTimeSeachSuccess(HomePartTimeSearchListInfo mHomePartTimeSearchListInfo);

    void getPartTimeDataSuccess(List<HomePartTimeInfo> mHomePartTimeInfo , int maxpage);
}
