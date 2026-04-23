package com.smartcampus.config;

import org.glassfish.jersey.server.ResourceConfig;

public class ApiApplication extends ResourceConfig {
    public ApiApplication() {
        // Register resources and providers
        packages("com.smartcampus.resource", "com.smartcampus.exception", "com.smartcampus.filter");
    }
}