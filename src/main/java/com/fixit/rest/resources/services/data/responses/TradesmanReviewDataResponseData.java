/**
 * 
 */
package com.fixit.rest.resources.services.data.responses;

import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;

import com.fixit.core.data.sql.Review;
import com.fixit.rest.resources.services.responses.ResponseData;

/**
 * @author 		Kostyantin
 * @createdAt 	2017/05/20 18:34:57 GMT+3
 */
public class TradesmanReviewDataResponseData implements ResponseData {

	private Map<ObjectId, Map<String, String>> reviewerDataMappings;
    private List<Review> reviews;
    
    public TradesmanReviewDataResponseData() { }
    
	public TradesmanReviewDataResponseData(Map<ObjectId, Map<String, String>> reviewerDataMappings,
			List<Review> reviews) {
		this.reviewerDataMappings = reviewerDataMappings;
		this.reviews = reviews;
	}

	public Map<ObjectId, Map<String, String>> getReviewerDataMappings() {
		return reviewerDataMappings;
	}
	
	public void setReviewerDataMappings(Map<ObjectId, Map<String, String>> reviewerDataMappings) {
		this.reviewerDataMappings = reviewerDataMappings;
	}
	
	public List<Review> getReviews() {
		return reviews;
	}
	
	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}
	
	@Override
	public String toString() {
		return "TradesmanReviewDataResponseData [reviewerDataMappings=" + reviewerDataMappings + ", reviews=" + reviews
				+ "]";
	}
    
}
