package sinia.com.baihangeducation.mine.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.example.framwork.utils.ProgressActivityUtils;
import com.example.framwork.utils.SpCommonUtils;
import com.example.framwork.widget.ProgressActivity;
import com.example.framwork.widget.superrecyclerview.recycleview.SuperRecyclerView;

import java.util.ArrayList;
import java.util.List;

import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.mine.adapter.DemoSwipeAdapter;
import sinia.com.baihangeducation.mine.adapter.FragmentMessageAdapter;

import com.mcxtzhang.swipemenulib.customview.recycleitemdeltet.view.YRecyclerView;
import com.mcxtzhang.swipemenulib.info.bean.FragmentMessageInfo;
import com.mcxtzhang.swipemenulib.info.bean.FragmentMessageListInfo;

import sinia.com.baihangeducation.supplement.base.BaseActivity;
import sinia.com.baihangeducation.mine.presenter.FragmentMessagePresenter;
import sinia.com.baihangeducation.mine.view.FragmentMessageView;

/**
 * Created by Administrator on 2018/4/28.
 */

public class FragmentMessageActivity extends BaseActivity implements FragmentMessageView {


    private List<FragmentMessageInfo> mList = new ArrayList<>();
    private YRecyclerView ycl;
    private FragmentMessagePresenter mFragmentMessagePresenter;
    private DemoSwipeAdapter demoAdapter;


    @Override
    public int initLayoutResID() {
        return R.layout.fragment_message;
    }

    @Override
    protected void initView() {
        ycl = (YRecyclerView) findViewById(R.id.recycler_view);

        mCommonTitle.setCenterText(R.string.mine_message);
        mCommonTitle.setBackgroundColor(Color.WHITE);
        mCommonTitle.getRightTxt().setText("一键已读");
        mCommonTitle.getRightTxt().setTextColor(getResources().getColor(R.color.red_fa3e3e));

        mCommonTitle.getRightTxt().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFragmentMessagePresenter.setMessageRead(0);
                if (demoAdapter != null)
                    demoAdapter.setDataRead();
            }
        });
    }

    @Override
    protected void initData() {
        countpage = 1;
        mFragmentMessagePresenter = new FragmentMessagePresenter(context, this);
        mFragmentMessagePresenter.getMessage();

        demoAdapter = new DemoSwipeAdapter(mList, this);
        ycl.setLayoutManager(new LinearLayoutManager(this));
        ycl.setAdapter(demoAdapter);
        ycl.setRefreshAndLoadMoreListener(new YRecyclerView.OnRefreshAndLoadMoreListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        demoAdapter.addReFreshData();
                        ycl.setReFreshComplete();
                    }
                }, 2500);
            }

            @Override
            public void onLoadMore() {
                Log.i("加载更多", "000");
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        demoAdapter.addRLoadMOreData();
                        ycl.setloadMoreComplete();
                    }
                }, 2500);
            }
        });
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    private int countpage = 1;
    private int itemnum = 20;


    public void setMessageReadSuccess() {

//        mCommonTitle.getRightTxt().setVisibility(View.GONE);
//        demoAdapter.notifyDataSetChanged();
        SpCommonUtils.put(context, AppConfig.FINAL_NO_READ_NUM, "0");
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
    public void getMessageSuccess(FragmentMessageListInfo mInfo, int maxpage) {
        if (mInfo.list.size() == 0) {
//            progressActivityUtils.showEmptry("暂无数据");
        } else {
//            progressActivityUtils.showContent();
            countpage++;
            if (maxpage == 1 || countpage > maxpage) {
                ycl.setLoadMoreEnabled(false);
            } else {
                ycl.setLoadMoreEnabled(true);
            }
//            if (!isLoadMore) {
//                mList.clear();
//            }

            demoAdapter.setData(mInfo.list);

            // TODO: 2018/9/4 0004    mFragmentMessageAdapter.notifyDataSetChanged();
        }
    }

    public void removeItem(FragmentMessageInfo fragmentMessageInfo, int size) {
        mFragmentMessagePresenter.setMessageDrop(fragmentMessageInfo.system_message_id);

        if (size == 10) {
            countpage = 1;
            getServerData();
        }
    }

    /**
     * 获取数据
     */
    public void getServerData() {
        mFragmentMessagePresenter.getMessage();
    }
}
