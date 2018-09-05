package sinia.com.baihangeducation.mine.presenter;

import android.app.Activity;

import com.example.framwork.base.BasePresenter;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.OnRequestListener;
import com.example.framwork.utils.CommonUtil;
import com.example.framwork.utils.Toast;

import java.util.HashMap;

import sinia.com.baihangeducation.mine.model.AccountManger;
import sinia.com.baihangeducation.mine.view.IUpdataPwdView;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;

/**
 * Created by Administrator on 2018.03.15.
 */

public class UpdataPwdPresenter extends BasePresenter {
    private Activity activity;
    private IUpdataPwdView view;

    public UpdataPwdPresenter(Activity activity, IUpdataPwdView view) {
        super(activity);
        this.activity = activity;
        this.view = view;
    }

    public void updataPwd() {
        if (!AccountManger.checkupUpadtePwd(activity, view.getNewPwd(), view.getNewPwdAgain(), view.getAuthCode())) {
            return;
        }
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "editPsw", "ucenter", true);
        info.put("vcode", view.getAuthCode());
        info.put("password", view.getNewPwd());
        info.put("device_id", CommonUtil.getAndroidId(activity));
        view.showLoading();
        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                view.showUpdataPwdSuccess();
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
