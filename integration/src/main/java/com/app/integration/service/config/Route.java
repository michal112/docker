package com.app.integration.service.config;

import org.apache.camel.Processor;

public class Route {

    private String from;

    private String to;

    private String method;

    private Processor processor;
    
    private Class<?> unmarshal;

    private boolean useList;

    public static class RouteBuilder implements RouteBuilderPrimary, RouteBuilderSecondary {

        private String from;

        private String to;

        private String method;

        private Processor processor;

        private Class<?> unmarshal;

        private boolean useList;

        private RouteBuilder() {
        }

        public static RouteBuilderPrimary get() {
            return new RouteBuilder();
        }

        @Override
        public RouteBuilderPrimary from(String from) {
            this.from = from;
            return this;
        }

        @Override
        public RouteBuilderPrimary to(String to) {
            this.to = to;
            return this;
        }

        @Override
        public RouteBuilderSecondary method(String method) {
            this.method = method;
            return this;
        }

        @Override
        public RouteBuilderPrimary process(Processor processor) {
            this.processor = processor;
            return this;
        }

        @Override
        public RouteBuilderSecondary unmarshal(Class<?> unmarshal) {
            this.unmarshal = unmarshal;
            return this;
        }

        @Override
        public RouteBuilderSecondary useList() {
            this.useList = true;
            return this;
        }

        @Override
        public Route end() {
            Route route = new Route();

            route.from = this.from;
            route.to = this.to;
            route.method = this.method;
            route.processor = this.processor;
            route.unmarshal = this.unmarshal;
            route.useList = this.useList;

            return route;
        }
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getMethod() {
        return method;
    }

    public Processor getProcessor() {
        return processor;
    }

    public Class<?> getUnmarshal() {
        return unmarshal;
    }

    public boolean useList() {
        return useList;
    }
}
