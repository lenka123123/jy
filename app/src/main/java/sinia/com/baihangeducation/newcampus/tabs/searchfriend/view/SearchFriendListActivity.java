package sinia.com.baihangeducation.newcampus.tabs.searchfriend.view;

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
import android.widget.Toast;

import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.newcampus.tabs.searchfriend.adapter.SearchFriendAdapter;
import sinia.com.baihangeducation.newcampus.tabs.searchfriend.bean.SearchFriendListBean;
import sinia.com.baihangeducation.newcampus.tabs.searchfriend.interfaces.SearchFriendListContract;
import sinia.com.baihangeducation.newcampus.tabs.searchfriend.model.SearchFriendListModel;
import sinia.com.baihangeducation.newcampus.tabs.searchfriend.present.SearchFriendListPresent;
import sinia.com.baihangeducation.supplement.base.BaseActivity;
import sinia.com.baihangeducation.supplement.base.Goto;

public class SearchFriendListActivity extends BaseActivity implements SearchFriendListContract.View {

    private RecyclerView mAutoLoadRecyclerView;
    private SearchFriendListPresent mHotFunListPresent;

    private SearchFriendAdapter hotFunAdapter;
    private List<SearchFriendListBean.Firendes> mList = new ArrayList<>();
    private EditText mSearchEditText;
    private RelativeLayout mRelativeLayout;

    private RefreshLayout mSmartRefreshLayout;
    private ImageView mErrorImageView;
    private TextView mErrorTextView;
    private boolean addData = true;
    private int currentPage = 1;

    @Override
    public int initLayoutResID() {
        return R.layout.search_friend_list_activity;
    }

    @Override
    protected void initData() {
        mHotFunListPresent = new SearchFriendListPresent(new SearchFriendListModel(context), this);
        currentPage = 1;
        mHotFunListPresent.getHotFunList(0, currentPage, "");
        setPullRefresher();
        hotFunAdapter.setItemClickListener(new SearchFriendAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                Goto.toHomePageAgain(mList.get(position).user_id, context);

//                Intent intent = new Intent();
//                intent.putExtra("topic_title", mList.get(position).topic_title);
//                intent.putExtra("topic_id", mList.get(position).topic_id);
//                setResult(1000, intent);
//                SearchFunSearchListActivity.this.finish();
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

                mList.clear();
                hotFunAdapter.setData(mList);
                hotFunAdapter.notifyDataSetChanged();
                mHotFunListPresent.getHotFunList(0, 0, s.toString());


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
        mRelativeLayout = findViewById(R.id.search);
        hotFunAdapter = new SearchFriendAdapter(context);
        mAutoLoadRecyclerView.setAdapter(hotFunAdapter);
        final GridLayoutManager manager = new GridLayoutManager(context, 1);
        mAutoLoadRecyclerView.setLayoutManager(manager);
        mAutoLoadRecyclerView.setItemAnimator(new DefaultItemAnimator());
        hotFunAdapter.setData(mList);
        mRelativeLayout.setOnClickListener(this);

    }


    private void setPullRefresher() {
        mSmartRefreshLayout.setRefreshHeader(new MaterialHeader(context));
        //   mSmartRefreshLayout.setRefreshFooter(new BallPulseFooter(getContext()));
        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                if (addData) {
                    mHotFunListPresent.getHotFunList(0, currentPage, "");
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
                    mHotFunListPresent.getHotFunList(0, currentPage, "");
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
