/**
 * 
 */
package com.fixit.rest.resources.services.data;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fixit.core.dao.mongo.TradesmanDao;
import com.fixit.core.dao.mongo.UserDao;
import com.fixit.core.dao.sql.ReviewDao;
import com.fixit.core.data.mongo.Tradesman;
import com.fixit.core.data.sql.Review;
import com.fixit.rest.resources.services.BaseServiceResource;
import com.fixit.rest.resources.services.data.requests.TradesmanReviewDataRequestData;
import com.fixit.rest.resources.services.data.requests.TradesmenRequestData;
import com.fixit.rest.resources.services.data.responses.TradesmanReviewDataResponseData;
import com.fixit.rest.resources.services.data.responses.TradesmenResponseData;
import com.fixit.rest.resources.services.requests.ServiceRequest;
import com.fixit.rest.resources.services.responses.ServiceResponse;
import com.fixit.rest.resources.services.responses.ServiceResponseHeader;

/**
 * @author Kostyantin
 * @createdAt 2017/05/20 18:29:49 GMT+3
 */
@Component
public class DataServiceResource extends BaseServiceResource {

	public final static String END_POINT = "data";

	private final ReviewDao mReviewDao;
	private final TradesmanDao mTradesmanDao;
	private final UserDao mUserDao;

	@Autowired
	public DataServiceResource(ReviewDao mReviewDao, TradesmanDao tradesmanDao, UserDao mUserDao) {
		this.mReviewDao = mReviewDao;
		this.mTradesmanDao = tradesmanDao;
		this.mUserDao = mUserDao;
	}

	@POST
	@Path("reviewsForTradesman")
	public ServiceResponse<TradesmanReviewDataResponseData> getReviewsForTradesman(
			ServiceRequest<TradesmanReviewDataRequestData> request) {
		ServiceResponseHeader respHeader = createRespHeader(request);

		if (!respHeader.hasErrors()) {
			List<Review> reviews = mReviewDao.getReviewsForTradesman(request.getData().getTradesmanId())
					.stream()
					.filter((v) -> v.getIsOnDisplay())
					.collect(Collectors.toList());
			
			Set<ObjectId> userIds = reviews
					.stream()
					.map((v) -> new ObjectId(v.getUserId())) 
					.collect(Collectors.toSet());
			
			final Map<ObjectId, Map<String, String>> data = mUserDao.getDataForReviews(userIds);
			
			reviews = reviews
					.stream()
					.filter((v) -> data.containsKey(new ObjectId(v.getUserId())))
					.collect(Collectors.toList());

			return new ServiceResponse<TradesmanReviewDataResponseData>(respHeader,
					new TradesmanReviewDataResponseData(data, reviews));
		}

		return new ServiceResponse<TradesmanReviewDataResponseData>(respHeader, null);
	}
	
	@POST
	@Path("tradesmen")
	public ServiceResponse<TradesmenResponseData> getTradesmen(ServiceRequest<TradesmenRequestData> request) {
		ServiceResponseHeader respHeader = createRespHeader(request);

		if (!respHeader.hasErrors()) {
			String[] tradesmenIds = request.getData().getTradesmenIds();
			Tradesman[] result = new Tradesman[tradesmenIds.length];
			for(int i = 0; i < result.length; i++) {
				ObjectId tradesmanId = new ObjectId(tradesmenIds[i]);
				result[i] = mTradesmanDao.findById(tradesmanId);
			}
			return new ServiceResponse<TradesmenResponseData>(respHeader, new TradesmenResponseData(result));
		}
		
		return new ServiceResponse<TradesmenResponseData>(respHeader, null);
	}

	@Override
	public String getEndPoint() {
		return END_POINT;
	}

}
