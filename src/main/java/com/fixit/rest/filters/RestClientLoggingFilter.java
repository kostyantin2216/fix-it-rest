/**
 * 
 */
package com.fixit.rest.filters;

import javax.servlet.filter.logging.LoggingFilter;
import javax.servlet.filter.logging.wrapper.LoggingHttpServletRequestWrapper;
import javax.servlet.filter.logging.wrapper.LoggingHttpServletResponseWrapper;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fixit.core.logging.FILog;

/**
 * @author 		Kostyantin
 * @createdAt 	2017/05/23 23:29:18 GMT+3
 */
@Component
public class RestClientLoggingFilter extends LoggingFilter {
	
	private final static String LOG_TAG = RestClientLoggingFilter.class.getName();
	
	@Override
	protected boolean isDebugLogEnabled() {
		return false;
	}
	
	@Override
	public void log(String logMsg) {
		FILog.i(logMsg);
	}
	
	@Override
	protected String getRequestDescription(LoggingHttpServletRequestWrapper rw) {
		String desc = rw.getMethod() + " " + rw.getRequestURI() + " from " + rw.getLocalAddr() + "\n"; 
		try {
			if(rw.isFormPost()) {
				desc += "Form params: " + OBJECT_MAPPER.writeValueAsString(rw.getParameters()) + "\n";
			}
			desc += "Body: " + rw.getContent();
		} catch (JsonProcessingException e) {
			FILog.e(LOG_TAG, "Could not log body for rest client request ", e);
		}
		return desc;
	}
	
}
