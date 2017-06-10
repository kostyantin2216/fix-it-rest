/**
 * 
 */
package com.fixit.rest.resources.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fixit.core.dao.sql.JobReasonDao;
import com.fixit.core.data.sql.JobReason;

/**
 * @author 		Kostyantin
 * @createdAt 	2017/06/01 17:27:56 GMT+3
 */
@Component
public class JobReasonResource extends DataAccessResource<JobReasonDao, JobReason, Integer> {
	
	public final static String END_POINT = "JobReasons";

	@Autowired
	public JobReasonResource(JobReasonDao jobReasonDao) {
		super(jobReasonDao);
	}

	@Override
	public String getEndPoint() {
		return END_POINT;
	}

}
