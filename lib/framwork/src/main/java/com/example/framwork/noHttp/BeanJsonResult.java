package com.example.framwork.noHttp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.example.framwork.baseapp.BaseAppConfig;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.Bean.BaseResponseGetBean;
import com.example.framwork.noHttp.Bean.IsLoginInfo;
import com.example.framwork.utils.EncryptUtil;
import com.example.framwork.utils.LongLongUtil;
import com.example.framwork.utils.Toast;
import com.yanzhenjie.nohttp.Headers;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RestRequest;
import com.yanzhenjie.nohttp.rest.StringRequest;

import java.io.File;
import java.io.PrintStream;


/**
 * Created by ${wjw} on 2016/3/31.
 */
public class BeanJsonResult<T> extends RestRequest<T> {
    public static String loginHint = "尚未登录，请登录";
    private Class<T> clazz;
    public static LoginRestLinstener loginRestLinstener;

    public static void setLoginRestLinstener(LoginRestLinstener loginRestLinstener) {
        BeanJsonResult.loginRestLinstener = loginRestLinstener;
    }

    public BeanJsonResult(String url, RequestMethod requestMethod, Class<T> clazz) {
        super(url, requestMethod);
        this.clazz = clazz;
    }

    public BeanJsonResult(String url, Class<T> clazz) {
        this(url, RequestMethod.GET, clazz);
    }

