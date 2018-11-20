package sinia.com.baihangeducation.find.campus.activity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.framwork.glide.ImageLoaderUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMWeb;

import java.util.ArrayList;
import java.util.List;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.supplement.base.BaseActivity;
import sinia.com.baihangeducation.MyApplication;
import sinia.com.baihangeducation.find.campus.info.CampusInterestingDetailListInfo;
import sinia.com.baihangeducation.find.campus.present.CampusInterestingDetailPresenter;
import sinia.com.baihangeducation.find.campus.view.ICampusInterestingDetailView;
import com.mcxtzhang.swipemenulib.info.bean.AddCollectionSuccessInfo;
import com.mcxtzhang.swipemenulib.info.bean.ShareEveryDayDetailCommentInfo;
import sinia.com.baihangeducation.home.present.AddOrDetelCollctionPresenter;
import sinia.com.baihangeducation.home.view.AddCollctionView;
import sinia.com.baihangeducation.supplement.base.Goto;
import sinia.com.baihangeducation.supplement.tool.BaseUtil;
import sinia.com.baihangeducation.find.campus.adapter.MessageDialog;

/**
 * 每日分享详情
 */

public class CampusInterestingDetailActivity extends BaseActivity implements ICampusInterestingDetailView, AddCollctionView {

    private MyApplication application;
    private String funID;
    private CampusInterestingDetailPresenter mCampusInterestingDetailPresenter;//获取详情数据
    private AddOrDetelCollctionPresenter mAddCollctionPresenter;//添加收藏
    private int collectionId;
    private String addCollectionType = "3";
    private CampusInterestingDetailListInfo mData;
    private Intent intent;
    private View contentView;
    private PopupWindow popupWindow;
    private List<ShareEveryDayDetailCommentInfo> mCommentList;
    private boolean isLoadMore = false;
    private int countpage = 1;
    private int itemnum = 20;
    private String type = "6";
    private String parentCommentId = "0";

    private MessageDialog dialog;

    private TextView mTitle;
    private TextView mShortInfo;
    private ImageView mImage;
    private TextView mContent;
    private ImageView mIsCollection;
    private RelativeLayout mMessageLayout;
    private ImageView mMessage;
    private TextView mMessageNum;
    private ImageView mShare;
    private EditText mWriteMessage;

    @Override
    public int initLayoutResID() {
        return R.layout.campusinterestingdetail;
    }

