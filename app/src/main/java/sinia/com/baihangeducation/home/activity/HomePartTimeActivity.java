package sinia.com.baihangeducation.home.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.framwork.utils.ProgressActivityUtils;
import com.example.framwork.widget.ProgressActivity;
import com.example.framwork.widget.superrecyclerview.recycleview.SuperRecyclerView;

import java.util.ArrayList;
import java.util.List;

import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.supplement.base.BaseActivity;
import sinia.com.baihangeducation.home.HomeFragment;
import sinia.com.baihangeducation.home.adapter.HomePartTimeAdapter;

import com.mcxtzhang.swipemenulib.info.HomePartTimeSearchListInfo;
import com.mcxtzhang.swipemenulib.info.bean.HomePartTimeDistInfo;
import com.mcxtzhang.swipemenulib.info.bean.HomePartTimeInfo;
import com.mcxtzhang.swipemenulib.info.bean.HomePartTimeSalaryInfo;
import com.mcxtzhang.swipemenulib.info.bean.HomeTraingSeachIndustryInfo;
import com.mcxtzhang.swipemenulib.info.bean.HomeTraingSeachOrderInfo;

import sinia.com.baihangeducation.home.present.HomePartTimePresenter;
import sinia.com.baihangeducation.home.view.HomePartTimeView;
import sinia.com.baihangeducation.supplement.tool.PartTimeDialog;

/**
 * Created by Administrator on 2018/4/24.
 */

public class HomePartTimeActivity extends BaseActivity implements HomePartTimeView, SuperRecyclerView.LoadingListener, SwipeRefreshLayout.OnRefreshListener {
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
    private CheckBox mOrder;             //综合

    private SuperRecyclerView rvContainer;
    private ProgressActivity progressActivity;
    private SwipeRefreshLayout swipeContainer;
    private ProgressActivityUtils progressActivityUtils;
    private HomePartTimeAdapter mHomePartTimeAdapter;
    private List<HomePartTimeInfo> mList;
    private boolean isLoadMore = false;

    public List<HomePartTimeSalaryInfo> mSalaryList;                    //薪资
    public List<HomeTraingSeachIndustryInfo> mIndustryList;             //行业
    public List<HomeTraingSeachOrderInfo> mOrderList;                   //排序
    public List<HomePartTimeDistInfo> mDistList;                        //地区


    @Override
    protected void onDestroy() {
        super.onDestroy();
        partTimeDialog = null;
        mSalaryList = null;
        mIndustryList = null;
        mOrderList = null;
        mDistList = null;


    }

    private final static String type = "2";
    private String indutryId = "0";
    private String money_id = "0";
    private String areaId = "0";
    private String worktime_id = "0";
    private String distance_id = "0";
    private String sex_id = "0";
    private String pubtime_id = "0";
    private PartTimeDialog partTimeDialog;

    // industryId,行业   money_id,日结 areaId,地址  worktime_id周末,distance_id一公里,sex_id,pubtime_id 三天内
    //industryId,行业   money_id,日结 areaId,地址  worktime_id周末,distance_id一公里,sex_id,pubtime_id 三天内
    @Override
    public int initLayoutResID() {
        return R.layout.fragment_home_parttime;
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        AppConfig.AREAID = areaId;
        indutryId = intent.getStringExtra("indutryId");
        money_id = intent.getStringExtra("money_id");
        areaId = intent.getStringExtra("areaId");
        worktime_id = intent.getStringExtra("worktime_id");
        distance_id = intent.getStringExtra("distance_id");
        sex_id = intent.getStringExtra("sex_id");
        pubtime_id = intent.getStringExtra("pubtime_id");


        mList = new ArrayList<>();
        mSalaryList = new ArrayList<>();
        mIndustryList = new ArrayList<>();
        mOrderList = new ArrayList<>();
        mDistList = new ArrayList<>();

        mCommonTitle.setCenterText(R.string.home_tab_parttime);
        mCommonTitle.setBackgroundColor(Color.WHITE);

        mHomePartTimePresenter = new HomePartTimePresenter(context, this);
        mHomePartTimePresenter.getPartTimeSeachList();
        getServerData(); //数据
        partTimeDialog = new PartTimeDialog();
        mHomePartTimeAdapter = new HomePartTimeAdapter(context, mList);
        initSwipeLayout(swipeContainer, this);
        showSwipeRefreshLayout(swipeContainer);
        progressActivityUtils = new ProgressActivityUtils(context, progressActivity);
        initRecyclerView(rvContainer, mHomePartTimeAdapter, this);
    }

    @Override
    protected void initView() {
        mIndustryLayout = $(R.id.fragment_home_parttime_industrylayout);
        mSalaryLayout = $(R.id.fragment_home_parttime_salarylayout);
        mDistLayout = $(R.id.fragment_home_parttime_distlayout);
        mOrderLayout = $(R.id.fragment_home_parttime_orderlayout);

        mIndustry = $(R.id.fragment_home_parttime_industry);
        mSalary = $(R.id.fragment_home_parttime_salary);
        mDist = $(R.id.fragment_home_parttime_dist);
        mOrder = $(R.id.fragment_home_parttime_order);

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
            case R.id.fragment_home_parttime_industrylayout:

                partTimeDialog.closeWindow();
                if (mIndustry.isChecked())
                    mIndustry.setChecked(false);
                else
                    mIndustry.setChecked(true);
                break;
            case R.id.fragment_home_parttime_salarylayout:

                partTimeDialog.closeWindow();
                if (mSalary.isChecked())
                    mSalary.setChecked(false);
                else
                    mSalary.setChecked(true);
                break;
            case R.id.fragment_home_parttime_distlayout:

                partTimeDialog.closeWindow();
                if (mDist.isChecked())
                    mDist.setChecked(false);
                else
                    mDist.setChecked(true);
                break;
            case R.id.fragment_home_parttime_orderlayout:


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
        return "";
    }

