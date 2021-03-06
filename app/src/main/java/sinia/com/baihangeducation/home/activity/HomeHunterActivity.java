package sinia.com.baihangeducation.home.activity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
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
import sinia.com.baihangeducation.home.HomeFragment;
import sinia.com.baihangeducation.home.adapter.HomeHunterAdapter;
import com.mcxtzhang.swipemenulib.info.HomePartTimeSearchListInfo;
import com.mcxtzhang.swipemenulib.info.bean.HomePartTimeDistInfo;
import com.mcxtzhang.swipemenulib.info.bean.HomePartTimeInfo;
import com.mcxtzhang.swipemenulib.info.bean.HomePartTimeSalaryInfo;
import com.mcxtzhang.swipemenulib.info.bean.HomeTraingSeachIndustryInfo;
import com.mcxtzhang.swipemenulib.info.bean.HomeTraingSeachOrderInfo;
import sinia.com.baihangeducation.home.present.HomePartTimePresenter;
import sinia.com.baihangeducation.home.view.HomePartTimeView;

/**
 * 首页 猎头
 */

public class HomeHunterActivity extends BaseActivity implements HomePartTimeView, SuperRecyclerView.LoadingListener, SwipeRefreshLayout.OnRefreshListener {
    private HomePartTimePresenter mHomePartTimePresenter;

    private int countpage = 1;
    private int itemnum = 20;

    private LinearLayout mIndustryLayout;
    private LinearLayout mSalaryLayout;
    private LinearLayout mDistLayout;
    private LinearLayout mOrderLayout;

    private CheckBox mIndustry;         //行业
    private CheckBox mSalary;            //薪资
    private CheckBox mDist;             //地区
    private CheckBox mOrder;             //排序

    private SuperRecyclerView rvContainer;
    private ProgressActivity progressActivity;
    private SwipeRefreshLayout swipeContainer;
    private ProgressActivityUtils progressActivityUtils;
    private HomeHunterAdapter mHomeHunterAdapter;
    private List<HomePartTimeInfo> mList;
    private boolean isLoadMore = false;

    public List<HomePartTimeSalaryInfo> mSalaryList;                    //薪资
    public List<HomeTraingSeachIndustryInfo> mIndustryList;             //行业
    public List<HomeTraingSeachOrderInfo> mOrderList;                   //排序
    public List<HomePartTimeDistInfo> mDistList;                        //地区

    private Intent intent;
    private String salaryId;
    private String industryId;
    private String orderId;
    private String distId;

    private final static String type = "1";

    @Override
    public int initLayoutResID() {
        return R.layout.fragment_home_hunter;
    }

    @Override
    protected void initData() {
        intent = getIntent();
        salaryId = intent.getStringExtra("SALARYID");
        industryId = intent.getStringExtra("INDUSTRYID");
        orderId = intent.getStringExtra("AREAID");
        distId = intent.getStringExtra("ORDERID");
        Log.i("职业筛选条件",industryId+"行业接受");
        Log.i("职业筛选条件",salaryId+"薪资接受");
        Log.i("职业筛选条件",distId+"地区接受");
        Log.i("职业筛选条件",orderId+"排序接受");

        mList = new ArrayList<>();
        mSalaryList = new ArrayList<>();
        mIndustryList = new ArrayList<>();
        mOrderList = new ArrayList<>();
        mDistList = new ArrayList<>();

        mCommonTitle.setCenterText(R.string.home_tab_headhunters);
        mCommonTitle.setBackground(getResources().getDrawable(R.color.white));

        mHomePartTimePresenter = new HomePartTimePresenter(context, this);
        mHomePartTimePresenter.getPartTimeSeachList();
        getServerData();

        mHomeHunterAdapter = new HomeHunterAdapter(context, mList);
        initSwipeLayout(swipeContainer, this);
        showSwipeRefreshLayout(swipeContainer);
        progressActivityUtils = new ProgressActivityUtils(context, progressActivity);
        initRecyclerView(rvContainer, mHomeHunterAdapter, this);
    }

