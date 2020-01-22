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
	 * @param AccountId			//Ailenin ait olduğu Hesap.
	 * @param FamilyName  		//Aile adı.
	 * @param RoomsId			//Id values for rooms. exp: "1,2,5,6,9,11"
	 * @param FamilyLocation	//family geographic location.
	 * @return					//return true(as JSON) if Family added successful.
	 */
	@GET
	@Produces("application/json")
	@Path("/AddFamily/{AccountId}/{FamilyName}/{RoomsId}/{FamilyLocation}")
	public Response AddFamily(@PathParam("AccountId")int AccountId,
							 @PathParam("FamilyName")String FamilyName,
							 @PathParam("RoomsId")String RoomsId,
							 @PathParam("FamilyLocation")String FamilyLocation) {
		/*
		 *  http://localhost:8080/REST1/Family/AddFamily/1/Aile4/1,2,3,4,5/loc
		 */
		Family family = new Family();
        String response = family.addFamily(AccountId, FamilyName, RoomsId, FamilyLocation);
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Edit family name.
	 * @param FamilyRowId		//which Family.
	 * @param FamilyName	//new family name value.
	 * @return  			//return true(as JSON) if Family name update successful.
	 */
	@GET
	@Produces("application/json")
	@Path("/EditName/{FamilyRowId}/{FamilyName}")
	public Response EditName(@PathParam("FamilyRowId")int FamilyRowId,
							 @PathParam("FamilyName")String FamilyName) {
		/*
		 * http://localhost:8080/REST1/Family/EditName/6/Aile_4
		 */
		Family family = new Family();
		String response = family.editName(FamilyRowId, FamilyName);
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Edit rooms of family-Aile odalarını düzenle-ata.
	 * @param familyRowId	//which Family.
	 * @param RoomsIds	//id values for rooms as string. exp: "1,2,4,5,8,10,11"
	 * @return			//return true(as JSON) if family rooms update successful.
	 */
	@GET
	@Produces("application/json")
	@Path("/EditRooms/{familyRowId}/{RoomsIds}")
	public Response EditRooms(@PathParam("familyRowId")int familyRowId,
							  @PathParam("RoomsIds")String RoomsIds) {
		/*
		 * http://localhost:8080/REST1/Family/EditRooms/6/55,6,8,7
		 */
		Family family = new Family();
		String response = family.editRooms(familyRowId, RoomsIds);
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Edit family geographic location.
	 * @param FamilyRowId			//which Family.
	 * @param FamilyLocation	//new location value.
	 * @return					//return true(as JSON) if family new location update successful.
	 */
	@GET
	@Produces("application/json")
	@Path("/EditLocation/{FamilyRowId}/{FamilyLocation}")
	public Response EditLocation(@PathParam("FamilyRowId")int FamilyRowId,
								 @PathParam("FamilyLocation")String FamilyLocation) {
		/*
		 * http://localhost:8080/REST1/Family/EditLocation/6/newloc
		 */
		Family family = new Family();
		String response = family.editLocation(FamilyRowId, FamilyLocation);
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Get info of one family.
	 * @param FamilyRowId	//which family.
	 * @return			//return family items as JSON.
	 */
	@GET
	@Produces("application/json")
	@Path("/GetItem/{FamilyRowId}")
	public Response GetItem(@PathParam("FamilyRowId")int FamilyRowId) {
		/*
		 * http://localhost:8080/REST1/Family/GetItem/5
		 */
		Family family = new Family();
		String response = family.getItem(FamilyRowId);
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Get all family as List.
	 * @param AccountId	//related which account
	 * @return		    //return all family list as JSON.
	 */
	@GET
	@Produces("application/json")
	@Path("/GetAllAsList/{AccountId}")
	public Response GetAllAsList(@PathParam("AccountId")int AccountId) {
		/*
		 * http://localhost:8080/REST1/Family/GetAllAsList/1
		 */
		Family family = new Family();
		String response = family.getAllAsList(AccountId);
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Remove family - Aile sil/kaldır
	 * @param FamilyRowId		//which family Id.
	 * @return				//return true(as JSON) if remove family successful.
	 */
	@GET
	@Produces("application/json")
	@Path("/RemoveFamily/{FamilyRowId}")
	public Response RemoveFamily(@PathParam("FamilyRowId")int FamilyRowId) {
		/*
		 * http://localhost:8080/REST1/Family/RemoveFamily/6
		 */
		Family family = new Family();
		String response = family.removeFamily(FamilyRowId);
		return Response.status(200).entity(response).build();
	}

}
