package sinia.com.baihangeducation.mine.presenter;

import android.app.Activity;
import android.util.Log;

import com.example.framwork.base.BasePresenter;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.OnRequestListener;
import com.example.framwork.utils.CommonUtil;

import java.util.HashMap;

import com.mcxtzhang.swipemenulib.info.bean.TraingListInfo;

import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.mine.view.TraingGrowthRecordView;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;

/**
 * Created by Administrator on 2018/4/13.
 */

public class TraingGrowthRecordPresenter extends BasePresenter {

    private Activity activity;
    private TraingGrowthRecordView view;

    public TraingGrowthRecordPresenter(Activity activity, TraingGrowthRecordView view) {
        super(activity);
        this.activity = activity;
        this.view = view;
    }

    public void getTraingGrowthRecord() {
        HashMap getPartTimeGrowthRecordInfo = BaseRequestInfo.getInstance().getRequestInfo(activity, "myTrainingList", "ucenter", true);

        getPartTimeGrowthRecordInfo.put("token", AppConfig.TOKEN);
        getPartTimeGrowthRecordInfo.put("user_id", AppConfig.USERID);
        getPartTimeGrowthRecordInfo.put("page", view.getPage());
        getPartTimeGrowthRecordInfo.put("perpage", view.getPerpage());
        view.showLoading();
        post(getPartTimeGrowthRecordInfo, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                Log.i("获取培训成长记录", bean.toString());
                TraingListInfo mTraingListInfo = bean.parseObject(TraingListInfo.class);
                int maxpag = CommonUtil.getMaxPage(mTraingListInfo.count, mTraingListInfo.perpage);
                view.getTraingGrowthRecordSuccess(mTraingListInfo, maxpag);
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
