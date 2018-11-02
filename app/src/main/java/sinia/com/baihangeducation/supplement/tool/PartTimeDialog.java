package sinia.com.baihangeducation.supplement.tool;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.mcxtzhang.swipemenulib.customview.CommonPopupWindow;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.home.activity.HomePartTimeActivity;
import sinia.com.baihangeducation.release.activity.SiftUrNeedActivity;
import sinia.com.baihangeducation.release.adapter.TagAdapter;
import sinia.com.baihangeducation.release.info.bean.JobTagListInfo;

public class PartTimeDialog implements CompoundButton.OnCheckedChangeListener {

    private String money_id = "0";
    private String worktime_id = "0";
    private String distance_id = "0";
    private String sex_id = "0";
    private String pubtime_id = "0";
    private TagFlowLayout layout;
    private RadioButton mRadioButtonNone;
    private RadioButton mRadioButtonThreeDay;
    private RadioButton mRadioButtonOneWeek;
    private RadioButton mRadioButtonOneWeekOut;
    private RadioButton mRadioButtonSexNeedNone;
    private RadioButton mRadioButtonSexNeedMan;
    private RadioButton mRadioButtonSexNeedWoman;
    private RadioButton mRadioButtonRangeNeedNone;
    private RadioButton mRadioButtonRangeNeedOne;
    private RadioButton mRadioButtonRangeNeedFive;
    private RadioButton mRadioButtonRangeNeedFiveOut;
    private RadioButton mRadioButtonRangeWorkTimeNone;
    private RadioButton mRadioButtonRangeWorkTimeWeekday;
    private RadioButton mRadioButtonRangeWorkTimeWeekends;
    private RadioButton mRadioButtonAccountNone;
    private RadioButton mRadioButtonAccountByDay;
    private RadioButton mRadioButtonAccountByTime;
    private RadioButton mRadioButtonAccountByMorrow;
    private RadioButton mRadioButtonAccountByWeek;
    private RadioButton mRadioButtonAccountByMonth;
    private TextView textViewCancle;
    private TextView textViewOk;
    private String[] array = {"不限", "日结", "周结", "月结", "次日结", "时结", "次结"};
    List<JobTagListInfo> datas = new ArrayList<>();

    private CommonPopupWindow.LayoutGravity layoutGravity;
    private View window;
    public CommonPopupWindow commonPopupWindow;
    private RadioButton wai_button;

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

