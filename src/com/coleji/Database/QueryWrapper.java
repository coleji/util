package com.coleji.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

// TODO: implement a state machine, including BUILDING_STATEMENT, CONNECTION_OPEN, and CONNECTION_CLOSED.

public class QueryWrapper {
	private StringBuilder sb;
	private Statement st;
	private Connection c;
	private ResultSet rs;
	
	public QueryWrapper(){
		this.sb = new StringBuilder();
	}
	
	public void add(String s){
		sb.append(" " + s);
	}
	
	public void printQuery(){
		System.out.println(new String(sb));
	}
	
	public ResultSet runQueryAndGetResultSet(ConnectionManager cm) throws Exception {
		return runQueryAndGetResultSet(cm.getConnection());
	}
	
	public ResultSet runQueryAndGetResultSet(Connection c) throws Exception {
		this.st = c.createStatement();
		this.rs = st.executeQuery(new String(sb));
		return this.rs;
	}
	
	public int runUpdateOrDeleteAndClose(ConnectionManager cm) throws Exception {
		this.c = cm.getConnection();
		this.st = c.createStatement();
		int rowsUpdated = st.executeUpdate(new String(sb));
		st.close();
		c.close();
		return rowsUpdated;
	}
	/*
	public void close() throws IOException {
		try {
			this.rs.close();
			this.st.close();
			this.c.close();
		} catch (SQLException e) {
			throw new IOException();
		}
	}*/
}
