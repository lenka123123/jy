package sinia.com.baihangeducation.newcampus.presenter;

import android.app.Activity;

import com.example.framwork.base.BasePresenter;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.OnRequestListener;
import com.example.framwork.utils.Toast;

import java.util.HashMap;

import sinia.com.baihangeducation.newcampus.interfaces.AddFirendListener;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;

public class AddFirendPresenter extends BasePresenter {
    private Activity activity;

    public AddFirendPresenter(Activity activity) {
        super(activity);
        this.activity = activity;
    }

    public void releaseComment(final AddFirendListener addFirendPresenter) {
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "setComment", "home", true);
        info.put("type", "7");
        info.put("parent_id", "0");
        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                addFirendPresenter.getAddFirendSuccessListener();
            }

            @Override
            public void requestFailed(String error) {
                addFirendPresenter.getAddFirendFailListener();
                Toast.getInstance().showErrorToast(activity, error);
            }

            @Override
            public void requestFinish() {

            }
        });

    }
}
