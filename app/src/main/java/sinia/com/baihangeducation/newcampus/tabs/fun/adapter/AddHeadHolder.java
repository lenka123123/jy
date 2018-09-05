package sinia.com.baihangeducation.newcampus.tabs.fun.adapter;

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
public class AddHeadHolder extends RecyclerView.ViewHolder {


    public TextView title;
    public ImageView imageView;


    //实现的方法
    public AddHeadHolder(View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.img);
        title = (TextView) itemView.findViewById(R.id.title);
    }
}
