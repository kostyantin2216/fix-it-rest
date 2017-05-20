/**
 * 
 */
package com.fixit.rest.exceptions;

import javax.ws.rs.ext.Provider;

import com.fixit.core.exceptions.IllegalQueryOperandException;

/**
 * @author 		Kostyantin
 * @createdAt 	2017/05/20 17:23:03 GMT+3
 */
@Provider
public class IllegalQueryOperandExceptionMapper extends ClientExceptionMapper<IllegalQueryOperandException> {
	
}
