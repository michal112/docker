package com.app.integration.adapter.category;

import com.app.integration.service.category.payload.Category;
import com.app.integration.service.config.JacksonUtils;
import org.apache.camel.builder.RouteBuilder;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class CategoryAdapter extends RouteBuilder {

    @Inject
    JacksonUtils utils;

    @Override
    public void configure() throws Exception {
        rest()
            .get("/category")
                .route()
                .to("direct:category-cbr-get-all")
                .marshal(utils.getJacksonDataFormat(Category.class, true))
                .endRest()
            .get("/category/{publicId}")
                .route()
                .to("direct:category-cbr-get-by-public-id")
                .marshal(utils.getJacksonDataFormat(Category.class, false))
                .endRest();
    }
}
