package com.service.sub;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.database.Database;

public class HelpCenter {
	/**
	 * Help(Yardım Merkezi)Center. Kaydet,Sil,bilgileri düzenle. etc.
	 * used by:
	 * 		-WS_HelpCenter.java
	 */
	/*
		CREATE TABLE IF NOT EXISTS tblHelpCenter (
	    Id INT AUTO_INCREMENT,
	    Title VARCHAR(255),
	    ContentPath VARCHAR(255),
	    PRIMARY KEY (Id)
		);
	 */
	private static String TAG = "HelpCenter";
	public HelpCenter() {}
	
	//String response = "{\"AddFamily\":[{ \"response\":false}]}";
	
	/**
	 * Add help content.
	 * @param title			//title of help content.
	 * @param contentPath	//path of help content. Html page name and path on server.
	 * @return				//return true(as JSON) if adding is successful.
	 */
	public String addHelp(String title,String contentPath) {
		String response = "{\"AddHelp\":[{ \"response\":false}]}";
		String insert_sql = "INSERT INTO tblHelpCenter(Title,ContentPath) VALUES('" + title + "','" + contentPath + "')";
		Database database = new Database();
		try {
			database.connect();
			int s = database.execSQL(insert_sql);
			if(s > 0)
				response = "{\"AddHelp\":[{ \"response\":true}]}";
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				database.close();
			} catch (NullPointerException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return response;
	}//addHelp()
	
	/**
	 * Edit help content.
	 * @param helpRowId		//which content.
	 * @param title			//title of help content.
	 * @param contentPath	//path of help content.
	 * @return				//return true(as JSON) if edit is successful.
	 */
	public String editHelp(int helpRowId,String title,String contentPath) {
		String response = "{\"EditHelp\":[{ \"response\":false}]}";
		String update_sql = "UPDATE tblHelpCenter SET Title='" + title + "',ContentPath='" + contentPath + "' WHERE Id='" + helpRowId + "'";
		Database database = new Database();
		try {
			database.connect();
			int s = database.execSQL(update_sql);
			if(s > 0)
				response = "{\"EditHelp\":[{ \"response\":true}]}";
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				database.close();
			} catch (NullPointerException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return response;
	}//editHelp()

	/**
	 * Get one help content as Item.
	 * @param helpRowId	//which content.
	 * @return			//return help content as JSON.
	 */
	public String getItem(int helpRowId) {
		String query_sql = "SELECT *FROM tblHelpCenter WHERE Id='" + helpRowId + "'";
		Database database = new Database();
		String content = "{\"Item\":[\n";
		try {
			database.connect();
			ResultSet resultSet  = database.queryForResultSet(query_sql);
            while (resultSet.next()) {
                content +="{\n";
                content += "\"Id\":"+resultSet.getInt(1) + ",\n";
                content += "\"Title\":\""+resultSet.getString(2) + "\",\n";
                content += "\"ContentPath\":\""+resultSet.getString(3) + "\"";
                content +="\n}";
           } 
            content += "\n]}";
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				database.close();
			} catch (NullPointerException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return content;
	}//getItem()
	
	public String getAllAsList() {
		String query_sql = "SELECT *FROM tblHelpCenter";
		Database database = new Database();
		String content = "{\"AllList\":[\n";
		try {
			database.connect();
			ResultSet resultSet  = database.queryForResultSet(query_sql);
			int count = 0;
            while (resultSet.next()) {
	           	 if(count == 0)
	        		 content +="{\n";
	        	 else
	        		content +=",\n{\n";
	                content += "\"Id\":"+resultSet.getInt(1) + ",\n";
	                content += "\"Title\":\""+resultSet.getString(2) + "\",\n";
	                content += "\"ContentPath\":\""+resultSet.getString(3) + "\"";
	                content +="\n}";
                count++;
           } 
            content += "\n]}";
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				database.close();
			} catch (NullPointerException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return content;
	}//getAllAsList()
	
	/**
	 * Remove specified help Item.
	 * @param helpRowId	//which Item.
	 * @return			//return true(as JSON) if help item remove is successful.
	 */
	public String remove(int helpRowId) {
		String response = "{\"Remove\":[{ \"response\":false}]}";
		String remove_sql = "DELETE FROM tblHelpCenter WHERE Id='" + helpRowId + "'";
		Database database = new Database();
		try {
			database.connect();
			int s = database.execSQL(remove_sql);
			if(s > 0)
				response = "{\"Remove\":[{ \"response\":true}]}";
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				database.close();
			} catch (NullPointerException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return response;
	}//remove()
}
