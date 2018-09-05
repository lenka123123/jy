package sinia.com.baihangeducation.mine.activity;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.supplement.base.BaseActivity;
import sinia.com.baihangeducation.mine.adapter.WantAreaAdapter;
import sinia.com.baihangeducation.mine.adapter.WantJobAdapter;

import com.mcxtzhang.swipemenulib.info.bean.WantAreaList;
import com.mcxtzhang.swipemenulib.info.bean.WantAreaList_Up;
import com.mcxtzhang.swipemenulib.info.bean.WantJobList;
import com.mcxtzhang.swipemenulib.info.bean.WantJobList_Up;

import sinia.com.baihangeducation.mine.presenter.WantJobPresenter;
import sinia.com.baihangeducation.mine.view.WantJobView;

/**
 * Created by Administrator on 2018/4/14.
 */

public class WantJobActivity extends BaseActivity implements WantJobView {

    private TagFlowLayout mWantJobLayout;
    private TagFlowLayout mWantAreaLayout;

    private WantJobAdapter mJobAdapter;
    private WantAreaAdapter mAreaAdapter;
    private WantJobPresenter mWantJobPresenter;
    private List<WantJobList> mJobData;
    private List<WantAreaList> mAreaData;
    private List<WantJobList_Up> mJobDataUp;                //选中意向求职的列表
    private List<WantAreaList_Up> mAreaDataUp;              //选中意向区域列表
    private Set<Integer> jobChoiceIndex;
    private Set<Integer> areaChoiceIndex;

    @Override
    public int initLayoutResID() {
        return R.layout.fragment_mine_wantjob;
    }

    @Override
    protected void initData() {
        mCommonTitle.setCenterText(R.string.mine_want_job);
        mCommonTitle.setBackgroundColor(Color.WHITE);
        mCommonTitle.setRightText("提交");
        mCommonTitle.getRightTxt().setTextColor(Color.RED);
        mCommonTitle.getRightTxt().setOnClickListener(this);


        mWantJobPresenter = new WantJobPresenter(context, this);
        mWantJobPresenter.getWantJobData();
        mWantJobPresenter.getWantAreaData();

        jobChoiceIndex = new HashSet<>();
        areaChoiceIndex = new HashSet<>();
    }

    @Override
    protected void initView() {
        mWantJobLayout = $(R.id.fragment_mine_wantjob_wantjob);
        mWantAreaLayout = $(R.id.fragment_mine_wantjob_wantarea);

        mJobDataUp = new ArrayList<>();
        mAreaDataUp = new ArrayList<>();


        mWantJobLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                return true;
            }
        });

        mWantJobLayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                mJobDataUp.clear();
                if (mJobData != null || mJobData.size() != 0) {
                    Iterator<Integer> iterator = selectPosSet.iterator();
                    while (iterator.hasNext()) {
                        int wantJobIndex = iterator.next();
                        WantJobList wantJobList = mJobData.get(wantJobIndex);
                        WantJobList_Up wantJobList_up = new WantJobList_Up();
                        wantJobList_up.intention_id = wantJobList.intention_id;
                        wantJobList_up.intention_name = wantJobList.intention_name;
                        mJobDataUp.add(wantJobList_up);
                    }
                }

            }
        });
        mWantAreaLayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                mAreaDataUp.clear();
                if (mAreaDataUp != null || mAreaDataUp.size() != 0) {
                    Iterator<Integer> iterator = selectPosSet.iterator();
                    while (iterator.hasNext()) {
                        int wantJobIndex = iterator.next();
                        WantAreaList wantAreaList = mAreaData.get(wantJobIndex);
                        WantAreaList_Up wantAreaList_up = new WantAreaList_Up();
                        wantAreaList_up.zone_id = wantAreaList.zone_id;
                        wantAreaList_up.zone_name = wantAreaList.zone_name;

                        mAreaDataUp.add(wantAreaList_up);
                    }
                }

            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.right_txt:

                mWantJobPresenter.submitData(mJobDataUp, mAreaDataUp);

                break;
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {
        finish();
    }

    @Override
    public WantJobList_Up getMyWantJobData() {
        return null;
    }

    @Override
    public WantAreaList_Up getMyWantAreaList_Up() {
        return null;
    }

    @Override
    public void getWantJobSuccess(final List<WantJobList> wantJobLists) {


        mJobData = wantJobLists;
        mJobAdapter = new WantJobAdapter(context, mWantJobLayout, mJobData);
        for (int i = 0; i < wantJobLists.size(); i++) {
            if (wantJobLists.get(i).is_choose == 1) {
                jobChoiceIndex.add(i);
            }
        }
        mJobAdapter.setSelectedList(jobChoiceIndex);
        mWantJobLayout.setAdapter(mJobAdapter);
    }

    @Override
    public void getWantAreaSuccess(List<WantAreaList> wantAreaLists) {
        AppConfig.INTENTION_SETTING = true;
        mAreaData = wantAreaLists;
        mAreaAdapter = new WantAreaAdapter(context, mWantAreaLayout, mAreaData);

//        for (int i = 0; i < wantAreaLists.size(); i++) {  //默认选择
//            if (wantAreaLists.get(i).is_choose == 1) {
//                areaChoiceIndex.add(i);
//            }
//        }


        mAreaAdapter.setSelectedList(areaChoiceIndex);
        mWantAreaLayout.setAdapter(mAreaAdapter);
    }

}
