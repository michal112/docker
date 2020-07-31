package com.app.integration.cbr.product;

import com.app.integration.cbr.product.processor.ProductEnrichProcessor;
import com.app.integration.cbr.product.strategy.ProductGetAllAggregationStrategy;
import com.app.integration.cbr.product.strategy.ProductGetByPublicIdAggregationStrategy;
import org.apache.camel.builder.RouteBuilder;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class ProductCbr extends RouteBuilder {

    @Inject
    ProductEnrichProcessor productEnrichProcessor;

    @Inject
    ProductGetAllAggregationStrategy productGetAllAggregationStrategy;

    @Inject
    ProductGetByPublicIdAggregationStrategy productGetByPublicIdAggregationStrategy;

    @Override
    public void configure() throws Exception {
        from("direct:product-cbr-get-by-public-id")
            //IN: header publicId
            .to("direct:product-service-get-by-public-id")
            //IN: body Product
            .enrich("direct:product-category-enrich", productGetByPublicIdAggregationStrategy);
            //OUT: body Product

        from("direct:product-cbr-get-all")
            .to("direct:product-service-get-all")
            //IN: body List<Product>
            .enrich("direct:product-category-enrich", productGetAllAggregationStrategy);
            //OUT: body List<Product>

        from("direct:product-category-enrich")
            .process(productEnrichProcessor)
            //IN: body null
            .to("direct:category-service-get-all");
            //OUT: body List<Category>
    }
}
