/**
 * 
 */
package com.fixit.rest.resources.services.orders;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fixit.components.orders.OrderController;
import com.fixit.core.dao.mongo.UserDao;
import com.fixit.core.dao.sql.ProfessionDao;
import com.fixit.core.data.OrderType;
import com.fixit.core.data.mongo.OrderData;
import com.fixit.core.data.mongo.User;
import com.fixit.core.data.sql.JobReason;
import com.fixit.core.data.sql.Profession;
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
	private final ProfessionDao mProfessionDao;
	private final OrderController mOrderController;
	
	@Autowired
	public OrderServiceResource(ProfessionDao professionDao, UserDao userDao, OrderController orderController) {
		mProfessionDao = professionDao;
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
			
			Profession profession = mProfessionDao.findById(reqData.getProfessionId());
			String userId = request.getHeader().getUserId();
			if(profession == null) {
				respHeader.addError(ServiceError.INVALID_DATA, "Invalid profession, profession with id " + reqData.getProfessionId() + " does not exist");
			} else if(profession.getIsActive()) {
				User user = mUserDao.findById(new ObjectId(userId));
				if(user != null) {
					JobReason[] jobReasons = reqData.getJobReasons();
					OrderData orderData = mOrderController.orderTradesmen(
							OrderType.REGULAR,
							profession,
							user, 
							reqData.getTradesmen(), 
							reqData.getJobLocation(), 
							jobReasons != null ? jobReasons : new JobReason[0],
							reqData.getComment()
					);
					respData.setOrderData(orderData);
					return new ServiceResponse<TradesmenOrderResponseData>(respHeader, respData);
				} else {
					respHeader.addError(ServiceError.INVALID_DATA, "Invalid userId, user with id " + userId + " does not exist");
				}
			} else {
				respHeader.addError(ServiceError.INVALID_DATA, "Invalid profession, " + profession.getName() + " is disabled");
			}
		}
		
		return new ServiceResponse<TradesmenOrderResponseData>(respHeader, null);
	}
	
	@Override
	public String getEndPoint() {
		return END_POINT;
	}

}
