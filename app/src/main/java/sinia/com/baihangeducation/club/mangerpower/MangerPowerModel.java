package sinia.com.baihangeducation.club.mangerpower;

import android.app.Activity;

import com.example.framwork.base.BasePresenter;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.OnRequestListener;
import com.example.framwork.utils.Toast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.List;

import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.club.addclub.AddClubList;
import sinia.com.baihangeducation.club.addclub.GetAddOptionRequestListener;
import sinia.com.baihangeducation.club.club.interfaces.GetRequestListener;
import sinia.com.baihangeducation.club.editorclubactive.power.SettingPowerData;
import sinia.com.baihangeducation.club.editorclubactive.power.SettingPowerInfoListener;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;

/**
 * 创建日期：2018/5/22 on 11:28
 * 描述:
 * 作者:yuxd Administrator
 */
public class MangerPowerModel extends BasePresenter {

    private Activity activity;

    public MangerPowerModel(Activity activity) {
        super(activity);
        this.activity = activity;
    }

    public void getClubOption(String role_id, MangerPowerListener listener) {
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "getRoleList", "clubUcenter", true);
        info.put("user_id", AppConfig.USERID);
        info.put("token", AppConfig.TOKEN);
        info.put("role_id", role_id);


        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                List<MangerPowerList> mPermissionList = new Gson().fromJson(bean.getData(), new TypeToken<List<MangerPowerList>>() {
                }.getType());

                listener.setRequestSuccess(mPermissionList);
            }

            @Override
            public void requestFailed(String error) {
                Toast.getInstance().showErrorToast(activity, error);
                listener.setRequestFail();
            }

            @Override
            public void requestFinish() {

            }
        });

    }


    public void getActiveInfo(String member_id, SettingPowerInfoListener obtainRankingDataListener) {
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "getAppointList", "clubUcenter", true);
        info.put("user_id", AppConfig.USERID);
        info.put("token", AppConfig.TOKEN);
        info.put("club_id", member_id);//


        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                List<SettingPowerData> inof = new Gson().fromJson(bean.getData(), new TypeToken<List<SettingPowerData>>() {
                }.getType());
                obtainRankingDataListener.onSuccess(inof);
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

    public void setRole(String role_id, String permission_id, String checked, GetRequestListener getRequestListener) {
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "setRole", "clubUcenter", true);
        info.put("user_id", AppConfig.USERID);
        info.put("token", AppConfig.TOKEN);
        info.put("role_id", role_id);//
        info.put("permission_id", permission_id);//
        info.put("checked", checked);//


        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                getRequestListener.setRequestSuccess("");
//

            }

            @Override
            public void requestFailed(String error) {
                Toast.getInstance().showErrorToast(activity, error);
                getRequestListener.setRequestFail();
            }

            @Override
            public void requestFinish() {

            }
        });
    }


}
