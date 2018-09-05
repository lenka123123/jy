package sinia.com.baihangeducation.find.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.framwork.adapter.BaseViewHolder;
import com.example.framwork.adapter.SuperBaseAdapter;

import java.util.List;

import sinia.com.baihangeducation.R;

import com.mcxtzhang.swipemenulib.info.bean.JobBangClassSecondInfo;

import sinia.com.baihangeducation.supplement.base.Goto;

/**
 * Created by Administrator on 2018/4/25.
 */

public class JobBangClassSecondAdapter extends SuperBaseAdapter<JobBangClassSecondInfo> {
    private Context context;
    private String typeName;
    private int cocllectionType = 1;

    public JobBangClassSecondAdapter(Context context, List<JobBangClassSecondInfo> data, String type) {
        super(context, data);
        this.context = context;
        this.typeName = type;
    }

    @Override
    protected void convert(BaseViewHolder holder, final JobBangClassSecondInfo item, int position) {


        holder.setText(R.id.type, typeName);
        holder.setText(R.id.jobbangclass_item_title, item.raiders_title);
        holder.setText(R.id.jobbangclass_item_date, "浏览" + item.raiders_look_num + "  评论" + item.raiders_comment_num);
        holder.setText(R.id.jobbangclass_item_price, item.raiders_price);

        Glide.with(context).load(item.raiders_cover).error(R.drawable.logo).into((ImageView) holder.getView(R.id.logo));
        holder.setOnClickListener(R.id.jobbangclassseconditem, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Goto.toJobBangDetailActivity(context,item.raiders_id,5,4);
//                Goto.toJobBangDetailActivity(context,item.raiders_id,6,2);
//（1：全职 2：兼职；3：趣事；4:攻略 5试卷 6秘籍 7资讯（每日分享） 8培训）

                if (typeName.equals("攻略干货"))
                    cocllectionType = 4;
                if (typeName.equals("职场秘籍"))
                    cocllectionType = 6;
                if (typeName.equals("真题试卷"))
                    cocllectionType = 5;

                Goto.toJobBangDetailActivity(context, item.raiders_id, typeName, cocllectionType, 3);
            }
        });
    }

    @Override
    protected int getItemViewLayoutId(int position, JobBangClassSecondInfo item) {
        return R.layout.jobbang_recommend_item;
    }
}
