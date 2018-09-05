package sinia.com.baihangeducation.newcampus.tabs.addfriend.view;

import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.newcampus.tabs.addfriend.adapter.AddFriendAdapter;
import sinia.com.baihangeducation.newcampus.tabs.addfriend.interfaces.AddFriendListContract;
import sinia.com.baihangeducation.newcampus.tabs.addfriend.model.AddFriendListModel;
import sinia.com.baihangeducation.newcampus.tabs.addfriend.present.AddFriendListPresent;
import sinia.com.baihangeducation.newcampus.tabs.searchfriend.adapter.SearchFriendAdapter;
import sinia.com.baihangeducation.newcampus.tabs.searchfriend.bean.SearchFriendListBean;
import sinia.com.baihangeducation.newcampus.tabs.searchfriend.view.SearchFriendListActivity;
import sinia.com.baihangeducation.supplement.base.BaseActivity;
import sinia.com.baihangeducation.supplement.base.Goto;

public class AddFriendListActivity extends BaseActivity implements AddFriendListContract.View {

    private RecyclerView mAutoLoadRecyclerView;
    private AddFriendListPresent mHotFunListPresent;

    private AddFriendAdapter hotFunAdapter;
    private List<SearchFriendListBean.Firendes> mList = new ArrayList<>();

    private RelativeLayout mRelativeLayout;
    private List<SearchFriendListBean.Firendes> firendes;
    private ImageView back;

    @Override
    public int initLayoutResID() {
        return R.layout.add_friend_list_activity;
    }

    @Override
    protected void initData() {
        mHotFunListPresent = new AddFriendListPresent(new AddFriendListModel(context), this);
        mHotFunListPresent.getHotFunList(1, 0, "");
    }

    @Override
    protected void initView() {
        mAutoLoadRecyclerView = findViewById(R.id.recyclerView);
        back = findViewById(R.id.back);
        mRelativeLayout = findViewById(R.id.search);
        hotFunAdapter = new AddFriendAdapter(context);
        mAutoLoadRecyclerView.setAdapter(hotFunAdapter);
        final GridLayoutManager manager = new GridLayoutManager(context, 1);
        mAutoLoadRecyclerView.setLayoutManager(manager);
        mAutoLoadRecyclerView.setItemAnimator(new DefaultItemAnimator());
//        hotFunAdapter.setData(mList);
        mRelativeLayout.setOnClickListener(this);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddFriendListActivity.this.finish();
            }
        });
        hotFunAdapter.setItemClickListener(new AddFriendAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
//                Goto.toHomePageAgain(firendes.get(position), context);
            }
        });
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);

        switch (v.getId()) {
            case R.id.search:

                Intent intent = new Intent(this, SearchFriendListActivity.class);
                startActivity(intent);

                break;
        }
    }

    //(1：推荐好友 2：猜你喜欢 3：附近的人 4：可能认识的人) )
    @Override
    public void showHotFunList(List<SearchFriendListBean> successMessage, int type) {


        firendes = new ArrayList<>();
        for (int i = 0; i < successMessage.size(); i++) {
            firendes.addAll(successMessage.get(i).list);
        }
        hotFunAdapter.setData(firendes);
        hotFunAdapter.notifyDataSetChanged();
//        mList.clear();
//        mList = successMessage.list;
//
//        hotFunAdapter.setData(successMessage.list);
//        hotFunAdapter.notifyDataSetChanged();


    }

    @Override
    public void upDateHotFunList(SearchFriendListBean successMessage) {

    }

    @Override
    public void showError(String errorMessage) {

    }
}
