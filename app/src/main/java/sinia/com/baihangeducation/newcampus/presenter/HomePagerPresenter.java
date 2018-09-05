package sinia.com.baihangeducation.newcampus.presenter;

import android.app.Activity;

import com.example.framwork.base.BasePresenter;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.OnRequestListener;
import com.example.framwork.utils.Toast;

import java.util.HashMap;

import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.newcampus.info.HomeInfoShowBean;
import sinia.com.baihangeducation.newcampus.info.HomeInfoShowTobBean;
import sinia.com.baihangeducation.newcampus.info.HomePager;
import sinia.com.baihangeducation.newcampus.info.HomePagerDetail;
import sinia.com.baihangeducation.newcampus.interfaces.AddFirendListener;
import sinia.com.baihangeducation.newcampus.interfaces.HomeDeatilPagerListener;
import sinia.com.baihangeducation.newcampus.interfaces.HomePagerListener;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;

public class HomePagerPresenter extends BasePresenter {
    private Activity activity;

    public HomePagerPresenter(Activity activity) {
        super(activity);
        this.activity = activity;
    }

    public void releaseComment(String topic_id, int page, String perpage,
                               final HomeDeatilPagerListener listener) {
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "getHomePage", "school", false);
        info.put("user_id", AppConfig.USERID);
        info.put("token", AppConfig.TOKEN);
        info.put("other_id", topic_id);
        info.put("page", page);
        info.put("perpage", perpage);
        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                HomeInfoShowBean info = bean.parseObject(HomeInfoShowBean.class);
                listener.setHomePagerSuccessListener(info);
            }

            @Override
            public void requestFailed(String error) {
                Toast.getInstance().showErrorToast(activity, error);
            }

            @Override
            public void requestFinish() {

            }
        });
    }

    public void releaseHotComment(String topic_id, int page, String perpage,
                                  final HomeDeatilPagerListener listener) {
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "getTopicInfo", "school", false);
        info.put("user_id", AppConfig.USERID);
        info.put("token", AppConfig.TOKEN);

        info.put("topic_id", topic_id);
        info.put("page", page);
        info.put("perpage", perpage);
        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                HomeInfoShowTobBean info = bean.parseObject(HomeInfoShowTobBean.class);
                listener.setHomePagerTobSuccessListener(info);
            }

            @Override
            public void requestFailed(String error) {
                Toast.getInstance().showErrorToast(activity, error);
            }

            @Override
            public void requestFinish() {

            }
        });

    }

}
