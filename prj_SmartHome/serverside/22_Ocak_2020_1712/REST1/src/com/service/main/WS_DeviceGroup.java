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
	 * @param FamilyId		 //which family
	 * @param GroupName		 //group name
	 * @param GroupLocation	 //group location. exp: Mutfak
	 * @return				 //return id value of added group row. (Eklenen kayda ait id değerini döndür.
	 */
	@GET
	@Produces("application/json")
	@Path("/AddGroup/{AccountId}/{FamilyId}/{GroupName}/{GroupLocation}")
	public Response AddGroup(@PathParam("AccountId")int AccountId,
							 @PathParam("FamilyId")int FamilyId,
			                 @PathParam("GroupName")String GroupName,
							 @PathParam("GroupLocation")String GroupLocation) {
		/*
		 * http://localhost:8080/REST1/DeviceGroup/AddGroup/1/1/GroupMutfak/Mutfak
		 */
		DeviceGroup deviceGroup = new DeviceGroup();
		String response = deviceGroup.addGroup(AccountId, FamilyId, GroupName, GroupLocation);
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Add device to device group(to tblDeviceGroupSub).  Use this method for "add" and "edit". (Hem cihaz eklemek için hem de güncellemek için kullanılacak.)
	 * @param AccountId				//which account
	 * @param GroupId				//related which group
	 * @param DeviceId				//device Id
	 * @param DeviceVirtualId    	//device virtual address. macaddres+deviceid
	 * @return						//return true(as JSON) if add is successful.
	 */
	@GET
	@Produces("application/json")
	@Path("/AddDevice/{AccountId}/{GroupId}/{DeviceId}/{DeviceVirtualId}")
	public Response AddDevice(@PathParam("AccountId")int AccountId,
							  @PathParam("GroupId")int GroupId,
							  @PathParam("DeviceId")int DeviceId,
							  @PathParam("DeviceVirtualId")String DeviceVirtualId) {
		/* 
		 * http://localhost:8080/REST1/DeviceGroup/AddDevice/1/1/5/macaddress1225
		 */
		DeviceGroup deviceGroup = new DeviceGroup();
		String response = deviceGroup.addDevice(AccountId, GroupId, DeviceId, DeviceVirtualId);
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Edit group name
	 * @param RowId	//related group row Id.
	 * @param GroupName	//group name
	 * @return			//return true(as JSON) if successful
	 */
	@GET
	@Produces("application/json")
	@Path("/EditGroupName/{RowId}/{GroupName}")
	public Response EditGroupName(@PathParam("RowId")int RowId,
								  @PathParam("GroupName")String GroupName) {
		/*
		 * http://localhost:8080/REST1/DeviceGroup/EditGroupName/5/GROUPMUTFAK
		 */
		DeviceGroup deviceGroup = new DeviceGroup();
		String response = deviceGroup.editGroupName(RowId, GroupName);
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Edit group location
	 * @param RowId		//related group row Id.
	 * @param GroupLocation	//group location exp:Mutfak
	 * @return				//return true(as JSON) if successful
	 */
	@GET
	@Produces("application/json")
	@Path("/EditGroupLocation/{RowId}/{GroupLocation}")
	public Response EditGroupLocation(@PathParam("RowId")int RowId,
									  @PathParam("GroupLocation")String GroupLocation) {
		/*
		 * http://localhost:8080/REST1/DeviceGroup/EditGroupLocation/1/Mutfak2
		 */
		DeviceGroup deviceGroup = new DeviceGroup();
		String response = deviceGroup.editGroupLocation(RowId, GroupLocation);
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Get content of group.(one group can accommodate a lot of device)
	 * @param GroupId	//which Group
	 * @return			//return devices list as JSON.
	 */
	@GET
	@Produces("application/json")
	@Path("/GetGroupDevicesAsList/{GroupId}")
	public Response GetGroupDevicesAsList(@PathParam("GroupId")int GroupId) {
		/*
		 * http://localhost:8080/REST1/DeviceGroup/GetGroupDevicesAsList/1
		 */
		DeviceGroup deviceGroup = new DeviceGroup();
		String response = deviceGroup.getGroupDevicesAsList(GroupId);
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Get all devicegroup as list according to the account and family.
	 * @param AccountId	//which account
	 * @param FamilyId	//which family
	 * @return			//return group list as JSON.
	 */
	@GET
	@Produces("application/json")
	@Path("/GetAllGroupsAsList/{AccountId}/{FamilyId}")
	public Response GetAllGroupsAsList(@PathParam("AccountId") int AccountId,
							     @PathParam("FamilyId")int FamilyId) {
		/*
		 * http://localhost:8080/REST1/DeviceGroup/GetAllGroupsAsList/1/1
		 */
		DeviceGroup deviceGroup = new DeviceGroup();
		String response = deviceGroup.getAllGroupsAsList(AccountId, FamilyId);
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Remove device in the Device Group. 
	 * @param RowId		//which item. 
	 * @return			//return true(as JSON) if successful.
	 */
	@GET
	@Produces("application/json")
	@Path("/RemoveDevice/{RowId}")
	public Response RemoveDevice(@PathParam("RowId")int RowId) {
		/*
		 * http://localhost:8080/REST1/DeviceGroup/RemoveDevice/1
		 */
		DeviceGroup deviceGroup = new DeviceGroup();
		String response = deviceGroup.removeDevice(RowId);
		return Response.status(200).entity(response).build();
	}

	/**
	 * Remove device group.
	 * @param RowId	    //which group. so related group Id.
	 * @return			//return true(as JSON) if successful.
	 */
	@GET
	@Produces("application/json")
	@Path("/RemoveGroup/{RowId}")
	public Response RemoveGroup(@PathParam("RowId")int RowId) {
		/*
		 * http://localhost:8080/REST1/DeviceGroup/RemoveGroup/1
		 * 
		 * NOT:Group silinince grup içinde bulunan tüm cihazları gruptan kaldır. yani tblDeviceGroupSub altında ilgili gruba ait tüm itemleri sil.
		 */
		DeviceGroup deviceGroup = new DeviceGroup();
		String response = deviceGroup.removeGroup(RowId);
		return Response.status(200).entity(response).build();
	}
	
}
