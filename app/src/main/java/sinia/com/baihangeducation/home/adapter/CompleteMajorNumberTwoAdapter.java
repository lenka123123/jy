package sinia.com.baihangeducation.home.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;

import com.example.framwork.adapter.BaseViewHolder;
import com.example.framwork.adapter.SuperBaseAdapter;

import java.util.List;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.MyApplication;
import com.mcxtzhang.swipemenulib.info.bean.Marjor;
import com.mcxtzhang.swipemenulib.info.bean.MyResumInfo;

import com.mcxtzhang.swipemenulib.utils.ACache;

/**
 * Created by Administrator on 2018/3/30.
 */

public class CompleteMajorNumberTwoAdapter extends SuperBaseAdapter<Marjor> {
    private Context context;
    private MyApplication application;
    private MyResumInfo myResumInfoCopy;
    private ACache aCache;

    public CompleteMajorNumberTwoAdapter(Context context, List<Marjor> data) {
        super(context, data);
        this.context = context;
        this.aCache = ACache.get(context);
        this.application = (MyApplication) context.getApplicationContext();
        this.myResumInfoCopy = application.getUserResumeInfoCopy();

    }

    @Override
    protected void convert(BaseViewHolder holder, final Marjor item, int position) {

        holder.setText(R.id.tv_choiceschoolormajoritem, item.major_name);
        holder.setOnClickListener(R.id.tv_choiceschoolormajoritem, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("大学专业", item.major_name + "1");
//                myResumInfoCopy.education_exp.major_name = item.major_name;
//                myResumInfoCopy.education_exp.major_id = item.major_id + "";
                aCache.put("COMPLETEMAJORNAME", item.major_name);
                aCache.put("COMPLETEMAJORID", item.major_id + "");
                if (Activity.class.isInstance(context)) {
                    // 转化为activity，然后finish就行了
                    Activity activity = (Activity) context;
                    activity.finish();
                }

            }
        });
    }

    @Override
    protected int getItemViewLayoutId(int position, Marjor item) {
        return R.layout.choiceschoolormajoritem;
    }
}
