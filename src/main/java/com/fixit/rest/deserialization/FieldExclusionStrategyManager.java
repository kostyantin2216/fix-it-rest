/**
 * 
 */
package com.fixit.rest.deserialization;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

/**
 * @author 		Kostyantin
 * @createdAt 	2017/06/28 00:07:53 GMT+3
 */
public class FieldExclusionStrategyManager implements ExclusionStrategy {

	private final Map<Class<?>, Set<String>> fieldsToExcludeForClasses;
	
	private FieldExclusionStrategyManager(Builder builder) {
		this.fieldsToExcludeForClasses = builder.fieldsToExclude;
	}
	
	public FieldExclusionStrategyManager(Map<Class<?>, Set<String>> fieldsToExcludeForClasses) {
		this.fieldsToExcludeForClasses = fieldsToExcludeForClasses;
	}
	
	public boolean shouldSkipClass(Class<?> clazz) {
		return false;
	}
	
	@Override
	public boolean shouldSkipField(FieldAttributes f) {
		Set<String> fieldsToExclude = fieldsToExcludeForClasses.get(f.getDeclaringClass());
		if(fieldsToExclude != null) {
			return fieldsToExclude.contains(f.getName());
		}
		return false;
	}
	
	public static class Builder {
		Map<Class<?>, Set<String>> fieldsToExclude = new HashMap<>();
		
		public Builder add(Class<?> type, String... fieldsToExclude) {
			Set<String> fieldsToExcludeSet = new HashSet<>();
			Collections.addAll(fieldsToExcludeSet, fieldsToExclude);
			this.fieldsToExclude.put(type, fieldsToExcludeSet);
			return this;
		}
		
		public FieldExclusionStrategyManager build() {
			return new FieldExclusionStrategyManager(this);
		}
	}
	
}
