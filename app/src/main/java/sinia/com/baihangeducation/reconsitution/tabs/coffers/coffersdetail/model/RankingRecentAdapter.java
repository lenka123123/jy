package sinia.com.baihangeducation.reconsitution.tabs.coffers.coffersdetail.model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.mcxtzhang.swipemenulib.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.reconsitution.tabs.coffers.coffersdetail.RankingRecentHandleHolder;

/**
 * 创建日期：2018/5/18 on 17:00
 * 描述:
 * 作者:yuxd Administrator
 */
public class RankingRecentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private List<CoffersDetail.Coffers> mInviteListInfo = new ArrayList<>();

    private Context context;
    private int myType;

    public RankingRecentAdapter(Context context, int myType) {


        this.myType = myType;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder viewHolder = null;
        //根据viewType生成viewHolder
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_my_coffes_item, null);
        viewHolder = new RankingRecentHandleHolder(view);
        view.setOnClickListener(this);

//            View view = inflater.inflate(R.layout.ranking_item, parent, false);
//           RankingRecentHolder holder = new RankingRecentHolder(view);

        return viewHolder;
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        RankingRecentHandleHolder vh = (RankingRecentHandleHolder) holder;
        // v记录类型 1:收入 2:提现 3:平台消费

        if (mInviteListInfo.get(position).type.equals("1")) {
            vh.money.setText("+ " + mInviteListInfo.get(position).total);
            vh.money.setTextColor(Color.RED);
        }

        if (mInviteListInfo.get(position).type.equals("2")) {
            vh.money.setText("- " + mInviteListInfo.get(position).total);
            vh.money.setTextColor(Color.BLACK);
        }

        vh.time.setText(TimeUtils.getStrTimeHM(mInviteListInfo.get(position).add_time));
        vh.title.setText(mInviteListInfo.get(position).title);


    }

    @Override
    public int getItemCount() {
        return mInviteListInfo.size();
    }

    public void setData(List<CoffersDetail.Coffers> pDatas, int currentPage) {
        System.out.println("后台数据" + pDatas.size());
        if (currentPage == 1) {
            mInviteListInfo.clear();
        }
        mInviteListInfo.addAll(pDatas);
    }

    @Override
    public void onClick(View view) {

    }
}



