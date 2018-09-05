package sinia.com.baihangeducation.mine.presenter;


import android.app.Activity;

import com.example.framwork.base.BasePresenter;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.OnRequestListener;
import com.example.framwork.utils.CommonUtil;
import com.example.framwork.utils.SPUtils;
import com.example.framwork.utils.Toast;
import com.example.framwork.utils.UserInfo;

import sinia.com.baihangeducation.mine.model.AccountManger;
import sinia.com.baihangeducation.mine.view.IRegisterView;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;
import com.mcxtzhang.swipemenulib.utils.Constants;

import java.util.HashMap;

/**
 * Created by Administrator on 2018.03.07.
 */

public class RegisterPresenter extends BasePresenter {

    private Activity activity;
    private IRegisterView view;

    public RegisterPresenter(Activity activity, IRegisterView view) {
        super(activity);
        this.activity = activity;
        this.view = view;
    }


    /**
     * 注册
     */
    public void register() {
        if (!AccountManger.checkupRegisterInfo(activity, view.getphoneNum(), view.getAuthCode(), view.getPassword(), view.getPasswordAgain(), view.getIsRead())) {
            return;
        }
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "register", "default", false);
        info.put("mobile", view.getphoneNum());
        info.put("password", view.getPassword());
        info.put("vcode", view.getAuthCode());
        info.put("device_id", CommonUtil.getAndroidId(activity));
//        view.showLoading();
        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {

                loginSuccessBack(bean);
                view.succress();

            }

            @Override
            public void requestFailed(String error) {
                Toast.getInstance().showErrorToast(activity, error);
            }

            @Override
            public void requestFinish() {
//                view.hideLoading();
            }
        });

    }

    /**
     * 注册成功储存用户信息
     *
     * @param bean
     */
    private void loginSuccessBack(BaseResponseBean bean) {
        SPUtils.getInstance().saveObject(activity, Constants.USER_INFO, bean.parseObject(UserInfo.class));
        SPUtils.getInstance().saveObject(activity, Constants.USER_ACCOUNT, view.getphoneNum());
        AccountManger.getUserInfo(activity);
    }
}
