package com.app.integration.adapter.product;

import com.app.integration.service.config.JacksonUtils;
import com.app.integration.service.product.payload.Product;
import org.apache.camel.builder.RouteBuilder;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class ProductAdapter extends RouteBuilder {

    @Inject
    JacksonUtils utils;

    @Override
    public void configure() throws Exception {
        rest()
            .get("/product")
                .route()
                .to("direct:product-cbr-get-all")
                .marshal(utils.getJacksonDataFormat(Product.class, true))
                .endRest()
            .get("/product/{publicId}")
                .route()
                .to("direct:product-cbr-get-by-public-id")
                .marshal(utils.getJacksonDataFormat(Product.class, false))
                .endRest();
    }
}
