package sinia.com.baihangeducation.club.searchschool.view;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mcxtzhang.swipemenulib.customview.GlideLoadUtils;

import java.util.ArrayList;
import java.util.List;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.club.searchschool.model.ClubSchoolList;
import sinia.com.baihangeducation.supplement.base.Goto;

/**
 * 创建日期：2018/5/18 on 17:00
 * 描述:
 * 作者:yuxd Administrator
 */
public class SearchSchoolAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {


    private String text = "";
    private List<ClubSchoolList.School> mInviteListInfo = new ArrayList<>();

    private Context context;

    public SearchSchoolAdapter(Context context) {
        this.context = context;
    }


    public void setChangeText(String text) {

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder viewHolder = null;
        //根据viewType生成viewHolder
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_club_list_item, null);
        viewHolder = new SearchSchoolHolder(view);
        view.setOnClickListener(this);

//            View view = inflater.inflate(R.layout.ranking_item, parent, false);
//           RankingRecentHolder holder = new RankingRecentHolder(view);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        SearchSchoolHolder vh = (SearchSchoolHolder) holder;
        vh.itemView.setTag(position);

//        if (position == 0) {
//            vh.view_line.setVisibility(View.INVISIBLE);
//        }

        vh.ranking_number.setText(mInviteListInfo.get(position).new_order);


        if (mInviteListInfo.get(position).fluctuate.equals("0")) {
            vh.ranking_up_img.setVisibility(View.GONE);
            vh.ranking_up_tx.setVisibility(View.GONE);
        } else {
            vh.ranking_up_tx.setText(mInviteListInfo.get(position).fluctuate);
            if (mInviteListInfo.get(position).fluctuate.startsWith("-")) {

                vh.ranking_up_tx.setText(mInviteListInfo.get(position).fluctuate.replace("-", ""));
                vh.ranking_up_img.setText("▼");   //    <!--▼▲   -->
                vh.ranking_up_img.setTextColor(context.getResources().getColor(R.color.club_item_green));
                vh.ranking_up_tx.setTextColor(context.getResources().getColor(R.color.club_item_green));
            } else {
                vh.ranking_up_img.setText("▲");
                vh.ranking_up_img.setTextColor(context.getResources().getColor(R.color.club_item_red));
                vh.ranking_up_tx.setTextColor(context.getResources().getColor(R.color.club_item_red));
            }
        }

        if (text.equals("")) {
            vh.ranking_school_name.setText(mInviteListInfo.get(position).name);
        } else {
            int index = mInviteListInfo.get(position).name.indexOf(text);
            if (index >= 0) {
                SpannableString spanString = new SpannableString(mInviteListInfo.get(position).name);
                ForegroundColorSpan span = new ForegroundColorSpan(Color.rgb(254, 58, 54));
                spanString.setSpan(span, index, index + text.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                vh.ranking_school_name.setText(spanString);
            }
        }
        vh.ranking_school_number.setText(mInviteListInfo.get(position).member_num);
        vh.ranking_money.setText(mInviteListInfo.get(position).income);
        GlideLoadUtils.getInstance().glideLoad(context, mInviteListInfo.get(position).logo, vh.ranking_logo, R.drawable.logo);


    }

    @Override
    public int getItemCount() {
        return mInviteListInfo.size();
    }

    public void setData(List<ClubSchoolList.School> pDatas, int currentPage, String msg) {
        this.text = msg;
        System.out.println("后台数据" + pDatas.size());
        if (currentPage == 1) {
            mInviteListInfo.clear();
        }
        mInviteListInfo.addAll(pDatas);
    }

    @Override
    public void onClick(View v) {
        int potion = (Integer) v.getTag();
        Goto.toClubDetailActivity(context, mInviteListInfo.get(potion).id);
    }
}



