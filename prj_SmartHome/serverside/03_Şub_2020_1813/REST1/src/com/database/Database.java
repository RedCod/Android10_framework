package com.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.service.sub.Account;

public class Database {
	
	private String MySQL_USERNAME = "root";//MySQL username.
	private String MySQL_PASSWORD = "";//MySQL password.
	private Connection connection = null;
	
	public Database() { }
	
	/**
	 * Connect to MySql.
	 * @throws SQLException if a database access error occurs
	 * @throws ClassNotFoundException if "com.mysql.jdbc.Driver" class not found.
	 */
	public void connect() throws SQLException, ClassNotFoundException  {
		 Class.forName("com.mysql.jdbc.Driver");
		 String url = "jdbc:mysql://localhost:3306/SmartHome";
		 connection = DriverManager.getConnection(url,MySQL_USERNAME,MySQL_PASSWORD);
	}
	
	/**
	 * Reconnect to Database.
	 * First closes the this connection and then opens it again.
	 * If there is no open connection,please use {@link #connect()} function.
	 * @throws ClassNotFoundException
	 * @throws SQLException if a database access error occurs
	 */
	public void reconnect() throws ClassNotFoundException, SQLException {
		close();
		connect();
	}
	
	/**
	 * Connection is closed?
	 * @return true if this Database Connection object is closed.
	 * @throws NullPointerException
	 * @throws SQLException if a database access error occurs
	 */
	public boolean isClosed() throws NullPointerException,SQLException {
		if(connection == null)
			throw new NullPointerException("Object is null");
		
		if(connection.isClosed())
			return true;
		return false;
	}
	
	/**
	 * Close this Database Connection.
	 * @throws NullPointerException 
	 * @throws SQLException if a database access error occurs
	 */
	public void close() throws NullPointerException, SQLException  {
	    if(isClosed() == false)
	    	connection.close();
	}
	
	/* *************
	//DİKKAT: query from mysql as JSON.
	//SELECT JSON_OBJECT('id',id,'date',date,'datetime',datetime,'c',c) FROM tesS
	*/
	
	/**
	 * for Query
	 * @param sql_code SQL Query code.
	 * @return  return query result 
	 * @throws SQLException if a database access error occurs
	 */
  public String query(String sql_code) throws SQLException {
	  //sql Query:
      Statement statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery(sql_code);
      String content = "";
      int count_line = 1; //for json line side the Account.java
      while (resultSet.next()) {
    	  if(count_line == 1)
    		  content += resultSet.getString(1);
    	  else
    		  content += ","+resultSet.getString(1);
    	  
    	  count_line++;
      }
	  return content;
  }
  
  /**
   * 
   * @param sql_code  //SQL query code.
   * @return return ResultSet
   * @throws SQLException if a database access error occurs
   */
  public ResultSet queryForResultSet(String sql_code)throws SQLException{
      Statement statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery(sql_code);
      return resultSet;
  }
  
  
  /**
   * For Insert,Update,Delete.
   * @param sqlContent //sql code.
   * @return the row count for SQL Data Manipulation Language (DML) statements or return 0(zero) for SQL statements that return nothing.
   * @throws SQLException if a database access error occurs.
   */
  public int execSQL(String sql) throws SQLException {
	  ///sql "insert,update,delete":
     Statement statement = connection.createStatement();
     int state = statement.executeUpdate(sql);
     return state;
  }
  
  
	

}
