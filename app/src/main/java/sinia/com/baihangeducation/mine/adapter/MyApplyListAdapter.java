package sinia.com.baihangeducation.mine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import sinia.com.baihangeducation.R;

import com.mcxtzhang.swipemenulib.info.bean.FindApplyPersonInfo;

public class MyApplyListAdapter extends BaseAdapter{
    private List<FindApplyPersonInfo> list;
    private LayoutInflater mLayoutInflater;

    public MyApplyListAdapter(Context context, List<FindApplyPersonInfo> list) {
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
        ViewHolder viewHolder;
        if (convertView==null){
            viewHolder = new ViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.applyperson_item,null);
            viewHolder.mApplyName = convertView.findViewById(R.id.applyperson_item_name);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        FindApplyPersonInfo personInfo = list.get(position);
        viewHolder.mApplyName.setText(personInfo.apply_user_name);
        return convertView;
    }

    class ViewHolder{
        private TextView mApplyName;
    }

}
