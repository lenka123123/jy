package sinia.com.baihangeducation.find.adapter;

import android.content.Context;
import android.view.View;

import com.example.framwork.adapter.BaseViewHolder;
import com.example.framwork.adapter.SuperBaseAdapter;

import java.util.List;

import sinia.com.baihangeducation.R;
import com.mcxtzhang.swipemenulib.info.bean.SearchResultSSTInfo;
import sinia.com.baihangeducation.supplement.base.Goto;

/**
 * Created by Administrator on 2018/4/19.
 */

public class SearchReasultSSTAdapter extends SuperBaseAdapter<SearchResultSSTInfo> {
    private Context context;
    private int cocllectionType;
    private int messageId;
    public SearchReasultSSTAdapter(Context context, List<SearchResultSSTInfo> datas,int cocllectionType,int messageId ) {
        super(context, datas);
        this.context = context;
        this.cocllectionType = cocllectionType;
        this.messageId = messageId;
    }

    @Override
    protected void convert(BaseViewHolder holder, final SearchResultSSTInfo item, int position) {
        holder.setText(R.id.fragment_mine_collection_sstitem_title, item.raiders_title);
        holder.setText(R.id.fragment_mine_collection_sstitem_date, item.raiders_add_date);
        //删除功能未做
        holder.setVisible(R.id.fragment_mine_collection_sstitem_detel,false);
        holder.setOnClickListener(R.id.fragment_mine_collection_sstitem, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Goto.toJobBangDetailActivity(context,item.raiders_id,cocllectionType,messageId);
            }
        });
    }

    @Override
    protected int getItemViewLayoutId(int position, SearchResultSSTInfo item) {
        return R.layout.fragment_mine_collection_sstitem;
    }
}
