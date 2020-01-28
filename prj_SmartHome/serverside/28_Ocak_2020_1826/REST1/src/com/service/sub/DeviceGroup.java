package com.service.sub;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.common.Log;
import com.database.Database;

public class DeviceGroup {
	/**
	 * Device group (Cihaz gruplamak) işlemleri. Kaydet,Sil,bilgileri düzenle. etc.
	 * used by:
	 * 		-WS_DeviceGroup.java
	 */
	/*
3:tblDeviceGroup
CREATE TABLE IF NOT EXISTS tblDeviceGroup (
    Id INT AUTO_INCREMENT,
    AccountId INT,
    FamilyId INT,
    GroupName VARCHAR(100),
    GroupLocation VARCHAR(100),
    PRIMARY KEY (Id)
);

4:tblDeviceGroupSub
CREATE TABLE IF NOT EXISTS tblDeviceGroupSub (
    Id INT AUTO_INCREMENT,
    AccountId INT,
    GroupId INT,
    DeviceId INT,
    VirtualId VARCHAR(100),
    PRIMARY KEY (Id)
);

	 */
	private static String TAG = "DeviceGroup";
	public DeviceGroup() {}
	
	/**
	 * Add device group.
	 * @param accountId      //which account
	 * @param accountName	//account(email_or_phone).for improving security.
	 * @param familyId	    //which family
	 * @param groupName	    //group name
	 * @param groupLocation //group location. exp:Mutfak
	 * @return			    //return id value of added group row. (Eklenen kayda ait id değerini döndür.
	 */
	public synchronized String addGroup(int accountId,
										String accountName,
										int familyId,
										String groupName,
										String groupLocation) {
		 //Tek tek eriş:tblDeviceGroup'e eklenen son kayıta ait Id değeri alını ve tblDeviceGroupSub'de kullanılır.
		String response = "{\"AddGroup\":[{ \"response\":false}]}";
		///for improving security[28120201126]:@{
		if(!Account.accountIsExists(accountId, accountName)){
			return response;
		}
		///for improving security[28120201126]:@}
		String insert_sql = "INSERT INTO tblDeviceGroup(AccountId,FamilyId,GroupName,GroupLocation)"
				+ "VALUES('" + accountId+"','" + familyId+"','"+groupName+"','"+groupLocation+"')";
		Database database = new Database();
		try {
			database.connect();
			int s = database.execSQL(insert_sql);
			if(s > 0) {
				//select max Id:
				String select_sql = "SELECT MAX(Id) FROM tblDeviceGroup";
				String max_id = database.query(select_sql);
				response = "{\"AddGroup\":[{ \"response\": " + max_id +" }]}";
			}
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
	}//addGroup()
	
	/**
	 * Add device to device group(to tblDeviceGroupSub).  Use this method for "add" and "edit". (Hem cihaz eklemek için hem de güncellemek için kullanılacak.)
	 * @param accountId		  //which account
	 * @param accountName	  //account
	 * @param groupId		  //related which group
	 * @param deviceId		  //device Id
	 * @param deviceVirtualId //device virtual address. macaddress+deviceid	
	 * @return				  //return true(as JSON) if add is successful.
	 */
	public String addDevice(int accountId,
			 				String accountName,
						    int groupId,
						    int deviceId,
						    String deviceVirtualId) {
		String response = "{\"AddDevice\":[{ \"response\":false}]}";
		///for improving security[28120201126]:@{
		if(!Account.accountIsExists(accountId, accountName)){
			return response;
		}
		///for improving security[28120201126]:@}
		String insert_sql = "INSERT INTO tblDeviceGroupSub(AccountId,GroupId,DeviceId,VirtualId)"
				+ "VALUES('"+accountId+"','"+groupId+"','"+deviceId+"','"+deviceVirtualId+"')";
		Database database = new Database();
		try {
			database.connect();
			int s = database.execSQL(insert_sql);
			if(s > 0)
				response = "{\"AddDevice\":[{ \"response\":true}]}";
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
	}//addDevice()
	
	/**
	 * Edit group name
	 * @param accountId	 //which account.improving for security
	 * @param accountName //account(email_or_phone).improving for security
	 * @param rowId	// related group row Id.
	 * @param groupName //group name
	 * @return			//return true(as JSON) if successful
	 */
	public String editGroupName(int accountId,String accountName,int rowId,String groupName) {
		String response = "{\"EditGroupName\":[{ \"response\":false}]}";
		///for improving security[28120201126]:@{
		if(!Account.accountIsExists(accountId, accountName)){
			return response;
		}
		///for improving security[28120201126]:@}
		String update_sql = "UPDATE tblDeviceGroup SET GroupName='" + groupName +"' WHERE Id='" + rowId +"'";
		Database database = new Database();
		try {
			database.connect();
			int s = database.execSQL(update_sql);
			if(s > 0)
				response = "{\"EditGroupName\":[{ \"response\":true}]}";
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
	}//editGroupName()
	
	/**
	 * Edit group location.
	 * @param accountId 	//whihc account
	 * @param accountName 	//account
	 * @param rowId			//related group row Id.
	 * @param groupLocation //group location value. exp:Mutfak
	 * @return				//return true(as JSON) if successful
	 */
	public String editGroupLocation(int accountId,String accountName,int rowId,String groupLocation) {
		String response = "{\"EditGroupLocation\":[{ \"response\":false}]}";
		///for improving security[28120201126]:@{
		if(!Account.accountIsExists(accountId, accountName)){
			return response;
		}
		///for improving security[28120201126]:@}
		String update_sql = "UPDATE tblDeviceGroup SET GroupLocation='" + groupLocation +"' WHERE Id='" + rowId +"'";
		Database database = new Database();
		try {
			database.connect();
			int s = database.execSQL(update_sql);
			if(s > 0)
				response = "{\"EditGroupLocation\":[{ \"response\":true}]}";
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
	}//editGroupLocation()
	
	
	/**
	 * Get one group Item.(one group can accomodate a lot of device)
	 * @param accountId  //which account
	 * @param accountName //account
	 * @param groupId	//which group
	 * @return			//return group Item as JSON.
	 */
	public String getGroupDevicesAsList(int accountId,String accountName,int groupId){
		///for improving security[28120201126]:@{
		if(!Account.accountIsExists(accountId, accountName)){
			return "{\"DevicesList\":[ ]}";
		}
		///for improving security[28120201126]:@}
		String query_sql = "SELECT *FROM tblDeviceGroupSub WHERE GroupId='" + groupId +"'";
		Database database = new Database();
		String content = "{\"DevicesList\":[\n";
		try {
			database.connect();
			ResultSet resultSet  = database.queryForResultSet(query_sql);
			int count = 0;
            while (resultSet.next()) {
	           	 if(count == 0)
	        		 content +="{\n";
	        	 else
	        		 content +=",\n{\n";
                content += "\"Id\":" + resultSet.getInt(1) + ",\n";
                content += "\"AccountId\":" + resultSet.getInt(2) + ",\n";
                content += "\"GroupId\":" + resultSet.getInt(3) + ",\n";
                content += "\"DeviceId\":" + resultSet.getInt(4) + ",\n";
                content += "\"DeviceVirtualId\":\"" + resultSet.getString(5) + "\"";
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
	}//getItem()
	
	/**
	 * Get all devicegroup as list according to the account and family.
	 * @param accountId	//which account
	 * @param accountName 	//account(email_or_phone). improving for security
	 * @param familyId		//which family
	 * @return			   //return group list as JSON
	 */
	public String getAllGroupsAsList(int accountId,String accountName,int familyId) {
		///for improving security[28120201126]:@{
		if(!Account.accountIsExists(accountId, accountName)){
			return "{\"GroupsList\":[ ]}";
		}
		///for improving security[28120201126]:@}
		String query_sql = "SELECT *FROM tblDeviceGroup WHERE AccountId='" + accountId +"' AND FamilyId='"+ familyId+"'";
		Database database = new Database();
		String content = "{\"GroupsList\":[\n";
		try {
			database.connect();
			ResultSet resultSet  = database.queryForResultSet(query_sql);
			int count = 0;
            while (resultSet.next()) {
	           	 if(count == 0)
	        		 content +="{\n";
	        	 else
	        		content +=",\n{\n";
	                content += "\"Id\":" + resultSet.getInt(1) + ",\n";
	                content += "\"AccountId\":" + resultSet.getInt(2) + ",\n";
	                content += "\"FamilyId\":" + resultSet.getInt(3) + ",\n";
	                content += "\"GroupName\":\"" + resultSet.getString(4) + "\",\n";
	                content += "\"GroupLocation\":\"" + resultSet.getString(5) + "\"";
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
	 * Remove device int the Device Group.
	 * @param accountId	//which account
	 * @param accountName //account
	 * @param rowId //related device row Id.
	 * @return		//return true(as JSON) if successful.
	 */
	public String removeDevice(int accountId,String accountName,int rowId) {
		/*!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		 *!DİKKAT!:Burda item Id olarak tblDeviceGroupSub Id değerini kullanıyoruz. 
		 * 		   Ayrıca bu iş için DeviceId de kullanılabilir.Şayet DeviceId kullanılırsa AccountId de birlikte kullanılmalıdır.
	     *         Aksi halde başka accountlara ait veri kaybı da yaşanacaktır.
		 */
		String response = "{\"RemoveDevice\":[{ \"response\":false}]}";
		///for improving security[28120201126]:@{
		if(!Account.accountIsExists(accountId, accountName)){
			return response;
		}
		///for improving security[28120201126]:@}
		String update_sql = "DELETE FROM tblDeviceGroupSub WHERE Id='" + rowId +"'";
		Database database = new Database();
		try {
			database.connect();
			int s = database.execSQL(update_sql);
			if(s > 0)
				response = "{\"RemoveDevice\":[{ \"response\":true}]}";
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
	}//removeDevice()
	
	/**
	 * Remove device in the Device Group
	 * @param accountId //which account. for improving security
	 * @param accountName //account. for improving security
	 * @param rowId	//which item
	 * @return		//return true(as JSON) if successful.
	 */
	public String removeGroup(int accountId,String accountName,int rowId) {
		String response = "{\"RemoveGroup\":[{ \"response\":false}]}";
		///for improving security[28120201126]:@{
		if(!Account.accountIsExists(accountId, accountName)){
			return response;
		}
		///for improving security[28120201126]:@}
		String update_sql = "DELETE FROM tblDeviceGroup WHERE Id='" +rowId+"'";
		Database database = new Database();
		try {
			database.connect();
			int s = database.execSQL(update_sql);
			if(s > 0) {
				response = "{\"RemoveGroup\":[{ \"response\":true}]}";
				//delete all device in the Sub table related these group:
				String delete_sql = "DELETE FROM tblDeviceGroupSub WHERE GroupId='" + rowId +"'";
				s = database.execSQL(delete_sql);
				if(Log.DEBUG)
					Log.println(TAG, "delete in tblDeviceGroupSub state:" + s);
			}
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
	}//removeGroup()
	
	
}
