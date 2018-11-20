package sinia.com.baihangeducation.club.clubpaint;

import android.app.Activity;
import android.util.Log;

import com.example.framwork.base.BasePresenter;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.OnRequestListener;
import com.example.framwork.utils.CommonUtil;
import com.example.framwork.utils.Toast;
import com.mcxtzhang.swipemenulib.info.ClubPartTimeListInfo;
import com.mcxtzhang.swipemenulib.info.HomePartTimeListInfo;
import com.mcxtzhang.swipemenulib.info.HomePartTimeSearchListInfo;
import com.mcxtzhang.swipemenulib.info.bean.ClubPartTimeSearchListInfo;

import java.util.HashMap;

import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.home.view.HomePartTimeView;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;

/**
 * Created by Administrator on 2018/4/24.
 */

public class ClubPartTimePresenter extends BasePresenter {
    private Activity activity;
    private ClubPaintActivity view;


    public ClubPartTimePresenter(Activity activity, ClubPaintActivity view) {
        super(activity);
        this.activity = activity;
        this.view = view;
    }

    public void getPartTimeSeachList() {
        HashMap mSeachWordInfo = BaseRequestInfo.getInstance().getRequestInfo(activity, "getJobOption", "club", false);
        mSeachWordInfo.put("city_id", AppConfig.CTYLEID);
//        mSeachWordInfo.put("type", view.getType());
//        mSeachWordInfo.put("user_id", AppConfig.USERID);
//        mSeachWordInfo.put("token", AppConfig.TOKEN);

        post(mSeachWordInfo, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                view.hideLoading();
                ClubPartTimeSearchListInfo mHomePartTimeSearchListInfo = bean.parseObject(ClubPartTimeSearchListInfo.class);
                view.getPartTimeSeachSuccess(mHomePartTimeSearchListInfo);
            }

            @Override
            public void requestFailed(String error) {
                view.hideLoading();
                Toast.getInstance().showErrorToast(activity, error);
            }

            @Override
            public void requestFinish() {
                view.hideLoading();
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

        mGetPartTimeDataInfo.put("city_id", AppConfig.CTYLEID);
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
                ClubPartTimeListInfo mHomePartTimeListInfo = bean.parseObject(ClubPartTimeListInfo.class);
                int maxpag = CommonUtil.getMaxPage(mHomePartTimeListInfo.count, mHomePartTimeListInfo.perpage);
                view.getPartTimeData(mHomePartTimeListInfo.list, maxpag);
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


    public void getPartTimeData(String industryId, String money_id, String areaId, String worktime_id, String distance_id, String sex_id, String pubtime_id) {

        HashMap<String, Object> mGetPartTimeDataInfo = new HashMap<>();
        mGetPartTimeDataInfo.clear();
        mGetPartTimeDataInfo.put("act", "getJobList");
        mGetPartTimeDataInfo.put("app", "club");

        mGetPartTimeDataInfo.put("city_id", AppConfig.CTYLEID);
        if (!industryId.equals(""))
            mGetPartTimeDataInfo.put("industry_id", industryId);
        if (!money_id.equals(""))
            mGetPartTimeDataInfo.put("money_id", money_id);
        if (!areaId.equals(""))
            mGetPartTimeDataInfo.put("dist_id", areaId);
        if (!pubtime_id.equals(""))
            mGetPartTimeDataInfo.put("pubtime_id", pubtime_id);
        if (!sex_id.equals(""))
            mGetPartTimeDataInfo.put("sex_id", sex_id);
        if (!worktime_id.equals(""))
            mGetPartTimeDataInfo.put("worktime_id", worktime_id);
        if (!distance_id.equals(""))
            mGetPartTimeDataInfo.put("distance_id", distance_id);
        mGetPartTimeDataInfo.put("page", view.getPager());
        mGetPartTimeDataInfo.put("perpage", view.getPerpage());


//        mGetPartTimeDataInfo.put("device", "2");
//        //每次传
//        mGetPartTimeDataInfo.put("app_version", AppConfig.API_VERSION);
//
//        mGetPartTimeDataInfo.put("token", AppConfig.TOKEN);
//        mGetPartTimeDataInfo.put("user_id", AppConfig.USERID);
//        mGetPartTimeDataInfo.put("lat", view.getLocationLat());
//        mGetPartTimeDataInfo.put("lng", view.getLocationLng());
//        mGetPartTimeDataInfo.put("type", view.getType());


        post(mGetPartTimeDataInfo, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                view.hideLoading();
                ClubPartTimeListInfo mHomePartTimeListInfo = bean.parseObject(ClubPartTimeListInfo.class);
                int maxpag = CommonUtil.getMaxPage(mHomePartTimeListInfo.count, mHomePartTimeListInfo.perpage);
                view.getPartTimeData(mHomePartTimeListInfo.list, maxpag);

//                HomePartTimeListInfo mHomePartTimeListInfo = bean.parseObject(HomePartTimeListInfo.class);
//                int maxpag = CommonUtil.getMaxPage(mHomePartTimeListInfo.count, mHomePartTimeListInfo.perpage);
//                view.getPartTimeDataSuccess(mHomePartTimeListInfo.list, maxpag);
            }

            @Override
            public void requestFailed(String error) {
                view.hideLoading();
                Toast.getInstance().showErrorToast(activity, error);
            }

            @Override
            public void requestFinish() {
                view.hideLoading();
            }
        });
    }
}
