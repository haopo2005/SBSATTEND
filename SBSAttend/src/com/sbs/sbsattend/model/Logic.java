package com.sbs.sbsattend.model;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sbs.sbsattend.db.DBConnection;
import com.sbs.tool.MySignal;

public class Logic {
	//private static String path = "http://10.0.2.2:8080/web/";
	private static String path = "http://192.168.0.200:8081/web/";

	public static Person login(String name, String pwd) throws Exception
	{
		String sql = "select * from dbo.permission where userid ='" + name + "' and pwd='" + pwd +"'";
		String subpath = "LoginServlet";

		//POST发送查询指令
		List<Person> pes = DBConnection.getPerson(sql, path + subpath);
		if(!pes.isEmpty() && pes.size()== 1)
		{
			return pes.get(0);
		}else
		{
			System.out.println("获取人员信息失败");
			return null;
		}
	}

	public static List<Leave> query_leavetimeall()
	{
		String subpath = "ReivewLeaveServlet";
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		String date = df.format(new Date());
		String time = date.substring(0,4) + "-" + date.substring(5,7);
	    //String sql = "select * from dbo.workoff where convert(varchar(50),starttime,120) like '"+ time + "%' and approve=4";
		String sql = "select * from dbo.workoff where (convert(varchar(50),starttime,120) like '"+ time + "%' or  convert(varchar(50),endtime,120) like '"+time + "%') and approve=4";
		//POST发送查询指令
		return DBConnection.getLeave(sql, path + subpath);
	}

	//导出请假申请
	public static List<Leave> query_leavetime()
	{
		String sql = "select * from dbo.workoff where approve=-1";
		String subpath = "ReivewLeaveServlet";
		//POST发送查询指令
		return DBConnection.getLeave(sql, path + subpath);
	}

	//导出本月值班表
	public static List<WorkHistory> query_monthlyworkinfo()
	{
		String subpath = "QueryWorktHistoryServlet";
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		String date = df.format(new Date());
		String time = date.substring(0,4) + "-" + date.substring(5,7);

		String sql = "select * from workhistory where convert(varchar(50),worktime,120) like '"+ time +"%'";

		return DBConnection.getWorkHistory(sql, path+subpath);
	}

	//导出本月调休申请结果
	public static List<Work> query_monthlyovertime(String name)
	{
		String subpath = "ReivewOverTimeServlet";
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		String date = df.format(new Date());
		String time = date.substring(0,4) + "-" + date.substring(5,7);
		String sql = "select * from dbo.overtime where convert(varchar(50),originwork,120) like '"+ time + "%' and name='"+ name +"'";

		//POST发送查询指令
		return DBConnection.getWork(sql, path + subpath);
	}

	//导出本月请假申请结果
	public static List<Leave> query_monthlyleavetime(String name)
	{
		String subpath = "ReivewLeaveServlet";
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		String date = df.format(new Date());
		String time = date.substring(0,4) + "-" + date.substring(5,7);
		String sql = "select * from workoff where convert(varchar(50),starttime,120) like '"+ time + "%' and name='"+ name +"'";

		//POST发送查询指令
		return DBConnection.getLeave(sql, path + subpath);
	}

	//请假申请
	public static int commit_leaevtime(String name, String start, String end, String originshift, String currentshift, String oweek, String cweek, String res)
	{
		int flag = 0;
		String subpath = "CommitTakeLeaveServlet";
		//生成请假申请信息
		String sql = "insert into dbo.workoff values('" + name + "','" + start + "','"+ end + "','"+ originshift + "','"+ currentshift + "','"
					+ oweek + "','" + cweek + "','"+ res +"',-1)";
		//POST发送查询指令
		flag = DBConnection.exectue(sql, path + subpath);
		return flag;
	}

	//提交调休审核结果
	public static int approve_overtime(Work work)
	{
		int flag = 0;
		String subpath = "CommitOverTimeServlet";
		String sql = "update dbo.overtime set approve = "+ work.getApprove() +"where name='" + work.getName() + "' and ID="+ work.getId();
		//POST发送查询指令
		flag = DBConnection.exectue(sql, path + subpath);
		return flag;
	}

