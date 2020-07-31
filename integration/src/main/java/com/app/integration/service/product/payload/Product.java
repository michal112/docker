package com.app.integration.service.product.payload;

import java.time.LocalDate;

public class Product {

    private String publicId;

    private String name;

    private Integer price;

    private LocalDate productionDate;

    private LocalDate bestBefore;

    private String categoryPublicId;

    //enrich
    private String categoryName;

    public Product() {
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

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public LocalDate getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(LocalDate productionDate) {
        this.productionDate = productionDate;
    }

    public LocalDate getBestBefore() {
        return bestBefore;
    }

    public void setBestBefore(LocalDate bestBefore) {
        this.bestBefore = bestBefore;
    }

    public String getCategoryPublicId() {
        return categoryPublicId;
    }

    public void setCategoryPublicId(String categoryPublicId) {
        this.categoryPublicId = categoryPublicId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}