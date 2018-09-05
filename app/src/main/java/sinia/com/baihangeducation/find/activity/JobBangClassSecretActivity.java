package sinia.com.baihangeducation.find.activity;

import android.graphics.Color;
import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.framwork.utils.ProgressActivityUtils;
import com.example.framwork.widget.ProgressActivity;
import com.example.framwork.widget.superrecyclerview.recycleview.SuperRecyclerView;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.supplement.base.BaseActivity;
import sinia.com.baihangeducation.find.adapter.JobBangClassSecondAdapter;

import com.mcxtzhang.swipemenulib.info.JobBangClassSecondListInfo;
import com.mcxtzhang.swipemenulib.info.JobBangClassSecondRadioListInfo;
import com.mcxtzhang.swipemenulib.info.bean.CateInfo;
import com.mcxtzhang.swipemenulib.info.bean.JobBangClassSecondInfo;
import com.mcxtzhang.swipemenulib.info.bean.MoneyInfo;
import com.mcxtzhang.swipemenulib.info.bean.OrderInfo;

import sinia.com.baihangeducation.find.presenter.JobBangClassInfoPresenter;
import sinia.com.baihangeducation.find.view.JobBangClassInfoView;

/**
 * 就业邦学堂 攻略干货
 */

public class JobBangClassSecretActivity extends BaseActivity implements JobBangClassInfoView, SuperRecyclerView.LoadingListener, SwipeRefreshLayout.OnRefreshListener, RadioGroup.OnCheckedChangeListener {
    private JobBangClassInfoPresenter mJobBangClassInfoPresenter;

    //    private CheckBox screening;                     //筛选
    private LinearLayout ll;
    private final static String type = "1";

    private TagFlowLayout order;                //排序选择器
    private TagFlowLayout cate;                 //分类选择器
    private TagFlowLayout price;                //价格选择器
    private String cadeId;                      //筛选选中的分类ID
    private String moneyId;                     //筛选选中的价格ID
    private String orderId;                     //筛选选中的排序ID
    private String isHot = "2";                       //标签栏热门tag
    private String isFree = "2";                       //标签栏热门tag
    private String isChoice = "2";                    //标签栏精选tag

    private RadioButton mAllTag;                //标签栏全部
    private RadioButton mHotTag;                //标签栏热门
    private RadioButton mChoiceTag;             //标签栏精选

    public List<CateInfo> cateList;             //筛选中分类的item数据
    public List<MoneyInfo> moneyList;           //筛选中价格的item数据
    public List<OrderInfo> orderList;           //筛选中排序的item数据
    private TagAdapter<CateInfo> mCateAdapter;  //分类选择器的数据适配器
    private TagAdapter<MoneyInfo> mMoneyAdapter;//价格选择器的数据适配器
    private TagAdapter<OrderInfo> mOrderAdapter;//排序选择器的数据适配器

    private int countpage = 1;
    private int itemnum = 20;


    private SuperRecyclerView rvContainer;
    private ProgressActivity progressActivity;
    private SwipeRefreshLayout swipeContainer;
    private ProgressActivityUtils progressActivityUtils;
    private JobBangClassSecondAdapter mJobBangClassSecondAdapter;
    private List<JobBangClassSecondInfo> mList;
    private boolean isLoadMore = false;
    private RadioGroup radioGroup;
    private LinearLayout linearLayout;
    private View popView;

    @Override
    public int initLayoutResID() {
        return R.layout.jobbangclass_strategy;
    }

