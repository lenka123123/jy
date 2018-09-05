package sinia.com.baihangeducation.home.present;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

import com.example.framwork.base.BasePresenter;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.OnRequestListener;
import com.example.framwork.utils.SpCommonUtils;
import com.example.framwork.utils.Toast;
import com.example.framwork.utils.UserInfo;
import com.mcxtzhang.swipemenulib.info.bean.JobInfoes;
import com.mcxtzhang.swipemenulib.utils.ACache;

import java.util.HashMap;

import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.MyApplication;
import sinia.com.baihangeducation.home.view.JobInfoView;
import sinia.com.baihangeducation.home.view.JobView;
import sinia.com.baihangeducation.supplement.base.Goto;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;

/**
 * Created by Administrator on 2018/4/26.
 */

public class JobInfoesPresenter extends BasePresenter {
    private Activity activity;
    private JobInfoView view;

    public JobInfoesPresenter(Activity activity, JobInfoView view) {
        super(activity);
        this.activity = activity;
        this.view = view;

    }

    public void getJobInfo() {
        ACache aCache = ACache.get(activity);
        HashMap<String, Object> info = new HashMap<>();
        info.clear();
        info.put("act", "getJobInfo");
        info.put("app", "home");
        info.put("job_id", view.getJobId());
        info.put("city_id", AppConfig.AREAID);

        info.put("token", AppConfig.TOKEN);
        info.put("user_id", AppConfig.USERID);

//        info.put("job_id", view.getJobId());
//        info.put("city_id", view.getCityId());

        view.showLoading();
        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                JobInfoes mJobInfo = bean.parseObject(JobInfoes.class);
                view.getJobInfoSuccess(mJobInfo);
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

    public void sendResume() {
        HashMap mSendResume = BaseRequestInfo.getInstance().getRequestInfo(activity, "setJobApply", "home", true);
        mSendResume.put("job_id", view.getJobId());
        view.showLoading();
        post(mSendResume, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {


                String parkname = (String) SpCommonUtils.get(activity, AppConfig.FINAL_NUM_FULL_PARK_NUM, "0");
                parkname = (Integer.valueOf(parkname) + 1) + "";
                SpCommonUtils.put(activity, AppConfig.FINAL_NUM_FULL_PARK_NUM, parkname);

                Toast.getInstance().showSuccessToast(activity, "投递成功");
            }

            @Override
            public void requestFailed(String error) {

                if (error.equals("请先完善简历信息后再投递")) {
                    new AlertDialog.Builder(activity).setTitle("提示！").setMessage("请先完善简历信息后再投递。").setPositiveButton("前往完善简历", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Goto.toMyResumeActivity(activity);
                        }
                    }).setNegativeButton("取消", null).show();
                } else
                    Toast.getInstance().showErrorToast(activity, "投递失败,原因：" + error);
            }

            @Override
            public void requestFinish() {
                view.hideLoading();
            }
        });
    }
}
