/**
 * 
 */
package com.fixit.rest.resources.services.test.responses;

import java.util.List;

import com.fixit.core.data.mongo.Tradesman;

/**
 * @author 		Kostyantin
 * @createdAt 	2017/04/15 16:48:06 GMT+3
 */
public class TradesmenCreationTestResponse {
	private List<Tradesman> tradesmen;
	
	public TradesmenCreationTestResponse(List<Tradesman> tradesmen) {
		this.tradesmen = tradesmen;
	}

	public List<Tradesman> getTradesmen() {
		return tradesmen;
	}

	public void setTradesmen(List<Tradesman> tradesmen) {
		this.tradesmen = tradesmen;
	}

	@Override
	public String toString() {
		return "TradesmenCreationTestResponse [tradesmen=" + tradesmen + "]";
	}
}
