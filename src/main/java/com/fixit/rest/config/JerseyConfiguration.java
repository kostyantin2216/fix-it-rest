package com.fixit.rest.config;

import org.glassfish.jersey.server.ResourceConfig;

public class JerseyConfiguration extends ResourceConfig {

	public JerseyConfiguration() {
		packages("com.fixit.rest");
	}
}
