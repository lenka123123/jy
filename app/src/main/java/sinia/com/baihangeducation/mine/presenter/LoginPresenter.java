package sinia.com.baihangeducation.mine.presenter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.example.framwork.base.BasePresenter;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.OnRequestListener;
import com.example.framwork.utils.CommonUtil;
import com.example.framwork.utils.ObjectSaveUtil;
import com.example.framwork.utils.SPUtils;
import com.example.framwork.utils.SpCommonUtils;
import com.example.framwork.utils.Toast;
import com.example.framwork.utils.UserInfo;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.im.android.api.JMessageClient;
import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.MyApplication;
import sinia.com.baihangeducation.mine.activity.LoginActivity;
import sinia.com.baihangeducation.mine.model.AccountManger;

import com.mcxtzhang.swipemenulib.info.IsCompleteInfo;

import sinia.com.baihangeducation.mine.view.ILoginView;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;
import sinia.com.baihangeducation.supplement.tool.GetLocation;

import com.mcxtzhang.swipemenulib.utils.BitmapSave;
import com.mcxtzhang.swipemenulib.utils.Constants;
import com.mcxtzhang.swipemenulib.utils.ImageUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

/**
 * Created by Administrator on 2018.03.07.
 */

public class LoginPresenter extends BasePresenter {
    private Activity activity;
    private ILoginView view;
    private GetLocation getLocation;

    public LoginPresenter(Activity activity, LoginActivity view) {
        super(activity);
        this.activity = activity;
        this.view = view;
        getLocation = new GetLocation(activity);
    }

    /**
     * 登录方法
     */
    public void login(  ) {
        if (!AccountManger.checkupLogin(activity, view.getPhoneNum(), view.getPassword())) {
            return;
        }
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "login", "default", false);
        info.put("mobile", view.getPhoneNum());
        info.put("password", view.getPassword());
        info.put("lng", view.getLocationLng());
        info.put("lat", view.getLocationLat());
        info.put("device_id", CommonUtil.getAndroidId(activity));


        System.out.println("device_id" + CommonUtil.getAndroidId(activity));
        String jPushId = JPushInterface.getRegistrationID(activity);

        System.out.println("getRegistrationID" + jPushId);
        view.showLoading();
        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {

                JMessageClient.logout();

                UserInfo userInfo = bean.parseObject(UserInfo.class);
                AppConfig.ISlOGINED = true;
                AppConfig.TOKEN = userInfo.token;
                AppConfig.USERID = userInfo.user_id;
                AppConfig.USERIDTYPE = userInfo.type;
                System.out.println("userInfouserInfouserIns试试fo.type" + userInfo.type);
//                getBaseInfoPresenter.getBaseInfoLoginAfter(AppConfig.TOKEN, AppConfig.USERID);

                SpCommonUtils.put(activity, AppConfig.USERTOKEN, userInfo.token);
                SpCommonUtils.put(activity, AppConfig.FINALUSERID, userInfo.user_id);
                SpCommonUtils.put(activity, AppConfig.USERPHOTO, view.getPhoneNum());
                SpCommonUtils.put(activity, AppConfig.USERPWD, view.getPassword());


                SpCommonUtils.put(activity, AppConfig.FINALUAVATAR, userInfo.avatar);
                SpCommonUtils.put(activity, AppConfig.FINALNICKNAME, userInfo.nickname);
                SpCommonUtils.put(activity, AppConfig.FINALSLOGAN, userInfo.slogan);
                SpCommonUtils.put(activity, AppConfig.FINALGENDEREEE, userInfo.gender + "");
                SpCommonUtils.put(activity, AppConfig.FINALEMEMAIL, userInfo.email);


                SpCommonUtils.put(activity, AppConfig.FINAL_NO_READ_NUM, userInfo.no_read_num);
                SpCommonUtils.put(activity, AppConfig.FINAL_NUM_TRAIN_NUM, userInfo.my_num.train_num);
                SpCommonUtils.put(activity, AppConfig.FINAL_NUM_FULL_JOB_NUM, userInfo.my_num.full_job_num);
                SpCommonUtils.put(activity, AppConfig.FINAL_NUM_FULL_PARK_NUM, userInfo.my_num.part_job_num);
                SpCommonUtils.put(activity, AppConfig.FINAL_NUM_FULL_HULP_NUM, userInfo.my_num.help_num);
                SpCommonUtils.put(activity, AppConfig.FINAL_NUM_FULL_HULP_NICKNAME, userInfo.nickname);
                SpCommonUtils.put(activity, AppConfig.FINAL_NUM_FULL_TYPE, userInfo.type);
                SpCommonUtils.put(activity, AppConfig.IS_LOGIN_APP, true);

                SpCommonUtils.put(activity, AppConfig.FINAL_NUM_FULL_AUTH_STATUS, userInfo.auth_status);
                SpCommonUtils.put(activity, AppConfig.FINAL_NUM_FULL_VIP_LEVEL, userInfo.vip_level);


//                ObjectSaveUtil.saveObject(activity, bean.parseObject(UserInfo.class));
                SPUtils.getInstance().saveObject(activity, Constants.USER_INFO, bean.parseObject(UserInfo.class));
                SPUtils.getInstance().saveObject(activity, Constants.USER_ACCOUNT, view.getPhoneNum());
                AccountManger.getUserInfo(activity);
                EventBus.getDefault().post(Constants.EB_LOGIN_SUCCESS);

                MyAsyncTask myAsyncTask = new MyAsyncTask();
                myAsyncTask.execute(userInfo.avatar);
                view.showLoginSuccress();
//                loginActivity.finish();
            }

