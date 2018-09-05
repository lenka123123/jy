package sinia.com.baihangeducation.mine.adapter;

import android.content.Context;

import com.example.framwork.adapter.BaseViewHolder;
import com.example.framwork.adapter.SuperBaseAdapter;

import java.util.List;

import sinia.com.baihangeducation.R;
import com.mcxtzhang.swipemenulib.info.bean.MyCouponsItemInfo;

/**
 * Created by Administrator on 2018/4/9.
 */

public class MyCouponsAdapter extends SuperBaseAdapter<MyCouponsItemInfo> {
    private Context context;

    public MyCouponsAdapter(Context context, List<MyCouponsItemInfo> data) {
        super(context, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder holder, MyCouponsItemInfo item, int position) {
        holder.setText(R.id.mycoupons_price, item.coupon_price);
    }

    @Override
    protected int getItemViewLayoutId(int position, MyCouponsItemInfo item) {
        return R.layout.mycoupons_item;
    }
}
