package sinia.com.baihangeducation.find.campus.present;

import android.app.Activity;

import com.example.framwork.base.BasePresenter;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.OnRequestListener;
import com.example.framwork.utils.Toast;
import com.example.framwork.utils.UserInfo;
import com.umeng.socialize.utils.Log;

import java.util.HashMap;

import sinia.com.baihangeducation.MyApplication;
import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.find.campus.info.CampusInterestingInfo;
import sinia.com.baihangeducation.find.campus.info.CampusListInfo;
import sinia.com.baihangeducation.find.campus.view.CampusView;
import com.mcxtzhang.swipemenulib.info.HomePartTimeListInfo;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;

/**
 * Created by Administrator on 2018/4/27.
 */

public class CampusPresenter extends BasePresenter {
    private Activity activity;
    private CampusView view;



    public CampusPresenter(Activity activity, CampusView view) {
        super(activity);
        this.activity = activity;
        this.view = view;
    }

    public void getCampusInfo() {
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "getSchoolPageInfo", "school", false);
        view.showLoading();
        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                Log.i("校园信息", bean.toString());
                CampusListInfo mInfo = bean.parseObject(CampusListInfo.class);
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

    /**
     * 获取校园页 职业信息
     */
    public void getCampusJobInfo() {
        //每次调用改方法获取一次用户信息

        HashMap<String, Object> mGetPartTimeDataInfo = new HashMap<>();
        mGetPartTimeDataInfo.clear();
        mGetPartTimeDataInfo.put("act", "getJobList");
        mGetPartTimeDataInfo.put("app", "home");
        mGetPartTimeDataInfo.put("device", "2");
        //每次传
        mGetPartTimeDataInfo.put("app_version", AppConfig.API_VERSION);

        //判断用户信息是否为空，若不为空，参数增加userid和token
        mGetPartTimeDataInfo.put("token", AppConfig.TOKEN);
        mGetPartTimeDataInfo.put("user_id", AppConfig.USERID);


        mGetPartTimeDataInfo.put("page", view.getCampusJobPager());
        mGetPartTimeDataInfo.put("perpage", view.getPerpage());
        mGetPartTimeDataInfo.put("lat", view.getLocationLat());
        mGetPartTimeDataInfo.put("lng", view.getLocationLng());
        mGetPartTimeDataInfo.put("cycle_id", view.getCityId());
        mGetPartTimeDataInfo.put("type", view.getType());
        view.showLoading();
        post(mGetPartTimeDataInfo, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                HomePartTimeListInfo mHomePartTimeListInfo = bean.parseObject(HomePartTimeListInfo.class);
                int maxpage = mHomePartTimeListInfo.count;
                view.getCampusJobInfoSuccess(mHomePartTimeListInfo.list, maxpage);
            }

            @Override
            public void requestFailed(String error) {

            }

            @Override
            public void requestFinish() {
                view.hideLoading();
            }
        });
    }


    /**
     * 获取校园页面 趣事信息
     */
    public void getCampusInteringInfo() {

        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "getFunList", "school", false);
        info.put("page", view.getCampusInterestingPager());
        info.put("perpage", view.getPerpage());
        info.put("token", AppConfig.TOKEN);
        info.put("user_id", AppConfig.USERID);
        view.showLoading();
        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                Log.i("趣事列表",bean.toString());
                CampusInterestingInfo mCampusInterestingInfo = bean.parseObject(CampusInterestingInfo.class);
                view.getCampusJobInterestingSuccess(mCampusInterestingInfo.list, mCampusInterestingInfo.count);
            }

            @Override
            public void requestFailed(String error) {
                Log.i("趣事列表",error+"错误");
                Toast.getInstance().showErrorToast(activity, error);
            }

            @Override
            public void requestFinish() {
                view.hideLoading();
            }
        });
    }
}
