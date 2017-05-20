package com.fixit.rest.resources.data;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fixit.core.dao.mongo.JobDao;
import com.fixit.core.data.mongo.Job;

@Component
public class JobResource extends DataAccessResource<JobDao, Job, ObjectId> {

	public final static String END_POINT = "Jobs";
	
	@Autowired
	public JobResource(JobDao dao) {
		super(dao);
	}

	@Override
	public String getEndPoint() {
		return END_POINT;
	}

}
