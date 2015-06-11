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

public class QueryWorktHistoryServlet extends HttpServlet {
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
		String sql = new String(req.getParameter("SQL").getBytes("ISO8859-1"),
				"UTF-8");
		log.info(sql);
		try {
			rs = dbConn.get(sql);
			if (rs.next()) {
				StringBuilder builder = new StringBuilder();
				builder.append('[');
				do {
					builder.append('{');
					builder.append("day:\"").append(rs.getString("worker")).append("\",");
					builder.append("night:\"").append(rs.getString("worker1")).append("\",");
					builder.append("time:\"").append(rs.getString("worktime")).append("\"},");
				} while (rs.next());
				builder.deleteCharAt(builder.length() - 1);
				builder.append(']');
				req.setAttribute("json", builder.toString());
				log.info(builder.toString());
			} else {
				//System.out.println("无值班信息！");
				log.info("无值班信息！");
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		req.getRequestDispatcher("result.jsp").forward(req, resp);
	}

}
