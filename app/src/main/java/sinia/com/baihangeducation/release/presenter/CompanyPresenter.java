package sinia.com.baihangeducation.release.presenter;

import com.example.framwork.base.BasePresenter;
import android.app.Activity;
import android.util.Log;

import com.example.framwork.base.BasePresenter;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.OnRequestListener;
import com.example.framwork.utils.Toast;

import java.util.HashMap;

import sinia.com.baihangeducation.mine.model.AccountManger;
import sinia.com.baihangeducation.release.view.ISendCompanyJobView;
import sinia.com.baihangeducation.release.view.ISendReleaseJobView;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;

public class CompanyPresenter extends BasePresenter {

    private Activity activity;
    private ISendCompanyJobView   view;

    public CompanyPresenter(Activity activity, ISendCompanyJobView view) {
        super(activity);
        this.activity = activity;
        this.view = view;
    }

    public void releaseJob() {
//        if (!AccountManger.checkReleaseJobInfo(activity, view.getReleaseType(), view.getReleaseTitle(), view.getProvId(), view.getAddress(), view.getNumLower(),
//                view.getNumUpper(), view.getEducationId(), view.getExperienceId(), view.getMoneyType(), view.getMoneyUpper(), view.getMoneyLower(), view.getMoneyUpper(),
//                view.getIndustryId(), view.getJobTypeIds(), view.getSexId(), view.getLanguage(), view.getTagIds(), view.getDateStart(), view.getDateEnd(),
//                view.getTimeStart(), view.getTimeEnd(), view.getLinkPerson(), view.getLinkPhone(), view.getReleaseContent())) {
//            return;
//        }
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "addJob", "Publish", true);
        info.put("type", view.getReleaseType());
        info.put("job_type_ids", view.getJobTypeIds());
        info.put("tag_ids", view.getTagIds());
        info.put("industry_id", view.getIndustryId());
        info.put("title", view.getReleaseTitle());
        info.put("content", view.getReleaseContent());
        info.put("num_lower", view.getNumLower());
        info.put("num_upper", view.getNumUpper());
        info.put("money_type", view.getMoneyType());
        info.put("money_lower", view.getMoneyLower());
        info.put("money_upper", view.getMoneyUpper());
        info.put("language", view.getLanguage());
        info.put("education", view.getEducationId());
        info.put("experience", view.getExperienceId());
        info.put("sex", view.getSexId());
        info.put("date_start", view.getDateStart());
        info.put("date_end", view.getDateEnd());
        info.put("time_start", view.getTimeStart());
        info.put("time_end", view.getTimeEnd());
        info.put("prov_id", view.getProvId());
        info.put("city_id", view.getReleaseCityId());
        info.put("dist_id", view.getDistId());
        info.put("address", view.getAddress());
        info.put("link_person", view.getLinkPerson());
        info.put("link_phone", view.getLinkPhone());
        view.showLoading();
        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                Log.i("发布职位结果", bean.toString());
                view.getReleaseSuccess();
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

    public void editJob() {
//        if (!AccountManger.checkReleaseJobInfo(activity, view.getReleaseType(), view.getReleaseTitle(), view.getProvId(), view.getAddress(), view.getNumLower(),
//                view.getNumUpper(), view.getEducationId(), view.getExperienceId(), view.getMoneyType(), view.getMoneyUpper(), view.getMoneyLower(), view.getMoneyUpper(),
//                view.getIndustryId(), view.getJobTypeIds(), view.getSexId(), view.getLanguage(), view.getTagIds(), view.getDateStart(), view.getDateEnd(),
//                view.getTimeStart(), view.getTimeEnd(), view.getLinkPerson(), view.getLinkPhone(), view.getReleaseContent())) {
//            return;
//        }
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "addJob", "Publish", true);
        info.put("type", view.getReleaseType());
        info.put("job_type_ids", view.getJobTypeIds());
        info.put("tag_ids", view.getTagIds());
        info.put("industry_id", view.getIndustryId());
        info.put("title", view.getReleaseTitle());
        info.put("content", view.getReleaseContent());
        info.put("num_lower", view.getNumLower());
        info.put("num_upper", view.getNumUpper());
        info.put("money_type", view.getMoneyType());
        info.put("money_lower", view.getMoneyLower());
        info.put("money_upper", view.getMoneyUpper());
        info.put("language", view.getLanguage());
        info.put("education", view.getEducationId());
        info.put("experience", view.getExperienceId());
        info.put("sex", view.getSexId());
        info.put("date_start", view.getDateStart());
        info.put("date_end", view.getDateEnd());
        info.put("time_start", view.getTimeStart());
        info.put("time_end", view.getTimeEnd());
        info.put("prov_id", view.getProvId());
        info.put("city_id", view.getReleaseCityId());
        info.put("dist_id", view.getDistId());
        info.put("address", view.getAddress());
        info.put("link_person", view.getLinkPerson());
        info.put("link_phone", view.getLinkPhone());
        info.put("id",view.getEditID());
        view.showLoading();
        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                Log.i("编辑成功", bean.toString());
                view.getReleaseSuccess();
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
