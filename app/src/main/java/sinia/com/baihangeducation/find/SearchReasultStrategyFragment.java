package sinia.com.baihangeducation.find;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.example.framwork.utils.ProgressActivityUtils;
import com.example.framwork.widget.ProgressActivity;
import com.example.framwork.widget.superrecyclerview.recycleview.SuperRecyclerView;

import java.util.ArrayList;
import java.util.List;

import sinia.com.baihangeducation.R;
import com.mcxtzhang.swipemenulib.base.BaseFragment;
import sinia.com.baihangeducation.find.activity.SearchReasultActivity;
import sinia.com.baihangeducation.find.adapter.SearchReasultSSTAdapter;
import com.mcxtzhang.swipemenulib.info.bean.SearchResultFunnyInfo;
import com.mcxtzhang.swipemenulib.info.bean.SearchResultInfomationInfo;
import com.mcxtzhang.swipemenulib.info.bean.SearchResultSSTInfo;
import sinia.com.baihangeducation.find.presenter.SearchReasultPresenter;
import sinia.com.baihangeducation.find.view.SearchReasultView;

/**
 * Created by Administrator on 2018/4/18.
 */

public class SearchReasultStrategyFragment extends BaseFragment implements SearchReasultView, SuperRecyclerView.LoadingListener, SwipeRefreshLayout.OnRefreshListener {
    private String typy = "2";
    private SearchReasultPresenter mSearchReasultPresenter;

    private int countpage = 1;
    private int itemnum = 20;

    private SuperRecyclerView rvContainer;
    private ProgressActivity progressActivity;
    private SwipeRefreshLayout swipeContainer;
    private ProgressActivityUtils progressActivityUtils;
    private SearchReasultSSTAdapter mSearchReasultSSTAdapter;
    private List<SearchResultSSTInfo> mList;
    private boolean isLoadMore = false;

    @Override
    public int initLayoutResID() {
        return R.layout.searchreasultfunnyfragment;
    }

    @Override
    protected void initData() {

        mList = new ArrayList<>();

        mSearchReasultPresenter = new SearchReasultPresenter(context, this);
        mSearchReasultPresenter.getStrategyData();

        mSearchReasultSSTAdapter = new SearchReasultSSTAdapter(context, mList,4,3);

        initSwipeLayout(swipeContainer, this);
        showSwipeRefreshLayout(swipeContainer);
        progressActivityUtils = new ProgressActivityUtils(context, progressActivity);
        initRecyclerView(rvContainer, mSearchReasultSSTAdapter, this);
    }

    @Override
    protected void initView() {
        rvContainer = $(R.id.rv_container);
        progressActivity = $(R.id.progressActivity);
        swipeContainer = $(R.id.swipe_container);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {
        hideSwipeRefreshLayout(swipeContainer);
        rvContainer.completeLoadMore();
    }

    @Override
    public String getType() {
        return typy;
    }

    @Override
    public String getPage() {
        return countpage + "";
    }

    @Override
    public String getPerpage() {
        return itemnum + "";
    }

    @Override
    public String getKeyWord() {
        return SearchReasultActivity.keyword;
    }

    @Override
    public void getSearchFunnyData(List<SearchResultFunnyInfo> list, int maxpage) {

    }

    @Override
    public void getSearchStrategyData(List<SearchResultSSTInfo> list, int maxpage) {
        if (list.size() == 0) {
            progressActivityUtils.showEmptry("暂无数据");
        } else {
            progressActivityUtils.showContent();
            countpage++;
            if (maxpage == 1 || countpage > maxpage) {
                rvContainer.setLoadMoreEnabled(false);
            } else {
                rvContainer.setLoadMoreEnabled(true);
            }
            if (!isLoadMore) {
                mList.clear();
            }
            mList.addAll(list);
            mSearchReasultSSTAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void getSearchTestData(List<SearchResultSSTInfo> list, int maxpage) {

    }

    @Override
    public void getSearchSecretData(List<SearchResultSSTInfo> list, int maxpage) {

    }

    @Override
    public void getSearchInformationData(List<SearchResultInfomationInfo> list, int maxpage) {

    }

    @Override
    public void onRefresh() {
        isLoadMore = false;
        countpage = 1;
        getServerData();
    }

    @Override
    public void onLoadMore() {
        isLoadMore = true;
        getServerData();
    }

    /**
     * 获取数据
     */
    private void getServerData() {
        mSearchReasultPresenter.getStrategyData();
    }
}
