package sinia.com.baihangeducation.club.myclub.myclub;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.example.framwork.adapter.BaseViewHolder;
import com.example.framwork.adapter.SuperBaseAdapter;
import com.mcxtzhang.swipemenulib.customview.GlideLoadUtils;
import com.mcxtzhang.swipemenulib.info.ClubPartTimeListInfo;

import java.util.ArrayList;
import java.util.List;

import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.club.clubschoollist.model.ClubSchoolList;
import sinia.com.baihangeducation.supplement.base.Goto;

/**
 * Created by Administrator on 2018/4/24.
 */

public class MyClubAdapter extends SuperBaseAdapter<MyClubSchoolList.School> {

    private Context context;

    public MyClubAdapter(Context context, List<MyClubSchoolList.School> data) {
        super(context, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder holder, final MyClubSchoolList.School item, int position) {

        holder.setRoundImageUrl(R.id.ranking_logo, item.logo, R.drawable.new_eorrlogo);
        holder.setText(R.id.ranking_school_name, item.name);
        if (!item.member_num.equals("0"))
            holder.setText(R.id.ranking_school_number, item.member_num + "人");

        //  ( 1：待审核 2：审核通过 3：审核失败（2018/11/6迭代） )
        if (item.status.equals("1")) {
            holder.setText(R.id.ranking_money, "待审核");
        } else if (item.status.equals("2")) {
            holder.setText(R.id.ranking_money, item.role_name);
        } else if (item.status.equals("3")) {
            holder.setText(R.id.ranking_money, "审核失败");
        }

        holder.setOnClickListener(R.id.club_item_view, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.status.equals("2")) {
                    Goto.toClubDetailActivity(context, item.id);
                }

//                Goto.toPartTimeJobDetailActivityForClub(context, Integer.valueOf(item.id), item.member_num);
            }
        });
    }

    @Override
    protected int getItemViewLayoutId(int position, MyClubSchoolList.School item) {
        return R.layout.fragment_club_list_item;
    }


}
