/**
 * 
 */
package com.fixit.rest.resources.services.synchronization.responses;

import java.util.List;

import com.fixit.components.synchronization.SynchronizationResult;
import com.fixit.rest.resources.services.responses.ResponseData;

/**
 * @author 		Kostyantin
 * @createdAt 	2017/03/29 21:58:08 GMT+3
 */
@SuppressWarnings("rawtypes")
public class SynchronizationResponseData implements ResponseData {

	private List<SynchronizationResult> synchronizationResults;

    public SynchronizationResponseData() { }

    public SynchronizationResponseData(List<SynchronizationResult> synchronizationResults) {
        this.synchronizationResults = synchronizationResults;
    }

    public List<SynchronizationResult> getSynchronizationResults() {
        return synchronizationResults;
    }

    public void setSynchronizationResults(List<SynchronizationResult> synchronizationResults) {
        this.synchronizationResults = synchronizationResults;
    }

    @Override
    public String toString() {
        return "SynchronizationResponseData{" +
                "synchronizationResults=" + synchronizationResults +
                '}';
    }
}
