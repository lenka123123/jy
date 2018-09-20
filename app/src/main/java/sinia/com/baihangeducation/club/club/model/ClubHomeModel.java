package sinia.com.baihangeducation.club.club.model;

import android.app.Activity;

import com.example.framwork.base.BasePresenter;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.OnRequestListener;
import com.example.framwork.utils.Toast;

import java.util.HashMap;

import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.club.club.interfaces.SetClubHomeListener;
import sinia.com.baihangeducation.newcampus.interfaces.AddFirendListener;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;

/**
 * 创建日期：2018/5/22 on 11:28
 * 描述:
 * 作者:yuxd Administrator
 */
public class ClubHomeModel extends BasePresenter {
    private Activity activity;

    public ClubHomeModel(Activity activity) {
        super(activity);
        this.activity = activity;
    }

    public void getClubHomeInfo(final SetClubHomeListener clubHomeListener) {
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "getClubHome", "club", true);
        info.put("user_id", AppConfig.USERID);
        info.put("token", AppConfig.TOKEN);
        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {

                ClubHomeInfo clubSchoolList = bean.parseObject(ClubHomeInfo.class);


                clubHomeListener.setClubHomeSuccess(clubSchoolList);
            }

            @Override
            public void requestFailed(String error) {
                Toast.getInstance().showErrorToast(activity, error);
                clubHomeListener.setClubHomeFail(error);
            }

            @Override
            public void requestFinish() {

            }
        });

    }
}
