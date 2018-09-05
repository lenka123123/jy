package sinia.com.baihangeducation.mine.presenter;

import android.app.Activity;
import android.util.Log;

import com.example.framwork.base.BasePresenter;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.OnRequestListener;

import com.mcxtzhang.swipemenulib.info.bean.AuthCodeInfo;
import sinia.com.baihangeducation.mine.view.IAuthCodeView;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;

import java.util.HashMap;

/**
 * Created by Administrator on 2018.03.07.
 */

public class AuthCodePresenter extends BasePresenter {

    private HashMap info;
    private Activity activity;
    private IAuthCodeView view;
    private int thirdType;

    public AuthCodePresenter(Activity activity, IAuthCodeView view) {
        super(activity);
        this.activity = activity;
        this.view = view;
    }

    /**
     * 获取验证码
     */
    public void getAuthCode() {
        if (Integer.valueOf(view.getAuthCodeType()) == 2) {
            info = BaseRequestInfo.getInstance().getRequestInfo(activity, "sendSignCode", "default", true);
        } else {
            info = BaseRequestInfo.getInstance().getRequestInfo(activity, "sendSignCode", "default", false);
        }

        info.put("mobile", view.getPhone());
        Log.i("修改密码验证码",view.getAuthCodeType());
        info.put("type", view.getAuthCodeType());
        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                Log.i("修改密码验证码",bean.toString()+"成功");
                AuthCodeInfo authCodeInfo = bean.parseObject(AuthCodeInfo.class);
                Log.i("验证码", authCodeInfo.toString());
                view.showAuthCodeSuccess(authCodeInfo);
            }

            @Override
            public void requestFailed(String error) {
                Log.i("验证码失败", error);
                view.showGetAuthCodeError(error);
            }

            @Override
            public void requestFinish() {

            }
        });
    }
}
