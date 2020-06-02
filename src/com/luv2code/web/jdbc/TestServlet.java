package com.luv2code.web.jdbc;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;




/**
 * Servlet implementation class TestServlet
 */
@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	//Define datasource/connection pool for the resource injection
	@Resource(name="jdbc/web_student_tracker")
	private DataSource datasource;
	
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		//step1 setup printer
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		
		//step2 get a connection to data base
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
	
		try {
			myConn = datasource.getConnection();
			
			//step3 create a sql statement
			String sql = "select * from student";
			myStmt = myConn.createStatement();
			
			//step4 Execute the query
			myRs = myStmt.executeQuery(sql);
			
			// Process the result set
			while(myRs.next()) {
				String email = myRs.getString("email");
				out.println(email);
			}
		}
		catch(Exception ee){
			ee.printStackTrace();
		}
	}

}
