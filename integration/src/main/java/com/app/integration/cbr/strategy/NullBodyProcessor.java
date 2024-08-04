package com.app.integration.cbr.strategy;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class NullBodyProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        exchange.getIn().setBody(null);
    }
}
