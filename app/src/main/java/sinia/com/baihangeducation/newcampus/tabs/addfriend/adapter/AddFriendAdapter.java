package sinia.com.baihangeducation.newcampus.tabs.addfriend.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.newcampus.tabs.searchfriend.bean.SearchFriendListBean;

public class AddFriendAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private List<SearchFriendListBean.Firendes> mInviteListInfo = new ArrayList<>();

    private Context context;

    public AddFriendAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        //根据viewType生成viewHolder

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_friend_list_item, null);
        viewHolder = new AddFriendHolder(view);

        view.setOnClickListener(this);

        //    View view = inflater.inflate(R.layout.ranking_item, parent, false);
        //   RankingRecentHolder holder = new RankingRecentHolder(view);

        return viewHolder;
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        AddFriendHolder vh = (AddFriendHolder) holder;


        Glide.with(context).load(mInviteListInfo.get(position).avatar).error(R.drawable.logo).into(vh.imageView);

        if (position == 0) {
            vh.line.setVisibility(View.VISIBLE);
            vh.separate.setVisibility(View.VISIBLE);
            vh.separate.setText("推荐");
        } else if (position == 3) {
            vh.line.setVisibility(View.VISIBLE);
            vh.separate.setVisibility(View.VISIBLE);
            vh.separate.setText("猜你喜欢");
        } else if (position == 6) {
            vh.line.setVisibility(View.VISIBLE);
            vh.separate.setVisibility(View.VISIBLE);
            vh.separate.setText("附近的人");
        } else if (position == 9) {
            vh.line.setVisibility(View.VISIBLE);
            vh.separate.setVisibility(View.VISIBLE);
            vh.separate.setText("可能认识的人");
        } else {
            vh.line.setVisibility(View.GONE);
            vh.separate.setVisibility(View.GONE);
        }
        vh.title.setText(mInviteListInfo.get(position).nickname);
        vh.content.setText(mInviteListInfo.get(position).fans + "粉丝");
        holder.itemView.setTag(position);
    }


    @Override
    public int getItemCount() {
        return mInviteListInfo.size();
    }

    public void setData(List<SearchFriendListBean.Firendes> pDatas) {
        mInviteListInfo.addAll(pDatas);
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
