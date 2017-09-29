/**
 * 
 */
package com.fixit.rest.resources.services.user.requests;

import java.util.Date;

import org.springframework.util.StringUtils;

import com.fixit.core.data.mongo.User;
import com.fixit.rest.resources.services.ServiceError;
import com.fixit.rest.resources.services.requests.RequestData;
import com.fixit.rest.resources.services.responses.ServiceResponseHeader;

/**
 * @author 		Kostyantin
 * @createdAt 	2017/05/20 20:53:50 GMT+3
 */
public class UserRegistrationRequestData implements RequestData {
	
	private String firstName;
    private String lastName;
    private String email;
    private String telephone;
    private String avatarUrl;
    private String googleId;
    private String facebookId;

    public UserRegistrationRequestData() { }

    public UserRegistrationRequestData(String firstName, String lastName, String email, String telephone,
                                       String avatarUrl, String googleId, String facebookId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.telephone = telephone;
        this.avatarUrl = avatarUrl;
        this.googleId = googleId;
        this.facebookId = facebookId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

	@Override
	public void validate(ServiceResponseHeader respHeader) {
		if(StringUtils.isEmpty(firstName)) {
			respHeader.addError(ServiceError.MISSING_DATA, "Missing firstName");
		}
		
		if(StringUtils.isEmpty(lastName)) {
			respHeader.addError(ServiceError.MISSING_DATA, "Missing lastName");
		}
		
		if(StringUtils.isEmpty(email)) {
			respHeader.addError(ServiceError.MISSING_DATA, "Missing email");
		}
		
		if(StringUtils.isEmpty(telephone)) {
			respHeader.addError(ServiceError.MISSING_DATA, "Missing telephone");
		}
	}
	
	public User toUser() {
		return new User(
				firstName + (lastName != null ? !lastName.equals("null") ? " " + lastName : "" : ""),
				email,
				telephone,
				avatarUrl,
				facebookId,
				googleId,
				new Date()
		);
	}

	@Override
	public String toString() {
		return "UserRegistrationRequestData [firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", telephone=" + telephone + ", avatarUrl=" + avatarUrl + ", googleId=" + googleId + ", facebookId="
				+ facebookId + "]";
	}

}
