package sinia.com.baihangeducation.home.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mcxtzhang.swipemenulib.customview.GlideLoadUtils;
import com.mcxtzhang.swipemenulib.info.bean.IndustryListInfo;

import java.util.ArrayList;
import java.util.List;

import sinia.com.baihangeducation.R;

public class GridViewHome extends BaseAdapter {


    private LayoutInflater layoutInflater;
    private List<IndustryListInfo> list=new ArrayList<>();
    private Activity context;

    public GridViewHome(Activity context, List<IndustryListInfo> list) {
        IndustryListInfo industryListInfo = new IndustryListInfo();
        industryListInfo.name = "更多";
        industryListInfo.icon = "100";
        list.add(list.size(), industryListInfo);
        this.list = list;
        layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }


    @Override
    public int getCount() {
        return list.size();
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

        View view = null;

        if (layoutInflater != null) {

            view = layoutInflater
                    .inflate(R.layout.textview_item_home_gradview, null);
            TextView textView = (TextView) view.findViewById(R.id.text1);
            ImageView img = (ImageView) view.findViewById(R.id.img);

            //获取自定义的类实例
            if (list.get(position).icon.endsWith("100")) {
                GlideLoadUtils.getInstance().glideLoadDefault(context,R.drawable.more_img,img,R.drawable.logo);
            } else {
                GlideLoadUtils.getInstance().glideLoad(context,list.get(position).icon,img,R.drawable.logo);
//                Glide.with(context).load(list.get(position).icon).error(R.drawable.logo).into(img);
            }

            textView.setText(list.get(position).name);

        }
        return view;
    }

}
