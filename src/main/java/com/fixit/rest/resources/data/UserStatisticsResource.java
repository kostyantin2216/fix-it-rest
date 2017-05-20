package com.fixit.rest.resources.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fixit.core.dao.sql.UserStatisticsDao;
import com.fixit.core.data.sql.UserStatistics;

@Component
public class UserStatisticsResource extends DataAccessResource<UserStatisticsDao, UserStatistics, String> {

	public final static String END_POINT = "UserStatistics";
	
	@Autowired
	public UserStatisticsResource(UserStatisticsDao dao) {
		super(dao);
	}

	@Override
	public String getEndPoint() {
		return END_POINT;
	}

}
