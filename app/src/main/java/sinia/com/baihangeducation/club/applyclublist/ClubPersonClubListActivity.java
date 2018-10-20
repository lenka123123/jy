package sinia.com.baihangeducation.club.applyclublist;

import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.club.applyclublist.interfaces.ApplyClubListContract;
import sinia.com.baihangeducation.club.applyclublist.model.ApplyClubListBean;
import sinia.com.baihangeducation.club.applyclublist.model.ApplyClubListModel;
import sinia.com.baihangeducation.club.applyclublist.model.GetPersonList;
import sinia.com.baihangeducation.club.applyclublist.presenter.ApplyClubListPresenter;
import sinia.com.baihangeducation.club.applyclublist.view.ApplyClubListAdapter;
import sinia.com.baihangeducation.club.applyclublist.view.GetPeronListAdapter;
import sinia.com.baihangeducation.supplement.base.BaseActivity;

public class ClubPersonClubListActivity extends BaseActivity implements ApplyClubListContract.View {
    private List<GetPersonList.PersonList> mRows = new ArrayList<>();

    private String text = "";
    private RefreshLayout mSmartRefreshLayout;
    private RecyclerView mAutoLoadRecyclerView;

    private GetPeronListAdapter paiedListAdapter;

    private boolean addData = false;
    private int currentPage = 1;
    private int perpage = 20;
    private boolean isCreated = false;
    private ImageView mErrorImageView;
    private TextView mErrorTextView;
    private ApplyClubListPresenter clubSchoolListPresenter;
    private TextView textView;

    public int initLayoutResID() {
        return R.layout.activity_club_apply;
    }


    @Override
    protected void initView() {
        mSmartRefreshLayout = findViewById(R.id.refreshLayout);
        mAutoLoadRecyclerView = findViewById(R.id.recyclerView);
        textView = findViewById(R.id.fragment_home_adressName);
        textView.setText("成员列表");
        mErrorImageView = findViewById(R.id.img_state);
        mErrorTextView = findViewById(R.id.tv_state);
        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        paiedListAdapter = new GetPeronListAdapter(this);
        mAutoLoadRecyclerView.setAdapter(paiedListAdapter);
        setPullRefresher();
        final GridLayoutManager manager = new GridLayoutManager(this, 1);
        mAutoLoadRecyclerView.setLayoutManager(manager);
        mAutoLoadRecyclerView.setItemAnimator(new DefaultItemAnimator());
        addData = false;
        paiedListAdapter.setData(mRows, currentPage);

    }

    @Override
    protected void initData() {
//        mCommonTitle.setBackgroundColor(Color.WHITE);
//        mCommonTitle.setCenterText("我的购买");
//        mCommonTitle.getRightTxt().setTextSize(16);
//        mCommonTitle.getRightTxt().setTextColor(Color.BLACK);

        Intent intent = getIntent();
        String club_id = intent.getStringExtra("club_id");

        clubSchoolListPresenter = new ApplyClubListPresenter(new ApplyClubListModel(context), this);
        clubSchoolListPresenter.getPersonList(club_id, "1", String.valueOf(perpage));
        text = "";


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
    public void showApplyClubList(ApplyClubListBean successMessage, int maxpage) {


    }


    @Override
    public void showError(String errorMessage) {

    }

    @Override
    public void showApplySuccess(String errorMessage) {

    }

    @Override
    public void showGetPersonListSuccess(GetPersonList getPersonList, int max) {

        if (getPersonList.list.size() < 1) {
            showErrorState(true);
        } else {
            showErrorState(false);
        }
        if (mSmartRefreshLayout != null)
            mSmartRefreshLayout.finishLoadMore(500);
        mRows.addAll(getPersonList.list);

        paiedListAdapter.setData(mRows, currentPage);


        if (!text.equals("")) {
            paiedListAdapter.setChangeText(text);
        }

        if (currentPage < max) {
            addData = true;
            currentPage = currentPage + 1;
        } else {
            addData = false;
        }

        paiedListAdapter.notifyDataSetChanged();

    }
}
