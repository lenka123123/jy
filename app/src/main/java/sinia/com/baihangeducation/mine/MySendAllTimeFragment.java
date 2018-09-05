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
import sinia.com.baihangeducation.mine.adapter.MySendAllTimeAdapter;
import com.mcxtzhang.swipemenulib.info.bean.MySendInfo;
import com.mcxtzhang.swipemenulib.info.bean.MySendListInfo;
import sinia.com.baihangeducation.mine.presenter.MySendPresent;
import sinia.com.baihangeducation.mine.view.MySendView;

/**
 * Created by Administrator on 2018/4/17.
 */

public class MySendAllTimeFragment extends BaseFragment implements MySendView, SuperRecyclerView.LoadingListener, SwipeRefreshLayout.OnRefreshListener {

    private MySendPresent present;

    private String type = "1";
    private int countpage = 1;
    private int itemnum = 20;

    private SuperRecyclerView rvContainer;
    private ProgressActivity progressActivity;
    private SwipeRefreshLayout swipeContainer;
    private ProgressActivityUtils progressActivityUtils;
    private MySendAllTimeAdapter mMySendAllTimeAdapter;
    private List<MySendInfo> mList;
    private boolean isLoadMore = false;

    @Override
    public int initLayoutResID() {
        return R.layout.mysendfragment;
    }

    @Override
    protected void initData() {

        mList = new ArrayList<>();

        present = new MySendPresent(context, this);
        present.getMySendAllTimeData();

        mMySendAllTimeAdapter = new MySendAllTimeAdapter(context, mList);

        initSwipeLayout(swipeContainer, this);
        showSwipeRefreshLayout(swipeContainer);
        progressActivityUtils = new ProgressActivityUtils(context, progressActivity);
        initRecyclerView(rvContainer, mMySendAllTimeAdapter, this);
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
        return type;
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
    public void getMySendPartTimeDataSuccess(MySendListInfo mMySendListInfo, int maxpage) {

    }

    @Override
    public void getMySendAllTimeDataSuccess(MySendListInfo mMySendListInfo, int maxpage) {
        List<MySendInfo> list = mMySendListInfo.list;
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
            mMySendAllTimeAdapter.notifyDataSetChanged();
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

    /**
     * 获取数据
     */
    private void getServerData() {
        present.getMySendAllTimeData();
    }
}
