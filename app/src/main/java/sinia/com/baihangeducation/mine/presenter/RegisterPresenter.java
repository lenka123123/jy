package sinia.com.baihangeducation.mine.presenter;


import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.example.framwork.base.BasePresenter;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.OnRequestListener;
import com.example.framwork.utils.CommonUtil;
import com.example.framwork.utils.SPUtils;
import com.example.framwork.utils.SpCommonUtils;
import com.example.framwork.utils.Toast;
import com.example.framwork.utils.UserInfo;

import cn.jpush.im.android.api.JMessageClient;
import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.mine.model.AccountManger;
import sinia.com.baihangeducation.mine.view.IRegisterView;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;

import com.mcxtzhang.swipemenulib.utils.BitmapSave;
import com.mcxtzhang.swipemenulib.utils.Constants;

import org.greenrobot.eventbus.EventBus;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

/**
 * Created by Administrator on 2018.03.07.
 */

public class RegisterPresenter extends BasePresenter {

    private Activity activity;
    private IRegisterView view;
    private final GetBaseInfoPresenter getBaseInfoPresenter;

    public RegisterPresenter(Activity activity, IRegisterView view) {
        super(activity);
        getBaseInfoPresenter = new GetBaseInfoPresenter(activity);
        this.activity = activity;
        this.view = view;
    }


    /**
     * 注册
     */
    public void register(String channelCode) {
        System.out.println("=======channelCode==" + channelCode);
        if (!AccountManger.checkupRegisterInfo(activity, view.getphoneNum(), view.getAuthCode(), view.getPassword(), view.getPasswordAgain(), view.getIsRead())) {
            return;
        }
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "register", "default", false);
        info.put("mobile", view.getphoneNum());
        info.put("password", view.getPassword());
        info.put("vcode", view.getAuthCode());
        info.put("device_id", CommonUtil.getAndroidId(activity));
        if (!channelCode.equals(""))
            info.put("channel", channelCode);
//        view.showLoading();
        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {

                loginSuccessBack(bean);
                view.succress();

            }

            @Override
            public void requestFailed(String error) {
                Toast.getInstance().showErrorToast(activity, error);
            }

            @Override
            public void requestFinish() {
//                view.hideLoading();
            }
        });

    }

    /**
     * 注册成功储存用户信息
     *
     * @param bean
     */
    private void loginSuccessBack(BaseResponseBean bean) {
        JMessageClient.logout();
        UserInfo userInfo = bean.parseObject(UserInfo.class);
        AppConfig.ISlOGINED = true;
        AppConfig.TOKEN = userInfo.token;
        AppConfig.USERID = userInfo.user_id;
        AppConfig.USERIDTYPE = userInfo.type;
        System.out.println("userInfouserInfouserIns试试fo.type" + userInfo.type);
//        getBaseInfoPresenter.getBaseInfoLoginAfter(AppConfig.TOKEN, AppConfig.USERID);

        SpCommonUtils.put(activity, AppConfig.USERTOKEN, userInfo.token);
        SpCommonUtils.put(activity, AppConfig.FINALUSERID, userInfo.user_id);
        SpCommonUtils.put(activity, AppConfig.USERPHOTO, view.getphoneNum());
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
        SPUtils.getInstance().saveObject(activity, Constants.USER_ACCOUNT, view.getphoneNum());
        AccountManger.getUserInfo(activity);
        EventBus.getDefault().post(Constants.EB_LOGIN_SUCCESS);

        RegisterPresenter.MyAsyncTask myAsyncTask = new RegisterPresenter.MyAsyncTask();
        myAsyncTask.execute(userInfo.avatar);
        view.succress();
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
