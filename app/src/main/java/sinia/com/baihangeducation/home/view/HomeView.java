package sinia.com.baihangeducation.home.view;

import com.example.framwork.base.BaseView;

import java.util.List;

import com.mcxtzhang.swipemenulib.info.CityIdInfo;
import com.mcxtzhang.swipemenulib.info.HomeListInfo;
import com.mcxtzhang.swipemenulib.info.IsCompleteInfo;
import com.mcxtzhang.swipemenulib.info.bean.CityInfo;

/**
 * Created by Administrator on 2018/4/22.
 */

public interface HomeView extends BaseView {

    String getLocationAdcode();

    String getPage();

    String getPerpage();

    String getAdCode();

    String getCityID();

    String getLocationLng();

    String getLocationLat();

    void getCityListSuccess(List<CityInfo> mCityInfo);

    void getHomeInfoSuccess(HomeListInfo mHomeListInfo, int maxpage);

    void getCityIdSuccess(CityIdInfo cityIdInfo);

    void getCompleteInfoSuccess(IsCompleteInfo mIsCompleteInfo ,int position);
}
