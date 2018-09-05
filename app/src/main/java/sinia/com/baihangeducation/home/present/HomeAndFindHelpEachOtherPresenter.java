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
import com.mcxtzhang.swipemenulib.info.HomeAndFindHelpEachOtherListInfo;
import com.mcxtzhang.swipemenulib.info.bean.HomeAndFindHelpEachOtherAllInfo;
import sinia.com.baihangeducation.home.view.HomeAndFindHelpEachOtherView;

/**
 * Created by Administrator on 2018/4/21.
 */

public class HomeAndFindHelpEachOtherPresenter extends BasePresenter {
    private Activity activity;
    private HomeAndFindHelpEachOtherView view;


    public HomeAndFindHelpEachOtherPresenter(Activity activity, HomeAndFindHelpEachOtherView view) {
        super(activity);
        this.activity = activity;
        this.view = view;
    }

    public void getHomeAndFindHelpEachOtherData() {

        HashMap<String, Object> info = new HashMap<>();
        info.clear();
        info.put("act", "getCooperationPageInfo");
        info.put("app", "home");
        info.put("device", "2");
        //每次传
        info.put("app_version", AppConfig.API_VERSION);
        info.put("token", AppConfig.TOKEN);
        info.put("user_id", AppConfig.USERID);
        info.put("page", view.getPage());
        info.put("perpage", view.getPerpage());
        info.put("lat", view.getLocationLat());
        info.put("lng", view.getLocationLng());
        info.put("type", view.getType());

        view.showLoading();
        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                HomeAndFindHelpEachOtherListInfo datas = bean.parseObject(HomeAndFindHelpEachOtherListInfo.class);
                HomeAndFindHelpEachOtherAllInfo listDatas = datas.cooperation_list;
                int maxpag = CommonUtil.getMaxPage(listDatas.count, listDatas.perpage);
                view.getHomeAndFindHelpEachOtherDataSuccess(datas, maxpag);
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
