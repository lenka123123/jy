package sinia.com.baihangeducation.club.myclub.help;

import android.content.Context;
import android.view.View;

import com.example.framwork.adapter.BaseViewHolder;
import com.example.framwork.adapter.SuperBaseAdapter;

import java.util.List;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.club.myclub.myclub.MyClubSchoolList;
import sinia.com.baihangeducation.supplement.base.Goto;

/**
 * Created by Administrator on 2018/4/24.
 */

public class MyHelpAdapter extends SuperBaseAdapter<MyHelplList.Help> {

    private Context context;

    public MyHelpAdapter(Context context, List<MyHelplList.Help> data) {
        super(context, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder holder, final MyHelplList.Help item, int position) {

//        holder.setRoundImageUrl(R.id.ranking_logo, item.logo, R.drawable.new_eorrlogo);
        holder.setText(R.id.title, item.title);
        holder.setText(R.id.name, item.club_name);
        holder.setText(R.id.school, item.school_name);
        holder.setText(R.id.type, item.status);
        holder.setText(R.id.money, item.money);
        if (item.status.equals("审核成功")) {
            holder.setVisible(R.id.apply_success, true);
            holder.setVisible(R.id.type_cope, false);
            holder.setVisible(R.id.type, false);
        } else {
            holder.setVisible(R.id.apply_success, false);
            holder.setVisible(R.id.type_cope, true);
            holder.setVisible(R.id.type, true);
        }

//        holder.setText(R.id.ranking_school_number, item.member_num + "人");
//        holder.setText(R.id.ranking_money, item.role_name);
//
        holder.setOnClickListener(R.id.club_item_view, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!item.status.equals("审核中")) {
                    Goto.toApplyHelpShow(context, item.support_id);
                }

            }
        });
    }

    @Override
    protected int getItemViewLayoutId(int position, MyHelplList.Help item) {
        return R.layout.activity_club_help_item;
    }


}
