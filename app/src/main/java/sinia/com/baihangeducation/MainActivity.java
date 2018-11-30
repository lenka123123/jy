package sinia.com.baihangeducation;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.services.core.LatLonPoint;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.framwork.utils.CommonUtil;
import com.example.framwork.utils.DateUtil;
import com.example.framwork.utils.SPUtils;
import com.example.framwork.utils.SpCommonUtils;
import com.example.framwork.utils.UserInfo;
import com.fm.openinstall.OpenInstall;
import com.fm.openinstall.listener.AppWakeUpAdapter;
import com.fm.openinstall.model.AppData;
import com.mcxtzhang.swipemenulib.activity.BaseRequestActivity;
import com.mcxtzhang.swipemenulib.customview.NoScrollViewPager;
import com.mcxtzhang.swipemenulib.utils.MyActivityManager;
import com.umeng.socialize.UMShareAPI;
import com.yanzhenjie.nohttp.Logger;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.jiguang.api.JCoreInterface;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.api.BasicCallback;
import sinia.com.baihangeducation.chat.ChatFragment;
import sinia.com.baihangeducation.club.ClubFragment;
import sinia.com.baihangeducation.club.im.ChatActivity;
import sinia.com.baihangeducation.mine.view.GetBaseInfoView;
import sinia.com.baihangeducation.parttime.PartTimeFragment;

import com.mcxtzhang.swipemenulib.dialog.CommonDialog;
import com.mcxtzhang.swipemenulib.dialog.ForceUpdateDialog;

import sinia.com.baihangeducation.home.HomeFragment;
import sinia.com.baihangeducation.mine.MineFragment;

import com.mcxtzhang.swipemenulib.info.bean.VersionInfo;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import sinia.com.baihangeducation.mine.presenter.UpdateVersionPresenter;
import sinia.com.baihangeducation.mine.view.IUpdateVersionView;
import sinia.com.baihangeducation.newcampus.NewCampusFragment;

import sinia.com.baihangeducation.supplement.base.Goto;
import sinia.com.baihangeducation.supplement.tool.BaseUtil;
import sinia.com.baihangeducation.supplement.tool.JudgeUtils;

public class MainActivity extends BaseRequestActivity implements IUpdateVersionView, RadioGroup.OnCheckedChangeListener, View.OnClickListener, NavigationView.OnNavigationItemSelectedListener, GetBaseInfoView {

    private RadioButton mHome;//首页
    private RadioButton mFine;//发现
    private RadioButton mCampus;//校园
    private RadioButton mMine;//我的
    private RadioButton mAdd;        //添加
    private NoScrollViewPager mNoScrollViewPager;
    private RadioGroup mRadioGroup;

    public static String lng;
    public static String lat;
    public static String adcode;
    boolean gpsShowToast = true;

    //版本更新
    private UpdateVersionPresenter updateVersionPresenter;
    private ForceUpdateDialog forceUpdateDialog = null;

    //退出时间
    private long exitTime = 0;

    //极光推送，绑定用户时候的参数.
    private String userType;
    private Set<String> set;

    private LinearLayout mLinearLayout;
    private RelativeLayout navigationView;
    private DrawerLayout mDrawerLayout;
    private TextView delete_train_text;
    private TextView linearLayout_full_text;
    private TextView linearLayout_park_text;
    private TextView huzhu_text;
    private ImageView logoImg;
    private TextView textViewName;
    private ImageView shiming_img;
    private TextView shiming_text;
    private ImageView logo_man;
    private TextView no_read_num;
    private LinearLayout delete_train;
    private LinearLayout linearLayout_full;
    private LinearLayout linearLayout_park;
    private LinearLayout huzhu;
    private TextView mMyCoffers;
    private TextView mMyPay;
    private ChatFragment chatFragment;
    private ClubFragment clubFragment;
    private MineFragment mineFragment;
    private HomeFragment homeFragment;
    private MyBroadCastRecevir recevir;
    private PartTimeFragment partTimeFragment;
    private String phone;
    private LinearLayout all_unread_number;


