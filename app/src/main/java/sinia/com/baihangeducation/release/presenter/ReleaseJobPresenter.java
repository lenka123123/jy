package sinia.com.baihangeducation.release.presenter;

import android.app.Activity;
import android.util.Log;

import com.example.framwork.base.BasePresenter;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.OnRequestListener;
import com.example.framwork.utils.Toast;
import com.example.framwork.utils.UserInfo;

import java.util.HashMap;

import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.MyApplication;
import sinia.com.baihangeducation.release.view.ISendReleaseJobView;
import sinia.com.baihangeducation.mine.model.AccountManger;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;

public class ReleaseJobPresenter extends BasePresenter {

    private Activity activity;
    private ISendReleaseJobView view;

    public ReleaseJobPresenter(Activity activity, ISendReleaseJobView view) {
        super(activity);
        this.activity = activity;
        this.view = view;
    }

    public void releaseJob(String job_id) {
        System.out.println(view.getMoneyLower() + "是否返回" + AccountManger.checkReleaseJobInfo(activity,
                view.getReleaseType(),
                view.getReleaseTitle(),
                view.getProvId(),
                view.getAddress(),
                view.getNumUpper(),
                view.getAgeLower(),
                view.getGender(),
                view.getExp(),
                view.getSalaryType(),
                view.getMoneyUpper(),
                view.getJobType(),
                view.getJobTag(),
                view.getTimeEnd(),
                view.getIsContinue(),
                view.getIsInterview(),
                view.getLinkPerson(),
                view.getLinkType(),  //联系方式
                view.getReleaseContent(),
                view.getJobTime()
        ));


        if (!AccountManger.checkReleaseJobInfo(activity,
                view.getReleaseType(),
                view.getReleaseTitle(),
                view.getProvId(),
                view.getAddress(),
                view.getNumUpper(),
                view.getAgeLower(),
                view.getGender(),
                view.getExp(),
                view.getSalaryType(),
                view.getMoneyUpper(),
                view.getJobType(),
                view.getJobTag(),
                view.getTimeEnd(),
                view.getIsContinue(),
                view.getIsInterview(),
                view.getLinkPerson(),
                view.getLinkType(),  //联系方式
                view.getReleaseContent(),
                view.getJobTime()
        )) {
            HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "pushJob", "club", true);

            info.put("user_id", AppConfig.USERID);
            info.put("token", AppConfig.TOKEN);
            if (!job_id.equals(""))
                info.put("job_id", job_id);

            System.out.println(view.getMoneyLower() + "job_ideeee" + job_id);
            if (view.getReleaseType().equals("1")) {//仅全职传递
//
//            info.put("time_end", view.getTimeEnd());
//            info.put("education", view.getEducationId());
//            info.put("time_start", view.getTimeStart());
//            info.put("date_start", view.getDateStart());
//            info.put("money_lower", view.getMoneyLower());
//            info.put("language", view.getLanguage());
            }

            info.put("industry_id", view.getIndustryId());//所属行业
            info.put("title", view.getReleaseTitle());
            info.put("content", view.getReleaseContent());
            info.put("num_lower", view.getNumUpper());
            info.put("money_type", view.getMoneyType());
            info.put("money_lower", view.getMoneyLower());
            info.put("experience", view.getExperienceId());
            info.put("sex", view.getSexId());
            info.put("prov_id", view.getProvId());
            info.put("city_id", view.getReleaseCityId());
            info.put("dist_id", view.getDistId());
            info.put("address", view.getAddress());
            info.put("link_person", view.getLinkPerson());
            info.put("link_phone", view.getLinkPhone());
            info.put("age_lower", view.getAgeLower());
            info.put("age_upper", view.getAgeUpper());
            info.put("is_continue", view.getIsContinue());
            info.put("is_interview", view.getIsInterview());
            info.put("time_group", view.getJobTime());//上班时间
            info.put("link_type", view.getLinkType());//联系方式


//        info.put("job_type_ids", view.getJobTypeIds());
//        info.put("type", view.getReleaseType());//职位标签编号 多个英文逗号分隔
//        info.put("num_upper", view.getNumUpper());
//        info.put("tag_ids", view.getTagIds());
//        info.put("money_upper", view.getMoneyUpper());


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
        } else {
            System.out.println(view.getMoneyLower() + "job_ideeee去去第三方士大夫去");
        }
    }
//    public void editJob() {
//        if (!AccountManger.checkReleaseJobInfo(activity, view.getReleaseType(), view.getReleaseTitle(), view.getProvId(), view.getAddress(),
//                view.getNumUpper(), view.getEducationId(), view.getExperienceId(), view.getMoneyType(), view.getMoneyUpper(), view.getMoneyLower(), view.getMoneyUpper(),
//                view.getIndustryId(), view.getJobTypeIds(), view.getSexId(), view.getLanguage(), view.getTagIds(), view.getDateStart(), view.getDateEnd(),
//                view.getTimeStart(), view.getTimeEnd(), view.getLinkPerson(), view.getLinkPhone(), view.getReleaseContent())) {
//            return;
//        }
//        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "addJob", "Publish", true);
//        info.put("type", view.getReleaseType());
//        info.put("job_type_ids", view.getJobTypeIds());
//        info.put("tag_ids", view.getTagIds());
//        info.put("industry_id", view.getIndustryId());
//        info.put("title", view.getReleaseTitle());
//        info.put("content", view.getReleaseContent());
//        info.put("num_lower", view.getNumLower());
//        info.put("num_upper", view.getNumUpper());
//        info.put("money_type", view.getMoneyType());
//        info.put("money_lower", view.getMoneyLower());
//        info.put("money_upper", view.getMoneyUpper());
//        info.put("language", view.getLanguage());
//        info.put("education", view.getEducationId());
//        info.put("experience", view.getExperienceId());
//        info.put("sex", view.getSexId());
//        info.put("date_start", view.getDateStart());
//        info.put("date_end", view.getDateEnd());
//        info.put("time_start", view.getTimeStart());
//        info.put("time_end", view.getTimeEnd());
//        info.put("prov_id", view.getProvId());
//        info.put("city_id", view.getReleaseCityId());
//        info.put("dist_id", view.getDistId());
//        info.put("address", view.getAddress());
//        info.put("link_person", view.getLinkPerson());
//        info.put("link_phone", view.getLinkPhone());
//        info.put("id", view.getEditID());
//        view.showLoading();
//        post(info, new OnRequestListener() {
//            @Override
//            public void requestSuccess(BaseResponseBean bean) {
//                Log.i("编辑成功", bean.toString());
//                view.getReleaseSuccess();
//            }
//
//            @Override
//            public void requestFailed(String error) {
//                Toast.getInstance().showErrorToast(activity, error);
//            }
//
//            @Override
//            public void requestFinish() {
//                view.hideLoading();
//            }
//        });
//    }


}