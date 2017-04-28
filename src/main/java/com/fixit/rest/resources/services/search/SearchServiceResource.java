/**
 * 
 */
package com.fixit.rest.resources.services.search;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fixit.components.search.SearchExecutor;
import com.fixit.components.search.SearchParams;
import com.fixit.components.search.SearchResult;
import com.fixit.core.config.CoreContextProvider;
import com.fixit.core.dao.mongo.MapAreaDao;
import com.fixit.core.dao.sql.ReviewDao;
import com.fixit.core.data.MapAreaType;
import com.fixit.core.data.MutableLatLng;
import com.fixit.core.data.mongo.MapArea;
import com.fixit.core.data.mongo.Tradesman;
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
	
	private final SearchExecutor mSearchExecutor;
	
	@Autowired
	public SearchServiceResource(SearchExecutor searchExecutor) {
		this.mSearchExecutor = searchExecutor;
	}
	
	@POST
	@Path("startTradesmanSearch")
	public ServiceResponse<TradesmenSearchResponseData> startTradesmanSearch(ServiceRequest<TradesmenSearchRequestData> request) {
		ServiceResponseHeader respHeader = createHeader();
		TradesmenSearchRequestData reqData = request.getData();
		
		reqData.validate(respHeader);
		if(!respHeader.hasErrors()) {
			MapAreaDao mapAreaDao = CoreContextProvider.getMapAreaDao();
			MutableLatLng location = reqData.getLocation();
			
			MapArea mapArea = mapAreaDao.getMapAreaAtLocationForType(
					location.getLng(), 
					location.getLat(), 
					MapAreaType.Ward
			);
			if(mapArea != null) {
				SearchParams searchParams = new SearchParams(reqData.getProfessionId(), mapArea);
				String searchId = mSearchExecutor.createSearch(searchParams);
				
				return new ServiceResponse<TradesmenSearchResponseData>(respHeader, new TradesmenSearchResponseData(searchId));
			} else {
				respHeader.addError(ServiceError.UNSUPPORTED, "location unsupported");
			}
		}
		
		return new ServiceResponse<TradesmenSearchResponseData>(respHeader, null);
	}
	
	@POST
	@Path("fetchTradesmenResults")
	public ServiceResponse<TradesmenSearchResultResponseData> fetchTradesmenResults(ServiceRequest<TradesmenSearchResultRequestData> request) {
		ServiceResponseHeader respHeader = createHeader();
		TradesmenSearchResultRequestData reqData = request.getData();
		
		reqData.validate(respHeader);
		if(!respHeader.hasErrors()) {
			SearchResult searchResult = mSearchExecutor.getResult(reqData.getSearchKey());
			
			TradesmenSearchResultResponseData respData = new TradesmenSearchResultResponseData();
			if(searchResult.isComplete) {
				respData.setComplete(true);
				if(searchResult.errors.isEmpty()) {
					if(!searchResult.tradesmen.isEmpty()) {
						respData.setTradesmen(searchResult.tradesmen);
						
						ReviewDao reviewDao = CoreContextProvider.getReviewDao();
						Map<String, Long> reviewsForTradesman = new HashMap<>();
						for(Tradesman tradesman : searchResult.tradesmen) {
							String tradesmanId = tradesman.get_id().toString();
							reviewsForTradesman.put(tradesmanId, reviewDao.getCountForTradesman(tradesmanId));
						}
						respData.setReviewCountForTradesmen(reviewsForTradesman);
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
