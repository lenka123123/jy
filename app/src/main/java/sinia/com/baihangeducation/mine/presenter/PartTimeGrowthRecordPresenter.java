package sinia.com.baihangeducation.mine.presenter;

import android.app.Activity;
import android.util.Log;

import com.example.framwork.base.BasePresenter;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.OnRequestListener;
import com.example.framwork.utils.CommonUtil;
import com.example.framwork.utils.Toast;

import java.util.HashMap;

import com.mcxtzhang.swipemenulib.info.bean.PartTimeListInfo;
import sinia.com.baihangeducation.mine.view.PartTimeGrowthRecordView;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;

/**
 * Created by Administrator on 2018/4/13.
 */

public class PartTimeGrowthRecordPresenter extends BasePresenter {

    private Activity activity;
    private PartTimeGrowthRecordView view;

    public PartTimeGrowthRecordPresenter(Activity activity, PartTimeGrowthRecordView view) {
        super(activity);
        this.activity = activity;
        this.view = view;
    }

    public void getPartTimeGrowthRecord() {
        HashMap getPartTimeGrowthRecordInfo = BaseRequestInfo.getInstance().getRequestInfo(activity, "myJobList", "ucenter", true);
        getPartTimeGrowthRecordInfo.put("page", view.getPage());
        getPartTimeGrowthRecordInfo.put("perpage", view.getPerpage());

        post(getPartTimeGrowthRecordInfo, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                Log.i("获取兼职成长记录", bean.toString());
                PartTimeListInfo mPartTimeListInfo = bean.parseObject(PartTimeListInfo.class);
                int maxpag = CommonUtil.getMaxPage(mPartTimeListInfo.count, mPartTimeListInfo.perpage);
                view.getPartTimeGrowthRecordSuccess(mPartTimeListInfo, maxpag);
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
