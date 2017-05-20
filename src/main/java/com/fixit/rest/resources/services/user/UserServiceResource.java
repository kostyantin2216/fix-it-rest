/**
 * 
 */
package com.fixit.rest.resources.services.user;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

import com.fixit.components.registration.users.UserRegistrant;
import com.fixit.components.registration.users.UserRegistrant.RegistrationResult;
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
public class UserServiceResource extends BaseServiceResource {

	public final static String END_POINT = "users";
	
	private final UserRegistrant mUserRegistrant;
	
	public UserServiceResource(UserRegistrant userRegistrant) {
		mUserRegistrant = userRegistrant;
	}
	
	@POST
	@Path("registerUser")
	public ServiceResponse<UserRegistrationResponseData> registerUser(ServiceRequest<UserRegistrationRequestData> request) {
		ServiceResponseHeader respHeader = createRespHeader(request);
		
		if(!respHeader.hasErrors()) {
			RegistrationResult result = mUserRegistrant.findOrRegister(
					request.getData().toUser(), 
					request.getHeader().getInstallationId()
			);
			
			if(result.invalidAppInstallationId) {
				respHeader.addError(ServiceError.INVALID_DATA, "Invalid installationId");
			} else {
				return new ServiceResponse<UserRegistrationResponseData>(respHeader, 
						new UserRegistrationResponseData(result.emailExists, result.user.get_id())
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
