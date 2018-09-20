package sinia.com.baihangeducation.club.searchschool;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.club.searchschool.interfaces.SearchSchoolContract;
import sinia.com.baihangeducation.club.searchschool.model.ClubSchoolList;
import sinia.com.baihangeducation.club.searchschool.model.SearchSchoolModel;
import sinia.com.baihangeducation.club.searchschool.presenter.ClubSchoolListPresenter;
import sinia.com.baihangeducation.club.searchschool.view.SearchSchoolAdapter;
import sinia.com.baihangeducation.supplement.base.BaseActivity;
import sinia.com.baihangeducation.supplement.base.Goto;

public class SearchListActivity extends BaseActivity implements SearchSchoolContract.View {
    private List<ClubSchoolList.School> mRows = new ArrayList<>();

    private String text = "";
    private RefreshLayout mSmartRefreshLayout;
    private RecyclerView mAutoLoadRecyclerView;

    private SearchSchoolAdapter paiedListAdapter;

    private boolean addData = false;
    private int currentPage = 1;
    private int perpage = 20;
    private boolean isCreated = false;
    private ImageView mErrorImageView;
    private TextView mErrorTextView;
    private ClubSchoolListPresenter clubSchoolListPresenter;
    private EditText searchEditText;
    private TextView contact_me;
    private LinearLayout contact_admin;

    public int initLayoutResID() {
        return R.layout.activity_club_search_school;
    }


    @Override
    protected void initView() {
        searchEditText = findViewById(R.id.search_searchhistory_searched);
        mSmartRefreshLayout = findViewById(R.id.refreshLayout);
        mAutoLoadRecyclerView = findViewById(R.id.recyclerView);

        contact_me = findViewById(R.id.contact_me);
        contact_admin = findViewById(R.id.contact_admin);
        mErrorImageView = findViewById(R.id.img_state);
        mErrorTextView = findViewById(R.id.tv_state);

        contact_me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Goto.toLinkUsActivity(context);
            }
        });
        TextView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        paiedListAdapter = new SearchSchoolAdapter(this);
        mAutoLoadRecyclerView.setAdapter(paiedListAdapter);
        setPullRefresher();
        final GridLayoutManager manager = new GridLayoutManager(this, 1);
        mAutoLoadRecyclerView.setLayoutManager(manager);
        mAutoLoadRecyclerView.setItemAnimator(new DefaultItemAnimator());
        addData = false;
//        paiedListAdapter.setData(mRows, 1);

    }

    @Override
    protected void initData() {
//        mCommonTitle.setBackgroundColor(Color.WHITE);
//        mCommonTitle.setCenterText("我的购买");
//        mCommonTitle.getRightTxt().setTextSize(16);
//        mCommonTitle.getRightTxt().setTextColor(Color.BLACK);

        clubSchoolListPresenter = new ClubSchoolListPresenter(new SearchSchoolModel(context), this);
//        clubSchoolListPresenter.getSearchSchoolList("1", String.valueOf(perpage), "");
        text = "";

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                text = s.toString();
                mRows.clear();
                clubSchoolListPresenter.getSearchSchoolList("1", String.valueOf(perpage), s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }


    private void setPullRefresher() {
        mSmartRefreshLayout.setRefreshHeader(new MaterialHeader(this));
        //   mSmartRefreshLayout.setRefreshFooter(new BallPulseFooter(getContext()));
        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                currentPage = 1;
                mRows.clear();
//                clubSchoolListPresenter.getSearchSchoolList("1", String.valueOf(perpage), "");
                refreshlayout.finishRefresh(500);
            }
        });
        mSmartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                //      AllCoffersFragment.this.refreshlayout = refreshlayout;
                if (addData) {
                    clubSchoolListPresenter.getSearchSchoolList(String.valueOf(currentPage), String.valueOf(perpage), "");
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

    @Override
    public void showSearchSchoolList(List<ClubSchoolList.School> successMessage, int maxpage) {

        if (successMessage.size() < 1) {
            contact_admin.setVisibility(View.VISIBLE);
            showErrorState(true);
        } else {
            contact_admin.setVisibility(View.GONE);
            showErrorState(false);
        }
        if (mSmartRefreshLayout != null)
            mSmartRefreshLayout.finishLoadMore(500);
        mRows.addAll(successMessage);


        if (currentPage < maxpage) {
            addData = true;
            currentPage = currentPage + 1;
        } else {
            addData = false;
        }

        paiedListAdapter.setData(mRows, 1, text);

        paiedListAdapter.notifyDataSetChanged();

    }

    @Override
    public void showError(String errorMessage) {

    }
}
