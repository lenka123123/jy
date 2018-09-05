package sinia.com.baihangeducation.mine.presenter;

import android.app.Activity;
import android.util.Log;

import com.example.framwork.base.BasePresenter;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.OnRequestListener;
import com.example.framwork.utils.Toast;

import java.util.HashMap;

import com.mcxtzhang.swipemenulib.info.bean.FindApplyPersonListInfo;
import sinia.com.baihangeducation.mine.view.GetApplyMyReleaseView;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;

/**
 * Created by Administrator on 2018/4/10.
 */

public class GetApplyMyReleasePersent extends BasePresenter {
    private Activity activity;
    private GetApplyMyReleaseView view;

    public GetApplyMyReleasePersent(Activity activity, GetApplyMyReleaseView view) {
        super(activity);
        this.activity = activity;
        this.view = view;
    }

    public void getApplyList() {
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "getCooperChooseList", "ucenter", true);
        info.put("cooperation_id", view.getCooperationId());
        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                Log.i("申请列表", bean.toString());
                FindApplyPersonListInfo info = bean.parseObject(FindApplyPersonListInfo.class);
                view.getSuccess(info);
            }

            @Override
            public void requestFailed(String error) {

            }

            @Override
            public void requestFinish() {

            }
        });
    }

    public void confirmPerson() {
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "setPushFinish", "ucenter", true);
        info.put("cooperation_id", view.getCooperationId());
        info.put("apply_user_id", view.getApplyUserId());
        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                Log.i("确认完成", bean.toString());
                Toast.getInstance().showSuccessToast(activity, "成功");
                view.getConfirmSuccess();
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
