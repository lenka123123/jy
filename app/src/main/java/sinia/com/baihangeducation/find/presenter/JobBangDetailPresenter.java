package sinia.com.baihangeducation.find.presenter;

import android.app.Activity;

import com.example.framwork.base.BasePresenter;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.OnRequestListener;
import com.example.framwork.utils.CommonUtil;
import com.example.framwork.utils.Toast;
import com.example.framwork.utils.UserInfo;

import java.util.HashMap;

import sinia.com.baihangeducation.MyApplication;
import sinia.com.baihangeducation.AppConfig;
import com.mcxtzhang.swipemenulib.info.JobBangDetailListInfo;
import sinia.com.baihangeducation.find.view.ShareEveryDayDetailView;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;

/**
 * Created by Administrator on 2018/4/19.
 */

public class JobBangDetailPresenter extends BasePresenter {
    private Activity activity;
    private ShareEveryDayDetailView view;



    public JobBangDetailPresenter(Activity activity, ShareEveryDayDetailView view) {
        super(activity);
        this.activity = activity;
        this.view = view;
    }

    public void getJobBangDetailData() {

        HashMap<String, Object> mShareEveryDayDetailData = new HashMap<>();
        mShareEveryDayDetailData.clear();
        mShareEveryDayDetailData.put("act", "getRaidersInfo");
        mShareEveryDayDetailData.put("app", "find");
        mShareEveryDayDetailData.put("device", "2");
        //每次传
        mShareEveryDayDetailData.put("app_version", AppConfig.API_VERSION);
        mShareEveryDayDetailData.put("token", AppConfig.TOKEN);
        mShareEveryDayDetailData.put("user_id", AppConfig.USERID);
        mShareEveryDayDetailData.put("raiders_id", view.getNewsId());
        post(mShareEveryDayDetailData, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                JobBangDetailListInfo info = bean.parseObject(JobBangDetailListInfo.class);
                int maxpag = CommonUtil.getMaxPage(info.comment_list.count, info.comment_list.perpage);
                view.getJobBangDetailSuccess(info, maxpag);
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

    public void sendCommen(String message) {

        HashMap sendCommenInfo = BaseRequestInfo.getInstance().getRequestInfo(activity, "setComment", "home", true);
        sendCommenInfo.put("type", view.getType());
        sendCommenInfo.put("type_id", view.getNewsId());
        sendCommenInfo.put("content", message);
        sendCommenInfo.put("user_id", AppConfig.USERID);
        sendCommenInfo.put("token", AppConfig.TOKEN);
        sendCommenInfo.put("parent_id ", view.getParentCommentId());
        view.showLoading();
        post(sendCommenInfo, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                view.getShareEveryDaySendCommenSuccess();
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
