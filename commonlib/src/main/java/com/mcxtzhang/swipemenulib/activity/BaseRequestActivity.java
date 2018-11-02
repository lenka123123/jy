package com.mcxtzhang.swipemenulib.activity;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.os.MessageQueue;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.baidu.mobstat.StatService;
import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;
import com.githang.statusbar.StatusBarCompat;
import com.mcxtzhang.swipemenulib.CommonApplictaion;
import com.mcxtzhang.swipemenulib.R;
import com.mcxtzhang.swipemenulib.titlebar.ScreenUtils;

/**
 * author:conanaiflj
 * date:2018/5/25 0025
 * description:基础网络请求页面
 */

@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
public abstract class BaseRequestActivity extends BaseLifeCycleActivity {

    private View mStatusBarView;
    private ViewGroup mDecorViewGroup;
    private Window mWindow;
    protected Context mContext;
    protected int mClickTab = 0;
    private View statusBarView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        requestWindowFeature(Window.FEATURE_NO_TITLE);  //去掉标题栏（一定要在setContentView()之后）
        setContentView(setContentID());


        StatService.start(this);////埋点二个
        mContext = this;
        StatusBarCompat.setStatusBarColor(this, Color.WHITE, true);
//        mWindow = getWindow();
//        mWindow.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        mWindow.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);//设置全屏
//        mDecorViewGroup = (ViewGroup) mWindow.getDecorView();
//        mStatusBarView = new View(mWindow.getContext());
//        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, ScreenUtils.dp2PxInt(mContext, 20));
//        params.gravity = Gravity.TOP;
//        mStatusBarView.setLayoutParams(params);
//        mStatusBarView.setBackgroundColor(Color.WHITE);
//        mDecorViewGroup.addView(mStatusBarView);
        if (savedInstanceState != null && savedInstanceState.getBoolean("isMainActivityDestroy", false)) {
            remove();

        }
        initView();
        initData();
        initAct();
    }

    /**
     * 获取状态栏高度
     *
     * @param context context
     * @return 状态栏高度
     */
    private static int getStatusBarHeight(Context context) {
        // 获得状态栏高度
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return context.getResources().getDimensionPixelSize(resourceId);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("isMainActivityDestroy", true);
    }

    public void remove() {
    }

    public void setMargins(View v, int tabChange) {
        mClickTab = tabChange;
//        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
//            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
//            if (isChange) {
//                p.setMargins(0, getStatusBarHeight(mContext), 0, 0);
//            } else {
//                p.setMargins(0, 0, 0, 0);
//            }
//
//            v.requestLayout();
//        }
    }

    protected void changeTitle(boolean isFullScreen, boolean showImage) {

//        if (isFullScreen) {
//            mDecorViewGroup.removeView(mStatusBarView);
//            mWindow.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//
//            mWindow.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);//设置全屏
//
//            return;
//        } else {
//            //    mWindow.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);//设置全屏
//            mDecorViewGroup.removeView(mStatusBarView);
//            if (showImage) {
////                Bitmap bm = BitmapFactory.decodeResource(getResources(), getDrawbale());
////                mStatusBarView.setBackground(new BitmapDrawable(getResources(), rotaingImageView(180, bm)));
////                mDecorViewGroup.addView(mStatusBarView);
//            } else {
//
//                mStatusBarView.setBackground(getResources().getDrawable(R.color.red_fa3e3e));
//                mDecorViewGroup.addView(mStatusBarView);
//            }
//
//        }

    }


    protected abstract void initView();

    protected abstract void initData();

    protected abstract void initAct();

    protected abstract int setContentID();

    /**
     * 页面销毁时取消请求
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (Util.isOnMainThread()) {
            Glide.with(CommonApplictaion.getContext()).pauseRequests();
        }
    }


}
