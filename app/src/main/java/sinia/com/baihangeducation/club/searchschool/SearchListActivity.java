package sinia.com.baihangeducation.club.searchschool;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.club.ClubPermissModel;
import sinia.com.baihangeducation.club.club.interfaces.GetSearchRequestListener;
import sinia.com.baihangeducation.club.searchschool.interfaces.SearchSchoolContract;
import sinia.com.baihangeducation.club.searchschool.model.ClubSchoolList;
import sinia.com.baihangeducation.club.searchschool.model.SearchSchoolModel;
import sinia.com.baihangeducation.club.searchschool.presenter.ClubSchoolListPresenter;
import sinia.com.baihangeducation.club.searchschool.view.SearchSchoolAdapter;
import sinia.com.baihangeducation.find.info.bean.SearchRecommend;
import sinia.com.baihangeducation.find.presenter.GetKeyWorldPresenter;
import sinia.com.baihangeducation.supplement.base.BaseActivity;
import sinia.com.baihangeducation.supplement.base.Goto;

public class SearchListActivity extends BaseActivity implements SearchSchoolContract.View, GetSearchRequestListener {
    private List<ClubSchoolList.School> mRows = new ArrayList<>();

    private String text = "";
    private RefreshLayout mSmartRefreshLayout;
    private RecyclerView mAutoLoadRecyclerView;
    private SearchSchoolAdapter paiedListAdapter;
    private boolean addData = false;
    private int currentPage = 1;
    private int perpage = 20;
    private boolean isCreated = false;
    private ImageView mErrorImageView;
    private TextView mErrorTextView;
    private ClubSchoolListPresenter clubSchoolListPresenter;
    private EditText searchEditText;
    private TextView contact_me;
    private LinearLayout contact_admin;
    private ACache mACache;
    private TagFlowLayout mSearchHistrory;      //搜索历史
    private TagFlowLayout mHotTalk;             //热门话题
    private LinearLayout mSearchHistroyLayout;        //搜索历史layout
//    private EditText mSearch;                       //搜索

    List<String> mList;
    List<String> mCacheList;
    private LinearLayout layout;
    private View listview;

    public int initLayoutResID() {
        return R.layout.activity_club_search_school;
    }


    @Override
    protected void initView() {
        searchEditText = findViewById(R.id.search_searchhistory_searched);
        mSmartRefreshLayout = findViewById(R.id.refreshLayout);
        mAutoLoadRecyclerView = findViewById(R.id.recyclerView);

        contact_me = findViewById(R.id.contact_me);
        contact_admin = findViewById(R.id.contact_admin);
        mErrorImageView = findViewById(R.id.img_state);
        mErrorTextView = findViewById(R.id.tv_state);
        layout = findViewById(R.id.layout);
        listview = findViewById(R.id.listview);

        initViewForClub();

        contact_me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Goto.toLinkUsActivity(context);
            }
        });
        TextView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        paiedListAdapter = new SearchSchoolAdapter(this);
        mAutoLoadRecyclerView.setAdapter(paiedListAdapter);
        setPullRefresher();
        final GridLayoutManager manager = new GridLayoutManager(this, 1);
        mAutoLoadRecyclerView.setLayoutManager(manager);
        mAutoLoadRecyclerView.setItemAnimator(new DefaultItemAnimator());
        addData = false;
