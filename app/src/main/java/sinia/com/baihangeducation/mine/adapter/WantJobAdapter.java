package sinia.com.baihangeducation.mine.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.mcxtzhang.swipemenulib.customview.RoundTextView;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

import sinia.com.baihangeducation.R;

import com.mcxtzhang.swipemenulib.info.bean.WantJobList;

/**
 * Created by Administrator on 2018/4/15.
 */

public class WantJobAdapter extends TagAdapter {

    private List<WantJobList> mData;
    private LayoutInflater mLayoutInflater;
    private TagFlowLayout mFlowLayout;


    public WantJobAdapter(Context context, TagFlowLayout mFlowLayout, List<WantJobList> datas) {
        super(datas);
        Log.i("传输数据", datas.size() + "");
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mData = datas;
        this.mFlowLayout = mFlowLayout;
    }

    @Override
    public View getView(FlowLayout parent, int position, Object o) {

        RoundTextView textView = (RoundTextView) mLayoutInflater.inflate(R.layout.wantjob_item, mFlowLayout, false);
        textView.setText(mData.get(position).intention_name);
        textView.setBackgroundResource(R.drawable.adapter_select);
        return textView;
    }

}
