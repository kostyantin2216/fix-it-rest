/**
 * 
 */
package com.fixit.rest.resources.services.orders.requests;

import java.util.Arrays;

import com.fixit.core.data.JobLocation;
import com.fixit.core.data.OrderType;
import com.fixit.core.data.mongo.Tradesman;
import com.fixit.core.data.sql.JobReason;
import com.fixit.rest.resources.services.ServiceError;
import com.fixit.rest.resources.services.requests.RequestData;
import com.fixit.rest.resources.services.responses.ServiceResponseHeader;

/**
 * @author 		Kostyantin
 * @createdAt 	2017/05/28 17:48:43 GMT+3
 */
public class TradesmenOrderRequestData implements RequestData {

	private OrderType orderType;
	private JobLocation jobLocation;
	private int professionId;
	private Tradesman[] tradesmen;
	private String[] tradesmenIds;
	private JobReason[] jobReasons;
	private String comment;
	
	public OrderType getOrderType() {
		return orderType;
	}

	public void setOrderType(OrderType orderType) {
		this.orderType = orderType;
	}

	public JobLocation getJobLocation() {
		return jobLocation;
	}

	public void setJobLocation(JobLocation jobLocation) {
		this.jobLocation = jobLocation;
	}

	public Tradesman[] getTradesmen() {
		return tradesmen;
	}

	public int getProfessionId() {
		return professionId;
	}

	public void setProfessionId(int professionId) {
		this.professionId = professionId;
	}

	public void setTradesmen(Tradesman[] tradesmen) {
		this.tradesmen = tradesmen;
	}
	
	public String[] getTradesmenIds() {
		return tradesmenIds;
	}

	public void setTradesmenIds(String[] tradesmenIds) {
		this.tradesmenIds = tradesmenIds;
	}

	public JobReason[] getJobReasons() {
		return jobReasons;
	}

	public void setJobReasons(JobReason[] jobReasons) {
		this.jobReasons = jobReasons;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Override
	public void validate(ServiceResponseHeader respHeader) {
		if(professionId == 0) {
			respHeader.addError(ServiceError.MISSING_DATA, "Missing professionId");
		} else if(professionId < 0) {
			respHeader.addError(ServiceError.INVALID_DATA, "Invalid professionId");
		}
		if((orderType == null || orderType != OrderType.QUICK) && 
				((tradesmen == null || tradesmen.length == 0) && (tradesmenIds == null || tradesmenIds.length == 0))) {
			respHeader.addError(ServiceError.MISSING_DATA, "Missing tradesmen");
		}
		if(jobLocation == null) {
			respHeader.addError(ServiceError.MISSING_DATA, "Missing jobLocation");
		}
	}

	@Override
	public String toString() {
		return "TradesmenOrderRequestData [jobLocation=" + jobLocation + ", tradesmen=" + Arrays.toString(tradesmen)
				+ ", reason=" + comment + "]";
	}
	
}
