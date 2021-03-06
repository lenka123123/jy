package sinia.com.baihangeducation.club.club.model;

import android.app.Activity;

import com.example.framwork.base.BasePresenter;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.OnRequestListener;
import com.example.framwork.utils.Toast;

import java.util.HashMap;

import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.club.club.interfaces.GetRequestListener;
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

    public void getClubHomeInfo(String school_id, String center_flag, String other_id, final SetClubHomeListener clubHomeListener) {
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "getClubHome", "club", true);
        info.put("user_id", AppConfig.USERID);
        info.put("token", AppConfig.TOKEN);
        if (!school_id.equals("")) {
            info.put("school_id", school_id);
        }

        info.put("center_flag", "1");

        if (!other_id.equals(""))
            info.put("other_id", other_id);

    //    System.out.println("other_id==" + other_id);

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


    //设置选择学校
    public void setSelectSchool(GetRequestListener getRequestListener) {
        if (AppConfig.SCHOOLNAMEID.equals("")) return;
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "setChooseSchool", "clubUcenter", true);
        info.put("user_id", AppConfig.USERID);
        info.put("token", AppConfig.TOKEN);
        info.put("school_id", AppConfig.SCHOOLNAMEID);
        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
              //  System.out.println("设置选择学校===" + bean.getData());
                getRequestListener.setRequestSuccess("");
//                ClubHomeInfo clubSchoolList = bean.parseObject(ClubHomeInfo.class);
//                clubHomeListener.setClubHomeSuccess(clubSchoolList);
            }

            @Override
            public void requestFailed(String error) {
                Toast.getInstance().showErrorToast(activity, error);
//                clubHomeListener.setClubHomeFail(error);
                getRequestListener.setRequestFail();
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
             //   System.out.println("getDatagetData===" + bean.getData());
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


    public void applyClub(String club_id, String member_id, GetRequestListener listener) {
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "setClubApply", "club", true);
        info.put("user_id", AppConfig.USERID);
        info.put("token", AppConfig.TOKEN);
        if (!member_id.equals(""))
            info.put("member_id", member_id);
        info.put("club_id", club_id);


        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
            //    System.out.println("getDatagetData===" + bean.getData());
                Toast.getInstance().showSuccessToast(activity, "申请成功，等待审核");
                listener.setRequestSuccess("");
//                ClubHomeInfo clubSchoolList = bean.parseObject(ClubHomeInfo.class);
//                clubHomeListener.setClubHomeSuccess(clubSchoolList);
            }

            @Override
            public void requestFailed(String error) {
                Toast.getInstance().showErrorToast(activity, error);
                listener.setRequestFail();
//                clubHomeListener.setClubHomeFail(error);
            }

            @Override
            public void requestFinish() {

            }
        });

    }

}
