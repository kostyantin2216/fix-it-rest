/**
 * 
 */
package com.fixit.rest.resources.services.data;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.springframework.stereotype.Component;

import com.fixit.rest.resources.services.BaseServiceResource;
import com.fixit.rest.resources.services.data.requests.TradesmanReviewDataRequestData;
import com.fixit.rest.resources.services.data.responses.TradesmanReviewDataResponseData;
import com.fixit.rest.resources.services.requests.ServiceRequest;
import com.fixit.rest.resources.services.responses.ServiceResponse;
import com.fixit.rest.resources.services.responses.ServiceResponseHeader;

/**
 * @author 		Kostyantin
 * @createdAt 	2017/05/20 18:29:49 GMT+3
 */
@Component
public class DataServiceResource extends BaseServiceResource {

	public final static String END_POINT = "data";
	
	@POST
	@Path("reviewsForTradesman")
	public ServiceResponse<TradesmanReviewDataResponseData> getReviewsForTradesman(ServiceRequest<TradesmanReviewDataRequestData> request) {
		ServiceResponseHeader respHeader = createHeader();
		
		return null;
	}
	
	@Override
	public String getEndPoint() {
		return END_POINT;
	}

}
