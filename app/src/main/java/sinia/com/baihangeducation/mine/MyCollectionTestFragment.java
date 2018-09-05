package sinia.com.baihangeducation.mine;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.example.framwork.utils.ProgressActivityUtils;
import com.example.framwork.widget.ProgressActivity;
import com.example.framwork.widget.superrecyclerview.recycleview.SuperRecyclerView;

import java.util.ArrayList;
import java.util.List;

import sinia.com.baihangeducation.R;
import com.mcxtzhang.swipemenulib.base.BaseFragment;
import sinia.com.baihangeducation.mine.adapter.MyCollectionSSTAdapter;
import com.mcxtzhang.swipemenulib.info.bean.MyCollectionListInfo;
import sinia.com.baihangeducation.mine.presenter.MyCollectionPresenter;
import sinia.com.baihangeducation.mine.view.MyCollectionView;
import com.mcxtzhang.swipemenulib.utils.Constants;

/**
 * 我的收藏 试卷
 */

public class MyCollectionTestFragment extends BaseFragment implements MyCollectionView, SuperRecyclerView.LoadingListener, SwipeRefreshLayout.OnRefreshListener {

    private MyCollectionPresenter presenter;

    private int countpage = 1;
    private int itemnum = 20;

    private SuperRecyclerView rvContainer;
    private ProgressActivity progressActivity;
    private SwipeRefreshLayout swipeContainer;
    private ProgressActivityUtils progressActivityUtils;
    private MyCollectionSSTAdapter myCollectionSSTAdapter;
    private List<MyCollectionListInfo> mList;
    private boolean isLoadMore = false;

    @Override
    public int initLayoutResID() {
        return R.layout.mycollectionfragment;
    }

    @Override
    protected void initData() {
        mList = new ArrayList<>();

        presenter = new MyCollectionPresenter(context, this);


        myCollectionSSTAdapter = new MyCollectionSSTAdapter(context, mList);

        initSwipeLayout(swipeContainer, this);
        showSwipeRefreshLayout(swipeContainer);
        progressActivityUtils = new ProgressActivityUtils(context, progressActivity);
        initRecyclerView(rvContainer, myCollectionSSTAdapter, this);

        getServerData();
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
        return Constants.MYCOLLECTION_TEST;
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
    public void getPatrTimeSuccess(List<MyCollectionListInfo> list, int maxpage) {

    }

    @Override
    public void getAllTimeSuccess(List<MyCollectionListInfo> list, int maxpage) {

    }

    @Override
    public void getFunnySuccess(List<MyCollectionListInfo> list, int maxpage) {

    }

    @Override
    public void getStrategySuccess(List<MyCollectionListInfo> list, int maxpage) {

    }

    @Override
    public void getTestSuccess(List<MyCollectionListInfo> list, int maxpage) {
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
            myCollectionSSTAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void getSecretSuccess(List<MyCollectionListInfo> list, int maxpage) {

    }

    @Override
    public void getInformationSuccess(List<MyCollectionListInfo> list, int maxpage) {

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
        presenter.getTestData();
    }
}
