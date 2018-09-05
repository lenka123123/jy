package sinia.com.baihangeducation.newcampus.tabs.searchfriend.present;

import sinia.com.baihangeducation.newcampus.tabs.searchfriend.bean.SearchFriendListBean;
import sinia.com.baihangeducation.newcampus.tabs.searchfriend.interfaces.SearchFriendDataListener;
import sinia.com.baihangeducation.newcampus.tabs.searchfriend.interfaces.SearchFriendListContract;
import sinia.com.baihangeducation.newcampus.tabs.searchfriend.model.SearchFriendListModel;
import sinia.com.baihangeducation.newcampus.tabs.searchfriend.view.SearchFriendListActivity;

public class SearchFriendListPresent implements SearchFriendListContract.Presenter {

    private SearchFriendListModel hotFunListModel;
    private SearchFriendListActivity searchFunListActivity;


    public SearchFriendListPresent(SearchFriendListModel hotFunListModel, SearchFriendListActivity searchFunListActivity) {
        this.hotFunListModel = hotFunListModel;
        this.searchFunListActivity = searchFunListActivity;

    }

    @Override
    public void getHotFunList(int type, int page,String s) {
        hotFunListModel.getHotFunList(type, page, s,new SearchFriendDataListener() {
            @Override
            public void getHotFunDataSuccess(SearchFriendListBean hotFunListBean) {
                searchFunListActivity.showHotFunList(hotFunListBean);
            }

            @Override
            public void getHotFunDataFail() {
                searchFunListActivity.showError("");
            }
        });
    }
}
