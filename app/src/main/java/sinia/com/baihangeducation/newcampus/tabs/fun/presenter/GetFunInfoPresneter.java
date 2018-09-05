package sinia.com.baihangeducation.newcampus.tabs.fun.presenter;

import android.app.Activity;
import android.util.Log;

import com.example.framwork.base.BasePresenter;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.OnRequestListener;
import com.example.framwork.utils.CommonUtil;
import com.example.framwork.utils.Toast;
import com.example.framwork.utils.UserInfo;

import java.util.HashMap;

import sinia.com.baihangeducation.MyApplication;
import sinia.com.baihangeducation.newcampus.info.FunInfo;
import sinia.com.baihangeducation.newcampus.info.HomePager;
import sinia.com.baihangeducation.newcampus.interfaces.HomePagerListener;
import sinia.com.baihangeducation.newcampus.view.IGetFunView;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;

public class GetFunInfoPresneter extends BasePresenter {
    private Activity activity;
    private IGetFunView view;


    public GetFunInfoPresneter(Activity activity, IGetFunView view) {
        super(activity);
        this.activity = activity;
        this.view = view;
    }

    public void getFunInfo() {

        HashMap   info = BaseRequestInfo.getInstance().getRequestInfo(activity, "getDynamicList", "school", false);

        info.put("page", view.getFunPage());
        info.put("perpage", view.getFunPerpage());
        view.showLoading();
        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                Log.i("获取校园趣事信息", bean.toString());
                FunInfo funInfo = bean.parseObject(FunInfo.class);
                int maxpag = CommonUtil.getMaxPage(funInfo.count, funInfo.perpage);
                view.getFunInfoSuccess(funInfo, maxpag);
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
