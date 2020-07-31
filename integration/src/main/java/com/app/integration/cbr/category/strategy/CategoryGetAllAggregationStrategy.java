package com.app.integration.cbr.category.strategy;

import com.app.integration.service.category.payload.Category;
import com.app.integration.service.product.payload.Product;
import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ApplicationScoped
public class CategoryGetAllAggregationStrategy implements AggregationStrategy {

    @Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        // oldExchange body List<Category>
        // newExchange body List<Product>
        List<Category> categories = oldExchange.getIn().getBody(List.class);

        Map<String, List<Product>> products = getProductsMap(newExchange);
        for (Category category : categories) {
            category.setProducts(products.get(category.getPublicId()));
        }

        return oldExchange;
    }

    private Map<String, List<Product>> getProductsMap(Exchange exchange) {
        List<Product> products = exchange.getIn().getBody(List.class);

        return products.stream()
                .collect(Collectors.groupingBy(Product::getCategoryPublicId));
    }
}
