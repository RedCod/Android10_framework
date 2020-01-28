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

@Path("/Family")
public class WS_Family {
	
	/**
	 * Add family.
	 * @param AccountId			//which account.Ailenin ait olduğu Hesap.
	 * @param AccountName 		//Account(email_or_phone). for improving security
	 * @param FamilyName  		//Aile adı.
	 * @param RoomsId			//Id values for rooms. exp: "1,2,5,6,9,11"
	 * @param FamilyLocation	//family geographic location.
	 * @return					//return true(as JSON) if Family added successful.
	 */
	@GET
	@Produces("application/json")
	@Path("/AddFamily/{AccountId}/{AccountName}/{FamilyName}/{RoomsId}/{FamilyLocation}")
	public Response AddFamily(@PathParam("AccountId")int AccountId,
						      @PathParam("AccountName")String AccountName,
							  @PathParam("FamilyName")String FamilyName,
							  @PathParam("RoomsId")String RoomsId,
							  @PathParam("FamilyLocation")String FamilyLocation) {
		/*
		 *  http://localhost:8080/REST1/Family/AddFamily/1/test@gmail.com/Aile4/1,2,3,4,5/loc
		 */
		Family family = new Family();
        String response = family.addFamily(AccountId,AccountName, FamilyName, RoomsId, FamilyLocation);
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Edit family name.
	 * @param AccountId		 //which account
	 * @param AccountName	 //Account
	 * @param RowId		     //which Family row.
	 * @param FamilyName	//new family name value.
	 * @return  			//return true(as JSON) if Family name update successful.
	 */
	@GET
	@Produces("application/json")
	@Path("/EditName/{AccountId}/{AccountName}/{RowId}/{FamilyName}")
	public Response EditName(@PathParam("AccountId")int AccountId,
						  	 @PathParam("AccountName")String AccountName,
			                 @PathParam("RowId")int RowId,
							 @PathParam("FamilyName")String FamilyName) {
		/*
		 * http://localhost:8080/REST1/Family/EditName/1/test@gmail.com/6/Aile_4
		 */
		Family family = new Family();
		String response = family.editName(AccountId,AccountName,RowId, FamilyName);
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Edit rooms of family-Aile odalarını düzenle-ata.
	 * @param AccountId		//which account
	 * @param AccountName 	//Account
	 * @param RowId		  //which Family.
	 * @param RoomsIds	//id values for rooms as string. exp: "1,2,4,5,8,10,11"
	 * @return			//return true(as JSON) if family rooms update successful.
	 */
	@GET
	@Produces("application/json")
	@Path("/EditRooms/{AccountId}/{AccountName}/{RowId}/{RoomsIds}")
	public Response EditRooms(@PathParam("AccountId")int AccountId,
							  @PathParam("AccountName")String AccountName,
			                  @PathParam("RowId")int RowId,
							  @PathParam("RoomsIds")String RoomsIds) {
		/*
		 * http://localhost:8080/REST1/Family/EditRooms/1/test@gmail.com/6/55,6,8,7
		 */
		Family family = new Family();
		String response = family.editRooms(AccountId,AccountName,RowId, RoomsIds);
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Edit family geographic location.
	 * @param AccountId		//which account
	 * @param AccountName	//Account
	 * @param RowId			//which Family.
	 * @param FamilyLocation	//new location value.
	 * @return					//return true(as JSON) if family new location update successful.
	 */
	@GET
	@Produces("application/json")
	@Path("/EditLocation/{AccountId}/{AccountName}/{RowId}/{FamilyLocation}")
	public Response EditLocation(@PathParam("AccountId")int AccountId,
								 @PathParam("AccountName")String AccountName,
			                     @PathParam("RowId")int RowId,
								 @PathParam("FamilyLocation")String FamilyLocation) {
		/*
		 * http://localhost:8080/REST1/Family/EditLocation/1/test@gmail.com/6/newloc
		 */
		Family family = new Family();
		String response = family.editLocation(AccountId,AccountName,RowId, FamilyLocation);
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Get info of one family.
	 * @param AccountId //which account
	 * @param AccountName //Account
	 * @param RowId	//which family.
	 * @return			//return family items as JSON.
	 */
	@GET
	@Produces("application/json")
	@Path("/GetItem/{AccountId}/{AccountName}/{RowId}")
	public Response GetItem(@PathParam("AccountId")int AccountId,
							@PathParam("AccountName")String AccountName,
							@PathParam("RowId")int RowId) {
		/*
		 * http://localhost:8080/REST1/Family/GetItem/1/test@gmail.com/5
		 */
		Family family = new Family();
		String response = family.getItem(AccountId,AccountName,RowId);
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Get all family as List.
	 * @param AccountId	//related which account
	 * @param AccountName //Account
	 * @return		    //return all family list as JSON.
	 */
	@GET
	@Produces("application/json")
	@Path("/GetAllAsList/{AccountId}/{AccountName}")
	public Response GetAllAsList(@PathParam("AccountId")int AccountId,
			                     @PathParam("AccountName")String AccountName) {
		/*
		 * http://localhost:8080/REST1/Family/GetAllAsList/1/test@gmail.com
		 */
		Family family = new Family();
		String response = family.getAllAsList(AccountId,AccountName);
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Remove family - Aile sil/kaldır
	 * @param AccountId 	//which account
	 * @param AccountName 	//Account
	 * @param RowId		//which family Id.
	 * @return				//return true(as JSON) if remove family successful.
	 */
	@GET
	@Produces("application/json")
	@Path("/RemoveFamily/{AccountId}/{AccountName}/{RowId}")
	public Response RemoveFamily(@PathParam("AccountId")int AccountId,
								 @PathParam("AccountName")String AccountName,
			                     @PathParam("RowId")int RowId) {
		/*
		 * http://localhost:8080/REST1/Family/RemoveFamily/1/test@gmail.com/6
		 */
		Family family = new Family();
		String response = family.removeFamily(AccountId,AccountName,RowId);
		return Response.status(200).entity(response).build();
	}

}
