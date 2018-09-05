package sinia.com.baihangeducation.newcampus.tabs.fans.view;

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

import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.newcampus.tabs.fans.adapter.SearchFriendAdapter;
import sinia.com.baihangeducation.newcampus.tabs.fans.bean.SearchFriendListBean;
import sinia.com.baihangeducation.newcampus.tabs.fans.interfaces.FansContract;
import sinia.com.baihangeducation.newcampus.tabs.fans.model.fansListModel;
import sinia.com.baihangeducation.newcampus.tabs.fans.present.fansListPresent;
import sinia.com.baihangeducation.supplement.base.BaseActivity;
import sinia.com.baihangeducation.supplement.base.Goto;

public class FansListActivity extends BaseActivity implements FansContract.View {

    private RecyclerView mAutoLoadRecyclerView;
    private fansListPresent mHotFunListPresent;

    private SearchFriendAdapter hotFunAdapter;
    private List<SearchFriendListBean.Firendes> mList = new ArrayList<>();

    private RefreshLayout mSmartRefreshLayout;
    private ImageView mErrorImageView;
    private TextView mErrorTextView;
    private boolean addData = true;
    private int currentPage = 1;
    private String user_id;
    private String type = "1";

    @Override
    public int initLayoutResID() {
        return R.layout.fans_list_activity;
    }


    @Override
    protected void initView() {

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FansListActivity.this.finish();
            }
        });

        mSmartRefreshLayout = findViewById(R.id.refreshLayout);
        mErrorImageView = findViewById(R.id.img_state);
        mErrorTextView = findViewById(R.id.tv_state);
        mAutoLoadRecyclerView = findViewById(R.id.recyclerView);
        hotFunAdapter = new SearchFriendAdapter(context);
        mAutoLoadRecyclerView.setAdapter(hotFunAdapter);
        final GridLayoutManager manager = new GridLayoutManager(context, 1);
        mAutoLoadRecyclerView.setLayoutManager(manager);
        mAutoLoadRecyclerView.setItemAnimator(new DefaultItemAnimator());
        hotFunAdapter.setData(mList);


    }


    @Override
    protected void initData() {
        user_id = AppConfig.OTHERID;
        mHotFunListPresent = new fansListPresent(new fansListModel(context), this);
        currentPage = 1;
        mHotFunListPresent.getHotFunList(user_id, type, currentPage + "", "20"); //  ( 1：获取该用户的粉丝 2：获取该用户的关注 )
        setPullRefresher();
        hotFunAdapter.setItemClickListener(new SearchFriendAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Goto.toHomePageAgain(mList.get(position).user_id, context);
            }
        });


    }


    private void setPullRefresher() {
        mSmartRefreshLayout.setRefreshHeader(new MaterialHeader(context));
        //   mSmartRefreshLayout.setRefreshFooter(new BallPulseFooter(getContext()));
        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                if (addData) {
                    mHotFunListPresent.getHotFunList(user_id, type, currentPage + "", "20");
                } else {
                    mSmartRefreshLayout.setNoMoreData(false);
                }
                refreshlayout.finishRefresh(2000);
            }
        });
        mSmartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {

                if (addData) {
                    mHotFunListPresent.getHotFunList(user_id, type, currentPage + "", "20");
                } else {
                    mSmartRefreshLayout.finishLoadMoreWithNoMoreData();
                }
                refreshlayout.finishLoadMore(2000);
            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

        switch (v.getId()) {
            case R.id.search:


                break;
        }
    }

    @Override
    public void showHotFunList(SearchFriendListBean successMessage) {
//        successMessage.page;  1
//        successMessage.perpage;20
//        successMessage.count;  2323
        if (successMessage.list == null || successMessage.list.size() < 1) {
            com.example.framwork.utils.Toast.getInstance().showErrorToast(context, "没有数据");
        }

        if ((currentPage * Integer.valueOf(successMessage.perpage)) >= Integer.valueOf(successMessage.count)) {
            addData = false;
        } else {
            ++currentPage;
        }
        mList.addAll(successMessage.list);
        hotFunAdapter.setData(mList);
        hotFunAdapter.notifyDataSetChanged();

    }

    @Override
    public void upDateHotFunList(SearchFriendListBean successMessage) {

    }

    @Override
    public void showError(String errorMessage) {

    }
}
