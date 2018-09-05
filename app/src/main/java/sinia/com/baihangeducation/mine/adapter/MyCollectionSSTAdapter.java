package sinia.com.baihangeducation.mine.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.example.framwork.adapter.BaseViewHolder;
import com.example.framwork.adapter.SuperBaseAdapter;

import java.util.List;

import sinia.com.baihangeducation.R;
import com.mcxtzhang.swipemenulib.info.bean.MyCollectionListInfo;
import sinia.com.baihangeducation.mine.presenter.MyCollectionPresenter;
import sinia.com.baihangeducation.supplement.base.Goto;

/**
 * Created by Administrator on 2018/3/30.
 */

public class MyCollectionSSTAdapter extends SuperBaseAdapter<MyCollectionListInfo> {
    private Context context;
    private MyCollectionPresenter presenter;
    private List<MyCollectionListInfo> data;

    public MyCollectionSSTAdapter(Context context, List<MyCollectionListInfo> data) {
        super(context, data);
        this.context = context;
        this.data = data;
        this.presenter = new MyCollectionPresenter((Activity) context);
    }

    @Override
    protected void convert(BaseViewHolder holder, final MyCollectionListInfo item, final int position) {

        holder.setText(R.id.fragment_mine_collection_sstitem_title, item.raiders_title);
        holder.setText(R.id.fragment_mine_collection_sstitem_date, item.raiders_add_date);
        holder.setOnClickListener(R.id.fragment_mine_collection_sstitem, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Goto.toJobBangDetailActivity(context, item.raiders_id, "考研试卷", 5, 3);

//               Goto.toShareEveryDayDetailActivity(context, item.raiders_id);
            }
        });
        //删除功能未写
        holder.setOnClickListener(R.id.fragment_mine_collection_sstitem_detel, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.remove(position);
                presenter.detelMyCollection(item.collect_id);
                notifyDataSetChanged();
            }
        });

        holder.setInVisible(R.id.fragment_mine_collection_sstitem_line,false);
    }

    @Override
    protected int getItemViewLayoutId(int position, MyCollectionListInfo item) {
        return R.layout.fragment_mine_collection_sstitem;
    }
}
