package sinia.com.baihangeducation.find.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import sinia.com.baihangeducation.R;

import com.bumptech.glide.Glide;
import com.mcxtzhang.swipemenulib.info.bean.JobBangClassSecretListInfo;

/**
 * Created by Administrator on 2018/4/21.
 */

public class JobBangClassSecretAdapter extends BaseAdapter {
    private LayoutInflater mLayoutInflater;
    private List<JobBangClassSecretListInfo> mList;
    private Context context;

    public JobBangClassSecretAdapter(Context context, List<JobBangClassSecretListInfo> list) {
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mList = list;
        this.context = context;
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
            convertView = mLayoutInflater.inflate(R.layout.jobbang_recommend_item, null);
            viewHolder.mJobBangClassTitle = convertView.findViewById(R.id.jobbangclass_item_title);
            viewHolder.mJobBangClassDate = convertView.findViewById(R.id.jobbangclass_item_date);
            viewHolder.mJobBangClassPrice = convertView.findViewById(R.id.jobbangclass_item_price);
            viewHolder.mLogo = convertView.findViewById(R.id.logo);
            viewHolder.mType = convertView.findViewById(R.id.type);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        JobBangClassSecretListInfo jobBangClassSecretListInfo = mList.get(position);
        viewHolder.mJobBangClassTitle.setText(jobBangClassSecretListInfo.raiders_title);
        viewHolder.mJobBangClassDate.setText("浏览" + jobBangClassSecretListInfo.raiders_look_num + "  评论" + jobBangClassSecretListInfo.raiders_comment_num);
        viewHolder.mJobBangClassPrice.setText(jobBangClassSecretListInfo.raiders_price);
        Glide.with(context).load(jobBangClassSecretListInfo.raiders_cover).error(R.drawable.logo).into(viewHolder.mLogo);

        if (position >= 1) {
            viewHolder.mType.setText("攻略干货");
        } else {
            viewHolder.mType.setText("职场秘籍");
        }

        return convertView;
    }

    class ViewHolder {
        private TextView mJobBangClassTitle;                //标题
        private TextView mJobBangClassDate;                 //日期
        private TextView mJobBangClassPrice;                //价格
        private TextView mType;
        private ImageView mLogo;
    }
}
