package sinia.com.baihangeducation.newcampus.tabs.addfriend.present;

import java.util.List;

import sinia.com.baihangeducation.newcampus.tabs.addfriend.interfaces.AddFriendDataListener;
import sinia.com.baihangeducation.newcampus.tabs.addfriend.interfaces.AddFriendListContract;
import sinia.com.baihangeducation.newcampus.tabs.addfriend.model.AddFriendListModel;
import sinia.com.baihangeducation.newcampus.tabs.addfriend.view.AddFriendListActivity;
import sinia.com.baihangeducation.newcampus.tabs.searchfriend.bean.SearchFriendListBean;

public class AddFriendListPresent implements AddFriendListContract.Presenter {

    private AddFriendListModel hotFunListModel;
    private AddFriendListActivity searchFunListActivity;


    public AddFriendListPresent(AddFriendListModel hotFunListModel, AddFriendListActivity searchFunListActivity) {
        this.hotFunListModel = hotFunListModel;
        this.searchFunListActivity = searchFunListActivity;

    }

    @Override
    public void getHotFunList(int type, int page, String s) {
        hotFunListModel.getHotFunList(type, page, s, new AddFriendDataListener() {
            @Override
            public void getHotFunDataSuccess(List<SearchFriendListBean> hotFunListBean, int type) {

                searchFunListActivity.showHotFunList(hotFunListBean, type);
            }

            @Override
            public void getHotFunDataFail() {
                searchFunListActivity.showError("");
            }
        });
    }
}
