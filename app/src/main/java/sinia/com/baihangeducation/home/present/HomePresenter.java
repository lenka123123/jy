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
import java.util.List;

import sinia.com.baihangeducation.MyApplication;
import sinia.com.baihangeducation.AppConfig;

import com.mcxtzhang.swipemenulib.info.CityIdInfo;
import com.mcxtzhang.swipemenulib.info.CityListInfo;
import com.mcxtzhang.swipemenulib.info.HomeListInfo;
import com.mcxtzhang.swipemenulib.info.IsCompleteInfo;
import com.mcxtzhang.swipemenulib.info.bean.CityInfo;
import com.mcxtzhang.swipemenulib.info.bean.IndustryListInfo;
import com.mcxtzhang.swipemenulib.info.bean.IndustryListInfoHome;

import sinia.com.baihangeducation.home.view.HomeView;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;
import sinia.com.baihangeducation.supplement.base.Goto;

/**
 * Created by Administrator on 2018/4/22.
 */

public class HomePresenter extends BasePresenter {
    private Activity activity;
    private HomeView view;

    public HomePresenter(Activity activity, HomeView view) {
        super(activity);
        this.activity = activity;
        this.view = view;
    }

    public void getCityData() {
        HashMap mCityData = BaseRequestInfo.getInstance().getRequestInfo(activity, "getOpenCityList", "home", false);
        mCityData.put("adcode", view.getAdCode());
        post(mCityData, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                Log.i("获取城市数据", bean.toString());
                List<CityListInfo> mCityListInfo = bean.parseList(CityListInfo.class);

                List<CityInfo> mCityInfo = mCityListInfo.get(0).list;
                if (mCityInfo != null) {
                    view.getCityListSuccess(mCityInfo);
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


    public void getIndustry(GetIndustryListener listener) {
        HashMap mCityData = BaseRequestInfo.getInstance().getRequestInfo(activity, "getIndustry", "home", false);
        mCityData.put("user_id", AppConfig.USERID);
        mCityData.put("token", AppConfig.TOKEN);
        post(mCityData, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                Log.i("获取城市数据", bean.toString());
                List<IndustryListInfoHome> mCityListInfo = bean.parseList(IndustryListInfoHome.class);
                listener.getDataSuccess(mCityListInfo);

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


    public void getHomeData() {

        HashMap<String, Object> mCityData = new HashMap<>();
        mCityData.clear();
        mCityData.put("act", "getHomeInfo");
        mCityData.put("app", "home");
        mCityData.put("device", "2");
        //每次传
        mCityData.put("app_version", AppConfig.API_VERSION);
        if (AppConfig.ISlOGINED) {
            mCityData.put("user_id", AppConfig.USERID);
            mCityData.put("token", AppConfig.TOKEN);
        }

        mCityData.put("page", view.getPage());
        mCityData.put("perpage", view.getPerpage());

        mCityData.put("city_id", view.getCityID());
        mCityData.put("lat", view.getLocationLat());
        mCityData.put("lng", view.getLocationLng());
        post(mCityData, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                Log.i("首页信息", bean.toString());
                HomeListInfo mHomeListInfo = bean.parseObject(HomeListInfo.class);
                int maxpag = CommonUtil.getMaxPage(mHomeListInfo.job_list.count, mHomeListInfo.job_list.perpage);
                view.getHomeInfoSuccess(mHomeListInfo, maxpag);
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

    public void getCityId() {
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "getParentAdCode", "home", false);
        info.put("adcode", view.getLocationAdcode());

//        info.put("user_id",AppConfig.USERID);
//        info.put("token",AppConfig.TOKEN);
        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                CityIdInfo cityIdInfo = bean.parseObject(CityIdInfo.class);
                view.getCityIdSuccess(cityIdInfo);
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

    public void getCompleteInfo(final int position) {
        HashMap<String, Object> mInfo = new HashMap<>();

        mInfo.put("act", "isCompleteInfo");
        mInfo.put("app", "ucenter");
        mInfo.put("user_id", AppConfig.USERID);
        mInfo.put("token", AppConfig.TOKEN);
        mInfo.put("device", "2");
        //每次传
        mInfo.put("app_version", AppConfig.API_VERSION);

        post(mInfo, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                IsCompleteInfo isCompleteInfo = bean.parseObject(IsCompleteInfo.class);
                view.getCompleteInfoSuccess(isCompleteInfo, position);
            }

            @Override
            public void requestFailed(String error) {
                Goto.toLogin(activity);
            }

            @Override
            public void requestFinish() {

            }
        });
    }
}
