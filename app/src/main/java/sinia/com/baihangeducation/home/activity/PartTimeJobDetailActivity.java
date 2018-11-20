package sinia.com.baihangeducation.home.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.ParseException;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdate;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.CoordinateConverter;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.geocoder.GeocodeAddress;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.example.framwork.utils.LogUtils;
import com.fm.openinstall.OpenInstall;
import com.fm.openinstall.listener.AppWakeUpAdapter;
import com.fm.openinstall.model.AppData;
import com.mcxtzhang.swipemenulib.info.bean.JobInfoes;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMWeb;
import com.zzhoujay.richtext.RichText;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.club.im.ChatActivity;
import sinia.com.baihangeducation.home.present.JobInfoesPresenter;
import sinia.com.baihangeducation.home.view.JobInfoView;
import sinia.com.baihangeducation.newcampus.info.FunContantInfo;
import sinia.com.baihangeducation.supplement.View.MapContainer;
import sinia.com.baihangeducation.supplement.base.BaseActivity;
import sinia.com.baihangeducation.MyApplication;

import com.mcxtzhang.swipemenulib.info.bean.AddCollectionSuccessInfo;

import sinia.com.baihangeducation.home.present.AddOrDetelCollctionPresenter;
import sinia.com.baihangeducation.home.present.JobInfoPresenter;
import sinia.com.baihangeducation.home.view.AddCollctionView;
import sinia.com.baihangeducation.home.view.JobView;
import sinia.com.baihangeducation.supplement.base.Goto;
import sinia.com.baihangeducation.supplement.tool.BaseUtil;

import com.mcxtzhang.swipemenulib.utils.Constants;

/**
 * 兼职详情
 */

public class PartTimeJobDetailActivity extends BaseActivity implements JobInfoView, AddCollctionView, GeocodeSearch.OnGeocodeSearchListener {

    private JobInfoesPresenter mJobInfoPresenter;
    private AddOrDetelCollctionPresenter mAddOrDetelCollctionPresenter;
    private String addCollectionType = "2";
    private String collectionId;
    private Intent intent;
    private String jobId;
    private JobInfoes jobInfo;
    private MyApplication application;
    private View contentView;
    private PopupWindow popupWindow;

    private TextView mTitle;                //标题
    private TextView mAdressAndDare;                //地址和时间
    private TextView mCompanyName;                //企业名称
    private ImageView mIsRealName;                //是否认证标签
    private TextView mPrice;                //薪资
    private TextView mJobType;                //兼职类型
    private TextView mNeedNum;                //需要人数
    private TextView mjie;                //性别要求
    private TextView mLanguage;                //语言要求
    private TextView mWorkExp;                //工作经验
    private TextView mWorkDate;                //工作日期
    private TextView mWorkTime;                //工作时间
    private TextView mWorkContent;                //工作内容
    private TextView mDetailAdress;                //详细地址
    private TextView mLinkTel;                //联系电话
    private TextView mLinkName;                //联系人名字
    private LinearLayout mCollectionLayout;     //收藏的layout
    private ImageView mCollection;              //收藏的图标
    private LinearLayout mShareLayout;          //转发的layout
    private TextView mSendResume;               //投递简历
    private TextView mLockPersonNumber;
    private TextView mApplyPersonNumber;
    private TextView mAgo;
    private TextView mNeedSex;
    private TextView mNeedAge;
    private TextView mNeedFace;
    private TextView mNeedContinuous;
    private WebView mContent;
    private ImageView imageViewShow;
    private ImageView imageViewCollect;

    private boolean isCollect = false;
    private ImageView online_contact;
    private String club = "";
    private String phone = "";
    private LinearLayout potion_icon;

