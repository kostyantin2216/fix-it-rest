/**
 * 
 */
package com.fixit.rest.resources.services.requests;

import com.fixit.rest.resources.services.responses.ServiceResponseHeader;

/**
 * @author 		Kostyantin
 * @createdAt 	2017/03/26 21:35:36 GMT+3
 */
public interface RequestData {
	void validate(ServiceResponseHeader respHeader);
}
