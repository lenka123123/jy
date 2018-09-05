package sinia.com.baihangeducation.mine.adapter;

import android.content.Context;

import com.example.framwork.adapter.BaseViewHolder;
import com.example.framwork.adapter.SuperBaseAdapter;

import java.util.List;

import sinia.com.baihangeducation.R;
import com.mcxtzhang.swipemenulib.info.bean.TraingInfo;

/**
 * Created by Administrator on 2018/4/16.
 */

public class TraingAdapter extends SuperBaseAdapter<TraingInfo> {
    public TraingAdapter(Context context,List<TraingInfo> data) {
        super(context,data);
    }

    @Override
    protected void convert(BaseViewHolder holder, TraingInfo item, int position) {

        holder.setText(R.id.traing_item_title, item.training_title);
        holder.setText(R.id.traing_item_date, item.training_buy_time);
        holder.setText(R.id.traing_item_growthnum, "+" +item.training_growth);

    }

    @Override
    protected int getItemViewLayoutId(int position, TraingInfo item) {
        return R.layout.traing_item;
    }
}
