package sinia.com.baihangeducation.mine.presenter;

import android.app.Activity;
import android.util.Log;

import com.example.framwork.base.BasePresenter;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.OnRequestListener;
import com.example.framwork.utils.CommonUtil;
import com.example.framwork.utils.Toast;

import java.util.HashMap;

import com.mcxtzhang.swipemenulib.info.bean.MySendListInfo;

import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.mine.view.MySendView;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;

/**
 * Created by Administrator on 2018/4/13.
 */

public class MySendPresent extends BasePresenter {

    private Activity activity;
    private MySendView view;

    public MySendPresent(Activity activity, MySendView view) {
        super(activity);
        this.activity = activity;
        this.view = view;
    }

    public void getMySendPartTimeData() {
        HashMap mMySendData = BaseRequestInfo.getInstance().getRequestInfo(activity, "getMyJobApplyList", "ucenter", true);
        mMySendData.put("user_id", AppConfig.USERID);
        mMySendData.put("token", AppConfig.TOKEN);
        mMySendData.put("type", view.getType());
        mMySendData.put("page", view.getPage());
        mMySendData.put("perpage", view.getPerpage());
        view.showLoading();
        post(mMySendData, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                Log.i("我的投递兼职列表", bean.toString());
                MySendListInfo mMySendListInfo = bean.parseObject(MySendListInfo.class);
                int maxpag = CommonUtil.getMaxPage(mMySendListInfo.count, mMySendListInfo.perpage);
                view.getMySendPartTimeDataSuccess(mMySendListInfo, maxpag);
            }

            @Override
            public void requestFailed(String error) {
                Toast.getInstance().showErrorToast(activity, error);
            }

            @Override
            public void requestFinish() {
                view.hideLoading();
            }
        });
    }

    public void getMySendAllTimeData() {
        HashMap mMySendData = BaseRequestInfo.getInstance().getRequestInfo(activity, "getMyJobApplyList", "ucenter", true);
        mMySendData.put("user_id", AppConfig.USERID);
        mMySendData.put("token", AppConfig.TOKEN);
        mMySendData.put("type", view.getType());
        mMySendData.put("page", view.getPage());
        mMySendData.put("perpage", view.getPerpage());
        view.showLoading();
        post(mMySendData, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                Log.i("我的投递全职列表", bean.toString());
                MySendListInfo mMySendListInfo = bean.parseObject(MySendListInfo.class);
                int maxpag = CommonUtil.getMaxPage(mMySendListInfo.count, mMySendListInfo.perpage);
                view.getMySendAllTimeDataSuccess(mMySendListInfo, maxpag);
            }

            @Override
            public void requestFailed(String error) {
                Toast.getInstance().showErrorToast(activity, error);
            }

            @Override
            public void requestFinish() {
                view.hideLoading();
            }
        });
    }
}
