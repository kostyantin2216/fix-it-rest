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
import com.fixit.rest.resources.services.search.SearchServiceResource;
import com.fixit.rest.resources.services.synchronization.SynchronizationServiceResource;

/**
 * @author 		Kostyantin
 * @createdAt 	2016/12/25 19:48:13 GMT+2
 */
@Path(ServicesResourceHandler.END_POINT)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Component
public class ServicesResourceHandler implements RestResource {
	
	public final static String END_POINT = "services";
	
	@Context
	private ResourceContext context;

	@Path(SearchServiceResource.END_POINT)
	public SearchServiceResource getSearchServiceResource() {
		return context.getResource(SearchServiceResource.class);
	}
	
	@Path(SynchronizationServiceResource.END_POINT)
	public SynchronizationServiceResource getSynchronizationServiceResource() {
		return context.getResource(SynchronizationServiceResource.class);
	}
	
	@Override
	public String getEndPoint() {
		return END_POINT;
	}

}
