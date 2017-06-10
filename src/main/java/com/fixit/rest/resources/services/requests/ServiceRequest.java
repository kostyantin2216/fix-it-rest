package com.fixit.rest.resources.services.requests;

import com.fixit.rest.resources.services.responses.ServiceResponseHeader;

/**
 * Created by Kostyantin on 3/20/2017.
 */
public class ServiceRequest<RD extends RequestData> {

    private ServiceRequestHeader header;
    private RD data;

    public ServiceRequest() { }

    public ServiceRequestHeader getHeader() {
        return header;
    }

    public void setHeader(ServiceRequestHeader header) {
        this.header = header;
    }

	public RD getData() {
		return data;
	}

	public void setData(RD data) {
		this.data = data;
	}
	
	public void validate(ServiceResponseHeader respHeader) {
		data.validate(respHeader);
	}

	@Override
	public String toString() {
		return "AppServiceRequest [header=" + header + ", data=" + data + "]";
	}
	
}
