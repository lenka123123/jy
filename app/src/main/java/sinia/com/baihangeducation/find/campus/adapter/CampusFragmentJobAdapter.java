package sinia.com.baihangeducation.find.campus.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.framwork.glide.ImageLoaderUtils;

import java.util.List;

import sinia.com.baihangeducation.R;
import com.mcxtzhang.swipemenulib.info.bean.HomePartTimeInfo;

/**
 * Created by Administrator on 2018/4/28.
 */

public class CampusFragmentJobAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<HomePartTimeInfo> mJobInfo;
    private Context mContext;

    public CampusFragmentJobAdapter(Context context, List<HomePartTimeInfo> mJobInfo) {
        this.mInflater = LayoutInflater.from(context);
        this.mJobInfo = mJobInfo;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return mJobInfo.size();
    }

    @Override
    public Object getItem(int position) {
        return mJobInfo.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.fragment_mine_mysend_alltime_item, null);
            viewHolder.mJobInfoImag = convertView.findViewById(R.id.fragment_mine_mysend_alltime_item_logo);
            viewHolder.mJobInfoTitle = convertView.findViewById(R.id.fragment_mine_mysend_alltime_item_title);
            viewHolder.mJobInfoContent = convertView.findViewById(R.id.fragment_mine_mysend_alltime_item_job);
            viewHolder.mJobInfoAdressAndDate = convertView.findViewById(R.id.fragment_mine_mysend_alltime_item_adressanddate);
            viewHolder.mJobInfoPrice = convertView.findViewById(R.id.fragment_mine_mysend_alltime_item_salary);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        HomePartTimeInfo mHomePartTimeInfo = mJobInfo.get(position);
        ImageLoaderUtils.displayRound(mContext, viewHolder.mJobInfoImag, mHomePartTimeInfo.job_company_logo, R.drawable.new_eorrlogo);
        viewHolder.mJobInfoTitle.setText(mHomePartTimeInfo.job_title);
        viewHolder.mJobInfoContent.setText(mHomePartTimeInfo.job_company_name);
        viewHolder.mJobInfoAdressAndDate.setText(mHomePartTimeInfo.job_city_name + "  " + mHomePartTimeInfo.job_add_date);
        viewHolder.mJobInfoPrice.setText(mHomePartTimeInfo.job_money);

        return convertView;
    }

    class ViewHolder {
        private ImageView mJobInfoImag;
        private TextView mJobInfoTitle;
        private TextView mJobInfoContent;
        private TextView mJobInfoAdressAndDate;
        private TextView mJobInfoPrice;
    }
}
