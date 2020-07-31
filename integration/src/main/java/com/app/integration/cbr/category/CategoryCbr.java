package com.app.integration.cbr.category;

import com.app.integration.cbr.category.processor.CategoryEnrichProcessor;
import com.app.integration.cbr.category.strategy.CategoryGetAllAggregationStrategy;
import com.app.integration.cbr.category.strategy.CategoryGetByPublicIdAggregationStrategy;
import org.apache.camel.builder.RouteBuilder;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class CategoryCbr extends RouteBuilder {

    @Inject
    CategoryEnrichProcessor categoryEnrichProcessor;

    @Inject
    CategoryGetAllAggregationStrategy categoryGetAllAggregationStrategy;

    @Inject
    CategoryGetByPublicIdAggregationStrategy categoryGetByPublicIdAggregationStrategy;

    @Override
    public void configure() throws Exception {
        from("direct:category-cbr-get-by-public-id")
            //IN: header publicId
            .to("direct:category-service-get-by-public-id")
            //IN: body Category
            .enrich("direct:category-product-enrich", categoryGetByPublicIdAggregationStrategy);
            //OUT: body Category

        from("direct:category-cbr-get-all")
            .to("direct:category-service-get-all")
            //IN: body List<Category>
            .enrich("direct:category-product-enrich", categoryGetAllAggregationStrategy);
            //OUT: body List<Category>

        from("direct:category-product-enrich")
            .process(categoryEnrichProcessor)
            //IN: body null
            .to("direct:product-service-get-all");
            //OUT: body List<Product>
    }
}
