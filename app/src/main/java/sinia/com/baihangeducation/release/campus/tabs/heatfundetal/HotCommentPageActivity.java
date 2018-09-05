package sinia.com.baihangeducation.release.campus.tabs.heatfundetal;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.newcampus.info.FunContantInfo;
import sinia.com.baihangeducation.newcampus.info.FunInfo;
import sinia.com.baihangeducation.newcampus.info.HomeInfoShowBean;
import sinia.com.baihangeducation.newcampus.info.HomeInfoShowTobBean;
import sinia.com.baihangeducation.newcampus.interfaces.HomeDeatilPagerListener;
import sinia.com.baihangeducation.newcampus.presenter.HomePagerPresenter;
import sinia.com.baihangeducation.newcampus.tabs.fun.adapter.NewCampusFunAdapter;
import sinia.com.baihangeducation.newcampus.tabs.searchfriend.interfaces.AddFollowDataListener;
import sinia.com.baihangeducation.newcampus.view.IGetFunView;
import sinia.com.baihangeducation.newcampus.view.IIsShowInput;
import sinia.com.baihangeducation.supplement.base.BaseActivity;
import sinia.com.baihangeducation.supplement.base.CommonModel;

public class HotCommentPageActivity extends BaseActivity implements IGetFunView, IIsShowInput {

    private TextView mErrorTextView;
    private ImageView mErrorImageView;
    private RecyclerView mSuperRecyclerView;
    private RefreshLayout mRefreshLayout;
    private LinearLayout mLayout;
    private String topic_id;
//        info.put("user_id", "7");
//        info.put("other_id", "0");

    private int mCurrentPage = 1;
    private final int mItemNumber = 10;
    public List<FunContantInfo> mList;
    private boolean mAddData = false;
    private NewCampusFunAdapter mNewCampusFunAdapter;
    private View header;
    private TextView name;
    private TextView follw;
    private TextView content;
    private HomePagerPresenter pagerPresenter;
    private ImageView back;
    private String topic_logo;
    private String topic_comment_num;
    private String topic_look_num;
    private String topic_title;
    private String topic_introduce;
    private ImageView myImg;

    public int initLayoutResID() {
        return R.layout.activity_mine_campus;
    }

    public void initView() {
        Intent intent = getIntent();
        topic_id = intent.getStringExtra("topic_id");
        topic_logo = intent.getStringExtra("topic_logo");
        topic_comment_num = intent.getStringExtra("topic_comment_num");
        topic_look_num = intent.getStringExtra("topic_look_num");
        topic_title = intent.getStringExtra("topic_title");
        topic_introduce = intent.getStringExtra("topic_introduce");

        mLayout = findViewById(R.id.newcampany);
        mSuperRecyclerView = findViewById(R.id.recyclerView);
        mRefreshLayout = findViewById(R.id.refreshLayout);
        mErrorImageView = findViewById(R.id.img_state);
        mErrorTextView = findViewById(R.id.tv_state);
    }

    public void initData() {
        mList = new ArrayList<>();
        mNewCampusFunAdapter = new NewCampusFunAdapter(context, mList, mLayout, this, false);
        mSuperRecyclerView.setAdapter(mNewCampusFunAdapter);
        mSuperRecyclerView.setLayoutManager(new GridLayoutManager(context, 1));
        setPullRefresher();
        pagerPresenter = new HomePagerPresenter(this);
        getData();
        addHeaderView();

    }

    public void addHeaderView() {
        if (header == null)
            header = LayoutInflater.from(context).inflate(R.layout.hot_comment_pager_item, null);

        name = header.findViewById(R.id.name);
        follw = header.findViewById(R.id.follw);
        content = header.findViewById(R.id.content);

        myImg = header.findViewById(R.id.my_photo);

        back = header.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HotCommentPageActivity.this.finish();
            }
        });

        mNewCampusFunAdapter.addHeaderView(header);
    }

    private void setPullRefresher() {
        mRefreshLayout.setRefreshHeader(new MaterialHeader(context));
        //   mSmartRefreshLayout.setRefreshFooter(new BallPulseFooter(getContext()));
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                if (mAddData) {
                    getData();    // TODO: 2018/7/29 0029     mFunCampusPresenter.getFunCampusList();
                } else {
                    mRefreshLayout.setNoMoreData(false);
                }
                refreshlayout.finishRefresh(2000);
            }
        });
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                //      DayRankingFragment.this.refreshlayout = refreshlayout;
                if (mAddData) {
                    getData();   // TODO: 2018/7/29 0029     mFunCampusPresenter.getFunCampusList();
                } else {
                    mRefreshLayout.finishLoadMoreWithNoMoreData();
                }
                refreshlayout.finishLoadMore(2000);
            }
        });
    }


    private void getData() {
        pagerPresenter.releaseHotComment(topic_id, mCurrentPage, "20", new HomeDeatilPagerListener() {
            @Override
            public void setHomePagerSuccessListener(HomeInfoShowBean homePager) {

            }

            @Override
            public void setHomePagerTobSuccessListener(HomeInfoShowTobBean homePager) {
                FunContantInfo list = homePager.hot_dynamic;

                List<FunContantInfo> userInfoDetail = homePager.dynamic_list.list;
                userInfoDetail.add(0, list);
                if (userInfoDetail != null) {

//                    Glide.with(context).load(topic_logo).error(R.drawable.logo).into(imageView);
                    Glide.with(context).load(topic_logo).error(R.drawable.logo).into(myImg);

                    name.setText(topic_title);
                    follw.setText(topic_look_num + "阅读  " + topic_comment_num + "讨论");
                    content.setText(topic_introduce);


//                    if (userInfoDetail.avatar == null || userInfoDetail.avatar.equals("")) {
//
//                    } else {
//                        Glide.with(context).load(userInfoDetail.avatar).error(R.drawable.logo).into(imageView);
//                    }

                }


                if (Integer.valueOf(homePager.dynamic_list.count) < 1) {
                    //  showErrorState(true);
                }
                if (mItemNumber * mCurrentPage < Integer.valueOf(homePager.dynamic_list.count)) {
                    mAddData = true;
                    mCurrentPage = mCurrentPage + 1;
                } else {
                    mAddData = false;
                }
                mList.addAll(homePager.dynamic_list.list);
                mNewCampusFunAdapter.notifyDataSetChanged();


                System.out.println("ddddddddd" + homePager.dynamic_list.list.size());


//                Glide.with(HomePageActivity.this).load(homePager.info.publish_user_avatar).skipMemoryCache(false).thumbnail(0.3f)
//                        .error(R.drawable.logo).crossFade().into(iconImageView);
//                Glide.with(HomePageActivity.this).load(homePager.info.publish_user_avatar).into(iconImageView);

                //  recyclerView.
                //     AdapterUtils.setNineGrid(HomePageActivity.this, homePager.info.thumbnail_list, homePager.info.images_list, recyclerView);

            }


            @Override
            public void homePagerFailListener() {

            }
        });

    }


    @Override
    public String getFunPage() {
        return String.valueOf(mCurrentPage);
    }

    @Override
    public String getFunPerpage() {
        return String.valueOf(mItemNumber);
    }

    @Override
    public void getFunInfoSuccess(FunInfo info, int maxpage) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void IsShowInput(boolean flag, FunContantInfo item) {

    }
}
