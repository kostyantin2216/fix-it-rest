/**
 * 
 */
package com.fixit.rest.resources.services.user.responses;

import java.util.List;

import org.bson.types.ObjectId;

import com.fixit.core.data.mongo.OrderData;
import com.fixit.rest.resources.services.responses.ResponseData;

/**
 * @author 		Kostyantin
 * @createdAt 	2017/05/20 20:54:04 GMT+3
 */
public class UserRegistrationResponseData implements ResponseData {
	private boolean existingEmail;
	private boolean newUser;
    private ObjectId userId;
    private List<OrderData> orderHistory;

    public UserRegistrationResponseData(boolean existingEmail, boolean newUser, ObjectId userId, List<OrderData> orderHistory) {
		this.existingEmail = existingEmail;
		this.newUser = newUser;
		this.userId = userId;
		this.orderHistory = orderHistory;
	}

	public boolean isExistingEmail() {
        return existingEmail;
    }

    public void setExistingEmail(boolean existingEmail) {
        this.existingEmail = existingEmail;
    }    

    public boolean isNewUser() {
		return newUser;
	}

	public void setNewUser(boolean newUser) {
		this.newUser = newUser;
	}

	public ObjectId getUserId() {
        return userId;
    }

    public void setUserId(ObjectId userId) {
        this.userId = userId;
    }
    
    public List<OrderData> getOrderHistory() {
		return orderHistory;
	}

	public void setOrderHistory(List<OrderData> orderHistory) {
		this.orderHistory = orderHistory;
	}

	@Override
	public String toString() {
		return "UserRegistrationResponseData [existingEmail=" + existingEmail + ", newUser=" + newUser + ", userId="
				+ userId + ", orderHistory=" + orderHistory + "]";
	}

}
