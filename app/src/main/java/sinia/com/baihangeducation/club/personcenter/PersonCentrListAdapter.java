package sinia.com.baihangeducation.club.personcenter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.example.framwork.adapter.BaseViewHolder;
import com.example.framwork.adapter.SuperBaseAdapter;
import com.mcxtzhang.swipemenulib.customview.GlideLoadUtils;

import java.util.List;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.club.ClubFragment;
import sinia.com.baihangeducation.club.club.interfaces.GetRequestListener;
import sinia.com.baihangeducation.club.club.model.ClubHomeInfo;
import sinia.com.baihangeducation.supplement.base.Goto;

/**
 * Created by Administrator on 2018/4/24.
 */

public class PersonCentrListAdapter extends SuperBaseAdapter<ClubHomeInfo.School> {
    private PersonCenterActivity activity;
    private Context context;

    public PersonCentrListAdapter(PersonCenterActivity activity, List<ClubHomeInfo.School> data) {
        super(activity, data);
        this.activity = activity;
        this.context = activity;
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

        //   ( 0：已申请 1：未申请 2：社员 )
        if (mInviteListInfo.is_apply.equals("3")) {
            holder.setText(R.id.ranking_money, "");
        } else if (mInviteListInfo.is_apply.equals("2")) {
            holder.setText(R.id.ranking_money, "已加入");
        } else if (mInviteListInfo.is_apply.equals("1")) {
            holder.setText(R.id.ranking_money, "加入");
        } else {
            holder.setText(R.id.ranking_money, "已申请");
        }


        ImageView img = holder.getView(R.id.ranking_logo);
        GlideLoadUtils.getInstance().glideLoad(context, mInviteListInfo.logo, img, R.drawable.logo);
        holder.setOnClickListener(R.id.ranking_money, new View.OnClickListener() {
            @Override
            public void onClick(View view) { //点击加入
                if (mInviteListInfo.is_apply.equals("1")) {
                    activity.applyClub(mInviteListInfo.id, "", new GetRequestListener() {
                        @Override
                        public void setRequestSuccess(String msg) {
                            holder.setText(R.id.ranking_money, "已申请");
                        }

                        @Override
                        public void setRequestFail() {

                        }
                    });
                }
            }
        });


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
