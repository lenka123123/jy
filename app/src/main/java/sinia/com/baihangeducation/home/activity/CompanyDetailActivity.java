package sinia.com.baihangeducation.home.activity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.framwork.glide.ImageLoaderUtils;
import com.example.framwork.utils.ProgressActivityUtils;
import com.example.framwork.widget.ProgressActivity;
import com.example.framwork.widget.superrecyclerview.recycleview.SuperRecyclerView;
import com.zzhoujay.richtext.RichText;


import java.util.ArrayList;
import java.util.List;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.supplement.base.BaseActivity;
import sinia.com.baihangeducation.home.adapter.CompanyDetailAdapter;
import com.mcxtzhang.swipemenulib.info.CompanyDetailInfo;
import com.mcxtzhang.swipemenulib.info.bean.CompanyJobListInfo;
import sinia.com.baihangeducation.home.present.CompanyDetailPresenter;
import sinia.com.baihangeducation.home.view.ICompanyDetailView;
import com.mcxtzhang.swipemenulib.utils.ACache;

public class CompanyDetailActivity extends BaseActivity implements ICompanyDetailView, SuperRecyclerView.LoadingListener, SwipeRefreshLayout.OnRefreshListener {
    private Intent intent;
    private String companyId;           //公司ID
    private ACache mCache;
//    private String lng;                 //经度
//    private String lat;                 //纬度
    private CompanyDetailPresenter mCompanyDetailPresenter;
    private int countpage = 1;          //页码
    private int itemnum = 20;           //每页数

    private ImageView logo;
    private TextView companyName;
    private TextView slogan;
    private ImageView isRealName;
    private TextView companyInfo;
    private TextView companyAdress;
    private TextView companyTel;
    private TextView companyOtherInfo;

    //一下是下拉刷新
    private SuperRecyclerView rvContainer;
    private ProgressActivity progressActivity;
    private SwipeRefreshLayout swipeContainer;
    private ProgressActivityUtils progressActivityUtils;
    private CompanyDetailAdapter mCompanyDetailAdapter;
    private List<CompanyJobListInfo> mList;
    private boolean isLoadMore = false;

    @Override
    public int initLayoutResID() {
        return R.layout.findandhome_helpeachother;
    }

    @Override
    protected void initData() {
        mList = new ArrayList<>();
        mCache = ACache.get(context);
        lng = mCache.getAsString("LNG");
        lat = mCache.getAsString("LAT");
        intent = getIntent();
        companyId = intent.getStringExtra("COMPANYID");
        mCommonTitle.setCenterText(R.string.companydetail);
        mCommonTitle.setBackground(getResources().getDrawable(R.color.white));
        mCompanyDetailPresenter = new CompanyDetailPresenter(context, this);
        mCompanyDetailPresenter.getCompanyInfo();

        mCompanyDetailAdapter = new CompanyDetailAdapter(context, mList);

        initSwipeLayout(swipeContainer, this);
        showSwipeRefreshLayout(swipeContainer);
        progressActivityUtils = new ProgressActivityUtils(context, progressActivity);
        initRecyclerView(rvContainer, mCompanyDetailAdapter, this);
        addHeaderView();
    }

    private void addHeaderView() {
        View header = LayoutInflater.from(context).inflate(R.layout.companydetail, null);

        logo = header.findViewById(R.id.companydetail_logo);
        companyName = header.findViewById(R.id.companydetail_companyname);
        slogan = header.findViewById(R.id.companydetail_companyslogan);
        isRealName = header.findViewById(R.id.companydetail_isrealname);
        companyInfo = header.findViewById(R.id.companydetail_companyinfo);
        companyAdress = header.findViewById(R.id.companydetail_companyadress);
        companyTel = header.findViewById(R.id.companydetail_companytel);
        companyOtherInfo = header.findViewById(R.id.companydetail_companyotherinfo);

        mCompanyDetailAdapter.addHeaderView(header);
    }

    @Override
    protected void initView() {
        rvContainer = $(R.id.rv_container);
        progressActivity = $(R.id.progressActivity);
        swipeContainer = $(R.id.swipe_container);
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
    public String getLocationLng() {
        return lng;
    }

    @Override
    public String getLocationLat() {
        return lat;
    }

    @Override
    public String getCompanyId() {
        return companyId;
    }

    @Override
    public void getCompanyDetailSuccess(CompanyDetailInfo info, int maxpage) {
        if (info.company_info != null) {
            ImageLoaderUtils.displayRound(context, logo, info.company_info.company_logo, R.drawable.new_eorrlogo);
            companyName.setText(info.company_info.company_name);
            slogan.setText(info.company_info.company_slogan);
            if (info.company_info.company_status == 1) {
                isRealName.setVisibility(View.VISIBLE);
            } else {
                isRealName.setVisibility(View.GONE);
            }
            RichText.from(info.company_info.company_introduce).into(companyInfo);
//            companyInfo.setText(info.company_info.company_introduce);
            companyAdress.setText(info.company_info.company_address);
            companyTel.setText(info.company_info.company_link_phone);
            companyOtherInfo.setText("TA发布的其他信息（" + info.company_job_list.count + "）");
        }
            if (info.company_job_list.list.size() == 0) {
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
                mList.addAll(info.company_job_list.list);
                mCompanyDetailAdapter.notifyDataSetChanged();
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
        mCompanyDetailPresenter.getCompanyInfo();
    }
}
