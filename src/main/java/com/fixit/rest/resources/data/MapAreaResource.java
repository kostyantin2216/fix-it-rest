package com.fixit.rest.resources.data;

import java.util.Collections;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fixit.core.dao.mongo.MapAreaDao;
import com.fixit.core.data.MapAreaType;
import com.fixit.core.data.mongo.MapArea;
import com.fixit.core.utils.Formatter;

@Component
public class MapAreaResource extends DataAccessResource<MapAreaDao, MapArea, ObjectId> {

	public final static String END_POINT = "MapAreas";

	@GET
	@Path("ofType/{type}")
	public List<MapArea> getAreasForType(@PathParam("type") String type) {
		MapAreaType enumType = MapAreaType.valueOf(Formatter.capitalizeSentence(type));
		
		if(enumType == null) {
			throw new IllegalArgumentException("Invalid path param: type");
		}
		
		return mDao.getAreasForType(enumType);
	}
	
	@GET
	@Path("parentsForType/{type}")
	public List<MapArea> getParentAreasForType(@PathParam("type") String type) {
		MapAreaType enumType = MapAreaType.valueOf(Formatter.capitalizeSentence(type));
		
		if(enumType == null) {
			throw new IllegalArgumentException("Invalid path param: type");
		}
		
		if(enumType.level == 0) {
			return Collections.emptyList();
		}
		
		MapAreaType parentType = MapAreaType.findByLevel(enumType.level - 1);
		return mDao.getAreasForType(parentType);
	}
	
	@GET
	@Path("childrenOf/{parentId}")
	public List<MapArea> getChildren(@PathParam("parentId") String parentId) {
		return mDao.getChildren(new ObjectId(parentId));
	}
	
	@Autowired
	public MapAreaResource(MapAreaDao dao) {
		super(dao);
	}

	@Override
	public String getEndPoint() {
		return END_POINT;
	}

}
