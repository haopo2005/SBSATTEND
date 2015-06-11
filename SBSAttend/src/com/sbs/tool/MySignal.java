package com.sbs.tool;

public class MySignal {
	public static final int CORRECTCOMMIT = 1;		//数据库操作成功返回码
	public static final int NOWORKTIMEDATA = 2;		//无值班信息返回码	
	public static final int NEEDWORK = 3;			//存在值班信息返回码
	public static final int APPROVE = 4;			//审核通过
	public static final int NOTAPPROVE = 5;			//审核不通过
	public static final int GETSHIFT = 6;			//获取值班表信息码
	public static final int GETREST = 7;			//获取调休信息码
	public static final int GETLEAVE = 8;			//获取请假信息码
	public static final int INRANGE = 9;			//在日期范围内
	public static final int ATSTART = 10;			//在日期开始
	public static final int ATEND = 11;				//在日期末尾
	public static final int OUTRANGE = 12;			//在日期范围外
	public static final int EXACTDAY = 13;			//正好在当日
}
