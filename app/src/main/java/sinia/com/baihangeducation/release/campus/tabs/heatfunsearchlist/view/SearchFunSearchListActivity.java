package sinia.com.baihangeducation.release.campus.tabs.heatfunsearchlist.view;

import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.release.campus.tabs.heatfunsearchlist.adapter.HotFunAdapter;
import sinia.com.baihangeducation.release.campus.tabs.heatfunsearchlist.bean.HotFunListBean;
import sinia.com.baihangeducation.release.campus.tabs.heatfunsearchlist.interfaces.HotFunListContract;
import sinia.com.baihangeducation.release.campus.tabs.heatfunsearchlist.model.HotFunListModel;
import sinia.com.baihangeducation.release.campus.tabs.heatfunsearchlist.present.HotFunListPresent;
import sinia.com.baihangeducation.supplement.base.BaseActivity;

public class SearchFunSearchListActivity extends BaseActivity implements HotFunListContract.View {

    private RecyclerView mAutoLoadRecyclerView;
    private HotFunListPresent mHotFunListPresent;

    private RefreshLayout mSmartRefreshLayout;
    private ImageView mErrorImageView;
    private TextView mErrorTextView;
    private HotFunAdapter hotFunAdapter;
    private List<HotFunListBean.HotList> mList = new ArrayList<>();
    private EditText mSearchEditText;

    @Override
    public int initLayoutResID() {
        return R.layout.search_fun_search_list_activity;
    }

    @Override
    protected void initData() {
        mHotFunListPresent = new HotFunListPresent(new HotFunListModel(context), this);


        hotFunAdapter.setItemClickListener(new HotFunAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent();
                intent.putExtra("topic_title", mList.get(position).topic_title);
                intent.putExtra("topic_id", mList.get(position).topic_id);
                setResult(1000, intent);
                SearchFunSearchListActivity.this.finish();
            }
        });
        mSearchEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() >= 1) {
                    mList.clear();
                    hotFunAdapter.setData(mList);
                    hotFunAdapter.notifyDataSetChanged();
                    mHotFunListPresent.getHotFunList(0, 0, s.toString());

                }
            }
        });

    }

    @Override
    protected void initView() {
        mSmartRefreshLayout = findViewById(R.id.refreshLayout);
        mErrorImageView = findViewById(R.id.img_state);
        mErrorTextView = findViewById(R.id.tv_state);
        mAutoLoadRecyclerView = findViewById(R.id.recyclerView);
        mSearchEditText = findViewById(R.id.background);

        hotFunAdapter = new HotFunAdapter(context);
        mAutoLoadRecyclerView.setAdapter(hotFunAdapter);
        setPullRefresher();
        final GridLayoutManager manager = new GridLayoutManager(context, 1);
        mAutoLoadRecyclerView.setLayoutManager(manager);
        mAutoLoadRecyclerView.setItemAnimator(new DefaultItemAnimator());
        hotFunAdapter.setData(mList);
    }


    private void setPullRefresher() {
        mSmartRefreshLayout.setRefreshHeader(new MaterialHeader(context));
        //   mSmartRefreshLayout.setRefreshFooter(new BallPulseFooter(getContext()));
        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
//                if (addData) {
//                    mRankingPresenter.getDateRecentMemberManageList(1, currentPage);
//                } else {
//                    mSmartRefreshLayout.setNoMoreData(false);
//                }
                refreshlayout.finishRefresh(2000);
            }
        });
        mSmartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                //      DayRankingFragment.this.refreshlayout = refreshlayout;
//                if (addData) {
//                    mRankingPresenter.getDateRecentMemberManageList(1, currentPage);
//                } else {
//                    mSmartRefreshLayout.finishLoadMoreWithNoMoreData();
//                }
                refreshlayout.finishLoadMore(2000);
            }
        });
    }

    @Override
    public void showHotFunList(HotFunListBean successMessage) {
        mList = successMessage.list;
        hotFunAdapter.setData(mList);
        hotFunAdapter.notifyDataSetChanged();

    }

    @Override
    public void upDateHotFunList(HotFunListBean successMessage) {

    }

    @Override
    public void showError(String errorMessage) {

    }
}
