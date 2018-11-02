package sinia.com.baihangeducation.club.systemmessage;

import android.app.Activity;

import com.example.framwork.base.BasePresenter;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.OnRequestListener;
import com.example.framwork.utils.CommonUtil;
import com.example.framwork.utils.Toast;

import java.util.HashMap;

import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.club.club.interfaces.GetRequestListener;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;

/**
 * 创建日期：2018/5/22 on 11:28
 * 描述:
 * 作者:yuxd Administrator
 */
public class ClubSystemModel extends BasePresenter {

    private Activity activity;

    public ClubSystemModel(Activity activity) {
        super(activity);
        this.activity = activity;

    }


    public void getSystemMsgList(GetSystemMessageRequestListener getRequestListener) {
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "getSystemMsg", "clubUcenter", true);
        info.put("user_id", AppConfig.USERID);
        info.put("token", AppConfig.TOKEN);
        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                ClubMessageList clubSchoolList = bean.parseObject(ClubMessageList.class);
                int maxpag = CommonUtil.getMaxPage(clubSchoolList.count, clubSchoolList.perpage);
                getRequestListener.setRequestSuccess(clubSchoolList, maxpag);
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


    public void setApplyCheck(String check_type, String category_id, String system_message_id, String is_pass, GetRequestListener getRequestListener) {
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "setApplyCheck", "club", true);
        info.put("user_id", AppConfig.USERID);
        info.put("token", AppConfig.TOKEN);


        info.put("check_type", check_type);
        info.put("category_id", category_id);
        info.put("system_message_id", system_message_id);
        info.put("is_pass", is_pass);
        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                Toast.getInstance().showSuccessToast(activity, "处理成功");
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
