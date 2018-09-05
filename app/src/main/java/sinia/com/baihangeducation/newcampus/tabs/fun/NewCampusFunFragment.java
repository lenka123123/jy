package sinia.com.baihangeducation.newcampus.tabs.fun;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.framwork.utils.ProgressActivityUtils;
import com.example.framwork.widget.ProgressActivity;
import com.example.framwork.widget.superrecyclerview.recycleview.SuperRecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import sinia.com.baihangeducation.R;

import com.mcxtzhang.swipemenulib.base.BaseFragment;
import com.mcxtzhang.swipemenulib.info.bean.CommentInfo;
import com.mcxtzhang.swipemenulib.info.bean.HomeAndFindHelpEachOtherDetailCommentInfo;

import sinia.com.baihangeducation.home.present.MessagePresenter;
import sinia.com.baihangeducation.home.view.MessageView;
import sinia.com.baihangeducation.newcampus.tabs.fun.adapter.NewCampusFunAdapter;
import sinia.com.baihangeducation.newcampus.info.FunContantInfo;
import sinia.com.baihangeducation.newcampus.info.FunInfo;
import sinia.com.baihangeducation.newcampus.tabs.fun.presenter.GetFunInfoPresneter;
import sinia.com.baihangeducation.newcampus.view.IGetFunView;
import sinia.com.baihangeducation.newcampus.view.IIsShowInput;
import sinia.com.baihangeducation.newcampus.adapter.SoftKeyboardStateHelper;

import com.mcxtzhang.swipemenulib.customview.BackEditText;

public class NewCampusFunFragment extends BaseFragment implements MessageView, BackEditText.BackListener, IIsShowInput, IGetFunView, SuperRecyclerView.LoadingListener, SwipeRefreshLayout.OnRefreshListener {


    private GetFunInfoPresneter presneter;
    private MessagePresenter messagePresenter;
    private int countpage = 1;
    private int itemnum = 5;

    private FunContantInfo mData;
    private SoftKeyboardStateHelper softKeyboardStateHelper;

    private LinearLayout mLayout;
    private BackEditText mInput;
    private SuperRecyclerView rvContainer;
    private ProgressActivity progressActivity;
    private SwipeRefreshLayout swipeContainer;
    private ProgressActivityUtils progressActivityUtils;
    private NewCampusFunAdapter mNewCampusFunAdapter;
    public List<FunContantInfo> mList;           //内容
    private boolean isLoadMore = false;

    @Override
    public int initLayoutResID() {
        return R.layout.newcampany;
    }

    @Override
    protected void initData() {
        mList = new ArrayList<>();

        presneter = new GetFunInfoPresneter(context, this);
        presneter.getFunInfo();
        messagePresenter = new MessagePresenter(context, this);

        mNewCampusFunAdapter = new NewCampusFunAdapter(context, mList, mLayout, this,true);

        //给EditText设置按返回键监听事件
        mInput.setBackListener(this);
        //根据试图高度判断是否点击软键盘收起按钮
        softKeyboardStateHelper = new SoftKeyboardStateHelper($(R.id.newcampany));
        softKeyboardStateHelper.addSoftKeyboardStateListener(new SoftKeyboardStateHelper.SoftKeyboardStateListener() {
            @Override
            public void onSoftKeyboardOpened(int keyboardHeightInPx) {
                //键盘打开
            }

            @Override
            public void onSoftKeyboardClosed() {
                //键盘关闭
                if (mInput.getVisibility() == View.VISIBLE) {
                    mInput.setVisibility(View.GONE);
                }
            }
        });

        mInput.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //是否是回车键
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    mInput.setVisibility(View.GONE);
                    hideSoftInput(mInput);
                    //发送
                    mInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                        @Override
                        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                            if (!v.getText().toString().trim().isEmpty()) {
                                messagePresenter.sendMessage(v.getText().toString().trim());
                            }
                            return true;
                        }
                    });
                }
                return false;
            }
        });

        initSwipeLayout(swipeContainer, this);
        showSwipeRefreshLayout(swipeContainer);
        progressActivityUtils = new ProgressActivityUtils(context, progressActivity);
        initRecyclerView(rvContainer, mNewCampusFunAdapter, this);

    }

    @Override
    protected void initView() {
        rvContainer = $(R.id.rv_container);
        progressActivity = $(R.id.progressActivity);
        swipeContainer = $(R.id.swipe_container);
        mLayout = $(R.id.newcampany);
        mInput = $(R.id.newcampany_input);
    }

    @Override
    public void onClick(View view) {
    }

    @Override
    public String getFunPage() {
        return countpage + "";
    }

    @Override
    public String getFunPerpage() {
        return itemnum + "";
    }

    @Override
    public void getFunInfoSuccess(FunInfo info, int maxpage) {
        if (info.list.size() == 0) {
//            progressActivityUtils.showEmptry("暂无数据");
            mList.clear();
//            mList=list;
            FunContantInfo nulldata = new FunContantInfo();
            mList.add(nulldata);
            mNewCampusFunAdapter.notifyDataSetChanged();
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
            mList.addAll(info.list);
            mNewCampusFunAdapter.notifyDataSetChanged();
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
        presneter.getFunInfo();
    }

    @Override
    public void IsShowInput(boolean flag, FunContantInfo item) {
        mData = item;
        Log.i("是否显示", flag + "@@@@@@@");
        if (flag) {
            mInput.setVisibility(View.VISIBLE);

            mInput.setFocusable(true);
            mInput.setFocusableInTouchMode(true);
            mInput.requestFocus();
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    InputMethodManager imm = (InputMethodManager) mInput.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(mInput, InputMethodManager.SHOW_FORCED);
                }
            }, 300);

        } else {
            mInput.setVisibility(View.GONE);
            hideSoftInput(mInput);
        }

    }

    @Override
    public void back(EditText textView) {
        if (mInput.getVisibility() == View.VISIBLE) {
            mInput.setVisibility(View.GONE);
        }
    }

    /**
     * 留言类型ID
     *
     * @return
     */
    @Override
    public String getMessageType() {
        return "7";
    }

    /**
     * 留言ID
     *
     * @return
     */
    @Override
    public String getMessageTypeID() {
        return mData.dynamic_id + "";
    }

    /**
     * 留言父级ID
     *
     * @return
     */
    @Override
    public String getMessageParentId() {
        return "0";
    }

    @Override
    public String getPage() {
        return null;
    }

    @Override
    public String getPerpage() {
        return null;
    }

    @Override
    public void getMessageSuccess(HomeAndFindHelpEachOtherDetailCommentInfo mHomeAndFindHelpEachOtherDetailCommentInfo, int maxpage) {

    }

    @Override
    public void sendMessageSuccess() {
        mInput.setText("");
        mInput.setHint("请输入内容...");
    }

    @Override
    public void sendMessageSuccess(CommentInfo info) {
        if (mInput.getVisibility() == View.VISIBLE) {
            mInput.setVisibility(View.GONE);
        }
        if (info != null) {
            for (int i = 0; i < mList.size(); i++) {
                if (mList.get(i).dynamic_id == mData.dynamic_id) {
                    mList.get(i).comment_list.add(info);
                    mNewCampusFunAdapter.notifyDataSetChanged();
                }
            }
        }
    }
}
