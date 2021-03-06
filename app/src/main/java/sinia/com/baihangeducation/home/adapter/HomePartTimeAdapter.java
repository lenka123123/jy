package sinia.com.baihangeducation.home.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.framwork.adapter.BaseViewHolder;
import com.example.framwork.adapter.SuperBaseAdapter;

import java.text.ParseException;
import java.util.List;

import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.R;

import com.example.framwork.utils.LogUtils;
import com.mcxtzhang.swipemenulib.customview.GlideLoadUtils;
import com.mcxtzhang.swipemenulib.info.bean.HomeJobTagInfo;
import com.mcxtzhang.swipemenulib.info.bean.HomePartTimeInfo;

import sinia.com.baihangeducation.supplement.base.Goto;

/**
 * Created by Administrator on 2018/4/24.
 */

public class HomePartTimeAdapter extends SuperBaseAdapter<HomePartTimeInfo> {
    private Context context;

    public HomePartTimeAdapter(Context context, List<HomePartTimeInfo> data) {
        super(context, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder holder, final HomePartTimeInfo item, int position) {
        //  holder.setRoundImageUrl(R.id.fragment_home_down_item_img, item.job_company_logo, R.drawable.new_eorrlogo);

//        holder.setLoadalImageUrl(R.id.fragment_home_down_item_img, getDrawable(item.job_industry_id), R.drawable.new_eorrlogo);
        ImageView imageView = holder.getView(R.id.fragment_home_down_item_img);
        GlideLoadUtils.getInstance().glideLoad(context, item.job_industry_icon, imageView, R.drawable.select_0);

        holder.setText(R.id.fragment_home_down_item_title, item.job_title);
        holder.setText(R.id.job_time, item.job_time_group);
        // 发布平台 1:非平台发布; 2:平台发布
        String address = item.job_city_name + "  " + item.job_distance + "  " + item.job_money_name;
        if (item.is_platform == 2) {
            address = item.job_city_name + "  " + item.job_distance + "  " + item.job_money_name + "  平台";
        }

        holder.setText(R.id.fragment_home_down_item_adressandtime, address);
        holder.setText(R.id.lock_person_number, "浏览" + item.job_look_num + "人");//+ " " + item.job_add_date
        holder.setText(R.id.apply_person_number, "申请" + item.job_apply_num + "人");//+ " " + item.job_add_date

        holder.setText(R.id.ago, item.job_time_shaft);

        List<HomeJobTagInfo> mHomeJobTagInfo = item.job_tag_list;
        if (mHomeJobTagInfo.size() != 0 || !mHomeJobTagInfo.isEmpty()) {
            for (int i = 0; i < mHomeJobTagInfo.size(); i++) {
                if ((mHomeJobTagInfo.get(i).tag_name).equals("日结")) {
                    holder.setVisible(R.id.fragment_home_down_item_daily, true);
                } else {
                    holder.setVisible(R.id.fragment_home_down_item_daily, false);
                }

                if ((mHomeJobTagInfo.get(i).tag_name).equals("实名")) {
                    holder.setVisible(R.id.fragment_home_down_item_realname, true);
                } else {
                    holder.setVisible(R.id.fragment_home_down_item_realname, false);
                }
            }
        } else {
            holder.setVisible(R.id.fragment_home_down_item_daily, false);
            holder.setVisible(R.id.fragment_home_down_item_realname, false);
        }
        holder.setText(R.id.fragment_home_down_item_salary, item.job_money);

        holder.setVisible(R.id.show_club_job, item.job_type == 3);

        Log.i("兼职的type", item.job_type + "");
        holder.setOnClickListener(R.id.fragment_home_down_item, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Goto.toPartTimeJobDetailActivityForHome(context, item.job_id, item.job_type,item.jmessage_phone);
//                switch (item.job_type) {
//                    case 1:
//                        Goto.toAllJobDetailActivity(context, item.job_id);
//                        break;
//                    case 2:
//                        Goto.toPartTimeJobDetailActivity(context, item.job_id);
//                        break;
//                }
            }
        });
    }

    private int getDrawable(String job_industry_id) {
        int flag = 0;
        if (job_industry_id == null || job_industry_id.equals("")) {
        } else {
            flag = Integer.valueOf(job_industry_id);
        }
        int drawableId = R.drawable.select_0;
        switch (flag) {
            case 0:
                drawableId = R.drawable.select_0;
                break;
            case 7:
                drawableId = R.drawable.select_7;
                break;
            case 8:
                drawableId = R.drawable.select_8;
                break;
            case 9:
                drawableId = R.drawable.select_9;
                break;
            case 10:
                drawableId = R.drawable.select_10;
                break;
            case 11:
                drawableId = R.drawable.select_11;
                break;
            case 12:
                drawableId = R.drawable.select_12;
                break;
            case 13:
                drawableId = R.drawable.select_13;
                break;
            case 14:
                drawableId = R.drawable.select_14;
                break;
            case 15:
                drawableId = R.drawable.select_15;
                break;
            case 16:
                drawableId = R.drawable.select_16;
                break;
            case 17:
                drawableId = R.drawable.select_17;
                break;
            case 18:
                drawableId = R.drawable.select_18;
                break;
            case 19:
                drawableId = R.drawable.select_19;
                break;
            case 20:
                drawableId = R.drawable.select_20;
                break;
            case 21:
                drawableId = R.drawable.select_21;
                break;
            case 22:
                //  drawableId = R.drawable.select_22;
                break;
            case 23:
                drawableId = R.drawable.select_23;
                break;
            case 24:
                drawableId = R.drawable.select_24;
                break;
            case 25:
                drawableId = R.drawable.select_25;
                break;
            case 26:
                drawableId = R.drawable.select_26;
                break;
            case 27:
                drawableId = R.drawable.select_27;
                break;
            case 28:
                drawableId = R.drawable.select_28;
                break;

        }
        return drawableId;
    }

    @Override
    protected int getItemViewLayoutId(int position, HomePartTimeInfo item) {
        return R.layout.fragment_home_down_item;
    }

    public void showClubJob(int show_club_job) {

    }
}