            @Override
            public void requestFailed(String error) {
                Log.i("登录", error.toString());
                Toast.getInstance().showErrorToast(activity, error);
            }

            @Override
            public void requestFinish() {
                view.hideLoading();
            }
        });
    }


    public void loginRegister(final LoginActivity loginActivity) {
        if (!AccountManger.checkupLogin(activity, view.getPhoneNum(), view.getPassword())) {
            return;
        }
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "login", "default", false);
        info.put("mobile", view.getPhoneNum());
        info.put("password", view.getPassword());
        info.put("lng", view.getLocationLng());
        info.put("lat", view.getLocationLat());
        info.put("device_id", CommonUtil.getAndroidId(activity));


        System.out.println("device_id" + CommonUtil.getAndroidId(activity));
        String jPushId = JPushInterface.getRegistrationID(activity);

        System.out.println("getRegistrationID" + jPushId);
        view.showLoading();
        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                view.showLoginSuccress();
                UserInfo userInfo = bean.parseObject(UserInfo.class);
                AppConfig.ISlOGINED = true;
                AppConfig.TOKEN = userInfo.token;
                AppConfig.USERID = userInfo.user_id;
                AppConfig.USERIDTYPE = userInfo.type;
                System.out.println("userInfouserInfouserIns试试fo.type" + userInfo.type);
//                getBaseInfoPresenter.getBaseInfoLoginAfter(AppConfig.TOKEN, AppConfig.USERID);

                SpCommonUtils.put(activity, AppConfig.USERTOKEN, userInfo.token);
                SpCommonUtils.put(activity, AppConfig.FINALUSERID, userInfo.user_id);
                SpCommonUtils.put(activity, AppConfig.USERPHOTO, view.getPhoneNum());
                SpCommonUtils.put(activity, AppConfig.USERPWD, view.getPassword());


                SpCommonUtils.put(activity, AppConfig.FINALUAVATAR, userInfo.avatar);
                SpCommonUtils.put(activity, AppConfig.FINALNICKNAME, userInfo.nickname);
                SpCommonUtils.put(activity, AppConfig.FINALSLOGAN, userInfo.slogan);
                SpCommonUtils.put(activity, AppConfig.FINALGENDEREEE, userInfo.gender + "");
                SpCommonUtils.put(activity, AppConfig.FINALEMEMAIL, userInfo.email);


                SpCommonUtils.put(activity, AppConfig.FINAL_NO_READ_NUM, userInfo.no_read_num);
                SpCommonUtils.put(activity, AppConfig.FINAL_NUM_TRAIN_NUM, userInfo.my_num.train_num);
                SpCommonUtils.put(activity, AppConfig.FINAL_NUM_FULL_JOB_NUM, userInfo.my_num.full_job_num);
                SpCommonUtils.put(activity, AppConfig.FINAL_NUM_FULL_PARK_NUM, userInfo.my_num.part_job_num);
                SpCommonUtils.put(activity, AppConfig.FINAL_NUM_FULL_HULP_NUM, userInfo.my_num.help_num);
                SpCommonUtils.put(activity, AppConfig.FINAL_NUM_FULL_HULP_NICKNAME, userInfo.nickname);
                SpCommonUtils.put(activity, AppConfig.FINAL_NUM_FULL_TYPE, userInfo.type);
                SpCommonUtils.put(activity, AppConfig.IS_LOGIN_APP, true);

                SpCommonUtils.put(activity, AppConfig.FINAL_NUM_FULL_AUTH_STATUS, userInfo.auth_status);
                SpCommonUtils.put(activity, AppConfig.FINAL_NUM_FULL_VIP_LEVEL, userInfo.vip_level);

//                ObjectSaveUtil.saveObject(activity, bean.parseObject(UserInfo.class));
                SPUtils.getInstance().saveObject(activity, Constants.USER_INFO, bean.parseObject(UserInfo.class));
                SPUtils.getInstance().saveObject(activity, Constants.USER_ACCOUNT, view.getPhoneNum());
                AccountManger.getUserInfo(activity);
                EventBus.getDefault().post(Constants.EB_LOGIN_SUCCESS);

                MyAsyncTask myAsyncTask = new MyAsyncTask();
                myAsyncTask.execute(userInfo.avatar);
                loginActivity.finish();
            }

            @Override
            public void requestFailed(String error) {
                Log.i("登录", error.toString());
                Toast.getInstance().showErrorToast(activity, error);
            }

            @Override
            public void requestFinish() {
                view.hideLoading();
            }
        });
    }

    private class MyAsyncTask extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            // TODO Auto-generated method stub
            super.onPostExecute(bitmap);

        }

        @Override
        protected Bitmap doInBackground(String... params) {
            String url = params[0];
            Bitmap bitmap = null;
            try {

                URLConnection connection = new URL(url).openConnection();
                InputStream is = connection.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(is);//封装一下输入流
                bitmap = BitmapFactory.decodeStream(bis);//解析
                String path = BitmapSave.saveBitmap(activity, bitmap);
                AppConfig.LOGINPHOTOTPATH = path;
                SpCommonUtils.put(activity, AppConfig.FINAL_SAVE_PHOTO_PATH, path);
                is.close();
                bis.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }


    }

}
