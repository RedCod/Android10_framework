package com.service.main;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.service.sub.Scenario;

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
	 * @return					//return ScenarioId (tblScenario->Id) as JSON if adding is successful.Else return false. Because we will use this value for adding scenario sub process to tblScenarioSub.
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
		 * http://localhost:8080/REST1/Scenario/AddScenario/1/1/Senaryo1/15/1/1
		 */
		Scenario scenario = new Scenario();
		String response = scenario.addScenario(AccountId, FamilyId, ScenarioName, CoverImage, ShowOnMainPage, ItemSort);
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Add scenario action to "tblScenarioSub" table.
	 * @param AccountId			//which account.
	 * @param ScenarioId		//which scenario. this value get "tblScenario->Id".
	 * @param ActionType		//determines the type of action. this value should be: "device","automation","time_lapse",...
	 * @param DeviceId			//which device if added device.
	 * @param DeviceVirtualAddress //device virtual address.  produce: maccaddres+deviceId
	 * @param DeviceSwitch		//device switch state:ON/OFF  if added device.
	 * @param AutomationId		//automation Id value. if added automation.
	 * @param TimeLapseValue	//time lapse value . if added TimeLapse.
	 * @param ItemSort			//Item sort value. Sort number in the action list.
	 * @return					//return true(as JSON) if action add to tblScenarioSub table.
	 */
	@GET
	@Produces("application/json")
	@Path("/AddScenarioAction/{AccountId}/{ScenarioId}/{ActionType}/{DeviceId}/{DeviceVirtualAddress}/{DeviceSwitch}/{AutomationId}/{TimeLapseValue}/{ItemSort}")
	public Response AddScenarioAction(@PathParam("AccountId")int AccountId,
									  @PathParam("ScenarioId")int ScenarioId,
									  @PathParam("ActionType")String ActionType,
									  @PathParam("DeviceId")int DeviceId,
									  @PathParam("DeviceVirtualAddress")String DeviceVirtualAddress,
									  @PathParam("DeviceSwitch")String DeviceSwitch,
									  @PathParam("AutomationId")int AutomationId,
									  @PathParam("TimeLapseValue")String TimeLapseValue,
									  @PathParam("ItemSort")int ItemSort) {
		/*
		 * http://localhost:8080/REST1/Scenario/AddScenarioAction/1/1/device/44/virtualaddress/ON/0/0/1
		 */
		Scenario scenario = new Scenario();
		String response = scenario.addScenarioAction(AccountId, ScenarioId, ActionType, DeviceId, DeviceVirtualAddress, DeviceSwitch, AutomationId, TimeLapseValue, ItemSort);
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Edit Scenario.
	 * @param ScenarioRowId	 //which scenario.
	 * @param ScenarioName	 //Scenario name.
	 * @param CoverImage	 //cover image index value as int.
	 * @param ShowOnMainPage //show on main page. 1 or 0.
	 * @return				 //return true(as JSON) if edit scenario successful.
	 */
	@GET
	@Produces("application/json")
	@Path("/EditScenario/{ScenarioRowId}/{ScenarioName}/{CoverImage}/{ShowOnMainPage}")
	public Response EditScenario(@PathParam("ScenarioRowId")int ScenarioRowId,
								 @PathParam("ScenarioName")String ScenarioName,
								 @PathParam("CoverImage")int CoverImage,
								 @PathParam("ShowOnMainPage")int ShowOnMainPage) {
		/*
		 * http://localhost:8080/REST1/Scenario/EditScenario/2/Senaryo2/16/1
		 */
		Scenario scenario = new Scenario();
		String response = scenario.editScenario(ScenarioRowId, ScenarioName, CoverImage, ShowOnMainPage);
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Edit scenario Item Sort.
	 * @param ScenarioRowId	//which scenario.
	 * @param ItemSort		//new ItemSort value
	 * @return				//return true(as JSON) if edit scenario ItemSort successful.
	 */
	@GET
	@Produces("application/json")
	@Path("/EditScenarioItemSort/{ScenarioRowId}/{ItemSort}")
	public Response EditScenarioItemSort(@PathParam("ScenarioRowId")int ScenarioRowId,
										 @PathParam("ItemSort")int ItemSort) {
		/*
		 * http://localhost:8080/REST1/Scenario/EditScenarioItemSort/2/2
		 */
		Scenario scenario = new Scenario();
		String response = scenario.editScenarioItemSort(ScenarioRowId, ItemSort);
		return Response.status(200).entity(response).build();
	}
	
	
	
	/**
	 * Edit Scenario Action.
	 * @param ActionId		//which action.
	 * @param ActionType	//determines the type of action. this value should be: "device","automation","time_lapse",...
	 * @param DeviceVirtualAddress //device virtual address.  produce: maccaddres+deviceId
	 * @param DeviceSwitch	//device switch state:ON/OFF  if device is editing.
	 * @param AutomationId	//automation Id value. if automation is editing.
	 * @param TimeLapseValue //time lapse value. if TimeLapse is editing.
	 * @param ItemSort		 //Item sort value. Get sort number in the action list.
	 * @return				 //return true(as JSON) if edit scenario action successful.
	 */
	@GET
	@Produces("application/json")
	@Path("/EditScenarioAction/{ActionId}/{DeviceVirtualAddress}/{DeviceSwitch}/{AutomationId}/{TimeLapseValue}/{ItemSort}")
    public Response EditScenarioAction(@PathParam("ActionId")int ActionId,
    								   @PathParam("DeviceVirtualAddress")String DeviceVirtualAddress,
    								   @PathParam("DeviceSwitch")String DeviceSwitch,
    								   @PathParam("AutomationId")int AutomationId,
    								   @PathParam("TimeLapseValue")String TimeLapseValue,
    								   @PathParam("ItemSort")int ItemSort) {
		/*
		 * http://localhost:8080/REST1/Scenario/EditScenarioAction/2/virtualaddress/OFF/0/0/2
		 */
		Scenario scenario = new Scenario();
		String response = scenario.editScenarioAction(ActionId,DeviceVirtualAddress, DeviceSwitch, AutomationId, TimeLapseValue, ItemSort);
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Get All Scenario as Items.(tüm senaryoları çek.senaryoları listelemek için)
	 * @param AccountId	//which account
	 * @param FamilyId  //which family
	 * @return			//return All Scenario list as JSON.
	 */
	@GET
	@Produces("application/json")
	@Path("/GetAllScenarioAsList/{AccountId}/{FamilyId}")
	public Response GetAllScenarioAsList(@PathParam("AccountId")int AccountId,
										 @PathParam("FamilyId")int FamilyId) {
		/*
		 * http://localhost:8080/REST1/Scenario/GetAllScenarioAsList/1/1
		 */
		Scenario scenario = new Scenario();
		String response = scenario.getAllScenarioAsList(AccountId, FamilyId);
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Get scenario all actions Items. (Senaryoya ait tüm işlemleri liste olarak getir)
	 * @param ScenarioId	//which scenario.
	 * @return				//return scenario actions list as JSON.
	 */
	@GET
	@Produces("application/json")
	@Path("/GetScenarioActionsAsList/{ScenarioId}")
	public Response GetScenarioActionsAsList(@PathParam("ScenarioId")int ScenarioId) {
		/*
		 * http://localhost:8080/REST1/Scenario/GetScenarioActionsAsList/1
		 */
		Scenario scenario = new Scenario();
		String response = scenario.getScenarioActionsAsList(ScenarioId);
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Remove Scenario. Delete all related actions when scenario is deleted. (tblScenario,tblScenarioSub)
	 * @param ScenarioRowId //which scenario.
	 * @return			//return true(as JSON) if remove scenario is successful.
	 */
	@GET
	@Produces("application/json")
	@Path("/RemoveScenario/{ScenarioRowId}")
	public Response RemoveScenario(@PathParam("ScenarioRowId")int ScenarioRowId) {
		/*
		 * http://localhost:8080/REST1/Scenario/RemoveScenario/3
		 */
		Scenario scenario = new Scenario();
		String response = scenario.removeScenario(ScenarioRowId);
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Remove scenario action item. 
	 * @param ActionRowId	//which action
	 * @return			//return true(as JSON) if remove action is successful.
	 */
	@GET
	@Produces("application/json")
	@Path("/RemoveScenarioAction/{ActionRowId}")
	public Response RemoveScenarioAction(@PathParam("ActionRowId")int ActionRowId) {
		/*
		 * http://localhost:8080/REST1/Scenario/RemoveScenarioAction/3
		 */
		Scenario scenario = new Scenario();
		String response = scenario.removeScenarioAction(ActionRowId);
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Perform scenario. Senaryoyu gerçekleştir-işlet. (Bu süreç Server yazılımı tarafından "mqtt işlet motoru" olarak oluşturulmuş modül tarafından işletilecek.)
	 * @param ScenarioId	//which scenario.
	 * @return				//return Scenario Actions List as JSON. (senaryoda yer alan işlem itemleri liste olarak return et.)
	 */
	@GET
	@Produces("application/json")
	@Path("/PerformScenario/{ScenarioId}")
	public Response PerformScenario(@PathParam("ScenarioId")int ScenarioId) {
		/*
		 * http://localhost:8080/REST1/Scenario/PerformScenario/1
		 */
		Scenario scenario = new Scenario();
		String response = scenario.performScenario(ScenarioId);
		return Response.status(200).entity(response).build();
	}
}