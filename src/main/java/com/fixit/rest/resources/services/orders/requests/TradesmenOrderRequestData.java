/**
 * 
 */
package com.fixit.rest.resources.services.orders.requests;

import java.util.Arrays;

import com.fixit.core.data.JobLocation;
import com.fixit.core.data.mongo.Tradesman;
import com.fixit.rest.resources.services.ServiceError;
import com.fixit.rest.resources.services.requests.RequestData;
import com.fixit.rest.resources.services.responses.ServiceResponseHeader;

/**
 * @author 		Kostyantin
 * @createdAt 	2017/05/28 17:48:43 GMT+3
 */
public class TradesmenOrderRequestData implements RequestData {

	private JobLocation jobLocation;
	private Tradesman[] tradesmen;
	private String reason;
	
	public JobLocation getJobLocation() {
		return jobLocation;
	}

	public void setJobLocation(JobLocation jobLocation) {
		this.jobLocation = jobLocation;
	}

	public Tradesman[] getTradesmen() {
		return tradesmen;
	}

	public void setTradesmen(Tradesman[] tradesmen) {
		this.tradesmen = tradesmen;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	@Override
	public void validate(ServiceResponseHeader respHeader) {
		if(tradesmen == null || tradesmen.length == 0) {
			respHeader.addError(ServiceError.MISSING_DATA, "Cannot order tradesmen without any tradesmen");
		}
		if(jobLocation == null) {
			respHeader.addError(ServiceError.MISSING_DATA, "Cannot order tradesmen without a location");
		}
	}

	@Override
	public String toString() {
		return "TradesmenOrderRequestData [jobLocation=" + jobLocation + ", tradesmen=" + Arrays.toString(tradesmen)
				+ ", reason=" + reason + "]";
	}
	
}
