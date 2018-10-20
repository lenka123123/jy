package sinia.com.baihangeducation.mine.presenter;

import android.app.Activity;
import android.text.TextUtils;
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

import sinia.com.baihangeducation.mine.model.AccountManger;

import com.mcxtzhang.swipemenulib.info.bean.RealNameInfo;

import sinia.com.baihangeducation.mine.view.IRealNameView;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;

/**
 * Created by Administrator on 2018/3/23.
 */

public class RealNamePresenter extends BasePresenter {
    private Activity activity;
    private IRealNameView view;

    public RealNamePresenter(Activity activity, IRealNameView view) {
        super(activity);
        this.activity = activity;
        this.view = view;
    }

    public void doRealNameAuthentication() {

        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "setAuthentication", "ucenter", true);
        info.put("realname", view.getRealName());
        info.put("idcard_no", view.getIDNum());
        Request<String> request = postFile(info);
        if (!TextUtils.isEmpty(view.getIDCard_OnHand()) && !TextUtils.isEmpty(view.getIDCard_On()) && !TextUtils.isEmpty(view.getIDCard_Off())) {

            request.add("idcard_hand_img", new File(BitmapUtil.compressImageUpload(view.getIDCard_OnHand())));//new FileBinary(new File(view.getIDCard_OnHand())));
            request.add("idcard_face_img", new File(BitmapUtil.compressImageUpload(view.getIDCard_On())));
            request.add("idcard_opposite_img", new File(BitmapUtil.compressImageUpload(view.getIDCard_Off())));
            view.showLoading();
            model.execute(activity, request, new OnRequestListener() {
                @Override
                public void requestSuccess(BaseResponseBean bean) {
                    Toast.getInstance().showSuccessToast(activity, "您已成功提交认证资料，请耐心等候");
                    view.submitSucess();
                }

                @Override
                public void requestFailed(String error) {
                    Toast.getInstance().showSuccessToast(activity, error);
                    view.submitFailed();
                }

                @Override
                public void requestFinish() {
                }
            });

        }
    }

    public void getRealNameInfo() {
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "getAuthentication", "ucenter", true);
        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                Log.i("获取实名认证数据", bean.toString());
                RealNameInfo info = bean.parseObject(RealNameInfo.class);
                view.getRealNameSuccess(info);
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
