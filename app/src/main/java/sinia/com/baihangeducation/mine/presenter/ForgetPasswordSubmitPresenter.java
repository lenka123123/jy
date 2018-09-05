package sinia.com.baihangeducation.mine.presenter;

import android.app.Activity;

import com.example.framwork.base.BasePresenter;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.OnRequestListener;
import com.example.framwork.utils.CommonUtil;
import com.example.framwork.utils.Toast;

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
}
