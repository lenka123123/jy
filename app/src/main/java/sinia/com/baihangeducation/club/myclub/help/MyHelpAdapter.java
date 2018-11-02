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

//        holder.setText(R.id.ranking_school_number, item.member_num + "人");
//        holder.setText(R.id.ranking_money, item.role_name);
//
//        holder.setOnClickListener(R.id.club_item_view, new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Goto.toPartTimeJobDetailActivityForClub(context, Integer.valueOf(item.id), item.member_num);
//            }
//        });
    }

    @Override
    protected int getItemViewLayoutId(int position, MyHelplList.Help item) {
        return R.layout.activity_club_help_item;
    }


}
