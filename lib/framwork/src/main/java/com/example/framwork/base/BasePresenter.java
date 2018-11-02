package com.example.framwork.base;

import android.app.Activity;

import com.example.framwork.noHttp.OnRequestListener;
import com.example.framwork.noHttp.OnRequestNewListener;
import com.yanzhenjie.nohttp.rest.Request;

import java.util.HashMap;

/**
 * Created by wanjingyu on 2016/10/8.
 */

public abstract class BasePresenter {
    public CommonModel model;
    protected Activity activity;

    public BasePresenter(Activity activity) {
        this.model = new CommonModel();
        this.activity = activity;
    }


    /**
     * 当请求里面需要拼接普通参数意外的数据时调用  如上传文件  先获取request
     * // 添加Bitmap
     * request.add("head", new BitmapBinary(bitmap))
     * // 添加File
     * request.add("head", new FileBinary(file))
     * // 添加ByteArray
     * request.add("head", new ByteArrayBinary(byte[]))
     * // 添加InputStream
     * request.add("head", new InputStreamBinary(inputStream));
     * <p>
     * 最后要调用model.execute
     *
     * @param info
     * @return
     */
    public Request<String> postFile(HashMap info) {
        return model.resultFileModel(info);
    }

    /**
     * 自定义requestlistener
     *
     * @param info
     * @param requestListener
     */
    public void post(HashMap info, OnRequestListener requestListener) {

        if ((info.get("act")).equals("login")){
            model.resultPostModel(activity, info, requestListener,1);
        }else {
            model.resultPostModel(activity, info, requestListener,0);
        }

    }

    public void postCustomUrl(HashMap info, String url, OnRequestListener requestListener) {
        model.resultCustomUrlModel(activity, info, url, requestListener);
    }

    public void postCustonUrl(HashMap info, String url, OnRequestListener requestListener) {
        model.resultCustomUrlModel(activity, info, url, requestListener);
    }

    public void get(HashMap info, String url, OnRequestNewListener requestListener) {
        model.resultGetCommonModel(activity, info, url, requestListener);
    }

}
