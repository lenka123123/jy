package sinia.com.baihangeducation.home.activity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.framwork.glide.ImageLoaderUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMWeb;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;
import com.zzhoujay.richtext.RichText;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.supplement.base.BaseActivity;
import sinia.com.baihangeducation.MyApplication;

import com.mcxtzhang.swipemenulib.info.bean.AddCollectionSuccessInfo;

import sinia.com.baihangeducation.home.adapter.AllJobDetailAdapter;

import com.mcxtzhang.swipemenulib.info.JobInfo;

import sinia.com.baihangeducation.home.present.AddOrDetelCollctionPresenter;
import sinia.com.baihangeducation.home.present.JobInfoPresenter;
import sinia.com.baihangeducation.home.view.AddCollctionView;
import sinia.com.baihangeducation.home.view.JobView;
import sinia.com.baihangeducation.supplement.base.Goto;
import sinia.com.baihangeducation.supplement.tool.BaseUtil;

import com.mcxtzhang.swipemenulib.utils.Constants;
import com.mcxtzhang.swipemenulib.customview.NoScrollListView;

import java.util.Set;

/**
 * Created by Administrator on 2018/4/23.
 */

public class AllJobDetailActivity extends BaseActivity implements JobView, AddCollctionView {

    private JobInfoPresenter mJobInfoPresenter;
    private AddOrDetelCollctionPresenter mAddOrDetelCollctionPresenter;
    private Intent intent;
    private String jobId;
    private JobInfo jobInfo;
    private AllJobDetailAdapter mAllJobDetailAdapter;
    private View contentView;
    private PopupWindow popupWindow;
    private int collectionId;
    private String addCollectionType = "1";
    private MyApplication application;

    private boolean isSuccess = true;

    private TextView mTitle;                    //标题
    private TextView mAdressAndDate;            //地址和发布时间
    private TextView mReleaseCompany;           //发布企业名字
    private ImageView mIsRealName;              //认证标识
    private TextView mPrice;                    //薪资
    private LinearLayout mCompanyInfoLayout;    //企业信息layout
    private ImageView mCompanyLogo;             //企业logo
    private TextView mCompanyName;              //企业名字
    private TextView mCompanyAdress;            //企业地址
    private TextView mCompanyTel;               //企业电话
    private TagFlowLayout mCompanyWelfare;      //企业福利
    private TextView mJobContent;               //工作内容
    private TextView mDetailAdress;             //联系地址
    private TextView mLinkTel;                  //联系电话
    private TextView mLinkName;                 //联系人
    private NoScrollListView mLikeListView;     //相似职位listView
    private LinearLayout mCollectionLayout;     //收藏最外层layout
    private ImageView mCollection;              //收藏图标
    private LinearLayout mShareLayout;          //分享layout
    private TextView mSendResume;               //投递简历
    private TextView mLikeJob;                  //相似职位标题
    private WebView webview;

