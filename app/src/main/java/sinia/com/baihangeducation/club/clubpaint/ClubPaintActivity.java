package sinia.com.baihangeducation.club.clubpaint;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.framwork.utils.ProgressActivityUtils;
import com.example.framwork.widget.ProgressActivity;
import com.example.framwork.widget.customtoolbar.CommonTitle;
import com.example.framwork.widget.superrecyclerview.recycleview.SuperRecyclerView;
import com.imnjh.imagepicker.SImagePicker;
import com.imnjh.imagepicker.activity.PhotoPickerActivity;
import com.mcxtzhang.swipemenulib.info.HomePartTimeSearchListInfo;
import com.mcxtzhang.swipemenulib.info.bean.HomePartTimeDistInfo;
import com.mcxtzhang.swipemenulib.info.bean.HomePartTimeInfo;
import com.mcxtzhang.swipemenulib.info.bean.HomePartTimeSalaryInfo;
import com.mcxtzhang.swipemenulib.info.bean.HomeTraingSeachIndustryInfo;
import com.mcxtzhang.swipemenulib.info.bean.HomeTraingSeachOrderInfo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.home.adapter.HomePartTimeAdapter;
import sinia.com.baihangeducation.home.present.HomePartTimePresenter;
import sinia.com.baihangeducation.home.view.HomePartTimeView;
import sinia.com.baihangeducation.supplement.base.BaseActivity;
import sinia.com.baihangeducation.supplement.tool.PartTimeDialog;
import sinia.com.baihangeducation.supplement.tool.PickerUtils;

public class ClubPaintActivity extends BaseActivity implements HomePartTimeView, SuperRecyclerView.LoadingListener, SwipeRefreshLayout.OnRefreshListener {

    private HomePartTimePresenter mHomePartTimePresenter;

//    private String lat = "32.089858";
//    private String lng = "118.755877";

    private int countpage = 1;
    private int itemnum = 20;

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

    private final static String type = "2";
    private String indutryId = "0";
    private String money_id = "0";
    private String areaId = "0";
    private String worktime_id = "0";
    private String distance_id = "0";
    private String sex_id = "0";
    private String pubtime_id = "0";
    private PartTimeDialog partTimeDialog;

    private boolean isCreated = false;


    @Override
    public int initLayoutResID() {
        return R.layout.fragment_home_parttime;
    }


    @Override
    protected void initData() {
        AppConfig.SHOWCLUBJOB = true;
    }

    @Override
    protected void initView() {
        isCreated = true;

//        mIndustryLayout = $(R.id.fragment_home_parttime_industrylayout);
//        mSalaryLayout = $(R.id.fragment_home_parttime_salarylayout);
//        mDistLayout = $(R.id.fragment_home_parttime_distlayout);
//        mOrderLayout = $(R.id.fragment_home_parttime_orderlayout);

        mIndustry = $(R.id.fragment_home_parttime_industry);
        mSalary = $(R.id.fragment_home_parttime_salary);
        mDist = $(R.id.fragment_home_parttime_dist);
        mOrder = $(R.id.fragment_home_parttime_order);

        rvContainer = $(R.id.rv_container);
        progressActivity = $(R.id.progressActivity);
        swipeContainer = $(R.id.swipe_container);

        CommonTitle commonTitle = $(R.id.title_bar);
        commonTitle.getLeftRes().setVisibility(View.GONE);
        commonTitle.setBackgroundColor(Color.WHITE);
        commonTitle.setCenterText("兼职");


        mList = new ArrayList<>();
        mSalaryList = new ArrayList<>();
        mIndustryList = new ArrayList<>();
        mOrderList = new ArrayList<>();
        mDistList = new ArrayList<>();

//        mCommonTitle.setCenterText(R.string.home_tab_parttime);
//        mCommonTitle.setBackgroundColor(Color.WHITE);

        mHomePartTimePresenter = new HomePartTimePresenter(context, this);
//        mHomePartTimePresenter.getPartTimeSeachList();
//        getServerData(); //数据

        mHomePartTimeAdapter = new HomePartTimeAdapter(context, mList);
        initSwipeLayout(swipeContainer, this);
        showSwipeRefreshLayout(swipeContainer);
        progressActivityUtils = new ProgressActivityUtils(context, progressActivity);
        initRecyclerView(rvContainer, mHomePartTimeAdapter, this);


        getServerData();
        mHomePartTimePresenter.getPartTimeSeachList();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.fragment_home_parttime_industrylayout:
//                if (partTimeDialog!=null)
//                    partTimeDialog.closeWindow();
//                mOrder.setChecked(false);
//                if (mIndustry.isChecked()) {
//                    mIndustry.setChecked(false);
//                } else {
//                    mIndustry.setChecked(true);
//                }
//                break;
//            case R.id.fragment_home_parttime_salarylayout:
//                if (partTimeDialog!=null)
//                    partTimeDialog.closeWindow();
//                mOrder.setChecked(false);
//                if (mSalary.isChecked()) {
//                    mSalary.setChecked(false);
//                } else {
//                    mSalary.setChecked(true);
//                }
//                break;
//            case R.id.fragment_home_parttime_distlayout:
//                if (partTimeDialog!=null)
//                    partTimeDialog.closeWindow();
//                mOrder.setChecked(false);
//
//                if (mDist.isChecked()) {
//                    mDist.setChecked(false);
//                } else {
//                    mDist.setChecked(true);
//                }
//                break;
//            case R.id.fragment_home_parttime_orderlayout:
//                if (partTimeDialog!=null)
//                    partTimeDialog.closeWindow();
//
//                mOrder.setChecked(true);
//                break;
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
        return AppConfig.CTYLEID;
    }