    @Override
    public String getMoneyId() {
        return "";
    }

    @Override
    public String getDistId() {
        return "";
    }

    @Override
    public String getOrderId() {
        return "";
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
                if (!isChecked) return;
                partTimeDialog.closeWindow();
                filterTabToggleT(isChecked, mIndustryLayout, mIndustryList, new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        hidePopListView();
                        mList.clear();
                        countpage = 1;
                        mHomePartTimeAdapter.notifyDataSetChanged();
                        indutryId = mIndustryList.get(position).industry_id + "";
                        System.out.println("indutryIdindutryId" + indutryId);
                        mHomePartTimePresenter.getPartTimeData(indutryId, "0", "0", "0", "0", "0", "0");
                        onRefresh();
                        //  Toast.makeText(context, mIndustryList.get(position).industry_name, Toast.LENGTH_SHORT).show();
                    }
                }, mIndustry, mSalary, mDist);
            }
        });
//        mSalary.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                filterTabToggleT(isChecked, mSalaryLayout, mSalaryList, new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        hidePopListView();
//                        salaryId = mSalaryList.get(position).money_id + "";
//                        industryId = null;
//                        orderId = null;
//                        distId = null;
//                        onRefresh();
//                        Toast.makeText(context, mSalaryList.get(position).money_name, Toast.LENGTH_SHORT).show();
//                    }
//                }, mSalary, mIndustry, mDist, mOrder);
//            }
//        });
        mDist.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) return;
                partTimeDialog.closeWindow();
                filterTabToggleT(isChecked, mDistLayout, mDistList, new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        hidePopListView();
                        areaId = mDistList.get(position).dist_id + "";
                        AppConfig.AREAID = areaId;
                        countpage = 1;
                        mList.clear();
                        mHomePartTimeAdapter.notifyDataSetChanged();
                        mHomePartTimePresenter.getPartTimeData(indutryId, "0", areaId, "0", "0", "0", "0");
                        onRefresh();
                        //   Toast.makeText(context, areaId + "===" + mDistList.get(position).dist_name, Toast.LENGTH_SHORT).show();
                    }
                }, mDist, mIndustry, mSalary);
            }
        });
        mOrder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {


            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                partTimeDialog.closeWindow();

                System.out.println("=======打开几次=====");
                partTimeDialog.showAlertPop(HomePartTimeActivity.this, mOrderLayout, new PartTimeDialog.OnClickParameterListener() {
                    @Override
                    public void getParameter(String money_id, String worktime_id, String distance_id, String sex_id, String pubtime_id) {
                        mList.clear();
                        countpage = 1;
                        partTimeDialog.closeWindow();
                        mHomePartTimeAdapter.notifyDataSetChanged();
                        System.out.println(indutryId + "rter" + areaId + "" + money_id + "rrrrrrrr" + worktime_id + "" + distance_id + "" + sex_id + "" + money_id + "" + pubtime_id + "");
                        mHomePartTimePresenter.getPartTimeData(indutryId, money_id, areaId, worktime_id, distance_id, sex_id, pubtime_id);
                    }
                });
//                filterTabToggleT(isChecked, mOrderLayout, mOrderList, new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        hidePopListView();
//                        orderId = mOrderList.get(position).order_id + "";
//                        industryId = null;
//                        distId = null;
//                        salaryId = null;
//                        onRefresh();
//                        Toast.makeText(context, mOrderList.get(position).order_name, Toast.LENGTH_SHORT).show();
//                    }
//                }, mOrder, mIndustry, mSalary, mDist);
            }
        });
    }

    @Override
    public void getPartTimeDataSuccess(List<HomePartTimeInfo> mHomePartTimeInfo, int maxpage) {
        partTimeDialog.closeWindow();
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
            mHomePartTimeAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onRefresh() {
        isLoadMore = false;
        countpage = 1;
        mHomePartTimePresenter.getPartTimeData(indutryId, money_id, areaId, worktime_id, distance_id, sex_id, pubtime_id);
    }

    @Override
    public void onLoadMore() {
        isLoadMore = true;
        mHomePartTimePresenter.getPartTimeData(indutryId, money_id, areaId, worktime_id, distance_id, sex_id, pubtime_id);
    }

    /**
     * mHomePartTimePresenter.getPartTimeData(indutryId, money_id, areaId, worktime_id, distance_id, sex_id, pubtime_id);
     * industryId,行业   money_id,日结 areaId,地址  worktime_id周末,distance_id一公里,sex_id,pubtime_id 三天内
     */
    private void getServerData() {
        if (partTimeDialog != null)
            partTimeDialog.closeWindow();
        mHomePartTimePresenter.getPartTimeData(indutryId, money_id, areaId, worktime_id, distance_id, sex_id, pubtime_id);
    }


}
