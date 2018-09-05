package sinia.com.baihangeducation.newcampus.tabs.fun.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.newcampus.info.bean.FunTopicInfo;

public class MyGridViewAdapter extends BaseAdapter {
    private List<FunTopicInfo> gridView;
    private Context context;

    public MyGridViewAdapter(List<FunTopicInfo> gridView, Context context) {
        this.gridView = gridView;
        this.context = context;
    }

    @Override
    public int getCount() {
        return gridView.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //通过布局填充器LayoutInflater填充网格单元格内的布局
        View v = LayoutInflater.from(context).inflate(R.layout.campus_gridview_title_item, null);
        //使用findViewById分别找到单元格内布局的图片以及文字
        ImageView iv = (ImageView) v.findViewById(R.id.gridview_img);
        TextView tv = (TextView) v.findViewById(R.id.gridview_tv);
        //引用数组内元素设置布局内图片以及文字的内容

        Glide.with(context)
                .load(gridView.get(position).topic_logo)
                .into(iv);
        tv.setText(gridView.get(position).topic_title);
        //返回值一定为单元格整体布局v
        return v;
    }
}
