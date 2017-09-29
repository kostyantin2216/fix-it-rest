/**
 * 
 */
package com.fixit.rest.resources.services.data.responses;

import java.util.Arrays;

import com.fixit.core.data.mongo.Tradesman;
import com.fixit.rest.resources.services.responses.ResponseData;

/**
 * @author 		Kostyantin
 * @createdAt 	2017/08/26 16:26:31 GMT+3
 */
public class TradesmenResponseData implements ResponseData {

	private Tradesman[] tradesmen;
	
	public TradesmenResponseData() { }

	public TradesmenResponseData(Tradesman[] tradesmen) {
		this.tradesmen = tradesmen;
	}

	public Tradesman[] getTradesmen() {
		return tradesmen;
	}

	public void setTradesmen(Tradesman[] tradesmen) {
		this.tradesmen = tradesmen;
	}

	@Override
	public String toString() {
		return "TradesmenResponseData [tradesmen=" + Arrays.toString(tradesmen) + "]";
	}
	
}
