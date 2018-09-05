package sinia.com.baihangeducation.mine.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Administrator on 2018/4/10.
 */

public class HelpEachOtherFragmentAdapter extends BaseAdapter {


    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    class ViewHolder {
        private TextView mHelpEachOtherFragmentAdapterTitle;
        private TextView mHelpEachOtherFragmentAdapterSeeNum;
        private TextView mHelpEachOtherFragmentAdapterSayNum;
        private TextView mHelpEachOtherFragmentAdapterApplyNum;
        private TextView mHelpEachOtherFragmentAdapterAdressAndTime;
        private Button mHelpEachOtherFragmentAdapterIsComplete;
    }
}
