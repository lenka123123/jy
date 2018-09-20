package sinia.com.baihangeducation.club.applyclublist.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.util.ArrayList;
import java.util.List;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.club.applyclublist.ApplyClubListActivity;
import sinia.com.baihangeducation.club.applyclublist.interfaces.ApplySuccessListListener;
import sinia.com.baihangeducation.club.applyclublist.model.ApplyClubListBean;
import sinia.com.baihangeducation.club.applyclublist.model.GetPersonList;

/**
 * 创建日期：2018/5/18 on 17:00
 * 描述:
 * 作者:yuxd Administrator
 */
public class GetPeronListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private List<GetPersonList.PersonList> mInviteListInfo = new ArrayList<>();

    private Context context;

    public GetPeronListAdapter(Context context) {
        this.context = context;
    }


    public void setChangeText(String text) {

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder viewHolder = null;
        //根据viewType生成viewHolder
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_club_person_list, null);
        viewHolder = new GetPersonListHolder(view);
        view.setOnClickListener(this);


//            View view = inflater.inflate(R.layout.ranking_item, parent, false);
//           RankingRecentHolder holder = new RankingRecentHolder(view);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        GetPersonListHolder vh = (GetPersonListHolder) holder;
        vh.itemView.setTag(position);

        vh.name.setText(mInviteListInfo.get(position).nickname);
        vh.type.setText(mInviteListInfo.get(position).role_name);
        vh.accept.setText(mInviteListInfo.get(position).income);


        if (!mInviteListInfo.get(position).avatar.equals("")) {
            Glide.with(context).load(mInviteListInfo.get(position).avatar).asBitmap().error(R.drawable.new_eorrlogo).centerCrop().into(new BitmapImageViewTarget(vh.logo) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable =
                            RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    vh.logo.setImageDrawable(circularBitmapDrawable);
                }
            });
        } else {
            Glide.with(context).load(R.drawable.new_eorrlogo).asBitmap().error(R.drawable.new_eorrlogo).centerCrop().into(new BitmapImageViewTarget(vh.logo) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable =
                            RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    vh.logo.setImageDrawable(circularBitmapDrawable);
                }
            });
        }


//            GlideLoadUtils.getInstance().glideLoad(context, mInviteListInfo.get(position).avatar, vh.logo, R.drawable.logo);


    }


    @Override
    public int getItemCount() {
        return mInviteListInfo.size();
    }

    public void setData(List<GetPersonList.PersonList> pDatas, int currentPage) {
        System.out.println("后台数据" + pDatas.size());
        if (currentPage == 1) {
            mInviteListInfo.clear();
        }
        mInviteListInfo.addAll(pDatas);
    }

    @Override
    public void onClick(View v) {
        int potion = (Integer) v.getTag();
//        Goto.toJobBangPayDetailActivity(context, mInviteListInfo.get(potion).order_raiders_id, "", 5, 3);
    }
}



