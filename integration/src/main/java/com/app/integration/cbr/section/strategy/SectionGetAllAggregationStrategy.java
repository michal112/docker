package com.app.integration.cbr.section.strategy;

import com.app.integration.service.product.payload.Product;
import com.app.integration.service.section.payload.Section;
import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class SectionGetAllAggregationStrategy implements AggregationStrategy {

    @Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        // oldExchange body List<Section>
        // newExchange body List<Product>
        List<Section> sections = oldExchange.getIn().getBody(List.class);
        List<Product> products = newExchange.getIn().getBody(List.class);
        var productsByCategory = products.stream()
                .collect(Collectors.groupingBy(Product::getCategoryPublicId));

        sections.forEach(section ->
            section.getCategories().forEach(category -> {
                var categoryProducts = productsByCategory.get(category.getPublicId());
                categoryProducts.forEach(p -> p.setCategoryName(category.getName()));
                category.setProducts(categoryProducts);
            })
        );
        return oldExchange;
    }
}
