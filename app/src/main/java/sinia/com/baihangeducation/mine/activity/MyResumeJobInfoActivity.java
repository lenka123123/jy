package sinia.com.baihangeducation.mine.activity;

import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.mcxtzhang.commonadapter.lvgv.CommonAdapter;
import com.mcxtzhang.commonadapter.lvgv.ViewHolder;
import com.mcxtzhang.swipemenulib.SwipeMenuLayout;

import java.util.ArrayList;
import java.util.List;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.supplement.base.BaseActivity;
import sinia.com.baihangeducation.MyApplication;

import com.mcxtzhang.swipemenulib.info.bean.MyResumInfo;
import com.mcxtzhang.swipemenulib.info.bean.MyResumeJobInfo;

import sinia.com.baihangeducation.supplement.base.Goto;

/**
 * Created by Administrator on 2018/4/4.
 */

public class MyResumeJobInfoActivity extends BaseActivity {

    private ListView mListView;
    private List<MyResumeJobInfo> mJobInfoData;

    @Override
    public int initLayoutResID() {
        return R.layout.myresume_jobinfolistview;
    }

    @Override
    protected void initData() {


        mCommonTitle.setCenterText(R.string.jobexperience);
        mCommonTitle.setBackground(getResources().getDrawable(R.color.white));
        TextView rightTxt = mCommonTitle.getRightTxt();
//        rightTxt.setBackground(getResources().getDrawable(R.drawable.add_img));
        rightTxt.setText("添加");
        rightTxt.setVisibility(View.VISIBLE);
        rightTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Goto.toAddJobExperienceActivity(context);
            }
        });
        setData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setData();
    }

    private void setData() {
        if (MyResumInfo.job_exp != null) {
            mJobInfoData = MyResumInfo.job_exp;

            mListView.setAdapter(new CommonAdapter<MyResumeJobInfo>(this, mJobInfoData, R.layout./*item_swipe_menu*/myresume_jobinfo_item) {
                @Override
                public void convert(final ViewHolder holder, MyResumeJobInfo swipeBean, final int position) {
                    //((SwipeMenuLayout)holder.getConvertView()).setIos(false);//这句话关掉IOS阻塞式交互效果
                    holder.setText(R.id.jobinfo_item_job, swipeBean.type_name);
                    holder.setText(R.id.jobinfo_item_jobinfo, swipeBean.name);
                    holder.setText(R.id.jobinfo_time, swipeBean.job_time_name);
                    holder.setText(R.id.jobinfo_item_detail, swipeBean.note);

                    holder.setOnClickListener(R.id.jobinfo_item_detel, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                        Toast.makeText(ListViewDelDemoActivity.this, "删除:" + position, Toast.LENGTH_SHORT).show();
                            //在ListView里，点击侧滑菜单上的选项时，如果想让擦花菜单同时关闭，调用这句话
                            ((SwipeMenuLayout) holder.getConvertView()).quickClose();
                            mDatas.remove(position);
                            notifyDataSetChanged();
                        }
                    });
                }
            });
        }



    }


    @Override
    protected void initView() {
        mListView = $(R.id.myresume_jobexp_listview);
    }
}
