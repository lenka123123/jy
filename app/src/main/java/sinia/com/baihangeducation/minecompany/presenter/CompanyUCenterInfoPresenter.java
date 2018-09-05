package sinia.com.baihangeducation.minecompany.presenter;

import android.app.Activity;
import android.util.Log;

import com.example.framwork.base.BasePresenter;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.OnRequestListener;
import com.example.framwork.utils.Toast;

import java.util.HashMap;

import sinia.com.baihangeducation.mine.model.AccountManger;
import sinia.com.baihangeducation.minecompany.info.CompanyDataInfo;
import sinia.com.baihangeducation.minecompany.view.IUpdateCompanyUCenterInfoView;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;

public class CompanyUCenterInfoPresenter extends BasePresenter {
    private Activity activity;
    private IUpdateCompanyUCenterInfoView view;

    public CompanyUCenterInfoPresenter(Activity activity, IUpdateCompanyUCenterInfoView view) {
        super(activity);
        this.activity = activity;
        this.view = view;
    }

    public void getCompanyUCenterInfo() {
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "getCompanyInfo", "company", true);
        view.showLoading();
        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                Log.i("获取企业信息", bean.toString());
                CompanyDataInfo companyDataInfo = bean.parseObject(CompanyDataInfo.class);
                view.getCompanyInfoSuccess(companyDataInfo);
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

    public void updateCompanyInfo() {
        if (!AccountManger.checkCompanyInfo(activity,view.getprov_id(),view.getaddress(),view.gettel(),view.getlink_person(),view.getlink_phone())){
            return;
        }
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "editCompanyInfo", "company", true);
        info.put("prov_id",view.getprov_id());
        info.put("city_id",view.getcity_id());
        info.put("dist_id",view.getdist_id());
        info.put("address",view.getaddress());
        info.put("tel",view.gettel());
        info.put("link_person",view.getlink_person());
        info.put("link_phone",view.getlink_phone());
        view.showLoading();
        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                Log.i("编辑企业信息", bean.toString());
                view.updateCompanyInfoSucccess();
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
