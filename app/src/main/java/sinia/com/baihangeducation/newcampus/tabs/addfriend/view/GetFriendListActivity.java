package sinia.com.baihangeducation.newcampus.tabs.addfriend.view;

import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.newcampus.tabs.addfriend.adapter.AddFriendAdapter;
import sinia.com.baihangeducation.newcampus.tabs.addfriend.adapter.GetFriendAdapter;
import sinia.com.baihangeducation.newcampus.tabs.addfriend.interfaces.AddFriendListContract;
import sinia.com.baihangeducation.newcampus.tabs.addfriend.model.AddFriendListModel;
import sinia.com.baihangeducation.newcampus.tabs.addfriend.model.GetriendListModel;
import sinia.com.baihangeducation.newcampus.tabs.addfriend.present.AddFriendListPresent;
import sinia.com.baihangeducation.newcampus.tabs.addfriend.present.GetFriendListPresent;
import sinia.com.baihangeducation.newcampus.tabs.searchfriend.bean.GetFriendListBean;
import sinia.com.baihangeducation.newcampus.tabs.searchfriend.bean.SearchFriendListBean;
import sinia.com.baihangeducation.newcampus.tabs.searchfriend.view.SearchFriendListActivity;
import sinia.com.baihangeducation.supplement.base.BaseActivity;
import sinia.com.baihangeducation.supplement.base.Goto;

public class GetFriendListActivity extends BaseActivity implements AddFriendListContract.View {

    private RecyclerView mAutoLoadRecyclerView;
    private GetFriendListPresent mHotFunListPresent;

    private GetFriendAdapter hotFunAdapter;
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
        mHotFunListPresent = new GetFriendListPresent(new GetriendListModel(this), this);
        mHotFunListPresent.getHotFunList(1, 0, "");
    }

    @Override
    protected void initView() {
        mAutoLoadRecyclerView = findViewById(R.id.recyclerView);
        back = findViewById(R.id.back);
        mRelativeLayout = findViewById(R.id.search);
        hotFunAdapter = new GetFriendAdapter(context);
        mAutoLoadRecyclerView.setAdapter(hotFunAdapter);
        final GridLayoutManager manager = new GridLayoutManager(context, 1);
        mAutoLoadRecyclerView.setLayoutManager(manager);
        mAutoLoadRecyclerView.setItemAnimator(new DefaultItemAnimator());
//        hotFunAdapter.setData(mList);
        mRelativeLayout.setOnClickListener(this);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetFriendListActivity.this.finish();
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

    public void showGetFunList(GetFriendListBean successMessage) {



        hotFunAdapter.setData(successMessage);
        hotFunAdapter.notifyDataSetChanged();
//        mList.clear();
//        mList = successMessage.list;
//
//        hotFunAdapter.setData(successMessage.list);
//        hotFunAdapter.notifyDataSetChanged();


    }

    @Override
    public void showHotFunList(List<SearchFriendListBean> successMessage, int type) {

    }

    @Override
    public void upDateHotFunList(SearchFriendListBean successMessage) {

    }

    @Override
    public void showError(String errorMessage) {

    }
}
