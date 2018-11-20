package sinia.com.baihangeducation.club.myparttimeapplylist;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.TextView;

import com.example.framwork.utils.ProgressActivityUtils;
import com.example.framwork.widget.ProgressActivity;
import com.example.framwork.widget.superrecyclerview.recycleview.SuperRecyclerView;

import java.util.ArrayList;
import java.util.List;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.club.club.interfaces.GetRequestListener;
import sinia.com.baihangeducation.club.clubactive.model.ActiveListData;
import sinia.com.baihangeducation.club.myclub.myactive.GetMyActiveListener;
import sinia.com.baihangeducation.club.myclub.myactive.MyActiveAdapter;
import sinia.com.baihangeducation.club.myclub.myparttime.MyClubModel;
import sinia.com.baihangeducation.supplement.base.BaseActivity;

public class ClubMyCreatePartTimeActivity extends BaseActivity implements
        SuperRecyclerView.LoadingListener, SwipeRefreshLayout.OnRefreshListener, GetApplyPersonListener {

    private boolean pushActivity = false;
    private SuperRecyclerView rvContainer;
    private ProgressActivity progressActivity;
    private SwipeRefreshLayout swipeContainer;
    private ProgressActivityUtils progressActivityUtils;
    private MyClubModel myPartTimeModel;
    private MyCreatePartTimeAdapter mHomePartTimeAdapter;
    private int countpage = 1;
    private List<ClubCreateData.ApplyPerson> mList = new ArrayList<>();
    private boolean isLoadMore = false;
    private TextView tab_will;
    private TextView tab_pass;
    private TextView tab_cancel;
    private View left;
    private View center;
    private View right;
    private String type = "1";
    private String job_id;

    public int initLayoutResID() {
        return R.layout.activity_club_my_create_parttime;
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        job_id = intent.getStringExtra("job_id");
        myPartTimeModel = new MyClubModel(context);
        myPartTimeModel.getMyCreatePartTimeApplyList(job_id, "1", 1, "20", this);
        mCommonTitle.setBackgroundColor(Color.WHITE);
        mCommonTitle.setCenterText("申请列表");
    }

    @Override
    protected void initView() {
        rvContainer = $(R.id.rv_container);
        progressActivity = $(R.id.progressActivity);
        swipeContainer = $(R.id.swipe_container);
        tab_will = $(R.id.tab_will);
        tab_pass = $(R.id.tab_pass);
        tab_cancel = $(R.id.tab_cancel);
        left = $(R.id.left);
        center = $(R.id.center);
        right = $(R.id.right);

        tab_will.setOnClickListener(this);
        tab_pass.setOnClickListener(this);
        tab_cancel.setOnClickListener(this);

        //首页职业适配器
        mHomePartTimeAdapter = new MyCreatePartTimeAdapter(ClubMyCreatePartTimeActivity.this, mList);
        mHomePartTimeAdapter.setType(type);
        //一下是下来刷新
        initSwipeLayout(swipeContainer, this);
        showSwipeRefreshLayout(swipeContainer);
        progressActivityUtils = new ProgressActivityUtils(context, progressActivity);
        initRecyclerView(rvContainer, mHomePartTimeAdapter, this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tab_will:
                //  1:我创建的 2:我发布的
                mList.clear();
                tab_will.setTextColor(context.getResources().getColor(R.color.red_fa3e3e));
                tab_pass.setTextColor(context.getResources().getColor(R.color.light_black));
                tab_cancel.setTextColor(context.getResources().getColor(R.color.light_black));
                left.setVisibility(View.VISIBLE);
                center.setVisibility(View.INVISIBLE);
                right.setVisibility(View.INVISIBLE);
                type = "1";
                countpage = 1;
                myPartTimeModel.getMyCreatePartTimeApplyList(job_id, type, countpage, "20", this);

                break;
            case R.id.tab_pass:
                mList.clear();
                tab_will.setTextColor(context.getResources().getColor(R.color.light_black));
                tab_pass.setTextColor(context.getResources().getColor(R.color.red_fa3e3e));
                tab_cancel.setTextColor(context.getResources().getColor(R.color.light_black));
                left.setVisibility(View.INVISIBLE);
                center.setVisibility(View.VISIBLE);
                right.setVisibility(View.INVISIBLE);
                type = "2";
                countpage = 1;
                myPartTimeModel.getMyCreatePartTimeApplyList(job_id, type, countpage, "20", this);
                break;

            case R.id.tab_cancel:
                mList.clear();
                tab_will.setTextColor(context.getResources().getColor(R.color.light_black));
                tab_pass.setTextColor(context.getResources().getColor(R.color.light_black));
                tab_cancel.setTextColor(context.getResources().getColor(R.color.red_fa3e3e));
                left.setVisibility(View.INVISIBLE);
                center.setVisibility(View.INVISIBLE);
                right.setVisibility(View.VISIBLE);
                type = "3";
                countpage = 1;
                myPartTimeModel.getMyCreatePartTimeApplyList(job_id, type, countpage, "20", this);
                break;
        }
    }

    @Override
    public void onSuccess(Object successMessage, int myIndex) {
        if (successMessage instanceof ClubCreateData) {
            ClubCreateData clubCreateData = (ClubCreateData) successMessage;
            if (clubCreateData.list.size() == 0) {
                progressActivityUtils.showEmptry("暂无数据");
            } else {
                progressActivityUtils.showContent();
                countpage++;
                if (!isLoadMore) {
                    mList.clear();
                }

                mHomePartTimeAdapter.setType(type);
                mList.addAll(clubCreateData.list);
                mHomePartTimeAdapter.notifyDataSetChanged();
            }
            hideLoading();
        }
    }

//    @Override
//    public void onSuccess(List<MyClubSchoolList.School> successMessage, int myIndex) {
//        rvContainer.completeLoadMore();
//        rvContainer.completeRefresh();
//        rvContainer.setLoadMoreEnabled(false);
//        rvContainer.setRefreshEnabled(false);
//
//        if (successMessage.size() == 0) {
//            progressActivityUtils.showEmptry("暂无数据");
//        } else {
//            countpage++;
//            if (!isLoadMore) {
//                mList.clear();
//            }
//            mList.addAll(successMessage);
//            mHomePartTimeAdapter.notifyDataSetChanged();
//        }
//        hideLoading();
//    }

    @Override
    public void onError(String errorMessage) {
        rvContainer.completeLoadMore();
        rvContainer.completeRefresh();
    }


    @Override
    public void onRefresh() {
        isLoadMore = false;
        hideLoading();
//        myPartTimeModel.getMyClubPartTime(countpage + "", "20", this);
    }

    @Override
    public void onLoadMore() {
        isLoadMore = true;
        myPartTimeModel.getMyCreatePartTimeApplyList(job_id, type, countpage, "20", this);
        hideLoading();
//        myPartTimeModel.getMyClubPartTime(countpage + "", "20", this);
    }


    public void showLoading() {//调用接口时候
        hideProgress();
    }

    public void hideLoading() {//得到数据的时候
        hideProgress();
        hideSwipeRefreshLayout(swipeContainer);
        rvContainer.completeLoadMore();
    }

    public void dealWithApply(String apply_id, String is_pass, GetRequestListener listener) {
        myPartTimeModel.dealWithApply(apply_id, is_pass, new GetRequestListener() {
            @Override
            public void setRequestSuccess(String msg) {
//                myPartTimeModel.getMyCreatePartTimeApplyList(job_id, type, "1", "20", ClubMyCreatePartTimeActivity.this);
                listener.setRequestSuccess(msg);
            }

            @Override
            public void setRequestFail() {
                listener.setRequestFail();
            }
        });
    }

}
