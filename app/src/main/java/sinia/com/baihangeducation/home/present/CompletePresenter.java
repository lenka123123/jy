package sinia.com.baihangeducation.home.present;

import android.app.Activity;
import android.util.Log;

import com.example.framwork.base.BasePresenter;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.OnRequestListener;
import com.example.framwork.utils.UserInfo;

import java.util.HashMap;

import sinia.com.baihangeducation.MyApplication;
import sinia.com.baihangeducation.AppConfig;
import com.mcxtzhang.swipemenulib.info.IsCompleteInfo;
import sinia.com.baihangeducation.home.view.ICompleteInfoView;

public class CompletePresenter extends BasePresenter {
    private Activity activity;
    private ICompleteInfoView view;


    public CompletePresenter(Activity activity, ICompleteInfoView view) {
        super(activity);
        this.activity = activity;
        this.view = view;
    }

    public void getCompleteInfo(final int position) {

        HashMap<String, Object> mInfo = new HashMap<>();
        mInfo.clear();
        mInfo.put("act", "isCompleteInfo");
        mInfo.put("app", "ucenter");
        mInfo.put("device", "2");
        //每次传
        mInfo.put("app_version", AppConfig.API_VERSION);

        mInfo.put("token", AppConfig.TOKEN);
        mInfo.put("user_id", AppConfig.USERID);
        post(mInfo, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                Log.i("是否完善资料", bean.toString());
                IsCompleteInfo isCompleteInfo = bean.parseObject(IsCompleteInfo.class);
                view.getSuccess(isCompleteInfo, position);
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
