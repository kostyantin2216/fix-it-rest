package com.fixit.rest.resources.data;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fixit.components.events.ServerEventController;
import com.fixit.core.dao.sql.ReviewDao;
import com.fixit.core.data.sql.Review;
import com.fixit.core.data.sql.Review.ReviewPK;

@Component
public class ReviewResource extends DataAccessResource<ReviewDao, Review, ReviewPK> {

	public final static String END_POINT = "Reviews";
	
	private final ServerEventController mEventController;
	
	@Autowired
	public ReviewResource(ServerEventController controller, ReviewDao dao) {
		super(dao);
		mEventController = controller;
	}

	@Override
	public String getEndPoint() {
		return END_POINT;
	}

	@Override
	public Response create(Review entity) {
		Response response = super.create(entity);
		if(response.getStatusInfo() == Status.OK) {
			mEventController.newReview(entity);
		}
		return response;
	}
	
	@Override
	public Response update(Review entity) {
		Response response = super.create(entity);
		if(response.getStatusInfo() == Status.OK) {
			mEventController.reviewUpdated(entity);
		}
		return response;
	}

}
