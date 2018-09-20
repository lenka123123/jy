package sinia.com.baihangeducation.reconsitution.tabs.coffers.coffersdetail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mcxtzhang.swipemenulib.customview.GlideLoadUtils;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.reconsitution.tabs.coffers.coffersdetail.interfaces.RankingContract;
import sinia.com.baihangeducation.reconsitution.tabs.coffers.coffersdetail.model.CoffersDetail;
import sinia.com.baihangeducation.reconsitution.tabs.coffers.coffersdetail.model.RankingModel;
import sinia.com.baihangeducation.reconsitution.tabs.coffers.coffersdetail.model.RankingRecentAdapter;
import sinia.com.baihangeducation.reconsitution.tabs.coffers.coffersdetail.presenter.RankingPresenter;

/**
 * 创建日期：2018/5/26 on 15:23
 * 描述:
 * 作者:yuxd Administrator
 */
public class AllCoffersFragment extends Fragment implements RankingContract.View {
    private RankingRecentAdapter mMemberManageAdapter;
    private List<CoffersDetail.Coffers> mRows = new ArrayList<>();
    private RankingPresenter mRankingPresenter;
    private boolean addData = false;
    private int currentPage = 1;
    private boolean isCreated = false;
    private View root;
    private RefreshLayout mSmartRefreshLayout;
    private ImageView mErrorImageView;
    private TextView mErrorTextView;
    private RecyclerView mAutoLoadRecyclerView;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (!isCreated) {
            return;
        }
        if (isVisibleToUser) {
            mRows.clear();
            mMemberManageAdapter.notifyDataSetChanged();
            mRankingPresenter.getDateRecentMemberManageList(0, 1);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isCreated = true;
        mRankingPresenter = new RankingPresenter(new RankingModel(getActivity()), this);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.activity_recyclerview_refresh, viewGroup, false);
        mSmartRefreshLayout = root.findViewById(R.id.refreshLayout);
        mErrorImageView = root.findViewById(R.id.img_state);
        mErrorTextView = root.findViewById(R.id.tv_state);
        mAutoLoadRecyclerView = root.findViewById(R.id.recyclerView);

        mMemberManageAdapter = new RankingRecentAdapter(getContext(), 1);
        mAutoLoadRecyclerView.setAdapter(mMemberManageAdapter);
        setPullRefresher();
        final GridLayoutManager manager = new GridLayoutManager(getContext(), 1);
        mAutoLoadRecyclerView.setLayoutManager(manager);
        mAutoLoadRecyclerView.setItemAnimator(new DefaultItemAnimator());
        addData = false;
        mMemberManageAdapter.setData(mRows,currentPage);
        if (getUserVisibleHint()) {
            mRankingPresenter.getDateRecentMemberManageList(0, currentPage);
        }
        return root;
    }

    private void setPullRefresher() {
        mSmartRefreshLayout.setRefreshHeader(new MaterialHeader(getContext()));
        //   mSmartRefreshLayout.setRefreshFooter(new BallPulseFooter(getContext()));
        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                currentPage = 1;
                mRows.clear();
                mRankingPresenter.getDateRecentMemberManageList(0, currentPage);
                refreshlayout.finishRefresh(2000);
            }
        });
        mSmartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                //      AllCoffersFragment.this.refreshlayout = refreshlayout;
                if (addData) {
                    mRankingPresenter.getDateRecentMemberManageList(0, currentPage);
                } else {

                    mSmartRefreshLayout.finishLoadMoreWithNoMoreData();
                }
                refreshlayout.finishLoadMore(2000);
            }
        });
    }

    public void upDateRanking(int total, int page) {
        if (20 * page < total) {
            addData = true;
            currentPage = page + 1;
        } else {
            addData = false;
        }
        mSmartRefreshLayout.setNoMoreData(false);
    }

    @Override
    public void showRankingList(CoffersDetail successMessage) {


        if (successMessage.list.size() < 1) {
            showErrorState(true);
        } else {
            showErrorState(false);
        }
        if (mSmartRefreshLayout != null)
            mSmartRefreshLayout.finishLoadMore(500);
        mRows.addAll(successMessage.list);
        mMemberManageAdapter.setData(mRows,currentPage);

        if (10 * currentPage < successMessage.count) {
            addData = true;
            currentPage = currentPage + 1;
        } else {
            addData = false;
        }


        mMemberManageAdapter.notifyDataSetChanged();
    }

    @Override
    public void upDateRankingList(CoffersDetail successMessage) {

    }

    @Override
    public void showError(String errorMessage) {
        showErrorState(true);
    }

    private void showErrorState(boolean isError) {
        mErrorImageView.setVisibility(View.GONE);
        mErrorTextView.setVisibility(View.GONE);

//        if (isError) {
//            mErrorImageView.setVisibility(View.VISIBLE);
//            mErrorTextView.setVisibility(View.VISIBLE);
//            GlideLoadUtils.getInstance().glideLoadDefault(getActivity(), R.drawable.logo, mErrorImageView, R.drawable.logo);
//            mErrorTextView.setText("没有数据");
//            if (mSmartRefreshLayout != null)
//                mSmartRefreshLayout.finishLoadMoreWithNoMoreData();
//        } else {
//            mErrorImageView.setVisibility(View.GONE);
//            mErrorTextView.setVisibility(View.GONE);
//        }
    }
}