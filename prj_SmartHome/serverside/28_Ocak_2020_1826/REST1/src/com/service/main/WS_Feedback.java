package com.service.main;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.service.sub.Feedback;

@Path("/Feedback")
public class WS_Feedback {

	/**
	 * Add feedback.
	 * @param AccountId	//which account.
	 * @param AccountName //Account(email_or_phone). for improving security
	 * @param Content	//content. choose from ready-made content.
	 * @param FeedbackDateTime //feedback date and time.
	 * @return			//return true(as JSON) if add feedback is successful.
	 */
	@GET
	@Produces("application/json")
	@Path("/AddFeedback/{AccountId}/{AccountName}/{Content}/{FeedbackDateTime}")
	public Response AddFeedback(@PathParam("AccountId")int AccountId,
			                    @PathParam("AccountName")String AccountName,
								@PathParam("Content")String Content,
								@PathParam("FeedbackDateTime")String FeedbackDateTime) {
		/*
		 * http://localhost:8080/REST1/Feedback/AddFeedback/1/test@gmail.com/Diğerleri/08-01-2020 2015:21
		 */
		Feedback feedback = new Feedback();
		String response = feedback.addFeedback(AccountId,AccountName, Content, FeedbackDateTime);
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Edit feedback Item.
	 * @param AccountId 	//which account
	 * @param AccountName	//Account
	 * @param FeedbackRowId //which feedback item.
	 * @param Content	//content. choose from ready-made content.
	 * @param FeedbackDateTime//feedback date and time. 	
	 * @return			//return true(as JSON) if edit feedback is successful.
	 */
	@GET
	@Produces("application/json")
	@Path("/EditFeedback/{AccountId}/{AccountName}/{FeedbackRowId}/{Content}/{FeedbackDateTime}")
	public Response EditFeedback(@PathParam("AccountId")int AccountId,
								 @PathParam("AccountName")String AccountName,
			                     @PathParam("FeedbackRowId")int FeedbackRowId,
								 @PathParam("Content")String Content,
								 @PathParam("FeedbackDateTime")String FeedbackateTime) {
		/*
		 * http://localhost:8080/REST1/Feedback/EditFeedback/1/test@gmail.com/3/Cihaz/08-01-2020 15:26
		 */
		Feedback feedback = new Feedback();
		String response = feedback.editFeedback(AccountId,AccountName,FeedbackRowId, Content, FeedbackateTime);
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Get one feedback item.
	 * @param AccountId 	//which account
	 * @param AccountName	//Account
	 * @param FeedbackRowId	//which item.
	 * @return				//return feedback item as JSON.
	 */
	@GET
	@Produces("application/json")
	@Path("/GetItem/{AccountId}/{AccountName}/{FeedbackRowId}")
	public Response GetItem(@PathParam("AccountId")int AccountId,
							@PathParam("AccountName")String AccountName,
			                @PathParam("FeedbackRowId")int FeedbackRowId) {
		/*
		 * http://localhost:8080/REST1/Feedback/GetItem/1/test@gmail.com/3
		 */
		Feedback feedback = new Feedback();
		String response = feedback.getItem(AccountId,AccountName,FeedbackRowId);
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Get all feedback item as list.
	 * @param AccountId	//which account.
	 * @param AccountName //Account
	 * @return			//return all feedback item as JSON.
	 */
    @GET
    @Produces("application/json")
    @Path("/GetAllAsList/{AccountId}/{AccountName}")
    public Response GetAllAsList(@PathParam("AccountId")int AccountId,
    		                     @PathParam("AccountName")String AccountName) {
    	/*
    	 * http://localhost:8080/REST1/Feedback/GetAllAsList/1/test@gmail.com
    	 */
    	Feedback feedback = new Feedback();
    	String response = feedback.getAllAsList(AccountId,AccountName);
    	return Response.status(200).entity(response).build();
    }
	
    /**
     * Remove feedback item.
     * @param AccountId 	//which account
     * @param AccountName	//Account
     * @param FeedbackRowId	//which item.
     * @return				//return true(as JSON) if remove is successful.
     */
    @GET
    @Produces("application/json")
    @Path("/Remove/{AccountId}/{AccountName}/{FeedbackRowId}")
    public Response Remove(@PathParam("AccountId")int AccountId,
    					   @PathParam("AccountName")String AccountName,
    		               @PathParam("FeedbackRowId") int FeedbackRowId) {
    	/*
    	 * http://localhost:8080/REST1/Feedback/Remove/1/test@gmail.com/4
    	 */
    	Feedback feedback = new Feedback();
    	String response = feedback.remove(AccountId,AccountName,FeedbackRowId);
    	return Response.status(200).entity(response).build();
    }
}
