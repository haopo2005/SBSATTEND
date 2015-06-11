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

public class QueryWorktTimeServlet extends HttpServlet {
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
					builder.append("work:").append(rs.getString("worktime").substring(0, 10));
					builder.append("},");					
				} while (rs.next());
				builder.deleteCharAt(builder.length() - 1);
				builder.append(']');
				req.setAttribute("json", builder.toString());
				log.info(builder.toString());
			} else {
				StringBuilder builder = new StringBuilder();
				builder.append('[');
				builder.append('{');
				builder.append("work:").append("-1");
				builder.append("}");
				builder.append(']');
				req.setAttribute("json", builder.toString());
				log.info(builder.toString());
			}  
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		
		req.getRequestDispatcher("result.jsp").forward(req, resp);
	}

}
