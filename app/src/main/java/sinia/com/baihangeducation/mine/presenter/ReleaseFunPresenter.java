package sinia.com.baihangeducation.mine.presenter;

import android.app.Activity;
import android.util.Log;

import com.example.framwork.base.BasePresenter;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.OnRequestListener;
import com.example.framwork.utils.Toast;
import com.mcxtzhang.swipemenulib.customview.BitmapUtil;
import com.yanzhenjie.nohttp.FileBinary;
import com.yanzhenjie.nohttp.rest.Request;

import java.io.File;
import java.util.HashMap;

import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.mine.view.IReleaseFunView;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;

public class ReleaseFunPresenter extends BasePresenter {
    private Activity activity;
    private IReleaseFunView view;
    private int j = 0;

    public ReleaseFunPresenter(Activity activity, IReleaseFunView view) {
        super(activity);
        this.activity = activity;
        this.view = view;
    }

    public void releaseImages(String path, final int j) {
//        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "uploadImage", "default", true);
//
//
//        Request<String> request = postFile(info);
//        if (!TextUtils.isEmpty(path)) {
//            Log.i("图片地址个人中心", path + "未处理");
//            request.add_friend("image", new FileBinary(new File(path)));
//            Log.i("编辑基本资料", path + "处理");
//            view.showLoading();
//            model.execute(activity, request, new OnRequestListener() {
//                @Override
//                public void requestSuccess(BaseResponseBean bean) {
//                    Log.i("编辑基本资料", bean.toString() + "图片");
//                    SingleImageBean imgbean = bean.parseObject(SingleImageBean.class);
//                    j++;
//                    view.releaseSingleImageSuccess(imgbean, j);
//                }
//
//                @Override
//                public void requestFailed(String error) {
//                    Toast.getInstance().showErrorToast(activity, error);
//                }
//
//                @Override
//                public void requestFinish() {
//
//                }
//            });
//        }

        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "pushDynamic", "publish", true);
        Request<String> request = postFile(info);
        if (view.getReleaseFunImgs() != null && view.getReleaseFunImgs().size() > 0) {
            Log.i("获取选择图片size", view.getReleaseFunImgs().size() + "请求中List大小");
            for (int i = 0; i < view.getReleaseFunImgs().size(); i++) {
                request.add("imgs[" + i + "]", new FileBinary(new File(view.getReleaseFunImgs().get(i))));
            }
        }
        view.showLoading();
        model.execute(activity, request, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                Log.i("多图上床", bean.toString() + "图片");
            }

            @Override
            public void requestFailed(String error) {
                Toast.getInstance().showErrorToast(activity, error);
            }

            @Override
            public void requestFinish() {
                view.hideLoading();
            }
        });
    }


    public void releaseFun(int open) {
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "pushDynamic", "publish", true);
        info.put("user_id", AppConfig.USERID);
        info.put("token", AppConfig.TOKEN);
        info.put("content", view.getReleaseFunContent());
        info.put("lng", view.getReleaseFunLng());
        info.put("lat", view.getReleaseFunLat());
        info.put("adcode", view.getReleaseFunAdcode());
        info.put("visible", open);
        Log.i("发布趣事参数", view.getReleaseFunContent() + "内容");
        Log.i("发布趣事参数", view.getReleaseFunLng() + "经度");
        Log.i("发布趣事参数", view.getReleaseFunLat() + "纬度");
        Log.i("发布趣事参数", view.getReleaseFunAdcode() + "城市编码");
        Request<String> request = postFile(info);
        if (view.getReleaseFunImgs() != null && view.getReleaseFunImgs().size() > 0) {
            Log.i("获取选择图片size", view.getReleaseFunImgs().size() + "请求中List大小");
            for (int i = 0; i < view.getReleaseFunImgs().size(); i++) {

                request.add("imgs[" + i + "]", new FileBinary(new File(BitmapUtil.compressImageUpload(view.getReleaseFunImgs().get(i)))));
            }
        }
        view.showLoading();
        model.execute(activity, request, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                Log.i("多图上床", bean.toString() + "图片");
                Toast.getInstance().showSuccessToast(activity, "上传成功");
                view.releaseFunSuccess();
            }

            @Override
            public void requestFailed(String error) {
                Toast.getInstance().showErrorToast(activity, error);
                view.releaseFunFail();
            }

            @Override
            public void requestFinish() {
                view.hideLoading();
            }
        });
    }


}