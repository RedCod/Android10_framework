package com.service.sub;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.database.Database;

public class DontDisturbDevices {
	/**
	 * "Cihazları Rahatsız Etmeyin". Kaydet,Sil,bilgileri düzenle. etc.
	 * used by:
	 * 		-WS_DontDisturbDevices.java
	 */
	/*
CREATE TABLE IF NOT EXISTS tblDontDisturbDevices(
    Id INT AUTO_INCREMENT,
    AccountId INT,
    Title VARCHAR(100),
    TimeStart VARCHAR(15),
    TimeEnd VARCHAR(15),
    DevicesId VARCHAR(500),
    Monday INT DEFAULT 1,
    Tuesday INT DEFAULT 1,
    Wednesday INT DEFAULT 1,
    Thursday INT DEFAULT 1,
    Friday INT DEFAULT 1,
    Saturday INT DEFAULT 1,
    Sunday INT DEFAULT 1,
    PRIMARY KEY (Id)
);
	 */
	private static String TAG = "DontDisturbDevices";
	public DontDisturbDevices() {}
	
	/**
	 * Get "Don't disturb devices(Cihazları Rahatsız etmeyin)" all Item list as JSON.
	 * @param accountId //which account
	 * @param accountName //account
	 * @return	//Get all Item list as JSON.
	 */
	public String getAllAsList(int accountId,String accountName) {
		///for improving security[28120201126]:@{
		if(!Account.accountIsExists(accountId, accountName)){
			return "{\"AllList\":[ ]}";
		}
		///for improving security[28120201126]:@}
		String query_sql = "SELECT *FROM tblDontDisturbDevices WHERE AccountId='" + accountId +"'";
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
                //content += "\"Id\":\""+resultSet.getInt(1) + "\",\n";
	             content += "\"Id\":"+resultSet.getInt(1) + ",\n";
	             content += "\"AccountId\":"+resultSet.getInt(2) + ",\n";
	             content += "\"Title\":\""+resultSet.getString(3) + "\",\n";
	             content += "\"TimeStart\":\""+resultSet.getString(4) + "\",\n";
	             content += "\"TimeEnd\":\""+resultSet.getString(5) + "\",\n";
	             content += "\"DevicesId\":\""+resultSet.getString(6) + "\",\n";
	             content += "\"Monday\":"+resultSet.getInt(7) + ",\n";
	             content += "\"Tuesday\":"+resultSet.getInt(8) + ",\n";
	             content += "\"Wednesday\":"+resultSet.getInt(9) + ",\n";
	             content += "\"Thursday\":"+resultSet.getInt(10) + ",\n";
	             content += "\"Friday\":"+resultSet.getInt(11) + ",\n";
	             content += "\"Saturday\":"+resultSet.getInt(12) + ",\n";
	             content += "\"Sunday\":"+resultSet.getInt(13) + "";
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
		/*String query_sql = "SELECT JSON_OBJECT('Id',Id,'AccountId',AccountId,'Title',Title,'TimeStart',TimeStart,'TimeEnd',TimeEnd,'DevicesId',DevicesId,"
				+"'Monday',Monday,'Tuesday',Tuesday,'Wednesday',Wednesday,'Thursday',Thursday,'Friday',Friday,'Saturday',Saturday,'Sunday',Sunday) FROM tblDontDisturbDevices WHERE AccountId='" + accountId +"'";
		Database database = new Database();
		String content = "{\"AllList\":[\n";
		try {
			database.connect();
            content += database.query(query_sql);
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
		return content;*/
	}//getAllAsList()
	
