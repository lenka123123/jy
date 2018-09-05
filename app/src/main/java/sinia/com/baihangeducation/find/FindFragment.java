package sinia.com.baihangeducation.find;


import android.os.Build;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.R;
import com.mcxtzhang.swipemenulib.base.BaseFragment;
import sinia.com.baihangeducation.MyApplication;
import sinia.com.baihangeducation.find.adapter.FindMomentAddAdapter;
import com.mcxtzhang.swipemenulib.info.AddListInfo;
import com.mcxtzhang.swipemenulib.info.GetKeyWorldInfo;
import com.mcxtzhang.swipemenulib.info.bean.JobBangClassADListInfo;
import sinia.com.baihangeducation.find.presenter.GetAddPresenter;
import sinia.com.baihangeducation.find.presenter.GetKeyWorldPresenter;
import sinia.com.baihangeducation.find.view.GetKeyWorldView;
import sinia.com.baihangeducation.find.view.IGetAddView;
import sinia.com.baihangeducation.supplement.base.Goto;
import com.mcxtzhang.swipemenulib.utils.ACache;
import com.mcxtzhang.swipemenulib.utils.Constants;
import com.mcxtzhang.swipemenulib.customview.HorizontalListView;

/**
 * Created by Administrator on 2018.02.24.
 */

public class FindFragment extends BaseFragment implements GetKeyWorldView, IGetAddView {
    private GetKeyWorldPresenter mGetKeyWorldPresenter;
    private GetAddPresenter mGetAddPresenter;
    private TagFlowLayout mHotTalk;
    private TextView mSearch;                           //搜索框
    private LinearLayout mShareEveryDayLayout;          //每日分享
    private LinearLayout mJobBangClass;          //就业邦学堂
    private LinearLayout mCompusHelpEachOther;          //就业邦学堂
    private HorizontalListView mHorizontalListView;                 //横向滚动控件
    private FindMomentAddAdapter addAdapter;
    private MyApplication application;

    private ACache mCache;

    private List<String> mCacheData1;               //搜索历史
    private List<JobBangClassADListInfo> adList;

    List<GetKeyWorldInfo> mList;

    @Override
    public int initLayoutResID() {
        return R.layout.fragment_find;
    }

