package sinia.com.baihangeducation.reconsitution.tabs.pay;

import android.app.Activity;
import android.util.Log;

import com.example.framwork.base.BasePresenter;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.OnRequestListener;
import com.example.framwork.utils.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;

public class PayPresenter extends BasePresenter {

    private PayActivity view;

    public PayPresenter(Activity activity, PayActivity view) {
        super(activity);
        this.activity = activity;
        this.view = view;
    }

    public void updateCompanyInfo(String type, String dataId, String payType) {

        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "charge", "payment", true);
        info.put("user_id", AppConfig.USERID);
        info.put("token", AppConfig.TOKEN);
        info.put("type", type);
        info.put("payType", payType);
        info.put("dataId", dataId);


        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                Log.i("支付=====", bean.getData().toString());
                view.setSuccessInfo(bean.getData().toString());
//                try {
//                    JSONObject jsonObject = new JSONObject(bean.getData().toString());
//                    String link = (String) jsonObject.get("link");
//                    view.setSuccessInfo(link);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
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



    public void getAliPayInfo(String type, String dataId, String payType) {

        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "charge", "payment", true);
        info.put("user_id", AppConfig.USERID);
        info.put("token", AppConfig.TOKEN);
        info.put("type", type);
        info.put("payType", payType);
        info.put("dataId", dataId);


        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                try {
                    JSONObject jsonObject = new JSONObject(bean.getData().toString());
                    String link = (String) jsonObject.get("link");
                    view.setAliPaySuccessInfo(link);
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
