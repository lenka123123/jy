package sinia.com.baihangeducation.release.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import sinia.com.baihangeducation.R;

import com.mcxtzhang.swipemenulib.info.bean.HomeTraingSeachOrderInfo;

public class SiftUrNeedOrderAdapter extends BaseAdapter {
    private LayoutInflater mLayoutInflater;
    private List<HomeTraingSeachOrderInfo> mList;

    public SiftUrNeedOrderAdapter(Context context, List<HomeTraingSeachOrderInfo> list) {
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
            convertView = mLayoutInflater.inflate(R.layout.poplistview_item, null);
            viewHolder.mText = convertView.findViewById(R.id.poplistview_item_text);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        HomeTraingSeachOrderInfo orderInfo = mList.get(position);
        viewHolder.mText.setText(orderInfo.order_name);

        return convertView;
    }

    class ViewHolder {
        private TextView mText;                //标题
    }
}
