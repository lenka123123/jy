package com.mcxtzhang.swipemenulib.customview.listdialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.mcxtzhang.swipemenulib.R;

import java.util.List;

public class CenterDialog {

    private Activity activity;

    public CenterDialog(Activity activity) {
        this.activity = activity;
    }


    public interface AlertOnClickListener {
        void alertClick(String age);
    }

    public void setAlertOnClickListener(ChangeISNOPicker.AlertOnClickListener alertOnClickListener) {
        this.alertOnClickListener = alertOnClickListener;
    }

    public ChangeISNOPicker.AlertOnClickListener alertOnClickListener;

    public void showDialog(final String title, int resource) {
        final AlertDialog dlg = new AlertDialog.Builder(activity).create();
        dlg.show();

        WindowManager m = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        Display d = m.getDefaultDisplay();  //为获取屏幕宽、高
        WindowManager.LayoutParams p = dlg.getWindow().getAttributes();  //获取对话框当前的参数值

        p.width = (int) d.getWidth() * 2 / 3;    //宽度设置为屏幕的0.5
        dlg.getWindow().setAttributes(p);     //设置生效
        dlg.setCanceledOnTouchOutside(false); //按对话框以外的地方不起作用。按返回键还起作用

        Window window = dlg.getWindow();
        window.setGravity(Gravity.CENTER);//设置弹框在屏幕的下方
        window.setContentView(R.layout.activity_center_dialog);


        ImageView img = (ImageView) window.findViewById(R.id.pay_img);
        TextView textTitle = (TextView) window.findViewById(R.id.pay_text);
        TextView cancel = (TextView) window.findViewById(R.id.cancel);
        textTitle.setText(title);
        img.setImageResource(resource);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dlg.dismiss();
                alertOnClickListener.alertClick(title);
            }
        });

    }

}
