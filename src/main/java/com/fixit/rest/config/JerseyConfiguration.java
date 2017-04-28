package com.fixit.rest.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;

public class JerseyConfiguration extends ResourceConfig {

	public JerseyConfiguration() {
		register(RequestContextFilter.class);
		
		packages("com.fixit.rest");
	}
}
