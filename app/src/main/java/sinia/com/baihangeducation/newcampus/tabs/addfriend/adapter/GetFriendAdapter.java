package sinia.com.baihangeducation.newcampus.tabs.addfriend.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.newcampus.tabs.searchfriend.bean.GetFriendListBean;
import sinia.com.baihangeducation.newcampus.tabs.searchfriend.bean.SearchFriendListBean;
import sinia.com.baihangeducation.newcampus.tabs.searchfriend.interfaces.FollowDataListener;
import sinia.com.baihangeducation.supplement.base.CommonModel;
import sinia.com.baihangeducation.supplement.base.Goto;

public class GetFriendAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {


    private Context context;
    private int recommend;
    private int like;
    private int distance;

    public GetFriendAdapter(Context context) {
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


        if (position < recommend) {
            if (position == 0) {
                vh.line.setVisibility(View.VISIBLE);
                vh.separate.setVisibility(View.VISIBLE);
                vh.separate.setText("推荐");
                vh.separate.setTextColor(Color.BLACK);
            } else {
                vh.line.setVisibility(View.GONE);
                vh.separate.setVisibility(View.GONE);
            }
        } else if (position >= recommend && position < recommend + like) {
            if (position == recommend) {
                vh.line.setVisibility(View.VISIBLE);
                vh.separate.setVisibility(View.VISIBLE);
                vh.separate.setTextColor(Color.BLACK);
                vh.separate.setText("猜你喜欢");
            } else {
                vh.line.setVisibility(View.GONE);
                vh.separate.setVisibility(View.GONE);
            }

        } else if (position >= recommend + like && position < recommend + like + distance) {
            if (position == recommend + like) {
                vh.line.setVisibility(View.VISIBLE);
                vh.separate.setVisibility(View.VISIBLE);
                vh.separate.setText("附近的人");
                vh.separate.setTextColor(Color.BLACK);
            } else {
                vh.line.setVisibility(View.GONE);
                vh.separate.setVisibility(View.GONE);
            }

        } else if (position >= recommend + like + distance) {
            if (position == recommend + like + distance) {
                vh.line.setVisibility(View.VISIBLE);
                vh.separate.setVisibility(View.VISIBLE);
                vh.separate.setTextColor(Color.BLACK);
                vh.separate.setText("可能认识的人");
            } else {
                vh.line.setVisibility(View.GONE);
                vh.separate.setVisibility(View.GONE);
            }
        } else {
            vh.line.setVisibility(View.GONE);
            vh.separate.setVisibility(View.GONE);
        }


        if (position < recommend) {

            vh.title.setText(pDatas.recommend_list.get(position).nickname);
            Glide.with(context).load(pDatas.recommend_list.get(position).avatar).error(R.drawable.logo).into(vh.imageView);
            vh.content.setText(pDatas.recommend_list.get(position).fans + "粉丝");
            vh.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Goto.toHomePageAgain(pDatas.recommend_list.get(position).user_id, context);
                }
            });
            String follow = pDatas.recommend_list.get(position).is_follow;//   ( 1：未关注 2：已关注 3：本人 )
            if (follow.equals("2")) {
                vh.addfollow.setBackgroundResource(R.drawable.add_follow_ed);
                vh.addfollow.setText("");
            } else {
                vh.addfollow.setBackgroundResource(R.drawable.attention_add);
                vh.addfollow.setText("+关注");
                vh.addfollow.setTextColor(Color.RED);
            }
            zan(vh.addfollow, pDatas.recommend_list.get(position).user_id);

        } else if (position >= recommend && position < recommend + like) {
            vh.title.setText(pDatas.like_list.get(position - recommend).nickname);
            Glide.with(context).load(pDatas.like_list.get(position - recommend).avatar).error(R.drawable.logo).into(vh.imageView);
            vh.content.setText(pDatas.like_list.get(position - recommend).fans + "粉丝");

            vh.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Goto.toHomePageAgain(pDatas.like_list.get(position - recommend).user_id, context);
                }
            });


            String follow = pDatas.like_list.get(position - recommend).is_follow;//   ( 1：未关注 2：已关注 3：本人 )
            if (follow.equals("2")) {
                vh.addfollow.setBackgroundResource(R.drawable.add_follow_ed);
                vh.addfollow.setText("");
            } else {
                vh.addfollow.setBackgroundResource(R.drawable.attention_add);
                vh.addfollow.setText("+关注");
                vh.addfollow.setTextColor(Color.RED);
            }
            zan(vh.addfollow, pDatas.like_list.get(position - recommend).user_id);

        } else if (position >= recommend + like && position < recommend + like + distance) {
            Glide.with(context).load(pDatas.distance_list.get(position - (recommend + like)).avatar).error(R.drawable.logo).into(vh.imageView);
            vh.title.setText(pDatas.distance_list.get(position - recommend - like).nickname);
            String distances = pDatas.distance_list.get(position - recommend - like).distance;
            if (distances.endsWith("km")) {
                vh.content.setText(distances.replace("km", "公里以内"));
            } else {
                vh.content.setText(distances.replace("m", "米以内"));
            }
            vh.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Goto.toHomePageAgain(pDatas.distance_list.get(position - recommend - like).user_id, context);
                }
            });
            String follow = pDatas.distance_list.get(position - recommend - like).is_follow;//   ( 1：未关注 2：已关注 3：本人 )
            if (follow.equals("2")) {
                vh.addfollow.setBackgroundResource(R.drawable.add_follow_ed);
                vh.addfollow.setText("");
            } else {
                vh.addfollow.setBackgroundResource(R.drawable.attention_add);
                vh.addfollow.setText("+关注");
                vh.addfollow.setTextColor(Color.RED);
            }
            zan(vh.addfollow, pDatas.distance_list.get(position - recommend - like).user_id);

        } else if (position >= recommend + like + distance) {
            Glide.with(context).load(pDatas.possible_list.get(position - recommend - like - distance).avatar).error(R.drawable.logo).into(vh.imageView);
            vh.title.setText(pDatas.possible_list.get(position - recommend - like - distance).nickname);
            vh.content.setText("好友" + pDatas.possible_list.get(position - recommend - like - distance).possible + "关注了他");
            vh.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Goto.toHomePageAgain(pDatas.possible_list.get(position - recommend - like - distance).user_id, context);
                }
            });

            String follow = pDatas.possible_list.get(position - recommend - like - distance).is_follow;//   ( 1：未关注 2：已关注 3：本人 )
            if (follow.equals("2")) {
                vh.addfollow.setBackgroundResource(R.drawable.add_follow_ed);
                vh.addfollow.setText("");
            } else {
                vh.addfollow.setBackgroundResource(R.drawable.attention_add);
                vh.addfollow.setText("+关注");
                vh.addfollow.setTextColor(Color.RED);
            }
            zan(vh.addfollow, pDatas.possible_list.get(position - recommend - like - distance).user_id);
        }

        holder.itemView.setTag(position);
    }


    @Override
    public int getItemCount() {
        return pDatas.distance_list == null ? 0 : pDatas.distance_list.size() + pDatas.recommend_list.size() + pDatas.like_list.size() + pDatas.possible_list.size();
    }

    private GetFriendListBean pDatas = new GetFriendListBean();

    public void setData(GetFriendListBean pDatas) {


        this.pDatas = pDatas;
        recommend = pDatas.recommend_list.size();
        like = pDatas.like_list.size();
        distance = pDatas.distance_list.size();
        int possible = pDatas.possible_list.size();

        notifyDataSetChanged();
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

    private void zan(final TextView addfollow, final String user_id) {
        addfollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommonModel commonModel = new CommonModel(context);
                commonModel.addFollow(String.valueOf(user_id), new FollowDataListener() {
                    @Override
                    public void getHotFunDataSuccess(int flag) {
                        if (flag == 2) {
                            addfollow.setBackgroundResource(R.drawable.add_follow_ed);
                            addfollow.setText("");
                        } else {
                            addfollow.setBackgroundResource(R.drawable.attention_add);
                            addfollow.setText("+关注");
                            addfollow.setTextColor(Color.RED);
                        }
                    }

                    @Override
                    public void getHotFunDataFail() {

                    }
                });
            }
        });
    }

}
