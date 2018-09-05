package sinia.com.baihangeducation.release.activity;

import android.content.Intent;
import android.support.v7.widget.OrientationHelper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.applikeysolutions.cosmocalendar.listeners.OnMonthChangeListener;
import com.applikeysolutions.cosmocalendar.model.Day;
import com.applikeysolutions.cosmocalendar.model.Month;
import com.applikeysolutions.cosmocalendar.selection.MultipleSelectionManager;
import com.applikeysolutions.cosmocalendar.selection.criteria.BaseCriteria;
import com.applikeysolutions.cosmocalendar.selection.criteria.WeekDayCriteria;
import com.applikeysolutions.cosmocalendar.selection.criteria.month.CurrentMonthCriteria;
import com.applikeysolutions.cosmocalendar.selection.criteria.month.NextMonthCriteria;
import com.applikeysolutions.cosmocalendar.selection.criteria.month.PreviousMonthCriteria;
import com.applikeysolutions.cosmocalendar.utils.SelectionType;
import com.applikeysolutions.cosmocalendar.view.CalendarView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.release.info.bean.SelectedDay;
import sinia.com.baihangeducation.supplement.base.BaseActivity;

public class DataSelectActivity extends BaseActivity {

    private CalendarView calendarView;
    private List<BaseCriteria> threeMonthsCriteriaList;
    private WeekDayCriteria fridayCriteria;

    private boolean fridayCriteriaEnabled;
    private boolean threeMonthsCriteriaEnabled;

    private MenuItem menuFridays;
    private MenuItem menuThreeMonth;

    public DataSelectActivity() {
    }

    @Override
    protected void initView() {
        initViews();
    }

    @Override
    protected void initData() {
        createCriterias();
    }

    public int initLayoutResID() {
        return R.layout.activity_default_calendar;
    }

