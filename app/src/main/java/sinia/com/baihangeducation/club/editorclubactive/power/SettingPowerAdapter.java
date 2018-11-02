package sinia.com.baihangeducation.club.editorclubactive.power;

import android.app.Activity;
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
import com.example.framwork.utils.Toast;

import java.util.ArrayList;
import java.util.List;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.club.applyclublist.ClubPersonClubListActivity;
import sinia.com.baihangeducation.club.applyclublist.model.GetPersonList;
import sinia.com.baihangeducation.club.applyclublist.view.GetPersonListHolder;
import sinia.com.baihangeducation.club.club.interfaces.GetRequestListener;
import sinia.com.baihangeducation.supplement.base.Goto;

/**
 * 创建日期：2018/5/18 on 17:00
 * 描述:
 * 作者:yuxd Administrator
 */
public class SettingPowerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Activity context;
    private boolean drop;
    private boolean setClubApply;
    private ClubPersonClubListActivity activity;
    private List<SettingPowerData> settingPowerDataList = new ArrayList<>();
    private SettingPowerActivity settingPowerActivity;
    private SettingPowerListHolder vh;
    private TextView currentPage;
    private int selectPosition = -1;


    public SettingPowerAdapter(Activity context, List<SettingPowerData> settingPowerDataList, SettingPowerActivity settingPowerActivity) {
        this.context = context;
        this.settingPowerDataList = settingPowerDataList;
        this.settingPowerActivity = settingPowerActivity;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder viewHolder = null;
        //根据viewType生成viewHolder
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.setting_power_item, null);
        viewHolder = new SettingPowerListHolder(view);

//            View view = inflater.inflate(R.layout.ranking_item, parent, false);
//           RankingRecentHolder holder = new RankingRecentHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        vh = (SettingPowerListHolder) holder;
        vh.itemView.setTag(position);
        vh.club_item_view.setTag(position);

        vh.name.setText(settingPowerDataList.get(position).role_name);
        vh.check.setVisibility(settingPowerDataList.get(position).selected.equals("1") ? View.VISIBLE : View.INVISIBLE);

        vh.club_item_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for (int i = 0; i < settingPowerDataList.size(); i++) {
                    System.out.println(i + "=======dddd==" + position);
                    if (position == i) {
                        settingPowerDataList.get(i).selected = "1";
                        selectPosition = position;
                    } else {
                        settingPowerDataList.get(i).selected = "0";
                    }
                }

                currentPage.post(new Runnable() {
                    @Override
                    public void run() {
                        notifyDataSetChanged();
                    }
                });

//
            }
        });

    }


    @Override
    public int getItemCount() {

        return settingPowerDataList.size();
    }

    public void setData(List<SettingPowerData> data, TextView currentPage) {
        this.currentPage = currentPage;
        selectPosition = -1;
        System.out.println("=======dddd==" + data.size());
        settingPowerDataList.addAll(data);
        notifyDataSetChanged();
        currentPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectPosition == -1) {
                    settingPowerActivity.setFinish();
                } else {
                    settingPowerActivity.setAppoint(settingPowerDataList.get(selectPosition).role_id);
                }

            }
        });
    }


}



