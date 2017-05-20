/**
 * 
 */
package com.fixit.rest.resources.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fixit.core.dao.sql.StoredPropertyDao;
import com.fixit.core.data.sql.StoredProperty;
import com.fixit.core.data.sql.StoredProperty.StoredPropertyPK;

/**
 * @author 		Kostyantin
 * @createdAt 	2016/12/21 22:36:18 GMT+2
 */
@Component
public class StoredPropertyResource extends DataAccessResource<StoredPropertyDao, StoredProperty, StoredPropertyPK> {

	public final static String END_POINT = "StoredProperties";
	
	@Autowired
	public StoredPropertyResource(StoredPropertyDao dao) {
		super(dao);
	}

	@Override
	public String getEndPoint() {
		return END_POINT;
	}

}
