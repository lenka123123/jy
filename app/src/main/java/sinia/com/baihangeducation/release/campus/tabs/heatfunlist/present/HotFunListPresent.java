package sinia.com.baihangeducation.release.campus.tabs.heatfunlist.present;

import sinia.com.baihangeducation.release.campus.tabs.heatfunlist.bean.HotFunListBean;
import sinia.com.baihangeducation.release.campus.tabs.heatfunlist.interfaces.HotFunDataListener;
import sinia.com.baihangeducation.release.campus.tabs.heatfunlist.interfaces.HotFunListContract;
import sinia.com.baihangeducation.release.campus.tabs.heatfunlist.model.HotFunListModel;
import sinia.com.baihangeducation.release.campus.tabs.heatfunlist.view.SearchFunListActivity;

public class HotFunListPresent implements HotFunListContract.Presenter {

    private HotFunListModel hotFunListModel;
    private SearchFunListActivity searchFunListActivity;


    public HotFunListPresent(HotFunListModel hotFunListModel, SearchFunListActivity searchFunListActivity) {
        this.hotFunListModel = hotFunListModel;
        this.searchFunListActivity = searchFunListActivity;

    }

    @Override
    public void getHotFunList(int type, int page) {
        hotFunListModel.getHotFunList(type, page, new HotFunDataListener() {
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
