package com.coleji.Database;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.coleji.Util.PropertiesWrapper;
import com.mysql.jdbc.Driver;

public class OracleConnectionManager extends ConnectionManager {
	public OracleConnectionManager(String connectionPropertiesFileLocation) {
		this.connectionPropertiesFileLocation = connectionPropertiesFileLocation;
	}

	public Connection getConnection() throws Exception  {
		conn = null;
		PropertiesWrapper props = new PropertiesWrapper(connectionPropertiesFileLocation, new String[] {"username", "password", "host", "port", "sid"});
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			DriverManager.registerDriver (new Driver());
			String connectionString = "jdbc:oracle:thin:" + props.getProperty("username") + "/" + 
										props.getProperty("password") + "@" + 
										props.getProperty("host") + ":" + 
										props.getProperty("port") + ":" + 
										props.getProperty("sid");
			conn = DriverManager.getConnection(connectionString);
			return conn;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception();
		}
	}
}