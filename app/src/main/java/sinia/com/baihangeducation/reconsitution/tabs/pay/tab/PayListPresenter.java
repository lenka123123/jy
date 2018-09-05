package sinia.com.baihangeducation.reconsitution.tabs.pay.tab;

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
import sinia.com.baihangeducation.find.campus.info.CampusListInfo;
import sinia.com.baihangeducation.reconsitution.tabs.pay.PayActivity;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;

public class PayListPresenter extends BasePresenter {

    private PaiedListActivity view;

    public PayListPresenter(Activity activity, PaiedListActivity view) {
        super(activity);
        this.activity = activity;
        this.view = view;
    }

    public void getPayList(int page,int perpage) {

        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "getMyBuy", "ucenter", true);
        info.put("user_id", AppConfig.USERID);
        info.put("token", AppConfig.TOKEN);
        info.put("page", page);
        info.put("perpage",perpage);


        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                PayDetail mInfo = bean.parseObject(PayDetail.class);
                view.setPayListSuccess(mInfo);
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
