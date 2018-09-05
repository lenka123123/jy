package sinia.com.baihangeducation.home.activity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.framwork.glide.ImageLoaderUtils;
import com.example.framwork.utils.ProgressActivityUtils;
import com.example.framwork.widget.ProgressActivity;
import com.example.framwork.widget.superrecyclerview.recycleview.SuperRecyclerView;

import java.util.ArrayList;
import java.util.List;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.supplement.base.BaseActivity;
import sinia.com.baihangeducation.MyApplication;
import sinia.com.baihangeducation.home.adapter.MessageAdapter;
import com.mcxtzhang.swipemenulib.info.bean.CommentInfo;
import com.mcxtzhang.swipemenulib.info.bean.HomeAndFindHelpEachOtherDetailCommentInfo;
import sinia.com.baihangeducation.home.present.MessagePresenter;
import sinia.com.baihangeducation.home.view.MessageView;
import sinia.com.baihangeducation.supplement.tool.BaseUtil;

/**
 * 留言
 */

public class MessageActivity extends BaseActivity implements MessageView ,SuperRecyclerView.LoadingListener, SwipeRefreshLayout.OnRefreshListener{
    private Intent intent;
    private CommentInfo fatherComment;
    private MyApplication application;
    private String type ;
    private int countpage = 1;
    private int itemnum = 20;
    private String typeid;
    private String parentid;

    private LinearLayout mFatherLayout;     //父级评论layout
    private TextView mFatherName;           //父级评论姓名
    private ImageView mFatherImg;           //父级评论头像
    private TextView mFatherContent;           //父级评论内容
    private TextView mFatherTime;           //父级评论时间
    private EditText mWriteMessage;         //写留言

    private SuperRecyclerView rvContainer;
    private ProgressActivity progressActivity;
    private SwipeRefreshLayout swipeContainer;
    private ProgressActivityUtils progressActivityUtils;
    private MessageAdapter mMessageAdapter;
    private List<CommentInfo> mList;
    private boolean isLoadMore = false;


    private MessagePresenter mMessagePresenter;

    @Override
    public int initLayoutResID() {
        return R.layout.message;
    }

    @Override
    protected void initData() {
        mList = new ArrayList<>();
        application = (MyApplication) context.getApplication();
        intent = getIntent();
        fatherComment = (CommentInfo) intent.getSerializableExtra("COMMENT");
        type = intent.getStringExtra("type");
        if(type==null){
            type="5";
        }

        mCommonTitle.setCenterText(R.string.message);
        mCommonTitle.setBackground(getResources().getDrawable(R.color.white));;

        if (fatherComment==null){
            mFatherLayout.setVisibility(View.GONE);
            typeid = intent.getStringExtra("typeid");
            parentid = "0";
        }else {
            typeid = fatherComment.comment_type_id;
            parentid = fatherComment.comment_id + "";
            ImageLoaderUtils.displayRound(context, mFatherImg, fatherComment.comment_user_avatar, R.drawable.new_eorrlogo);
            mFatherName.setText(fatherComment.comment_user_nickname);
            mFatherContent.setText(fatherComment.comment_content);
            mFatherTime.setText(fatherComment.comment_add_date);
        }

        mMessagePresenter = new MessagePresenter(context, this);
        mMessagePresenter.getMessageInfo();

        mMessageAdapter = new MessageAdapter(context, mList);
        initSwipeLayout(swipeContainer, this);
        showSwipeRefreshLayout(swipeContainer);
        progressActivityUtils = new ProgressActivityUtils(context, progressActivity);
        initRecyclerView(rvContainer, mMessageAdapter, this);

    }

    @Override
    protected void initView() {
        mFatherLayout = $(R.id.message_fatherlayout);
        mFatherName = $(R.id.message_fathername);
        mFatherImg = $(R.id.message_fatherimg);
        mFatherContent = $(R.id.message_fathercontent);
        mFatherTime = $(R.id.message_fathertime);
        mWriteMessage = $(R.id.message_writemessage);

        rvContainer = $(R.id.rv_container);
        progressActivity = $(R.id.progressActivity);
        swipeContainer = $(R.id.swipe_container);

        mWriteMessage.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (!BaseUtil.isLogin(context, application)) {
            return;
        }
        switch (v.getId()) {
            case R.id.message_writemessage:
                mWriteMessage.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        //是否是回车键
                        if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                            //隐藏键盘
                            ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                                    .hideSoftInputFromWindow(context.getCurrentFocus()
                                            .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                            //发送
                            mWriteMessage.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                                @Override
                                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                                    if (!v.getText().toString().trim().isEmpty()) {
                                        mMessagePresenter.sendMessage(v.getText().toString().trim());
                                    }
                                    return false;
                                }
                            });
                        }
                        return false;
                    }
                });
                break;
        }
    }

    @Override
    public void showLoading() {
showProgress();
    }

    @Override
    public void hideLoading() {
        hideProgress();
        hideSwipeRefreshLayout(swipeContainer);
        rvContainer.completeLoadMore();
    }

    @Override
    public String getMessageType() {
        return type;
    }

    @Override
    public String getMessageTypeID() {
        return typeid;
    }

    @Override
    public String getMessageParentId() {
        return parentid;
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
    public void getMessageSuccess(HomeAndFindHelpEachOtherDetailCommentInfo mInfo ,int maxpage) {
        if (mInfo.list.size() == 0) {
            progressActivityUtils.showEmptry("暂无数据");
        } else {
            progressActivityUtils.showContent();
            countpage++;
            if (maxpage == 1 || countpage > maxpage) {
                rvContainer.setLoadMoreEnabled(false);
            } else {
                rvContainer.setLoadMoreEnabled(true);
            }
            if (!isLoadMore) {
                mList.clear();
            }
            mList.addAll(mInfo.list);
            mMessageAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void sendMessageSuccess() {
        mWriteMessage.setText("");
        mWriteMessage.setHint("写留言...");
        onRefresh();
    }

    @Override
    public void sendMessageSuccess(CommentInfo info) {

    }

    @Override
    public void onRefresh() {
        isLoadMore = false;
        countpage = 1;
        getServerData();
    }

    @Override
    public void onLoadMore() {
        isLoadMore = true;
        getServerData();
    }

    /**
     * 获取数据
     */
    private void getServerData() {
        mMessagePresenter.getMessageInfo();
    }
}
