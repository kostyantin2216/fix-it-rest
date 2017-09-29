package com.fixit.rest.resources.data;

import java.io.Serializable;
import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.fixit.core.dao.CommonDao;
import com.fixit.core.dao.queries.DataResourceQuery;
import com.fixit.core.data.DataModelObject;

public abstract class DataAccessResource<DAO extends CommonDao<E, ID>, E extends DataModelObject<ID>, ID extends Serializable> 
	implements CommonDataResource<E, ID> {

	protected final DAO mDao;
	
	public DataAccessResource(DAO dao) {
		mDao = dao;
	}
	
	@POST
	@Override
	public Response create(E entity) {
		mDao.save(entity);
		return Response.ok(entity).build();
	}

	@PUT
	@Override
	public Response update(E entity) {
		mDao.update(entity);
		return Response.ok().build();
	}

	@DELETE
	@Path("{id}")
	@Override
	public Response delete(@PathParam("id") String id) {
		//mDao.delete(id);
		return Response.status(Status.NOT_IMPLEMENTED).build();
	}

	@GET
	@Path("{id}")
	@Override
	public Response get(@PathParam("id") String id) {
	//	E entity = mDao.findById(id);
	//	return Response.ok(entity != null ? entity : "no results for id: " + id).build();
		return Response.status(Status.NOT_IMPLEMENTED).build();
	}

	@GET
	@Override
	public Response get() {
		List<E> entities = mDao.findAll();
		return Response.ok(entities).build();
	}
	
	@POST
	@Path("query")
	@Override
	public Response query(DataResourceQuery[] queries) {
		List<E> entities = mDao.processQueries(queries);
		return Response.ok(entities).build();
	}

}
