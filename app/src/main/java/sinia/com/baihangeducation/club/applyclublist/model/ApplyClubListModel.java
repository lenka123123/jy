package sinia.com.baihangeducation.club.applyclublist.model;

import android.app.Activity;

import com.example.framwork.base.BasePresenter;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.OnRequestListener;
import com.example.framwork.utils.CommonUtil;
import com.example.framwork.utils.Toast;

import java.util.HashMap;

import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.club.applyclublist.interfaces.ApplyClubListListener;
import sinia.com.baihangeducation.club.applyclublist.interfaces.GetPersonListener;
import sinia.com.baihangeducation.club.clubdetail.interfaces.JoinClubDetailListener;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;

/**
 * 创建日期：2018/5/22 on 11:28
 * 描述:
 * 作者:yuxd Administrator
 */
public class ApplyClubListModel extends BasePresenter {

    private Activity activity;

    public ApplyClubListModel(Activity activity) {
        super(activity);
        this.activity = activity;

    }

    public void getClubListList(String page, String perpage, String club_id,
                                final ApplyClubListListener setSchoolListListener) {
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "getApplyList", "club", true);

        info.put("user_id", AppConfig.USERID);
        info.put("token", AppConfig.TOKEN);
        info.put("club_id", club_id);
        info.put("page", page);
        info.put("perpage", perpage);
        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                ApplyClubListBean clubSchoolList = bean.parseObject(ApplyClubListBean.class);
                int maxpag = CommonUtil.getMaxPage(clubSchoolList.count, clubSchoolList.perpage);

                setSchoolListListener.setSchookList(clubSchoolList, maxpag);

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


    public void joinClub(String club_id, String member_id, final JoinClubDetailListener joinClubDetailListener) {
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "setClubApply", "club", true);

        info.put("user_id", AppConfig.USERID);
        info.put("token", AppConfig.TOKEN);
        info.put("club_id", club_id);
        info.put("member_id", member_id);

        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {

                joinClubDetailListener.setSchookList("");

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


    public void getPersonList(String club_id, String page, String perpage, final GetPersonListener joinClubDetailListener) {
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "getMemberList", "club", true);

        info.put("user_id", AppConfig.USERID);
        info.put("token", AppConfig.TOKEN);
        info.put("club_id", club_id);
        info.put("page", page);
        info.put("perpage", perpage);

        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                GetPersonList getPersonList = bean.parseObject(GetPersonList.class);
                int maxpag = CommonUtil.getMaxPage(getPersonList.count, getPersonList.perpage);

                joinClubDetailListener.showGetPersonSuccess(getPersonList, maxpag);

            }

            @Override
            public void requestFailed(String error) {
                Toast.getInstance().showErrorToast(activity, error);
                joinClubDetailListener.showGetPersonFail(error);

            }

            @Override
            public void requestFinish() {

            }
        });

    }
}
