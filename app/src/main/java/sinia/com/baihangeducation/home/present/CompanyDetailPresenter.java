package sinia.com.baihangeducation.home.present;

import android.app.Activity;
import android.util.Log;

import com.example.framwork.base.BasePresenter;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.OnRequestListener;
import com.example.framwork.utils.CommonUtil;
import com.example.framwork.utils.Toast;

import java.util.HashMap;

import com.mcxtzhang.swipemenulib.info.CompanyDetailInfo;

import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.home.view.ICompanyDetailView;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;

public class CompanyDetailPresenter extends BasePresenter {
    private Activity activity;
    private ICompanyDetailView view;

    public CompanyDetailPresenter(Activity activity, ICompanyDetailView view) {
        super(activity);
        this.activity = activity;
        this.view = view;
    }

    public void getCompanyInfo() {
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "getCompanyInfo", "home", true);
        info.put("page", view.getPage());
        info.put("perpage", view.getPerpage());
        info.put("lat", view.getLocationLat());
        info.put("lng", view.getLocationLng());
        info.put("company_id", view.getCompanyId());
        info.put("user_id", AppConfig.USERID);
        info.put("token", AppConfig.TOKEN);
        view.showLoading();
        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                Log.i("公司信息", bean.toString());
                CompanyDetailInfo companyDetailInfo = bean.parseObject(CompanyDetailInfo.class);
                int maxpag = CommonUtil.getMaxPage(companyDetailInfo.company_job_list.count, companyDetailInfo.company_job_list.perpage);
                view.getCompanyDetailSuccess(companyDetailInfo , maxpag);
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
