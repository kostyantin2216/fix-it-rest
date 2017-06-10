/**
 * 
 */
package com.fixit.rest.resources.services;

import com.fixit.rest.resources.RestResource;
import com.fixit.rest.resources.services.requests.RequestData;
import com.fixit.rest.resources.services.requests.ServiceRequest;
import com.fixit.rest.resources.services.responses.ServiceResponseHeader;

/**
 * @author 		Kostyantin
 * @createdAt 	2017/04/11 18:57:14 GMT+3
 */
public abstract class BaseServiceResource implements RestResource {
	

	protected <RD extends RequestData> ServiceResponseHeader createRespHeader(ServiceRequest<RD> request) {
		return createRespHeader(request, false);
	}

	protected <RD extends RequestData> ServiceResponseHeader createRespHeader(ServiceRequest<RD> request, boolean userIdRequired) {
		ServiceResponseHeader header = new ServiceResponseHeader();
		request.getHeader().validate(header, userIdRequired);
		request.validate(header);
		return header;
	}
	
}
