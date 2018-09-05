package sinia.com.baihangeducation.newcampus.tabs.fun.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.List;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.newcampus.tabs.addfriend.adapter.AddFriendHolder;
import sinia.com.baihangeducation.release.campus.tabs.heatfunlist.bean.HotFunListBean;

public class AddHeaderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private HotFunListBean mInviteListInfo = new HotFunListBean();

    private Context context;

    public AddHeaderAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        //根据viewType生成viewHolder

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hot_fun_list_item, null);
        viewHolder = new AddHeadHolder(view);

        view.setOnClickListener(this);

        //    View view = inflater.inflate(R.layout.ranking_item, parent, false);
        //   RankingRecentHolder holder = new RankingRecentHolder(view);

        return viewHolder;
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        AddFriendHolder vh = (AddFriendHolder) holder;
        Glide.with(context).load(mInviteListInfo.list.get(position).topic_logo).into(vh.imageView);
        vh.title.setText(mInviteListInfo.list.get(position).topic_title);

        holder.itemView.setTag(position);
    }


    @Override
    public int getItemCount() {
        if (mInviteListInfo.list == null) {
            return 0;
        } else
            return 4;
    }

    public void setData(sinia.com.baihangeducation.release.campus.tabs.heatfunlist.bean.HotFunListBean pDatas) {
        mInviteListInfo = pDatas;
    }


    @Override
    public void onClick(View v) {
        if (mItemClickListener != null) {
            mItemClickListener.onItemClick((Integer) v.getTag());
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private OnItemClickListener mItemClickListener;

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }


}
