package sinia.com.baihangeducation.mine.presenter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;

import com.example.framwork.base.BasePresenter;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.OnRequestListener;
import com.example.framwork.utils.CommonUtil;
import com.example.framwork.utils.SpCommonUtils;
import com.example.framwork.utils.Toast;

import java.io.IOException;
import java.util.HashMap;

import com.example.framwork.utils.UserInfo;
import com.google.gson.Gson;
import com.mcxtzhang.swipemenulib.base.BaseFragment;
import com.mcxtzhang.swipemenulib.info.bean.VersionInfo;
import com.mcxtzhang.swipemenulib.utils.FileUtils;

import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.MainActivity;
import sinia.com.baihangeducation.mine.MineFragment;
import sinia.com.baihangeducation.mine.view.IUpdateVersionView;
import sinia.com.baihangeducation.supplement.base.Goto;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;

/**
 * Created by gaoy on 2016/10/26.
 */

public class UpdateVersionPresenter extends BasePresenter {

    private Activity activity;
    private IUpdateVersionView view;

    public UpdateVersionPresenter(Activity activity, IUpdateVersionView view) {
        super(activity);
        this.activity = activity;
        this.view = view;
    }

    /**
     * 获取首页版本更新信息
     */
    public void getVersionInfo() {
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "getUpdateVersion", "default", false);
        info.put("device", 2);
        info.put("cur_version", CommonUtil.getVersion(activity));
        post(info, new OnRequestListener() {

            @Override
            public void requestSuccess(BaseResponseBean bean) {
                Log.i("更新提示", bean.toString());
                if (bean.getData() == null) {
                    return;
                }
                if (bean.isSuccess()) {
                    VersionInfo versionInfo = bean.parseObject(VersionInfo.class);
                    if (bean.getData() == null || versionInfo == null || versionInfo.version == null) {
                        return;
                    }
                    String curVersionStr = CommonUtil.getVersion(activity);
                    String newVersionStr = versionInfo.version;
                    int curVersion;
                    int newVersion;
                    if (curVersionStr != null && curVersionStr.contains(".")) {
                        curVersion = Integer.valueOf(curVersionStr.replace(".", ""));
                    } else curVersion = Integer.valueOf(curVersionStr);

                    if (newVersionStr != null && newVersionStr.contains(".")) {
                        newVersion = Integer.valueOf(newVersionStr.replace(".", ""));
                    } else newVersion = Integer.valueOf(newVersionStr);
                    if (newVersion > curVersion && versionInfo.is_update == 1) {
                        //是否强制更新(1是 2否)
                        if (versionInfo.is_force == 1) {
                            view.showForceUpdate(versionInfo);
                        } else {
                            view.showUpdate(versionInfo);
                        }
                    }
                }
            }

            @Override
            public void requestFailed(String error) {
                Toast.getInstance().showToastView(activity, error);
            }

            @Override
            public void requestFinish() {

            }
        });
    }


    public void getBaseInfoLoginAfter(MainActivity mainActivity) {
        HashMap mGetBaseInfoData = BaseRequestInfo.getInstance().getRequestInfo(activity, "getMyInfo", "ucenter", true);
        mGetBaseInfoData.put("token", AppConfig.TOKEN);
        mGetBaseInfoData.put("user_id", AppConfig.USERID);

        post(mGetBaseInfoData, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                Log.i("获取资本信息", bean.toString());

                UserInfo userInfo = bean.parseObject(UserInfo.class);

                String no_read_num = userInfo.no_read_num;
                int num = Integer.valueOf(no_read_num);
                if (num < 1) no_read_num = "0";

                SpCommonUtils.put(activity, AppConfig.FINAL_NO_READ_NUM, no_read_num);
                mainActivity.gsetBaseInfoSuccess(userInfo);

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


    public void getBaseInfo(MineFragment mainActivity) {
        HashMap mGetBaseInfoData = BaseRequestInfo.getInstance().getRequestInfo(activity, "getMyInfo", "ucenter", true);
        mGetBaseInfoData.put("token", AppConfig.TOKEN);
        mGetBaseInfoData.put("user_id", AppConfig.USERID);

        post(mGetBaseInfoData, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                Log.i("获取资本信息", bean.toString());

                UserInfo userInfo = bean.parseObject(UserInfo.class);

                String no_read_num = userInfo.no_read_num;
                int num = Integer.valueOf(no_read_num);
                if (num < 1) no_read_num = "0";

                SpCommonUtils.put(activity, AppConfig.FINAL_NO_READ_NUM, no_read_num);
                mainActivity.gsetBaseInfoSuccess(userInfo);

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
