package sinia.com.baihangeducation.club.club.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mcxtzhang.swipemenulib.customview.GlideLoadUtils;

import java.util.List;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.club.club.model.ClubHomeInfo;
import sinia.com.baihangeducation.supplement.base.Goto;

/**
 * Created by Administrator on 2018/4/24.
 */

public class ClubHorizontalAdapter extends RecyclerView.Adapter<ClubHorizontalAdapter.ViewHolder> implements View.OnClickListener {

    private Context context;
    private LayoutInflater mInflater;
    private List<ClubHomeInfo.LateList> data;

    public ClubHorizontalAdapter(Context context, List<ClubHomeInfo.LateList> data) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
        this.data = data;
    }

    public void setData(List<ClubHomeInfo.LateList> dataList) {
        data.clear();
        data.addAll(dataList);
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        int potion = (Integer) view.getTag();
        Goto.toClubDetailActivity(context, data.get(potion).id);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View arg0) {
            super(arg0);
        }

        ImageView mImg;
        TextView mSchoolNmae;
        TextView mSchoolPerson;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    /**
     * 创建ViewHolder
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = mInflater.inflate(R.layout.club_include_foot_horizontal_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(this);
        viewHolder.mImg = (ImageView) view.findViewById(R.id.id_index_gallery_item_image);
        viewHolder.mSchoolNmae = (TextView) view.findViewById(R.id.school_name);
        viewHolder.mSchoolPerson = (TextView) view.findViewById(R.id.school_person);

        return viewHolder;
    }

    /**
     * 设置值
     */
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        viewHolder.itemView.setTag(i);
        viewHolder.mSchoolNmae.setText(data.get(i).name);
        viewHolder.mSchoolPerson.setText(data.get(i).member_num + "人");
        ImageView img = viewHolder.mImg;
        GlideLoadUtils.getInstance().glideLoad(context, data.get(i).logo, img, R.drawable.logo);
    }

}
