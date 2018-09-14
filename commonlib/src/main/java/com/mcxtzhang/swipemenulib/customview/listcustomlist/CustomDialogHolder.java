package com.mcxtzhang.swipemenulib.customview.listcustomlist;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mcxtzhang.swipemenulib.R;
import com.mcxtzhang.swipemenulib.customview.MyTextView;


/**
 * 创建日期：2018/5/18 on 17:00
 * 描述:
 * 作者:yuxd Administrator
 */
public class CustomDialogHolder extends RecyclerView.ViewHolder {


    public TextView discount_number;
    public TextView discount_content;
    public TextView discount_time;
    public ImageView discount_select;

    //实现的方法
    public CustomDialogHolder(View itemView) {
        super(itemView);
        discount_number = (TextView) itemView.findViewById(R.id.discount_number);
        discount_content = (TextView) itemView.findViewById(R.id.discount_content);
        discount_time = (TextView) itemView.findViewById(R.id.discount_time);
        discount_select = (ImageView) itemView.findViewById(R.id.discount_select);
    }
}
