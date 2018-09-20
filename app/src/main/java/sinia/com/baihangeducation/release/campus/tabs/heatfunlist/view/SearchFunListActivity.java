package sinia.com.baihangeducation.release.campus.tabs.heatfunlist.view;

import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.club.searchschool.SearchListActivity;
import sinia.com.baihangeducation.release.campus.tabs.heatfunlist.adapter.HotFunAdapter;
import sinia.com.baihangeducation.release.campus.tabs.heatfunlist.bean.HotFunListBean;
import sinia.com.baihangeducation.release.campus.tabs.heatfunlist.interfaces.HotFunListContract;
import sinia.com.baihangeducation.release.campus.tabs.heatfunlist.model.HotFunListModel;
import sinia.com.baihangeducation.release.campus.tabs.heatfunlist.present.HotFunListPresent;
import sinia.com.baihangeducation.release.campus.tabs.heatfunsearchlist.view.SearchFunSearchListActivity;
import sinia.com.baihangeducation.supplement.base.BaseActivity;

public class SearchFunListActivity extends BaseActivity implements HotFunListContract.View {

    private RecyclerView mAutoLoadRecyclerView;
    private HotFunListPresent mHotFunListPresent;
    private RefreshLayout mSmartRefreshLayout;
    private ImageView mErrorImageView;
    private TextView mErrorTextView;
    private HotFunAdapter hotFunAdapter;
    private List<HotFunListBean.HotList> mList = new ArrayList<>();
    private RelativeLayout mRelativeLayout;

    @Override
    public int initLayoutResID() {
        return R.layout.search_fun_list_activity;
    }

    @Override
    protected void initData() {
        mHotFunListPresent = new HotFunListPresent(new HotFunListModel(context), this);
        mHotFunListPresent.getHotFunList(0, 0);

        hotFunAdapter.setItemClickListener(new HotFunAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent();
                intent.putExtra("topic_title", mList.get(position).topic_title);
                intent.putExtra("topic_id", mList.get(position).topic_id);
                setResult(1000, intent);
                SearchFunListActivity.this.finish();
            }
        });

        mRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, SearchFunSearchListActivity.class);
                startActivityForResult(intent, 999);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 999 && resultCode == 1000) {
            String topic_title = data.getStringExtra("topic_title");

            String mTopicID = data.getStringExtra("topic_id");

            Intent intent = new Intent();
            intent.putExtra("topic_title", topic_title);
            intent.putExtra("topic_id", mTopicID);
            setResult(1000, intent);
            SearchFunListActivity.this.finish();
        }
    }

    @Override
    protected void initView() {
        mSmartRefreshLayout = findViewById(R.id.refreshLayout);
        mErrorImageView = findViewById(R.id.img_state);
        mErrorTextView = findViewById(R.id.tv_state);
        mAutoLoadRecyclerView = findViewById(R.id.recyclerView);
        mRelativeLayout = findViewById(R.id.search);
        ImageView back = findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchFunListActivity.this.finish();
            }
        });
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
//
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
