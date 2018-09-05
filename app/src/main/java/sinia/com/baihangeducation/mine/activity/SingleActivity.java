package sinia.com.baihangeducation.mine.activity;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.framwork.utils.Toast;

import java.util.List;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.supplement.base.BaseActivity;
import com.mcxtzhang.swipemenulib.info.bean.DoSignInfo;
import com.mcxtzhang.swipemenulib.info.bean.GetSignInfo;
import sinia.com.baihangeducation.mine.presenter.GetSignPresenter;
import sinia.com.baihangeducation.mine.view.ISignView;
import com.mcxtzhang.swipemenulib.widget.CalendarView.CalendarView;
import com.mcxtzhang.swipemenulib.widget.CalendarView.ClickDataListener;

/**
 * 我的页面 签到
 */

public class SingleActivity extends BaseActivity implements ISignView {

    private CalendarView mCalendarView;
    private GetSignPresenter presenter;

    private TextView mSign;                 //签到按钮
    private TextView mTotalNum;             //签到总数
    private TextView mCountineNum;          //连续签到

    private String curMonth = "";
    private String selectDay = "";
    private int isSign = 0;             //是否已经签到        0未签到 1签到
    private List<String> days;

    @Override
    public int initLayoutResID() {
        return R.layout.fragment_mine_single;
    }

    @Override
    protected void initData() {

        mCommonTitle.setCenterText(R.string.mine_marktoday);
        mCommonTitle.setBackground(getResources().getDrawable(R.color.white));

        presenter = new GetSignPresenter(context, this);
        initCalendarview();
    }

    @Override
    protected void initView() {
        mCalendarView = $(R.id.calendarview);
        mSign = $(R.id.fragment_mine_signin);
        mTotalNum = $(R.id.fragment_mine_sign_totalnum);
        mCountineNum = $(R.id.fragment_mine_sign_countinenum);

        mSign.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.fragment_mine_signin:
                if (isSign == 1) {
                    Toast.getInstance().showErrorToast(context, "已签到");
                } else
                    presenter.doSign();
                break;
        }
    }

    private void initCalendarview() {
        //设置点击日期的事件监听
        mCalendarView.setClickDataListener(new ClickDataListener() {

            @Override
            public void clickData(String year, String month, String day) {
                if (Integer.valueOf(month) < 10) {
                    selectDay = year + "-0" + month;
                } else {
                    selectDay = year + "-" + month;
                }
                if (Integer.valueOf(day) < 10) {
                    selectDay += "-0" + day;
                } else {
                    selectDay += "-" + day;
                }

            }
        });
        //切换月份
        mCalendarView.setClickMonthListener(new CalendarView.ClickMonthListener() {

            @Override
            public void clickMonth(int year, int month) {
                if (month < 10) {
                    curMonth = year + "-0" + month;
                } else {
                    curMonth = year + "-" + month;
                }
                presenter.getSignDays(curMonth);

            }
        });
        presenter.getSignDays(curMonth);
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
    public String getMouth() {
        return null;
    }

    /**
     * 获取签到数据成功
     *
     * @param getSignInfo
     */
    @Override
    public void getSignDaysSuccess(GetSignInfo getSignInfo) {
        mTotalNum.setText(getSignInfo.total_num + "");
        mCountineNum.setText(getSignInfo.continuity_num + "");
        if (getSignInfo.is_signed == 1) {
            mSign.setBackground(getResources().getDrawable(R.drawable.textview_round_unselector));
            mSign.setEnabled(false);
        } else {
            mSign.setBackground(getResources().getDrawable(R.drawable.textview_round));
            mSign.setEnabled(true);
        }
        days = getSignInfo.list;
        Log.i("天数", days.get(0).toString());
        mCalendarView.setCurMonthData(days);
    }

    /**
     * 签到成功返回
     *
     * @param doSignInfo
     */
    @Override
    public void doSignSuccess(DoSignInfo doSignInfo) {
        mTotalNum.setText(doSignInfo.total_num + "");
        mCountineNum.setText(doSignInfo.continuity_num + "");
        presenter.getSignDays(curMonth);
    }
}
