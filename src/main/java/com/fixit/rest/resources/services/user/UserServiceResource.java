/**
 * 
 */
package com.fixit.rest.resources.services.user;

import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fixit.components.orders.OrderController;
import com.fixit.components.registration.UserRegistrationController;
import com.fixit.components.registration.UserRegistrationController.RegistrationResult;
import com.fixit.core.data.mongo.OrderData;
import com.fixit.rest.resources.services.BaseServiceResource;
import com.fixit.rest.resources.services.ServiceError;
import com.fixit.rest.resources.services.requests.ServiceRequest;
import com.fixit.rest.resources.services.responses.ServiceResponse;
import com.fixit.rest.resources.services.responses.ServiceResponseHeader;
import com.fixit.rest.resources.services.user.requests.UserRegistrationRequestData;
import com.fixit.rest.resources.services.user.responses.UserRegistrationResponseData;

/**
 * @author 		Kostyantin
 * @createdAt 	2017/05/20 20:50:24 GMT+3
 */
@Component
public class UserServiceResource extends BaseServiceResource {

	public final static String END_POINT = "users";
	
	private final UserRegistrationController mUserRegistrationController;
	private final OrderController mOrderController;
	
	@Autowired
	public UserServiceResource(UserRegistrationController userRegistrationController, OrderController orderController) {
		mUserRegistrationController = userRegistrationController;
		mOrderController = orderController; 
	}
	
	@POST
	@Path("registerUser")
	public ServiceResponse<UserRegistrationResponseData> registerUser(ServiceRequest<UserRegistrationRequestData> request) {
		ServiceResponseHeader respHeader = createRespHeader(request);
		
		if(!respHeader.hasErrors()) {
			RegistrationResult result = mUserRegistrationController.findOrRegister(
					request.getData().toUser(), 
					request.getHeader().getInstallationId()
			);
			
			if(result.invalidAppInstallationId) {
				respHeader.addError(ServiceError.INVALID_DATA, "Invalid installationId");
			} else {
				List<OrderData> orderHistory = null;
				if(!result.newUser) {
					orderHistory = mOrderController.getUserOrderHistory(result.user.get_id());
				}
				return new ServiceResponse<UserRegistrationResponseData>(respHeader, 
						new UserRegistrationResponseData(result.emailExists, result.newUser, result.user.get_id(), orderHistory)
				);
			}
		}
		
		return new ServiceResponse<UserRegistrationResponseData>(respHeader, null);
	}
	
	@Override
	public String getEndPoint() {
		return END_POINT;
	}

}
