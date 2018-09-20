package sinia.com.baihangeducation.club;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.framwork.banner.SimpleImageBanner;
import com.example.framwork.utils.DensityUtil;
import com.example.framwork.utils.ProgressActivityUtils;
import com.example.framwork.utils.Toast;
import com.example.framwork.widget.ProgressActivity;
import com.example.framwork.widget.superrecyclerview.recycleview.SuperRecyclerView;
import com.mcxtzhang.swipemenulib.info.IsCompleteInfo;
import com.mcxtzhang.swipemenulib.info.bean.JobBangClassADListInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.club.club.interfaces.ClubHomeContract;
import sinia.com.baihangeducation.club.club.model.ClubHomeInfo;
import sinia.com.baihangeducation.club.club.model.ClubHomeModel;
import sinia.com.baihangeducation.club.club.presenter.ClubHomePresenter;
import sinia.com.baihangeducation.club.club.presenter.ClubPresenter;
import sinia.com.baihangeducation.club.club.view.ClubHorizontalAdapter;
import sinia.com.baihangeducation.club.club.view.ClubListAdapter;
import sinia.com.baihangeducation.club.searchschool.model.ClubSchoolList;
import sinia.com.baihangeducation.club.searchschool.view.SearchSchoolAdapter;
import sinia.com.baihangeducation.home.activity.HomePartTimeActivity;
import sinia.com.baihangeducation.home.activity.MoreDetailActivity;
import sinia.com.baihangeducation.home.present.HomePresenter;
import sinia.com.baihangeducation.parttime.MyBaseFragment;
import sinia.com.baihangeducation.supplement.advertisement.ADDataProvider;
import sinia.com.baihangeducation.supplement.base.Goto;

