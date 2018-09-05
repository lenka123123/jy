package sinia.com.baihangeducation.mine.adapter;

import android.content.Context;

import com.example.framwork.adapter.BaseViewHolder;
import com.example.framwork.adapter.SuperBaseAdapter;

import java.util.List;

import sinia.com.baihangeducation.R;
import com.mcxtzhang.swipemenulib.info.bean.PartTimeInfo;

/**
 * Created by Administrator on 2018/4/16.
 */

public class PartTimeAdapter extends SuperBaseAdapter<PartTimeInfo> {
    public PartTimeAdapter(Context context, List<PartTimeInfo> datas) {
        super(context, datas);
    }

    @Override
    protected void convert(BaseViewHolder holder, PartTimeInfo item, int position) {

        holder.setText(R.id.parttime_item_title, item.job_title);
        holder.setText(R.id.parttime_item_date, item.job_apply_time);
        holder.setText(R.id.parttime_item_growthnum, "+" + item.job_title);

    }

    @Override
    protected int getItemViewLayoutId(int position, PartTimeInfo item) {
        return R.layout.parttime_item;
    }
}
