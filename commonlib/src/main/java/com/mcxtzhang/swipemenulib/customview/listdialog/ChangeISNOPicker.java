package com.mcxtzhang.swipemenulib.customview.listdialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.TextView;

import com.mcxtzhang.swipemenulib.R;

import java.util.List;

public class ChangeISNOPicker {

    private Activity activity;

    public ChangeISNOPicker(Activity activity) {
        this.activity = activity;
    }


    public interface AlertOnClickListener {
        void alertClick(String age);
    }

    public void setAlertOnClickListener(AlertOnClickListener alertOnClickListener) {
        this.alertOnClickListener = alertOnClickListener;
    }

    public AlertOnClickListener alertOnClickListener;

    public void showAlertDialog(final List<String> list, String title, final TextView textView) {
        final AlertDialog dlg = new AlertDialog.Builder(activity, R.style.dialog_helf_transparent).create();
        dlg.show();

        WindowManager m = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        Display d = m.getDefaultDisplay();  //为获取屏幕宽、高
        WindowManager.LayoutParams p = dlg.getWindow().getAttributes();  //获取对话框当前的参数值

        p.width = (int) d.getWidth();    //宽度设置为屏幕的0.5
        dlg.getWindow().setAttributes(p);     //设置生效


        Window window = dlg.getWindow();
        window.setGravity(Gravity.BOTTOM);//设置弹框在屏幕的下方
        window.setContentView(R.layout.activity_list_dialog);

        MaxListView listView = (MaxListView) window.findViewById(R.id.listview);

        listView.setListViewHeight(500);
        TextView textConfirm = (TextView) window.findViewById(R.id.cancel);
        TextView textTitle = (TextView) window.findViewById(R.id.title);
        textTitle.setText(title);
        MyAdapter myAdapter = new MyAdapter(list, activity);
        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                textView.setText(list.get(i));
                alertOnClickListener.alertClick(list.get(i));
                dlg.dismiss();
            }
        });
        textConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dlg.dismiss();
            }
        });

    }

}
