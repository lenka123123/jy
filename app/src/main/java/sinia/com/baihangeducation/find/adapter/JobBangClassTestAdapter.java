package sinia.com.baihangeducation.find.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import sinia.com.baihangeducation.R;

import com.mcxtzhang.swipemenulib.info.bean.JobBangClassTestListInfo;

/**
 * Created by Administrator on 2018/4/21.
 */

public class JobBangClassTestAdapter extends BaseAdapter {
    private LayoutInflater mLayoutInflater;
    private List<JobBangClassTestListInfo> mList;

    public JobBangClassTestAdapter(Context context, List<JobBangClassTestListInfo> list) {
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
            convertView = mLayoutInflater.inflate(R.layout.jobbangclass_item, null);
            viewHolder.mJobBangClassTitle = convertView.findViewById(R.id.jobbangclass_item_title);
            viewHolder.mJobBangClassDate = convertView.findViewById(R.id.jobbangclass_item_date);
            viewHolder.mJobBangClassPrice = convertView.findViewById(R.id.jobbangclass_item_price);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        JobBangClassTestListInfo jobBangClassTestListInfo = mList.get(position);
        viewHolder.mJobBangClassTitle.setText(jobBangClassTestListInfo.raiders_title);
        viewHolder.mJobBangClassDate.setText("浏览" + jobBangClassTestListInfo.raiders_look_num + "  评论" + jobBangClassTestListInfo.raiders_comment_num);
        viewHolder.mJobBangClassPrice.setText(jobBangClassTestListInfo.raiders_price);

        return convertView;
    }

    class ViewHolder {
        private TextView mJobBangClassTitle;                //标题
        private TextView mJobBangClassDate;                 //日期
        private TextView mJobBangClassPrice;                //价格
    }
}
