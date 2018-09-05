package sinia.com.baihangeducation.find.adapter;

import android.content.Context;

import com.example.framwork.adapter.BaseViewHolder;
import com.example.framwork.adapter.SuperBaseAdapter;

import java.util.List;

import sinia.com.baihangeducation.R;
import com.mcxtzhang.swipemenulib.info.bean.ShareEveryDayDetailCommentInfo;

/**
 * Created by Administrator on 2018/4/25.
 */

public class ShareEveryDayDetailMessageAdapter extends SuperBaseAdapter<ShareEveryDayDetailCommentInfo>{
    public ShareEveryDayDetailMessageAdapter(Context context, List<ShareEveryDayDetailCommentInfo> data) {
        super(context, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, ShareEveryDayDetailCommentInfo item, int position) {

        holder.setRoundImageUrl(R.id.shareeverydaydetailmessageitem_img,item.comment_user_avatar,R.drawable.new_eorrlogo);
        holder.setText(R.id.shareeverydaydetailmessageitem_name,item.comment_user_nickname);
        holder.setText(R.id.shareeverydaydetailmessageitem_message,item.comment_content);
        holder.setText(R.id.shareeverydaydetailmessageitem_time,item.comment_add_date);

    }

    @Override
    protected int getItemViewLayoutId(int position, ShareEveryDayDetailCommentInfo item) {
        return R.layout.shareeverydaydetailmessageitem;
    }
}
