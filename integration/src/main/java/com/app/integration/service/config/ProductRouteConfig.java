package com.app.integration.service.config;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ProductRouteConfig {

    @ConfigProperty(name = "product.host")
    String host;

    @ConfigProperty(name = "product.port")
    Integer port;

    public String getHostName() {
        return host + ":" + port;
    }
}
