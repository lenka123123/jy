package sinia.com.baihangeducation.reconsitution.tabs.coffers.entrance;

import android.app.Activity;
import android.util.Log;

import com.example.framwork.base.BasePresenter;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.OnRequestListener;
import com.example.framwork.utils.CommonUtil;
import com.example.framwork.utils.Toast;
import com.mcxtzhang.swipemenulib.info.bean.CommentInfo;
import com.mcxtzhang.swipemenulib.info.bean.HomeAndFindHelpEachOtherDetailCommentInfo;

import java.util.HashMap;

import sinia.com.baihangeducation.home.view.MessageView;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;

/**
 * Created by Administrator on 2018/4/27.
 */

public class MyCoffersPresenter extends BasePresenter {
    private Activity activity;
    private MessageView view;

    public MyCoffersPresenter(Activity activity, MessageView view) {
        super(activity);
        this.activity = activity;
        this.view = view;
    }

    public void getMessageInfo() {
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "getCommentList", "home", false);
        info.put("type", view.getMessageType());
        info.put("type_id", view.getMessageTypeID());
        info.put("parent_id", view.getMessageParentId());
        info.put("page", view.getPage());
        info.put("perpage", view.getPerpage());
        view.showLoading();
        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                Log.i("获取评论", bean.toString());
                HomeAndFindHelpEachOtherDetailCommentInfo info = bean.parseObject(HomeAndFindHelpEachOtherDetailCommentInfo.class);
                int maxpag = CommonUtil.getMaxPage(info.count, info.perpage);
                view.getMessageSuccess(info, maxpag);
            }

            @Override
            public void requestFailed(String error) {
                Toast.getInstance().showErrorToast(activity, error);
            }

            @Override
            public void requestFinish() {
                view.hideLoading();
            }
        });
    }

    public void sendMessage(String content) {
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "setComment", "home", true);
        info.put("type", view.getMessageType());
        info.put("type_id", view.getMessageTypeID());
        info.put("parent_id", view.getMessageParentId());
        info.put("content", content);
        view.showLoading();
        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                Log.i("留言返回数据", bean.toString());
                Toast.getInstance().showSuccessToast(activity, "发布成功");
                CommentInfo commentInfo = bean.parseObject(CommentInfo.class);
                view.sendMessageSuccess();
                view.sendMessageSuccess(commentInfo);
            }

            @Override
            public void requestFailed(String error) {
                Toast.getInstance().showErrorToast(activity, error);
            }

            @Override
            public void requestFinish() {
                view.hideLoading();
            }
        });
    }
}
