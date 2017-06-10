/**
 * 
 */
package com.fixit.rest.resources.services.data.requests;

import org.bson.types.ObjectId;

import com.fixit.rest.resources.services.ServiceError;
import com.fixit.rest.resources.services.requests.RequestData;
import com.fixit.rest.resources.services.responses.ServiceResponseHeader;

/**
 * @author 		Kostyantin
 * @createdAt 	2017/05/20 18:34:21 GMT+3
 */
public class TradesmanReviewDataRequestData implements RequestData {

	private String tradesmanId;
	
	public String getTradesmanId() {
		return tradesmanId;
	}

	public void setTradesmanId(String tradesmanId) {
		this.tradesmanId = tradesmanId;
	}

	@Override
	public void validate(ServiceResponseHeader header) {
		if(tradesmanId == null) {
			header.addError(ServiceError.MISSING_DATA, "missing tradesmanId");
		} else if(!ObjectId.isValid(tradesmanId)) {
			header.addError(ServiceError.INVALID_DATA, "Invalid tradesmanId");
		}
	}
	
	@Override
	public String toString() {
		return "TradesmanReviewDataRequestData [tradesmanId=" + tradesmanId + "]";
	}
		
}
