package com.example.framwork;

import android.app.Application;
import android.content.Context;
import android.util.SparseLongArray;

import com.example.framwork.baseapp.BaseAppConfig;
import com.yanzhenjie.nohttp.InitializationConfig;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.OkHttpNetworkExecutor;

/**
 *
 * @author lenovo
 * @date 2017/1/17
 */

public class BaseApplictaion extends Application {
    private static Context _instance;
    // 用于存放倒计时时间
    public static SparseLongArray timeMap;
    private final int TIME_OUT = 20 * 1000;

    @Override
    public void onCreate() {
        super.onCreate();
        _instance = this;
        initNohttp();
        BaseAppConfig.init(this, "ethereum");
    }

    public static Context getContext() {
        return _instance;
    }


    //初始化nohttp
    private void initNohttp() {
//        NoHttp.initialize(this, new NoHttp.Config()
//                // 设置全局连接超时时间，单位毫秒
//                .setConnectTimeout(TIME_OUT)
//                // 设置全局服务器响应超时时间，单位毫秒
//                .setReadTimeout(TIME_OUT)
//                .setNetworkExecutor(new OkHttpNetworkExecutor())
//        );
        InitializationConfig config = InitializationConfig.newBuilder(this)
                .connectionTimeout(TIME_OUT)
                .readTimeout(TIME_OUT)
                .networkExecutor(new OkHttpNetworkExecutor())
                .build();
        NoHttp.initialize(config);


    }
}