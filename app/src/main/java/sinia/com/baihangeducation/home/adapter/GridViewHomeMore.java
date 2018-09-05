package sinia.com.baihangeducation.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mcxtzhang.swipemenulib.info.bean.IndustryListInfo;
import com.mcxtzhang.swipemenulib.info.bean.IndustryListInfoHome;

import java.util.ArrayList;
import java.util.List;

import sinia.com.baihangeducation.R;

public class GridViewHomeMore extends BaseAdapter {


    private LayoutInflater layoutInflater;
    private List<IndustryListInfoHome> list = new ArrayList<>();
    private Context context;

    public GridViewHomeMore(Context context, List<IndustryListInfoHome> list) {

        for (int i = 0; i < 30; i++) {
            IndustryListInfoHome home=new IndustryListInfoHome();
            home.name="ww";
            home.icon="100";
            list.add(home);
        }
    }

    public void setData(List<IndustryListInfoHome> list) {
        list.addAll(list);
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        System.out.println("dddddddddd"+list.size());
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

            view = layoutInflater.inflate(R.layout.textview_item_home_gradview_look, null);
            TextView textView = (TextView) view.findViewById(R.id.text);
            ImageView img = (ImageView) view.findViewById(R.id.img);
            Glide.with(context).load(R.drawable.more_img).error(R.drawable.logo).into(img);
            //获取自定义的类实例
            if (list.get(position).icon.endsWith("100")) {
                Glide.with(context).load(R.drawable.more_img).error(R.drawable.logo).into(img);
            } else {
                Glide.with(context).load(list.get(position).icon).error(R.drawable.logo).into(img);
            }

            textView.setText(list.get(position).name);

        }
        return view;
    }

}
