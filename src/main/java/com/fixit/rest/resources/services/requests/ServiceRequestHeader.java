package com.fixit.rest.resources.services.requests;

import org.bson.types.ObjectId;
import org.springframework.util.StringUtils;

import com.fixit.rest.resources.services.ServiceError;
import com.fixit.rest.resources.services.responses.ServiceResponseHeader;

/**
 * Created by Kostyantin on 3/20/2017.
 */

public class ServiceRequestHeader {

    private String userId;
    private String installationId;
    private String latestScreen;

    public ServiceRequestHeader() { }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getInstallationId() {
		return installationId;
	}

	public void setInstallationId(String installationId) {
		this.installationId = installationId;
	}

	public String getLatestScreen() {
        return latestScreen;
    }

    public void setLatestScreen(String latestScreen) {
        this.latestScreen = latestScreen;
    }
	
	public void validate(ServiceResponseHeader header, boolean userIdRequired) {
		if(!StringUtils.isEmpty(userId) && !ObjectId.isValid(userId)) {
			header.addError(ServiceError.INVALID_DATA, "Invalid field 'userId'");
		} else if(userIdRequired && StringUtils.isEmpty(userId)) {
			header.addError(ServiceError.MISSING_DATA, "Missing required field 'userId'");
		}
		if(!StringUtils.isEmpty(installationId) && !ObjectId.isValid(installationId)) {
			header.addError(ServiceError.INVALID_DATA, "Invalid field 'installationId'");
		}
	}

	@Override
	public String toString() {
		return "ServiceRequestHeader [userId=" + userId + ", installationId=" + installationId + ", latestScreen="
				+ latestScreen + "]";
	}

}
