package com.fixit.rest.resources.data;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fixit.core.dao.mongo.UserDao;
import com.fixit.core.data.mongo.User;

@Component
public class UserResource extends DataAccessResource<User, ObjectId> {
	
	public final static String END_POINT = "Users";

	@Autowired
	public UserResource(UserDao dao) {
		super(dao);
	}

	@Override
	public String getEndPoint() {
		return END_POINT;
	}

}
