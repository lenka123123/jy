package sinia.com.baihangeducation.newcampus.tabs.fans.present;

import sinia.com.baihangeducation.newcampus.tabs.fans.bean.SearchFriendListBean;
import sinia.com.baihangeducation.newcampus.tabs.fans.interfaces.FansContract;
import sinia.com.baihangeducation.newcampus.tabs.fans.interfaces.SearchFriendDataListener;
import sinia.com.baihangeducation.newcampus.tabs.fans.model.fansListModel;
import sinia.com.baihangeducation.newcampus.tabs.fans.view.FansListActivity;
import sinia.com.baihangeducation.newcampus.tabs.fans.view.FollowListActivity;

public class FollowListPresent implements FansContract.Presenter {

    private fansListModel hotFunListModel;
    private FollowListActivity searchFunListActivity;


    public FollowListPresent(fansListModel hotFunListModel, FollowListActivity searchFunListActivity) {
        this.hotFunListModel = hotFunListModel;
        this.searchFunListActivity = searchFunListActivity;
    }

    @Override
    public void getHotFunList(String other_id, String type, String page, String perpage) {
        hotFunListModel.getHotFunList(other_id, type, page, perpage, new SearchFriendDataListener() {
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
