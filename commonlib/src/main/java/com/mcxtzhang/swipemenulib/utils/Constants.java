package com.mcxtzhang.swipemenulib.utils;


public class Constants {
    public static final String PerPage = "20";
    public static final int page = 20;
    public static final String ACTION_DOWNLOAD_PROGRESS = "download_progress";
    public static final String ACTION_DOWNLOAD_SUCCESS = "download_success";
    public static final String ACTION_DOWNLOAD_FAIL = "download_fail";
    public static final String ACTION_DOWNLOAD_PAUSE = "download_pause";
    //打开相册管理器
    public static final int REQUEST_CODE_GALLERY = 1001;
    //拍照
    public static final int REQUEST_CODE_CAMERA = 1002;
    //微信
    public static String APP_ID = "wx0103fc7565e7069c";
    public static final String APP_SECRET = "1ca7aeff27eafd534c8c3d5e1d1034df";
    //新浪微博
    public static final String SINA_KEY = "1813126197";
    public static final String SINA_SECRET = "0a6fd079dd49269468f3ac694b24bad2";
    //QQ
    public static final String QQ_ID = "1105546121";
    public static final String QQ_SECRET = "LTTiT2YjagGdp0bl";

    public static final String JYB_LOGO = "http://h5.891jyb.com/images/logo.png";

    /**
     * 保存在SharedPreferences里面的文件名
     */
    public static final String FILE_NAME = "jyb_userinfo";//sp
    public static final String USER_INFO = "user_info";//用户登录信息
    public static final String USER_ACCOUNT = "user_account";//用户登录账号
    public static final String COMMON_INFO = "common_info";//公共信息
    public static final String IS_FIRST_START = "is_first_start";
    public static final String HOME_LIST = "HOME_LIST";//首页列表
    public static final int ORDER_PAY_APP_WX = 1;//微信支付
    public static final int ORDER_PAY_APP_ALI = 2;//支付宝支付
    public static final String RECHARGE_PAY = "2";//支付项类型    ( 必传 1培训 2充值 )
    public static final String CLASS_PAY = "1";//支付项类型    ( 必传 1培训 2充值 )
    public static final String LOGIN_OUT_EB = "user_logout";

    public static final String USER_RESUMEINFO = "user_resumeinfo";//用户简历信息
    public static final String USER_RESUMEINFOCOPY = "user_resumeinfo_copy";//拷贝用户简历信息

    public static final int COME_WHERE_USER_CENTER = 1;
    /**
     * 获取验证码类型
     * 1用户注册，2修改密码，3忘记密码, 4绑定帐号   ( 必传 )
     */
    public static final String CODE_NUM_REGISTER = "1";
    public static final String CODE_NUM_MODIFY_PSD = "2";
    public static final String CODE_NUM_FORGET_PSD = "3";
    public static final String CODE_NUM_BIND = "4";
    /**
     * 新增发布
     */
    public static final String RELEASE_TAB_HELPEACHOTHER = "1";                         //互助
    public static final String RELEASE_TAB_FUN = "2";                                   //趣事
    public static final String RELEASE_TAB_HELPEACHOTHER_TYPE_TRANSFER = "1";       //转让
    public static final String RELEASE_TAB_HELPEACHOTHER_TYPE_HELPEACHOTHER = "2";  //互助
    public static final String RELEASE_TAB_HELPEACHOTHER_ISPAY_YES = "1";           //付费
    public static final String RELEASE_TAB_HELPEACHOTHER_ISPAY_NO = "2";            //免费
    public static final String RELEASE_TAB_HELPEACHOTHER_SEX_MALE = "1";            //男
    public static final String RELEASE_TAB_HELPEACHOTHER_SEX_FAMALE = "2";          //女
    public static final String RELEASE_TAB_HELPEACHOTHER_SEX_OTHER = "3";           //不限


    /**
     * 我的收藏type
     */
    public static final String MYCOLLECTION_PARTTIME = "1";           //兼职
    public static final String MYCOLLECTION_ALLTIME = "2";           //全职
    public static final String MYCOLLECTION_FUNNY = "3";           //趣事
    public static final String MYCOLLECTION_STRATEGY = "4";           //攻略
    public static final String MYCOLLECTION_TEST = "5";           //试卷
    public static final String MYCOLLECTION_SECRET = "6";           //秘籍
    public static final String MYCOLLECTION_INFORMATION = "7";           //资讯

    /**
     * 绑定手机
     * 1.绑定手机
     * 2.换绑手机
     */
    public static final int BING_MOBILE = 1;
    public static final int RECHANGE_MOBILE = 2;

    /**
     * 首页
     * 1.全职
     * 2.兼职
     */
    public static final int JOB_FULL_TIME = 1;
    public static final int JOB_PART = 2;

    /**
     * 定位
     */
    public static String lng = "";//自己定位到的坐标
    public static String lat = "";
    public static String city_lng = "";//首页切换城市是的坐标
    public static String city_lat = "";
    public static String city_id = "1388";
    public static String provice_id = "1387";
    public static boolean isChangeCity = false;//用户切换城市

    public static String EB_LOGIN_SUCCESS = "login_success";

    public static int COME_WHERE_MIJI = 1;
    public static int COME_WHERE_SHIJUAN = 2;

    //分享        1.校园新鲜事 2.培训详情  3.发现
    public static final int SHARE_CAMPUS_TYPE = 1;
    public static final int SHARE_TRAIN_TYPE = 2;
    public static final int SHARE_FIND_TYPE = 3;

    /**
     * 优惠券类型
     * 类型(1未使用 2已过期 3已使用)
     */
    public static final int COUPON_NOUSE_TYPE = 1;
    public static final int COUPON_OLD_TYPE = 2;
    public static final int COUPON_USED_TYPE = 3;

    public static final String INTENT_KEY_COME_WHERE = "come_where";

    /**
     * 顶部广告
     */
    public static final String AD_HOME_TOP = "app_home_index_top";          //首页
    public static final String AD_HELP = "app_help_index_top";              //互助
    public static final String AD_CLASS = "app_find_index_top";             //学堂
    public static final String AD_FIND_TOP = "app_find_index_top_ex";       //发现

    public static final String SETTING_SP_RECEIVE_INFO = "setting_sp_receiver_info";
    public static final String SETTING_SP_SHOW_INFO_DETAIL = "setting_sp_show_info_detail";
    public static final String SETTING_SP_VOICE = "setting_sp_voice";
    public static final String SETTING_SP_VIB = "setting_sp_vib";
    public static final String SETTING_SP_NOT = "setting_sp_not";//是否提示通知消息权限


    /**
     * 朋友圈
     */
    public static final String IMG_LIST = "img_list"; //第几张图片
    public static final String POSITION = "position"; //第几张图片
    public static final String ISDETEL = "isdetel"; //是否显示删除按钮
    public static final String PIC_PATH = "pic_path"; //图片路径
    public static final int MAX_SELECT_PIC_NUM = 5; // 最多上传5张图片
    public static final int REQUEST_CODE_MAIN = 10; //请求码
    public static final int RESULT_CODE_VIEW_IMG = 11; //查看大图页面的结果码

    /**
     * 公司个人中心
     */
    public static final int COMPANY_RELEASEJOB_PARTTIME = 2; //公司发布的兼职type
    public static final int COMPANY_RELEASEJOB_ALLTIME = 1; //公司发布的全职type
}