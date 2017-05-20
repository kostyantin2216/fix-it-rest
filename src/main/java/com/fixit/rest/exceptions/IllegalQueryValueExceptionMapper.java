/**
 * 
 */
package com.fixit.rest.exceptions;

import javax.ws.rs.ext.Provider;

import com.fixit.core.exceptions.IllegalQueryValueException;

/**
 * @author 		Kostyantin
 * @createdAt 	2017/05/20 17:23:03 GMT+3
 */
@Provider
public class IllegalQueryValueExceptionMapper extends ClientExceptionMapper<IllegalQueryValueException> {
	
}
