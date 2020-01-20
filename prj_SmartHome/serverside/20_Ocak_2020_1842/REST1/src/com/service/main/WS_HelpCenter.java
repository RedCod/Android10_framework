package com.service.main;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.service.sub.HelpCenter;

@Path("/HelpCenter")
public class WS_HelpCenter {
	
	/**
	 * Add help content.
	 * @param Title			//title of help content.
	 * @param ContentPath	//path of help content. html page name and path on server.
	 * @return				//return true(as JSON) if adding is successful.
	 */
	@GET
	@Produces("application/json")
	@Path("/AddHelp/{Title}/{ContentPath}")
	public Response AddHelp(@PathParam("Title")String Title,
							@PathParam("ContentPath")String ContentPath) {
		/*
		 * http://localhost:8080/REST1/HelpCenter/AddHelp/Bu bir yardım başlığıdır1/help1.html
		 */
		HelpCenter helpCenter = new HelpCenter();
		String response = helpCenter.addHelp(Title, ContentPath);
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Edit help content.
	 * @param HelpRowId	   //which content.		
	 * @param Title		   //title of help content.	
	 * @param ContentPath  //path of help content. html page name and path on server.	
	 * @return			  //return true(as JSON) if edit is successful.	
	 */
	@GET
	@Produces("application/json")
	@Path("/EditHelp/{HelpRowId}/{Title}/{ContentPath}")
	public Response EditHelp(@PathParam("HelpRowId")int HelpRowId,
							 @PathParam("Title")String Title,
							 @PathParam("ContentPath")String ContentPath) {
		/*
		 * http://localhost:8080/REST1/HelpCenter/EditHelp/2/YARDIM BAŞLIĞI/help2.html
		 */
		HelpCenter helpCenter = new HelpCenter();
		String response = helpCenter.editHelp(HelpRowId, Title, ContentPath);
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Get one help content as Item.
	 * @param HelpRowId	//which content.
	 * @return			//return help content as JSON.
	 */
	@GET
	@Produces("application/json")
	@Path("GetItem/{HelpRowId}")
	public Response GetItem(@PathParam("HelpRowId")int HelpRowId) {
		/*
		 * http://localhost:8080/REST1/HelpCenter/GetItem/2
		 */
		HelpCenter helpCenter = new HelpCenter();
		String response = helpCenter.getItem(HelpRowId);
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
		 * http://localhost:8080/REST1/HelpCenter/GetAllAsList
		 */
		HelpCenter helpCenter = new HelpCenter();
		String response = helpCenter.getAllAsList();
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Remove specified help Item.
	 * @param HelpRowId	//which Item.
	 * @return			//return true(as JSON) if help item remove is successful.
	 */
	@GET
	@Produces("application/json")
	@Path("/Remove/{HelpRowId}")
	public Response Remove(@PathParam("HelpRowId")int HelpRowId)  {
		/*
		 * http://localhost:8080/REST1/HelpCenter/Remove/3
		 */
		HelpCenter helpCenter = new HelpCenter();
		String response = helpCenter.remove(HelpRowId);
		return Response.status(200).entity(response).build();
	}

}
