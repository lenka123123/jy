package com.example.framwork.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.example.framwork.baseapp.BaseAppConfig;
import com.example.framwork.noHttp.BeanJsonResult;
import com.example.framwork.noHttp.FastJsonRequest;
import com.example.framwork.noHttp.OnRequestListener;
import com.example.framwork.noHttp.OnRequestNewListener;
import com.example.framwork.utils.SpCommonUtils;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;


import org.json.JSONObject;

import java.util.HashMap;


/**
 * Created by wanjingyu on 2016/10/8.
 */

public class CommonModel {
    public final String USERTOKEN = "FINALUSERTOKEN";
    public final String FINALUSERID = "FINALUSERID";

    /*
     * 一般post请求是调用*/

    public void resultPostModel(Activity context, HashMap info, OnRequestListener requestListener, int flag) {
        String token = (String) SpCommonUtils.get(context, USERTOKEN, "USERID");
        String userId = (String) SpCommonUtils.get(context, FINALUSERID, "USERID");
        switch (flag) {
            case 1:
                if (info.containsKey("user_id") && info.containsKey("token"))
                    if (info.get("user_id").equals("USERID") || info.get("token").equals("USERID")) {
                        info.remove("user_id");
                        info.remove("token");
                    }

                Request<String> request_other = new FastJsonRequest(BaseAppConfig.SERVICE_PATH,
                        RequestMethod.POST, JSON.toJSONString(info));
                BeanJsonResult.execute(context, request_other, requestListener);
                break;
            case 0:
                // info.put("noAes", "1"); // TODO: 2018/10/15 0015  不加密
                if (info.containsKey("user_id") && info.containsKey("token")) {
                    if (!token.equals("USERID") && !userId.equals("USERID")) {
                        info.put("user_id", userId);
                        info.put("token", token);
                    }

                    if (info.get("user_id").equals("USERID") || info.get("token").equals("USERID") || info.get("token").equals("")) {
                        info.remove("user_id");
                        info.remove("token");
                    }
                    System.out.println(info.get("token") + "=================最后一次传token");
                }

                Request<String> request = new FastJsonRequest(BaseAppConfig.SERVICE_PATH,
                        RequestMethod.POST, JSON.toJSONString(info));
                BeanJsonResult.execute(context, request, requestListener);
                break;
        }
    }

    /*
     * 一般Get请求是调用*/
    public void resultGetCommonModel(Activity context, HashMap info, String
            methodName, OnRequestNewListener requestListener) {
        Request<String> request = NoHttp.createStringRequest(methodName, RequestMethod.GET);
        request.addHeader("Authorization", "APPCODE aff9b2bc697f4ec6b26a7525caf2c07e");
        request.add(info);
        BeanJsonResult.executeCommon(context, request, requestListener);
    }

    /*
     * 自定义URL的model*/
    public void resultCustomUrlModel(Activity context, HashMap info, String
            URL, OnRequestListener requestListener) {
        Request<String> request = new FastJsonRequest(URL,
                RequestMethod.POST, JSON.toJSONString(info));
        BeanJsonResult.execute(context, request, requestListener);
    }

    /*
     * 当需要 添加文件的时候  配合 下面 execute使用
     * */
    public Request<String> resultFileModel(HashMap info) {
        return new FastJsonRequest(BaseAppConfig.SERVICE_PATH,
                RequestMethod.POST, JSON.toJSONString(info));

    }

    /**
     * 需要配合resultFileModel
     *
     * @param context
     * @param request
     * @param requestListener
     */
    public void execute(Activity context, Request<String> request, OnRequestListener
            requestListener) {
        BeanJsonResult.execute(context, request, requestListener);
    }
}
