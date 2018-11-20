package sinia.com.baihangeducation.club.myclub.myparttime;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.LinearLayout;

import com.example.framwork.utils.ProgressActivityUtils;
import com.example.framwork.widget.ProgressActivity;
import com.example.framwork.widget.superrecyclerview.recycleview.SuperRecyclerView;
import com.mcxtzhang.swipemenulib.info.ClubPartTimeListInfo;

import java.util.ArrayList;
import java.util.List;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.club.club.interfaces.GetRequestListener;
import sinia.com.baihangeducation.club.clubpaint.ClubPartTimeAdapter;
import sinia.com.baihangeducation.club.clubpaint.ClubPartTimeMySendAdapter;
import sinia.com.baihangeducation.supplement.base.BaseActivity;

public class ClubMyPartTimeActivity extends BaseActivity implements GetMyPartTiemListener,
        SuperRecyclerView.LoadingListener, SwipeRefreshLayout.OnRefreshListener {

    private boolean pushActivity = false;
    private SuperRecyclerView rvContainer;
    private ProgressActivity progressActivity;
    private SwipeRefreshLayout swipeContainer;
    private ProgressActivityUtils progressActivityUtils;
    private MyClubModel myPartTimeModel;
    private ClubPartTimeMySendAdapter mHomePartTimeAdapter;
    private int countpage = 1;
    private List<ClubPartTimeListInfo.ClubPartInfo> mList = new ArrayList<>();
    private boolean isLoadMore = false;
    private String other_id;

    public int initLayoutResID() {
        return R.layout.activity_club_my_club_new;
    }


    @Override
    protected void initData() {
        Intent intent = getIntent();
        other_id = intent.getStringExtra("other_id");
        myPartTimeModel = new MyClubModel(context);
        myPartTimeModel.getMyClubPartTime(other_id, "1", "20", this);
        mCommonTitle.setBackgroundColor(Color.WHITE);
        mCommonTitle.setCenterText("我发布的");
    }

    @Override
    protected void initView() {
        rvContainer = $(R.id.rv_container);
        progressActivity = $(R.id.progressActivity);
        swipeContainer = $(R.id.swipe_container);

        //首页职业适配器
        mHomePartTimeAdapter = new ClubPartTimeMySendAdapter(ClubMyPartTimeActivity.this, mList);

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
            case R.id.right_txt:
                if (pushActivity) {
//                    Goto.toEditorActive(context, clubid);
                } else {
                    com.example.framwork.utils.Toast.getInstance().showErrorToast(context, "你没有创建权限");
                }

                break;
        }
    }


    @Override
    public void onSuccess(ClubPartTimeListInfo clubPartTimeListInfo, int maxpage) {
        rvContainer.completeLoadMore();
        rvContainer.completeRefresh();
        if (clubPartTimeListInfo.list.size() == 0) {
            progressActivityUtils.showEmptry("暂无数据");
        } else {
            progressActivityUtils.showContent();
            progressActivityUtils.showContent();
            countpage++;
            if (maxpage == 1 || countpage > maxpage) {

                rvContainer.setLoadMoreEnabled(false);
            } else {
                rvContainer.setLoadMoreEnabled(true);
            }
            if (!isLoadMore) {
                mList.clear();
            }
            mList.addAll(clubPartTimeListInfo.list);
            mHomePartTimeAdapter.notifyDataSetChanged();
        }
        hideLoading();
    }

    @Override
    public void onError(String errorMessage) {

    }


    @Override
    public void onRefresh() {
        isLoadMore = false;
        countpage = 1;
        mList.clear();
        hideProgress();
        myPartTimeModel.getMyClubPartTime(other_id, countpage + "", "20", this);
    }

    @Override
    public void onLoadMore() {
        isLoadMore = true;
        hideProgress();
        myPartTimeModel.getMyClubPartTime(other_id, countpage + "", "20", this);
    }


    public void showLoading() {//调用接口时候
        showProgress();
    }

    public void hideLoading() {//得到数据的时候
        hideProgress();
        hideSwipeRefreshLayout(swipeContainer);
        rvContainer.completeLoadMore();
    }


    public void deleteJob(String job_id, GetRequestListener listener) {
        myPartTimeModel.dealWithJob(job_id, new GetRequestListener() {
            @Override
            public void setRequestSuccess(String msg) {
                listener.setRequestSuccess(msg);
            }

            @Override
            public void setRequestFail() {
                listener.setRequestFail();
            }
        });
    }


}
