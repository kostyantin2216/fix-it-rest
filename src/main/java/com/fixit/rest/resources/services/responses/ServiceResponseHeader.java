package com.fixit.rest.resources.services.responses;

import java.util.ArrayList;
import java.util.List;

import com.fixit.rest.resources.services.ServiceError;

/**
 * Created by Kostyantin on 3/20/2017.
 * 
 * Lazy load errors since there shouldn't be any.
 */
public class ServiceResponseHeader {

    private List<Error> errors;

    public ServiceResponseHeader() { }

    public List<Error> getErrors() {
        return errors;
    }

    public void setErrors(List<Error> errors) {
        this.errors = errors;
    }
    
    public void addError(ServiceError error, String description) {
    	if(errors == null) {
    		errors = new ArrayList<>();
    	}
    	this.errors.add(new Error(error.code, description));
    }
    
    public boolean hasErrors() {
    	return errors != null ? !errors.isEmpty() : false;
    }

    @Override
    public String toString() {
        return "APIResponseHeader{" +
                "errors=" + errors +
                '}';
    }
    
    public static class Error {
    	public final int code;
        public final String description;

        private Error(int code, String description) {
            this.code = code;
            this.description = description;
        }

        @Override
        public String toString() {
            return "APIError{" +
                    "code=" + code +
                    ", description='" + description + '\'' +
                    '}';
        }
    }
}
