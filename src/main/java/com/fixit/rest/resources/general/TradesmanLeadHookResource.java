/**
 * 
 */
package com.fixit.rest.resources.general;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fixit.components.registration.TradesmanRegistrationController;
import com.fixit.core.data.sql.TradesmanLead;
import com.fixit.core.logging.FILog;
import com.fixit.rest.resources.RestResource;

/**
 * @author 		Kostyantin
 * @createdAt 	2017/04/22 12:48:03 GMT+3
 * 
 */
@Component
public class TradesmanLeadHookResource implements RestResource {
	
	private final static String LOG_TAG = TradesmanLeadHookResource.class.getName();

	public final static String END_POINT = "tradesmenLeads";
	
	@Autowired
	private TradesmanRegistrationController tradesmanRegistrationController;

	@POST
	@Path("newLead")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response newLead(@FormParam("firstName") String firstName, @FormParam("lastName") String lastName, @FormParam("email") String email) {
		TradesmanLead lead = TradesmanLead.newLead(firstName, lastName, email);
		try {
			tradesmanRegistrationController.newLead(lead);
		} catch(Exception e) {
			FILog.e(LOG_TAG, "Error while creating new tradesman account.", e);
		}
		
		return Response.ok().build();
	}
	
	@Override
	public String getEndPoint() {
		return END_POINT;
	}
	
}
