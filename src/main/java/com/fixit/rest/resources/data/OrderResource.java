package com.fixit.rest.resources.data;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fixit.core.dao.mongo.OrderDataDao;
import com.fixit.core.data.mongo.OrderData;

@Component
public class OrderResource extends DataAccessResource<OrderDataDao, OrderData, ObjectId> {

	public final static String END_POINT = "Orders";
	
	@Autowired
	public OrderResource(OrderDataDao orderDataDao) {
		super(orderDataDao);
	}

	@Override
	public String getEndPoint() {
		return END_POINT;
	}

}
