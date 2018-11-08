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
import com.example.framwork.utils.SpCommonUtils;
import com.example.framwork.utils.Toast;

import java.util.ArrayList;
import java.util.List;

import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.club.applyclublist.ApplyClubListActivity;
import sinia.com.baihangeducation.club.applyclublist.ClubPersonClubListActivity;
import sinia.com.baihangeducation.club.applyclublist.interfaces.ApplySuccessListListener;
import sinia.com.baihangeducation.club.applyclublist.model.ApplyClubListBean;
import sinia.com.baihangeducation.club.applyclublist.model.GetPersonList;
import sinia.com.baihangeducation.club.club.interfaces.GetRequestListener;
import sinia.com.baihangeducation.supplement.base.Goto;

/**
 * 创建日期：2018/5/18 on 17:00
 * 描述:
 * 作者:yuxd Administrator
 */
public class GetPeronListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private List<GetPersonList.PersonList> mInviteListInfo = new ArrayList<>();

    private Context context;
    private boolean drop;
    private boolean setClubApply;
    private ClubPersonClubListActivity activity;

    public GetPeronListAdapter(Context context, ClubPersonClubListActivity activity, boolean drop, boolean setClubApply) {
        this.context = context;
        this.activity = activity;
        this.drop = drop;
        this.setClubApply = setClubApply;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder viewHolder = null;
        //根据viewType生成viewHolder
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.club_item_swipe_menu, null);
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
        vh.delete.setTag(position);
        vh.ok.setTag(position);
        vh.name.setText(mInviteListInfo.get(position).nickname);
        vh.type.setText("");


        if (drop && (mInviteListInfo.get(position).type.equals("5"))) {
            vh.delete.setVisibility(View.VISIBLE);
        } else {
            vh.delete.setVisibility(View.GONE);
        }

        if (is_chairman.equals("1") && !(mInviteListInfo.get(position).type.equals("1"))) {
            vh.ok.setVisibility(View.VISIBLE);
        } else {
            vh.ok.setVisibility(View.GONE);
        }


        if (mInviteListInfo.get(position).user_id.equals(AppConfig.USERID)) {
            vh.delete.setVisibility(View.GONE);
            vh.ok.setVisibility(View.GONE);
        }


        vh.accept.setText(mInviteListInfo.get(position).role_name);
        if (!mInviteListInfo.get(position).type.equals("5")) {
            vh.accept.setTextColor(context.getResources().getColor(R.color.red_fa3e3e));
        } else {
            vh.accept.setTextColor(context.getResources().getColor(R.color.gray_8080));
        }

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

        vh.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drop) {
                    removeData(position);
                } else {
                    notifyDataSetChanged();
                    Toast.getInstance().showErrorToast(context, "你没有权限");
                }

            }
        });
        vh.ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (setClubApply) {
                    Goto.toClubPower(context, mInviteListInfo.get(position).id);
                    notifyDataSetChanged();
                } else {
                    notifyDataSetChanged();
                    Toast.getInstance().showErrorToast(context, "你没有权限");
                }

            }
        });

//            GlideLoadUtils.getInstance().glideLoad(context, mInviteListInfo.get(position).avatar, vh.logo, R.drawable.logo);


    }


    /**
     * 删除一个数据的方法
     *
     * @param position 索引
     */
    public void removeData(int position) {
        // TODO 使用刷新单一个条目会出现问题,所以请不要使用
        activity.removeItem(mInviteListInfo.get(position), new GetRequestListener() {
            @Override
            public void setRequestSuccess(String msg) {
                mInviteListInfo.remove(position);
                notifyDataSetChanged();
            }

            @Override
            public void setRequestFail() {
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mInviteListInfo.size();
    }

    private String is_chairman;

    public void setData(List<GetPersonList.PersonList> pDatas, int currentPage, String is_chairman) {
        this.is_chairman = is_chairman;
        if (currentPage == 1) {
            mInviteListInfo.clear();
        }
        mInviteListInfo.addAll(pDatas);
    }

    @Override
    public void onClick(View v) {
        int potion = (Integer) v.getTag();
        Goto.toPersonScenter(context,
                mInviteListInfo.get(potion).mobile,mInviteListInfo.get(potion).nickname ,mInviteListInfo.get(potion).avatar );
    }
}



