package sinia.com.baihangeducation.mine.activity;

import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.TextView;

import com.example.framwork.utils.ProgressActivityUtils;
import com.example.framwork.widget.ProgressActivity;
import com.example.framwork.widget.superrecyclerview.recycleview.SuperRecyclerView;

import java.util.List;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.supplement.base.BaseActivity;
import sinia.com.baihangeducation.mine.adapter.TraingAdapter;
import com.mcxtzhang.swipemenulib.info.bean.TraingInfo;
import com.mcxtzhang.swipemenulib.info.bean.TraingListInfo;
import sinia.com.baihangeducation.mine.presenter.TraingGrowthRecordPresenter;
import sinia.com.baihangeducation.mine.view.TraingGrowthRecordView;

/**
 * 我的页面 培训
 */

public class TraingActivity extends BaseActivity implements TraingGrowthRecordView, SuperRecyclerView.LoadingListener, SwipeRefreshLayout.OnRefreshListener {

    private TraingGrowthRecordPresenter presenter;

    private TextView mAllTraingTimes;           //总次数

    private int countpage = 1;
    private int itemnum = 20;

    private ProgressActivityUtils progressActivityUtils;
    private TraingAdapter mTraingAdapter;
    private List<TraingInfo> mList;

    private SuperRecyclerView rvContainer;
    private ProgressActivity progressActivity;
    private SwipeRefreshLayout swipeContainer;
    private boolean isLoadMore;

    @Override
    public int initLayoutResID() {
        return R.layout.fragment_mine_traing;
    }

    @Override
    protected void initData() {

        mCommonTitle.setCenterText(R.string.trainggrowthrecord);
        mCommonTitle.setBackgroundColor(Color.WHITE);

        presenter = new TraingGrowthRecordPresenter(context, this);
        presenter.getTraingGrowthRecord();

        mTraingAdapter = new TraingAdapter(context, mList);

        initSwipeLayout(swipeContainer, this);
        showSwipeRefreshLayout(swipeContainer);
        progressActivityUtils = new ProgressActivityUtils(context, progressActivity);
        initRecyclerView(rvContainer, mTraingAdapter, this);

    }

    @Override
    protected void initView() {
        rvContainer = $(R.id.rv_container);
        progressActivity = $(R.id.progressActivity);
        swipeContainer = $(R.id.swipe_container);

        mAllTraingTimes = $(R.id.alltraingtimes);
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
    public String getPage() {
        return countpage + "";
    }

    @Override
    public String getPerpage() {
        return itemnum + "";
    }

    @Override
    public void getTraingGrowthRecordSuccess(TraingListInfo data, int maxpage) {
        mAllTraingTimes.setText(data.count + "");
        List<TraingInfo> list = data.list;
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
            mTraingAdapter.notifyDataSetChanged();
        }
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

    public void getServerData() {
        presenter.getTraingGrowthRecord();
    }
}
