/**
 * 
 */
package com.fixit.rest.resources.data;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fixit.core.dao.mongo.SearchTagDao;
import com.fixit.core.data.mongo.SearchTag;

/**
 * @author 		Kostyantin
 * @createdAt 	2016/12/16 20:37:15 GMT+2
 */
@Component
public class SearchTagResource extends DataAccessResource<SearchTagDao, SearchTag, ObjectId> {

	public final static String END_POINT = "SearchTags";
	
	@Autowired
	public SearchTagResource(SearchTagDao dao) {
		super(dao);
	}

	@Override
	public String getEndPoint() {
		return END_POINT;
	}

}
