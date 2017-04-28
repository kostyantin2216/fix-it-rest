/**
 * 
 */
package com.fixit.rest.resources.services.search.responses;

import java.util.List;
import java.util.Map;

import com.fixit.core.data.mongo.Tradesman;
import com.fixit.rest.resources.services.responses.ResponseData;

/**
 * @author Kostyantin
 * @createdAt 2017/04/04 00:34:42 GMT+3
 */
public class TradesmenSearchResultResponseData implements ResponseData {

	private boolean complete;
	private List<Tradesman> tradesmen;
	private Map<String, Long> reviewCountForTradesmen;
	
	public TradesmenSearchResultResponseData() { }

	public TradesmenSearchResultResponseData(boolean complete, List<Tradesman> tradesmen,
			Map<String, Long> reviewCountForTradesmen) {
		this.complete = complete;
		this.tradesmen = tradesmen;
		this.reviewCountForTradesmen = reviewCountForTradesmen;
	}

	public boolean isComplete() {
		return complete;
	}

	public void setComplete(boolean complete) {
		this.complete = complete;
	}

	public List<Tradesman> getTradesmen() {
		return tradesmen;
	}

	public void setTradesmen(List<Tradesman> tradesmen) {
		this.tradesmen = tradesmen;
	}

	public Map<String, Long> getReviewCountForTradesmen() {
		return reviewCountForTradesmen;
	}

	public void setReviewCountForTradesmen(Map<String, Long> reviewCountForTradesmen) {
		this.reviewCountForTradesmen = reviewCountForTradesmen;
	}

	@Override
	public String toString() {
		return "SearchResultResponseData{" + "complete=" + complete + ", tradesmen=" + tradesmen
				+ ", reviewCountForTradesmen=" + reviewCountForTradesmen + '}';
	}

}
