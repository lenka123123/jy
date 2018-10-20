package sinia.com.baihangeducation.home;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.framwork.banner.SimpleImageBanner;
import com.example.framwork.utils.DensityUtil;
import com.example.framwork.utils.EncryptUtil;
import com.example.framwork.utils.ProgressActivityUtils;
import com.example.framwork.utils.Toast;
import com.example.framwork.utils.UserInfo;
import com.example.framwork.widget.ProgressActivity;
import com.example.framwork.widget.superrecyclerview.recycleview.SuperRecyclerView;
import com.mcxtzhang.swipemenulib.info.bean.IndustryListInfo;
import com.xiaosu.view.text.DataSetAdapter;
import com.xiaosu.view.text.VerticalRollingTextView;

import java.util.ArrayList;
import java.util.List;

import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.MainActivity;
import sinia.com.baihangeducation.R;

import com.mcxtzhang.swipemenulib.base.BaseFragment;

import sinia.com.baihangeducation.MyApplication;

import com.mcxtzhang.swipemenulib.info.bean.JobBangClassADListInfo;

import sinia.com.baihangeducation.home.activity.HomePartTimeActivity;
import sinia.com.baihangeducation.home.activity.MoreDetailActivity;
import sinia.com.baihangeducation.home.adapter.GlideImageLoader;
import sinia.com.baihangeducation.home.adapter.GridViewHome;
import sinia.com.baihangeducation.home.adapter.GridViewSim;
import sinia.com.baihangeducation.home.adapter.HomeListAdapter;
import sinia.com.baihangeducation.home.adapter.HomeMomentAdapter;

import com.mcxtzhang.swipemenulib.info.CityIdInfo;
import com.mcxtzhang.swipemenulib.info.HomeListInfo;
import com.mcxtzhang.swipemenulib.info.IsCompleteInfo;
import com.mcxtzhang.swipemenulib.info.bean.CityInfo;
import com.mcxtzhang.swipemenulib.info.bean.HomeJobInfo;
import com.mcxtzhang.swipemenulib.info.bean.HomeNewsInfo;

import sinia.com.baihangeducation.home.present.HomePresenter;
import sinia.com.baihangeducation.home.view.HomeView;
import sinia.com.baihangeducation.supplement.base.Goto;

import com.mcxtzhang.swipemenulib.utils.ACache;

import sinia.com.baihangeducation.supplement.advertisement.ADDataProvider;

import com.mcxtzhang.swipemenulib.utils.Constants;

import sinia.com.baihangeducation.find.campus.adapter.SelectPopupWindow;

/**
 * Created by Administrator on 2018.02.24.
 */

public class HomeFragment extends BaseFragment implements HomeView, SuperRecyclerView.LoadingListener, SwipeRefreshLayout.OnRefreshListener {

    //选择城市popwindow
    private SelectPopupWindow mPopupWindow = null;
    private boolean isHasCityCode = false;

    private HomePresenter mHomePresenter;

    private LinearLayout adress;//地址布局
    private TextView adressName;//地址
    private ImageView arrow;//下拉箭头

    //    private ImageView mSearch;       //搜索
//    private TextView recommended;   //推荐
//    private TextView training;      //培训
//    private TextView parttime;      //兼职
//    private TextView headhunters;   //猎头fragment_home_momentprivate TextView helpeachother; //互助
    private SimpleImageBanner sibTopAd;                             //广告栏

    private ACache mCache;
//    private VerticalRollingTextView mRollingTextView;               //跑马灯广告

    private String adcode = "320106";
    private String lat = "32.089858";
    private String lng = "118.755877";
    public static String cityCode;                                  //城市编码
    public static String cityID;                                    //城市id
    private int countpage = 1;                                      //页码
    private int itemnum = 20;                                       //每页加载二十条数据
    private int itemADWidth;                                        //设置广告的宽
    private int itemADHeight;                                       //设置广告的高

    private List<JobBangClassADListInfo> adList;                    //广告数据
    private List<IndustryListInfo> industry_list;
    private SuperRecyclerView rvContainer;

    private ProgressActivity progressActivity;
    private SwipeRefreshLayout swipeContainer;
    private ProgressActivityUtils progressActivityUtils;
    private HomeListAdapter mHomeListAdapter;                       //首页工作数据适配器
    //    private HomeMomentAdapter mHomeMomentAdapter;                   //首页新闻数据适配器
    private List<HomeJobInfo> mList;                                //首页职业列表信息
    private List<HomeNewsInfo> mNewsList;                           //首页新闻信息
    private boolean isLoadMore = false;                             //是否加载更多

//    private HorizontalListView mHorizontalListView;                 //横向滚动控件

    private List<CityInfo> mCityDatas = new ArrayList<>();                              //城市数据