    private void initViews() {
        calendarView = (CalendarView) findViewById(R.id.calendar_view);
        calendarView.setCalendarOrientation(OrientationHelper.VERTICAL);//是垂直的
        calendarView.setSelectionType(SelectionType.MULTIPLE);//点选

        mCommonTitle.setCenterText("上班时间");
        mCommonTitle.setBackground(getResources().getDrawable(R.color.white));
        mCommonTitle.getRightTxt().setVisibility(View.VISIBLE);
        mCommonTitle.getRightTxt().setText("确定");
        mCommonTitle.getRightTxt().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Day> day = calendarView.getSelectedDays();
                if (day == null || day.size() < 1) {
                    DataSelectActivity.this.finish();
                    return;
                }

                List<SelectedDay> selectedDayes = new ArrayList<>();
                selectedDayes.clear();
                for (int i = 0; i < day.size(); i++) {
                    SelectedDay selectedDay = new SelectedDay();
                    selectedDay.year = String.valueOf(day.get(i).getCalendar().get(Calendar.YEAR));
                    selectedDay.month = String.valueOf(day.get(i).getCalendar().get(Calendar.MONTH));
                    selectedDay.day = String.valueOf(day.get(i).getCalendar().get(Calendar.DAY_OF_MONTH));
                    selectedDayes.add(selectedDay);
                }
                // 获取启动该Activity之前的Activity对应的Intent
                Intent intent = getIntent();
                intent.putExtra("daylist", (Serializable) selectedDayes);

                // 设置该SelectCityActivity的结果码，并设置结束之后退回的Activity
                if (selectedDayes.size() < 1) {
                    DataSelectActivity.this.setResult(70, intent);
                } else {
                    DataSelectActivity.this.setResult(200, intent);
                }

                // 结束SelectCityActivity。
                DataSelectActivity.this.finish();


            }
        });
    }

    private void createCriterias() {
        fridayCriteria = new WeekDayCriteria(Calendar.FRIDAY);
        threeMonthsCriteriaList = new ArrayList<>();
        threeMonthsCriteriaList.add(new CurrentMonthCriteria());
        threeMonthsCriteriaList.add(new NextMonthCriteria());
        threeMonthsCriteriaList.add(new PreviousMonthCriteria());
//        calendarView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
//            @Override
//            public void onLayoutChange(View view, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {
//                Toast.makeText(DataSelectAct.this, "Selected " + calendarView.getSelectedDays().size(), Toast.LENGTH_SHORT).show();
//            }
//        });
        calendarView.setOnMonthChangeListener(new OnMonthChangeListener() {
            @Override
            public void onMonthChanged(Month month) {
                List<Day> day = calendarView.getSelectedDays();
                for (int i = 0; i < day.size(); i++) {
                    Log.i("onMonthChanged===" + i, "onMonthChanged: " + day.get(i).toString());
                }
                Toast.makeText(DataSelectActivity.this, "Selected " + calendarView.getSelectedDays().size(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fridayMenuClick() {
        if (fridayCriteriaEnabled) {
            menuFridays.setTitle("select_all_fridays");
            unselectAllFridays();
        } else {
            menuFridays.setTitle("unselect_all_fridays");
            selectAllFridays();
        }
        fridayCriteriaEnabled = !fridayCriteriaEnabled;
    }

    private void threeMonthsMenuClick() {
        if (threeMonthsCriteriaEnabled) {
            menuThreeMonth.setTitle("干啥");
            unselectThreeMonths();
        } else {
            menuThreeMonth.setTitle("unselect_three_months");
            selectThreeMonths();
        }
        threeMonthsCriteriaEnabled = !threeMonthsCriteriaEnabled;
    }

    private void selectAllFridays() {
        if (calendarView.getSelectionManager() instanceof MultipleSelectionManager) {
            ((MultipleSelectionManager) calendarView.getSelectionManager()).addCriteria(fridayCriteria);
        }
        calendarView.update();
    }

    private void unselectAllFridays() {
        if (calendarView.getSelectionManager() instanceof MultipleSelectionManager) {
            ((MultipleSelectionManager) calendarView.getSelectionManager()).removeCriteria(fridayCriteria);
        }
        calendarView.update();
    }

    private void selectThreeMonths() {
        if (calendarView.getSelectionManager() instanceof MultipleSelectionManager) {
            ((MultipleSelectionManager) calendarView.getSelectionManager()).addCriteriaList(threeMonthsCriteriaList);
        }
        calendarView.update();
    }

    private void unselectThreeMonths() {
        if (calendarView.getSelectionManager() instanceof MultipleSelectionManager) {
            ((MultipleSelectionManager) calendarView.getSelectionManager()).removeCriteriaList(threeMonthsCriteriaList);
        }
        calendarView.update();
    }

    private void clearSelectionsMenuClick() {
        calendarView.clearSelections();

        fridayCriteriaEnabled = false;
        threeMonthsCriteriaEnabled = false;
        menuFridays.setTitle("select_all_fridays");
        menuThreeMonth.setTitle("select_three_months");
    }


    private void logSelectedDaysMenuClick() {

        Toast.makeText(this, "Selected " + calendarView.getSelectedDays().size(), Toast.LENGTH_SHORT).show();
    }

//    @Override
//    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
//        clearSelectionsMenuClick();
//        switch (checkedId) {
//            case R.id.rb_horizontal:
//                calendarView.setCalendarOrientation(OrientationHelper.HORIZONTAL);
//                break;
//
//            case R.id.rb_vertical:
//
//                break;
//
//            case R.id.rb_single:
//                calendarView.setSelectionType(SelectionType.SINGLE);
//                menuFridays.setVisible(false);
//                menuThreeMonth.setVisible(false);
//                break;
//
//            case R.id.rb_multiple:
//                calendarView.setSelectionType(SelectionType.MULTIPLE);
//                menuFridays.setVisible(true);
//                menuThreeMonth.setVisible(true);
//                break;
//
//            case R.id.rb_range:
//                calendarView.setSelectionType(SelectionType.RANGE);
//                menuFridays.setVisible(false);
//                menuThreeMonth.setVisible(false);
//                break;
//
//            case R.id.rb_none:
//                calendarView.setSelectionType(SelectionType.NONE);
//                menuFridays.setVisible(false);
//                menuThreeMonth.setVisible(false);
//                break;
//        }
//    }
}