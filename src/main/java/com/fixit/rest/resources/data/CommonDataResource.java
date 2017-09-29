package com.fixit.rest.resources.data;

import java.io.Serializable;

import javax.ws.rs.core.Response;

import com.fixit.core.dao.queries.DataResourceQuery;
import com.fixit.core.data.DataModelObject;
import com.fixit.rest.resources.RestResource;

public interface CommonDataResource<E extends DataModelObject<ID>, ID extends Serializable> extends RestResource {
	Response create(E entity);
	Response update(E entity);
	Response delete(String id);
	Response get(String id);
	Response get();
	Response query(DataResourceQuery[] queries);
}
