package sinia.com.baihangeducation.newcampus.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.newcampus.tabs.answer.AppNoScrollerListView;
import sinia.com.baihangeducation.newcampus.tabs.answer.CommentItem;
import sinia.com.baihangeducation.newcampus.tabs.answer.UpdateCommetAdapter;
import sinia.com.baihangeducation.newcampus.tabs.searchfriend.bean.ChildCommentList;
import sinia.com.baihangeducation.newcampus.tabs.searchfriend.bean.CommentListBean;

public class CommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    private CommentListBean mInviteListInfo = new CommentListBean();
    private Context context;
    private String publish_user_nickname; //发的
    private String oneComment;  //// 第一评论这
    private EditText comment;  //// 第一评论这
    private List<CommentItem> commentsDatas;


    public CommentAdapter(Context context, String publish_user_nickname, EditText comment) {
        this.context = context;
        this.publish_user_nickname = publish_user_nickname;
        this.comment = comment;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        //根据viewType生成viewHolder

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_list_item, null);
        viewHolder = new CommentHolder(view);
        view.setOnClickListener(this);
        //    View view = inflater.inflate(R.layout.ranking_item, parent, false);
        //   RankingRecentHolder holder = new RankingRecentHolder(view);

        return viewHolder;
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        CommentHolder vh = (CommentHolder) holder;
        oneComment = mInviteListInfo.list.get(position).comment_user_nickname;
        Glide.with(context).load(mInviteListInfo.list.get(position).comment_user_avatar).into(vh.imageView);
        vh.comment_name.setText(oneComment);
        vh.comment_content.setText(mInviteListInfo.list.get(position).comment_content);
        vh.comment_time.setText(mInviteListInfo.list.get(position).comment_add_date);
        vh.answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                comment.setFocusable(true);
                comment.setFocusableInTouchMode(true);
                comment.requestFocus();
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(mInviteListInfo.list.get(position).comment_id);//parent_id  comment_parent_id
                }
            }
        });
        changeAres(vh.commentList, vh.digCommentBody, mInviteListInfo.list, position);
        holder.itemView.setTag(position);
    }

    private List<CommentItem> recursionData(List<?> comment_child_list) {  //主评列表下的comment_child_list

        if (comment_child_list != null && comment_child_list.size() >= 1) {
            for (int i = 0; i < comment_child_list.size(); i++) {
                ChildCommentList commentList = (ChildCommentList) comment_child_list.get(i);
                CommentItem commentItem = new CommentItem();
                commentItem.content = commentList.comment_content;
                commentItem.id = commentList.comment_id;
                commentItem.user.name = commentList.comment_user_nickname;
                commentItem.user.id = commentList.comment_user_id;
                commentItem.toReplyUser.id = commentList.comment_reply_id;
                commentItem.toReplyUser.name = commentList.comment_reply_nickname;
                if (commentList.comment_content != null && !commentList.comment_content.equals(""))
                    commentsDatas.add(commentItem);

                if (commentList.comment_child_list.size() >= 1 && commentList.comment_child_list.get(0) != null) {
                    recursionData(commentList.comment_child_list);
                }
            }

        }
        return commentsDatas;
    }


    private void changeAres(AppNoScrollerListView answer_ares, LinearLayout digCommentBody, List<CommentListBean.CommentList> list, int position) {

        commentsDatas = new ArrayList<>();
        // for (int i = 0; i < list.size(); i++) {  //主评
        digCommentBody.setVisibility(View.VISIBLE);
        if (list != null && list.size() >= 1 && list.get(position).comment_child_list.size() >= 1) {   // //主评列表下的comment_child_list

            UpdateCommetAdapter updateCommetAdapter = new UpdateCommetAdapter(context);
            List<CommentItem> yaoid = recursionData(list.get(position).comment_child_list);
            if (yaoid.size() >= 1) {
                updateCommetAdapter.setDatasource(yaoid);
                answer_ares.setAdapter(updateCommetAdapter);
            }

            updateCommetAdapter.setCommentClickListener(new sinia.com.baihangeducation.newcampus.tabs.answer.CommentAdapter.ICommentItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    comment.setFocusable(true);
                    comment.setFocusableInTouchMode(true);
                    comment.requestFocus();
                    if (mItemClickListener != null) {
                        mItemClickListener.onItemClick(yaoid.get(position).id);//parent_id  comment_parent_id
                    }
                }
            });
        } else {
            digCommentBody.setVisibility(View.INVISIBLE);
        }

    }


    @Override
    public int getItemCount() {
        System.out.println("1111111eeee" + mInviteListInfo.list.size());
        return mInviteListInfo.list.size();
    }

    public void setData(CommentListBean pDatas) {
        mInviteListInfo.list.addAll(pDatas.list);
    }

    public void cleanData() {
        if (mInviteListInfo != null && mInviteListInfo.list != null) {
            mInviteListInfo.list.clear();
//            notifyDataSetChanged();
        }
    }


    @Override
    public void onClick(View v) {

    }

    public interface OnItemClickListener {
        void onItemClick(String position);
    }

    private OnItemClickListener mItemClickListener;

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }


}
