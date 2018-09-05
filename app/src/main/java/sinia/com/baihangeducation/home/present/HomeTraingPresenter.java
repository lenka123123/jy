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
import com.mcxtzhang.swipemenulib.info.HomeTraingDataListInfo;
import com.mcxtzhang.swipemenulib.info.HomeTraingSeachListInfo;
import sinia.com.baihangeducation.home.view.HomeTraingView;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;

/**
 * Created by Administrator on 2018/4/24.
 */

public class HomeTraingPresenter extends BasePresenter {
    private Activity activity;
    private HomeTraingView view;


    public HomeTraingPresenter(Activity activity, HomeTraingView view) {
        super(activity);
        this.activity = activity;
        this.view = view;
    }

    public void getSeachList() {
        HashMap mSeachListInfo = BaseRequestInfo.getInstance().getRequestInfo(activity, "getConditionForTrain", "home", false);
        view.showLoading();
        post(mSeachListInfo, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                HomeTraingSeachListInfo mHomeTraingSeachListInfo = bean.parseObject(HomeTraingSeachListInfo.class);
                view.getSeachListSuccess(mHomeTraingSeachListInfo);
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

    public void getTraingData() {


        HashMap<String, Object> mGetTraingDataInfo = new HashMap<>();
        mGetTraingDataInfo.clear();
        mGetTraingDataInfo.put("act", "getTrainList");
        mGetTraingDataInfo.put("app", "home");
        mGetTraingDataInfo.put("device", "2");
        //每次传
        mGetTraingDataInfo.put("app_version", AppConfig.API_VERSION);


        mGetTraingDataInfo.put("token", AppConfig.TOKEN);
        mGetTraingDataInfo.put("user_id", AppConfig.USERID);

        mGetTraingDataInfo.put("page", view.getPage());
        mGetTraingDataInfo.put("perpage", view.getPerpage());
        mGetTraingDataInfo.put("industry_id", view.getIndustryId());
        mGetTraingDataInfo.put("level_id", view.getLevelId());
        mGetTraingDataInfo.put("cycle_id", view.getWeekId());
        mGetTraingDataInfo.put("order_id", view.getOrderId());
        view.showLoading();
        post(mGetTraingDataInfo, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                HomeTraingDataListInfo mHomeTraingDataListInfo = bean.parseObject(HomeTraingDataListInfo.class);
                int maxpag = CommonUtil.getMaxPage(mHomeTraingDataListInfo.count, mHomeTraingDataListInfo.perpage);
                view.getTraingInfoSuccess(mHomeTraingDataListInfo, maxpag);
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
