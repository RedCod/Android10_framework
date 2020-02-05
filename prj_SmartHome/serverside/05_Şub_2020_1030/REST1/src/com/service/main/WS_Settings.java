package com.service.main;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import com.service.sub.Schedule;

import com.service.sub.Settings;

@Path("/Settings")
public class WS_Settings {
	
	/**
	 * Add "Settings(for mobil app side)" when creating a new account.
	 * @param AccountId			 //which account
	 * @param AccountName		 //Account(email_or_phone). for improving security
	 * @param DontDisturbDevices //for don't disturb devices. 1=Aktif,0=Pasif	
	 * @param PushNotification	 //state value for "Push Notification". 1=Aktif,0=Pasif
	 * @return				     //return true(as JSON) if successful
	 */
	@GET
	@Produces("application/json")
	@Path("/AddSettings/{AccountId}/{AccountName}/{DontDisturbDevices}/{PushNotification}")
	public Response AddSettings(@PathParam("AccountId")int AccountId,
							    @PathParam("AccountName")String AccountName,
			                    @PathParam("DontDisturbDevices")int DontDisturbDevices,
			                    @PathParam("PushNotification")int PushNotification) {
		/*
		 * http://localhost:8080/REST1/Settings/AddSettings/1/test@gmail.com/1/1
		 */
		Settings settings = new Settings();
		String response = settings.addSettings(AccountId,AccountName, DontDisturbDevices, PushNotification);
		return Response.status(200).entity(response).build();
	}
	
	
	/**
	 * Edit "don't disturb" devices setting value. 
	 * @param AccountId	 //which account
	 * @param AccountName //Account
	 * @param StateValue //state value. 1=Aktif,0=Pasif
	 * @return		   	 //return true(as JSON) if successful
	 */
	@GET
	@Produces("application/json")
	@Path("/EditDontDisturbDevices/{AccountId}/{AccountName}/{StateValue}")
	public Response EditDontDisturbDevices(@PathParam("AccountId")int AccountId,
			                               @PathParam("AccountName")String AccountName,
										   @PathParam("StateValue")int StateValue) {
		/*
		 *  http://localhost:8080/REST1/Settings/EditDontDisturbDevices/1/test@gmail.com/1
		 */
		Settings settings = new Settings();
		String response = settings.editDontDisturbDevices(AccountId,AccountName, StateValue);
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Edit "Push Notification" setting value for the user to receive or not recieve notifications.
	 * @param AccountId	  //which account
	 * @param AccountName	//Account
	 * @param StateValue  //state value. 1=Aktif,0=Pasif	
	 * @return			  //return true(as JSON) if successful.
	 */
	@GET
	@Produces("application/json")
	@Path("/EditPushNotification/{AccountId}/{AccountName}/{StateValue}")
	public Response EditPushNotification(@PathParam("AccountId")int AccountId,
										 @PathParam("AccountName")String AccountName,
			                             @PathParam("StateValue")int StateValue) {
		/*
		 *  http://localhost:8080/REST1/Settings/EditPushNotification/1/test@gmail.com/1
		 */
		Settings settings = new Settings();
		String response = settings.editPushNotification(AccountId,AccountName, StateValue);
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Remove settings. 
	 * @param AccountId //related which account
	 * @param AccountName	//Account
	 * @return			//return true(as JSON) if successful
	 */
	@GET
	@Produces("application/json")
	@Path("/RemoveSettings/{AccountId}/{AccountName}")
	public Response RemoveSettings(@PathParam("AccountId")int AccountId,
			                       @PathParam("AccountName")String AccountName) {
		/*
		 * http://localhost:8080/REST1/Settings/RemoveSettings/2/test@gmail.com
		 */
		Settings settings = new Settings();
		String response = settings.removeSettings(AccountId,AccountName);
		return Response.status(200).entity(response).build();
	}
	
	
}
