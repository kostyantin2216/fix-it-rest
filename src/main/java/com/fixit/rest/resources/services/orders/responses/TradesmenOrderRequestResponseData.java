/**
 * 
 */
package com.fixit.rest.resources.services.orders.responses;

import com.fixit.core.data.mongo.OrderRequest;
import com.fixit.rest.resources.services.responses.ResponseData;

/**
 * @author 		Kostyantin
 * @createdAt 	2017/05/28 17:49:21 GMT+3
 */
public class TradesmenOrderRequestResponseData implements ResponseData {

	private OrderRequest orderRequest;

	public OrderRequest getOrderRequest() {
		return orderRequest;
	}

	public void setOrderRequest(OrderRequest orderRequest) {
		this.orderRequest = orderRequest;
	}

	@Override
	public String toString() {
		return "TradesmenOrderRequestResponseData [orderRequest=" + orderRequest + "]";
	}
	
}
