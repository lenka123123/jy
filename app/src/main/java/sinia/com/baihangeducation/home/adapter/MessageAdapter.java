package sinia.com.baihangeducation.home.adapter;

import android.content.Context;

import com.example.framwork.adapter.BaseViewHolder;
import com.example.framwork.adapter.SuperBaseAdapter;

import java.util.List;

import sinia.com.baihangeducation.R;
import com.mcxtzhang.swipemenulib.info.bean.CommentInfo;

/**
 * Created by Administrator on 2018/4/27.
 */

public class MessageAdapter extends SuperBaseAdapter<CommentInfo> {
    public MessageAdapter(Context context, List<CommentInfo> data) {
        super(context, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, CommentInfo item, int position) {
        if (item.comment_add_date==null){
            holder.setVisible(R.id.homeandfindhelpeachotherdetailheaditem_layout,false);
            holder.setVisible(R.id.homeandfindhelpeachotherdetailheaditem_time,false);
            holder.setVisible(R.id.homeandfindhelpeachotherdetailheaditem_nodata,true);

        }else {
            holder.setVisible(R.id.homeandfindhelpeachotherdetailheaditem_layout,true);
            holder.setVisible(R.id.homeandfindhelpeachotherdetailheaditem_time,true);
            holder.setVisible(R.id.homeandfindhelpeachotherdetailheaditem_nodata,false);
            holder.setRoundImageUrl(R.id.homeandfindhelpeachotherdetailheaditem_img, item.comment_user_avatar, R.drawable.new_eorrlogo);
            holder.setText(R.id.homeandfindhelpeachotherdetailheaditem_name, item.comment_user_nickname);
            holder.setText(R.id.homeandfindhelpeachotherdetailheaditem_message, item.comment_content);
            holder.setText(R.id.homeandfindhelpeachotherdetailheaditem_time, item.comment_add_date);
            holder.setVisible(R.id.homeandfindhelpeachotherdetailheaditem_messagenum, false);
        }
    }

    @Override
    protected int getItemViewLayoutId(int position, CommentInfo item) {
        return R.layout.homeandfindhelpeachotherdetailheaditem;
    }
}
