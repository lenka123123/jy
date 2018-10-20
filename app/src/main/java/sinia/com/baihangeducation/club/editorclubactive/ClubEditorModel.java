package sinia.com.baihangeducation.club.editorclubactive;

import android.app.Activity;

import com.example.framwork.base.BasePresenter;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.OnRequestListener;
import com.example.framwork.utils.CommonUtil;
import com.example.framwork.utils.Toast;

import java.util.HashMap;

import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.club.club.interfaces.GetRequestListener;
import sinia.com.baihangeducation.club.clubactive.model.ActiveListData;
import sinia.com.baihangeducation.club.clubactive.model.ObtainActiveDataListener;
import sinia.com.baihangeducation.club.editorclubactive.model.ActiveInfoData;
import sinia.com.baihangeducation.club.editorclubactive.model.ObtainActiveInfoListener;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;

/**
 * 创建日期：2018/5/22 on 11:28
 * 描述:
 * 作者:yuxd Administrator
 */
public class ClubEditorModel extends BasePresenter {


    public ClubEditorModel(Activity activity) {
        super(activity);
    }

    public void getActiveInfo(String activity_id, ObtainActiveInfoListener obtainRankingDataListener) {
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "getActivityInfo", "club", true);
        info.put("user_id", AppConfig.USERID);
        info.put("token", AppConfig.TOKEN);
        info.put("activity_id", activity_id);//


        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                ActiveInfoData info = bean.parseObject(ActiveInfoData.class);
                obtainRankingDataListener.onSuccess(info, 0);
//

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


    public void applyActive(String activity_id, GetRequestListener requestListener) {
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "setActivityApply", "club", true);
        info.put("user_id", AppConfig.USERID);
        info.put("token", AppConfig.TOKEN);
        info.put("activity_id", activity_id);//


        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                Toast.getInstance().showSuccessToast(activity, "申请成功");
                requestListener.setRequestSuccess("");
            }

            @Override
            public void requestFailed(String error) {
                Toast.getInstance().showErrorToast(activity, error);
                requestListener.setRequestFail();
            }

            @Override
            public void requestFinish() {

            }
        });
    }
}
