package sinia.com.baihangeducation.club.systemmessage;

import android.app.Activity;
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
import sinia.com.baihangeducation.club.club.interfaces.GetRequestListener;
import sinia.com.baihangeducation.club.club.model.ClubHomeModel;
import sinia.com.baihangeducation.club.clubschoollist.model.ClubSchoolList;
import sinia.com.baihangeducation.club.clubschoollist.view.ClubSchoolListHolder;
import sinia.com.baihangeducation.club.im.utils.TimeFormat;
import sinia.com.baihangeducation.supplement.base.Goto;

/**
 * 创建日期：2018/5/18 on 17:00
 * 描述:
 * 作者:yuxd Administrator
 */
public class ClubSystemMeaageListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements View.OnClickListener, GetRequestListener {

    private ClubMessageList.SystemMessage deleteData = null;
    private String text = "";
    private List<ClubMessageList.SystemMessage> mInviteListInfo = new ArrayList<>();

    private ClubSystemListActivity context;
    private ClubMessageListHolder vh;

    public ClubSystemMeaageListAdapter(ClubSystemListActivity context) {
        this.context = context;
    }


    public void setChangeText(String text) {
        this.text = text;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder viewHolder = null;
        //根据viewType生成viewHolder
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_message_item, null);
        viewHolder = new ClubMessageListHolder(view);
        view.setOnClickListener(this);

//            View view = inflater.inflate(R.layout.ranking_item, parent, false);
//           RankingRecentHolder holder = new RankingRecentHolder(view);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        vh = (ClubMessageListHolder) holder;
        vh.itemView.setTag(position);
        vh.accept.setTag(position);
        vh.cancel.setTag(position);

//        GlideLoadUtils.getInstance().glideLoad(context, mInviteListInfo.get(position).logo, vh.ranking_logo, R.drawable.logo);

        vh.time.setText(mInviteListInfo.get(position).date);
        vh.title.setText(mInviteListInfo.get(position).content);
        vh.club.setText("所属社团:  " + mInviteListInfo.get(position).keywords.club_name);
        vh.time.setText(mInviteListInfo.get(position).date);

        //测试删除 不能删 类型    (  info：提示 check：审核 )
        if (mInviteListInfo.get(position).keywords.type.equals("info")) {
            vh.is_show_layout.setVisibility(View.GONE);
            vh.is_show_layout_view.setVisibility(View.GONE);
        } else {
            vh.is_show_layout.setVisibility(View.VISIBLE);
            vh.is_show_layout_view.setVisibility(View.VISIBLE);
        }

        if (mInviteListInfo.get(position).keywords.type.equals("info")) {
            vh.club_item_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteData = mInviteListInfo.get(position);
                    context.setReaded(deleteData.system_message_id);
                    Goto.toClubDetailActivity(context, mInviteListInfo.get(position).keywords.category_id);

                    if (deleteData != null) {
                        mInviteListInfo.remove(deleteData);
                        notifyDataSetChanged();
                    }
                }
            });
        }

        vh.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //是否通过审核    ( 1：通过 2：拒绝 )
                deleteData = mInviteListInfo.get(position);
                context.setPower(mInviteListInfo.get(position).keywords.check_type, mInviteListInfo.get(position).keywords.category_id,
                        mInviteListInfo.get(position).system_message_id, "1", ClubSystemMeaageListAdapter.this);
                context.setReaded(deleteData.system_message_id);
            }
        });
        vh.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteData = mInviteListInfo.get(position);
                context.setPower(mInviteListInfo.get(position).keywords.check_type, mInviteListInfo.get(position).keywords.category_id,
                        mInviteListInfo.get(position).system_message_id, "2", ClubSystemMeaageListAdapter.this);
                context.setReaded(deleteData.system_message_id);
            }
        });


//        vh.ranking_money.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (mInviteListInfo.get(position).is_apply.equals("1"))
//                    clubHomeModel.applyClub(mInviteListInfo.get(position).id, "", new GetRequestListener() {
//                        @Override
//                        public void setRequestSuccess(String msg) {
//
//                            ((ClubSchoolListHolder) holder).ranking_money.setText("已申请");
//
//                            System.out.println("======setRequestSuccess===" + msg);
////                            if (vh.ranking_money.getTag().equals(position))
////                                vh.ranking_money.setText("已申请");
//                        }
//
//                        @Override
//                        public void setRequestFail() {
//                            System.out.println("======setRequestSuccess=2==");
//                        }
//                    });
//            }
//        });


    }

    @Override
    public int getItemCount() {
        return mInviteListInfo.size();
    }

    public void setData(List<ClubMessageList.SystemMessage> pDatas, int currentPage) {
        System.out.println("后台数据" + pDatas.size());
        mInviteListInfo.clear();
//        ClubMessageList.SystemMessage sss = new ClubMessageList.SystemMessage();
//        sss.content = "aaa";
//        sss.date = "22";
//        sss.title = "ddd";
//        mInviteListInfo.add(0, sss);
//        mInviteListInfo.add(1, sss);
//        mInviteListInfo.add(2, sss);
        mInviteListInfo.addAll(pDatas);
    }

    @Override
    public void onClick(View v) {
        int potion = (Integer) v.getTag();
        if (v.getId() == R.id.ranking_money) {
        } else {
//            Goto.toClubDetailActivity(context, mInviteListInfo.get(potion).id);
        }
    }

    @Override
    public void setRequestSuccess(String msg) {
        if (deleteData != null) {
            mInviteListInfo.remove(deleteData);
            notifyDataSetChanged();
        }

    }

    @Override
    public void setRequestFail() {
        if (deleteData != null) {
            mInviteListInfo.remove(deleteData);
            notifyDataSetChanged();
        }
    }
}



