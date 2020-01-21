package com.service.sub;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.database.Database;

public class Feedback {
	
	/**
	 * Feedback(Geri bildirim). Kaydet,Sil,bilgileri düzenle. etc.
	 * used by:
	 * 		-WS_Feedback.java
	 */
	/*
	 CREATE TABLE IF NOT EXISTS tblFeedback (
    Id INT AUTO_INCREMENT,
    AccountId INT,
    Content VARCHAR(255),
    FdDateTime VARCHAR(50) DEFAULT '',
    PRIMARY KEY (Id)
    );
	 */
	private static String TAG = "Feedback";
	public Feedback() {}
	//String response = "{\"AddHelp\":[{ \"response\":false}]}";
	
	/**
	 * Add feedback.
	 * @param accountId	 //which account.
	 * @param content	 //content. choose from ready-made content.
	 * @param feedbackDateTime //feedback date and time.	
	 * @return			 //return true(as JSON) if add feedback is successful.
	 */
	public String addFeedback(int accountId,String content,String feedbackDateTime) {
		String response = "{\"AddFeedback\":[{ \"response\":false}]}";
		String sql = "INSERT INTO tblFeedback(AccountId,Content,FdDateTime)VALUES('" + accountId + "','" + content + "','"+ feedbackDateTime +"')";
		Database database = new Database();
		try {
			database.connect();
			int s = database.execSQL(sql);
			if(s > 0)
				response = "{\"AddFeedback\":[{ \"response\":true}]}";
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
	}//addFeedback()
	
	/**
	 * Edit feedback item.
	 * @param feedbackRowId	//which feedback item.
	 * @param content		//content. choose from ready-made content.
	 * @param feedbackDateTime	//feedback date and time.
	 * @return				//return true(as JSON) if edit feedback is successful.
	 */
	public String editFeedback(int feedbackRowId,String content,String feedbackDateTime) {
		String response = "{\"EditFeedback\":[{ \"response\":false}]}";
		String sql = "UPDATE tblFeedback SET Content='" + content + "',FdDateTime='" + feedbackDateTime + "' WHERE Id='" + feedbackRowId + "'";
		Database database = new Database();
		try {
			database.connect();
			int s = database.execSQL(sql);
			if(s > 0)
				response = "{\"EditFeedback\":[{ \"response\":true}]}";
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
	}//editFeedback()

	/**
	 * Get feedback account-related as Item.
	 * @param feedbackRowId //which feedback.
	 * @return			//return Item as JSON.
	 */
	public String getItem(int feedbackRowId) {
		String query_sql = "SELECT *FROM tblFeedback WHERE Id='" + feedbackRowId + "'";
		Database database = new Database();
		String content = "{\"Item\":[\n";
		try {
			database.connect();
			ResultSet resultSet  = database.queryForResultSet(query_sql);
            while (resultSet.next()) {
                content +="{\n";
                content += "\"Id\":"+resultSet.getInt(1) + ",\n";
                content += "\"AccountId\":"+resultSet.getInt(2) + ",\n";
                content += "\"Content\":\""+resultSet.getString(3) + "\",\n";
                content += "\"FdDateTime\":\""+resultSet.getString(4) + "\",";
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
	
	/**
	 * Get all feedback account-related as List.
	 * @param accountId //which account.
	 * @return			//return all feedback related account as JSON.
	 */
	public String getAllAsList(int accountId) {
		String query_sql = "SELECT *FROM tblFeedback WHERE AccountId='" + accountId +"'";
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
	                content += "\"AccountId\":"+resultSet.getInt(2) + ",\n";
	                content += "\"Content\":\""+resultSet.getString(3) + "\",\n";
	                content += "\"FdDateTime\":\""+resultSet.getString(4) + "\",";
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
	 * Remove feedback item.
	 * @param feedbackRowId	//which item.
	 * @return				//return true(as JSON) if remove is successful.
	 */
	public String remove(int feedbackRowId) {
		String response = "{\"Remove\":[{ \"response\":false}]}";
		String sql = "DELETE FROM tblFeedback WHERE Id='" + feedbackRowId +"'";
		Database database = new Database();
		try {
			database.connect();
			int s = database.execSQL(sql);
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
