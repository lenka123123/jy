package sinia.com.baihangeducation.mine.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.example.framwork.adapter.BaseViewHolder;
import com.example.framwork.adapter.SuperBaseAdapter;

import java.util.List;

import sinia.com.baihangeducation.R;
import com.mcxtzhang.swipemenulib.info.bean.AlreadyGetTaskInfo;
import com.mcxtzhang.swipemenulib.info.bean.AlreadyGetTaskListInfo;
import sinia.com.baihangeducation.mine.presenter.AlreadyGetTaskPresenter;
import sinia.com.baihangeducation.mine.view.AlreadyGetTaskView;

/**
 * Created by Administrator on 2018/4/17.
 */

public class AlreadyGetTaskAdapter extends SuperBaseAdapter<AlreadyGetTaskInfo> implements AlreadyGetTaskView {

    private AlreadyGetTaskPresenter presenter;
    private String tab;

    public AlreadyGetTaskAdapter(Context context, List<AlreadyGetTaskInfo> datas, String tab) {
        super(context, datas);
        this.presenter = new AlreadyGetTaskPresenter((Activity) context, this);
        this.tab = tab;
    }

    @Override
    protected void convert(BaseViewHolder holder, final AlreadyGetTaskInfo item, int position) {
        holder.setText(R.id.alreadygettask_item_title, item.task_title);
        holder.setText(R.id.alreadygettask_item_adressanddata, item.task_city_name + " " + item.task_add_date);
        if (tab.equals("1")) {
            holder.setVisible(R.id.alreadygettask_item_complete, true);
            holder.setOnClickListener(R.id.alreadygettask_item_complete, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.setTaskComplete(item.task_apply_id + "");
                }
            });
        } else {
            holder.setVisible(R.id.alreadygettask_item_complete, false);
        }
    }

    @Override
    protected int getItemViewLayoutId(int position, AlreadyGetTaskInfo item) {
        return R.layout.alreadygettask_item;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public String getPage() {
        return null;
    }

    @Override
    public String getPerpage() {
        return null;
    }

    @Override
    public String getTab() {
        return null;
    }

    @Override
    public void getUnCompleteSuccess(AlreadyGetTaskListInfo mAlreadyGetTaskListInfo, int maxpage) {

    }

    @Override
    public void doRefresh() {

    }

    @Override
    public void getAuditSuccess() {

    }

    @Override
    public void getCompleteSuccess() {

    }
}
