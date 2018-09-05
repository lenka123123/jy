package sinia.com.baihangeducation.mine.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.TextView;

import com.example.framwork.utils.ProgressActivityUtils;
import com.example.framwork.widget.ProgressActivity;
import com.example.framwork.widget.superrecyclerview.recycleview.SuperRecyclerView;

import java.util.List;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.supplement.base.BaseActivity;
import sinia.com.baihangeducation.mine.adapter.PartTimeAdapter;
import com.mcxtzhang.swipemenulib.info.bean.PartTimeInfo;
import com.mcxtzhang.swipemenulib.info.bean.PartTimeListInfo;
import sinia.com.baihangeducation.mine.presenter.PartTimeGrowthRecordPresenter;
import sinia.com.baihangeducation.mine.view.PartTimeGrowthRecordView;

/**
 * 我的页面 兼职
 */

public class PartTimeActivity extends BaseActivity implements PartTimeGrowthRecordView, SuperRecyclerView.LoadingListener, SwipeRefreshLayout.OnRefreshListener {

    private PartTimeGrowthRecordPresenter partTimeGrowthRecordPresenter;

    private TextView mPartTimeTimes;            //兼职次数

    private ProgressActivityUtils progressActivityUtils;
    private List<PartTimeInfo> mList;
    private PartTimeAdapter mPartTimeAdapter;

    private SuperRecyclerView rvContainer;
    private ProgressActivity progressActivity;
    private SwipeRefreshLayout swipeContainer;
    private boolean isLoadMore;

    private int countpage = 1;
    private int itemnum = 20;

    @Override
    public int initLayoutResID() {
        return R.layout.fragment_mine_parttime;
    }

    @Override
    protected void initData() {

        mCommonTitle.setCenterText(R.string.parttimegrowthrecord);
        mCommonTitle.setBackground(getResources().getDrawable(R.color.white));

        partTimeGrowthRecordPresenter = new PartTimeGrowthRecordPresenter(context, this);
        partTimeGrowthRecordPresenter.getPartTimeGrowthRecord();

        mPartTimeAdapter = new PartTimeAdapter(context, mList);

        initSwipeLayout(swipeContainer, this);
        showSwipeRefreshLayout(swipeContainer);
        progressActivityUtils = new ProgressActivityUtils(context, progressActivity);
        initRecyclerView(rvContainer, mPartTimeAdapter, this);
    }

    @Override
    protected void initView() {
        mPartTimeTimes = $(R.id.parttime_item_times);

        rvContainer = $(R.id.rv_container);
        progressActivity = $(R.id.progressActivity);
        swipeContainer = $(R.id.swipe_container);
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
    public String getPage() {
        return countpage + "";
    }

    @Override
    public String getPerpage() {
        return itemnum + "";
    }

    @Override
    public void getPartTimeGrowthRecordSuccess(PartTimeListInfo mPartTimeListInfo, int maxpage) {
        mPartTimeTimes.setText(mPartTimeListInfo.count + "");
        List<PartTimeInfo> list = mPartTimeListInfo.list;
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
            mPartTimeAdapter.notifyDataSetChanged();
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
        partTimeGrowthRecordPresenter.getPartTimeGrowthRecord();
    }
}
