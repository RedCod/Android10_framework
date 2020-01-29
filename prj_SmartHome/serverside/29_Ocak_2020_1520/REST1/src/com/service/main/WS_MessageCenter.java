package com.service.main;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.service.sub.MessageCenter;

@Path("/MessageCenter")
public class WS_MessageCenter {
	
	/**
	 * Add Message for User. 
	 * Server tarafından işletilen Schedular ve hizmet sağlayıcı firmanın User için yayınladığı mesajlar.
	 * @param AccountId			//which account.
	 * @param AccountName 		//Account(email_or_phone).for improving security
	 * @param Message			//Message content.
	 * @param MessageDateTime	//Message date time.
	 * @param MessageType		//Message type.
	 * @return					//return true(as JSON) if adding is successful.
	 */
	@GET
	@Produces("application/json")
	@Path("/AddMessage/{AccountId}/{AccountName}/{Message}/{MessageDateTime}/{MessageType}")
	public Response AddMessage(@PathParam("AccountId")int AccountId,
							   @PathParam("AccountName")String AccountName,
							   @PathParam("Message")String Message,
							   @PathParam("MessageDateTime")String MessageDateTime,
							   @PathParam("MessageType")int MessageType) {
		/*
		 * http://localhost:8080/REST1/MessageCenter/AddMessage/1/test@gmail.com/bu%20mesaj%20sana/07-01-2020/1
		 */
		MessageCenter messageCenter = new MessageCenter();
        String response = messageCenter.addMessage(AccountId,AccountName, Message, MessageDateTime, MessageType);
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Edit Message.
	 * @param AccountId			//which account.for improving security
	 * @param AccountName	    //Account(email_or_phone). for improving security
	 * @param MessageRowId			//which message.
	 * @param Message			//message content.
	 * @param MessageDatetime	//message date time.
	 * @param MessageType		//message type.
	 * @return					//return true(as JSON) if editing is successful.
	 */
	@GET
	@Produces("application/json")
	@Path("/EditMessage/{AccountId}/{AccountName}/{MessageRowId}/{Message}/{MessageDateTime}/{MessageType}")
	public Response EditMessage(@PathParam("AccountId")int AccountId,
							    @PathParam("AccountName")String AccountName,
			                    @PathParam("MessageRowId")int MessageRowId,
								@PathParam("Message")String Message,
								@PathParam("MessageDateTime")String MessageDatetime,
								@PathParam("MessageType")int MessageType) {
		/*
		 * http://localhost:8080/REST1/MessageCenter/EditMessage/1/test@gmail.com/1/BU%20MESAJ%20SANA/07-02-2020%2017:07/2
		 */
		MessageCenter messageCenter = new MessageCenter();
		String response = messageCenter.editMessage(AccountId,AccountName,MessageRowId, Message, MessageDatetime, MessageType);
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Get one message Item.
	 * @param AccountId		//which account
	 * @param AccountName	//Account
	 * @param MessageRowId	//which message.
	 * @return			//return Message Item as JSON.
	 */
	@GET
	@Produces("application/json")
	@Path("/GetItem/{AccountId}/{AccountName}/{MessageRowId}")
	public Response GetItem(@PathParam("AccountId")int AccountId,
							@PathParam("AccountName")String AccountName,
			                @PathParam("MessageRowId")int MessageRowId) {
		/*
		 * http://localhost:8080/REST1/MessageCenter/GetItem/1/test@gmail.com/1
		 */
		MessageCenter messageCenter = new MessageCenter();
		String response = messageCenter.getItem(AccountId,AccountName,MessageRowId);
		return Response.status(200).entity(response).build();
	}

	/**
	 * Get all message as list.
	 * @param AccountId	//which account.
	 * @param AccountName //Account
	 * @return			//return all message Item as JSON.
	 */
	@GET
	@Produces("application/json")
	@Path("/GetAllAsList/{AccountId}/{AccountName}")
	public Response GetAllAsList(@PathParam("AccountId")int AccountId,
			                     @PathParam("AccountName")String AccountName) {
		/*
		 * http://localhost:8080/REST1/MessageCenter/GetAllAsList/1/test@gmail.com
		 */
		MessageCenter messageCenter = new MessageCenter();
		String response = messageCenter.getAllAsList(AccountId,AccountName);
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Remove specified message.
	 * @param AccountId		//which account
	 * @param AccountName	//Account
	 * @param MessageRowId	//which message.
	 * @return			//return true(as JSON) if remove message is successful.
	 */
	@GET
	@Produces("application/json")
	@Path("/RemoveMessage/{AccountId}/{AccountName}/{MessageRowId}")
	public Response RemoveMessage(@PathParam("AccountId")int AccountId,
								  @PathParam("AccountName")String AccountName,
			                      @PathParam("MessageRowId")int MessageRowId) {
		/*
		 * http://localhost:8080/REST1/MessageCenter/RemoveMessage/1/test@gmail.com/2
		 */
		MessageCenter messageCenter = new MessageCenter();
		String response = messageCenter.removeMessage(AccountId,AccountName,MessageRowId);
		return Response.status(200).entity(response).build();
	}
	
}
