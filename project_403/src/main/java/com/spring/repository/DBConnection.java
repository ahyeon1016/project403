package com.spring.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	

	  public static Connection getConnection() throws SQLException,
	  ClassNotFoundException {
	  
		Connection conn = null;		
	
		String url = "jdbc:mariadb://wjdwoals222.cafe24.com:3306/wjdwoals222?useUnicode=true&characterEncoding=utf8";
		String user = "wjdwoals222";
		String password = "rlpl403!";

		Class.forName("org.mariadb.jdbc.Driver");
		conn = DriverManager.getConnection(url, user, password);		
		System.out.println("데이터베이스가 연결되었습니다.");
		return conn;
		
	}

}
