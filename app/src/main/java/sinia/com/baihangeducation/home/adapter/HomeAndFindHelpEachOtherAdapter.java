package sinia.com.baihangeducation.home.adapter;

import android.content.Context;
import android.view.View;

import com.example.framwork.adapter.BaseViewHolder;
import com.example.framwork.adapter.SuperBaseAdapter;

import java.util.List;

import sinia.com.baihangeducation.R;
import com.mcxtzhang.swipemenulib.info.bean.HomeAndFindHelpEachOtherInfo;
import sinia.com.baihangeducation.supplement.base.Goto;

/**
 * Created by Administrator on 2018/4/22.
 */

public class HomeAndFindHelpEachOtherAdapter extends SuperBaseAdapter<HomeAndFindHelpEachOtherInfo> {
    private Context context;

    public HomeAndFindHelpEachOtherAdapter(Context context, List<HomeAndFindHelpEachOtherInfo> data) {
        super(context, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder holder, final HomeAndFindHelpEachOtherInfo item, int position) {
        if (item.cooperation_title == null) {
            holder.setInVisible(R.id.findandhome_helpeachother_item_nodata, true);
            holder.setInVisible(R.id.findandhome_helpeachother_item_data, false);
        } else {
            holder.setInVisible(R.id.findandhome_helpeachother_item_nodata, false);
            holder.setInVisible(R.id.findandhome_helpeachother_item_data, true);

            holder.setRoundImageUrl(R.id.findandhome_helpeachother_item_img, item.cooperation_user_avatar, R.drawable.new_eorrlogo);
            holder.setText(R.id.findandhome_helpeachother_item_title, item.cooperation_title);
            holder.setText(R.id.findandhome_helpeachother_item_content, item.cooperation_content);
            holder.setText(R.id.findandhome_helpeachother_item_adressanddistance, item.cooperation_city_name + " " + item.cooperation_distance);
            holder.setText(R.id.findandhome_helpeachother_item_time, item.cooperation_add_date);
            holder.setText(R.id.findandhome_helpeachother_item_seenum, "浏览" + item.cooperation_look_num + "次");
            if (item.cooperation_is_paid == 1) {
                holder.setVisible(R.id.findandhome_helpeachother_item_reward, true);
            } else {
                holder.setVisible(R.id.findandhome_helpeachother_item_reward, false);
            }
            if (item.cooperation_status == 1) {
                holder.setVisible(R.id.findandhome_helpeachother_item_issolve, true);
            } else {
                holder.setVisible(R.id.findandhome_helpeachother_item_issolve, false);
            }
            holder.setOnClickListener(R.id.findandhome_helpeachother_item, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Goto.toHomeAndFindHelpEachOtherDetailActivity(context, item.cooperation_id);
                }
            });
        }
    }

    @Override
    protected int getItemViewLayoutId(int position, HomeAndFindHelpEachOtherInfo item) {
        return R.layout.findandhome_helpeachother_item;
    }
}
