package sinia.com.baihangeducation.club.applyclublist.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import sinia.com.baihangeducation.R;


/**
 * 创建日期：2018/5/18 on 17:00
 * 描述:
 * 作者:yuxd Administrator
 */
public class GetPersonListHolder extends RecyclerView.ViewHolder {


    public TextView accept;
    public TextView type;
    public TextView name;
    public ImageView logo;

    //实现的方法
    public GetPersonListHolder(View itemView) {
        super(itemView);
        accept = (TextView) itemView.findViewById(R.id.accept);
        type = (TextView) itemView.findViewById(R.id.type);

        name = (TextView) itemView.findViewById(R.id.name);
        logo = (ImageView) itemView.findViewById(R.id.logo);


    }
}
