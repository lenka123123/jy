package sinia.com.baihangeducation.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import sinia.com.baihangeducation.R;

import com.mcxtzhang.swipemenulib.info.bean.JobInfoLikeJob;

/**
 * Created by Administrator on 2018/4/27.
 */

public class AllJobDetailAdapter extends BaseAdapter{
    private LayoutInflater mInflater;
    private List<JobInfoLikeJob> mLikeJob;
    private Context mContext;

    public AllJobDetailAdapter(List<JobInfoLikeJob> mLikeJob, Context mContext) {
        this.mInflater = LayoutInflater.from(mContext);
        this.mLikeJob = mLikeJob;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mLikeJob.size();
    }

    @Override
    public Object getItem(int position) {
        return mLikeJob.get(position);
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
            convertView = mInflater.inflate(R.layout.jobbangclass_item, null);
            viewHolder.mJobBangClassTitle = convertView.findViewById(R.id.jobbangclass_item_title);
            viewHolder.mJobBangClassDate = convertView.findViewById(R.id.jobbangclass_item_date);
            viewHolder.mJobBangClassPrice = convertView.findViewById(R.id.jobbangclass_item_price);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        JobInfoLikeJob mJobInfoLikeJob = mLikeJob.get(position);
        viewHolder.mJobBangClassTitle.setText(mJobInfoLikeJob.job_title);

//        viewHolder.mJobBangClassDate.setText(mJobInfoLikeJob.job_add_date);
//        viewHolder.mJobBangClassPrice.setText(mJobInfoLikeJob.job_money);

        return convertView;
    }

    class ViewHolder {
        private TextView mJobBangClassTitle;                //标题
        private TextView mJobBangClassDate;                 //日期
        private TextView mJobBangClassPrice;                //价格
    }
}
