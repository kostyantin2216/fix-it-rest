/**
 * 
 */
package com.fixit.rest.exceptions;

import javax.ws.rs.ext.Provider;

import com.fixit.core.exceptions.IllegalQueryPropertyException;

/**
 * @author 		Kostyantin
 * @createdAt 	2017/05/20 17:40:56 GMT+3
 */
@Provider
public class IllegalQueryPropertyExceptionMapper extends ClientExceptionMapper<IllegalQueryPropertyException> {

}
