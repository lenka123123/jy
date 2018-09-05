package sinia.com.baihangeducation.mine.presenter;

import android.app.Activity;
import android.util.Log;

import com.example.framwork.base.BasePresenter;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.OnRequestListener;

import java.util.HashMap;

import com.mcxtzhang.swipemenulib.info.bean.TaskInfo;
import sinia.com.baihangeducation.mine.view.TaskView;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;

/**
 * Created by Administrator on 2018/4/16.
 */

public class TaskPresenter extends BasePresenter {
    private Activity activity;
    private TaskView view;

    public TaskPresenter(Activity activity, TaskView view) {
        super(activity);
        this.activity = activity;
        this.view = view;
    }

    public void getTaskData() {
        HashMap mTaskData = BaseRequestInfo.getInstance().getRequestInfo(activity, "getMyTaskInfo", "ucenter", true);
        post(mTaskData, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                Log.i("获得我的任务信息", bean.toString());
                TaskInfo taskInfo = bean.parseObject(TaskInfo.class);
                view.getTaskDataSuccess(taskInfo);
            }

            @Override
            public void requestFailed(String error) {

            }

            @Override
            public void requestFinish() {

            }
        });
    }
}
