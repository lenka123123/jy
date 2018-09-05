package sinia.com.baihangeducation.release.adapter;

import android.content.Context;
import android.view.View;

import com.example.framwork.adapter.BaseViewHolder;
import com.example.framwork.adapter.SuperBaseAdapter;

import java.util.List;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.find.campus.info.bean.CampusInterestingListInfo;
import sinia.com.baihangeducation.supplement.base.Goto;

public class SchoolFunAdapter extends SuperBaseAdapter<CampusInterestingListInfo> {
    private Context context;
    public SchoolFunAdapter(Context context, List<CampusInterestingListInfo> data) {
        super(context, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder holder, final CampusInterestingListInfo item, int position) {

        holder.setImageUrl(R.id.campus_funthing_item_img, item.fun_images, R.drawable.new_errorlogo_sp);
        holder.setText(R.id.campus_funthing_item_title,item.fun_title);
        holder.setText(R.id.campus_funthing_item_date,item.fun_add_date);
        holder.setText(R.id.campus_funthing_item_looknum,"浏览了" + item.fun_look_num + "次");
        holder.setOnClickListener(R.id.campus_funthing_item, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Goto.toCampusInterestingDetailActivity(context,item.fun_id);
            }
        });
    }

    @Override
    protected int getItemViewLayoutId(int position, CampusInterestingListInfo item) {
        return R.layout.campus_funthing_item;
    }
}
