package sinia.com.baihangeducation.home.adapter;

import android.content.Context;
import android.view.View;

import com.example.framwork.adapter.BaseViewHolder;
import com.example.framwork.adapter.SuperBaseAdapter;

import java.util.List;

import sinia.com.baihangeducation.R;
import com.mcxtzhang.swipemenulib.info.bean.HomeTraingDataInfo;
import sinia.com.baihangeducation.supplement.base.Goto;

/**
 * Created by Administrator on 2018/4/24.
 */

public class HomeTraingAdapter extends SuperBaseAdapter<HomeTraingDataInfo> {
    private Context context;

    public HomeTraingAdapter(Context context, List<HomeTraingDataInfo> data) {
        super(context, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder holder, final HomeTraingDataInfo item, int position) {

        holder.setImageUrl(R.id.fragment_home_traing_item_img, item.train_cover, R.drawable.new_errorlogo_sp);
        holder.setText(R.id.fragment_home_traing_item_company, item.train_title);
        holder.setText(R.id.fragment_home_traing_item_price, item.train_price);
        holder.setText(R.id.fragment_home_traing_item_adressanddate, item.train_city_name + " " + item.train_add_date);
        holder.setOnClickListener(R.id.fragment_home_traing_item, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转培训
                Goto.toTraingDetailActivity(context, item.train_id);
            }
        });
    }

    @Override
    protected int getItemViewLayoutId(int position, HomeTraingDataInfo item) {
        return R.layout.fragment_home_traing_item;
    }
}
