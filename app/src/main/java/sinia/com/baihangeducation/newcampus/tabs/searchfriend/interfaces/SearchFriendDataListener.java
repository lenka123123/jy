package sinia.com.baihangeducation.newcampus.tabs.searchfriend.interfaces;

import sinia.com.baihangeducation.newcampus.tabs.searchfriend.bean.SearchFriendListBean;

public interface SearchFriendDataListener {
    void getHotFunDataSuccess(SearchFriendListBean hotFunListBean);

    void getHotFunDataFail();

}
