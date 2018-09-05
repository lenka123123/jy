package sinia.com.baihangeducation.home.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;
import com.example.framwork.glide.ImageLoaderUtils;

import java.util.List;

import sinia.com.baihangeducation.R;
import com.mcxtzhang.swipemenulib.info.bean.HomeNewsInfo;

/**
 * Created by Administrator on 2018/4/23.
 */

public class HomeMomentAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<HomeNewsInfo> mList;
    private Context mContext;

    public HomeMomentAdapter(Context mContext, List<HomeNewsInfo> list) {
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
        Log.i("查看空间是否为空",viewHolder.mImageView.toString());
        if (!isDestroy((Activity) mContext)){
            ImageLoaderUtils.display(mContext,viewHolder.mImageView,mList.get(position).news_cover,R.drawable.new_errorlogo_sp);
        }

        return convertView;
    }

    class ViewHolder {
        private ImageView mImageView;
    }

    //判断Activity是否Destroy
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static boolean isDestroy(Activity activity) {
        if (activity == null || activity.isFinishing() || (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && activity.isDestroyed())) {
            Log.i("查看问题点",(activity == null)+"111111");
            Log.i("查看问题点",(activity.isFinishing())+"22222222");
            Log.i("查看问题点",(activity.isDestroyed())+"33333333");
            Log.i("查看问题点",(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)+"4444444444");
            return true;
        } else {
            return false;
        }
    }
}
