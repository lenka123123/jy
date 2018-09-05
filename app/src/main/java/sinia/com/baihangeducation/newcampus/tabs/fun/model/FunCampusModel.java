package sinia.com.baihangeducation.newcampus.tabs.fun.model;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.example.framwork.base.BasePresenter;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.OnRequestListener;
import com.example.framwork.utils.CommonUtil;
import com.example.framwork.utils.LogUtils;
import com.example.framwork.utils.Toast;
import com.example.framwork.utils.UserInfo;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.MyApplication;
import sinia.com.baihangeducation.newcampus.info.FunInfo;
import sinia.com.baihangeducation.newcampus.tabs.fun.FunCampusFragment;
import sinia.com.baihangeducation.newcampus.tabs.fun.interfaces.GetFunCampusListener;
import sinia.com.baihangeducation.newcampus.tabs.fun.interfaces.GetShowListener;
import sinia.com.baihangeducation.newcampus.view.IGetFunView;
import sinia.com.baihangeducation.supplement.base.Goto;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;

public class FunCampusModel extends BasePresenter {

    private Context activity;

    public FunCampusModel(Activity activity) {
        super(activity);
        this.activity = activity;
    }

    public void getFunInfo(String page, String perpage, final GetFunCampusListener funCampusListener) {
        HashMap info;

        if (AppConfig.ISlOGINED) {
            info = BaseRequestInfo.getInstance().getRequestInfo(activity, "getDynamicList", "school", true);
        } else {
            info = BaseRequestInfo.getInstance().getRequestInfo(activity, "getDynamicList", "school", false);
        }
        info.put("page", page);
        info.put("perpage", perpage);
        info.put("user_id", AppConfig.USERID);
        info.put("token", AppConfig.TOKEN);
        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                FunInfo funInfo = bean.parseObject(FunInfo.class);
                int maxpag = CommonUtil.getMaxPage(funInfo.count, funInfo.perpage);
                funCampusListener.SuccessInfo(funInfo, maxpag);
            }

            @Override
            public void requestFailed(String error) {
                Toast.getInstance().showErrorToast(activity, error);
            }

            @Override
            public void requestFinish() {
                funCampusListener.ErrorInfo();
            }
        });

    }


    public void setShowNum(int dynamic_id, final GetShowListener funCampusListener) {
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "addShareNum", "school", false);

        info.put("dynamic_id", dynamic_id);
//        info.put("perpage", perpage);
//        info.put("user_id", AppConfig.USERID);
//        info.put("token", AppConfig.TOKEN);
        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {

                try {
                    JSONObject jsonObject = new JSONObject(bean.getData());
                    funCampusListener.SuccessInfo(jsonObject.getInt("share_num"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

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
