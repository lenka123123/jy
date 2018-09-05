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
import com.mcxtzhang.swipemenulib.info.ShareEveryDayDetailListInfo;
import sinia.com.baihangeducation.find.view.ShareEveryDayDetailView;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;

/**
 * Created by Administrator on 2018/4/19.
 */

public class ShareEveryDayDetailPresenter extends BasePresenter {
    private Activity activity;
    private ShareEveryDayDetailView view;


    public ShareEveryDayDetailPresenter(Activity activity, ShareEveryDayDetailView view) {
        super(activity);
        this.activity = activity;
        this.view = view;
    }

    public void getShareEveryDayDetailData() {

        HashMap<String, Object> mShareEveryDayDetailData = new HashMap<>();
        mShareEveryDayDetailData.clear();
        mShareEveryDayDetailData.put("act", "getNewsInfo");
        mShareEveryDayDetailData.put("app", "find");
        mShareEveryDayDetailData.put("device", "2");
        //每次传
        mShareEveryDayDetailData.put("app_version", AppConfig.API_VERSION);
        mShareEveryDayDetailData.put("token", AppConfig.TOKEN);
        mShareEveryDayDetailData.put("user_id", AppConfig.USERID);
        mShareEveryDayDetailData.put("news_id", view.getNewsId());
        post(mShareEveryDayDetailData, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                ShareEveryDayDetailListInfo info = bean.parseObject(ShareEveryDayDetailListInfo.class);
                int maxpag = CommonUtil.getMaxPage(info.comment_list.count, info.comment_list.perpage);
                view.getShareEveryDayDetailSuccess(info, maxpag);
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

        sendCommenInfo.put("token", AppConfig.TOKEN);
        sendCommenInfo.put("user_id", AppConfig.USERID);
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
