package com.service.main;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/Automation")
public class WS_Automation {

///ADD:@{	
	/**
	 * Add automation.
	 * @param AccountId				//which account
	 * @param FamilyId				//which family
	 * @param AutomationName		//automation name
	 * @param CoverImage			//cover image index value as int
	 * @param AutomationCondition	//"all condition" OR "any condition". Tüm koşullar karşılandığında VEYA Koşullardan herhangi biri karşılandığında.
	 * @param ValidTimePeriod		//TamGün=24 saat, Gün=gündoğumdan günbatımına kadar,Gece=günbatımından gündoğumuna kadar,Özelleştir=12:00-15:14
	 * @param ItemSort				//sorting number in automation list. //otomasyonun listede kaçıncı sırada yer aldığı. 
	 * @return						//return AutomationId(tblAutomation->Id) as JSON if adding is successful.Else return false. Because we will use this value for adding automation "conditions" and "Action" process to other table related to tblAutomation.
	 */
	@GET
	@Produces("application/json")
	@Path("/AddAutomation/{AccountId}/{FamilyId}/{AutomationName}/{CoverImage}/{AutomationCondition}/{ValidTimePeriod}/{CurrentCity}/{ItemSort}/{Monday}/{Tuesday}/{Wednesday}/{Thursday}/{Friday}/{Saturday}/{Sunday}")
	public Response AddAutomation(@PathParam("AccountId")int AccountId,
								  @PathParam("FamilyId")int FamilyId,
								  @PathParam("AutomationName")String AutomationName,
								  @PathParam("CoverImage")int CoverImage,
								  @PathParam("AutomationCondition")String AutomationCondition,
								  @PathParam("ValidTimePeriod")String ValidTimePeriod,
								  @PathParam("CurrentCity")String CurrentCity,
								  @PathParam("ItemSort")int ItemSort,
								  @PathParam("Monday")int Monday,
								  @PathParam("Tuesday")int Tuesday,
								  @PathParam("Wednesday")int Wednesday,
								  @PathParam("Thursday")int Thursday,
								  @PathParam("Friday")int Friday,
								  @PathParam("Saturday")int Saturday,
								  @PathParam("Sunday")int Sunday) {
		String response = ""; //use synchronized
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Add automation condition to "tblAutomationCondition" table.
	 * @param AccountId			//which account
	 * @param AutomationId		//which automation
	 * @param ConditionType		//wich condition. "koşul tipi. örn:temperature,humidity,weather,air_quality,etc."
	 * @param ConditionValue	//condition value. örn: bulutlu,güneşli,karlı,etc. Bu değer "sabit değer" olarak eklenecek.Bu şartın Mobil app tarafından karşılığı dile uygun gösterilecek. 
	 * @param DeviceId			//which device if added device.
	 * @param DeviceSwitch		//device switch state:ON/OFF if added device. Use the device status as a condition.
	 * @param CurrentCity		//city where the condition is met.
	 * @return					//return true(as JSON) if condition add to tblAutomationCondition table.
	 */
	@GET
	@Produces("application/json")
	@Path("/AddCondition/{AccountId}/{AutomationId}/{ConditionType}/{ConditionValue}/{DeviceId}/{DeviceSwitch}/{CurrentCity}")
	public Response AddCondition(@PathParam("AccountId")int AccountId,
								 @PathParam("AutomationId")int AutomationId,
								 @PathParam("ConditionType")String ConditionType,
								 @PathParam("ConditionValue")String ConditionValue,
								 @PathParam("DeviceId")int DeviceId,
								 @PathParam("DeviceSwitch")String DeviceSwitch,
								 @PathParam("CurrentCity")String CurrentCity) {
		String response = "";
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Add automation condition schedule repeat.(if schedule is added as a condition)
	 * @param AccountId		//which account. this value get "tblAccount->Id"
	 * @param AutomationId	//wich automation. This value get "tblAutomation->Id"
	 * @param ConditionId	//which condition. this value get "tblAutomationCondition->Id"
	 * @param Monday	    //day to repeat for Monday. 1 or 0
	 * @param Tuesday		//day to repeat for Tuesday. 1 or 0
	 * @param Wednesday		//day to repeat for Wednesday. 1 or 0
	 * @param Thursday		//day to repeat for Thursday. 1 or 0
	 * @param Friday		//day to repeat for Friday. 1 or 0
	 * @param Saturday		//day to repeat for Saturday. 1 or 0
	 * @param Sunday		//day to repeat for Sunday. 1 or 0
	 * @return				//return true(as JSON) if condition schedule repeat add to tblAutomationConditionScheduleRepeat table.
	 */
	@GET
	@Produces("application/json")
	@Path("/AddConditionScheduleRepeat/{AccountId}/{AutomationId}/{ConditionId}/{Monday}/{Tuesday}/{Wednesday}/{Thursday}/{Friday}/{Saturday}/{Sunday}")
	public Response AddConditionScheduleRepeat(@PathParam("AccountId")int AccountId,
						  					   @PathParam("AutomationId")int AutomationId,
											   @PathParam("ConditionId")int ConditionId,
											   @PathParam("Monday")int Monday,
											   @PathParam("Tuesday")int Tuesday,
											   @PathParam("Wednesday")int Wednesday,
											   @PathParam("Thursday")int Thursday,
											   @PathParam("Friday")int Friday,
											   @PathParam("Saturday")int Saturday,
											   @PathParam("Sunday")int Sunday) {
		String response = "";
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Add automation action to "tblAutomationOperation" table.
	 * @param AccountId				//which account
	 * @param AutomationId			//wich automation. This value get "tblAutomation->Id"
	 * @param ActionType			//determines the type of action. this value should be: "device","automation","time_lapse",...
	 * @param DeviceId				//which device if added device.
	 * @param DeviceVirtualAddress	//device virtual address. produce: macaddress+deviceId
	 * @param DeviceSwitch			//device switch state:ON/OFF  if added device.
	 * @param AutomationIdAssign	//automation id value. if added automation.(Action olarak bir otomasyon eklenirse)
	 * @param TimeLapseValue		//time lapse value. iff added TimeLapse
	 * @param ItemSort				//Item sort value. Sort number in the action list.
	 * @return						//return true(as JSON) if action add to "tblAutomationOperation" table.
	 */
	@GET
	@Produces("application/json")
	@Path("/AddAction/{AccountId}/{AutomationId}/{ActionType}/{DeviceId}/{DeviceVirtualAddress}/{DeviceSwitch}/{AutomationIdAssign}/{TimeLapseValue}/{ItemSort}")
	public Response AddAction(@PathParam("AccountId")int AccountId,
				              @PathParam("AutomationId")int AutomationId,
							  @PathParam("ActionType")String ActionType,
							  @PathParam("DeviceId")int DeviceId,
							  @PathParam("DeviceVirtualAddress")String DeviceVirtualAddress,
							  @PathParam("DeviceSwitch")String DeviceSwitch,
							  @PathParam("AutomationIdAssign")int AutomationIdAssign,
							  @PathParam("TimeLapseValue")String TimeLapseValue,
							  @PathParam("ItemSort")int ItemSort) {
		String response = "";
		return Response.status(200).entity(response).build();
	}
///ADD:@}	
//////////////////////////////////////////////////////////////////////////////////////////////////
///EDiT:@{
	/**
	 * Edit Automation
	 * @param AutomationId			//which automation. so Id
	 * @param AutomationName		//automation name
	 * @param CoverImage			//cover image index value as int.
	 * @param AutomationCondition	//automation condition. all OR any
	 * @param ValidTimePeriod		//valid time period (Geçerlilik zaman bölümü)
	 * @param CurrentCity			//current city
	 * @param Monday				//day to repeat for Monday. 1 or 0
	 * @param Tuesday				//day to repeat for Tuesday. 1 or 0
	 * @param Wednesday				//day to repeat for Wednesday. 1 or 0
	 * @param Thursday				//day to repeat for Thursday. 1 or 0
	 * @param Friday				//day to repeat for Friday. 1 or 0
	 * @param Saturday				//day to repeat for Saturday. 1 or 0
	 * @param Sunday				//day to repeat for Sunday. 1 or 0
	 * @return						//return true(as JSON) if automation edit successful
	 */
	@GET
	@Produces("application/json")
	@Path("/EditAutomation/{AutomationId}/{AutomationName}/{CoverImage}/{AutomationCondition}/{ValidTimePeriod}/{CurrentCity}/{Monday}/{Tuesday}/{Wednesday}/{Thursday}/{Friday}/{Saturday}/{Sunday}")
	public Response EditAutomation(@PathParam("AutomationId")int AutomationId,
								   @PathParam("AutomationName")String AutomationName,
								   @PathParam("CoverImage")int CoverImage,
								   @PathParam("AutomationCondition")String AutomationCondition,
								   @PathParam("ValidTimePeriod")String ValidTimePeriod,
								   @PathParam("CurrentCity")String CurrentCity,
								   @PathParam("Monday")int Monday,
								   @PathParam("Tuesday")int Tuesday,
								   @PathParam("Wednesday")int Wednesday,
								   @PathParam("Thursday")int Thursday,
								   @PathParam("Friday")int Friday,
								   @PathParam("Saturday")int Saturday,
								   @PathParam("Sunday")int Sunday) {
		String response = ""; //use synchronized
		return Response.status(200).entity(response).build();
	}
	
    /**
     * Activate automation. activate or deactivate
     * @param AutomationId //which automation
     * @param State		   //state activate=1,OR deactivate = 0
     * @return
     */
	@GET
	@Produces("appliction/json")
	@Path("/ActivateAutomation/{AutomationId}/{State}")
	public Response ActivateAutomation(@PathParam("AutomationId")int AutomationId,
									   @PathParam("State")int State) {
		/*
		 * 
		 */
		String response = "";
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Edit automation Item sort.
	 * @param AutomationId	//which automation
	 * @param ItemSort		//new item sort value.
	 * @return				//return true(as JSON) if edit automation ItemSort successful. 
	 */
	@GET
	@Produces("application/json")
	@Path("/EditAutomationItemSort/{AutomationId}/{ItemSort}")
	public Response EditAutomationItemSort(@PathParam("AutomationId")int AutomationId,
										   @PathParam("ItemSort")int ItemSort) {
		String response = "";
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Edit automation condition.
	 * @param ConditionId	 //which condition. so Id of tblAutomationCondition
	 * @param ConditionType	 //determines the type of condition. "koşul tipi. örn:temperature,humidity,weather,air_quality,etc."
	 * @param ConditionValue //condition value. örn: bulutlu,güneşli,karlı,etc. Bu değer "sabit değer" olarak eklenecek.Bu şartın Mobil app tarafından karşılığı dile uygun gösterilecek.
	 * @param DeviceSwitch	 //device switch state: ON/OFF if device is editing. (koşul olarak bir cihaz durumu eklenmişse)
	 * @param CurrentCity	 //current city.
	 * @return				 //return true(as JSON) if edit automation successful
	 */
	@GET
	@Produces("application/json")
	@Path("/EditCondition/{ConditionId}/{ConditionType}/{ConditionValue}/{DeviceSwitch}/{CurrentCity}")
	public Response EditCondition(@PathParam("ConditionId")int ConditionId,
				                  @PathParam("ConditionType")String ConditionType,
						          @PathParam("ConditionValue")String ConditionValue,
						          @PathParam("DeviceSwitch")String DeviceSwitch,
						          @PathParam("CurrentCity")String CurrentCity) {
		String response = "";
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Edit automation condition "schedule(program/zamanlama)" repeat.(if schedule is added as a condition)
	 * @param ConditionId	//which condition. this value get "tblAutomationCondition->Id"
	 * @param Monday	    //day to repeat for Monday. 1 or 0
	 * @param Tuesday		//day to repeat for Tuesday. 1 or 0
	 * @param Wednesday		//day to repeat for Wednesday. 1 or 0
	 * @param Thursday		//day to repeat for Thursday. 1 or 0
	 * @param Friday		//day to repeat for Friday. 1 or 0
	 * @param Saturday		//day to repeat for Saturday. 1 or 0
	 * @param Sunday		//day to repeat for Sunday. 1 or 0
	 * @return				//return true(as JSON) if condition schedule repeat add to tblAutomationConditionScheduleRepeat table.
	 */
	@GET
	@Produces("application/json")
	@Path("/EditConditionScheduleRepeat/{ConditionId}/{Monday}/{Tuesday}/{Wednesday}/{Thursday}/{Friday}/{Saturday}/{Sunday}")
	public Response EditConditionScheduleRepeat(@PathParam("ConditionId")int ConditionId,
														 @PathParam("Monday")int Monday,
														 @PathParam("Tuesday")int Tuesday,
														 @PathParam("Wednesday")int Wednesday,
														 @PathParam("Thursday")int Thursday,
														 @PathParam("Friday")int Friday,
														 @PathParam("Saturday")int Saturday,
														 @PathParam("Sunday")int Sunday) {
		String response = "";
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Edit automation action to "tblAutomationOperation" table.
	 * @param ActionId				//which action. so Id of tblAutomationOperation table.
	 * @param DeviceVirtualAddress	//device virtual address. produce: macaddress+deviceId
	 * @param DeviceSwitch			//device switch state:ON/OFF  if added device.
	 * @param AutomationIdAssign	//automation id value. if added automation.(Action olarak bir otomasyon eklenirse)
	 * @param TimeLapseValue		//time lapse value. iff added TimeLapse
	 * @param ItemSort				//Item sort value. Sort number in the action list.
	 * @return						//return true(as JSON) if action add to "tblAutomationOperation" table.
	 */
	@GET
	@Produces("application/json")
	@Path("/EditAction/{ActionId}/{DeviceVirtualAddress}/{DeviceSwitch}/{AutomationIdAssign}/{TimeLapseValue}/{ItemSort}")
	public Response EditAction(@PathParam("ActionId")int ActionId,
							   @PathParam("DeviceVirtualAddress")String DeviceVirtualAddress,
							   @PathParam("DeviceSwitch")String DeviceSwitch,
							   @PathParam("AutomationIdAssign")int AutomationIdAssign,
							   @PathParam("TimeLapseValue")String TimeLapseValue,
							   @PathParam("ItemSort")int ItemSort) {
		String response = "";
		return Response.status(200).entity(response).build();
	}

	
///EDiT:@}	
////////////////////////////////////////////////////////////////////////////////////////////////// 
///GET:@{
	
	/**
	 * Get all automation as Items.(tüm otomasyonları listele)
	 * @param AccountId	//which account
	 * @param FamilyId	//which family
	 * @return			//return all automation list as JSON.
	 */
	@GET
	@Produces("application/json")
	@Path("/GetAllAutomationAsList/{AccountId}/{FamilyId}")
	public Response GetAllAutomationAsList(@PathParam("AccountId")int AccountId,
										   @PathParam("FamilyId")int FamilyId) {
		String response = "";
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Get automation all conditions Items.(otomasyona ait tüm "condition(şartlarları)" liste olarak getir. 
	 * @param AutomationId	//which automation.
	 * @return				//return automation actions list as JSON.
	 */
	@GET
	@Produces("application/json")
	@Path("/GetAllConditionAsList/{AutomationId}")
	public Response GetAllConditionAsList(@PathParam("AutomationId")int AutomationId) {
		/*
		 * 
		 */
		String response = "";
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Get one automation conditions "schedule" repeat Item.(bir şarta yönelik "program tekrarla" değerlerini getir.)
	 * @param ConditionId	//which condition.
	 * @return				//return schedule repeat Item as JSON.
	 */
	@GET
	@Produces("application/json")
	@Path("/GetConditionScheduleRepeatItem/{ConditionId}")
	public Response GetConditionScheduleRepeatItem(@PathParam("ConditionId")int ConditionId) {
		/*
		 * 
		 */
		String response = "";
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Get all Automation actions Items as list.(tüm otomasyon actionsları listele)
	 * @param AutomationId	//which automation
	 * @return				//return all actions items as JSON.
	 */
	@GET
	@Produces("application/json")
	@Path("/GetAllActionAsList/{AutomationId}")
	public Response GetAllActionAsList(@PathParam("AutomationId")int AutomationId) {
		/*
		 * 
		 */
		String response = "";
		return Response.status(200).entity(response).build();
	}
///GET:@}	
//////////////////////////////////////////////////////////////////////////////////////////////////	
///REMOVE:@{
	/**
	 * Remove Automation. Delete all related conditions,conditionschedulerepeat,actions when automation is deleted.
	 * Related tables: tblAutomation,tblAutomationCondition,tblAutomationConditionScheduleRepeat,tblAutomationOperation
	 * @param AutomationId	//which automation. 
	 * @return				//return true(as JSON) if remove automation is successful.
	 */
	@GET
	@Produces("application/json")
	@Path("/RemoveAutomation/{AutomationId}")
	public Response RemoveAutomation(@PathParam("AutomationId")int AutomationId) {
		/*
		 * 
		 */
		String response = "";
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Remove automation condition in tblAutomationCondition. And then delete related "condition schedule repeat" item in "tblAutomationConditionScheduleRepeat"
	 * @param ConditionId	//which condition
	 * @return				//return true(as JSON) if remove condition is successful.
	 */
	@GET
	@Produces("application/json")
	@Path("/RemoveCondition/{ConditionId}")
	public Response RemoveCondition(@PathParam("ConditionId")int ConditionId) {
		/*
		 * 
		 */
		String response = "";
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Remove automation action.
	 * @param ActionId	//which action
	 * @return			//return true(as JSON) if remove action is successful.
	 */
	@GET
	@Produces("application/json")
	@Path("/RemoveAction/{ActionId}")
	public Response RemoveAction(@PathParam("ActionId")int ActionId) {
		/*
		 * 
		 */
		String response = "";
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Perform automation. Otomasyonu gerçekleştir-işlet(tetikle).(Bu süreç Server yazılımı tarafından "mqtt işlet motoru" olarak oluşturulmuş modül tarafından işletilecek.)
	 * @param AutomationId	//which automaiton
	 * @return				//return automation Actions List as JSON. (otomasyonda yer alan işlem itemleri liste olarak return et.)
	 */
	@GET
	@Produces("application/json")
	@Path("/PerformAutomation/{AutomationId}")
	public Response PerformAutomation(@PathParam("AutomationId")int AutomationId) {
		/*
		 * 
		 */
		String response = "";
		return Response.status(200).entity(response).build();
	}
///REMOVE:@}	
	
	
	
}
