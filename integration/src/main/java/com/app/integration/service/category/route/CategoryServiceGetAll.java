package com.app.integration.service.category.route;

import com.app.integration.service.category.payload.Category;
import com.app.integration.service.config.BaseRoute;
import com.app.integration.service.config.CategoryRouteConfig;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CategoryServiceGetAll extends BaseRoute {

    public CategoryServiceGetAll(CategoryRouteConfig config) {
        withRoute(
                routeBuilder()
                    .from("direct:category-service-get-all")
                    .to(config.getHostName() + "/category")
                    .method("GET")
                    .unmarshal(Category.class)
                    .useList()
                .end()
        );
    }
}