package sinia.com.baihangeducation.club.mangerpower;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import sinia.com.baihangeducation.R;


/**
 * 创建日期：2018/5/18 on 17:00
 * 描述:
 * 作者:yuxd Administrator
 */
public class MangerPowerHolder extends RecyclerView.ViewHolder {


    public TextView title;
    public TextView name;
    public Switch open;

    //实现的方法
    public MangerPowerHolder(View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.title);
        name = (TextView) itemView.findViewById(R.id.name);
        open = (Switch) itemView.findViewById(R.id.open);


    }
}
