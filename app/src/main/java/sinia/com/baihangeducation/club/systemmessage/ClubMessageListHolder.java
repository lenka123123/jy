package sinia.com.baihangeducation.club.systemmessage;

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
public class ClubMessageListHolder extends RecyclerView.ViewHolder {

    public ImageView logo;
    public TextView time;
    public TextView title;
    public TextView club;
    public TextView accept;
    public TextView cancel;
    public LinearLayout is_show_layout;
    public View is_show_layout_view;
    public LinearLayout club_item_view;

    //实现的方法
    public ClubMessageListHolder(View itemView) {
        super(itemView);
        logo = (ImageView) itemView.findViewById(R.id.logo);
        time = (TextView) itemView.findViewById(R.id.time);
        title = (TextView) itemView.findViewById(R.id.title);
        club = (TextView) itemView.findViewById(R.id.club);
        accept = (TextView) itemView.findViewById(R.id.accept);
        cancel = (TextView) itemView.findViewById(R.id.cancel);
        is_show_layout = (LinearLayout) itemView.findViewById(R.id.is_show_layout);
        is_show_layout_view = (View) itemView.findViewById(R.id.is_show_layout_view);
        club_item_view = (LinearLayout) itemView.findViewById(R.id.club_item_view);


    }
}
