package sinia.com.baihangeducation.find.presenter;

import android.app.Activity;

import com.example.framwork.base.BasePresenter;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.OnRequestListener;

import java.util.HashMap;

import com.mcxtzhang.swipemenulib.info.JobBangClassListInfo;
import sinia.com.baihangeducation.find.view.JobBangClassView;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;

/**
 * Created by Administrator on 2018/4/20.
 */

public class JobBangClassPresenter extends BasePresenter {
    private Activity activity;
    private JobBangClassView view;

    public JobBangClassPresenter(Activity activity, JobBangClassView view) {
        super(activity);
        this.activity = activity;
        this.view = view;
    }

    public void getJobBangClassData() {
        HashMap mJobBangClassData = BaseRequestInfo.getInstance().getRequestInfo(activity, "getSchoolPageInfo", "find", false);
        view.showLoading();
        post(mJobBangClassData, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                JobBangClassListInfo mJobBangClassListInfo = bean.parseObject(JobBangClassListInfo.class);
                view.getJobBangClassDataSuccess(mJobBangClassListInfo);
            }

            @Override
            public void requestFailed(String error) {

            }

            @Override
            public void requestFinish() {
                view.hideLoading();
            }
        });
    }
}
