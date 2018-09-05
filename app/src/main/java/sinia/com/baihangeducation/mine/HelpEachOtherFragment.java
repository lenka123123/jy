package sinia.com.baihangeducation.mine;

import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.RadioButton;

import com.example.framwork.utils.ProgressActivityUtils;
import com.example.framwork.widget.ProgressActivity;
import com.example.framwork.widget.superrecyclerview.recycleview.SuperRecyclerView;

import java.util.ArrayList;
import java.util.List;

import sinia.com.baihangeducation.R;
import com.mcxtzhang.swipemenulib.base.BaseFragment;
import sinia.com.baihangeducation.mine.adapter.MyReleaseAdapter;
import com.mcxtzhang.swipemenulib.info.bean.MyReleaseFunInfo;
import com.mcxtzhang.swipemenulib.info.bean.MyReleaseInfo;
import sinia.com.baihangeducation.mine.presenter.MyReleasePresenter;
import sinia.com.baihangeducation.mine.view.MyReleaseHelpView;
import com.mcxtzhang.swipemenulib.utils.Constants;

/**
 * Created by Administrator on 2018/4/10.
 */

public class HelpEachOtherFragment extends BaseFragment implements MyReleaseHelpView, SuperRecyclerView.LoadingListener, SwipeRefreshLayout.OnRefreshListener {

    private RadioButton mHelp;                  //互助
    private RadioButton mTransfer;              //转让
    private ListView mListView;
    private String type = 2 + "";                        //1互助 2转让
    private int countpage = 1;
    private int itemnum = 20;
    private MyReleasePresenter presenter;

    private SuperRecyclerView rvContainer;
    private ProgressActivity progressActivity;
    private SwipeRefreshLayout swipeContainer;
    private ProgressActivityUtils progressActivityUtils;
    private MyReleaseAdapter myReleaseAdapter;
    private List<MyReleaseInfo> list;
    private boolean isLoadMore = false;


    @Override
    public int initLayoutResID() {
        return R.layout.helpeachotherfragment;
    }

    @Override
    protected void initData() {
        list = new ArrayList<>();
        presenter = new MyReleasePresenter(context, this);
        getServerData();
//        presenter.getHelpData();

        myReleaseAdapter = new MyReleaseAdapter(context, list);

        initSwipeLayout(swipeContainer, this);
        showSwipeRefreshLayout(swipeContainer);
        progressActivityUtils = new ProgressActivityUtils(context, progressActivity);
        initRecyclerView(rvContainer, myReleaseAdapter, this);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter = new MyReleasePresenter(context, this);
        onRefresh();
    }

    @Override
    protected void initView() {
        mHelp = $(R.id.helpeachotherfragment_help);
        mTransfer = $(R.id.helpeachotherfragment_transfer);

        rvContainer = $(R.id.rv_container);
        progressActivity = $(R.id.progressActivity);
        swipeContainer = $(R.id.swipe_container);

        mHelp.setOnClickListener(this);
        mTransfer.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.helpeachotherfragment_help:
                //互助
                type = 2 + "";
                presenter.getHelpData();
                onRefresh();
                break;
            case R.id.helpeachotherfragment_transfer:
                //转让
                type = 1 + "";
                presenter.getTranfData();
                onRefresh();
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
    public String getTab() {
        return Constants.RELEASE_TAB_HELPEACHOTHER;
    }

    @Override
    public String getType() {
        return type;
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
        Log.i("哈哈", "" + myReleaseInfoHelp.toString());
        if (myReleaseInfoHelp.size() == 0) {
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
            list.addAll(myReleaseInfoHelp);
            myReleaseAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void getHelpTranfSuccess(List<MyReleaseInfo> myReleaseInfoTranf, int maxpage) {
        if (myReleaseInfoTranf.size() == 0) {
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
            list.addAll(myReleaseInfoTranf);
            myReleaseAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void getFunSuccess(List<MyReleaseFunInfo> myReleaseFunListInfos, int maxpage) {

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
        if (type.equals("1") || type == 1 + "") {
            presenter.getHelpData();
        } else {
            presenter.getTranfData();
        }
    }
}
