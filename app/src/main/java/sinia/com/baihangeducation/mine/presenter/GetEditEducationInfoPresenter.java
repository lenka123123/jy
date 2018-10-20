package sinia.com.baihangeducation.mine.presenter;

import android.app.Activity;
import android.util.Log;

import com.example.framwork.base.BasePresenter;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.OnRequestListener;
import com.example.framwork.utils.CommonUtil;

import java.util.HashMap;
import java.util.List;

import com.mcxtzhang.swipemenulib.info.bean.College;
import com.mcxtzhang.swipemenulib.info.bean.CollegeInfo;
import com.mcxtzhang.swipemenulib.info.bean.EducationInfo;
import com.mcxtzhang.swipemenulib.info.bean.Marjor;
import com.mcxtzhang.swipemenulib.info.bean.MarjorInfo;

import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.mine.view.IGetEducationView;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;

/**
 * Created by Administrator on 2018/3/30.
 */

public class GetEditEducationInfoPresenter extends BasePresenter {
    private Activity activity;
    private IGetEducationView view;

    public GetEditEducationInfoPresenter(Activity activity, IGetEducationView view) {
        super(activity);
        this.activity = activity;
        this.view = view;
    }


    /**
     * 选择学历
     */
    public void getEducation() {
        HashMap educationinfo = BaseRequestInfo.getInstance().getRequestInfo(activity, "getEduList", "ucenter", true);
        educationinfo.put("user_id", AppConfig.USERID);
        educationinfo.put("token", AppConfig.TOKEN);
        post(educationinfo, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                List<EducationInfo> educationInfos = bean.parseList(EducationInfo.class);
                view.getEducationSuccess(educationInfos);
            }

            @Override
            public void requestFailed(String error) {

            }

            @Override
            public void requestFinish() {

            }
        });
    }

    /**
     * 选择大学
     */
    public void getEducationChoiceSchool() {
        HashMap choiceSchoolInfo = BaseRequestInfo.getInstance().getRequestInfo(activity, "getCollegeList", "ucenter", true);
        choiceSchoolInfo.put("page", view.getPage());
        choiceSchoolInfo.put("user_id", AppConfig.USERID);
        choiceSchoolInfo.put("token", AppConfig.TOKEN);
        choiceSchoolInfo.put("perpage", view.getItenmNum());

        post(choiceSchoolInfo, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                CollegeInfo collegeInfo = bean.parseObject(CollegeInfo.class);
                List<College> list = bean.parseObject(CollegeInfo.class).list;
                int maxpag = CommonUtil.getMaxPage(collegeInfo.count, collegeInfo.perpage);
                view.getEducationSchoolSuccess(list, maxpag);
            }

            @Override
            public void requestFailed(String error) {

            }

            @Override
            public void requestFinish() {
                view.hideLoading();
            }
        });
    }


    /**
     * 选择大学  club
     */
    public void getEducationChoiceSchoolClub() {
        HashMap choiceSchoolInfo = BaseRequestInfo.getInstance().getRequestInfo(activity, "getSchoolList", "club", true);
        choiceSchoolInfo.put("page", view.getPage());
        choiceSchoolInfo.put("user_id", AppConfig.USERID);
        choiceSchoolInfo.put("token", AppConfig.TOKEN);
        choiceSchoolInfo.put("perpage", view.getItenmNum());

        post(choiceSchoolInfo, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                CollegeInfo collegeInfo = bean.parseObject(CollegeInfo.class);
                List<College> list = bean.parseObject(CollegeInfo.class).list;
                int maxpag = CommonUtil.getMaxPage(collegeInfo.count, collegeInfo.perpage);
                view.getEducationSchoolSuccess(list, maxpag);
            }

            @Override
            public void requestFailed(String error) {

            }

            @Override
            public void requestFinish() {
                view.hideLoading();
            }
        });
    }


    /**
     * 选择 专业
     */
    public void getEducationChoiceSchoolByKeyword(String keyword) {
        HashMap choiceSchoolInfo = BaseRequestInfo.getInstance().getRequestInfo(activity, "getCollegeList", "ucenter", true);
        choiceSchoolInfo.put("page", view.getPage());
        choiceSchoolInfo.put("perpage", view.getItenmNum());
        choiceSchoolInfo.put("keyword", keyword);
        choiceSchoolInfo.put("user_id", AppConfig.USERID);
        choiceSchoolInfo.put("token", AppConfig.TOKEN);

        post(choiceSchoolInfo, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                Log.i("选择学校", bean.toString());
                CollegeInfo collegeInfo = bean.parseObject(CollegeInfo.class);
                List<College> list = bean.parseObject(CollegeInfo.class).list;
                int maxpag = CommonUtil.getMaxPage(collegeInfo.count, collegeInfo.perpage);
                view.getEducationSchoolSuccess(list, maxpag);
            }

            @Override
            public void requestFailed(String error) {

            }

            @Override
            public void requestFinish() {
                view.hideLoading();
            }
        });
    }


    /**
     * 选择专业
     */
    public void getMajor() {
        HashMap majorInfo = BaseRequestInfo.getInstance().getRequestInfo(activity, "getMajorList", "ucenter", true);
        majorInfo.put("pid", view.getFatherId());
        majorInfo.put("page", view.getPage());
        majorInfo.put("perpage", view.getItenmNum());
        majorInfo.put("user_id", AppConfig.USERID);
        majorInfo.put("token", AppConfig.TOKEN);
        post(majorInfo, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                Log.i("专业", bean.toString());
                MarjorInfo marjorInfo = bean.parseObject(MarjorInfo.class);
                List<Marjor> list = bean.parseObject(MarjorInfo.class).list;
                int maxpag = CommonUtil.getMaxPage(marjorInfo.count, marjorInfo.perpage);
                view.getEducationMajor_1_Success(list, maxpag);
            }

            @Override
            public void requestFailed(String error) {

            }

            @Override
            public void requestFinish() {
                view.hideLoading();
            }
        });
    }

}
