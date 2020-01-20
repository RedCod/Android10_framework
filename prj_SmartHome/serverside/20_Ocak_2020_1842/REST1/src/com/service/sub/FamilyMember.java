package com.service.sub;

import java.sql.ResultSet;
import java.sql.SQLException;

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
	 * @param familyId		//which family.
	 * @param accountId		//which account.
	 * @param accountIsAdmin//Account is admin=1,else 0.
	 * @return				//return true(as JSON) if adding is successfuly.
	 */
	public String addMember(int familyId,int accountId,int accountIsAdmin) {
		String response = "{\"AddMember\":[{ \"response\":false}]}";
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
	 * @param memberRowId		//which member.
	 * @param accountIsAdmin//Account admin state. Admin=1,else 0.
	 * @return				//return true(as JSON) if edit is successfuly.
	 */
	public String editMember(int memberRowId,int accountIsAdmin) {
		String response = "{\"EditMember\":[{ \"response\":false}]}";
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
	 * @param memberRowId	//which member.
	 * @return			//return family member Item as JSON.
	 */
	public String getItem(int memberRowId) {
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
	 * @param familyId	//which family.
	 * @return			//return all family list as JSON.
	 */
	public String getAllAsList(int familyId) {
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
	 * Remove family member.
	 * @param memberRowId	//which member.
	 * @return			//return true(as JSON) if remove member is successful.
	 */
	public String removeMember(int memberRowId) {
		String response = "{\"RemoveMember\":[{ \"response\":false}]}";
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

}
