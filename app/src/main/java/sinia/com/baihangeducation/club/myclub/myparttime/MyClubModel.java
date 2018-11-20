package sinia.com.baihangeducation.club.myclub.myparttime;

import android.app.Activity;
import android.util.Log;

import com.example.framwork.base.BasePresenter;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.OnRequestListener;
import com.example.framwork.utils.CommonUtil;
import com.example.framwork.utils.Toast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mcxtzhang.swipemenulib.info.ClubPartTimeListInfo;
import com.mcxtzhang.swipemenulib.info.HomeListInfo;
import com.mcxtzhang.swipemenulib.info.HomePartTimeListInfo;

import java.util.HashMap;
import java.util.List;

import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.club.club.interfaces.GetRequestListener;
import sinia.com.baihangeducation.club.clubactive.model.ActiveListData;
import sinia.com.baihangeducation.club.clubschoollist.model.ClubSchoolList;
import sinia.com.baihangeducation.club.myclub.help.GetHelpListener;
import sinia.com.baihangeducation.club.myclub.help.MyHelplList;
import sinia.com.baihangeducation.club.myclub.myactive.GetMyActiveListener;
import sinia.com.baihangeducation.club.myclub.myclub.GetMyClubListener;
import sinia.com.baihangeducation.club.myclub.myclub.MyClubSchoolList;
import sinia.com.baihangeducation.club.myparttimeapplylist.ClubCreateData;
import sinia.com.baihangeducation.club.myparttimeapplylist.GetApplyPersonListener;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;

/**
 * 创建日期：2018/5/22 on 11:28
 * 描述:
 * 作者:yuxd Administrator
 */
public class MyClubModel extends BasePresenter {

    private Activity activity;

    public MyClubModel(Activity activity) {
        super(activity);
        this.activity = activity;

    }


    public void getMyClubPartTime(String other_id, String page, String perpage, GetMyPartTiemListener getMyPartTiemListener) {

        HashMap<String, Object> mCityData = new HashMap<>();
        mCityData.put("act", "getMyJob");
        mCityData.put("app", "clubUcenter");

        mCityData.put("user_id", AppConfig.USERID);
        mCityData.put("token", AppConfig.TOKEN);
        if (!other_id.equals(""))
            mCityData.put("other_id ", other_id);
        mCityData.put("page", page);
        mCityData.put("perpage", perpage);
        post(mCityData, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                Log.i("首页信息", bean.toString());
                ClubPartTimeListInfo mHomePartTimeListInfo = bean.parseObject(ClubPartTimeListInfo.class);
                int maxpag = CommonUtil.getMaxPage(mHomePartTimeListInfo.count, mHomePartTimeListInfo.perpage);
                getMyPartTiemListener.onSuccess(mHomePartTimeListInfo, maxpag);

            }

            @Override
            public void requestFailed(String error) {
                Toast.getInstance().showErrorToast(activity, error);
            }

            @Override
            public void requestFinish() {
                getMyPartTiemListener.onError("");
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

    // 我加入、创建的社团
    public void getMyClubSchool(String other_id, String type, GetMyClubListener getRequestListener) {
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "getMyClub", "clubUcenter", true);
        info.put("user_id", AppConfig.USERID);
        info.put("token", AppConfig.TOKEN);
        info.put("type", type);
        if (!other_id.equals(""))
            info.put("other_id", other_id);


        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
//                List<ClubSchoolList.School> mPermissionList = new Gson().fromJson(bean.getData(), new TypeToken<List<ClubSchoolList.School>>() {
//                }.getType());
                MyClubSchoolList clubSchoolList = bean.parseObject(MyClubSchoolList.class);
                getRequestListener.onSuccess(clubSchoolList.list, 0);
//                clubHomeListener.setClubHomeSuccess(clubSchoolList);
            }

            @Override
            public void requestFailed(String error) {
                Toast.getInstance().showErrorToast(activity, error);
                getRequestListener.onError("");
//                clubHomeListener.setClubHomeFail(error);
            }

            @Override
            public void requestFinish() {

            }
        });

    }


    // 我加入、创建的社团
    public void getMyActivity(String other_id, String type, String page, String perpage, GetMyActiveListener getMyActiveListener) {
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "getMyActivity", "clubUcenter", true);
        info.put("user_id", AppConfig.USERID);
        info.put("token", AppConfig.TOKEN);
        info.put("type", type);
        info.put("page", page);
        info.put("perpage", perpage);
        if (!other_id.equals(""))
            info.put("other_id", other_id);

        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                ActiveListData activeListData = bean.parseObject(ActiveListData.class);
                int maxpag = CommonUtil.getMaxPage(activeListData.count, activeListData.perpage);
                getMyActiveListener.onSuccess(activeListData, maxpag);
            }

            @Override
            public void requestFailed(String error) {
                Toast.getInstance().showErrorToast(activity, error);
                getMyActiveListener.onError("");
            }

            @Override
            public void requestFinish() {

            }
        });

    }


    //  申请赞助
    public void setSupport(String name, String mobile, String title, String club_id,
                           String money, String requirement, String reason,
                           GetRequestListener getRequestListener) {
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "setSupport", "clubSupport", true);
        info.put("user_id", AppConfig.USERID);
        info.put("token", AppConfig.TOKEN);

        info.put("name", name);
        info.put("mobile", mobile);
        info.put("title", title);
        info.put("club_id", club_id);
        info.put("money", money);
        info.put("requirement", requirement);
        info.put("reason", reason);

        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                getRequestListener.setRequestSuccess("");

            }

            @Override
            public void requestFailed(String error) {
                Toast.getInstance().showErrorToast(activity, error);
                getRequestListener.setRequestFail();
            }

            @Override
            public void requestFinish() {
            }
        });
    }

    //  赞助列表
    public void getSupportList(String other_id, String status, String page, String perpage, GetHelpListener getHelpListener) {
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "getSupportList", "clubSupport", true);
        info.put("user_id", AppConfig.USERID);
        info.put("token", AppConfig.TOKEN);
