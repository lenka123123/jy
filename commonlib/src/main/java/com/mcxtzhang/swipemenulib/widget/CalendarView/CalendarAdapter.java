package com.mcxtzhang.swipemenulib.widget.CalendarView;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.mcxtzhang.swipemenulib.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * 日历gridview中的每一个item显示的textview
 */
public class CalendarAdapter extends BaseAdapter {
	private boolean isLeapyear = false; // 是否为闰年
	private int daysOfMonth = 0; // 某月的天数
	private int dayOfWeek = 0; // 具体某一天是星期几
	private int lastDaysOfMonth = 0; // 上一个月的总天数
	private Context context;
	private String[] dayNumber = new String[42]; // 一个gridview中的日期存入此数组中
//	private List<DayInfo> dayNumber = new ArrayList<>(42);
	private SpecialCalendar sc = null;
	private LunarCalendar lc = null;
	private Resources res = null;
	private String currentYear = "";
	private String currentMonth = "";
	private String currentDay = "";

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private String showYear = ""; // 用于在头部显示的年份
	private String showMonth = ""; // 用于在头部显示的月份
	private String animalsYear = "";
	private String leapMonth = ""; // 闰哪一个月
	private String cyclical = ""; // 天干地支
	// 系统当前时间
	private String sysDate = "";
	private String sys_year = "";
	private String sys_month = "";
	private String sys_day = "";
	private int colorDataPosition = -1;
	private boolean isClickData = false;
	private List<String> dayInfos;

	public CalendarAdapter() {
		Date date = new Date();
		sysDate = sdf.format(date); // 当期日期
		sys_year = sysDate.split("-")[0];
		sys_month = sysDate.split("-")[1];
		sys_day = sysDate.split("-")[2];

	}

	public CalendarAdapter(Context context, Resources rs, int jumpMonth,
						   int jumpYear, int year_c, int month_c, int day_c, List<String> dayInfos) {
		this();
		this.context = context;
		sc = new SpecialCalendar();
		lc = new LunarCalendar();
		this.res = rs;
		this.dayInfos = dayInfos;

		int stepYear = year_c + jumpYear;
		int stepMonth = month_c + jumpMonth;
		if (stepMonth > 0) {
			// 往下一个月滑动
			if (stepMonth % 12 == 0) {
				stepYear = year_c + stepMonth / 12 - 1;
				stepMonth = 12;
			} else {
				stepYear = year_c + stepMonth / 12;
				stepMonth = stepMonth % 12;
			}
		} else {
			// 往上一个月滑动
			stepYear = year_c - 1 + stepMonth / 12;
			stepMonth = stepMonth % 12 + 12;
			if (stepMonth % 12 == 0) {

			}
		}

		currentYear = String.valueOf(stepYear); // 得到当前的年份
		currentMonth = String.valueOf(stepMonth); // 得到本月
													// （jumpMonth为滑动的次数，每滑动一次就增加一月或减一月）
		currentDay = String.valueOf(day_c); // 得到当前日期是哪天

		getCalendar(Integer.parseInt(currentYear),
				Integer.parseInt(currentMonth));

	}

	public CalendarAdapter(Context context, Resources rs, int year, int month,
			int day) {
		this();
		this.context = context;
		sc = new SpecialCalendar();
		lc = new LunarCalendar();
		this.res = rs;
		currentYear = String.valueOf(year);// 得到跳转到的年份
		currentMonth = String.valueOf(month); // 得到跳转到的月份
		currentDay = String.valueOf(day); // 得到跳转到的天
		getCalendar(Integer.parseInt(currentYear),
				Integer.parseInt(currentMonth));
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return dayNumber.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.calen_calendar_item, null);
		}
		TextView textView = (TextView) convertView.findViewById(R.id.tvtext);
		ImageView imgSelect = (ImageView) convertView.findViewById(R.id.img_select);
		TextView tvStatus = (TextView) convertView.findViewById(R.id.tv_status);
		String d = dayNumber[position].split("\\.")[0];
		String ds = dayNumber[position].split("\\.")[1];
