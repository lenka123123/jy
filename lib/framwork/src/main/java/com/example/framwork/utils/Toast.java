package com.example.framwork.utils;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.framwork.R;
import com.sdsmdg.tastytoast.TastyToast;

/**
 * Created by mc on 16/12/16.
 */

public class Toast {
    /**
     * 内部类实现单例模式
     * 延迟加载，减少内存开销
     *
     * @author xuzhaohu
     */
    private static class SingletonHolder {
        private static Toast instance = new Toast();
    }

    /**
     * 私有的构造函数
     */
    private Toast() {

    }

    public static Toast getInstance() {
        return Toast.SingletonHolder.instance;
    }

    public void showSuccessToast(Context context, String message) {
        if (context == null || TextUtils.isEmpty(message)) {
            return;
        }
        TastyToast.makeText(context, message, TastyToast.LENGTH_SHORT,
                TastyToast.SUCCESS);
    }

    public void showWarningToast(Context context, String message) {
        TastyToast.makeText(context, message, TastyToast.LENGTH_SHORT,
                TastyToast.WARNING);
    }

    public void showErrorToast(Context context, String message) {
        if (towMin(message))
            TastyToast.makeText(context, message, TastyToast.LENGTH_SHORT, TastyToast.ERROR);
    }

    public void showInfoToast(Context context, String message) {
        TastyToast.makeText(context, message, TastyToast.LENGTH_SHORT,
                TastyToast.INFO);
    }

    public void showDefaultToast(Context context, String message) {
        TastyToast.makeText(context, message, TastyToast.LENGTH_SHORT,
                TastyToast.DEFAULT);
    }


    public void showConfusingToast(Context context, String message) {
        TastyToast.makeText(context, message, TastyToast.LENGTH_SHORT,
                TastyToast.CONFUSING);
    }

    public void showToastView(Context context, String text) {
        android.widget.Toast toast = new android.widget.Toast(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.toast_view, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.toast_image);
        imageView.setVisibility(View.GONE);
        TextView t = (TextView) view.findViewById(R.id.toast_text);
        t.setText(text);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(android.widget.Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.show();
    }

    //退出时间
    private long exitTime = 0;
    private String exitString = "";

    private boolean towMin(String message) {
        if ((System.currentTimeMillis() - exitTime) > 2000)  //System.currentTimeMillis()无论何时调用，肯定大于2000
        {
            exitString = message;
            exitTime = System.currentTimeMillis();
            return true;
        } else {
            if (exitString.equals(message)) {
                return false;
            }
            return true;
        }

    }

}