    @Override
    protected void initData() {
        mList = new ArrayList<>();
        cateList = new ArrayList<>();
        moneyList = new ArrayList<>();
        orderList = new ArrayList<>();

        mCommonTitle.setCenterText(R.string.secret);
        mCommonTitle.setBackgroundColor(Color.WHITE);
        mCommonTitle.getRightTxt().setText("筛选");
        mCommonTitle.getRightTxt().setTextSize(16);
        mCommonTitle.getRightTxt().setTextColor(Color.BLACK);
        mCommonTitle.getRightTxt().setOnClickListener(this);

        mJobBangClassInfoPresenter = new JobBangClassInfoPresenter(context, this);
        mJobBangClassInfoPresenter.getJobBangClassInfo();
        mJobBangClassInfoPresenter.gerRadioInfo();

        mJobBangClassSecondAdapter = new JobBangClassSecondAdapter(context, mList,"职场秘籍");
        initSwipeLayout(swipeContainer, this);
        showSwipeRefreshLayout(swipeContainer);
        progressActivityUtils = new ProgressActivityUtils(context, progressActivity);
        initRecyclerView(rvContainer, mJobBangClassSecondAdapter, this);
    }

    @Override
    protected void initView() {
        ll = $(R.id.ll);
        rvContainer = $(R.id.rv_container);
        progressActivity = $(R.id.progressActivity);
        swipeContainer = $(R.id.swipe_container);
        linearLayout = $(R.id.jobbangclass_strategy_parttime);
        mAllTag = $(R.id.fragment_mine_collection_all);
        mHotTag = $(R.id.fragment_mine_collection_hot);
        mChoiceTag = $(R.id.fragment_mine_collection_best);

        popView = $(R.id.popLayoutid);

        mAllTag.setOnClickListener(this);
        mHotTag.setOnClickListener(this);
        mChoiceTag.setOnClickListener(this);

        radioGroup = (RadioGroup) findViewById(R.id.main_tab_radiogroup);
        radioGroup.setOnCheckedChangeListener(this);// 当然也可以使用匿名内部类实现

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.right_txt:
                addPop();
                break;
        }
    }
    private void addPop() {

        WindowManager wm1 = this.getWindowManager();
        int width1 = wm1.getDefaultDisplay().getWidth();
        int height1 = wm1.getDefaultDisplay().getHeight();
        View view = LayoutInflater.from(this).inflate(R.layout.jobbangclassshaixuanmenu, null);
        final PopupWindow pop = new PopupWindow(view, width1 * 6 / 10, height1 * 8 / 10, true);
        pop.setBackgroundDrawable(getResources().getDrawable(android.R.color.transparent));
        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                darkenBackground(1f);
            }
        });

        view.findViewById(R.id.relativelayout).setBackgroundColor(Color.WHITE);
        order = view.findViewById(R.id.jobbangclassshaixuanmenu_order);
        cate = view.findViewById(R.id.jobbangclassshaixuanmenu_cate);
        price = view.findViewById(R.id.jobbangclassshaixuanmenu_price);

        TextView reBuild = view.findViewById(R.id.jobbangclassshaixuanmenu_rebuilder);
        TextView confirm = view.findViewById(R.id.jobbangclassshaixuanmenu_confirm);
        setTagFlowLayout();
        setTagFlowChoiceLayoutId();

        reBuild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadeId = null;
                moneyId = null;
                order = null;
                mCateAdapter.setSelectedList();
                mMoneyAdapter.setSelectedList();
                mOrderAdapter.setSelectedList();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRefresh();
                pop.dismiss();
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            pop.showAsDropDown(popView, 60, 0, Gravity.RIGHT);
            darkenBackground(0.5f);
        }
    }
    /**
     * 改变背景颜色
     */
    private void darkenBackground(Float bgcolor) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgcolor;

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setAttributes(lp);

    }
    /**
     * 获取选中ID
     */
    private void setTagFlowChoiceLayoutId() {
        order.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                orderId = orderList.get(position).order_id + "";
                return false;
            }
        });
        cate.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                cadeId = cateList.get(position).cate_id + "";
                return false;
            }
        });
        price.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                moneyId = moneyList.get(position).money_id + "";
                return false;
            }
        });
    }

    /**
     * 给flowlayout加载数据
     */
    private void setTagFlowLayout() {
        order.setAdapter(mOrderAdapter = new TagAdapter(orderList) {
            @Override
            public View getView(FlowLayout parent, int position, Object o) {
                TextView textView = (TextView) LayoutInflater.from(context).inflate(R.layout.shaixuanitem, order, false);
                textView.setText(orderList.get(position).order_name);
                return textView;
            }
        });
        cate.setAdapter(mCateAdapter = new TagAdapter(cateList) {
            @Override
            public View getView(FlowLayout parent, int position, Object o) {
                TextView textView = (TextView) LayoutInflater.from(context).inflate(R.layout.shaixuanitem, cate, false);
                textView.setText(cateList.get(position).cate_name);
                return textView;
            }
        });
        price.setAdapter(mMoneyAdapter = new TagAdapter(moneyList) {
            @Override
            public View getView(FlowLayout parent, int position, Object o) {
                TextView textView = (TextView) LayoutInflater.from(context).inflate(R.layout.shaixuanitem, price, false);
                textView.setText(moneyList.get(position).money_name);
                return textView;
            }
        });
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
    public String getType() {
        return type;
    }

    @Override
    public String getRadioType() {
        return type;
    }

    @Override
    public String getCadeId() {
        return cadeId;
    }

    @Override
    public String getMoneyId() {
        return moneyId;
    }

    @Override
    public String getOrderId() {
        return orderId;
    }

    @Override
    public String getIsHot() {
        return isHot;
    }

    @Override
    public String getIsFree() {
        return isFree;
    }

    @Override
    public String getIsChoice() {
        return isChoice;
    }

    @Override
    public String getPage() {
        return countpage + "";
    }

    @Override
    public String getPerpage() {
        return itemnum + "";
    }

    @Override
    public void getJobBangClassInfoSuccess(JobBangClassSecondListInfo mJobBangClassSecondListInfo, int maxpage) {
        if (mJobBangClassSecondListInfo.list.size() == 0) {
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
            mList.addAll(mJobBangClassSecondListInfo.list);
            mJobBangClassSecondAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void getJobBangClassRadioInfoSuccess(JobBangClassSecondRadioListInfo mJobBangClassSecondRadioListInfo) {
        if (mJobBangClassSecondRadioListInfo.cate_list != null && mJobBangClassSecondRadioListInfo.cate_list.size() > 0) {
            cateList = mJobBangClassSecondRadioListInfo.cate_list;
        }
        if (mJobBangClassSecondRadioListInfo.money_list != null && mJobBangClassSecondRadioListInfo.money_list.size() > 0) {
            moneyList = mJobBangClassSecondRadioListInfo.money_list;
        }
        if (mJobBangClassSecondRadioListInfo.order_list != null && mJobBangClassSecondRadioListInfo.order_list.size() > 0) {
            orderList = mJobBangClassSecondRadioListInfo.order_list;
        }
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
        countpage = 1;
        mList.clear();
        mJobBangClassSecondAdapter.notifyDataSetChanged();
        mJobBangClassInfoPresenter.getJobBangClassInfo();
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        mList.clear();
        mJobBangClassSecondAdapter.notifyDataSetChanged();

        switch (checkedId) {
//            mAllTag = $(R.id.fragment_mine_collection_all);
//            mHotTag = $(R.id.fragment_mine_collection_hot);
//            mChoiceTag = $(R.id.fragment_mine_collection_best);
//            mChoiceFreeTag = $(R.id.fragment_mine_collection_free);

            case R.id.fragment_mine_collection_all:
                isChoice = "";
                isFree = "";
                isHot = "";
                break;
            case R.id.fragment_mine_collection_hot:
                isChoice = "";
                isFree = "";
                isHot = "1";
                break;
            case R.id.fragment_mine_collection_best:
                isChoice = "1";
                isFree = "";
                isHot = "";
                break;
            case R.id.fragment_mine_collection_free:
                isChoice = "";
                isFree = "1";
                isHot = "";
                break;


        }
        cadeId = null;
        moneyId = null;
        order = null;
        getServerData();
    }
}
