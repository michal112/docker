package com.app.integration.service.config;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CategoryRouteConfig {

    @ConfigProperty(name = "category.host")
    String host;

    @ConfigProperty(name = "category.port")
    Integer port;

    public String getHostName() {
        return host + ":" + port;
    }
}
