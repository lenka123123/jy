package sinia.com.baihangeducation.minecompany.avtivity;

import android.support.v4.widget.SwipeRefreshLayout;

import com.example.framwork.utils.ProgressActivityUtils;
import com.example.framwork.utils.Toast;
import com.example.framwork.widget.ProgressActivity;
import com.example.framwork.widget.superrecyclerview.recycleview.SuperRecyclerView;

import java.util.ArrayList;
import java.util.List;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.supplement.base.BaseActivity;
import sinia.com.baihangeducation.minecompany.adapter.CompanyUCenterReleaseJobAdapter;
import sinia.com.baihangeducation.minecompany.info.CompanyUCenterReleaseTrainingInfo;
import com.mcxtzhang.swipemenulib.info.bean.CompanyReleaseItemInfo;
import com.mcxtzhang.swipemenulib.info.bean.CompanyReleaseJobInfo;
import sinia.com.baihangeducation.minecompany.presenter.CompanyUCenterReleaseJobPresenter;
import sinia.com.baihangeducation.minecompany.view.ICompanyUCenterReleaseJobView;
import sinia.com.baihangeducation.minecompany.view.INotifyRefreshActivityView;
import sinia.com.baihangeducation.supplement.base.Goto;
import com.mcxtzhang.swipemenulib.utils.Constants;

public class CompanyUCenterReleaseAllTimeActivity extends BaseActivity implements INotifyRefreshActivityView, ICompanyUCenterReleaseJobView, SuperRecyclerView.LoadingListener, SwipeRefreshLayout.OnRefreshListener {
    private CompanyUCenterReleaseJobPresenter presenter;
    private int countpage = 1;
    private int itemnum = 10;
    private String mJobId;


    private SuperRecyclerView rvContainer;
    private ProgressActivity progressActivity;
    private SwipeRefreshLayout swipeContainer;
    private ProgressActivityUtils progressActivityUtils;
    private CompanyUCenterReleaseJobAdapter mCompanyUCenterReleaseJobAdapter;
    private List<CompanyReleaseItemInfo> mList;
    private boolean isLoadMore = false;

    @Override
    public int initLayoutResID() {
        return R.layout.fragment_mine_company_myreleaseparttime;
    }

    @Override
    protected void initData() {
        mList = new ArrayList<>();
        mCommonTitle.setCenterText(R.string.companyUCenteralltime);
        mCommonTitle.setBackground(getResources().getDrawable(R.color.white));
        presenter = new CompanyUCenterReleaseJobPresenter(context, this);
        getServerData();

        mCompanyUCenterReleaseJobAdapter = new CompanyUCenterReleaseJobAdapter(context, mList, this);

        initSwipeLayout(swipeContainer, this);
        showSwipeRefreshLayout(swipeContainer);
        progressActivityUtils = new ProgressActivityUtils(context, progressActivity);
        initRecyclerView(rvContainer, mCompanyUCenterReleaseJobAdapter, this);

    }

    @Override
    protected void initView() {
        rvContainer = $(R.id.rv_container);
        progressActivity = $(R.id.progressActivity);
        swipeContainer = $(R.id.swipe_container);
    }

    @Override
    public String getCompanyUCenterReleaseJobTypy() {
        return Constants.COMPANY_RELEASEJOB_ALLTIME + "";
    }

    @Override
    public String getCompanyUCenterReleaseJobPage() {
        return countpage + "";
    }

    @Override
    public String getCompanyUCenterReleaseJobPerPage() {
        return itemnum + "";
    }

    @Override
    public String getReleaseJobId() {
        return mJobId;
    }

    @Override
    public String getReleaseStatus() {
        return null;
    }

    @Override
    public void getStatusChangeSuccess(int status) {
        onRefresh();
        switch (status){
            case 1:
                Toast.getInstance().showSuccessToast(context,"上架成功");
                break;
            case 2:
                Toast.getInstance().showSuccessToast(context,"下架成功");
                break;
            case 3:
                Toast.getInstance().showSuccessToast(context,"删除成功");
                break;
        }
    }

    @Override
    public void getCompanyUCenterReleaseJobSuccess(CompanyReleaseJobInfo info, int maxpage) {
        if (info.list.size() == 0) {
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
                mList.clear();
            }
            mList.addAll(info.list);
            mCompanyUCenterReleaseJobAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void getCompanyUcenterReleaseTrianSuccess(CompanyUCenterReleaseTrainingInfo trainingInfo, int maxpage) {

    }

    @Override
    public void showLoading() {
        showProgress();
    }

    @Override
    public void hideLoading() {
        hideProgress();
        hideSwipeRefreshLayout(swipeContainer);
        rvContainer.completeLoadMore();
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

    /**
     * 获取数据
     */
    private void getServerData() {
        presenter.getCompanyReleaseJobData();
    }

    /**
     * 跳转编辑页面
     */
    @Override
    public void go2EditActivity(int jobId) {
        mJobId = jobId+"";
        Goto.toCompanyEditJobActivity(context, jobId);
    }

    /**
     * 下架
     */
    @Override
    public void downJob(int jobId) {
        mJobId = jobId+"";
        presenter.upReleaseJobData(2);
    }

    /**
     * 上架
     */
    @Override
    public void upJob(int jobId) {
        mJobId = jobId+"";
        presenter.upReleaseJobData(1);
    }

    /**
     * 删除
     */
    @Override
    public void detelJob(int jobId) {
        mJobId = jobId+"";
        presenter.upReleaseJobData(3);
    }
}
