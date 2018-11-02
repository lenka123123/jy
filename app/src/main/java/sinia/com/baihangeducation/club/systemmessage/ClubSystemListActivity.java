package sinia.com.baihangeducation.club.systemmessage;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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
import sinia.com.baihangeducation.club.ClubPermissModel;
import sinia.com.baihangeducation.club.club.interfaces.GetRequestListener;
import sinia.com.baihangeducation.club.clubschoollist.interfaces.ClubSchoolListContract;
import sinia.com.baihangeducation.club.clubschoollist.model.ClubSchoolList;
import sinia.com.baihangeducation.club.clubschoollist.model.ClubSchoolListModel;
import sinia.com.baihangeducation.club.clubschoollist.presenter.ClubSchoolListPresenter;
import sinia.com.baihangeducation.club.clubschoollist.view.ClubSchoolListAdapter;
import sinia.com.baihangeducation.supplement.base.BaseActivity;
import sinia.com.baihangeducation.supplement.base.Goto;

public class ClubSystemListActivity extends BaseActivity implements ClubSchoolListContract.View, GetSystemMessageRequestListener {
    private List<ClubMessageList.SystemMessage> mRows = new ArrayList<>();

    private String text = "";
    private RefreshLayout mSmartRefreshLayout;
    private RecyclerView mAutoLoadRecyclerView;

    private ClubSystemMeaageListAdapter paiedListAdapter;

    private boolean addData = false;
    private int currentPage = 1;
    private int perpage = 20;
    private boolean isCreated = false;
    private ImageView mErrorImageView;
    private TextView mErrorTextView;
    private ClubSystemModel clubSystemModel;
    private ClubPermissModel clubPermissModel;

    public int initLayoutResID() {
        return R.layout.activity_club_school_list;
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        mRows.clear();
    }

    @Override
    protected void initView() {

        mSmartRefreshLayout = findViewById(R.id.refreshLayout);
        mAutoLoadRecyclerView = findViewById(R.id.recyclerView);
        TextView fragment_home_adressName = findViewById(R.id.fragment_home_adressName);
        fragment_home_adressName.setText("系统提醒");

        mErrorImageView = findViewById(R.id.img_state);
        mErrorTextView = findViewById(R.id.tv_state);
        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        RelativeLayout search = findViewById(R.id.home_search_layout);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Goto.toClubSearchActivity(context);
            }
        });
        paiedListAdapter = new ClubSystemMeaageListAdapter(this);
        mAutoLoadRecyclerView.setAdapter(paiedListAdapter);
        setPullRefresher();
        final GridLayoutManager manager = new GridLayoutManager(this, 1);
        mAutoLoadRecyclerView.setLayoutManager(manager);
        mAutoLoadRecyclerView.setItemAnimator(new DefaultItemAnimator());
        addData = false;
    }

    @Override
    protected void initData() {
        clubSystemModel = new ClubSystemModel(context);
        clubSystemModel.getSystemMsgList(this);
        clubPermissModel = new ClubPermissModel(context);
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
//                    clubSchoolListPresenter.getSearchSchoolList(String.valueOf(currentPage), String.valueOf(perpage), "");
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

    }

    @Override
    public void setRequestSuccess(ClubMessageList successMessage, int maxpage) {
        System.out.println("===========" + "keywordkeyword" + successMessage.list.size());

        if (successMessage.list.size() < 1) {
            showErrorState(true);
        } else {
            showErrorState(false);
        }
        if (mSmartRefreshLayout != null)
            mSmartRefreshLayout.finishLoadMore(500);
        mRows.addAll(successMessage.list);
        paiedListAdapter.setData(mRows, currentPage);
        if (!text.equals("")) {
            paiedListAdapter.setChangeText(text);
        }
        if (currentPage < maxpage) {
            addData = true;
            currentPage = currentPage + 1;
        } else {
            addData = false;
        }
        paiedListAdapter.notifyDataSetChanged();

    }

    @Override
    public void setRequestFail() {

    }


    @Override
    public void showError(String errorMessage) {

    }

    public void setPower(String check_type, String category_id, String system_message_id, String is_pass, GetRequestListener getRequestListener) {
        clubSystemModel.setApplyCheck(check_type, category_id, system_message_id, is_pass, new GetRequestListener() {
            @Override
            public void setRequestSuccess(String msg) {
                getRequestListener.setRequestSuccess(msg);
            }

            @Override
            public void setRequestFail() {
                getRequestListener.setRequestFail();
            }
        });
    }

    public void setReaded(String id) {
        clubPermissModel.setMessageRead(id);
    }
}
