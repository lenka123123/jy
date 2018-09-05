package sinia.com.baihangeducation.newcampus.tabs.fans.interfaces;

import sinia.com.baihangeducation.newcampus.tabs.fans.bean.SearchFriendListBean;

public interface SearchFriendDataListener {
    void getHotFunDataSuccess(SearchFriendListBean hotFunListBean);

    void getHotFunDataFail();

}
