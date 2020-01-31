package com.service.main;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.service.sub.Automation;

@Path("/Automation")
public class WS_Automation {

///ADD:@{	
	/**
	 * Add automation
	 * @param AccountId				//which account
	 * @param AccountName			//account(email_or_phone).for improving security
	 * @param FamilyId				//wich family
	 * @param AutomationName		//automation name
	 * @param CoverImage			//cover image index value as int.
	 * @param AutomationCondition	//"all condition" OR "any condition". Tüm koşullar karşılandığında VEYA Koşullardan herhangi biri karşılandığında.
	 * @param ValidTimePeriod		//TamGün=24 saat, Gün=gündoğumdan günbatımına kadar,Gece=günbatımından gündoğumuna kadar,Özelleştir=12:00-15:14
	 * @param CurrentCity			//current city
	 * @param ItemSort				//sorting number in automation list. //otomasyonun listede kaçıncı sırada yer aldığı.
	 * @param Monday				//day to repeat for Monday. 1 or 0
	 * @param Tuesday				//day to repeat for Tuesday. 1 or 0
	 * @param Wednesday				//day to repeat for Wednesday. 1 or 0
	 * @param Thursday				//day to repeat for Thursday. 1 or 0
	 * @param Friday				//day to repeat for Friday. 1 or 0
	 * @param Saturday				//day to repeat for Saturday. 1 or 0
	 * @param Sunday				//day to repeat for Sunday. 1 or 0
	 * @return						//return AutomationId(tblAutomation->Id) as JSON if adding is successful.Else return false. Because we will use this value for adding automation "conditions" and "Action" process to other table related to tblAutomation.
	 */
	@GET
	@Produces("application/json")
	@Path("/AddAutomation/{AccountId}/{AccountName}/{FamilyId}/{AutomationName}/{CoverImage}/{AutomationCondition}/{ValidTimePeriod}/{CurrentCity}/{ItemSort}/{Monday}/{Tuesday}/{Wednesday}/{Thursday}/{Friday}/{Saturday}/{Sunday}")
	public Response AddAutomation(@PathParam("AccountId")int AccountId,
			                      @PathParam("AccountName")String AccountName,
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
		/*
		 * http://localhost:8080/REST1/Automation/AddAutomation/1/test@gmail.com/1/Otomasyon1/14/any/tamg%C3%BCn/istanbul/1/1/1/1/1/1/1/1
		 */
		Automation automation = new Automation();
		String response = automation.addAutomation(AccountId, AccountName, FamilyId, AutomationName, CoverImage, AutomationCondition, ValidTimePeriod, CurrentCity, ItemSort, Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday);
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Add automation condition to "tblAutomationCondition" table.
	 * @param AccountId			//which account
	 * @param AccountName		//account(email_or_phone).for improving security
	 * @param AutomationId		//which automation
	 * @param ConditionType		//wich condition. "koşul tipi. örn:temperature,humidity,weather,air_quality,etc."
	 * @param ConditionValue	//condition value. örn: bulutlu,güneşli,karlı,etc. Bu değer "sabit değer" olarak eklenecek.Bu şartın Mobil app tarafından karşılığı dile uygun gösterilecek. 
	 * @param DeviceId			//which device if added device.
	 * @param DeviceSwitch		//device switch state:ON/OFF if added device. Use the device status as a condition.
	 * @param CurrentCity		//city where the condition is met.
	 * @param Monday	       //day to repeat for Monday. 1 or 0
	 * @param Tuesday		   //day to repeat for Tuesday. 1 or 0
	 * @param Wednesday		   //day to repeat for Wednesday. 1 or 0
	 * @param Thursday		   //day to repeat for Thursday. 1 or 0
	 * @param Friday		  //day to repeat for Friday. 1 or 0
	 * @param Saturday		  //day to repeat for Saturday. 1 or 0
	 * @param Sunday		  //day to repeat for Sunday. 1 or 0
	 * @return				//return true(as JSON) if condition add to tblAutomationCondition table.
	 */
	@GET
	@Produces("application/json")
	@Path("/AddCondition/{AccountId}/{AccountName}/{AutomationId}/{ConditionType}/{ConditionValue}/{DeviceId}/{DeviceSwitch}/{CurrentCity}/{Monday}/{Tuesday}/{Wednesday}/{Thursday}/{Friday}/{Saturday}/{Sunday}")
	public Response AddCondition(@PathParam("AccountId")int AccountId,
								 @PathParam("AccountName")String AccountName,
								 @PathParam("AutomationId")int AutomationId,
								 @PathParam("ConditionType")String ConditionType,
								 @PathParam("ConditionValue")String ConditionValue,
								 @PathParam("DeviceId")int DeviceId,
								 @PathParam("DeviceSwitch")String DeviceSwitch,
								 @PathParam("CurrentCity")String CurrentCity,
								 @PathParam("Monday")int Monday,
								 @PathParam("Tuesday")int Tuesday,
								 @PathParam("Wednesday")int Wednesday,
								 @PathParam("Thursday")int Thursday,
								 @PathParam("Friday")int Friday,
								 @PathParam("Saturday")int Saturday,
								 @PathParam("Sunday")int Sunday) {
		/*
		 * http://localhost:8080/REST1/Automation/AddCondition/1/test@gmail.com/1/humidity/44/0/-/istanbul/0/0/0/0/0/0/0
		 */
		Automation automation = new Automation();
		String response = automation.addCondition(AccountId,AccountName, AutomationId, ConditionType, ConditionValue, DeviceId, DeviceSwitch, CurrentCity, Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday);
		return Response.status(200).entity(response).build();
	}
	
	
	/**
	 * Add automation action to "tblAutomationOperation" table.
	 * @param AccountId				//which account
	 * @param AccountName			//account(email_or_phone)
	 * @param AutomationId			//wich automation. This value get "tblAutomation->Id"
	 * @param ActionType			//determines the type of action. this value should be: "device","automation","time_lapse",...
	 * @param DeviceId				//which device if added device.
	 * @param MacAddress        	//device macc address. for produce mqtt topic: macaddress
	 * @param DeviceSwitch			//device switch state:ON/OFF  if added device.
	 * @param AutomationIdAssign	//automation id value. if added automation.(Action olarak bir otomasyon eklenirse)
	 * @param TimeLapseValue		//time lapse value. iff added TimeLapse
	 * @param ItemSort				//Item sort value. Sort number in the action list.
	 * @return						//return true(as JSON) if action add to "tblAutomationOperation" table.
	 */
	@GET
	@Produces("application/json")
	@Path("/AddAction/{AccountId}/{AccountName}/{AutomationId}/{ActionType}/{DeviceId}/{MacAddress}/{DeviceSwitch}/{AutomationIdAssign}/{TimeLapseValue}/{ItemSort}")
	public Response AddAction(@PathParam("AccountId")int AccountId,
							  @PathParam("AccountName")String AccountName,
				              @PathParam("AutomationId")int AutomationId,
							  @PathParam("ActionType")String ActionType,
							  @PathParam("DeviceId")int DeviceId,
							  @PathParam("MacAddress")String MacAddress,
							  @PathParam("DeviceSwitch")String DeviceSwitch,
							  @PathParam("AutomationIdAssign")int AutomationIdAssign,
							  @PathParam("TimeLapseValue")String TimeLapseValue,
							  @PathParam("ItemSort")int ItemSort) {
		/*
		 * http://localhost:8080/REST1/Automation/AddAction/1/test@gmail.com/1/device/1/macaddress/ON/0/0/1
		 */
		Automation automation = new Automation();
		String response = automation.addAction(AccountId,AccountName, AutomationId, ActionType, DeviceId, MacAddress, DeviceSwitch, AutomationIdAssign, TimeLapseValue, ItemSort);
		return Response.status(200).entity(response).build();
	}
///ADD:@}	
//////////////////////////////////////////////////////////////////////////////////////////////////
///EDiT:@{
	/**
	 * Edit Automation
	 * @param AccountId				//which account
	 * @param AccountName			//account(email_or_phone)
	 * @param AutomationId			//which automation. so Id
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
	@Path("/EditAutomation/{AccountId}/{AccountName}/{AutomationId}/{AutomationName}/{CoverImage}/{AutomationCondition}/{ValidTimePeriod}/{CurrentCity}/{Monday}/{Tuesday}/{Wednesday}/{Thursday}/{Friday}/{Saturday}/{Sunday}")
	public Response EditAutomation(@PathParam("AccountId")int AccountId,
								   @PathParam("AccountName")String AccountName,
			                       @PathParam("AutomationId")int AutomationId,
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
		/*
		 * http://localhost:8080/REST1/Automation/EditAutomation/1/test@gmail.com/1/Otomasyon_1/16/OR/0/Istanbul/1/1/1/1/1/1/1
		 */
		Automation automation = new Automation();
		String response = automation.editAutomation(AccountId,AccountName,AutomationId, AutomationName, CoverImage, AutomationCondition, ValidTimePeriod, CurrentCity, Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday);
		return Response.status(200).entity(response).build();
	}
	
