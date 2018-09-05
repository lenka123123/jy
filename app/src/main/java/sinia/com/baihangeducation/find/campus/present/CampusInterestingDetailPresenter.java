package sinia.com.baihangeducation.find.campus.present;

import android.app.Activity;
import android.util.Log;

import com.example.framwork.base.BasePresenter;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.OnRequestListener;
import com.example.framwork.utils.CommonUtil;
import com.example.framwork.utils.Toast;
import com.example.framwork.utils.UserInfo;

import java.util.HashMap;

import sinia.com.baihangeducation.MyApplication;
import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.find.campus.info.CampusInterestingDetailListInfo;
import sinia.com.baihangeducation.find.campus.view.ICampusInterestingDetailView;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;

public class CampusInterestingDetailPresenter extends BasePresenter {
    private Activity activity;
    private ICampusInterestingDetailView view;


    public CampusInterestingDetailPresenter(Activity activity, ICampusInterestingDetailView view) {
        super(activity);
        this.activity = activity;
        this.view = view;
    }

    public void getFunDetail(){

        HashMap<String, Object> mShareEveryDayDetailData = new HashMap<>();
        mShareEveryDayDetailData.clear();
        mShareEveryDayDetailData.put("act", "getFunInfo");
        mShareEveryDayDetailData.put("app", "school");
        mShareEveryDayDetailData.put("device", "2");
        //每次传
        mShareEveryDayDetailData.put("app_version", AppConfig.API_VERSION);
        mShareEveryDayDetailData.put("token", AppConfig.TOKEN);
        mShareEveryDayDetailData.put("user_id", AppConfig.USERID);
        Log.i("趣事详情信息",view.getFunId()+"这里是ID");
        mShareEveryDayDetailData.put("fun_id", view.getFunId());
        post(mShareEveryDayDetailData, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                Log.i("趣事详情信息",bean.toString());
                CampusInterestingDetailListInfo info = bean.parseObject(CampusInterestingDetailListInfo.class);
                int maxpag = CommonUtil.getMaxPage(info.comment_list.count, info.comment_list.perpage);
                view.getFunSuccess(info);
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
        sendCommenInfo.put("type_id", view.getFunId());
        sendCommenInfo.put("content", message);
        sendCommenInfo.put("parent_id ", view.getParentCommentId());
        sendCommenInfo.put("user_id", AppConfig.USERID);
        sendCommenInfo.put("token", AppConfig.TOKEN);

        view.showLoading();
        post(sendCommenInfo, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                Log.i("趣事留言",bean.toString());
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