//		String dv = dayNumber[position].split("\\.")[1];

//		SpannableString sp = new SpannableString(d + "\n" + dv);
//		sp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0,
//				d.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//		sp.setSpan(new RelativeSizeSpan(1.2f), 0, d.length(),
//				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//		if (dv != null || dv != "") {
//			sp.setSpan(new RelativeSizeSpan(0.75f), d.length() + 1,
//					dayNumber[position].length(),
//					Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//		}
		textView.setText(d);
		textView.setTextColor(Color.GRAY);

		if (position < daysOfMonth + dayOfWeek && position >= dayOfWeek) {
			// 当前月信息显示
			textView.setTextColor(Color.BLACK);// 当月字体设黑
			// drawable = new ColorDrawable(Color.rgb(23, 126, 214));
//			if (position % 7 == 0 || position % 7 == 6) {
//				// 当前月信息显示
//				textView.setTextColor(Color.rgb(23, 126, 214));// 当月字体设黑
//			}
			if (Integer.valueOf(currentMonth) == Integer.valueOf(sys_month) && Integer.valueOf(d) <= Integer.valueOf(currentDay)){
				if (ds.equals("1")) {
					imgSelect.setVisibility(View.VISIBLE);
//					tvStatus.setVisibility(View.INVISIBLE);
				} else {
					imgSelect.setVisibility(View.INVISIBLE);
//					tvStatus.setVisibility(View.VISIBLE);
//					tvStatus.setText("补签");
				}
			} else if (Integer.valueOf(currentMonth) < Integer.valueOf(sys_month)) {
				if (ds.equals("1")) {
					imgSelect.setVisibility(View.VISIBLE);
//					tvStatus.setVisibility(View.INVISIBLE);
				} else {
					imgSelect.setVisibility(View.INVISIBLE);
//					tvStatus.setVisibility(View.VISIBLE);
//					tvStatus.setText("补签");
				}
			} else {
				imgSelect.setVisibility(View.INVISIBLE);
//				tvStatus.setVisibility(View.INVISIBLE);
			}
		}

		if (colorDataPosition == position) {
			// 设置当天的背景
			textView.setTextColor(Color.rgb(23, 126, 214));

//			imgSelect.setVisibility(View.VISIBLE);
		} else {
			textView.setBackgroundColor(res
					.getColor(android.R.color.transparent));
//			imgSelect.setVisibility(View.INVISIBLE);
		}
		return convertView;
	}

	// 得到某年的某月的天数且这月的第一天是星期几
	private void getCalendar(int year, int month) {
		isLeapyear = sc.isLeapYear(year); // 是否为闰年
		daysOfMonth = sc.getDaysOfMonth(isLeapyear, month); // 某月的总天数
		dayOfWeek = sc.getWeekdayOfMonth(year, month); // 某月第一天为星期几
		lastDaysOfMonth = sc.getDaysOfMonth(isLeapyear, month - 1); // 上一个月的总天数
		getweek(year, month);
	}

	// 将一个月中的每一天的值添加入数组dayNuber中
	private void getweek(int year, int month) {
		int j = 1;
		String lunarDay = "";
		int status = 0;
		// 得到当前月的所有日程日期(这些日期需要标记)
		for (int i = 0; i < dayNumber.length; i++) {
			if (i < dayOfWeek) { // 前一个月
				int temp = lastDaysOfMonth - dayOfWeek + 1;
//				lunarDay = lc.getLunarDate(year, month - 1, temp + i, false);
//				status = getisSign(year, month, temp + i);
//				dayNumber[i] = (temp + i) + "." + status;
				dayNumber[i] = getisSign(year, month, temp + i);
			} else if (i < daysOfMonth + dayOfWeek) { // 本月
				String day = String.valueOf(i - dayOfWeek + 1); // 得到的日期
//				lunarDay = lc.getLunarDate(year, month, i - dayOfWeek + 1, false);
//				status = getisSign(year, month, i - dayOfWeek + 1);
//				dayNumber[i] = i - dayOfWeek + 1 + "." + status;
				dayNumber[i] = getisSign(year, month, i - dayOfWeek + 1);
				// 对于当前月才去标记当前日期
				if (sys_year.equals(String.valueOf(year))
						&& sys_month.equals(String.valueOf(month))
						&& sys_day.equals(day)) {
					// 标记当前日期
					colorDataPosition = i;
				}
				setShowYear(String.valueOf(year));
				setShowMonth(String.valueOf(month));
				setAnimalsYear(lc.animalsYear(year));
				setLeapMonth(lc.leapMonth == 0 ? "" : String
						.valueOf(lc.leapMonth));
				setCyclical(lc.cyclical(year));
			} else { // 下一个月
//				lunarDay = lc.getLunarDate(year, month + 1, j, false);
//				status = getisSign(year, month, j);
//				dayNumber[i] = j + "." + status;
				dayNumber[i] = getisSign(year, month, j);
				j++;
			}
		}

		String abc = "";
		for (int i = 0; i < dayNumber.length; i++) {
			abc = abc + dayNumber[i] + ":";
		}
		Log.d("DAYNUMBER", abc);

	}

	private String getisSign(int year, int month, int day) {
		String curMonth = "";
		String curDay = "";
		int status = 0;
		String signStr = day + ".0";
		if (month < 10) {
			curMonth = "0" + month;
		} else {
			curMonth = String.valueOf(month);
		}

		if (day < 10) {
			curDay = "0" + day;
		} else {
			curDay = String.valueOf(day);
		}
		if (dayInfos != null && dayInfos.size() > 0) {
			String curDayStr = year+"-"+curMonth+"-"+curDay;
			for (String dayStr : dayInfos) {
				if (dayStr.equals(curDayStr)) {
					status = 1;
					signStr = day + "." + status;
					break;
				}

			}
		}
		return signStr;
	}

	public void matchScheduleDate(int year, int month, int day) {

	}

	/**
	 * 点击每一个item时返回item中的日期
	 * 
	 * @param position
	 * @return
	 */
	public String getDateByClickItem(int position) {
		return dayNumber[position];
	}

	/**
	 * 在点击gridView时，得到这个月中第一天的位置
	 * 
	 * @return
	 */
	public int getStartPositon() {
		return dayOfWeek + 7;
	}

	/**
	 * 在点击gridView时，得到这个月中最后一天的位置
	 * 
	 * @return
	 */
	public int getEndPosition() {
		return (dayOfWeek + daysOfMonth + 7) - 1;
	}

	public String getShowYear() {
		return showYear;
	}

	public void setShowYear(String showYear) {
		this.showYear = showYear;
	}

	public String getShowMonth() {
		return showMonth;
	}

	public void setShowMonth(String showMonth) {
		this.showMonth = showMonth;
	}

	public String getAnimalsYear() {
		return animalsYear;
	}

	public void setAnimalsYear(String animalsYear) {
		this.animalsYear = animalsYear;
	}

	public String getLeapMonth() {
		return leapMonth;
	}

	public void setLeapMonth(String leapMonth) {
		this.leapMonth = leapMonth;
	}

	public String getCyclical() {
		return cyclical;
	}

	public void setCyclical(String cyclical) {
		this.cyclical = cyclical;
	}

	/**
	 * @param position
	 *            设置点击的日期的颜色位置
	 */
	public void setColorDataPosition(int position) {
		if (position >= dayOfWeek && position < daysOfMonth + dayOfWeek) {
			colorDataPosition = position;
			notifyDataSetChanged();
		}
	}
}
