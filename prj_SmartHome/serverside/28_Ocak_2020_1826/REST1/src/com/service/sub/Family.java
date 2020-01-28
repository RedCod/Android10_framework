package com.service.sub;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.common.Log;
import com.database.Database;

public class Family {
	/**
	 * Family(Aile) işlemleri. Kaydet,Sil,Düzenle,etc.
	 * used by:
	 * 		-WS_Family.java
	 */
	private static String TAG = "Family";
	public Family() {
		//...
	}
	
	/*
CREATE TABLE IF NOT EXISTS tblFamily (
    Id INT AUTO_INCREMENT,
    AccountId INT,
    FamilyName VARCHAR(100),
    RoomsId VARCHAR(255),	
    FamilyLocation VARCHAR(100),
    PRIMARY KEY (Id)
);

CREATE TABLE IF NOT EXISTS tblFamilyMember (
    Id INT AUTO_INCREMENT,
    FamilyId INT,
    AccountId INT,
    AccountIsAdmin INT,
    PRIMARY KEY (Id)
);
	 */
	//String response = "{\"AddDevice\":[{ \"response\":\"false\"}]}";
	
	/**
	 * Add family.
	 * @param accountId  	  //which account.Ailenin ait olduğu Hesap.
	 * @param accountName	 //account(email_or_phone). for improving security
	 * @param familyName	  //Aile adı.
	 * @param roomsId		  //Aileye ait odalar.
	 * @param familyLocation //Ailenin haritadaki lokasyonu.
	 * @return				//return true(as JSON) if Family added successful.
	 */
	public synchronized String addFamily(int accountId,String accountName,String familyName,String roomsId,String familyLocation) {
		    //Tek tek eriş:tblFamily'e eklenen son kayıta ait Id değeri alını ve tblFamilyMember'de kullanılır.
		    String response = "{\"AddFamily\":[{ \"response\":false}]}";
			///for improving security[28120201126]:@{
			if(!Account.accountIsExists(accountId, accountName)){
				return response;
			}
			///for improving security[28120201126]:@}
			Database database = new Database();
			try {
				String insert_sql = "INSERT INTO tblFamily(AccountId,FamilyName,RoomsId,FamilyLocation)"
						+"VALUES('" +accountId +"','" + familyName + "','"+ roomsId + "','"+ familyLocation + "')";
				database.connect();
				int s = database.execSQL(insert_sql);
				if(Log.DEBUG)
					Log.println(TAG, "addFamily: insert into tblFamily state:" + s);
				if(s > 0) {
					response = "{\"AddFamily\":[{ \"response\":true}]}";
					/*
					 * insert to tblFamilyMember.
					 * Aile oluşturulurken aşağıdaki süreç izlenir:
					 * Kullanıcı bir aile ismi girer ve buradaki süreç tamamlanır.Ancak hemen akabinde buradaki "Id" değeri alınarak "tblFamilyMember" tablosuna kayıt girilir.
					 * Çünkü mevcut hesap,oluşturulan aileye üye edilmelidir.
					 * NOT: Ayrıca hesap ilk olarak oluşturulurken bir Aile oluşturulması istenir-zorunludur.
					 */
					insert_sql = "INSERT INTO tblFamilyMember(FamilyId,AccountId,AccountIsAdmin)"
							+"VALUE((SELECT MAX(Id) FROM tblFamily),'" + accountId + "','1')";
					s = database.execSQL(insert_sql);
					if(Log.DEBUG)
						Log.println(TAG, "addFamily: insert into tblFamilyMember state:" + s);
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
	}//addFamily()
	
	/**
	 * Edit family name.
	 * @param accountId		//which account
	 * @param accountName	//account
	 * @param rowId		//which family.
	 * @param familyName	//new family value.
	 * @return				//return true(as JSON) if Family name update successful.
	 */
	public String editName(int accountId,String accountName,int rowId,String familyName) {
		String response = "{\"EditName\":[{ \"response\":false}]}";
		///for improving security[28120201126]:@{
		if(!Account.accountIsExists(accountId, accountName)){
			return response;
		}
		///for improving security[28120201126]:@}
		Database database = new Database();
		try {
			String update_sql = "UPDATE tblFamily SET FamilyName='" + familyName + "' WHERE Id='" + rowId +"'";
			database.connect();
			int s = database.execSQL(update_sql);
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
	}//editName()
	
	/**
	 * Edit rooms of family-Aile odalarını(aileye ait) düzenle-ata.
	 * @param accountId //which account
	 * @param accountName //account
	 * @param rowId //which family.
	 * @param roomsIds //id values for rooms as string. exp: "1,2,4,5,8,10,11"
	 * @return		   //return true(as JSON) if family rooms update successful.
	 */
	public String editRooms(int accountId,String accountName,int rowId,String roomsIds) {
		String response = "{\"EditRooms\":[{ \"response\":false}]}";
		///for improving security[28120201126]:@{
		if(!Account.accountIsExists(accountId, accountName)){
			return response;
		}
		///for improving security[28120201126]:@}
		Database database = new Database();
		try {
			String update_sql = "UPDATE tblFamily SET RoomsId='" + roomsIds + "' WHERE Id='" + rowId + "'";
			database.connect();
			int s = database.execSQL(update_sql);
			if(s > 0)
				response = "{\"EditRooms\":[{ \"response\":true}]}";
			if(Log.DEBUG)
				Log.println(TAG, "editRooms: update tblFamily state:" + s);
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
	}//editRooms()
	
	/**
	 * Edit family geographic location.
	 * @param accountId 	//which account
	 * @param accountName 	//account
	 * @param rowId			//which family.
	 * @param familyLocation	//new location value.
	 * @return					//return true(as JSON) if family new location update successful.
	 */
	public String editLocation(int accountId,String accountName,int rowId,String familyLocation) {
		String response = "{\"EditLocation\":[{ \"response\":false}]}";
		///for improving security[28120201126]:@{
		if(!Account.accountIsExists(accountId, accountName)){
			return response;
		}
		///for improving security[28120201126]:@}
		Database database = new Database();
		try {
			String update_sql = "UPDATE tblFamily SET FamilyLocation='" + familyLocation + "' WHERE Id='" + rowId + "'";
			database.connect();
			int s = database.execSQL(update_sql);
			if(s > 0)
				response = "{\"EditLocation\":[{ \"response\":true}]}";
			if(Log.DEBUG)
				Log.println(TAG, "editLocation: update tblFamily state:" + s);
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
	 * Get all family Items as list.
	 * @param accountId	//which account?
	 * @param accountName //account
	 * @return			//return all family Items as JSON.
	 */
	public String getAllAsList(int accountId,String accountName) {
		///for improving security[28120201126]:@{
		if(!Account.accountIsExists(accountId, accountName)){
			return "{\"AllList\":[ ]}";
		}
		///for improving security[28120201126]:@}
		String query_sql = "SELECT *FROM tblFamily WHERE AccountId='" + accountId +"'";
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
	           	 	//content += "\"Id\":\""+resultSet.getInt(1) + "\",\n";//json string olarak.
	                content += "\"Id\":"+resultSet.getInt(1) + ",\n";//json int olarak.
	                content += "\"AccountId\":"+resultSet.getInt(2) + ",\n";
	                content += "\"FamilyName\":\""+resultSet.getString(3) + "\",\n";
	                content += "\"RoomsId\":\""+resultSet.getString(4) + "\",\n";
	                content += "\"FamilyLocation\":\""+resultSet.getString(5) + "\"";
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
	}
	
	/**
	 * Get one family info as Item.
	 * @param accountId //which account
	 * @param accountName //account
	 * @param rowId	//which family?
	 * @return		//return family item as JSON.
	 */
	public String getItem(int accountId,String accountName,int rowId) {
		///for improving security[28120201126]:@{
		if(!Account.accountIsExists(accountId, accountName)){
			return "{\"Item\":[ ]}";
		}
		///for improving security[28120201126]:@}
		String query_sql = "SELECT *FROM tblFamily WHERE Id='" + rowId + "'";
		Database database = new Database();
		String content = "{\"Item\":[\n";
		try {
			database.connect();
			ResultSet resultSet  = database.queryForResultSet(query_sql);
            while (resultSet.next()) {
                content +="{\n";
                content += "\"Id\":"+resultSet.getInt(1) + ",\n";//json int olarak.
                content += "\"AccountId\":"+resultSet.getInt(2) + ",\n";
                content += "\"FamilyName\":\""+resultSet.getString(3) + "\",\n";
                content += "\"RoomsId\":\""+resultSet.getString(4) + "\",\n";
                content += "\"FamilyLocation\":\""+resultSet.getString(5) + "\"";
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
	 * Remove family-Aile sil/kaldır.
	 * @param accountId //which account
	 * @param accountName //account
	 * @param rowId	//which family Id.
	 * @return			//return true(as JSON) if remove family successful.
	 */
	public String removeFamily(int accountId,String accountName,int rowId) {
		String response = "{\"RemoveFamily\":[{ \"response\":false}]}";
		///for improving security[28120201126]:@{
		if(!Account.accountIsExists(accountId, accountName)){
			return response;
		}
		///for improving security[28120201126]:@}
		Database database = new Database();
		try {
			String delete_sql = "DELETE FROM tblFamily WHERE Id='" + rowId + "'";
			database.connect();
			int s = database.execSQL(delete_sql);
			if(Log.DEBUG)
				Log.println(TAG, "removeFamily: delete from tblFamily state:" + s);
			if(s > 0) {
				response = "{\"RemoveFamily\":[{ \"response\":true}]}";
				String[] tables = {"tblFamilyMember","tblDevice"};
				for(int i=0;i<tables.length;i++) {
					//delete family member and devices related this family:
					delete_sql = "DELETE FROM " + tables[i] + " WHERE FamilyId='" + rowId +"'";
					if(Log.DEBUG)
						Log.println(TAG, "delete in:" + tables[i] + " table.");
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
	}//removeFamily()
}
