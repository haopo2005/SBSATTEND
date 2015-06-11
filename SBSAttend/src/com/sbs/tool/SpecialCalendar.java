package com.sbs.tool;

import java.util.Calendar;

public class SpecialCalendar {
	private int daysOfMonth = 0; // 某月的天数
	private int dayOfWeek = 0; // 具体某一天是星期几

	// 判断是否为闰年
	public boolean isLeapYear(int year) {
		if (year % 100 == 0 && year % 400 == 0) {
			return true;
		} else if (year % 100 != 0 && year % 4 == 0) {
			return true;
		}
		return false;
	}

	// 得到某月有多少天数
	public int getDaysOfMonth(boolean isLeapyear, int month) {
		switch (month) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			daysOfMonth = 31;
			break;
		case 4:
		case 6:
		case 9:
		case 11:
			daysOfMonth = 30;
			break;
		case 2:
			if (isLeapyear) {
				daysOfMonth = 29;
			} else {
				daysOfMonth = 28;
			}

		}
		return daysOfMonth;
	}

	// 指定某年中的某月的第一天是星期几
	public int getWeekdayOfMonth(int year, int month) {
		Calendar cal = Calendar.getInstance();
		cal.set(year, month - 1, 1);
		dayOfWeek = cal.get(Calendar.DAY_OF_WEEK) - 1;
		return dayOfWeek;
	}
	
	//计算某年某月某日对应是星期几
	public static String getWeekdayofYear(int year, int month, int day){
		String week = "";
		Calendar cal = Calendar.getInstance();
		cal.set(year, month - 1, day);
		int weekday = cal.get(Calendar.DAY_OF_WEEK) - 1;
		switch(weekday)
		{
		case 0:
			week = "周日";break;
		case 1:
			week = "周一";break;
		case 2:
			week = "周二";break;
		case 3:
			week = "周三";break;
		case 4:
			week = "周四";break;
		case 5:
			week = "周五";break;	
		case 6:
			week = "周六";break;
		}
		return week;
	}
}
