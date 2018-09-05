package com.mcxtzhang.swipemenulib.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.framwork.adapter.AnimationType;
import com.example.framwork.adapter.SuperBaseAdapter;
import com.example.framwork.widget.superrecyclerview.recycleview.ProgressStyle;
import com.example.framwork.widget.superrecyclerview.recycleview.SuperRecyclerView;
import com.mcxtzhang.swipemenulib.utils.ACache;

/**
 * Created by Administrator on 2018.02.24.
 */

public abstract class BaseFragment extends Fragment implements View.OnClickListener {

    private ACache mCache;
    private int layoutResID;
    protected static Activity context = null;
    protected View rootView = null;
    protected LinearLayoutManager linearLayoutManager;
    private boolean isCreated = false;



    protected void switchoverChangeData() {
    }

    private static final String STATE_SAVE_IS_HIDDEN = "STATE_SAVE_IS_HIDDEN";
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            boolean isSupportHidden = savedInstanceState.getBoolean(STATE_SAVE_IS_HIDDEN);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            if (isSupportHidden) {
                ft.hide(this);
            } else {
                ft.show(this);
            }
            ft.commit();


        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        outState.putBoolean(STATE_SAVE_IS_HIDDEN, isHidden());
    }





        @Nullable
        @Override
        public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup
        container, @Nullable Bundle savedInstanceState){


            isCreated = true;
            context = getActivity();
            rootView = inflater.inflate(initLayoutResID(), container, false);


            initView();
            initData();

            // TODO 自动生成的方法存根
            if (rootView != null) {
                ViewGroup parent = (ViewGroup) rootView.getParent();
                if (parent != null) {
                    parent.removeView(rootView);
                }
                return rootView;
            }
            return rootView = inflater.inflate(initLayoutResID(), container, false);


        }

        /**
         * 初始化数据
         */
        protected abstract void initData ();

        /**
         * 初始化控件
         */
        protected abstract void initView ();


        /**
         * 初始化layout，此方法里只能写layoutResID = R.layout.xxx
         */
        public int initLayoutResID () {
            return layoutResID;
        }

        protected <T extends View> T $ ( int id){
            return (T) rootView.findViewById(id);
        }

        protected void initSwipeLayout (SwipeRefreshLayout
        mSwipeLayout, SwipeRefreshLayout.OnRefreshListener refreshListener){
            mSwipeLayout.setOnRefreshListener(refreshListener);
            // 设置下拉圆圈上的颜色，蓝色、绿色、橙色、红色
//        mSwipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright);
//        mSwipeLayout.setDistanceToTriggerSync(400);// 设置手指在屏幕下拉多少距离会触发下拉刷新
//        mSwipeLayout.setProgressBackgroundColorSchemeResource(android.R.color.white); // 设定下拉圆圈的背景
//        mSwipeLayout.setSize(SwipeRefreshLayout.LARGE); // 设置圆圈的大小
        }

        protected void showSwipeRefreshLayout ( final SwipeRefreshLayout swipeRefreshLayout){
            if (swipeRefreshLayout != null) {
                swipeRefreshLayout.post(new Runnable() {

                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(true);
                    }
                });
            }
        }

        protected void initRecyclerView (SuperRecyclerView recyclerView, SuperBaseAdapter
        adapter, SuperRecyclerView.LoadingListener loadingListener){
            linearLayoutManager = new LinearLayoutManager(context);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setRefreshEnabled(false);
            recyclerView.setLoadMoreEnabled(true);
            recyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallClipRotate);//上拉加载的样式
            recyclerView.setLoadingListener(loadingListener);
            adapter.setItemAnimation(AnimationType.SLIDE_FROM_LEFT);//设置显示的动画
            adapter.setShowItemAnimationEveryTime(false);//是否每次都会执行动画,默认是false,该方便测试
            recyclerView.setAdapter(adapter);
        }

        protected void hideSwipeRefreshLayout ( final SwipeRefreshLayout swipeRefreshLayout){
            if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()) {
                swipeRefreshLayout.post(new Runnable() {

                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        }

        /**
         * 显示加载progress
         */
        protected void showProgress () {
//
        }

        /**
         * 隐藏加载progress
         */
        protected void hideProgress () {

        }

        /**
         * 隐藏软键盘
         */
        protected void hideSoftInput (EditText view){
            InputMethodManager mInputMethodManager = (InputMethodManager) context.getSystemService(context.INPUT_METHOD_SERVICE);
            mInputMethodManager
                    .hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        /**
         * 打开软键盘
         */
        protected void showSoftInput (EditText view){
            InputMethodManager mInputMethodManager = (InputMethodManager) context.getSystemService(context.INPUT_METHOD_SERVICE);
            mInputMethodManager
                    .showSoftInput(view, InputMethodManager.SHOW_FORCED);
        }



}
