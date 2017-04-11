/**
 * 
 */
package com.fixit.rest.resources.services.search.requests;

import org.springframework.util.StringUtils;

import com.fixit.rest.resources.services.ServiceError;
import com.fixit.rest.resources.services.requests.RequestData;
import com.fixit.rest.resources.services.responses.ServiceResponseHeader;

/**
 * @author 		Kostyantin
 * @createdAt 	2017/04/04 00:33:58 GMT+3
 */
public class TradesmenSearchResultRequestData implements RequestData {
	
	private String searchId;

	public TradesmenSearchResultRequestData(String searchId) {
		this.searchId = searchId;
	}

	public String getSearchId() {
		return searchId;
	}

	public void setSearchId(String searchId) {
		this.searchId = searchId;
	}
	
	public void validate(ServiceResponseHeader respHeader) {
		if(StringUtils.isEmpty(searchId)) {
			respHeader.addError(ServiceError.MISSING_DATA, "cannot fetch results without a searchId");
		}
	}

	@Override
	public String toString() {
		return "TradesmenSearchResultResponseData [searchId=" + searchId + "]";
	}
	
}
