package sinia.com.baihangeducation.mine.presenter;

import android.app.Activity;
import android.util.Log;

import com.example.framwork.base.BasePresenter;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.OnRequestListener;
import com.example.framwork.utils.CommonUtil;
import com.example.framwork.utils.Toast;

import java.util.HashMap;

import com.mcxtzhang.swipemenulib.info.bean.AlreadyGetTaskListInfo;
import sinia.com.baihangeducation.mine.view.AlreadyGetTaskView;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;

/**
 * Created by Administrator on 2018/4/16.
 */

public class AlreadyGetTaskPresenter extends BasePresenter {

    private Activity activity;
    private AlreadyGetTaskView view;

    public AlreadyGetTaskPresenter(Activity activity, AlreadyGetTaskView view) {
        super(activity);
        this.activity = activity;
        this.view = view;
    }

    public AlreadyGetTaskPresenter(Activity activity) {
        super(activity);
        this.activity = activity;
    }

    public void getAlreadyGetTaskData() {
        HashMap mAlreadyGetTaskData = BaseRequestInfo.getInstance().getRequestInfo(activity, "getMyJobTaskList", "ucenter", true);
        mAlreadyGetTaskData.put("tab", view.getTab());
        mAlreadyGetTaskData.put("page", view.getPage());
        mAlreadyGetTaskData.put("perpage", view.getPerpage());

        post(mAlreadyGetTaskData, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                Log.i("获得我的任务数据", bean.toString());
                AlreadyGetTaskListInfo mAlreadyGetTaskListInfo = bean.parseObject(AlreadyGetTaskListInfo.class);
                int maxpag = CommonUtil.getMaxPage(mAlreadyGetTaskListInfo.count, mAlreadyGetTaskListInfo.perpage);
                view.getUnCompleteSuccess(mAlreadyGetTaskListInfo, maxpag);
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

    public void setTaskComplete(String id) {
        HashMap mTaskCompleteData = BaseRequestInfo.getInstance().getRequestInfo(activity, "setJobTaskFinish", "ucenter", true);
        mTaskCompleteData.put("job_apply_id", id);
        post(mTaskCompleteData, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                Log.i("完成任务", bean.toString());
                Toast.getInstance().showSuccessToast(activity, "操作成功");
                view.doRefresh();
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
