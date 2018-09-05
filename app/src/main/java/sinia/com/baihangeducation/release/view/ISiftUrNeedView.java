package sinia.com.baihangeducation.release.view;

import com.example.framwork.base.BaseView;

import com.mcxtzhang.swipemenulib.info.HomePartTimeSearchListInfo;

public interface ISiftUrNeedView extends BaseView {
    String getType();             //1全职 2兼职

    String getCityId();

    void getSuccess(HomePartTimeSearchListInfo mSearchListInfo);
}
