package com.app.integration.service.config;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;

public abstract class BaseRoute extends RouteBuilder {

    @Inject
    JacksonUtils utils;

    private Route route;

    @Override
    public void configure() throws Exception {
        from(route.getFrom())
                .removeHeader(Exchange.HTTP_PATH)
                .process(exchange -> {
                        if (route.getProcessor() != null) {
                            route.getProcessor().process(exchange);
                        }
                    }
                )
                .setHeader(Exchange.HTTP_METHOD, constant(route.getMethod()))
                .setHeader("Accept", constant(MediaType.APPLICATION_JSON))
                .log("Request body: ${body}")
                .toD("http://" +
                        route.getTo() +
                        "?bridgeEndpoint=true&throwExceptionOnFailure=false"
                )
                .convertBodyTo(String.class)
                .log("Response body: ${body}")
                .unmarshal(utils.getJacksonDataFormat(route.getUnmarshal(), route.useList()));
    }

    protected void withRoute(Route route) {
        this.route = route;
    }

    protected RouteBuilderPrimary routeBuilder() {
        return Route.RouteBuilder.get();
    }
}
