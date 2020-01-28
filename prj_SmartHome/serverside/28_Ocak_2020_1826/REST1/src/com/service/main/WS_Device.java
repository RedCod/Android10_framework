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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Path("/Device")  
public class WS_Device {
	
	/**
	 * Add smart device.
	 * @param AccountId		//Accout Id. this value get from tlbAccount->Id
	 * @param AccountName   //Account(email_or_phone). for improving security
	 * @param FamilyId		//Family Id. Get from tblFamily->Id
	 * @param RoomId		//Room id. So device location. Get from tblRoom->Id
	 * @param DeviceName	//Device name.
	 * @param VirtualAddress //consist of AccountId + MacAddress
	 * @param IpAddress		 //device ip address
	 * @param MacAddress	//device mac address
	 * @param DeviceType	//device type. exp: "lamp","wall_plug"
	 * @param DeviceTimeZone //device time zone. Exp: Europa/Istanbul	
	 * @return				 //return true(as JSON) if device added successful.
	 */
	@GET
	@Produces("application/json")
	@Path("/AddDevice/{AccountId}/{AccountName}/{FamilyId}/{RoomId}/{DeviceName}/{VirtualAddress}/{IpAddress}/{MacAddress}/{DeviceType}/{DeviceTimeZone}")
	public Response AddDevice(@PathParam("AccountId")int AccountId,
							  @PathParam("AccountName")String AccountName,
							  @PathParam("FamilyId")int FamilyId,
							  @PathParam("RoomId") int RoomId,
							  @PathParam("DeviceName")String DeviceName,
							  @PathParam("VirtualAddress")String VirtualAddress,
							  @PathParam("IpAddress")String IpAddress,
							  @PathParam("MacAddress")String MacAddress,
							  @PathParam("DeviceType")String DeviceType,
							  @PathParam("DeviceTimeZone")String DeviceTimeZone) {
		/*
		 * http://localhost:8080/REST1/Device/AddDevice/1/test@gmail.com/5/1/Lamba1/111macadres/78.55.41.15/macaddres/lamp/Istanbul
		 */
		Device device = new Device();
	    String response = device.addDevice(AccountId, AccountName,FamilyId, RoomId, DeviceName, VirtualAddress, IpAddress, MacAddress, DeviceType, DeviceTimeZone);
		return Response.status(200).entity(response).build();
	}

	/**
	 * Edit device name.
	 * @param AccountId	 //which account.
	 * @param AccountName //Account(email_or_phone)
	 * @param DeviceId   //Device Id. Which Device?
	 * @param DeviceName //new device name.
	 * @return 			//return true(as JSON) if device name edit successful.
	 */
	@GET
	@Produces("application/json")
	@Path("/EditName/{AccountId}/{AccountName}/{DeviceId}/{DeviceName}")
	public Response EditName(@PathParam("AccountId")int AccountId,
							 @PathParam("AccountName")String AccountName,
							 @PathParam("DeviceId")int DeviceId,
							 @PathParam("DeviceName")String DeviceName) {
		/*
		 * http://localhost:8080/REST1/Device/EditName/1/test@gmail.com/1/LambaMasam5
		 */
		Device device = new Device();
		String response = device.editName(AccountId,AccountName,DeviceId, DeviceName);
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Edit device location. 
	 * @param AccountId		 //which account
	 * @param AccountName	 //Account(email_or_phone) 
	 * @param DeviceId  	 //Device Id. Which device?
	 * @param DeviceLocation //tblRoom->Id. Location = Mutfak or Yemek Odası etc.	
	 * @return				//return true(as JSON) if edit device location successful.
	 */
	@GET
	@Produces("application/json")
	@Path("/EditLocation/{AccountId}/{AccountName}/{DeviceId}/{DeviceLocation}")
	public Response EditLocation(@PathParam("AccountId")int AccountId,
					 			 @PathParam("AccountName")String AccountName,
			                     @PathParam("DeviceId")int DeviceId,
								 @PathParam("DeviceLocation")int DeviceLocation) {
		/*
		 * http://localhost:8080/REST1/Device/EditLocation/1/test@gmail.com/1/7
		 */
		Device device = new Device();
		String response = device.editLocation(AccountId,AccountName,DeviceId, DeviceLocation);
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Edit device sort.(Cihaz sıralamasını ayarla/değiştir.)
	 * @param AccountId	//which account
	 * @param AccountName //Account
	 * @param DeviceId	//which device.
	 * @param SortValue //sorting value.
	 * @return			//return true(as JSON) if device sorting update successful.
	 */
	@GET
	@Produces("application/json")
	@Path("/EditDeviceSort/{AccountId}/{AccountName}/{DeviceId}/{SortValue}")
	public Response EditDeviceSort(@PathParam("AccountId")int AccountId,
								   @PathParam("AccountName")String AccountName,
                                   @PathParam("DeviceId")int DeviceId,
 			                       @PathParam("SortValue")int SortValue) {
		/*
		 * http://localhost:8080/REST1/Device/EditDeviceSort/1/test@gmail.com/1/3
		 */
		Device device = new Device();
		String response = device.editDeviceSort(AccountId,AccountName,DeviceId, SortValue);
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Get one device Item.
	 * @param AccountId //which account
	 * @param AccountName //Account
	 * @param DeviceId //which device? 
	 * @return		  //return device Item as JSON.
	 */
	@GET
	@Produces("application/json")
	@Path("/GetItem/{AccountId}/{AccountName}/{DeviceId}")
	public Response GetItem(@PathParam("AccountId")int AccountId,
							@PathParam("AccountName")String AccountName,
			                @PathParam("DeviceId")int DeviceId) {
		/*
		 * http://localhost:8080/REST1/Device/GetItem/1/test@gmail.com/1
		 */
		Device device = new Device();
		String response = device.getItem(AccountId,AccountName,DeviceId);
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Get all device as list according to the account and family.
	 * @param AccountId	//which account?
	 * @param AccountName //Account(email_or_phone)
	 * @param FamilyId	//which family. related family.
	 * @return			//return all item list as JSON.
	 */
	@GET
	@Produces("application/json")
	@Path("/GetAllAsList/{AccountId}/{AccountName}/{FamilyId}")
	public Response GetAllAsList(@PathParam("AccountId")int AccountId,
			                     @PathParam("AccountName")String AccountName,
								 @PathParam("FamilyId")int FamilyId) {
		/*
		 * http://localhost:8080/REST1/Device/GetAllAsList/1/test@gmail.com/1
		 */
		Device device = new Device();
		String response = device.getAllAsList(AccountId,AccountName,FamilyId);
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Remove Device(Cihazı Sil/Kaldır).
	 * @param AccountId	//which account
	 * @param AccountName //Account.
	 * @param DeviceId	 //which device
	 * @return 		 	//return true(as JSON) if remove device successful.
	 */
	@GET
	@Produces("application/json")
	@Path("/RemoveDevice/{AccountId}/{AccountName}/{DeviceId}")
	public Response RemoveDevice(@PathParam("AccountId")int AccountId,
			                     @PathParam("AccountName")String AccountName,
			                     @PathParam("DeviceId")int DeviceId) {
		/*
		 * http://localhost:8080/REST1/Device/RemoveDevice/1/test@gmail.com/1
		 */
		Device device = new Device();
		String response = device.removeDevice(AccountId,AccountName,DeviceId);
		return Response.status(200).entity(response).build();
	}
	

	
}
