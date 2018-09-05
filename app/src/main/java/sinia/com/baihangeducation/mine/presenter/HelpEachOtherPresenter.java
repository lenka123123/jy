package sinia.com.baihangeducation.mine.presenter;

import android.app.Activity;
import android.util.Log;

import com.example.framwork.base.BasePresenter;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.OnRequestListener;
import com.example.framwork.utils.CommonUtil;
import com.example.framwork.utils.Toast;

import java.util.HashMap;

import com.mcxtzhang.swipemenulib.info.bean.HelpEachOtherListInfo;

import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.mine.view.HelpEachOtherView;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;

/**
 * Created by Administrator on 2018/4/13.
 */

public class HelpEachOtherPresenter extends BasePresenter {

    private Activity activity;
    private HelpEachOtherView view;

    public HelpEachOtherPresenter(Activity activity, HelpEachOtherView view) {
        super(activity);
        this.activity = activity;
        this.view = view;
    }

    public void getHelpEachOtherData() {
        HashMap mHelpEachOtherData = BaseRequestInfo.getInstance().getRequestInfo(activity, "myHelpList", "ucenter", true);
        mHelpEachOtherData.put("user_id", AppConfig.USERID);
        mHelpEachOtherData.put("token", AppConfig.TOKEN);
        mHelpEachOtherData.put("type", view.getType());
        mHelpEachOtherData.put("page", view.getPage());
        mHelpEachOtherData.put("perpage", view.getPerpage());
        view.showLoading();
        post(mHelpEachOtherData, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                Log.i("互助成长记录", bean.toString());
                HelpEachOtherListInfo mHelpEachOtherListInfo = bean.parseObject(HelpEachOtherListInfo.class);
                int maxpag = CommonUtil.getMaxPage(mHelpEachOtherListInfo.count, mHelpEachOtherListInfo.perpage);
                view.getHelpEachOtherDataSuccess(mHelpEachOtherListInfo, maxpag);
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
