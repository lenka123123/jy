package sinia.com.baihangeducation.mine.presenter;

import android.app.Activity;
import android.util.Log;

import com.example.framwork.base.BasePresenter;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.OnRequestListener;
import com.example.framwork.utils.CommonUtil;
import com.example.framwork.utils.Toast;
import com.mcxtzhang.swipemenulib.info.bean.AuthCodeInfo;

import java.util.HashMap;

import sinia.com.baihangeducation.mine.model.AccountManger;
import sinia.com.baihangeducation.mine.view.IForgetPasswordView;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;

/**
 * Created by Administrator on 2018.03.12.
 */

public class ForgetPasswordSubmitPresenter extends BasePresenter {

    private IForgetPasswordView view;
    private int thirdType;

    public ForgetPasswordSubmitPresenter(Activity activity, IForgetPasswordView view) {
        super(activity);
        this.activity = activity;
        this.view = view;
    }

    /**
     * 获取验证码
     */
    public void forgetPasswordSubmit() {
        if (!AccountManger.checkFindPwd_2(activity, view.getPassword(), view.getPasswordAgain())) {
            //提交操作
            return;
        }
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "resetPwd", "default", false);
        info.put("mobile", view.getPhoneNum());
        info.put("password", view.getPassword());
        info.put("vcode", view.getAuthCode());
        info.put("device_id", CommonUtil.getAndroidId(activity));
        view.showLoading();
        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                view.showAuthCodeSuccess();
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

    /// 忘记密码获取验证码
    public void getAuthCode(String phone, String type) {
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "sendSignCode", "default", false);
        info.put("mobile", phone);
        info.put("type", type);
        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                com.example.framwork.utils.Toast.getInstance().showErrorToast(activity, "获取验证码成功");
            }

            @Override
            public void requestFailed(String error) {
                com.example.framwork.utils.Toast.getInstance().showErrorToast(activity, error);
            }

            @Override
            public void requestFinish() {

            }
        });
    }
}
