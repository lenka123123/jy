package sinia.com.baihangeducation.mine.adapter;

import android.content.Context;
import android.view.View;

import com.example.framwork.adapter.BaseViewHolder;
import com.example.framwork.adapter.SuperBaseAdapter;

import java.util.List;

import sinia.com.baihangeducation.R;
import com.mcxtzhang.swipemenulib.info.bean.MyReleaseFunInfo;
import sinia.com.baihangeducation.supplement.base.Goto;

/**
 * Created by Administrator on 2018/3/30.
 */

public class MyReleaseFunAdapter extends SuperBaseAdapter<MyReleaseFunInfo> {
    private Context context;

    public MyReleaseFunAdapter(Context context, List<MyReleaseFunInfo> data) {
        super(context, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder holder, final MyReleaseFunInfo item, int position) {

        holder.setText(R.id.interestingfragment_item_title, item.title);
        holder.setText(R.id.interestingfragment_item_adressanddate, item.city_name + " " + item.add_date);
        holder.setOnClickListener(R.id.interestingfragment_item, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Goto.toCampusInterestingDetailActivity(context,item.fun_id);
            }
        });

    }

    @Override
    protected int getItemViewLayoutId(int position, MyReleaseFunInfo item) {
        return R.layout.interestingfragment_item;
    }
}
