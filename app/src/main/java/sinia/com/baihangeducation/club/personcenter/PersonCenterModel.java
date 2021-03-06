package sinia.com.baihangeducation.club.personcenter;

import android.app.Activity;

import com.example.framwork.base.BasePresenter;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.OnRequestListener;
import com.example.framwork.utils.Toast;

import java.util.HashMap;

import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.club.addclub.AddClubList;
import sinia.com.baihangeducation.club.addclub.GetAddOptionRequestListener;
import sinia.com.baihangeducation.club.club.interfaces.GetRequestListener;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;

/**
 * 创建日期：2018/5/22 on 11:28
 * 描述:
 * 作者:yuxd Administrator
 */
public class PersonCenterModel extends BasePresenter {

    private Activity activity;

    public PersonCenterModel(Activity activity) {
        super(activity);
        this.activity = activity;

    }

    public void getClubOption(GetAddOptionRequestListener getAddOptionRequestListener) {
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "pushClubOption", "club", true);
        info.put("user_id", AppConfig.USERID);
        info.put("token", AppConfig.TOKEN);


        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {

                AddClubList addClubList = bean.parseObject(AddClubList.class);
                getAddOptionRequestListener.setRequestSuccess(addClubList);
            }

            @Override
            public void requestFailed(String error) {
                Toast.getInstance().showErrorToast(activity, error);
                getAddOptionRequestListener.setRequestFail();
//                clubHomeListener.setClubHomeFail(error);
            }

            @Override
            public void requestFinish() {

            }
        });

    }


    public void pushClub(String real_name, String real_mobile, String clubname,
                         String type, String school_id, String introduce, String logo,
                         GetRequestListener getRequestListener) {
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "pushClub", "club", true);
        info.put("user_id", AppConfig.USERID);
        info.put("token", AppConfig.TOKEN);

        info.put("real_name", real_name);
        info.put("real_mobile", real_mobile);
        info.put("name", clubname);
        info.put("type", type);
        info.put("school_id", school_id);
        info.put("introduce", introduce);
        info.put("logo", logo);


        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                Toast.getInstance().showSuccessToast(activity, "创建社团成功");
                getRequestListener.setRequestSuccess("");

            }

            @Override
            public void requestFailed(String error) {
                Toast.getInstance().showErrorToast(activity, error);
                getRequestListener.setRequestFail();
//                clubHomeListener.setClubHomeFail(error);
            }

            @Override
            public void requestFinish() {

            }
        });

    }
}
