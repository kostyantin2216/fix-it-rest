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

import com.fixit.core.dao.CommonDao;
import com.fixit.core.data.DataModelObject;

public abstract class DataAccessResource<E extends DataModelObject<ID>, ID extends Serializable> implements CommonDataResource<E, ID> {

	private final CommonDao<E, ID> mDao;
	
	public DataAccessResource(CommonDao<E, ID> dao) {
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
	public Response delete(ID id) {
		mDao.delete(id);
		return Response.ok().build();
	}

	@GET
	@Path("{id}")
	@Override
	public Response get(@PathParam("id") ID id) {
		E entity = mDao.findById(id);
		return Response.ok(entity != null ? entity : "no results for id: " + id).build();
	}

	@GET
	@Override
	public Response get() {
		List<E> mapAreas = mDao.findAll();
		return Response.ok(mapAreas).build();
	}

}
