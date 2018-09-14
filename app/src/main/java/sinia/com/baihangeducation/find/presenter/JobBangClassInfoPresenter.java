package sinia.com.baihangeducation.find.presenter;

import android.app.Activity;

import com.example.framwork.base.BasePresenter;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.OnRequestListener;
import com.example.framwork.utils.CommonUtil;
import com.example.framwork.utils.Toast;

import java.util.HashMap;

import com.mcxtzhang.swipemenulib.info.JobBangClassSecondListInfo;
import com.mcxtzhang.swipemenulib.info.JobBangClassSecondRadioListInfo;

import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.find.view.JobBangClassInfoView;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;

/**
 * Created by Administrator on 2018/4/25.
 */

public class JobBangClassInfoPresenter extends BasePresenter {
    private Activity activity;
    private JobBangClassInfoView view;

    public JobBangClassInfoPresenter(Activity activity, JobBangClassInfoView view) {
        super(activity);
        this.activity = activity;
        this.view = view;
    }

    public void getJobBangClassInfo() {
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "getRaidersList", "find", false);
        info.put("type", view.getType());

        info.put("token", AppConfig.TOKEN);
        info.put("user_id", AppConfig.USERID);
        info.put("cate_id", view.getCadeId());
        info.put("money_id", view.getMoneyId());
        info.put("order_id", view.getOrderId());
        info.put("is_hot",view.getIsHot());
        info.put("is_free",view.getIsFree());
        info.put("is_choice",view.getIsChoice());
        info.put("page", view.getPage());
        info.put("perpage", view.getPerpage());
        view.hideLoading();
        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                JobBangClassSecondListInfo mJobBangClassSecondListInfo = bean.parseObject(JobBangClassSecondListInfo.class);
                int maxpag = CommonUtil.getMaxPage(mJobBangClassSecondListInfo.count, mJobBangClassSecondListInfo.perpage);
                view.getJobBangClassInfoSuccess(mJobBangClassSecondListInfo, maxpag);
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

    public void gerRadioInfo() {
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "getRaidersOptionList", "find", false);
        info.put("type", view.getRadioType());
        view.hideLoading();
        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                JobBangClassSecondRadioListInfo mJobBangClassSecondRadioListInfo = bean.parseObject(JobBangClassSecondRadioListInfo.class);
                view.getJobBangClassRadioInfoSuccess(mJobBangClassSecondRadioListInfo);
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
