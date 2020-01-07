package com.service.main;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/HelpCenter")
public class WS_HelpCenter {
	
	/**
	 * Add help content.
	 * @param Title			//title of help content.
	 * @param ContentPath	//help content path. html page name and path on server.
	 * @return				//return true(as JSON) if adding is successful.
	 */
	@GET
	@Produces("application/json")
	@Path("/AddHelp/{Title}/{ContentPath}")
	public Response AddHelp(@PathParam("Title")String Title,
							@PathParam("ContentPath")String ContentPath) {
		/*
		 * 
		 */
		String response = "";
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Edit help content.
	 * @param HelpId	   //which content.		
	 * @param Title		   //title of help content.	
	 * @param ContentPath  //help content path. html page name and path on server.	
	 * @return			  //return true(as JSON) if adding is successful.	
	 */
	@GET
	@Produces("application/json")
	@Path("/EditHelp/{HelpId}/{Title}/{ContentPath}")
	public Response EditHelp(@PathParam("HelpId")int HelpId,
							 @PathParam("Title")String Title,
							 @PathParam("ContentPath")String ContentPath) {
		/*
		 * 
		 */
		String response = "";
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Get one help content as Item.
	 * @param HelpId	//which content.
	 * @return			//return help content as JSON.
	 */
	@GET
	@Produces("application/json")
	@Path("GetItem/{HelpId}")
	public Response GetItem(@PathParam("HelpId")int HelpId) {
		/*
		 * 
		 */
		String response = "";
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Get all help content as list.
	 * @return	//return all help content as JSON.
	 */
	@GET
	@Produces("application/json")
	@Path("/GetAllAsList")
	public Response GetAllAsList() {
		/*
		 * 
		 */
		String response = "";
		return Response.status(200).entity(response).build();
	}
	

}
