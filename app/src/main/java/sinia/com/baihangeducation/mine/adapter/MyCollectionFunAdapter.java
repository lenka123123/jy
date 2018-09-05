package sinia.com.baihangeducation.mine.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.example.framwork.adapter.BaseViewHolder;
import com.example.framwork.adapter.SuperBaseAdapter;

import java.util.List;

import sinia.com.baihangeducation.R;
import com.mcxtzhang.swipemenulib.info.bean.MyCollectionListInfo;
import com.mcxtzhang.swipemenulib.info.bean.MyCollectionNewTagInfo;
import sinia.com.baihangeducation.mine.presenter.MyCollectionPresenter;
import sinia.com.baihangeducation.supplement.base.Goto;

/**
 * Created by Administrator on 2018/3/30.
 */

public class MyCollectionFunAdapter extends SuperBaseAdapter<MyCollectionListInfo> {
    private Context context;
    private MyCollectionPresenter presenter;
    private List<MyCollectionListInfo> data;

    public MyCollectionFunAdapter(Context context, List<MyCollectionListInfo> data) {
        super(context, data);
        this.context = context;
        this.data = data;
        this.presenter = new MyCollectionPresenter((Activity) context);
    }

    @Override
    protected void convert(BaseViewHolder holder, final MyCollectionListInfo item, final int position) {

        holder.setRoundImageUrl(R.id.fragment_mine_collection_funandinfoitem_img, item.fun_images, R.drawable.new_eorrlogo);
        holder.setText(R.id.fragment_mine_collection_funandinfoitem_title, item.fun_title);
        holder.setText(R.id.fragment_mine_collection_funandinfoitem_contant, item.fun_short_info);
        List<MyCollectionNewTagInfo> myCollectionNewTagInfos = item.news_tag_list;
        //如果tag列表是空的则隐藏标签
        if (myCollectionNewTagInfos!=null&&myCollectionNewTagInfos.size() != 0 ) {
            for (int i = 0; i < myCollectionNewTagInfos.size(); i++) {
                if ((myCollectionNewTagInfos.get(i).tag_name).equals("热门")) {
                    holder.setVisible(R.id.fragment_mine_collection_funandinfoitem_hotarticle, true);
                } else {
                    holder.setVisible(R.id.fragment_mine_collection_funandinfoitem_hotarticle, false);
                }

                if ((myCollectionNewTagInfos.get(i).tag_name).equals("精选")) {
                    holder.setVisible(R.id.fragment_mine_collection_funandinfoitem_bestseclet, true);
                } else {
                    holder.setVisible(R.id.fragment_mine_collection_funandinfoitem_bestseclet, false);
                }
            }
        } else {
            holder.setVisible(R.id.fragment_mine_collection_funandinfoitem_hotarticle, false);
            holder.setVisible(R.id.fragment_mine_collection_funandinfoitem_bestseclet, false);
        }
        //删除功能未写
        holder.setOnClickListener(R.id.fragment_mine_collection_funandinfoitem, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Goto.toCampusInterestingDetailActivity(context, item.fun_id);
            }
        });

        holder.setOnClickListener(R.id.fragment_mine_collection_funandinfoitem_detel, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.remove(position);
                presenter.detelMyCollection(item.collect_id);
                notifyDataSetChanged();
            }
        });


        holder.setInVisible(R.id.fragment_mine_collection_funandinfoitem_line,false);


    }

    @Override
    protected int getItemViewLayoutId(int position, MyCollectionListInfo item) {
        return R.layout.fragment_mine_collection_funandinfoitem;
    }
}
