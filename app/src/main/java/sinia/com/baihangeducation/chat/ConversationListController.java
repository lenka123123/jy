package sinia.com.baihangeducation.chat;

import android.app.Dialog;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;

import com.example.framwork.utils.SpCommonUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.enums.ConversationType;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.GroupInfo;
import cn.jpush.im.android.api.model.UserInfo;
import sinia.com.baihangeducation.MyApplication;
import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.chat.view.ConversationListView;
import sinia.com.baihangeducation.club.im.ChatActivity;
import sinia.com.baihangeducation.club.im.utils.DialogCreator;
import sinia.com.baihangeducation.supplement.base.Goto;
import sinia.com.baihangeducation.supplement.tool.SortConvList;
import sinia.com.baihangeducation.supplement.tool.SortTopConvList;

/**
 * Created by ${chenyn} on 2017/2/20.
 */

public class ConversationListController implements View.OnClickListener,
        AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private ConversationListView mConvListView;
    private ChatFragment mContext;
    private int mWidth;
    private ConversationListAdapter mListAdapter;
    private List<Conversation> mDatas = new ArrayList<Conversation>();
    private Dialog mDialog;
    private boolean isCreateSystem = false;

    public ConversationListController(ConversationListView listView, ChatFragment context,
                                      int width) {
        this.mConvListView = listView;
        this.mContext = context;
        this.mWidth = width;
        initConvListAdapter();
    }

    List<Conversation> topConv = new ArrayList<>();
    List<Conversation> forCurrent = new ArrayList<>();
    List<Conversation> delFeedBack = new ArrayList<>();


    private void addData() {
        if (isCreateSystem) {
            return;
        }
        isCreateSystem = true;


    }

    public void initConvListAdapter() {
        forCurrent.clear();
        topConv.clear();

        delFeedBack.clear();
        int i = 0;
        mDatas = JMessageClient.getConversationList();  //todo 为空
        if (mDatas == null) return;
        if (mDatas != null && mDatas.size() > 0) {

            mConvListView.setNullConversation(true);
            SortConvList sortConvList = new SortConvList();
            Collections.sort(mDatas, sortConvList);
            for (Conversation con : mDatas) {
                if (con.getTargetAppKey().equals("1712")) {
                    isCreateSystem = true;
                } else {
                    isCreateSystem = false;


                }
                if (con.getTargetId().equals("feedback_Android")) {
                    delFeedBack.add(con);
                }
                if (!TextUtils.isEmpty(con.getExtra())) {
                    forCurrent.add(con);
                }
            }
            if (!isCreateSystem) {
                Conversation conversation = Conversation.createSingleConversation("系统提醒", "1712");
                conversation.updateConversationExtra(0 + "");
                mDatas.add(0, conversation);
            }

            topConv.addAll(forCurrent);
            mDatas.removeAll(forCurrent);
            mDatas.removeAll(delFeedBack);

        } else {
            mConvListView.setNullConversation(false);
        }
        if (topConv != null && topConv.size() > 0) {
            SortTopConvList top = new SortTopConvList();
            Collections.sort(topConv, top);
            for (Conversation conv : topConv) {
                mDatas.add(i, conv);
                i++;
            }
        }

        mListAdapter = new ConversationListAdapter(mContext.getActivity(), mDatas, mConvListView);

        mConvListView.setConvListAdapter(mListAdapter);

    }


    public void updata() {
//        mDatas.clear();
//        mListAdapter.notifyDataSetChanged();
        initConvListAdapter();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.create_group_btn:


//                mContext.showPopWindow();
//                break;
//            case R.id.search_title:
//                Intent intent = new Intent();
//                intent.setClass(mContext.getActivity(), SearchContactsActivity.class);
//                mContext.startActivity(intent);
//                break;
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


        mDatas = getAdapter().getData();
        //点击会话条目
        Intent intent = new Intent();
        if (position > 0) {
            //这里-2是减掉添加的两个headView
            Conversation conv = mDatas.get(position - 2);
            System.out.println(" 点击位置1111" + position + "mDatas==" + conv.getTitle());
            intent.putExtra(MyApplication.CONV_TITLE, conv.getTitle());

            if (conv.getTitle().equals("系统提醒")) {
                Goto.toSystemMeaage(mContext.getContext());
                return;
            }
            //群聊
            if (conv.getType() == ConversationType.group) {
                if (mListAdapter.includeAtMsg(conv)) {
                    intent.putExtra("atMsgId", mListAdapter.getAtMsgId(conv));
                }

                if (mListAdapter.includeAtAllMsg(conv)) {
                    intent.putExtra("atAllMsgId", mListAdapter.getatAllMsgId(conv));
                }
                long groupId = ((GroupInfo) conv.getTargetInfo()).getGroupID();
                intent.putExtra(MyApplication.GROUP_ID, groupId);
                intent.putExtra(MyApplication.DRAFT, getAdapter().getDraft(conv.getId()));
                intent.setClass(mContext.getActivity(), ChatActivity.class);
                mContext.getActivity().startActivity(intent);
                return;
                //单聊
            } else {
                String targetId = ((UserInfo) conv.getTargetInfo()).getUserName();
                intent.putExtra(MyApplication.TARGET_ID, targetId);
                intent.putExtra(MyApplication.TARGET_APP_KEY, conv.getTargetAppKey());
                intent.putExtra(MyApplication.DRAFT, getAdapter().getDraft(conv.getId()));
            }
            intent.setClass(mContext.getActivity(), ChatActivity.class);
            mContext.getContext().startActivity(intent);
        }
    }

    public ConversationListAdapter getAdapter() {
        return mListAdapter;
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        System.out.println(" 点击位置" + position + " ==");
        mDatas = getAdapter().getData();
        final Conversation conv = mDatas.get(position - 2);
        if (conv != null) {
            View.OnClickListener listener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
//                        会话置顶
                        case R.id.jmui_top_conv_ll:
                            //已经置顶,去取消
                            if (!TextUtils.isEmpty(conv.getExtra())) {
                                mListAdapter.setCancelConvTop(conv);
                                //没有置顶,去置顶
                            } else {
                                mListAdapter.setConvTop(conv);
                            }
                            mDialog.dismiss();
                            break;
                        //删除会话
                        case R.id.jmui_delete_conv_ll:
                            if (conv.getType() == ConversationType.group) {
                                JMessageClient.deleteGroupConversation(((GroupInfo) conv.getTargetInfo()).getGroupID());
                            } else {
                                JMessageClient.deleteSingleConversation(((UserInfo) conv.getTargetInfo()).getUserName());
                            }
                            mDatas.remove(position - 2);
                            if (mDatas.size() > 0) {
                                mConvListView.setNullConversation(true);
                            } else {
                                mConvListView.setNullConversation(false);
                            }
                            mListAdapter.notifyDataSetChanged();
                            mDialog.dismiss();
                            break;
                        default:
                            break;
                    }

                }
            };
            mDialog = DialogCreator.createDelConversationDialog(mContext.getActivity(), listener, TextUtils.isEmpty(conv.getExtra()));
            mDialog.show();
            mDialog.getWindow().setLayout((int) (0.8 * mWidth), WindowManager.LayoutParams.WRAP_CONTENT);
        }
        return true;
    }
}
