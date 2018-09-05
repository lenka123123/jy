package sinia.com.baihangeducation.find.presenter;

import android.app.Activity;
import android.util.Log;

import com.example.framwork.base.BasePresenter;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.OnRequestListener;
import com.example.framwork.utils.CommonUtil;
import com.example.framwork.utils.Toast;

import java.util.HashMap;
import java.util.List;

import com.mcxtzhang.swipemenulib.info.ReCommendedListInfo;
import com.mcxtzhang.swipemenulib.info.bean.ReCommendedInfo;
import sinia.com.baihangeducation.home.view.ReCommendedView;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;

/**
 * Created by Administrator on 2018/4/23.
 */

public class ReCommendedAPresenter extends BasePresenter {
    private Activity activity;
    private ReCommendedView view;

    public ReCommendedAPresenter(Activity activity, ReCommendedView view) {
        super(activity);
        this.activity = activity;
        this.view = view;
    }

    public void getReCommendedData() {
        HashMap mReCommendedData = BaseRequestInfo.getInstance().getRequestInfo(activity, "getRecommendList", "home", false);
        mReCommendedData.put("page", view.getPage());
        mReCommendedData.put("perpage", view.getPerpage());
        view.showLoading();
        post(mReCommendedData, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                Log.i("推荐详情",bean.toString());
                ReCommendedListInfo mReCommendedListInfo = bean.parseObject(ReCommendedListInfo.class);
                List<ReCommendedInfo> mReCommendedInfo = mReCommendedListInfo.list;
                int maxpag = CommonUtil.getMaxPage(mReCommendedListInfo.count, mReCommendedListInfo.perpage);
                view.getReCommendedDataSuccess(mReCommendedInfo, maxpag);
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
