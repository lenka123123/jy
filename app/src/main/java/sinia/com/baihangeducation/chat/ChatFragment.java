package sinia.com.baihangeducation.chat;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.example.framwork.utils.SpCommonUtils;
import com.google.gson.Gson;
import com.google.gson.jpush.JsonObject;
import com.google.gson.jpush.JsonParser;

import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetAvatarBitmapCallback;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.enums.ConversationType;
import cn.jpush.im.android.api.event.ConversationRefreshEvent;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.event.MessageReceiptStatusChangeEvent;
import cn.jpush.im.android.api.event.MessageRetractEvent;
import cn.jpush.im.android.api.event.OfflineMessageEvent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.GroupInfo;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.android.eventbus.EventBus;
import cn.jpush.im.api.BasicCallback;
import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.MyApplication;
import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.chat.view.ConversationListView;
import sinia.com.baihangeducation.chat.view.MenuItemController;
import sinia.com.baihangeducation.chat.view.MenuItemView;
import sinia.com.baihangeducation.club.ClubPermissModel;
import sinia.com.baihangeducation.club.club.interfaces.GetRequestListener;
import sinia.com.baihangeducation.club.im.entity.Event;

public class ChatFragment extends ChatBaseFragment implements GetRequestListener {

    private Activity mContext;
    private View mRootView;
    private ConversationListView mConvListView;
    private ConversationListController mConvListController;
    private HandlerThread mThread;
    private static final int REFRESH_CONVERSATION_LIST = 0x3000;
    private static final int DISMISS_REFRESH_HEADER = 0x3001;
    private static final int ROAM_COMPLETED = 0x3002;
    private BackgroundHandler mBackgroundHandler;
    private View mMenuView;
    private PopupWindow mMenuPopWindow;
    private MenuItemView mMenuItemView;
    private NetworkReceiver mReceiver;
    private MenuItemController mMenuController;
    protected boolean isCreate = false;
    private String phone;
    private ClubPermissModel clubPermissModel;


    @Override
    public void setRequestSuccess(String msg) {
//        unread -  消息数量
////        content -  最新消息内容
        Gson gson = new Gson();
        ChatData chatData = gson.fromJson(msg, ChatData.class);
        AppConfig.CHATMESSAGE = chatData.content;
        AppConfig.CHATMESSAGENUM = chatData.unread + "";
        sortConvList();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        AppConfig.HOMT = false;
        AppConfig.PART = false;
        AppConfig.CHAT = true;
        AppConfig.CLUB = false;
        AppConfig.ME = false;
        if (isCreate) {
            if (clubPermissModel != null)
                clubPermissModel.getSystemHome(this);
//            Conversation conv = JMessageClient.getSingleConversation(targetId, userInfo.getAppKey());
//            mBackgroundHandler.sendMessage(mBackgroundHandler.obtainMessage(REFRESH_CONVERSATION_LIST, conv));
            sortConvList();
            setReSatart();
        }
    }

