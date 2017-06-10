package com.fixit.rest.resources.handlers;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Component;

import com.fixit.rest.resources.RestResource;
import com.fixit.rest.resources.data.AppInstallationResource;
import com.fixit.rest.resources.data.AppLogResource;
import com.fixit.rest.resources.data.JobReasonResource;
import com.fixit.rest.resources.data.OrderResource;
import com.fixit.rest.resources.data.MapAreaResource;
import com.fixit.rest.resources.data.ProfessionResource;
import com.fixit.rest.resources.data.ReviewResource;
import com.fixit.rest.resources.data.SearchTagResource;
import com.fixit.rest.resources.data.StoredPropertyResource;
import com.fixit.rest.resources.data.TradesmanResource;
import com.fixit.rest.resources.data.TradesmanStatisticsResource;
import com.fixit.rest.resources.data.UserBookmarkResource;
import com.fixit.rest.resources.data.UserResource;
import com.fixit.rest.resources.data.UserStatisticsResource;

@Path(DataResourceHandler.END_POINT)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Component
public class DataResourceHandler implements RestResource {
	
	public final static String END_POINT = "data";
	
	@Context
	private ResourceContext context;
	
	@Path(AppInstallationResource.END_POINT)
	public AppInstallationResource getAppInstallationResource() {
		return context.getResource(AppInstallationResource.class);
	}
	
	@Path(AppLogResource.END_POINT)
	public AppLogResource getAppLogResource() {
		return context.getResource(AppLogResource.class);
	}
	
	@Path(JobReasonResource.END_POINT)
	public JobReasonResource getJobReasonResource() {
		return context.getResource(JobReasonResource.class);
	}
	
	@Path(OrderResource.END_POINT)
	public OrderResource getJobResource() {
		return context.getResource(OrderResource.class);
	}
	
	@Path(MapAreaResource.END_POINT)
	public MapAreaResource getMapAreaResource() {
		return context.getResource(MapAreaResource.class);
	}
	
	@Path(ProfessionResource.END_POINT)
	public ProfessionResource getProfessionResource() {
		return context.getResource(ProfessionResource.class);
	}
	
	@Path(ReviewResource.END_POINT)
	public ReviewResource getReviewResource() {
		return context.getResource(ReviewResource.class);
	}
	
	@Path(SearchTagResource.END_POINT)
	public SearchTagResource getSearchTagResource() { 
		return context.getResource(SearchTagResource.class);
	}
	
	@Path(StoredPropertyResource.END_POINT)
	public StoredPropertyResource getStoredPropertyResource() {
		return context.getResource(StoredPropertyResource.class);
	}
	
	@Path(TradesmanResource.END_POINT)
	public TradesmanResource getTradesmanResource() {
		return context.getResource(TradesmanResource.class);
	}
	
	@Path(TradesmanStatisticsResource.END_POINT)
	public TradesmanStatisticsResource getTradesmanStatisticsResource() {
		return context.getResource(TradesmanStatisticsResource.class);
	}
	
	@Path(UserBookmarkResource.END_POINT)
	public UserBookmarkResource getUserBookmarkResource() {
		return context.getResource(UserBookmarkResource.class);
	}
	
	@Path(UserResource.END_POINT)
	public UserResource getUserResource() {
		return context.getResource(UserResource.class);
	}
	
	@Path(UserStatisticsResource.END_POINT)
	public UserStatisticsResource getUserStatisticsResource() {
		return context.getResource(UserStatisticsResource.class);
	}

	@Override
	public String getEndPoint() {
		return END_POINT;
	}

}
