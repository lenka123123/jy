package sinia.com.baihangeducation.club.myclub.help;

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
import sinia.com.baihangeducation.club.clubdetail.interfaces.ClubDetailContract;
import sinia.com.baihangeducation.club.myclub.myclub.GetMyClubListener;
import sinia.com.baihangeducation.club.myclub.myclub.MyClubAdapter;
import sinia.com.baihangeducation.club.myclub.myclub.MyClubSchoolList;
import sinia.com.baihangeducation.club.myclub.myparttime.MyClubModel;
import sinia.com.baihangeducation.supplement.base.BaseActivity;
import sinia.com.baihangeducation.supplement.base.Goto;

public class ClubHelpActivity extends BaseActivity implements
        SuperRecyclerView.LoadingListener, SwipeRefreshLayout.OnRefreshListener, GetMyClubListener, GetHelpListener {

    private boolean pushActivity = false;
    private SuperRecyclerView rvContainer;
    private ProgressActivity progressActivity;
    private SwipeRefreshLayout swipeContainer;
    private ProgressActivityUtils progressActivityUtils;
    private MyClubModel myPartTimeModel;
    private MyHelpAdapter mHomePartTimeAdapter;
    private int countpage = 1;
    private List<MyHelplList.Help> mList = new ArrayList<>();
    private boolean isLoadMore = false;
    private TextView tab_first;
    private TextView tab_two;
    private View left;
    private View right;
    private String other_id;

    public int initLayoutResID() {
        return R.layout.activity_club_my_club;
    }

    @Override
    protected void initData() {
        Intent intent=getIntent();
        other_id = intent.getStringExtra("other_id");

        myPartTimeModel = new MyClubModel(context);
        // 审核状态    ( 1：待审核 2：通过审核 3：审核失败；23：已审核（含通过、不通过） )
        myPartTimeModel.getSupportList(other_id,"1", countpage + "", "20", this);
        mCommonTitle.setBackgroundColor(Color.WHITE);
        mCommonTitle.setCenterText("赞助列表");
        mCommonTitle.setRightText("申请");
        mCommonTitle.getRightTxt().setOnClickListener(this);
    }

    @Override
    protected void initView() {
        rvContainer = $(R.id.rv_container);
        progressActivity = $(R.id.progressActivity);
        swipeContainer = $(R.id.swipe_container);
        tab_first = $(R.id.tab_first);
        tab_two = $(R.id.tab_two);
        left = $(R.id.left);
        right = $(R.id.right);

        tab_first.setText("审核中");
        tab_two.setText("已通过");

        tab_first.setOnClickListener(this);
        tab_two.setOnClickListener(this);

        //首页职业适配器
        mHomePartTimeAdapter = new MyHelpAdapter(context, mList);

        //一下是下来刷新
        initSwipeLayout(swipeContainer, this);
        showSwipeRefreshLayout(swipeContainer);
        progressActivityUtils = new ProgressActivityUtils(context, progressActivity);
        initRecyclerView(rvContainer, mHomePartTimeAdapter, this);
    }

    public String type = "1";

    @Override
    protected void onRestart() {
        super.onRestart();

        if (myPartTimeModel != null) {
            mList.clear();
            myPartTimeModel.getSupportList(other_id,type, "1", "20", this);
        }

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tab_first:
                // ( 不传默认全部；1：我创建的；2：我加入的 )
                mList.clear();

                left.setVisibility(View.VISIBLE);
                right.setVisibility(View.INVISIBLE);
                tab_first.setTextColor(context.getResources().getColor(R.color.red_fa3e3e));
                tab_two.setTextColor(context.getResources().getColor(R.color.light_black));
                // 审核状态    ( 1：待审核 2：通过审核 3：审核失败；23：已审核（含通过、不通过） )
                type = "1";
                countpage = 1;
                myPartTimeModel.getSupportList(other_id,type, countpage + "", "20", this);

                break;
            case R.id.tab_two:
                mList.clear();

                left.setVisibility(View.INVISIBLE);
                right.setVisibility(View.VISIBLE);
                tab_first.setTextColor(context.getResources().getColor(R.color.light_black));
                tab_two.setTextColor(context.getResources().getColor(R.color.red_fa3e3e));
                // 审核状态    ( 1：待审核 2：通过审核 3：审核失败；23：已审核（含通过、不通过） )
                type = "2";
                countpage = 1;
                myPartTimeModel.getSupportList(other_id,type, countpage + "", "20", this);
                break;
            case R.id.right_txt:
                Goto.toApplyHelp(context);
//                if (pushActivity) {
////                    Goto.toEditorActive(context, clubid);
//                } else {
//                    com.example.framwork.utils.Toast.getInstance().showErrorToast(context, "你没有创建权限");
//                }

                break;
        }
    }


    @Override
    public void onSuccess(List<MyClubSchoolList.School> successMessage, int myIndex) {


    }

    @Override
    public void onSuccess(MyHelplList successMessage, int maxpage) {
        if (successMessage.list.size() == 0) {
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
            mList.addAll(successMessage.list);
            mHomePartTimeAdapter.notifyDataSetChanged();
        }
//        if (successMessage.list.size() == 0) {
//            progressActivityUtils.showEmptry("暂无数据");
//        } else {
//            countpage++;
//            if (!isLoadMore) {
//                mList.clear();
//            }
//            mList.addAll(successMessage.list);
//            mHomePartTimeAdapter.notifyDataSetChanged();
//        }
        hideLoading();
    }

    @Override
    public void onError(String errorMessage) {
        rvContainer.completeLoadMore();
        rvContainer.completeRefresh();
    }


    @Override
    public void onRefresh() {
        isLoadMore = false;
        hideLoading();
        mList.clear();
        myPartTimeModel.getSupportList(other_id,type, "1", "20", this);
    }

    @Override
    public void onLoadMore() {
        isLoadMore = true;
        hideLoading();
        myPartTimeModel.getSupportList(other_id,type, countpage + "", "20", this);
    }


    public void showLoading() {//调用接口时候
        hideProgress();
    }

    public void hideLoading() {//得到数据的时候
        hideProgress();
        hideSwipeRefreshLayout(swipeContainer);
        rvContainer.completeLoadMore();
    }

}
