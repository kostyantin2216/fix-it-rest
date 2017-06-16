/**
 * 
 */
package com.fixit.rest.resources.services.synchronization;

import java.util.List;

import javax.ws.rs.POST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.fixit.components.synchronization.SynchronizationResult;
import com.fixit.components.synchronization.SynchronizationTask;
import com.fixit.core.dao.mongo.SynchronizationParamsDao;
import com.fixit.core.tasks.TaskResult;
import com.fixit.rest.resources.services.BaseServiceResource;
import com.fixit.rest.resources.services.ServiceError;
import com.fixit.rest.resources.services.requests.ServiceRequest;
import com.fixit.rest.resources.services.responses.ServiceResponse;
import com.fixit.rest.resources.services.responses.ServiceResponseHeader;
import com.fixit.rest.resources.services.synchronization.requests.SynchronizationRequestData;
import com.fixit.rest.resources.services.synchronization.responses.SynchronizationResponseData;

/**
 * @author 		Kostyantin
 * @createdAt 	2017/04/11 18:51:11 GMT+3
 */
@Component
public class SynchronizationServiceResource extends BaseServiceResource {
	
	public final static String END_POINT = "synchronization";
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@Autowired
	private SynchronizationParamsDao synchronizationParamsDao;
	
	@POST
	@SuppressWarnings("rawtypes")
	public ServiceResponse<SynchronizationResponseData> synchronize(ServiceRequest<SynchronizationRequestData> request) {
		ServiceResponseHeader respHeader = createRespHeader(request);

		if(!respHeader.hasErrors()) {
			SynchronizationRequestData reqData = request.getData();
			SynchronizationTask synchronizationTask = new SynchronizationTask(applicationContext, synchronizationParamsDao, reqData.getLastSynchronization());
			TaskResult<List<SynchronizationResult>> result = synchronizationTask.process(reqData.getSynchronizationHistory());

			if(result.isCriticalError()) {
				respHeader.addError(ServiceError.UNKNOWN, "Could not synchronize due to: " + result.getFirstError().getMsg());
			} else {
				return new ServiceResponse<SynchronizationResponseData>(respHeader, new SynchronizationResponseData(result.getResult()));
			}
		}

		return new ServiceResponse<SynchronizationResponseData>(respHeader, null);
	}

	@Override
	public String getEndPoint() {
		return END_POINT;
	}

}
