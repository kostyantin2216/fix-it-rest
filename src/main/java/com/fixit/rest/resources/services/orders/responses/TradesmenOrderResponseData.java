/**
 * 
 */
package com.fixit.rest.resources.services.orders.responses;

import com.fixit.rest.resources.services.responses.ResponseData;

/**
 * @author 		Kostyantin
 * @createdAt 	2017/05/28 17:49:21 GMT+3
 */
public class TradesmenOrderResponseData implements ResponseData {

	private boolean complete;

	public boolean isComplete() {
		return complete;
	}

	public void setComplete(boolean complete) {
		this.complete = complete;
	}

	@Override
	public String toString() {
		return "TradesmenOrderResponseData [complete=" + complete + "]";
	}
	
}