public class ClubFragment extends MyBaseFragment implements
        SuperRecyclerView.LoadingListener, SwipeRefreshLayout.OnRefreshListener, ClubHomeContract.View {

    private List<ClubHomeInfo.School> list = new ArrayList<>();
    private SuperRecyclerView rvContainer;
    private ProgressActivity progressActivity;
    private SwipeRefreshLayout swipeContainer;
    private ProgressActivityUtils progressActivityUtils;

    private ClubListAdapter mClubListAdapter;
    private String adcode = "320106";
    private int countpage = 1;
    private int itemnum = 20;
    private boolean isLoadMore = false;
    private View header;
    private int itemADWidth;
    private int itemADHeight;

    private SimpleImageBanner sibTopAd;                             //广告栏
    private List<JobBangClassADListInfo> adList;                    //广告数据
    private View foot;
    private List mDatas;
    private ImageView home_search;


    private boolean isCreated = false;
    private ClubHomePresenter clubHomePresenter;
    private ClubHorizontalAdapter clubHorizontalAdapter;
    private ClubPresenter mHomePresenter;
    private RelativeLayout home_search_layout;
    private TextView look_more;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isCreated) {
            getServerData();
        }
    }

    @Override
    public int initLayoutResID() {
        return R.layout.activity_club_list;
    }

    @Override
    protected void initData() {
        isCreated = true;
        clubHomePresenter = new ClubHomePresenter(new ClubHomeModel(context), this);
        mHomePresenter = new ClubPresenter(context, this);
        progressActivityUtils = new ProgressActivityUtils(context, progressActivity);

        //获取屏幕宽度
        itemADWidth = DensityUtil.getScreenWidth(context);
        //设置高度数据为屏幕宽度/1.9
        itemADHeight = (int) (itemADWidth * 60 / 100);
        //设置广告栏宽高属性
        sibTopAd.setLayoutParams(new FrameLayout.LayoutParams(itemADWidth, itemADHeight));
        ADDataProvider.initAdvertisement(sibTopAd, adList, itemADHeight, itemADWidth);
        adClickEvent();

    }

    @Override
    protected void initView() {

        progressActivity = $(R.id.progressActivity);
        swipeContainer = $(R.id.swipe_container);
        rvContainer = $(R.id.rv_container);
        home_search = $(R.id.home_search);
        home_search_layout = $(R.id.home_search_layout);
//        home_search.setOnClickListener(this);
        home_search_layout.setOnClickListener(this);

        adList = new ArrayList<>();
        initSwipeLayout(swipeContainer, this);
        showSwipeRefreshLayout(swipeContainer);
        mClubListAdapter = new ClubListAdapter(context, list);
        initRecyclerView(rvContainer, mClubListAdapter, this);

        addHeaderView();
        addFootView();
        //获取屏幕宽度
        itemADWidth = DensityUtil.getScreenWidth(context);
        //设置高度数据为屏幕宽度/1.9
        itemADHeight = (int) (itemADWidth / 3);
        //设置广告栏宽高属性
        sibTopAd.setLayoutParams(new FrameLayout.LayoutParams(itemADWidth, itemADHeight));
        ADDataProvider.initAdvertisement(sibTopAd, adList, itemADHeight, itemADWidth);

        adClickEvent();
    }

    public String getAdCode() {
        return adcode;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.home_search_layout:
                Goto.toClubSearchActivity(context);
                break;
        }
    }


    @Override
    public void onRefresh() {
        isLoadMore = false;
        showLoading();
        countpage = 1;
        getServerData();
    }

    @Override
    public void onLoadMore() {
        isLoadMore = true;
        hideLoading();

    }


    public void showLoading() { //调用接口时候
        showProgress();
    }


    public void hideLoading() {//得到数据的时候
        hideProgress();
        hideSwipeRefreshLayout(swipeContainer);
        rvContainer.completeLoadMore();
    }

    /**
     * 获取数据
     */
    private void getServerData() {
        clubHomePresenter.getClubHomeInfo();
    }


    private void addHeaderView() {
        header = LayoutInflater.from(getActivity()).inflate(R.layout.club_include_scroll_banner, null);
        sibTopAd = header.findViewById(R.id.sib_top_ad);

        mClubListAdapter.addHeaderView(header);

    }

    private void addFootView() {
        foot = LayoutInflater.from(getActivity()).inflate(R.layout.club_include_foot_view, null);
        look_more = foot.findViewById(R.id.look_more);
        RecyclerView recyclerView = foot.findViewById(R.id.recycleview);

        look_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Goto.toClubSchoolListActivity(context);
            }
        });

        mDatas = new ArrayList<>();
        clubHorizontalAdapter = new ClubHorizontalAdapter(context, mDatas);
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        //设置适配器
        recyclerView.setAdapter(clubHorizontalAdapter);
        mClubListAdapter.addFooterView(foot);
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

    public void getCompleteInfoSuccess(IsCompleteInfo mIsCompleteInfo, int position) {
        if (mIsCompleteInfo.is_complete == 1) {
            ADDataProvider.adToInformation(context, adList.get(position));
        } else {
            Goto.toCompleteInfoActivity(context);
        }
    }


    @Override
    public void showClubList(ClubHomeInfo clubSchoolList) {
        if (clubSchoolList.list.size() == 0) {
            look_more.setText("暂无俱乐部入住，尽请期待");
            look_more.setClickable(false);
            progressActivityUtils.showContent();
        } else {
            look_more.setText("查看更多");
            look_more.setClickable(true);
            progressActivityUtils.showContent();
            rvContainer.setLoadMoreEnabled(true);
        }

        list.clear();
        adList.clear();

        list.addAll(clubSchoolList.list);
        adList.addAll(clubSchoolList.ad_list);
        clubHorizontalAdapter.setData(clubSchoolList.late_list);
        ADDataProvider.initAdvertisement(sibTopAd, adList, itemADHeight, itemADWidth);

    }

    @Override
    public void showError(String errorMessage) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        rvContainer.removeAllViews();
        rvContainer = null;
        header = null;
        foot = null;
        sibTopAd.pauseScroll();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        rvContainer.removeAllViews();
        rvContainer = null;
        header = null;
        foot = null;

        sibTopAd.pauseScroll();
    }
}
