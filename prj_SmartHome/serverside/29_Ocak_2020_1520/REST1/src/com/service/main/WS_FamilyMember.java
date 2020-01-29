package com.service.main;

import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.service.sub.FamilyMember;


@Path("/FamilyMember")
public class WS_FamilyMember {
	
	/**
	 * Add family member if the member has an account.
	 * @param AccountId 	//which account.
	 * @param AccountName	//Account(email_or_phone). for improving security
	 * @param FamilyId		//which family.
	 * @param AccountId		//which account.
	 * @param AccountIsAdmin//account is admin set 1 else 0.
	 * @return				//return true(as JSON) if adding is successful.
	 */
	@GET
	@Produces("application/json")
	@Path("/AddMember/{AccountId}/{AccountName}/{FamilyId}/{AccountId}/{AccountIsAdmin}")
	public Response AddMember(@PathParam("AccountId")int AccountId,
			 				  @PathParam("AccountName")String AccountName,
			 				  @PathParam("FamilyId")int FamilyId,
			 				  @PathParam("AccountIsAdmin")int AccountIsAdmin) {
		/*
		 *   http://localhost:8080/REST1/FamilyMember/AddMember/1/test@gmail.com/1/0
		 */
		FamilyMember familyMember = new FamilyMember();
        String response = familyMember.addMember(AccountId,AccountName,FamilyId,AccountIsAdmin);
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Edit family member. Edit just admin state.
	 * @param AccountId			//which account. for improving security
	 * @param AccountName 		//Account(email_or_phone). for improving security
	 * @param MemberRowId			//which member. WHERE Id
	 * @param AccountIsAdmin	// admin = 1,normal=0
	 * @return					//return true(as JSON) if edit is successful.
	 */
	@GET
	@Produces("application/json")
	@Path("/EditMember/{AccountId}/{AccountName}/{MemberRowId}/{AccountIsAdmin}")
	public Response EditMember(@PathParam("AccountId")int AccountId,
							   @PathParam("AccountName")String AccountName,
			                   @PathParam("MemberRowId")int MemberRowId,
							   @PathParam("AccountIsAdmin")int AccountIsAdmin) {
		/*
		 * http://localhost:8080/REST1/FamilyMember/EditMember/1/test@gmail.com/11/1
		 */
		FamilyMember familyMember = new FamilyMember();
		String response =  familyMember.editMember(AccountId,AccountName,MemberRowId, AccountIsAdmin);
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Get Family member as Item.
	 * @param AccountId 	//which account
	 * @param AccountName	//Account
	 * @param MemberRowId	//which member.
	 * @return			//return family member Item as JSON.
	 */
	@GET
	@Produces("application/json")
	@Path("/GetItem/{AccountId}/{AccountName}/{MemberRowId}")
	public Response GetItem(@PathParam("AccountId")int AccountId,
							@PathParam("AccountName")String AccountName,
			                @PathParam("MemberRowId")int MemberRowId) {
		/*
		 * http://localhost:8080/REST1/FamilyMember/GetItem/1/test@gmail.com/3
		 */
		FamilyMember familyMember = new FamilyMember();
		String response = familyMember.getItem(AccountId,AccountName,MemberRowId);
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Get all family member of family as list.
	 * @param AccountId	//which account
	 * @param AccountName	//Account
	 * @param FamilyId	//which family.
	 * @return			//return all family member list as JSON.
	 */
	@GET
	@Produces("application/json")
	@Path("/GetAllAsList/{AccountId}/{AccountName}/{FamilyId}")
	public Response GetAllAsList(@PathParam("AccountId")int AccountId,
								 @PathParam("AccountName")String AccountName,
			                     @PathParam("FamilyId")int FamilyId) {
		/*
		 * http://localhost:8080/REST1/FamilyMember/GetAllAsList/1/test@gmail.com/4
		 */
		FamilyMember familyMember = new FamilyMember();
		String response =  familyMember.getAllAsList(AccountId,AccountName,FamilyId);
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Set as family administrator.
	 * @param AccountId //which account
	 * @param AccountName 	//Account
	 * @param FamilyId //which family Id.
	 * @return		   //return true(as JSON) if set family admin successful.
	 */
	@GET
	@Produces("application/json")
	@Path("/SetFamilyAdmin/{AccountId}/{AccountName}/{FamilyId}")
	public Response SetFamilyAdmin(@PathParam("AccountId")int AccountId,
			                       @PathParam("AccountName")String AccountName,
								   @PathParam("FamilyId")int FamilyId) {
		/*
		 * http://localhost:8080/REST1/FamilyMember/SetFamilyAdmin/1/test@gmail.com/5
		 */
		FamilyMember family = new FamilyMember();
		String response  = family.setFamilyAdmin(AccountId,AccountName,FamilyId);
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Remove family admin.
	 * @param AccountId //which account.
	 * @param AccountName //Account
	 * @param FamilyId	//which family Id.
	 * @return			//return true(as JSON) if remove family admin successful.
	 */
	@GET
	@Produces("application/json")
	@Path("/RemoveFamilyAdmin/{AccountId}/{AccountName}/{FamilyId}")
	public Response RemoveFamilyAdmin(@PathParam("AccountId")int AccountId,
			                          @PathParam("AccountName")String AccountName,
			                          @PathParam("FamilyId")int FamilyId) {
		/*
		 *  http://localhost:8080/REST1/FamilyMember/RemoveFamilyAdmin/1/test@gmail.com/5
		 */
		FamilyMember family = new FamilyMember();
		String response = family.removeFamilyAdmin(AccountId,AccountName,FamilyId);
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Remove family member.
	 * @param AccountId		//which account
	 * @param AccountName 	//Account
	 * @param MemberRowId	//which member.
	 * @return			//return true(as JSON) if remove member is successful.
	 */
	@GET
	@Produces("application/json")
	@Path("/RemoveMember/{AccountId}/{AccountName}/{MemberRowId}")
	public Response RemoveMember(@PathParam("AccountId")int AccountId,
								 @PathParam("AccountName")String AccountName,
			                     @PathParam("MemberRowId")int MemberRowId) {
		/*
		 * http://localhost:8080/REST1/FamilyMember/RemoveMember/1/test@gmail.com/10
		 */
		FamilyMember familyMember = new FamilyMember();
		String response = familyMember.removeMember(AccountId,AccountName,MemberRowId);
		return Response.status(200).entity(response).build();
	}

}
