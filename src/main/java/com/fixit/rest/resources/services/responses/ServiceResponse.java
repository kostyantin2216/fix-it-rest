package com.fixit.rest.resources.services.responses;

/**
 * Created by Kostyantin on 3/20/2017.
 */

public class ServiceResponse<RD extends ResponseData> {

    private ServiceResponseHeader header;
    private RD data;

    public ServiceResponse() { }
    
    public ServiceResponse(ServiceResponseHeader header, RD data) {
		this.header = header;
		this.data = data;
	}

	public ServiceResponseHeader getHeader() {
        return header;
    }

    public void setHeader(ServiceResponseHeader header) {
        this.header = header;
    }

    public RD getData() {
        return data;
    }

    public void setData(RD responseData) {
        this.data = responseData;
    }

    @Override
    public String toString() {
        return "APIResponse{" +
                "header=" + header +
                ", data=" + data +
                '}';
    }
}
