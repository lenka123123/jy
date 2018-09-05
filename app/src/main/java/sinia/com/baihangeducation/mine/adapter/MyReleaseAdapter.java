package sinia.com.baihangeducation.mine.adapter;

import android.content.Context;
import android.view.View;

import com.example.framwork.adapter.BaseViewHolder;
import com.example.framwork.adapter.SuperBaseAdapter;

import java.util.List;

import sinia.com.baihangeducation.R;
import com.mcxtzhang.swipemenulib.info.bean.MyReleaseInfo;
import sinia.com.baihangeducation.supplement.base.Goto;

/**
 * Created by Administrator on 2018/3/30.
 */

public class MyReleaseAdapter extends SuperBaseAdapter<MyReleaseInfo> {
    private Context context;

    public MyReleaseAdapter(Context context, List<MyReleaseInfo> data) {
        super(context, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder holder, final MyReleaseInfo item, int position) {

        holder.setText(R.id.helpeachotherfragment_item_title, item.title);
        holder.setText(R.id.helpeachotherfragment_item_seenum, "浏览了" + item.look_num + "次");
        holder.setText(R.id.helpeachotherfragment_item_saynum, "评论了" + item.comment_count + "条");
        holder.setText(R.id.helpeachotherfragment_item_applynum, "申请了" + item.apply_count + "人");
        holder.setText(R.id.helpeachotherfragment_item_adressanddate, item.city_name + " " + item.add_date);
        if ((item.status) == 2) {
            holder.setText(R.id.helpeachotherfragment_item_iscomplete, item.status_name);
            holder.setTextColor(R.id.helpeachotherfragment_item_iscomplete, R.color.color_C5C5C5_gray);
            holder.setEnabled(R.id.helpeachotherfragment_item_iscomplete, false);
        } else {
            holder.setText(R.id.helpeachotherfragment_item_iscomplete, "确认完成");
            holder.setEnabled(R.id.helpeachotherfragment_item_iscomplete, true);
        }

        holder.setOnClickListener(R.id.helpeachotherfragment_item_iscomplete, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击已完成
                Goto.toMyApplyListActivity(context, item.cooperation_id);
            }
        });
        holder.setOnClickListener(R.id.helpeachotherfragment_item, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Goto.toHomeAndFindHelpEachOtherDetailActivity(context, item.cooperation_id);
            }
        });

    }

    @Override
    protected int getItemViewLayoutId(int position, MyReleaseInfo item) {
        return R.layout.helpeachotherfragment_item;
    }


}
