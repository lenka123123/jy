package sinia.com.baihangeducation.mine.presenter;

import android.app.Activity;
import android.util.Log;

import com.example.framwork.base.BasePresenter;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.OnRequestListener;
import com.example.framwork.utils.CommonUtil;
import com.example.framwork.utils.Toast;

import java.util.HashMap;

import com.mcxtzhang.swipemenulib.info.bean.NewTaskListInfo;
import sinia.com.baihangeducation.mine.view.NewTaskView;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;

/**
 * Created by Administrator on 2018/4/16.
 */

public class NewTaskPresenter extends BasePresenter {
    private Activity activity;
    private NewTaskView view;

    public NewTaskPresenter(Activity activity, NewTaskView view) {
        super(activity);
        this.activity = activity;
        this.view = view;
    }
    public NewTaskPresenter(Activity activity) {
        super(activity);
        this.activity = activity;
    }

    public void getNewTaskData() {
        HashMap mNewTaskData = BaseRequestInfo.getInstance().getRequestInfo(activity, "getJobTaskList", "ucenter", true);
        mNewTaskData.put("page", view.getPage());
        mNewTaskData.put("perpage", view.getPerpage());
        post(mNewTaskData, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                Log.i("获得可领取任务", bean.toString());
                NewTaskListInfo mNewTaskListInfo = bean.parseObject(NewTaskListInfo.class);
                int maxpag = CommonUtil.getMaxPage(mNewTaskListInfo.count, mNewTaskListInfo.perpage);
                view.getNewTaskSuccess(mNewTaskListInfo.list, maxpag);
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

    public void getNewTask(String task_id) {
        HashMap mGetNewTask = BaseRequestInfo.getInstance().getRequestInfo(activity, "setNewTask", "ucenter", true);
        mGetNewTask.put("task_id", task_id);
        post(mGetNewTask, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                Log.i("领取任务结果", bean.toString());
                Toast.getInstance().showSuccessToast(activity,"领取成功");
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
}
