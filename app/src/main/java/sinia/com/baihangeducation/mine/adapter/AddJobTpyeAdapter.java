package sinia.com.baihangeducation.mine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import sinia.com.baihangeducation.R;
import com.mcxtzhang.swipemenulib.info.bean.AddJobTypeInfoList;

/**
 * Created by Administrator on 2018/3/26.
 */

public class AddJobTpyeAdapter extends BaseAdapter {

    private List<AddJobTypeInfoList> list;
    private LayoutInflater mLayoutInflater;

    public AddJobTpyeAdapter(Context context, List<AddJobTypeInfoList> list) {
        this.list = list;
        mLayoutInflater =  LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder ;
        if (convertView==null){
            viewHolder = new ViewHolder();
            convertView= mLayoutInflater.inflate(R.layout.textview_item,null);
            viewHolder.textView = convertView.findViewById(R.id.type);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.textView.setText(list.get(position).job_type_name);
        return convertView;
    }

    class ViewHolder{
        TextView textView;
    }
}
