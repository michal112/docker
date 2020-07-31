package com.app.integration.adapter;

import org.apache.camel.builder.RouteBuilder;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RestConfigurationRoute extends RouteBuilder {

    @ConfigProperty(name = "rest.host")
    String host;

    @ConfigProperty(name = "rest.port")
    Integer port;

    @Override
    public void configure() throws Exception {
        restConfiguration()
                .component("netty-http")
                .host(host)
                .port(port);
    }
}
