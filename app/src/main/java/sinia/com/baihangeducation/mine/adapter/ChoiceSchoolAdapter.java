package sinia.com.baihangeducation.mine.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;

import com.example.framwork.adapter.BaseViewHolder;
import com.example.framwork.adapter.SuperBaseAdapter;

import java.util.List;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.MyApplication;
import com.mcxtzhang.swipemenulib.info.bean.College;
import com.mcxtzhang.swipemenulib.info.bean.MyResumInfo;

/**
 * Created by Administrator on 2018/3/30.
 */

public class ChoiceSchoolAdapter extends SuperBaseAdapter<College> {
    private Context context;
    private MyApplication application;
    private MyResumInfo myResumInfoCopy;

    public ChoiceSchoolAdapter(Context context, List<College> data) {
        super(context, data);
        this.context = context;
        this.application = (MyApplication) context.getApplicationContext();
        this.myResumInfoCopy = application.getUserResumeInfoCopy();

    }

    @Override
    protected void convert(BaseViewHolder holder, final College item, int position) {

        holder.setText(R.id.tv_choiceschoolormajoritem, item.college_name);
        holder.setOnClickListener(R.id.tv_choiceschoolormajoritem, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("大学选择", item.college_name + "1");
                myResumInfoCopy.education_exp.school_name = item.college_name;
                myResumInfoCopy.education_exp.school_id = item.college_id + "";
                if (Activity.class.isInstance(context)) {
                    // 转化为activity，然后finish就行了
                    Activity activity = (Activity) context;
                    activity.finish();
                }

            }
        });
    }

    @Override
    protected int getItemViewLayoutId(int position, College item) {
        return R.layout.choiceschoolormajoritem;
    }
}
