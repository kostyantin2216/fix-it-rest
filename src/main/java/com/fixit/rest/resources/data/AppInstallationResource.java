/**
 * 
 */
package com.fixit.rest.resources.data;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fixit.core.dao.mongo.AppInstallationDao;
import com.fixit.core.data.mongo.AppInstallation;

/**
 * @author 		Kostyantin
 * @createdAt 	2016/12/24 22:46:18 GMT+2
 */
@Component
public class AppInstallationResource extends DataAccessResource<AppInstallation, ObjectId> {

	public final static String END_POINT = "AppInstallations";
	
	@Autowired
	public AppInstallationResource(AppInstallationDao dao) {
		super(dao);
	}

	@Override
	public String getEndPoint() {
		return END_POINT;
	}

}
