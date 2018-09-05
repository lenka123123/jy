package sinia.com.baihangeducation.release.presenter;

import android.app.Activity;
import android.util.Log;

import com.example.framwork.base.BasePresenter;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.OnRequestListener;
import com.example.framwork.utils.CommonUtil;
import com.example.framwork.utils.Toast;

import java.util.HashMap;

import sinia.com.baihangeducation.release.view.ISchoolFunView;
import sinia.com.baihangeducation.find.campus.info.CampusInterestingInfo;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;

public class SchoolFunPresenter extends BasePresenter {
    private Activity activity;
    private ISchoolFunView view;

    public SchoolFunPresenter(Activity activity, ISchoolFunView view) {
        super(activity);
        this.activity = activity;
        this.view = view;
    }

    public void getSchoolFunData() {
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "getFunList", "school", false);
        info.put("page", view.getPage());
        info.put("perpage", view.getPerpage());
        view.showLoading();
        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                Log.i("校园趣事信息", bean.toString());
                CampusInterestingInfo funInfo = bean.parseObject(CampusInterestingInfo.class);
                int maxpag = CommonUtil.getMaxPage(funInfo.count, funInfo.perpage);
                view.getSchoolFunSuccess(funInfo,maxpag);

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
