package sinia.com.baihangeducation.mine.adapter;

import android.content.Context;
import android.view.View;

import com.example.framwork.adapter.BaseViewHolder;
import com.example.framwork.adapter.SuperBaseAdapter;

import java.util.List;

import sinia.com.baihangeducation.R;
import com.mcxtzhang.swipemenulib.info.bean.MySendInfo;
import com.mcxtzhang.swipemenulib.info.bean.MySendTagInfo;
import sinia.com.baihangeducation.supplement.base.Goto;

/**
 * Created by Administrator on 2018/4/17.
 */

public class MySendPartTimeAdapter extends SuperBaseAdapter<MySendInfo> {
    private Context context;

    public MySendPartTimeAdapter(Context context, List<MySendInfo> datas) {
        super(context, datas);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder holder, final MySendInfo item, int position) {
        holder.setRoundImageUrl(R.id.fragment_mine_mysend_parttime_item_logo, item.job_company_logo, R.drawable.new_eorrlogo);
        holder.setText(R.id.fragment_mine_mysend_parttime_item_title, item.job_title);
        holder.setText(R.id.fragment_mine_mysend_parttime_item_adressanddata, item.job_city_name  );
        holder.setText(R.id.fragment_mine_mysend_parttime_item_adressanddata_name, item.job_company_name  );
        holder.setText(R.id.fragment_mine_mysend_parttime_item_salary, item.job_money);
        List<MySendTagInfo> mySendTagInfos = item.job_tag_list;
        for (int i = 0; i < mySendTagInfos.size(); i++) {
            if ((mySendTagInfos.get(i).tag_name) != null && (mySendTagInfos.get(i).tag_name).equals("日结")) {
                holder.setVisible(R.id.fragment_mine_mysend_parttime_item_daily, true);
            }
            if ((mySendTagInfos.get(i).tag_name) != null && (mySendTagInfos.get(i).tag_name).equals("实名")) {
                holder.setVisible(R.id.fragment_mine_mysend_parttime_item_reallyname, true);
            }
        }
        holder.setOnClickListener(R.id.fragment_mine_mysend_parttime_item, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Goto.toPartTimeJobDetailActivity(context, item.job_id);
            }
        });
    }

    @Override
    protected int getItemViewLayoutId(int position, MySendInfo item) {
        return R.layout.fragment_mine_mysend_parttime_item;
    }
}
