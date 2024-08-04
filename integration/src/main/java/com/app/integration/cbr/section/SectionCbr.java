package com.app.integration.cbr.section;

import com.app.integration.cbr.section.strategy.SectionAssignCategoryAggregationStrategy;
import com.app.integration.cbr.section.strategy.SectionGetAllAggregationStrategy;
import com.app.integration.cbr.strategy.NullBodyProcessor;
import org.apache.camel.builder.RouteBuilder;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class SectionCbr extends RouteBuilder {

    @Inject
    SectionGetAllAggregationStrategy sectionGetAllAggregationStrategy;

    @Inject
    SectionAssignCategoryAggregationStrategy sectionAssignCategoryAggregationStrategy;

    @Inject
    NullBodyProcessor nullBodyProcessor;

    @Override
    public void configure() throws Exception {
        from("direct:section-cbr-get-all")
            .to("direct:section-service-get-all")
            //IN: body List<Section>
            .enrich("direct:section-products-enrich", sectionGetAllAggregationStrategy);
            //OUT: body List<Section>

        from("direct:section-category-assign")
            .to("direct:section-service-category-assign")
            //IN: body Section
            .enrich("direct:section-products-enrich", sectionAssignCategoryAggregationStrategy);
            //OUT: body Section

        from("direct:section-products-enrich")
            .process(nullBodyProcessor)
            //IN: body null
            .to("direct:product-service-get-all");
            //OUT: body List<Product>
    }
}
