package sinia.com.baihangeducation.release.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.release.info.bean.JobTypeListInfo;

/**
 * Created by Administrator on 2018/4/15.
 */

public class BelongIndutryAdapter extends TagAdapter {

    private List<JobTypeListInfo> mData;
    private LayoutInflater mLayoutInflater;
    private TagFlowLayout mFlowLayout;


    public BelongIndutryAdapter(Context context, TagFlowLayout mFlowLayout, List<JobTypeListInfo> datas) {
        super(datas);
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mData = datas;
        this.mFlowLayout = mFlowLayout;
    }

    @Override
    public View getView(FlowLayout parent, int position, Object o) {

        TextView textView = (TextView) mLayoutInflater.inflate(R.layout.wantjob_item, mFlowLayout, false);
        textView.setText(mData.get(position).type_name);
        return textView;
    }

}
