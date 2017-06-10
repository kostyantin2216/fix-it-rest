package com.fixit.rest.resources.data;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fixit.core.dao.mongo.OrderDao;
import com.fixit.core.data.mongo.Order;

@Component
public class OrderResource extends DataAccessResource<OrderDao, Order, ObjectId> {

	public final static String END_POINT = "Orders";
	
	@Autowired
	public OrderResource(OrderDao dao) {
		super(dao);
	}

	@Override
	public String getEndPoint() {
		return END_POINT;
	}

}
