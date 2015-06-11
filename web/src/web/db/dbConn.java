package web.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

public class dbConn {
	private static Connection ct = null;
	private static Statement sm = null;
	private static ResultSet rs = null;
	
	public static ResultSet get(String sql) throws SQLException, ClassNotFoundException
	{
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		ct = DriverManager.getConnection(
				"jdbc:sqlserver://192.168.100.19;DatabaseName=WorkFlow",
				"sa", "Pass@1234");
		sm = ct.createStatement();
		rs = sm.executeQuery(sql);
		return rs;
	}
	
	public static int execute(String sql) throws ClassNotFoundException, SQLException
	{
		int flag = 0;
		int count = 0;
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		ct = DriverManager.getConnection(
				"jdbc:sqlserver://192.168.100.19;DatabaseName=WorkFlow",
				"sa", "Pass@1234");
		sm = ct.createStatement();
		if(!sm.execute(sql))
		{
			count = sm.getUpdateCount();
		}
		
		if(count == 1)
			flag = 1;
		return flag;
	}
}
