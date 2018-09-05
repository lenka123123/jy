package com.mcxtzhang.swipemenulib.customview.listdialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.mcxtzhang.swipemenulib.R;

import java.util.List;

public class MyAdapter  extends BaseAdapter {
    private List<String> fruitList;
    Context context;

    //从构造函数中获取fruitList和Context对象.
    public MyAdapter(List<String> fruitList, Context context)
    {
        this.fruitList = fruitList;
        this.context = context;

    }

    public int getCount()
    {
        return fruitList.size();
    }

    public Object getItem(int position)
    {
        return null;
    }

    public long getItemId(int position)
    {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {


        View v;
        ViewHolder holder;

        //滑入ListView中的item view没有缓存
        if(convertView == null)
        {
            v = LayoutInflater.from(context).inflate(R.layout.activity_list_dialog_item, null);//动态加载滑入ListView的item的子布局文件，并绘制item view

            /*  缓存子布局文件中的控件对象*/
            holder = new ViewHolder();
            holder.fruitName = (TextView)v.findViewById(R.id.name);

            v.setTag(holder);
        }
        //滑入ListView中的item view有缓存
        else
        {
            v = convertView;					        //取出缓存的item View对象
            holder = (ViewHolder)v.getTag();	                        //重新获取ViewHolder对象
        }

        holder.fruitName.setText(fruitList.get(position));			//设置fruitName


        return v;
    }


    /*  定义ViewHolder用来缓存item view控件  */
    class ViewHolder
    {
        TextView fruitName;
    }

}
