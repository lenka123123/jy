package sinia.com.baihangeducation.release.activity;


import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.util.AttributeSet;

import com.applikeysolutions.cosmocalendar.model.Day;
import com.applikeysolutions.cosmocalendar.model.Month;
import com.applikeysolutions.cosmocalendar.selection.MultipleSelectionManager;
import com.applikeysolutions.cosmocalendar.utils.CalendarUtils;
import com.applikeysolutions.cosmocalendar.view.CalendarView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

// MyCalendarsinia.com.baihangeducation.release.activity.MyCalendarViewView
public class MyCalendarView extends CalendarView {

    public MyCalendarView(Context context) {
        super(context);
    }

    public MyCalendarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyCalendarView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MyCalendarView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    public void setSelectData(List<Day> list) {
        for (int i = 0; i < list.size(); i++) {
            ((MultipleSelectionManager) getSelectionManager()).toggleDay(list.get(i));
        }
        onDaySelected();

//        CalendarUtils.createInitialMonths(settingsManager);

    }




}
