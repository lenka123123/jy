package sinia.com.baihangeducation.mine;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.example.framwork.utils.ProgressActivityUtils;
import com.example.framwork.widget.ProgressActivity;
import com.example.framwork.widget.superrecyclerview.recycleview.SuperRecyclerView;

import java.util.ArrayList;
import java.util.List;

import sinia.com.baihangeducation.R;
import com.mcxtzhang.swipemenulib.base.BaseFragment;
import sinia.com.baihangeducation.mine.adapter.MyReleaseFunAdapter;
import com.mcxtzhang.swipemenulib.info.bean.MyReleaseFunInfo;
import com.mcxtzhang.swipemenulib.info.bean.MyReleaseInfo;
import sinia.com.baihangeducation.mine.presenter.MyReleasePresenter;
import sinia.com.baihangeducation.mine.view.MyReleaseHelpView;
import com.mcxtzhang.swipemenulib.utils.Constants;

/**
 * Created by Administrator on 2018/4/10.
 */

public class InterestingFragment extends BaseFragment implements MyReleaseHelpView, SuperRecyclerView.LoadingListener, SwipeRefreshLayout.OnRefreshListener {

    private int countpage = 1;
    private int itemnum = 20;
    private MyReleasePresenter presenter;

    private SuperRecyclerView rvContainer;
    private ProgressActivity progressActivity;
    private SwipeRefreshLayout swipeContainer;
    private ProgressActivityUtils progressActivityUtils;
    private MyReleaseFunAdapter myReleaseFunAdapter;
    private List<MyReleaseFunInfo> list;
    private boolean isLoadMore = false;


    @Override
    public int initLayoutResID() {
        return R.layout.interestingfragment;
    }


    @Override
    protected void initData() {
        list = new ArrayList<>();
        presenter = new MyReleasePresenter(context, this);
        presenter.getFunData();

        myReleaseFunAdapter = new MyReleaseFunAdapter(context, list);

        initSwipeLayout(swipeContainer, this);
        showSwipeRefreshLayout(swipeContainer);
        progressActivityUtils = new ProgressActivityUtils(context, progressActivity);
        initRecyclerView(rvContainer, myReleaseFunAdapter, this);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter = new MyReleasePresenter(context, this);
        onRefresh();
    }

    @Override
    protected void initView() {

        rvContainer = $(R.id.rv_container);
        progressActivity = $(R.id.progressActivity);
        swipeContainer = $(R.id.swipe_container);
//        getServerData();

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
    public String getTab() {
        return Constants.RELEASE_TAB_FUN;
    }

    @Override
    public String getType() {
        return "";
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
    public void getHelpHelpSuccess(List<MyReleaseInfo> myReleaseInfoHelp, int maxpage) {
    }

    @Override
    public void getHelpTranfSuccess(List<MyReleaseInfo> myReleaseInfoTranf, int maxpage) {
    }

    @Override
    public void getFunSuccess(List<MyReleaseFunInfo> mMyReleaseFunInfo, int maxpage) {
        if (mMyReleaseFunInfo.size() == 0) {
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
                list.clear();
            }
            list.addAll(mMyReleaseFunInfo);
            myReleaseFunAdapter.notifyDataSetChanged();
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

    private void getServerData() {
        presenter.getFunData();
    }

    @Override
    public void onClick(View v) {

    }
}
