package sinia.com.baihangeducation.home.present;

import android.app.Activity;
import android.util.Log;

import com.example.framwork.base.BasePresenter;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.OnRequestListener;
import com.example.framwork.utils.Toast;
import com.example.framwork.utils.UserInfo;

import java.util.HashMap;

import sinia.com.baihangeducation.MyApplication;
import sinia.com.baihangeducation.AppConfig;
import com.mcxtzhang.swipemenulib.info.TraingDetailInfo;
import sinia.com.baihangeducation.home.view.TraingDetailView;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;

/**
 * Created by Administrator on 2018/4/27.
 */

public class TraingDetailPresenter extends BasePresenter {
    private Activity activity;
    private TraingDetailView view;


    public TraingDetailPresenter(Activity activity, TraingDetailView view) {
        super(activity);
        this.activity = activity;
        this.view = view;

    }

    public void getTraingDetailInfo() {
        HashMap<String, Object> info = new HashMap<>();
        info.clear();
        info.put("act", "getTrainInfo");
        info.put("app", "home");
        info.put("device", "2");
        //每次传
        info.put("app_version", AppConfig.API_VERSION);

        info.put("token", AppConfig.TOKEN);
        info.put("user_id", AppConfig.USERID);
        info.put("train_id", view.getTrainId());
        view.showLoading();
        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                Log.i("培训内容",bean.toString());
                TraingDetailInfo mTraingDetailInfo = bean.parseObject(TraingDetailInfo.class);
                view.getMyReleaseTraingDetailInfoSuccess(mTraingDetailInfo);
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

    public void sendResume() {
        HashMap mSendResume = BaseRequestInfo.getInstance().getRequestInfo(activity, "setJobApply", "home", true);
        mSendResume.put("job_id", view.getTrainId());
        view.showLoading();
        post(mSendResume, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                Toast.getInstance().showSuccessToast(activity, "投递成功");
            }

            @Override
            public void requestFailed(String error) {
                Toast.getInstance().showErrorToast(activity, "投递失败,原因："+error);
            }

            @Override
            public void requestFinish() {
                view.hideLoading();
            }
        });
    }
}
