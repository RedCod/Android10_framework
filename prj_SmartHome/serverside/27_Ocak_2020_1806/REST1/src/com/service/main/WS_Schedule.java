package com.service.main;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import com.service.sub.Schedule;

@Path("/Schedule")
public class WS_Schedule {
	
	/**
	 * Add schedule for Device ON-OFF process.
	 * @param AccountId		//which Account.
	 * @param DeviceId		//Which Device.
	 * @param ScheduleTime	//Schedule time.When will it work.
	 * @param Switch		//opreation-process ON/OFF
	 * @param Monday		//work day. 1 or 0
	 * @param Tuesday		//work day. 1 or 0
	 * @param Wednesday		//work day. 1 or 0
	 * @param Thursday		//work day. 1 or 0
	 * @param Friday		//work day. 1 or 0
	 * @param Saturday		//work day. 1 or 0
	 * @param Sunday		//work day. 1 or 0
	 * @param OnlyOnce		//if no day is specified,only work once.(eğer gün belirtilmemişse,sadece bir kez çalış.)
	 * @return				//return true(as JSON) if adding is successful.
	 */
	@GET
	@Produces("application/json")
	@Path("/AddSchedule/{AccountId}/{DeviceId}/{ScheduleTime}/{Switch}/{Monday}/{Tuesday}/{Wednesday}/{Thursday}/{Friday}/{Saturday}/{Sunday}/{OnlyOnce}")
	public Response AddSchedule(@PathParam("AccountId")int AccountId,
								@PathParam("DeviceId")int DeviceId,
								@PathParam("ScheduleTime")String ScheduleTime,
								@PathParam("Switch")String Switch,
								@PathParam("Monday")int Monday,
								@PathParam("Tuesday")int Tuesday,
								@PathParam("Wednesday")int Wednesday,
								@PathParam("Thursday")int Thursday,
								@PathParam("Friday")int Friday,
								@PathParam("Saturday")int Saturday,
								@PathParam("Sunday")int Sunday,
								@PathParam("OnlyOnce")int OnlyOnce) {
		/*
		 * http://localhost:8080/REST1/Schedule/AddSchedule/1/4/12:50/OFF/1/1/1/1/1/1/1/0
		 */
		Schedule schedule = new Schedule();
		String response = schedule.addSchedule(AccountId, DeviceId, ScheduleTime, Switch, Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday, OnlyOnce);
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Edit schedule Item.
	 * @param ScheduleRowId	//which schedule.
	 * @param ScheduleTime	//schedule time.When will it work.
	 * @param Switch		//operation-process. ON/OFF
	 * @param Monday		//work day. 1 or 0
	 * @param Tuesday		//work day. 1 or 0
	 * @param Wednesday		//work day. 1 or 0
	 * @param Thursday		//work day. 1 or 0
	 * @param Friday		//work day. 1 or 0
	 * @param Saturday		//work day. 1 or 0
	 * @param Sunday		//work day. 1 or 0
	 * @param OnlyOnce		//if no day is specified,only work once.(eğer gün belirtilmemişse,sadece bir kez çalış.)
	 * @return				//return true(as JSON) if edit is successful.
	 */
	@GET
	@Produces("application/json")
	@Path("/EditSchedule/{ScheduleRowId}/{ScheduleTime}/{Switch}/{Monday}/{Tuesday}/{Wednesday}/{Thursday}/{Friday}/{Saturday}/{Sunday}/{OnlyOnce}")
	public Response EditSchedule(@PathParam("ScheduleRowId")int ScheduleRowId,
								 @PathParam("ScheduleTime")String ScheduleTime,
								 @PathParam("Switch")String Switch,
								 @PathParam("Monday")int Monday,
								 @PathParam("Tuesday")int Tuesday,
								 @PathParam("Wednesday")int Wednesday,
							  	 @PathParam("Thursday")int Thursday,
								 @PathParam("Friday")int Friday,
								 @PathParam("Saturday")int Saturday,
								 @PathParam("Sunday")int Sunday,
								 @PathParam("OnlyOnce")int OnlyOnce) {
		/*
		 * http://localhost:8080/REST1/Schedule/EditSchedule/1/11:10/ON/1/1/1/1/1/1/1/0
		 */
		Schedule schedule = new Schedule();
		String response = schedule.editSchedule(ScheduleRowId, ScheduleTime, Switch, Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday, OnlyOnce);
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Get schedule as Item.
	 * @param ScheduleRowId	//which schedule.
	 * @return				//return one schedule Item as JSON.
	 */
	@GET
	@Produces("application/json")
	@Path("/GetItem/{ScheduleRowId}")
	public Response GetItem(@PathParam("ScheduleRowId")int ScheduleRowId) {
		/*
		 * http://localhost:8080/REST1/Schedule/GetItem/3
		 */
		Schedule schedule = new Schedule();
		String response = schedule.getItem(ScheduleRowId);
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Get all schedule items as List. WHERE AccountId=X AND DeviceId=xx
	 * @param DeviceId	//which device.
	 * @return			//return all schedule Items as JSON.
	 */
	@GET
	@Produces("application/json")
	@Path("/GetAllAsList/{DeviceId}")
	public Response GetAllAsList(@PathParam("DeviceId")int DeviceId) {
		/*
		 * http://localhost:8080/REST1/Schedule/GetAllAsList/4
		 */
		Schedule schedule = new Schedule();
		String response = schedule.getAllAsList(DeviceId);
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Remove one schedule item.
	 * @param ScheduleRowId //which schedule.
	 * @return			 //return true(as JSON) if remove is successful.	
	 */
	@GET
	@Produces("application/json")
	@Path("/Remove/{ScheduleRowId}")
	public Response Remove(@PathParam("ScheduleRowId")int ScheduleRowId) {
		/*
		 * http://localhost:8080/REST1/Schedule/Remove/3
		 */
		Schedule schedule = new Schedule();
		String response = schedule.remove(ScheduleRowId);
		return Response.status(200).entity(response).build();
	}
	

}
