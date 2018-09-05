package sinia.com.baihangeducation.minecompany.presenter;

import android.app.Activity;
import android.util.Log;

import com.example.framwork.base.BasePresenter;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.OnRequestListener;
import com.example.framwork.utils.CommonUtil;
import com.example.framwork.utils.Toast;

import java.util.HashMap;

import sinia.com.baihangeducation.minecompany.info.CompanyUCenterReleaseTrainingInfo;
import com.mcxtzhang.swipemenulib.info.bean.CompanyReleaseJobInfo;
import sinia.com.baihangeducation.minecompany.view.ICompanyUCenterReleaseJobView;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;

public class CompanyUCenterReleaseJobPresenter extends BasePresenter {
    private Activity activity;
    private ICompanyUCenterReleaseJobView view;

    public CompanyUCenterReleaseJobPresenter(Activity activity, ICompanyUCenterReleaseJobView view) {
        super(activity);
        this.activity = activity;
        this.view = view;
    }

    /**
     * 获取我发布职位列表
     */
    public void getCompanyReleaseJobData() {
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "geyMyJobList", "company", true);
        info.put("type", view.getCompanyUCenterReleaseJobTypy());
        info.put("page", view.getCompanyUCenterReleaseJobPage());
        info.put("perpage", view.getCompanyUCenterReleaseJobPerPage());
        view.showLoading();
        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                Log.i("获取我发布的职位数据", bean.toString());
                CompanyReleaseJobInfo releaseJobInfo = bean.parseObject(CompanyReleaseJobInfo.class);
                int maxpag = CommonUtil.getMaxPage(releaseJobInfo.count, releaseJobInfo.perpage);
                view.getCompanyUCenterReleaseJobSuccess(releaseJobInfo, maxpag);
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

    /**
     * 上架 下架 删除
     */
    public void upReleaseJobData(final int status) {
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "setJobStatus", "company", true);
        info.put("job_id", view.getReleaseJobId());
        info.put("status", status);
        Log.i("参数", view.getReleaseJobId() + "这边的id");
        Log.i("参数", status + "状态");
        view.showLoading();
        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                Log.i("上架", bean.toString());
                view.getStatusChangeSuccess(status);
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

    public void getMyReleaseTrainingInfo() {
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "getMyTrainList", "company", true);
        info.put("page", view.getCompanyUCenterReleaseJobPage());
        info.put("perpage", view.getCompanyUCenterReleaseJobPerPage());
        view.showLoading();
        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                Log.i("培训列表", bean.toString());
                CompanyUCenterReleaseTrainingInfo trainingInfo = bean.parseObject(CompanyUCenterReleaseTrainingInfo.class);
                int maxpag = CommonUtil.getMaxPage(trainingInfo.count, trainingInfo.perpage);
                view.getCompanyUcenterReleaseTrianSuccess(trainingInfo,maxpag);
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

    /**
     * 培训  上架 下架 删除
     */
    public void actionMyTraining(final int status) {
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "setTrainStatus", "company", true);
        info.put("train_id", view.getReleaseJobId());
        info.put("status", status);
        Log.i("参数", view.getReleaseJobId() + "这边的id");
        Log.i("参数", status + "状态");
        view.showLoading();
        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                Log.i("上架", bean.toString());
                view.getStatusChangeSuccess(status);
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
