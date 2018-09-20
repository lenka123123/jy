package sinia.com.baihangeducation.supplement.tool;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;

import com.mcxtzhang.swipemenulib.customview.GlideLoadUtils;

import sinia.com.baihangeducation.MyApplication;
import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.supplement.base.Goto;

import java.util.HashMap;


/**
 * Created by wanjingyu on 2016/6/28.
 */
public class BaseRequestInfo {
    private static BaseRequestInfo instance;


    /**
     * 创建一个单例类
     */
    public static BaseRequestInfo getInstance() {
        if (instance == null) {
            synchronized (BaseRequestInfo.class) {
                if (instance == null)
                    instance = new BaseRequestInfo();
            }
        }
        return instance;
    }

    /**
     * 公共请求参数
     *
     * @param act
     * @param app
     * @param needLogin 是否需要强制登录
     * @return
     */
    public HashMap getRequestInfo(final Context context, String act, String app, boolean needLogin) {

//        AppConfig = new AppConfig();
        HashMap<String, Object> info = new HashMap<>();
        info.clear();
        info.put("act", act);
        info.put("app", app);
        info.put("device", "2");

        //每次传
        info.put("app_version", AppConfig.getAppVersion((Activity) context));
//        Log.i("当前版本号",AppConfig.getAppVersion((Activity) context));
        if (needLogin) {

            if (!AppConfig.ISlOGINED) {

//                new AlertDialog.Builder(context).setTitle("提示！").setMessage("您尚未登录，请先登录。").setPositiveButton("登录", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Goto.toLogin(context);
//                    }
//                }).setNegativeButton("取消", null).show();
                return info;
            }

            //  2018/8/23 0023 全局token]
            if (AppConfig.ISlOGINED) {
                if (!AppConfig.USERID.equals("") && !AppConfig.TOKEN.equals("")) {
                    info.put("user_id", AppConfig.USERID);
                    info.put("token", AppConfig.TOKEN);
                }
            }
        }
        return info;
    }

    /**
     * 公共请求参数
     *
     * @return
     */
//    public HashMap getRequestInfo(Context context) {
//        HashMap<String, Object> info = new HashMap<>();
//        info.clear();
//        info.put("device", "2");
//        JYBApplication application = (JYBApplication) context.getApplicationContext();
//        if (!TextUtils.isEmpty(application.getUserInfo().user_id)){
//            info.put("user_id", application.getUserInfo().user_id);
//            info.put("token", application.getUserInfo().token);
//        }
//        return info;
//    }
}
