package sinia.com.baihangeducation.home.adapter;

import android.content.Context;
import android.view.View;

import com.example.framwork.adapter.BaseViewHolder;
import com.example.framwork.adapter.SuperBaseAdapter;

import java.util.List;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.MyApplication;
import com.mcxtzhang.swipemenulib.info.bean.CommentInfo;
import sinia.com.baihangeducation.supplement.base.Goto;
import sinia.com.baihangeducation.supplement.tool.BaseUtil;

/**
 * Created by Administrator on 2018/4/27.
 */

public class HomeAndFindHelpEachOtherDetailAdapter extends SuperBaseAdapter<CommentInfo> {
    private Context context;
    private MyApplication application;

    public HomeAndFindHelpEachOtherDetailAdapter(Context context, List<CommentInfo> data) {
        super(context, data);
        this.context = context;
        this.application = (MyApplication) context.getApplicationContext();
    }

    @Override
    protected void convert(BaseViewHolder holder, final CommentInfo item, int position) {
        holder.setRoundImageUrl(R.id.homeandfindhelpeachotherdetailheaditem_img, item.comment_user_avatar, R.drawable.new_eorrlogo);
        holder.setText(R.id.homeandfindhelpeachotherdetailheaditem_name, item.comment_user_nickname);
        holder.setText(R.id.homeandfindhelpeachotherdetailheaditem_message, item.comment_content);
        holder.setText(R.id.homeandfindhelpeachotherdetailheaditem_time, item.comment_add_date);
        holder.setText(R.id.homeandfindhelpeachotherdetailheaditem_messagenum, item.son_comment_num + "条回复");
        holder.setOnClickListener(R.id.homeandfindhelpeachotherdetailheaditem, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!BaseUtil.isLogin(context, application)) {
                    return;
                }
                Goto.toMessageActivity(context,item);
            }
        });
    }

    @Override
    protected int getItemViewLayoutId(int position, CommentInfo item) {
        return R.layout.homeandfindhelpeachotherdetailheaditem;
    }
}
