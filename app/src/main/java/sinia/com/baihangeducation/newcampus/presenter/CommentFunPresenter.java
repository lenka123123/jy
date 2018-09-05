package sinia.com.baihangeducation.newcampus.presenter;

import android.app.Activity;

import com.example.framwork.base.BasePresenter;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.OnRequestListener;
import com.example.framwork.utils.Toast;

import java.util.HashMap;

import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;

public class CommentFunPresenter extends BasePresenter {
    private Activity activity;

    public CommentFunPresenter(Activity activity) {
        super(activity);
        this.activity = activity;
    }

    public void releaseComment(int type_id, String content) {
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "setComment", "home", true);
        info.put("type", "7");
        info.put("type_id", type_id);
        info.put("content", content);
        info.put("parent_id", "0");
        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                Toast.getInstance().showSuccessToast(activity, "留言成功");
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
