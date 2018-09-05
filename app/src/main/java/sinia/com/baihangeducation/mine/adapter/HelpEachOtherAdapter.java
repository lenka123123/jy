package sinia.com.baihangeducation.mine.adapter;

import android.content.Context;

import com.example.framwork.adapter.BaseViewHolder;
import com.example.framwork.adapter.SuperBaseAdapter;

import java.util.List;

import sinia.com.baihangeducation.R;
import com.mcxtzhang.swipemenulib.info.bean.HelpEachOtherInfo;

/**
 * Created by Administrator on 2018/4/16.
 */

public class HelpEachOtherAdapter extends SuperBaseAdapter<HelpEachOtherInfo> {

    public HelpEachOtherAdapter(Context context, List<HelpEachOtherInfo> mList) {
        super(context, mList);
    }

    @Override
    protected void convert(BaseViewHolder holder, HelpEachOtherInfo item, int position) {

        holder.setText(R.id.helpeachother_item_title, item.cooper_title);
        holder.setText(R.id.helpeachother_item_date, item.cooper_apply_time);
        holder.setText(R.id.helpeachother_item_growthnum, "+" +item.cooper_growth + "");
    }

    @Override
    protected int getItemViewLayoutId(int position, HelpEachOtherInfo item) {
        return R.layout.helpeachother_item;
    }
}
