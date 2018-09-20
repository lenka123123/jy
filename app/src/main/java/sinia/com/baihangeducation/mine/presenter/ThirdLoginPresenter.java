package sinia.com.baihangeducation.mine.presenter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.example.framwork.base.BasePresenter;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.OnRequestListener;
import com.example.framwork.utils.CommonUtil;
import com.example.framwork.utils.SPUtils;
import com.example.framwork.utils.SpCommonUtils;
import com.example.framwork.utils.Toast;
import com.example.framwork.utils.UserInfo;
import com.mcxtzhang.swipemenulib.utils.BitmapSave;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.greenrobot.eventbus.EventBus;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.mine.model.AccountManger;

import com.mcxtzhang.swipemenulib.info.bean.ThirdLoginInfo;

import sinia.com.baihangeducation.mine.view.IThirdLoginView;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;
import sinia.com.baihangeducation.supplement.base.Goto;

import com.mcxtzhang.swipemenulib.utils.Constants;

/**
 * Created by gao on 2017/5/4.
 */

public class ThirdLoginPresenter extends BasePresenter {
    public Activity activity;
    public IThirdLoginView view;
    private ThirdLoginInfo postInfo;
    private SHARE_MEDIA platform;
    private UMShareAPI mShareAPI;
    private UMAuthListener listener;
    private   GetBaseInfoPresenter getBaseInfoPresenter;

    public ThirdLoginPresenter(Activity activity, IThirdLoginView view) {
        super(activity);
        this.activity = activity;
        this.view = view;
        getBaseInfoPresenter = new GetBaseInfoPresenter(activity);
    }

    public ThirdLoginPresenter(Activity activity, IThirdLoginView thirdBindView, ThirdLoginInfo info) {
        super(activity);
        this.activity = activity;
        this.view = thirdBindView;
        this.postInfo = info;
    }

    /**
     * 第三方登录
     *
     * @param thirdLoginInfo
     * @param platform
     * @param mShareAPI      //     * @param listener
     */
    public void bindLogin(ThirdLoginInfo thirdLoginInfo, final SHARE_MEDIA platform, final UMShareAPI mShareAPI) {
        this.postInfo = thirdLoginInfo;
        this.platform = platform;
        this.mShareAPI = mShareAPI;
        this.listener = listener;
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "thirdAccountLogin", "default", false);
        info.put("type", postInfo.third_type);
        info.put("union_id", postInfo.uuid);
        info.put("device_id", CommonUtil.getAndroidId(activity));
        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                if (bean == null) {
                    return;
                }
                UserInfo info = bean.parseObject(UserInfo.class);
                if (bean.isSuccess()) {
                    if (info.is_bind == 1) {
                        Log.i("是否已经绑定", "绑定");

                        loginSuccessBack(bean);
                        Goto.toMainActivity(activity);
                    } else {
                        Log.i("是否已经绑定", "未绑定");
                        Goto.toThirdBind(activity, postInfo.uuid, postInfo.third_type + "");
                    }
                } else {
                    Toast.getInstance().showToastView(activity, bean.getMessage());
                }
            }

            @Override
            public void requestFailed(String error) {
                Toast.getInstance().showToastView(activity, error);
            }

            @Override
            public void requestFinish() {
                view.hideLoading();
            }
        });
    }

    /**
     * 第三方绑定
     */
    public void bindAccount() {
        if (AccountManger.checkThridBind(activity, view.getPhoneNum(), view.getVcode())) {
            HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "thirdAccountBind", "default", false);
            info.put("mobile", view.getPhoneNum());
            info.put("vcode", view.getVcode());
            info.put("account_type", view.getUserType());
            info.put("type", postInfo.third_type);
            info.put("union_id", postInfo.uuid);
            info.put("device_id", CommonUtil.getAndroidId(activity));
            post(info, new OnRequestListener() {
                @Override
                public void requestSuccess(BaseResponseBean bean) {
                    if (bean == null) {
                        return;
                    }
                    if (bean.isSuccess()) {
                        loginSuccessBack(bean);
//                        BindingPhoneActivity.activity.finish();
                    } else {
                        view.hideLoading();
                        Toast.getInstance().showToastView(activity, TextUtils.isEmpty(bean.getMessage()) ? "登录失败" : bean.getMessage());
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
    }


    private void loginSuccessBack(BaseResponseBean bean) {

        UserInfo userInfo = bean.parseObject(UserInfo.class);
        AppConfig.ISlOGINED = true;
        AppConfig.TOKEN = userInfo.token;
        AppConfig.USERID = userInfo.user_id;
        AppConfig.USERIDTYPE = userInfo.type;
        System.out.println("userInfouserInfouserIns试试fo.type" + userInfo.type);
//        getBaseInfoPresenter.getBaseInfoLoginAfter(AppConfig.TOKEN, AppConfig.USERID);

        SpCommonUtils.put(activity, AppConfig.USERTOKEN, userInfo.token);
        SpCommonUtils.put(activity, AppConfig.FINALUSERID, userInfo.user_id);
        SpCommonUtils.put(activity, AppConfig.USERPHOTO, view.getPhoneNum());


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


//                ObjectSaveUtil.saveObject(activity, bean.parseObject(UserInfo.class));
        SPUtils.getInstance().saveObject(activity, Constants.USER_INFO, bean.parseObject(UserInfo.class));
        SPUtils.getInstance().saveObject(activity, Constants.USER_ACCOUNT, view.getPhoneNum());
        AccountManger.getUserInfo(activity);
        EventBus.getDefault().post(Constants.EB_LOGIN_SUCCESS);

        MyAsyncTask myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute(userInfo.avatar);



//        SPUtils.getInstance().saveObject(activity, Constants.USER_INFO, bean.parseObject(UserInfo.class));
//        SPUtils.getInstance().saveObject(activity, Constants.USER_ACCOUNT, postInfo.mobile);
//        AccountManger.getUserInfo(activity);
//        EventBus.getDefault().post(Constants.EB_LOGIN_SUCCESS);
//        LoginActivity.activity.finish();
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
