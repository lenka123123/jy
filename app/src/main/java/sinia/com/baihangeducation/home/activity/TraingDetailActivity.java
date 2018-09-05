package sinia.com.baihangeducation.home.activity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMWeb;
import com.zzhoujay.richtext.RichText;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.supplement.base.BaseActivity;
import sinia.com.baihangeducation.MyApplication;
import com.mcxtzhang.swipemenulib.info.bean.AddCollectionSuccessInfo;
import com.mcxtzhang.swipemenulib.info.TraingDetailInfo;
import sinia.com.baihangeducation.home.present.AddOrDetelCollctionPresenter;
import sinia.com.baihangeducation.home.present.TraingDetailPresenter;
import sinia.com.baihangeducation.home.view.AddCollctionView;
import sinia.com.baihangeducation.home.view.TraingDetailView;
import sinia.com.baihangeducation.supplement.base.Goto;
import sinia.com.baihangeducation.supplement.tool.BaseUtil;

/**
 * 培训详情页面
 */

public class TraingDetailActivity extends BaseActivity implements TraingDetailView, AddCollctionView {
    private TraingDetailPresenter mTraingDetailPresenter;
    private AddOrDetelCollctionPresenter mAddOrDetelCollctionPresenter;
    private Intent intent;
    private String mTraingId;
    private MyApplication application;
    private String addCollectionType = "8";
    private TraingDetailInfo traingDetailInfo;
    private int collectionId;
    private View contentView;
    private PopupWindow popupWindow;

    private TextView mTitle;                //标题
    private TextView mSeconedTitle;         //副标题
    private TextView mPrice;                //价格
    private TextView mClassAdress;          //上课地址
    private TextView mTel;                  //联系电话
    private TextView mClassNum;             //课时数
    private TextView mClassTime;            //课长
    private TextView mClassDate;            //授课时间
    private TextView mClassContent;         //课程内容
    private LinearLayout mCollectionLayout; //收藏最外层layout
    private ImageView mCollectionImg;       //收藏图标
    private LinearLayout mShareLayout;      //转发layout
    private TextView mSendResume;           //投递简历


    @Override
    public int initLayoutResID() {
        return R.layout.traingdetail;
    }

    @Override
    protected void initData() {
        intent = getIntent();
        mTraingId = intent.getStringExtra("TRAINID");
        application = (MyApplication) context.getApplication();
        mCommonTitle.setCenterText(R.string.classinfo);
        mCommonTitle.setBackground(getResources().getDrawable(R.color.white));

        mTraingDetailPresenter = new TraingDetailPresenter(context, this);
        mAddOrDetelCollctionPresenter = new AddOrDetelCollctionPresenter(context, this);
        mTraingDetailPresenter.getTraingDetailInfo();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mTraingDetailPresenter != null) {
            mTraingDetailPresenter = new TraingDetailPresenter(context, this);
            mTraingDetailPresenter.getTraingDetailInfo();
        }
    }

    @Override
    protected void initView() {
        mTitle = $(R.id.traingdetail_title);
        mSeconedTitle = $(R.id.traingdetail_secondtitle);
        mPrice = $(R.id.traingdetail_price);
        mClassAdress = $(R.id.traingdetail_classadress);
        mTel = $(R.id.traingdetail_tel);
        mClassNum = $(R.id.traingdetail_classnum);
        mClassTime = $(R.id.traingdetail_classtime);
        mClassDate = $(R.id.traingdetail_classdate);
        mClassContent = $(R.id.traingdetail_classcontent);
        mCollectionLayout = $(R.id.traingdetail_collectionlayout);
        mCollectionImg = $(R.id.traingdetail_collectionimg);
        mShareLayout = $(R.id.traingdetail_sharelayout);
        mSendResume = $(R.id.traingdetail_sendresume);

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
            case R.id.traingdetail_collectionlayout:
                //收藏
                Log.i("收藏", traingDetailInfo.is_collect + "点击收藏按钮的时候判断是否收藏");
                Log.i("收藏", collectionId + "点击收藏按钮的时候判断收藏ID");
                if (traingDetailInfo.is_collect != 1) {
                    mAddOrDetelCollctionPresenter.addCollection();
                } else {
                    mAddOrDetelCollctionPresenter.detelCollection();
                }
                break;
            case R.id.traingdetail_sharelayout:
                //分享
                if (traingDetailInfo != null)
                    addShareMeun();
                break;
            case R.id.traingdetail_sendresume:
                //投递简历
//                mTraingDetailPresenter.sendResume();
                Goto.toTraingJoinInActivity(context,traingDetailInfo);
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
        UMWeb web = new UMWeb(traingDetailInfo.train_share_url);
        web.setTitle(traingDetailInfo.train_title);//标题
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
            Toast.makeText(context, "分享取消了", Toast.LENGTH_LONG).show();
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
    public String getTrainId() {
        return mTraingId;
    }

    @Override
    public void getMyReleaseTraingDetailInfoSuccess(TraingDetailInfo mTraingDetailInfo) {
        if (mTraingDetailInfo != null) {
            traingDetailInfo = mTraingDetailInfo;
            collectionId = mTraingDetailInfo.collect_id;
            mTitle.setText(mTraingDetailInfo.train_title);
            mSeconedTitle.setText(mTraingDetailInfo.train_sub_title);
            mPrice.setText(mTraingDetailInfo.train_price);
            mClassAdress.setText(mTraingDetailInfo.train_address);
            mTel.setText(mTraingDetailInfo.train_tel);
            mClassNum.setText(mTraingDetailInfo.train_class_num);
            mClassTime.setText(mTraingDetailInfo.train_class_duration);
            mClassDate.setText(mTraingDetailInfo.train_class_date);
            RichText.fromHtml(mTraingDetailInfo.train_class_intro).into(mClassContent);
            Log.i("收藏", mTraingDetailInfo.is_collect + "获取信息成功返回的是否收藏");

            if (mTraingDetailInfo.is_collect == 1) {
                isSuccess = true;
                mCollectionImg.setBackground(getResources().getDrawable(R.drawable.new_find_collection));
            } else {
                isSuccess = false;
                mCollectionImg.setBackground(getResources().getDrawable(R.drawable.new_find_uncollection));
            }

        }
    }

    @Override
    public String getAddCollctionType() {
        return addCollectionType;
    }

    @Override
    public String getAddClloectionTpyeId() {
        return traingDetailInfo.train_id + "";
    }

    @Override
    public String getAddClloectionId() {
        return collectionId + "";
    }

    @Override
    public void addCollectionSuccess(AddCollectionSuccessInfo mAddCollectionSuccessInfo) {

        if (!isSuccess) {
            mCollectionImg.setBackground(getResources().getDrawable(R.drawable.new_find_collection));
            isSuccess = true;
        } else {
            mCollectionImg.setBackground(getResources().getDrawable(R.drawable.new_find_uncollection));
            isSuccess = false;
        }
       // mTraingDetailPresenter.getTraingDetailInfo();
    }

    @Override
    public void detelCollectionSuccess() {
        traingDetailInfo.is_collect = 2;
        mCollectionImg.setBackground(getResources().getDrawable(R.drawable.new_find_uncollection));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, intent);
    }
    private boolean isSuccess = true;
}
