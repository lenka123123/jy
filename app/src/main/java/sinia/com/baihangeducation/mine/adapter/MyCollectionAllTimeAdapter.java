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

public class MyCollectionAllTimeAdapter extends SuperBaseAdapter<MyCollectionListInfo> {
    private Context context;
    private MyCollectionPresenter presenter;
    List<MyCollectionListInfo> data;

    public MyCollectionAllTimeAdapter(Context context, List<MyCollectionListInfo> data) {
        super(context, data);
        this.context = context;
        this.data = data;
        this.presenter = new MyCollectionPresenter((Activity) context);
    }

    @Override
    protected void convert(BaseViewHolder holder, final MyCollectionListInfo item, final int position) {

        holder.setRoundImageUrl(R.id.fragment_mine_collection_alltimeitem_img, item.job_company_logo, R.drawable.new_eorrlogo);
        holder.setText(R.id.fragment_mine_collection_alltimeitem_job, item.job_title);
        holder.setText(R.id.fragment_mine_collection_alltimeitem_company, item.job_company_name);
        holder.setText(R.id.fragment_mine_collection_alltimeitem_adressanddate, item.job_city_name + " " + item.job_add_date);
        //删除功能未写
        holder.setOnClickListener(R.id.fragment_mine_collection_alltimeitem, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Goto.toAllJobDetailActivity(context, item.job_id);
            }
        });
        holder.setOnClickListener(R.id.fragment_mine_collection_alltimeitem_detel, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.remove(position);
                presenter.detelMyCollection(item.collect_id);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    protected int getItemViewLayoutId(int position, MyCollectionListInfo item) {
        return R.layout.fragment_mine_collection_alltimeitem;
    }
}
