package sinia.com.baihangeducation.find.campus.view;

import com.example.framwork.base.BaseView;

import java.util.List;

import sinia.com.baihangeducation.find.campus.info.bean.CampusInterestingListInfo;

import com.mcxtzhang.swipemenulib.info.bean.HomePartTimeInfo;

/**
 * Created by Administrator on 2018/4/27.
 */

public interface CampusView extends BaseView {

    String getCityId();

    String getType();

    String getLocationLat();

    String getLocationLng();

    String getCampusJobPager();

    String getCampusInterestingPager();

    String getPerpage();

    void getCampusJobInfoSuccess(List<HomePartTimeInfo> mHomePartTimeInfo, int maxpage);

    void getCampusJobInterestingSuccess(List<CampusInterestingListInfo> list, int maxpage);
}
