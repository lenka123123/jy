package sinia.com.baihangeducation;


import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.StrictMode;
import android.support.multidex.MultiDex;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.baidu.mobstat.StatService;
import com.example.framwork.BaseApplictaion;
import com.example.framwork.baseapp.BaseAppConfig;
import com.example.framwork.utils.CommonInfoSaveUtil;
import com.example.framwork.utils.ObjectSaveUtil;
import com.example.framwork.utils.SpCommonUtils;
import com.example.framwork.utils.UserInfo;
import com.fm.openinstall.OpenInstall;
import com.fm.openinstall.listener.AppInstallAdapter;
import com.fm.openinstall.model.AppData;
import com.imnjh.imagepicker.PickerConfig;
import com.imnjh.imagepicker.SImagePicker;
import com.lzy.ninegrid.NineGridView;
import com.mcxtzhang.swipemenulib.utils.CrashHandler;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.yanzhenjie.nohttp.Logger;

import cn.jpush.android.api.JPushInterface;

import com.mcxtzhang.swipemenulib.info.bean.CommonInfo;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.GroupInfo;
import cn.jpush.im.android.api.model.Message;
import sinia.com.baihangeducation.club.im.entity.NotificationClickEventReceiver;
import sinia.com.baihangeducation.club.im.utils.SharePreferenceManager;
import sinia.com.baihangeducation.club.im.utils.StorageUtil;
import sinia.com.baihangeducation.reconsitution.common.FrescoImageLoader;
import sinia.com.baihangeducation.supplement.loader.GlideImageLoader;

import com.mcxtzhang.swipemenulib.info.bean.MyResumInfo;

import sinia.com.baihangeducation.supplement.tool.NineGridGlideLoader;

import com.mcxtzhang.swipemenulib.utils.ACache;
import com.mcxtzhang.swipemenulib.utils.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018.02.08.
 */

public class MyApplication extends BaseApplictaion {


    public static final String CONV_TITLE = "conv_title";
    public static final int IMAGE_MESSAGE = 1;
    public static final int TAKE_PHOTO_MESSAGE = 2;
    public static final int TAKE_LOCATION = 3;
    public static final int FILE_MESSAGE = 4;
    public static final int TACK_VIDEO = 5;
    public static final int TACK_VOICE = 6;
    public static final int BUSINESS_CARD = 7;
    public static final int REQUEST_CODE_SEND_FILE = 26;


    public static final int RESULT_CODE_ALL_MEMBER = 22;
    public static Map<Long, Boolean> isAtMe = new HashMap<>();
    public static Map<Long, Boolean> isAtall = new HashMap<>();
    public static List<Message> forwardMsg = new ArrayList<>();

    public static long registerOrLogin = 1;
    public static final int REQUEST_CODE_TAKE_PHOTO = 4;
    public static final int REQUEST_CODE_SELECT_PICTURE = 6;
    public static final int REQUEST_CODE_CROP_PICTURE = 18;
    public static final int REQUEST_CODE_CHAT_DETAIL = 14;
    public static final int RESULT_CODE_FRIEND_INFO = 17;
    public static final int REQUEST_CODE_ALL_MEMBER = 21;
    public static final int RESULT_CODE_EDIT_NOTENAME = 29;
    public static final String NOTENAME = "notename";
    public static final int REQUEST_CODE_AT_MEMBER = 30;
    public static final int RESULT_CODE_AT_MEMBER = 31;
    public static final int RESULT_CODE_AT_ALL = 32;
    public static final int SEARCH_AT_MEMBER_CODE = 33;

    public static final int RESULT_BUTTON = 2;
    public static final int START_YEAR = 1900;
    public static final int END_YEAR = 2050;
    public static final int RESULT_CODE_SELECT_FRIEND = 23;

