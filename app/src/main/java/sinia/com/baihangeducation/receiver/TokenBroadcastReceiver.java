package sinia.com.baihangeducation.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.example.framwork.utils.LogUtils;

/**
 * 创建日期：2018/6/11 on 16:16
 * 描述:
 * 作者:yuxd Administrator
 */
public class TokenBroadcastReceiver extends BroadcastReceiver {
    public static final String TAG = "MyBroadcastReceiver";
    //  if (response.code() == 50103 || response.code() == 50102) {

    @Override
    public void onReceive(final Context context, Intent intent) {
        LogUtils.i("TAG: ", "MyBroadcastReceiver======");
        if ("com.feichang.a91consultant.main.broadcast110".equals(intent.getAction())) {
            String code = intent.getStringExtra("code");
            LogUtils.i("TAG: ", "MyBroadcastReceiver===" + code + "===");
            if ("50102".equals(code)) {  //可以刷新

            } else {
                showDialog(context);
            }
        }
    }

    public void showDialog(final Context context) {

    }



}
