package sinia.com.baihangeducation.mine.activity;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.supplement.base.BaseActivity;
import com.mcxtzhang.swipemenulib.info.bean.DoSignInfo;
import com.mcxtzhang.swipemenulib.info.bean.GetSignInfo;
import com.mcxtzhang.swipemenulib.info.bean.TaskInfo;
import sinia.com.baihangeducation.mine.presenter.GetSignPresenter;
import sinia.com.baihangeducation.mine.presenter.TaskPresenter;
import sinia.com.baihangeducation.mine.view.ISignView;
import sinia.com.baihangeducation.mine.view.TaskView;
import sinia.com.baihangeducation.supplement.base.Goto;

/**
 * Created by Administrator on 2018/4/16.
 */

public class TaskActivity extends BaseActivity implements TaskView ,ISignView {

    private GetSignPresenter signPresenter;
    private TaskPresenter taskPresenter;
    private TextView mSign;             //点击签到
    private TextView mSignDays;             //累计签到天数
    private TextView mSignRule;         //签到规则
    private RelativeLayout mGetTask;    //可领取任务
    private TextView mCanGetTaskNum;    //可领取的任务
    private RelativeLayout mAuditTask;  //审核中的任务
    private TextView mCompleteAndUnCompleteNum;     //完成和未完成的任务

    @Override
    public int initLayoutResID() {
        return R.layout.fragment_mine_task;
    }

    @Override
    protected void initData() {
        mCommonTitle.setCenterText(R.string.mine_task_title);
        mCommonTitle.setBackground(getResources().getDrawable(R.color.white));
        signPresenter = new GetSignPresenter(context,this);
        taskPresenter = new TaskPresenter(context, this);
        taskPresenter.getTaskData();

    }

    @Override
    protected void initView() {
        mSign = $(R.id.fragment_mine_sign);
        mSignDays = $(R.id.fragment_mine_sigedays);
        mSignRule = $(R.id.fragment_mine_sigerule);
        mGetTask = $(R.id.fragment_mine_gettask);
        mCanGetTaskNum = $(R.id.fragment_mine_cangettasknum);
        mAuditTask = $(R.id.fragment_mine_audittask);
        mCompleteAndUnCompleteNum = $(R.id.fragment_mine_campleteanduncompletenum);

        mSign.setOnClickListener(this);
        mSignRule.setOnClickListener(this);
        mGetTask.setOnClickListener(this);
        mAuditTask.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.fragment_mine_sign:
                //签到
                signPresenter.doSign();
                break;
            case R.id.fragment_mine_sigerule:
                //签到规则
                break;
            case R.id.fragment_mine_gettask:
                //可领取的任务
                Goto.toNewTaskActivity(context);
                break;
            case R.id.fragment_mine_audittask:
                //审核中的任务
                Goto.toAlreadyGetTaskActivity(context);
                break;
        }
    }

    @Override
    public void showLoading() {
        showProgress();
    }

    @Override
    public void hideLoading() {
        hideProgress();
    }

    @Override
    public void getTaskDataSuccess(TaskInfo data) {
        if (data != null) {
            mSignDays.setText("累计签到" + data.sign_total + "天");
            mCanGetTaskNum.setText("累计领取" + data.has_job + "个任务");
            mCompleteAndUnCompleteNum.setText("已完成" + data.finish_job + "件，未完成" + data.no_finish_job + "件");
        }
    }

    @Override
    public String getMouth() {
        return null;
    }

    @Override
    public void getSignDaysSuccess(GetSignInfo getSignInfo) {

    }

    @Override
    public void doSignSuccess(DoSignInfo doSignInfo) {

    }
}
