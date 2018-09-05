package sinia.com.baihangeducation.home.present;

import android.app.Activity;
import android.util.Log;

import com.example.framwork.base.BasePresenter;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.OnRequestListener;
import com.example.framwork.utils.Toast;

import java.util.HashMap;

import com.mcxtzhang.swipemenulib.info.SimpleResumeInfo;

import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.home.view.IGetSimpleResumeView;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;

public class GetSimpleResumePresenter extends BasePresenter {
    private Activity activity;
    private IGetSimpleResumeView view;

    public GetSimpleResumePresenter(Activity activity, IGetSimpleResumeView view) {
        super(activity);
        this.activity = activity;
        this.view = view;
    }

    public void getSimpleInfo() {
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "getSimpleData", "ucenter", true);
        info.put("user_id", AppConfig.USERID);
        info.put("token", AppConfig.TOKEN);
       view.showLoading();
        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                SimpleResumeInfo simpleResumeInfo = bean.parseObject(SimpleResumeInfo.class);
                view.getSimpleResumeSuccess(simpleResumeInfo);
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

    public void sendSimpleInfo() {
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "setSimpleData", "ucenter", true);
        info.put("name", view.getName());
        info.put("school_id", AppConfig.SCHOOLNAMEID);
        info.put("school_name", AppConfig.SCHOOLNAME);
        info.put("major_id", AppConfig.SCHOOLMAGORID);
        info.put("major_name", AppConfig.SCHOOLMAGOR);

        info.put("user_id", AppConfig.USERID);
        info.put("token", AppConfig.TOKEN);
        view.showLoading();
        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                Log.i("发送简单资料", bean.toString());
                view.sendSimpleResumeSuccess();
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
