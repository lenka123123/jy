package sinia.com.baihangeducation.mine.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.example.framwork.adapter.BaseViewHolder;
import com.example.framwork.adapter.SuperBaseAdapter;

import java.util.List;

import sinia.com.baihangeducation.R;
import com.mcxtzhang.swipemenulib.info.bean.MyCollectionJobTagInfo;
import com.mcxtzhang.swipemenulib.info.bean.MyCollectionListInfo;
import sinia.com.baihangeducation.mine.presenter.MyCollectionPresenter;
import sinia.com.baihangeducation.supplement.base.Goto;

/**
 * Created by Administrator on 2018/3/30.
 */

public class MyCollectionPartTimeAdapter extends SuperBaseAdapter<MyCollectionListInfo> {
    private Context context;
    private MyCollectionPresenter presenter;
    private List<MyCollectionListInfo> data;

    public MyCollectionPartTimeAdapter(Context context, List<MyCollectionListInfo> data) {
        super(context, data);
        this.context = context;
        this.presenter = new MyCollectionPresenter((Activity) context);
        this.data = data;
    }

    @Override
    protected void convert(BaseViewHolder holder, final MyCollectionListInfo item, final int position) {

        holder.setRoundImageUrl(R.id.fragment_mine_collection_parttimeitem_img, item.job_company_logo, R.drawable.new_eorrlogo);
        holder.setText(R.id.fragment_mine_collection_parttimeitem_job, item.job_title);
        holder.setText(R.id.fragment_mine_collection_parttimeitem_adressanddate, item.job_city_name + " " + item.job_add_date);
        List<MyCollectionJobTagInfo> myCollectionJobTagInfos = item.job_tag_list;
        for (int i = 0; i < myCollectionJobTagInfos.size(); i++) {
            if ((myCollectionJobTagInfos.get(i).tag_name) != null && (myCollectionJobTagInfos.get(i).tag_name).equals("日结")) {
                holder.setVisible(R.id.fragment_mine_collection_parttimeitem_daily, true);
            } else {
                holder.setVisible(R.id.fragment_mine_collection_parttimeitem_daily, false);
            }

            if ((myCollectionJobTagInfos.get(i).tag_name) != null && (myCollectionJobTagInfos.get(i).tag_name).equals("实名")) {
                holder.setVisible(R.id.fragment_mine_collection_parttimeitem_realname, true);
            } else {
                holder.setVisible(R.id.fragment_mine_collection_parttimeitem_realname, false);
            }
        }
        holder.setOnClickListener(R.id.fragment_mine_collection_parttimeitem, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Goto.toPartTimeJobDetailActivity(context, item.job_id);
            }
        });
        //删除功能未写

        holder.setOnClickListener(R.id.fragment_mine_collection_parttimeitem_detel, new View.OnClickListener() {
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
        return R.layout.fragment_mine_collection_parttimeitem;
    }
}
