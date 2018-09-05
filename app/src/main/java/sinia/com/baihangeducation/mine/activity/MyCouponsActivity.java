package sinia.com.baihangeducation.mine.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.RadioButton;

import com.example.framwork.utils.ProgressActivityUtils;
import com.example.framwork.widget.ProgressActivity;
import com.example.framwork.widget.superrecyclerview.recycleview.SuperRecyclerView;

import java.util.ArrayList;
import java.util.List;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.supplement.base.BaseActivity;
import sinia.com.baihangeducation.mine.adapter.MyCouponsAdapter;
import com.mcxtzhang.swipemenulib.info.bean.MyCouponsInfo;
import com.mcxtzhang.swipemenulib.info.bean.MyCouponsItemInfo;
import sinia.com.baihangeducation.mine.presenter.MyCouponsPresenter;
import sinia.com.baihangeducation.mine.view.MyCouponsView;

/**
 * Created by Administrator on 2018/4/9.
 */

public class MyCouponsActivity extends BaseActivity implements MyCouponsView, SuperRecyclerView.LoadingListener, SwipeRefreshLayout.OnRefreshListener {

    private RadioButton mUnUse;             //未使用
    private RadioButton mUse;               //已使用
    private RadioButton mPass;              //已过期
    private String type = "1";                    //优惠券类型 1：已用；2：未用；3过期
    private MyCouponsPresenter myCouponsPresenter;
    private List<MyCouponsItemInfo> myCouponsItemInfos;

    private SuperRecyclerView rvContainer;
    private ProgressActivity progressActivity;
    private SwipeRefreshLayout swipeContainer;
    private ProgressActivityUtils progressActivityUtils;
    private MyCouponsAdapter myCouponsAdapter;
    private boolean isLoadMore = false;
    private int countpage = 1;
    private int itemnum = 10;

    @Override
    public int initLayoutResID() {
        return R.layout.mycoupons;
    }

    @Override
    protected void initData() {

        mCommonTitle.setCenterText(R.string.mycoupons);
        mCommonTitle.setBackground(getResources().getDrawable(R.color.white));

        myCouponsPresenter = new MyCouponsPresenter(context, this);

        myCouponsItemInfos = new ArrayList<>();
        myCouponsAdapter = new MyCouponsAdapter(context,myCouponsItemInfos);
        getCouponsData();

        initSwipeLayout(swipeContainer, this);
        showSwipeRefreshLayout(swipeContainer);
        progressActivityUtils = new ProgressActivityUtils(context, progressActivity);
        initRecyclerView(rvContainer, myCouponsAdapter, this);
    }

    @Override
    protected void initView() {
        mUnUse = $(R.id.mycoupons_unuse);
        mUse = $(R.id.mycoupons_use);
        mPass = $(R.id.mycoupons_pass);
        rvContainer = $(R.id.rv_container);
        progressActivity = $(R.id.progressActivity);
        swipeContainer = $(R.id.swipe_container);

        mUnUse.setOnClickListener(this);
        mUse.setOnClickListener(this);
        mPass.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.mycoupons_unuse:
                type = 1 + "";
                getCouponsData();
                break;
            case R.id.mycoupons_use:
                type = 2 + "";
                getCouponsData();
                break;
            case R.id.mycoupons_pass:
                type = 3 + "";
                getCouponsData();
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
    public String getPage() {
        return countpage + "";
    }

    @Override
    public String getItenmNum() {
        return itemnum + "";
    }

    @Override
    public String getCouponsType() {
        return type;
    }

    @Override
    public void getCouponsSuccess(MyCouponsInfo myCouponsInfo,int maxpage) {
        mUnUse.setText("未使用（" + myCouponsInfo.no_use_num + "）");
        mUse.setText("未使用（" + myCouponsInfo.use_num + "）");
        mPass.setText("未使用（" + myCouponsInfo.expired_num + "）");
        if (myCouponsInfo.list.size() == 0) {
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
                myCouponsItemInfos.clear();
            }
            myCouponsItemInfos.addAll(myCouponsInfo.list);
            myCouponsAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 获取数据
     */
    public void getCouponsData() {
        myCouponsPresenter.getCoupons();
    }

    @Override
    public void onRefresh() {
        isLoadMore = false;
        countpage = 1;
        getCouponsData();
    }

    @Override
    public void onLoadMore() {
        isLoadMore = true;
        getCouponsData();
    }
}
