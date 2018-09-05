package sinia.com.baihangeducation.mine.presenter;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;

import com.example.framwork.base.BasePresenter;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.OnRequestListener;
import com.example.framwork.utils.CommonUtil;
import com.example.framwork.utils.ObjectSaveUtil;
import com.example.framwork.utils.SPUtils;
import com.example.framwork.utils.SpCommonUtils;
import com.example.framwork.utils.Toast;
import com.example.framwork.utils.UserInfo;
import com.yanzhenjie.nohttp.FileBinary;
import com.yanzhenjie.nohttp.rest.Request;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.HashMap;

import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.MyApplication;
import sinia.com.baihangeducation.mine.activity.UCentreBaseInfoActivity;
import sinia.com.baihangeducation.mine.model.AccountManger;
import sinia.com.baihangeducation.mine.view.IUCentreBaseInfoView;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;

import com.mcxtzhang.swipemenulib.utils.Constants;

/**
 * Created by Administrator on 2018/3/29.
 */

public class UCentreBaseInfoPresenter extends BasePresenter {
    private Activity activity;
    private IUCentreBaseInfoView view;

    public UCentreBaseInfoPresenter(Activity activity, IUCentreBaseInfoView view) {
        super(activity);
        this.activity = activity;
        this.view = view;
    }

    public void updataUCentreBaseInfo() {
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "editBaseInfo", "ucenter", true);

        info.put("token", AppConfig.TOKEN);
        info.put("user_id", AppConfig.USERID);
        if (!view.getUCentreNickName().equals(""))
            info.put("nickname", view.getUCentreNickName());
        if (!view.getUCentreGender().equals(""))
            info.put("gender", view.getUCentreGender());
        if (!view.getUCentreEmail().equals(""))
            info.put("email", view.getUCentreEmail());
        if (!view.getUCentreSlogan().equals(""))
            info.put("slogan", view.getUCentreSlogan());

        Request<String> request = postFile(info);
        if (!TextUtils.isEmpty(view.getUCentreImage())) {
            request.add("avatar", new FileBinary(new File(view.getUCentreImage())));
            Log.i("图片地址个人中心", view.getUCentreImage() + "处理1");
            view.showLoading();
            model.execute(activity, request, new OnRequestListener() {
                @Override
                public void requestSuccess(BaseResponseBean bean) {
                    Log.i("编辑基本资料", bean.toString() + "图片");
                    loginSuccessBack(bean);
                    view.upDataUCentreBaseInfoSuccess();
                }

                @Override
                public void requestFailed(String error) {
                    Toast.getInstance().showErrorToast(activity, error);
                }

                @Override
                public void requestFinish() {

                }
            });
        } else {
            post(info, new OnRequestListener() {
                @Override
                public void requestSuccess(BaseResponseBean bean) {
                    Log.i("编辑基本资料", bean.toString() + "no图片");
                    loginSuccessBack(bean);
                    view.upDataUCentreBaseInfoSuccess();
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

    /**
     * 登录成功储存用户信息
     *
     * @param bean
     */
    private void loginSuccessBack(BaseResponseBean bean) {
        SPUtils.getInstance().saveObject(activity, Constants.USER_INFO, bean.parseObject(UserInfo.class));
        AccountManger.getUserInfo(activity);
        EventBus.getDefault().post(Constants.EB_LOGIN_SUCCESS);
    }

//    public void login(final UCentreBaseInfoActivity loginActivity) {
//        String name= (String) SpCommonUtils.get(activity, AppConfig.USERPHOTO, "");
//        String pwd= (String)  SpCommonUtils.get(activity, AppConfig.USERPWD,  "");
//        if (name.equals("")||pwd.equals("")){
//            loginActivity.finish();
//            return;
//        }
//
//        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "login", "default", false);
//        info.put("mobile", name);
//        info.put("password",pwd);
//        info.put("lng", "118.755877");
//        info.put("lat", "32.089858");
//        info.put("device_id", CommonUtil.getAndroidId(activity));
//
//        post(info, new OnRequestListener() {
//            @Override
//            public void requestSuccess(BaseResponseBean bean) {
//
//                UserInfo userInfo = bean.parseObject(UserInfo.class);
//                AppConfig.ISlOGINED = true;
//                AppConfig.TOKEN = userInfo.token;
//                AppConfig.USERID = userInfo.user_id;
//
//                MyApplication application = (MyApplication) activity.getApplicationContext();
//                application.setUserInfo(userInfo);
//
//                SpCommonUtils.put(activity, AppConfig.USERTOKEN, userInfo.token);
//                SpCommonUtils.put(activity, AppConfig.FINALUSERID, userInfo.user_id);
//
//
//                SpCommonUtils.put(activity, AppConfig.FINALUAVATAR, userInfo.avatar);
//                SpCommonUtils.put(activity, AppConfig.FINALNICKNAME, userInfo.nickname);
//                SpCommonUtils.put(activity, AppConfig.FINALSLOGAN, userInfo.slogan);
//                SpCommonUtils.put(activity, AppConfig.FINALGENDEREEE, userInfo.gender + "");
//                SpCommonUtils.put(activity, AppConfig.FINALEMEMAIL, userInfo.email);
//
//
//                SpCommonUtils.put(activity, AppConfig.FINAL_NO_READ_NUM, userInfo.no_read_num);
//                SpCommonUtils.put(activity, AppConfig.FINAL_NUM_TRAIN_NUM, userInfo.my_num.train_num);
//                SpCommonUtils.put(activity, AppConfig.FINAL_NUM_FULL_JOB_NUM, userInfo.my_num.full_job_num);
//                SpCommonUtils.put(activity, AppConfig.FINAL_NUM_FULL_PARK_NUM, userInfo.my_num.part_job_num);
//                SpCommonUtils.put(activity, AppConfig.FINAL_NUM_FULL_HULP_NUM, userInfo.my_num.help_num);
//                SpCommonUtils.put(activity, AppConfig.FINAL_NUM_FULL_HULP_NICKNAME, userInfo.nickname);
//                SpCommonUtils.put(activity, AppConfig.FINAL_NUM_FULL_TYPE, userInfo.type);
//                SpCommonUtils.put(activity, AppConfig.FINAL_NUM_FULL_AUTH_STATUS, userInfo.auth_status);
//
//
//                ObjectSaveUtil.saveObject(activity, bean.parseObject(UserInfo.class));
//                SPUtils.getInstance().saveObject(activity, Constants.USER_INFO, bean.parseObject(UserInfo.class));
//
//                AccountManger.getUserInfo(activity);
//                EventBus.getDefault().post(Constants.EB_LOGIN_SUCCESS);
//                loginActivity.finish();
//            }
//
//            @Override
//            public void requestFailed(String error) {
//                loginActivity.finish();
//            }
//
//            @Override
//            public void requestFinish() {
//                view.hideLoading();
//            }
//        });
//    }

}
