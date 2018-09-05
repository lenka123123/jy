package sinia.com.baihangeducation.home.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;

import com.example.framwork.banner.SimpleImageBanner;
import com.example.framwork.utils.DensityUtil;
import com.example.framwork.utils.ProgressActivityUtils;
import com.example.framwork.utils.SPUtils;
import com.example.framwork.widget.ProgressActivity;
import com.example.framwork.widget.superrecyclerview.recycleview.SuperRecyclerView;

import java.util.ArrayList;
import java.util.List;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.supplement.base.BaseActivity;
import sinia.com.baihangeducation.MyApplication;

import com.mcxtzhang.swipemenulib.info.bean.CommentInfo;
import com.mcxtzhang.swipemenulib.info.bean.JobBangClassADListInfo;

import sinia.com.baihangeducation.home.adapter.HomeAndFindHelpEachOtherAdapter;

import com.mcxtzhang.swipemenulib.info.HomeAndFindHelpEachOtherListInfo;
import com.mcxtzhang.swipemenulib.info.bean.CommonInfo;
import com.mcxtzhang.swipemenulib.info.bean.HomeAndFindHelpEachOtherInfo;
import com.mcxtzhang.swipemenulib.utils.Constants;

import sinia.com.baihangeducation.home.present.HomeAndFindHelpEachOtherPresenter;
import sinia.com.baihangeducation.home.view.HomeAndFindHelpEachOtherView;
import sinia.com.baihangeducation.supplement.advertisement.ADDataProvider;

/**
 * 校园互助
 */

public class HomeAndFindHelpEachOtherActivity extends BaseActivity implements HomeAndFindHelpEachOtherView, SuperRecyclerView.LoadingListener, SwipeRefreshLayout.OnRefreshListener {

    private HomeAndFindHelpEachOtherPresenter mHomeAndFindHelpEachOtherPresenter;
    private List<JobBangClassADListInfo> adList;                    //广告数据
    private int itemADWidth;                                        //设置广告的宽
    private int itemADHeight;                                       //设置广告的高
    private SimpleImageBanner sibTopAd;                             //广告栏
    private int countpage = 1;
    private int itemnum = 20;
    private String type = "1";

    private RadioButton mZhuanRang;     //转让
    private RadioButton mHuZhu;         //互助

    private SuperRecyclerView rvContainer;
    private ProgressActivity progressActivity;
    private SwipeRefreshLayout swipeContainer;
    private ProgressActivityUtils progressActivityUtils;
    private HomeAndFindHelpEachOtherAdapter mHomeAndFindHelpEachOtherAdapter;
    private List<HomeAndFindHelpEachOtherInfo> mList;
    private boolean isLoadMore = false;
    private CommonInfo commentInfo;


    @Override
    public int initLayoutResID() {
        return R.layout.findandhome_helpeachother;
    }

    @Override
    protected void initData() {
        adList = new ArrayList<>();
        mList = new ArrayList<>();
        Log.i("南京的adcode", getAdCode());
        //获取关于信息
        mCommonTitle.setCenterText(R.string.home_tab_helpeachother);
        mCommonTitle.setBackground(getResources().getDrawable(R.color.white));


        mHomeAndFindHelpEachOtherPresenter = new HomeAndFindHelpEachOtherPresenter(context, this);
        mHomeAndFindHelpEachOtherPresenter.getHomeAndFindHelpEachOtherData();

        mHomeAndFindHelpEachOtherAdapter = new HomeAndFindHelpEachOtherAdapter(context, mList);

        initSwipeLayout(swipeContainer, this);
        showSwipeRefreshLayout(swipeContainer);
        progressActivityUtils = new ProgressActivityUtils(context, progressActivity);
        initRecyclerView(rvContainer, mHomeAndFindHelpEachOtherAdapter, this);

        addHeaderView();
        commentInfo = (CommonInfo) SPUtils.getInstance().readObject(context, Constants.COMMON_INFO);
//        Log.i("获取开关",commentInfo.ad_switch.help);
        if ((commentInfo.ad_switch.help).equals("open")) {
            sibTopAd.setVisibility(View.VISIBLE);
        } else {
            sibTopAd.setVisibility(View.GONE);
        }

        //获取屏幕宽度
        itemADWidth = DensityUtil.getScreenWidth(context);
        //设置高度数据为屏幕宽度/1.9
        itemADHeight = (int) (itemADWidth / 1.9);
        //设置广告栏宽高属性
        sibTopAd.setLayoutParams(new FrameLayout.LayoutParams(itemADWidth, itemADHeight));
        ADDataProvider.initAdvertisement(sibTopAd, adList, itemADHeight, itemADWidth);
        adClickEvent();
    }

    private void addHeaderView() {
        View header = LayoutInflater.from(context).inflate(R.layout.findandhome_helpeachother_head, null);
        mZhuanRang = header.findViewById(R.id.findandhome_helpeachother_zhuanrang);
        mHuZhu = header.findViewById(R.id.findandhome_helpeachother_huzhu);
        sibTopAd = header.findViewById(R.id.sib_top_ad);


        mZhuanRang.setOnClickListener(this);
        mHuZhu.setOnClickListener(this);
        mHomeAndFindHelpEachOtherAdapter.addHeaderView(header);
    }

    /**
     * 广告栏点击事件
     */
    private void adClickEvent() {
        sibTopAd.setOnItemClickL(new SimpleImageBanner.OnItemClickL() {
            @Override
            public void onItemClick(int position) {

                ADDataProvider.adToInformation(context, adList.get(position));
            }
        });
    }

    @Override
    protected void initView() {
//        sibTopAd = $(R.id.sib_top_ad);

        rvContainer = $(R.id.rv_container);
        progressActivity = $(R.id.progressActivity);
        swipeContainer = $(R.id.swipe_container);

//        mZhuanRang = $(R.id.findandhome_helpeachother_zhuanrang);
//        mHuZhu = $(R.id.findandhome_helpeachother_huzhu);

//        mZhuanRang.setOnClickListener(this);
//        mHuZhu.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.findandhome_helpeachother_zhuanrang:
                type = "1";
                onRefresh();
                break;
            case R.id.findandhome_helpeachother_huzhu:
                type = "2";
                onRefresh();
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
    public String getLocationLat() {
        return MyApplication.lat + "";
    }

    @Override
    public String getLocationLng() {
        return MyApplication.lng + "";
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public void getHomeAndFindHelpEachOtherDataSuccess(HomeAndFindHelpEachOtherListInfo datas, int maxpage) {

        if (datas.ad_list != null && datas.ad_list.size() > 0 && adList != null && sibTopAd != null) {
            adList.clear();
            adList.addAll(datas.ad_list);
            ADDataProvider.initAdvertisement(sibTopAd, adList, itemADHeight, itemADWidth);
        }
        if (datas.cooperation_list.list.size() == 0) {
//            progressActivityUtils.showEmptry("暂无数据");
            mList.clear();
//            mList=list;
            HomeAndFindHelpEachOtherInfo info = new HomeAndFindHelpEachOtherInfo();
            mList.add(info);
            mHomeAndFindHelpEachOtherAdapter.notifyDataSetChanged();
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
            mList.addAll(datas.cooperation_list.list);
            mHomeAndFindHelpEachOtherAdapter.notifyDataSetChanged();
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
        mHomeAndFindHelpEachOtherPresenter.getHomeAndFindHelpEachOtherData();
    }
}
