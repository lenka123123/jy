package sinia.com.baihangeducation.club.editorclubactive.power;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.example.framwork.base.BasePresenter;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.OnRequestListener;
import com.example.framwork.utils.Toast;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.mcxtzhang.swipemenulib.customview.BitmapUtil;
import com.yanzhenjie.nohttp.rest.Request;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.club.club.interfaces.GetRequestListener;
import sinia.com.baihangeducation.club.editorclubactive.model.ActiveInfoData;
import sinia.com.baihangeducation.club.editorclubactive.model.GetActiveOptionListener;
import sinia.com.baihangeducation.club.editorclubactive.model.GetClubActiveOption;
import sinia.com.baihangeducation.club.editorclubactive.model.ObtainActiveInfoListener;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;

/**
 * 创建日期：2018/5/22 on 11:28
 * 描述:
 * 作者:yuxd Administrator
 */
public class SettingPowerModel extends BasePresenter {


    public SettingPowerModel(Activity activity) {
        super(activity);
    }

    public void getActiveInfo(String member_id, SettingPowerInfoListener obtainRankingDataListener) {
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "getAppointList", "clubUcenter", true);
        info.put("user_id", AppConfig.USERID);
        info.put("token", AppConfig.TOKEN);
        info.put("member_id", member_id);//


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


    public void setAppoint(String member_id, String role_id, GetRequestListener obtainRankingDataListener) {
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "setAppoint", "clubUcenter", true);
        info.put("user_id", AppConfig.USERID);
        info.put("token", AppConfig.TOKEN);
        info.put("member_id", member_id);//
        info.put("role_id", role_id);//


        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {

                obtainRankingDataListener.setRequestSuccess("");

                Toast.getInstance().showSuccessToast(activity, "任命成功");

            }

            @Override
            public void requestFailed(String error) {
                Toast.getInstance().showErrorToast(activity, error);
                obtainRankingDataListener.setRequestFail();
            }

            @Override
            public void requestFinish() {

            }
        });
    }


}
