/**
 * 
 */
package com.fixit.rest.resources.services.data;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fixit.core.dao.mongo.UserDao;
import com.fixit.core.dao.sql.ReviewDao;
import com.fixit.core.data.sql.Review;
import com.fixit.rest.resources.services.BaseServiceResource;
import com.fixit.rest.resources.services.data.requests.TradesmanReviewDataRequestData;
import com.fixit.rest.resources.services.data.responses.TradesmanReviewDataResponseData;
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
	private final UserDao mUserDao;

	@Autowired
	public DataServiceResource(ReviewDao mReviewDao, UserDao mUserDao) {
		this.mReviewDao = mReviewDao;
		this.mUserDao = mUserDao;
	}

	@POST
	@Path("reviewsForTradesman")
	public ServiceResponse<TradesmanReviewDataResponseData> getReviewsForTradesman(
			ServiceRequest<TradesmanReviewDataRequestData> request) {
		ServiceResponseHeader respHeader = createRespHeader(request);

		if (!respHeader.hasErrors()) {
			List<Review> reviews = mReviewDao.getReviewsForTradesman(request.getData().getTradesmanId());
			Set<ObjectId> userIds = new HashSet<>();
			for (Review review : reviews) {
				userIds.add(new ObjectId(review.getUserId()));
			}
			Map<ObjectId, Map<String, String>> data = mUserDao.getDataForReviews(userIds);

			return new ServiceResponse<TradesmanReviewDataResponseData>(respHeader,
					new TradesmanReviewDataResponseData(data, reviews));
		}

		return new ServiceResponse<TradesmanReviewDataResponseData>(respHeader, null);
	}

	@Override
	public String getEndPoint() {
		return END_POINT;
	}

}
