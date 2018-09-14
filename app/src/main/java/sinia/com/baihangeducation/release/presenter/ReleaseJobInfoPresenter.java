package sinia.com.baihangeducation.release.presenter;

import android.app.Activity;
import android.util.Log;

import com.example.framwork.base.BasePresenter;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.OnRequestListener;
import com.example.framwork.utils.Toast;

import java.util.HashMap;

import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.release.info.ReleaseJobInfoListInfo;
import sinia.com.baihangeducation.release.view.IReleaseJobInfoView;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;

public class ReleaseJobInfoPresenter extends BasePresenter {
    private Activity activity;
    private IReleaseJobInfoView view;

    public ReleaseJobInfoPresenter(Activity activity, IReleaseJobInfoView view) {
        super(activity);
        this.activity = activity;
        this.view = view;
    }

    public void getJobOptionList() {
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "getJobOptionList", "publish", false);
// 1全职 2兼职
        // TODO: 2018/9/7 0007  type
        info.put("user_id", AppConfig.USERID);
        info.put("token", AppConfig.TOKEN);

        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                Log.i("获取职位选项列表", bean.toString());
                ReleaseJobInfoListInfo info = bean.parseObject(ReleaseJobInfoListInfo.class);
                view.getReleaseJobInfoSuccess(info);
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
