package sinia.com.baihangeducation.release.campus.tabs.heatfunsearchlist.adapter;

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
public class HotFunHolder extends RecyclerView.ViewHolder {


    public TextView content;
    public TextView title;
    public ImageView imageView;


    //实现的方法
    public HotFunHolder(View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.img);
        title = (TextView) itemView.findViewById(R.id.title);
        content = (TextView) itemView.findViewById(R.id.content);
    }
}
