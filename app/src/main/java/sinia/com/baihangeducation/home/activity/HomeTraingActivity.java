package sinia.com.baihangeducation.home.activity;

import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.framwork.utils.ProgressActivityUtils;
import com.example.framwork.widget.ProgressActivity;
import com.example.framwork.widget.superrecyclerview.recycleview.SuperRecyclerView;

import java.util.ArrayList;
import java.util.List;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.supplement.base.BaseActivity;
import sinia.com.baihangeducation.home.adapter.HomeTraingAdapter;
import com.mcxtzhang.swipemenulib.info.HomeTraingDataListInfo;
import com.mcxtzhang.swipemenulib.info.HomeTraingSeachListInfo;
import com.mcxtzhang.swipemenulib.info.bean.HomeTraingDataInfo;
import com.mcxtzhang.swipemenulib.info.bean.HomeTraingSeachIndustryInfo;
import com.mcxtzhang.swipemenulib.info.bean.HomeTraingSeachLevelInfo;
import com.mcxtzhang.swipemenulib.info.bean.HomeTraingSeachOrderInfo;
import com.mcxtzhang.swipemenulib.info.bean.HomeTraingSeachWeekInfo;
import sinia.com.baihangeducation.home.present.HomeTraingPresenter;
import sinia.com.baihangeducation.home.view.HomeTraingView;

/**
 * 首页 培训
 */

public class HomeTraingActivity extends BaseActivity implements HomeTraingView, SuperRecyclerView.LoadingListener, SwipeRefreshLayout.OnRefreshListener {

    private HomeTraingPresenter mHomeTraingPresenter;
    private int countpage = 1;
    private int itemnum = 20;
    private String industryId;
    private String levelId;
    private String weekId;
    private String orderId;

    private SuperRecyclerView rvContainer;
    private ProgressActivity progressActivity;
    private SwipeRefreshLayout swipeContainer;
    private ProgressActivityUtils progressActivityUtils;
    private HomeTraingAdapter mHomeTraingAdapter;
    private List<HomeTraingDataInfo> mList;
    private boolean isLoadMore = false;

    private LinearLayout mIndustryLayout;
    private LinearLayout mLevelLayout;
    private LinearLayout mWeekLayout;
    private LinearLayout mSortLayout;

    private CheckBox mIndustry;         //行业
    private CheckBox mLevel;            //级别
    private CheckBox mWeek;             //周期
    private CheckBox mSort;             //排序

    private List<HomeTraingSeachWeekInfo> mWeekList;           //周期
    private List<HomeTraingSeachIndustryInfo> mIndustryList;           //行业
    private List<HomeTraingSeachLevelInfo> mLevelList;           //等级
    private List<HomeTraingSeachOrderInfo> mOrderList;           //排序


    @Override
    public int initLayoutResID() {
        return R.layout.fragment_home_traing;
    }

    @Override
    protected void initData() {
        mList = new ArrayList<>();
        mWeekList = new ArrayList<>();
        mIndustryList = new ArrayList<>();
        mLevelList = new ArrayList<>();
        mOrderList = new ArrayList<>();

        mCommonTitle.setCenterText(R.string.home_tab_training);
        mCommonTitle.setBackgroundColor(Color.WHITE);

        mHomeTraingPresenter = new HomeTraingPresenter(context, this);
        mHomeTraingPresenter.getSeachList();
        getServerData();

        mHomeTraingAdapter = new HomeTraingAdapter(context, mList);
        initSwipeLayout(swipeContainer, this);
        showSwipeRefreshLayout(swipeContainer);
        progressActivityUtils = new ProgressActivityUtils(context, progressActivity);
        initRecyclerView(rvContainer, mHomeTraingAdapter, this);
    }

