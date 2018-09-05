package sinia.com.baihangeducation.find.presenter;

import android.app.Activity;
import android.util.Log;

import com.example.framwork.base.BasePresenter;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.OnRequestListener;
import com.example.framwork.utils.Toast;

import java.util.HashMap;

import com.mcxtzhang.swipemenulib.info.AddListInfo;
import sinia.com.baihangeducation.find.view.IGetAddView;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;

public class GetAddPresenter extends BasePresenter {
    private Activity activity;
    private IGetAddView view;

    public GetAddPresenter(Activity activity, IGetAddView view) {
        super(activity);
        this.activity = activity;
        this.view = view;
    }

    public void getAddInfo() {
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "getAdList", "default", false);
        info.put("position", view.getAddPosition());
        view.showLoading();
        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                Log.i("获取的广告数据", bean.toString());
                AddListInfo addListInfo = bean.parseObject(AddListInfo.class);
                view.getAddSuccess(addListInfo);
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
