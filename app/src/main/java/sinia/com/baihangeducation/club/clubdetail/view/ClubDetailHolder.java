package sinia.com.baihangeducation.club.clubdetail.view;

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
public class ClubDetailHolder extends RecyclerView.ViewHolder {


    public TextView ranking_number;
    public TextView ranking_up_img;
    public TextView ranking_up_tx;
    public TextView ranking_school_name;
    public TextView ranking_school_number;
    public TextView ranking_money;
    public ImageView ranking_logo;
    public View view_line;

    //实现的方法
    public ClubDetailHolder(View itemView) {
        super(itemView);
        view_line = (View) itemView.findViewById(R.id.view_line);
        ranking_number = (TextView) itemView.findViewById(R.id.ranking_number);
        ranking_up_img = (TextView) itemView.findViewById(R.id.ranking_up_img);
        ranking_up_tx = (TextView) itemView.findViewById(R.id.ranking_up_tx);
        ranking_school_name = (TextView) itemView.findViewById(R.id.ranking_school_name);
        ranking_school_number = (TextView) itemView.findViewById(R.id.ranking_school_number);
        ranking_money = (TextView) itemView.findViewById(R.id.ranking_money);
        ranking_logo = (ImageView) itemView.findViewById(R.id.ranking_logo);


    }
}
