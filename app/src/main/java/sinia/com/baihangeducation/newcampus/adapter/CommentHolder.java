package sinia.com.baihangeducation.newcampus.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.newcampus.tabs.answer.AppNoScrollerListView;

/**
 * 创建日期：2018/5/18 on 17:00
 * 描述:
 * 作者:yuxd Administrator
 */
public class CommentHolder extends RecyclerView.ViewHolder {


    public TextView comment_content;
    public TextView comment_name;
    public TextView comment_time;
    public TextView answer;
    public ImageView imageView;
    public AppNoScrollerListView commentList;


    //实现的方法
    public CommentHolder(View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.img);
        comment_name = (TextView) itemView.findViewById(R.id.comment_name);
        comment_content = (TextView) itemView.findViewById(R.id.comment_content);
        comment_time = (TextView) itemView.findViewById(R.id.comment_time);

        answer = (TextView) itemView.findViewById(R.id.answer);
        commentList = (AppNoScrollerListView) itemView.findViewById(R.id.commentList);

    }
}