    @Override
    protected void initView() {
        mIndustryLayout = $(R.id.fragment_home_hunter_industrylayout);
        mSalaryLayout = $(R.id.fragment_home_hunter_salarylayout);
        mDistLayout = $(R.id.fragment_home_hunter_distlayout);
        mOrderLayout = $(R.id.fragment_home_hunter_orderlayout);

        mIndustry = $(R.id.fragment_home_hunter_industry);
        mSalary = $(R.id.fragment_home_hunter_salary);
        mDist = $(R.id.fragment_home_hunter_dist);
        mOrder = $(R.id.fragment_home_hunter_order);

        rvContainer = $(R.id.rv_container);
        progressActivity = $(R.id.progressActivity);
        swipeContainer = $(R.id.swipe_container);

        mIndustryLayout.setOnClickListener(this);
        mSalaryLayout.setOnClickListener(this);
        mDistLayout.setOnClickListener(this);
        mOrderLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.fragment_home_hunter_industrylayout:
                if (mIndustry.isChecked())
                    mIndustry.setChecked(false);
                else
                    mIndustry.setChecked(true);
                break;
            case R.id.fragment_home_hunter_salarylayout:
                if (mSalary.isChecked())
                    mSalary.setChecked(false);
                else
                    mSalary.setChecked(true);
                break;
            case R.id.fragment_home_hunter_distlayout:
                if (mDist.isChecked())
                    mDist.setChecked(false);
                else
                    mDist.setChecked(true);
                break;
            case R.id.fragment_home_hunter_orderlayout:
                if (mOrder.isChecked())
                    mOrder.setChecked(false);
                else
                    mOrder.setChecked(true);
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
    public String getCityId() {
        return HomeFragment.cityID;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getLocationLat() {
        return getLat();
    }

    @Override
    public String getLocationLng() {
        return getLng();
    }

    @Override
    public String getIndustryId() {
        return industryId;
    }

    @Override
    public String getMoneyId() {
        return salaryId;
    }

    @Override
    public String getDistId() {
        return distId;
    }

    @Override
    public String getOrderId() {
        return orderId;
    }

    @Override
    public String getPager() {
        return countpage + "";
    }

    @Override
    public String getPerpage() {
        return itemnum + "";
    }

    @Override
    public void getPartTimeSeachSuccess(HomePartTimeSearchListInfo mHomePartTimeSearchListInfo) {
        if (mHomePartTimeSearchListInfo.industry_list != null && mHomePartTimeSearchListInfo.industry_list.size() > 0) {
            mIndustryList.addAll(mHomePartTimeSearchListInfo.industry_list);
        }
        if (mHomePartTimeSearchListInfo.money_list != null && mHomePartTimeSearchListInfo.money_list.size() > 0) {
            mSalaryList.addAll(mHomePartTimeSearchListInfo.money_list);
        }
        if (mHomePartTimeSearchListInfo.dist_list != null && mHomePartTimeSearchListInfo.dist_list.size() > 0) {
            mDistList.addAll(mHomePartTimeSearchListInfo.dist_list);
        }
        if (mHomePartTimeSearchListInfo.order_list != null && mHomePartTimeSearchListInfo.order_list.size() > 0) {
            mOrderList.addAll(mHomePartTimeSearchListInfo.order_list);
        }

        mIndustry.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                filterTabToggleT(isChecked, mIndustryLayout, mIndustryList, new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        hidePopListView();
                        industryId = mIndustryList.get(position).industry_id + "";
                        salaryId = null;
                        orderId = null;
                        distId = null;
                        onRefresh();
                        Toast.makeText(context, mIndustryList.get(position).industry_name, Toast.LENGTH_SHORT).show();
                    }
                }, mIndustry, mSalary, mDist, mOrder);
            }
        });
        mSalary.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                filterTabToggleT(isChecked, mSalaryLayout, mSalaryList, new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        hidePopListView();
                        salaryId = mSalaryList.get(position).money_id + "";
                        industryId = null;
                        orderId = null;
                        distId = null;
                        onRefresh();
                        Toast.makeText(context, mSalaryList.get(position).money_name, Toast.LENGTH_SHORT).show();
                    }
                }, mSalary, mIndustry, mDist, mOrder);
            }
        });
        mDist.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                filterTabToggleT(isChecked, mDistLayout, mDistList, new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        hidePopListView();
                        distId = mDistList.get(position).dist_id + "";
                        industryId = null;
                        orderId = null;
                        salaryId = null;
                        onRefresh();
                        Toast.makeText(context, mDistList.get(position).dist_name, Toast.LENGTH_SHORT).show();
                    }
                }, mDist, mIndustry, mSalary, mOrder);
            }
        });
        mOrder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                filterTabToggleT(isChecked, mOrderLayout, mOrderList, new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        hidePopListView();
                        orderId = mOrderList.get(position).order_id + "";
                        industryId = null;
                        distId = null;
                        salaryId = null;
                        onRefresh();
                        Toast.makeText(context, mOrderList.get(position).order_name, Toast.LENGTH_SHORT).show();
                    }
                }, mOrder, mIndustry, mSalary, mDist);
            }
        });
    }

    @Override
    public void getPartTimeDataSuccess(List<HomePartTimeInfo> mHomePartTimeInfo, int maxpage) {
        if (mHomePartTimeInfo.size() == 0) {
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
            mList.addAll(mHomePartTimeInfo);
            mHomeHunterAdapter.notifyDataSetChanged();
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
        mHomePartTimePresenter.getPartTimeData();
    }
}
