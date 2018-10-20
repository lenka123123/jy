package sinia.com.baihangeducation.club.clubactive.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import sinia.com.baihangeducation.R;


/**
 * 创建日期：2018/5/18 on 17:00
 * 描述:
 * 作者:yuxd Administrator
 */
public class ActiveListHandleHolder extends RecyclerView.ViewHolder {


    public   ImageView img;
    public TextView title;
    public TextView time;
    public TextView address;
    public TextView person;
    public TextView free;
    public LinearLayout active_item_view;

    //实现的方法
    public ActiveListHandleHolder(View itemView) {
        super(itemView);
        active_item_view = (LinearLayout) itemView.findViewById(R.id.active_item_view);
        img = (ImageView) itemView.findViewById(R.id.img);
        title = (TextView) itemView.findViewById(R.id.title);
        time = (TextView) itemView.findViewById(R.id.time);
        address = (TextView) itemView.findViewById(R.id.address);
        person = (TextView) itemView.findViewById(R.id.person);
        free = (TextView) itemView.findViewById(R.id.free);
    }
}
