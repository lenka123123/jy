package sinia.com.baihangeducation.mine;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.example.framwork.utils.ProgressActivityUtils;
import com.example.framwork.utils.Toast;
import com.example.framwork.widget.ProgressActivity;
import com.example.framwork.widget.superrecyclerview.recycleview.SuperRecyclerView;

import java.util.ArrayList;
import java.util.List;

import sinia.com.baihangeducation.R;

import com.mcxtzhang.swipemenulib.base.BaseFragment;

import sinia.com.baihangeducation.mine.adapter.MySendPartTimeAdapter;

import com.mcxtzhang.swipemenulib.info.bean.MySendInfo;
import com.mcxtzhang.swipemenulib.info.bean.MySendListInfo;

import sinia.com.baihangeducation.mine.presenter.MySendPresent;
import sinia.com.baihangeducation.mine.view.MySendView;

/**
 * 已经报名
 */

public class MySendPartTimeFragment extends BaseFragment implements MySendView, SuperRecyclerView.LoadingListener, SwipeRefreshLayout.OnRefreshListener {

    private MySendPresent present;

    private String type = "2";
    private int countpage = 1;
    private int itemnum = 20;
    private boolean complete = false;

    private SuperRecyclerView rvContainer;
    private ProgressActivity progressActivity;
    private SwipeRefreshLayout swipeContainer;
    private ProgressActivityUtils progressActivityUtils;
    private MySendPartTimeAdapter mMySendPartTimeAdapter;
    private List<MySendInfo> mList;
    private boolean isLoadMore = false;

    public void setComplete(boolean full) {
        complete = full;
    }

    @Override
    public int initLayoutResID() {
        return R.layout.mysendfragment;
    }

    @Override
    protected void initData() {

        mList = new ArrayList<>();

        present = new MySendPresent(context, this);
        present.getMySendPartTimeData("0" ,complete);

        mMySendPartTimeAdapter = new MySendPartTimeAdapter(context, mList);

        initSwipeLayout(swipeContainer, this);
        showSwipeRefreshLayout(swipeContainer);
        progressActivityUtils = new ProgressActivityUtils(context, progressActivity);
        initRecyclerView(rvContainer, mMySendPartTimeAdapter, this);
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
        List<MySendInfo> list = mMySendListInfo.list;
        if (countpage == 1 && list.size() == 0) {
            progressActivityUtils.showEmptry("暂无数据");
        } else {
            progressActivityUtils.showContent();

            if (countpage > maxpage) {
                rvContainer.setLoadMoreEnabled(false);
//                Toast.getInstance().showErrorToast(getActivity(),"没有更多数据");
            } else {
                rvContainer.setLoadMoreEnabled(true);
            }
            countpage++;
            if (!isLoadMore) {
                mList.clear();
            }
            mList.addAll(list);
            mMySendPartTimeAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void getMySendAllTimeDataSuccess(MySendListInfo mMySendListInfo, int maxpage) {

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
        present.getMySendPartTimeData("0",complete);
    }
}
