package sinia.com.baihangeducation.newcampus;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.RadioButton;

import com.example.framwork.utils.ProgressActivityUtils;
import com.example.framwork.widget.ProgressActivity;
import com.example.framwork.widget.superrecyclerview.recycleview.SuperRecyclerView;

import java.util.ArrayList;
import java.util.List;

import sinia.com.baihangeducation.R;
import com.mcxtzhang.swipemenulib.base.BaseFragment;
import com.mcxtzhang.swipemenulib.info.bean.JobBangClassADListInfo;
import sinia.com.baihangeducation.home.adapter.HomeAndFindHelpEachOtherAdapter;
import com.mcxtzhang.swipemenulib.info.HomeAndFindHelpEachOtherListInfo;
import com.mcxtzhang.swipemenulib.info.bean.HomeAndFindHelpEachOtherInfo;
import sinia.com.baihangeducation.home.present.HomeAndFindHelpEachOtherPresenter;
import sinia.com.baihangeducation.home.view.HomeAndFindHelpEachOtherView;
import com.mcxtzhang.swipemenulib.utils.ACache;

public class NewCampusHelpFragment extends BaseFragment implements HomeAndFindHelpEachOtherView, SuperRecyclerView.LoadingListener, SwipeRefreshLayout.OnRefreshListener{

    private HomeAndFindHelpEachOtherPresenter mHomeAndFindHelpEachOtherPresenter;
    private List<JobBangClassADListInfo> adList;                    //广告数据
    private int countpage = 1;
    private int itemnum = 10;

    private RadioButton mZhuanRang;     //转让
    private RadioButton mHuZhu;         //互助

    private SuperRecyclerView rvContainer;
    private ProgressActivity progressActivity;
    private SwipeRefreshLayout swipeContainer;
    private ProgressActivityUtils progressActivityUtils;
    private HomeAndFindHelpEachOtherAdapter mHomeAndFindHelpEachOtherAdapter;
    private List<HomeAndFindHelpEachOtherInfo> mList;
    private boolean isLoadMore = false;

    private ACache mCache;
    private String adcode = "320106";
    private String lng = "32.089858";
    private String lat = "118.755877";
    private String type = "2";

    @Override
    public int initLayoutResID() {
        return R.layout.newcampany;
    }

    @Override
    protected void initData() {
        adList = new ArrayList<>();
        mList = new ArrayList<>();

        mCache = ACache.get(context);
        if (mCache.getAsString("ADCODE") != null) {
            adcode = mCache.getAsString("ADCODE");
        }
        if (mCache.getAsString("LAT") != null) {
            lat = mCache.getAsString("LAT");
        }
        if (mCache.getAsString("LNG") != null) {
            lng = mCache.getAsString("LNG");
        }

        mHomeAndFindHelpEachOtherPresenter = new HomeAndFindHelpEachOtherPresenter(context, this);
        mHomeAndFindHelpEachOtherPresenter.getHomeAndFindHelpEachOtherData();

        mHomeAndFindHelpEachOtherAdapter = new HomeAndFindHelpEachOtherAdapter(context, mList);

        initSwipeLayout(swipeContainer, this);
        showSwipeRefreshLayout(swipeContainer);
        progressActivityUtils = new ProgressActivityUtils(context, progressActivity);
        initRecyclerView(rvContainer, mHomeAndFindHelpEachOtherAdapter, this);

    }

    @Override
    protected void initView() {
        rvContainer = $(R.id.rv_container);
        progressActivity = $(R.id.progressActivity);
        swipeContainer = $(R.id.swipe_container);
    }

    @Override
    public void onClick(View view) {

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
        return lat;
    }

    @Override
    public String getLocationLng() {
        return lng;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public void getHomeAndFindHelpEachOtherDataSuccess(HomeAndFindHelpEachOtherListInfo datas, int maxpage) {
//        if (datas.ad_list != null && datas.ad_list.size() > 0 && adList != null && sibTopAd != null) {
//            adList.clear();
//            adList.addAll(datas.ad_list);
//            ADDataProvider.initAdvertisement(sibTopAd, adList, itemADHeight, itemADWidth);
//        }
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
    /**
     * 获取数据
     */
    private void getServerData() {
        mHomeAndFindHelpEachOtherPresenter.getHomeAndFindHelpEachOtherData();
    }
}
