package sinia.com.baihangeducation.club.myclub.myactive;

import android.content.Context;
import android.view.View;

import com.example.framwork.adapter.BaseViewHolder;
import com.example.framwork.adapter.SuperBaseAdapter;

import java.util.List;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.club.clubactive.model.ActiveListData;
import sinia.com.baihangeducation.club.myclub.myclub.MyClubSchoolList;
import sinia.com.baihangeducation.supplement.base.Goto;

/**
 * Created by Administrator on 2018/4/24.
 */

public class MyActiveAdapter extends SuperBaseAdapter<ActiveListData.ActiveList> {

    private Context context;

    public MyActiveAdapter(Context context, List<ActiveListData.ActiveList> data) {
        super(context, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder holder, final ActiveListData.ActiveList item, int position) {

//        holder.setRoundImageUrl(R.id.img, item.cover, R.drawable.new_eorrlogo);
        holder.setImageUrl(R.id.img, item.cover, R.drawable.new_eorrlogo);
        holder.setText(R.id.title, item.club_name);
        holder.setText(R.id.time, item.start_time);
        holder.setText(R.id.address, item.addr);
        holder.setText(R.id.free, item.expenditure);
        holder.setText(R.id.person, item.join_num);
//        holder.setText(R.id.ranking_school_number, item.member_num + "人");
//        holder.setText(R.id.ranking_money, item.role_name);

        holder.setOnClickListener(R.id.newcampany, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Goto.toShowHotActive(context, item.activity_id);
            }
        });
    }

    @Override
    protected int getItemViewLayoutId(int position, ActiveListData.ActiveList item) {
        return R.layout.activity_my_club_recommend_item;
    }


}
