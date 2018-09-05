package sinia.com.baihangeducation.find.activity;

import android.graphics.Color;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.framwork.banner.SimpleImageBanner;
import com.example.framwork.utils.DensityUtil;

import java.util.ArrayList;
import java.util.List;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.supplement.base.BaseActivity;
import sinia.com.baihangeducation.MyApplication;
import sinia.com.baihangeducation.find.adapter.JobBangClassSecretAdapter;
import sinia.com.baihangeducation.find.adapter.JobBangClassStrategyAdapter;
import sinia.com.baihangeducation.find.adapter.JobBangClassTestAdapter;

import com.mcxtzhang.swipemenulib.info.JobBangClassListInfo;
import com.mcxtzhang.swipemenulib.info.bean.JobBangClassADListInfo;
import com.mcxtzhang.swipemenulib.info.bean.JobBangClassSecretListInfo;
import com.mcxtzhang.swipemenulib.info.bean.JobBangClassStrategyListInfo;
import com.mcxtzhang.swipemenulib.info.bean.JobBangClassTestListInfo;

import sinia.com.baihangeducation.find.presenter.JobBangClassPresenter;
import sinia.com.baihangeducation.find.view.JobBangClassView;

import com.mcxtzhang.swipemenulib.info.bean.CommonInfo;

import sinia.com.baihangeducation.supplement.base.Goto;
import sinia.com.baihangeducation.supplement.advertisement.ADDataProvider;

import com.mcxtzhang.swipemenulib.customview.NoScrollListView;

/**
 * Created by Administrator on 2018/4/20.
 */

public class JobBangClassActivity extends BaseActivity implements JobBangClassView {

    private LinearLayout mStrategyTitle;                                //攻略标题栏
    private LinearLayout mSecretTitle;                                  //秘籍标题栏
    private LinearLayout mTestTitle;                                    //试卷标题栏
//    private NoScrollListView mStrategyListView;                     //攻略ListView
    private NoScrollListView mSecretListView;                       //秘籍ListView
    private NoScrollListView mTestListView;                         //书卷ListView
    private SimpleImageBanner sibTopAd;                             //广告栏

    private int itemADWidth;                                        //设置广告的宽
    private int itemADHeight;                                       //设置广告的高

    private JobBangClassPresenter mJobBangClassPresenter;

    private List<JobBangClassADListInfo> adList;                    //广告数据
    private List<JobBangClassSecretListInfo> strategyList;        //攻略数据
    private List<JobBangClassSecretListInfo> secretList;            //秘籍数据
    private List<JobBangClassTestListInfo> testList;                //试卷数据

    private JobBangClassSecretAdapter mJobBangClassSecretAdapter;
    private JobBangClassTestAdapter mJobBangClassTestAdapter;



    @Override
    public int initLayoutResID() {
        return R.layout.jobbangclass;
    }

    @Override
    protected void initData() {
        adList = new ArrayList<>();
        strategyList = new ArrayList<>();
        secretList = new ArrayList<>();
        testList = new ArrayList<>();


        mCommonTitle.setCenterText(R.string.jobbangclass);
        mCommonTitle.setBackgroundColor(Color.WHITE);

//        if (((commentInfo.ad_switch.find).trim()).equals("open")) {
//            sibTopAd.setVisibility(View.VISIBLE);
//        } else {
//            sibTopAd.setVisibility(View.GONE);
//        }

        mJobBangClassPresenter = new JobBangClassPresenter(context, this);
        mJobBangClassPresenter.getJobBangClassData();

        //获取屏幕宽度
        itemADWidth = DensityUtil.getScreenWidth(context);
        //设置高度数据为屏幕宽度/1.9
        itemADHeight = (int) (itemADWidth / 1.9);
        //设置广告栏宽高属性
        sibTopAd.setLayoutParams(new FrameLayout.LayoutParams(itemADWidth, itemADHeight));
        ADDataProvider.initAdvertisement(sibTopAd, adList, itemADHeight, itemADWidth);
        adClickEvent();
    }

    /**
     * 广告栏点击事件
     */
    private void adClickEvent() {
        sibTopAd.setOnItemClickL(new SimpleImageBanner.OnItemClickL() {
            @Override
            public void onItemClick(int position) {

            }
        });
    }

    @Override
    protected void initView() {
        mStrategyTitle = $(R.id.jobbangclass_strategy_title);
        mSecretTitle = $(R.id.jobbangclass_jobsecret_title);
        mTestTitle = $(R.id.jobbangclass_test_title);

        mSecretListView = $(R.id.jobbangclass_jobsecret_list);
        mTestListView = $(R.id.jobbangclass_test_list);

        sibTopAd = $(R.id.sib_top_ad);

        mStrategyTitle.setOnClickListener(this);
        mSecretTitle.setOnClickListener(this);
        mTestTitle.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.jobbangclass_strategy_title:
                Goto.toJobBangClassStrategyActivity(context);
                break;
            case R.id.jobbangclass_jobsecret_title:
                Goto.toJobBangClassSecretActivity(context);
                break;
            case R.id.jobbangclass_test_title:
                Goto.toJobBangClassTestActivity(context);
                break;
        }
    }

    @Override
    public void showLoading() {
        showProgress();
    }

    @Override
    public void hideLoading() {
        hideProgress();
    }

    @Override
    public void getJobBangClassDataSuccess(JobBangClassListInfo datas) {
//        strategyList = datas.strategy_list;
//        secretList = datas.cheats_list;
        secretList.addAll(datas.strategy_list);
        secretList.addAll(datas.cheats_list);
        testList = datas.test_list;

        if (datas.ad_list != null && datas.ad_list.size() > 0 && adList != null && sibTopAd != null) {
            adList.clear();
            adList.addAll(datas.ad_list);
            ADDataProvider.initAdvertisement(sibTopAd, adList, itemADHeight, itemADWidth);
        }

        //为攻略加载数据
//        mJobBangClassStrategyAdapter = new JobBangClassStrategyAdapter(context, strategyList);
//        mStrategyListView.setAdapter(mJobBangClassStrategyAdapter);
//        mStrategyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Goto.toJobBangDetailActivity(context, strategyList.get(position).raiders_id, 4, 3);
//            }
//        });
        //为秘籍加载数据
        mJobBangClassSecretAdapter = new JobBangClassSecretAdapter(context, secretList);
        mSecretListView.setAdapter(mJobBangClassSecretAdapter);
        mSecretListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Goto.toJobBangDetailActivity(context, secretList.get(position).raiders_id, 6, 2);
            }
        });
        //为试卷加载数据
        mJobBangClassTestAdapter = new JobBangClassTestAdapter(context, testList);
        mTestListView.setAdapter(mJobBangClassTestAdapter);
        mTestListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Goto.toJobBangDetailActivity(context, testList.get(position).raiders_id, 5, 4);
            }
        });
    }

}
