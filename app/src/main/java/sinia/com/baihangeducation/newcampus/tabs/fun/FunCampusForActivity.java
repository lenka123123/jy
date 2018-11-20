package sinia.com.baihangeducation.newcampus.tabs.fun;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mcxtzhang.swipemenulib.customview.BackEditText;
import com.mcxtzhang.swipemenulib.customview.GlideLoadUtils;
import com.mcxtzhang.swipemenulib.customview.fragment.FragmentLazy;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.newcampus.info.FunContantInfo;
import sinia.com.baihangeducation.newcampus.info.FunInfo;
import sinia.com.baihangeducation.newcampus.tabs.fun.adapter.NewCampusFunAdapter;
import sinia.com.baihangeducation.newcampus.tabs.fun.model.FunCampusModel;
import sinia.com.baihangeducation.newcampus.tabs.fun.presenter.FunCampusPresenter;
import sinia.com.baihangeducation.newcampus.tabs.fun.presenter.FunCampusPresenterForActivity;
import sinia.com.baihangeducation.newcampus.view.IGetFunView;
import sinia.com.baihangeducation.newcampus.view.IIsShowInput;
import sinia.com.baihangeducation.release.campus.tabs.heatfundetal.HotCommentPageActivity;
import sinia.com.baihangeducation.release.campus.tabs.heatfunlist.bean.HotFunListBean;
import sinia.com.baihangeducation.release.campus.tabs.heatfunlist.interfaces.HotFunDataListener;
import sinia.com.baihangeducation.release.campus.tabs.heatfunlist.model.HotFunListModel;
import sinia.com.baihangeducation.supplement.base.BaseActivity;
import sinia.com.baihangeducation.supplement.base.Goto;

public class FunCampusForActivity extends BaseActivity implements IGetFunView, IIsShowInput, View.OnClickListener {

    private BackEditText mInput;
    private LinearLayout mLayout;
    private TextView mErrorTextView;
    private ImageView mErrorImageView;
    private RecyclerView mSuperRecyclerView;
    private RefreshLayout mRefreshLayout;

    private int mCurrentPage = 1;
    private final int mItemNumber = 10;
    public List<FunContantInfo> mList = new ArrayList<>();
    private boolean mAddData = false;

    private FunCampusPresenterForActivity mFunCampusPresenter;
    private NewCampusFunAdapter mNewCampusFunAdapter;
    private ImageView imageViewOne;
    private ImageView imageViewTwo;
    private ImageView imageViewThree;
    private ImageView imageViewFour;
    private TextView textViewOne;
    private TextView textViewTwo;
    private TextView textViewThree;
    private TextView textViewFour;

    private HotFunListBean hotFunListBean;
    private Intent intent;
    private HotFunListModel hotFunListModel;

    private boolean isCreated = false;
    private LinearLayout mHeadLinearLayout;
    private LinearLayout linearLayout_one;
    private LinearLayout linearLayout_two;
    private LinearLayout linearLayout_three;
    private LinearLayout linearLayout_four;
    private TextView releasemoment_release;
    private LinearLayout releasemoment_back;


    @Override
    public int initLayoutResID() {
        return R.layout.fragment_fun_campus;
    }

//    @Override
//    protected View initViews(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstanceState) {
//        mRoot = layoutInflater.inflate(R.layout.fragment_fun_campus, viewGroup, false);
//        initView();
//        isCreated = true;
//        //   fetchData();
//        return mRoot;
//    }


    private void fetchData() {
        mFunCampusPresenter.getFunCampusList();
        getData();

    }

    public void initView() {
        releasemoment_release = findViewById(R.id.releasemoment_release);
        releasemoment_back = findViewById(R.id.releasemoment_back);
        mSuperRecyclerView = findViewById(R.id.recyclerView);
        mRefreshLayout = findViewById(R.id.refreshLayout);
        mLayout = findViewById(R.id.newcampany);
        mInput = findViewById(R.id.newcampany_input);
        mErrorImageView = findViewById(R.id.img_state);
        mErrorTextView = findViewById(R.id.tv_state);

        releasemoment_release.setOnClickListener(this);
        releasemoment_back.setOnClickListener(this);
        mSuperRecyclerView.setLayoutManager(new GridLayoutManager(context, 1));
        mNewCampusFunAdapter = new NewCampusFunAdapter(context, mList, mLayout, this, true);
        mSuperRecyclerView.setAdapter(mNewCampusFunAdapter);


        mFunCampusPresenter = new FunCampusPresenterForActivity(new FunCampusModel(context), FunCampusForActivity.this);
        hotFunListModel = new HotFunListModel(context);

        setPullRefresher();
        addHeaderView();
    }


