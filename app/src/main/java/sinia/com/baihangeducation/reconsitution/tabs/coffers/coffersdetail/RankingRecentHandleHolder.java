package sinia.com.baihangeducation.reconsitution.tabs.coffers.coffersdetail;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import sinia.com.baihangeducation.R;


/**
 * 创建日期：2018/5/18 on 17:00
 * 描述:
 * 作者:yuxd Administrator
 */
public class RankingRecentHandleHolder extends RecyclerView.ViewHolder {


    public TextView title;
    public TextView time;
    public TextView money;

    //实现的方法
    public RankingRecentHandleHolder(View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.title);
        time = (TextView) itemView.findViewById(R.id.time);
        money = (TextView) itemView.findViewById(R.id.money);
    }
}
