package sinia.com.baihangeducation.mine.adapter;

import android.content.Context;
import android.view.View;

import com.example.framwork.adapter.BaseViewHolder;
import com.example.framwork.adapter.SuperBaseAdapter;

import java.util.Date;
import java.util.List;

import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.R;

import com.example.framwork.utils.SpCommonUtils;
import com.mcxtzhang.swipemenulib.info.bean.FragmentMessageInfo;
import com.mcxtzhang.swipemenulib.utils.TimeUtils;

import sinia.com.baihangeducation.supplement.base.Goto;

/**
 * Created by Administrator on 2018/4/28.
 */

public class FragmentMessageAdapter extends SuperBaseAdapter<FragmentMessageInfo> {
    private Context context;

    public FragmentMessageAdapter(Context context, List<FragmentMessageInfo> data) {
        super(context, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder holder, final FragmentMessageInfo item, int position) {
        holder.setText(R.id.fragment_message_item_name, item.title);
        holder.setText(R.id.fragment_message_item_content, item.content);

        if (item.date != null && item.date.length() > 12) {
            int dataLength = item.date.length();
            if (TimeUtils.isToday(item.date)) {
                holder.setText(R.id.fragment_message_item_date, item.date.substring(dataLength - 8, dataLength - 3));
            } else {
                holder.setText(R.id.fragment_message_item_date, item.date.substring(0, 11));
            }
        }

        //是否已阅读1是2否
        if (item.is_read == 1) {
            holder.getView(R.id.point).setVisibility(View.GONE);
        } else {
            holder.getView(R.id.point).setVisibility(View.VISIBLE);
        }

        holder.setOnClickListener(R.id.fragment_message_item, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Goto.toFragmentMessageDetailActivity(context, item);
                changePoint(holder, item);
            }
        });
    }


    private void changePoint(BaseViewHolder holder, FragmentMessageInfo item) {
        //是否已阅读1是2否
        if (item.is_read == 1) {
            return;
        }
        //是否已阅读1是2否
        String no_read_num_es = (String) SpCommonUtils.get(context, AppConfig.FINAL_NO_READ_NUM, "");

        if (Integer.valueOf(no_read_num_es) >= 1) {
            no_read_num_es = (Integer.valueOf(no_read_num_es) - 1) + "";
        } else {
            no_read_num_es = "0";
        }


        holder.getView(R.id.point).setVisibility(View.GONE);
        SpCommonUtils.put(context, AppConfig.FINAL_NO_READ_NUM, no_read_num_es);
    }

    @Override
    protected int getItemViewLayoutId(int position, FragmentMessageInfo item) {
        return R.layout.fragment_message_item;
    }
}
