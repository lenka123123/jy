package sinia.com.baihangeducation.mine.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;

import com.example.framwork.adapter.BaseViewHolder;
import com.example.framwork.adapter.SuperBaseAdapter;

import java.util.List;

import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.MyApplication;

import com.example.framwork.utils.SPUtils;
import com.mcxtzhang.swipemenulib.info.bean.Marjor;
import com.mcxtzhang.swipemenulib.info.bean.MyResumInfo;

/**
 * Created by Administrator on 2018/3/30.
 */

public class ChoiceMajor_2_Adapter extends SuperBaseAdapter<Marjor> {
    private Context context;
    private MyApplication application;
    private MyResumInfo myResumInfoCopy;

    public ChoiceMajor_2_Adapter(Context context, List<Marjor> data) {
        super(context, data);
        this.context = context;
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
                AppConfig.SCHOOLMAGOR = item.major_name;
                AppConfig.SCHOOLMAGORID = String.valueOf(item.major_id);
                SPUtils.getInstance().put(AppConfig.getContext(), "SCHOOLNAME", item.major_name);
//                myResumInfoCopy.education_exp.major_name = sort_item.major_name;
//                myResumInfoCopy.education_exp.major_id = sort_item.major_id + "";
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
