package com.service.main;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
 
@Path("/DeviceGroup")
public class WS_DeviceGroup {
	
	
	/**
	 * Add device group.
	 * @param AccountId		 //which account
	 * @param FamilyId		 //which family
	 * @param GroupName		 //group name
	 * @param GroupLocation	 //group location. exp: Mutfak
	 * @return				 //return id value of added group. 
	 */
	@GET
	@Produces("application/json")
	@Path("/AddGroup/{AccountId}/{FamilyId}/{GroupName}/{GroupLocation}")
	public Response AddGroup(@PathParam("AccountId")int AccountId,
							 @PathParam("FamilyId")int FamilyId,
			                 @PathParam("GroupName")String GroupName,
							 @PathParam("GroupLocation")String GroupLocation) {
		
		String response = "";//syncroniced
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Add device to device group(to tblDeviceGroupSub).  Use this method to add and edit. (Hem cihaz eklemek için hem de güncellemek için kullanılacak.)
	 * @param GroupId				//related which group
	 * @param DeviceId				//device Id
	 * @param DeviceVirtualAddress	//device virtual address.
	 * @return						//return true(as JSON) if add is successful.
	 */
	@GET
	@Produces("application/json")
	@Path("/AddDevice/{AccountId}/{GroupId}{DeviceId}/{DeviceVirtualAddress}")
	public Response AddDevice(@PathParam("AccountId")int AccountId,
							  @PathParam("GroupId")int GroupId,
							  @PathParam("DeviceId")int DeviceId,
							  @PathParam("DeviceVirtualAddress")String DeviceVirtualAddress) {
		String response = "";
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Edit group name
	 * @param GroupId	//related which group
	 * @param GroupName	//group name
	 * @return			//return true(as JSON) if successful
	 */
	@GET
	@Produces("application/json")
	@Path("/EditGroupName/{GroupId}/{GroupName}")
	public Response EditGroupName(@PathParam("GroupId")int GroupId,
								  @PathParam("GroupName")String GroupName) {
		String response = "";
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Edit group location
	 * @param GroupId		//which group
	 * @param GroupLocation	//group location exp:Mutfak
	 * @return				//return true(as JSON) if successful
	 */
	@GET
	@Produces("application/json")
	@Path("/EditGroupLocation/{GroupId}/{GroupLocation}")
	public Response EditGroupLocation(@PathParam("GroupId")int GroupId,
									  @PathParam("GroupLocation")String GroupLocation) {
		String response = "";
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Remove device in the Device Group. 
	 * @param SubId		//which item. (!DİKKAT! NOT: Burda item Id olarak tblDeviceGroupSub Id değerini kullanıyoruz. Ayrıca bu iş için DeviceId de kullanılabilir.
	 * @return			//return true(as JSON) if successful.
	 */
	@GET
	@Produces("application/json")
	@Path("/RemoveDevice/{SubId}")
	public Response RemoveDevice(@PathParam("SubId")int SubId) {
		String response = "";
		return Response.status(200).entity(response).build();
	}

	/**
	 * Remove device group.
	 * @param GroupId	//which group
	 * @return			//return true(as JSON) if successful.
	 */
	@GET
	@Produces("application/json")
	@Path("/RemoveGroup/{GroupId}")
	public Response RemoveGroup(@PathParam("GroupId")int GroupId) {
		/*
		 * NOT:Group silinince grup içinde bulunan tüm cihazları gruptan kaldır. yani tblDeviceGroupSub altında ilgili gruba ait tüm itemleri sil.
		 */
		String response = "";
		return Response.status(200).entity(response).build();
	}
	
}
