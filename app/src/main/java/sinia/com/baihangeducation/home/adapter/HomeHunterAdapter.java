package sinia.com.baihangeducation.home.adapter;

import android.content.Context;
import android.view.View;

import com.example.framwork.adapter.BaseViewHolder;
import com.example.framwork.adapter.SuperBaseAdapter;

import java.util.List;

import sinia.com.baihangeducation.R;
import com.mcxtzhang.swipemenulib.info.bean.HomePartTimeInfo;
import sinia.com.baihangeducation.supplement.base.Goto;

/**
 * Created by Administrator on 2018/4/24.
 */

public class HomeHunterAdapter extends SuperBaseAdapter<HomePartTimeInfo> {
    private Context context;

    public HomeHunterAdapter(Context context, List<HomePartTimeInfo> data) {
        super(context, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder holder, final HomePartTimeInfo item, int position) {
        holder.setRoundImageUrl(R.id.fragment_mine_mysend_alltime_item_logo, item.job_company_logo, R.drawable.new_eorrlogo);
        holder.setText(R.id.fragment_mine_mysend_alltime_item_title, item.job_title);
        holder.setText(R.id.fragment_mine_mysend_alltime_item_job, item.job_company_name);
        holder.setText(R.id.fragment_mine_mysend_alltime_item_adressanddate, item.job_city_name + "  " + item.job_add_date);
        holder.setText(R.id.fragment_mine_mysend_alltime_item_salary, item.job_money);
        holder.setOnClickListener(R.id.fragment_mine_mysend_alltime_item, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (item.job_type) {
                    case 1:
                        Goto.toAllJobDetailActivity(context, item.job_id);
                        break;
                    case 2:
                        Goto.toPartTimeJobDetailActivity(context, item.job_id);
                        break;
                }
            }
        });
    }

    @Override
    protected int getItemViewLayoutId(int position, HomePartTimeInfo item) {
        return R.layout.fragment_mine_mysend_alltime_item;
    }
}
