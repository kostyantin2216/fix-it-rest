/**
 * 
 */
package com.fixit.rest.resources.services.orders.responses;

import com.fixit.core.data.mongo.OrderData;
import com.fixit.rest.resources.services.responses.ResponseData;

/**
 * @author 		Kostyantin
 * @createdAt 	2017/05/28 17:49:21 GMT+3
 */
public class TradesmenOrderResponseData implements ResponseData {

	private OrderData orderData;
	
	public OrderData getOrderData() {
		return orderData;
	}

	public void setOrderData(OrderData orderData) {
		this.orderData = orderData;
	}

	@Override
	public String toString() {
		return "TradesmenOrderResponseData [orderData=" + orderData + "]";
	}
	
}
