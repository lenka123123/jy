package sinia.com.baihangeducation.club.clubactive.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.club.clubactive.model.ActiveListData;
import sinia.com.baihangeducation.supplement.base.Goto;

/**
 * 创建日期：2018/5/18 on 17:00
 * 描述:
 * 作者:yuxd Administrator
 */
public class ReCommendForTestAdapter extends RecyclerView.Adapter<MyHolder> {

    private List<ActiveListData.ActiveList> mInviteListInfo = new ArrayList<>();

    private Context context;

    public ReCommendForTestAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_my_club_recommend_item_for, parent, false);
        MyHolder holder = new MyHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        ActiveListData.ActiveList date = mInviteListInfo.get(position);
        Glide.with(context).load(mInviteListInfo.get(position).cover).error(R.drawable.logo).into(holder.icon);
        holder.title.setText(date.name);
        holder.address.setText(date.addr);
        holder.time.setText(date.start_time);
        holder.free.setText(date.expenditure);
        holder.person.setText(date.join_num);
        holder.active_item_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("activity_id==" + mInviteListInfo.get(position).activity_id);
                Goto.toShowHotActive(context, mInviteListInfo.get(position).activity_id);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mInviteListInfo.size();
    }

//    view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_my_club_recommend_item, null);
//    viewHolder = new ActiveListHandleHolder(view);


//        Glide.with(context).load(mInviteListInfo.get(position).cover).error(R.drawable.logo).into((ImageView) vh.img);
//        vh.title.setText(mInviteListInfo.get(position).name);
//        vh.time.setText(mInviteListInfo.get(position).start_time);
//        vh.address.setText(mInviteListInfo.get(position).addr);
//        vh.person.setText(mInviteListInfo.get(position).join_num);
//        vh.free.setText(mInviteListInfo.get(position).expenditure);
//
//


    public void setData(List<ActiveListData.ActiveList> pDatas, int currentPage) {
        System.out.println("后台数据" + pDatas.size() + "uuu" + mInviteListInfo.size());

        mInviteListInfo.clear();
        mInviteListInfo.addAll(pDatas);
        notifyDataSetChanged();
    }


}