    @Override
    public int initLayoutResID() {
        return R.layout.fragment_home_parttimejobinfo_update;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mJobInfoPresenter != null) {
            mJobInfoPresenter = new JobInfoesPresenter(context, this);
            mJobInfoPresenter.getJobInfo();
        }
    }

    private String jobInfo_is_collect = "";


    @Override
    protected void initData() {


        mCommonTitle.setCenterText("兼职");
        mCommonTitle.setCenterTextColor(R.color.black);
        mCommonTitle.setBackground(getResources().getDrawable(R.color.white));
        mCommonTitle.getLeftRes().setImageDrawable(getResources().getDrawable(R.drawable.back_black));

        imageViewCollect = mCommonTitle.getRightImgOne();
        imageViewCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(AppConfig.USERID + "AppConfigAppConfigaaaaaaaaaa" + jobInfo_is_collect);  //2
                if (!jobInfo_is_collect.equals("1")) { //   是否收藏 1是2否
                    mAddOrDetelCollctionPresenter.addCollection();
                } else {
                    mAddOrDetelCollctionPresenter.detelCollection();
                }
            }
        });

        imageViewShow = mCommonTitle.getRightImgTwo();
        imageViewShow.setBackgroundResource(R.drawable.new_find_unshare);
        imageViewShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!BaseUtil.isLogin(context, application)) {
                    return;
                }
                if (jobInfo != null)
                    addShareMeun();
            }
        });


        application = (MyApplication) context.getApplication();

        mJobInfoPresenter = new JobInfoesPresenter(context, this);
        mJobInfoPresenter.getJobInfo();
        mAddOrDetelCollctionPresenter = new AddOrDetelCollctionPresenter(context, this);
    }

    @Override
    protected void initView() {
        intent = getIntent();
        jobId = intent.getStringExtra("JOBID");
        club = intent.getStringExtra("club");
        phone = intent.getStringExtra("phone");
//        if (intent.getStringExtra("club") != null && !intent.getStringExtra("club").equals("")) {
//        }

        mTitle = $(R.id.fragment_home_parttimejobinfo_title);
        mAdressAndDare = $(R.id.fragment_home_parttimejobinfo_adressanddate);
        mCompanyName = $(R.id.fragment_home_parttimejobinfo_companyname);
        mIsRealName = $(R.id.fragment_home_parttimejobinfo_isrealname);
        mPrice = $(R.id.fragment_home_parttimejobinfo_price);
        mJobType = $(R.id.fragment_home_parttimejobinfo_jobtype);
        mNeedNum = $(R.id.fragment_home_parttimejobinfo_neednum);
        mjie = $(R.id.fragment_home_parttimejobinfo_jie);
        mLanguage = $(R.id.fragment_home_parttimejobinfo_language);
        mWorkExp = $(R.id.fragment_home_parttimejobinfo_workexp);
        mWorkDate = $(R.id.fragment_home_parttimejobinfo_workdate);
        mWorkTime = $(R.id.fragment_home_parttimejobinfo_worktime);
        mWorkContent = $(R.id.fragment_home_parttimejobinfo_workcontent);
        mDetailAdress = $(R.id.fragment_home_parttimejobinfo_detailadress);
        mLinkTel = $(R.id.fragment_home_parttimejobinfo_linktel);
        mLinkName = $(R.id.fragment_home_parttimejobinfo_linkname);
        online_contact = $(R.id.online_contact);
        mCollectionLayout = $(R.id.fragment_home_parttimejobinfo_collectionimglayout);
        mCollection = $(R.id.fragment_home_parttimejobinfo_collectionimg);
        mShareLayout = $(R.id.fragment_home_parttimejobinfo_sharelayout);
        mSendResume = $(R.id.fragment_home_parttimejobinfo_sendresume);
        potion_icon = $(R.id.potion_icon);



        mLockPersonNumber = $(R.id.lock_person_number);
        mApplyPersonNumber = $(R.id.apply_person_number);
        mAgo = $(R.id.ago);
        mNeedSex = $(R.id.need_sex);
        mNeedAge = $(R.id.need_age);
        mNeedFace = $(R.id.need_face);
        mNeedContinuous = $(R.id.need_continuous);
        mContent = $(R.id.content);
        MapContainer map_container = $(R.id.map_container);
        ScrollView scrollView = $(R.id.scrollview);
        map_container.setScrollView(scrollView);
        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);//创建地图
        aMap = mapView.getMap();//获取地图对象

        mCollectionLayout.setOnClickListener(this);
        mShareLayout.setOnClickListener(this);
        mSendResume.setOnClickListener(this);
        online_contact.setOnClickListener(this);
        potion_icon.setOnClickListener(this);

        if (club.equals("")) {
            online_contact.setVisibility(View.GONE);
        } else {
            online_contact.setVisibility(View.VISIBLE);
        }

        if (club.equals("club")) {
            mSendResume.setText("分享");
        }
    }

    MapView mapView = null;//地图视图
    AMap aMap;//地图对象
    CameraUpdate cameraUpdate;


    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (!BaseUtil.isLogin(context, application)) {
            return;
        }
        switch (v.getId()) {
            case R.id.potion_icon:
                Goto.toSystemMeaagePotion(context, jobInfo.job_insurance_url);
                break;
            case R.id.fragment_home_parttimejobinfo_collectionimglayout:
                //收藏
//                Log.i("收藏", jobInfo.is_collect + "点击收藏按钮的时候判断是否收藏");
//                Log.i("收藏", collectionId + "点击收藏按钮的时候判断收藏ID");

                break;
            case R.id.fragment_home_parttimejobinfo_sharelayout:
                //分享

                break;
            case R.id.online_contact:
                //打开单聊
                JMessageClient.getUserInfo(phone, new GetUserInfoCallback() {
                    @Override
                    public void gotResult(int i, String s, cn.jpush.im.android.api.model.UserInfo userInfo) {
                        chat(userInfo);
                    }
                });

                break;
            case R.id.fragment_home_parttimejobinfo_sendresume:
                //投递简历
                mJobInfoPresenter.sendResume();


//                if (club.equals("club")) {
//                    if (jobInfo != null) {
//                        addShareMeun();
//                        System.out.println(" clu==2====");
//                    }
//                } else {
//                    mJobInfoPresenter.sendResume();
//                }

                break;
            case R.id.sharemeun_qqfriend:
                //QQ好友
                doShare(SHARE_MEDIA.QQ);
                break;
            case R.id.sharemeun_qqzone:
                //QQ空间
                doShare(SHARE_MEDIA.QZONE);
                Toast.makeText(context, "QQ空间", Toast.LENGTH_SHORT).show();
                break;
            case R.id.sharemeun_wechatfriend:
                //微信好友
                doShare(SHARE_MEDIA.WEIXIN);
                Toast.makeText(context, "微信好友", Toast.LENGTH_SHORT).show();
                break;
            case R.id.sharemeun_moment:
                //朋友圈
                doShare(SHARE_MEDIA.WEIXIN_CIRCLE);
                Toast.makeText(context, "朋友圈", Toast.LENGTH_SHORT).show();
                break;
        }
    }


    public void chat(cn.jpush.im.android.api.model.UserInfo userInfo) {
        Intent intent = new Intent();
        intent.putExtra(MyApplication.CONV_TITLE, userInfo.getNickname());
        String targetId = userInfo.getUserName();
        intent.putExtra(MyApplication.TARGET_ID, targetId);
        intent.putExtra(MyApplication.TARGET_APP_KEY, userInfo.getAppKey());
        intent.putExtra(MyApplication.DRAFT, "");
        intent.setClass(context, ChatActivity.class);
        startActivity(intent);
    }


    /**
     * 分享
     *
     * @param
     */
