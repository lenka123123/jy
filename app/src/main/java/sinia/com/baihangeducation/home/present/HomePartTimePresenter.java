package sinia.com.baihangeducation.home.present;

import android.app.Activity;
import android.util.Log;

import com.example.framwork.base.BasePresenter;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.OnRequestListener;
import com.example.framwork.utils.CommonUtil;
import com.example.framwork.utils.Toast;
import com.example.framwork.utils.UserInfo;

import java.util.HashMap;

import sinia.com.baihangeducation.MyApplication;
import sinia.com.baihangeducation.AppConfig;

import com.mcxtzhang.swipemenulib.info.HomePartTimeListInfo;
import com.mcxtzhang.swipemenulib.info.HomePartTimeSearchListInfo;
import com.mcxtzhang.swipemenulib.utils.Constants;

import sinia.com.baihangeducation.home.view.HomePartTimeView;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;

/**
 * Created by Administrator on 2018/4/24.
 */

public class HomePartTimePresenter extends BasePresenter {
    private Activity activity;
    private HomePartTimeView view;


    public HomePartTimePresenter(Activity activity, HomePartTimeView view) {
        super(activity);
        this.activity = activity;
        this.view = view;
    }

    public void getPartTimeSeachList() {
        HashMap mSeachWordInfo = BaseRequestInfo.getInstance().getRequestInfo(activity, "getConditionForJob", "home", false);
        mSeachWordInfo.put("city_id",  AppConfig.CTYLEID);
        mSeachWordInfo.put("type", view.getType());
        mSeachWordInfo.put("user_id", AppConfig.USERID);
        mSeachWordInfo.put("token", AppConfig.TOKEN);
        view.showLoading();
        post(mSeachWordInfo, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                HomePartTimeSearchListInfo mHomePartTimeSearchListInfo = bean.parseObject(HomePartTimeSearchListInfo.class);
                view.getPartTimeSeachSuccess(mHomePartTimeSearchListInfo);
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

    public void getPartTimeData() {
        HashMap<String, Object> mGetPartTimeDataInfo = new HashMap<>();
        mGetPartTimeDataInfo.clear();
        mGetPartTimeDataInfo.put("act", "getJobList");
        mGetPartTimeDataInfo.put("app", "home");
        mGetPartTimeDataInfo.put("device", "2");
        //每次传
        mGetPartTimeDataInfo.put("app_version", AppConfig.API_VERSION);

        mGetPartTimeDataInfo.put("token", AppConfig.TOKEN);
        mGetPartTimeDataInfo.put("user_id", AppConfig.USERID);
//        System.out.println(AppConfig.TOKEN+" 传递=================最后一次传token");
        mGetPartTimeDataInfo.put("page", view.getPager());
        mGetPartTimeDataInfo.put("perpage", view.getPerpage());
        mGetPartTimeDataInfo.put("lat", view.getLocationLat());
        mGetPartTimeDataInfo.put("lng", view.getLocationLng());

           mGetPartTimeDataInfo.put("city_id",  AppConfig.CTYLEID);
        mGetPartTimeDataInfo.put("type", view.getType());
        mGetPartTimeDataInfo.put("industry_id ", view.getIndustryId());
        mGetPartTimeDataInfo.put("money_id", view.getMoneyId());
        mGetPartTimeDataInfo.put("dist_id", view.getDistId());
        mGetPartTimeDataInfo.put("order_id", view.getOrderId());
        Log.i("职业筛选条件", view.getIndustryId() + "行业参数");
        Log.i("职业筛选条件", view.getMoneyId() + "薪资参数");
        Log.i("职业筛选条件", view.getDistId() + "地区参数");
        Log.i("职业筛选条件", view.getOrderId() + "排序参数");

        view.showLoading();
        post(mGetPartTimeDataInfo, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                HomePartTimeListInfo mHomePartTimeListInfo = bean.parseObject(HomePartTimeListInfo.class);
                int maxpag = CommonUtil.getMaxPage(mHomePartTimeListInfo.count, mHomePartTimeListInfo.perpage);
                view.getPartTimeDataSuccess(mHomePartTimeListInfo.list, maxpag);
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


    public void getPartTimeData(String industryId, String money_id, String areaId, String worktime_id,
                                String distance_id, String sex_id, String pubtime_id) {


        HashMap<String, Object> mGetPartTimeDataInfo = new HashMap<>();
        mGetPartTimeDataInfo.clear();
        mGetPartTimeDataInfo.put("act", "getJobList");
        mGetPartTimeDataInfo.put("app", "home");
        mGetPartTimeDataInfo.put("device", "2");
        //每次传
        mGetPartTimeDataInfo.put("app_version", AppConfig.API_VERSION);

        mGetPartTimeDataInfo.put("token", AppConfig.TOKEN);
        mGetPartTimeDataInfo.put("user_id", AppConfig.USERID);

        mGetPartTimeDataInfo.put("page", view.getPager());
        mGetPartTimeDataInfo.put("perpage", view.getPerpage());

        /**
         mHomePartTimePresenter.getPartTimeData(indutryId, money_id, areaId, worktime_id, distance_id, sex_id, pubtime_id);
         * industryId,行业   money_id,日结 areaId,地址  worktime_id周末,distance_id一公里,sex_id,pubtime_id 三天内
         */
        mGetPartTimeDataInfo.put("lat", view.getLocationLat());
        mGetPartTimeDataInfo.put("lng", view.getLocationLng());

        mGetPartTimeDataInfo.put("city_id", AppConfig.CTYLEID);
        mGetPartTimeDataInfo.put("type", view.getType());
        mGetPartTimeDataInfo.put("industry_id", industryId);
        mGetPartTimeDataInfo.put("money_id", money_id);
        mGetPartTimeDataInfo.put("dist_id", areaId);
        mGetPartTimeDataInfo.put("pubtime_id", pubtime_id);
        mGetPartTimeDataInfo.put("sex_id", sex_id);
        mGetPartTimeDataInfo.put("worktime_id", worktime_id);
        mGetPartTimeDataInfo.put("distance_id", distance_id);
        view.showLoading();
        post(mGetPartTimeDataInfo, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                HomePartTimeListInfo mHomePartTimeListInfo = bean.parseObject(HomePartTimeListInfo.class);
                int maxpag = CommonUtil.getMaxPage(mHomePartTimeListInfo.count, mHomePartTimeListInfo.perpage);
                view.getPartTimeDataSuccess(mHomePartTimeListInfo.list, maxpag);
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
