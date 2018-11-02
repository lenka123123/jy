package sinia.com.baihangeducation.club.clubactive.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
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
public class ReCommendAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ActiveListData.ActiveList> mInviteListInfo = new ArrayList<>();

    private Context context;
    private int myType;

    public ReCommendAdapter(Context context, int myType) {
        this.myType = myType;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder viewHolder = null;
        //根据viewType生成viewHolder
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_my_club_recommend_item, null);
        viewHolder = new ActiveListHandleHolder(view);


//            View view = inflater.inflate(R.layout.ranking_item, parent, false);
//           RankingRecentHolder holder = new RankingRecentHolder(view);

        return viewHolder;
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        ActiveListHandleHolder vh = (ActiveListHandleHolder) holder;

        Glide.with(context).load(mInviteListInfo.get(position).cover).error(R.drawable.logo).into((ImageView) vh.img);
        vh.title.setText(mInviteListInfo.get(position).name);
        vh.time.setText(mInviteListInfo.get(position).start_time);
        vh.address.setText(mInviteListInfo.get(position).addr);
        vh.person.setText(mInviteListInfo.get(position).join_num);
        vh.free.setText(mInviteListInfo.get(position).expenditure);

        vh.active_item_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Goto.toShowHotActive(context, mInviteListInfo.get(position).activity_id);
            }
        });

//        if (mInviteListInfo.get(position).type.equals("1")) {
//            vh.money.setText("+ " + mInviteListInfo.get(position).total);
//            vh.money.setTextColor(Color.RED);
//        }
//
//        if (mInviteListInfo.get(position).type.equals("2")) {
//            vh.money.setText("- " + mInviteListInfo.get(position).total);
//            vh.money.setTextColor(Color.BLACK);
//        }
//
//        vh.time.setText(TimeUtils.getStrTimeHM(mInviteListInfo.get(position).add_time));
//        vh.title.setText(mInviteListInfo.get(position).title);
    }

    @Override
    public int getItemCount() {
        return mInviteListInfo.size();
    }

    public void setData(List<ActiveListData.ActiveList> pDatas, int currentPage) {
        System.out.println("后台数据" + pDatas.size() + "uuu" + mInviteListInfo.size());

        mInviteListInfo.clear();

        mInviteListInfo.addAll(pDatas);
    }


}



