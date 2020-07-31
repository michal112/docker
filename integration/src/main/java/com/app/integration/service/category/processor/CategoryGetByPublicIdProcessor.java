package com.app.integration.service.category.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CategoryGetByPublicIdProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        String publicId = exchange.getIn().getHeader("publicId", String.class);
        exchange.getIn().setBody(publicId, String.class);
    }
}
