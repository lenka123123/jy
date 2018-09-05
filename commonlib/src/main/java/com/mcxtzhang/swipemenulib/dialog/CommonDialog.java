package com.mcxtzhang.swipemenulib.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.framwork.utils.DialogUtils;
import com.example.framwork.utils.Toast;
import com.mcxtzhang.swipemenulib.R;
import com.mcxtzhang.swipemenulib.info.bean.VersionInfo;


/**
 * Created by gao on 2017/3/27.
 */

public class CommonDialog {

    /**
     * 内部类实现单例模式
     * 延迟加载，减少内存开销
     *
     * @author gaoy
     */
    private static class SingletonHolder {
        private static CommonDialog instance = new CommonDialog();
    }

    /**
     * 私有的构造函数
     */
    private CommonDialog() {

    }

    public static CommonDialog getInstance() {
        return SingletonHolder.instance;
    }

    public static void showMobileDialog(final Context context, final String mobile) {
        final Dialog authDialog = DialogUtils.getInstance().getCenterDialog(context, R.layout.contact_service_dialog);
        TextView tvContent = (TextView) authDialog.findViewById(R.id.tv_contact_service_content);
        Button btnLeft = (Button) authDialog.findViewById(R.id.btn_contact_service_left);
        Button btnRight = (Button) authDialog.findViewById(R.id.btn_contact_service_right);
        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authDialog.dismiss();
            }
        });
        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mobile)) {
                    Toast.getInstance().showWarningToast(context, "没有联系方式");
                    return;
                }
                if (TextUtils.isEmpty(mobile)) {
                    Toast.getInstance().showDefaultToast(context, "暂无咨询电话");
                    return;
                }
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse("tel:" + mobile);
                intent.setData(data);
                context.startActivity(intent);
                authDialog.dismiss();
            }
        });
        tvContent.setText(mobile);
        btnLeft.setText(R.string.cancel);
        btnRight.setText(R.string.dialog_confirm);
        authDialog.show();
    }



    /**
     * 版本更新强制
     *
     * @param context
     */
    public static ForceUpdateDialog versionUpdateForceDilog(Activity context, VersionInfo data) {
        ForceUpdateDialog dialog = new ForceUpdateDialog(context, data);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }
}
