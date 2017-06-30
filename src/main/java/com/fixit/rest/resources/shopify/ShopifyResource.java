/**
 * 
 */
package com.fixit.rest.resources.shopify;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fixit.components.registration.tradesmen.TradesmanRegistrant;
import com.fixit.core.data.shopify.ShopifyCustomer;
import com.fixit.core.data.shopify.ShopifyOrder;
import com.fixit.core.logging.FILog;
import com.fixit.rest.resources.RestResource;

/**
 * @author 		Kostyantin
 * @createdAt 	2017/04/22 12:48:03 GMT+3
 * 
 * Shopify web hooks, make sure not respond with an error otherwise shopify
 * will stop notifying the web hooks.
 * 
 */
@Component
public class ShopifyResource implements RestResource {
	
	private final static String LOG_TAG = ShopifyResource.class.getName();

	public final static String END_POINT = "shopify";
	
	@Autowired
	private TradesmanRegistrant tradesmanRegistrant;

	@POST
	@Path("onOrderCreated")
	public Response onOrderCreated(ShopifyOrder order) {
		try {
			tradesmanRegistrant.newRegistration(order.getCustomer());
		} catch(Exception e) {
			FILog.e(LOG_TAG, "Error while creating new tradesman account.", e);
		}
		
		return Response.ok().build();
	}
	
	@POST
	@Path("onCustomerCreated")
	public Response onCustomerCreated(ShopifyCustomer customer) {
		try {
			tradesmanRegistrant.newRegistration(customer);
		} catch(Exception e) {
			FILog.e(LOG_TAG, "Error while creating new tradesman account.", e);
		}
		
		return Response.ok().build();
	}
	
	@Override
	public String getEndPoint() {
		return END_POINT;
	}
	
}