    @Override
    protected void initView() {

        mIndustryLayout = $(R.id.fragment_home_traing_industrylayout);
        mLevelLayout = $(R.id.fragment_home_traing_levellayout);
        mWeekLayout = $(R.id.fragment_home_traing_weeklayout);
        mSortLayout = $(R.id.fragment_home_traing_sortinglayout);

        mIndustry = $(R.id.fragment_home_traing_industry);
        mLevel = $(R.id.fragment_home_traing_level);
        mWeek = $(R.id.fragment_home_traing_week);
        mSort = $(R.id.fragment_home_traing_sorting);

        rvContainer = $(R.id.rv_container);
        progressActivity = $(R.id.progressActivity);
        swipeContainer = $(R.id.swipe_container);

        mIndustryLayout.setOnClickListener(this);
        mLevelLayout.setOnClickListener(this);
        mWeekLayout.setOnClickListener(this);
        mSortLayout.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.fragment_home_traing_industrylayout:
                if (mIndustry.isChecked())
                    mIndustry.setChecked(false);
                else
                    mIndustry.setChecked(true);
                break;
            case R.id.fragment_home_traing_levellayout:
                if (mLevel.isChecked())
                    mLevel.setChecked(false);
                else
                    mLevel.setChecked(true);
                break;
            case R.id.fragment_home_traing_weeklayout:
                if (mWeek.isChecked())
                    mWeek.setChecked(false);
                else
                    mWeek.setChecked(true);
                break;
            case R.id.fragment_home_traing_sortinglayout:
                if (mSort.isChecked())
                    mSort.setChecked(false);
                else
                    mSort.setChecked(true);
                break;
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
    public String getPage() {
        return countpage + "";
    }

    @Override
    public String getPerpage() {
        return itemnum + "";
    }

    @Override
    public String getIndustryId() {
        return industryId;
    }

    @Override
    public String getLevelId() {
        return levelId;
    }

    @Override
    public String getWeekId() {
        return weekId;
    }

    @Override
    public String getOrderId() {
        return orderId;
    }

    @Override
    public void getTraingInfoSuccess(HomeTraingDataListInfo mHomeTraingDataListInfo, int maxpage) {
        if (mHomeTraingDataListInfo.list.size() == 0) {
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
            mList.addAll(mHomeTraingDataListInfo.list);
            mHomeTraingAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void getSeachListSuccess(HomeTraingSeachListInfo mHomeTraingSeachListInfo) {
        if (mHomeTraingSeachListInfo.industry_list != null && mHomeTraingSeachListInfo.industry_list.size() > 0) {
            mIndustryList.addAll(mHomeTraingSeachListInfo.industry_list);
        }
        if (mHomeTraingSeachListInfo.level_list != null && mHomeTraingSeachListInfo.level_list.size() > 0) {
            mLevelList.addAll(mHomeTraingSeachListInfo.level_list);
        }
        if (mHomeTraingSeachListInfo.cycle_list != null && mHomeTraingSeachListInfo.cycle_list.size() > 0) {
            mWeekList.addAll(mHomeTraingSeachListInfo.cycle_list);
        }
        if (mHomeTraingSeachListInfo.order_list != null && mHomeTraingSeachListInfo.order_list.size() > 0) {
            mOrderList.addAll(mHomeTraingSeachListInfo.order_list);
        }

        mIndustry.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                filterTabToggleT(isChecked, mIndustryLayout, mIndustryList, new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        hidePopListView();
                        industryId = mIndustryList.get(position).industry_id + "";
                        levelId = null;
                        weekId = null;
                        orderId = null;
                        onRefresh();
                        Toast.makeText(context, mIndustryList.get(position).industry_name, Toast.LENGTH_SHORT).show();
                    }
                }, mIndustry, mLevel, mWeek, mSort);
            }
        });
        mLevel.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                filterTabToggleT(isChecked, mIndustryLayout, mLevelList, new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        hidePopListView();
                        levelId = mLevelList.get(position).level_id + "";
                        industryId = null;
                        weekId = null;
                        orderId = null;
                        onRefresh();
                    }
                }, mLevel, mIndustry, mWeek, mSort);
            }
        });
        mWeek.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                filterTabToggleT(isChecked, mWeekLayout, mWeekList, new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        hidePopListView();
                        weekId = mWeekList.get(position).cycle_id + "";
                        industryId = null;
                        levelId = null;
                        orderId = null;
                        onRefresh();
                    }
                }, mWeek, mLevel, mIndustry, mSort);
            }
        });
        mSort.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                filterTabToggleT(isChecked, mSortLayout, mOrderList, new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        hidePopListView();
                        orderId = mOrderList.get(position).order_id + "";
                        industryId = null;
                        levelId = null;
                        weekId = null;
                        onRefresh();
                        Toast.makeText(context, mOrderList.get(position).order_name, Toast.LENGTH_SHORT).show();
                    }
                }, mSort, mLevel, mWeek, mIndustry);
            }
        });
    }


    /**
     * 获取数据
     */
    private void getServerData() {
        mHomeTraingPresenter.getTraingData();
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
}
