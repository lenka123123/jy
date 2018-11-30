package sinia.com.baihangeducation.club.mangerpower;

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
import android.widget.CompoundButton;

import com.mcxtzhang.swipemenulib.customview.GlideLoadUtils;

import java.util.ArrayList;
import java.util.List;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.club.club.interfaces.GetRequestListener;
import sinia.com.baihangeducation.club.club.model.ClubHomeModel;
import sinia.com.baihangeducation.club.searchschool.model.ClubSchoolList;
import sinia.com.baihangeducation.club.searchschool.view.SearchSchoolHolder;
import sinia.com.baihangeducation.supplement.base.Goto;

/**
 * 创建日期：2018/5/18 on 17:00
 * 描述:
 * 作者:yuxd Administrator
 */
public class MangerPowerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {


    private Context context;
    private final ClubHomeModel clubHomeModel;
    private MangerPowerHolder vh;

    private List<MangerPowerList> myDate = new ArrayList<>();
    private List<MangerPowerList.PowerList> lists = new ArrayList<>();


    public MangerPowerAdapter(Activity context) {
        this.context = context;
        clubHomeModel = new ClubHomeModel(context);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder viewHolder = null;
        //根据viewType生成viewHolder
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.manger_power_item, null);
        viewHolder = new MangerPowerHolder(view);
        view.setOnClickListener(this);

//            View view = inflater.inflate(R.layout.ranking_item, parent, false);
//           RankingRecentHolder holder = new RankingRecentHolder(view);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        vh = (MangerPowerHolder) holder;
        vh.itemView.setTag(position);   //3 3 3
        vh.open.setTag(position);

        int a = 0;
        for (int i = 0; i < myDate.size(); i++) {
            a += myDate.get(i).list.size();
          //  System.out.println(position + "===看看======" + a);

            vh.title.setVisibility(View.VISIBLE);
            if (position == 0 || position == a) {
                vh.title.setVisibility(View.VISIBLE);
                if (position == 0) {
                    vh.title.setText(myDate.get(0).name);
                } else {
                    vh.title.setText(myDate.get(i).name);
                }
                break;
            } else {
                vh.title.setVisibility(View.GONE);
            }
        }

        vh.name.setText(lists.get(position).permission_name);
        vh.open.setChecked(lists.get(position).checked.equals("1"));

        vh.open.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                //当按钮被按下时会触发此listener
                if (!compoundButton.isPressed()) return;

               // System.out.println(position + "===看看w=====" + b);
                activity.changPermiss(lists.get(position).permission_id, b ? "1" : "0");
            }
        });


    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    private MangerPowerActivity activity;

    public void setData(List<MangerPowerList> pDatas, MangerPowerActivity activity) {
        //System.out.println("后台数据" + pDatas.size());
        this.activity = activity;
        lists.clear();
        myDate.clear();

        myDate.addAll(pDatas);
        for (int i = 0; i < pDatas.size(); i++) {
            for (int j = 0; j < pDatas.get(i).list.size(); j++) {
                lists.add(pDatas.get(i).list.get(j));
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        int potion = (Integer) v.getTag();

        if (v.getId() == R.id.ranking_money) {


        } else {
//            Goto.toClubDetailActivity(context, mInviteListInfo.get(potion).id);
        }
    }
}



