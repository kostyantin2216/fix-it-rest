/**
 * 
 */
package com.fixit.rest.deserialization;

import com.google.gson.ExclusionStrategy;

/**
 * @author 		Kostyantin
 * @createdAt 	2017/06/28 00:01:38 GMT+3
 */
public abstract class TypedExclusionStrategy<T> implements ExclusionStrategy {

	private final Class<T> type;
	
	public TypedExclusionStrategy(Class<T> type) {
		this.type = type;
	}
	
	@Override
	public boolean shouldSkipClass(Class<?> clazz) {
		return !type.getName().equals(clazz.getName());
	}

}
