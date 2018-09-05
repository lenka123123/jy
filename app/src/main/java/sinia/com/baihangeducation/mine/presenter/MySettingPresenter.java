package sinia.com.baihangeducation.mine.presenter;

import android.app.Activity;
import android.util.Log;

import com.example.framwork.base.BasePresenter;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.OnRequestListener;
import com.example.framwork.utils.Toast;

import java.util.HashMap;

import sinia.com.baihangeducation.mine.view.IMySettingView;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;

public class MySettingPresenter extends BasePresenter {
    private Activity activity;
    private IMySettingView view;

    public MySettingPresenter(Activity activity, IMySettingView view) {
        super(activity);
        this.activity = activity;
        this.view = view;
    }

    public void logout() {
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "loginOut", "default", false);
        view.showLoading();
        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                Log.i("退出登录", bean.toString());
                view.logoutSuccess();
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
