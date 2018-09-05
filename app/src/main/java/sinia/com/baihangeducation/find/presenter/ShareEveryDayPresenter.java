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

import com.mcxtzhang.swipemenulib.info.GetShareEveryDayListInfo;

import com.mcxtzhang.swipemenulib.info.bean.ShareEveryDayTabInfo;
import sinia.com.baihangeducation.find.view.ShareEveryDayView;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;

/**
 * Created by Administrator on 2018/4/19.
 */

public class ShareEveryDayPresenter extends BasePresenter {

    private Activity activity;
    private ShareEveryDayView view;

    public ShareEveryDayPresenter(Activity activity, ShareEveryDayView view) {
        super(activity);
        this.activity = activity;
        this.view = view;
    }

    /**
     * 获取每日分享tab
     */
    public void getShareEveryDayTab() {
        HashMap mShareEveryDayData = BaseRequestInfo.getInstance().getRequestInfo(activity, "getNewsCateList", "find", false);
        post(mShareEveryDayData, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                List<ShareEveryDayTabInfo> mShareEveryDayTabInfos = bean.parseList(ShareEveryDayTabInfo.class);
                view.getShareEveryDaySuccess(mShareEveryDayTabInfos);
            }

            @Override
            public void requestFailed(String error) {

            }

            @Override
            public void requestFinish() {

            }
        });
    }

    /**
     * 获取每日分享列表
     */
    public void getShareEveryDayList() {
        HashMap mShareEveryDayListData = BaseRequestInfo.getInstance().getRequestInfo(activity, "getNewsList", "find", false);
        mShareEveryDayListData.put("cate_id", view.getTabId());
        mShareEveryDayListData.put("page", view.getPage());
        mShareEveryDayListData.put("perpage", view.getPerpage());
        post(mShareEveryDayListData, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                Log.i("每日分享",bean.toString());
                GetShareEveryDayListInfo mGetShareEveryDayListInfo = bean.parseObject(GetShareEveryDayListInfo.class);
                int maxpag = CommonUtil.getMaxPage(mGetShareEveryDayListInfo.count, mGetShareEveryDayListInfo.perpage);
                view.getShareEveryDayDataSuccess(mGetShareEveryDayListInfo.list, maxpag);
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
