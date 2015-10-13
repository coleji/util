package com.coleji.Database;

import java.sql.Connection;
import java.sql.SQLException;

// TODO: (maybe?)  rather than create new connection on call to getConnection(), hand out connections from a pool of 2-3
// maybe a different constructor that can do this?  Or two different manager subclasses?

public abstract class ConnectionManager {
	protected Connection conn;
	protected String connectionPropertiesFileLocation;
	
	public abstract Connection getConnection() throws Exception;
	
	public void close() throws SQLException {
		conn.close();
	}
}
