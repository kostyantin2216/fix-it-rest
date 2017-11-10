/**
 * 
 */
package com.fixit.rest.resources.services.search;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fixit.components.search.SearchController;
import com.fixit.components.search.SearchResult;
import com.fixit.core.dao.mongo.MapAreaDao;
import com.fixit.core.dao.sql.ProfessionDao;
import com.fixit.core.data.MapAreaType;
import com.fixit.core.data.MutableLatLng;
import com.fixit.core.data.mongo.MapArea;
import com.fixit.core.data.sql.Profession;
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
	private final ProfessionDao mProfessionDao;
	private final MapAreaDao mMapAreaDao;
	
	@Autowired
	public SearchServiceResource(SearchController searchController, ProfessionDao professionDao, MapAreaDao mapAreaDao) {
		mSearchController = searchController;
		mProfessionDao = professionDao;
		mMapAreaDao = mapAreaDao;
	}
	
	@POST
	@Path("startTradesmanSearch")
	public ServiceResponse<TradesmenSearchResponseData> startTradesmanSearch(ServiceRequest<TradesmenSearchRequestData> request) {
		ServiceResponseHeader respHeader = createRespHeader(request);
		
		if(!respHeader.hasErrors()) {
			TradesmenSearchRequestData reqData = request.getData();
			
			Profession profession = mProfessionDao.findById(reqData.getProfessionId());
			
			if(profession != null) {
				if(profession.getIsActive()) {
					MutableLatLng location = reqData.getLocation();
					MapArea mapArea = mMapAreaDao.getMapAreaAtLocationForType(
							location.getLng(), 
							location.getLat(), 
							MapAreaType.Ward
					);
					
					if(mapArea != null) {
						String searchId = mSearchController.createSearch(profession, mapArea);
						return new ServiceResponse<TradesmenSearchResponseData>(respHeader, new TradesmenSearchResponseData(searchId));
					} else {
						respHeader.addError(ServiceError.UNSUPPORTED, "location unsupported");
					}
				} else {
					respHeader.addError(ServiceError.UNSUPPORTED, "profession is currently unsupported");
				}
			} else {
				respHeader.addError(ServiceError.INVALID_DATA, "invalid profession id");
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
			if(searchResult.isComplete()) {
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
