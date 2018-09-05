package sinia.com.baihangeducation.reconsitution.tabs.coffers.coffersmain;

import android.app.Activity;

import com.example.framwork.base.BasePresenter;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.OnRequestListener;
import com.example.framwork.utils.Toast;

import java.util.HashMap;

import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.reconsitution.tabs.coffers.CoffersDataBean;
import sinia.com.baihangeducation.reconsitution.tabs.coffers.ObtainMyCoffersDataListener;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;

/**
 * 创建日期：2018/5/22 on 11:28
 * 描述:
 * 作者:yuxd Administrator
 */
public class RankingMainModel extends BasePresenter {


    public RankingMainModel(Activity activity) {
        super(activity);
    }

    public void getCompanyUCenterInfo(int type, int page, ObtainMyCoffersDataListener obtainRankingDataListener) {
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "getMyCapital", "ucenter", true);
        info.put("user_id", AppConfig.USERID);
        info.put("token", AppConfig.TOKEN);


        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                CoffersDataBean info = bean.parseObject(CoffersDataBean.class);
                obtainRankingDataListener.onSuccess(info);
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
