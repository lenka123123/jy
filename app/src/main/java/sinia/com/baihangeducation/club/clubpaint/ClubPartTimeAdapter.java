package sinia.com.baihangeducation.club.clubpaint;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.framwork.adapter.BaseViewHolder;
import com.example.framwork.adapter.SuperBaseAdapter;
import com.mcxtzhang.swipemenulib.customview.GlideLoadUtils;
import com.mcxtzhang.swipemenulib.info.ClubPartTimeListInfo;
import com.mcxtzhang.swipemenulib.info.bean.HomeJobTagInfo;
import com.mcxtzhang.swipemenulib.info.bean.HomePartTimeInfo;

import java.util.List;

import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.supplement.base.Goto;

/**
 * Created by Administrator on 2018/4/24.
 */

public class ClubPartTimeAdapter extends SuperBaseAdapter<ClubPartTimeListInfo.ClubPartInfo> {
    private Context context;

    public ClubPartTimeAdapter(Context context, List<ClubPartTimeListInfo.ClubPartInfo> data) {
        super(context, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder holder, final ClubPartTimeListInfo.ClubPartInfo item, int position) {

//          holder.setRoundImageUrl(R.id.fragment_home_down_item_img, item.industry_icon, R.drawable.new_eorrlogo);

//        holder.setLoadalImageUrl(R.id.fragment_home_down_item_img, getDrawable(item.job_industry_id), R.drawable.new_eorrlogo);
        ImageView imageView = holder.getView(R.id.fragment_home_down_item_img);
        GlideLoadUtils.getInstance().glideLoad(context, item.industry_icon, imageView, R.drawable.select_0);
        holder.setText(R.id.fragment_home_down_item_title, item.industry_name);
        holder.setVisible(R.id.job_time_line, false);

//        String address = item.job_city_name + "  " + item.job_distance +"  "+ item.job_money_name;
        holder.setText(R.id.fragment_home_down_item_adressandtime, item.city_name);
        holder.setText(R.id.lock_person_number, "浏览" + item.look_num + "人");//+ " " + item.job_add_date
        holder.setText(R.id.apply_person_number, "申请" + item.client_look_num + "人");//+ " " + item.job_add_date

        holder.setText(R.id.ago, item.add_time);
        holder.setText(R.id.fragment_home_down_item_salary, item.money_total + item.money_name);

        holder.setVisible(R.id.show_club_job, AppConfig.SHOWCLUBJOB);

        holder.setOnClickListener(R.id.fragment_home_down_item, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Goto.toPartTimeJobDetailActivityForClub(context, Integer.valueOf(item.job_id), item.jmessage_phone);
            }
        });
    }

    @Override
    protected int getItemViewLayoutId(int position, ClubPartTimeListInfo.ClubPartInfo item) {
        return R.layout.fragment_home_down_item;
    }

    public void showClubJob(int show_club_job) {

    }
}
