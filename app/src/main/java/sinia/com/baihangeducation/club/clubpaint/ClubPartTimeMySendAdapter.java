package sinia.com.baihangeducation.club.clubpaint;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.example.framwork.adapter.BaseViewHolder;
import com.example.framwork.adapter.SuperBaseAdapter;
import com.mcxtzhang.swipemenulib.customview.GlideLoadUtils;
import com.mcxtzhang.swipemenulib.info.ClubPartTimeListInfo;

import java.util.List;

import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.club.club.interfaces.GetRequestListener;
import sinia.com.baihangeducation.club.myclub.myparttime.ClubMyPartTimeActivity;
import sinia.com.baihangeducation.supplement.base.Goto;

/**
 * Created by Administrator on 2018/4/24.
 */

public class ClubPartTimeMySendAdapter extends SuperBaseAdapter<ClubPartTimeListInfo.ClubPartInfo> {
    private ClubMyPartTimeActivity context;
    private List<ClubPartTimeListInfo.ClubPartInfo> data;

    public ClubPartTimeMySendAdapter(ClubMyPartTimeActivity context, List<ClubPartTimeListInfo.ClubPartInfo> data) {
        super(context, data);
        this.context = context;
        this.data = data;
    }

    @Override
    protected void convert(BaseViewHolder holder, final ClubPartTimeListInfo.ClubPartInfo item, int position) {
        holder.setText(R.id.fragment_home_down_item_title, item.title);
        holder.setText(R.id.job_time_type, item.money_type_name);
        holder.setText(R.id.job_time, item.time_group);
        holder.setText(R.id.ago, item.add_time);

        holder.setOnClickListener(R.id.fragment_home_down_item, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Goto.toPartTimeJobDetailActivityForClub(context, Integer.valueOf(item.job_id), item.jmessage_phone);
            }
        });

        holder.setOnClickListener(R.id.apply_list, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Goto.toMyCreatePartTimeApplyList(context, item.job_id);
            }
        });

        holder.setOnClickListener(R.id.delete, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.deleteJob(item.job_id, new GetRequestListener() {
                    @Override
                    public void setRequestSuccess(String msg) {
                        data.remove(item);
                        notifyDataSetChanged();
                    }

                    @Override
                    public void setRequestFail() {

                    }
                });


            }
        });
    }

    @Override
    protected int getItemViewLayoutId(int position, ClubPartTimeListInfo.ClubPartInfo item) {
        return R.layout.activity_my_send_parttime;
    }

    public void showClubJob(int show_club_job) {

    }
}