    public static final int REQUEST_CODE_SELECT_ALBUM = 10;
    public static final int RESULT_CODE_SELECT_ALBUM = 11;
    public static final int RESULT_CODE_SELECT_PICTURE = 8;
    public static final int REQUEST_CODE_BROWSER_PICTURE = 12;
    public static final int RESULT_CODE_BROWSER_PICTURE = 13;
    public static final int RESULT_CODE_SEND_LOCATION = 25;
    public static final int RESULT_CODE_SEND_FILE = 27;
    public static final int REQUEST_CODE_SEND_LOCATION = 24;
    public static final int REQUEST_CODE_FRIEND_INFO = 16;
    public static final int RESULT_CODE_CHAT_DETAIL = 15;
    public static final int ON_GROUP_EVENT = 3004;
    public static final String DELETE_MODE = "deleteMode";
    public static final int RESULT_CODE_ME_INFO = 20;

    public static final String DRAFT = "draft";
    public static final String GROUP_ID = "groupId";
    public static final String POSITION = "position";
    public static final String MsgIDs = "msgIDs";
    public static final String NAME = "name";
    public static final String ATALL = "atall";
    public static final String SEARCH_AT_MEMBER_NAME = "search_at_member_name";
    public static final String SEARCH_AT_MEMBER_USERNAME = "search_at_member_username";
    public static final String SEARCH_AT_APPKEY = "search_at_appkey";

    public static final String MEMBERS_COUNT = "membersCount";

    public static String PICTURE_DIR = "sdcard/JChatDemo/pictures/";
    private static final String JCHAT_CONFIGS = "JChat_configs";
    public static String FILE_DIR = "sdcard/JChatDemo/recvFiles/";
    public static String VIDEO_DIR = "sdcarVIDEOd/JChatDemo/sendFiles/";
    public static final String TARGET_ID = "targetId";
    public static final String ATUSER = "atuser";
    public static final String TARGET_APP_KEY = "targetAppKey";
    public static int maxImgCount;               //允许选择图片最大数
    public static final String GROUP_NAME = "groupName";

    public static Context context;
//    public static LocationService locationService; // TODO: 2018/10/9 0009 百度定位 

    public static List<GroupInfo> mGroupInfoList = new ArrayList<>();
    public static List<cn.jpush.im.android.api.model.UserInfo> mFriendInfoList = new ArrayList<>();
    public static List<cn.jpush.im.android.api.model.UserInfo> mSearchGroup = new ArrayList<>();
    public static List<cn.jpush.im.android.api.model.UserInfo> mSearchAtMember = new ArrayList<>();
    public static List<Message> ids = new ArrayList<>();
    public static List<cn.jpush.im.android.api.model.UserInfo> alreadyRead = new ArrayList<>();
    public static List<cn.jpush.im.android.api.model.UserInfo> unRead = new ArrayList<>();
    public static List<String> forAddFriend = new ArrayList<>();


    public static UserInfo userInfo;
    private MyResumInfo userResumeInfo;
    private MyResumInfo userResumeInfocopy;
    public static String lng;
    public static String lat;
    public static String adCode;
    //声明mlocationClient对象
    public AMapLocationClient mlocationClient;
    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;

    public static Context sContext;
    private ACache mCache;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
        context = getApplicationContext();
        StorageUtil.init(context, null);
        JMessageClient.init(context, true);// 是否启用消息漫游，true - 启用，false - 关闭。
        JMessageClient.setDebugMode(false);
        SharePreferenceManager.init(getApplicationContext(), JCHAT_CONFIGS);
        //设置Notification的模式
        JMessageClient.setNotificationFlag(JMessageClient.FLAG_NOTIFY_WITH_SOUND | JMessageClient.FLAG_NOTIFY_WITH_LED | JMessageClient.FLAG_NOTIFY_WITH_VIBRATE);
        //注册Notification点击的接收器
        new NotificationClickEventReceiver(getApplicationContext());


        if (isMainProcess()) {
            OpenInstall.init(this);
        }

        OpenInstall.getInstall(new AppInstallAdapter() {
            @Override
            public void onInstall(AppData appData) {
                //获取渠道数据
                String channelCode = appData.getChannel();
                //获取自定义数据
                String bindData = appData.getData();
                Log.d("OpenInstall", "getInstall : installData = " + appData.toString());
            }
        });


