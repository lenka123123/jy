package sinia.com.baihangeducation.supplement.base;

import android.app.Activity;
import android.content.Context;

import com.alibaba.fastjson.JSONObject;
import com.example.framwork.base.BasePresenter;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.OnRequestListener;
import com.example.framwork.utils.Toast;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.HashMap;

import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.newcampus.tabs.searchfriend.bean.SearchFriendListBean;
import sinia.com.baihangeducation.newcampus.tabs.searchfriend.interfaces.AddFollowDataListener;
import sinia.com.baihangeducation.newcampus.tabs.searchfriend.interfaces.FollowDataListener;
import sinia.com.baihangeducation.newcampus.tabs.searchfriend.interfaces.SearchFriendDataListener;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;

public class CommonModel extends BasePresenter {

    private Context context;

    public CommonModel(Context activity) {
        super((Activity) activity);
        context = activity;
    }


    public void addFollow(String follow_user_id, final FollowDataListener listener) {
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(context, "addFollow", "ucenter", false);
        info.put("user_id", AppConfig.USERID);
        info.put("token", AppConfig.TOKEN);
        info.put("follow_user_id", follow_user_id);
        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                JsonObject obj = new JsonParser().parse(bean.getData()).getAsJsonObject();
                int code = obj.get("is_follow").getAsInt();

                System.out.println(code + "00000000");
                listener.getHotFunDataSuccess(code);
            }

            @Override
            public void requestFailed(String error) {
                Toast.getInstance().showErrorToast(context, error);
                listener.getHotFunDataFail();
            }

            @Override
            public void requestFinish() {

            }
        });

    }


    public void setComment(String type_id, String content, String parent_id, final AddFollowDataListener listener) {
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(context, "setComment", "home", false);
        info.put("user_id", AppConfig.USERID);
        info.put("token", AppConfig.TOKEN);

        info.put("type", "7");
        info.put("type_id", type_id);
        info.put("content", content);
        info.put("parent_id", parent_id);
        System.out.println(type_id + "eee" + content + "eee" + parent_id);

        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {

                listener.getHotFunDataSuccess();
            }

            @Override
            public void requestFailed(String error) {
                Toast.getInstance().showErrorToast(context, error);
                listener.getHotFunDataFail();
            }

            @Override
            public void requestFinish() {

            }
        });

    }
}
