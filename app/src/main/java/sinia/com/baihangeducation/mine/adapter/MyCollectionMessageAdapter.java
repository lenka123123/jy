package sinia.com.baihangeducation.mine.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.example.framwork.adapter.BaseViewHolder;
import com.example.framwork.adapter.SuperBaseAdapter;
import com.mcxtzhang.swipemenulib.info.bean.MyCollectionJobTagInfo;
import com.mcxtzhang.swipemenulib.info.bean.MyCollectionListInfo;
import com.mcxtzhang.swipemenulib.info.bean.MyCollectionMessage;

import java.util.List;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.mine.presenter.MyCollectionPresenter;
import sinia.com.baihangeducation.supplement.base.Goto;

/**
 * Created by Administrator on 2018/3/30.
 */

public class MyCollectionMessageAdapter extends SuperBaseAdapter<MyCollectionMessage.Message> {
    private Context context;
    private MyCollectionPresenter presenter;
    private List<MyCollectionMessage.Message> data;

    public MyCollectionMessageAdapter(Context context, List<MyCollectionMessage.Message> data) {
        super(context, data);
        this.context = context;
        this.presenter = new MyCollectionPresenter((Activity) context);
        this.data = data;
    }


    @Override
    protected void convert(BaseViewHolder holder, final MyCollectionMessage.Message item, final int position) {

        holder.setRoundImageUrl(R.id.fragment_mine_collection_alltimeitem_img, item.train_cover, R.drawable.new_eorrlogo);
        holder.setText(R.id.fragment_mine_collection_alltimeitem_job, item.train_title);
        holder.setText(R.id.fragment_mine_collection_alltimeitem_company, item.train_city_name);
        holder.setText(R.id.fragment_mine_collection_alltimeitem_adressanddate, item.train_add_date);

        holder.setOnClickListener(R.id.fragment_mine_collection_alltimeitem, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Goto.toTraingDetailActivity(context, item.train_id);
            }
        });
        //删除功能未写

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
    protected int getItemViewLayoutId(int position, MyCollectionMessage.Message item) {
        return R.layout.fragment_mine_collection_alltimeitem;
    }


}
