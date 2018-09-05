package com.mcxtzhang.swipemenulib.customview;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.mcxtzhang.swipemenulib.R;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ChangeStyleNumberPicker {

    NumberPicker np1, np2, center;
    //定义最低价格、最高价格的初始值
    int minPrice = 0, maxPrice = 0;
    private String[] array;
    private String[] center1;
    private Context activity;

    public ChangeStyleNumberPicker(Context activity) {
        this.activity = activity;
        List list = new ArrayList();
        for (int i = 16; i < 51; i++) {
            list.add(String.valueOf(i));
        }
        list.add(0, "不限");
        array = new String[list.size()];
        list.toArray(array);
        center1 = new String[]{"一"};
    }


    public interface AlertOnClickListener {
        void alertClick(String age);
    }

    public void setAlertOnClickListener(AlertOnClickListener alertOnClickListener) {
        this.alertOnClickListener = alertOnClickListener;
    }

    public AlertOnClickListener alertOnClickListener;

    public void showAlertDialog() {
        final AlertDialog dlg = new AlertDialog.Builder(activity,R.style.dialog_fs).create();
        dlg.show();

        WindowManager m = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        Display d = m.getDefaultDisplay();  //为获取屏幕宽、高
        android.view.WindowManager.LayoutParams p = dlg.getWindow().getAttributes();  //获取对话框当前的参数值

        p.width = (int) d.getWidth();    //宽度设置为屏幕的0.5
        dlg.getWindow().setAttributes(p);     //设置生效


        Window window = dlg.getWindow();
        window.setGravity(Gravity.BOTTOM);//设置弹框在屏幕的下方
        window.setContentView(R.layout.layout_number_picker);

        np1 = (NumberPicker) window.findViewById(R.id.left);
        np2 = (NumberPicker) window.findViewById(R.id.right);
        center = (NumberPicker) window.findViewById(R.id.center);
        TextView textCancel = (TextView) window.findViewById(R.id.tv_cancel);
        TextView textConfirm = (TextView) window.findViewById(R.id.tv_confirm);
        textCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (maxPrice == 0 || minPrice == 0) {
                    alertOnClickListener.alertClick("不限");
                } else {
                    alertOnClickListener.alertClick(15 + minPrice + "-" + (15 + maxPrice));
                }
                dlg.dismiss();
            }
        });

        textConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (maxPrice == 0 || minPrice == 0 || minPrice == maxPrice) {
                    alertOnClickListener.alertClick("不限");
                } else {
                    if (minPrice > maxPrice) {
                        Toast.makeText(activity, "年龄限制有误，请重新选择", Toast.LENGTH_LONG).show();
                        return;
                    }
                    alertOnClickListener.alertClick(15 + minPrice + "-" + (15 + maxPrice));
                }
                dlg.dismiss();
            }
        });

        setDatePickerDividerColor(np1);
        setDatePickerDividerColor(np2);
        setDatePickerDividerColor(center);
        //设置np1的最小值和最大值
        np1.setMinValue(0);
        np1.setMaxValue(array.length - 1);
        //设置np1的当前值
        np1.setValue(0);

        np1.setWrapSelectorWheel(false);//是否循环滚动
        np1.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);//禁止编辑
        np1.setDisplayedValues(array);
        np1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                minPrice = newVal;
                np2.setValue(minPrice);
                np2.setMaxValue(array.length - 1);
            }
        });

        center.setMinValue(0);
        center.setValue(0);
        center.setWrapSelectorWheel(false);//是否循环滚动
        center.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);//禁止编辑
        center.setDisplayedValues(center1);

        //设置np2的最小值和最大值
        np2.setMinValue(0);
        np2.setValue(0);
        np2.setMaxValue(array.length - 1);

        np2.setWrapSelectorWheel(false);//是否循环滚动
        np2.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);//禁止编辑
        np2.setDisplayedValues(array);
        np2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                maxPrice = newVal;
            }
        });
    }

    private void setDatePickerDividerColor(NumberPicker picker) {
        Field[] pickerFields = NumberPicker.class.getDeclaredFields();
        for (Field pf : pickerFields) {
            if (pf.getName().equals("mSelectionDivider")) {
                pf.setAccessible(true);
                try {
                    pf.set(picker, new ColorDrawable(Color.GRAY));//这里是将其隐藏,如有其它需要,修改这里的文字即可
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }
}