    /**
     * 初始化数据
     */
    @Override
    protected void initData() {
        adList = new ArrayList<>();
        application = (MyApplication) context.getApplication();

        mGetKeyWorldPresenter = new GetKeyWorldPresenter(context, this);
        mGetKeyWorldPresenter.getKeyWorld();
        mGetAddPresenter = new GetAddPresenter(context, this);
        mGetAddPresenter.getAddInfo();

        mHorizontalListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                ADDataProvider.adToInformation(context, adList.get(position), application);
                String title = adList.get(position).title;
                int type = adList.get(position).type;
                //App内容板块 (1: 兼职 ; 2 : 全职  ; 3 : 培训  ; 4 : 转让  ； 5：互助  ； 6：App内容板块   ； 7：趣事   ； 8：每日分享    ）
                int plate = adList.get(position).plate;
                if (type == 1) {
                    switch (plate) {
                        case 1:
                            Goto.toPartTimeJobDetailActivity(context, adList.get(position).type_id);
                            break;
                        case 2:
                            Goto.toAllJobDetailActivity(context, adList.get(position).type_id);
                            break;
                        case 3:
                            Goto.toTraingDetailActivity(context, adList.get(position).type_id);
                            break;
                        case 4:
                            Goto.toHomeAndFindHelpEachOtherDetailActivity(context, adList.get(position).type_id);
                            break;
                        case 5:
                            Goto.toHomeAndFindHelpEachOtherDetailActivity(context, adList.get(position).type_id);
                            break;
                        case 7:
                            Goto.toCampusInterestingDetailActivity(context,adList.get(position).type_id);
                            break;
                        case 8:
                            Goto.toShareEveryDayDetailActivity(context, adList.get(position).type_id);
                            break;
                    }
                } else {
                    if (TextUtils.isEmpty(adList.get(position).url)) {
                        return;
                    }
                    String url = "";
                    url = adList.get(position).url + "&user_id=" + AppConfig.USERID;
                    Goto.toWebView(context, title, url, R.drawable.new_realname_title_bg);
                }
            }
        });

    }

    /**
     * 初始化控件
     */
    @Override
    protected void initView() {
        mCacheData1 = new ArrayList<>();
        mCache = ACache.get(context);
        mHotTalk = $(R.id.fragment_find_hottalk);
        mSearch = $(R.id.fragment_find_search);
        mShareEveryDayLayout = $(R.id.fragment_find_shareeveryday);
        mJobBangClass = $(R.id.fragment_find_jobbangclass);
        mCompusHelpEachOther = $(R.id.fragment_find_compushelpeachother);
        mHorizontalListView = $(R.id.fragment_find_moment);

        mSearch.setOnClickListener(this);
        mShareEveryDayLayout.setOnClickListener(this);
        mJobBangClass.setOnClickListener(this);
        mCompusHelpEachOther.setOnClickListener(this);

        mHotTalk.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {

                mCache = ACache.get(context);
                //去除缓存中的搜索历史对象
                Object searchdata = mCache.getAsObject("SEARCHDATA");
                //将对象转成需要的数据类型
                if (searchdata != null) {
                    mCacheData1 = (List<String>) JSONObject.parse(searchdata.toString());
                }

                if (mCacheData1 != null && !mCacheData1.isEmpty() && mCacheData1.size() > 0) {
                    if (mCacheData1.size() >= 8) {

                        for (int i = 0; i < mCacheData1.size(); i++) {
                        }
                        //如果list的长度大于8，则移除最早放如的
                        mCacheData1.remove(0);
                        //将数据添加进list中
                        mCacheData1.add(mList.get(position).key_name);
                        //将list转成jsonarry
                        JSONArray array = JSONArray.parseArray(JSON.toJSONString(mCacheData1));
                        //将数据存入缓存中
                        mCache.put("SEARCHDATA", array);
                    } else {
                        mCacheData1.add(mList.get(position).key_name);
                        //将list转成jsonarry
                        JSONArray array = JSONArray.parseArray(JSON.toJSONString(mCacheData1));
                        //将数据存入缓存中
                        mCache.put("SEARCHDATA", array);
                    }
                } else {

                    mCacheData1.add(mList.get(position).key_name);
                    //将list转成jsonarry
                    JSONArray array = JSONArray.parseArray(JSON.toJSONString(mCacheData1));
                    //将数据存入缓存中
                    mCache.put("SEARCHDATA", array);
                }

                Goto.toSearchReasultActivity(context, mList.get(position).key_name);
                return true;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_find_search:
                Goto.toSearchActivity(context);
                break;
            case R.id.fragment_find_shareeveryday:
                Goto.toShareEveryDayActivity(context);
                break;
            case R.id.fragment_find_jobbangclass:
                Goto.toJobBangClassActivity(context);
                break;
            case R.id.fragment_find_compushelpeachother:
                Goto.toHomeAndFindHelpEachOtherActivity(context);
                break;
        }
    }

    @Override
    public void getKeyWorldSuccess(List<GetKeyWorldInfo> mGetKeyWorldInfo) {
        mList = mGetKeyWorldInfo;
        mHotTalk.setAdapter(new TagAdapter(mGetKeyWorldInfo) {
            @Override
            public View getView(FlowLayout parent, int position, Object o) {
                TextView textView = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.keyworlditem, mHotTalk, false);
                textView.setText(mList.get(position).key_name);
                return textView;
            }
        });
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public String getAddPosition() {
        return Constants.AD_FIND_TOP;
    }

    @Override
    public void getAddSuccess(AddListInfo addListInfo) {
        if (addListInfo.ad_list != null && addListInfo.ad_list.size() > 0) {
            adList = (addListInfo.ad_list);
            //首页朋友圈新闻适配器
            addAdapter = new FindMomentAddAdapter(context, adList);
            //为横向滚动list加载适配器
            mHorizontalListView.setAdapter(addAdapter);
            addAdapter.notifyDataSetChanged();

        }
    }
}
