package sinia.com.baihangeducation.find.campus.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import sinia.com.baihangeducation.R;
import com.mcxtzhang.swipemenulib.info.bean.CityInfo;


/**
 * 父类别 适配器
 * @author ansen
 * @create time 2015-09-25
 */
public class ParentCategoryAdapter extends BaseAdapter {
    private Context mContext;
    private List<CityInfo> mCityDatas;
    private int pos;

    public ParentCategoryAdapter(Context context,List<CityInfo> mCityDatas) {
        mContext = context;
        this.mCityDatas = mCityDatas;
    }

    @Override
    public int getCount() {
        return mCityDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_address_filter_view, null);
            holder.adressName = convertView.findViewById(R.id.tv_title);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.adressName.setText(mCityDatas.get(position).name);

        if(pos==position){
            Log.i("Adapter+位置信息",pos+"");
            holder.adressName.setTextColor(mContext.getResources().getColor(R.color.white));
            convertView.setBackgroundColor(mContext.getResources().getColor(R.color.pink));
        }else{
            holder.adressName.setTextColor(mContext.getResources().getColor(R.color.pink));
            convertView.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        }
        return convertView;
    }

    private class ViewHolder {
        private TextView adressName;
    }

    public void setSelectedPosition(int pos) {
        this.pos = pos;
    }

    public int getPos() {
        return pos;
    }
}
