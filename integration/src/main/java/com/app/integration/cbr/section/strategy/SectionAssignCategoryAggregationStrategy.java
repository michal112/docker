package com.app.integration.cbr.section.strategy;

import com.app.integration.service.product.payload.Product;
import com.app.integration.service.section.payload.Section;
import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class SectionAssignCategoryAggregationStrategy implements AggregationStrategy {

    @Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        // oldExchange body Section
        // newExchange body List<Product>
        var section = oldExchange.getIn().getBody(Section.class);
        List<Product> products = newExchange.getIn().getBody(List.class);
        var productsByCategory = products.stream()
                .collect(Collectors.groupingBy(Product::getCategoryPublicId));

        section.getCategories().forEach(category -> {
            var categoryProducts = productsByCategory.get(category.getPublicId());
            categoryProducts.forEach(p -> p.setCategoryName(category.getName()));
            category.setProducts(categoryProducts);
        });
        return oldExchange;
    }
}
