package sinia.com.baihangeducation.release.view;

import com.example.framwork.base.BaseView;

import sinia.com.baihangeducation.find.campus.info.CampusInterestingInfo;

public interface ISchoolFunView extends BaseView {
    String getPage();

    String getPerpage();

    void getSchoolFunSuccess(CampusInterestingInfo funInfo , int maxpage);
}
