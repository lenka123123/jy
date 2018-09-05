package sinia.com.baihangeducation.home.present;

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
import com.mcxtzhang.swipemenulib.info.HomeAndFindHelpEachOtherDetailAllInfo;
import sinia.com.baihangeducation.home.view.HomeAndFindHelpEachOtherDetailView;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;

/**
 * Created by Administrator on 2018/4/27.
 */

public class HomeAndFindHelpEachOtherDetailPresenter extends BasePresenter {
    private Activity activity;
    private HomeAndFindHelpEachOtherDetailView view;


    public HomeAndFindHelpEachOtherDetailPresenter(Activity activity, HomeAndFindHelpEachOtherDetailView view) {
        super(activity);
        this.activity = activity;
        this.view = view;

    }

    public void getDetailInfo() {
        HashMap<String, Object> info = new HashMap<>();
        info.clear();
        info.put("act", "getCooperationInfo");
        info.put("app", "home");
        info.put("device", "2");
        //每次传
        info.put("app_version", AppConfig.API_VERSION);

        info.put("token", AppConfig.TOKEN);
        info.put("user_id", AppConfig.USERID);

        info.put("page", view.getPage());
        info.put("perpage", view.getPerpage());
        info.put("lat",view.getLocationLat());
        info.put("lng",view.getLocationLng());
        info.put("cooperation_id", view.getCooperationId());
        view.showLoading();
        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                HomeAndFindHelpEachOtherDetailAllInfo mHomeAndFindHelpEachOtherDetailAllInfo = bean.parseObject(HomeAndFindHelpEachOtherDetailAllInfo.class);
                int maxpag = CommonUtil.getMaxPage(mHomeAndFindHelpEachOtherDetailAllInfo.comment_list.count, mHomeAndFindHelpEachOtherDetailAllInfo.comment_list.perpage);
                view.getDetailInfoSuccess(mHomeAndFindHelpEachOtherDetailAllInfo, maxpag);
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
        sendCommenInfo.put("type_id", view.getTypeId());
        sendCommenInfo.put("content", message);
        sendCommenInfo.put("parent_id ", view.getParentId());
        sendCommenInfo.put("token", AppConfig.TOKEN);
        sendCommenInfo.put("user_id", AppConfig.USERID);
        view.showLoading();
        post(sendCommenInfo, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                view.getSendMessageSuccess();
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

    public void apply() {
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "serApplyCooperation", "home", true);
        info.put("cooperation_id", view.getCooperationId());
        info.put("token", AppConfig.TOKEN);
        info.put("user_id", AppConfig.USERID);

        view.showLoading();
        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                Toast.getInstance().showSuccessToast(activity, "申请成功");
                view.getApplySuccess();
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
