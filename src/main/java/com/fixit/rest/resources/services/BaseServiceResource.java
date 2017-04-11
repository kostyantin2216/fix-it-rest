/**
 * 
 */
package com.fixit.rest.resources.services;

import java.util.ArrayList;

import com.fixit.rest.resources.RestResource;
import com.fixit.rest.resources.services.responses.ServiceResponseHeader;

/**
 * @author 		Kostyantin
 * @createdAt 	2017/04/11 18:57:14 GMT+3
 */
public abstract class BaseServiceResource implements RestResource {

	protected ServiceResponseHeader createHeader() {
		ServiceResponseHeader header = new ServiceResponseHeader();
		header.setErrors(new ArrayList<>());
		return header;
	}
	
}
