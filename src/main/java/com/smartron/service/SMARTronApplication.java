package com.smartron.service;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

public class SMARTronApplication extends ResourceConfig {
	public SMARTronApplication() {
		register(JacksonFeature.class);
	}
}