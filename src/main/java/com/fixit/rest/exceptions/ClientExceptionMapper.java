/**
 * 
 */
package com.fixit.rest.exceptions;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

/**
 * @author 		Kostyantin
 * @createdAt 	2017/05/20 17:26:11 GMT+3
 */
public abstract class ClientExceptionMapper<T extends Throwable> implements ExceptionMapper<T> {

	@Override
	public Response toResponse(T throwable) {
		return Response.status(Status.BAD_REQUEST)
				.entity(throwable.getMessage())
				.type(MediaType.TEXT_PLAIN)
				.build();
	}
	
}
