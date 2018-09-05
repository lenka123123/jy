package sinia.com.baihangeducation.find.adapter;

import android.content.Context;
import android.view.View;

import com.example.framwork.adapter.BaseViewHolder;
import com.example.framwork.adapter.SuperBaseAdapter;

import java.util.List;

import sinia.com.baihangeducation.R;
import com.mcxtzhang.swipemenulib.info.bean.SearchResultInfomationInfo;
import com.mcxtzhang.swipemenulib.info.bean.SearchResultInfomationTagInfo;
import sinia.com.baihangeducation.supplement.base.Goto;

/**
 * Created by Administrator on 2018/4/19.
 */

public class SearchReasultInfomationAdapter extends SuperBaseAdapter<SearchResultInfomationInfo> {
    private Context context;

    public SearchReasultInfomationAdapter(Context context, List<SearchResultInfomationInfo> datas) {
        super(context, datas);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder holder, final SearchResultInfomationInfo item, int position) {
        holder.setRoundImageUrl(R.id.fragment_mine_collection_funandinfoitem_img, item.news_cover, R.drawable.new_eorrlogo);
        holder.setText(R.id.fragment_mine_collection_funandinfoitem_title, item.news_title);
        holder.setText(R.id.fragment_mine_collection_funandinfoitem_contant, item.news_short_info);
        List<SearchResultInfomationTagInfo> mSearchResultInfomationTagInfo = item.news_tag_list;
        //如果tag列表是空的则隐藏标签
        if (mSearchResultInfomationTagInfo.size() != 0 || !mSearchResultInfomationTagInfo.isEmpty()) {
            for (int i = 0; i < mSearchResultInfomationTagInfo.size(); i++) {
                if ((mSearchResultInfomationTagInfo.get(i).tag_name).equals("热门")) {
                    holder.setVisible(R.id.fragment_mine_collection_funandinfoitem_hotarticle, true);
                } else {
                    holder.setVisible(R.id.fragment_mine_collection_funandinfoitem_hotarticle, false);
                }

                if ((mSearchResultInfomationTagInfo.get(i).tag_name).equals("精选")) {
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
        holder.setVisible(R.id.fragment_mine_collection_funandinfoitem_detel,false);

        holder.setOnClickListener(R.id.fragment_mine_collection_funandinfoitem, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Goto.toShareEveryDayDetailActivity(context,item.news_id);
            }
        });

    }

    @Override
    protected int getItemViewLayoutId(int position, SearchResultInfomationInfo item) {
        return R.layout.fragment_mine_collection_funandinfoitem;
    }
}
