package sinia.com.baihangeducation.mine.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.framwork.utils.ProgressActivityUtils;
import com.example.framwork.utils.SPUtils;
import com.example.framwork.widget.ProgressActivity;
import com.example.framwork.widget.superrecyclerview.recycleview.SuperRecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.supplement.base.BaseActivity;
import sinia.com.baihangeducation.mine.adapter.ChoiceMajor_1_Adapter;

import com.mcxtzhang.swipemenulib.info.bean.College;
import com.mcxtzhang.swipemenulib.info.bean.EducationInfo;
import com.mcxtzhang.swipemenulib.info.bean.Marjor;

import sinia.com.baihangeducation.mine.presenter.GetEditEducationInfoPresenter;
import sinia.com.baihangeducation.mine.view.IGetEducationView;
import sinia.com.baihangeducation.supplement.base.Goto;
import sinia.com.baihangeducation.supplement.sortrecycleview.ClearEditText;
import sinia.com.baihangeducation.supplement.sortrecycleview.PinyinComparator;
import sinia.com.baihangeducation.supplement.sortrecycleview.PinyinUtils;
import sinia.com.baihangeducation.supplement.sortrecycleview.SideBar;
import sinia.com.baihangeducation.supplement.sortrecycleview.SortAdapter;
import sinia.com.baihangeducation.supplement.sortrecycleview.SortModel;

/**
 * Created by Administrator on 2018/3/30.
 */
public class MyResumeEditEducationExpChoiceMajor_1Acitvity extends BaseActivity implements IGetEducationView {

    private RecyclerView mRecyclerView;
    private SideBar sideBar;
    private TextView dialog;
    private SortAdapter adapter;
    private ClearEditText mClearEditText;
    LinearLayoutManager manager;
    private String mCurrentPage = "1";
    private int mCurrentTime = 0;

    private List<SortModel> SourceDateList = new ArrayList<>();

    /**
     * 根据拼音来排列RecyclerView里面的数据类
     */
    private PinyinComparator pinyinComparator;


    private GetEditEducationInfoPresenter presenter;
    private List<SortModel> mSortList;
    private List<Marjor> mList;
    private RelativeLayout mNotData;


    @Override
    public int initLayoutResID() {
        return R.layout.sort_activity;
    }

    @Override
    protected void initView() {
        mCurrentPage = "1";
        mCurrentTime = 0;

        pinyinComparator = new PinyinComparator();

        sideBar = (SideBar) findViewById(R.id.sideBar);
        dialog = (TextView) findViewById(R.id.dialog);
        mNotData = (RelativeLayout) findViewById(R.id.not_data);
        sideBar.setTextView(dialog);

        //设置右侧SideBar触摸监听
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                //该字母首次出现的位置
                int position = adapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    manager.scrollToPositionWithOffset(position, 0);
                }
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        adapter = new SortAdapter(context, SourceDateList);
        mRecyclerView.setAdapter(adapter);
        manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);

        //item点击事件
        adapter.setOnItemClickListener(new SortAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                AppConfig.SCHOOLMAGOR = ((SortModel) adapter.getItem(position)).getName();
                SPUtils.getInstance().put(AppConfig.getContext(), "SCHOOLMAGOR", ((SortModel) adapter.getItem(position)).getName());

                for (int i = 0; i < mList.size(); i++) {
                    if (mList.get(i).major_name.equals(((SortModel) adapter.getItem(position)).getName())) {
                        Goto.toEditMyResumeEducationExpChoice_2Major(context, String.valueOf(mList.get(i).major_id));
                    }
                }

                MyResumeEditEducationExpChoiceMajor_1Acitvity.this.finish();
            }
        });
        mClearEditText = (ClearEditText) findViewById(R.id.filter_edit);

        //根据输入框输入值的改变来过滤搜索
        mClearEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
                filterData(s.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }


        });


    }

    @Override
    protected void initData() {
        mCommonTitle.setCenterText(R.string.choicmajor);
        mCommonTitle.setBackground(getResources().getDrawable(R.color.white));

        presenter = new GetEditEducationInfoPresenter(context, this);
        presenter.getMajor();
    }

    /**
     * 为RecyclerView填充数据
     *
     * @param date
     * @return
     */
    private List<SortModel> filledData(List<Marjor> date) {
        if (mSortList == null)
            mSortList = new ArrayList<>();

        for (int i = 0; i < date.size(); i++) {
            SortModel sortModel = new SortModel();
            sortModel.setName(date.get(i).major_name);
            //汉字转换成拼音
            String pinyin = PinyinUtils.getPingYin(date.get(i).major_name);
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                sortModel.setLetters(sortString.toUpperCase());
            } else {
                sortModel.setLetters("#");
            }

            mSortList.add(sortModel);
        }
        return mSortList;

    }

    /**
     * 根据输入框中的值来过滤数据并更新RecyclerView
     *
     * @param filterStr
     */
    private void filterData(String filterStr) {
        List<SortModel> filterDateList = new ArrayList<>();

        if (TextUtils.isEmpty(filterStr)) {
            filterDateList = SourceDateList;
        } else {
            filterDateList.clear();
            for (SortModel sortModel : SourceDateList) {
                String name = sortModel.getName();
                if (name.indexOf(filterStr.toString()) != -1 ||
                        PinyinUtils.getFirstSpell(name).startsWith(filterStr.toString())
                        //不区分大小写
                        || PinyinUtils.getFirstSpell(name).toLowerCase().startsWith(filterStr.toString())
                        || PinyinUtils.getFirstSpell(name).toUpperCase().startsWith(filterStr.toString())
                        ) {
                    filterDateList.add(sortModel);
                }
            }
        }

        // 根据a-z进行排序
        Collections.sort(filterDateList, pinyinComparator);
        adapter.updateList(filterDateList);
    }

    @Override
    public String getPage() {
        return "1";
    }

    @Override
    public String getItenmNum() {
        return "200";
    }

    @Override
    public String getFatherId() {
        return null;
    }

    @Override
    public void getEducationSchoolSuccess(List<College> list, int maxpage) {


    }

    @Override
    public void getEducationMajor_1_Success(List<Marjor> list, int maxpage) {
        if (list.size() < 1) {
            mNotData.setVisibility(View.VISIBLE);
        } else {
            mNotData.setVisibility(View.GONE);
        }

        List<SortModel> sSourceDateList = filledData(list);
        mCurrentTime = mCurrentTime + 1;
        // 根据a-z进行排序源数据
        Collections.sort(sSourceDateList, pinyinComparator);

        mList = list;
        //RecyclerView社置manager
        SourceDateList.addAll(sSourceDateList);
        adapter.notifyDataSetChanged();

        mCurrentPage = "2";
        if (presenter != null && maxpage > 200 * mCurrentTime)
            presenter.getMajor();
    }

    @Override
    public void getEducationMajor_2_Success(List<Marjor> list, int maxpage) {

    }

    @Override
    public void getEducationSuccess(List<EducationInfo> educationInfos) {


    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
