package sinia.com.baihangeducation.supplement.base;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.PopupWindow;

import com.amap.api.location.AMapLocationClientOption;
import com.baidu.mobstat.StatService;
import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;
import com.example.framwork.adapter.AnimationType;
import com.example.framwork.adapter.SuperBaseAdapter;
import com.example.framwork.ricyclerview.DividerGridItemDecoration;
import com.example.framwork.utils.UserInfo;
import com.example.framwork.widget.customtoolbar.CommonTitle;
import com.example.framwork.widget.superrecyclerview.recycleview.ProgressStyle;
import com.example.framwork.widget.superrecyclerview.recycleview.SuperRecyclerView;
import com.githang.statusbar.StatusBarCompat;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.mcxtzhang.swipemenulib.base.CheckPermissionsActivity;
import com.umeng.socialize.UMShareAPI;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import com.mcxtzhang.swipemenulib.info.bean.BaseBean;
import com.mcxtzhang.swipemenulib.interfaces.BaseFilter;
import com.mcxtzhang.swipemenulib.widget.CommonFilterPop;

import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.MyApplication;
import sinia.com.baihangeducation.R;

/**
 * Created by Administrator on 2018.02.24.
 */

public abstract class BaseActivity extends CheckPermissionsActivity implements View.OnClickListener {
    protected LinearLayoutManager linearLayoutManager;
    protected GridLayoutManager gridLayoutManager;
    /**
     * 筛选pop
     */
    private CommonFilterPop mPopupWindow;
    protected FragmentManager mFragmentManager;
    protected int layoutResID;
    protected static Activity context;
    protected KProgressHUD progressHUD;
    protected static CommonTitle mCommonTitle;   //头部布局
    protected String lng;
    protected String lat;
    private Window window;
    protected View statusBarView;
    public Bundle savedInstanceState;


    @Override
    protected void onRestart() {
        super.onRestart();
        if (mCommonTitle != null) {
            mCommonTitle.setBackgroundColor(getResources().getColor(R.color.white));
            mCommonTitle.getBackground().setAlpha(0);
            mCommonTitle.getCenter_txt().setTextColor(Color.BLACK);
            mCommonTitle.getCenter_txt().setTextSize(20);
            mCommonTitle.getCenter_txt().setTypeface(Typeface.DEFAULT);
            mCommonTitle.getLeftRes().setImageDrawable(getResources().getDrawable(R.drawable.back_black));
            mCommonTitle.getLeftRes().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mFragmentManager = getSupportFragmentManager();
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState;
        context = this;
        StatusBarCompat.setStatusBarColor(this, Color.WHITE, true);
        setContentView(initLayoutResID());
        StatService.start(this);//埋点二个

        ViewGroup contentFrameLayout = findViewById(Window.ID_ANDROID_CONTENT);
        View parentView = contentFrameLayout.getChildAt(0);
        if (parentView != null && Build.VERSION.SDK_INT >= 14) {
            parentView.setFitsSystemWindows(true);
        }
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        mCommonTitle = findViewById(R.id.title_bar);
        if (mCommonTitle != null) {
            mCommonTitle.setBackgroundColor(getResources().getColor(R.color.white));
            mCommonTitle.getBackground().setAlpha(0);
            mCommonTitle.getCenter_txt().setTextColor(Color.BLACK);
            mCommonTitle.getCenter_txt().setTextSize(20);
            mCommonTitle.getCenter_txt().setTypeface(Typeface.DEFAULT);
            mCommonTitle.getLeftRes().setImageDrawable(getResources().getDrawable(R.drawable.back_black));
            mCommonTitle.getLeftRes().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }

        progressHUD = KProgressHUD.create(this).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE);
        initView();
        initData();
    }


    /**
     * 获取经度
     *
     * @return
     */
    public String getLng() {
        return MyApplication.lng;
    }

    /**
     * 获取纬度
     *
     * @return
     */
    public String getLat() {
        return MyApplication.lat;
    }

    public UserInfo getUserInfo() {
        return MyApplication.userInfo;
    }

    /**
     * 获取纬度
     *
     * @return
     */
    public String getAdCode() {
        return MyApplication.adCode;
    }

