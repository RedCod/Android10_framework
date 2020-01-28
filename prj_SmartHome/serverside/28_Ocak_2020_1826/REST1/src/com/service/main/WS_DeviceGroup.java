package com.service.main;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.service.sub.DeviceGroup;
 
@Path("/DeviceGroup")
public class WS_DeviceGroup {
	
	
	/**
	 * Add device group.
	 * @param AccountId		 //which account
	 * @param AccountName 	 //Account(email_or_phone). for improving security
	 * @param FamilyId		 //which family
	 * @param GroupName		 //group name
	 * @param GroupLocation	 //group location. exp: Mutfak
	 * @return				 //return id value of added group row. (Eklenen kayda ait id değerini döndür.
	 */
	@GET
	@Produces("application/json")
	@Path("/AddGroup/{AccountId}/{AccountName}/{FamilyId}/{GroupName}/{GroupLocation}")
	public Response AddGroup(@PathParam("AccountId")int AccountId,
							 @PathParam("AccountName")String AccountName,
							 @PathParam("FamilyId")int FamilyId,
			                 @PathParam("GroupName")String GroupName,
							 @PathParam("GroupLocation")String GroupLocation) {
		/*
		 * http://localhost:8080/REST1/DeviceGroup/AddGroup/1/test@gmail.com/1/GroupMutfak/Mutfak
		 */
		DeviceGroup deviceGroup = new DeviceGroup();
		String response = deviceGroup.addGroup(AccountId,AccountName, FamilyId, GroupName, GroupLocation);
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Add device to device group(to tblDeviceGroupSub).  Use this method for "add" and "edit". (Hem cihaz eklemek için hem de güncellemek için kullanılacak.)
	 * @param AccountId				//which account
	 * @param AccountName		    //Account
	 * @param GroupId				//related which group
	 * @param DeviceId				//device Id
	 * @param DeviceVirtualId    	//device virtual address. macaddres+deviceid
	 * @return						//return true(as JSON) if add is successful.
	 */
	@GET
	@Produces("application/json")
	@Path("/AddDevice/{AccountId}/{AccountName}/{GroupId}/{DeviceId}/{DeviceVirtualId}")
	public Response AddDevice(@PathParam("AccountId")int AccountId,
							  @PathParam("AccountName")String AccountName,
							  @PathParam("GroupId")int GroupId,
							  @PathParam("DeviceId")int DeviceId,
							  @PathParam("DeviceVirtualId")String DeviceVirtualId) {
		/* 
		 * http://localhost:8080/REST1/DeviceGroup/AddDevice/1/test@gmail.com/1/5/macaddress1225
		 */
		DeviceGroup deviceGroup = new DeviceGroup();
		String response = deviceGroup.addDevice(AccountId,AccountName, GroupId, DeviceId, DeviceVirtualId);
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Edit group name
	 * @param AccountId	//which account. for improving security
	 * @param AccountName //Account(email_or_phone). for improving security
	 * @param RowId	//related group row Id.
	 * @param GroupName	//group name
	 * @return			//return true(as JSON) if successful
	 */
	@GET
	@Produces("application/json")
	@Path("/EditGroupName/{AccountId}/{AccountName}/{RowId}/{GroupName}")
	public Response EditGroupName(@PathParam("AccountId")int AccountId,
								  @PathParam("AccountName")String AccountName,
								  @PathParam("RowId")int RowId,
								  @PathParam("GroupName")String GroupName) {
		/*
		 * http://localhost:8080/REST1/DeviceGroup/EditGroupName/1/test@gmail.com/5/GROUPMUTFAK
		 */
		DeviceGroup deviceGroup = new DeviceGroup();
		String response = deviceGroup.editGroupName(AccountId,AccountName,RowId, GroupName);
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Edit group location
	 * @param AccountId	//which account
	 * @param AccountName //Account
	 * @param RowId		//related group row Id.
	 * @param GroupLocation	//group location exp:Mutfak
	 * @return				//return true(as JSON) if successful
	 */
	@GET
	@Produces("application/json")
	@Path("/EditGroupLocation/{AccountId}/{AccountName}/{RowId}/{GroupLocation}")
	public Response EditGroupLocation(@PathParam("AccountId")int AccountId,
									  @PathParam("AccountName")String AccountName,
									  @PathParam("RowId")int RowId,
									  @PathParam("GroupLocation")String GroupLocation) {
		/*
		 * http://localhost:8080/REST1/DeviceGroup/EditGroupLocation/1/test@gmail.com/1/Mutfak2
		 */
		DeviceGroup deviceGroup = new DeviceGroup();
		String response = deviceGroup.editGroupLocation(AccountId,AccountName,RowId, GroupLocation);
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Get content of group.(one group can accommodate a lot of device)
	 * @param AccountId //which accoun
	 * @param AccountName //Account
	 * @param GroupId	//which Group
	 * @return			//return devices list as JSON.
	 */
	@GET
	@Produces("application/json")
	@Path("/GetGroupDevicesAsList/{AccountId}/{AccountName}/{GroupId}")
	public Response GetGroupDevicesAsList(@PathParam("AccountId")int AccountId,
										  @PathParam("AccountName")String AccountName,
			                              @PathParam("GroupId")int GroupId) {
		/*
		 * http://localhost:8080/REST1/DeviceGroup/GetGroupDevicesAsList/1/test@gmail.com/1
		 */
		DeviceGroup deviceGroup = new DeviceGroup();
		String response = deviceGroup.getGroupDevicesAsList(AccountId,AccountName,GroupId);
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Get all devicegroup as list according to the account and family.
	 * @param AccountId	//which account
	 * @param AccountName //Account
	 * @param FamilyId	//which family
	 * @return			//return group list as JSON.
	 */
	@GET
	@Produces("application/json")
	@Path("/GetAllGroupsAsList/{AccountId}/{AccountName}/{FamilyId}")
	public Response GetAllGroupsAsList(@PathParam("AccountId") int AccountId,
			                           @PathParam("AccountName")String AccountName,
			                           @PathParam("FamilyId")int FamilyId) {
		/*
		 * http://localhost:8080/REST1/DeviceGroup/GetAllGroupsAsList/1/test@gmail.com/1
		 */
		DeviceGroup deviceGroup = new DeviceGroup();
		String response = deviceGroup.getAllGroupsAsList(AccountId,AccountName, FamilyId);
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Remove device in the Device Group. 
	 * @param AccountId //whihc account. for improving security
	 * @param AccountName //Account. for improving security		
	 * @param RowId		//which item. 
	 * @return			//return true(as JSON) if successful.
	 */
	@GET
	@Produces("application/json")
	@Path("/RemoveDevice/{AccountId}/{AccountName}/{RowId}")
	public Response RemoveDevice(@PathParam("AccountId")int AccountId,
			                     @PathParam("AccountName")String AccountName,
			                     @PathParam("RowId")int RowId) {
		/*
		 * http://localhost:8080/REST1/DeviceGroup/RemoveDevice/1/test@gmail.com/1
		 */
		DeviceGroup deviceGroup = new DeviceGroup();
		String response = deviceGroup.removeDevice(AccountId,AccountName,RowId);
		return Response.status(200).entity(response).build();
	}

	/**
	 * Remove device group.
	 * @param AccountId  //which account.
	 * @param AccountName //Account
	 * @param RowId	    //which group. so related group Id.
	 * @return			//return true(as JSON) if successful.
	 */
	@GET
	@Produces("application/json")
	@Path("/RemoveGroup/{AccountId}/{AccountName}/{RowId}")
	public Response RemoveGroup(@PathParam("AccountId")int AccountId,
								@PathParam("AccountName")String AccountName,
								@PathParam("RowId")int RowId) {
		/*
		 * http://localhost:8080/REST1/DeviceGroup/RemoveGroup/1/test@gmail.com/1
		 * 
		 * NOT:Group silinince grup içinde bulunan tüm cihazları gruptan kaldır. yani tblDeviceGroupSub altında ilgili gruba ait tüm itemleri sil.
		 */
		DeviceGroup deviceGroup = new DeviceGroup();
		String response = deviceGroup.removeGroup(AccountId,AccountName,RowId);
		return Response.status(200).entity(response).build();
	}
	
}