    @Override
    public T parseResponse(Headers responseHeaders, byte[] responseBody) {
        String res = StringRequest.parseResponseString(responseHeaders, responseBody);
        try {
            return JSON.parseObject(res, clazz);
        } catch (Exception e) {

            try {
                // 所以传进来的JavaBean一定要提供默认无参构造
                return clazz.newInstance();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        return null;
    }

    public interface LoginRestLinstener {
        void loginRest(Activity activity);
    }

    public static void execute(final Activity activity, final Request request, final OnRequestListener requestListener) {
        CallServer.getRequestInstance().add(activity, request, new HttpCallBack<String>() {
            @Override
            public void onSucceed(int what, String response) {
                if (activity.isFinishing()) {
                    return;
                }
                System.out.println("===========start============");
                System.out.println("start_response==" + response + "===" + what);
                System.out.println("===========end============");


                if (!TextUtils.isEmpty(response.trim()) && !response.contains("403 Forbidden")) {
                    BaseResponseBean bean = null;
                    try {

                        bean = BaseResponseBean.parseObj(EncryptUtil.getInstance().decodeValue(response), BaseResponseBean.class);
                        System.out.println("===========start============");
                        System.out.println(bean.toString());
                        System.out.println("===========end============");


                        if (!TextUtils.isEmpty(bean.getCode()) && bean.getCode().equals("300001")) {
//                            if (loginRestLinstener != null) {
//                                loginRestLinstener.loginRest(activity);
//                            }
//                            gotoLogin(activity);
                            if (towMin("300001"))
                                requestListener.requestFailed("尚未登录，请登录");
                        } else if (bean.getData() == null || TextUtils.isEmpty(bean.getData())) {
                            if (bean.getCode().equals("9108")) {
                                requestListener.requestFailed(bean.getCode());
                            } else {
                                requestListener.requestFailed(bean.getMessage());
                            }

                        } else if (!bean.isSuccess()) {
                            IsLoginInfo loginInfo = bean.parseObject(IsLoginInfo.class);
                            if (loginInfo != null && loginInfo.is_need_jump_login == 1) {
//                                requestListener.requestFailed("尚未登录，请登录");

                                if (towMin("300001"))
                                    gotoLogin(activity);

//                                if (bean.getCode().equals("90000")){
//                                }else {
//                                    gotoLogin(activity);
//                                }

//                                if (loginRestLinstener != null) {
//                                    loginRestLinstener.loginRest(activity);
//                                }
                            } else {
                                requestListener.requestFailed(TextUtils.isEmpty(bean.getMessage()) ? "获取数据失败" : bean.getMessage());
                            }
                        } else {
                            requestListener.requestSuccess(bean);
                        }

                    } catch (Exception e1) {
                        e1.printStackTrace();
                        requestListener.requestFailed("操作失败,请稍后再试");

                    }
                } else {
                    requestListener.requestFailed("操作失败,请稍后再试");
                }
            }

            @Override
            public void onFailed(int what, String exception) {
                if (activity.isFinishing()) {
                    return;
                }
                if (exception.equals("9108")) {
                    requestListener.requestFailed(exception);
                } else {
                    requestListener.requestFailed(exception);
                }
            }

            @Override
            public void onFinish() {
                if (activity.isFinishing()) {
                    return;
                }
                requestListener.requestFinish();
            }

        }, 0, false);
    }


    /**
     * 无加密请求
     *
     * @param activity
     * @param request
     * @param requestListener
     */
    public static void executeNojiami(final Activity activity, Request<String> request, final OnRequestListener requestListener) {
        CallServer.getRequestInstance().add(activity, request, new HttpCallBack<String>() {
            @Override
            public void onSucceed(int what, String response) {
                if (activity.isFinishing()) {
                    return;
                }
                if (!TextUtils.isEmpty(response)) {
//                    Logger.d("返回原始数据:" + response);
                    BaseResponseBean bean = null;
                    try {
                        bean = JSON.parseObject(response, BaseResponseBean.class);
                        requestListener.requestSuccess(bean);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                        Toast.getInstance().showErrorToast(activity, "获取数据失败");
                    }

                } else {
                    Toast.getInstance().showErrorToast(activity, "获取数据失败");
                }
            }

            @Override
            public void onFailed(int what, String exception) {
                if (activity.isFinishing()) {
                    return;
                }

                requestListener.requestFailed(exception);
            }

            @Override
            public void onFinish() {
                if (activity.isFinishing()) {
                    return;
                }
                requestListener.requestFinish();
            }

        }, 0, false);
    }

    public static void executeCommon(final Activity activity, final Request request, final OnRequestNewListener requestListener) {
        CallServer.getRequestInstance().add(activity, request, new HttpCallBack<String>() {
            @Override
            public void onSucceed(int what, String response) {
                System.out.println("===========start==1==========");
                System.out.println("start_response1==" + response + "===" + what);
                System.out.println("===========end1============");


                if (activity.isFinishing()) {
                    return;
                }
//                Logger.d("返回原始数据:" + response);
                if (!TextUtils.isEmpty(response.trim()) && !response.contains("403 Forbidden")) {
                    BaseResponseGetBean bean = null;
                    try {
                        bean = BaseResponseBean.parseObj(response, BaseResponseGetBean.class);
                        if (bean.getStatus() == null || TextUtils.isEmpty(bean.getStatus())) {
                            requestListener.requestFailed("操作失败,请稍后再试");
                        } else if (bean.getStatus().equals("201")) {
                            requestListener.requestFailed("银行卡号错误");
                        } else {
                            requestListener.requestSuccess(bean);
                        }
//                        Logger.d(bean.toString());
                    } catch (Exception e1) {
                        e1.printStackTrace();
                        requestListener.requestFailed("操作失败,请稍后再试");
                    }
                } else {
                    requestListener.requestFailed("操作失败,请稍后再试");
                }
            }

            @Override
            public void onFailed(int what, String exception) {
                if (activity.isFinishing()) {
                    return;
                }
                requestListener.requestFailed(exception);
            }

            @Override
            public void onFinish() {

                if (activity.isFinishing()) {
                    return;
                }
                requestListener.requestFinish();
            }

        }, 0, false);
    }

    private static void gotoLogin(final Context context) {
        new AlertDialog.Builder(context).setTitle("提示！").setMessage("您尚未登录，请先登录。")
                .setPositiveButton("登录", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.setAction("sinia.com.baihangeducation.login.action");
                        intent.addCategory("android.intent.category.DEFAULT");
                        context.startActivity(intent);
                    }
                }).setNegativeButton("取消", null).show();


    }


    //退出时间
    private static long exitTime = 0;
    private static String exitString = "";

    private static boolean towMin(String message) {
        if ((System.currentTimeMillis() - exitTime) > 2000)  //System.currentTimeMillis()无论何时调用，肯定大于2000
        {
            exitString = message;
            exitTime = System.currentTimeMillis();
            return true;
        } else {
            if (exitString.equals(message)) {
                return false;
            }
            return true;
        }

    }
}
