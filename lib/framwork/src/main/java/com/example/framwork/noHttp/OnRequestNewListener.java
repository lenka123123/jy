package com.example.framwork.noHttp;

import com.example.framwork.noHttp.Bean.BaseResponseGetBean;

/**
 * Created by Administrator on 2017/10/12.
 */

public interface OnRequestNewListener {
    void requestSuccess(BaseResponseGetBean request);

    void requestFailed(String error);

    //用于hide loading
    void requestFinish();
}
