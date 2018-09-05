package sinia.com.baihangeducation.find.adapter;

import android.content.Context;
import android.view.View;

import com.example.framwork.adapter.BaseViewHolder;
import com.example.framwork.adapter.SuperBaseAdapter;

import java.util.List;

import sinia.com.baihangeducation.R;
import com.mcxtzhang.swipemenulib.info.bean.ReCommendedInfo;
import sinia.com.baihangeducation.supplement.base.Goto;

/**
 * Created by Administrator on 2018/4/23.
 */

public class ReCommendedAdapter extends SuperBaseAdapter<ReCommendedInfo> {
    private Context context;

    public ReCommendedAdapter(Context context, List<ReCommendedInfo> data) {
        super(context, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder holder, final ReCommendedInfo item, int position) {
        holder.setImageUrl(R.id.fragment_home_recommended_activity_item_img, item.recommend_cover, R.drawable.new_errorlogo_sp);
        holder.setText(R.id.fragment_home_recommended_activity_item_commpany, item.recommend_title);
        holder.setText(R.id.fragment_home_recommended_activity_item_date, item.recommend_add_date);
        holder.setOnClickListener(R.id.fragment_home_recommended_activity_item, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.recommend_type == 1) {
                    //跳转APP
                    switch (item.recommend_plate) {
                        case 1:
                            //兼职详情
                            Goto.toPartTimeJobDetailActivity(context, item.recommend_type_id);
                            break;
                        case 2:
                            //全职详情
                            Goto.toAllJobDetailActivity(context, item.recommend_type_id);
                            break;
                        case 3:
                            //培训详情
                            Goto.toAllJobDetailActivity(context, item.recommend_id);
                            break;
                        case 4:
                            Goto.toShareEveryDayDetailActivity(mContext,item.recommend_id);
                            break;
                    }
                } else {
                    //跳转h5
                    Goto.toWebView(context,item.recommend_title,item.recommend_url,R.drawable.new_realname_title_bg);
                }
            }
        });
    }

    @Override
    protected int getItemViewLayoutId(int position, ReCommendedInfo item) {
        return R.layout.fragment_home_recommended_activity_item;
    }
}
