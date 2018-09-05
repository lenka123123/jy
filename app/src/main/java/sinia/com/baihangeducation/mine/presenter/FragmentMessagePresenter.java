package sinia.com.baihangeducation.mine.presenter;

import android.app.Activity;
import android.util.Log;

import com.example.framwork.base.BasePresenter;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.OnRequestListener;
import com.example.framwork.utils.CommonUtil;
import com.example.framwork.utils.Toast;

import java.util.HashMap;

import com.mcxtzhang.swipemenulib.info.bean.FragmentMessageListInfo;

import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.mine.view.FragmentMessageView;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;

/**
 * Created by Administrator on 2018/4/28.
 */

public class FragmentMessagePresenter extends BasePresenter {
    private Activity activity;
    private FragmentMessageView view;

    public FragmentMessagePresenter(Activity activity, FragmentMessageView view) {
        super(activity);
        this.activity = activity;
        this.view = view;
    }

    public FragmentMessagePresenter(Activity activity) {
        super(activity);
        this.activity = activity;
        this.view = null;
    }

    public void getMessage() {
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "getMessage", "ucenter", true);
        info.put("user_id", AppConfig.USERID);
        info.put("token", AppConfig.TOKEN);

        info.put("page", view.getPage());
        info.put("perpage", view.getPerpage());
        view.showLoading();
        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                Log.i("消息数据", bean.toString());
                FragmentMessageListInfo mInfo = bean.parseObject(FragmentMessageListInfo.class);
                int maxpag = CommonUtil.getMaxPage(mInfo.count, mInfo.perpage);
                view.getMessageSuccess(mInfo, maxpag);
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

    public void setMessageRead(int message_id) {
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "setMessageRead", "ucenter", true);
        info.put("user_id", AppConfig.USERID);
        info.put("token", AppConfig.TOKEN);
        info.put("system_message_id", message_id);

        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                if (view != null)
                    view.setMessageReadSuccess();
                Log.i("消息数据", bean.toString());
            }

            @Override
            public void requestFailed(String error) {
                Toast.getInstance().showErrorToast(activity, error);
            }

            @Override
            public void requestFinish() {

            }
        });
    }

    public void setMessageDrop(int system_message_id ) {
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "dropMessage", "ucenter", true);
        info.put("user_id", AppConfig.USERID);
        info.put("token", AppConfig.TOKEN);
        info.put("system_message_id", system_message_id );

        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {

            }

            @Override
            public void requestFailed(String error) {
                Toast.getInstance().showErrorToast(activity, error);
            }

            @Override
            public void requestFinish() {

            }
        });
    }
}
