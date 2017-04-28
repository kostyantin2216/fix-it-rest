/**
 * 
 */
package com.fixit.rest.resources.services.test;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.bson.types.ObjectId;

import com.fixit.core.config.CoreContextProvider;
import com.fixit.core.dao.mongo.MapAreaDao;
import com.fixit.core.data.MapAreaType;
import com.fixit.core.data.mongo.MapArea;
import com.fixit.core.data.mongo.Tradesman;
import com.fixit.core.utils.TestUtils;
import com.fixit.rest.resources.RestResource;
import com.fixit.rest.resources.services.test.responses.TradesmenCreationTestResponse;

/**
 * @author 		Kostyantin
 * @createdAt 	2017/04/15 16:44:54 GMT+3
 */
public class TestServiceResource implements RestResource {

	public final static String END_POINT = "test";
	private final static ObjectId[] WARDS_IN_JOHANESBURG;

	static {
		MapAreaDao mapAreaDao = CoreContextProvider.getMapAreaDao();
		
		ObjectId johannesburgId = new ObjectId("585e6df2dabad5205065fb22"); 
		List<MapArea> mapAreas = mapAreaDao.getAreasIn(mapAreaDao.findById(johannesburgId), MapAreaType.Ward);
		
		final int mapAreasCount = mapAreas.size();
		WARDS_IN_JOHANESBURG = new ObjectId[mapAreasCount];
		
		for(int i = 0; i < mapAreasCount; i++) {
			WARDS_IN_JOHANESBURG[i] = mapAreas.get(i).get_id();
		}
	}
			
	@GET
	@Path("createTradesmen")
	public TradesmenCreationTestResponse createTestTradesmen(@QueryParam("count") int count) {
		if(count < 1) {
			count = 1;
		} else if(count > 20) {
			count = 20;
		}
		
		
		List<Tradesman> tradesmen = new ArrayList<>();
		for(int i = 0; i < count; i++) {
			Tradesman tradesman = TestUtils.createDummyTradesman();
			tradesman.setWorkingAreas(WARDS_IN_JOHANESBURG);
			tradesmen.add(tradesman);
		}
		
		return new TradesmenCreationTestResponse(tradesmen);
	}
	
	@Override
	public String getEndPoint() {
		return END_POINT;
	}

}
