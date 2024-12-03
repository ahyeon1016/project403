package com.spring.repository;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
	
	public static Connection dbconn() throws Exception{
		Class.forName("com.mysql.jdbc.Driver");
		
	    String database = "jdbc:mysql://localhost:3306/project403"; 
	    String id = "root"; 
	    String pwd = "1234";
	    Connection conn = DriverManager.getConnection(database, id, pwd);
	    
	    return conn;
	}
}
