/**
 * 
 */
package com.fixit.rest.deserialization;

import com.fixit.core.data.mongo.Tradesman;
import com.google.gson.FieldAttributes;

/**
 * @author 		Kostyantin
 * @createdAt 	2017/06/27 23:59:53 GMT+3
 */
public class TradesmanDeserializationExclusionStrategy extends TypedExclusionStrategy<Tradesman> {
	
	public TradesmanDeserializationExclusionStrategy() {
		super(Tradesman.class);
	}

	@Override
	public boolean shouldSkipField(FieldAttributes f) {
		// TODO Auto-generated method stub
		return false;
	}

}