    /**
     * 默认的定位参数
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    private AMapLocationClientOption getDefaultOption() {
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Device_Sensors);//设置定位模式为AMapLocationMode.Device_Sensors，仅设备模式。
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);//设置定位模式为AMapLocationMode.Battery_Saving，低功耗模式。
        mOption.setMockEnable(true);//设置是否允许模拟位置,默认为true，允许模拟位置
        mOption.setGpsFirst(false);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.setHttpTimeOut(30000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.setInterval(60 * 1000);//可选，设置定位间隔。默认为2秒
        mOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是true
        mOption.setOnceLocation(false);//可选，设置是否单次定位。默认是false
        mOption.setOnceLocationLatest(false);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        mOption.setSensorEnable(false);//可选，设置是否使用传感器。默认是false
        mOption.setWifiScan(true); //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        mOption.setLocationCacheEnable(true); //可选，设置是否使用缓存定位，默认为true
        return mOption;
    }

    protected void hideSwipeRefreshLayout(final SwipeRefreshLayout swipeRefreshLayout) {
        if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.post(new Runnable() {

                @Override
                public void run() {
                    swipeRefreshLayout.setRefreshing(false);
                }
            });
        }
    }

    public Bitmap rotaingImageView(int angle, Bitmap bitmap) {
        // 旋转图片 动作
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        if (resizedBitmap != bitmap && bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
            bitmap = null;
        }
        return resizedBitmap;
    }


    private int getStatusBarHeight(Context context) {
        int statusBarHeight = 0;
        Resources res = context.getResources();
        int resourceId = res.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = res.getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 初始化控件
     */
    protected abstract void initView();

    /**
     * 初始化布局
     *
     * @return
     */
    public int initLayoutResID() {
        return layoutResID;
    }

    /**
     * 简化findViewById，写法为$(R.id.xxx)
     *
     * @param id
     * @param <T>
     * @return
     */
    protected <T extends View> T $(int id) {
        return (T) super.findViewById(id);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
    }

    /**
     * 点击事件
     */

    protected void showProgress() {
        if (progressHUD != null)
            progressHUD.show();
    }

    protected void hideProgress() {
        if (progressHUD != null) {
            progressHUD.dismiss();
        }
    }

