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
	 * @param FamilyId		//which family.
	 * @param AccountId		//which account.
	 * @param AccountIsAdmin//account is admin set 1 else 0.
	 * @return				//return true(as JSON) if adding is successful.
	 */
	@GET
	@Produces("application/json")
	@Path("/AddMember/{FamilyId}/{AccountId}/{AccountIsAdmin}")
	public Response AddMember(@PathParam("FamilyId")int FamilyId,
							 @PathParam("AccountId")int AccountId,
							 @PathParam("AccountIsAdmin")int AccountIsAdmin) {
		/*
		 *   http://localhost:8080/REST1/FamilyMember/AddMember/1/1/0
		 */
		FamilyMember familyMember = new FamilyMember();
        String response = familyMember.addMember(FamilyId, AccountId, AccountIsAdmin);
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Edit family member. Edit just admin state.
	 * @param MemberRowId			//which member. WHERE Id
	 * @param AccountIsAdmin	// admin = 1,normal=0
	 * @return					//return true(as JSON) if edit is successful.
	 */
	@GET
	@Produces("application/json")
	@Path("/EditMember/{MemberRowId}/{AccountIsAdmin}")
	public Response EditMember(@PathParam("MemberRowId")int MemberRowId,
							   @PathParam("AccountIsAdmin")int AccountIsAdmin) {
		/*
		 * http://localhost:8080/REST1/FamilyMember/EditMember/11/1
		 */
		FamilyMember familyMember = new FamilyMember();
		String response =  familyMember.editMember(MemberRowId, AccountIsAdmin);
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Get Family member as Item.
	 * @param MemberRowId	//which member.
	 * @return			//return family member Item as JSON.
	 */
	@GET
	@Produces("application/json")
	@Path("/GetItem/{MemberRowId}")
	public Response GetItem(@PathParam("MemberRowId")int MemberRowId) {
		/*
		 * http://localhost:8080/REST1/FamilyMember/GetItem/3
		 */
		FamilyMember familyMember = new FamilyMember();
		String response = familyMember.getItem(MemberRowId);
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Get all family member of family as list.
	 * @param FamilyId	//which family.
	 * @return			//return all family member list as JSON.
	 */
	@GET
	@Produces("application/json")
	@Path("/GetAllAsList/{FamilyId}")
	public Response GetAllAsList(@PathParam("FamilyId")int FamilyId) {
		/*
		 * http://localhost:8080/REST1/FamilyMember/GetAllAsList/1
		 */
		FamilyMember familyMember = new FamilyMember();
		String response =  familyMember.getAllAsList(FamilyId);
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Remove family member.
	 * @param MemberRowId	//which member.
	 * @return			//return true(as JSON) if remove member is successful.
	 */
	@GET
	@Produces("application/json")
	@Path("/RemoveMember/{MemberRowId}")
	public Response RemoveMember(@PathParam("MemberRowId")int MemberRowId) {
		/*
		 * http://localhost:8080/REST1/FamilyMember/RemoveMember/10
		 */
		FamilyMember familyMember = new FamilyMember();
		String response = familyMember.removeMember(MemberRowId);
		return Response.status(200).entity(response).build();
	}

}
