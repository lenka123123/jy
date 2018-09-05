package sinia.com.baihangeducation.minecompany.presenter;

import android.app.Activity;
import android.util.Log;

import com.example.framwork.base.BasePresenter;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.OnRequestListener;

import java.util.HashMap;

import com.mcxtzhang.swipemenulib.info.TraingDetailInfo;
import sinia.com.baihangeducation.home.view.TraingDetailView;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;

public class CompanyUCenterEditTrainingPresenter extends BasePresenter {
    private Activity activity;
    private TraingDetailView view;

    public CompanyUCenterEditTrainingPresenter(Activity activity, TraingDetailView view) {
        super(activity);
        this.activity = activity;
        this.view = view;
    }

    public void getMyReleaseTrainingDetailInfo(){
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity,"getTrainInfo","home",true);
        info.put("train_id",view.getTrainId());
        view.showLoading();
        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                Log.i("培训详情",bean.toString());
                TraingDetailInfo mTraingDetailInfo = bean.parseObject(TraingDetailInfo.class);
                view.getMyReleaseTraingDetailInfoSuccess(mTraingDetailInfo);
            }

            @Override
            public void requestFailed(String error) {

            }

            @Override
            public void requestFinish() {

            }
        });
    }
}
