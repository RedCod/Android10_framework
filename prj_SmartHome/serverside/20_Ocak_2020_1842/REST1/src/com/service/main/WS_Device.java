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
	 * 
	 * @param AccountId		//Accout Id. this value get from tlbAccount->Id
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
	@Path("/AddDevice/{AccountId}/{FamilyId}/{RoomId}/{DeviceName}/{VirtualAddress}/{IpAddress}/{MacAddress}/{DeviceType}/{DeviceTimeZone}")
	public Response AddDevice(@PathParam("AccountId")int AccountId,
						@PathParam("FamilyId")int FamilyId,
						@PathParam("RoomId") int RoomId,
						@PathParam("DeviceName")String DeviceName,
						@PathParam("VirtualAddress")String VirtualAddress,
						@PathParam("IpAddress")String IpAddress,
						@PathParam("MacAddress")String MacAddress,
						@PathParam("DeviceType")String DeviceType,
						@PathParam("DeviceTimeZone")String DeviceTimeZone) {
		/*
		 * http://localhost:8080/REST1/Device/AddDevice/1/5/1/Lamba1/111macadres/78.55.41.15/macaddres/lamp/Istanbul
		 */
		Device device = new Device();
	    String response = device.addDevice(AccountId, FamilyId, RoomId, DeviceName, VirtualAddress, IpAddress, MacAddress, DeviceType, DeviceTimeZone);
		return Response.status(200).entity(response).build();
	}

	/**
	 * Edit device name.
	 * @param DeviceId   //Device Id. Which Device?
	 * @param DeviceName //new device name.
	 * @return 			//return true(as JSON) if device name edit successful.
	 */
	@GET
	@Produces("application/json")
	@Path("/EditName/{DeviceId}/{DeviceName}")
	public Response EditName(@PathParam("DeviceId")int DeviceId,
							 @PathParam("DeviceName")String DeviceName) {
		/*
		 * http://localhost:8080/REST1/Device/EditName/1/LambaMasam5
		 */
		Device device = new Device();
		String response = device.editName(DeviceId, DeviceName);
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Edit device location. 
	 * @param DeviceId  	 //Device Id. Which device?
	 * @param DeviceLocation //tblRoom->Id. Location = Mutfak or Yemek Odası etc.	
	 * @return				//return true(as JSON) if edit device location successful.
	 */
	@GET
	@Produces("application/json")
	@Path("/EditLocation/{DeviceId}/{DeviceLocation}")
	public Response EditLocation(@PathParam("DeviceId")int DeviceId,
								 @PathParam("DeviceLocation")int DeviceLocation) {
		/*
		 * http://localhost:8080/REST1/Device/EditLocation/1/7
		 */
		Device device = new Device();
		String response = device.editLocation(DeviceId, DeviceLocation);
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Edit device sort.(Cihaz sıralamasını ayarla/değiştir.)
	 * @param DeviceId	//which device.
	 * @param SortValue //sorting value.
	 * @return			//return true(as JSON) if device sorting update successful.
	 */
	@GET
	@Produces("application/json")
	@Path("/EditDeviceSort/{DeviceId}/{SortValue}")
	public Response EditDeviceSort(@PathParam("DeviceId")int DeviceId,
			                      @PathParam("SortValue")int SortValue) {
		/*
		 * http://localhost:8080/REST1/Device/SetDeviceSort/1/3
		 */
		Device device = new Device();
		String response = device.editDeviceSort(DeviceId, SortValue);
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Get one device Item.
	 * @param DeviceId //which device? 
	 * @return		  //return device Item as JSON.
	 */
	@GET
	@Produces("application/json")
	@Path("/GetItem/{DeviceId}")
	public Response GetItem(@PathParam("DeviceId")int DeviceId) {
		/*
		 * http://localhost:8080/REST1/Device/GetItem/1
		 */
		Device device = new Device();
		String response = device.getItem(DeviceId);
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Get all device as list according to the account and family.
	 * @param AccountId	//which account?
	 * @return			//return all item list as JSON.
	 */
	@GET
	@Produces("application/json")
	@Path("/GetAllAsList/{AccountId}/{FamilyId}")
	public Response GetAllAsList(@PathParam("AccountId")int AccountId,
								 @PathParam("FamilyId")int FamilyId) {
		/*
		 * http://localhost:8080/REST1/Device/GetAllAsList/1/1
		 */
		Device device = new Device();
		String response = device.getAllAsList(AccountId,FamilyId);
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Remove Device(Cihazı Sil/Kaldır).
	 * @param AccountId	//Device Id. Which device?
	 * @return 		 	//return true(as JSON) if remove device successful.
	 */
	@GET
	@Produces("application/json")
	@Path("/RemoveDevice/{DeviceId}")
	public Response RemoveDevice(@PathParam("DeviceId")int DeviceId) {
		/*
		 * http://localhost:8080/REST1/Device/RemoveDevice/1
		 */
		Device device = new Device();
		String response = device.removeDevice(DeviceId);
		return Response.status(200).entity(response).build();
	}
	

	
}
