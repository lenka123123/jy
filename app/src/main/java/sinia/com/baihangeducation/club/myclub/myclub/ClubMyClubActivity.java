package sinia.com.baihangeducation.club.myclub.myclub;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.TextView;

import com.example.framwork.utils.ProgressActivityUtils;
import com.example.framwork.widget.ProgressActivity;
import com.example.framwork.widget.superrecyclerview.recycleview.SuperRecyclerView;
import com.mcxtzhang.swipemenulib.info.ClubPartTimeListInfo;

import java.util.ArrayList;
import java.util.List;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.club.clubpaint.ClubPartTimeAdapter;
import sinia.com.baihangeducation.club.clubschoollist.model.ClubSchoolList;
import sinia.com.baihangeducation.club.myclub.myparttime.GetMyPartTiemListener;
import sinia.com.baihangeducation.club.myclub.myparttime.MyClubModel;
import sinia.com.baihangeducation.supplement.base.BaseActivity;

public class ClubMyClubActivity extends BaseActivity implements
        SuperRecyclerView.LoadingListener, SwipeRefreshLayout.OnRefreshListener, GetMyClubListener {

    private boolean pushActivity = false;
    private SuperRecyclerView rvContainer;
    private ProgressActivity progressActivity;
    private SwipeRefreshLayout swipeContainer;
    private ProgressActivityUtils progressActivityUtils;
    private MyClubModel myPartTimeModel;
    private MyClubAdapter mHomePartTimeAdapter;
    private int countpage = 1;
    private List<MyClubSchoolList.School> mList = new ArrayList<>();
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
        Intent intent = getIntent();
        other_id = intent.getStringExtra("other_id");
        myPartTimeModel = new MyClubModel(context);
        myPartTimeModel.getMyClubSchool(other_id, "1", this);
        mCommonTitle.setBackgroundColor(Color.WHITE);
        mCommonTitle.setCenterText("我的社团");
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

        tab_first.setText("我创建的");
        tab_two.setText("我加入的");

        tab_first.setOnClickListener(this);
        tab_two.setOnClickListener(this);

        //首页职业适配器
        mHomePartTimeAdapter = new MyClubAdapter(context, mList);

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
            case R.id.tab_first:
                // ( 不传默认全部；1：我创建的；2：我加入的 )
                mList.clear();
                left.setVisibility(View.VISIBLE);
                right.setVisibility(View.INVISIBLE);
                tab_first.setTextColor(context.getResources().getColor(R.color.red_fa3e3e));
                tab_two.setTextColor(context.getResources().getColor(R.color.light_black));
                countpage = 1;
                myPartTimeModel.getMyClubSchool(other_id, "1", this);

                break;
            case R.id.tab_two:
                mList.clear();
                left.setVisibility(View.INVISIBLE);
                right.setVisibility(View.VISIBLE);
                tab_first.setTextColor(context.getResources().getColor(R.color.light_black));
                tab_two.setTextColor(context.getResources().getColor(R.color.red_fa3e3e));
                countpage = 1;
                myPartTimeModel.getMyClubSchool(other_id, "2", this);
                break;
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
    public void onSuccess(List<MyClubSchoolList.School> successMessage, int myIndex) {
        System.out.println("是否有数据" + successMessage.size() + "code" + myIndex);

        if (successMessage.size() == 0) {
            progressActivityUtils.showEmptry("目前暂无相关信息哦");
        } else {
            progressActivityUtils.showContent();
            countpage++;
            if (!isLoadMore) {
                mList.clear();
            }
            mList.addAll(successMessage);
            mHomePartTimeAdapter.notifyDataSetChanged();
        }


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
//        myPartTimeModel.getMyClubPartTime(countpage + "", "20", this);
    }

    @Override
    public void onLoadMore() {
        isLoadMore = true;
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

}
