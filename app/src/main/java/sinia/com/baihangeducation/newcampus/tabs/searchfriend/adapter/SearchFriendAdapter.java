package sinia.com.baihangeducation.newcampus.tabs.searchfriend.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.framwork.utils.Toast;

import java.util.ArrayList;
import java.util.List;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.newcampus.tabs.searchfriend.bean.SearchFriendListBean;
import sinia.com.baihangeducation.newcampus.tabs.searchfriend.interfaces.AddFollowDataListener;
import sinia.com.baihangeducation.newcampus.tabs.searchfriend.interfaces.FollowDataListener;
import sinia.com.baihangeducation.newcampus.tabs.searchfriend.interfaces.SearchFriendDataListener;
import sinia.com.baihangeducation.supplement.base.CommonModel;

public class SearchFriendAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private List<SearchFriendListBean.Firendes> mInviteListInfo = new ArrayList<>();

    private Context context;

    public SearchFriendAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        //根据viewType生成viewHolder

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_firend_list_item, null);
        viewHolder = new SearchFriendHolder(view);

        view.setOnClickListener(this);

        //    View view = inflater.inflate(R.layout.ranking_item, parent, false);
        //   RankingRecentHolder holder = new RankingRecentHolder(view);

        return viewHolder;
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final SearchFriendHolder vh = (SearchFriendHolder) holder;
        Glide.with(context).load(mInviteListInfo.get(position).avatar).error(R.drawable.logo).into(vh.imageView);

        vh.title.setText(mInviteListInfo.get(position).nickname);
        vh.content.setText(mInviteListInfo.get(position).fans + " 粉丝");


        if (mInviteListInfo.get(position).is_follow.equals("2")) {
            vh.addfollow.setBackgroundResource(R.drawable.add_follow_ed);
            vh.addfollow.setText("");
        }
        vh.addfollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommonModel commonModel = new CommonModel(context);
                commonModel.addFollow(mInviteListInfo.get(position).user_id, new FollowDataListener() {
                    @Override
                    public void getHotFunDataSuccess(int flag) {
                        if (flag == 2) {
                            vh.addfollow.setBackgroundResource(R.drawable.add_follow_ed);
                            vh.addfollow.setText("");
                            vh.addfollow.setClickable(false);
                        } else {
                            vh.addfollow.setBackgroundResource(R.drawable.attention_add);
                            vh.addfollow.setText("+");
                            vh.addfollow.setClickable(false);
                        }

                    }

                    @Override
                    public void getHotFunDataFail() {

                    }
                });
            }
        });

        holder.itemView.setTag(position);
    }


    @Override
    public int getItemCount() {
        return mInviteListInfo.size();
    }

    public void setData(List<SearchFriendListBean.Firendes> pDatas) {

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
