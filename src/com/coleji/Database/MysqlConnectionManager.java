package com.coleji.Database;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.coleji.Util.PropertiesWrapper;

public class MysqlConnectionManager extends ConnectionManager {
	public MysqlConnectionManager(String connectionPropertiesFileLocation) {
		this.connectionPropertiesFileLocation = connectionPropertiesFileLocation;
	}

	public Connection getConnection() throws Exception  {
		conn = null;
		PropertiesWrapper props = new PropertiesWrapper(connectionPropertiesFileLocation, new String[] {"user", "password", "host", "port"});
		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			conn = DriverManager.getConnection("jdbc:mysql://" + props.getProperty("host") + ":" + props.getProperty("port") + "/",props);
			return conn;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception();
		}
	}
}