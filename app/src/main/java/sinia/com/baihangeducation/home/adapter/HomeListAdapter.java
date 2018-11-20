package sinia.com.baihangeducation.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.framwork.adapter.BaseViewHolder;
import com.example.framwork.adapter.SuperBaseAdapter;

import java.text.ParseException;
import java.util.List;

import sinia.com.baihangeducation.R;

import com.example.framwork.utils.LogUtils;
import com.mcxtzhang.swipemenulib.customview.GlideLoadUtils;
import com.mcxtzhang.swipemenulib.info.bean.HomeJobInfo;
import com.mcxtzhang.swipemenulib.info.bean.HomeJobTagInfo;

import sinia.com.baihangeducation.supplement.base.Goto;

import static java.lang.Integer.*;

/**
 * Created by Administrator on 2018/4/23.
 */

public class HomeListAdapter extends SuperBaseAdapter<HomeJobInfo> {
    private Context context;
    private List<HomeJobInfo> data;

    public HomeListAdapter(Context context, List<HomeJobInfo> data) {
        super(context, data);
        this.context = context;
        this.data = data;
    }

    @Override
    protected void convert(BaseViewHolder holder, final HomeJobInfo item, int position) {
//        Log.i("首页item内容",data.size()+"");
        if (item.job_company_name == null) {
            holder.setInVisible(R.id.fragment_home_down_item_data, false);
            holder.setInVisible(R.id.fragment_home_down_item_salary, false);
            holder.setInVisible(R.id.fragment_home_down_item_nodata, true);
        } else {
            holder.setInVisible(R.id.fragment_home_down_item_data, true);
            holder.setInVisible(R.id.fragment_home_down_item_salary, true);
            holder.setInVisible(R.id.fragment_home_down_item_nodata, false);
//            holder.setRoundImageUrl(R.id.fragment_home_down_item_img, item.job_industry_icon, R.drawable.select_0);
            ImageView imageView = holder.getView(R.id.fragment_home_down_item_img);
            GlideLoadUtils.getInstance().glideLoad(context, item.job_industry_icon, imageView, R.drawable.select_0);

//            holder.setLoadalImageUrl(R.id.fragment_home_down_item_img, getDrawable(item.job_industry_id), R.drawable.new_eorrlogo);
            holder.setText(R.id.job_time, item.job_time_group);
            // 发布平台 1:非平台发布; 2:平台发布
            String address = item.job_city_name + "  " + item.job_distance + "  " + item.job_money_name;
            if (item.job_is_platform == 2) {
                address = item.job_city_name + "  " + item.job_distance + "  " + item.job_money_name + "  平台";
            }

            holder.setText(R.id.fragment_home_down_item_title, item.job_title);
            holder.setText(R.id.fragment_home_down_item_adressandtime, address);//+ " " + item.job_add_date
            holder.setText(R.id.lock_person_number, "浏览" + item.job_look_num + "人");//+ " " + item.job_add_date
            holder.setText(R.id.apply_person_number, "申请" + item.job_apply_num + "人");//+ " " + item.job_add_date
            holder.setText(R.id.ago, item.job_time_shaft);

            if (item.job_type == 3) {
                holder.setVisible(R.id.show_club_job, true);
            } else {
                holder.setVisible(R.id.show_club_job, false);
            }


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
            holder.setOnClickListener(R.id.fragment_home_down_item, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Goto.toPartTimeJobDetailActivityForHome(context, item.job_id, item.job_type, item.jmessage_phone);
                    System.out.println("aaaa--" + item.job_type);
//                    switch (item.job_type) {
//                        case 1:
//                            Goto.toAllJobDetailActivity(context, item.job_id);
//                            break;
//
//                        case 2:
//                            Goto.toPartTimeJobDetailActivity(context, item.job_id);
//                            break;
//                    }
                }
            });
        }
    }

    @Override
    protected int getItemViewLayoutId(int position, HomeJobInfo item) {
        return R.layout.fragment_home_down_item;
    }
}
