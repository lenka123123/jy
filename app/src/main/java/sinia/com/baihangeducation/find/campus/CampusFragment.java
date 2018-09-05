package sinia.com.baihangeducation.find.campus;


import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.framwork.glide.ImageLoaderUtils;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import sinia.com.baihangeducation.R;
import com.mcxtzhang.swipemenulib.base.BaseFragment;
import sinia.com.baihangeducation.find.campus.adapter.CampusFragmentJobAdapter;
import sinia.com.baihangeducation.find.campus.info.bean.CampusInterestingListInfo;
import sinia.com.baihangeducation.find.campus.present.CampusPresenter;
import sinia.com.baihangeducation.find.campus.view.CampusView;
import com.mcxtzhang.swipemenulib.info.bean.HomePartTimeInfo;
import sinia.com.baihangeducation.supplement.base.Goto;
import com.mcxtzhang.swipemenulib.utils.ACache;
import com.mcxtzhang.swipemenulib.utils.Constants;
import com.mcxtzhang.swipemenulib.customview.NoScrollListView;

/**
 * Created by Administrator on 2018.02.24.
 */

public class CampusFragment extends BaseFragment implements CampusView {
    ACache mCache;
    private TextView mSearch;                           //搜索框
    private TextView mCampusRefresh;            //校园招聘刷新
    private NoScrollListView mCampusListView;       //校园招聘ListView
    private TextView mCampusInteringRefresh;        //校园趣事刷新
    private TagFlowLayout mCampusInteringListView;  //校园趣事ListView

    private CampusFragmentJobAdapter mCampusFragmentJobAdapter;
    List<HomePartTimeInfo> mJobData;

    private final static String campusJobType = "2";
    private String lng;
    private String lat;
    private int cmpusJobPage = 1;               //职业分页页码
    private int cmpusJobMaxPage;                //职业总数
    private int cmpusInterestingMaxPage;        //趣事总数
    private int cmpusInterestingPage = 1;       //趣事分页页码
    private int perpage = 4;                    //分页每页显示数

    private CampusPresenter mCampusPresenter;


    @Override
    public int initLayoutResID() {
        return R.layout.fragment_campus;
    }

    /**
     * 初始化数据
     */
    @Override
    protected void initData() {
        lat = mCache.getAsString("LAT");
        lng = mCache.getAsString("LNG");
        mJobData = new ArrayList<>();

        mCampusPresenter = new CampusPresenter(context, this);
        mCampusPresenter.getCampusJobInfo();
        mCampusPresenter.getCampusInteringInfo();


    }

    /**
     * 初始化控件
     */
    @Override
    protected void initView() {
        mCache = ACache.get(context);
        mSearch = $(R.id.fragment_campus_search);
        mCampusRefresh = $(R.id.fragment_campus_campusrefresh);
        mCampusListView = $(R.id.fragment_campus_campuslistview);
        mCampusInteringRefresh = $(R.id.fragment_campus_campusintertingrefresh);
        mCampusInteringListView = $(R.id.fragment_campus_campusintertingListView);

        mCampusRefresh.setOnClickListener(this);
        mCampusInteringRefresh.setOnClickListener(this);
        mSearch.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_campus_search:
                //查找页
                Goto.toSearchActivity(context);
                break;
            case R.id.fragment_campus_campusrefresh:
                //职业刷新按钮
                if (cmpusJobMaxPage / perpage > cmpusJobPage) {
                    cmpusJobPage++;
                    mCampusPresenter.getCampusJobInfo();
                } else {
                    cmpusJobPage = 1;
                    mCampusPresenter.getCampusJobInfo();
                }
                break;
            case R.id.fragment_campus_campusintertingrefresh:
                //趣事刷新按钮
                if (cmpusInterestingMaxPage / (perpage*cmpusInterestingPage) > cmpusJobPage) {
                    cmpusInterestingPage++;
                    mCampusPresenter.getCampusInteringInfo();
                } else {
                    cmpusInterestingPage = 1;
                    mCampusPresenter.getCampusInteringInfo();
                }
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
    public String getCityId() {
        return Constants.city_id;
    }

    @Override
    public String getType() {
        return campusJobType;
    }

    @Override
    public String getLocationLat() {
        return lat;
    }

    @Override
    public String getLocationLng() {
        return lng;
    }

    @Override
    public String getCampusJobPager() {
        return cmpusJobPage + "";
    }

    @Override
    public String getCampusInterestingPager() {
        return cmpusInterestingPage + "";
    }

    @Override
    public String getPerpage() {
        return perpage + "";
    }

    @Override
    public void getCampusJobInfoSuccess(final List<HomePartTimeInfo> mHomePartTimeInfo, int maxpage) {
        if (mHomePartTimeInfo != null) {
            mJobData = mHomePartTimeInfo;
            cmpusJobMaxPage = maxpage;
            mCampusFragmentJobAdapter = new CampusFragmentJobAdapter(context, mJobData);
            mCampusListView.setAdapter(mCampusFragmentJobAdapter);
            mCampusListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    switch (mHomePartTimeInfo.get(position).job_type) {
                        case 1:
                            Goto.toAllJobDetailActivity(context, mHomePartTimeInfo.get(position).job_id);
                            break;
                        case 2:
                            Goto.toPartTimeJobDetailActivity(context, mHomePartTimeInfo.get(position).job_id);
                            break;
                    }
                }
            });
        }
    }

    @Override
    public void getCampusJobInterestingSuccess(final List<CampusInterestingListInfo> list, int maxpage) {
        if (list != null) {
            cmpusInterestingMaxPage = maxpage;
            mCampusInteringListView.setAdapter(new TagAdapter(list) {
                @Override
                public View getView(FlowLayout parent, int position, Object o) {
                    View view = LayoutInflater.from(context).inflate(R.layout.campus_funthing_item, mCampusInteringListView, false);
                    ImageView img = view.findViewById(R.id.campus_funthing_item_img);
                    TextView title = view.findViewById(R.id.campus_funthing_item_title);
                    TextView date = view.findViewById(R.id.campus_funthing_item_date);
                    TextView look = view.findViewById(R.id.campus_funthing_item_looknum);
                    ImageLoaderUtils.display(context, img, list.get(position).fun_images, R.drawable.new_errorlogo_sp);
                    title.setText(list.get(position).fun_title);
                    date.setText(list.get(position).fun_add_date);
                    look.setText("浏览了" + list.get(position).fun_look_num + "次");
                    return view;
                }
            });

            mCampusInteringListView.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                @Override
                public boolean onTagClick(View view, int position, FlowLayout parent) {
                    Goto.toCampusInterestingDetailActivity(context,list.get(position).fun_id);
                    return true;
                }
            });
        }
    }
}
