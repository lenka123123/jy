package sinia.com.baihangeducation.newcampus.tabs.addfriend.interfaces;


import java.util.List;

import sinia.com.baihangeducation.newcampus.tabs.searchfriend.bean.GetFriendListBean;
import sinia.com.baihangeducation.newcampus.tabs.searchfriend.bean.SearchFriendListBean;

public interface GetFriendDataListener {
    void getHotFunDataSuccess(GetFriendListBean hotFunListBean);

    void getHotFunDataFail();

}
