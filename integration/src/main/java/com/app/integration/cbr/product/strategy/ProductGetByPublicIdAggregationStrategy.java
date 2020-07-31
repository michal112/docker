package com.app.integration.cbr.product.strategy;

import com.app.integration.service.category.payload.Category;
import com.app.integration.service.product.payload.Product;
import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class ProductGetByPublicIdAggregationStrategy implements AggregationStrategy {

    @Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        // oldExchange body Product
        // newExchange body List<Category>
        Product product = oldExchange.getIn().getBody(Product.class);

        String categoryName = getCategoryName(newExchange, product.getCategoryPublicId());
        product.setCategoryName(categoryName);

        return oldExchange;
    }

    private String getCategoryName(Exchange exchange, String categoryPublicId) {
        List<Category> categories = exchange.getIn().getBody(List.class);

        return categories.stream()
                .filter(category -> category.getPublicId().equals(categoryPublicId))
                .findFirst()
                .map(Category::getName)
                .get();
    }
}
