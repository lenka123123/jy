package sinia.com.baihangeducation.mine.presenter;

import android.app.Activity;
import android.util.Log;

import com.example.framwork.base.BasePresenter;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.OnRequestListener;
import com.example.framwork.utils.Toast;

import java.util.HashMap;

import com.mcxtzhang.swipemenulib.info.bean.DoSignInfo;
import com.mcxtzhang.swipemenulib.info.bean.GetSignInfo;

import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.mine.view.ISignView;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;

/**
 * Created by Administrator on 2018/4/13.
 */

public class GetSignPresenter extends BasePresenter {

    private Activity activity;
    private ISignView view;

    public GetSignPresenter(Activity activity, ISignView view) {
        super(activity);
        this.activity = activity;
        this.view = view;
    }

    public GetSignPresenter(Activity activity) {
        super(activity);
        this.activity = activity;
    }

    public void getSignDays(String mouth) {
        HashMap getSignDaysInfo = BaseRequestInfo.getInstance().getRequestInfo(activity, "getSignDays", "ucenter", true);
        getSignDaysInfo.put("user_id", AppConfig.USERID);
        getSignDaysInfo.put("token", AppConfig.TOKEN);
        getSignDaysInfo.put("month", mouth);
        Log.i("查看mouth", mouth);
        view.showLoading();
        post(getSignDaysInfo, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                GetSignInfo getSignInfo = bean.parseObject(GetSignInfo.class);
                view.getSignDaysSuccess(getSignInfo);
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

    public void doSign() {
        HashMap doSignInfo = BaseRequestInfo.getInstance().getRequestInfo(activity, "sign", "ucenter", true);
        doSignInfo.put("user_id", AppConfig.USERID);
        doSignInfo.put("token", AppConfig.TOKEN);
        view.showLoading();
        post(doSignInfo, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                Log.i("签到", bean.toString() + "签到");
                if (bean != null) {
                    Toast.getInstance().showSuccessToast(activity, "签到成功");
                    DoSignInfo doSignInfo1 = bean.parseObject(DoSignInfo.class);
                    view.doSignSuccess(doSignInfo1);
                }

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
