package sinia.com.baihangeducation.find.activity;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mcxtzhang.swipemenulib.info.GetKeyWorldInfo;
import com.mcxtzhang.swipemenulib.utils.ACache;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.find.presenter.GetKeyWorldPresenter;
import sinia.com.baihangeducation.find.view.GetKeyWorldView;
import sinia.com.baihangeducation.supplement.base.BaseActivity;
import sinia.com.baihangeducation.supplement.base.Goto;

/**
 * Created by Administrator on 2018/4/18.
 */

    public class SearchClubActivity extends BaseActivity implements GetKeyWorldView {

        private GetKeyWorldPresenter mGetKeyWorldPresenter;
        private ACache mACache;

        private ImageView mBack;                    //返回
        private TagFlowLayout mSearchHistrory;      //搜索历史
        private TagFlowLayout mHotTalk;             //热门话题
        private LinearLayout mSearchHistroyLayout;        //搜索历史layout
        private EditText mSearch;                       //搜索

        List<GetKeyWorldInfo> mList;
        List<String> mCacheList;

    @Override
    public int initLayoutResID() {
        return R.layout.search;
    }

    private boolean doNone = true;

    @Override
    protected void initView() {
        mSearchHistrory = $(R.id.search_searchhistory);
        mHotTalk = $(R.id.search_hottalk);
        mSearchHistroyLayout = $(R.id.search_searchhistory_layout);
        mSearch = $(R.id.search_searchhistory_searched);
        mBack = $(R.id.search_back);
        mBack.setOnClickListener(this);


        // EditText 编写搜索框 而是在我们编辑完之后点击软键盘上的回车键才会触发
        mSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (!(v.getText().toString().trim()).isEmpty()) {
                    mACache = ACache.get(context);
                    //去除缓存中的搜索历史对象
                    Object searchdata = mACache.getAsObject("SEARCHDATA");
                    //将对象转成需要的数据类型
                    if (searchdata != null) {
                        mCacheList = (List<String>) JSONObject.parse(searchdata.toString());
                    }
                    if (mCacheList != null && !mCacheList.isEmpty() && mCacheList.size() > 0) {
                        if (mCacheList.size() >= 8) {
                            if (!mCacheList.contains(v.getText().toString().trim())) {
                                //如果list的长度大于8，则移除最早放如的
                                mCacheList.remove(mCacheList.size() - 1);
                                //将数据添加进list中

                                mCacheList.add(0, v.getText().toString().trim());
                                //将list转成jsonarry
                                JSONArray array = JSONArray.parseArray(JSON.toJSONString(mCacheList));
                                //将数据存入缓存中
                                mACache.put("SEARCHDATA", array);
                            }

                        } else {
                            if (!mCacheList.contains(v.getText().toString().trim())) {
                                mCacheList.add(0, v.getText().toString().trim());
                                //将list转成jsonarry
                                JSONArray array = JSONArray.parseArray(JSON.toJSONString(mCacheList));
                                //将数据存入缓存中
                                mACache.put("SEARCHDATA", array);
                            }
                        }
                    } else {

                        mCacheList.add(0, v.getText().toString().trim());
                        //将list转成jsonarry
                        JSONArray array = JSONArray.parseArray(JSON.toJSONString(mCacheList));
                        //将数据存入缓存中
                        mACache.put("SEARCHDATA", array);
                    }
                    Goto.toSearchReasultActivity(context, v.getText().toString().trim());

                }
                return false;
            }
        });

        mACache = ACache.get(context);

        mSearch.setOnClickListener(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //历史搜索加载数据
        if (mSearchHistrory != null && mCacheList != null)
            mSearchHistrory.setAdapter(new TagAdapter(mCacheList) {
                @Override
                public View getView(FlowLayout parent, int position, Object o) {
                    TextView textView = (TextView) LayoutInflater.from(context).inflate(R.layout.keyworlditem, mHotTalk,
                            false);
                    textView.setText(mCacheList.get(position));
                    return textView;
                }
            });
    }

    @Override
    protected void initData() {
        mCacheList = new ArrayList<>();
        mGetKeyWorldPresenter = new GetKeyWorldPresenter(context, this);
        mGetKeyWorldPresenter.getKeyWorld();

        //去除缓存中的搜索历史对象
        Object searchdata = mACache.getAsObject("SEARCHDATA");
        //判断是否为空
        if (searchdata != null) {
            //若数据不为空，则显示历史搜索
            mSearchHistroyLayout.setVisibility(View.VISIBLE);
            //将对象转成需要的数据类型
            mCacheList = (List<String>) JSONObject.parse(searchdata.toString());

            //历史搜索加载数据
            mSearchHistrory.setAdapter(new TagAdapter(mCacheList) {
                @Override
                public View getView(FlowLayout parent, int position, Object o) {
                    TextView textView = (TextView) LayoutInflater.from(context).inflate(R.layout.keyworlditem, mHotTalk,
                            false);
                    textView.setText(mCacheList.get(position));
                    return textView;
                }
            });
        } else {
            //数据为空，隐藏搜索历史
            mSearchHistroyLayout.setVisibility(View.GONE);
        }

        //历史搜索点击item之后放入缓存操作
        mSearchHistrory.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {

                mACache = ACache.get(context);
                //去除缓存中的搜索历史对象
                Object searchdata = mACache.getAsObject("SEARCHDATA");
                //将对象转成需要的数据类型
                mCacheList = null;
                if (searchdata != null) {
                    mCacheList = (List<String>) JSONObject.parse(searchdata.toString());
                }

                String clickitem = mCacheList.get(position);
                Goto.toSearchReasultActivity(context, clickitem);
//                finish();
                //  Toast.makeText(context, clickitem, Toast.LENGTH_SHORT).show();

                return true;
            }
        });

        //热门话点击   历史搜索点击item之后放入缓存操作
        mHotTalk.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                mACache = ACache.get(context);
                //去除缓存中的搜索历史对象
                Object searchdata = mACache.getAsObject("SEARCHDATA");
                //将对象转成需要的数据类型
                if (searchdata != null) {
                    mCacheList = (List<String>) JSONObject.parse(searchdata.toString());
                }

                if (mCacheList != null && !mCacheList.isEmpty() && mCacheList.size() > 0) {
                    if (mCacheList.size() >= 8) {
                        if (!mCacheList.contains(mList.get(position).key_name)) {
                            //如果list的长度大于8，则移除最早放如的
                            mCacheList.remove(mCacheList.size() - 1);
                            //将数据添加进list中
                            mCacheList.add(0, mList.get(position).key_name);
                            //将list转成jsonarry
                            JSONArray array = JSONArray.parseArray(JSON.toJSONString(mCacheList));
                            //将数据存入缓存中
                            mACache.put("SEARCHDATA", array);
                        }
                    } else {
                        if (!mCacheList.contains(mList.get(position).key_name)) {
                            mCacheList.add(0, mList.get(position).key_name);
                            //将list转成jsonarry
                            JSONArray array = JSONArray.parseArray(JSON.toJSONString(mCacheList));
                            //将数据存入缓存中
                            mACache.put("SEARCHDATA", array);
                        }
                    }
                } else {
                    mCacheList.add(mList.get(position).key_name);
                    //将list转成jsonarry
                    JSONArray array = JSONArray.parseArray(JSON.toJSONString(mCacheList));
                    //将数据存入缓存中
                    mACache.put("SEARCHDATA", array);
                }
                Goto.toSearchReasultActivity(context, mList.get(position).key_name);
                return true;
            }
        });
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.search_searchhistory_searched:

                break;
            case R.id.search_back:
                finish();
                break;
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void getKeyWorldSuccess(List<GetKeyWorldInfo> mGetKeyWorldInfo) {
        mList = mGetKeyWorldInfo;
        mHotTalk.setAdapter(new TagAdapter(mGetKeyWorldInfo) {
            @Override
            public View getView(FlowLayout parent, int position, Object o) {
                TextView textView = (TextView) LayoutInflater.from(context).inflate(R.layout.keyworlditem, mHotTalk, false);
                textView.setText(mList.get(position).key_name);
                return textView;
            }
        });
    }


    public List removeDuplicate(List list) {
        List listTemp = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            if (!listTemp.contains(list.get(i))) {
                listTemp.add(list.get(i));
            }
        }
        return listTemp;
    }

}
