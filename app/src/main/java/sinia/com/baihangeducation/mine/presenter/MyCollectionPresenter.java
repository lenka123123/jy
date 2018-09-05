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

import com.mcxtzhang.swipemenulib.info.bean.MyCollectionInfo;
import com.mcxtzhang.swipemenulib.info.bean.MyCollectionListInfo;

import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.mine.view.MyCollectionView;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;

/**
 * Created by Administrator on 2018/4/11.
 */

public class MyCollectionPresenter extends BasePresenter {

    private Activity activity;
    private MyCollectionView view;

    public MyCollectionPresenter(Activity activity, MyCollectionView view) {
        super(activity);
        this.activity = activity;
        this.view = view;
    }

    public MyCollectionPresenter(Activity activity) {
        super(activity);
        this.activity = activity;
    }

    /**
     * 兼职
     */
    public void getPartTimeData() {
        HashMap mPartTime = BaseRequestInfo.getInstance().getRequestInfo(activity, "getMyCollect", "ucenter", true);
        mPartTime.put("user_id", AppConfig.USERID);
        mPartTime.put("token", AppConfig.TOKEN);
        mPartTime.put("type", view.getType());
        mPartTime.put("page", view.getPage());
        mPartTime.put("perpage", view.getPerpage());
        view.hideLoading();
        post(mPartTime, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                Log.i("我的收藏", bean.toString() + "兼职");
                MyCollectionInfo myCollectionInfo = bean.parseObject(MyCollectionInfo.class);
                List<MyCollectionListInfo> myCollectionListInfo = myCollectionInfo.list;
                int maxpag = CommonUtil.getMaxPage(myCollectionInfo.count, myCollectionInfo.perpage);
                view.getPatrTimeSuccess(myCollectionListInfo, maxpag);
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

    /**
     * 全职
     */
    public void getAllTimeData() {
        HashMap mAllTime = BaseRequestInfo.getInstance().getRequestInfo(activity, "getMyCollect", "ucenter", true);
        mAllTime.put("user_id", AppConfig.USERID);
        mAllTime.put("token", AppConfig.TOKEN);
        mAllTime.put("type", view.getType());
        mAllTime.put("page", view.getPage());
        mAllTime.put("perpage", view.getPerpage());
        view.hideLoading();
        post(mAllTime, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                Log.i("我的收藏", bean.toString() + "全职");
                MyCollectionInfo myCollectionInfo = bean.parseObject(MyCollectionInfo.class);
                List<MyCollectionListInfo> myCollectionListInfo = myCollectionInfo.list;
                int maxpag = CommonUtil.getMaxPage(myCollectionInfo.count, myCollectionInfo.perpage);
                view.getAllTimeSuccess(myCollectionListInfo, maxpag);
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

    /**
     * 趣事
     */
    public void getFunnyData() {
        HashMap mFunnyData = BaseRequestInfo.getInstance().getRequestInfo(activity, "getMyCollect", "ucenter", true);
        mFunnyData.put("user_id", AppConfig.USERID);
        mFunnyData.put("token", AppConfig.TOKEN);
        mFunnyData.put("type", view.getType());
        mFunnyData.put("page", view.getPage());
        mFunnyData.put("perpage", view.getPerpage());
        view.hideLoading();
        post(mFunnyData, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                Log.i("我的收藏", bean.toString() + "趣事 ");
                MyCollectionInfo myCollectionInfo = bean.parseObject(MyCollectionInfo.class);
                List<MyCollectionListInfo> myCollectionListInfo = myCollectionInfo.list;
                int maxpag = CommonUtil.getMaxPage(myCollectionInfo.count, myCollectionInfo.perpage);
                view.getFunnySuccess(myCollectionListInfo, maxpag);
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

    /**
     * 攻略
     */
    public void getStrategyData() {
        HashMap mStrategyData = BaseRequestInfo.getInstance().getRequestInfo(activity, "getMyCollect", "ucenter", true);
        mStrategyData.put("user_id", AppConfig.USERID);
        mStrategyData.put("token", AppConfig.TOKEN);
        mStrategyData.put("type", view.getType());
        mStrategyData.put("page", view.getPage());
        mStrategyData.put("perpage", view.getPerpage());
        view.hideLoading();
        post(mStrategyData, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                Log.i("我的收藏", bean.toString() + "攻略 ");
                MyCollectionInfo myCollectionInfo = bean.parseObject(MyCollectionInfo.class);
                List<MyCollectionListInfo> myCollectionListInfo = myCollectionInfo.list;
                int maxpag = CommonUtil.getMaxPage(myCollectionInfo.count, myCollectionInfo.perpage);
                view.getStrategySuccess(myCollectionListInfo, maxpag);
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

    /**
     * 试卷
     */
    public void getTestData() {
        HashMap mTestData = BaseRequestInfo.getInstance().getRequestInfo(activity, "getMyCollect", "ucenter", true);
        mTestData.put("user_id", AppConfig.USERID);
        mTestData.put("token", AppConfig.TOKEN);
        mTestData.put("type", view.getType());
        mTestData.put("page", view.getPage());
        mTestData.put("perpage", view.getPerpage());
        view.hideLoading();
        post(mTestData, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                Log.i("我的收藏", bean.toString() + "试卷 ");
                MyCollectionInfo myCollectionInfo = bean.parseObject(MyCollectionInfo.class);
                List<MyCollectionListInfo> myCollectionListInfo = myCollectionInfo.list;
                int maxpag = CommonUtil.getMaxPage(myCollectionInfo.count, myCollectionInfo.perpage);
                view.getTestSuccess(myCollectionListInfo, maxpag);
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

    /**
     * 秘籍
     */
    public void getSecretData() {
        HashMap mSecretData = BaseRequestInfo.getInstance().getRequestInfo(activity, "getMyCollect", "ucenter", true);

        mSecretData.put("user_id", AppConfig.USERID);
        mSecretData.put("token", AppConfig.TOKEN);
        mSecretData.put("type", view.getType());
        mSecretData.put("page", view.getPage());
        mSecretData.put("perpage", view.getPerpage());
        view.hideLoading();
        post(mSecretData, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                Log.i("我的收藏", bean.toString() + "秘籍 ");
                MyCollectionInfo myCollectionInfo = bean.parseObject(MyCollectionInfo.class);
                List<MyCollectionListInfo> myCollectionListInfo = myCollectionInfo.list;
                int maxpag = CommonUtil.getMaxPage(myCollectionInfo.count, myCollectionInfo.perpage);
                view.getSecretSuccess(myCollectionListInfo, maxpag);
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

    /**
     * 资讯
     */
    public void getInformationData() {
        HashMap mInformationData = BaseRequestInfo.getInstance().getRequestInfo(activity, "getMyCollect", "ucenter", true);
        mInformationData.put("user_id", AppConfig.USERID);
        mInformationData.put("token", AppConfig.TOKEN);

        mInformationData.put("type", view.getType());
        mInformationData.put("page", view.getPage());
        mInformationData.put("perpage", view.getPerpage());
        view.hideLoading();
        post(mInformationData, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                Log.i("我的收藏", bean.toString() + "资讯 ");
                MyCollectionInfo myCollectionInfo = bean.parseObject(MyCollectionInfo.class);
                List<MyCollectionListInfo> myCollectionListInfo = myCollectionInfo.list;
                int maxpag = CommonUtil.getMaxPage(myCollectionInfo.count, myCollectionInfo.perpage);
                view.getInformationSuccess(myCollectionListInfo, maxpag);
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

    public void detelMyCollection(int id) {
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "dropMyCollect", "ucenter", true);
        info.put("collect_id", id + "");
        info.put("user_id", AppConfig.USERID);
        info.put("token", AppConfig.TOKEN);
        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                Log.i("删除收藏", bean.toString());
                Toast.getInstance().showSuccessToast(activity, "删除成功");
            }

            @Override
            public void requestFailed(String error) {
                Toast.getInstance().showErrorToast(activity, error);
            }

            @Override
            public void requestFinish() {

            }
        });
    }
}
