package sinia.com.baihangeducation.club.club.presenter;

import android.app.Activity;
import android.util.Log;

import com.example.framwork.base.BasePresenter;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.OnRequestListener;
import com.example.framwork.utils.CommonUtil;
import com.example.framwork.utils.Toast;
import com.mcxtzhang.swipemenulib.info.CityIdInfo;
import com.mcxtzhang.swipemenulib.info.CityListInfo;
import com.mcxtzhang.swipemenulib.info.HomeListInfo;
import com.mcxtzhang.swipemenulib.info.IsCompleteInfo;
import com.mcxtzhang.swipemenulib.info.bean.CityInfo;
import com.mcxtzhang.swipemenulib.info.bean.IndustryListInfoHome;

import java.util.HashMap;
import java.util.List;

import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.club.ClubFragment;
import sinia.com.baihangeducation.home.present.GetIndustryListener;
import sinia.com.baihangeducation.home.view.HomeView;
import sinia.com.baihangeducation.supplement.base.Goto;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;

/**
 * Created by Administrator on 2018/4/22.
 */

public class ClubPresenter extends BasePresenter {
    private Activity activity;
    private ClubFragment view;

    public ClubPresenter(Activity activity, ClubFragment view) {
        super(activity);
        this.activity = activity;
        this.view = view;
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
