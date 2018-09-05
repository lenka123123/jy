package sinia.com.baihangeducation.mine.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.RadioButton;

import com.example.framwork.utils.ProgressActivityUtils;
import com.example.framwork.widget.ProgressActivity;
import com.example.framwork.widget.superrecyclerview.recycleview.SuperRecyclerView;

import java.util.ArrayList;
import java.util.List;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.supplement.base.BaseActivity;
import sinia.com.baihangeducation.mine.adapter.AlreadyGetTaskAdapter;
import com.mcxtzhang.swipemenulib.info.bean.AlreadyGetTaskInfo;
import com.mcxtzhang.swipemenulib.info.bean.AlreadyGetTaskListInfo;
import sinia.com.baihangeducation.mine.presenter.AlreadyGetTaskPresenter;
import sinia.com.baihangeducation.mine.view.AlreadyGetTaskView;

/**
 * Created by Administrator on 2018/4/16.
 */

public class AlreadyGetTaskActivity extends BaseActivity implements AlreadyGetTaskView, SuperRecyclerView.LoadingListener, SwipeRefreshLayout.OnRefreshListener {

    private AlreadyGetTaskPresenter mAlreadyGetTaskPresenter;

    private String tab = "1";       //查询类型 1未完成 2审核中 3已完成

    private RadioButton mUnCompleteTask;            //未完成
    private RadioButton mAuditTask;                 //审核中
    private RadioButton mCompleteTask;              //已完成

    private int countpage = 1;
    private int itemnum = 20;

    private ProgressActivityUtils progressActivityUtils;
    private List<AlreadyGetTaskInfo> mList;
    private AlreadyGetTaskAdapter mAlreadyGetTaskAdapter;

    private SuperRecyclerView rvContainer;
    private ProgressActivity progressActivity;
    private SwipeRefreshLayout swipeContainer;
    private boolean isLoadMore;

    @Override
    public int initLayoutResID() {
        return R.layout.alreadygettask;
    }

    @Override
    protected void initData() {
        mList = new ArrayList<>();
        mCommonTitle.setCenterText(R.string.alreadygettask);
        mCommonTitle.setBackground(getResources().getDrawable(R.color.white));

        mAlreadyGetTaskPresenter = new AlreadyGetTaskPresenter(context, this);
        mAlreadyGetTaskPresenter.getAlreadyGetTaskData();

        initSwipeLayout(swipeContainer, this);
        showSwipeRefreshLayout(swipeContainer);
        progressActivityUtils = new ProgressActivityUtils(context, progressActivity);
        mAlreadyGetTaskAdapter = new AlreadyGetTaskAdapter(context, mList, tab);
        initRecyclerView(rvContainer, mAlreadyGetTaskAdapter, this);
    }

    @Override
    protected void initView() {
        mUnCompleteTask = $(R.id.alreadygettask_uncomplete);
        mAuditTask = $(R.id.alreadygettask_audit);
        mCompleteTask = $(R.id.alreadygettask_complete);

        rvContainer = $(R.id.rv_container);
        progressActivity = $(R.id.progressActivity);
        swipeContainer = $(R.id.swipe_container);

        mUnCompleteTask.setOnClickListener(this);
        mAuditTask.setOnClickListener(this);
        mCompleteTask.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.alreadygettask_uncomplete:
                //未完成
                tab = "1";
                mAlreadyGetTaskPresenter.getAlreadyGetTaskData();

                mAlreadyGetTaskAdapter = new AlreadyGetTaskAdapter(context, mList, tab);
                initRecyclerView(rvContainer, mAlreadyGetTaskAdapter, this);

                onRefresh();
                break;
            case R.id.alreadygettask_audit:
                //审核中
                tab = "2";
                mAlreadyGetTaskPresenter.getAlreadyGetTaskData();

                mAlreadyGetTaskAdapter = new AlreadyGetTaskAdapter(context, mList, tab);
                initRecyclerView(rvContainer, mAlreadyGetTaskAdapter, this);

                onRefresh();
                break;
            case R.id.alreadygettask_complete:
                //已完成
                tab = "3";
                mAlreadyGetTaskPresenter.getAlreadyGetTaskData();

                mAlreadyGetTaskAdapter = new AlreadyGetTaskAdapter(context, mList, tab);
                initRecyclerView(rvContainer, mAlreadyGetTaskAdapter, this);

                onRefresh();
                break;
        }
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
    public String getTab() {
        return tab;
    }

    @Override
    public void getUnCompleteSuccess(AlreadyGetTaskListInfo mAlreadyGetTaskListInfo, int maxpage) {
        List<AlreadyGetTaskInfo> data = mAlreadyGetTaskListInfo.list;
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
            mAlreadyGetTaskAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void doRefresh() {
        onRefresh();
    }

    @Override
    public void getAuditSuccess() {

    }

    @Override
    public void getCompleteSuccess() {

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
        mAlreadyGetTaskPresenter.getAlreadyGetTaskData();
    }
}
