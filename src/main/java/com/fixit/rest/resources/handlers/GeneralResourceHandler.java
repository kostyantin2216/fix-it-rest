/**
 * 
 */
package com.fixit.rest.resources.handlers;

import javax.ws.rs.Path;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;

import org.springframework.stereotype.Component;

import com.fixit.rest.resources.RestResource;
import com.fixit.rest.resources.general.OrderHookResource;
import com.fixit.rest.resources.general.TradesmanLeadHookResource;

/**
 * @author 		Kostyantin
 * @createdAt 	2017/04/22 12:50:14 GMT+3
 */
@Path(GeneralResourceHandler.END_POINT)
@Component
public class GeneralResourceHandler implements RestResource {

	public final static String END_POINT = "general";

	@Context
	private ResourceContext context;
	
	@Path(TradesmanLeadHookResource.END_POINT)
	public TradesmanLeadHookResource getTradesmanLeadHookResource() {
		return context.getResource(TradesmanLeadHookResource.class);
	}
	
	@Path(OrderHookResource.END_POINT)
	public OrderHookResource getOrderHookResource() {
		return context.getResource(OrderHookResource.class);
	}
	
	@Override
	public String getEndPoint() {
		return END_POINT;
	}
	
}
