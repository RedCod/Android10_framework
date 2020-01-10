package com.service.sub;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.common.Log;
import com.database.Database;

public class Scenario {
	/**
	 * Scenario(Senaryo) işlemleri. Kaydet,Sil,Düzenle,etc.
	 * used by:
	 * 		-WS_Scenario.java
	 */
	/*
	 CREATE TABLE IF NOT EXISTS tblScenario (
    Id INT AUTO_INCREMENT,
    AccountId INT,
    FamilyId INT,
    Name VARCHAR(150),
    CoverImage INT,
    ShowOnMainPage INT,
    ItemSort INT,
    PRIMARY KEY (Id)
);
CREATE TABLE IF NOT EXISTS tblScenarioSub (
    Id INT AUTO_INCREMENT,
    AccountId INT,
    ScenarioId INT,
    ActionType VARCHAR(25),
    DeviceId INT,
    DeviceSwitch VARCHAR(10),
    AutomationId INT,
    TimeLapseValue VARCHAR(15),
    ItemSort INT,
    PRIMARY KEY (Id)
);
	 */
	private static String TAG = "Scenario";
	public Scenario() {}
	
	/**
	 * Add scenario.
	 * @param accountId			//wich account
	 * @param familyId			//which family
	 * @param scenarioName		//scenario name
	 * @param coverImage		//cover image index value as intex
	 * @param showOnMainPage	//show on main page. 1,or 0
	 * @param itemSort			//sorting number in scenario list.//listede kaçıncı sırada yer aldığı.
	 * @return					//return ScenarioId (tblScenario->Id) as JSON if adding is successful else return false.Because we will use this value for adding scenario sub process to tblScenarioSub.
	 */
	public synchronized String addScenario(int accountId,int familyId,String scenarioName,int coverImage,int showOnMainPage,int itemSort) {
		//synchronized :Tek tek eriş:tblScenario'ya eklenen son kayıta ait Id değeri alın ve tblScenarioSub'de kullanılır.
		String response = "{\"AddScenario\":[{ \"response\":false}]}";
		String insert_sql = "INSERT INTO tblScenario(AccountId,FamilyId,Name,CoverImage,ShowOnMainPage,ItemSort)"
				+"VALUES('" + accountId +"','" + familyId +"','" + scenarioName +"','" + coverImage +"','" + showOnMainPage+"','" + itemSort +"')";
		Database database = new Database();
		try {
			database.connect();
			int s = database.execSQL(insert_sql);
			if(s > 0) {
				String query_sql = "SELECT MAX(Id) FROM tblScenario";//get Id value related last Insert
				String max_id = database.query(query_sql);
				response = "{\"AddScenario\":[{ \"response\":"+ max_id +"}]}";
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
	}//addScenario()
	
	/**
	 * Add scenario action to "tblScenarioSub" table.
	 * @param accountId			//which account.
	 * @param scenarioId		//which scenario.this value get "tblScenario->Id"
	 * @param actionType		//determines the type of action. This value should be:"device","automation","time_lapse",...
	 * @param deviceId		    //which device if added device.
	 * @param deviceSwitch		//device switch state:ON/OFF if added device.
	 * @param automationId		//automation Id value. if added automation.
	 * @param timeLapseValue	//time lapse value. if added TimeLapse
	 * @param itemSort			//Item sort value. Sort number in the action list.
	 * @return					//return true(as JSON) if action add to tblScenarioSub table.
	 */
	public String addScenarioAction(int accountId,
			                        int scenarioId,
			                        String actionType,
			                        int deviceId,
			                        String deviceSwitch,
			                        int automationId,
			                        String timeLapseValue,
			                        int itemSort) {
		String response = "{\"AddScenarioAction\":[{ \"response\":false}]}";
		String insert_sql = "INSERT INTO tblScenarioSub(AccountId,ScenarioId,ActionType,DeviceId,DeviceSwitch,AutomationId,TimeLapseValue,ItemSort)"
				+"VALUES('" +accountId+"','"+scenarioId+"','"+actionType+"','"+deviceId+"','"+deviceSwitch+"','"+automationId+"','"+timeLapseValue+"','"+itemSort+"')";
		Database database = new Database();
		try {
			database.connect();
			int s = database.execSQL(insert_sql);
			if(s > 0)
				response = "{\"AddScenarioAction\":[{ \"response\":true}]}";
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
	}//addScenarioAction
	
	/**
	 * Edit scenario.
	 * @param scenarioId	 //which scenario.
	 * @param scenarioName	 //scenario name.
	 * @param coverImage	 //cover image index value as int.
	 * @param showOnMainPage //show on main page. 1 or 0.
	 * @return				 //return true(as JSON) if edit scenario successful.
	 */
	public String editScenario(int scenarioId,String scenarioName,int coverImage,int showOnMainPage) {
		String response = "{\"EditScenario\":[{ \"response\":false}]}";
		String update_sql = "UPDATE tblScenario SET Name='"+scenarioName+"',CoverImage='"+coverImage+"',ShowOnMainPage='" + showOnMainPage +"' WHERE Id='" + scenarioId +"'";
		Database database = new Database();
		try {
			database.connect();
			int s = database.execSQL(update_sql);
			if(s > 0)
				response = "{\"EditScenario\":[{ \"response\":true}]}";
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
	}//editScenario
	
	/**
	 * Edit scenario Item Sort.
	 * @param scenarioId //which scenario
	 * @param itemSort	 //new ItemSort value
	 * @return			//return true(as JSON) if edit scenario ItemSort successful.
	 */
	public String editScenarioItemSort(int scenarioId,int itemSort) {
		String response = "{\"EditScenarioItemSort\":[{ \"response\":false}]}";
		String sql = "UPDATE tblScenario SET ItemSort='" + itemSort +"' WHERE Id='" + scenarioId +"'";
		Database database = new Database();
		try {
			database.connect();
			int s = database.execSQL(sql);
			if(s > 0)
				response = "{\"EditScenarioItemSort\":[{ \"response\":true}]}";
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
	}//editScenarioItemSort
	
	/**
	 * Edit Scenario Action.
	 * @param actionId			//which action
	 * @param actionType		//determines the type of action. this value should be:"device","automation","time_lapse",...
	 * @param deviceId			//device id
	 * @param deviceSwitch		//device switch state:ON/OFF if device is editing.
	 * @param automationId      //automation Id value. if automation is editing.
	 * @param timeLapseValue 	//time lapse value. if TimeLapse is editing.
	 * @param itemSort			//Item Sort value. Get sort number in the action list.
	 * @return					//return true(as JSON) if edit scenario action successful.
	 */
	public String editScenarioAction(int actionId,
			                         String actionType,
			                         int deviceId,
			                         String deviceSwitch,
			                         int automationId,
			                         String timeLapseValue,
			                         int itemSort) {
		String response = "{\"EditScenarioAction\":[{ \"response\":false}]}";
		String sql = "UPDATE tblScenarioSub SET ActionType='" + actionType+"',DeviceId='"+deviceId+"',DeviceSwitch='"+deviceSwitch+"',AutomationId='"+automationId+"',TimeLapseValue='"+timeLapseValue+"',ItemSort='"+itemSort+"' WHERE Id='"+actionId+"'";
		Database database = new Database();
		try {
			database.connect();
			int s = database.execSQL(sql);
			if(s > 0)
				response = "{\"EditScenarioAction\":[{ \"response\":true}]}";
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
	}//editScenarioAction()
	
	/**
	 * Get all scenario as Items.
	 * @param accountId	//which account
	 * @param familyId	//which family
	 * @return			//return all scenario list as JSON.
	 */
	public String getAllScenarioAsList(int accountId,int familyId) {
		String query_sql = "SELECT *FROM tblScenario WHERE AccountId='" + accountId +"' AND FamilyId='" + familyId +"'";
		Database database = new Database();
		String content = "{\"AllScenarioList\":[\n";
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
	                content += "\"FamilyId\":"+resultSet.getInt(3) + ",\n";
	                content += "\"Name\":\""+resultSet.getString(4) + "\",\n";
	                content += "\"CoverImage\":"+resultSet.getInt(5) + ",\n";
	                content += "\"ShowOnMainPage\":"+resultSet.getInt(6) + ",\n";
	                content += "\"ItemSort\":"+resultSet.getInt(7) + "";
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
	}//getAllScenarioAsList()
	
	/**
	 * Get scenario all actions Items. (Senaryoya ait tüm işlemleri liste olarak getir)
	 * @param scenarioId //which scenario
	 * @return			 //return scenario actions list as JSON.
	 */
	public String getScenarioActions(int scenarioId) {
		//just one Item content. So Scenario actions content.
		String query_sql = "SELECT *FROM tblScenarioSub WHERE ScenarioId='" + scenarioId + "'";
		Database database = new Database();
		String content = "{\"AllScenarioActionsList\":[\n";
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
	                content += "\"ScenarioId\":"+resultSet.getInt(3) + ",\n";
	                content += "\"ActionType\":\""+resultSet.getString(4) + "\",\n";
	                content += "\"DeviceId\":"+resultSet.getInt(5) + ",\n";
	                content += "\"DeviceSwitch\":\""+resultSet.getString(6) + "\",\n";
	                content += "\"AutomationId\":"+resultSet.getInt(7) + ",\n";
	                content += "\"TimeLapseValue\":\""+resultSet.getString(8) + "\",\n";
	                content += "\"ItemSort\":"+resultSet.getInt(9) + "";
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
	}//getScenarioActions()
	
	/**
	 * Remove Scenario. Delete all related actions when scenario is deleted. (tblScenario,tblScenarioSub)
	 * @param scenarioId //which scenario.
	 * @return			//return true(as JSON) if remove scenario successful.
	 */
	public String removeScenario(int scenarioId) {
		String response = "{\"RemoveScenario\":[{ \"response\":false}]}";
		String delete_sql = "DELETE FROM tblScenario WHERE Id='" + scenarioId +"'";
		Database database = new Database();
		try {
			database.connect();
			int s = database.execSQL(delete_sql);
			if(s > 0) {
				response = "{\"RemoveScenario\":[{ \"response\":true}]}";
				//then delete related all actions if actions is inside the tblScenarioSub
				delete_sql = "DELETE FROM tblScenarioSub WHERE ScenarioId='" + scenarioId +"'";
				s = database.execSQL(delete_sql);//
				if(Log.DEBUG)
					Log.println(TAG, "removeScenario s2:" + s);
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
	}//removeScenario()
	
	/**
	 * Remove scenario action item. (delete just one action in tblScenarioSub)
	 * @param actionId	//which action
	 * @return			//return true(as JSON ) if remove action successful.
	 */
	public String removeScenarioAction(int actionId) {
		String response = "{\"RemoveScenarioAction\":[{ \"response\":false}]}";
		String sql = "DELETE FROM tblScenarioSub WHERE Id='" + actionId +"'";
		Database database = new Database();
		try {
			database.connect();
			int s = database.execSQL(sql);
			if(s > 0)
				response = "{\"RemoveScenarioAction\":[{ \"response\":true}]}";
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
	}//removeScenarioAction()
	
	/**
	 * Perform scenario. Senaryoyu gerçekleştir-işlet. (Bu süreç Server yazılımı tarafından "mqtt işlet motoru" olarak oluşturulmuş modül tarafından işletilecek.)
	 * @param scenarioId //which scenario
	 * @return 			 //return Scenario Actions List as JSON. (senaryoda yer alan işlem itemleri liste olarak return et)
	 */
	public String performScenario(int scenarioId) {
		String response = "{\"PerformScenrio\":[{ \"response\":false}]}";
		/*
		 * TODO: Senaryoyu gerçekleştir.
		 * -İlgili senaryo içeriğini liste olarak çek.
		 * -Eğer senaryo içinde Otomasyon var ise otomasyonu çek. 
		 * -Mqtt motorunu harekete geçir:
		 *  |_Senaryo içeriği
		 *  |_Otomasyon içeriği
		 */
		//..
		//...
		response = "{\"PerformScenrio\":[{ \"response\":true}]}";
		//...
		return response;
	}//performScenario()
}