//审核状态    ( 1：待审核 2：通过审核 3：审核失败；23：已审核（含通过、不通过） )
        info.put("status", status);
        info.put("page", page);
        info.put("perpage", perpage);
        if (!other_id.equals(""))
            info.put("other_id", other_id);


        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                MyHelplList myHelplList = bean.parseObject(MyHelplList.class);
                int maxpag = CommonUtil.getMaxPage(myHelplList.count, myHelplList.perpage);
                getHelpListener.onSuccess(myHelplList, maxpag);

            }

            @Override
            public void requestFailed(String error) {
                Toast.getInstance().showErrorToast(activity, error);
                getHelpListener.onError("");
            }

            @Override
            public void requestFinish() {

            }
        });

    }


    // 我创建的兼职申请列表
    public void getMyCreatePartTimeApplyList(String job_id, String type, int page, String perpage, GetApplyPersonListener getMyActiveListener) {
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "getJobApplyList", "clubUcenter", true);
        info.put("user_id", AppConfig.USERID);
        info.put("token", AppConfig.TOKEN);

//        类型 1：待处理 2：已通过 3：已拒绝    ( 必传 )
        info.put("type", type);
        info.put("job_id", job_id);

        info.put("page", page);
        info.put("perpage", perpage);


        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                ClubCreateData activeListData = bean.parseObject(ClubCreateData.class);
                int maxpag = CommonUtil.getMaxPage(activeListData.count, activeListData.perpage);
                getMyActiveListener.onSuccess(activeListData, maxpag);
            }

            @Override
            public void requestFailed(String error) {
                Toast.getInstance().showErrorToast(activity, error);
                getMyActiveListener.onError("");
            }

            @Override
            public void requestFinish() {

            }
        });

    }


    // 我创建的兼职申请列表
    public void dealWithApply(String apply_id, String is_pass, GetRequestListener listener) {
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "setJobApply", "clubUcenter", true);
        info.put("user_id", AppConfig.USERID);
        info.put("token", AppConfig.TOKEN);

//      1：同意 2：拒绝    ( 必传 )
        info.put("apply_id", apply_id);
        info.put("is_pass", is_pass);


        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                Toast.getInstance().showSuccessToast(activity, "处理成功");
                listener.setRequestSuccess("");
            }

            @Override
            public void requestFailed(String error) {
                Toast.getInstance().showErrorToast(activity, error);
                listener.setRequestFail();
            }

            @Override
            public void requestFinish() {

            }
        });

    }


    // 我创建的兼职申请列表
    public void dealWithJob(String job_id, GetRequestListener listener) {
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "dropMyJob", "clubUcenter", true);
        info.put("user_id", AppConfig.USERID);
        info.put("token", AppConfig.TOKEN);

//      1：同意 2：拒绝    ( 必传 )
        info.put("job_id", job_id);


        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                Toast.getInstance().showSuccessToast(activity, "删除成功");
                listener.setRequestSuccess("");
            }

            @Override
            public void requestFailed(String error) {
                Toast.getInstance().showErrorToast(activity, error);
                listener.setRequestFail();
            }

            @Override
            public void requestFinish() {

            }
        });

    }


}
