package web.servlet;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import web.db.dbConn;

public class LoginServlet extends HttpServlet {
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
		String sql = req.getParameter("SQL");
		log.info(sql);
		try {
			rs = dbConn.get(sql);
			if (rs.next()) {
				StringBuilder builder = new StringBuilder();
				builder.append('[');
				do {						
					builder.append('{');
					builder.append("id:").append(rs.getInt("ID")).append(',');
					builder.append("name:\"").append(rs.getString("username"))
							.append("\",");
					builder.append("permission:")
							.append(rs.getInt("permission")).append(",");
					builder.append("quotan:").append(rs.getFloat("quotan"));
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
				builder.append("id:").append(0).append(',');
				builder.append("name:\"").append("xx").append("\",");
				builder.append("permission:").append(-1).append(',');
				builder.append("quotan:").append(0.0);
				builder.append("}");
				builder.append(']');
				req.setAttribute("json", builder.toString());
				log.info(builder.toString());
			}

			req.getRequestDispatcher("result.jsp").forward(req, resp);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
