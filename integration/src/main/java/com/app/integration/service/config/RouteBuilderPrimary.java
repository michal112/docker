package com.app.integration.service.config;

import org.apache.camel.Processor;

public interface RouteBuilderPrimary {

    RouteBuilderPrimary from(String from);

    RouteBuilderPrimary to(String uri);

    RouteBuilderSecondary method(String method);

    RouteBuilderPrimary process(Processor processor);
}
