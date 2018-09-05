package sinia.com.baihangeducation.release.presenter;

import android.app.Activity;
import android.util.Log;

import com.example.framwork.base.BasePresenter;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.OnRequestListener;
import com.example.framwork.utils.Toast;
import com.example.framwork.utils.UserInfo;

import java.util.HashMap;

import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.release.info.ApplyResultInfo;
import sinia.com.baihangeducation.release.view.IAddFragmentView;
import sinia.com.baihangeducation.MyApplication;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;

/**
 * Created by Administrator on 2018/4/17.
 */

public class AddFragmentPresenter extends BasePresenter {

    private Activity activity;
    private IAddFragmentView view;


    public AddFragmentPresenter(Activity activity, IAddFragmentView view) {
        super(activity);
        this.activity = activity;
        this.view = view;

    }

    public void getApplyResult(final int flag) {
        HashMap mGetBaseInfoData = BaseRequestInfo.getInstance().getRequestInfo(activity, "getUserType", "ucenter", true);
        mGetBaseInfoData.put("user_id", AppConfig.USERID);
        mGetBaseInfoData.put("token", AppConfig.TOKEN);
        post(mGetBaseInfoData, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                Log.i("获取资本信息", bean.toString());
                ApplyResultInfo info = bean.parseObject(ApplyResultInfo.class);
                view.getGetBaseInfoSuccess(info, flag);
            }

            @Override
            public void requestFailed(String error) {
                Toast.getInstance().showErrorToast(activity, error);
            }

            @Override
            public void requestFinish() {

            }
        });
    }

}
