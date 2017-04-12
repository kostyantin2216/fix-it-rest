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
	
	private String searchKey;

	public TradesmenSearchResultRequestData(String searchKey) {
		this.searchKey = searchKey;
	}

	public String getSearchKey() {
		return searchKey;
	}

	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}

	public void validate(ServiceResponseHeader respHeader) {
		if(StringUtils.isEmpty(searchKey)) {
			respHeader.addError(ServiceError.MISSING_DATA, "cannot fetch results without a searchId");
		}
	}

	@Override
	public String toString() {
		return "TradesmenSearchResultRequestData [sarchKey=" + searchKey + "]";
	}
	
}
