package com.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
	static private Connection connection;

	private DBUtil() {

	}

	public static Connection getConnection() {
		return connection;
	}

	public static void closeConnection() throws Exception {
		connection.close();
	}

	static {
		try {
			Class.forName("org.h2.Driver");
			System.out.println("Driver Loaded.");
			connection = DriverManager.getConnection("jdbc:h2:~/test", "sa", "");
			System.out.println("Connected");
		} catch (ClassNotFoundException e) {
			System.out.println("Couldn't load the driver.");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("Couldn't establish the connection.");
			e.printStackTrace();
		}
	}
}
