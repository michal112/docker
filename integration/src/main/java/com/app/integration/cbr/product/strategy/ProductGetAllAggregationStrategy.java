package com.app.integration.cbr.product.strategy;

import com.app.integration.service.category.payload.Category;
import com.app.integration.service.product.payload.Product;
import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ApplicationScoped
public class ProductGetAllAggregationStrategy implements AggregationStrategy {

    @Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        // oldExchange body List<Product>
        // newExchange body List<Category>
        List<Product> products = oldExchange.getIn().getBody(List.class);

        Map<String, String> categories = getCategoryNameMap(newExchange);
        for (Product product : products) {
            product.setCategoryName(categories.get(product.getCategoryPublicId()));
        }

        return oldExchange;
    }

    private Map<String, String> getCategoryNameMap(Exchange exchange) {
        List<Category> categories = exchange.getIn().getBody(List.class);

        return categories.stream()
                .collect(Collectors.toMap(Category::getPublicId, Category::getName));
    }
}
