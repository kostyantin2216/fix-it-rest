/**
 * 
 */
package com.fixit.rest.resources.data;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fixit.core.dao.mongo.UserBookmarkDao;
import com.fixit.core.data.mongo.UserBookmark;

/**
 * @author 		Kostyantin
 * @createdAt 	2016/12/16 20:35:38 GMT+2
 */
@Component
public class UserBookmarkResource extends DataAccessResource<UserBookmark, ObjectId> {

	public final static String END_POINT = "UserBookmarks";
	
	@Autowired
	public UserBookmarkResource(UserBookmarkDao dao) {
		super(dao);
	}

	@Override
	public String getEndPoint() {
		return END_POINT;
	}

}
