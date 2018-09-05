package sinia.com.baihangeducation.mine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import sinia.com.baihangeducation.R;

import com.mcxtzhang.swipemenulib.info.bean.MyResumeJobInfo;

/**
 * Created by Administrator on 2018/4/9.
 */

public class JobExpAdapter extends BaseAdapter {

    private LayoutInflater mLayoutInflater;
    private List<MyResumeJobInfo> mList = new ArrayList<>();

    public JobExpAdapter(Context context, List<MyResumeJobInfo> list) {
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
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
            convertView = mLayoutInflater.inflate(R.layout.myresumejobexplistitem, null);
            viewHolder.mJobType = convertView.findViewById(R.id.jobinfo_item_job);
            viewHolder.mJob = convertView.findViewById(R.id.jobinfo_item_jobinfo);
            viewHolder.mJobTime = convertView.findViewById(R.id.jobinfo_time);
            viewHolder.mJobDetail = convertView.findViewById(R.id.jobinfo_item_detail);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        MyResumeJobInfo myResumeJobInfo = mList.get(position);
        viewHolder.mJobType.setText(myResumeJobInfo.type_name);
        viewHolder.mJob.setText(myResumeJobInfo.name);
        viewHolder.mJobTime.setText(myResumeJobInfo.job_time_name);
        viewHolder.mJobDetail.setText(myResumeJobInfo.note);

        return convertView;
    }

    class ViewHolder {
        TextView mJobType;
        TextView mJob;
        TextView mJobTime;
        TextView mJobDetail;
    }
}
