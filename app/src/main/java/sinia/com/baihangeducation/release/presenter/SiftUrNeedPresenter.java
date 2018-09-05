package sinia.com.baihangeducation.release.presenter;

import android.app.Activity;
import android.util.Log;

import com.example.framwork.base.BasePresenter;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.OnRequestListener;
import com.example.framwork.utils.Toast;

import java.util.HashMap;

import sinia.com.baihangeducation.release.view.ISiftUrNeedView;
import com.mcxtzhang.swipemenulib.info.HomePartTimeSearchListInfo;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;

public class SiftUrNeedPresenter extends BasePresenter {
    private Activity activity;
    private ISiftUrNeedView view;

    public SiftUrNeedPresenter(Activity activity, ISiftUrNeedView view) {
        super(activity);
        this.activity = activity;
        this.view = view;
    }

    public void getPartTimeSeachList() {
        HashMap mSeachWordInfo = BaseRequestInfo.getInstance().getRequestInfo(activity, "getConditionForJob", "home", false);
        mSeachWordInfo.put("city_id", view.getCityId());
        mSeachWordInfo.put("type", view.getType());
        view.showLoading();
        post(mSeachWordInfo, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                Log.i("筛选条件数据",bean.toString());
                HomePartTimeSearchListInfo mHomePartTimeSearchListInfo = bean.parseObject(HomePartTimeSearchListInfo.class);
                view.getSuccess(mHomePartTimeSearchListInfo);
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
