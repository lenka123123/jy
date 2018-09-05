package sinia.com.baihangeducation.find.campus.adapter;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.framwork.adapter.AnimationType;
import com.example.framwork.adapter.SuperBaseAdapter;
import com.example.framwork.utils.DensityUtil;
import com.example.framwork.utils.ProgressActivityUtils;
import com.example.framwork.widget.ProgressActivity;
import com.example.framwork.widget.superrecyclerview.recycleview.ProgressStyle;
import com.example.framwork.widget.superrecyclerview.recycleview.SuperRecyclerView;

import java.util.List;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.find.adapter.ShareEveryDayDetailMessageAdapter;
import com.mcxtzhang.swipemenulib.info.bean.ShareEveryDayDetailCommentInfo;

/**
 * Created by Administrator on 2018/4/25.
 */

public class MessageDialog extends Dialog {
    private Context context;
    private View view;
    private List<ShareEveryDayDetailCommentInfo> mData;
    private SwipeRefreshLayout.OnRefreshListener refreshListener;
    private SuperRecyclerView.LoadingListener loadingListener;

    private SuperRecyclerView rvContainer;
    private ProgressActivity progressActivity;
    private SwipeRefreshLayout swipeContainer;
    private ProgressActivityUtils progressActivityUtils;
    private ShareEveryDayDetailMessageAdapter mShareEveryDayDetailMessageAdapter;
    //    private List<HomePartTimeInfo> mList;
    private boolean isLoadMore = false;

    public MessageDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    public MessageDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
    }

    protected MessageDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.context = context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = View.inflate(context, R.layout.messagedialog, null);
        setContentView(view);

        Window win = getWindow();
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.gravity = Gravity.CENTER;
        lp.height = DensityUtil.dip2px(context, 400);
        lp.width = DensityUtil.dip2px(context, 300);
        win.setAttributes(lp);

        initView();
        initData();
    }

    private void initData() {
        mShareEveryDayDetailMessageAdapter = new ShareEveryDayDetailMessageAdapter(context, mData);
        swipeContainer.setOnRefreshListener(refreshListener);
        if (swipeContainer != null) {
            swipeContainer.post(new Runnable() {

                @Override
                public void run() {
                    swipeContainer.setRefreshing(true);
                }
            });
        }
        progressActivityUtils = new ProgressActivityUtils(context, progressActivity);
        initRecyclerView(rvContainer, mShareEveryDayDetailMessageAdapter, loadingListener);
    }

    private void initView() {
        rvContainer = view.findViewById(R.id.rv_container);
        progressActivity = view.findViewById(R.id.progressActivity);
        swipeContainer = view.findViewById(R.id.swipe_container);
    }

    protected void initRecyclerView(SuperRecyclerView recyclerView, SuperBaseAdapter adapter, SuperRecyclerView.LoadingListener loadingListener) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setRefreshEnabled(false);
        recyclerView.setLoadMoreEnabled(true);
        recyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallClipRotate);//上拉加载的样式
        recyclerView.setLoadingListener(loadingListener);
        adapter.setItemAnimation(AnimationType.SLIDE_FROM_LEFT);//设置显示的动画
        adapter.setShowItemAnimationEveryTime(false);//是否每次都会执行动画,默认是false,该方便测试
        recyclerView.setAdapter(adapter);
    }

    public void setData(List<ShareEveryDayDetailCommentInfo> data) {
        this.mData = data;
    }

    public void OnRefreshListener(SwipeRefreshLayout.OnRefreshListener refreshListener) {
        this.refreshListener = refreshListener;
    }

    public void LoadingListener(SuperRecyclerView.LoadingListener loadingListener) {
        this.loadingListener = loadingListener;
    }

    public void hideSwipeRefreshLayout() {
        if (swipeContainer != null && swipeContainer.isRefreshing()) {
            swipeContainer.post(new Runnable() {

                @Override
                public void run() {
                    swipeContainer.setRefreshing(false);
                }
            });
        }
    }


    public void showDialogEmptry() {
        progressActivityUtils.showEmptry("暂无数据");
    }

    public void isLodeMore(boolean isLoad) {
        if (rvContainer != null)
            rvContainer.setLoadMoreEnabled(isLoad);
    }

    public void isLodeMoreTag(boolean isLodeMore) {
        this.isLoadMore = isLodeMore;
    }

    public void showDialogData(List<ShareEveryDayDetailCommentInfo> data) {
        if (progressActivity != null) {
            progressActivityUtils.showContent();
            if (!isLoadMore) {
                mData.clear();
            }
            mData.addAll(data);
            mShareEveryDayDetailMessageAdapter.notifyDataSetChanged();
        }
    }
}
