package com.service.main;

import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.service.sub.Account;
import com.service.sub.Device;
import com.service.sub.Family;
import com.database.Database;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import com.service.sub.Room;

@Path("/Room")
public class WS_Room {

	/**
	 * Add room. Oda ekle.
	 * @param AccountId //which account
	 * @param AccountName	//Account(email_or_phone).for improving security
	 * @param FamilyId	//which family.
	 * @param RoomName	//Room name.
	 * @return			//return true(as JSON) if add room successful.
	 */
	@GET
	@Produces("application/json")
	@Path("/AddRoom/{AccountId}/{AccountName}/{FamilyId}/{RoomName}")
	public Response AddRoom(@PathParam("AccountId")int AccountId,
			                @PathParam("AccountName")String AccountName,
							@PathParam("FamilyId")int FamilyId,
							@PathParam("RoomName")String RoomName) {
		/*
		 * http://localhost:8080/REST1/Room/AddRoom/1/test@gmail.com/5/Mutfak
		 */
		Room room = new Room();
        String response = room.addRoom(AccountId,AccountName,FamilyId, RoomName);
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Edit room name. Oda adı süzenle.
	 * @param AccountId //which account
	 * @param AccountName //Account
	 * @param RoomRowId	//which room.
	 * @param RoomName	//room name.
	 * @return  		//return true(as JSON) if edit room name successful.	
	 */
	@GET
	@Produces("application/json")
	@Path("/EditRoomName/{AccountId}/{AccountName}/{RoomRowId}/{RoomName}")
	public Response EditRoomName(@PathParam("AccountId")int AccountId,
			 					 @PathParam("AccountName")String AccountName,
			                     @PathParam("RoomRowId")int RoomRowId,
							     @PathParam("RoomName")String RoomName) {
		/*
		 * http://localhost:8080/REST1/Room/EditRoomName/1/test@gmail.com/1/Mutfak2/
		 */
		Room room = new Room();
		String response = room.editRoomName(AccountId,AccountName,RoomRowId, RoomName);
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Edit room sort.(Oda sırasını değiştir).
	 * @param AccountId //which account
	 * @param AccountName //Account
	 * @param RoomRowId	//which room.
	 * @param SortValue	//sort value.
	 * @return			//return true(as JSON) if edit room sort successful.
	 */
	@GET
	@Produces("application/json")
	@Path("/EditRoomSort/{AccountId}/{AccountName}/{RoomRowId}/{SortValue}")
	public Response EditRoomSort(@PathParam("AccountId")int AccountId,
								 @PathParam("AccountName")String AccountName,
			                     @PathParam("RoomRowId")int RoomRowId,
								 @PathParam("SortValue")int SortValue) {
		/*
		 * http://localhost:8080/REST1/Room/EditRoomSort/1/test@gmial.com/1/3
		 */
		Room room = new Room();
		String response = room.editRoomSort(AccountId,AccountName,RoomRowId, SortValue);
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Get one room info as Item.
	 * @param AccountId //which account
	 * @param AccountName //Account
	 * @param RoomRowId	//which room?
	 * @return			//return room Item as JSON.
	 */
	@GET
	@Produces("application/json")
	@Path("/GetItem/{AccountId}/{AccountName}/{RoomRowId}")
	public Response GetItem(@PathParam("AccountId")int AccountId,
							@PathParam("AccountName")String AccountName,
			                @PathParam("RoomRowId")int RoomRowId) {
		/*
		 * http://localhost:8080/REST1/Room/GetItem/1/test@gmail.com/3
		 */
		Room room = new Room();
		String response = room.getItem(AccountId,AccountName,RoomRowId);
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Get room Items as List by AccountId and FamilyId. 
	 * @param AccountId	//which account?
	 * @param AccountName //Account
	 * @param FamilyId	//which family?
	 * @return			//return room Items as JSON.
	 */
	@GET
	@Produces("application/json")
	@Path("/GetAllAsList/{AccountId}/{AccountName}/{FamilyId}")
	public Response GetAllAsList(@PathParam("AccountId")int AccountId,
			                     @PathParam("AccountName")String AccountName,
								 @PathParam("FamilyId")int FamilyId) {
		/*
		 * http://localhost:8080/REST1/Room/GetAllAsList/1/test@gmail.com/1
		 */
		Room room = new Room();
		String response = room.getAllAsList(AccountId,AccountName,FamilyId);
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Remove room.  Oda sil.
	 * @param AccountId  //which account
	 * @param AccountName //Account
	 * @param RoomRowId	//which room
	 * @return			//return true(as JSON) if remove successful.
	 */
	@GET
	@Produces("application/json")
	@Path("/RemoveRoom/{AccountId}/{AccountName}/{RoomRowId}")
	public Response RemoveRoom(@PathParam("AccountId")int AccountId,
							   @PathParam("AccountName")String AccountName,
			                   @PathParam("RoomRowId")int RoomRowId) {
		/*
		 * http://localhost:8080/REST1/Room/RemoveRoom/1/test@gmail.com/3
		 */
		Room room = new Room();
		String response = room.removeRoom(AccountId,AccountName,RoomRowId);
		return Response.status(200).entity(response).build();
	}
	
	
}
