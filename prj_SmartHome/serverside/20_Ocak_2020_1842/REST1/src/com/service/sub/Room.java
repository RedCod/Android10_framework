package com.service.sub;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.database.Database;

public class Room {
	/**
	 * Room(oda) işlemleri. Kaydet,Sil,Düzenle,etc.
	 * used by:
	 * 		-WS_Room.java
	 */
	/*
CREATE TABLE IF NOT EXISTS tblRoom (
    Id INT AUTO_INCREMENT,
    AccountId INT,
    FamilyId INT,
    RoomName VARCHAR(100),
    ItemSort INT,
    PRIMARY KEY (Id)
); 
	 */
	private static String TAG = "Room";
	public Room() {}
	
	/**
	 * Add room. Oda Ekle.
	 * @param accountId //which account.
	 * @param familyId	//belong of which family
	 * @param roomName	//Room name.
	 * @return			//return true(as JSON) if add room successful. 
	 */
	public String addRoom(int accountId,int familyId,String roomName) {
		String response = "{\"AddRoom\":[{ \"response\":false}]}";
		String sql = "INSERT INTO tblRoom(AccountId,FamilyId,RoomName,ItemSort) VALUES('"+ accountId + "' ,'"+ familyId +"','" + roomName + "',1)";
		Database database = new Database();
		try {
			database.connect();
			int s = database.execSQL(sql);
			if(s > 0)
				response = "{\"AddRoom\":[{ \"response\":true}]}";
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
	}//addRoom()
	
	
	/**
	 * Edit room name.
	 * @param roomRowId	//which room.
	 * @param roomName  //new room name
	 * @return			//return true(as JSON) if edit successful.
	 */
	public String editRoomName(int roomRowId,String roomName) {
		String response = "{\"EditRoomName\":[{ \"response\":false}]}";
		String sql = "UPDATE tblRoom SET RoomName='" + roomName + "' WHERE Id='" + roomRowId + "'";
		Database database = new Database();
		try {
			database.connect();
			int s = database.execSQL(sql);
			if(s > 0)
				response = "{\"EditRoomName\":[{ \"response\":true}]}";
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
	}
	
	
	/**
	 * Edit room sort. Oda sıralamasını değiştir.
	 * @param roomRowId	//which room.
	 * @param sortValue	//new sort value.
	 * @return			//return true(as JSON) if edit room sort successful.
	 */
	public String editRoomSort(int roomRowId,int sortValue) {
		String response = "{\"EditRoomSort\":[{ \"response\":false}]}";
		String sql = "UPDATE tblRoom SET ItemSort='" + sortValue + "' WHERE Id='" + roomRowId + "'";
		Database database = new Database();
		try {
			database.connect();
			int s = database.execSQL(sql);
			if(s > 0)
				response = "{\"EditRoomSort\":[{ \"response\":true}]}";
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
	}//editRoomSort()
	
	
	/**
	 * Get one room info.
	 * @param roomRowId	//which room?
	 * @return		//return room Item as JSON.
	 */
	public String getItem(int roomRowId) {
		String query_sql = "SELECT *FROM tblRoom WHERE Id='" + roomRowId + "'";
		Database database = new Database();
		String content = "{\"Item\":[\n";
		try {
			database.connect();
			ResultSet resultSet  = database.queryForResultSet(query_sql);
            while (resultSet.next()) {
                content +="{\n";
                //content += "\"Id\":\""+resultSet.getInt(1) + "\",\n";
                content += "\"Id\":"+resultSet.getInt(1) + ",\n";
                content += "\"AccountId\":"+resultSet.getInt(2) + ",\n";
                content += "\"FamilyId\":"+resultSet.getInt(3) + ",\n";
                content += "\"RoomName\":\""+resultSet.getString(4) + "\",\n";
                content += "\"ItemSort\":"+resultSet.getInt(5) + "";
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
	 * Get all room info as Items.
	 * @param accountId	//which account?
	 * @param familyId	//which family?
	 * @return			//return Items as JSON.
	 */
	public String getAllAsList(int accountId,int familyId) {
		String query_sql = "SELECT *FROM tblRoom WHERE AccountId='" + accountId +"' AND FamilyId='" + familyId +"'";
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
	                content += "\"Id\":"+resultSet.getInt(1) + ",\n";
	                content += "\"AccountId\":"+resultSet.getInt(2) + ",\n";
	                content += "\"FamilyId\":"+resultSet.getInt(3) + ",\n";
	                content += "\"RoomName\":\""+resultSet.getString(4) + "\",\n";
	                content += "\"ItemSort\":"+resultSet.getInt(5) + "";
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
	 * Delete room. Oda sil.
	 * @param roomRowId	//which room.
	 * @return			//return true(as JSON) if remove successful.
	 */
	public String removeRoom(int roomRowId) {
		String response = "{\"RemoveRoom\":[{ \"response\":false}]}";
		String sql = "DELETE FROM tblRoom WHERE Id='" + roomRowId + "'";
		Database database = new Database();
		try {
			database.connect();
			int s = database.execSQL(sql);
			if(s > 0)
				response = "{\"RemoveRoom\":[{ \"response\":true}]}";
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
	}//removeRoom()
	

}
