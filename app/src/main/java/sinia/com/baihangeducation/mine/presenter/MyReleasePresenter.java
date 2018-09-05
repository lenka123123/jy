package sinia.com.baihangeducation.mine.presenter;

import android.app.Activity;
import android.util.Log;

import com.example.framwork.base.BasePresenter;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.OnRequestListener;
import com.example.framwork.utils.CommonUtil;
import com.example.framwork.utils.Toast;

import java.util.HashMap;
import java.util.List;

import com.mcxtzhang.swipemenulib.info.bean.MyReleaseFunInfo;
import com.mcxtzhang.swipemenulib.info.bean.MyReleaseFunListInfo;
import com.mcxtzhang.swipemenulib.info.bean.MyReleaseInfo;
import com.mcxtzhang.swipemenulib.info.bean.MyReleaseListInfo;
import sinia.com.baihangeducation.mine.view.MyReleaseHelpView;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;

/**
 * Created by Administrator on 2018/4/10.
 */

public class MyReleasePresenter extends BasePresenter {

    private Activity activity;
    private MyReleaseHelpView view;

    public MyReleasePresenter(Activity activity, MyReleaseHelpView view) {
        super(activity);
        this.activity = activity;
        this.view = view;
    }

    public void getHelpData() {
        HashMap helpData = BaseRequestInfo.getInstance().getRequestInfo(activity, "getMyPush", "ucenter", true);
        helpData.put("tab", view.getTab());
        helpData.put("type", view.getType());
        helpData.put("page", view.getPage());
        helpData.put("perpage", view.getPerpage());
        post(helpData, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                MyReleaseListInfo myReleaseListInfo = bean.parseObject(MyReleaseListInfo.class);
                List<MyReleaseInfo> myReleaseInfoHelp = myReleaseListInfo.list;
                Log.i("我的发布互助", bean.toString());
                int maxpag = CommonUtil.getMaxPage(myReleaseListInfo.count, myReleaseListInfo.perpage);
                view.getHelpHelpSuccess(myReleaseInfoHelp, maxpag);
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

    public void getTranfData() {
        HashMap helpData = BaseRequestInfo.getInstance().getRequestInfo(activity, "getMyPush", "ucenter", true);
        helpData.put("tab", view.getTab());
        helpData.put("type", view.getType());
        helpData.put("page", view.getPage());
        helpData.put("perpage", view.getPerpage());
        post(helpData, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                Log.i("我的发布转让", bean.toString());
                MyReleaseListInfo myReleaseListInfo = bean.parseObject(MyReleaseListInfo.class);
                List<MyReleaseInfo> myReleaseInfoTranf = myReleaseListInfo.list;
                int maxpag = CommonUtil.getMaxPage(myReleaseListInfo.count, myReleaseListInfo.perpage);
                view.getHelpHelpSuccess(myReleaseInfoTranf, maxpag);
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
    public void getFunData() {
        HashMap helpData = BaseRequestInfo.getInstance().getRequestInfo(activity, "getMyPush", "ucenter", true);
        helpData.put("tab", view.getTab());
        helpData.put("type", view.getType());
        helpData.put("page", view.getPage());
        helpData.put("perpage", view.getPerpage());
        post(helpData, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                Log.i("我的趣事发布", bean.toString());
                MyReleaseFunListInfo myReleaseFunListInfo = bean.parseObject(MyReleaseFunListInfo.class);
                List<MyReleaseFunInfo> myReleaseFunInfos = myReleaseFunListInfo.list;
                int maxpag = CommonUtil.getMaxPage(myReleaseFunListInfo.count, myReleaseFunListInfo.perpage);
                view.getFunSuccess(myReleaseFunInfos, maxpag);
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
