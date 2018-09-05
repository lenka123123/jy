package sinia.com.baihangeducation.find.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.widget.SwipeRefreshLayout;

import com.example.framwork.utils.ProgressActivityUtils;
import com.example.framwork.widget.ProgressActivity;
import com.example.framwork.widget.superrecyclerview.recycleview.SuperRecyclerView;

import java.util.ArrayList;
import java.util.List;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.supplement.base.BaseActivity;
import sinia.com.baihangeducation.find.adapter.ShareEveryDayAdapter;
import com.mcxtzhang.swipemenulib.info.bean.GetShareEveryDayInfo;
import com.mcxtzhang.swipemenulib.info.bean.ShareEveryDayTabInfo;
import sinia.com.baihangeducation.find.presenter.ShareEveryDayPresenter;
import sinia.com.baihangeducation.find.view.ShareEveryDayView;

/**
 * Created by Administrator on 2018/4/19.
 */

public class ShareEveryDayActivity extends BaseActivity implements ShareEveryDayView, SuperRecyclerView.LoadingListener, SwipeRefreshLayout.OnRefreshListener {

    private ShareEveryDayPresenter mShareEveryDayPresenter;
    private List<ShareEveryDayTabInfo> mTabList;
    private TabLayout mTabLayout;       //tab列表布局
    private String tabID;
    private int countpage = 1;
    private int itemnum = 20;

    private SuperRecyclerView rvContainer;
    private ProgressActivity progressActivity;
    private SwipeRefreshLayout swipeContainer;
    private ProgressActivityUtils progressActivityUtils;
    private ShareEveryDayAdapter mShareEveryDayAdapter;
    List<GetShareEveryDayInfo> mList;
    private boolean isLoadMore = false;

    @Override
    public int initLayoutResID() {
        return R.layout.sharaeveryday;
    }

    @Override
    protected void initData() {
        mList = new ArrayList<>();

        mCommonTitle.setCenterText(R.string.shareeveryday);
        mCommonTitle.setBackground(getResources().getDrawable(R.color.white));

        mShareEveryDayPresenter = new ShareEveryDayPresenter(context, this);
        mShareEveryDayPresenter.getShareEveryDayTab();

        mShareEveryDayAdapter = new ShareEveryDayAdapter(context, mList);

        initSwipeLayout(swipeContainer, this);
        showSwipeRefreshLayout(swipeContainer);
        progressActivityUtils = new ProgressActivityUtils(context, progressActivity);
        initRecyclerView(rvContainer, mShareEveryDayAdapter, this);
    }

    @Override
    protected void initView() {

        rvContainer = $(R.id.rv_container);
        progressActivity = $(R.id.progressActivity);
        swipeContainer = $(R.id.swipe_container);

        mTabLayout = $(R.id.shareeveryday_tablayout);
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabID = getClickTabId(tab.getText().toString()) + "";
//                mShareEveryDayPresenter.getShareEveryDayList();
                onRefresh();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private int getClickTabId(String text) {
        int id = 0;
        for (int i = 0; i < mTabList.size(); i++) {
            if ((mTabList.get(i).cate_name).equals(text)) {
                id = mTabList.get(i).cate_id;
            }
        }
        return id;
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
    public String getTabId() {
        return tabID;
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
    public void getShareEveryDaySuccess(List<ShareEveryDayTabInfo> list) {
        mTabList = list;
        for (int i = 0; i < list.size(); i++) {
            mTabLayout.addTab(mTabLayout.newTab().setText(list.get(i).cate_name));
        }
        tabID = mTabList.get(0).cate_id + "";
//        mShareEveryDayPresenter.getShareEveryDayList();
    }

    @Override
    public void getShareEveryDayDataSuccess(List<GetShareEveryDayInfo> list, int maxpage) {
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
            mShareEveryDayAdapter.notifyDataSetChanged();
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
        mShareEveryDayPresenter.getShareEveryDayList();
    }
}
