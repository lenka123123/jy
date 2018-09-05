package sinia.com.baihangeducation.mine.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.example.framwork.adapter.BaseViewHolder;
import com.example.framwork.adapter.SuperBaseAdapter;

import java.util.List;

import sinia.com.baihangeducation.R;
import com.mcxtzhang.swipemenulib.info.bean.NewTaskInfo;
import sinia.com.baihangeducation.mine.presenter.NewTaskPresenter;

/**
 * Created by Administrator on 2018/4/16.
 */

public class NewTaskAdapter extends SuperBaseAdapter<NewTaskInfo> {

    private NewTaskPresenter presenter;

    public NewTaskAdapter(Context context, List<NewTaskInfo> data) {
        super(context, data);
        this.presenter = new NewTaskPresenter((Activity) context);
    }

    @Override
    protected void convert(BaseViewHolder holder, final NewTaskInfo item, int position) {
        holder.setText(R.id.newtask_item_title, item.task_title);
        holder.setText(R.id.newtask_item_adressanddata, item.task_city_name + " " + item.task_add_date);
        holder.setOnClickListener(R.id.newtask_item_get, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //领取任务
                presenter.getNewTask(item.task_id + "");
            }
        });
    }

    @Override
    protected int getItemViewLayoutId(int position, NewTaskInfo item) {
        return R.layout.newtask_item;
    }
}
