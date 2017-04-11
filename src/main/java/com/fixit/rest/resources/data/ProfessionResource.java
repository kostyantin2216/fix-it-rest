package com.fixit.rest.resources.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fixit.core.dao.sql.ProfessionDao;
import com.fixit.core.data.sql.Profession;

@Component
public class ProfessionResource extends DataAccessResource<Profession, Integer> {

	public final static String END_POINT = "Professions";
	
	@Autowired
	public ProfessionResource(ProfessionDao dao) {
		super(dao);
	}

	@Override
	public String getEndPoint() {
		return END_POINT;
	}

}
