package sinia.com.baihangeducation.mine.presenter;

import android.app.Activity;
import android.util.Log;

import com.example.framwork.base.BasePresenter;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.OnRequestListener;
import com.example.framwork.utils.CommonUtil;
import com.example.framwork.utils.SPUtils;
import com.example.framwork.utils.Toast;
import com.example.framwork.utils.UserInfo;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

import sinia.com.baihangeducation.mine.model.AccountManger;
import sinia.com.baihangeducation.mine.view.IBindingPhoneView;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;
import sinia.com.baihangeducation.supplement.base.Goto;
import com.mcxtzhang.swipemenulib.utils.Constants;

/**
 * Created by Administrator on 2018.03.14.
 */

public class BindingPhonePresenter extends BasePresenter {
    private Activity activity;
    private IBindingPhoneView view;

    public BindingPhonePresenter(Activity activity, IBindingPhoneView view) {
        super(activity);
        this.activity = activity;
        this.view = view;
    }

    public void binding() {
        if (!AccountManger.checkThridBind(activity, view.getPhoneNum(), view.getAuthCode())) {
            return;
        }
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "thirdAccountBind", "default", false);
        info.put("mobile", view.getPhoneNum());
        info.put("vcode", view.getAuthCode());
        info.put("type", view.getType());
        info.put("union_id", view.getUid());
        info.put("device_id", CommonUtil.getAndroidId(activity));
        view.showLoading();
        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                Log.i("绑定返回数据", bean.toString());
                loginSuccessBack(bean);
                Goto.toMainActivity(activity);
                view.showBindingSucess();
            }

            @Override
            public void requestFailed(String error) {
                Toast.getInstance().showToastView(activity, error);
            }

            @Override
            public void requestFinish() {
            }
        });

    }

    /**
     * 登录成功储存用户信息
     *
     * @param bean
     */
    private void loginSuccessBack(BaseResponseBean bean) {
        SPUtils.getInstance().saveObject(activity, Constants.USER_INFO, bean.parseObject(UserInfo.class));
        SPUtils.getInstance().saveObject(activity, Constants.USER_ACCOUNT, view.getPhoneNum());
        AccountManger.getUserInfo(activity);
        EventBus.getDefault().post(Constants.EB_LOGIN_SUCCESS);
    }
}
