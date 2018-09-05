package sinia.com.baihangeducation.mine.presenter;

import android.app.Activity;
import android.util.Log;

import com.example.framwork.base.BasePresenter;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.OnRequestListener;
import com.example.framwork.utils.SPUtils;

import java.util.HashMap;

import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.mine.activity.MyResumeActivity;
import sinia.com.baihangeducation.mine.model.AccountManger;

import com.google.gson.Gson;
import com.mcxtzhang.swipemenulib.info.bean.MyResumInfo;

import sinia.com.baihangeducation.mine.view.IGetMyResumeView;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;

import com.mcxtzhang.swipemenulib.info.bean.MyResumInfoforResume;
import com.mcxtzhang.swipemenulib.utils.Constants;

/**
 * Created by Administrator on 2018/3/30.
 */

public class GetMyResumePresenter extends BasePresenter {
    private Activity activity;
    private IGetMyResumeView view;
    private MyResumeActivity mMyResumeActivity;

    public GetMyResumePresenter(Activity activity, IGetMyResumeView view, MyResumeActivity mMyResumeActivity) {
        super(activity);
        this.activity = activity;
        this.view = view;
        this.mMyResumeActivity = mMyResumeActivity;
    }

    public void getMyResume() {
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "getMyResumeInfo", "ucenter", true);

        info.put("token", AppConfig.TOKEN);
        info.put("user_id", AppConfig.USERID);
        view.showLoading();
        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
//                HomeInfoShowBean info = bean.parseObject(HomeInfoShowBean.class);
                MyResumInfoforResume jsonObject =bean.parseObject(MyResumInfoforResume.class);  // gs.fromJson(bean.getData(), MyResumInfoforResume.class);//把JSON字符串转为对象
                mMyResumeActivity.getMyResumeSuccess(jsonObject);
//                resumeSuccessBack(bean);
            }

            @Override
            public void requestFailed(String error) {
                Log.i("操作结果", "失败" + error);
            }

            @Override
            public void requestFinish() {
                view.hideLoading();
            }
        });
    }

    /**
     * 获取的用户简历信息存入缓存中
     *
     * @param bean
     */
    private void resumeSuccessBack(BaseResponseBean bean) {
        SPUtils.getInstance().saveObject(activity, Constants.USER_RESUMEINFO, bean.parseObject(MyResumInfo.class));
        SPUtils.getInstance().saveObject(activity, Constants.USER_RESUMEINFOCOPY, bean.parseObject(MyResumInfo.class));

        AccountManger.getUserResumeInfo(activity);
        AccountManger.getUserResumeInfoCopy(activity);
    }
}
