package sinia.com.baihangeducation.newcampus.tabs.addfriend.interfaces;


import java.util.List;

import sinia.com.baihangeducation.newcampus.tabs.searchfriend.bean.SearchFriendListBean;

public interface AddFriendDataListener {
    void getHotFunDataSuccess(List<SearchFriendListBean> hotFunListBean, int type);

    void getHotFunDataFail();

}
