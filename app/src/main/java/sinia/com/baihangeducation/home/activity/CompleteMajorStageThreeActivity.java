package sinia.com.baihangeducation.home.activity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;

import com.example.framwork.utils.ProgressActivityUtils;
import com.example.framwork.widget.ProgressActivity;
import com.example.framwork.widget.superrecyclerview.recycleview.SuperRecyclerView;

import java.util.ArrayList;
import java.util.List;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.supplement.base.BaseActivity;
import sinia.com.baihangeducation.home.adapter.CompleteMajorNumberTwoAdapter;
import com.mcxtzhang.swipemenulib.info.bean.College;
import com.mcxtzhang.swipemenulib.info.bean.EducationInfo;
import com.mcxtzhang.swipemenulib.info.bean.Marjor;
import sinia.com.baihangeducation.mine.presenter.GetEditEducationInfoPresenter;
import sinia.com.baihangeducation.mine.view.IGetEducationView;

/**
 * Created by Administrator on 2018/3/30.
 */
public class CompleteMajorStageThreeActivity extends BaseActivity implements IGetEducationView, SuperRecyclerView.LoadingListener, SwipeRefreshLayout.OnRefreshListener {
    private int countpage = 1;
    private int itemnum = 20;


    private GetEditEducationInfoPresenter presenter;

    private ProgressActivityUtils progressActivityUtils;

    private SuperRecyclerView rvContainer;
    private ProgressActivity progressActivity;
    private SwipeRefreshLayout swipeContainer;

    private List<Marjor> marjorList;
    private CompleteMajorNumberTwoAdapter choiceMajor_2Adapter;
    private boolean isLoadMore = false;

    private Intent intent;
    private String marjor;

    @Override
    public int initLayoutResID() {
        return R.layout.myresumeediteducationexpchoice;
    }

    @Override
    protected void initView() {
        intent = getIntent();
        marjor = intent.getStringExtra("MAJORID");

        mCommonTitle.setCenterText(R.string.choicmajor);
        mCommonTitle.setBackground(getResources().getDrawable(R.color.white));

        presenter = new GetEditEducationInfoPresenter(context, this);

        rvContainer = $(R.id.rv_container);
        progressActivity = $(R.id.progressActivity);
        swipeContainer = $(R.id.swipe_container);
        getServerData();
    }

    @Override
    protected void initData() {
        marjorList = new ArrayList<>();
        presenter.getMajor();
        choiceMajor_2Adapter = new CompleteMajorNumberTwoAdapter(context, marjorList);

        initSwipeLayout(swipeContainer, this);
        showSwipeRefreshLayout(swipeContainer);
        progressActivityUtils = new ProgressActivityUtils(context, progressActivity);
        initRecyclerView(rvContainer, choiceMajor_2Adapter, this);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {
        hideSwipeRefreshLayout(swipeContainer);
        rvContainer.completeLoadMore();
    }

    @Override
    public String getPage() {
        return countpage + "";
    }

    @Override
    public String getItenmNum() {
        return itemnum + "";
    }

    @Override
    public String getFatherId() {
        return marjor;
    }

    @Override
    public void getEducationSchoolSuccess(List<College> list, int maxpage) {

    }

    @Override
    public void getEducationMajor_1_Success(List<Marjor> list, int maxpage) {
        if (list.size() == 0) {
            progressActivityUtils.showEmptry("暂无数据");
        } else {
            progressActivityUtils.showContent();
            countpage++;
            if (maxpage == 1 || countpage > maxpage) {
                rvContainer.setLoadMoreEnabled(false);
            } else {
                rvContainer.setLoadMoreEnabled(true);
            }
            if (!isLoadMore) {
                marjorList.clear();
            }
            marjorList.addAll(list);
            choiceMajor_2Adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void getEducationMajor_2_Success(List<Marjor> list, int maxpage) {

    }

    @Override
    public void getEducationSuccess(List<EducationInfo> educationInfos) {

    }


    @Override
    public void onRefresh() {
        isLoadMore = false;
        countpage = 1;
        getServerData();
    }

    @Override
    public void onLoadMore() {
        isLoadMore = true;
        getServerData();
    }

    private void getServerData() {
        presenter.getMajor();
    }
}
