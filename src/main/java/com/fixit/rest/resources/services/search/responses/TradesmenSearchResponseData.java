package com.fixit.rest.resources.services.search.responses;

import com.fixit.rest.resources.services.responses.ResponseData;

/**
 * @author Kostyantin
 * @createdAt 2017/04/04 00:19:34 GMT+3
 */
public class TradesmenSearchResponseData implements ResponseData {

	private String searchId;

	public TradesmenSearchResponseData(String searchId) {
		this.searchId = searchId;
	}

	public String getSearchId() {
		return searchId;
	}

	public void setSearchId(String searchId) {
		this.searchId = searchId;
	}

	@Override
	public String toString() {
		return "TradesmenSearchResponseData [searchId=" + searchId + "]";
	}
	
}
