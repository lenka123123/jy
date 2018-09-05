package sinia.com.baihangeducation.find.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.framwork.glide.ImageLoaderUtils;

import java.util.List;

import sinia.com.baihangeducation.R;
import com.mcxtzhang.swipemenulib.info.bean.JobBangClassADListInfo;

/**
 * Created by Administrator on 2018/4/23.
 */

public class FindMomentAddAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<JobBangClassADListInfo> mList;
    private Context mContext;

    public FindMomentAddAdapter(Context mContext, List<JobBangClassADListInfo> list) {
        this.mContext = mContext;
        this.mList = list;
        this.mInflater = LayoutInflater.from(mContext);
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
            convertView = mInflater.inflate(R.layout.fragment_home_middle_item, null);
            viewHolder.mImageView = convertView.findViewById(R.id.fragment_home_middle_item_img);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ImageLoaderUtils.display(mContext,viewHolder.mImageView,mList.get(position).cover_url,R.drawable.new_errorlogo_sp);
        return convertView;
    }

    class ViewHolder {
        private ImageView mImageView;
    }
}
