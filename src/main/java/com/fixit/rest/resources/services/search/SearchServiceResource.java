/**
 * 
 */
package com.fixit.rest.resources.services.search;

import java.util.Optional;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fixit.components.search.SearchController;
import com.fixit.components.search.SearchResult;
import com.fixit.rest.resources.services.BaseServiceResource;
import com.fixit.rest.resources.services.ServiceError;
import com.fixit.rest.resources.services.requests.ServiceRequest;
import com.fixit.rest.resources.services.responses.ServiceResponse;
import com.fixit.rest.resources.services.responses.ServiceResponseHeader;
import com.fixit.rest.resources.services.search.requests.TradesmenSearchRequestData;
import com.fixit.rest.resources.services.search.requests.TradesmenSearchResultRequestData;
import com.fixit.rest.resources.services.search.responses.TradesmenSearchResponseData;
import com.fixit.rest.resources.services.search.responses.TradesmenSearchResultResponseData;

/**
 * @author 		Kostyantin
 * @createdAt 	2017/04/11 18:50:43 GMT+3
 */
@Component
public class SearchServiceResource extends BaseServiceResource {

	public final static String END_POINT = "search";
	
	private final SearchController mSearchController;
	
	@Autowired
	public SearchServiceResource(SearchController searchController) {
		mSearchController = searchController;
	}
	
	@POST
	@Path("startTradesmanSearch")
	public ServiceResponse<TradesmenSearchResponseData> startTradesmanSearch(ServiceRequest<TradesmenSearchRequestData> request) {
		ServiceResponseHeader respHeader = createRespHeader(request);
		
		if(!respHeader.hasErrors()) {
			TradesmenSearchRequestData reqData = request.getData();
			
			Optional<String> searchId = mSearchController.createSearch(reqData.getProfessionId(), reqData.getLocation());
			if(searchId.isPresent()) {
				return new ServiceResponse<TradesmenSearchResponseData>(respHeader, new TradesmenSearchResponseData(searchId.get()));
			} else {
				respHeader.addError(ServiceError.UNSUPPORTED, "location unsupported");
			}
		}
		
		return new ServiceResponse<TradesmenSearchResponseData>(respHeader, null);
	}
	
	@POST
	@Path("fetchTradesmenResults")
	public ServiceResponse<TradesmenSearchResultResponseData> fetchTradesmenResults(ServiceRequest<TradesmenSearchResultRequestData> request) {
		ServiceResponseHeader respHeader = createRespHeader(request);
		
		if(!respHeader.hasErrors()) {
			TradesmenSearchResultRequestData reqData = request.getData();
			SearchResult searchResult = mSearchController.getSearchResult(reqData.getSearchKey());
			
			TradesmenSearchResultResponseData respData = new TradesmenSearchResultResponseData();
			if(searchResult.isComplete) {
				respData.setComplete(true);
				if(searchResult.errors.isEmpty()) {
					if(!searchResult.tradesmen.isEmpty()) {
						respData.setTradesmen(searchResult.tradesmen);
						respData.setReviewCountForTradesmen(searchResult.reviewCountForTradesmen);
					}
				} else {
					respHeader.addError(ServiceError.UNKNOWN, searchResult.errorToString());
				}
			}
			return new ServiceResponse<TradesmenSearchResultResponseData>(respHeader, respData);
		}
		
		return new ServiceResponse<TradesmenSearchResultResponseData>(respHeader, null);
	}
	
	@Override
	public String getEndPoint() {
		return END_POINT;
	}

}
