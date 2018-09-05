package sinia.com.baihangeducation.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import sinia.com.baihangeducation.R;

public class GridViewSim extends BaseAdapter {


    private LayoutInflater layoutInflater;
    private List<String> list = new ArrayList<>();

    public GridViewSim(Context context, List<String> list) {
        list.clear();
        layoutInflater = LayoutInflater.from(context);

    }

    public void setData(List<String> listCity) {
        System.out.println("===000=====" + listCity.size());
        list.addAll(listCity);
        notifyDataSetChanged();

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
                    .inflate(R.layout.item_select_city, null);
            TextView textView = (TextView) view
                    .findViewById(R.id.text1);

            //获取自定义的类实例

            textView.setText(list.get(position));

        }
        return view;
    }

}