//        paiedListAdapter.setData(mRows, 1);

    }

    @Override
    protected void initData() {
//        mCommonTitle.setBackgroundColor(Color.WHITE);
//        mCommonTitle.setCenterText("我的购买");
//        mCommonTitle.getRightTxt().setTextSize(16);
//        mCommonTitle.getRightTxt().setTextColor(Color.BLACK);
        ClubPermissModel clubPermissModel = new ClubPermissModel(this);
        clubPermissModel.getRecommend(this);
        clubSchoolListPresenter = new ClubSchoolListPresenter(new SearchSchoolModel(context), this);
//        clubSchoolListPresenter.getSearchSchoolList("1", String.valueOf(perpage), "");
        text = "";

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                text = s.toString();
                mRows.clear();
                // : 0length0length1length1
                System.out.println(start + "length" + before + "length" + count + "length" + s.toString().length());
                if (s.toString().length() < 1) {
                    layout.setVisibility(View.VISIBLE);
                    contact_admin.setVisibility(View.GONE);
                    listview.setVisibility(View.GONE);
                } else {
                    layout.setVisibility(View.GONE);
                    contact_admin.setVisibility(View.GONE);
                    listview.setVisibility(View.VISIBLE);
                    clubSchoolListPresenter.getSearchSchoolList("1", String.valueOf(perpage), s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }


    protected void initViewForClub() {
        mSearchHistrory = $(R.id.search_searchhistory);
        mHotTalk = $(R.id.search_hottalk);
        mSearchHistroyLayout = $(R.id.search_searchhistory_layout);


        // EditText 编写搜索框 而是在我们编辑完之后点击软键盘上的回车键才会触发
//        searchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if (!(v.getText().toString().trim()).isEmpty()) {
//                    mACache = ACache.get(context);
//                    //去除缓存中的搜索历史对象
//                    Object searchdata = mACache.getAsObject("SEARCHDATA");
//                    //将对象转成需要的数据类型
//                    if (searchdata != null) {
//                        mCacheList = (List<String>) JSONObject.parse(searchdata.toString());
//                    }
//                    if (mCacheList != null && !mCacheList.isEmpty() && mCacheList.size() > 0) {
//                        if (mCacheList.size() >= 8) {
//                            if (!mCacheList.contains(v.getText().toString().trim())) {
//                                //如果list的长度大于8，则移除最早放如的
//                                mCacheList.remove(mCacheList.size() - 1);
//                                //将数据添加进list中
//
//                                mCacheList.add(0, v.getText().toString().trim());
//                                //将list转成jsonarry
//                                JSONArray array = JSONArray.parseArray(JSON.toJSONString(mCacheList));
//                                //将数据存入缓存中
//                                mACache.put("SEARCHDATA", array);
//                            }
//
//                        } else {
//                            if (!mCacheList.contains(v.getText().toString().trim())) {
//                                mCacheList.add(0, v.getText().toString().trim());
//                                //将list转成jsonarry
//                                JSONArray array = JSONArray.parseArray(JSON.toJSONString(mCacheList));
//                                //将数据存入缓存中
//                                mACache.put("SEARCHDATA", array);
//                            }
//                        }
//                    } else {
//
//                        mCacheList.add(0, v.getText().toString().trim());
//                        //将list转成jsonarry
//                        JSONArray array = JSONArray.parseArray(JSON.toJSONString(mCacheList));
//                        //将数据存入缓存中
//                        mACache.put("SEARCHDATA", array);
//                    }
//                    Goto.toSearchReasultActivity(context, v.getText().toString().trim());
//
//                }
//                return false;
//            }
//        });

        mACache = ACache.get(context);

        searchEditText.setOnClickListener(this);
        initDataForClub();
    }

    protected void initDataForClub() {
        mCacheList = new ArrayList<>();
        mList = new ArrayList<>();

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
                mRows.clear();
                searchEditText.setText(clickitem);
                clubSchoolListPresenter.getSearchSchoolList("1", String.valueOf(perpage), clickitem);
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
                        if (!mCacheList.contains(mList.get(position))) {
                            //如果list的长度大于8，则移除最早放如的
                            mCacheList.remove(mCacheList.size() - 1);
                            //将数据添加进list中
                            mCacheList.add(0, mList.get(position));
                            //将list转成jsonarry
                            JSONArray array = JSONArray.parseArray(JSON.toJSONString(mCacheList));
                            //将数据存入缓存中
                            mACache.put("SEARCHDATA", array);
                        }
                    } else {
                        if (!mCacheList.contains(mList.get(position))) {
                            mCacheList.add(0, mList.get(position));
                            //将list转成jsonarry
                            JSONArray array = JSONArray.parseArray(JSON.toJSONString(mCacheList));
                            //将数据存入缓存中
                            mACache.put("SEARCHDATA", array);
                        }
                    }
                } else {
                    mCacheList.add(mList.get(position));
                    //将list转成jsonarry
                    JSONArray array = JSONArray.parseArray(JSON.toJSONString(mCacheList));
                    //将数据存入缓存中
                    mACache.put("SEARCHDATA", array);
                }
                mRows.clear();
                searchEditText.setText(mList.get(position));
//                clubSchoolListPresenter.getSearchSchoolList("1", String.valueOf(perpage), mList.get(position));
                return true;
            }
        });
    }


    private void setPullRefresher() {
        mSmartRefreshLayout.setRefreshHeader(new MaterialHeader(this));
        //   mSmartRefreshLayout.setRefreshFooter(new BallPulseFooter(getContext()));
        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                currentPage = 1;
                mRows.clear();
//                clubSchoolListPresenter.getSearchSchoolList("1", String.valueOf(perpage), "");
                refreshlayout.finishRefresh(500);
            }
        });
        mSmartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                //      AllCoffersFragment.this.refreshlayout = refreshlayout;
                if (addData) {
                    clubSchoolListPresenter.getSearchSchoolList(String.valueOf(currentPage), String.valueOf(perpage), "");
                } else {
                    mSmartRefreshLayout.finishLoadMoreWithNoMoreData();
                }
                refreshlayout.finishLoadMore(2000);
            }
        });
    }

    private void showErrorState(boolean isError) {
//        if (isError) {
//            mErrorImageView.setVisibility(View.VISIBLE);
//            mErrorTextView.setVisibility(View.VISIBLE);
//            Glide.with(context).load(R.mipmap.ic_launcher).into(mErrorImageView);
//            mErrorTextView.setText("没有数据");
//            if (mSmartRefreshLayout != null)
//                mSmartRefreshLayout.finishLoadMoreWithNoMoreData();
//        } else {
//            mErrorImageView.setVisibility(View.GONE);
//            mErrorTextView.setVisibility(View.GONE);
//        }
    }

    @Override
    public void showSearchSchoolList(List<ClubSchoolList.School> successMessage, int maxpage) {

        if (successMessage.size() < 1) {
            contact_admin.setVisibility(View.VISIBLE);
            layout.setVisibility(View.GONE);
            listview.setVisibility(View.GONE);


            showErrorState(true);
        } else {
            contact_admin.setVisibility(View.GONE);
            layout.setVisibility(View.GONE);
            listview.setVisibility(View.VISIBLE);
            showErrorState(false);
        }
        if (mSmartRefreshLayout != null)
            mSmartRefreshLayout.finishLoadMore(500);


        if (currentPage < maxpage) {
            addData = true;
            currentPage = currentPage + 1;
        } else {
            addData = false;
        }

        if (currentPage == 1)
            mRows.clear();

        mRows.addAll(successMessage);

        paiedListAdapter.setData(mRows, 1, text);

        paiedListAdapter.notifyDataSetChanged();

    }

    @Override
    public void showError(String errorMessage) {

    }

    @Override
    public void setRequestSuccess(SearchRecommend msg) {
        searchEditText.setHint(msg.hot_search);

        mList.clear();
        for (int i = 0; i < msg.hot_recommend.size(); i++) {
            mList.add(i, msg.hot_recommend.get(i).keyword);
        }
        mHotTalk.setAdapter(new TagAdapter(mList) {
            @Override
            public View getView(FlowLayout parent, int position, Object o) {
                TextView textView = (TextView) LayoutInflater.from(context).inflate(R.layout.keyworlditem, mHotTalk, false);
                textView.setText(mList.get(position));
                return textView;
            }
        });


    }

    @Override
    public void setRequestFail() {

    }

    public void addName(String name) {
        if (!name.isEmpty()) {
            mACache = ACache.get(context);
            //去除缓存中的搜索历史对象
            Object searchdata = mACache.getAsObject("SEARCHDATA");
            //将对象转成需要的数据类型
            if (searchdata != null) {
                mCacheList = (List<String>) JSONObject.parse(searchdata.toString());
            }
            if (mCacheList != null && !mCacheList.isEmpty() && mCacheList.size() > 0) {
                if (mCacheList.size() >= 8) {
                    if (!mCacheList.contains(name)) {
                        //如果list的长度大于8，则移除最早放如的
                        mCacheList.remove(mCacheList.size() - 1);
                        //将数据添加进list中

                        mCacheList.add(0, name);
                        //将list转成jsonarry
                        JSONArray array = JSONArray.parseArray(JSON.toJSONString(mCacheList));
                        //将数据存入缓存中
                        mACache.put("SEARCHDATA", array);
                    }

                } else {
                    if (!mCacheList.contains(name)) {
                        mCacheList.add(0, name);
                        //将list转成jsonarry
                        JSONArray array = JSONArray.parseArray(JSON.toJSONString(mCacheList));
                        //将数据存入缓存中
                        mACache.put("SEARCHDATA", array);
                    }
                }
            } else {

                mCacheList.add(0, name);
                //将list转成jsonarry
                JSONArray array = JSONArray.parseArray(JSON.toJSONString(mCacheList));
                //将数据存入缓存中
                mACache.put("SEARCHDATA", array);
            }
        }
//可视的时候用
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

}
