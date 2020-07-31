package com.app.integration.service.product.route;

import com.app.integration.service.config.BaseRoute;
import com.app.integration.service.config.ProductRouteConfig;
import com.app.integration.service.product.payload.Product;
import com.app.integration.service.product.processor.ProductGetByPublicIdProcessor;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ProductServiceGetByPublicId extends BaseRoute {

    public ProductServiceGetByPublicId(ProductRouteConfig config, ProductGetByPublicIdProcessor productGetByPublicIdProcessor) {
        withRoute(
                routeBuilder()
                    .from("direct:product-service-get-by-public-id")
                    .process(productGetByPublicIdProcessor)
                    .to(config.getHostName() + "/product/${body}")
                    .method("GET")
                    .unmarshal(Product.class)
                .end()
        );
    }
}