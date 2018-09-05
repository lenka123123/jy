package sinia.com.baihangeducation.mine.presenter;

import android.app.Activity;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.example.framwork.base.BasePresenter;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.OnRequestListener;
import com.example.framwork.utils.Toast;

import java.util.HashMap;
import java.util.List;

import com.mcxtzhang.swipemenulib.info.bean.MyWantJob;
import com.mcxtzhang.swipemenulib.info.bean.WantAreaList_Up;
import com.mcxtzhang.swipemenulib.info.bean.WantJobList_Up;
import sinia.com.baihangeducation.mine.view.WantJobView;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;

/**
 * Created by Administrator on 2018/4/14.
 */

public class WantJobPresenter extends BasePresenter {

    private Activity activity;
    private WantJobView view;

    public WantJobPresenter(Activity activity, WantJobView view) {
        super(activity);
        this.activity = activity;
        this.view = view;
    }

    public void getWantJobData() {
        HashMap mWantJobInfo = BaseRequestInfo.getInstance().getRequestInfo(activity, "getJobIntentionOption", "ucenter", true);
        post(mWantJobInfo, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                Log.i("获取求职意向信息", bean.toString());
                MyWantJob myWantJob = bean.parseObject(MyWantJob.class);

                view.getWantJobSuccess(myWantJob.job_intention);
            }

            @Override
            public void requestFailed(String error) {
                Toast.getInstance().showErrorToast(activity, error);
            }

            @Override
            public void requestFinish() {

            }
        });
    }

    public void getWantAreaData() {
        HashMap mWantJobInfo = BaseRequestInfo.getInstance().getRequestInfo(activity, "getJobIntentionOption", "ucenter", true);
        post(mWantJobInfo, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                Log.i("获取意向区域信息", bean.toString());
                MyWantJob myWantJob = bean.parseObject(MyWantJob.class);
                view.getWantAreaSuccess(myWantJob.job_zone);
            }

            @Override
            public void requestFailed(String error) {
                Toast.getInstance().showErrorToast(activity, error);
            }

            @Override
            public void requestFinish() {

            }
        });
    }

    public void submitData(List<WantJobList_Up> mJobDataUp, List<WantAreaList_Up> mAreaDataUp) {
        HashMap mSubmitDataInfo = BaseRequestInfo.getInstance().getRequestInfo(activity, "setJobIntentionOption", "ucenter", true);
        for (int i = 0; i < mJobDataUp.size(); i++) {
            Log.i("数据遍历查看", mJobDataUp.get(i).intention_id + "jobID");
            Log.i("数据遍历查看", mJobDataUp.get(i).intention_name + "jobNAME");
        }
        for (int i = 0; i < mAreaDataUp.size(); i++) {
            Log.i("数据遍历查看", mAreaDataUp.get(i).zone_id + "areaID");
            Log.i("数据遍历查看", mAreaDataUp.get(i).zone_name + "areaNAME");
        }
        JSONArray jobData = JSONArray.parseArray(JSON.toJSONString(mJobDataUp));
        JSONArray areaData = JSONArray.parseArray(JSON.toJSONString(mAreaDataUp));
        mSubmitDataInfo.put("job_intention", jobData);
        mSubmitDataInfo.put("job_zone", areaData);

        post(mSubmitDataInfo, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                Toast.getInstance().showSuccessToast(activity, "提交成功");
                Log.i("提交是否成功", bean.toString());
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
