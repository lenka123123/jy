package sinia.com.baihangeducation.find.adapter;

import android.content.Context;
import android.view.View;

import com.example.framwork.adapter.BaseViewHolder;
import com.example.framwork.adapter.SuperBaseAdapter;

import java.util.List;

import sinia.com.baihangeducation.R;
import com.mcxtzhang.swipemenulib.info.bean.GetShareEveryDayInfo;
import com.mcxtzhang.swipemenulib.info.bean.GetShareEveryDayTagInfo;
import sinia.com.baihangeducation.supplement.base.Goto;

/**
 * Created by Administrator on 2018/4/19.
 */

public class ShareEveryDayAdapter extends SuperBaseAdapter<GetShareEveryDayInfo> {
    public ShareEveryDayAdapter(Context context, List<GetShareEveryDayInfo> datas) {
        super(context, datas);
    }

    @Override
    protected void convert(BaseViewHolder holder, final GetShareEveryDayInfo item, int position) {
        holder.setRoundImageUrl(R.id.shareeverydaylist_item_logo, item.news_cover, R.drawable.new_eorrlogo);
        holder.setText(R.id.shareeverydaylist_item_title, item.news_title);
        holder.setText(R.id.shareeverydaylist_item_shortinfo, item.news_short_info);

        List<GetShareEveryDayTagInfo> mGetShareEveryDayTagInfo = item.news_tag_list;
        //如果tag列表是空的则隐藏标签
        if (mGetShareEveryDayTagInfo.size() != 0 || !mGetShareEveryDayTagInfo.isEmpty()) {
            for (int i = 0; i < mGetShareEveryDayTagInfo.size(); i++) {
//                Log.i("tag的名字",mGetShareEveryDayTagInfo.get(i).tag_name);
                if ((mGetShareEveryDayTagInfo.get(i).tag_name).equals("热门")) {
                    holder.setVisible(R.id.shareeverydaylist_item_hot, true);
                }
//                else {
//                    holder.setVisible(R.id.shareeverydaylist_item_hot, false);
//                }

                if ((mGetShareEveryDayTagInfo.get(i).tag_name).equals("精选")) {
                    holder.setVisible(R.id.shareeverydaylist_item_best, true);
                }
//                else {
//                    holder.setVisible(R.id.shareeverydaylist_item_best, false);
//                }
            }
        } else {
            holder.setVisible(R.id.shareeverydaylist_item_hot, false);
            holder.setVisible(R.id.shareeverydaylist_item_best, false);
        }
        holder.setOnClickListener(R.id.shareeverydaylist_item_layout, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Goto.toShareEveryDayDetailActivity(mContext,item.news_id);
            }
        });

        holder.setText(R.id.shareeverydaylist_item_time,item.news_add_date);
    }

    @Override
    protected int getItemViewLayoutId(int position, GetShareEveryDayInfo item) {
        return R.layout.shareeverydaylist_item;
    }
}
