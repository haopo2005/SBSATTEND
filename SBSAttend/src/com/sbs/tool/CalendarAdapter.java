package com.sbs.tool;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sbs.sbsattend.R;
import com.sbs.sbsattend.model.Leave;
import com.sbs.sbsattend.model.Logic;
import com.sbs.sbsattend.model.Work;
import com.sbs.sbsattend.model.WorkHistory;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CalendarAdapter extends BaseAdapter {
	private boolean isLeapyear = false; // 是否为闰年
	private int daysOfMonth = 0; // 某月的天数
	private int dayOfWeek = 0; // 具体某一天是星期几
	private Context context;
	private String[] dayNumber = new String[42]; // 一个gridview中的日期存入此数组中
	private SpecialCalendar sc = null;
	private Resources res = null;
	private Drawable drawable = null;

	private int OpCode = 0;
	private int currentFlag = -1; // 用于标记当天
	private String showYear = ""; // 用于在头部显示的年份
	private String showMonth = ""; // 用于在头部显示的月份

	// 系统当前时间
	private String sysDate = "";
	private String sys_year = "";
	private String sys_month = "";
	private String sys_day = "";
	private int current_day = 0;

	private List<WorkHistory> wh = null; // 值班记录
	private List<Work> wk = null; // 调休记录
	private List<Leave> les; // 请假记录

	public CalendarAdapter() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
		Date date = new Date();
		sysDate = sdf.format(date); // 当期日期
		sys_year = sysDate.split("-")[0];
		sys_month = sysDate.split("-")[1];
		sys_day = sysDate.split("-")[2];
	}

	public CalendarAdapter(Context context, Resources rs, int year, int month,
			int OpCode) {
		this();
		this.context = context;
		this.OpCode = OpCode;
		this.res = rs;
		sc = new SpecialCalendar();

		// 获取每月数据集
		switch (OpCode) {
		case MySignal.GETSHIFT:
			wh = Logic.query_monthlyworkinfo();
			break;
		case MySignal.GETREST:
			wh = Logic.query_monthlyworkinfo();
			wk = Logic.query_overtimeall();			
			break;
		case MySignal.GETLEAVE:
			les = Logic.query_leavetimeall();
			break;
		}

		// 获取日期信息
		getCalendar(year, month);
	}

	@Override
	public int getCount() {
		return dayNumber.length;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		SpannableString sp = null;
		String dayshift = "";
		String nightshift = "";
		String temp = "";
		int ifday = 0;
		int ifnight = 0;
		int flag = 0;

		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.calendar_item, null);
		}
		TextView textView = (TextView) convertView.findViewById(R.id.tvtext);
		String d = dayNumber[position];

		if (position < daysOfMonth + dayOfWeek && position >= dayOfWeek) {
			if (OpCode == MySignal.GETSHIFT) {
				// 当前月信息显示
				dayshift = "早:" + wh.get(position - dayOfWeek).getDaywork();
				nightshift = "晚:" + wh.get(position - dayOfWeek).getNightwork();
			} else if (OpCode == MySignal.GETREST) {
				nightshift = "下午:";
				dayshift = "上午:";
				if (wk != null) {
					// 申请到的调休
					for (Work w : wk) {
						if (Integer.parseInt(w.getCurrentrest()
								.substring(8, 10)) == (position - dayOfWeek + 1)) {
							if (w.getCurrentshift().equals("上午")) {
								dayshift += w.getName() + ";";
							} else if (w.getCurrentshift().equals("下午")) {
								nightshift += w.getName() + ";";
							}
						}
					}
					// 本来调休
					for (Work w : wk) {
						int mdate = Integer.parseInt(w.getOriginrest().substring(8, 10));
						if (mdate == (position - dayOfWeek + 1)) {
							if (w.getOriginshift().equals("当天下午")) {
								ifday = 1; // 当前下午不能再休息
							}
						}
						if (mdate == (position - dayOfWeek) || (0 == (position - dayOfWeek) && mdate >=28)) {	//后面的条件针对本月第一天
							if (w.getOriginshift().equals("明天上午")) {
								ifnight = 2; // 明天上午不能再休息
							}
						}
					}
				}

				if (ifday != 1) {
					nightshift += wh.get(position - dayOfWeek + 1).getDaywork()				//上月末最后一天插入了wh队列，故这边加1防止班次往后偏移
							+ ";";
				}
				if (ifnight != 2) {
					if (position - dayOfWeek - 1 >= 0) {
						dayshift += wh.get(position - dayOfWeek)                            //上月末最后一天插入了wh队列，故这边没有减1防止班次往后偏移
								.getNightwork() + ";";
					}
					if(position - dayOfWeek == 0)	//月初第一天上午
					{
						dayshift += wh.get(position - dayOfWeek)
								.getNightwork() + ";";
					}
				}
			} else if (OpCode == MySignal.GETLEAVE) {
				dayshift = "上午:";
				nightshift = "下午:";

				if (les != null) {
					for (Leave l : les) {
						current_day = position - dayOfWeek + 1;
						flag = insideRange(l);
						if (flag == MySignal.INRANGE) {
							System.out.println("MySignal.INRANGE");
							dayshift += l.getName() + ";";
							nightshift += l.getName() + ";";
						} else if (flag == MySignal.ATSTART) {
							System.out.println("MySignal.ATSTART");
							if (l.getOriginshift().equals("上午")) {
								dayshift += l.getName() + ";";
								nightshift += l.getName() + ";";
							} else {
								nightshift += l.getName() + ";";
							}
						} else if (flag == MySignal.ATEND) {
							System.out.println("MySignal.ATEND");
							if (l.getCurrentshift().equals("下午")) {
								dayshift += l.getName() + ";";
								nightshift += l.getName() + ";";
							} else {
								dayshift += l.getName() + ";";
							}
						}else if(flag == MySignal.EXACTDAY)
						{
							System.out.println("MySignal.EXACTDAY");
							if (l.getOriginshift().equals("上午")) {
								dayshift += l.getName() + ";";
								if(l.getCurrentshift().equals("下午"))
								{
									nightshift += l.getName() + ";";
								}
							} else {
								nightshift += l.getName() + ";";
							}
						}
					}
				}
			}
		}
		temp = d + "\n" + dayshift + "\n" + nightshift;
		sp = new SpannableString(temp);
		sp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0,
				d.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		sp.setSpan(new ForegroundColorSpan(Color.BLUE), 0, d.length(),
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		sp.setSpan(new RelativeSizeSpan(1.2f), 0, d.length(),
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		sp.setSpan(new RelativeSizeSpan(0.8f), d.length() + 1, temp.length(),
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

		textView.setText(sp);
		textView.setTextColor(Color.BLACK);

		if (currentFlag == position) {
			// 设置当天的背景
			drawable = res.getDrawable(R.drawable.current_day_bgc);
			// textView.setBackgroundColor(Color.BLACK);
			textView.setBackgroundDrawable(drawable);
			textView.setTextColor(Color.WHITE);
		}
		return convertView;
	}

	// 判断当前月在不在请假时间段内
	public int insideRange(Leave l) {
		//解析年月日
		int syear = Integer.parseInt(l.getStarttime().substring(0, 4));
		int smonth = Integer.parseInt(l.getStarttime().substring(5, 7));
		int sday = Integer.parseInt(l.getStarttime().substring(8, 10));
		int eyear = Integer.parseInt(l.getEndtime().substring(0, 4));
		int emonth = Integer.parseInt(l.getEndtime().substring(5, 7));
		int eday = Integer.parseInt(l.getEndtime().substring(8, 10));
		int current_year = Integer.parseInt(sys_year);
		int current_month = Integer.parseInt(sys_month);
		//int current_day = Integer.parseInt(sys_day);
		//计算某日是该年的第几天
		int scount = getYearDay(syear, smonth, sday);
		int ccount = getYearDay(current_year, current_month, current_day);
		int ecount = getYearDay(eyear, emonth, eday);
		
		if(syear<current_year && current_year < eyear)
		{
			return MySignal.INRANGE;
		}else if(syear == current_year && current_year < eyear)
		{
			if(ccount > scount)
			{
				return MySignal.INRANGE;
			}else if(ccount == scount)
			{
				return MySignal.ATSTART;
			}
		}else if(eyear == current_year && current_year > syear)
		{
			if(ccount < scount)
			{
				return MySignal.INRANGE;
			}else if(ccount == scount)
			{
				return MySignal.ATEND;
			}
		}else if(eyear == current_year && syear == current_year)
		{
			if(ccount < ecount && ccount > scount)
			{
				return MySignal.INRANGE;
			}else if(ccount == scount && ccount != ecount)
			{
				return MySignal.ATSTART;
			}else if(ccount == ecount && ccount != scount)
			{
				return MySignal.ATEND;
			}else if(ccount == ecount && scount == ccount)
			{
				return MySignal.EXACTDAY;
			}
		}
		return MySignal.OUTRANGE;
	}

	//计算某日是该年的第几天
	public int getYearDay(int y, int m, int d) {
		int count = 0;
		int feb = 28;

		if ((y % 4 == 0 && y % 100 != 0) || y % 400 == 0) {
			feb = 29;
		}
		switch (m) {
		case 1:
			count = d;
			break;
		case 2:
			count = 31 + d;
			break;
		case 3:
			count = 31 + feb + d;
			break;
		case 4:
			count = 31 + feb + 31 + d;
			break;
		case 5:
			count = 31 + feb + 31 + 30 + d;
			break;
		case 6:
			count = 31 + feb + 31 + 30 + 31 + d;
			break;
		case 7:
			count = 31 + feb + 31 + 30 + 31 + 30 + d;
			break;
		case 8:
			count = 31 + feb + 31 + 30 + 31 + 30 + 31 + d;
			break;
		case 9:
			count = 31 + feb + 31 + 30 + 31 + 30 + 31 + 31 + d;
			break;
		case 10:
			count = 31 + feb + 31 + 30 + 31 + 30 + 31 + 31 + 30 + d;
			break;
		case 11:
			count = 31 + feb + 31 + 30 + 31 + 30 + 31 + 31 + 30 + 31 + d;
			break;
		case 12:
			count = 31 + feb + 31 + 30 + 31 + 30 + 31 + 31 + 30 + 31 + 30 + d;
		}
		return count;
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

	// 得到某年的某月的天数且这月的第一天是星期几
	public void getCalendar(int year, int month) {
		isLeapyear = sc.isLeapYear(year); // 是否为闰年
		daysOfMonth = sc.getDaysOfMonth(isLeapyear, month); // 某月的总天数
		dayOfWeek = sc.getWeekdayOfMonth(year, month); // 某月第一天为星期几
		getweek(year, month);// 将一个月中的每一天的值添加入数组dayNumber中
	}

	private void getweek(int year, int month) {
		// 得到当前月的所有日程日期(这些日期需要标记)
		for (int i = 0; i < dayNumber.length; i++) {
			if ((i >= dayOfWeek) && (i < daysOfMonth + dayOfWeek)) { // 本月
				String day = String.valueOf(i - dayOfWeek + 1); // 得到的日期
				dayNumber[i] = i - dayOfWeek + 1 + "";
				// 对于当前月才去标记当前日期
				if (sys_year.equals(String.valueOf(year))
						&& sys_month.equals(String.valueOf(month))
						&& sys_day.equals(day)) {
					// 标记当前日期
					currentFlag = i;
					// System.out.println(currentFlag);
				}
			} else {
				dayNumber[i] = " ";
			}
		}
	}
}