    /**
     * Activate out automation.  
     * @param AccountId		//which account
     * @param AccountName	//Account(email_or_phone). For improving security.
     * @param AutomationId //which automation
     * @return			   //return true(as JSON) if activate out successful.
     */
	@GET
	@Produces("application/json")
	@Path("/ActivateAutomation/{AccountId}/{AccountName}/{AutomationId}")
	public Response ActivateAutomation(@PathParam("AccountId")int AccountId,
									   @PathParam("AccountName")String AccountName,
			                           @PathParam("AutomationId")int AutomationId) {
		/*
		 * http://localhost:8080/REST1/Automation/ActivateAutomation/AccountId/test@gmail.com/1
		 */
		Automation automation = new Automation();
		String response = automation.activateAutomation(AccountId,AccountName,AutomationId);
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Deactivate out automation
	 * @param AccountId		//which account
	 * @param AccountName 	//Account(email_or_phone)
	 * @param AutomationId	//which automation
	 * @return				//return true(as JSON) if deactive out successful
	 */
	@GET
	@Produces("application/json")
	@Path("/DeactivateAutomation/{AccountId}/{AccountName}/{AutomationId}")
	public Response DeactivateAutomation(@PathParam("AccountId")int AccountId,
										 @PathParam("AccountName")String AccountName,
			                             @PathParam("AutomationId")int AutomationId) {
		/*
		 * http://localhost:8080/REST1/Automation/DeactivateAutomation/1/test@gmail.com/1
		 */
		Automation automation = new Automation();
		String response = automation.deactivateAutomation(AccountId,AccountName,AutomationId);
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Edit automation Item sort.
	 * @param AccountId	 	//which account
	 * @param AccountName	//Account(email_or_phone)
	 * @param AutomationId	//which automation
	 * @param ItemSort		//new item sort value.
	 * @return				//return true(as JSON) if edit automation ItemSort successful. 
	 */
	@GET
	@Produces("application/json")
	@Path("/EditAutomationItemSort/{AccountId}/{AccountName}/{AutomationId}/{ItemSort}")
	public Response EditAutomationItemSort(@PathParam("AccountId")int AccountId,
										   @PathParam("AccountName")String AccountName,
			                               @PathParam("AutomationId")int AutomationId,
										   @PathParam("ItemSort")int ItemSort) {
		/*
		 * http://localhost:8080/REST1/Automation/EditAutomationItemSort/1/test@gmail.com/1/3
		 */
		Automation automation = new Automation();
		String response = automation.editAutomationItemSort(AccountId,AccountName,AutomationId, ItemSort);
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Edit automation condition.
	 * @param AccountId		 //which account
	 * @param AccountName	//Account(email_or_phone)
	 * @param ConditionId	 //which condition. so Id of tblAutomationCondition
	 * @param ConditionType	 //determines the type of condition. "koşul tipi. örn:temperature,humidity,weather,air_quality,etc."
	 * @param ConditionValue //condition value. örn: bulutlu,güneşli,karlı,etc. Bu değer "sabit değer" olarak eklenecek.Bu şartın Mobil app tarafından karşılığı dile uygun gösterilecek.
	 * @param DeviceSwitch	 //device switch state: ON/OFF if device is editing. (koşul olarak bir cihaz durumu eklenmişse)
	 * @param CurrentCity	 //current city.
	 * @param Monday				//day to repeat for Monday. 1 or 0
	 * @param Tuesday				//day to repeat for Tuesday. 1 or 0
	 * @param Wednesday				//day to repeat for Wednesday. 1 or 0
	 * @param Thursday				//day to repeat for Thursday. 1 or 0
	 * @param Friday				//day to repeat for Friday. 1 or 0
	 * @param Saturday				//day to repeat for Saturday. 1 or 0
	 * @param Sunday				//day to repeat for Sunday. 1 or 0
	 * @return				 //return true(as JSON) if edit automation successful
	 */
	@GET
	@Produces("application/json")
	@Path("/EditCondition/{AccountId}/{AccountName}/{ConditionId}/{ConditionType}/{ConditionValue}/{DeviceSwitch}/{CurrentCity}/{Monday}/{Tuesday}/{Wednesday}/{Thursday}/{Friday}/{Saturday}/{Sunday}")
	public Response EditCondition(@PathParam("AccountId")int AccountId,
								  @PathParam("AccountName")String AccountName,
			                      @PathParam("ConditionId")int ConditionId,
				                  @PathParam("ConditionType")String ConditionType,
						          @PathParam("ConditionValue")String ConditionValue,
						          @PathParam("DeviceSwitch")String DeviceSwitch,
						          @PathParam("CurrentCity")String CurrentCity,
								  @PathParam("Monday")int Monday,
								  @PathParam("Tuesday")int Tuesday,
								  @PathParam("Wednesday")int Wednesday,
								  @PathParam("Thursday")int Thursday,
								  @PathParam("Friday")int Friday,
								  @PathParam("Saturday")int Saturday,
								  @PathParam("Sunday")int Sunday) {
		/*
		 * http://localhost:8080/REST1/Automation/EditCondition/1/test@gmail.com/2/air_quality/ortakalite/-/istanbul/1/1/1/1/1/1/1
		 */
		Automation automation = new Automation();
		String response = automation.editCondition(AccountId,AccountName,ConditionId, ConditionType, ConditionValue, DeviceSwitch, CurrentCity, Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday);
		return Response.status(200).entity(response).build();
	}
	
	
	/**
	 * Edit automation action to "tblAutomationOperation" table.
	 * @param AccountId				//which account
	 * @param AccountName			//Account(email_or_phone)
	 * @param ActionId				//which action. so Id of tblAutomationOperation table.
	 * @param MacAddress	       //device mac address. for produce mqtt topic: macaddress
	 * @param DeviceSwitch			//device switch state:ON/OFF  if added device.
	 * @param AutomationIdAssign	//automation id value. if added automation.(Action olarak bir otomasyon eklenirse)
	 * @param TimeLapseValue		//time lapse value. if added TimeLapse
	 * @param ItemSort				//Item sort value. Sort number in the action list.
	 * @return						//return true(as JSON) if action add to "tblAutomationOperation" table.
	 */
	@GET
	@Produces("application/json")
	@Path("/EditAction/{AccountId}/{AccountName}/{ActionId}/{MacAddress}/{DeviceSwitch}/{AutomationIdAssign}/{TimeLapseValue}/{ItemSort}")
	public Response EditAction(@PathParam("AccountId")int AccountId,
							   @PathParam("AccountName")String AccountName,
			                   @PathParam("ActionId")int ActionId,
							   @PathParam("MacAddress")String MacAddress,
							   @PathParam("DeviceSwitch")String DeviceSwitch,
							   @PathParam("AutomationIdAssign")int AutomationIdAssign,
							   @PathParam("TimeLapseValue")String TimeLapseValue,
							   @PathParam("ItemSort")int ItemSort) {
		/*
		 *  http://localhost:8080/REST1/Automation/EditAction/1/test@gmail.com/1/macaddress/OFF/0/0:0/2
		 */
		Automation automation = new Automation();
		String response = automation.editAction(AccountId,AccountName,ActionId, MacAddress, DeviceSwitch, AutomationIdAssign, TimeLapseValue, ItemSort);
		return Response.status(200).entity(response).build();
	}

	
///EDiT:@}	
////////////////////////////////////////////////////////////////////////////////////////////////// 
///GET:@{
	
	/**
	 * Get all automation as Items.(tüm otomasyonları listele)
	 * @param AccountId	//which account
	 * @param AccountName //Account(email_or_phone)
	 * @param FamilyId	//which family
	 * @return			//return all automation list as JSON.
	 */
	@GET
	@Produces("application/json")
	@Path("/GetAllAutomationsAsList/{AccountId}/{AccountName}/{FamilyId}")
	public Response GetAllAutomationsAsList(@PathParam("AccountId")int AccountId,
											@PathParam("AccountName")String AccountName,
										    @PathParam("FamilyId")int FamilyId) {
		/*
		 * http://localhost:8080/REST1/Automation/GetAllAutomationsAsList/1/test@gmail.com/1
		 */
		Automation automation = new Automation();
		String response = automation.getAllAutomationsAsList(AccountId,AccountName,FamilyId);
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Get all conditions Items.(otomasyona ait tüm "condition/koşulları" liste olarak getir) 
	 * @param AccountId		//which account
	 * @param AccountName 	//Account(email_or_phone)
	 * @param AutomationId	//which automation.
	 * @return				//return automation condition list as JSON.
	 */
	@GET
	@Produces("application/json")
	@Path("/GetAllConditionsAsList/{AccountId}/{AccountName}/{AutomationId}")
	public Response GetAllConditionsAsList(@PathParam("AccountId")int AccountId,
										   @PathParam("AccountName")String AccountName,
										   @PathParam("AutomationId")int AutomationId) {
		/*
		 * http://localhost:8080/REST1/Automation/GetAllConditionsAsList/1/test@gmail.com/1
		 */
		Automation automation = new Automation();
		String response = automation.getAllConditionsAsList(AccountId,AccountName,AutomationId);
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Get all Automation actions Items as list.(tüm otomasyon actionsları listele)
	 * @param AccountId		//which account
	 * @param AccountName	//Account(email_or_phone)
	 * @param AutomationId	//which automation
	 * @return				//return all actions items as JSON.
	 */
	@GET
	@Produces("application/json")
	@Path("/GetAllActionsAsList/{AccountId}/{AccountName}/{AutomationId}")
	public Response GetAllActionsAsList(@PathParam("AccountId")int AccountId,
										@PathParam("AccountName")String AccountName,
										@PathParam("AutomationId")int AutomationId) {
		/*
		 *  http://localhost:8080/REST1/Automation/GetAllActionsAsList/1/test@gmail.com/1
		 */
		Automation automation = new Automation();
		String response = automation.getAllActionsAsList(AccountId,AccountName,AutomationId);
		return Response.status(200).entity(response).build();
	}
///GET:@}	
//////////////////////////////////////////////////////////////////////////////////////////////////	
///REMOVE PROCESS:@{
	/**
	 * Remove Automation. Delete all related conditions,conditionschedulerepeat,actions when automation is deleted.
	 * Related tables: tblAutomation,tblAutomationCondition,tblAutomationOperation
	 * @param AccountId		//which account. for improving security
	 * @param AccountName 	//Account(email_or_phone). for improving security
	 * @param AutomationId	//which automation. 
	 * @return				//return true(as JSON) if remove automation is successful.
	 */
	@GET
	@Produces("application/json")
	@Path("/RemoveAutomation/{AccountId}/{AccountName}/{AutomationId}")
	public Response RemoveAutomation(@PathParam("AccountId")int AccountId,
									 @PathParam("AccountName")String AccountName,
									 @PathParam("AutomationId")int AutomationId) {
		/*
		 * http://localhost:8080/REST1/Automation/RemoveAutomation/1/test@gmail.com/3
		 */
		Automation automation = new Automation();
		String response = automation.removeAutomation(AccountId,AccountName,AutomationId);
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Remove condition in tblAutomationCondition.
	 * @param AccountId 	//which account
	 * @param AccountName	//Account
	 * @param ConditionId	//which condition
	 * @return				//return true(as JSON) if remove condition is successful.
	 */
	@GET
	@Produces("application/json")
	@Path("/RemoveCondition/{AccountId}/{AccountName}/{ConditionId}")
	public Response RemoveCondition(@PathParam("AccountId")int AccountId,
									@PathParam("AccountName")String AccountName,
			                        @PathParam("ConditionId")int ConditionId) {
		/*
		 * http://localhost:8080/REST1/Automation/RemoveCondition/1/test@gmail.com/3
		 */
		Automation automation = new Automation();
		String response = automation.removeCondition(AccountId,AccountName,ConditionId);
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Remove automation action.
	 * @param AccountId //which account
	 * @param AccountName //Account
	 * @param ActionId	//which action
	 * @return			//return true(as JSON) if remove action is successful.
	 */
	@GET
	@Produces("application/json")
	@Path("/RemoveAction/{AccountId}/{AccountName}/{ActionId}")
	public Response RemoveAction(@PathParam("AccountId")int AccountId,
								 @PathParam("AccountName")String AccountName,
			                     @PathParam("ActionId")int ActionId) {
		/*
		 * http://localhost:8080/REST1/Automation/RemoveAction/1/test@gmail.com/2
		 */
		Automation automation = new Automation();
		String response = automation.removeAction(AccountId,AccountName,ActionId);
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Perform automation. Otomasyonu gerçekleştir-işlet(tetikle).(Bu süreç Server yazılımı tarafından "mqtt işlet motoru" olarak oluşturulmuş modül tarafından işletilecek.)
	 * @param AccountId     //which account
	 * @param AccountName 	//Account
	 * @param AutomationId	//which automaiton
	 * @return				//return true(as JSON) if successful.//İPTAL:return automation Actions List as JSON. (otomasyonda yer alan işlem itemleri liste olarak return et.)
	 */
	@GET
	@Produces("application/json")
	@Path("/PerformAutomation/{AccountId}/{AccountName}/{AutomationId}")
	public Response PerformAutomation(@PathParam("AccountId")int AccountId,
									  @PathParam("AccountName")String AccountName,
			                          @PathParam("AutomationId")int AutomationId) {
		/*
		 *  http://localhost:8080/REST1/Automation/PerformAutomation/1/test@gmail.com/1
		 */
		Automation automation = new Automation();
		String response = automation.performAutomation(AccountId,AccountName,AutomationId);
		return Response.status(200).entity(response).build();
	}
///REMOVE PROCESS:@}	
	
	
	
}