    private UserInfo userInfo;
    private View header;
    private ImageView mDrawable;
    private GridViewSim mAddressArrayAdapter;
    private List<String> mCity;
    private boolean oneAddHead = true;
    private GridView gridView;


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save State Her
    }

    public void setReSatart() {
        if (!AppConfig.HOMT) return;

        if (AppConfig.INTENTION_SETTING) {
            adList.clear();
            industry_list.clear();
            mHomePresenter.getHomeData();

            AppConfig.INTENTION_SETTING = false;
        }

        if (AppConfig.CTYLENAMESELECT) {
            adressName.setText(AppConfig.CTYLENAME);
            cityID = AppConfig.CTYLEID;
            adList.clear();
            industry_list.clear();
            mHomePresenter.getHomeData();
        }
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        AppConfig.HOMT = true;
        AppConfig.PART = false;
        AppConfig.CHAT = false;
        AppConfig.CLUB = false;
        AppConfig.ME = false;

        //fragment可见时恢复轮播图滚动
        //开始轮播
//            if (mHomeListAdapter != null) {
//                if (!oneAddHead) {
//                    if (AppConfig.CTYLENAMESELECT) {
//                        cityID = AppConfig.CTYLEID;
//                        if (adressName != null) {
//                            adressName.setText(AppConfig.CTYLENAME);
//                        }
//                    }
//                    mHomePresenter.getHomeData();
//                }
//            }

    }

    @Override
    public int initLayoutResID() {
        return R.layout.fragment_home;
    }

    @Override
    public void switchoverChangeData() {
//        //获取城市信息
////        mHomePresenter.getCityData();
//        mHomePresenter.getCityId();
//        //获取首页资料信息
//        mHomePresenter.getHomeData();
    }

    /**
     * 初始化数据
     */
    @Override
    protected void initData() {
        AppConfig.CTYLENAMESELECT = false;
        oneAddHead = false;
        mCache = ACache.get(context);
        Log.i("fragment里面的经纬度", mCache.getAsString("ADCODE"));
        Log.i("fragment里面的经纬度", mCache.getAsString("LAT"));
        Log.i("fragment里面的经纬度", mCache.getAsString("LNG"));
        if (mCache.getAsString("ADCODE") != null) {
            adcode = mCache.getAsString("ADCODE");
        }
        if (mCache.getAsString("LAT") != null) {
            lat = mCache.getAsString("LAT");
        }
        if (mCache.getAsString("LNG") != null) {
            lng = mCache.getAsString("LNG");
        }
        mCache = ACache.get(context);
        lat = mCache.getAsString("LAT");
        lng = mCache.getAsString("LNG");
        mList = new ArrayList<>();
        adList = new ArrayList<>();
        industry_list = new ArrayList<>();
        mNewsList = new ArrayList<>();
        mCity = new ArrayList();

        // TODO: 2018/8/14 0014    mAddressArrayAdapter = new GridViewSim(context, mCity);
        // TODO: 2018/8/14 0014       mMyGridView.setAdapter(mAddressArrayAdapter);

        cityCode = Constants.city_id;

        mHomePresenter = new HomePresenter(context, this);
        //获取城市信息
//        mHomePresenter.getCityData();
        mHomePresenter.getCityId();
        //获取首页资料信息
        mHomePresenter.getHomeData();


        //首页职业适配器
        mHomeListAdapter = new HomeListAdapter(context, mList);


        //一下是下来刷新
        initSwipeLayout(swipeContainer, this);
        showSwipeRefreshLayout(swipeContainer);
        progressActivityUtils = new ProgressActivityUtils(context, progressActivity);
        initRecyclerView(rvContainer, mHomeListAdapter, this);


        addHeaderView();

        //获取屏幕宽度
        itemADWidth = DensityUtil.getScreenWidth(context);
        //设置高度数据为屏幕宽度/1.9
        itemADHeight = (int) (itemADWidth * 50 / 100);

        System.out.println("initAdvertisement" + itemADHeight);

        //设置广告栏宽高属性
        sibTopAd.setLayoutParams(new FrameLayout.LayoutParams(itemADWidth, itemADHeight));
        ADDataProvider.initAdvertisement(sibTopAd, adList, itemADHeight, itemADWidth);
        adClickEvent();
    }


    private void addHeaderView() {
        header = LayoutInflater.from(getActivity()).inflate(R.layout.include_scroll_banner, null);
        sibTopAd = header.findViewById(R.id.sib_top_ad);

        gridView = header.findViewById(R.id.gridview);
        header.findViewById(R.id.fragment_find_jobbangclass).setOnClickListener(this);
        header.findViewById(R.id.tv_training).setOnClickListener(this);
        header.findViewById(R.id.fragment_find_shareeveryday).setOnClickListener(this);
        header.findViewById(R.id.intention_setting).setOnClickListener(this);


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (industry_list != null) {
//                      if (MoreDetailActivity)
                    if (i == industry_list.size()) {
                        Intent intent = new Intent(context, MoreDetailActivity.class);
                        context.startActivity(intent);
                        return;
                    }
                    Intent intent = new Intent(context, HomePartTimeActivity.class);
                    intent.putExtra("indutryId", industry_list.get(i).id);
                    context.startActivity(intent);
                }
            }
        });

        //   header = LayoutInflater.from(context).inflate(R.layout.fragment_home_head, null);