    @Override
    public int initLayoutResID() {
        return R.layout.fragment_home_alljobinfo;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mJobInfoPresenter != null) {
            mJobInfoPresenter = new JobInfoPresenter(context, this);
            mJobInfoPresenter.getJobInfo();
        }
    }

    @Override
    protected void initData() {
        intent = getIntent();
        jobId = intent.getStringExtra("JOBID");
        Log.i("传过来的jobid", jobId);
        application = (MyApplication) context.getApplication();
        mCommonTitle.setCenterText(R.string.jobinfo);
        mCommonTitle.setBackground(getResources().getDrawable(R.color.white));

        mAddOrDetelCollctionPresenter = new AddOrDetelCollctionPresenter(context, this);
        mJobInfoPresenter = new JobInfoPresenter(context, this);
        mJobInfoPresenter.getJobInfo();
    }

    @Override
    protected void initView() {
        mTitle = $(R.id.fragment_home_alljobinfo_title);
        mAdressAndDate = $(R.id.fragment_home_alljobinfo_adressanddate);
        mReleaseCompany = $(R.id.fragment_home_alljobinfo_company);
        mIsRealName = $(R.id.fragment_home_alljobinfo_isreallname);
        mPrice = $(R.id.fragment_home_alljobinfo_price);
        mCompanyInfoLayout = $(R.id.fragment_home_alljobinfo_companyinfolayoout);
        mCompanyLogo = $(R.id.fragment_home_alljobinfo_companylogo);
        mCompanyName = $(R.id.fragment_home_alljobinfo_companyname);
        mCompanyAdress = $(R.id.fragment_home_alljobinfo_companyadress);
        mCompanyTel = $(R.id.fragment_home_alljobinfo_companytel);
        mCompanyWelfare = $(R.id.fragment_home_alljobinfo_companywelfare);
        mJobContent = $(R.id.fragment_home_alljobinfo_jobinfo);
        webview = $(R.id.webview);
        mDetailAdress = $(R.id.fragment_home_alljobinfo_linkadress);
        mLinkTel = $(R.id.fragment_home_alljobinfo_linktel);
        mLinkName = $(R.id.fragment_home_alljobinfo_linename);
        mLikeListView = $(R.id.fragment_home_alljobinfo_likelistview);
        mCollectionLayout = $(R.id.fragment_home_alljobinfo_collectionlayout);
        mCollection = $(R.id.fragment_home_alljobinfo_collection);
        mShareLayout = $(R.id.fragment_home_alljobinfo_sharelayout);
        mSendResume = $(R.id.fragment_home_alljobinfo_sendresume);
        mLikeJob = $(R.id.fragment_home_alljobinfo_likejob);

        mTitle.setFocusable(true);
        mTitle.setFocusableInTouchMode(true);
        mTitle.requestFocus();

        mCompanyInfoLayout.setOnClickListener(this);
        mCollectionLayout.setOnClickListener(this);
        mShareLayout.setOnClickListener(this);
        mSendResume.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (!BaseUtil.isLogin(context, application)) {
            return;
        }
        switch (v.getId()) {
            case R.id.fragment_home_alljobinfo_companyinfolayoout:
                //跳转企业详情
                Goto.toCompanyDetailActivity(context, jobInfo.job_company_id);
                break;
            case R.id.fragment_home_alljobinfo_collectionlayout:
                //收藏
                if (jobInfo.is_collect != 1) {
                    mAddOrDetelCollctionPresenter.addCollection();
                } else {
                    mAddOrDetelCollctionPresenter.detelCollection();
                }
                break;
            case R.id.fragment_home_alljobinfo_sharelayout:
                //分享
                if (jobInfo != null)
                    addShareMeun();
                break;
            case R.id.fragment_home_alljobinfo_sendresume:
                //投递简历
                mJobInfoPresenter.sendResume();
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

    /**
     * 分享
     *
     * @param
     */
    private void doShare(SHARE_MEDIA media) {
        UMWeb web = new UMWeb(jobInfo.job_share_url);
        web.setTitle(jobInfo.job_title);//标题
        new ShareAction(context)
                .setPlatform(media)
                .withMedia(web)
                .setCallback(shareListener)
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
//            Toast.makeText(context, "分享成功", Toast.LENGTH_LONG).show();
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

    }

    @Override
    public void hideLoading() {

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
    public void getJobInfoSuccess(final JobInfo mJobInfo) {
        if (mJobInfo != null) {
            Log.i("传过来的jobid", mJobInfo.job_id + "哈哈");
            collectionId = mJobInfo.collect_id;
            jobInfo = mJobInfo;
            mTitle.setText(mJobInfo.job_title);
            mAdressAndDate.setText(mJobInfo.job_address);
            mReleaseCompany.setText(mJobInfo.job_company_name);
            if (mJobInfo.job_company_status == 1) {
                mIsRealName.setVisibility(View.VISIBLE);
            } else {
                mIsRealName.setVisibility(View.GONE);
            }
            mPrice.setText(mJobInfo.job_money);
            ImageLoaderUtils.displayRound(context, mCompanyLogo, mJobInfo.job_company_log, R.drawable.new_eorrlogo);
            mCompanyName.setText(mJobInfo.job_company_name);
            mCompanyAdress.setText(mJobInfo.job_company_address);
            mCompanyTel.setText(mJobInfo.job_company_tel);
            if (mJobInfo.job_tag_list != null && mJobInfo.job_tag_list.size() > 0) {
                mCompanyWelfare.setAdapter(new TagAdapter(mJobInfo.job_tag_list) {
                    @Override
                    public View getView(FlowLayout parent, int position, Object o) {
                        TextView textView = (TextView) LayoutInflater.from(context).inflate(R.layout.keyworlditem, mCompanyWelfare, false);
                        textView.setText(mJobInfo.job_tag_list.get(position).tag_name);
                        return textView;
                    }
                });
                mCompanyWelfare.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
                    @Override
                    public void onSelected(Set<Integer> selectPosSet) {
                        Goto.toCompanyDetailActivity(context, jobInfo.job_company_id);
                    }
                });
            }
//            System.out.println("=================tttttt");
//            System.out.println(mJobInfo.job_content);
            //设置webView里字体大小
            WebSettings settings = webview.getSettings();
            settings.setSupportZoom(true);
            settings.setTextSize(WebSettings.TextSize.SMALLER);
            webview.loadData(mJobInfo.job_content, "text/html; charset=UTF-8", null);//这种写法可以正确解码
//             RichText.from(mJobInfo.job_content).into(mJobContent);
            mDetailAdress.setText(mJobInfo.job_company_address);
            mLinkTel.setText(mJobInfo.job_link_phone);
            mLinkName.setText(mJobInfo.job_link_person);

            if (mJobInfo.is_collect == 1) {
                isSuccess = true;
                mCollection.setBackground(getResources().getDrawable(R.drawable.new_find_collection));
            } else {
                isSuccess = false;
                mCollection.setBackground(getResources().getDrawable(R.drawable.new_find_uncollection));
            }

            if (mJobInfo.like_job_list == null || mJobInfo.like_job_list.size() < 0) {
                mLikeJob.setVisibility(View.GONE);
            } else {
                mAllJobDetailAdapter = new AllJobDetailAdapter(mJobInfo.like_job_list, context);
                mLikeListView.setAdapter(mAllJobDetailAdapter);
                mLikeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        switch (Integer.valueOf(mJobInfo.like_job_list.get(position).job_type)) {
                            case 1:
                                //全职
                                Goto.toAllJobDetailActivity(context, Integer.valueOf(mJobInfo.like_job_list.get(position).job_id));
                                break;
                            case 2:
                                //兼职
                                Goto.toPartTimeJobDetailActivity(context, Integer.valueOf(mJobInfo.like_job_list.get(position).job_id));
                                break;
                        }
                    }
                });
            }

        }
    }

    @Override
    public String getAddCollctionType() {
        return addCollectionType;
    }

    @Override
    public String getAddClloectionTpyeId() {
        return jobId;
    }

    @Override
    public String getAddClloectionId() {
        return collectionId + "";
    }

    @Override
    public void addCollectionSuccess(AddCollectionSuccessInfo mAddCollectionSuccessInfo) {

        if (!isSuccess) {
            mCollection.setBackground(getResources().getDrawable(R.drawable.new_find_collection));
            isSuccess = true;
        } else {
            mCollection.setBackground(getResources().getDrawable(R.drawable.new_find_uncollection));
            isSuccess = false;
        }
//        mJobInfoPresenter.getJobInfo();
//        jobInfo = null;
    }

    @Override
    public void detelCollectionSuccess() {
        jobInfo.is_collect = 2;
        mCollection.setBackground(getResources().getDrawable(R.drawable.new_find_uncollection));
    }
}
