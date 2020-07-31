package com.app.integration.service.config;

public interface RouteBuilderSecondary {

    RouteBuilderSecondary unmarshal(Class<?> clazz);

    RouteBuilderSecondary useList();

    Route end();
}
