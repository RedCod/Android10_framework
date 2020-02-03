package com.service.sub;

import java.sql.SQLException;

import com.database.Database;

public class Settings {
	/**
	 * Device Settings(Cihaz ayarları) işlemleri. Kaydet,Sil,bilgileri düzenle. etc.
	 * used by:
	 * 		-WS_Settings.java
	 */
	/*
	 18:tblSettings
	CREATE TABLE IF NOT EXISTS tblSettings (
	    Id INT AUTO_INCREMENT,
	    AccountId INT,
	    DontDisturbDevices INT,
	    PushNotification INT,
	    PRIMARY KEY (Id)
	);
	 */
	private static String TAG = "Settings";
	public Settings() {}
	
	
	/**
	 * Add "Settings(for mobil app side)" when creating a new account.
	 * @param accountId			 //which account
	 * @param accountName		 //account
	 * @param dontDisturbDevices //for don't disturb devices. 1=Aktif,0=Pasif
	 * @param pushNotification	 //state value for "Push Notification". 1=Aktif,0=Pasif
	 * @return					 //return true(as JSON) if successful
	 */
	public String addSettings(int accountId,String accountName,int dontDisturbDevices,int pushNotification) {
		String response = "{\"AddSettings\":[{ \"response\":false}]}";
		///for improving security[29120200937]:@{
		if(!Account.accountIsExists(accountId, accountName)){
			return response;
		}
		///for improving security[29120200937]:@}
		String insert_sql = "INSERT INTO tblSettings(AccountId,DontDisturbDevices,PushNotification)"
				+ "VALUES('"+accountId+"','"+dontDisturbDevices+"','"+pushNotification +"')";
		Database database = new Database();
		try {
			database.connect();
			int s = database.execSQL(insert_sql);
			if(s > 0)
				response = "{\"AddSettings\":[{ \"response\":true}]}";
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
	}//addSettings()
	
	
	/**
	 * Edit "Don't disturb" devices setting value.
	 * @param accountId		//which account
	 * @param accountName	//account
	 * @param stateValue	//state value for "Don't Disturb Devices". 1=Aktif,0=Pasif
	 * @return				//return true(as JSON) if successful
	 */
	public String editDontDisturbDevices(int accountId,String accountName,int stateValue) {
		String response = "{\"EditDontDisturbDevices\":[{ \"response\":false}]}";
		///for improving security[29120200937]:@{
		if(!Account.accountIsExists(accountId, accountName)){
			return response;
		}
		///for improving security[29120200937]:@}
		String update_sql = "UPDATE tblSettings SET DontDisturbDevices='" + stateValue +"' WHERE AccountId='" + accountId +"'";
		Database database = new Database();
		try {
			database.connect();
			int s = database.execSQL(update_sql);
			if(s > 0)
				response = "{\"EditDontDisturbDevices\":[{ \"response\":true}]}";
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
	}//editDontDisturbDevices()
	
	/**
	 * Edit "Push Notification" setting value for the user to receive or not receive notification.
	 * @param accountId	 //which account
	 * @param accountName//account
	 * @param stateValue //state value. 1=Aktif,0=Pasif
	 * @return			 //return true(as JSON) if successful.
	 */
	public String editPushNotification(int accountId,String accountName,int stateValue) {
		String response = "{\"EditPushNotification\":[{ \"response\":false}]}";
		///for improving security[29120200937]:@{
		if(!Account.accountIsExists(accountId, accountName)){
			return response;
		}
		///for improving security[29120200937]:@}
		String update_sql = "UPDATE tblSettings SET PushNotification='" + stateValue +"' WHERE AccountId='" + accountId +"'";
		Database database = new Database();
		try {
			database.connect();
			int s = database.execSQL(update_sql);
			if(s > 0)
				response = "{\"EditPushNotification\":[{ \"response\":true}]}";
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
	}//editPushNotification()
	
	/**
	 * Remove account settings. Remove settings when account is deleted.
	 * @param accountId	//which account
	 * @param accountName //account
	 * @return			//return true(as JSON) if successful
	 */
	public String removeSettings(int accountId,String accountName) {
		String response = "{\"RemoveSettings\":[{ \"response\":false}]}";
		///for improving security[29120200937]:@{
		if(!Account.accountIsExists(accountId, accountName)){
			return response;
		}
		///for improving security[29120200937]:@}
		String delete_sql = "DELETE FROM tblSettings WHERE AccountId='" + accountId +"'";
		Database database = new Database();
		try {
			database.connect();
			int s = database.execSQL(delete_sql);
			if(s > 0)
				response = "{\"RemoveSettings\":[{ \"response\":true}]}";
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
	}//removeSettings()
}