	//提交请假审核结果
	public static int approve_leavetime(Leave leave)
	{
		int flag = 0;
		String subpath = "CommitTakeLeaveServlet";
		String sql = "update workoff set approve = "+ leave.getApprove() +"where name='" + leave.getName() + "' and ID="+ leave.getID();
		//POST发送查询指令
		flag = DBConnection.exectue(sql, path + subpath);
		return flag;
	}

	//导出某人当月调休信息
	public static List<String> query_work(String name){
		String subpath = "QueryWorktTimeServlet";
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		String date = df.format(new Date());
		String time = date.substring(0,4) + "-" + date.substring(5,7);

		List<String> result = new ArrayList<String>();
		List<String> early = query_early(name, time, path + subpath);
		List<String> late = query_late(name, time, path + subpath);

		for(String l : early)
		{
			result.add(l+" 当天下午");
		}
		for(String l : late)
		{
			result.add(l+" 明天上午");
		}
		return result;
	}

	/*导出本月所有人员的调休申请信息，此接口已废弃*/
	public static List<Work> query_overtimeall()
	{
		String subpath = "ReivewOverTimeServlet";
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		String date = df.format(new Date());
		String time = date.substring(0,4) + "-" + date.substring(5,7);
		//String sql = "select * from dbo.overtime where convert(varchar(50),originwork,120) like '"+ time + "%' and approve=4";
		String sql = "select * from dbo.overtime where (convert(varchar(50),originwork,120) like '"+ time + "%' or  convert(varchar(50),overwork,120) like '"+time +"%') and approve=4";
		//POST发送查询指令
		return DBConnection.getWork(sql, path + subpath);
	}

	public static List<Work> query_overtime()
	{
		String sql = "select * from dbo.overtime where approve=-1";
		String subpath = "ReivewOverTimeServlet";
		//POST发送查询指令
		return DBConnection.getWork(sql, path + subpath);
	}

	//早班
	public static List<String> query_early(String name, String time, String path)
	{
		String sql = "select convert(varchar(50),worktime,120) as worktime from workhistory where convert(varchar(50),worktime,120) like '"+ time +"%'" + "and worker='"
				+ name + "'";
		return DBConnection.getWorkInfo(sql, path);
	}

	//晚班
	public static List<String> query_late(String name, String time, String path)
	{
		String sql = "select convert(varchar(50),worktime,120) as worktime from workhistory where convert(varchar(50),worktime,120) like '"+ time +"%'" + "and worker1='"
				+ name + "'";
		return DBConnection.getWorkInfo(sql, path);
	}

	//调休申请
	public static int commit_overtime(String name, String origin, String current, String origin_shift, String current_shift, String currentweek, String originweek, String res)
	{
		int flag = 0;
		String subpath = "CommitOverTimeServlet";
		//生存调休申请信息
		String sql = "insert into dbo.overtime values('" + name + "','" + origin+ "','"+ current + "','"+ origin_shift+ "','"+ current_shift
				+ "','" + originweek + "','" + currentweek + "','"+ res +"',-1)";
		//POST发送查询指令
		flag = DBConnection.exectue(sql, path + subpath);
		return flag;
	}

	//此接口暂保留，但无用，获取原始调休时间对应值班记录
	/*public static Work query_overinfo(String name, String date)
	{
		String subpath = "QueryWorktInfoServlet";
		String day = date.substring(0, date.indexOf(" "));

		String sql = "select worker, worker1 as affectnum from dbo.workhistory where convert(varchar(50),worktime,120) like '" + day +"%'"
				+ "and (worker = '" + name +"' or worker1 = '" + name +"')";
		System.out.println(sql);

		return DBConnection.getWork(sql, path + subpath);
	}*/

	//此接口暂保留，但无用
	/*public static int query_overtime(String name, String date)
	{
		String subpath = "QueryWorktTimeServlet";
		//仅值班当天可选调休,当天应只有一条记录
		int flag = 0;
		String day = date.substring(0, date.indexOf(" ")) ;
		System.out.println(day);
		String sql = "select count(*) as affectnum from dbo.workhistory where convert(varchar(50),worktime,120) like '" + day +"%'"
				+ "and (worker = '" + name +"' or worker1 = '" + name +"')";
		flag = DBConnection.exectue(sql, path + subpath);
		System.out.println("affectnum:"+flag);

		return flag;
	}*/
}
