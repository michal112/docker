package com.app.integration.cbr.category.strategy;

import com.app.integration.service.category.payload.Category;
import com.app.integration.service.product.payload.Product;
import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class CategoryGetByPublicIdAggregationStrategy implements AggregationStrategy {

    @Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        // oldExchange body Category
        // newExchange body List<Product>
        Category category = oldExchange.getIn().getBody(Category.class);

        List<Product> products = getCategoryProducts(newExchange, category.getPublicId());
        category.setProducts(products);

        return oldExchange;
    }

    private List<Product> getCategoryProducts(Exchange exchange, String categoryPublicId) {
        List<Product> products = exchange.getIn().getBody(List.class);

        return products.stream()
                .filter(it -> categoryPublicId.equals(it.getCategoryPublicId()))
                .collect(Collectors.toList());
    }
}
