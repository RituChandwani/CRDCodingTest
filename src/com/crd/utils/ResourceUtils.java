package com.crd.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ResourceUtils {

	public static void initResources() throws IOException {
		Properties prop = new Properties();
		InputStream input = null;

		try {
			input = new FileInputStream(DBConstants.PROPERTIES_FILE);
			// load a properties file
			prop.load(input);
			DBConstants.DBNAME=prop.getProperty("dbName");
			DBConstants.DBUSER=prop.getProperty("dbUserName");
			DBConstants.DBPASSWORD=prop.getProperty("dbPassword");
			DBConstants.DRIVERNAME =prop.getProperty("dbDriverName");
			DBConstants.CONNECTION_URL=prop.getProperty("dbConnectionURL");
			
		} catch (IOException ex) {
			throw ex;
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
