package sinia.com.baihangeducation.release.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.release.info.bean.JobTagListInfo;

/**
 * Created by Administrator on 2018/4/15.
 */

public class TagAdapter extends com.zhy.view.flowlayout.TagAdapter {

    private List<JobTagListInfo> mData;
    private LayoutInflater mLayoutInflater;
    private TagFlowLayout mFlowLayout;


    public TagAdapter(Context context, TagFlowLayout mFlowLayout, List<JobTagListInfo> datas) {
        super(datas);
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mData = datas;
        this.mFlowLayout = mFlowLayout;
    }

    @Override
    public View getView(FlowLayout parent, int position, Object o) {

        TextView textView = (TextView) mLayoutInflater.inflate(R.layout.wantjob_item, mFlowLayout, false);
        textView.setText(mData.get(position).tag_name);
        textView.setBackgroundResource(R.drawable.adapter_select);
        return textView;
    }

}
