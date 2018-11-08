package sinia.com.baihangeducation.club;

import android.app.Activity;
import android.util.Log;
import android.widget.TextView;

import com.example.framwork.base.BasePresenter;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.OnRequestListener;
import com.example.framwork.utils.Toast;
import com.yanzhenjie.nohttp.rest.Request;

import java.io.File;
import java.util.HashMap;

import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.club.club.interfaces.GetRequestListener;
import sinia.com.baihangeducation.club.club.interfaces.GetSearchRequestListener;
import sinia.com.baihangeducation.club.clubdetail.interfaces.ClubDetailListener;
import sinia.com.baihangeducation.club.clubdetail.interfaces.JoinClubDetailListener;
import sinia.com.baihangeducation.club.clubdetail.model.ClubDetailBean;
import sinia.com.baihangeducation.find.info.bean.SearchRecommend;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;

/**
 * 创建日期：2018/5/22 on 11:28
 * 描述:
 * 作者:yuxd Administrator
 */
public class ClubPermissModel extends BasePresenter {

    private Activity activity;

    public ClubPermissModel(Activity activity) {
        super(activity);
        this.activity = activity;

    }

    // 获取搜索推荐
    public void getRecommend(  GetSearchRequestListener getRequestListener) {
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "getRecommend", "club", true);
        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                System.out.println("getDatagetData===" + bean.getData());
                SearchRecommend mInfo = bean.parseObject(SearchRecommend.class);
                getRequestListener.setRequestSuccess(mInfo);

//                ClubHomeInfo clubSchoolList = bean.parseObject(ClubHomeInfo.class);
//                clubHomeListener.setClubHomeSuccess(clubSchoolList);
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

    public void getClubPermission(String club_id, GetRequestListener getRequestListener) {
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "getPermission", "club", true);
        info.put("user_id", AppConfig.USERID);
        info.put("token", AppConfig.TOKEN);
        if (!club_id.equals("")) {
            info.put("club_id", club_id);
        }

        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                System.out.println("getDatagetData===" + bean.getData());
                getRequestListener.setRequestSuccess(bean.getData().toString());

//                ClubHomeInfo clubSchoolList = bean.parseObject(ClubHomeInfo.class);
//                clubHomeListener.setClubHomeSuccess(clubSchoolList);
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

    public void getSystemHome(GetRequestListener getRequestListener) {
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "getSystemHome", "clubUcenter", true);
        info.put("user_id", AppConfig.USERID);
        info.put("token", AppConfig.TOKEN);
        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                System.out.println("getDatagetData===" + bean.getData());
                getRequestListener.setRequestSuccess(bean.getData().toString());

//                ClubHomeInfo clubSchoolList = bean.parseObject(ClubHomeInfo.class);
//                clubHomeListener.setClubHomeSuccess(clubSchoolList);
            }

            @Override
            public void requestFailed(String error) {
                getRequestListener.setRequestFail();
//                clubHomeListener.setClubHomeFail(error);
            }

            @Override
            public void requestFinish() {

            }
        });

    }

    public void checkText(String text, GetRequestListener getRequestListener) {
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "textCensor", "censor", true);
        info.put("user_id", AppConfig.USERID);
        info.put("token", AppConfig.TOKEN);
        info.put("text", text);
        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {

                getRequestListener.setRequestSuccess("");

//                ClubHomeInfo clubSchoolList = bean.parseObject(ClubHomeInfo.class);
//                clubHomeListener.setClubHomeSuccess(clubSchoolList);
            }

            @Override
            public void requestFailed(String error) {
                Toast.getInstance().showErrorToast(activity, "内容审核不通过");
                getRequestListener.setRequestFail();
//                clubHomeListener.setClubHomeFail(error);
            }

            @Override
            public void requestFinish() {
            }
        });
    }

    public void checkImg(File file, GetRequestListener getRequestListener) {
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "imageCensor", "censor", true);
        info.put("user_id", AppConfig.USERID);
        info.put("token", AppConfig.TOKEN);
        Request<String> request = postFile(info);
        request.add("img", file);
        model.execute(activity, request, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {

                getRequestListener.setRequestSuccess("");
            }

            @Override
            public void requestFailed(String error) {
                Toast.getInstance().showErrorToast(activity, "内容审核不通过");
                getRequestListener.setRequestFail();
            }

            @Override
            public void requestFinish() {

            }
        });

    }


    public void setMessageRead(String message_id) {
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "setMessageRead", "ucenter", true);
        info.put("user_id", AppConfig.USERID);
        info.put("token", AppConfig.TOKEN);
        info.put("system_message_id", message_id);

        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {

            }

            @Override
            public void requestFailed(String error) {

            }

            @Override
            public void requestFinish() {

            }
        });
    }
}
