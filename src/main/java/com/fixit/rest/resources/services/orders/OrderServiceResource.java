/**
 * 
 */
package com.fixit.rest.resources.services.orders;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fixit.components.orders.TradesmanOrderController;
import com.fixit.core.dao.mongo.TradesmanDao;
import com.fixit.core.dao.mongo.UserDao;
import com.fixit.core.data.mongo.User;
import com.fixit.rest.resources.services.BaseServiceResource;
import com.fixit.rest.resources.services.ServiceError;
import com.fixit.rest.resources.services.orders.requests.TradesmenOrderRequestData;
import com.fixit.rest.resources.services.orders.responses.TradesmenOrderResponseData;
import com.fixit.rest.resources.services.requests.ServiceRequest;
import com.fixit.rest.resources.services.responses.ServiceResponse;
import com.fixit.rest.resources.services.responses.ServiceResponseHeader;

/**
 * @author 		Kostyantin
 * @createdAt 	2017/05/28 17:47:08 GMT+3
 */
@Component
public class OrderServiceResource extends BaseServiceResource {

	public final static String END_POINT = "orders";
	
	private final UserDao mUserDao;
	private final TradesmanOrderController mOrderController;
	
	@Autowired
	public OrderServiceResource(UserDao userDao, TradesmanOrderController orderController) {
		mUserDao = userDao;
		mOrderController = orderController;
	}
	
	@POST
	@Path("orderTradesmen")
	public ServiceResponse<TradesmenOrderResponseData> orderTradesmen(ServiceRequest<TradesmenOrderRequestData> request) {
		ServiceResponseHeader respHeader = createRespHeader(request, true);
		
		if(!respHeader.hasErrors()) {
			TradesmenOrderRequestData reqData = request.getData();
			TradesmenOrderResponseData respData = new TradesmenOrderResponseData();
			
			User user = mUserDao.findById(new ObjectId(request.getHeader().getUserId()));
			if(user != null) {
				mOrderController.orderTradesmen(
						user, 
						reqData.getTradesmen(), 
						reqData.getJobLocation(), 
						reqData.getReason()
				);
				respData.setComplete(true);
				return new ServiceResponse<TradesmenOrderResponseData>(respHeader, respData);
			} else {
				respHeader.addError(ServiceError.INVALID_DATA, "Invalid userId, user does not exist");
			}			
		}
		
		return new ServiceResponse<TradesmenOrderResponseData>(respHeader, null);
	}
	
	@Override
	public String getEndPoint() {
		return END_POINT;
	}

}
