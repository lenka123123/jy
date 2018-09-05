package sinia.com.baihangeducation.mine.presenter;

import android.app.Activity;
import android.util.Log;

import com.example.framwork.base.BasePresenter;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.OnRequestListener;
import com.example.framwork.utils.Toast;

import java.util.HashMap;

import com.mcxtzhang.swipemenulib.info.bean.MyReleaseJobDetaiInfo;
import sinia.com.baihangeducation.mine.view.IGetMyReleaseJobDetaiInfoView;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;

public class GetMyReleaseJobDetaiInfoPresenter extends BasePresenter {
    private Activity activity;
    private IGetMyReleaseJobDetaiInfoView view;

    public GetMyReleaseJobDetaiInfoPresenter(Activity activity, IGetMyReleaseJobDetaiInfoView view) {
        super(activity);
        this.activity = activity;
        this.view = view;
    }

    public void getMyReleaseJobDetaiData() {
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "getJobInfo", "home", true);
        info.put("job_id", view.getMyReleaseJobDetailJobId());
        info.put("city_id", view.getMyReleaseJobDetailCityId());
        view.showLoading();
        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                Log.i("获取我发布的职位详情", bean.toString());
                MyReleaseJobDetaiInfo jobDetaiInfo = bean.parseObject(MyReleaseJobDetaiInfo.class);
                view.getMyReleaseJobDetailInfoSuccess(jobDetaiInfo);
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
