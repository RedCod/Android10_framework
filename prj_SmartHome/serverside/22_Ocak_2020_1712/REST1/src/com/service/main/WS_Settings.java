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
	 * @param DontDisturbDevices //for don't disturb devices. 1=Aktif,0=Pasif	
	 * @param PushNotification	 //state value for "Push Notification". 1=Aktif,0=Pasif
	 * @return				     //return true(as JSON) if successful
	 */
	@GET
	@Produces("application/json")
	@Path("/AddSettings/{AccountId}/{DontDisturbDevices}/{PushNotification}")
	public Response AddSettings(@PathParam("AccountId")int AccountId,
			                    @PathParam("DontDisturbDevices")int DontDisturbDevices,
			                    @PathParam("PushNotification")int PushNotification) {
		/*
		 * http://localhost:8080/REST1/Settings/AddSettings/1/1/1
		 */
		Settings settings = new Settings();
		String response = settings.addSettings(AccountId, DontDisturbDevices, PushNotification);
		return Response.status(200).entity(response).build();
	}
	
	
	/**
	 * Edit "don't disturb" devices setting value. 
	 * @param AccountId	 //which account
	 * @param StateValue //state value. 1=Aktif,0=Pasif
	 * @return		   	 //return true(as JSON) if successful
	 */
	@GET
	@Produces("application/json")
	@Path("/EditDontDisturbDevices/{AccountId}/{StateValue}")
	public Response EditDontDisturbDevices(@PathParam("AccountId")int AccountId,
										   @PathParam("StateValue")int StateValue) {
		/*
		 *  http://localhost:8080/REST1/Settings/EditDontDisturbDevices/1/1
		 */
		Settings settings = new Settings();
		String response = settings.editDontDisturbDevices(AccountId, StateValue);
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Edit "Push Notification" setting value for the user to receive or not recieve notifications.
	 * @param AccountId	  //which account
	 * @param StateValue  //state value. 1=Aktif,0=Pasif	
	 * @return			  //return true(as JSON) if successful.
	 */
	@GET
	@Produces("application/json")
	@Path("/EditPushNotification/{AccountId}/{StateValue}")
	public Response EditPushNotification(@PathParam("AccountId")int AccountId,
			                             @PathParam("StateValue")int StateValue) {
		/*
		 *  http://localhost:8080/REST1/Settings/EditPushNotification/1/1
		 */
		Settings settings = new Settings();
		String response = settings.editPushNotification(AccountId, StateValue);
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Remove settings. 
	 * @param AccountId //related which account
	 * @return			//return true(as JSON) if successful
	 */
	@GET
	@Produces("application/json")
	@Path("/RemoveSettings/{AccountId}")
	public Response RemoveSettings(@PathParam("AccountId")int AccountId) {
		/*
		 * http://localhost:8080/REST1/Settings/RemoveSettings/2
		 */
		Settings settings = new Settings();
		String response = settings.removeSettings(AccountId);
		return Response.status(200).entity(response).build();
	}
	
	
}
