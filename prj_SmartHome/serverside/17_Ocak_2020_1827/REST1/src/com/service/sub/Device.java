package com.service.sub;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.ws.rs.PathParam;

import com.common.Log;
import com.database.Database;

public class Device {
	/**
	 * Device(Cihaz) işlemleri. Kaydet,Sil,bilgileri düzenle. etc.
	 * used by:
	 * 		-WS_Device.java
	 */
	/*DEVİCE TYPE:
	 *  public static String LAMP = "lamp";//for lamp
       public static String WALL_PLUG = "wall_plug";//wall_plug
	 * 
	 */
	private static String TAG = "Device";
	public Device() {
		
	}
	
	/*
CREATE TABLE IF NOT EXISTS tblDevice (
    Id INT AUTO_INCREMENT,
    AccountId INT,
    FamilyId INT,
    DeviceLocation INT,
    DeviceName VARCHAR(100),
    VirtualId VARCHAR(100),
    IpAddress VARCHAR(100),
    MacAddress VARCHAR(50),
    DeviceType VARCHAR(50),
    DeviceTimeZone VARCHAR(50),
    ItemSort INT,
    PRIMARY KEY (Id)
);
	 */

	/**
	 * Cihaz ekle.
	 * @param accountId			//Accout Id. this value get from tlbAccount->Id
	 * @param familyId			//Family Id. Get from tblFamily->Id
	 * @param roomId			//Room id. So device location. Get from tblRoom->Id
	 * @param deviceName		//Device name.
	 * @param virtualAddress	//consist of AccountId + MacAddress	
	 * @param ipAddress			//device ip address
	 * @param macAddress		//device mac address
	 * @param deviceType		//device type. exp: "lamp","wall_plug"
	 * @param deviceTimeZone	//device time zone. Exp: Europa/Istanbul	
	 * @return					//return true(as JSON) if device added successful.
	 */
	public String addDevice(int accountId,
							int familyId,
							int roomId,
							String deviceName,
							String virtualAddress,
							String ipAddress,
							String macAddress,
							String deviceType,
							String deviceTimeZone) {
		String response = "{\"AddDevice\":[{ \"response\":false}]}";
		Database database = new Database();
		try {
			String insert_sql = "INSERT INTO tblDevice(AccountId,FamilyId,DeviceLocation,DeviceName,VirtualId,IpAddress,Macaddress,DeviceType,DeviceTimeZone,ItemSort)"
			+" VALUES('"+ accountId +"','" + familyId + "','" + roomId +"','" + deviceName+"','" +  virtualAddress + "','"+ ipAddress +"','" + macAddress + "','" + deviceType + "','"+ deviceTimeZone + "',1)";
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
	}//addDevice
	
	/**
	 * Cihaz ismi değiştir.
	 * @param deviceId		//Id value of the device to be edit.
	 * @param deviceName	//new device name. 
	 * @return				//return(as JSON) true if device name update successful.
	 */
	public String editName(int deviceId,String deviceName) {
		String response = "{\"EditName\":[{ \"response\":false}]}";
		Database database = new Database();
		try {
			String edit_sql = "UPDATE tblDevice SET DeviceName='" + deviceName + "' WHERE Id='" + deviceId + "'";
			database.connect();
			int s = database.execSQL(edit_sql);
			if(s > 0)
				response = "{\"EditName\":[{ \"response\":true}]}";
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
	}//editName
	
	/**  //>OK<
	 * Cihazın ait olduğu ev bölümünü(örn: Mutfak,Oturma Odası) değiştir.
	 * @param deviceId			//Id value of the device to be edit.
	 * @param deviceLocation	//tblRoom->Id assigned here. New location value. exp: Mutfak
	 * @return					//return(as JSON) true if device location update successful. 
	 */
	public String editLocation(int deviceId,int deviceLocation) {
		String response = "{\"EditLocation\":[{ \"response\":false}]}";
		String edit_sql = "UPDATE tblDevice SET DeviceLocation='" + deviceLocation + "' WHERE Id='" + deviceId + "'";
		Database database = new Database();
		try {
			database.connect();
			int s = database.execSQL(edit_sql);
			if(s > 0)
				response = "{\"EditLocation\":[{ \"response\":true}]}";
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
	}//editLocation()
	
	/**
	 * Edit device sort.(Cihaz sıralamasını ayarla/değiştir.)
	 * @param deviceId	//which device.
	 * @param sortValue //sorting value.
	 * @return			//return true(as JSON) if device sorting update successful.
	 */
	public String editDeviceSort(int deviceId,int sortValue) {
		//String response = "{\"EditDeviceSort\":[{ \"response\":\"false\"}]}";
		String response = "{\"EditDeviceSort\":[{ \"response\":false}]}";
		String edit_sql = "UPDATE tblDevice SET ItemSort='" + sortValue + "' WHERE Id='" + deviceId + "'";
		Database database = new Database();
		try {
			database.connect();
			int s = database.execSQL(edit_sql);
			if(s > 0)
				response = "{\"EditDeviceSort\":[{ \"response\":true}]}";
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
	}//editDeviceSort()

	/**
	 * Get all device as list where accountId.
	 * @param accountId	//which account?
	 * @return			//return all item list as JSON.
	 */
	public String getAllAsList(int accountId,int familyId) {
		String query_sql = "SELECT *FROM tblDevice WHERE AccountId='" + accountId +"' AND FamilyId='"+ familyId+"'";
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
	                content += "\"Id\":" + resultSet.getInt(1) + ",\n";
	                content += "\"AccountId\":" + resultSet.getInt(2) + ",\n";
	                content += "\"FamilyId\":" + resultSet.getInt(3) + ",\n";
	                content += "\"DeviceLocation\":" + resultSet.getInt(4) + ",\n";
	                content += "\"DeviceName\":\"" + resultSet.getString(5) + "\",\n";
	                content += "\"VirtualId\":\"" + resultSet.getString(6) + "\",\n";
	                content += "\"IpAddress\":\"" + resultSet.getString(7) + "\",\n";
	                content += "\"MacAddress\":\"" + resultSet.getString(8) + "\",\n";
	                content += "\"DeviceType\":\"" + resultSet.getString(9) + "\",\n";
	                content += "\"DeviceTimeZone\":\"" + resultSet.getString(10) + "\",\n";
	                content += "\"ItemSort\":"+resultSet.getInt(11) + "";
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
	 * Get device item as JSON.
	 * @param id 	//which device?
	 * @return		//return Item as JSON.
	 */
	public String getItem(int deviceId) {
		String query_sql = "SELECT *FROM tblDevice WHERE Id='" + deviceId + "'";
		Database database = new Database();
		String content = "{\"Item\":[\n";
		try {
			database.connect();
			ResultSet resultSet  = database.queryForResultSet(query_sql);
            while (resultSet.next()) {
                content +="{\n";
                //content += "\"Id\":\""+resultSet.getInt(1) + "\",\n";
                content += "\"Id\":" + resultSet.getInt(1) + ",\n";
                content += "\"AccountId\":" + resultSet.getInt(2) + ",\n";
                content += "\"FamilyId\":" + resultSet.getInt(3) + ",\n";
                content += "\"DeviceLocation\":" + resultSet.getInt(4) + ",\n";
                content += "\"DeviceName\":\"" + resultSet.getString(5) + "\",\n";
                content += "\"VirtualId\":\"" + resultSet.getString(6) + "\",\n";
                content += "\"IpAddress\":\"" + resultSet.getString(7) + "\",\n";
                content += "\"MacAddress\":\"" + resultSet.getString(8) + "\",\n";
                content += "\"DeviceType\":\"" + resultSet.getString(9) + "\",\n";
                content += "\"DeviceTimeZone\":\"" + resultSet.getString(10) + "\",\n";
                content += "\"ItemSort\":"+resultSet.getInt(11) + "";
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
	
	
	
	/**  //>OK<
	 * Cihazı sil/kaldır.
	 * @param deviceId  //Id value of the device to be deleted.
	 * @return //return(as JSON) true if device delete successful. 
	 */
	public String removeDevice(int deviceId) {
		/*TODO:
		 * -Cihaz silinirken cihazla ilgili diğer tablolarda yer alan işlemler de silinmelidir.
		 * Örn: "Cihazları Rahatsız Etmeyin","Senaryolar","Otonom","Zamanlama(Schedule)" etc. içinde yer alan ilgili kayıtlar da silinmelidir.
		 * 
		 * Cihazı Aşağıdaki tablolarda arattır ve sil:
		 * -tblDevice
		 * -tblDeviceGroupSub
		 * -tblDontDisturbDevicesDevice
		 * -tblSchedule
		 * -tblScenarioSub
		 * -tblAutomationOperations
		 * -tblAutomationCondition
		 * 
		 */
		String response = "{\"RemoveDevice\":[{ \"response\":false}]}";
		Database database = new Database();
		try {
			database.connect();
			//1:
			String delete_sql = "DELETE FROM tblDevice WHERE Id='" + deviceId + "'";//tblDevice
			int s = database.execSQL(delete_sql);//delete in tblDevice if device exists.
			if(Log.DEBUG)
				Log.println(TAG,"delete tlbDevice state:" + s);
			/*//deleting in "for" loop.
			 *!!!!!!!!!! REMOVE !!!!!!!!!!!!
			 * //2:
			delete_sql = "DELETE FROM tblDontDisturbDevicesDevice WHERE DeviceId='" + deviceId + "'";//tblDontDisturbDevicesDevice
			s = database.execSQL(delete_sql);//delete in tblDontDisturbDevicesDevice if device exists.
			if(Log.DEBUG)
				Log.println(TAG,"delete tblDontDisturbDevicesDevice state:" + s);
			//3:
			delete_sql = "DELETE FROM tblSchedule WHERE DeviceId='" + deviceId + "'";//tblSchedule
			s = database.execSQL(delete_sql);//delete in tblSchedule if device exists.
			if(Log.DEBUG)
				Log.println(TAG,"delete tblSchedule state:" + s);
			//4:
			delete_sql = "DELETE FROM tblScenarioSub WHERE DeviceId='" + deviceId + "'";//tblScenarioSub
			s = database.execSQL(delete_sql);//delete in tblScenarioSub if device exists.
			if(Log.DEBUG)
				Log.println(TAG,"delete tblScenarioSub state:" + s);
			//5:
			delete_sql = "DELETE FROM tblAutomationOperations WHERE DeviceId='" + deviceId + "'";//tblAutomationOperations
			s = database.execSQL(delete_sql);//delete in tblAutomationOperations if device exists.
			if(Log.DEBUG)
				Log.println(TAG,"delete tblAutomationOperations state:" + s);
			//6:
			delete_sql = "DELETE FROM tblAutomationCondition WHERE DeviceId='" + deviceId + "'";//tblAutomationCondition
			s = database.execSQL(delete_sql);//delete in tblAutomationCondition if exists.
			if(Log.DEBUG)
				Log.println(TAG,"delete tblAutomationCondition state:" + s);
			*/
			if(s > 0) {
				response = "{\"RemoveDevice\":[{ \"response\":true}]}";
				//cihaz silindiğinde,cihazın muhtemel kullanıldığı her yerde siliver:
				String[] tables = {"tblDeviceGroupSub","tblDontDisturbDevicesDevice","tblSchedule","tblScenarioSub","tblAutomationOperations","tblAutomationCondition"};
				for(int i=0;i<tables.length;i++) {
					delete_sql = "DELETE FROM " + tables[i] +" WHERE DeviceId='" + deviceId +"'";
					s = database.execSQL(delete_sql);
					if(Log.DEBUG)
						Log.println(TAG, "deleting in:" + tables[i] + " table...");
				}//for
			}
		} catch (ClassNotFoundException e) {
			//response = "CNFERROR";
			e.printStackTrace();
		} catch (SQLException e) {
			//response = "SQLERROR";
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
	

	

}
