package sinia.com.baihangeducation.find.presenter;

import android.app.Activity;

import com.example.framwork.base.BasePresenter;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.OnRequestListener;
import com.example.framwork.utils.CommonUtil;
import com.example.framwork.utils.Toast;

import java.util.HashMap;

import com.mcxtzhang.swipemenulib.info.SearchResultFunnyListInfo;
import com.mcxtzhang.swipemenulib.info.SearchResultInfomationListInfo;
import com.mcxtzhang.swipemenulib.info.SearchResultSSTListInfo;
import sinia.com.baihangeducation.find.view.SearchReasultView;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;

/**
 * Created by Administrator on 2018/4/18.
 */

public class SearchReasultPresenter extends BasePresenter {
    private Activity activity;
    private SearchReasultView view;

    public SearchReasultPresenter(Activity activity, SearchReasultView view) {
        super(activity);
        this.activity = activity;
        this.view = view;
    }

    public void getFunnyData() {
        HashMap getFunnyInfo = BaseRequestInfo.getInstance().getRequestInfo(activity, "getSearchInfo", "home", false);
        getFunnyInfo.put("page", view.getPage());
        getFunnyInfo.put("perpage", view.getPerpage());
        getFunnyInfo.put("type", view.getType());
        getFunnyInfo.put("keyword", view.getKeyWord());
        post(getFunnyInfo, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                SearchResultFunnyListInfo mSearchResultFunnyListInfo = bean.parseObject(SearchResultFunnyListInfo.class);
                int maxpag = CommonUtil.getMaxPage(mSearchResultFunnyListInfo.count, mSearchResultFunnyListInfo.perpage);
                view.getSearchFunnyData(mSearchResultFunnyListInfo.list, maxpag);
            }

            @Override
            public void requestFailed(String error) {
                Toast.getInstance().showErrorToast(activity, error);
            }

            @Override
            public void requestFinish() {
                view.hideLoading();
            }
        });
    }

    public void getStrategyData() {
        HashMap getStrategyInfo = BaseRequestInfo.getInstance().getRequestInfo(activity, "getSearchInfo", "home", false);
        getStrategyInfo.put("page", view.getPage());
        getStrategyInfo.put("perpage", view.getPerpage());
        getStrategyInfo.put("type", view.getType());
        getStrategyInfo.put("keyword", view.getKeyWord());
        post(getStrategyInfo, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                SearchResultSSTListInfo mSearchResultSSTListInfo = bean.parseObject(SearchResultSSTListInfo.class);
                int maxpag = CommonUtil.getMaxPage(mSearchResultSSTListInfo.count, mSearchResultSSTListInfo.perpage);
                view.getSearchStrategyData(mSearchResultSSTListInfo.list, maxpag);
            }

            @Override
            public void requestFailed(String error) {
                Toast.getInstance().showErrorToast(activity, error);
            }

            @Override
            public void requestFinish() {
                view.hideLoading();
            }
        });
    }

    public void getTestData() {
        HashMap getTestInfo = BaseRequestInfo.getInstance().getRequestInfo(activity, "getSearchInfo", "home", false);
        getTestInfo.put("page", view.getPage());
        getTestInfo.put("perpage", view.getPerpage());
        getTestInfo.put("type", view.getType());
        getTestInfo.put("keyword", view.getKeyWord());
        post(getTestInfo, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                SearchResultSSTListInfo mSearchResultSSTListInfo = bean.parseObject(SearchResultSSTListInfo.class);
                int maxpag = CommonUtil.getMaxPage(mSearchResultSSTListInfo.count, mSearchResultSSTListInfo.perpage);
                view.getSearchTestData(mSearchResultSSTListInfo.list, maxpag);
            }

            @Override
            public void requestFailed(String error) {
                Toast.getInstance().showErrorToast(activity, error);
            }

            @Override
            public void requestFinish() {
                view.hideLoading();
            }
        });
    }

    public void getSecretData() {
        HashMap getSecretInfo = BaseRequestInfo.getInstance().getRequestInfo(activity, "getSearchInfo", "home", false);
        getSecretInfo.put("page", view.getPage());
        getSecretInfo.put("perpage", view.getPerpage());
        getSecretInfo.put("type", view.getType());
        getSecretInfo.put("keyword", view.getKeyWord());
        post(getSecretInfo, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                SearchResultSSTListInfo mSearchResultSSTListInfo = bean.parseObject(SearchResultSSTListInfo.class);
                int maxpag = CommonUtil.getMaxPage(mSearchResultSSTListInfo.count, mSearchResultSSTListInfo.perpage);
                view.getSearchSecretData(mSearchResultSSTListInfo.list, maxpag);
            }

            @Override
            public void requestFailed(String error) {
                Toast.getInstance().showErrorToast(activity, error);
            }

            @Override
            public void requestFinish() {
                view.hideLoading();
            }
        });
    }

    public void getInformationData() {
        HashMap getInformationInfo = BaseRequestInfo.getInstance().getRequestInfo(activity, "getSearchInfo", "home", false);
        getInformationInfo.put("page", view.getPage());
        getInformationInfo.put("perpage", view.getPerpage());
        getInformationInfo.put("type", view.getType());
        getInformationInfo.put("keyword", view.getKeyWord());
        post(getInformationInfo, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                SearchResultInfomationListInfo mSearchResultInfomationListInfo = bean.parseObject(SearchResultInfomationListInfo.class);
                int maxpag = CommonUtil.getMaxPage(mSearchResultInfomationListInfo.count, mSearchResultInfomationListInfo.perpage);
                view.getSearchInformationData(mSearchResultInfomationListInfo.list, maxpag);
            }

            @Override
            public void requestFailed(String error) {
                Toast.getInstance().showErrorToast(activity, error);
            }

            @Override
            public void requestFinish() {
                view.hideLoading();
            }
        });
    }
}
