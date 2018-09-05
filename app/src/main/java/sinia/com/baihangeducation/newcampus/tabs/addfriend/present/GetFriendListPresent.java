package sinia.com.baihangeducation.newcampus.tabs.addfriend.present;

import java.util.List;

import sinia.com.baihangeducation.newcampus.tabs.addfriend.interfaces.AddFriendDataListener;
import sinia.com.baihangeducation.newcampus.tabs.addfriend.interfaces.AddFriendListContract;
import sinia.com.baihangeducation.newcampus.tabs.addfriend.interfaces.GetFriendDataListener;
import sinia.com.baihangeducation.newcampus.tabs.addfriend.model.AddFriendListModel;
import sinia.com.baihangeducation.newcampus.tabs.addfriend.model.GetriendListModel;
import sinia.com.baihangeducation.newcampus.tabs.addfriend.view.AddFriendListActivity;
import sinia.com.baihangeducation.newcampus.tabs.addfriend.view.GetFriendListActivity;
import sinia.com.baihangeducation.newcampus.tabs.searchfriend.bean.GetFriendListBean;
import sinia.com.baihangeducation.newcampus.tabs.searchfriend.bean.SearchFriendListBean;

public class GetFriendListPresent implements AddFriendListContract.Presenter {

    private GetriendListModel hotFunListModel;
    private GetFriendListActivity searchFunListActivity;


    public GetFriendListPresent(GetriendListModel hotFunListModel, GetFriendListActivity searchFunListActivity) {
        this.hotFunListModel = hotFunListModel;
        this.searchFunListActivity = searchFunListActivity;

    }


    @Override
    public void getHotFunList(int type, int page, String s) {
        hotFunListModel.getHotFunList(type, page, s, new GetFriendDataListener() {


            @Override
            public void getHotFunDataSuccess(GetFriendListBean hotFunListBean) {
                searchFunListActivity.showGetFunList(hotFunListBean);
            }

            @Override
            public void getHotFunDataFail() {
                searchFunListActivity.showError("");
            }
        });
    }
}
