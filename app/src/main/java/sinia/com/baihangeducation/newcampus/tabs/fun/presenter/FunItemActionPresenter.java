package sinia.com.baihangeducation.newcampus.tabs.fun.presenter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;

import com.example.framwork.base.BasePresenter;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.OnRequestListener;
import com.example.framwork.utils.Toast;
import com.example.framwork.utils.UserInfo;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.HashMap;

import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.MyApplication;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;
import sinia.com.baihangeducation.supplement.base.Goto;

public class FunItemActionPresenter extends BasePresenter {
    private Activity activity;
    private UserInfo userInfo;

    public FunItemActionPresenter(Activity activity) {
        super(activity);
        this.activity = activity;
    }

    public void praise(int type, int type_id, final ApproveListener presenterListener) {

        HashMap info;
        if (AppConfig.ISlOGINED) {
            info = BaseRequestInfo.getInstance().getRequestInfo(activity, "setNewPraise", "home", true);
        } else {
//            new AlertDialog.Builder(activity).setTitle("提示！").setMessage("您尚未登录，请先登录。").setPositiveButton("登录", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    Goto.toLogin(activity);
//                }
//            }).setNegativeButton("取消", null).show();
            return;
        }
        info.put("user_id", AppConfig.USERID);
        info.put("token", AppConfig.TOKEN);
        info.put("type", type);
        info.put("type_id", type_id);
        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                // {"is_praise":"1","praise_num":"0"}
                JsonObject obj = new JsonParser().parse(bean.getData()).getAsJsonObject();
                int is_praise = obj.get("is_praise").getAsInt();
                int praise_num = obj.get("praise_num").getAsInt();
                presenterListener.actionSuccess(is_praise, praise_num);
                Log.i("点赞返回结果", bean.toString());
            }

            @Override
            public void requestFailed(String error) {
                presenterListener.actionFail();
                Toast.getInstance().showErrorToast(activity, error);
            }

            @Override
            public void requestFinish() {

            }
        });
    }


    public void praiseFor(int type, int type_id, final PresenterListener presenterListener) {

        HashMap info;
        if (AppConfig.ISlOGINED) {
            info = BaseRequestInfo.getInstance().getRequestInfo(activity, "setNewPraise", "home", true);
        } else {
//            new AlertDialog.Builder(activity).setTitle("提示！").setMessage("您尚未登录，请先登录。").setPositiveButton("登录", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    Goto.toLogin(activity);
//                }
//            }).setNegativeButton("取消", null).show();
            return;
        }
        info.put("user_id", AppConfig.USERID);
        info.put("token", AppConfig.TOKEN);
        info.put("type", type);
        info.put("type_id", type_id);
        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                presenterListener.actionSuccess();
                Log.i("点赞返回结果", bean.toString());
            }

            @Override
            public void requestFailed(String error) {
                presenterListener.actionFail();
                Toast.getInstance().showErrorToast(activity, error);
            }

            @Override
            public void requestFinish() {

            }
        });
    }
}
