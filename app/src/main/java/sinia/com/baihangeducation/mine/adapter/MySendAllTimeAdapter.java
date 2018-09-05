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
        holder.setRoundImageUrl(R.id.fragment_mine_mysend_alltime_item_logo, item.job_company_logo, R.drawable.new_eorrlogo);
        holder.setText(R.id.fragment_mine_mysend_alltime_item_title, item.job_title);
        holder.setText(R.id.fragment_mine_mysend_alltime_item_job, item.job_company_name);
        holder.setText(R.id.fragment_mine_mysend_alltime_item_adressanddate, item.job_city_name + " " + item.job_apply_date);
        holder.setText(R.id.fragment_mine_mysend_alltime_item_salary, item.job_money);
        holder.setOnClickListener(R.id.fragment_mine_mysend_alltime_item, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Goto.toAllJobDetailActivity(context,item.job_id);
            }
        });
    }

    @Override
    protected int getItemViewLayoutId(int position, MySendInfo item) {
        return R.layout.fragment_mine_mysend_alltime_item;
    }
}
