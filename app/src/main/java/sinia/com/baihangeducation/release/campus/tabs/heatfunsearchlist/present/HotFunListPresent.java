package sinia.com.baihangeducation.release.campus.tabs.heatfunsearchlist.present;

import sinia.com.baihangeducation.release.campus.tabs.heatfunsearchlist.bean.HotFunListBean;
import sinia.com.baihangeducation.release.campus.tabs.heatfunsearchlist.interfaces.HotFunDataListener;
import sinia.com.baihangeducation.release.campus.tabs.heatfunsearchlist.interfaces.HotFunListContract;
import sinia.com.baihangeducation.release.campus.tabs.heatfunsearchlist.model.HotFunListModel;
import sinia.com.baihangeducation.release.campus.tabs.heatfunsearchlist.view.SearchFunSearchListActivity;

public class HotFunListPresent implements HotFunListContract.Presenter {

    private HotFunListModel hotFunListModel;
    private SearchFunSearchListActivity searchFunListActivity;


    public HotFunListPresent(HotFunListModel hotFunListModel, SearchFunSearchListActivity searchFunListActivity) {
        this.hotFunListModel = hotFunListModel;
        this.searchFunListActivity = searchFunListActivity;

    }

    @Override
    public void getHotFunList(int type, int page,String s) {
        hotFunListModel.getHotFunList(type, page, s,new HotFunDataListener() {
            @Override
            public void getHotFunDataSuccess(HotFunListBean hotFunListBean) {
                searchFunListActivity.showHotFunList(hotFunListBean);
            }

            @Override
            public void getHotFunDataFail() {
                searchFunListActivity.showError("");
            }
        });
    }
}
