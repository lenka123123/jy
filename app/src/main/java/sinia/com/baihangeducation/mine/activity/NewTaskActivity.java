package sinia.com.baihangeducation.mine.activity;

import android.support.v4.widget.SwipeRefreshLayout;

import com.example.framwork.utils.ProgressActivityUtils;
import com.example.framwork.widget.ProgressActivity;
import com.example.framwork.widget.superrecyclerview.recycleview.SuperRecyclerView;

import java.util.ArrayList;
import java.util.List;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.supplement.base.BaseActivity;
import sinia.com.baihangeducation.mine.adapter.NewTaskAdapter;
import com.mcxtzhang.swipemenulib.info.bean.NewTaskInfo;
import sinia.com.baihangeducation.mine.presenter.NewTaskPresenter;
import sinia.com.baihangeducation.mine.view.NewTaskView;

/**
 * Created by Administrator on 2018/4/16.
 */

public class NewTaskActivity extends BaseActivity implements NewTaskView, SuperRecyclerView.LoadingListener, SwipeRefreshLayout.OnRefreshListener {

    private int countpage = 1;
    private int itemnum = 20;

    private ProgressActivityUtils progressActivityUtils;
    private List<NewTaskInfo> mList;
    private NewTaskAdapter mNewTaskAdapter;

    private SuperRecyclerView rvContainer;
    private ProgressActivity progressActivity;
    private SwipeRefreshLayout swipeContainer;
    private boolean isLoadMore;


    private NewTaskPresenter newTaskPresenter;

    @Override
    public int initLayoutResID() {
        return R.layout.newtask;
    }

    @Override
    protected void initData() {
        mList = new ArrayList<>();
        mCommonTitle.setCenterText(R.string.newtask);
        mCommonTitle.setBackground(getResources().getDrawable(R.color.white));

        newTaskPresenter = new NewTaskPresenter(context, this);
        newTaskPresenter.getNewTaskData();

        mNewTaskAdapter = new NewTaskAdapter(context, mList);

        initSwipeLayout(swipeContainer, this);
        showSwipeRefreshLayout(swipeContainer);
        progressActivityUtils = new ProgressActivityUtils(context, progressActivity);
        initRecyclerView(rvContainer, mNewTaskAdapter, this);
    }

    @Override
    protected void initView() {
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
    public void getNewTaskSuccess(List<NewTaskInfo> data, int maxpage) {
        if (data.size() == 0) {
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
            mList.addAll(data);
            mNewTaskAdapter.notifyDataSetChanged();
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
        newTaskPresenter.getNewTaskData();
    }
}