    @Override
    public String getType() {
        return type;
    }


    @Override
    public String getLocationLat() {
        return AppConfig.CURRENTLAT;
    }

    @Override
    public String getLocationLng() {
        return AppConfig.CURRENTLON;
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
        mIndustryList.clear();
        if (mHomePartTimeSearchListInfo.industry_list != null && mHomePartTimeSearchListInfo.industry_list.size() > 0) {
            mIndustryList.addAll(mHomePartTimeSearchListInfo.industry_list);
            System.out.println(mHomePartTimeSearchListInfo.industry_list.size() + "=industry_list==" + mHomePartTimeSearchListInfo.industry_list.get(0).industry_name);

        }
        mSalaryList.clear();
        if (mHomePartTimeSearchListInfo.money_list != null && mHomePartTimeSearchListInfo.money_list.size() > 0) {
            mSalaryList.addAll(mHomePartTimeSearchListInfo.money_list);
        }
        mDistList.clear();
        if (mHomePartTimeSearchListInfo.dist_list != null && mHomePartTimeSearchListInfo.dist_list.size() > 0) {
            mDistList.addAll(mHomePartTimeSearchListInfo.dist_list);
            System.out.println(mDistList.size() + "调用意向" + AppConfig.CTYLEID + "" + AppConfig.CTYLENAME);

        }
        mOrderList.clear();
        if (mHomePartTimeSearchListInfo.order_list != null && mHomePartTimeSearchListInfo.order_list.size() > 0) {
            mOrderList.addAll(mHomePartTimeSearchListInfo.order_list);
        }

        mIndustry.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                System.out.println("mIndustry111111112" + isChecked);
                if (partTimeDialog != null)
                    partTimeDialog.closeWindow();
                mIndustry.setChecked(false);
                if (partTimeDialog != null) {
                    partTimeDialog.commonPopupWindow.getPopupWindow().dismiss();
                }
                System.out.println("indutryIdindutryId333rrr44" + mIndustryList.size());
                filterTabToggleT(true, mOrder, mIndustryList, new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        System.out.println("indutryIdindutryId333rrr" + isChecked);
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
                });
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
                if (partTimeDialog != null)
                    partTimeDialog.closeWindow();
                mDist.setChecked(false);
                if (partTimeDialog != null) {
                    partTimeDialog.commonPopupWindow.getPopupWindow().dismiss();
                }
                filterTabToggleT(isChecked, mOrder, mDistList, new AdapterView.OnItemClickListener() {
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
                });
            }
        });
        mOrder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mOrder.setChecked(false);
                if (partTimeDialog != null)
                    partTimeDialog.closeWindow();
                partTimeDialog = new PartTimeDialog();
                partTimeDialog.showAlertPop(context, mOrder, new PartTimeDialog.OnClickParameterListener() {
                    @Override
                    public void getParameter(String money_id, String worktime_id, String distance_id, String sex_id, String pubtime_id) {
                        mList.clear();
                        countpage = 1;
                        mOrder.setChecked(false);
                        partTimeDialog.closeWindow();
                        mHomePartTimeAdapter.notifyDataSetChanged();

//                       System.out.println(indutryId + "aawwaaa" + areaId + "bb" + money_id + "cc" + worktime_id + "dd" + distance_id + "ee" + sex_id + "ff" + money_id + "gg" + pubtime_id + "");
                        mHomePartTimePresenter.getPartTimeData(indutryId, money_id, areaId, worktime_id, distance_id, sex_id, pubtime_id);
                    }
                });         //  0aa0bb3cc0dd0ee0ff3gg0
                //   0aa0bb1cc0dd0ee0ff1gg0
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

        mList.clear();
        mHomePartTimeAdapter.notifyDataSetChanged();
        countpage = 1;
        mHomePartTimePresenter.getPartTimeData(indutryId, money_id, areaId, worktime_id, distance_id, sex_id, pubtime_id);
    }


}