    protected void initRecyclerView(SuperRecyclerView recyclerView, SuperBaseAdapter adapter, SuperRecyclerView.LoadingListener loadingListener) {
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

    public void initRecyclerViewGrid(SuperRecyclerView recyclerView, SuperBaseAdapter adapter, SuperRecyclerView.LoadingListener loadingListener, int columns, int span) {
        gridLayoutManager = new GridLayoutManager(context, columns);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setRefreshEnabled(false);
        recyclerView.setLoadMoreEnabled(true);
        recyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallClipRotate);//上拉加载的样式
        recyclerView.setLoadingListener(loadingListener);
        adapter.setItemAnimation(AnimationType.SLIDE_FROM_LEFT);//设置显示的动画
        adapter.setShowItemAnimationEveryTime(false);//是否每次都会执行动画,默认是false,该方便测试
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerGridItemDecoration(context, span));
    }

    // 初始化SwipeLayout
    protected void initSwipeLayout(SwipeRefreshLayout mSwipeLayout, SwipeRefreshLayout.OnRefreshListener refreshListener) {
        mSwipeLayout.setOnRefreshListener(refreshListener);
        // 设置下拉圆圈上的颜色，蓝色、绿色、橙色、红色
//        mSwipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright);
//        mSwipeLayout.setDistanceToTriggerSync(400);// 设置手指在屏幕下拉多少距离会触发下拉刷新
//        mSwipeLayout.setProgressBackgroundColorSchemeResource(android.R.color.white); // 设定下拉圆圈的背景
//        mSwipeLayout.setSize(SwipeRefreshLayout.LARGE); // 设置圆圈的大小
    }

    protected void showSwipeRefreshLayout(final SwipeRefreshLayout swipeRefreshLayout) {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.post(new Runnable() {

                @Override
                public void run() {
                    swipeRefreshLayout.setRefreshing(true);
                }
            });
        }
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        if (progressHUD != null && progressHUD.isShowing()) {
            progressHUD.dismiss();
        }

        if (Util.isOnMainThread()) {
            Glide.with(AppConfig.getContext()).pauseRequests();
        }

        if (UMShareAPI.get(this) != null) {
            UMShareAPI.get(this).release();
        }

        if (isUseEventBus() && EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    /**
     * 隐藏软键盘
     */
    protected void hideSoftInput(EditText view) {
        InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
        mInputMethodManager
                .hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 打开软键盘
     */
    protected void showSoftInput(EditText view) {
        InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
        mInputMethodManager
                .showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    /**
     * 列表选择popupWindow
     *
     * @param parentView        父View
     * @param itemTexts         列表项文本集合
     * @param itemClickListener 列表项点击事件
     */
    public void showFilterPopupWindow(View parentView,
                                      List<BaseBean> itemTexts,
                                      AdapterView.OnItemClickListener itemClickListener,
                                      CustomerDismissListener dismissListener) {
        showFilterPopupWindow(parentView, itemTexts, itemClickListener, dismissListener, 0);
    }

    /**
     * 列表选择popupWindow
     *
     * @param parentView        父View
     * @param itemTexts         列表项文本集合
     * @param itemClickListener 列表项点击事件
     */
    public void showFilterPopupWindow(View parentView,
                                      List<BaseBean> itemTexts,
                                      AdapterView.OnItemClickListener itemClickListener,
                                      CustomerDismissListener dismissListener, float alpha) {

        // 判断当前是否显示
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
            mPopupWindow = null;
        }
        mPopupWindow = new CommonFilterPop(context, itemTexts);
        mPopupWindow.setOnDismissListener(dismissListener);
        // 绑定筛选点击事件
        mPopupWindow.setOnItemSelectedListener(itemClickListener);
        // 如果透明度设置为0的话,则默认设置为0.6f
        if (0 == alpha) {
            alpha = 0.6f;
        }
        // 设置背景透明度
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = alpha;
        context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        context.getWindow().setAttributes(lp);
        // 显示pop
        mPopupWindow.showAsDropDown(parentView);

    }

    /**
     * Tab筛选栏切换
     *
     * @param isChecked         选中状态
     * @param showView          展示pop的跟布局
     * @param showMes           展示选择的数据
     * @param itemClickListener 点击回调
     * @param tabs              所有的cb(需要几个输入几个就可以,cb1,cb2....)
     */
    public void filterTabToggle(boolean isChecked, View showView, List<BaseBean> showMes, AdapterView.OnItemClickListener itemClickListener, final CheckBox... tabs) {
        if (isChecked) {
            if (tabs.length <= 0) {
                return;
            }
            // 第一个checkBox为当前点击选中的cb,其他cb进行setChecked(false);
            for (int i = 1; i < tabs.length; i++) {
                tabs[i].setChecked(false);
            }

            showFilterPopupWindow(showView, showMes, itemClickListener, new CustomerDismissListener() {
                @Override
                public void onDismiss() {
                    super.onDismiss();
                    // 当pop消失时对第一个cb进行.setChecked(false)操作
                    tabs[0].setChecked(false);
                }
            });
        } else {
            // 关闭checkBox时直接隐藏popuwindow
            hidePopListView();
        }
    }

    /**
     * Tab筛选栏切换
     *
     * @param isChecked         选中状态
     * @param showView          展示pop的跟布局
     * @param showMes           展示选择的数据源
     * @param itemClickListener 点击回调
     * @param tabs              所有的cb(需要几个输入几个就可以,cb1,cb2....)
     */
    public void filterTabToggleT(boolean isChecked, View showView, List<? extends BaseFilter> showMes,
                                 AdapterView.OnItemClickListener itemClickListener, final CheckBox... tabs) {
        if (isChecked) {
            if (tabs.length <= 0) {
//                return;
            }
            // 第一个checkBox为当前点击选中的cb,其他cb进行setChecked(false);

            if (tabs.length >= 1)
                for (int i = 1; i < tabs.length; i++) {
                    tabs[i].setChecked(false);
                }
            // 从数据源中提取出展示的筛选条件
            List<BaseBean> showStr = new ArrayList<>();
            for (BaseFilter baseFilter : showMes) {
                BaseBean baseBean = new BaseBean();
                baseBean.id = baseFilter.getFilterId();
                baseBean.name = baseFilter.getFilterStr();
                showStr.add(baseBean);
            }
            showFilterPopupWindow(showView, showStr, itemClickListener, new CustomerDismissListener() {
                @Override
                public void onDismiss() {
                    super.onDismiss();
                    // 当pop消失时对第一个cb进行.setChecked(false)操作
                    if (tabs.length >= 1)
                        tabs[0].setChecked(false);
                }
            });
        } else {
            // 关闭checkBox时直接隐藏popuwindow
            hidePopListView();
        }
    }


    /**
     * 自定义OnDismissListener
     */
    public static class CustomerDismissListener implements PopupWindow.OnDismissListener {
        @Override
        public void onDismiss() {
            // 当pop消失的时候,重置背景色透明度
            WindowManager.LayoutParams lp = context.getWindow().getAttributes();
            lp.alpha = 1.0f;
            context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            context.getWindow().setAttributes(lp);
        }
    }

    /**
     * 隐藏pop
     */
    public void hidePopListView() {
        // 判断当前是否显示,如果显示则dismiss
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
            mPopupWindow = null;
        }
    }


    /**
     * 是否使用 EventBus
     *
     * @return
     */
    protected boolean isUseEventBus() {
        return false;
    }


}