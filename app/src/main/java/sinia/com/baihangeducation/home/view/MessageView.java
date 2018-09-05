package sinia.com.baihangeducation.home.view;

import com.example.framwork.base.BaseView;

import com.mcxtzhang.swipemenulib.info.bean.CommentInfo;
import com.mcxtzhang.swipemenulib.info.bean.HomeAndFindHelpEachOtherDetailCommentInfo;

/**
 * Created by Administrator on 2018/4/27.
 */

public interface MessageView extends BaseView {
    /**
     * 类型 1早读趣事 2秘籍 3攻略 4试卷 5互助
     *
     * @return
     */
    String getMessageType();

    String getMessageTypeID();

    String getMessageParentId();

    String getPage();

    String getPerpage();

    void getMessageSuccess(HomeAndFindHelpEachOtherDetailCommentInfo mHomeAndFindHelpEachOtherDetailCommentInfo,int maxpage);

    void sendMessageSuccess();
    void sendMessageSuccess(CommentInfo info);
}
