package com.example.application;

import java.sql.*;

public class PizzaDBManager{
	final static private String DB_URL = "jdbc:derby:Pizza;create=true";
	
	public static void getConn() throws SQLException{
		DriverManager.getConnection(DB_URL);
	}
}
