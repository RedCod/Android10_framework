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
import com.service.sub.DontDisturbDevices;
import com.service.sub.Family;
import com.database.Database;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Path("/DontDisturbDevices")
public class WS_DontDisturbDevices {
	
	/**
	 * Add "Don't disturb Devices" (Cihazları Rahatsız Etmeyin).
	 * @param AccountId		//which account.
	 * @param TimeStart		//Time start. exp:12:00
	 * @param TimeEnd		//Time end.   exp:13:00 
	 * @param DevicesId		//Selected Devices Ids. exp: "10,102,35,14" ..
	 * @param Monday		//day to repeat for Monday. 1 or 0
	 * @param Tuesday		//day to repeat for Tuesday. 1 or 0
	 * @param Wednesday		//day to repeat for Wednesday. 1 or 0
	 * @param Thursday		//day to repeat for Thursday. 1 or 0
	 * @param Friday		//day to repeat for Friday. 1 or 0
	 * @param Saturday		//day to repeat for Saturday. 1 or 0
	 * @param Sunday		//day to repeat for Sunday. 1 or 0
	 * @return				//return true(as JSON) if "Don't disturb devices" Added successful.
	 */
	@GET
	@Produces("application/json")
	@Path("/Add/{AccountId}/{TimeStart}/{TimeEnd}/{DevicesId}/{Monday}/{Tuesday}/{Wednesday}/{Thursday}/{Friday}/{Saturday}/{Sunday}")
	public Response Add(@PathParam("AccountId")int AccountId,
						@PathParam("TimeStart")String TimeStart,
						@PathParam("TimeEnd")String TimeEnd,
						@PathParam("DevicesId")String DevicesId,
						@PathParam("Monday")int Monday,
						@PathParam("Tuesday")int Tuesday,
						@PathParam("Wednesday")int Wednesday,
						@PathParam("Thursday")int Thursday,
						@PathParam("Friday")int Friday,
						@PathParam("Saturday")int Saturday,
						@PathParam("Sunday")int Sunday){
		/*
		 *http://localhost:8080/REST1/DontDisturbDevices/Add/4/13:55/16:00/4,5,9,101/1/1/1/1/1/1/1
		 */
		DontDisturbDevices dontDisturbDevices = new DontDisturbDevices();
        String response = dontDisturbDevices.add(AccountId, TimeStart, TimeEnd, DevicesId, Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday);
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Edit "Don't disturb Devices" (Cihazları Rahatsız Etmeyin)
	 * @param Id			//which item Id.
	 * @param TimeStart		//Time start. exp:12:00
	 * @param TimeEnd		//Time end.	  exp:13:00
	 * @param DevicesId		//Selected Devices Ids. exp:"10,102,35,14". //seçilen tüm cihazların id değerleri:exp: "10,102,35,14".
	 * @param Monday		//day to repeat for Monday. 1 or 0
	 * @param Tuesday		//day to repeat for Tuesday. 1 or 0
	 * @param Wednesday		//day to repeat for Wednesday. 1 or 0
	 * @param Thursday		//day to repeat for Thursday. 1 or 0
	 * @param Friday		//day to repeat for Friday. 1 or 0
	 * @param Saturday		//day to repeat for Saturday. 1 or 0
	 * @param Sunday		//day to repeat for Sunday. 1 or 0
	 * @return				//return true(as JSON) if "Don't disturb devices" Edit successful.
	 */
	@GET
	@Produces("application/json")
	@Path("/Edit/{Id}/{TimeStart}/{TimeEnd}/{DevicesId}/{Monday}/{Tuesday}/{Wednesday}/{Thursday}/{Friday}/{Saturday}/{Sunday}")
	public Response Edit(@PathParam("Id")int Id,
						 @PathParam("TimeStart")String TimeStart,
						 @PathParam("TimeEnd")String TimeEnd,
						 @PathParam("DevicesId")String DevicesId,
						 @PathParam("Monday")int Monday,
						 @PathParam("Tuesday")int Tuesday,
						 @PathParam("Wednesday")int Wednesday,
						 @PathParam("Thursday")int Thursday,
						 @PathParam("Friday")int Friday,
						 @PathParam("Saturday")int Saturday,
						 @PathParam("Sunday")int Sunday) {
		/*
		 * http://localhost:8080/REST1/DontDisturbDevices/Edit/30/18:57/19:10/4,5,9,101/1/1/1/1/1/1/1
		 */
		DontDisturbDevices dontDisturbDevices = new DontDisturbDevices();
		String response = dontDisturbDevices.edit(Id, TimeStart, TimeEnd, DevicesId, Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday);
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Get Item(as JSON). Item getir.
	 * @param Id	//which item.
	 * @return		//return item as JSON. Or return false(as JSON) if the item doen't exists.
	 */
	@GET
	@Produces("application/json")
	@Path("/GetItem/{Id}")
	public Response GetItem(@PathParam("Id")int Id) {
		/*
		 * http://localhost:8080/REST1/DontDisturbDevices/GetItem/30
		 */
		DontDisturbDevices dontDisturbDevices = new DontDisturbDevices();
		String response = dontDisturbDevices.getItem(Id);
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Get all as list(JSON). Tümünü listele(JSON olarak).
	 * @param AccountId	//account Id.
	 * @return	return all as list(as JSON). Or return false(as JSON) if the table is empty.
	 */
	@GET
	@Produces("application/json")
	@Path("/GetAllAsList/{AccountId}")
	public Response GetAllAsList(@PathParam("AccountId")int AccountId) {
		/*
		 * http://localhost:8080/REST1/DontDisturbDevices/GetAllAsList/4
		 */
		DontDisturbDevices dontDisturbDevices = new DontDisturbDevices();
		String response =dontDisturbDevices.getAllAsList(AccountId);
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Remove "Don't disturb Devices" (Cihazları Rahatsız Etmeyin)
	 * @param RowId	//which item("Disturb Devices") Id.
	 * @return		//return true(as JSON) if "Don't disturb devices" Remove successful.
	 */
	@GET
	@Produces("application/json")
	@Path("/Remove/{RowId}")
	public Response Remove(@PathParam("RowId")int RowId) {
		/*
		 * http://localhost:8080/REST1/DontDisturbDevices/Remove/28
		 */
		DontDisturbDevices dontDisturbDevices = new DontDisturbDevices();
		String response = dontDisturbDevices.remove(RowId);
		return Response.status(200).entity(response).build();
	}
	
	
}