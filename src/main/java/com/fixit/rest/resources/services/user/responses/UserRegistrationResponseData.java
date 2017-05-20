/**
 * 
 */
package com.fixit.rest.resources.services.user.responses;

import org.bson.types.ObjectId;

import com.fixit.rest.resources.services.responses.ResponseData;

/**
 * @author 		Kostyantin
 * @createdAt 	2017/05/20 20:54:04 GMT+3
 */
public class UserRegistrationResponseData implements ResponseData {
	private boolean existingEmail;
    private ObjectId userId;

    public UserRegistrationResponseData(boolean existingEmail, ObjectId userId) {
		this.existingEmail = existingEmail;
		this.userId = userId;
	}

	public boolean isExistingEmail() {
        return existingEmail;
    }

    public void setExistingEmail(boolean existingEmail) {
        this.existingEmail = existingEmail;
    }

    public ObjectId getUserId() {
        return userId;
    }

    public void setUserId(ObjectId userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "UserRegistrationResponseData{" +
                "existingEmail=" + existingEmail +
                ", userId='" + userId + '\'' +
                '}';
    }
}
