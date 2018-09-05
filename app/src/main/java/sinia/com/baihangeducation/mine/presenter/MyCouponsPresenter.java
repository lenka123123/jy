package sinia.com.baihangeducation.mine.presenter;

import android.app.Activity;
import android.util.Log;

import com.example.framwork.base.BasePresenter;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.OnRequestListener;
import com.example.framwork.utils.CommonUtil;

import java.util.HashMap;

import com.mcxtzhang.swipemenulib.info.bean.MyCouponsInfo;
import sinia.com.baihangeducation.mine.view.MyCouponsView;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;

/**
 * Created by Administrator on 2018/4/9.
 */

public class MyCouponsPresenter extends BasePresenter {

    private Activity activity;
    private MyCouponsView view;

    public MyCouponsPresenter(Activity activity, MyCouponsView view) {
        super(activity);
        this.activity = activity;
        this.view = view;
    }

    public void getCoupons() {
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "getMyCoupon", "ucenter", true);
        info.put("type", view.getCouponsType());
        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                Log.i("优惠券", bean.toString());
                MyCouponsInfo myCouponsInfo = bean.parseObject(MyCouponsInfo.class);
                int maxpag = CommonUtil.getMaxPage(myCouponsInfo.count, myCouponsInfo.perpage);
                view.getCouponsSuccess(myCouponsInfo,maxpag);
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
