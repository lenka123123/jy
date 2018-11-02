package sinia.com.baihangeducation;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;

import com.example.framwork.baseapp.BaseAppConfig;
import com.example.framwork.utils.CommonUtil;

import com.example.framwork.utils.UserInfo;
import com.mcxtzhang.swipemenulib.utils.BuildConfig;

import sinia.com.baihangeducation.newcampus.info.FunContantInfo;


/**
 * Created by lenovo on 2017/2/22.
 */

public class AppConfig extends BaseAppConfig {

    //    currentLat, currentLon
    public static String CURRENTLAT = "32.089858";
    public static String CURRENTLON = "118.755877";

    public static boolean HOMT = false;
    public static boolean PART = false;
    public static boolean CHAT = false;
    public static boolean CLUB = false;
    public static boolean ME = false;

    public static boolean ISSELECTCLICK = false;


    public static final String USERTOKEN = "FINALUSERTOKEN";
    public static final String FINALUSERID = "FINALUSERID";
    public static final String FINALUAVATAR = "FINALUAVATAR";
    public static final String FINALNICKNAME = "FINALNICKNAME";
    public static final String FINALSLOGAN = "FINALSLOGAN";
    public static final String FINALGENDEREEE = "FINALGENDEREEE";
    public static final String FINALEMEMAIL = "FINALEMEMAIL";


    public static final String FINAL_NO_READ_NUM = "FINAL_NO_READ_NUM";
    public static final String FINAL_NUM_TRAIN_NUM = "FINAL_NUM_TRAIN_NUM";
    public static final String FINAL_NUM_FULL_JOB_NUM = "FINAL_NUM_FULL_JOB_NUM";
    public static final String FINAL_NUM_FULL_PARK_NUM = "FINAL_NUM_FULL_PARK_NUM";
    public static final String FINAL_NUM_FULL_HULP_NUM = "FINAL_NUM_FULL_HULP_NUM";
    public static final String FINAL_NUM_FULL_HULP_NICKNAME = "FINAL_NUM_FULL_HULP_NICKNAME";
    public static final String FINAL_NUM_FULL_TYPE = "FINAL_NUM_FULL_TYPE";
    public static final String FINAL_NUM_FULL_AUTH_STATUS = "FINAL_NUM_FULL_AUTH_STATUS";
    public static final String FINAL_NUM_FULL_VIP_LEVEL = "FINAL_NUM_FULL_VIP_LEVEL";
    public static final String FINAL_SAVE_PHOTO_PATH = "FINAL_SAVE_PHOTO_PATH";
    public static final String USERPHOTO = "USERPHOTO";  //手机号
    public static final String USERPWD = "USERPWD";
    public static final String IS_LOGIN_APP = "IS_LOGIN_APP";

    public static final String COMMON_INFO_ANDROID_VERSION = "COMMON_INFO_ANDROID_VERSION";
    public static final String COMMON_INFO_HOTLINE = "COMMON_INFO_HOTLINE";
    public static final String COMMON_INFO_OPEN_IMG = "COMMON_INFO_OPEN_IMG";
    public static final String COMMON_INFO_ABOUT = "COMMON_INFO_ABOUT";
    public static final String COMMON_INFO_ABOUTREGISTRATION_PROTOCOL = "COMMON_INFO_ABOUTREGISTRATION_PROTOCOL";
    public static final String COMMON_INFO_HELP = "COMMON_INFO_HELP";
    public static final String IS_DIM = "IS_DIM";
    public static final String IS_NEED_LOGIN = "IS_NEED_LOGIN";


    public static String CHATMESSAGE = "";
    public static String CHATMESSAGENUM = "";
    public static String API_VERSION = "4.1.8";
    public static String HTML_LV = "1.1.1";

    public static String SCHOOLNAME = "";
    public static String SCHOOLNAMEID = "";

    public static String SCHOOLMAGOR = "";
    public static String SCHOOLMAGORID = "";

    public static String educationtext = "";
    public static String educationtextID = "";

    public static String SCHOOLEDUCTION = "";
    public static String AREAID = "areaId";
    public static String TOPIC_TITLE = "topic_title";
    public static String TOPIC_ID = "topic_id";
    public static String USERID = "USERID";
    public static int USERIDTYPE = 0;
    public static String OTHERID = "other_id";
    public static String USERNAME = "USERNAME";
    public static String TOKEN = "USERID";
    public static String CTYLEID = "1388";
    public static String CTYLENAME = "CTYLENAME";
    public static String LOGINPHOTOTPATH = "";
    public static boolean CTYLENAMESELECT = false;
    public static Boolean SHOWCLUBJOB = false;
    public static FunContantInfo mFunContantInfo = null;


    public static boolean ISlOGINED = true;
    public static boolean IS_MANUAL_lOGINED = false;
    public static boolean PAYSUCCESS = false;
    public static boolean INTENTION_SETTING = false; //意向设置
    public static final int SERVER_TYPE_OLINE = 1;//正式服务器
    public static final int SERVER_TYPE = BuildConfig.SERVER_TYPE;


    @SuppressLint("StaticFieldLeak")
    private static Context context;

    /**
     * 必须在Application初始化  为服务器地址赋值
     */
    public static void initServerSpServices() {
        if (SERVER_TYPE == SERVER_TYPE_OLINE) {
            //测试地址
//            SERVICE_PATH = "http://api.jyb.qimixi.net";
            //线上地址
            SERVICE_PATH = "https://newapi.891jyb.com";
        } else {
            SERVICE_PATH = "https://newapi.891jyb.com";
        }
    }

    public static String getAppVersion(Activity activity) {
        return CommonUtil.getVersion(activity);
    }

    /**
     * 初始化工具类
     *
     * @param context 上下文
     */
    public static void init(Context context) {
        AppConfig.context = context.getApplicationContext();
    }

    /**
     * 在某种获取不到 Context 的情况下，即可以使用才方法获取 Context
     * <p>
     * 获取ApplicationContext
     *
     * @return ApplicationContext
     */
    public static Context getContext() {
        if (context != null) {
            return context;
        }
        throw new NullPointerException("请先调用init()方法");
    }
}
