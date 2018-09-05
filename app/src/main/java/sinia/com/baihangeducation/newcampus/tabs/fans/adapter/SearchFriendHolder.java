package sinia.com.baihangeducation.newcampus.tabs.fans.adapter;

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
public class SearchFriendHolder extends RecyclerView.ViewHolder {


    public TextView content;
    public TextView addfollow;
    public TextView title;
    public ImageView imageView;


    //实现的方法
    public SearchFriendHolder(View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.img);
        title = (TextView) itemView.findViewById(R.id.title);
        content = (TextView) itemView.findViewById(R.id.content);
        addfollow = (TextView) itemView.findViewById(R.id.addfollow);

    }
}
