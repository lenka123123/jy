package sinia.com.baihangeducation.home.present;

import android.app.Activity;
import android.text.TextUtils;

import com.example.framwork.base.BasePresenter;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.OnRequestListener;
import com.example.framwork.utils.Toast;
import com.yanzhenjie.nohttp.FileBinary;
import com.yanzhenjie.nohttp.rest.Request;

import java.io.File;
import java.util.HashMap;

import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.mine.model.AccountManger;
import sinia.com.baihangeducation.home.view.CompanyJoinInView;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;

/**
 * Created by Administrator on 2018/4/24.
 */

public class CompanyJoinInPresenter extends BasePresenter {
    private Activity activity;
    private CompanyJoinInView view;

    public CompanyJoinInPresenter(Activity activity, CompanyJoinInView view) {
        super(activity);
        this.activity = activity;
        this.view = view;
    }

    public void submitCompanyInfo() {
        if (!AccountManger.checkCompanyJoinIn(activity, view.getLogo(), view.getLegalName(), view.getCompanyName(), view.getAdressDetail(), view.getCompanyTel(), view.getHeadName(), view.getHeadTel(), view.getLicensePhoto(),view.getOrganizationType())) {
            return;
        }
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "setCompanyApply", "home", true);
        info.put("user_id", AppConfig.USERID);
        info.put("token", AppConfig.TOKEN);

        info.put("legal_person", view.getLegalName());
        info.put("name", view.getCompanyName());
        info.put("prov_id", view.getProvinceId());
        info.put("city_id", view.getCityId());
        info.put("dist_id", view.getDistId());
        info.put("address", view.getAdressDetail());
        info.put("tel", view.getCompanyTel());
        info.put("type",view.getOrganizationType());
        info.put("link_person", view.getHeadName());
        info.put("link_phone", view.getHeadTel());
        Request<String> request = postFile(info);
        if (!TextUtils.isEmpty(view.getLogo()) && !TextUtils.isEmpty(view.getLicensePhoto())) {
            request.add("logo", new FileBinary(new File(view.getLogo())));
            request.add("business_license", new FileBinary(new File(view.getLicensePhoto())));
            view.showLoading();
            model.execute(activity, request, new OnRequestListener() {
                @Override
                public void requestSuccess(BaseResponseBean bean) {
                    view.submitCompanyInfoSuccess();
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
}
