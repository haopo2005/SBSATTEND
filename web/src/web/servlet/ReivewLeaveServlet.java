package web.servlet;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import web.db.dbConn;

public class ReivewLeaveServlet extends HttpServlet {
	private ResultSet rs = null;
	private Log log = LogFactory.getLog( this .getClass());
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		super.doGet(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String sql = new String(req.getParameter("SQL").getBytes("ISO8859-1"), "UTF-8");
		log.info(sql);
		try {
			rs = dbConn.get(sql);
			if (rs.next()) {
				StringBuilder builder = new StringBuilder();
				builder.append('[');
				do {					
					builder.append('{');
					builder.append("name:\"").append(rs.getString("name")).append("\",");
					builder.append("start:\"").append(rs.getString("starttime")).append("\",");
					builder.append("end:\"").append(rs.getString("endtime")).append("\",");
					builder.append("origin:\"").append(rs.getString("originshift")).append("\",");
					builder.append("current:\"").append(rs.getString("currentshift")).append("\",");
					builder.append("oweek:\"").append(rs.getString("originweek")).append("\",");
					builder.append("cweek:\"").append(rs.getString("currentweek")).append("\",");
					builder.append("reason:\"").append(rs.getString("reason")).append("\",");
					builder.append("approve:").append(rs.getInt("approve")).append(",");
					builder.append("id:").append(rs.getInt("ID")).append("},");
				} while (rs.next());
				builder.deleteCharAt(builder.length() - 1);
				builder.append(']');
				req.setAttribute("json", builder.toString());
				//System.out.println(builder.toString());
				log.info(builder.toString());
			} else {
				//System.out.println("无请假数据");
				log.info("无请假数据");
			}
			req.getRequestDispatcher("result.jsp").forward(req, resp);
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
