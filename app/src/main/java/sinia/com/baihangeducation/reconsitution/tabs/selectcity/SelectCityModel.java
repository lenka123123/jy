package sinia.com.baihangeducation.reconsitution.tabs.selectcity;

import android.app.Activity;
import android.util.Log;

import com.example.framwork.base.BasePresenter;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.OnRequestListener;
import com.example.framwork.utils.Toast;
import com.mcxtzhang.swipemenulib.info.CityListInfo;
import com.mcxtzhang.swipemenulib.info.bean.CityInfo;

import java.util.HashMap;
import java.util.List;

import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.reconsitution.tabs.coffers.ObtainMyCoffersDataListener;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;

/**
 * 创建日期：2018/5/22 on 11:28
 * 描述:
 * 作者:yuxd Administrator
 */
public class SelectCityModel extends BasePresenter {

    private SelectCityActivity view;

    public SelectCityModel(SelectCityActivity activity) {
        super(activity);
        this.view=activity;
    }

    public void getCityData() {
        HashMap mCityData = BaseRequestInfo.getInstance().getRequestInfo(activity, "getOpenCityList", "home", false);
        mCityData.put("adcode", view.getAdCode());
        post(mCityData, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {

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
}
