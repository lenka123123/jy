package sinia.com.baihangeducation.reconsitution.tabs.pay.tab;

import android.graphics.Color;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mcxtzhang.swipemenulib.customview.GlideLoadUtils;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.supplement.base.BaseActivity;

public class PaiedListActivity extends BaseActivity {

    private List<PayDetail.Detail> mRows = new ArrayList<>();

    private RefreshLayout mSmartRefreshLayout;
    private RecyclerView mAutoLoadRecyclerView;

    private PaiedListAdapter paiedListAdapter;

    private boolean addData = false;
    private int currentPage = 1;
    private int perpage = 20;
    private boolean isCreated = false;
    private PayListPresenter payListPresenter;
    private ImageView mErrorImageView;
    private TextView mErrorTextView;

    public int initLayoutResID() {
        return R.layout.paied_list;
    }


    @Override
    protected void initData() {
        mCommonTitle.setBackgroundColor(Color.WHITE);
        mCommonTitle.setCenterText("我的购买");
        mCommonTitle.getRightTxt().setTextSize(16);
        mCommonTitle.getRightTxt().setTextColor(Color.BLACK);
        payListPresenter = new PayListPresenter(this, this);
      payListPresenter.getPayList(1, perpage);
    }
    public void setPayListSuccess(PayDetail successMessage) {


        if (successMessage.list.size() < 1) {
            showErrorState(true);
        } else {
            showErrorState(false);
        }
        if (mSmartRefreshLayout != null)
            mSmartRefreshLayout.finishLoadMore(500);
        mRows.addAll(successMessage.list);
        paiedListAdapter.setData(mRows, currentPage);

        if (perpage * currentPage < successMessage.count) {
            addData = true;
            currentPage = currentPage + 1;
        } else {
            addData = false;
        }
        paiedListAdapter.notifyDataSetChanged();

    }


    @Override
    protected void initView() {
        mSmartRefreshLayout = findViewById(R.id.refreshLayout);

        mAutoLoadRecyclerView = findViewById(R.id.recyclerView);

        mErrorImageView = findViewById(R.id.img_state);
        mErrorTextView = findViewById(R.id.tv_state);

        paiedListAdapter = new PaiedListAdapter(this);
        mAutoLoadRecyclerView.setAdapter(paiedListAdapter);
        setPullRefresher();
        final GridLayoutManager manager = new GridLayoutManager(this, 1);
        mAutoLoadRecyclerView.setLayoutManager(manager);
        mAutoLoadRecyclerView.setItemAnimator(new DefaultItemAnimator());
        addData = false;
        paiedListAdapter.setData(mRows, currentPage);

    }

    private void setPullRefresher() {
        mSmartRefreshLayout.setRefreshHeader(new MaterialHeader(this));
        //   mSmartRefreshLayout.setRefreshFooter(new BallPulseFooter(getContext()));
        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                currentPage = 1;
                mRows.clear();
                payListPresenter.getPayList(1, perpage);
                refreshlayout.finishRefresh(2000);
            }
        });
        mSmartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                //      AllCoffersFragment.this.refreshlayout = refreshlayout;
                if (addData) {
                    payListPresenter.getPayList(currentPage, perpage);
                } else {
                    mSmartRefreshLayout.finishLoadMoreWithNoMoreData();
                }
                refreshlayout.finishLoadMore(2000);
            }
        });
    }

    private void showErrorState(boolean isError) {
//        if (isError) {
//            mErrorImageView.setVisibility(View.VISIBLE);
//            mErrorTextView.setVisibility(View.VISIBLE);
//            Glide.with(context).load(R.mipmap.ic_launcher).into(mErrorImageView);
//            mErrorTextView.setText("没有数据");
//            if (mSmartRefreshLayout != null)
//                mSmartRefreshLayout.finishLoadMoreWithNoMoreData();
//        } else {
//            mErrorImageView.setVisibility(View.GONE);
//            mErrorTextView.setVisibility(View.GONE);
//        }
    }

}
