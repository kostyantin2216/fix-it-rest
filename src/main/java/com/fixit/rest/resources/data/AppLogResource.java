/**
 * 
 */
package com.fixit.rest.resources.data;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fixit.core.dao.mongo.AppLogDao;
import com.fixit.core.data.mongo.AppLog;

/**
 * @author 		Kostyantin
 * @createdAt 	2016/12/24 16:59:48 GMT+2
 */
@Component
public class AppLogResource extends DataAccessResource<AppLog, ObjectId> {

	public final static String END_POINT = "AppLogs";
	
	@Autowired
	public AppLogResource(AppLogDao dao) {
		super(dao);
	}

	@Override
	public String getEndPoint() {
		return END_POINT;
	}

}
