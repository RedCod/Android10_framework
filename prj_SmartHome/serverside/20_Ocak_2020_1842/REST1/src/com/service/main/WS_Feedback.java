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
	 * @param Content	//content. choose from ready-made content.
	 * @param FeedbackDateTime //feedback date and time.
	 * @return			//return true(as JSON) if add feedback is successful.
	 */
	@GET
	@Produces("application/json")
	@Path("/AddFeedback/{AccountId}/{Content}/{FeedbackDateTime}")
	public Response AddFeedback(@PathParam("AccountId")int AccountId,
								@PathParam("Content")String Content,
								@PathParam("FeedbackDateTime")String FeedbackDateTime) {
		/*
		 * http://localhost:8080/REST1/Feedback/AddFeedback/1/Diğerleri/08-01-2020 2015:21
		 */
		Feedback feedback = new Feedback();
		String response = feedback.addFeedback(AccountId, Content, FeedbackDateTime);
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Edit feedback Item.
	 * @param FeedbackRowId //which feedback item.
	 * @param Content	//content. choose from ready-made content.
	 * @param FeedbackDateTime//feedback date and time. 	
	 * @return			//return true(as JSON) if edit feedback is successful.
	 */
	@GET
	@Produces("application/json")
	@Path("/EditFeedback/{FeedbackRowId}/{Content}/{FeedbackDateTime}")
	public Response EditFeedback(@PathParam("FeedbackRowId")int FeedbackRowId,
								 @PathParam("Content")String Content,
								 @PathParam("FeedbackDateTime")String FeedbackateTime) {
		/*
		 * http://localhost:8080/REST1/Feedback/EditFeedback/3/Cihaz/08-01-2020 15:26
		 */
		Feedback feedback = new Feedback();
		String response = feedback.editFeedback(FeedbackRowId, Content, FeedbackateTime);
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Get one feedback item.
	 * @param FeedbackRowId	//which item.
	 * @return				//return feedback item as JSON.
	 */
	@GET
	@Produces("application/json")
	@Path("/GetItem/{FeedbackRowId}")
	public Response GetItem(@PathParam("FeedbackRowId")int FeedbackRowId) {
		/*
		 * http://localhost:8080/REST1/Feedback/GetItem/3
		 */
		Feedback feedback = new Feedback();
		String response = feedback.getItem(FeedbackRowId);
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Get all feedback item as list.
	 * @param AccountId	//which account.
	 * @return			//return all feedback item as JSON.
	 */
    @GET
    @Produces("application/json")
    @Path("/GetAllAsList/{AccountId}")
    public Response GetAllAsList(@PathParam("AccountId")int AccountId) {
    	/*
    	 * http://localhost:8080/REST1/Feedback/GetAllAsList/1
    	 */
    	Feedback feedback = new Feedback();
    	String response = feedback.getAllAsList(AccountId);
    	return Response.status(200).entity(response).build();
    }
	
    /**
     * Remove feedback item.
     * @param FeedbackRowId	//which item.
     * @return				//return true(as JSON) if remove is successful.
     */
    @GET
    @Produces("application/json")
    @Path("/Remove/{FeedbackRowId}")
    public Response Remove(@PathParam("FeedbackRowId") int FeedbackRowId) {
    	/*
    	 * http://localhost:8080/REST1/Feedback/Remove/4
    	 */
    	Feedback feedback = new Feedback();
    	String response = feedback.remove(FeedbackRowId);
    	return Response.status(200).entity(response).build();
    }
}
