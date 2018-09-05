package sinia.com.baihangeducation.supplement.tool;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;

import com.example.framwork.utils.CommonUtil;
import com.example.framwork.utils.SPUtils;
import com.example.framwork.utils.UserInfo;
import com.mcxtzhang.swipemenulib.utils.Constants;

import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.supplement.base.Goto;
import sinia.com.baihangeducation.MyApplication;


/**
 * Created by Administrator on 2018/4/8.
 */

public class BaseUtil {

    /**
     * 判断用户是否登录
     *
     * @param context
     * @return
     */
    public static boolean isLogin(final Context context, MyApplication application) {


        if (!AppConfig.ISlOGINED) {

            new AlertDialog.Builder(context).setTitle("提示！").setMessage("您尚未登录，请先登录。").setPositiveButton("登录", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Goto.toLogin(context);
                }
            }).setNegativeButton("取消", null).show();

            return false;
        } else if (CommonUtil.isFastClick()) {
            return false;
        }
        return true;
    }
}
