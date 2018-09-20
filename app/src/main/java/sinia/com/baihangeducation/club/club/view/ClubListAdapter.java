package sinia.com.baihangeducation.club.club.view;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;

import com.example.framwork.adapter.BaseViewHolder;
import com.example.framwork.adapter.SuperBaseAdapter;
import com.mcxtzhang.swipemenulib.customview.GlideLoadUtils;
import com.mcxtzhang.swipemenulib.info.bean.HomePartTimeInfo;

import java.util.List;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.club.club.model.ClubHomeInfo;
import sinia.com.baihangeducation.club.searchschool.model.ClubSchoolList;
import sinia.com.baihangeducation.supplement.base.Goto;

/**
 * Created by Administrator on 2018/4/24.
 */

public class ClubListAdapter extends SuperBaseAdapter<ClubHomeInfo.School> {
    private Context context;

    public ClubListAdapter(Context context, List<ClubHomeInfo.School> data) {
        super(context, data);
        this.context = context;
    }


    @Override
    protected void convert(BaseViewHolder holder, final ClubHomeInfo.School mInviteListInfo, int position) {


        holder.setText(R.id.ranking_number, mInviteListInfo.new_order);


        if (mInviteListInfo.fluctuate.equals("0")) {
            holder.setVisible(R.id.ranking_up_img, false);
            holder.setVisible(R.id.ranking_up_tx, false);
        } else {
            holder.setText(R.id.ranking_up_tx, mInviteListInfo.fluctuate);
            if (mInviteListInfo.fluctuate.startsWith("-")) {
                holder.setText(R.id.ranking_up_tx, mInviteListInfo.fluctuate.replace("-", ""));
                holder.setText(R.id.ranking_up_img, "▼");

                holder.setTextColor(R.id.ranking_up_img, context.getResources().getColor(R.color.club_item_green));
                holder.setTextColor(R.id.ranking_up_tx, context.getResources().getColor(R.color.club_item_green));

            } else {
                holder.setText(R.id.ranking_up_img, "▲");
                holder.setTextColor(R.id.ranking_up_img, context.getResources().getColor(R.color.club_item_red));
                holder.setTextColor(R.id.ranking_up_tx, context.getResources().getColor(R.color.club_item_red));
            }
        }

        holder.setText(R.id.ranking_school_name, mInviteListInfo.name);
        holder.setText(R.id.ranking_school_number, mInviteListInfo.member_num + "人");
        holder.setText(R.id.ranking_money, mInviteListInfo.income);

        ImageView img = holder.getView(R.id.ranking_logo);
        GlideLoadUtils.getInstance().glideLoad(context, mInviteListInfo.logo, img, R.drawable.logo);


        holder.setOnClickListener(R.id.club_item_view, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Goto.toClubDetailActivity(context, mInviteListInfo.id);
            }
        });
    }


    @Override
    protected int getItemViewLayoutId(int position, ClubHomeInfo.School item) {
        return R.layout.fragment_club_list_item;
    }
}