    public void loginIm() {
      //  System.out.println("登录的手机号" + phone);
        JMessageClient.login(phone, "123456", new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                //更新用户头像
                String phonePath = (String) SpCommonUtils.get(activity, AppConfig.FINAL_SAVE_PHOTO_PATH, "");
                JMessageClient.updateUserAvatar(new File(phonePath), new BasicCallback() {
                    @Override
                    public void gotResult(int i, String s) {
                        List<Conversation> list = JMessageClient.getConversationList();

                       // if (list != null)
                         //   System.out.println("JMessageClient dong" + i + "===" + s + "====" + list.size());
                    }
                });
              //  System.out.println("JMessageClient dong" + i + "===" + s);
//                JMessageClient.getUserInfo("18851032658", new GetUserInfoCallback() {
//                    @Override
//                    public void gotResult(int i, String s, cn.jpush.im.android.api.model.UserInfo userInfo) {
//                        System.out.println("JMessageClient dong1" + i + "===" + s + "===" + userInfo.getAppKey());
//                           chat(userInfo);
//                    }
//                });


            }
        });
    }


    public void chat(cn.jpush.im.android.api.model.UserInfo userInfo) {
        Intent intent = new Intent();
        intent.putExtra(MyApplication.CONV_TITLE, "聊天");
        String targetId = userInfo.getUserName();
        intent.putExtra(MyApplication.TARGET_ID, targetId);
        intent.putExtra(MyApplication.TARGET_APP_KEY, userInfo.getAppKey());
        intent.putExtra(MyApplication.DRAFT, "***");

        intent.setClass(mContext, ChatActivity.class);
        startActivity(intent);
    }


    @Override
    protected void onDestroy() {
        unregisterReceiver(recevir);
        updateVersionPresenter = null;
        mNoScrollViewPager = null;
        mRadioGroup = null;
        wakeUpAdapter = null;

        if (homeFragment != null) {
            homeFragment.onDestroyView();
            homeFragment.onDestroy();
            homeFragment.onDetach();
        }
        if (mineFragment != null) {
            mineFragment.onDestroyView();
            mineFragment.onDestroy();
            mineFragment.onDetach();
        }

        if (clubFragment != null) {
            clubFragment.onDestroyView();
            clubFragment.onDestroy();
            clubFragment.onDetach();
        }
        if (chatFragment != null) {
            chatFragment.onDestroyView();
            chatFragment.onDestroy();
            chatFragment.onDetach();
        }
        if (partTimeFragment != null) {
            partTimeFragment.onDestroyView();
            partTimeFragment.onDestroy();
            partTimeFragment.onDetach();
        }
        TabFragment.onDestroy();

      //  System.out.println("Mian delete");
        Log.i("", "onDestroy:      Mian delete");

        if (mLocationClient != null) {
            mLocationClient.onDestroy();//销毁定位客户端。
        }

        super.onDestroy();


    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStart() {
        super.onStart();


        if (mLocationClient != null) {
            mLocationClient.startLocation(); // 启动定位
        }
    }

    @Override
    protected void onPause() {
        JCoreInterface.onPause(this);
        super.onPause();
        if (mLocationClient != null) {
            mLocationClient.stopLocation();//停止定位
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (AppConfig.ISlOGINED) {
            homeFragment.setReSatart();
            mineFragment.setReSatart();
            clubFragment.setReSatart();
            chatFragment.setReSatart();
        }

    }

    @Override
    protected void onResume() {
        int num = JMessageClient.getAllUnReadMsgCount();
        changeMsgCountView(num, 0);
        JCoreInterface.onResume(this);
        // TODO: 2018/10/20 0020      chatFragment.sortConvList();
        super.onResume();

    }

    private class MyBroadCastRecevir extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
          //  System.out.println("MainActivity.this.finish======");
            MainActivity.this.finish();
        }
    }


    public DrawerLayout getmDrawerLayout() {
        return mDrawerLayout;
    }

    public int setContentID() {
        return R.layout.activity_main;
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        // 此处要调用，否则App在后台运行时，会无法截获
        OpenInstall.getWakeUp(intent, wakeUpAdapter);
    }

    AppWakeUpAdapter wakeUpAdapter = new AppWakeUpAdapter() {
        @Override
        public void onWakeUp(AppData appData) {
            //获取渠道数据
            String channelCode = appData.getChannel();
            //获取绑定数据
            String bindData = appData.getData();
        //    System.out.println("getWakeUp : wakeupData = " + appData.toString());
            Log.d("OpenInstallww", "getInstall : installData = " + appData.toString());
            // getWakeUp : wakeupData = AppData : channelCode= , bindData={"app":"share","act":"job","id":"452"}
            try {
                JSONObject object = new JSONObject(appData.getData().toString());
                String app = object.getString("app");
                String act = object.getString("act");
                String id = object.getString("id");
                if (app.equals("share") && act.equals("job")) {
                    Goto.toPartTimeJobDetailActivity(MainActivity.this, Integer.valueOf(id));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    };

    public void initView() {
        phone = (String) SpCommonUtils.get(activity, AppConfig.USERPHOTO, "");
        recevir = new MyBroadCastRecevir();
        IntentFilter intentFiltet = new IntentFilter();
        //设置广播的名字（设置Action，可以添加多个要监听的动作）
        intentFiltet.addAction("myBroadCastAction");
        // 注册广播,传入两个参数， 实例化的广播接受者对象，
        registerReceiver(recevir, intentFiltet);
        loginIm();

        homeFragment = (HomeFragment) TabFragment.home.fragment();
        mineFragment = (MineFragment) TabFragment.me.fragment();
        clubFragment = (ClubFragment) TabFragment.club.fragment();
        chatFragment = (ChatFragment) TabFragment.chat.fragment();
        partTimeFragment = (PartTimeFragment) TabFragment.part.fragment();

        getCurrentLocationLatLng();


        OpenInstall.getWakeUp(getIntent(), wakeUpAdapter);

        MyActivityManager mam = MyActivityManager.getInstance();
        mam.pushOneActivity(MainActivity.this);//就把当前activity压入了栈中

        navigationView = (RelativeLayout) findViewById(R.id.nav_view);
        updateVersionPresenter = new UpdateVersionPresenter(this, this);
        updateVersionPresenter.getVersionInfo();


        initJPushTag();

        mHome = (RadioButton) findViewById(R.id.main_tab_home);
        mFine = (RadioButton) findViewById(R.id.main_tab_find);
        mCampus = (RadioButton) findViewById(R.id.main_tab_campus);
        mMine = (RadioButton) findViewById(R.id.main_tab_mine);
        all_unread_number = (LinearLayout) findViewById(R.id.all_unread_number);
        mAdd = (RadioButton) findViewById(R.id.main_tab_add);
        mLinearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        // TODO: 2018/8/23 0023      mDrawerLayout.setScrimColor(Color.WHITE);
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        //培训
        delete_train = (LinearLayout) findViewById(R.id.delete_train);
        linearLayout_full = (LinearLayout) findViewById(R.id.linearLayout_full);
        linearLayout_park = (LinearLayout) findViewById(R.id.linearLayout_park);
        huzhu = (LinearLayout) findViewById(R.id.huzhu);

        //培训
        delete_train.setOnClickListener(this);
        linearLayout_full.setOnClickListener(this);
        linearLayout_park.setOnClickListener(this);
        huzhu.setOnClickListener(this);

        //培训
        delete_train_text = (TextView) findViewById(R.id.delete_train_text);
        linearLayout_full_text = (TextView) findViewById(R.id.linearLayout_full_text);
        linearLayout_park_text = (TextView) findViewById(R.id.linearLayout_park_text);
        huzhu_text = (TextView) findViewById(R.id.huzhu_text);

        shiming_text = (TextView) findViewById(R.id.shiming_text);
        findViewById(R.id.setting_home).setOnClickListener(this);
        no_read_num = findViewById(R.id.news_home);


        logoImg = (ImageView) findViewById(R.id.logo);
        logo_man = (ImageView) findViewById(R.id.logo_man);
        textViewName = (TextView) findViewById(R.id.name);
        logoImg.setOnClickListener(this);

        mMyCoffers = findViewById(R.id.my_coffers);
        mMyPay = findViewById(R.id.my_pay);
        int type = AppConfig.USERIDTYPE;
      //  System.out.println("userInfouserInfouserIns试试33fo.type" + type);


        if (type == 1) { //1个人/2企业/3培训机构
            delete_train.setVisibility(View.GONE); //培训
            mMyCoffers.setVisibility(View.VISIBLE);//金 库
            mMyPay.setVisibility(View.VISIBLE);//金 库
            huzhu.setVisibility(View.VISIBLE);
        }
        if (type == 2) {
            delete_train.setVisibility(View.VISIBLE); //培训
            mMyCoffers.setVisibility(View.GONE);//金 库
            mMyPay.setVisibility(View.GONE);//金 库
            huzhu.setVisibility(View.GONE);
        }
        if (type == 3) {
            huzhu.setVisibility(View.GONE);
            mMyCoffers.setVisibility(View.GONE);//金 库
            mMyPay.setVisibility(View.GONE);//金 库
            delete_train.setVisibility(View.VISIBLE); //培训
        }

        findViewById(R.id.my_select).setOnClickListener(this);
        findViewById(R.id.my_send).setOnClickListener(this);
        findViewById(R.id.my_resume).setOnClickListener(this);
        findViewById(R.id.my_sign).setOnClickListener(this);
        findViewById(R.id.chat_layout).setOnClickListener(this);
        mMyCoffers.setOnClickListener(this);
        mMyPay.setOnClickListener(this);


        mNoScrollViewPager = (NoScrollViewPager) findViewById(R.id.content);
        mRadioGroup = (RadioGroup) findViewById(R.id.main_tab_radiogroup);

        mNoScrollViewPager.setOffscreenPageLimit(5);
//        WindowManager wm = this.getWindowManager();//获取屏幕宽高
//        int width1 = wm.getDefaultDisplay().getWidth();
//        int height1 = wm.getDefaultDisplay().getHeight();
//        ViewGroup.LayoutParams para= mDrawerLayout.getLayoutParams();//获取drawerlayout的布局
//        para.width=width1/2;//修改宽度
//        para.height=height1;//修改高度
//        mDrawerLayout.setLayoutParams(para); //设置修改后的布局。

        mAdd.setOnClickListener(this);
        mRadioGroup.setOnCheckedChangeListener(this);

        initPagerContent();


        mDrawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            }

            @Override
            public void onDrawerStateChanged(int newState) {
            }
        });

    }


    public void initAct() {

    }

    public void initData() {
        int num = JMessageClient.getAllUnReadMsgCount();
        changeMsgCountView(num, 0);
    }

    public void changeMsgCountView(int num, int isSystem) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
          //      System.out.println("changeMsgCountView==" + num + "系统" + isSystem);
                if (all_unread_number == null) return;
                if (isSystem == 1) {  //系统
                    if (num >= 1) {
                        all_unread_number.setVisibility(View.VISIBLE);
                    } else {
                        int num = JMessageClient.getAllUnReadMsgCount();
                        if (num >= 1) {
                            all_unread_number.setVisibility(View.VISIBLE);
                        } else {
                            all_unread_number.setVisibility(View.GONE);
                        }

                    }
                } else if (isSystem == 0) {
                    if (num >= 1) {
                        all_unread_number.setVisibility(View.VISIBLE);
                    } else {
                        all_unread_number.setVisibility(View.GONE);
                    }
                }


            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000)  //System.currentTimeMillis()无论何时调用，肯定大于2000
            {
                Toast.makeText(getApplicationContext(), "再按一次退出就业邦", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                android.os.Process.killProcess(android.os.Process.myPid());
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void showForceUpdate(VersionInfo data) {
        forceUpdateDialog = CommonDialog.versionUpdateForceDilog(this, data);
    }

    @Override
    public void showUpdate(VersionInfo data) {
        if (SPUtils.getInstance().contains(this, "version_time")) {
            Long version_time = (Long) SPUtils.getInstance().get(this, "version_time", 0L);
            Long curTime = System.currentTimeMillis();
            if (DateUtil.getInstance().mssToDay(curTime - version_time) >= 1) {//如果时间大于一天  再次弹出更新提示框
                forceUpdateDialog = CommonDialog.versionUpdateForceDilog(this, data);
            }
        } else {
            forceUpdateDialog = CommonDialog.versionUpdateForceDilog(this, data);
        }
    }

    /**
     * 设置推送别名和tag
     */
    private void initJPushTag() {
        userType = CommonUtil.getAndroidId(this);
        Log.i("设备ID", userType + "        MainActivity");
        set = new HashSet<>();
        set.add(userType);
        pushTag();
    }

    private void pushTag() {
        JPushInterface.setAliasAndTags(this, userType, set,
                new TagAliasCallback() {
                    @Override
                    public void gotResult(int result, String arg1,
                                          Set<String> arg2) {
                        if (result == 6002)
                            pushTag();
                        Logger.d("JPushInterface.setTags--------" + userType + "-------------result=" + result);
                    }
                });
    }

    /**
     * 初始化tab内容页
     */
    private void initPagerContent() {

        mNoScrollViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return TabFragment.values().length;
            }

            @Override
            public Fragment getItem(int position) {
                return TabFragment.values()[position].fragment();
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                //ViewPager切换不重新创建Fragment,注释super
//                super.destroyItem(container, position, object);
            }

            @Override
            public void destroyItem(@NonNull View container, int position, @NonNull Object object) {
                //ViewPager切换不重新创建Fragment,注释super
//                super.destroyItem(container, position, object);
            }
        });

        mNoScrollViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        mRadioGroup.check(R.id.main_tab_home);
                        break;
                    case 1:
                        mRadioGroup.check(R.id.main_tab_find);
                        break;
                    case 2:
                        mRadioGroup.check(R.id.main_tab_add);
                        break;
                    case 3:
                        mRadioGroup.check(R.id.main_tab_campus);
                        break;
                    case 4:
                        mRadioGroup.check(R.id.main_tab_mine);
                        break;
                }
            }
        });
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        switch (checkedId) {
            case R.id.main_tab_home:
                mNoScrollViewPager.setCurrentItem(0);
                break;
            case R.id.main_tab_find:
                mNoScrollViewPager.setCurrentItem(1);
                break;
            case R.id.main_tab_add:
                mNoScrollViewPager.setCurrentItem(2);
                break;
            case R.id.main_tab_campus:
                mNoScrollViewPager.setCurrentItem(3);
                break;
            case R.id.main_tab_mine:
                mNoScrollViewPager.setCurrentItem(4);
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_tab_add:
                mNoScrollViewPager.setCurrentItem(2);
                break;
            case R.id.my_sign:
                Goto.toSingleActivity(mContext);
                mDrawerLayout.closeDrawers();
                break;
            case R.id.my_resume:
                Goto.toMyResumeActivity(mContext);
                mDrawerLayout.closeDrawers();
                break;
            case R.id.my_send:
                Goto.toMySendActivity(mContext);
                break;
            case R.id.my_select:
                Goto.toMyCollectionActivity(mContext);
                mDrawerLayout.closeDrawers();
                break;
            case R.id.setting_home:
                if (!AppConfig.ISlOGINED) {
//                    new AlertDialog.Builder(activity).setTitle("提示！").setMessage("您尚未登录，请先登录。").setPositiveButton("登录", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            Goto.toLogin(activity);
//                        }
//                    }).setNegativeButton("取消", null).show();
                } else {
                    Goto.toMySettingActivity(mContext);
                }

                mDrawerLayout.closeDrawers();
                break;
//            case R.id.news_home:
//                Goto.toHomeAndFindHelpEachOtherActivity(mContext);
//                break;
            case R.id.chat_layout:
                Goto.toFragmentMessageActivity(mContext);
                mDrawerLayout.closeDrawers();
                break;
            case R.id.linearLayout_full:
                //我的投递
                Goto.toMySendActivityFull(mContext);
                mDrawerLayout.closeDrawers();
                break;
            case R.id.linearLayout_park:
                //我的投递
                Goto.toMySendActivity(mContext);
                mDrawerLayout.closeDrawers();
                break;
            case R.id.delete_train:
                //培训
                Goto.toTraingActivity(mContext);
                mDrawerLayout.closeDrawers();
                break;
            case R.id.huzhu:
                //互助
                Goto.toHelpEachOtherActivity(mContext);
                mDrawerLayout.closeDrawers();
                break;
            case R.id.logo:
                Goto.toUCentreBaseInfoActivity(mContext);
                mDrawerLayout.closeDrawers();
                break;
            case R.id.my_coffers:
                Goto.toMyCoffersActivity(mContext);
                mDrawerLayout.closeDrawers();
                break;
            case R.id.my_pay:
                Goto.toMyPayActivity(mContext);
                mDrawerLayout.closeDrawers();
                break;
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }


    public void open() {
        updateVersionPresenter.getBaseInfoLoginAfter(this);
        setDrawableOPpen();
    }

    public void setDrawableOPpen() {
        navigationView.setBackgroundColor(Color.WHITE);
        mDrawerLayout.setBackgroundColor(Color.WHITE);
        String no_read_num_es = (String) SpCommonUtils.get(activity, AppConfig.FINAL_NO_READ_NUM, "");
        String train_num = (String) SpCommonUtils.get(activity, AppConfig.FINAL_NUM_TRAIN_NUM, "");
        String full_job_num = (String) SpCommonUtils.get(activity, AppConfig.FINAL_NUM_FULL_JOB_NUM, "");
        String part_job_num = (String) SpCommonUtils.get(activity, AppConfig.FINAL_NUM_FULL_PARK_NUM, "");
        String help_num = (String) SpCommonUtils.get(activity, AppConfig.FINAL_NUM_FULL_HULP_NUM, "");
        String nickname = (String) SpCommonUtils.get(activity, AppConfig.FINAL_NUM_FULL_HULP_NICKNAME, "");
        int type = (int) SpCommonUtils.get(activity, AppConfig.FINAL_NUM_FULL_TYPE, 1);
        int auth_status = (int) SpCommonUtils.get(activity, AppConfig.FINAL_NUM_FULL_AUTH_STATUS, 1);
        String avatar = (String) SpCommonUtils.get(activity, AppConfig.FINALUAVATAR, "");
        String gender = (String) SpCommonUtils.get(activity, AppConfig.FINALGENDEREEE, "1");

        if (no_read_num_es.equals("0")) {
            no_read_num.setVisibility(View.GONE);
        } else {
            no_read_num.setText(no_read_num_es);
            no_read_num.setVisibility(View.VISIBLE);
        }
        delete_train_text.setText(train_num);
        linearLayout_full_text.setText(full_job_num);
        linearLayout_park_text.setText(part_job_num);
        huzhu_text.setText(help_num);
        textViewName.setText(nickname);
//    public int type;                //用户类型（1个人/2企业/3培训机构）

        delete_train.setVisibility(View.VISIBLE);
        delete_train.setVisibility(View.VISIBLE);
        huzhu.setVisibility(View.VISIBLE);
        type = AppConfig.USERIDTYPE;
      //  System.out.println("userInfouserInfouserIns试试fo.type" + type);
        if (type == 1) { //1个人/2企业/3培训机构
            delete_train.setVisibility(View.GONE); //培训
            mMyCoffers.setVisibility(View.VISIBLE);//金 库
            mMyPay.setVisibility(View.VISIBLE);//金 库
            huzhu.setVisibility(View.VISIBLE);
        }
        if (type == 2) {
            delete_train.setVisibility(View.VISIBLE); //培训
            mMyCoffers.setVisibility(View.GONE);//金 库
            mMyPay.setVisibility(View.GONE);//金 库
            huzhu.setVisibility(View.GONE);
        }
        if (type == 3) {
            huzhu.setVisibility(View.GONE);
            mMyCoffers.setVisibility(View.GONE);//金 库
            mMyPay.setVisibility(View.GONE);//金 库
            delete_train.setVisibility(View.VISIBLE); //培训
        }
        shiming_text.setText(auth_status == 1 ? "未认证" : "已认证");
        // public int auth_status;  //认证状态（1：未认证；2：审核中；3：已认证；4：认证不通过；）

        if (Integer.valueOf(gender) == 1) {
            Glide.with(MainActivity.this).load("").error(R.drawable.new_male).into(logo_man);
        } else {
            Glide.with(MainActivity.this).load("").error(R.drawable.moman).into(logo_man);
        }


        if (!AppConfig.LOGINPHOTOTPATH.equals("")) {
            avatar = AppConfig.LOGINPHOTOTPATH;
        }

        Glide.with(MainActivity.this).load(avatar).asBitmap().error(R.drawable.new_eorrlogo).centerCrop().into(new BitmapImageViewTarget(logoImg) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(MainActivity.this.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                logoImg.setImageDrawable(circularBitmapDrawable);
            }
        });


    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void remove() {
        super.remove();

        TabFragment.onDestroy();
    }

    @Override
    public void getGetBaseInfoSuccess(UserInfo userInfo) {

    }

    public void gsetBaseInfoSuccess(UserInfo userInfo) {
        String no_read_num_es = "0";
        int num = Integer.valueOf(userInfo.no_read_num);
        if (num < 1) {
            num = 0;
        }
        no_read_num_es = num + "";
        if (no_read_num != null) {
            if (no_read_num_es.equals("0")) {
                no_read_num.setVisibility(View.GONE);
            } else {
                no_read_num.setText(no_read_num_es);
                no_read_num.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * private ChatFragment chatFragment;
     * private ClubFragment clubFragment;
     * private MineFragment mineFragment;
     * private HomeFragment homeFragment;
     */

    private enum TabFragment {
        home(R.id.nav_home, HomeFragment.class),
        part(R.id.nav_part, PartTimeFragment.class),
        //release(R.id.nav_release, AddFragment.class),
        club(R.id.nav_full, ClubFragment.class),
        chat(R.id.nav_release, ChatFragment.class),
        me(R.id.nav_compus, MineFragment.class);
//me(R.id.nav_compus, NewCampusFragment.class);

        private Fragment fragment;
        private final int menuId;
        private final Class<? extends Fragment> clazz;

        TabFragment(@IdRes int menuId, Class<? extends Fragment> clazz) {
            this.menuId = menuId;
            this.clazz = clazz;
        }

        @NonNull
        public Fragment fragment() {
            if (fragment == null) {
                try {
                    fragment = clazz.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                    fragment = new Fragment();
                }
            }
            return fragment;
        }

        public static TabFragment from(int itemId) {
            for (TabFragment fragment : values()) {
                if (fragment.menuId == itemId) {
                    return fragment;
                }
            }
            return home;
        }

        public static void onDestroy() {
            for (TabFragment fragment : values()) {
                fragment.fragment = null;
            }

        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEventBus(Integer changeTab) {
        if (changeTab != null) {
            switch (changeTab) {
                case 0:
                    mRadioGroup.check(R.id.main_tab_home);
                    break;
                case 1:
                    mRadioGroup.check(R.id.main_tab_find);
                    break;
                case 2:
                    mRadioGroup.check(R.id.main_tab_add);
                    break;
                case 3:
                    mRadioGroup.check(R.id.main_tab_campus);
                    break;
                case 4:
                    mRadioGroup.check(R.id.main_tab_mine);
                    break;
            }
        }
    }

    //声明AMapLocationClient类对象
    AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    private LatLonPoint latLonPoint;

    public void getCurrentLocationLatLng() {
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();

 /* //设置定位场景，目前支持三种场景（签到、出行、运动，默认无场景） 设置了场景就不用配置定位模式等
    option.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.SignIn);
    if(null != locationClient){
        locationClient.setLocationOption(option);
        //设置场景模式后最好调用一次stop，再调用start以保证场景模式生效
        locationClient.stopLocation();
        locationClient.startLocation();
    }*/
        // 同时使用网络定位和GPS定位,优先返回最高精度的定位结果,以及对应的地址描述信息
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //只会使用网络定位
        /* mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);*/
        //只使用GPS进行定位
        /*mLocationOption.setLocationMode(AMapLocationMode.Device_Sensors);*/
        // 设置为单次定位 默认为false
        /*mLocationOption.setOnceLocation(true);*/
        //设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。默认连续定位 切最低时间间隔为1000ms
        mLocationOption.setInterval(3500);
        //设置是否返回地址信息（默认返回地址信息）
        /*mLocationOption.setNeedAddress(true);*/
        //关闭缓存机制 默认开启 ，在高精度模式和低功耗模式下进行的网络定位结果均会生成本地缓存,不区分单次定位还是连续定位。GPS定位结果不会被缓存。
        /*mLocationOption.setLocationCacheEnable(false);*/
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }

    /**
     * 定位回调监听器
     */
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            if (!JudgeUtils.isOPenGPS(getApplicationContext())) {
                if (gpsShowToast) {
                    Toast toast = Toast.makeText(getApplicationContext(), "检测GPS没有开启", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    gpsShowToast = false;
                }
            } else {
                if (amapLocation != null) {
                    if (amapLocation.getErrorCode() == 0) {
                        //定位成功回调信息，设置相关消息
                        amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                        double currentLat = amapLocation.getLatitude();//获取纬度
                        double currentLon = amapLocation.getLongitude();//获取经度

                        latLonPoint = new LatLonPoint(currentLat, currentLon);  // latlng形式的
                        AppConfig.CURRENTLAT = String.valueOf(latLonPoint.getLatitude());
                        AppConfig.CURRENTLON = String.valueOf(latLonPoint.getLongitude());

                        amapLocation.getAccuracy();//获取精度信息
                    } else {
                        //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                        Log.e("AmapError", "location Error, ErrCode:"
                                + amapLocation.getErrorCode() + ", errInfo:"
                                + amapLocation.getErrorInfo());
                    }
                }
            }
        }
    };
}