    private void addHeaderView() {
        View header = LayoutInflater.from(context).inflate(R.layout.activity_recyclerview_head, null);
        mHeadLinearLayout = header.findViewById(R.id.newcampany);
        imageViewOne = header.findViewById(R.id.img_one);
        imageViewTwo = header.findViewById(R.id.img_two);
        imageViewThree = header.findViewById(R.id.img_three);
        imageViewFour = header.findViewById(R.id.img_four);

        textViewOne = header.findViewById(R.id.title_one);
        textViewTwo = header.findViewById(R.id.title_two);
        textViewThree = header.findViewById(R.id.title_three);
        textViewFour = header.findViewById(R.id.title_four);

        linearLayout_one = header.findViewById(R.id.linearLayout_one);
        linearLayout_two = header.findViewById(R.id.linearLayout_two);
        linearLayout_three = header.findViewById(R.id.linearLayout_three);
        linearLayout_four = header.findViewById(R.id.linearLayout_four);
        linearLayout_one.setOnClickListener(this);
        linearLayout_two.setOnClickListener(this);
        linearLayout_three.setOnClickListener(this);
        linearLayout_four.setOnClickListener(this);

        mNewCampusFunAdapter.addHeaderView(header);
        intent = new Intent(context, HotCommentPageActivity.class);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.releasemoment_back:
                finish();
                break;

            case R.id.releasemoment_release:
                Goto.toReleaseFunActivity(context);
                break;

            case R.id.linearLayout_one:
                intent.putExtra("topic_id", hotFunListBean.list.get(0).topic_id);
                intent.putExtra("topic_logo", hotFunListBean.list.get(0).topic_logo);
                intent.putExtra("topic_comment_num", hotFunListBean.list.get(0).topic_comment_num);
                intent.putExtra("topic_look_num", hotFunListBean.list.get(0).topic_look_num);
                intent.putExtra("topic_title", hotFunListBean.list.get(0).topic_title);
                intent.putExtra("topic_introduce", hotFunListBean.list.get(0).topic_introduce);
                startActivity(intent);
                break;
            case R.id.linearLayout_two:
                intent.putExtra("topic_id", hotFunListBean.list.get(1).topic_id);
                intent.putExtra("topic_logo", hotFunListBean.list.get(1).topic_logo);
                intent.putExtra("topic_comment_num", hotFunListBean.list.get(1).topic_comment_num);
                intent.putExtra("topic_look_num", hotFunListBean.list.get(1).topic_look_num);
                intent.putExtra("topic_title", hotFunListBean.list.get(1).topic_title);
                intent.putExtra("topic_introduce", hotFunListBean.list.get(1).topic_introduce);
                startActivity(intent);

                break;
            case R.id.linearLayout_three:
                intent.putExtra("topic_id", hotFunListBean.list.get(2).topic_id);
                intent.putExtra("topic_logo", hotFunListBean.list.get(2).topic_logo);
                intent.putExtra("topic_comment_num", hotFunListBean.list.get(2).topic_comment_num);
                intent.putExtra("topic_look_num", hotFunListBean.list.get(2).topic_look_num);
                intent.putExtra("topic_title", hotFunListBean.list.get(2).topic_title);
                intent.putExtra("topic_introduce", hotFunListBean.list.get(2).topic_introduce);
                startActivity(intent);
                break;
            case R.id.linearLayout_four:
                intent.putExtra("topic_id", hotFunListBean.list.get(3).topic_id);
                intent.putExtra("topic_logo", hotFunListBean.list.get(3).topic_logo);
                intent.putExtra("topic_comment_num", hotFunListBean.list.get(3).topic_comment_num);
                intent.putExtra("topic_look_num", hotFunListBean.list.get(3).topic_look_num);
                intent.putExtra("topic_title", hotFunListBean.list.get(3).topic_title);
                intent.putExtra("topic_introduce", hotFunListBean.list.get(3).topic_introduce);
                startActivity(intent);
                break;
        }
    }