	/**
	 * Get "Don't disturb devices(Cihazları Rahatsız etmeyin)" Item as JSON.
	 * @param accountId	//which account
	 * @param accountName //account
	 * @param rowId //which item?
	 * @return	//return Item as JSON.
	 */
	public String getItem(int accountId,String accountName,int rowId) {
		///for improving security[28120201126]:@{
		if(!Account.accountIsExists(accountId, accountName)){
			return "{\"Item\":[ ]}";
		}
		///for improving security[28120201126]:@}		
		String query_sql = "SELECT *FROM tblDontDisturbDevices WHERE Id='" + rowId + "'";
		Database database = new Database();
		String content = "{\"Item\":[\n";
		try {
			database.connect();
			ResultSet resultSet  = database.queryForResultSet(query_sql);
            while (resultSet.next()) {
                content +="{\n";
                content += "\"Id\":"+resultSet.getInt(1) + ",\n";
                content += "\"AccountId\":"+resultSet.getInt(2) + ",\n";
                content += "\"Title\":\""+resultSet.getString(3) + "\",\n";
                content += "\"TimeStart\":\""+resultSet.getString(4) + "\",\n";
                content += "\"TimeEnd\":\""+resultSet.getString(5) + "\",\n";
                content += "\"DevicesId\":\""+resultSet.getString(6) + "\",\n";
                content += "\"Monday\":"+resultSet.getInt(7) + ",\n";
                content += "\"Tuesday\":"+resultSet.getInt(8) + ",\n";
                content += "\"Wednesday\":"+resultSet.getInt(9) + ",\n";
                content += "\"Thursday\":"+resultSet.getInt(10) + ",\n";
                content += "\"Friday\":"+resultSet.getInt(11) + ",\n";
                content += "\"Saturday\":"+resultSet.getInt(12) + ",\n";
                content += "\"Sunday\":"+resultSet.getInt(13) + "";
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
		
		/*String query_sql = "SELECT JSON_OBJECT('Id',Id,'AccountId',AccountId,'Title',Title,'TimeStart',TimeStart,'TimeEnd',TimeEnd,'DevicesId',DevicesId," 
				+ "'Monday',Monday,'Tuesday',Tuesday,'Wednesday',Wednesday,'Thursday',Thursday,'Friday',Friday,'Saturday',Saturday,'Sunday',Sunday) FROM tblDontDisturbDevices WHERE Id='" + id + "'";
		Database database = new Database();
		String content = "{\"Item\":[\n";
		try {
			database.connect();
            content += database.query(query_sql);
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
		return content;*/
	}//getItem()
	
	/**
	 * Add "Don't disturb Devices" (Cihazları Rahatsız Etmeyin).
	 * @param accountId		//which account.
	 * @param accountName	//account(email_or_phone). for improving security
	 * @param timeStart		//Time start. exp:12:00
	 * @param timeEnd		//Time end.	  exp:13:00
	 * @param devicesId		//Selected Devices Ids. exp:"10,102,35,14". //seçilen tüm cihazların id değerleri:exp: "10,102,35,14".
	 * @param monday		//day to repeat for Monday. 1 or 0
	 * @param tuesday		//day to repeat for Tuesday. 1 or 0
	 * @param wednesday		//day to repeat for Wednesday. 1 or 0
	 * @param thursday		//day to repeat for Thursday. 1 or 0
	 * @param friday		//day to repeat for Friday. 1 or 0
	 * @param saturday		//day to repeat for Saturday. 1 or 0
	 * @param sunday		//day to repeat for Sunday. 1 or 0
	 * @return				//return true(as JSON) if "Don't disturb devices" Added successful.
	 */
	public String add(int accountId,
					  String accountName,
					  String timeStart,
					  String timeEnd,
					  String devicesId,
					  int monday,
					  int tuesday,
					  int wednesday,
					  int thursday,
					  int friday,
					  int saturday,
					  int sunday) {
		String response = "{\"Add\":[{ \"response\":\"false\"}]}";
		///for improving security[28120201126]:@{
		if(!Account.accountIsExists(accountId, accountName)){
			return response;
		}
		///for improving security[28120201126]:@}
		String insert_sql = "INSERT INTO tblDontDisturbDevices(AccountId,Title,TimeStart,TimeEnd,DevicesId,Monday,Tuesday,Wednesday,Thursday,Friday,Saturday,Sunday) "
				+" VALUES('" + accountId +"',(SELECT AccountId FROM(SELECT Count(AccountId)+1 AccountId FROM tblDontDisturbDevices WHERE AccountId='"+accountId+"') subquery ),'"+timeStart+"','"+timeEnd+"','"+devicesId+"','"+ monday +"','"+tuesday+"','"+wednesday+"','"+thursday+"','"+friday+"','"+saturday+"','"+sunday+"')";
		Database database = new Database();
		try {
			database.connect();
			int s = database.execSQL(insert_sql);
			if(s > 0)
				response = "{\"Add\":[{ \"response\":true}]}";
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
	}//add()
	
	/**
	 * Edit "Don't disturb Devices"(Cihazları Rahatsız Etmeyin).
	 * @param accountId		//which account
	 * @param accountName   //account
	 * @param rowId			//which item Id.
	 * @param timeStart		//Time start. exp:12:00
	 * @param timeEnd		//Time end.   exp:13:00
	 * @param devicesId		//Selected Devices Ids. exp:"10,102,35,14". //seçilen tüm cihazların id değerleri:exp: "10,102,35,14".
	 * @param monday		//day to repeat for Monday. 1 or 0
	 * @param tuesday		//day to repeat for Tuesday. 1 or 0
	 * @param wednesday		//day to repeat for Wednesday. 1 or 0
	 * @param thursday		//day to repeat for Thursday. 1 or 0
	 * @param friday		//day to repeat for Friday. 1 or 0
	 * @param saturday		//day to repeat for Saturday. 1 or 0
	 * @param sunday		//day to repeat for Sunday. 1 or 0
	 * @return				//return true(as JSON) if "Don't disturb devices" Edit successful.
	 */
	public String edit(int accountId,
					   String accountName,
					   int rowId,
					   String timeStart,
					   String timeEnd,
					   String devicesId,
					   int monday,
					   int tuesday,
					   int wednesday,
					   int thursday,
					   int friday,
					   int saturday,
					   int sunday) {
		String response = "{\"Edit\":[{ \"response\":false}]}";
		///for improving security[28120201126]:@{
		if(!Account.accountIsExists(accountId, accountName)){
			return response;
		}
		///for improving security[28120201126]:@}
		String update_sql = "UPDATE tblDontDisturbDevices SET TimeStart='" +timeStart+"',TimeEnd='"+timeEnd+"',DevicesId='"+devicesId+"',Monday='"+monday+"',Tuesday='"+tuesday+"',Wednesday='"+wednesday+"',Thursday='"+thursday+"',Friday='"+friday+"',Saturday='"+saturday+"',Sunday='"+sunday+"' WHERE Id='"+rowId+"'";
		Database database = new Database();
		try {
			database.connect();
			int s = database.execSQL(update_sql);
			if(s > 0)
				response = "{\"Edit\":[{ \"response\":true}]}";
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
	}//edit()
	
	/**
	 * Remove "Don't disturb Devices".(Cihazları Rahatsız Etmeyin)
	 * @param accountId //which account
	 * @param accountName //account
	 * @param rowId	//which item Id.
	 * @return		//return true(as JSON) if "Don't disturb devices" remove successful.
	 */
	public String remove(int accountId,String accountName,int rowId) {
		String response = "{\"Remove\":[{ \"response\":false}]}";
		///for improving security[28120201126]:@{
		if(!Account.accountIsExists(accountId, accountName)){
			return response;
		}
		///for improving security[28120201126]:@}
		String delete_sql = "DELETE FROM tblDontDisturbDevices WHERE Id='" + rowId + "'";
		Database database = new Database();
		try {
			database.connect();
			int s = database.execSQL(delete_sql);
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
