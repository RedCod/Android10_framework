package com.service.sub;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.database.Database;

public class Schedule {
	/**
	 * Schedule(Zamanlama-Programlama) işlemleri. Kaydet,Sil,Düzenle,etc.
	 * used by:
	 * 		-WS_Schedule.java
	 */
	
	/*
CREATE TABLE IF NOT EXISTS tblSchedule (
    Id INT AUTO_INCREMENT,
    AccountId INT,
    DeviceId INT,
    ScheduleTime TIME,
    Switch VARCHAR(10),
    IsActive INT DEFAULT 1,
    Monday INT,
    Tuesday INT,
    Wednesday INT,
    Thursday INT,
    Friday INT,
    Saturday INT,
    Sunday INT,
    OnlyOnce INT,
    PRIMARY KEY (Id)
);
	 */
	private static String TAG ="Schedule";
	public Schedule() {}
	
	/**
	 * Add schedule for Device ON-OFF process.
	 * @param accountId		//which account.
	 * @param accountName	//account(email_or_phone).for improving security
	 * @param deviceId		//which device.	
	 * @param scheduleTime	//schedule time.when will it work.
	 * @param swtch			//Operation-process ON/OFF
	 * @param monday		//work day. 1 or 0
	 * @param tuesday		//work day. 1 or 0
	 * @param wednesday		//work day. 1 or 0
	 * @param thursday		//work day. 1 or 0
	 * @param friday		//work day. 1 or 0
	 * @param saturday		//work day. 1 or 0
	 * @param sunday		//work day. 1 or 0
	 * @param onlyOnce		//work day. 1 or 0
	 * @return				//return true(as JSON) if adding is successful.
	 */
	public String addSchedule(int accountId,
			 				  String accountName,
							  int deviceId,
							  String scheduleTime,
							  String swtch,
							  int monday,
							  int tuesday,
							  int wednesday,
							  int thursday,
							  int friday,
							  int saturday,
							  int sunday,
							  int onlyOnce) {
		String response = "{\"AddSchedule\":[{ \"response\":false}]}";
		///for improving security[29120200937]:@{
		if(!Account.accountIsExists(accountId, accountName)){
			return response;
		}
		///for improving security[29120200937]:@}
		String insert_sql = "INSERT INTO tblSchedule(AccountId,DeviceId,ScheduleTime,Switch,Monday,Tuesday,Wednesday,Thursday,Friday,Saturday,Sunday,OnlyOnce) "
				+ "VALUES('" + accountId +"','" + deviceId + "','" + scheduleTime +"','" + swtch + "','" + monday + "','" + tuesday + "','" + wednesday + "','" + thursday + "','" + friday + "','" + saturday + "','" + sunday + "','" + onlyOnce + "')";
		Database database = new Database();
		try {
			database.connect();
			int s = database.execSQL(insert_sql);
			if(s > 0)
				response = "{\"AddSchedule\":[{ \"response\":true}]}";
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
	}//addSchedule()
	
	/**
	 * Edit schedule Item.
	 * @param accountId		//which account
	 * @param accountName	//account
	 * @param scheduleRowId	//which schedule.
	 * @param scheduleTime	//schedule time. when will it work.
	 * @param swtch			//operation-process ON/OFF
	 * @param monday		//work day. 1 or 0
	 * @param tuesday		//work day. 1 or 0
	 * @param wednesday		//work day. 1 or 0
	 * @param thursday		//work day. 1 or 0
	 * @param friday		//work day. 1 or 0
	 * @param saturday		//work day. 1 or 0
	 * @param sunday		//work day. 1 or 0
	 * @param onlyOnce		//if no day is specified,only work once.(eğer gün belirtilmemişse,sadece bir kez çalış.)
	 * @return				//return true(as JSON) if edit is successful.
	 */
	public String editSchedule(int accountId,
							   String accountName,
			                   int scheduleRowId,
							   String scheduleTime,
							   String swtch,
							   int monday,
							   int tuesday,
							   int wednesday,
							   int thursday,
							   int friday,
							   int saturday,
							   int sunday,
							   int onlyOnce) {
		String response = "{\"EditSchedule\":[{ \"response\":false}]}";
		///for improving security[29120200937]:@{
		if(!Account.accountIsExists(accountId, accountName)){
			return response;
		}
		///for improving security[29120200937]:@}
		String update_sql = "UPDATE tblSchedule SET ScheduleTime='" + scheduleTime+"',Switch='" + swtch+"',Monday='" + monday +"',Tuesday='" + tuesday +"',Wednesday='" + wednesday +"',Thursday='" + thursday +"',Friday='" + friday +"',Saturday='" +saturday +"',Sunday='" + sunday +"',OnlyOnce='" + onlyOnce +"' WHERE Id='" + scheduleRowId +"'";
		Database database = new Database();
		try {
			database.connect();
			int s = database.execSQL(update_sql);
			if(s > 0)
				response = "{\"EditSchedule\":[{ \"response\":true}]}";
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
	}//editSchedule()
	
	/**
	 * Get one Schedule Item.
	 * @param accountId	//which account
	 * @param accountName	//account
	 * @param scheduleRowId //which schedule
	 * @return			//return schedule Item as JSON.
	 */
	public String getItem(int accountId,String accountName,int scheduleRowId) {
		///for improving security[29120200937]:@{
		if(!Account.accountIsExists(accountId, accountName)){
			return "{\"Item\":[ ]}";
		}
		///for improving security[29120200937]:@}
		String query_sql = "SELECT *FROM tblSchedule WHERE Id='" + scheduleRowId + "'";
		Database database = new Database();
		String content = "{\"Item\":[\n";
		try {
			database.connect();
			ResultSet resultSet  = database.queryForResultSet(query_sql);
            while (resultSet.next()) {
                content +="{\n";
                content += "\"Id\":"+resultSet.getInt(1) + ",\n";
                content += "\"AccountId\":"+resultSet.getInt(2) + ",\n";
                content += "\"DeviceId\":"+resultSet.getInt(3) + ",\n";
                content += "\"ScheduleTime\":\""+resultSet.getString(4) + "\",\n";
                content += "\"Switch\":\""+resultSet.getString(5) + "\",\n";
                content += "\"IsActive\":\""+resultSet.getInt(6) + "\",\n";
                content += "\"Monday\":\""+resultSet.getInt(7) + "\",\n";
                content += "\"Tuesday\":\""+resultSet.getInt(8) + "\",\n";
                content += "\"Wednesday\":\""+resultSet.getInt(9) + "\",\n";
                content += "\"Thursday\":\""+resultSet.getInt(10) + "\",\n";
                content += "\"Friday\":\""+resultSet.getInt(11) + "\",\n";
                content += "\"Saturday\":\""+resultSet.getInt(12) + "\",\n";
                content += "\"Sunday\":\""+resultSet.getInt(13) + "\",\n";
                content += "\"OnlyOnce\":\""+resultSet.getInt(14) + "\"";
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
	}
	
	/**
	 * Get schedule Items as list.
	 * @param accountId //which account
	 * @param accountName //account
	 * @param deviceId	//related which device?
	 * @return			//return schedule Items list as JSON.
	 */
	public String getAllAsList(int accountId,String accountName,int deviceId) {
		///for improving security[29120200937]:@{
		if(!Account.accountIsExists(accountId, accountName)){
			return "{\"AllList\":[ ]}";
		}
		///for improving security[29120200937]:@}
		String query_sql = "SELECT *FROM tblSchedule";
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
	                content += "\"DeviceId\":"+resultSet.getInt(3) + ",\n";
	                content += "\"ScheduleTime\":\""+resultSet.getString(4) + "\",\n";
	                content += "\"Switch\":\""+resultSet.getString(5) + "\",\n";
	                content += "\"IsActive\":\""+resultSet.getInt(6) + "\",\n";
	                content += "\"Monday\":\""+resultSet.getInt(7) + "\",\n";
	                content += "\"Tuesday\":\""+resultSet.getInt(8) + "\",\n";
	                content += "\"Wednesday\":\""+resultSet.getInt(9) + "\",\n";
	                content += "\"Thursday\":\""+resultSet.getInt(10) + "\",\n";
	                content += "\"Friday\":\""+resultSet.getInt(11) + "\",\n";
	                content += "\"Saturday\":\""+resultSet.getInt(12) + "\",\n";
	                content += "\"Sunday\":\""+resultSet.getInt(13) + "\",\n";
	                content += "\"OnlyOnce\":\""+resultSet.getInt(14) + "\"";
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
	 * Delete specified schedule Item.
	 * @param accountId 	//which account
	 * @param accountName	//account
	 * @param scheduleRowId //which device.
	 * @return			//return true(as JSON) 
	 */
	public String remove(int accountId,String accountName,int scheduleRowId) {
		String response = "{\"Remove\":[{ \"response\":false}]}";
		///for improving security[29120200937]:@{
		if(!Account.accountIsExists(accountId, accountName)){
			return response;
		}
		///for improving security[29120200937]:@}
		String sql = "DELETE FROM tblSchedule WHERE Id='" + scheduleRowId + "'";
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
