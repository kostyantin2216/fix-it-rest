package com.fixit.rest.resources.data;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fixit.core.dao.mongo.TradesmanDao;
import com.fixit.core.data.mongo.Tradesman;

@Component
public class TradesmanResource extends DataAccessResource<Tradesman, ObjectId> {

	public final static String END_POINT = "Tradesmen";
	
	@Autowired
	public TradesmanResource(TradesmanDao dao) {
		super(dao);
	}

	@Override
	public String getEndPoint() {
		return END_POINT;
	}

}
