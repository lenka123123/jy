package sinia.com.baihangeducation.mine.adapter;

import android.content.Context;
import android.view.View;

import com.example.framwork.adapter.BaseViewHolder;
import com.example.framwork.adapter.SuperBaseAdapter;

import java.util.List;

import sinia.com.baihangeducation.R;

import com.mcxtzhang.swipemenulib.info.bean.MySendInfo;

import sinia.com.baihangeducation.supplement.base.Goto;

/**
 * Created by Administrator on 2018/4/17.
 */

public class MySendAllTimeAdapter extends SuperBaseAdapter<MySendInfo> {
    private Context context;

    public MySendAllTimeAdapter(Context context, List<MySendInfo> datas) {
        super(context, datas);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder holder, final MySendInfo item, int position) {
//        holder.setRoundImageUrl(R.id.fragment_mine_mysend_alltime_item_logo, item.job_company_logo, R.drawable.new_eorrlogo);
        holder.setText(R.id.fragment_mine_mysend_alltime_item_title, item.job_title);

        holder.setText(R.id.fragment_mine_mysend_alltime_item_job, item.job_city_name);
        if (item.job_apply_status == 6) { //已完成 没评价
            holder.getView(R.id.comment_img).setVisibility(View.VISIBLE);
            holder.getView(R.id.fragment_mine_mysend_alltime_item_adressanddate).setVisibility(View.GONE);

        }

        if (item.job_apply_status == 7) { //已评价
            holder.getView(R.id.comment_img).setVisibility(View.GONE);
            holder.getView(R.id.fragment_mine_mysend_alltime_item_adressanddate).setVisibility(View.VISIBLE);
        }

        holder.setText(R.id.fragment_mine_mysend_alltime_item_adressanddate, "已评价");

        holder.setText(R.id.fragment_mine_mysend_alltime_item_salary, item.job_money);

        holder.setOnClickListener(R.id.fragment_mine_mysend_alltime_item_adressanddate, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Goto.toCommentActivity(context, item.job_title, item.job_city_name, item.job_money, item.job_apply_id, "已评价");
            }
        });

        holder.setOnClickListener(R.id.comment_img, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Goto.toCommentActivity(context, item.job_title, item.job_city_name, item.job_money, item.job_apply_id, "未评价");
            }
        });

        holder.setOnClickListener(R.id.fragment_mine_mysend_alltime_item, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Goto.toPartTimeJobDetailActivity(context, item.job_id);
            }
        });
    }

    @Override
    protected int getItemViewLayoutId(int position, MySendInfo item) {
        return R.layout.fragment_mine_mysend_alltime_item;
    }
}
