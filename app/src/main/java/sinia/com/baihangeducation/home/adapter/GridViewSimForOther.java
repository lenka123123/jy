package sinia.com.baihangeducation.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mcxtzhang.swipemenulib.info.bean.IndustryListInfoHome;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import sinia.com.baihangeducation.R;

public class GridViewSimForOther extends BaseAdapter {


    private LayoutInflater layoutInflater;
    private List<IndustryListInfoHome> list = new ArrayList<>();
    private Context context;

    public GridViewSimForOther(Context context) {
        this.context = context;
        list.clear();
        layoutInflater = LayoutInflater.from(context);
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

            view = layoutInflater.inflate(R.layout.textview_item_black_for_otherr_more, null);
            TextView textView = (TextView) view.findViewById(R.id.text);
            ImageView imageView = (ImageView) view.findViewById(R.id.img);

            //获取自定义的类实例

            textView.setText(list.get(position).name);
            Glide.with(context).load(list.get(position).icon).into(imageView);

        }
        return view;
    }

    public void puData(List<IndustryListInfoHome> listInfos) {
        list.clear();

        list.addAll(listInfos);
        notifyDataSetChanged();
    }
}