//    private void doShare(SHARE_MEDIA media) {
//        UMWeb web = new UMWeb(jobInfo.job_share_url);
//        web.setTitle(jobInfo.job_title);//标题
//        new ShareAction(PartTimeJobDetailActivity.this)
//                .setPlatform(media)
//                .withMedia(web)
//                .setCallback(shareListener)
//                .share();
//    }
    private void doShare(SHARE_MEDIA media) {

        UMWeb web = new UMWeb(jobInfo.job_share_url);
        web.setTitle(jobInfo.job_share_title);//标题
        web.setDescription(jobInfo.job_share_introduce);
//        web.setThumb(thumb);  //缩略图
        new ShareAction(PartTimeJobDetailActivity.this)
                .setPlatform(media)
                .withMedia(web)
                .setCallback(new UMShareListener() {
                    @Override
                    public void onStart(SHARE_MEDIA platform) {
                    }

                    /**
                     * @descrption 分享成功的回调
                     * @param platform 平台类型
                     */
                    @Override
                    public void onResult(SHARE_MEDIA platform) {
//                        Toast.makeText(context, "分享成功", Toast.LENGTH_LONG).show();
                    }

                    /**
                     * @descrption 分享失败的回调
                     * @param platform 平台类型
                     * @param t 错误原因
                     */
                    @Override
                    public void onError(SHARE_MEDIA platform, Throwable t) {
                        Toast.makeText(context, "分享失败" + t.getMessage(), Toast.LENGTH_LONG).show();
                    }

                    /**
                     * @descrption 分享取消的回调
                     * @param platform 平台类型
                     */
                    @Override
                    public void onCancel(SHARE_MEDIA platform) {
                        Toast.makeText(context, "分享取消了", Toast.LENGTH_LONG).show();
                    }
                })
                .share();
    }


    //分享回调监听
    private UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {
        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
//            Toast.makeText(context, "分享成功dddddddddd", Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(context, "分享失败" + t.getMessage(), Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(context, "分享取消", Toast.LENGTH_LONG).show();
        }
    };

    /**
     * 分享popwindow
     */
    private void addShareMeun() {
        contentView = LayoutInflater.from(this).inflate(
                R.layout.sharemenu, null);
        // 创建PopupWindow对象
        popupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, false);
        // 需要设置一下此参数，点击外边可消失
        TextView qq = contentView.findViewById(R.id.sharemeun_qqfriend);
        TextView qqZone = contentView.findViewById(R.id.sharemeun_qqzone);
        TextView wechat = contentView.findViewById(R.id.sharemeun_wechatfriend);
        TextView moment = contentView.findViewById(R.id.sharemeun_moment);
        qq.setOnClickListener(this);
        qqZone.setOnClickListener(this);
        wechat.setOnClickListener(this);
        moment.setOnClickListener(this);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        // 设置点击窗口外边窗口消失
        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(R.style.pop_anim);
        // 设置此参数获得焦点，否则无法点击
        popupWindow.setFocusable(true);
        popupWindow.showAtLocation(mShareLayout.getRootView(), Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void showLoading() {
        showProgress();
    }

    @Override
    public void hideLoading() {
        hideProgress();
    }

    @Override
    public String getJobId() {
        return jobId;
    }

    @Override
    public String getCityId() {
        return Constants.city_id;
    }

    @Override
    public void getJobInfoSuccess(JobInfoes mJobInfo) {
        if (mJobInfo != null) {
            jobInfo = mJobInfo;
            jobInfo_is_collect = mJobInfo.is_collect;
            collectionId = mJobInfo.collect_id;
//            phone = mJobInfo.job_link_phone;
            mTitle.setText(mJobInfo.job_title);
            mPrice.setText(mJobInfo.job_money);
            mAdressAndDare.setText(mJobInfo.job_city_name);//+ " " + mJobInfo.job_add_date
            mCompanyName.setText(mJobInfo.job_company_name);
            mLockPersonNumber.setText("浏览" + mJobInfo.job_look_num + "人");
            mApplyPersonNumber.setText("申请" + mJobInfo.job_apply_num + "人");
            try {
                int time = 0;
                try {
                    time = LogUtils.daysOfTwo(LogUtils.getCurrentTimeByymd().toString(), mJobInfo.job_add_date);
                    if (time == 0) {
                        mAgo.setText("刚刚");//+ " " + item.job_add_date
                    } else if (time == 1) {
                        mAgo.setText("昨天");//+ " " + item.job_add_date
                    } else if (time == 2) {
                        mAgo.setText("前天");//+ " " + item.job_add_date
                    } else {
                        mAgo.setText(mJobInfo.job_add_date);//+ " " + item.job_add_date
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } catch (java.text.ParseException e) {
                e.printStackTrace();
            }
            mNeedNum.setText(mJobInfo.job_people_num + "人");
            mJobType.setText(mJobInfo.job_industry_name_ex);
            mNeedSex.setText(mJobInfo.job_sex_name);
            if (mJobInfo.job_age_lower_ex.equals("0") || mJobInfo.job_age_upper_ex.equals("0")) {
                mNeedAge.setText("不限");
            } else {
                mNeedAge.setText(mJobInfo.job_age_lower_ex + "-" + mJobInfo.job_age_upper_ex);
            }

            mjie.setText(mJobInfo.job_money_name_ex);
            mWorkExp.setText(mJobInfo.job_experience);
            mNeedFace.setText(mJobInfo.job_is_interview);
            mNeedContinuous.setText(mJobInfo.job_is_continue);
            mWorkDate.setText(mJobInfo.job_time_group);
            mLinkName.setText(mJobInfo.job_link_person);
            mDetailAdress.setText(mJobInfo.job_link_type_name);
            mLinkTel.setText(mJobInfo.job_link_phone);

//            online_contact.setVisibility(AppConfig.SHOWCLUBJOB ? View.VISIBLE : View.INVISIBLE);


            if (!mJobInfo.job_content.isEmpty())
                mContent.loadDataWithBaseURL(null, mJobInfo.job_content, "text/html", "utf-8", null);

//            System.out.println("======报错======"); ssssssssss
//            mContent.setText(Html.fromHtml(mJobInfo.job_content, new Html.ImageGetter() {
//                @Override
//                public Drawable getDrawable(String s) {
//                    System.out.println("======报错======" + s);
//                    // ======报错====== http://apps.bdimg.com/libs/ueditor/1.4.3.1/themes/default/images/spacer.gif
//                    if (!s.endsWith("gif")) {
//                        int drawableId = Integer.parseInt(s);
//                        Drawable drawable = getResources().getDrawable(drawableId);
//                        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
//                        return drawable;
//                    }
//
//
//                    return null;
//                }
//            }, null));

            GeocodeSearch(mJobInfo.job_address);
//            if (mJobInfo.job_company_status == 1) {
//                mIsRealName.setVisibility(View.VISIBLE);
//            } else {
//                mIsRealName.setVisibility(View.GONE);
//            }
//
//
//
//            mSex.setText(mJobInfo.job_sex_name);
//            mLanguage.setText(mJobInfo.job_language);
//
//            mWorkTime.setText(mJobInfo.job_work_time);
//            RichText.from(mJobInfo.job_content).into(mWorkContent);
//
//
//

            if (jobInfo_is_collect.equals("1")) {//是否收藏 1是2否
                imageViewCollect.setBackgroundResource(R.drawable.new_find_collection);
                isCollect = true;
            } else {
                imageViewCollect.setBackgroundResource(R.drawable.new_find_uncollection);
                isCollect = false;
                //   mCollection.setBackground(getResources().getDrawable(R.drawable.uncollect_icon));//new_find_uncollection
            }
//            if (mJobInfo.is_apply == 1) {
//                mSendResume.setBackground(getResources().getDrawable(R.drawable.textview_unchoicw_round));
//                mSendResume.setText("已投递");
//                mSendResume.setEnabled(false);
//            } else {
//                mSendResume.setBackground(getResources().getDrawable(R.drawable.textview_round));
//                mSendResume.setEnabled(true);
//                mSendResume.setText("投递简历");
//            }
        }
    }


    @Override
    public String getAddCollctionType() {
        return addCollectionType;
    }

    @Override
    public String getAddClloectionTpyeId() {
        return jobInfo.job_id;
    }

    @Override
    public String getAddClloectionId() {
        return collectionId;
    }

    @Override
    public void addCollectionSuccess(AddCollectionSuccessInfo mAddCollectionSuccessInfo) {
        if (AppConfig.ISlOGINED) {
            collectionId = String.valueOf(mAddCollectionSuccessInfo.collect_id);
            imageViewCollect.setBackgroundResource(R.drawable.new_find_collection);
            jobInfo_is_collect = "1";
        }

//        mJobInfoPresenter.getJobInfo();
//        是否收藏 1是2否
//        mJobInfoPresenter.getJobInfo();
    }

    @Override
    public void detelCollectionSuccess() {
        if (AppConfig.ISlOGINED) {
            imageViewCollect.setBackgroundResource(R.drawable.new_find_uncollection);
            jobInfo_is_collect = "2";
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, intent);
    }


    private String city = "";

    public void GeocodeSearch(String city) {
        this.city = city;
        //构造 GeocodeSearch 对象，并设置监听。
        GeocodeSearch geocodeSearch = new GeocodeSearch(this);
        geocodeSearch.setOnGeocodeSearchListener(this);
//通过GeocodeQuery设置查询参数,调用getFromLocationNameAsyn(GeocodeQuery geocodeQuery) 方法发起请求。
//address表示地址，第二个参数表示查询城市，中文或者中文全拼，citycode、adcode都ok
        GeocodeQuery query = new GeocodeQuery(city, city);
        geocodeSearch.getFromLocationNameAsyn(query);
    }


    //解析指定坐标的地址
    private void getadress(final float job_lng, final float job_lat,
                           final String job_address) {
        Log.e("Shunxu", "调用getadress");
        GeocodeSearch geocodeSearch = new GeocodeSearch(this);//地址查询器

        //设置查询参数,
        //三个参数依次为坐标，范围多少米，坐标系
        RegeocodeQuery regeocodeQuery = new RegeocodeQuery(new LatLonPoint(Double.valueOf(job_lat), Double.valueOf(job_lng)), 200, GeocodeSearch.AMAP);

        //设置查询结果监听
        geocodeSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
            //根据坐标获取地址信息调用
            @Override
            public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
                String s = regeocodeResult.getRegeocodeAddress().getFormatAddress();
                Log.e("Shunxu", "获得请求结果");
                makepoint(job_lng, job_lat, job_address);
            }

            //根据地址获取坐标信息是调用
            @Override
            public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {
            }
        });

        geocodeSearch.getFromLocationAsyn(regeocodeQuery);//发起异步查询请求
    }

    //根据地址绘制需要显示的点
    public void makepoint(float job_lng, float job_lat, String sss) {
        Log.e("Shunxu", "开始绘图");
        //北纬39.22，东经116.39，为负则表示相反方向
        LatLng latLng = new LatLng(Double.valueOf(job_lat), Double.valueOf(job_lng));
        Log.e("地址", sss);

        MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("地点:").snippet(sss);
        //使用默认点标记
        aMap.addMarker(markerOptions).showInfoWindow();


//        //自定义点标记
//        MarkerOptions markerOptions = new MarkerOptions();
//        markerOptions.position(new LatLng(34, 115)).title("标题").snippet("内容");
//        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
//                .decodeResource(getResources(), R.mipmap.ic_launcher)));//设置图标
//        aMap.addMarker(markerOptions);

        //改变可视区域为指定位置
        //CameraPosition4个参数分别为位置，缩放级别，目标可视区域倾斜度，可视区域指向方向（正北逆时针算起，0-360）
        cameraUpdate = CameraUpdateFactory.newCameraPosition(new CameraPosition(latLng, 8, 0, 30));
        aMap.moveCamera(cameraUpdate);//地图移向指定区域
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        UMShareAPI.get(this).release();

    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {

    }

    //正地理编码
    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

        if (i == AMapException.CODE_AMAP_SUCCESS) {
            if (geocodeResult != null && geocodeResult.getGeocodeAddressList() != null
                    && geocodeResult.getGeocodeAddressList().size() > 0) {
                GeocodeAddress address = geocodeResult.getGeocodeAddressList().get(0);
                //获取到的经纬度
                LatLonPoint latLongPoint = address.getLatLonPoint();

                getadress((float) latLongPoint.getLongitude(), (float) latLongPoint.getLatitude(), city);

            }
        }
    }
}
