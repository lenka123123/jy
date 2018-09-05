package sinia.com.baihangeducation.release.activity;

import android.support.v4.widget.SwipeRefreshLayout;

import com.example.framwork.utils.ProgressActivityUtils;
import com.example.framwork.widget.ProgressActivity;
import com.example.framwork.widget.superrecyclerview.recycleview.SuperRecyclerView;

import java.util.ArrayList;
import java.util.List;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.release.adapter.SchoolFunAdapter;
import sinia.com.baihangeducation.release.presenter.SchoolFunPresenter;
import sinia.com.baihangeducation.release.view.ISchoolFunView;
import sinia.com.baihangeducation.supplement.base.BaseActivity;
import sinia.com.baihangeducation.find.campus.info.CampusInterestingInfo;
import sinia.com.baihangeducation.find.campus.info.bean.CampusInterestingListInfo;

public class SchoolFunActivity extends BaseActivity implements ISchoolFunView , SuperRecyclerView.LoadingListener, SwipeRefreshLayout.OnRefreshListener {
    private int countpage = 1;
    private int itemnum = 10;
    private SchoolFunPresenter presenter;

    private SuperRecyclerView rvContainer;
    private ProgressActivity progressActivity;
    private SwipeRefreshLayout swipeContainer;
    private ProgressActivityUtils progressActivityUtils;
    private SchoolFunAdapter mSchoolFunAdapter;
    private List<CampusInterestingListInfo> mList;
    private boolean isLoadMore = false;

    @Override
    public int initLayoutResID() {
        return R.layout.fragment_home_recommended_activity;
    }

    @Override
    protected void initData() {
        mList = new ArrayList<>();
        mCommonTitle.setCenterText(R.string.fundetail);
        mCommonTitle.setBackground(getResources().getDrawable(R.color.white));

        presenter = new SchoolFunPresenter(context,this);
        presenter.getSchoolFunData();

        mSchoolFunAdapter = new SchoolFunAdapter(context, mList);

        initSwipeLayout(swipeContainer, this);
        showSwipeRefreshLayout(swipeContainer);
        progressActivityUtils = new ProgressActivityUtils(context, progressActivity);
        initRecyclerView(rvContainer, mSchoolFunAdapter, this);
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
    public void getSchoolFunSuccess(CampusInterestingInfo funInfo , int maxpage) {
        if (funInfo.list.size() == 0) {
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
            mList.addAll(funInfo.list);
            mSchoolFunAdapter.notifyDataSetChanged();
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
        presenter.getSchoolFunData();
    }
}
