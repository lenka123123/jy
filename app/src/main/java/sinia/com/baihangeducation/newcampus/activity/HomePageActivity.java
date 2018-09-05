package sinia.com.baihangeducation.newcampus.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.bumptech.glide.Glide;
import com.donkingliang.imageselector.entry.Image;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.newcampus.info.FunContantInfo;
import sinia.com.baihangeducation.newcampus.info.FunInfo;
import sinia.com.baihangeducation.newcampus.info.HomeInfoShowBean;
import sinia.com.baihangeducation.newcampus.info.HomeInfoShowTobBean;
import sinia.com.baihangeducation.newcampus.info.HomePagerDetail;
import sinia.com.baihangeducation.newcampus.interfaces.HomeDeatilPagerListener;
import sinia.com.baihangeducation.newcampus.interfaces.HomePagerListener;
import sinia.com.baihangeducation.newcampus.presenter.HomePagerPresenter;
import sinia.com.baihangeducation.newcampus.tabs.fans.view.FansListActivity;
import sinia.com.baihangeducation.newcampus.tabs.fans.view.FollowListActivity;
import sinia.com.baihangeducation.newcampus.tabs.fun.FunCampusFragment;
import sinia.com.baihangeducation.newcampus.tabs.fun.adapter.NewCampusFunAdapter;
import sinia.com.baihangeducation.newcampus.tabs.searchfriend.interfaces.FollowDataListener;
import sinia.com.baihangeducation.newcampus.view.IGetFunView;
import sinia.com.baihangeducation.newcampus.view.IIsShowInput;
import sinia.com.baihangeducation.supplement.base.BaseActivity;
import sinia.com.baihangeducation.supplement.base.CommonModel;

public class HomePageActivity extends BaseActivity implements IGetFunView, IIsShowInput {
    private TextView mErrorTextView;
    private ImageView mErrorImageView;
    private RecyclerView mSuperRecyclerView;
    private RefreshLayout mRefreshLayout;
    private LinearLayout mLayout;

    private String dynamic_id;
    private String user_id;
//        info.put("user_id", "7");
//        info.put("other_id", "0");

    private int mCurrentPage = 1;
    private final int mItemNumber = 10;
    public List<FunContantInfo> mList;
    private boolean mAddData = false;
    private NewCampusFunAdapter mNewCampusFunAdapter;
    private View header;
    private TextView name;
    private TextView dynamic_number;
    private TextView attention_number;
    private TextView fans_number;
    private TextView addfollow;
    private ImageView user_photo;
    private HomePagerPresenter pagerPresenter;
    private ImageView back;

    public int initLayoutResID() {
        return R.layout.activity_mine_campus;
    }

    public void initView() {
        StatService.start(this);
        Intent intent = getIntent();
        dynamic_id = intent.getStringExtra("dynamic_id");
        user_id = intent.getStringExtra("user_id");
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
            header = LayoutInflater.from(context).inflate(R.layout.compus_head_view, null);

        name = header.findViewById(R.id.name);
        dynamic_number = header.findViewById(R.id.dynamic_number);
        attention_number = header.findViewById(R.id.attention_number);
        fans_number = header.findViewById(R.id.fans_number);
        addfollow = header.findViewById(R.id.addfollow);
        user_photo = header.findViewById(R.id.user_photo);

        header.findViewById(R.id.linearLayout_dynamic).setOnClickListener(this);
        header.findViewById(R.id.linearLayout_attention).setOnClickListener(this);
        header.findViewById(R.id.linearLayout_fans).setOnClickListener(this);


        back = header.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomePageActivity.this.finish();
            }
        });

        mNewCampusFunAdapter.addHeaderView(header);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.linearLayout_dynamic: //动态
                break;
            case R.id.linearLayout_attention: // 关注
                Intent myFollowIntent = new Intent(context, FollowListActivity.class);
                AppConfig.OTHERID = user_id;
                myFollowIntent.putExtra("user_id", user_id);
                startActivity(myFollowIntent);
                break;
            case R.id.linearLayout_fans:
                Intent myIntent = new Intent(context, FansListActivity.class);
                AppConfig.OTHERID = user_id;
                myIntent.putExtra("user_id", user_id);
                startActivity(myIntent);
                break;
        }


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
                //      AllCoffersFragment.this.refreshlayout = refreshlayout;
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
        pagerPresenter.releaseComment(user_id, mCurrentPage, "20", new HomeDeatilPagerListener() {
            @Override
            public void setHomePagerSuccessListener(HomeInfoShowBean homePager) {
                final HomeInfoShowBean.UserInfoDetail userInfoDetail = homePager.info;
                if (userInfoDetail != null) {
                    user_id = userInfoDetail.user_id;
                    name.setText(userInfoDetail.nickname);
                    dynamic_number.setText(userInfoDetail.article_num);
                    attention_number.setText(userInfoDetail.follow);
                    fans_number.setText(userInfoDetail.fans);
                    Glide.with(context).load(userInfoDetail.avatar).error(R.drawable.logo).into(user_photo);
                    if (userInfoDetail.avatar == null || userInfoDetail.avatar.equals("")) {

                    } else {
//                        Glide.with(context).load(userInfoDetail.avatar).error(R.drawable.logo).into(imageView);
                    }

                    if (userInfoDetail.is_follow.equals("3")) {
                        addfollow.setVisibility(View.INVISIBLE);
                    } else if (userInfoDetail.is_follow.equals("2")) {
                        addfollow.setBackgroundResource(R.drawable.add_follow_ed);
                        addfollow.setText("");
                    } else {
                        addfollow.setBackgroundResource(R.drawable.attention_add);
                        addfollow.setText("+关注");
                        addfollow.setTextColor(Color.WHITE);
                        addfollow.setBackgroundColor(Color.RED);
                    }
                    addfollow.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            CommonModel commonModel = new CommonModel(context);
                            commonModel.addFollow(String.valueOf(userInfoDetail.user_id), new FollowDataListener() {
                                @Override
                                public void getHotFunDataSuccess(int flag) {
                                    ///   com.example.framwork.utils.Toast.getInstance().showSuccessToast(context, "关注成功");
                                    if (flag == 2) {
                                        addfollow.setBackgroundResource(R.drawable.add_follow_ed);
                                        addfollow.setText("");
                                    } else {
                                        addfollow.setBackgroundResource(R.drawable.attention_add);
                                        addfollow.setText("+关注");
                                        addfollow.setTextColor(Color.WHITE);
                                        addfollow.setBackgroundColor(Color.RED);
                                    }


                                }

                                @Override
                                public void getHotFunDataFail() {

                                }
                            });
                        }
                    });


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


                System.out.println("ddddddddd" + homePager.info.avatar);


//                Glide.with(HomePageActivity.this).load(homePager.info.publish_user_avatar).skipMemoryCache(false).thumbnail(0.3f)
//                        .error(R.drawable.logo).crossFade().into(iconImageView);
//                Glide.with(HomePageActivity.this).load(homePager.info.publish_user_avatar).into(iconImageView);

                //  recyclerView.
                //     AdapterUtils.setNineGrid(HomePageActivity.this, homePager.info.thumbnail_list, homePager.info.images_list, recyclerView);

            }

            @Override
            public void setHomePagerTobSuccessListener(HomeInfoShowTobBean homePager) {

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