    @Override
    protected void initData() {
        mCommentList = new ArrayList<>();
        dialog = new MessageDialog(context);

        application = (MyApplication) context.getApplication();
        mCommonTitle.setCenterText(R.string.fundetail);
        mCommonTitle.setBackground(getResources().getDrawable(R.color.white));
        intent = getIntent();
        funID = intent.getStringExtra("FUNID");
        mCampusInterestingDetailPresenter = new CampusInterestingDetailPresenter(context, this);
        mCampusInterestingDetailPresenter.getFunDetail();
        mAddCollctionPresenter = new AddOrDetelCollctionPresenter(context, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCampusInterestingDetailPresenter = new CampusInterestingDetailPresenter(context, this);
        mCampusInterestingDetailPresenter.getFunDetail();
    }

    @Override
    protected void initView() {
        mTitle = $(R.id.campusinterestingdetail_title);
        mShortInfo = $(R.id.campusinterestingdetail_companyanddate);
        mImage = $(R.id.campusinterestingdetail_img);
        mContent = $(R.id.campusinterestingdetail_caontent);

        mIsCollection = $(R.id.campusinterestingdetail_iscollection);
        mMessageLayout = $(R.id.campusinterestingdetail_messagelayout);
        mMessage = $(R.id.campusinterestingdetail_message);
        mMessageNum = $(R.id.campusinterestingdetail_messagenum);
        mShare = $(R.id.campusinterestingdetail_share);
        mWriteMessage = $(R.id.search_writemessage);


        mWriteMessage.setOnClickListener(this);
        mMessageLayout.setOnClickListener(this);
        mIsCollection.setOnClickListener(this);
        mShare.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (!BaseUtil.isLogin(context, application)) {
            return;
        }
        switch (v.getId()) {
            case R.id.campusinterestingdetail_messagelayout:
                //查看留言
                Goto.toMessageActivity(context,mData.fun_id,"6");
                break;
            case R.id.search_writemessage:
                //留言
                //解决点击发送两次问题
                mWriteMessage.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        //是否是回车键
                        if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                            //隐藏键盘
                            ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                                    .hideSoftInputFromWindow(CampusInterestingDetailActivity.this.getCurrentFocus()
                                            .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                            //发送
                            mWriteMessage.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                                @Override
                                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                                    if (!v.getText().toString().trim().isEmpty()) {
                                        mCampusInterestingDetailPresenter.sendCommen(v.getText().toString().trim());
                                    }
                                    return false;
                                }
                            });
                        }
                        return false;
                    }
                });

                break;
            case R.id.campusinterestingdetail_share:
                //分享页面
                if (mData != null)
                    addShareMeun();
                break;
            case R.id.campusinterestingdetail_iscollection:
                //收藏
                Log.i("是否收藏", mData.is_collect + "");
                Log.i("参数ID", collectionId + "");
                if (mData.is_collect != 1) {
                    mAddCollctionPresenter.addCollection();
                } else {
                    mAddCollctionPresenter.detelCollection();
                }
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
        UMWeb web = new UMWeb(mData.fun_share_url);
        web.setTitle(mData.fun_title);//标题
        web.setDescription(mData.fun_content);//描述
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
        popupWindow.showAtLocation(mShare.getRootView(), Gravity.BOTTOM, 0, 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (popupWindow != null) {
            popupWindow.dismiss();
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {
    }


    @Override
    public String getFunId() {
        return funID;
    }

    @Override
    public String getPage() {
        return countpage + "";
    }

    @Override
    public String getPerpage() {
        return itemnum + "";
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getParentCommentId() {
        return parentCommentId;
    }

    @Override
    public void getFunSuccess(CampusInterestingDetailListInfo info) {
        if (info!=null){
            mData = info;
            mTitle.setText(info.fun_title);
            mShortInfo.setText(info.fun_add_date);
            ImageLoaderUtils.display(context,mImage,info.fun_images);
            mContent.setText(info.fun_content);

            //判断是否收藏

            if (info.is_collect == 1) {
                isSuccess = true;
                mIsCollection.setBackground(getResources().getDrawable(R.drawable.new_find_collection));
            } else {
                isSuccess = false;
                mIsCollection.setBackground(getResources().getDrawable(R.drawable.new_find_uncollection));
            }

        //判断留言是否为0

        if ((info.comment_list.count) == 0) {
            mMessageNum.setVisibility(View.GONE);
        } else {
            mMessageNum.setVisibility(View.VISIBLE);
            mMessageNum.setText(info.comment_list.count + "");
        }
        }
    }
    private boolean isSuccess = true;

    @Override
    public void getShareEveryDaySendCommenSuccess() {
        com.example.framwork.utils.Toast.getInstance().showSuccessToast(context, "留言成功");
        mWriteMessage.setText("");
        mWriteMessage.setHint("写留言...");
    }

    @Override
    public String getAddCollctionType() {
        return addCollectionType;
    }

    @Override
    public String getAddClloectionTpyeId() {
        return mData.fun_id + "";
    }

    @Override
    public String getAddClloectionId() {
        return collectionId + "";
    }

    @Override
    public void addCollectionSuccess(AddCollectionSuccessInfo mAddCollectionSuccessInfo) {

        collectionId = mAddCollectionSuccessInfo.collect_id;
        if (!isSuccess) {
            mIsCollection.setBackground(getResources().getDrawable(R.drawable.new_find_collection));
            isSuccess = true;
        } else {
            mIsCollection.setBackground(getResources().getDrawable(R.drawable.new_find_uncollection));
            isSuccess = false;
        }
    }

    @Override
    public void detelCollectionSuccess() {
//        mShareEveryDayDetailPresenter = new ShareEveryDayDetailPresenter(context, this);
//        mShareEveryDayDetailPresenter.getShareEveryDayDetailData();
        mData.is_collect = 2;
        mIsCollection.setBackground(getResources().getDrawable(R.drawable.new_find_uncollection));
    }
}
