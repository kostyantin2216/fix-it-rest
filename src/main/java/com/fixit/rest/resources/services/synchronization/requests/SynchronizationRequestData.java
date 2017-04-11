/**
 * 
 */
package com.fixit.rest.resources.services.synchronization.requests;

import java.util.Date;
import java.util.Map;
import java.util.Set;

import com.fixit.core.synchronization.SynchronizationAction;
import com.fixit.rest.resources.services.ServiceError;
import com.fixit.rest.resources.services.requests.RequestData;
import com.fixit.rest.resources.services.responses.ServiceResponseHeader;

/**
 * @author 		Kostyantin
 * @createdAt 	2017/03/29 21:48:11 GMT+3
 */
public class SynchronizationRequestData implements RequestData {

	private Date firstSynchronization;
    private Map<String, Set<SynchronizationAction>> synchronizationHistory;

    public SynchronizationRequestData() { }

    public SynchronizationRequestData(Date firstSynchronization, Map<String, Set<SynchronizationAction>> synchronizationHistory) {
        this.firstSynchronization = firstSynchronization;
        this.synchronizationHistory = synchronizationHistory;
    }

    public Date getFirstSynchronization() {
        return firstSynchronization;
    }

    public void setFirstSynchronization(Date firstSynchronization) {
        this.firstSynchronization = firstSynchronization;
    }

    public Map<String, Set<SynchronizationAction>> getSynchronizationHistory() {
        return synchronizationHistory;
    }

    public void setSynchronizationHistory(Map<String, Set<SynchronizationAction>> synchronizationHistory) {
        this.synchronizationHistory = synchronizationHistory;
    }
    
    public void validate(ServiceResponseHeader respHeader) {
    	if(firstSynchronization == null) {
    		respHeader.addError(ServiceError.MISSING_DATA, "Cannot synchronize without firstSynchronization");
    	}
    	if(synchronizationHistory == null || synchronizationHistory.isEmpty()) {
    		respHeader.addError(ServiceError.MISSING_DATA, "Cannot synchronize wihtout synchronizationHistory");
    	} else {
    		for(Map.Entry<String, Set<SynchronizationAction>> historyEntry : synchronizationHistory.entrySet()) {
    			if(historyEntry.getKey() == null) {
    				respHeader.addError(ServiceError.MISSING_DATA, "Cannot synchronize without table name");
    			}
    			if(historyEntry.getValue() == null) {
    				respHeader.addError(ServiceError.MISSING_DATA, "Cannot synchroniz with null action history");
    			}
    		}
    	}
    }

	@Override
	public String toString() {
		return "AppSynchronizationRequestData [firstSynchronization=" + firstSynchronization
				+ ", synchronizationHistory=" + synchronizationHistory + "]";
	}
	
}
