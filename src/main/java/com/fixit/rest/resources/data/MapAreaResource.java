package com.fixit.rest.resources.data;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fixit.core.dao.mongo.MapAreaDao;
import com.fixit.core.data.mongo.MapArea;

@Component
public class MapAreaResource extends DataAccessResource<MapArea, ObjectId> {

	public final static String END_POINT = "MapAreas";
	
	@Autowired
	public MapAreaResource(MapAreaDao dao) {
		super(dao);
	}

	@Override
	public String getEndPoint() {
		return END_POINT;
	}

}