    public void setReSatart() {
//        if (!AppConfig.CHAT) return;
        if (isCreate) {
            if (clubPermissModel != null)
                clubPermissModel.getSystemHome(this);
            JMessageClient.login(phone, "123456", new BasicCallback() {
                @Override
                public void gotResult(int i, String s) {
                    List<Conversation> list = JMessageClient.getConversationList();
                    if (mConvListController != null)
                        mConvListController.updata();
                }
            });
        }

        if (!AppConfig.CHAT) return;


    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isCreate = true;
        mContext = this.getActivity();
        clubPermissModel = new ClubPermissModel(getActivity());
        clubPermissModel.getSystemHome(this);
        phone = (String) SpCommonUtils.get(getContext(), AppConfig.USERPHOTO, "");

        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        mRootView = layoutInflater.inflate(R.layout.chat_fragment,
                (ViewGroup) getActivity().findViewById(R.id.drawer_layout), false);

        mRootView.findViewById(R.id.create_group_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("点击率");
                systemMsg();
            }
        });

        mConvListView = new ConversationListView(mRootView, this.getActivity(), this);
        mConvListView.initModule();
        mThread = new HandlerThread("MainActivity");
        mThread.start();
        mBackgroundHandler = new BackgroundHandler(mThread.getLooper());
        mMenuView = getActivity().getLayoutInflater().inflate(R.layout.drop_down_menu, null);
        mConvListController = new ConversationListController(mConvListView, this, mWidth);

        mConvListView.setListener(mConvListController);
        mConvListView.setItemListeners(mConvListController);
        mConvListView.setLongClickListener(mConvListController);
        mMenuPopWindow = new PopupWindow(mMenuView, WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT, true);
        mMenuItemView = new MenuItemView(mMenuView);
        mMenuItemView.initModule();

        mMenuController = new MenuItemController(this);
        mMenuItemView.setListeners(mMenuController);


        ConnectivityManager manager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeInfo = manager.getActiveNetworkInfo();
        if (null == activeInfo) {
            mConvListView.showHeaderView();
        } else {
            mConvListView.dismissHeaderView();
            mConvListView.showLoadingHeader();
            mBackgroundHandler.sendEmptyMessageDelayed(DISMISS_REFRESH_HEADER, 1000);
        }
        initReceiver();

    }

    private void initReceiver() {
        if (!mReceiverTag) {
            mReceiverTag = true;
            mReceiver = new NetworkReceiver();
            IntentFilter filter = new IntentFilter();
            filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
            mContext.registerReceiver(mReceiver, filter);
        }
    }

    private boolean mReceiverTag = false;

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        if (mReceiverTag) {   //判断广播是否注册
            mReceiverTag = false;
            getActivity().unregisterReceiver(mReceiver);
        }
        if (mBackgroundHandler != null)
            mBackgroundHandler.removeCallbacksAndMessages(null);
        if (mThread != null && mThread.getLooper() != null)
            mThread.getLooper().quit();
        super.onDestroy();
    }


    @Override
    public void setRequestFail() {

    }

    //监听网络状态的广播
    private class NetworkReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null && intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE")) {
                ConnectivityManager manager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeInfo = manager.getActiveNetworkInfo();
                if (null == activeInfo) {
                    mConvListView.showHeaderView();
                } else {
                    mConvListView.dismissHeaderView();
                }
            }
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    public void showPopWindow() {
        mMenuPopWindow.setTouchable(true);
        mMenuPopWindow.setOutsideTouchable(true);
        mMenuPopWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
        if (mMenuPopWindow.isShowing()) {
            mMenuPopWindow.dismiss();
        } else {
//            mMenuPopWindow.showAsDropDown(mRootView.findViewById(R.id.create_group_btn), -10, -5);
        }
    }

    private Conversation mConv;

    public void systemMsg() {

//        String username 目标的用户用户名。
//        String appkey 用户所属应用的appkey

//        mConv = JMessageClient.getSingleConversation("系统用户www", "");
//        TextContent content = new TextContent("======");
//        mConv.createSendMessage(content);
//        JMessageClient.getUserInfo("系统用户www", new GetUserInfoCallback() {
//            @Override
//            public void gotResult(int i, String s, cn.jpush.im.android.api.model.UserInfo userInfo) {
//                System.out.println("JMessageClient dong1" + i + "===" + s + "===" + userInfo.getAppKey());
//                Conversation conv = JMessageClient.getSingleConversation("系统用户www", userInfo.getAppKey());
//
//                mBackgroundHandler.sendMessage(mBackgroundHandler.obtainMessage(REFRESH_CONVERSATION_LIST,
//                        conv));
//            }
//        });
    }

    /**
     * 收到消息
     */
    public void onEvent(MessageEvent event) {
        mConvListView.setUnReadMsg(JMessageClient.getAllUnReadMsgCount());
        Message msg = event.getMessage();
        if (msg.getTargetType() == ConversationType.group) {
            long groupId = ((GroupInfo) msg.getTargetInfo()).getGroupID();
            Conversation conv = JMessageClient.getGroupConversation(groupId);
            if (conv != null && mConvListController != null) {
                if (msg.isAtMe()) {
                    MyApplication.isAtMe.put(groupId, true);
                    mConvListController.getAdapter().putAtConv(conv, msg.getId());
                }
                if (msg.isAtAll()) {
                    MyApplication.isAtall.put(groupId, true);
                    mConvListController.getAdapter().putAtAllConv(conv, msg.getId());
                }
                mBackgroundHandler.sendMessage(mBackgroundHandler.obtainMessage(REFRESH_CONVERSATION_LIST,
                        conv));
            }
        } else {
            final UserInfo userInfo = (UserInfo) msg.getTargetInfo();
            String targetId = userInfo.getUserName();
            Conversation conv = JMessageClient.getSingleConversation(targetId, userInfo.getAppKey());
            if (conv != null && mConvListController != null) {
                mContext.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (TextUtils.isEmpty(userInfo.getAvatar())) {
                            userInfo.getAvatarBitmap(new GetAvatarBitmapCallback() {
                                @Override
                                public void gotResult(int responseCode, String responseMessage, Bitmap avatarBitmap) {
                                    if (responseCode == 0) {
                                        mConvListController.getAdapter().notifyDataSetChanged();
                                    }
                                }
                            });
                        }
                    }
                });
                mBackgroundHandler.sendMessage(mBackgroundHandler.obtainMessage(REFRESH_CONVERSATION_LIST, conv));
            }
        }
    }

    /**
     * 接收离线消息
     *
     * @param event 离线消息事件
     */
    public void onEvent(OfflineMessageEvent event) {
        Conversation conv = event.getConversation();
        if (!conv.getTargetId().equals("feedback_Android")) {
            mBackgroundHandler.sendMessage(mBackgroundHandler.obtainMessage(REFRESH_CONVERSATION_LIST, conv));
        }
    }

    /**
     * 消息撤回
     */
    public void onEvent(MessageRetractEvent event) {
        Conversation conversation = event.getConversation();
        mBackgroundHandler.sendMessage(mBackgroundHandler.obtainMessage(REFRESH_CONVERSATION_LIST, conversation));
    }

    /**
     * 消息已读事件
     */
    public void onEventMainThread(MessageReceiptStatusChangeEvent event) {
        mConvListController.getAdapter().notifyDataSetChanged();
    }

    /**
     * 消息漫游完成事件
     *
     * @param event 漫游完成后， 刷新会话事件
     */
    public void onEvent(ConversationRefreshEvent event) {
        Conversation conv = event.getConversation();
        if (!conv.getTargetId().equals("feedback_Android")) {
            mBackgroundHandler.sendMessage(mBackgroundHandler.obtainMessage(REFRESH_CONVERSATION_LIST, conv));
            //多端在线未读数改变时刷新
            if (event.getReason().equals(ConversationRefreshEvent.Reason.UNREAD_CNT_UPDATED)) {
                mBackgroundHandler.sendMessage(mBackgroundHandler.obtainMessage(REFRESH_CONVERSATION_LIST, conv));
            }
        }
    }

    public class BackgroundHandler extends Handler {
        public BackgroundHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case REFRESH_CONVERSATION_LIST:
                    Conversation conv = (Conversation) msg.obj;
                    if (mConvListController != null && mConvListController.getAdapter() != null)
                        mConvListController.getAdapter().setToTop(conv);
                    break;
                case DISMISS_REFRESH_HEADER:
                    mContext.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mConvListView.dismissLoadingHeader();
                        }
                    });
                    break;
                case ROAM_COMPLETED:
                    conv = (Conversation) msg.obj;
                    if (mConvListController != null && mConvListController.getAdapter() != null)
                        mConvListController.getAdapter().addAndSort(conv);
                    break;
            }
        }
    }

    public void onEventMainThread(Event event) {
        switch (event.getType()) {
            case top:
                Conversation conversation = event.getConversation();
                System.out.println("=====事件" + conversation.getTitle());
                if (conversation != null) {
                    if (!TextUtils.isEmpty(conversation.getExtra())) {
                        mConvListController.getAdapter().setCancelConvTop(conversation);
                        //没有置顶,去置顶
                    } else {
                        mConvListController.getAdapter().setConvTop(conversation);
                    }

                }
                break;
            case createConversation:
                Conversation conv = event.getConversation();
                if (conv != null) {
                    if (mConvListController != null && mConvListController.getAdapter() != null)
                        mConvListController.getAdapter().addNewConversation(conv);
                }
                break;
            case deleteConversation:
                conv = event.getConversation();
                if (null != conv) {
                    if (mConvListController != null && mConvListController.getAdapter() != null)
                        mConvListController.getAdapter().deleteConversation(conv);
                }
                break;
            //收到保存为草稿事件
            case draft:
                conv = event.getConversation();
                String draft = event.getDraft();
                //如果草稿内容不为空，保存，并且置顶该会话
                if (!TextUtils.isEmpty(draft)) {
                    if (mConvListController != null && mConvListController.getAdapter() != null)
                        mConvListController.getAdapter().putDraftToMap(conv, draft);
                    if (mConvListController != null && mConvListController.getAdapter() != null)
                        mConvListController.getAdapter().setToTop(conv);
                    //否则删除
                } else {
                    if (mConvListController != null && mConvListController.getAdapter() != null)
                        mConvListController.getAdapter().delDraftFromMap(conv);
                }
                break;
            case addFriend:
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup p = (ViewGroup) mRootView.getParent();
        if (p != null) {
            p.removeAllViewsInLayout();
        }
        return mRootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        dismissPopWindow();
        if (mMenuItemView != null)
            mMenuItemView.showAddFriend();
        if (mConvListController != null && mConvListController.getAdapter() != null)
            mConvListController.getAdapter().notifyDataSetChanged();
    }

    public void dismissPopWindow() {

        if (mMenuPopWindow != null && mMenuPopWindow.isShowing()) {
            mMenuPopWindow.dismiss();
        }
    }


    public void sortConvList() {
        if (mConvListController != null && mConvListController.getAdapter() != null) {
            mConvListController.getAdapter().sortConvList();
        }
    }

}