        AppConfig.init(this);
        BaseAppConfig.init(this, "dong");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }

        JPushInterface.setDebugMode(false);    // 设置开启日志,发布时请关闭日志
        JPushInterface.init(sContext);            // 初始化 JPush

        mCache = ACache.get(this);

        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());//全局bug


        //初始化client
        mlocationClient = new AMapLocationClient(this);
        mLocationOption = getDefaultOption();
        //设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
        // 设置定位监听
        mlocationClient.setLocationListener(locationListener);
        mlocationClient.startLocation();

        NineGridView.setImageLoader(new NineGridGlideLoader());//初始化九宫格加载

        Logger.setDebug(false); // 开启NoHttp调试模式。
        Logger.setTag("MyNoHttpDebug");// 设置NoHttp打印Log的TAG。

        initImagePicker();
        initUM();
        initShare();
        StatService.autoTrace(this, true, false);//开启自动埋点    https://mtj.baidu.com/static/userguide/book/android/adconfig/circle/circle.html
    }

    private void initImagePicker() {
        SImagePicker.init(new PickerConfig.Builder().setAppContext(this)
                .setImageLoader(new FrescoImageLoader())
                .setToolbaseColor(Color.BLUE)
                .build());
    }

    private void initUM() {
        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, null);
        UMShareAPI.get(this);//初始化sdk
        //开启debug模式，方便定位错误，具体错误检查方式可以查看http://dev.umeng.com/social/android/quick-integration的报错必看，正式发布，请关闭该模式
        Config.DEBUG = false;
        UMConfigure.setLogEnabled(true);
    }

    private void initShare() {
        //微信
        PlatformConfig.setWeixin(Constants.APP_ID, Constants.APP_SECRET);
        //QQ
        PlatformConfig.setQQZone(Constants.QQ_ID, Constants.QQ_SECRET);
        //微薄
//        PlatformConfig.setSinaWeibo(Constants.SINA_KEY, Constants.SINA_SECRET, "http://sns.whalecloud.com/sina2/callback");
    }

    /**
     * 存入用户信息
     *
     * @param userInfo
     */
    public void setUserInfo(UserInfo userInfo) {
        if (userInfo == null) {
            AppConfig.ISlOGINED = false;

        }

//        ObjectSaveUtil.saveObject(getContext(), userInfo);
        this.userInfo = userInfo;
    }

    /**
     * 存入用户简历信息
     *
     * @param userResumeInfo
     */
    public void setUserResumeInfo(MyResumInfo userResumeInfo) {
        this.userResumeInfo = userResumeInfo;
//        Log.i("存入缓存简历",userResumeInfo.toString());
        Log.i("存入缓存简历", userResumeInfo.name);
    }

    /**
     * 存入用户简历信息拷贝
     *
     * @param userResumeInfo
     */
    public void setUserResumeInfoCopy(MyResumInfo userResumeInfo) {
        this.userResumeInfocopy = userResumeInfo;
    }


    /**
     * 取出用户信息
     *
     * @return
     */


    /**
     * 取出用户信息
     *
     * @return
     */
    public MyResumInfo getUserResumeInfo() {
        return userResumeInfo;
    }

    /**
     * 取出用户信息拷贝
     *
     * @return
     */
    public MyResumInfo getUserResumeInfoCopy() {
        return userResumeInfocopy;
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

    /**
     * 定位监听
     */
    AMapLocationListener locationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation location) {
            if (null != location) {

                StringBuffer sb = new StringBuffer();
                //errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
                Log.i("定位错误", location.getErrorCode() + "");
                if (location.getErrorCode() == 0) {
                    int int_longintude = (int) (location.getLongitude() * 1000000);
                    int int_latitude = (int) (location.getLatitude() * 1000000);
                    double longintude = (double) int_longintude / 1000000;
                    double latitude = (double) int_latitude / 1000000;
                    lng = longintude + "";
                    lat = latitude + "";
                    adCode = location.getCityCode() + "";

                } else {
                    lng = 118.755877 + "";
                    lat = 32.089858 + "";
                    adCode = 320106 + "";
                }
                mCache.put("LAT", lat);
                mCache.put("LNG", lng);
                mCache.put("ADCODE", adCode);
            }
        }
    };


    public boolean isMainProcess() {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return getApplicationInfo().packageName.equals(appProcess.processName);
            }
        }
        return false;
    }
}
