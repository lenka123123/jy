package sinia.com.baihangeducation.mine.activity;

import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.framwork.utils.ProgressActivityUtils;
import com.example.framwork.widget.ProgressActivity;
import com.example.framwork.widget.superrecyclerview.recycleview.SuperRecyclerView;

import java.util.ArrayList;
import java.util.List;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.supplement.base.BaseActivity;

import sinia.com.baihangeducation.mine.adapter.HelpEachOtherAdapter;
import com.mcxtzhang.swipemenulib.info.bean.HelpEachOtherInfo;
import com.mcxtzhang.swipemenulib.info.bean.HelpEachOtherListInfo;
import sinia.com.baihangeducation.mine.presenter.HelpEachOtherPresenter;
import sinia.com.baihangeducation.mine.view.HelpEachOtherView;

/**
 * 我的页面 互助
 */

public class HelpEachOtherActivity extends BaseActivity implements HelpEachOtherView, SuperRecyclerView.LoadingListener, SwipeRefreshLayout.OnRefreshListener {

    private HelpEachOtherPresenter mHelpEachOtherPresenter;
    private RadioButton mPayMoney;
    private RadioButton mGetMoney;
    private TextView mAllTimes;
    private TextView mGetHelp;
    private TextView mForHelp;
    private HelpEachOtherAdapter mHelpEachOtherAdapter;

    private ProgressActivityUtils progressActivityUtils;

    private SuperRecyclerView rvContainer;
    private ProgressActivity progressActivity;
    private SwipeRefreshLayout swipeContainer;
    private boolean isLoadMore;

    private List<HelpEachOtherInfo> mList;
    private int countpage = 1;
    private int itemnum = 20;
    private String type = "1";

    @Override
    public int initLayoutResID() {
        return R.layout.fragment_mine_helpeachother;
    }

    @Override
    protected void initData() {
        mList = new ArrayList<>();
        mCommonTitle.setCenterText(R.string.helpeachothergrowthrecord);
        mCommonTitle.setBackgroundColor(Color.WHITE);

        mHelpEachOtherPresenter = new HelpEachOtherPresenter(context, this);
        mHelpEachOtherPresenter.getHelpEachOtherData();

        mHelpEachOtherAdapter = new HelpEachOtherAdapter(context,mList);

        initSwipeLayout(swipeContainer, this);
        showSwipeRefreshLayout(swipeContainer);
        progressActivityUtils = new ProgressActivityUtils(context, progressActivity);
        initRecyclerView(rvContainer, mHelpEachOtherAdapter, this);
    }

    @Override
    protected void initView() {

        rvContainer = $(R.id.rv_container);
        progressActivity = $(R.id.progressActivity);
        swipeContainer = $(R.id.swipe_container);
//        getServerData();

        mPayMoney = $(R.id.paymoney);
        mGetMoney = $(R.id.getmoney);
        mAllTimes = $(R.id.alltimes);
        mGetHelp = $(R.id.gethelp);
        mForHelp = $(R.id.forhelp);

        mPayMoney.setOnClickListener(this);
        mGetMoney.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.paymoney:
                //打赏
                type = "1";
                onRefresh();
                break;
            case R.id.getmoney:
                //领尚
                type = "2";
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
    public void getHelpEachOtherDataSuccess(HelpEachOtherListInfo data, int maxpage) {
        mAllTimes.setText(data.count + "");
        mForHelp.setText(data.for_help_count + "次");
        mGetHelp.setText(data.help_count + "次");
        List<HelpEachOtherInfo> list = data.list;
        if (list.size() == 0) {
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
            mList.addAll(list);
            mHelpEachOtherAdapter.notifyDataSetChanged();
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

    public void getServerData() {
        mHelpEachOtherPresenter.getHelpEachOtherData();
    }
}
