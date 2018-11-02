package sinia.com.baihangeducation.club.editorclubactive;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.framwork.base.BasePresenter;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.OnRequestListener;
import com.example.framwork.utils.Toast;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mcxtzhang.swipemenulib.customview.BitmapUtil;
import com.yanzhenjie.nohttp.rest.Request;

import java.io.File;
import java.util.HashMap;

import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.club.club.interfaces.GetRequestListener;
import sinia.com.baihangeducation.club.editorclubactive.model.ActiveInfoData;
import sinia.com.baihangeducation.club.editorclubactive.model.GetActiveOptionListener;
import sinia.com.baihangeducation.club.editorclubactive.model.GetClubActiveOption;
import sinia.com.baihangeducation.club.editorclubactive.model.ObtainActiveInfoListener;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;

/**
 * 创建日期：2018/5/22 on 11:28
 * 描述:
 * 作者:yuxd Administrator
 */
public class ClubEditorModel extends BasePresenter {


    public ClubEditorModel(Activity activity) {
        super(activity);
    }

    public void getActiveInfo(String activity_id, ObtainActiveInfoListener obtainRankingDataListener) {
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "getActivityInfo", "club", true);
        info.put("user_id", AppConfig.USERID);
        info.put("token", AppConfig.TOKEN);
        info.put("activity_id", activity_id);//


        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                ActiveInfoData info = bean.parseObject(ActiveInfoData.class);
                obtainRankingDataListener.onSuccess(info, 0);
//

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


    public void applyActive(String activity_id, GetRequestListener requestListener) {
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "setActivityApply", "club", true);
        info.put("user_id", AppConfig.USERID);
        info.put("token", AppConfig.TOKEN);
        info.put("activity_id", activity_id);//


        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                Toast.getInstance().showSuccessToast(activity, "申请成功");
                requestListener.setRequestSuccess("");
            }

            @Override
            public void requestFailed(String error) {
                if (error.startsWith("请完善")) {
                    requestListener.setRequestSuccess("请完善");
                    return;
                }
                Toast.getInstance().showErrorToast(activity, error);
                requestListener.setRequestFail();
            }

            @Override
            public void requestFinish() {

            }
        });
    }


    // 获取发布活动选项
    public void getActivityOption(GetActiveOptionListener requestListener) {
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "getActivityOption", "club", true);
        info.put("user_id", AppConfig.USERID);
        info.put("token", AppConfig.TOKEN);
        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                GetClubActiveOption info = bean.parseObject(GetClubActiveOption.class);

                requestListener.onSuccess(info);
            }

            @Override
            public void requestFailed(String error) {
                Toast.getInstance().showErrorToast(activity, error);
                requestListener.onError(error);
            }

            @Override
            public void requestFinish() {

            }
        });
    }


    // 获取发布活动选项

    public void sendActive(String name,
                           String type_id,
                           String club_id,
                           String activeStartTime,
                           String activeStopTime,
                           String mProvinceId,
                           String mCityId,
                           String mDistrictId,
                           String addr,
                           String expenditure,
                           String human_type,
                           String human_num,
                           String introduce,
                           String img,
                           String media_id,
                           ClubEditorActiveActivity activeActivity) {

        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "pushActivity", "club", true);
        info.put("user_id", AppConfig.USERID);
        info.put("token", AppConfig.TOKEN);

        info.put("name", name);
        info.put("type", type_id);
        info.put("club_id", club_id);
        info.put("begin_time", activeStartTime.substring(0, activeStartTime.length() - 3));
        info.put("end_time", activeStopTime.substring(0, activeStopTime.length() - 3));
        info.put("prov_id", mProvinceId);
        info.put("city_id", mCityId);
        info.put("dist_id", mDistrictId);
        info.put("addr", addr);
        System.out.println(human_num + "expenditure == " + expenditure);
        info.put("expenditure", expenditure);
        info.put("human_type", human_type);
        info.put("human_num", human_num);
        info.put("introduce", introduce);
        info.put("cover", img);
        info.put("media_id", media_id);


        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                activeActivity.finish();
                Toast.getInstance().showSuccessToast(activity, "发布成功");
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


    public void updataPhone(String url, String is_jmessage, GetRequestListener listener) {
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "uploadImage", "default", true);
        Log.i("图片地址002", url);
//        url = BitmapUtil.compressImageUpload(url);
//        File file = new File(url);
//        url = AppConfig.IMAGE_PATH + "/add_club_photo.png";

        Request<String> request = postFile(info);
        request.add("is_jmessage", is_jmessage);//new FileBinary(new File(view.getIDCard_OnHand())));
        request.add("image", new File(BitmapUtil.compressImageUpload(url)));//new FileBinary(new File(view.getIDCard_OnHand())));

        model.execute(activity, request, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {

                listener.setRequestSuccess(bean.getData());
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
