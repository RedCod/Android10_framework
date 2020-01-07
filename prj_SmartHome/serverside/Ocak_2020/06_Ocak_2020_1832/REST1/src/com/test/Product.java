package com.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "movie")
public class Product {
	
	private String name;
	private int age;
	private String career;
	private String ID;
		
	public Product() {
		//
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public void setCareer(String career) {
		this.career = career;
	}
	
	@XmlElement
	public String getName() {
		return name;
	}

	@XmlElement
	public int getAge() {
		return age;
	}
	
	@XmlElement
	public String getCareer() {
		return career;
	}
	
	@XmlElement
	public String getId() {
		return ID;
	}
	
	public void genelinsert() {
		 try
		    {
		      // create a mysql database connection
		      String myDriver = "org.gjt.mm.mysql.Driver";
		      String myUrl = "jdbc:mysql://localhost/SmartHome";
		      Class.forName(myDriver);
		      Connection conn = DriverManager.getConnection(myUrl, "root", "");
		    
		      // create a sql date object so we can use it in our INSERT statement
		      Calendar calendar = Calendar.getInstance();
		      java.sql.Date startDate = new java.sql.Date(calendar.getTime().getTime());

		      // the mysql insert statement
		      String query = " insert into users (first_name, last_name, date_created, is_admin, num_points)"
		        + " values (?, ?, ?, ?, ?)";

		      // create the mysql insert preparedstatement
		      PreparedStatement preparedStmt = conn.prepareStatement(query);
		      preparedStmt.setString (1, "Barney");
		      preparedStmt.setString (2, "Rubble");
		      preparedStmt.setDate   (3, startDate);
		      preparedStmt.setBoolean(4, false);
		      preparedStmt.setInt    (5, 5000);

		      // execute the preparedstatement
		      preparedStmt.execute();
		      
		      conn.close();
		    }
		    catch (Exception e)
		    {
		      System.err.println("Got an exception!");
		      System.err.println(e.getMessage());
		    }
		  }//genelinsert
	
	
	
	public void Insert() {
		 try
		    {
		      // create a mysql database connection
		      String myDriver = "org.gjt.mm.mysql.Driver";
		      String myUrl = "jdbc:mysql://localhost/SmartHome";
		      Class.forName(myDriver);
		      Connection conn = DriverManager.getConnection(myUrl, "root", "");
		    
		      // create a sql date object so we can use it in our INSERT statement
		      Calendar calendar = Calendar.getInstance();
		      java.sql.Date startDate = new java.sql.Date(calendar.getTime().getTime());

		      // the mysql insert statement
		      String query = " insert into users (first_name, last_name, date_created, is_admin, num_points)"
		        + " values (?, ?, ?, ?, ?)";

		      // create the mysql insert preparedstatement
		      PreparedStatement preparedStmt = conn.prepareStatement(query);
		      preparedStmt.setString (1, "Barney");
		      preparedStmt.setString (2, "Rubble");
		      preparedStmt.setDate   (3, startDate);
		      preparedStmt.setBoolean(4, false);
		      preparedStmt.setInt    (5, 5000);

		      // execute the preparedstatement
		      preparedStmt.execute();
		      
		      conn.close();
		    }
		    catch (Exception e)
		    {
		      System.err.println("Got an exception!");
		      System.err.println(e.getMessage());
		    }
		  }//genelinsert
	
 
	public void genelSelect2() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/SmartHome";
	        String user = "root";
	        String password = "";
	        
	        	String query = "SELECT *FROM tesS";
	            Connection con = DriverManager.getConnection(url, user, password);
	            Statement st = con.createStatement();
	            ResultSet rs = st.executeQuery(query);
	            while (rs.next()) {
	            	ID = rs.getString(2);
	                System.out.println(rs.getString(1));
	                System.out.println(rs.getString(2));
	                System.out.println(rs.getString(3));
	                System.out.println(rs.getString(4));
	            }
	        } catch (Exception ex) {

	            System.out.println("exceptionW:" + ex.getMessage());
	        } 
	}
	 

	/*@XmlElement 
	public String getToString() {
		String to  = name + "," +  age;
		return to;
	}*/

}
