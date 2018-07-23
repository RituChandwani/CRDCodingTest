package com.crd.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtils {
	
	public static Connection getDatabaseConnection() throws ClassNotFoundException,SQLException {
		try {
			
			Class.forName(DBConstants.DRIVERNAME);
		} catch (ClassNotFoundException e) {
			System.out.println("Where is your PostgreSQL JDBC Driver? " + "Include in your library path!");
			throw e;
		}
		Connection connection = null;
		try {
			String dbURL=DBConstants.CONNECTION_URL;
			String dbName=DBConstants.DBNAME;
			connection = DriverManager.getConnection(dbURL+dbName,
					DBConstants.DBUSER, DBConstants.DBPASSWORD);
		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return null;
		}

		if (connection != null) {
			return connection;
		} else {
			return null;
		}
	}
	
	public static void closeConnection(){
		
	}

}
