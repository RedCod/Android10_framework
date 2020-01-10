package com.service.main;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/Scenario")
public class WS_Scenario {

	
	/**
	 * Add scenario.
	 * @param AccountId	 		//which account.
	 * @param FamilyId			//which family.
	 * @param ScenarioName		//scenario name.
	 * @param CoverImage		//cover image index value as int.
	 * @param ShowOnMainPage	//show on main page. 1 or 0.
	 * @param ItemSort			//sorting number in scenario list. //listede kaçıncı sırada yer aldığı. 
	 * @return					//return ScenarioId (tblScenario->Id) as JSON if adding is successful else return false. Because we will use this value for adding scenario sub process to tblScenarioSub.
	 */
	@GET
	@Produces("application/json")
	@Path("/AddScenario/{AccountId}/{FamilyId}/{ScenarioName}/{CoverImage}/{ShowOnMainPage}/{ItemSort}")
	public Response AddScenario(@PathParam("AccountId")int AccountId,
								@PathParam("FamilyId")int FamilyId,
								@PathParam("ScenarioName")String ScenarioName,
								@PathParam("CoverImage")int CoverImage,
								@PathParam("ShowOnMainPage")int ShowOnMainPage,
								@PathParam("ItemSort")int ItemSort) {
		/*
		 * 
		 */
		String response = "";
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Add scenario action to "tblScenarioSub" table.
	 * @param AccountId			//which account.
	 * @param ScenarioId		//which scenario. this value get "tblScenario->Id".
	 * @param ActionType		//determines the type of action. this value should be: "device","automation","time_lapse",...
	 * @param DeviceId			//which device if added device.
	 * @param DeviceSwitch		//device switch state:ON/OFF  if added device.
	 * @param AutomationId		//automation Id value. if added automation.
	 * @param TimeLapseValue	//time lapse value . if added TimeLapse.
	 * @param ItemSort			//Item sort value. Sort number in the action list.
	 * @return					//return true(as JSON) if action add to tblScenarioSub table.
	 */
	@GET
	@Produces("application/json")
	@Path("/AddScenarioAction/{AccountId}/{ScenarioId}/{ActionType}/{DeviceId}/{DeviceSwitch}/{AutomationId}/{TimeLapseValue}/{ItemSort}")
	public Response AddScenarioAction(@PathParam("AccountId")int AccountId,
									  @PathParam("ScenarioId")int ScenarioId,
									  @PathParam("ActionType")String ActionType,
									  @PathParam("DeviceId")int DeviceId,
									  @PathParam("DeviceSwitch")String DeviceSwitch,
									  @PathParam("AutomationId")int AutomationId,
									  @PathParam("TimeLapseValue")String TimeLapseValue,
									  @PathParam("ItemSort")int ItemSort) {
		/*
		 * 
		 */
		String response = "";
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Edit Scenario.
	 * @param ScenarioId	 //which scenario.
	 * @param ScenarioName	 //Scenario name.
	 * @param CoverImage	 //cover image index value as int.
	 * @param ShowOnMainPage //show on main page. 1 or 0.
	 * @return				 //return true(as JSON) if edit scenario successful.
	 */
	@GET
	@Produces("application/json")
	@Path("/EditScenario/{ScenarioId}/{ScenarioName}/{CoverImage}/{ShowOnMainPage}")
	public Response EditScenario(@PathParam("ScenarioId")int ScenarioId,
								 @PathParam("ScenarioName")String ScenarioName,
								 @PathParam("CoverImage")int CoverImage,
								 @PathParam("ShowOnMainPage")int ShowOnMainPage) {
		/*
		 * 
		 */
		String response = "";
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Edit scenario Item Sort.
	 * @param ScenarioId	//which scenario.
	 * @param ItemSort		//new ItemSort value
	 * @return				//return true(as JSON) if edit scenario ItemSort successful.
	 */
	@GET
	@Produces("application/json")
	@Path("/EditScenarioItemSort/{ScenarioId}/{ItemSort}")
	public Response EditScenarioItemSort(@PathParam("ScenarioId")int ScenarioId,
										 @PathParam("ItemSort")int ItemSort) {
		/*
		 * 
		 */
		String response = "";
		return Response.status(200).entity(response).build();
	}
	
	
	
	/**
	 * Edit Scenario Action.
	 * @param ActionId		//which action.
	 * @param ActionType	//determines the type of action. this value should be: "device","automation","time_lapse",...
	 * @param DeviceId		//device Id
	 * @param DeviceSwitch	//device switch state:ON/OFF  if device is editing.
	 * @param AutomationId	//automation Id value. if automation is editing.
	 * @param TimeLapseValue //time lapse value. if TimeLapse is editing.
	 * @param ItemSort		 //Item sort value. Get sort number in the action list.
	 * @return				 //return true(as JSON) if edit scenario action successful.
	 */
	@GET
	@Produces("application/json")
	@Path("/EditScenarioAction/{ActionId}/{ActionType}/{DeviceId}/{DeviceSwitch}/{AutomationId}/{TimeLapseValue}/{ItemSort}")
    public Response EditScenarioAction(@PathParam("ActionId")int ActionId,
    								   @PathParam("ActionType")String ActionType,
    								   @PathParam("DeviceId")int DeviceId,
    								   @PathParam("DeviceSwitch")String DeviceSwitch,
    								   @PathParam("AutomationId")int AutomationId,
    								   @PathParam("TimeLapseValue")String TimeLapseValue,
    								   @PathParam("ItemSort")int ItemSort) {
		/*
		 * 
		 */
		String response = "";
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Get All Scenario as Items.
	 * @param AccountId	//which account
	 * @param FamilyId  //which family
	 * @return			//return All Scenario list as JSON.
	 */
	@GET
	@Produces("application/json")
	@Path("/GetAllScenarioAsList/{AccountId}")
	public Response GetAllScenarioAsList(@PathParam("AccountId")int AccountId,
										 @PathParam("FamilyId")int FamilyId) {
		/*
		 * 
		 */
		String response = "";
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Get scenario all actions Items. (Senaryoya ait tüm işlemleri liste olarak getir)
	 * @param ScenarioId	//which scenario.
	 * @return				//return scenario actions list as JSON.
	 */
	@GET
	@Produces("application/json")
	@Path("/GetScenarioActions/{ScenarioId}")
	public Response GetScenarioActions(@PathParam("ScenarioId")int ScenarioId) {
		/*
		 * 
		 */
		String response = "";
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Remove Scenario. Delete all related actions when scenario is deleted. (tblScenario,tblScenarioSub)
	 * @param ScenarioId //which scenario.
	 * @return			//return true(as JSON) if remove scenario successful.
	 */
	@GET
	@Produces("application/json")
	@Path("/RemoveScenario/{ScenarioId}")
	public Response RemoveScenario(@PathParam("ScenarioId")int ScenarioId) {
		/*
		 * 
		 */
		String response = "";
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Remove scenario action item. 
	 * @param ActionId	//which action
	 * @return			//return true(as JSON) if remove action successful.
	 */
	@GET
	@Produces("application/json")
	@Path("/RemoveScenarioAction/{ActionId}")
	public Response RemoveScenarioAction(@PathParam("ActionId")int ActionId) {
		/*
		 * 
		 */
		String response = "";
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Perform scenario. Senaryoyu gerçekleştir-işlet. (Bu süreç Server yazılımı tarafından "mqtt işlet motoru" olarak oluşturulmuş modül tarafından işletilecek.)
	 * @param ScenarioId	//which scenario.
	 * @return				//return Scenario Actions List as JSON. (senaryoda yer alan işlem itemleri liste olarak return et)
	 */
	@GET
	@Produces("application/json")
	@Path("/PerformScenario/{ScenarioId}")
	public Response PerformScenario(@PathParam("ScenarioId")int ScenarioId) {
		/*
		 * 
		 */
		String response = "";
		return Response.status(200).entity(response).build();
	}
}