//        recommended = header.findViewById(R.id.tv_recommended);
//        training = header.findViewById(R.id.tv_training);
//        parttime = header.findViewById(R.id.tv_parttime);
//        headhunters = header.findViewById(R.id.tv_headhunters);

//        if (banner == null)
//            banner = header.findViewById(R.id.banner);

//        sibTopAd.setOnClickListener(this);
//        recommended.setOnClickListener(this);
//        training.setOnClickListener(this);
//        parttime.setOnClickListener(this);
//        headhunters.setOnClickListener(this);

        mHomeListAdapter.addHeaderView(header);

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (rvContainer != null) {
            rvContainer.removeAllViews();
            rvContainer = null;
        }

        header = null;
        sibTopAd.pauseScroll();
//        getActivity().finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        getActivity().finish();
        header = null;
        if (rvContainer != null)
            rvContainer.removeAllViews();
        rvContainer = null;
        if (sibTopAd != null)
            sibTopAd.pauseScroll();

    }

    /**
     * 广告栏点击事件
     */
    private void adClickEvent() {
        sibTopAd.setOnItemClickL(new SimpleImageBanner.OnItemClickL() {
            @Override
            public void onItemClick(int position) {
                mHomePresenter.getCompleteInfo(position);
            }
        });
    }

    /**
     * 初始化控件
     */
    @Override
    protected void initView() {
        rvContainer = $(R.id.rv_container);
        progressActivity = $(R.id.progressActivity);
        swipeContainer = $(R.id.swipe_container);

        adress = $(R.id.fragment_home_adress);
        adressName = $(R.id.fragment_home_adressName);
        arrow = $(R.id.fragment_home_arrow);


        ImageView mSearch = $(R.id.home_search);
        mDrawable = $(R.id.drawable);

//        String[] city = {"广州", "深圳", "北京", "上海", "香港", "澳门", "天津"};  //定义一个数组，作为数据源


//        mSearch.setOnClickListener(this);
        mDrawable.setOnClickListener(this);
        adress.setOnClickListener(this);
        mSearch.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            //首页更换地址
            case R.id.fragment_home_adress:
                Goto.toSelectCityActivity(context);


//
                // TODO: 2018/8/14 0014        if (mCity.size() < 1 && mHomePresenter != null) {
                // TODO: 2018/8/14 0014              mHomePresenter.getCityData();
                // TODO: 2018/8/14 0014          }


//                arrow.setImageDrawable(getResources().getDrawable(R.drawable.arrow_down_black));
//                mAddressArrayAdapter.notifyDataSetChanged();

//                mMyGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                        cityID = String.valueOf(mCityDatas.get(i).city_id);
//                        AppConfig.CTYLEID = cityID;
//                        Constants.city_id = cityID;
//                        adressName.setText(mCity.get(i));
//                        mMyGridView.setVisibility(View.GONE);
//                        adList.clear();
//                        industry_list.clear();
//
//                        mHomePresenter.getHomeData();
//                    }
//                });

                break;

            case R.id.tv_all:
                break;
            case R.id.tv_recommended:
                Goto.toReCommendedActivity(context);
                break;
            case R.id.tv_training:
                Goto.toHomeTraingActivity(context);
                break;
            case R.id.tv_parttime:
                Goto.toHomePartTimeActivity(context);
                break;
            case R.id.tv_headhunters:
                Goto.toHomeHunterActivity(context);
                break;
            case R.id.home_search:
                Goto.toSearchActivity(context);
                break;
            case R.id.drawable:
//
//                System.out.println("==========ISlOGINED===");
//                System.out.println(AppConfig.ISlOGINED);
//                if (!AppConfig.ISlOGINED) {
//                    new AlertDialog.Builder(getActivity()).setTitle("提示！").setMessage("您尚未登录，请先登录。").setPositiveButton("登录", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            Goto.toLogin(getActivity());
//                        }
//                    }).setNegativeButton("取消", null).show();
//                } else {
//
//                    ((MainActivity) getActivity()).getmDrawerLayout().openDrawer(Gravity.LEFT);
//                    ((MainActivity) getActivity()).open();
//                }
                break;
            case R.id.fragment_find_jobbangclass:
                Goto.toJobBangClassActivity(context);
                break;
            case R.id.fragment_find_shareeveryday:
                Goto.toShareEveryDayActivity(context);
                break;
            case R.id.intention_setting:
                if (!AppConfig.ISlOGINED) {
//                    new AlertDialog.Builder(getActivity()).setTitle("提示！").setMessage("您尚未登录，请先登录。").setPositiveButton("登录", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            Goto.toLogin(getActivity());
//                        }
//                    }).setNegativeButton("取消", null).show();
//                    return;
                }
                Goto.toWantJobActivity(context);
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
    public String getLocationAdcode() {
        return adcode;
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
    public String getAdCode() {
        return adcode;
    }

    @Override
    public String getCityID() {
        return cityID == null ? Constants.city_id : cityID;
    }

    @Override
    public String getLocationLng() {
        return lng;
    }

    @Override
    public String getLocationLat() {
        return lat;
    }

    @Override
    public void getCityListSuccess(List<CityInfo> mCityInfo) {
        mCityDatas.clear();
        mCity.clear();
        if (mCityInfo != null) {
            mCityDatas.addAll(mCityInfo);
            for (int i = 0; i < mCityInfo.size(); i++) {
                if ((mCityInfo.get(i).adcode + "") == adcode) {
//                    adressName.setText(mCityInfo.get(i).name);
                    cityID = mCityInfo.get(i).city_id + "";
                    isHasCityCode = true;
                }
            }

            for (int i = 0; i < mCityDatas.size(); i++) {
                mCity.add(i, mCityDatas.get(i).name);
            }

            if (!isHasCityCode) {
                for (int i = 0; i < mCityInfo.size(); i++) {
                    if ((mCityInfo.get(i).is_default) == 1) {
//                        adressName.setText(mCityInfo.get(i).name);
                        cityID = mCityInfo.get(i).city_id + "";
                        isHasCityCode = true;
                    }
                }
            }
        }
    }

    private List<String> photoUrl = new ArrayList<>();

    @Override
    public void getHomeInfoSuccess(HomeListInfo mHomeListInfo, int maxpage) {

        if (mHomeListInfo.ad_list != null && mHomeListInfo.ad_list.size() > 0) {//adList != null &&
            adList.clear();
            industry_list.clear();
            photoUrl.clear();
            adList.addAll(mHomeListInfo.ad_list);
            industry_list.addAll(mHomeListInfo.industry_list);


            //设置图片集合
            for (int i = 0; i < adList.size(); i++) {
                photoUrl.add(i, ADDataProvider.getList(adList).get(i).imgUrl);
            }


            ADDataProvider.initAdvertisement(sibTopAd, adList, itemADHeight, itemADWidth);

        }

        if (mHomeListInfo.industry_list != null) {

            GridViewHome gridViewHome = new GridViewHome(getActivity(), mHomeListInfo.industry_list);
            gridView.setAdapter(gridViewHome);
        }

//        if (mHomeListInfo.article_pic != null)
//            Glide.with(context).load(mHomeListInfo.article_pic).error(R.drawable.logo).into(mImageView);

        if (mHomeListInfo.news_list != null && mHomeListInfo.news_list.size() > 0) {
            mNewsList.clear();
            mNewsList.addAll(mHomeListInfo.news_list);
//          mNewsList = (mHomeListInfo.news_list);

            //跑马灯
            //为跑马灯创建一个标题集合
            List<String> title = new ArrayList<>();
            //将新闻里的标题放入集合中
            for (int i = 0; i < mHomeListInfo.news_list.size(); i++) {
//                Log.i("新闻信息",mHomeListInfo.news_list.get(i).news_title);

                title.add(mHomeListInfo.news_list.get(i).news_title);
            }
        }

        if (mHomeListInfo.job_list.list.size() == 0) {
//            progressActivityUtils.showEmptry("暂无数据");
//            List<HomeJobInfo> list = new ArrayList<>();
            mList.clear();
//            mList=list;
            HomeJobInfo info = new HomeJobInfo();
            mList.add(info);
            mHomeListAdapter.notifyDataSetChanged();
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
            mList.addAll(mHomeListInfo.job_list.list);
            mHomeListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void getCityIdSuccess(CityIdInfo cityIdInfo) {
        if (cityIdInfo != null) {
            adcode = cityIdInfo.adcode;
            mHomePresenter.getCityData();
        }
    }

    @Override
    public void getCompleteInfoSuccess(IsCompleteInfo mIsCompleteInfo, int position) {
        if (mIsCompleteInfo.is_complete == 1) {
            ADDataProvider.adToInformation(context, adList.get(position));
        } else {
            Goto.toCompleteInfoActivity(context);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (context.isFinishing()) {
            Log.i("是否被结束", "Home######" + "onResume");
        }
//        Log.i("是否被结束","Home######"+"onResume");
        onRefresh();
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
        mHomePresenter.getHomeData();
    }

}
