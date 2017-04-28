/**
 * 
 */
package com.fixit.rest.resources.handlers;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Component;

import com.fixit.rest.resources.RestResource;
import com.fixit.rest.resources.shopify.ShopifyResource;

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
	
	@Path(ShopifyResource.END_POINT)
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public ShopifyResource getShopifyResource() {
		return context.getResource(ShopifyResource.class);
	}
	
	@Override
	public String getEndPoint() {
		return END_POINT;
	}
	
}
