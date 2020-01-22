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
	 * @param Message			//Message content.
	 * @param MessageDateTime	//Message date time.
	 * @param MessageType		//Message type.
	 * @return					//return true(as JSON) if adding is successful.
	 */
	@GET
	@Produces("application/json")
	@Path("/AddMessage/{AccountId}/{Message}/{MessageDateTime}/{MessageType}")
	public Response AddMessage(@PathParam("AccountId")int AccountId,
							  @PathParam("Message")String Message,
							  @PathParam("MessageDateTime")String MessageDateTime,
							  @PathParam("MessageType")int MessageType) {
		/*
		 * http://localhost:8080/REST1/MessageCenter/AddMessage/1/bu%20mesaj%20sana/07-01-2020/1
		 */
		MessageCenter messageCenter = new MessageCenter();
        String response = messageCenter.addMessage(AccountId, Message, MessageDateTime, MessageType);
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Edit Message.
	 * @param MessageRowId			//which message.
	 * @param Message			//message content.
	 * @param MessageDatetime	//message date time.
	 * @param MessageType		//message type.
	 * @return					//return true(as JSON) if editing is successful.
	 */
	@GET
	@Produces("application/json")
	@Path("/EditMessage/{MessageRowId}/{Message}/{MessageDateTime}/{MessageType}")
	public Response EditMessage(@PathParam("MessageRowId")int MessageRowId,
								@PathParam("Message")String Message,
								@PathParam("MessageDateTime")String MessageDatetime,
								@PathParam("MessageType")int MessageType) {
		/*
		 * http://localhost:8080/REST1/MessageCenter/EditMessage/1/BU%20MESAJ%20SANA/07-02-2020%2017:07/2
		 */
		MessageCenter messageCenter = new MessageCenter();
		String response = messageCenter.editMessage(MessageRowId, Message, MessageDatetime, MessageType);
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Get one message Item.
	 * @param MessageRowId	//which message.
	 * @return			//return Message Item as JSON.
	 */
	@GET
	@Produces("application/json")
	@Path("/GetItem/{MessageRowId}")
	public Response GetItem(@PathParam("MessageRowId")int MessageRowId) {
		/*
		 * http://localhost:8080/REST1/MessageCenter/GetItem/1
		 */
		MessageCenter messageCenter = new MessageCenter();
		String response = messageCenter.getItem(MessageRowId);
		return Response.status(200).entity(response).build();
	}

	/**
	 * Get all message as list.
	 * @param AccountId	//which account.
	 * @return			//return all message Item as JSON.
	 */
	@GET
	@Produces("application/json")
	@Path("/GetAllAsList/{AccountId}")
	public Response GetAllAsList(@PathParam("AccountId")int AccountId) {
		/*
		 * http://localhost:8080/REST1/MessageCenter/GetAllAsList/1
		 */
		MessageCenter messageCenter = new MessageCenter();
		String response = messageCenter.getAllAsList(AccountId);
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Remove specified message.
	 * @param MessageRowId	//which message.
	 * @return			//return true(as JSON) if remove message is successful.
	 */
	@GET
	@Produces("application/json")
	@Path("/RemoveMessage/{MessageRowId}")
	public Response RemoveMessage(@PathParam("MessageRowId")int MessageRowId) {
		/*
		 * http://localhost:8080/REST1/MessageCenter/RemoveMessage/2
		 */
		MessageCenter messageCenter = new MessageCenter();
		String response = messageCenter.removeMessage(MessageRowId);
		return Response.status(200).entity(response).build();
	}
	
}
