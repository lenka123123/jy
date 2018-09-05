package sinia.com.baihangeducation.home.activity;

import android.support.v4.widget.SwipeRefreshLayout;

import com.example.framwork.utils.ProgressActivityUtils;
import com.example.framwork.widget.ProgressActivity;
import com.example.framwork.widget.superrecyclerview.recycleview.SuperRecyclerView;

import java.util.ArrayList;
import java.util.List;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.supplement.base.BaseActivity;
import sinia.com.baihangeducation.find.adapter.ReCommendedAdapter;
import sinia.com.baihangeducation.find.presenter.ReCommendedAPresenter;
import com.mcxtzhang.swipemenulib.info.bean.ReCommendedInfo;
import sinia.com.baihangeducation.home.view.ReCommendedView;

/**
 * 首页 推荐
 */

public class ReCommendedActivity extends BaseActivity implements ReCommendedView, SuperRecyclerView.LoadingListener, SwipeRefreshLayout.OnRefreshListener {

    private ReCommendedAPresenter mReCommendedAPresenter;

    private int countpage = 1;
    private int itemnum = 20;

    private SuperRecyclerView rvContainer;
    private ProgressActivity progressActivity;
    private SwipeRefreshLayout swipeContainer;
    private ProgressActivityUtils progressActivityUtils;
    private ReCommendedAdapter mReCommendedAdapter;
    private List<ReCommendedInfo> mList;
    private boolean isLoadMore = false;

    @Override
    public int initLayoutResID() {
        return R.layout.fragment_home_recommended_activity;
    }

    @Override
    protected void initData() {
        mList = new ArrayList<>();
        mCommonTitle.setCenterText(R.string.home_tab_recommended);
        mCommonTitle.setBackground(getResources().getDrawable(R.color.white));

        mReCommendedAPresenter = new ReCommendedAPresenter(context, this);
        mReCommendedAPresenter.getReCommendedData();

        mReCommendedAdapter = new ReCommendedAdapter(context, mList);

        initSwipeLayout(swipeContainer, this);
        showSwipeRefreshLayout(swipeContainer);
        progressActivityUtils = new ProgressActivityUtils(context, progressActivity);
        initRecyclerView(rvContainer, mReCommendedAdapter, this);
    }

    @Override
    protected void initView() {
        rvContainer = $(R.id.rv_container);
        progressActivity = $(R.id.progressActivity);
        swipeContainer = $(R.id.swipe_container);
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
    public void getReCommendedDataSuccess(List<ReCommendedInfo> mReCommendedInfo, int maxpage) {
        if (mReCommendedInfo.size() == 0) {
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
            mList.addAll(mReCommendedInfo);
            mReCommendedAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showLoading() {
        showProgress();
    }

    @Override
    public void hideLoading() {
        hideProgress();
        hideSwipeRefreshLayout(swipeContainer);
        rvContainer.completeLoadMore();
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
        mReCommendedAPresenter.getReCommendedData();
    }
}
