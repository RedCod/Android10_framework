package com.service.sub;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONObject;

import com.common.ActionType;
import com.common.Log;
import com.database.Database;
import com.mqtt.Mqtt;

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
    Topic VARCHAR(50),
    ItemSort INT,
    PRIMARY KEY (Id)
);
	 */
	private static String TAG = "Scenario";
	public Scenario() {}
	
	/**
	 * Add scenario.
	 * @param accountId			//wich account
	 * @param accountName 		//accountName
	 * @param familyId			//which family
	 * @param scenarioName		//scenario name
	 * @param coverImage		//cover image index value as intex
	 * @param showOnMainPage	//show on main page. 1,or 0
	 * @param itemSort			//sorting number in scenario list.//listede kaçıncı sırada yer aldığı.
	 * @return					//return ScenarioId (tblScenario->Id) as JSON if adding is successful else return false.Because we will use this value for adding scenario sub process to tblScenarioSub.
	 */
	public synchronized String addScenario(int accountId,String accountName,int familyId,String scenarioName,int coverImage,int showOnMainPage,int itemSort) {
		//synchronized :Tek tek eriş:tblScenario'ya eklenen son kayıta ait Id değeri alın ve tblScenarioSub'de kullanılır.
		String response = "{\"AddScenario\":[{ \"response\":false}]}";
		///for improving security[29120200937]:@{
		if(!Account.accountIsExists(accountId, accountName)){
			return response;
		}
		///for improving security[29120200937]:@}
		String insert_sql = "INSERT INTO tblScenario(AccountId,FamilyId,Name,CoverImage,ShowOnMainPage,ItemSort)"
				+"VALUES('" + accountId +"','" + familyId +"','" + scenarioName +"','" + coverImage +"','" + showOnMainPage+"','" + itemSort +"')";
		Database database = new Database();
		try {
			database.connect();
			int s = database.execSQL(insert_sql);
			if(s > 0) {
				String query_sql = "SELECT MAX(Id) FROM tblScenario";//get Id value related last Insert
				String max_id = database.query(query_sql);
				response = "{\"AddScenario\":[{ \"response\":"+ max_id +"}]}";//burdaki değer "AddScenarioAction"da ScenarioId değeri olarak kullanılacak.
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
	 * @param accountName		//account
	 * @param scenarioId		//which scenario.this value get "tblScenario->Id"
	 * @param actionType		//determines the type of action. This value should be:"device","automation","time_lapse",...
	 * @param deviceId		    //which device if added device.
	 * @param macAddress         //device mac address.  produce topic for mqtt: maccaddres
	 * @param deviceSwitch		//device switch state:ON/OFF if added device.
	 * @param automationId		//automation Id value. if added automation.
	 * @param timeLapseValue	//time lapse value. if added TimeLapse
	 * @param topic			//topic to run if added device. "/hesapbilgisi/cihazmacadres_veya_cihazvirtualadres/" 
	 * @param itemSort			//Item sort value. Sort number in the action list.
	 * @return					//return true(as JSON) if action add to tblScenarioSub table.
	 */
	public String addScenarioAction(int accountId,
									String accountName,
			                        int scenarioId,
			                        String actionType,
			                        int deviceId,
			                        String macAddress,
			                        String deviceSwitch,
			                        int automationId,
			                        String timeLapseValue,
			                        int itemSort) {
		String response = "{\"AddScenarioAction\":[{ \"response\":false}]}";
		///for improving security[29120200937]:@{
		if(!Account.accountIsExists(accountId, accountName)){
			return response;
		}
		///for improving security[29120200937]:@}
		String produce_topic = "/"+macAddress+"/"; //exp: "/maccaddress/"
		String insert_sql = "INSERT INTO tblScenarioSub(AccountId,ScenarioId,ActionType,DeviceId,DeviceSwitch,AutomationId,TimeLapseValue,Topic,ItemSort)"
				+"VALUES('" +accountId+"','"+scenarioId+"','"+actionType+"','"+deviceId+"','"+deviceSwitch+"','"+automationId+"','"+timeLapseValue+"','"+ produce_topic +"','"+itemSort+"')";
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
	 * @param accountId 	//which account. for improving security
	 * @param accountName 	//account(email_or_phone). for improving security
	 * @param scenarioRowId	 //which scenario.
	 * @param scenarioName	 //scenario name.
	 * @param coverImage	 //cover image index value as int.
	 * @param showOnMainPage //show on main page. 1 or 0.
	 * @return				 //return true(as JSON) if edit scenario successful.
	 */
	public String editScenario(int accountId,String accountName,int scenarioRowId,String scenarioName,int coverImage,int showOnMainPage) {
		String response = "{\"EditScenario\":[{ \"response\":false}]}";
		///for improving security[29120200937]:@{
		if(!Account.accountIsExists(accountId, accountName)){
			return response;
		}
		///for improving security[29120200937]:@}
		String update_sql = "UPDATE tblScenario SET Name='"+scenarioName+"',CoverImage='"+coverImage+"',ShowOnMainPage='" + showOnMainPage +"' WHERE Id='" + scenarioRowId +"'";
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
	 * @param accountId 	//which account
	 * @param accountName 	//account
	 * @param scenarioRowId //which scenario
	 * @param itemSort	 //new ItemSort value
	 * @return			//return true(as JSON) if edit scenario ItemSort successful.
	 */
	public String editScenarioItemSort(int accountId,String accountName,int scenarioRowId,int itemSort) {
		String response = "{\"EditScenarioItemSort\":[{ \"response\":false}]}";
		///for improving security[29120200937]:@{
		if(!Account.accountIsExists(accountId, accountName)){
			return response;
		}
		///for improving security[29120200937]:@}
		String sql = "UPDATE tblScenario SET ItemSort='" + itemSort +"' WHERE Id='" + scenarioRowId +"'";
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
	 * @param accountId 		//which account
	 * @param accountName		//account
	 * @param actionId			//which action
	 * @param macAddress        //device mac  address. for produce mqtt topic: maccaddres
	 * @param deviceSwitch		//device switch state:ON/OFF if device is editing.
	 * @param automationId      //automation Id value. if automation is editing.
	 * @param timeLapseValue 	//time lapse value. if TimeLapse is editing.
	 * @param itemSort			//Item Sort value. Get sort number in the action list.
	 * @return					//return true(as JSON) if edit scenario action successful.
	 */
	public String editScenarioAction(int accountId,
									 String accountName,
			                         int actionId,
			                         String macAddress,
			                         String deviceSwitch,
			                         int automationId,
			                         String timeLapseValue,
			                         int itemSort) {
		String response = "{\"EditScenarioAction\":[{ \"response\":false}]}";
		///for improving security[29120200937]:@{
		if(!Account.accountIsExists(accountId, accountName)){
			return response;
		}
		///for improving security[29120200937]:@}
		String produce_topic= "/"+macAddress+"/"; //exp: "/macaddress/"
		String sql = "UPDATE tblScenarioSub SET DeviceSwitch='"+deviceSwitch+"',AutomationId='"+automationId+"',TimeLapseValue='"+timeLapseValue+"',Topic='"+produce_topic +"',ItemSort='"+itemSort+"' WHERE Id='"+actionId+"'";
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
	 * @param accountName //account
	 * @param familyId	//which family
	 * @return			//return all scenario list as JSON.
	 */
	public String getAllScenarioAsList(int accountId,String accountName,int familyId) {
		///for improving security[29120200937]:@{
		if(!Account.accountIsExists(accountId, accountName)){
			return "{\"AllScenarioList\":[ ]}";
		}
		///for improving security[29120200937]:@}
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
	 * @param accountId	//which account
	 * @param accountName//account
	 * @param scenarioId //which scenario
	 * @return			 //return scenario actions list as JSON.
	 */
	public String getScenarioActionsAsList(int accountId,String accountName,int scenarioId) {
		//just one Item content. So Scenario actions content.
		///for improving security[29120200937]:@{
		if(!Account.accountIsExists(accountId, accountName)){
			return "{\"AllScenarioActionsAsList\":[ ]}";
		}
		///for improving security[29120200937]:@}
		String query_sql = "SELECT *FROM tblScenarioSub WHERE ScenarioId='" + scenarioId + "'";
		Database database = new Database();
		String content = "{\"AllScenarioActionsAsList\":[\n";
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
	                content += "\"Topic\":\""+resultSet.getString(9) + "\",\n";
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
	}//getScenarioActions()
	
	/**
	 * Remove Scenario. Delete all related actions when scenario is deleted. (tblScenario,tblScenarioSub)
	 * @param accountId		//which account
	 * @param accountName	//account
	 * @param scenarioRowId //which scenario.
	 * @return			//return true(as JSON) if remove scenario successful.
	 */
	public String removeScenario(int accountId,String accountName,int scenarioRowId) {
		String response = "{\"RemoveScenario\":[{ \"response\":false}]}";
		///for improving security[29120200937]:@{
		if(!Account.accountIsExists(accountId, accountName)){
			return response;
		}
		///for improving security[29120200937]:@}
		String delete_sql = "DELETE FROM tblScenario WHERE Id='" + scenarioRowId +"'";
		Database database = new Database();
		try {
			database.connect();
			int s = database.execSQL(delete_sql);
			if(s > 0) {
				response = "{\"RemoveScenario\":[{ \"response\":true}]}";
				//then delete related all actions if actions is inside the tblScenarioSub
				delete_sql = "DELETE FROM tblScenarioSub WHERE ScenarioId='" + scenarioRowId +"'";
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
	 * @param accountId		//which account
	 * @param accountName	//account
	 * @param actionRowId	//which action
	 * @return			//return true(as JSON ) if remove action successful.
	 */
	public String removeScenarioAction(int accountId,String accountName,int actionRowId) {
		String response = "{\"RemoveScenarioAction\":[{ \"response\":false}]}";
		///for improving security[29120200937]:@{
		if(!Account.accountIsExists(accountId, accountName)){
			return response;
		}
		///for improving security[29120200937]:@}
		String sql = "DELETE FROM tblScenarioSub WHERE Id='" + actionRowId +"'";
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
	 * @param _accountId		//which account
	 * @param _accountName	//account
	 * @param scenarioId //which scenario
	 * @return 			 //return Scenario Actions List as JSON. (senaryoda yer alan işlem itemleri liste olarak return et)
	 */
	public String performScenario(int _accountId,String _accountName,int scenarioId) {
		String response = "{\"PerformScenrio\":[ ]}";
		///for improving security[29120200937]:@{
		if(!Account.accountIsExists(_accountId, _accountName)){
			return response;
		}
		///for improving security[29120200937]:@}
		/*
		 * TODO: Senaryoyu gerçekleştir.
		 * -İlgili senaryo içeriğini liste olarak çek.
		 * -Eğer senaryo içinde Otomasyon var ise otomasyonu çek. 
		 * -Mqtt motorunu harekete geçir:
		 *  |_Senaryo içeriği
		 *  |_Otomasyon içeriği
		 */
		String getScenarioActionsAsJSON = getScenarioActionsAsList(_accountId,_accountName,scenarioId);//get as JSON.
    	try {
    		Mqtt mqtt = new Mqtt();
            JSONObject jsonObj = new JSONObject(getScenarioActionsAsJSON);
            JSONArray jsonArr = jsonObj.getJSONArray("AllScenarioActionsAsList");
            for (int i = 0; i < jsonArr.length(); i++) {
                int id                = jsonArr.getJSONObject(i).getInt("Id");
                int accountId         = jsonArr.getJSONObject(i).getInt("AccountId");
                int scenarioid        = jsonArr.getJSONObject(i).getInt("ScenarioId");
                String actionType     = jsonArr.getJSONObject(i).getString("ActionType");
                int deviceId          = jsonArr.getJSONObject(i).getInt("DeviceId");
                String deviceSwitch   = jsonArr.getJSONObject(i).getString("DeviceSwitch");
                int automationId      = jsonArr.getJSONObject(i).getInt("AutomationId");
                String timeLapseValue = jsonArr.getJSONObject(i).getString("TimeLapseValue");
                String topic          = jsonArr.getJSONObject(i).getString("Topic");
                int itemSort          = jsonArr.getJSONObject(i).getInt("ItemSort");
                
                System.out.println("Id:" + id);
                System.out.println("AccountId:" + accountId);
                System.out.println("ScenarioId:" + scenarioid);
                System.out.println("ActionType:" + actionType);
                System.out.println("DeviceId:" + deviceId);
                System.out.println("DeviceSwitch:" + deviceSwitch);//use as command
                System.out.println("AutomationId:" + automationId);
                System.out.println("TimeLapseValue:" + timeLapseValue);
                System.out.println("Topic:" + topic);//use as mqtt topic
                System.out.println("ItemSort:" + itemSort);
                System.out.println();
                
                if(actionType.equals(ActionType.DEVICE)) {
                	//TODO: action is device:
                	//..
                	mqtt.publisher(topic,deviceSwitch);
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
                	 * senaryo içinde otomasyon varsa  performAutomation(id) şeklinde yeni bir thread ile gönder.
                	 * Yeni Thread: çünkü otomasyonun bitmesini beklemiyeceğiz,senaryoyu işletmeye devam edeceğiz.
                	 * Yani otomasyon sadece tetikle ve yoluna devam et...
                	 */
                	//otomasyonu tetikle: FIXME:
                	if(Log.DEBUG)
                		Log.println(TAG, "perform automation start");
                	 Thread thread = new Thread("new_AutomationThread") {
                	      public void run(){
                	    	  Automation automaiton = new Automation();
                	    	  automaiton.performAutomation(_accountId,_accountName,automationId); 
                	      }
                	   };
                	   thread.start();
                	if(Log.DEBUG)
                		Log.println(TAG, "perform automation end-");
                }else {
                	//nothing...
                }
            }//for
            mqtt.clear();//disconnect mqtt.
            response = getScenarioActionsAsJSON;//çalıştırılan senaryoları kullanıcıya göstermemiz gerekiyor.Bu nedenle JSON'u şutla.
        	}catch(Exception ex) {
        		System.out.println("ex:" + ex.getMessage());
        	}
		//..
		//...
		return response;
	}//performScenario()
}
