package sinia.com.baihangeducation.find.adapter;

import android.content.Context;

import com.example.framwork.adapter.BaseViewHolder;
import com.example.framwork.adapter.SuperBaseAdapter;

import java.util.List;

import sinia.com.baihangeducation.R;
import com.mcxtzhang.swipemenulib.info.bean.SearchResultFunnyInfo;

/**
 * Created by Administrator on 2018/4/18.
 */

public class SearchReasultFunnyAdapter extends SuperBaseAdapter<SearchResultFunnyInfo> {
    public SearchReasultFunnyAdapter(Context context, List<SearchResultFunnyInfo> datas) {
        super(context, datas);
    }

    @Override
    protected void convert(BaseViewHolder holder, SearchResultFunnyInfo item, int position) {
        holder.setText(R.id.fragment_mine_collection_sstitem_title, item.fun_title);
        holder.setText(R.id.fragment_mine_collection_sstitem_date, item.fun_add_date);
        //删除未写
        holder.setVisible(R.id.fragment_mine_collection_sstitem_detel,false);

    }

    @Override
    protected int getItemViewLayoutId(int position, SearchResultFunnyInfo item) {
        return R.layout.fragment_mine_collection_sstitem;
    }
}
