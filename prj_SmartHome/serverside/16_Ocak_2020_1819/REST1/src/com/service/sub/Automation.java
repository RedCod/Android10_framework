package com.service.sub;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONObject;

import com.common.ActionType;
import com.common.Log;
import com.database.Database;
import com.mqtt.Mqtt;

public class Automation {
	/**
	 * Automation(Otomasyon) işlemleri. Kaydet,Sil,bilgileri düzenle. etc.
	 * used by:
	 * 		-WS_Automation.java
	 */
	private static String TAG ="Automation";
	public Automation() {}
	/*
    13:tblAutomation
	 CREATE TABLE IF NOT EXISTS tblAutomation (
    Id INT AUTO_INCREMENT,
    AccountId INT,
    FamilyId INT,
    Name VARCHAR(150),
    CoverImage INT,
    AutomationCondition VARCHAR(10),
    ValidTimePeriod VARCHAR(50),
    CurrentCity	VARCHAR(50),
    IsActive INT DEFAULT 1,
    ItemSort INT,
    Monday INT,
    Tuesday INT,
    Wednesday INT,
    Thursday INT,
    Friday INT,
    Saturday INT,
    Sunday INT,
    PRIMARY KEY (Id)
);

14:tblAutomationOperations
CREATE TABLE IF NOT EXISTS tblAutomationOperations (
    Id INT AUTO_INCREMENT,
    AccountId INT,
    AutomationId INT,
    ActionType VARCHAR(25),
    DeviceId INT,
    DeviceSwitch VARCHAR(10),
    AutomationIdAssign INT,
    TimeLapseValue VARCHAR(15),
    Command VARCHAR(50),
    ItemSort INT,
    PRIMARY KEY (Id)
);

15:tblAutomationCondition
CREATE TABLE IF NOT EXISTS tblAutomationCondition (
    Id INT AUTO_INCREMENT,
    AccountId INT,
    AutomationId INT,
    ConditionType VARCHAR(15),
    ConditionValue VARCHAR(50),
    DeviceId INT,
    DeviceSwitch VARCHAR(10),
    CurrentCity VARCHAR(50),
    Monday INT DEFAULT -1,
    Tuesday INT DEFAULT -1,
    Wednesday INT DEFAULT -1,
    Thursday INT DEFAULT -1,
    Friday INT DEFAULT -1,
    Saturday INT DEFAULT -1,
    Sunday INT DEFAULT -1,
    PRIMARY KEY (Id)
);
	 
	 */
///ADD:@{
	/**
	 * Add automation
	 * @param accountId				//which account
	 * @param familyId				//wich family
	 * @param automationName		//automation name
	 * @param coverImage			//cover image index value as int.
	 * @param automationCondition	//"all condition" OR "any condition". Tüm koşullar karşılandığında VEYA Koşullardan herhangi biri karşılandığında.
	 * @param validTimePeriod		//TamGün=24 saat, Gün=gündoğumdan günbatımına kadar,Gece=günbatımından gündoğumuna kadar,Özelleştir=12:00-15:14
	 * @param currentCity			//current city
	 * @param itemSort				//sorting number in automation list. //otomasyonun listede kaçıncı sırada yer aldığı.
	 * @param monday				//day to repeat for Monday. 1 or 0
	 * @param tuesday				//day to repeat for Monday. 1 or 0
	 * @param wednesday				//day to repeat for Monday. 1 or 0
	 * @param thursday				//day to repeat for Monday. 1 or 0
	 * @param friday				//day to repeat for Monday. 1 or 0
	 * @param saturday				//day to repeat for Monday. 1 or 0
	 * @param sunday				//day to repeat for Monday. 1 or 0
	 * @return						//return AutomationId(tblAutomation->Id) as JSON if adding is successful.Else return false. Because we will use this value for adding automation "conditions" and "Action" process to other table related to tblAutomation.
	 */
	public synchronized String addAutomation(int accountId,
								int familyId,
								String automationName,
								int coverImage,
								String automationCondition,
								String  validTimePeriod,
								String currentCity,
								int itemSort,
								int monday,
								int tuesday,
								int wednesday,
								int thursday,
								int friday,
								int saturday,
								int sunday) {
		String response = "{\"AddAutomation\":[{ \"response\":false}]}";
		int active = 1;//otomasyonu aktif et.
		String insert_sql = "INSERT INTO tblAutomation(AccountId,FamilyId,Name,CoverImage,AutomationCondition,ValidTimePeriod,CurrentCity,IsActive,ItemSort,Monday,Tuesday,Wednesday,Thursday,Friday,Saturday,Sunday)"
				+ "VALUES('"+accountId+"','"+familyId+"','"+automationName+"','"+coverImage+"','"+automationCondition+"','"+validTimePeriod+"','"+currentCity+"','"+active+"','"+itemSort+"','"+monday+"','"+tuesday+"','"+wednesday+"','"+thursday+"','"+friday+"','"+saturday+"','"+sunday+"')";
		Database database = new Database();
		try {
			database.connect();
			int s = database.execSQL(insert_sql);
			if(s > 0) {
				//eklenen son id'i return yap. çünkü bu değeri diğer tablolarda kullanacağız.
				String query_sql = "SELECT MAX(Id) FROM tblAutomation";
				String max_id = database.query(query_sql);
				response = "{\"AddAutomation\":[{ \"response\":"+max_id+"}]}";
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
	}//addAutomation()
	
	/**
	 * Add automation condition to "tblAutomationCondition" table.
	 * @param accountId			//which account
	 * @param automationId		//which automation
	 * @param conditionType		//wich condition. "koşul tipi.örn:temperature,humidity,weather,air_quality,etc."
	 * @param conditionValue	//condition value. örn: bulutlu,güneşli,karlı,etc. Bu değer "sabit değer" olarak eklenecek.Bu şartın Mobil app tarafından karşılığı dile uygun gösterilecek.
	 * @param deviceId			//which device if added device.
	 * @param deviceSwitch		//device switch state:ON/OFF if added device. Use the device status as a condition.
	 * @param currentCity		//city where the condition is met.
	 * @param Monday	       //day to repeat for Monday. 1 or 0
	 * @param Tuesday		   //day to repeat for Tuesday. 1 or 0
	 * @param Wednesday		   //day to repeat for Wednesday. 1 or 0
	 * @param Thursday		   //day to repeat for Thursday. 1 or 0
	 * @param Friday		  //day to repeat for Friday. 1 or 0
	 * @param Saturday		  //day to repeat for Saturday. 1 or 0
	 * @param Sunday		  //day to repeat for Sunday. 1 or 0
	 * @return					//return true(as JSON) if condition add to tblAutomationCondition table.
	 */
	public String addCondition(int accountId,
							   int automationId,
							   String conditionType,
							   String conditionValue,
							   int deviceId,
							   String deviceSwitch,
							   String currentCity,
							   int monday,
							   int tuesday,
							   int wednesday,
							   int thursday,
							   int friday,
							   int saturday,
							   int sunday) {
		String response = "{\"AddCondition\":[{ \"response\":false}]}";
		String insert_sql = "INSERT INTO tblAutomationCondition(AccountId,AutomationId,ConditionType,ConditionValue,DeviceId,DeviceSwitch,CurrentCity,Monday,Tuesday,Wednesday,Thursday,Friday,Saturday,Sunday)"
				+ "VALUES('" + accountId+"','"+automationId+"','"+conditionType+"','"+conditionValue+"','"+deviceId+"','"+deviceSwitch+"','"+currentCity+"','"+monday+"','"+tuesday+"','" +wednesday+"','"+thursday+"','"+friday+"','"+saturday+"','"+sunday+"')";
		Database database = new Database();
		try {
			database.connect();
			int s = database.execSQL(insert_sql);
			if(s > 0)
				response = "{\"AddCondition\":[{ \"response\":true}]}";
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
	}//addCondition()
 
	/**
	 * Add automation action to "tblAutomationOperation" table.
	 * @param accountId				//which account
	 * @param automationId		    //which automation. this value get "tblAutomation->Id"
	 * @param actionType			//determines the type of action.this value should be:"device","automation","time_lapse",...
	 * @param deviceId				//which device if added device.
	 * @param deviceVirtualAddress	//device virtual address. produce: macaddress+deviceId
	 * @param deviceSwitch			//device switch state:ON/OFF  if added device.
	 * @param automationIdAssign	//automation id value. if added automation.(Action olarak bir otomasyon eklenirse)
	 * @param timeLapseValue		//time lapse value. iff added TimeLapse
	 * @param itemSort				//Item sort value. Sort number in the action list.
	 * @return						//return true(as JSON) if action add to "tblAutomationOperation" table.
	 */
	public String addAction(int accountId,
							int automationId,
							String actionType,
							int deviceId,
							String deviceVirtualAddress,
							String deviceSwitch,
							int automationIdAssign,
							String timeLapseValue,
							int itemSort) {
		String response = "{\"AddAction\":[{ \"response\":false}]}";
		String produce_command = "/"+deviceVirtualAddress+"/" +deviceSwitch; //exp: "/devicevirtualaddress/ON"
		String insert_sql = "INSERT INTO tblAutomationOperation(AccountId,AutomationId,ActionType,DeviceId,DeviceSwitch,AutomationIdAssign,TimeLapseValue,Command,ItemSort)"
				+ "VALUES('"+accountId+"','"+automationId+"','"+actionType+"','"+deviceId+"','"+deviceSwitch+"','"+automationIdAssign+"','"+timeLapseValue+"','"+produce_command+"','" +itemSort+"')";
		Database database = new Database();
		try {
			database.connect();
			int s = database.execSQL(insert_sql);
			if(s > 0)
				response = "{\"AddAction\":[{ \"response\":true}]}";
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
	}//addAction()
	
///ADD:@}	
//////////////////////////////////////////////////////////////////////////////////////////////////
///EDiT:@{
	/**
	 * Edit automation
	 * @param automationId			//which automation. so Id
	 * @param automationName		//automation name
	 * @param coverImage			//cover image index value as int.
	 * @param automationCondition	//automation condition. all OR any
	 * @param validTimePeriod		//valid time period(Geçerlilik zaman bölümü)
	 * @param currentCity			//current city
	 * @param monday				//day to repeat for Monday. 1 or 0
	 * @param tuesday				//day to repeat for Tuesday. 1 or 0
	 * @param wednesday				//day to repeat for Wednesday. 1 or 0
	 * @param thursday				//day to repeat for Thursday. 1 or 0
	 * @param friday				//day to repeat for Friday. 1 or 0
	 * @param saturday				//day to repeat for Saturday. 1 or 0
	 * @param sunday				//day to repeat for Sunday. 1 or 0
	 * @return						//return true(as JSON) if automation edit successful.
	 */
	public String editAutomation(int automationId,
								 String automationName,
								 int coverImage,
								 String automationCondition,
								 String validTimePeriod,
								 String currentCity,
								 int monday,
								 int tuesday,
								 int wednesday,
								 int thursday,
								 int friday,
								 int saturday,
								 int sunday) {
		String response = "{\"EditAutomation\":[{ \"response\":false}]}";
		String update_sql = "UPDATE tblAutomation SET Name='"+automationName+"',CoverImage='"+coverImage+"',AutomationCondition='"+automationCondition +"',"
				+ "ValidTimePeriod='"+validTimePeriod+"',CurrentCity='"+currentCity+"',Monday='"+monday+"',Tuesday='"+tuesday+"',Wednesday='"+wednesday+"',"
				+ "Thursday='"+thursday+"',Friday='"+friday+"',Saturday='"+saturday+"',Sunday='"+sunday+"' WHERE Id='"+automationId+"'";
		Database database = new Database();
		try {
			database.connect();
			int s = database.execSQL(update_sql);
			if(s > 0)
				response = "{\"EditAutomation\":[{ \"response\":true}]}";
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
	}//editAutomaiton()
	
	/**
	 * Activate automation. activate or deactivate.
	 * @param automationId	//which automation
	 * @param state			//state activate=1,OR deactivate = 0
	 * @return				//return true(as JSON) if successful
	 */
	public String activateAutomation(int automationId,int state) {
		String response = "{\"ActivateAutomation\":[{ \"response\":false}]}";
		String update_sql = "UPDATE tblAutomation SET IsActive='" + state + "' WHERE Id='" + automationId +"'";
		Database database = new Database();
		try {
			database.connect();
			int s = database.execSQL(update_sql);
			if(s > 0)
				response = "{\"ActivateAutomation\":[{ \"response\":true}]}";
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
	}//activateAutomation()
	
	/**
	 * Edit automation Item sort.
	 * @param automationId	//which automation
	 * @param itemSort		//new item sort value.
	 * @return				//return true(as JSON) if edit automation ItemSort successful.
	 */
	public String editAutomationItemSort(int automationId,int itemSort) {
		String response = "{\"EditAutomation\":[{ \"response\":false}]}";
		String update_sql = "UPDATE tblAutomation SET ItemSort='" + itemSort + "' WHERE Id='" + automationId +"'";
		Database database = new Database();
		try {
			database.connect();
			int s = database.execSQL(update_sql);
			if(s > 0)
				response = "{\"EditAutomation\":[{ \"response\":true}]}";
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
	}//editAutomationItemSort()
	
	/**
	 * Edit automation condition
	 * @param conditionId	 //which condition.so Id of tblAutomationCondition.	
	 * @param conditionType	 //determines the type of condition. "koşul tipi. örn:temperature,humidity,weather,air_quality,etc."
	 * @param conditionValue //condition value. örn: bulutlu,güneşli,karlı,etc. Bu değer "sabit değer" olarak eklenecek.Bu şartın Mobil app tarafından karşılığı dile uygun gösterilecek.
	 * @param deviceSwitch   //device switch state: ON/OFF if device is editing. (koşul olarak bir cihaz durumu eklenmişse)
	 * @param currentCity	//current city.
	 * @param monday		//day to repeat for Monday. 1 or 0
	 * @param tuesday		//day to repeat for Tuesday. 1 or 0
	 * @param wednesday		//day to repeat for Wednesday. 1 or 0
	 * @param thursday		//day to repeat for Thursday. 1 or 0
	 * @param friday		//day to repeat for Friday. 1 or 0
	 * @param saturday		//day to repeat for Saturday. 1 or 0
	 * @param sunday		//day to repeat for Sunday. 1 or 0
	 * @return				//return true(as JSON) if edit automation successful
	 */
	public String editCondition(int conditionId,
								String conditionType,
								String conditionValue,
								String deviceSwitch,
								String currentCity,
								int monday,
								int tuesday,
								int wednesday,
								int thursday,
								int friday,
								int saturday,
								int sunday) {
		String response = "{\"EditCondition\":[{ \"response\":false}]}";
		String update_sql = "UPDATE tblAutomationCondition SET ConditionType='" +conditionType+"',ConditionValue='"+conditionValue+"',DeviceSwitch='"+deviceSwitch+"',"
				+"CurrentCity='"+currentCity+"',Monday='"+monday+"',Tuesday='"+tuesday+"',Wednesday='"+wednesday+"',Thursday='"+thursday+"',Friday='"+friday+"',Saturday='"+saturday+"',Sunday='"+sunday+"' WHERE Id='" +conditionId+"'";
		Database database = new Database();
		try {
			database.connect();
			int s = database.execSQL(update_sql);
			if(s > 0)
				response = "{\"EditCondition\":[{ \"response\":true}]}";
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
	}//editCondition()
	
	/**
	 * Edit automation action to "tblAutomationOperation" table.
	 * @param actionId				//which action. so Id of tblAutomationOpertaion table.
	 * @param deviceVirtualAddress	//device virtual address. produce:macaddress+deviceId
	 * @param deviceSwitch			//device switch state:ON/OFF  if added device.
	 * @param automationIdAssign	//automation id value. if added automation.(Action olarak bir otomasyon eklenirse)
	 * @param timeLapseValue		//time lapse value. if added TimeLapse.
	 * @param itemSort				//Item sort value. Sort number in the action list.
	 * @return						//return true(as JSON) if action add to "tblAutomationOperation" table.
	 */
	public String editAction(int actionId,
			                 String deviceVirtualAddress,
			                 String deviceSwitch,
			                 int automationIdAssign,
			                 String timeLapseValue,
			                 int itemSort) {
		String response = "{\"EditAction\":[{ \"response\":false}]}";
		String produce_command = "/"+deviceVirtualAddress+"/" +deviceSwitch; //exp: "/devicevirtualaddress/ON"
		String update_sql = "UPDATE tblAutomaitonOperation SET DeviceSwitch='" +deviceSwitch+"',AutomationIdAssign='"+automationIdAssign+"',TimeLapseValue='"+timeLapseValue+"',Command='" + produce_command+"',ItemSort='"+itemSort+"' WHERE Id='"+actionId+"'";
		Database database = new Database();
		try {
			database.connect();
			int s = database.execSQL(update_sql);
			if(s > 0)
				response = "{\"EditAction\":[{ \"response\":true}]}";
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
	}//editAction()
	
///EDiT:@}	
//////////////////////////////////////////////////////////////////////////////////////////////////
///GET:@{
	/**
	 * Get all automation as Items.(tüm otomasyonları listele)
	 * @param accountId	//which account
	 * @param familyId	//wich family
	 * @return			//return all automation list as JSON.
	 */
	public String getAllAutomationsAsList(int accountId,int familyId) {
		String query_sql = "SELECT *FROM tblAutomation WHERE AccountId='" + accountId +"' AND FamilyId='" + familyId +"'";
		Database database = new Database();
		String content = "{\"AllAutomationList\":[\n";
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
	                content += "\"Name\":" + resultSet.getString(4) + ",\n";
	                content += "\"CoverImage\":" + resultSet.getInt(5) + ",\n";
	                content += "\"AutomationCondition\":" + resultSet.getString(6) + ",\n";
	                content += "\"ValidTimePeriod\":" + resultSet.getString(7) + ",\n";
	                content += "\"CurrentCity\":" + resultSet.getString(8) + ",\n";
	                content += "\"IsActive\":" + resultSet.getInt(9) + ",\n";
	                content += "\"ItemSort\":" + resultSet.getInt(10) + ",\n";
	                content += "\"Monday\":" + resultSet.getInt(11) + ",\n";
	                content += "\"Tuesday\":" + resultSet.getInt(12) + ",\n";
	                content += "\"Wednesday\":" + resultSet.getInt(13) + ",\n";
	                content += "\"Thursday\":" + resultSet.getInt(14) + ",\n";
	                content += "\"Friday\":" + resultSet.getInt(15) + ",\n";
	                content += "\"Saturday\":" + resultSet.getInt(16) + ",\n";
	                content += "\"Sunday\":"+resultSet.getInt(17) + "";
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
	}//getAllAutomationsAsList()
	
	/**
	 * Get all conditions Items.(otomasyona ait tüm "condition/koşulları" liste olarak getir)
	 * @param automationId	//which automation
	 * @return				//return automation condition list as JSON.
	 */
	public String getAllConditionsAsList(int automationId) {
		String query_sql = "SELECT *FROM tblAutomationCondition WHERE AutomationId='" + automationId +"'";
		Database database = new Database();
		String content = "{\"AllConditionsList\":[\n";
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
	                content += "\"AutomationId\":" + resultSet.getInt(3) + ",\n";
	                content += "\"ConditionType\":" + resultSet.getString(4) + ",\n";
	                content += "\"ConditionValue\":" + resultSet.getString(5) + ",\n";
	                content += "\"DeviceId\":" + resultSet.getInt(6) + ",\n";
	                content += "\"DeviceSwitch\":" + resultSet.getString(7) + ",\n";
	                content += "\"CurrentCity\":" + resultSet.getString(8) + ",\n";
	                content += "\"Monday\":" + resultSet.getInt(9) + ",\n";
	                content += "\"Tuesday\":" + resultSet.getInt(10) + ",\n";
	                content += "\"Wednesday\":" + resultSet.getInt(11) + ",\n";
	                content += "\"Thursday\":" + resultSet.getInt(12) + ",\n";
	                content += "\"Friday\":" + resultSet.getInt(13) + ",\n";
	                content += "\"Saturday\":" + resultSet.getInt(14) + ",\n";
	                content += "\"Sunday\":"+resultSet.getInt(15) + "";
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
	}//getAllConditionsAsList()
	
	/**
	 * Get all automation actions Items as list.(tüm otomasyon actionsları listele)
	 * @param automationId	//which automation
	 * @return				//return all actions items as JSON.
	 */
	public String getAllActionsAsList(int automationId) {
		String query_sql = "SELECT *FROM tblAutomationOperation WHERE AutomationId='" + automationId +"'";
		Database database = new Database();
		String content = "{\"AllActionsList\":[\n";
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
	                content += "\"AutomationId\":" + resultSet.getInt(3) + ",\n";
	                content += "\"ActionType\":" + resultSet.getString(4) + ",\n";
	                content += "\"DeviceId\":" + resultSet.getInt(5) + ",\n";
	                content += "\"DeviceSwitch\":" + resultSet.getString(6) + ",\n";
	                content += "\"AutomationIdAssign\":" + resultSet.getInt(7) + ",\n";
	                content += "\"TimeLapseValue\":" + resultSet.getInt(8) + ",\n";
	                content += "\"Command\":" + resultSet.getString(9) + ",\n";
	                content += "\"ItemSort\":"+resultSet.getInt(10) + "";
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
	}//getAllActionsAsList()
///GET:@}	
//////////////////////////////////////////////////////////////////////////////////////////////////
///REMOVE:@{
	/**
	 * Remove Automation. Delete all related conditions,conditionschedulerepeat,actions when automation is deleted.
	 * Related tables: tblAutomation,tblAutomationCondition,tblAutomationOperation
	 * @param automationId	//which automation
	 * @return				//return true(as JSON) if remove automation is successful.
	 */
	public String removeAutomation(int automationId) {
		String response = "{\"RemoveAutomation\":[{ \"response\":false}]}";
		String delete_sql = "DELETE FROM tblAutomation WHERE Id='" + automationId+"'";
		Database database = new Database();
		try {
			database.connect();
			int s = database.execSQL(delete_sql);
			if(s > 0) {
				response = "{\"RemoveAutomation\":[{ \"response\":true}]}";
				//delete everything other related to automation:
				String[] tables= {"tblAutomationCondition","tblAutomationOperation"};
				for(int i=0;i<tables.length;i++) {
					delete_sql = "DELETE FROM "+tables[i]+" WHERE AutomationId='" + automationId +"'";
					if(Log.DEBUG)
						Log.println(TAG, "removeAutomation-> delete in table:" + tables[i]);
				}//for
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
	}//removeAutomation()
	
	/**
	 * Remove condition in tblAutomationCondition
	 * @param conditionId	//which condition
	 * @return				//return true(as JSON) if remove condition is successful.
	 */
	public String removeCondition(int conditionId) {
		String response = "{\"RemoveCondition\":[{ \"response\":false}]}";
		String delete_sql = "DELETE FROM tblAutomationCondition WHERE Id='" + conditionId+"'";
		Database database = new Database();
		try {
			database.connect();
			int s = database.execSQL(delete_sql);
			if(s > 0)
				response = "{\"RemoveCondition\":[{ \"response\":true}]}";
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
	}//removeCondition()
	
	/**
	 * Remove automation action.
	 * @param actionId	//which action
	 * @return			//return true(as JSON) if remove action is successful
	 */
	public String removeAction(int actionId) {
		String response = "{\"RemoveAction\":[{ \"response\":false}]}";
		String delete_sql = "DELETE FROM tblAutomationOperation WHERE Id='" + actionId +"'";
		Database database = new Database();
		try {
			database.connect();
			int s = database.execSQL(delete_sql);
			if(s > 0)
				response = "{\"RemoveAction\":[{ \"response\":true}]}";
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
	}//removeAction()
	
	public String performAutomation(int _automationId) {
		String response = "{\"PerformAutomation\":[{ \"response\":false}]}";
		/*
		 * TODO: Senaryoyu gerçekleştir.
		 * -İlgili senaryo içeriğini liste olarak çek.
		 * -Eğer senaryo içinde Otomasyon var ise otomasyonu çek. 
		 * -Mqtt motorunu harekete geçir:
		 *  |_Senaryo içeriği
		 *  |_Otomasyon içeriği
		 */
		String getAutomationActionsAsJSON = getAllActionsAsList(_automationId);//get as JSON.
    	try {
            JSONObject jsonObj = new JSONObject(getAutomationActionsAsJSON);
            JSONArray jsonArr = jsonObj.getJSONArray("AllActionsList");
            for (int i = 0; i < jsonArr.length(); i++) {
                int id                = jsonArr.getJSONObject(i).getInt("Id");
                int accountId         = jsonArr.getJSONObject(i).getInt("AccountId");
                int automationId      = jsonArr.getJSONObject(i).getInt("AutomationId");
                String actionType     = jsonArr.getJSONObject(i).getString("ActionType");
                int deviceId          = jsonArr.getJSONObject(i).getInt("DeviceId");
                String deviceSwitch   = jsonArr.getJSONObject(i).getString("DeviceSwitch");
                int automationIdAssign= jsonArr.getJSONObject(i).getInt("AutomationIdAssign");
                String timeLapseValue = jsonArr.getJSONObject(i).getString("TimeLapseValue");
                String command        = jsonArr.getJSONObject(i).getString("Command");
                int itemSort          = jsonArr.getJSONObject(i).getInt("ItemSort");
                
                System.out.println("Id:" + id);
                System.out.println("AccountId:" + accountId);
                System.out.println("AutomationId:" + automationId);
                System.out.println("ActionType:" + actionType);
                System.out.println("DeviceId:" + deviceId);
                System.out.println("DeviceSwitch:" + deviceSwitch);
                System.out.println("AutomationIdAssign:" + automationIdAssign);
                System.out.println("TimeLapseValue:" + timeLapseValue);
                System.out.println("Command:" + command);
                System.out.println("ItemSort:" + itemSort);
                System.out.println();
                
                if(actionType.equals(ActionType.DEVICE)) {
                	//TODO: action is device:
                	//..
                	Mqtt mqtt = new Mqtt();
                	mqtt.executeCommand(command);
                	
                }else if(actionType.equals(ActionType.TIME_LAPSE)) {
                	/*
                	 * TODO:
                	 * -TimeLapseValue hem dakika hem saniye değerlerini birlikte tutar. Bunları ayrıştır ve milisaniye cinsine çevirerek Sleep'a uygula.
                	 * NOT:!!!!!!! şimdilik temsili değerle çalışıyor. !!!!!!!!!!!!!! 
                	 */
                    int dk = Integer.valueOf("2");//(timeLapseValue);
                    long millisecond = dk * 60 * 1000; //or: dk * 60000
                	Thread.sleep(millisecond);
                	
                }else if(actionType.equals(ActionType.SCENARIO)) {
                	//TODO: action is Scenario:
                	//V.2 de uygulanacak.
                }else if(actionType.equals(ActionType.AUTOMATION)) {
                	//TODO: action is Automation:
                	/*TODO: otomasyona bağla<
                	 * Otomasyon içinde otomasyon varsa  performAutomation(id) şeklinde yeni bir thread ile gönder.
                	 * Yeni Thread: çünkü yeni tetiklenen otomasyonun bitmesini beklemiyeceğiz,mevcut otomasyonu işletmeye devam edeceğiz.
                	 * Yani otomasyonu sadece tetikle ve yoluna devam et...
                	 */
                }else {
                	//nothing...
                }
                
            }//for
        	}catch(Exception ex) {
        		System.out.println("ex:" + ex.getMessage());
        	}
		//..
		//...
		response = "{\"PerformAutomation\":[{ \"response\":true}]}";
		//...
		return response;
	}//performAutomation()
	
	
///REMOVE:@}	
}
