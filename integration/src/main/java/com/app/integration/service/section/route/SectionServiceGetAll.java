package com.app.integration.service.section.route;

import com.app.integration.service.config.BaseRoute;
import com.app.integration.service.config.CategoryRouteConfig;
import com.app.integration.service.product.payload.Product;
import com.app.integration.service.section.payload.Section;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SectionServiceGetAll extends BaseRoute {

    public SectionServiceGetAll(CategoryRouteConfig config) {
        withRoute(
                routeBuilder()
                    .from("direct:section-service-get-all")
                    .to(config.getHostName() + "/section")
                    .method("GET")
                    .unmarshal(Section.class)
                    .useList()
                .end()
        );
    }
}