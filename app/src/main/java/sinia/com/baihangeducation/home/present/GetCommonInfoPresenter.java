package sinia.com.baihangeducation.home.present;

import android.app.Activity;
import android.util.Log;

import com.example.framwork.base.BasePresenter;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.OnRequestListener;
import com.example.framwork.utils.SPUtils;

import java.util.HashMap;

import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.mine.model.AccountManger;

import com.example.framwork.utils.SpCommonUtils;
import com.mcxtzhang.swipemenulib.info.bean.CommonInfo;

import sinia.com.baihangeducation.home.view.IGetCommonInfoView;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;

import com.mcxtzhang.swipemenulib.utils.Constants;


/**
 * Created by gaoy on 2016/12/23.
 */

public class GetCommonInfoPresenter extends BasePresenter {
    private Activity context;
    private IGetCommonInfoView view;

    public GetCommonInfoPresenter(Activity context, IGetCommonInfoView view) {
        super(context);
        this.context = context;
        this.view = view;
    }


    public void getCommonInfo() {
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(context, "getAboutInfo", "default", false);

        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                CommonInfo commonInfo = bean.parseObject(CommonInfo.class);

                SpCommonUtils.put(activity, AppConfig.COMMON_INFO_ANDROID_VERSION, commonInfo.android_audit_version);
                SpCommonUtils.put(activity, AppConfig.COMMON_INFO_HOTLINE, commonInfo.hotline);
                SpCommonUtils.put(activity, AppConfig.COMMON_INFO_OPEN_IMG, commonInfo.open_img);
                SpCommonUtils.put(activity, AppConfig.COMMON_INFO_ABOUT, commonInfo.about);
                SpCommonUtils.put(activity, AppConfig.COMMON_INFO_ABOUTREGISTRATION_PROTOCOL, commonInfo.registrationProtocol);
                SpCommonUtils.put(activity, AppConfig.COMMON_INFO_HELP, commonInfo.help);



                SPUtils.getInstance().saveObject(context, Constants.COMMON_INFO, commonInfo);
                AccountManger.getCommonInfo(context);
                view.showSuccess(commonInfo);
            }

            @Override
            public void requestFailed(String error) {
                view.showError();
            }


            @Override
            public void requestFinish() {
            }
        });
    }


}