//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        if (mNewCampusFunAdapter != null)
//            mNewCampusFunAdapter.removeAllHeaderView();
//        mNewCampusFunAdapter = null;
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mNewCampusFunAdapter != null)
            mNewCampusFunAdapter.removeAllHeaderView();
        mNewCampusFunAdapter = null;

    }

    public void getData() {

        hotFunListModel.getHotFunList(1, 1, new HotFunDataListener() {
            @Override
            public void getHotFunDataSuccess(HotFunListBean hotFunListBean) {
                FunCampusForActivity.this.hotFunListBean = hotFunListBean;
                if (hotFunListBean != null && hotFunListBean.list != null && hotFunListBean.list.size() >= 1) {
                    linearLayout_one.setVisibility(View.GONE);
                    linearLayout_two.setVisibility(View.GONE);
                    linearLayout_three.setVisibility(View.GONE);
                    linearLayout_four.setVisibility(View.GONE);
                    if (hotFunListBean.list.size() >= 1) {
                        linearLayout_one.setVisibility(View.VISIBLE);
                    }
                    if (hotFunListBean.list.size() >= 2) {
                        linearLayout_two.setVisibility(View.VISIBLE);
                    }
                    if (hotFunListBean.list.size() >= 3) {
                        linearLayout_three.setVisibility(View.VISIBLE);
                    }
                    if (hotFunListBean.list.size() >= 4) {
                        linearLayout_four.setVisibility(View.VISIBLE);
                    }

                    GlideLoadUtils.getInstance().glideLoad(context, hotFunListBean.list.get(0).topic_logo, imageViewOne, R.drawable.logo);
                    GlideLoadUtils.getInstance().glideLoad(context, hotFunListBean.list.get(1).topic_logo, imageViewTwo, R.drawable.logo);
                    GlideLoadUtils.getInstance().glideLoad(context, hotFunListBean.list.get(2).topic_logo, imageViewThree, R.drawable.logo);
                    GlideLoadUtils.getInstance().glideLoad(context, hotFunListBean.list.get(3).topic_logo, imageViewFour, R.drawable.logo);

//                    Glide.with(context).load(hotFunListBean.list.get(0).topic_logo).error(R.drawable.logo).into(imageViewOne);
//                    Glide.with(context).load(hotFunListBean.list.get(1).topic_logo).error(R.drawable.logo).into(imageViewTwo);
//                    Glide.with(context).load(hotFunListBean.list.get(2).topic_logo).error(R.drawable.logo).into(imageViewThree);
//                    Glide.with(context).load(hotFunListBean.list.get(3).topic_logo).error(R.drawable.logo).into(imageViewFour);
                    textViewOne.setText(hotFunListBean.list.get(0).topic_introduce);
                    textViewTwo.setText(hotFunListBean.list.get(1).topic_introduce);
                    textViewThree.setText(hotFunListBean.list.get(2).topic_introduce);
                    textViewFour.setText(hotFunListBean.list.get(3).topic_introduce);

                } else {
                    mHeadLinearLayout.setVisibility(View.GONE);
                }


            }

            @Override
            public void getHotFunDataFail() {

            }
        });
    }


    @Override
    protected void initData() {
        fetchData();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

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
        //"count":"49","page":"1","perpage":"10"
        if (maxpage < 1) {
            showErrorState(true);
        }
        if (mItemNumber * mCurrentPage < info.count) {
            mAddData = true;
            mCurrentPage = mCurrentPage + 1;
        } else {
            mAddData = false;
        }
        if (info.list.size() >= 1)
            mList.addAll(info.list);

        mNewCampusFunAdapter.notifyDataSetChanged();
    }

    private void setPullRefresher() {
        mRefreshLayout.setRefreshHeader(new MaterialHeader(context));
        mRefreshLayout.setRefreshFooter(new BallPulseFooter(context));
        //下拉刷新事件f
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                if (mAddData) {
                    mCurrentPage = 1;
                    mList.clear();
                    mNewCampusFunAdapter.notifyDataSetChanged();
                    fetchData();
                    System.out.println("============onRefresh=");
                } else {
                    mRefreshLayout.setNoMoreData(false);
                }
                refreshlayout.finishRefresh(2000);
            }
        });
        // 处理加载更多
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                //      DayRankingFragment.this.refreshlayout = refreshlayout;
                if (mAddData) {
                    System.out.println("============getFunCampusList=");
                    mFunCampusPresenter.getFunCampusList();
                } else {
                    mRefreshLayout.finishLoadMoreWithNoMoreData();
                }
                refreshlayout.finishLoadMore(2000);
            }
        });
    }

    @Override
    public void IsShowInput(boolean flag, FunContantInfo item) {
        if (flag) {
            mInput.setVisibility(View.VISIBLE);
            mInput.setFocusable(true);
            mInput.setFocusableInTouchMode(true);
            mInput.requestFocus();
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    InputMethodManager imm = (InputMethodManager) mInput.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(mInput, InputMethodManager.SHOW_FORCED);
                }
            }, 300);

        } else {
            mInput.setVisibility(View.INVISIBLE);
            hideSoftInput(mInput);
        }
    }

    private void showErrorState(boolean isError) {
        if (isError) {
            mErrorImageView.setVisibility(View.VISIBLE);
            mErrorTextView.setVisibility(View.VISIBLE);
            Glide.with(context).load(R.mipmap.ic_launcher).into(mErrorImageView);
            mErrorTextView.setText("没有数据");
            if (mRefreshLayout != null)
                mRefreshLayout.finishLoadMoreWithNoMoreData();
        } else {
            mErrorImageView.setVisibility(View.GONE);
            mErrorTextView.setVisibility(View.GONE);
        }
    }

    /**
     * 隐藏软键盘
     */
    protected void hideSoftInput(EditText view) {
        InputMethodManager mInputMethodManager = (InputMethodManager) context.getSystemService(context.INPUT_METHOD_SERVICE);
        mInputMethodManager
                .hideSoftInputFromWindow(view.getWindowToken(), 0);
    }



}
