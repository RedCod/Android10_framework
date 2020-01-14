package com.service.sub;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.database.*;

public class MessageCenter {
	/**
	 * Mesaj işlemleri. Kaydet,Sil,bilgileri düzenle. etc.
	 * used by:
	 * 		-WS_MessageCenter.java
	 */
/*
 CREATE TABLE IF NOT EXISTS tblMessageCenter (
    Id INT AUTO_INCREMENT,
    AccountId INT,
    Message VARCHAR(255),
    MessageDateTime VARCHAR(50),
    MessageType INT,
    PRIMARY KEY (Id)
);
 */
	private static String TAG = "MessageCenter";
	public MessageCenter() {}
	
	/**
	 * Add Message for User. 
	 * Server tarafından işletilen Schedular ve hizmet sağlayıcı firmanın User için yayınladığı mesajlar.
	 * @param accountId			//which account.
	 * @param message			//message content.
	 * @param messageDateTime	//message date time.
	 * @param messageType		//message type.
	 * @return					//return true(as JSON) if adding is successful.
	 */
	public String addMessage(int accountId,String message,String messageDateTime,int messageType) {
		String response = "{\"AddMessage\":[{ \"response\":false}]}";
		String insert_sql = "INSERT INTO tblMessageCenter(AccountId,Message,MessageDateTime,MessageType) VALUES('"+ accountId +"','"+ message +"','" + messageDateTime+"','"+messageType+"')";
		Database database = new Database();
		try {
			database.connect();
			int s = database.execSQL(insert_sql);
			if(s > 0)
				response = "{\"AddMessage\":[{ \"response\":true}]}";
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
	}//addMessage()
	
	/**
	 * Edit Message.
	 * @param messageId			//which message.
	 * @param message			//message content.
	 * @param messageDateTime	//message date time.
	 * @param messageType		//message type.
	 * @return					//return true(as JSON) if editing is successful.
	 */
	public String editMessage(int messageId,String message,String messageDateTime,int messageType) {
		String response = "{\"EditMessage\":[{ \"response\":false}]}";
		String insert_sql = "UPDATE tblMessageCenter SET Message='" + message+"',MessageDateTime='"+ messageDateTime +"',MessageType='" + messageType +"' WHERE Id='" + messageId +"'";
		Database database = new Database();
		try {
			database.connect();
			int s = database.execSQL(insert_sql);
			if(s > 0)
				response = "{\"EditMessage\":[{ \"response\":true}]}";
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
	}//editMessage()
	
	/**
	 * Get one message Item.
	 * @param messageId	//which message.
	 * @return			//return message Item as JSON.
	 */
	public String getItem(int messageId) {
		String query_sql = "SELECT *FROM tblMessageCenter WHERE Id='" + messageId + "'";
		Database database = new Database();
		String content = "{\"Item\":[\n";
		try {
			database.connect();
			ResultSet resultSet  = database.queryForResultSet(query_sql);
            while (resultSet.next()) {
                content +="{\n";
                content += "\"Id\":"+resultSet.getInt(1) + ",\n";
                content += "\"AccountId\":"+resultSet.getInt(2) + ",\n";
                content += "\"Message\":\""+resultSet.getString(3) + "\",\n";
                content += "\"MessageDateTime\":\""+resultSet.getString(4) + "\",\n";
                content += "\"MessageType\":"+resultSet.getInt(5) + "";
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
	 * Get all message as list.
	 * @param accountId	//which account.
	 * @return			//return all message Item as JSON.
	 */
	public String getAllAsList(int accountId) {
		String query_sql = "SELECT *FROM tblMessageCenter WHERE AccountId='" + accountId +"'";
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
	                content += "\"Message\":\""+resultSet.getString(3) + "\",\n";
	                content += "\"MessageDateTime\":\""+resultSet.getString(4) + "\",\n";
	                content += "\"MessageType\":"+resultSet.getInt(5) + "";
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
	 * Remove specified message.
	 * @param messageId	//which message.
	 * @return			//return true(as JSON) if remove message is successful.
	 */
	public String removeMessage(int messageId) {
		String response = "{\"RemoveMessage\":[{ \"response\":false}]}";
		String insert_sql = "DELETE FROM tblMessageCenter WHERE Id='" + messageId +"'";
		Database database = new Database();
		try {
			database.connect();
			int s = database.execSQL(insert_sql);
			if(s > 0)
				response = "{\"RemoveMessage\":[{ \"response\":true}]}";
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
	}//removeMessage()
	
}
