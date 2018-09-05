package sinia.com.baihangeducation.release.campus.tabs.heatfunsearchlist.interfaces;

import sinia.com.baihangeducation.release.campus.tabs.heatfunsearchlist.bean.HotFunListBean;

public interface HotFunDataListener {
    void getHotFunDataSuccess(HotFunListBean hotFunListBean);

    void getHotFunDataFail();

}
