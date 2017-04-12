package com.fixit.rest.resources.services.search.responses;

import com.fixit.rest.resources.services.responses.ResponseData;

/**
 * @author Kostyantin
 * @createdAt 2017/04/04 00:19:34 GMT+3
 */
public class TradesmenSearchResponseData implements ResponseData {

	private String searchKey;

	public TradesmenSearchResponseData(String searchKey) {
		this.searchKey = searchKey;
	}

	public String getSearchKey() {
		return searchKey;
	}

	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}

	@Override
	public String toString() {
		return "TradesmenSearchResponseData [searchKey=" + searchKey + "]";
	}
	
}
