package com.app.integration.service.category.route;

import com.app.integration.service.category.payload.Category;
import com.app.integration.service.category.processor.CategoryGetByPublicIdProcessor;
import com.app.integration.service.config.BaseRoute;
import com.app.integration.service.config.CategoryRouteConfig;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CategoryServiceGetByPublicId extends BaseRoute {

    public CategoryServiceGetByPublicId(CategoryRouteConfig config, CategoryGetByPublicIdProcessor categoryGetByPublicIdProcessor) {
        withRoute(
                routeBuilder()
                    .from("direct:category-service-get-by-public-id")
                    .process(categoryGetByPublicIdProcessor)
                    .to(config.getHostName() + "/category/${body}")
                    .method("GET")
                    .unmarshal(Category.class)
                .end()
        );
    }
}