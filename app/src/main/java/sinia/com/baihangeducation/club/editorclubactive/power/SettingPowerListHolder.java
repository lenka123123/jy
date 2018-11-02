package sinia.com.baihangeducation.club.editorclubactive.power;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;

import sinia.com.baihangeducation.R;


/**
 * 创建日期：2018/5/18 on 17:00
 * 描述:
 * 作者:yuxd Administrator
 */
public class SettingPowerListHolder extends RecyclerView.ViewHolder {


    public TextView name;
    public Button check;
    public LinearLayout club_item_view;

    //实现的方法
    public SettingPowerListHolder(View itemView) {
        super(itemView);
        name = (TextView) itemView.findViewById(R.id.name);
        check = (Button) itemView.findViewById(R.id.check);
        club_item_view = (LinearLayout) itemView.findViewById(R.id.club_item_view);


    }
}
