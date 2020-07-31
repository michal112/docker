package com.app.integration.service.product.route;

import com.app.integration.service.config.BaseRoute;
import com.app.integration.service.config.ProductRouteConfig;
import com.app.integration.service.product.payload.Product;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ProductServiceGetAll extends BaseRoute {

    public ProductServiceGetAll(ProductRouteConfig config) {
        withRoute(
                routeBuilder()
                    .from("direct:product-service-get-all")
                    .to(config.getHostName() + "/product")
                    .method("GET")
                    .unmarshal(Product.class)
                    .useList()
                .end()
        );
    }
}