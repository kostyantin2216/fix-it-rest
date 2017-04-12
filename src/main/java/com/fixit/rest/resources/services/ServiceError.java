/**
 * 
 */
package com.fixit.rest.resources.services;

/**
 * @author 		Kostyantin
 * @createdAt 	2017/04/11 18:53:22 GMT+3
 */
public enum ServiceError {
	
	MISSING_DATA(1),
	INVALID_DATA(2),
	UNSUPPORTED(3),
	UNKNOWN(4);
	
	public final int code;
	
	ServiceError(int code) {
		this.code = code;
	}
}