        if (!b) return;
        switch (compoundButton.getId()) {
            case R.id.sent_time_none:
                pubtime_id = "0";
                break;
            case R.id.sent_time_three_day:
                pubtime_id = "1";
                break;
            case R.id.sent_time_one_week:
                pubtime_id = "2";
                break;
            case R.id.sent_time_one_week_out:
                pubtime_id = "3";
                break;  //

            case R.id.sex_need_none:
                sex_id = "0";

                break;
            case R.id.sex_need_man:
                sex_id = "1";

                break;
            case R.id.sex_need_woman:
                sex_id = "2";

                break;   //

            case R.id.range_need_none:
                distance_id = "0";
                break;
            case R.id.range_need_one:
                distance_id = "1";
                break;
            case R.id.range_need_five:
                distance_id = "2";
                break;
            case R.id.range_need_five_out:
                distance_id = "3";
                break;   //

            case R.id.work_time_none:
                worktime_id = "0";
                break;
            case R.id.work_time_weekday:
                worktime_id = "1";
                break;
            case R.id.work_time_weekends:
                worktime_id = "2";
                break;  //

            case R.id.account_none:
                money_id = "0";
                wai_button.setChecked(false);
                break;
            case R.id.account_by_day:
                money_id = "1";
                wai_button.setChecked(false);
                break;
            case R.id.account_by_morrow:
                money_id = "5";
                wai_button.setChecked(false);
                break;
            case R.id.account_by_time:
                money_id = "6";
                wai_button.setChecked(false);
                break;
            case R.id.account_by_week:
                money_id = "2";
                wai_button.setChecked(false);
                break;
            case R.id.account_by_month:
                money_id = "3";
                wai_button.setChecked(false);
                break;
            case R.id.wai_button:
                money_id = "7";
                mRadioButtonAccountNone.setChecked(false);
                mRadioButtonAccountByTime.setChecked(false);
                mRadioButtonAccountByDay.setChecked(false);
                mRadioButtonAccountByMorrow.setChecked(false);
                mRadioButtonAccountByWeek.setChecked(false);
                mRadioButtonAccountByMonth.setChecked(false);
                break;
            /**

             *
             */

        }
    }

    public interface OnClickParameterListener {
        void getParameter(String money_id, String worktime_id, String distance_id, String sex_id, String pubtime_id);
    }


    public OnClickParameterListener listener;


    public void showAlertPop(Activity activity, final View view, final OnClickParameterListener listener) {
        //  //industryId,行业   money_id,日结 areaId,地址  worktime_id周末,distance_id一公里,sex_id,pubtime_id 三天内
        commonPopupWindow = new CommonPopupWindow(activity, R.layout.dialog_item_part_job,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT) {

            @Override
            protected void initView() {
                window = getContentView();

                wai_button = window.findViewById(R.id.wai_button);
                wai_button.setOnCheckedChangeListener(PartTimeDialog.this);
                mRadioButtonNone = window.findViewById(R.id.sent_time_none);
                mRadioButtonThreeDay = window.findViewById(R.id.sent_time_three_day);
                mRadioButtonOneWeek = window.findViewById(R.id.sent_time_one_week);
                mRadioButtonOneWeekOut = window.findViewById(R.id.sent_time_one_week_out);

                mRadioButtonSexNeedNone = window.findViewById(R.id.sex_need_none);
                mRadioButtonSexNeedMan = window.findViewById(R.id.sex_need_man);
                mRadioButtonSexNeedWoman = window.findViewById(R.id.sex_need_woman);

                mRadioButtonRangeNeedNone = window.findViewById(R.id.range_need_none);
                mRadioButtonRangeNeedOne = window.findViewById(R.id.range_need_one);
                mRadioButtonRangeNeedFive = window.findViewById(R.id.range_need_five);
                mRadioButtonRangeNeedFiveOut = window.findViewById(R.id.range_need_five_out);

                mRadioButtonRangeWorkTimeNone = window.findViewById(R.id.work_time_none);
                mRadioButtonRangeWorkTimeWeekday = window.findViewById(R.id.work_time_weekday);
                mRadioButtonRangeWorkTimeWeekends = window.findViewById(R.id.work_time_weekends);

                mRadioButtonAccountNone = window.findViewById(R.id.account_none);
                mRadioButtonAccountByTime = window.findViewById(R.id.account_by_time);
                mRadioButtonAccountByDay = window.findViewById(R.id.account_by_day);
                mRadioButtonAccountByMorrow = window.findViewById(R.id.account_by_morrow);
                mRadioButtonAccountByWeek = window.findViewById(R.id.account_by_week);
                mRadioButtonAccountByMonth = window.findViewById(R.id.account_by_month);

                mRadioButtonNone.setOnCheckedChangeListener(PartTimeDialog.this);
                mRadioButtonThreeDay.setOnCheckedChangeListener(PartTimeDialog.this);
                mRadioButtonOneWeek.setOnCheckedChangeListener(PartTimeDialog.this);
                mRadioButtonOneWeekOut.setOnCheckedChangeListener(PartTimeDialog.this);

                mRadioButtonSexNeedNone.setOnCheckedChangeListener(PartTimeDialog.this);
                mRadioButtonSexNeedMan.setOnCheckedChangeListener(PartTimeDialog.this);
                mRadioButtonSexNeedWoman.setOnCheckedChangeListener(PartTimeDialog.this);

                mRadioButtonRangeNeedNone.setOnCheckedChangeListener(PartTimeDialog.this);
                mRadioButtonRangeNeedOne.setOnCheckedChangeListener(PartTimeDialog.this);
                mRadioButtonRangeNeedFive.setOnCheckedChangeListener(PartTimeDialog.this);
                mRadioButtonRangeNeedFiveOut.setOnCheckedChangeListener(PartTimeDialog.this);

                mRadioButtonRangeWorkTimeNone.setOnCheckedChangeListener(PartTimeDialog.this);
                mRadioButtonRangeWorkTimeWeekday.setOnCheckedChangeListener(PartTimeDialog.this);
                mRadioButtonRangeWorkTimeWeekends.setOnCheckedChangeListener(PartTimeDialog.this);

                mRadioButtonAccountNone.setOnCheckedChangeListener(PartTimeDialog.this);
                mRadioButtonAccountByDay.setOnCheckedChangeListener(PartTimeDialog.this);
                mRadioButtonAccountByTime.setOnCheckedChangeListener(PartTimeDialog.this);
                mRadioButtonAccountByMorrow.setOnCheckedChangeListener(PartTimeDialog.this);
                mRadioButtonAccountByWeek.setOnCheckedChangeListener(PartTimeDialog.this);
                mRadioButtonAccountByMonth.setOnCheckedChangeListener(PartTimeDialog.this);


            }

            @Override
            protected void initEvent() {

                textViewCancle = window.findViewById(R.id.cancel);
                textViewOk = window.findViewById(R.id.ok_tv);
                textViewCancle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        closeWindow();
                    }
                });
                textViewOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        closeWindow();
                        //  //industryId,行业   money_id,日结 areaId,地址  worktime_id周末,distance_id一公里,sex_id,pubtime_id 三天内
                        listener.getParameter(money_id, worktime_id, distance_id, sex_id, pubtime_id);

                    }
                });
            }
        };
        layoutGravity = new CommonPopupWindow.LayoutGravity(CommonPopupWindow.LayoutGravity.ALIGN_LEFT | CommonPopupWindow.LayoutGravity.TO_BOTTOM);

        commonPopupWindow.showBashOfAnchor(view, layoutGravity, 0, 0);
    }


    public void closeWindow() {
        if (commonPopupWindow != null) {
//         boolean is=   commonPopupWindow.getPopupWindow().isShowing();
//         if (is)
            commonPopupWindow.getPopupWindow().dismiss();
        }

    }

}
