package sinia.com.baihangeducation.mine.presenter;

import android.app.Activity;
import android.util.Log;

import com.example.framwork.base.BasePresenter;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.OnRequestListener;

import java.util.HashMap;
import java.util.List;

import com.mcxtzhang.swipemenulib.info.bean.AddJobTimeInfoList;
import com.mcxtzhang.swipemenulib.info.bean.AddJobTypeInfoList;

import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.mine.view.IAddJobTypeView;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;

/**
 * Created by Administrator on 2018/3/26.
 */

public class GetJobTypePresenter extends BasePresenter {
    private Activity activity;
    private IAddJobTypeView view;

    public GetJobTypePresenter(Activity activity, IAddJobTypeView view) {
        super(activity);
        this.activity = activity;
        this.view = view;
    }

    public void getJobType() {
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "getJobTypeList", "ucenter", true);
        info.put("user_id", AppConfig.USERID);
        info.put("token", AppConfig.TOKEN);
        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                Log.i("获取职位信息", bean.toString());
                List<AddJobTypeInfoList> addJobTypeInfoLists = bean.parseList(AddJobTypeInfoList.class);
                view.getJobTypeSuccess(addJobTypeInfoLists);
            }

            @Override
            public void requestFailed(String error) {
                Log.i("获取职位信息", error + "shibabi");
            }

            @Override
            public void requestFinish() {

            }
        });
    }
    public void getJobTime() {
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "getJobTimeList", "ucenter", true);
        info.put("user_id", AppConfig.USERID);
        info.put("token", AppConfig.TOKEN);
        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                Log.i("获取时间信息", bean.toString());
                List<AddJobTimeInfoList> addJobTimeInfoLists = bean.parseList(AddJobTimeInfoList.class);
                view.getJobTimeSuccess(addJobTimeInfoLists);
            }

            @Override
            public void requestFailed(String error) {
                Log.i("获取职位信息", error + "shibabi");
            }

            @Override
            public void requestFinish() {

            }
        });
    }
}
