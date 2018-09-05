package sinia.com.baihangeducation.reconsitution.tabs.pay.tab;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mcxtzhang.swipemenulib.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.reconsitution.tabs.coffers.coffersdetail.RankingRecentHandleHolder;
import sinia.com.baihangeducation.reconsitution.tabs.coffers.coffersdetail.model.CoffersDetail;
import sinia.com.baihangeducation.supplement.base.Goto;

/**
 * 创建日期：2018/5/18 on 17:00
 * 描述:
 * 作者:yuxd Administrator
 */
public class PaiedListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private List<PayDetail.Detail> mInviteListInfo = new ArrayList<>();

    private Context context;

    public PaiedListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder viewHolder = null;
        //根据viewType生成viewHolder
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_my_pay_list_item, null);
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
        vh.itemView.setTag(position);
        // v记录类型 1:收入 2:提现 3:平台消费
        // order_pay_status;//支付状态    ( 1：待支付；2：已支付；3：失败；4：已取消 )
        if (mInviteListInfo.get(position).order_pay_status.equals("2")) {
            vh.time.setText(mInviteListInfo.get(position).order_add_time);
            vh.title.setText(mInviteListInfo.get(position).order_title);
        }
    }

    @Override
    public int getItemCount() {
        return mInviteListInfo.size();
    }

    public void setData(List<PayDetail.Detail> pDatas, int currentPage) {
        System.out.println("后台数据" + pDatas.size());
        if (currentPage == 1) {
            mInviteListInfo.clear();
        }
        mInviteListInfo.addAll(pDatas);
    }

    @Override
    public void onClick(View v) {
        int potion = (Integer) v.getTag();
        Goto.toJobBangPayDetailActivity(context, mInviteListInfo.get(potion).order_raiders_id, "", 5, 3);
    }
}



