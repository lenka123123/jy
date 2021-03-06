package sinia.com.baihangeducation.release.campus.tabs.heatfunsearchlist.model;

import android.app.Activity;

import com.example.framwork.base.BasePresenter;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.OnRequestListener;
import com.example.framwork.utils.Toast;

import java.util.HashMap;

import sinia.com.baihangeducation.release.campus.tabs.heatfunsearchlist.bean.HotFunListBean;
import sinia.com.baihangeducation.release.campus.tabs.heatfunsearchlist.interfaces.HotFunDataListener;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;

public class HotFunListModel extends BasePresenter {

    private Activity context;

    public HotFunListModel(Activity activity) {
        super(activity);
        context = activity;
    }


    public void getHotFunList(int type, int page, String s, final HotFunDataListener listener) {
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(context, "getTopicList", "school", false);
        info.put("search", s);
        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {

                HotFunListBean addListInfo = bean.parseObject(HotFunListBean.class);
                listener.getHotFunDataSuccess(addListInfo);
            }

            @Override
            public void requestFailed(String error) {
                Toast.getInstance().showErrorToast(context, error);
                listener.getHotFunDataFail();
            }

            @Override
            public void requestFinish() {

            }
        });

    }
}
