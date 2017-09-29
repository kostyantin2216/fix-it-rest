/**
 * 
 */
package com.fixit.rest.resources.services.data.requests;

import com.fixit.rest.resources.services.ServiceError;
import com.fixit.rest.resources.services.requests.RequestData;
import com.fixit.rest.resources.services.responses.ServiceResponseHeader;

/**
 * @author 		Kostyantin
 * @createdAt 	2017/08/26 16:23:22 GMT+3
 */
public class TradesmenRequestData implements RequestData {

	private String[] tradesmenIds;
	
	public TradesmenRequestData() { }
	
	public TradesmenRequestData(String[] tradesmenIds) {
		this.tradesmenIds = tradesmenIds;
	}

	public String[] getTradesmenIds() {
		return tradesmenIds;
	}

	public void setTradesmenIds(String[] tradesmenIds) {
		this.tradesmenIds = tradesmenIds;
	}

	@Override
	public void validate(ServiceResponseHeader respHeader) {
		if(tradesmenIds == null || tradesmenIds.length == 0) {
			respHeader.addError(ServiceError.MISSING_DATA, "missing tradesmen");
		}
	}

}
