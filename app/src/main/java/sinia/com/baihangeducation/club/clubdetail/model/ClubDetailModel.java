package sinia.com.baihangeducation.club.clubdetail.model;

import android.app.Activity;

import com.example.framwork.base.BasePresenter;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.OnRequestListener;
import com.example.framwork.utils.CommonUtil;
import com.example.framwork.utils.Toast;

import java.util.HashMap;

import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.club.club.interfaces.GetRequestListener;
import sinia.com.baihangeducation.club.clubdetail.interfaces.ClubDetailListener;
import sinia.com.baihangeducation.club.clubdetail.interfaces.JoinClubDetailListener;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;

/**
 * 创建日期：2018/5/22 on 11:28
 * 描述:
 * 作者:yuxd Administrator
 */
public class ClubDetailModel extends BasePresenter {

    private Activity activity;

    public ClubDetailModel(Activity activity) {
        super(activity);
        this.activity = activity;

    }

    public void getClubListList(String page, String perpage, String club_id,
                                final ClubDetailListener setSchoolListListener) {
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "getClubInfo", "club", true);

        info.put("user_id", AppConfig.USERID);
        info.put("token", AppConfig.TOKEN);
        info.put("club_id", club_id);
        info.put("page", page);
        info.put("perpage", perpage);
        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {

                ClubDetailBean clubSchoolList = bean.parseObject(ClubDetailBean.class);


                setSchoolListListener.setSchookList(clubSchoolList);

            }

            @Override
            public void requestFailed(String error) {
                Toast.getInstance().showErrorToast(activity, error);
                setSchoolListListener.setSchookListFail(error);

            }

            @Override
            public void requestFinish() {

            }
        });

    }


    public void joinClub(String club_id, final JoinClubDetailListener joinClubDetailListener) {
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "setClubApply", "club", true);

        info.put("user_id", AppConfig.USERID);
        info.put("token", AppConfig.TOKEN);
        info.put("club_id", club_id);

        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                Toast.getInstance().showSuccessToast(activity, "申请成功");
                joinClubDetailListener.setSchookList("加入成功");

            }

            @Override
            public void requestFailed(String error) {
                Toast.getInstance().showErrorToast(activity, error);
                joinClubDetailListener.setSchookListFail(error);

            }

            @Override
            public void requestFinish() {

            }
        });

    }


    public void dropClub(String club_id, final JoinClubDetailListener joinClubDetailListener) {
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "dropClub", "club", true);

        info.put("user_id", AppConfig.USERID);
        info.put("token", AppConfig.TOKEN);
        info.put("club_id", club_id);

        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                Toast.getInstance().showSuccessToast(activity, "退出成功");
                joinClubDetailListener.setSchookList("退出成功");

            }

            @Override
            public void requestFailed(String error) {
                Toast.getInstance().showErrorToast(activity, error);
                joinClubDetailListener.setSchookListFail(error);

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
}
