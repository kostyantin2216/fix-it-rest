package com.fixit.rest.resources.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fixit.core.dao.sql.TradesmanStatisticsDao;
import com.fixit.core.data.sql.TradesmanStatistics;

@Component
public class TradesmanStatisticsResource extends DataAccessResource<TradesmanStatisticsDao, TradesmanStatistics, String> {

	public final static String END_POINT = "TradesmanStatistics";
	
	@Autowired
	public TradesmanStatisticsResource(TradesmanStatisticsDao dao) {
		super(dao);
	}

	@Override
	public String getEndPoint() {
		return END_POINT;
	}

}
