package com.app.integration.service.category.payload;

import com.app.integration.service.product.payload.Product;

import java.util.List;

public class Category {

    private String publicId;

    private String name;

    //enrich
    private List<Product> products;

    public Category() {
    }

    public String getPublicId() {
        return publicId;
    }

    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
