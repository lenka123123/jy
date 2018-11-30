package com.mcxtzhang.swipemenulib.customview.listcustomlist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mcxtzhang.swipemenulib.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建日期：2018/5/18 on 17:00
 * 描述:
 * 作者:yuxd Administrator
 */
public class CustomDialogAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<DiscountDetail.Discount> mInviteListInfo = new ArrayList<>();

    private Context context;
    private int nextTimePosition;

    public CustomDialogAdapter(Context context) {

        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder viewHolder = null;
        //根据viewType生成viewHolder
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_list_custom_dialog_item, null);
        viewHolder = new CustomDialogHolder(view);


//            View view = inflater.inflate(R.layout.ranking_item, parent, false);
//           RankingRecentHolder holder = new RankingRecentHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final CustomDialogHolder vh = (CustomDialogHolder) holder;
        vh.itemView.setTag(position);

        vh.discount_number .setText(mInviteListInfo.get(position).coupon_price);
        vh.discount_content.setText(mInviteListInfo.get(position).coupon_name);
        vh.discount_time.setText(mInviteListInfo.get(position).start_time + " - " + mInviteListInfo.get(position).end_time);
        if (position == nextTimePosition){
            vh.discount_select.setImageResource(R.drawable.pay_select);
        }else {
            vh.discount_select.setImageResource(R.drawable.pay_cancel);
        }


        vh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemClickListener.onClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mInviteListInfo.size();
    }

    public void setData(List<DiscountDetail.Discount> pDatas, int currentPage, int nextTimePosition) {
        this.nextTimePosition = nextTimePosition;
//        for (int i = 0; i < 5; i++) {
//            DiscountDetail.Discount discount = new DiscountDetail.Discount();
//            discount.coupon_id = i + "";
//            discount.coupon_name = "" + i;
//            discount.coupon_price = "33";
//            discount.end_time = "";
//            discount.start_time = "";
//            discount.use_time = "";
//            pDatas.add(discount);
//        }

     //   System.out.println("后台数据" + pDatas.size());
        if (currentPage == 1) {
            mInviteListInfo.clear();
        }
        mInviteListInfo.addAll(pDatas);
    }

    private OnItemClickListener mItemClickListener;

    public interface OnItemClickListener {
        void onClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mItemClickListener = onItemClickListener;
    }
}



