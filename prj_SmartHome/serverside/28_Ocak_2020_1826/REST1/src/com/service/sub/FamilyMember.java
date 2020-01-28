package com.service.sub;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.common.Log;
import com.database.Database;

public class FamilyMember {
	/**
	 * FamilyMember(Aile Üyeleri) işlemleri. Kaydet,Sil,Düzenle,etc.
	 * used by:
	 * 		-WS_FamilyMember.java
	 */
	private static String TAG = "FamilyMember";
	public FamilyMember() {}

	/**
	 * Add family member if the member has an account.
	 * @param accountId		//which account.
	 * @param accountName 	//account(email_or_phone). for improving security
	 * @param familyId		//which family.
	 * @param accountIsAdmin//Account is admin=1,else 0.
	 * @return				//return true(as JSON) if adding is successfuly.
	 */
	public String addMember(int accountId,String accountName,int familyId,int accountIsAdmin) {
		String response = "{\"AddMember\":[{ \"response\":false}]}";
		///for improving security[28120201126]:@{
		if(!Account.accountIsExists(accountId, accountName)){
			return response;
		}
		///for improving security[28120201126]:@}
		//check member's account when adding:
		String insert_sql = "INSERT INTO tblFamilyMember(FamilyId,AccountId,AccountIsAdmin) SELECT '"+familyId+"','"+accountId+"','"+accountIsAdmin+"' WHERE EXISTS(SELECT Id FROM tblAccount WHERE Id='"+accountId+"')";
		Database database = new Database();
		try {
			database.connect();
			int s = database.execSQL(insert_sql);
			if(s > 0)
				response = "{\"AddMember\":[{ \"response\":true}]}";
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
	}//addMember()
	
	/**
	 * Edit family member. Edit just admin state.
	 * @param accountId 	//which account
	 * @param accountName	//account
	 * @param memberRowId		//which member.
	 * @param accountIsAdmin//Account admin state. Admin=1,else 0.
	 * @return				//return true(as JSON) if edit is successfuly.
	 */
	public String editMember(int accountId,String accountName,int memberRowId,int accountIsAdmin) {
		String response = "{\"EditMember\":[{ \"response\":false}]}";
		///for improving security[28120201126]:@{
		if(!Account.accountIsExists(accountId, accountName)){
			return response;
		}
		///for improving security[28120201126]:@}
		String insert_sql = "UPDATE tblFamilyMember SET AccountIsAdmin='" + accountIsAdmin + "' WHERE Id='" + memberRowId + "'";
		Database database = new Database();
		try {
			database.connect();
			int s = database.execSQL(insert_sql);
			if(s > 0)
				response = "{\"EditMember\":[{ \"response\":true}]}";
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
	}//editMember()
	
	/**
	 * Get family member as Item.
	 * @param accountId 	//which account
	 * @param accountName	//account
	 * @param memberRowId	//which member.
	 * @return			//return family member Item as JSON.
	 */
	public String getItem(int accountId,String accountName,int memberRowId) {
		///for improving security[28120201126]:@{
		if(!Account.accountIsExists(accountId, accountName)){
			return "{\"Item\":[ ]}";
		}
		///for improving security[28120201126]:@}
		String query_sql = "SELECT *FROM tblFamilyMember WHERE Id='" + memberRowId + "'";
		Database database = new Database();
		String content = "{\"Item\":[\n";
		try {
			database.connect();
			ResultSet resultSet  = database.queryForResultSet(query_sql);
            while (resultSet.next()) {
                content +="{\n";
                content += "\"Id\":"+resultSet.getInt(1) + ",\n";
                content += "\"FamilyId\":"+resultSet.getInt(2) + ",\n";
                content += "\"AccountId\":"+resultSet.getInt(3) + ",\n";
                content += "\"AccountIsAdmin\":"+resultSet.getInt(4) + "";
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
	 * Get all family member of family as list.
	 * @param accountId	//which account
	 * @param accountName //account
	 * @param familyId	//which family.
	 * @return			//return all family list as JSON.
	 */
	public String getAllAsList(int accountId,String accountName,int familyId) {
		///for improving security[28120201126]:@{
		if(!Account.accountIsExists(accountId, accountName)){
			return "{\"AllList\":[ ]}";
		}
		///for improving security[28120201126]:@}
		String query_sql = "SELECT *FROM tblFamilyMember WHERE FamilyId='" + familyId +"'";
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
	                content += "\"FamilyId\":"+resultSet.getInt(2) + ",\n";
	                content += "\"AccountId\":"+resultSet.getInt(3) + ",\n";
	                content += "\"AccountIsAdmin\":"+resultSet.getInt(4) + "";
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
	 * Aile üyeye,Aile üzerinde Admin yetkisi ver.
	 * @param accountId //which account
	 * @param accountName //account
	 * @param familyId  //which family
	 * @return			//return true(as JSON) if remove family successful.
	 */
	public String setFamilyAdmin(int accountId,String accountName,int familyId) {
		String response = "{\"SetFamilyAdmin\":[{ \"response\":false}]}";
		///for improving security[28120201126]:@{
		if(!Account.accountIsExists(accountId, accountName)){
			return response;
		}
		///for improving security[28120201126]:@}
		int s = adminOperations(accountId,familyId,1);
		if(s > 0)
			response = "{\"SetFamilyAdmin\":[{ \"response\":true}]}";
		return response;
	}//setFamilyAdmin()
	
	/**
	 * Üyeye,Aile üzerinde Admin yetkisini kaldır.
	 * @param accountId  //which account
	 * @param accountName //account
	 * @param familyId	 //which family
	 * @return			//return true(as JSON) if remove family successful.
	 */
	public String removeFamilyAdmin(int accountId,String accountName,int familyId) {
		String response = "{\"RemoveFamilyAdmin\":[{ \"response\":false}]}";
		///for improving security[28120201126]:@{
		if(!Account.accountIsExists(accountId, accountName)){
			return response;
		}
		///for improving security[28120201126]:@}
		int s = adminOperations(accountId,familyId,0);
		if(s > 0)
			response = "{\"RemoveFamilyAdmin\":[{ \"response\":true}]}";
		return response;
	}
	
	
	/**
	 * Remove family member.
	 * @param accountId		//which account
	 * @param accountName	//account
	 * @param memberRowId	//which member.
	 * @return			//return true(as JSON) if remove member is successful.
	 */
	public String removeMember(int accountId,String accountName,int memberRowId) {
		String response = "{\"RemoveMember\":[{ \"response\":false}]}";
		///for improving security[28120201126]:@{
		if(!Account.accountIsExists(accountId, accountName)){
			return response;
		}
		///for improving security[28120201126]:@}
		String insert_sql = "DELETE FROM tblFamilyMember WHERE Id='" + memberRowId + "'";
		Database database = new Database();
		try {
			database.connect();
			int s = database.execSQL(insert_sql);
			if(s > 0)
				response = "{\"RemoveMember\":[{ \"response\":true}]}";
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
	}//removeMember()

	///////////////////////////////////
	/**
	 * @param accountId //which account
	 * @param familyId	//wich family.
	 * @param state		//admin=1,normal=0
	 * @return			//return 1 if update successful.
	 */
	private int adminOperations(int accountId,int familyId,int state) {
		Database database = new Database();
		int s = 0;
		try {
			String update_sql = "UPDATE tblFamilyMember SET AccountIsAdmin='"+ state +"' WHERE FamilyId='" + familyId + "' AND AccountId='" + accountId +"'";
			database.connect();
			s = database.execSQL(update_sql);
			if(Log.DEBUG)
				Log.println(TAG, "adminOperations: update tblFamilyMember state:" + s);
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
		return s;
	}//adminOperations()
}
