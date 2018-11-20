package sinia.com.baihangeducation.club.clubactive.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import sinia.com.baihangeducation.R;

public class MyHolder extends RecyclerView.ViewHolder {

    public ImageView icon;
    public TextView textView;
    public TextView title;
    public TextView time;
    public TextView address;
    public TextView person;
    public TextView free;
    public LinearLayout active_item_view;

    //实现的方法
    public MyHolder(View itemView) {
        super(itemView);
        icon = itemView.findViewById(R.id.img);
        title = itemView.findViewById(R.id.title);
        time = itemView.findViewById(R.id.time);
        person = itemView.findViewById(R.id.person);
        address = itemView.findViewById(R.id.address);
        free = itemView.findViewById(R.id.free);
        active_item_view = itemView.findViewById(R.id.active_item_view);
    }

}
