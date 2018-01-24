/**
 * 
 */
package com.fixit.rest.resources.services.orders;

import java.util.Arrays;
import java.util.Optional;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fixit.components.orders.OrderController;
import com.fixit.components.orders.OrderParams;
import com.fixit.components.orders.OrderRequestController;
import com.fixit.core.dao.mongo.TradesmanDao;
import com.fixit.core.dao.mongo.UserDao;
import com.fixit.core.dao.sql.ProfessionDao;
import com.fixit.core.dao.sql.TrafficSourceDao;
import com.fixit.core.data.JobLocation;
import com.fixit.core.data.OrderType;
import com.fixit.core.data.mongo.OrderData;
import com.fixit.core.data.mongo.OrderRequest;
import com.fixit.core.data.mongo.Tradesman;
import com.fixit.core.data.mongo.User;
import com.fixit.core.data.sql.JobReason;
import com.fixit.core.data.sql.Profession;
import com.fixit.core.data.sql.TrafficSource;
import com.fixit.core.utils.Constants;
import com.fixit.rest.resources.services.BaseServiceResource;
import com.fixit.rest.resources.services.ServiceError;
import com.fixit.rest.resources.services.orders.requests.TradesmenOrderRequestData;
import com.fixit.rest.resources.services.orders.responses.TradesmenOrderRequestResponseData;
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
	private final TradesmanDao mTradesmanDao;
	private final TrafficSourceDao mTrafficSourceDao;
	private final OrderController mOrderController;
	private final OrderRequestController mOrderRequestController;
	
	@Autowired
	public OrderServiceResource(ProfessionDao professionDao, UserDao userDao, TradesmanDao tradesmanDao, TrafficSourceDao trafficSourceDao, 
								OrderController orderController, OrderRequestController orderRequestController) {
		mProfessionDao = professionDao;
		mUserDao = userDao;
		mTradesmanDao = tradesmanDao;
		mTrafficSourceDao = trafficSourceDao;
		mOrderController = orderController;
		mOrderRequestController = orderRequestController;
	}
	
	@POST
	@Path("orderTradesmen")
	public ServiceResponse<TradesmenOrderResponseData> orderTradesmen(ServiceRequest<TradesmenOrderRequestData> request) {
		ServiceResponseHeader respHeader = createRespHeader(request, true);
		
		if(!respHeader.hasErrors()) {
			OrderParams orderParams = createParams(request, respHeader);
			
			if(!respHeader.hasErrors()) {
				TradesmenOrderResponseData respData = new TradesmenOrderResponseData();
				OrderType orderType = orderParams.getOrderType();
				
				if(orderType == null || orderType == OrderType.SEARCH || orderType == OrderType.QUICK) {
					Optional<OrderData> orderData = mOrderController.orderTradesmen(orderParams);
					
					if(orderData.isPresent()) {
						respData.setOrderData(orderData.get());
					} else {
						respHeader.addError(ServiceError.UNKNOWN, "Error occured during order");
					}
				} else {
					respHeader.addError(ServiceError.INVALID_DATA, "Order type " + orderType + " is not supported");
				}
				
				if(!respHeader.hasErrors()) {
					return new ServiceResponse<TradesmenOrderResponseData>(respHeader, respData);
				}
			}
		}
		
		return new ServiceResponse<TradesmenOrderResponseData>(respHeader, null);
	}
	
	@POST
	@Path("requestTradesmen")
	public ServiceResponse<TradesmenOrderRequestResponseData> requestTradesmen(ServiceRequest<TradesmenOrderRequestData> request) {
		ServiceResponseHeader respHeader = createRespHeader(request, true);
		
		if(!respHeader.hasErrors()) {
			OrderParams orderParams = createParams(request, respHeader);
			
			if(!respHeader.hasErrors()) {
				TradesmenOrderRequestResponseData respData = new TradesmenOrderRequestResponseData();
				
				OrderRequest orderRequest = mOrderRequestController.newRequest(orderParams);
				respData.setOrderRequest(orderRequest);
				
				return new ServiceResponse<TradesmenOrderRequestResponseData>(respHeader, respData);
			}
		}
		
		return new ServiceResponse<>(respHeader, null);
	}
	
	private OrderParams createParams(ServiceRequest<TradesmenOrderRequestData> request, ServiceResponseHeader respHeader) {
		TradesmenOrderRequestData reqData = request.getData();
		
		Profession profession = mProfessionDao.findById(reqData.getProfessionId());
		String userId = request.getHeader().getUserId();
		if(profession == null) {
			respHeader.addError(ServiceError.INVALID_DATA, "Invalid profession, profession with id " + reqData.getProfessionId() + " does not exist");
		} else if(profession.getIsActive()) {
			User user = mUserDao.findById(new ObjectId(userId));
			if(user != null) {
				JobLocation location = reqData.getJobLocation();
				JobReason[] jobReasons = reqData.getJobReasons();
				String comment = reqData.getComment();
				
				OrderType orderType = reqData.getOrderType();
				TrafficSource trafficSource = mTrafficSourceDao.findById(Constants.TRAFFIC_SRC_ANDROID_APP);
				
				if(orderType == null || orderType == OrderType.SEARCH || orderType == OrderType.QUICK) {
					OrderParams.Builder opBuilder = new OrderParams.Builder(orderType)
							.forProfession(profession)
							.byUser(user)
							.atLocation(location)
							.forReasons(jobReasons)
							.withComment(comment)
							.fromTrafficSource(trafficSource);
					
					
					if(orderType != OrderType.QUICK) {
						Tradesman[] tradesmen = reqData.getTradesmen();
						
						if(tradesmen == null) {
							tradesmen = Arrays.stream(reqData.getTradesmenIds())
									.map(id -> mTradesmanDao.findById(new ObjectId(id)))
									.toArray(Tradesman[]::new);
						}
						opBuilder.withTradesmen(tradesmen);
					}
					
					
					return opBuilder.build();
				} else {
					respHeader.addError(ServiceError.INVALID_DATA, "Order type " + orderType + " is not supported");
				}
			} else {
				respHeader.addError(ServiceError.INVALID_DATA, "Invalid userId, user with id " + userId + " does not exist");
			}
		} else {
			respHeader.addError(ServiceError.INVALID_DATA, "Invalid profession, " + profession.getName() + " is disabled");
		}
		return null;
	}
	
	@Override
	public String getEndPoint() {
		return END_POINT;
	}

}
