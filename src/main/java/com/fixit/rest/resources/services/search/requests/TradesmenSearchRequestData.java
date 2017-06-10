/**
 * 
 */
package com.fixit.rest.resources.services.search.requests;

import com.fixit.core.data.MutableLatLng;
import com.fixit.rest.resources.services.ServiceError;
import com.fixit.rest.resources.services.requests.RequestData;
import com.fixit.rest.resources.services.responses.ServiceResponseHeader;

/**
 * @author 		Kostyantin
 * @createdAt 	2017/04/04 00:15:52 GMT+3
 */
public class TradesmenSearchRequestData implements RequestData {

	private Integer professionId;
    private MutableLatLng location;

    public TradesmenSearchRequestData() { }

    public TradesmenSearchRequestData(Integer professionId, MutableLatLng location) {
        this.professionId = professionId;
        this.location = location;
    }

    public Integer getProfessionId() {
        return professionId;
    }

    public void setProfessionId(Integer professionId) {
        this.professionId = professionId;
    }

    public MutableLatLng getLocation() {
        return location;
    }

    public void setLocation(MutableLatLng location) {
        this.location = location;
    }
    
    @Override
    public void validate(ServiceResponseHeader respHeader) {
    	if(professionId == null) {
    		respHeader.addError(ServiceError.MISSING_DATA, "cannot search without a professionId");
    	} else if(professionId < 0) {
    		respHeader.addError(ServiceError.INVALID_DATA, "professionId must be a positive number");
    	}
    	
    	if(location == null) {
    		respHeader.addError(ServiceError.MISSING_DATA, "must include a search location");
    	}
    }

    @Override
    public String toString() {
        return "SearchRequestData{" +
                "professionId=" + professionId +
                ", location='" + location + '\'' +
                '}';
    } 
	
}
