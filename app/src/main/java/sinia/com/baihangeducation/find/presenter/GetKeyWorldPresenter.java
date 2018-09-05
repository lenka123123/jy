package sinia.com.baihangeducation.find.presenter;

import android.app.Activity;

import com.example.framwork.base.BasePresenter;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.OnRequestListener;

import java.util.HashMap;
import java.util.List;

import com.mcxtzhang.swipemenulib.info.GetKeyWorldInfo;
import sinia.com.baihangeducation.find.view.GetKeyWorldView;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;

/**
 * Created by Administrator on 2018/4/17.
 */

public class GetKeyWorldPresenter extends BasePresenter {
    private Activity activity;
    private GetKeyWorldView view;

    public GetKeyWorldPresenter(Activity activity, GetKeyWorldView view) {
        super(activity);
        this.activity = activity;
        this.view = view;
    }

    public void getKeyWorld() {
        HashMap getKeyWorldData = BaseRequestInfo.getInstance().getRequestInfo(activity, "getSearchPageKeyWords", "home", false);
        view.showLoading();
        post(getKeyWorldData, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                List<GetKeyWorldInfo> mGetKeyWorldInfo = bean.parseList(GetKeyWorldInfo.class);
                view.getKeyWorldSuccess(mGetKeyWorldInfo);
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
