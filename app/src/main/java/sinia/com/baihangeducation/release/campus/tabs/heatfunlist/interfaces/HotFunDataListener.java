package sinia.com.baihangeducation.release.campus.tabs.heatfunlist.interfaces;

import sinia.com.baihangeducation.release.campus.tabs.heatfunlist.bean.HotFunListBean;

public interface HotFunDataListener {
    void getHotFunDataSuccess(HotFunListBean hotFunListBean);

    void getHotFunDataFail();

}
